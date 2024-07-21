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
package com.sicheng.wap.service;

import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.utils.TradeOrderUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.UserAgentUtils;
import com.sicheng.common.web.R;
import com.sicheng.service.TradeStockService;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private LogisticsTemplateService logisticsTemplateService;
    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private TradeStockService tradeStockService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;
    @Autowired
    private StoreService storeService;

    /**
     * 成交额
     *
     * @param wrapper
     * @return
     */
    public Map<String, Object> sumByWhere(Wrapper wrapper) {
        return dao.sumByWhere(wrapper);
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
     * 添加订单
     *
     * @param keys       cartMap中所有的键,存放格式 storeName-storeId
     * @param address    收货地址
     * @param deliverId  发票id
     * @param cartMap    购物车map
     * @param cartIdList 购物车id list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<TradeOrder> addOrder(String[] keys, MemberAddress address, String deliverId, Map<String, List<TradeCart>> cartMap, List<Object> cartIdList, String paySwitch) {
        List<TradeOrder> orderList = new ArrayList<>();
        String outTradeNo = Long.toString(System.currentTimeMillis() / 1000);
        for (int i = 0; i < keys.length; i++) {
            String storeIdStr=keys[i].trim();
            Store store=storeService.selectById(Long.parseLong(storeIdStr));
            TradeOrder order = new TradeOrder();
            order.setUId(AppTokenUtils.findUser().getUId());//属主检查
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
                    if ("1".equals(type) || ("3".equals(type) && !AppTokenUtils.findUser().isTypeUserPurchaser())) {
                        price = cart.getProductSku().getPrice();
                    }
                    if ("2".equals(type) || ("3".equals(type) && AppTokenUtils.findUser().isTypeUserPurchaser())) {
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
     * @param price      商品总价格
     * @param productSpu 商品spu
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
     * 取消订单
     *
     * @param tradeOrder 订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(TradeOrder tradeOrder) {
        TradeOrder condition = new TradeOrder();
        condition.setOrderId(tradeOrder.getOrderId());//属主检查
        UserMain userMain = AppTokenUtils.findUser();
        condition.setUId(userMain.getUId());//属主检查
        super.updateByWhereSelective(tradeOrder, new Wrapper(condition));
        Long uId = 0L;
        String messageTemplateNum = SiteSendMessagsService.STORE_CANCEL_ORDER;
        TradeOrder tradeOrder2 = super.selectOne(new Wrapper(condition));
        if (tradeOrder2.getStore() != null && tradeOrder2.getStore().getUserMain() != null) {
            uId = tradeOrder2.getStore().getUserMain().getId();
        }
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
     * 确认收货
     *
     * @param tradeOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void delayedReceipt(TradeOrder tradeOrder) {
        TradeOrder condition = new TradeOrder();
        condition.setOrderId(tradeOrder.getOrderId());//属主检查
        UserMain userMain = AppTokenUtils.findUser();
        condition.setUId(userMain.getUId());//属主检查
        super.updateByWhereSelective(tradeOrder, new Wrapper(condition));
        TradeOrder tradeOrder2 = super.selectOne(new Wrapper(condition));
        if (tradeOrder2 == null) {
            return;
        }
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", tradeOrder.getOrderId().toString());
        contentMap.put("finishTime", DateUtils.formatDateTime(tradeOrder.getProductReceiptDate()));
        //给买家发送消息
        String messageTemplateNum1 = SiteSendMessagsService.MEMBER_ORDERS_RESEIVE;
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum1, tradeOrder.getUId());
        //给商家发送消息
        String messageTemplateNum2 = SiteSendMessagsService.STORE_ORDERS_RESEIVE;
        if (tradeOrder2.getStore() != null && tradeOrder2.getStore().getUserMain() != null) {
            Long uId2 = tradeOrder2.getStore().getUserMain().getUId();
            siteSendMessagsService.sendMessage(contentMap, messageTemplateNum2, uId2);
        }
    }
}