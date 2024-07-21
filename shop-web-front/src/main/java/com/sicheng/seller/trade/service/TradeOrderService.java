/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.seller.trade.service;

import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.utils.TradeOrderUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.UserAgentUtils;
import com.sicheng.common.web.R;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;
import com.sicheng.seller.logistics.service.LogisticsTemplateItemService;
import com.sicheng.seller.logistics.service.LogisticsTemplateService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreService;
import com.sicheng.service.TradeStockService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 订单 Service
 *
 * @author fxx
 * @version 2017-01-05
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeOrderService extends CrudService<TradeOrderDao, TradeOrder> {

    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private LogisticsTemplateService logisticsTemplateService;
    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;
    @Autowired
    private TradeStockService tradeStockService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private StoreService storeService;

    /**
     * 添加订单
     *
     * @param keys       cartMap中所有的键,存放格式 storeName-storeId
     * @param address    收货地址
     * @param deliverId  发票id
     * @param cartMap    购物车map
     * @param cartIdList 购物车id list
     * @param paySwitch 在线支付的开启与关闭：0关闭、1开启
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<TradeOrder> addOrder(String[] keys, MemberAddress address, String deliverId, Map<String, List<TradeCart>> cartMap,/*List<TradeCart> cartList,*/List<Object> cartIdList,String paySwitch) {
        List<TradeOrder> orderList = new ArrayList<>();
        String outTradeNo = Long.toString(System.currentTimeMillis() / 1000);
        for (int i = 0; i < keys.length; i++) {
            String storeIdStr=keys[i].trim();
            Store store=storeService.selectById(Long.parseLong(storeIdStr));
            TradeOrder order = new TradeOrder();
            order.setUId(SsoUtils.getUserMain().getUserMember().getUId());
            order.setStoreId(Long.parseLong(storeIdStr));
            if(store!=null){
                order.setBName(store.getName());
            }
            order.setAddressId(address.getAddressId());
            order.setConsignee(address.getName());
            order.setPhone(address.getMobile());
            order.setZipCode(address.getZipCode());
            order.setProvinceName(address.getProvinceName());
            order.setCityName(address.getCityName());
            order.setDistrictName(address.getDistrictName());
            order.setDetailedAddress(address.getDetailedAddress());
            order.setOutTradeNo(outTradeNo);
            // 订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
            if("0".equals(paySwitch)){
                order.setOrderStatus("20");
            }else{
                order.setOrderStatus("10");
            }
            //是否需要支付：needPay(0.不需要、1.需要。)
            order.setNeedPay(paySwitch);
            // 是否退货退款，0否、1退货退款、2退款
            order.setIsReturnStatus("0");
            //留言
            String memo = R.get("memo_" + storeIdStr);
            if (StringUtils.isNotBlank(memo)) {
                order.setMemo(memo);
            }
            //发票
            if (StringUtils.isNotBlank(deliverId)) {
                order.setDeliverId(Long.parseLong(deliverId));
                // 是否开据发票，0否、1是
                order.setIsInvoice("1");
            } else {
                order.setIsInvoice("0");
            }
            order.setPlaceOrderTime(new Date());
            // 来源，0pc端、1移动端
            String sources = "1";
            Boolean isComputer = UserAgentUtils.isComputer(R.getRequest());
            if (isComputer) {
                sources = "0";
            }
            order.setSources(sources);
            orderList.add(order);
            //订单总金额
            BigDecimal productAmountPaid =new BigDecimal("0");
            //运费
            BigDecimal freight=new BigDecimal("0");
            List<TradeCart> carts = cartMap.get(storeIdStr);
            if (carts != null && !carts.isEmpty()) {
                //佣金
                BigDecimal fee = new BigDecimal("0");
                List<TradeOrderItem> tradeItems = new ArrayList<>();
                //累计同一个spu的数量，计算运费
                Map<Long,Integer> spuCountMap=new HashMap<>();
                for (TradeCart cart : carts) {
                    if(spuCountMap.get(cart.getPId())==null){
                        spuCountMap.put(cart.getPId(),0);
                    }
                    Integer pidCount=spuCountMap.get(cart.getPId());
                    spuCountMap.put(cart.getPId(),pidCount+cart.getCount());
                    //订单总金额
                    productAmountPaid=productAmountPaid.add(cart.getPriceSum());
                    //佣金比
                    BigDecimal price = new BigDecimal("0");
                    Integer count = cart.getCount();
                    String type = cart.getProductSpu().getType();
                    if ("1".equals(type) || ("3".equals(type) && !SsoUtils.getUserMain().isTypeUserPurchaser())) {
                        price = cart.getProductSku().getPrice();
                    }
                    if ("2".equals(type) || ("3".equals(type) && SsoUtils.getUserMain().isTypeUserPurchaser())) {
                        price = productSpuService.calculatePrice(cart.getProductSpu().getPId(), count);
                    }
                    fee = fee.add(getCommission(price.multiply(new BigDecimal(cart.getCount())), cart.getProductSpu()));
                    TradeOrderItem orderItem = new TradeOrderItem();
                    orderItem.setPId(cart.getPId());
                    orderItem.setSkuId(cart.getSkuId());
                    orderItem.setSkuValue(cart.getSkuValue());
                    orderItem.setName(cart.getProductSpu().getName());
                    orderItem.setPrice(price);
                    orderItem.setQuantity(cart.getCount());
                    orderItem.setBenefit(R.get("benefit_" + cart.getPId()));
                    if (cart.getProductSpu().getProductCategory() != null) {
                        orderItem.setCategory(cart.getProductSpu().getProductCategory().getName());
                    }
                    if (cart.getProductSpu().getStoreCategory() != null) {
                        orderItem.setStoreCategory(cart.getProductSpu().getStoreCategory().getName());
                    }
                    if (cart.getProductSpu().getProductBrand() != null) {
                        orderItem.setBrand(cart.getProductSpu().getProductBrand().getName());
                    }
                    orderItem.setType(cart.getProductSpu().getType());
                    // 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)
                    orderItem.setProductType("0");
                    orderItem.setWeight(cart.getProductSpu().getWeight());
                    orderItem.setIsAdditionalComment("0");
                    // 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
                    orderItem.setIsAdditionalComment("0");
                    if (cart.getProductSpu().getProductCategory() != null && cart.getProductSpu().getProductCategory().getCommission() != null) {
                        orderItem.setCommissionRatio(cart.getProductSpu().getProductCategory().getCommission().toString());
                    }
                    // 是否退货退款,0否、1退货退款、2退款
                    orderItem.setIsReturnStatus("0");
                    orderItem.setThumbnailPath(cart.getProductSpu().getImage());
                    //给订单详情添加了创建时间和跟新时间
                    orderItem.setCreateDate(new Date());
                    orderItem.setUpdateDate(new Date());
                    tradeItems.add(orderItem);
                    //更新商品库存
                    ProductSku sku = cart.getProductSku();
                    if (sku != null) {
                        //减可销售库存
                        tradeStockService.reduceSalableStock(sku.getSkuId(), cart.getCount());
                        //增加占用库存
                        tradeStockService.addOccupyStock(sku.getSkuId(), cart.getCount());
                    }
                }
                order.setFee(fee);
                order.setPkMode(1);
                order.setOrderId(TradeOrderUtils.creatOrderNumber("1"));
                //计算运费
                Set set = spuCountMap.keySet();
                for(Iterator iter = set.iterator(); iter.hasNext();) {
                    Long pid = (Long)iter.next();
                    ProductSpu productSpu=productSpuService.selectById(pid);
                    Integer count =spuCountMap.get(pid);
                    freight=freight.add(calculateFreight(productSpu, order.getMemberAddress(),count));
                }
                //总金额
                order.setAmountPaid(productAmountPaid.add(freight));
                //运费
                order.setFreight(freight);
                super.insertSelective(order);
                for (TradeOrderItem item : tradeItems) {
                    item.setOrderId(order.getOrderId());
                }
                tradeOrderItemService.insertBatch(tradeItems);
            }
        }
        //删除购物车数据
        tradeCartService.deleteByIdIn(cartIdList);
        //发送消息
        for (TradeOrder order : orderList) {
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("orderId", order.getOrderId().toString());
            String messageTemplateNum = SiteSendMessagsService.STORE_ORDERS_NEW;
            if (order.getStore() != null && order.getStore().getUserMain() != null) {
                Long uId = order.getStore().getUserMain().getId();
                siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
            }
        }
        return orderList;
    }

    /**
     * 获取佣金
     *
     * @param price           商品总价格
     * @param productSpu      商品分类
     * @return
     */
    public BigDecimal getCommission(BigDecimal price, ProductSpu productSpu) {
        BigDecimal commission = new BigDecimal("0");
        if (productSpu == null || price == null) {
            return commission;
        }
        //佣金类型：1无佣金，2根据商品分类计算佣金，3根据店铺计算分类
        String commissionType = Global.getConfig("trade.commissionType");
        if ("2".equals(commissionType)) {
            ProductCategory productCategory = productSpu.getProductCategory();
            if (productCategory == null || productCategory.getCommission() == null) {
                return commission;
            }
            commission = price.multiply(new BigDecimal(productCategory.getCommission().toString()));
        }
        if ("3".equals(commissionType)) {
            Store store = productSpu.getStore();
            if (store == null) {
                return commission;
            }
            commission = price.multiply(new BigDecimal(store.getCommission().toString()));
        }
        return commission;
    }

    /**
     * 计算运费
     *
     * @param productSpu    商品spu
     * @param memberAddress 收货地址
     * @param count         商品数量
     * @return
     */
    public BigDecimal calculateFreight(ProductSpu productSpu, MemberAddress memberAddress, Integer count) {
        BigDecimal freight = new BigDecimal("0");
        if (productSpu == null || memberAddress == null || count == null) {
            return freight;
        }
        //ExpressType:运费方式，0固定运费，1使用运费模板，2运费到付(买家承担)，3卖家承担运费
        if ("0".equals(productSpu.getExpressType())) {
            freight = productSpu.getExpressPrice();
            return freight;
        }
        if ("1".equals(productSpu.getExpressType())) {
            LogisticsTemplate template = logisticsTemplateService.selectById(productSpu.getLtId());
            if (template == null) {
                return freight;
            }
            List<LogisticsTemplateItem> logisticsTemplateItemList = template.getLogisticsTemplateItemList();
            //是否包邮，0自定义运费、1卖家承担运费
            //是否包邮，0自定义运费、1卖家承担运费,选择卖家承担运费并且没有指定具体的地址，说明所有地区都包邮
            if ("1".equals(template.getIsFreeShipping()) && (logisticsTemplateItemList == null || logisticsTemplateItemList.isEmpty())) {
                return freight;
            }
            //先根据cityId查询，如果查不到就用provinceId查询
            List<LogisticsTemplateItem> templateItemList = logisticsTemplateItemService.selectByWhere(new Wrapper().and("lt_id=", template.getLtId()).and("ids like", "%," + memberAddress.getCityId() + ",%"));
            if (templateItemList.isEmpty()) {
                templateItemList = logisticsTemplateItemService.selectByWhere(new Wrapper().and("lt_id=", template.getLtId()).and("ids like", "%," + memberAddress.getProvinceId() + ",%"));
            }
            if (templateItemList.isEmpty()) {
                return new BigDecimal("-1");
            }
            LogisticsTemplateItem templateItem = templateItemList.get(0);
            //是否包邮，0自定义运费、1卖家承担运费
            if ("1".equals(template.getIsFreeShipping())) {
                return freight;
            }
            // 计价方式，0按件数、1按重量、2按体积
            String valuationMethod = template.getValuationMethod();
            if ("0".equals(valuationMethod)) {
                //如果数量小于首件（重），则运费等于首重的价格（元），否则 运费=首重的价格（元）+（数量-首件（重））/续（件）重*续件的价格
                if (count <= templateItem.getFirstItem()) {
                    freight = templateItem.getFirstPrice();
                } else {
                    //总续件=总数量-首件
                    BigDecimal totalContinueItem = new BigDecimal(count - templateItem.getFirstItem());
                    //续件
                    BigDecimal continueItem = new BigDecimal(templateItem.getContinueItem());
                    //总续件价格=总续件/续件*续件的价格
                    BigDecimal continuePrice = totalContinueItem.divide(continueItem).multiply(templateItem.getContinuePrice());
                    //运费= 首件的价格+总续件的价格
                    freight = templateItem.getFirstPrice().add(continuePrice);
                }
            } else if ("1".equals(valuationMethod)) {
                if (productSpu.getWeight() <= templateItem.getFirstItem()) {
                    freight = templateItem.getFirstPrice();
                } else {
                    //商品重量
                    BigDecimal weight = new BigDecimal(productSpu.getWeight());
                    //总续重=总重量-首重
                    BigDecimal totalContinueWeight = weight.subtract(new BigDecimal(templateItem.getFirstItem()));
                    //续重
                    BigDecimal continueWeight = new BigDecimal(templateItem.getContinueItem());
                    //总续重价格=总续重/续重*续中的价格
                    BigDecimal totalContinuePrice = totalContinueWeight.divide(continueWeight).multiply(templateItem.getContinuePrice());
                    //运费= 首重的价格+总续重的价格
                    freight = templateItem.getFirstPrice().add(totalContinuePrice);
                }
            } else if ("2".equals(valuationMethod)) {
                if (productSpu.getVolume() <= templateItem.getFirstItem()) {
                    freight = templateItem.getFirstPrice();
                } else {
                    //商品体积
                    BigDecimal volume = new BigDecimal(productSpu.getVolume());
                    //总续重=总重量-首重
                    BigDecimal totalContinueVolume = volume.subtract(new BigDecimal(templateItem.getFirstItem()));
                    //续重
                    BigDecimal continueVolume = new BigDecimal(templateItem.getContinueItem());
                    //总续重价格=总续重/续重*续中的价格
                    BigDecimal totalContinuePrice = totalContinueVolume.divide(continueVolume).multiply(templateItem.getContinuePrice());
                    //运费= 首重的价格+总续重的价格
                    freight = templateItem.getFirstPrice().add(totalContinuePrice);
                }
            }
        }
        return freight;
    }

    /**
     * 发货
     *
     * @param tradeOrder 订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void deliverGoods(TradeOrder tradeOrder) {
        //按多个条件进行修改
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        TradeOrder condition = new TradeOrder();
        condition.setOrderId(tradeOrder.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        super.updateByWhereSelective(tradeOrder, new Wrapper(condition));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", tradeOrder.getOrderId().toString());
        contentMap.put("sendTime", DateUtils.formatDateTime(tradeOrder.getDeliverProductDate()));
        contentMap.put("trackingNo", tradeOrder.getTrackingNo());
        String messageTemplateNum = SiteSendMessagsService.MEMBER_ORDERS_SEND;
        Long uId = tradeOrder.getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        //更新库存
        List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
        if (tradeOrderItemList != null && !tradeOrderItemList.isEmpty()) {
            for (TradeOrderItem order : tradeOrderItemList) {
                //减少占用库存
                tradeStockService.reduceOccupyStock(order.getSkuId(), order.getQuantity());
            }
        }
    }


    /**
     *  当取消的订单存在支付方式时，查询第三方支付订单，
     * 并根据第三方返回的状态，调整当前订单的状态
     *  @param tradeOrder 订单信息
     *  @param queryType 取消定单的用户类型(1商家、2会员)
     *  @param map 查询结果返回信息
     *  @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> queryAndCancelOrder(TradeOrder tradeOrder, String queryType) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (tradeOrder == null || tradeOrder.getPaymentMethodId() == null) {
            return map;
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PayConstants.OUT_TRADE_NO, tradeOrder.getOutTradeNo());
        String payWayNum = tradeOrder.getSettlementPayWay().getPayWayNum();//支付方式编号
        ShopPay shopPay = ShopPayFactory.get(payWayNum);
        try {
            Map<String, Object> mapResult = new HashMap<>();
            mapResult = (Map<String, Object>) shopPay.query(paramMap);
            //第三方支付支付成功，修改订单状态为已支付
            if ("SUCCESS".equals(mapResult.get("tradeState"))) {
                tradeOrder.setOrderStatus("20");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrder.setPayOrderTime(new Date());
                super.updateByIdSelective(tradeOrder);
                map.put("statusCode", "202");
                map.put("message", FYUtils.fyParams("订单:") + tradeOrder.getId() + FYUtils.fyParams("已付款,不能取消，状态已修改为待发货"));
            } else {
                //第三方支付支付失败，修改订单状态为已取消
                tradeOrder.setOrderStatus("60");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrder.setCancelOrderDate(new Date());
                cancelOrder(tradeOrder, queryType);
                map.put("statusCode", "201");
                map.put("message", FYUtils.fyParams("取消订单成功"));
            }
            return map;
        } catch (PayException e) {
            map.put("statusCode", "500");
            map.put("message", FYUtils.fyParams("查询第三方订单发生错误：") + e.getMessage());
            logger.error("查询第三方订单发生异常：", e);
            return map;
        }
    }


    /**
     * 取消订单
     *
     * @param tradeOrder 订单
     * @param type       会员类型 (1商家,2买家)
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(TradeOrder tradeOrder, String type) {
        TradeOrder condition = new TradeOrder();
        condition.setOrderId(tradeOrder.getId());//属主检查
        UserMain userMain = SsoUtils.getUserMain();
        String messageTemplateNum = "";
        Long uId = 0L;
        //会员类型 (1商家,2买家)
        if ("1".equals(type)) {
            condition.setStoreId(userMain.getUserSeller().getStoreId());//属主检查
            messageTemplateNum = SiteSendMessagsService.MEMBER_CANCEL_ORDER;
            uId = tradeOrder.getUId();
        } else {
            condition.setUId(userMain.getUId());//属主检查
            messageTemplateNum = SiteSendMessagsService.STORE_CANCEL_ORDER;
            if (tradeOrder.getStore() != null && tradeOrder.getStore().getUserMain() != null) {
                uId = tradeOrder.getStore().getUserMain().getId();
            }
        }
        super.updateByWhereSelective(tradeOrder, new Wrapper(condition));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", tradeOrder.getOrderId().toString());
        contentMap.put("cancleTime", DateUtils.formatDate(tradeOrder.getCancelOrderDate()));
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        //更新库存
        List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
        if (tradeOrderItemList != null && !tradeOrderItemList.isEmpty()) {
            for (TradeOrderItem order : tradeOrderItemList) {
                //减少占用库存
                tradeStockService.reduceOccupyStock(order.getSkuId(), order.getQuantity());
                //增加可销售库存
                tradeStockService.addSalableStock(order.getSkuId(), order.getQuantity());
            }
        }
    }

    /**
     * 导出订单
     *
     * @param tradeOrderList
     * @param response
     */
    public void exprotOrder(List<TradeOrder> tradeOrderList, HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/x-excel");//导出类型
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("订单信息.xls".getBytes(), StandardCharsets.ISO_8859_1));
            OutputStream outputStream = response.getOutputStream();
            //1.创建工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 20);
            //头标题样式
            HSSFCellStyle style1 = createCellStyle(workbook, (short) 16);
            //列标题样式
            HSSFCellStyle style2 = createCellStyle(workbook, (short) 13);
            //2、创建工作表
            HSSFSheet sheet = workbook.createSheet(FYUtils.fyParams("订单信息"));
            //加载并合并单元格对象
            sheet.addMergedRegion(cellRangeAddress);
            //设置默认列宽
            sheet.setDefaultColumnWidth(25);
            //3.创建行
            //创建头标题行，并且设置头标题
            HSSFRow row1 = sheet.createRow(0);
            HSSFCell cell1 = row1.createCell(0);
            //加载单元格样式
            cell1.setCellStyle(style1);
            cell1.setCellValue("订单信息列表");

            //创建列标题行，并且设置列标题
            HSSFRow headRow = sheet.createRow(1);
            String[] titles = {FYUtils.fyParams("订单编号"), FYUtils.fyParams("商品名称"), FYUtils.fyParams("商品单价"), FYUtils.fyParams("数量"), 
            		FYUtils.fyParams("会员"), FYUtils.fyParams("订单总额"), FYUtils.fyParams("订单状态"), FYUtils.fyParams("运费"), 
            				FYUtils.fyParams("是否需物流"), FYUtils.fyParams("物流公司"), FYUtils.fyParams("运单号"), FYUtils.fyParams("下单时间"),
            						FYUtils.fyParams("付款时间"), FYUtils.fyParams("发货时间"), FYUtils.fyParams("收货时间"), 
            								FYUtils.fyParams("收货人"), FYUtils.fyParams("邮编"), FYUtils.fyParams("电话"), 
            										FYUtils.fyParams("收货地址所在省"), FYUtils.fyParams("收货地址所在市"), 
            												FYUtils.fyParams("收货地址所在县"), FYUtils.fyParams("详细地址")};
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell2 = headRow.createCell(i);
                //加载单元格样式
                cell2.setCellStyle(style2);
                cell2.setCellValue(titles[i]);
            }
            //4.操作单元格，将订单信息列表写入excel
            int rowIndex = 2;
            for (int j = 0; j < tradeOrderList.size(); j++) {
                List<TradeOrderItem> tradeItemList = tradeOrderList.get(j).getTradeOrderItemList();
                HSSFRow row = sheet.createRow(rowIndex);
                HSSFCell cell0 = row.createCell(0);
                cell0.setCellValue(tradeOrderList.get(j).getOrderId().toString());
                //创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellOrderId = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 0, 0);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellOrderId);
                for (int i = 0; i < tradeItemList.size(); i++) {
                    if (i == 0) {
                        HSSFCell cell01 = row.createCell(1);
                        cell01.setCellValue(tradeItemList.get(i).getName());
                        HSSFCell cell2 = row.createCell(2);
                        BigDecimal price = tradeItemList.get(i).getPrice() == null ? new BigDecimal("0") : tradeItemList.get(i).getPrice();
                        cell2.setCellValue(price.toString());
                        HSSFCell cell14 = row.createCell(3);
                        cell14.setCellValue(tradeItemList.get(i).getQuantity());
                    } else {
                        HSSFRow row2 = sheet.createRow(rowIndex + i);
                        HSSFCell cell12 = row2.createCell(1);
                        cell12.setCellValue(tradeItemList.get(i).getName());
                        HSSFCell cell13 = row2.createCell(2);
                        cell13.setCellValue("0");
                        HSSFCell cell14 = row2.createCell(3);
                        cell14.setCellValue(tradeItemList.get(i).getQuantity());
                    }
                }
                //会员名   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellUserName = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 4, 4);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellUserName);
                HSSFCell cell15 = row.createCell(4);
                cell15.setCellValue(tradeOrderList.get(j).getUserMain().getLoginName());

                //订单总金额   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellAmountPaid = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 5, 5);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellAmountPaid);
                HSSFCell cell16 = row.createCell(5);
                cell16.setCellValue(tradeOrderList.get(j).getAmountPaid().toString());

                //订单状态   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellStatus = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 6, 6);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellStatus);
                HSSFCell cell17 = row.createCell(6);
                cell17.setCellValue(DictUtils.getDictLabel(tradeOrderList.get(j).getOrderStatus(), "trade_order_status", ""));

                //运费  创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellFreight = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 7, 7);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellFreight);
                HSSFCell cell18 = row.createCell(7);
                BigDecimal freight = tradeOrderList.get(j).getFreight() == null ? new BigDecimal("0") : tradeOrderList.get(j).getFreight();
                cell18.setCellValue(freight.toString());

                //是否需物流   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellIsNeedLogistics = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 8, 8);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellIsNeedLogistics);
                HSSFCell cell19 = row.createCell(8);
                cell19.setCellValue(DictUtils.getDictLabel(tradeOrderList.get(j).getIsNeedLogistics(), "yes_no", ""));

                //物流公司   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellLogiTemName = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 9, 9);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellLogiTemName);
                HSSFCell cell110 = row.createCell(9);
                cell110.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getLogisticsTemplateName()) ? "" : tradeOrderList.get(j).getLogisticsTemplateName());

                //运单号   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellTrackingNo = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 10, 10);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellTrackingNo);
                HSSFCell cell111 = row.createCell(10);
                cell111.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getTrackingNo()) ? "" : tradeOrderList.get(j).getTrackingNo());

                //下单时间   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellPlaceOrderTime = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 11, 11);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellPlaceOrderTime);
                HSSFCell cell112 = row.createCell(11);
                Date placeOrderTime = tradeOrderList.get(j).getPlaceOrderTime();
                if (placeOrderTime != null) {
                    cell112.setCellValue(DateUtils.formatDateTime(placeOrderTime));
                } else {
                    cell112.setCellValue("");
                }

                //付款时间   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellPayOrderTime = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 12, 12);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellPayOrderTime);
                HSSFCell cell113 = row.createCell(12);
                Date payOrderTime = tradeOrderList.get(j).getPayOrderTime();
                if (payOrderTime != null) {
                    cell113.setCellValue(DateUtils.formatDateTime(payOrderTime));
                } else {
                    cell113.setCellValue("");
                }

                //发货时间   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellDeliverProductDate = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 13, 13);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellDeliverProductDate);
                HSSFCell cell114 = row.createCell(13);
                Date deliverProductDate = tradeOrderList.get(j).getDeliverProductDate();
                if (deliverProductDate != null) {
                    cell114.setCellValue(DateUtils.formatDateTime(deliverProductDate));
                } else {
                    cell114.setCellValue("");
                }

                //收货时间   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellProductReceiptDate = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 14, 14);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellProductReceiptDate);
                HSSFCell cell115 = row.createCell(14);
                Date productReceiptDate = tradeOrderList.get(j).getProductReceiptDate();
                if (productReceiptDate != null) {
                    cell115.setCellValue(DateUtils.formatDateTime(productReceiptDate));
                } else {
                    cell115.setCellValue("");
                }

                //收货人   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellConsignee = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 15, 15);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellConsignee);
                HSSFCell cell116 = row.createCell(15);
                cell116.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getConsignee()) ? "" : tradeOrderList.get(j).getConsignee());

                //邮编   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellZipCode = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 16, 16);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellZipCode);
                HSSFCell cell117 = row.createCell(16);
                cell117.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getZipCode()) ? "" : tradeOrderList.get(j).getZipCode());

                //电话   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellPhone = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 17, 17);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellPhone);
                HSSFCell cell118 = row.createCell(17);
                cell118.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getPhone()) ? "" : tradeOrderList.get(j).getPhone());

                //收货地址所在省   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellProvinceName = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 18, 18);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellProvinceName);
                HSSFCell cell119 = row.createCell(18);
                cell119.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getProvinceName()) ? "" : tradeOrderList.get(j).getProvinceName());

                //收货地址所在市   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellCityName = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 19, 19);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellCityName);
                HSSFCell cell120 = row.createCell(19);
                cell120.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getCityName()) ? "" : tradeOrderList.get(j).getCityName());

                //收货地址所在县   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellDistrictName = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 20, 20);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellDistrictName);
                HSSFCell cell121 = row.createCell(20);
                cell121.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getDistrictName()) ? "" : tradeOrderList.get(j).getDistrictName());

                //详细地址   创建合并单元格对象 CellRangeAddress(起始行号，终止行号， 起始列号，终止列号)
                CellRangeAddress cellDetailedAddress = new CellRangeAddress(rowIndex, rowIndex + tradeItemList.size() - 1, 21, 21);
                //加载并合并单元格对象
                sheet.addMergedRegion(cellDetailedAddress);
                HSSFCell cell122 = row.createCell(21);
                cell122.setCellValue(StringUtils.isBlank(tradeOrderList.get(j).getDetailedAddress()) ? "" : tradeOrderList.get(j).getDetailedAddress());
                rowIndex += tradeItemList.size();
            }
            //输出
            workbook.write(outputStream);
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建单元格格式
     *
     * @param workbook 工作薄
     * @param fontSize 字体大小
     * @return 单元格样式
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
        font.setFontHeightInPoints(fontSize);
        //加载字体
        style.setFont(font);
        return style;
    }

    /**
     * 确认收货
     *
     * @param tradeOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delayedReceipt(TradeOrder tradeOrder) {
        Map<String, Object> map = new HashMap<>();
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        TradeOrder order = super.selectOne(new Wrapper().and("order_id =", tradeOrder.getId()).and("u_id = ", userMember.getUId()));
        BigDecimal orderAmountPaid = order.getAmountPaid();
        if (order.getOffsetAmount() != null) {
            orderAmountPaid = order.getOffsetAmount();
        }
        super.updateByWhereSelective(tradeOrder, new Wrapper().and("order_id =", tradeOrder.getId()).and("u_id = ", userMember.getUId()));
        map.put("success", true);
        return map;
    }

    /**
     * 成交额
     *
     * @param wrapper
     * @return
     */
    public Map<String, Object> sumByWhere(Wrapper wrapper) {
        return dao.sumByWhere(wrapper);
    }
}