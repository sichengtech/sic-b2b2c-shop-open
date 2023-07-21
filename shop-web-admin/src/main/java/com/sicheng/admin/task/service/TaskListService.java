/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.admin.task.service;

import com.sicheng.admin.account.service.SettlementBillService;
import com.sicheng.admin.account.service.SettlementTaskMainService;
import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.service.MemberCollectionProductService;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.entity.ProductSpuAnalyze;
import com.sicheng.admin.product.service.ProductSkuService;
import com.sicheng.admin.product.service.ProductSpuAnalyzeService;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.service.PurchaseService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.service.StoreAdminLogService;
import com.sicheng.admin.store.service.StoreService;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.sys.service.*;
import com.sicheng.admin.task.entity.TaskAdminIndex;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.service.TradeErrorPoolService;
import com.sicheng.admin.trade.service.TradeOrderItemService;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.admin.trade.service.TradeReturnOrderService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;


/**
 * <p>标题: TaskListService</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhangjiali
 * @version 2017年5月9日 下午1:06:35
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class TaskListService {

    protected Logger logger = LoggerFactory.getLogger(getClass());//日志对象
    @Autowired
    private SettlementTaskMainService settlementTaskMainService;
    @Autowired
    private SysTokenService sysTokenService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private MemberCollectionProductService collectionProductService;
    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TaskAdminIndexService taskAdminIndexService;
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SysVariableService variableService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SysTimedTaskLogService timedTaskLog;//定时任务日志
    @Autowired
    private LogService logService;//admin系统日志
    @Autowired
    private SysSmsLogService smsLogService;//短信日志
    @Autowired
    private StoreAdminLogService storeAdminLogService;//seller系统日志
    @Autowired
    private TradeErrorPoolService tradeErrorPoolService;
    @Autowired
    private SettlementBillService settlementBillService;
    @Autowired
    private PurchaseService purchaseService;
	/*@Autowired
    @Qualifier("weixinPay")
    ShopPay weixinPay;//微信支付
	
	@Autowired
    @Qualifier("aliPay")
    ShopPay aliPay;*/

    /**
     * 结算定时任务
     */
    public void settlementProductTask() {
        settlementTaskMainService.settlementTask("1");
    }

    /**
     * 清理sysToken 过期token
     */
    public void clearToken() {
        sysTokenService.clearToken();
    }

    /**
     * 计算店铺商品数
     */
    public void storeProductSum() {
        String sqlId = "selectByWhere";
        Cursor<Store> cursor = storeService.selectCursor(sqlId, new Wrapper());
        Iterator<Store> iter = cursor.iterator();
        while (iter.hasNext()) {
        	//每一家店铺
            Store store = iter.next();
            ProductSpu productSpu = new ProductSpu();
            productSpu.setStoreId(store.getId());
            //只查询在售的
            productSpu.setStatus("50");
            int productCount = productSpuService.countByWhere(new Wrapper(productSpu));//每一家店铺的商品数
            store.setProductCount(productCount);
            storeService.updateByIdSelective(store);
        }
    }

    /**
     * 清理30天前工程所有的日志
     */
    public void cleanLog() {
        timedTaskLog.deleteByWhere(new Wrapper().and("a.end_time<", new Date(new Date().getTime() - (30 * 24 * 60 * 60 * 1000L))));//清理30天前的定时任务日志
        logService.deleteByWhere(new Wrapper().and("a.create_date<", new Date(new Date().getTime() - (30 * 24 * 60 * 60 * 1000L))));//清理30天前的admin系统日志
        smsLogService.deleteByWhere(new Wrapper().and("a.send_date<", new Date(new Date().getTime() - (30 * 24 * 60 * 60 * 1000L))));//清理30天前的短信日志
        storeAdminLogService.deleteByWhere(new Wrapper().and("a.create_date<", new Date(new Date().getTime() - (30 * 24 * 60 * 60 * 1000L))));//清理30天前的seller系统日志
    }

    /**
     * 查询并修改会员收藏的商品的状态
     */
    public void updateCollectionProduct() {
        String sqlId = "selectByWhere";
        Cursor<MemberCollectionProduct> cursor = collectionProductService.selectCursor(sqlId, new Wrapper());
        Iterator<MemberCollectionProduct> iter = cursor.iterator();
        while (iter.hasNext()) {
            MemberCollectionProduct collectionProduct = iter.next();//买家收藏的商品
            ProductSpu productSpu = productSpuService.selectById(collectionProduct.getPId());//每一个商品
            if (productSpu != null) {
                if ("50".equals(productSpu.getStatus())) {// 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                    if ("0".equals(collectionProduct.getStatus())) {// 0、下架，1、上架
                        collectionProduct.setStatus("1");
                        collectionProductService.updateByIdSelective(collectionProduct);
                    }
                } else if ("1".equals(collectionProduct.getStatus())) {// 0、下架，1、上架
                    collectionProduct.setStatus("0");
                    collectionProductService.updateByIdSelective(collectionProduct);
                }
            }
        }
    }

    /**
     * 商品 最近30天销量
     */
    public void monthSales() {
        String sqlId = "selectByWhere";
        Cursor<ProductSpu> cursor = productSpuService.selectCursor(sqlId, new Wrapper());
        Iterator<ProductSpu> iter = cursor.iterator();
        while (iter.hasNext()) {
            ProductSpu productSpu = iter.next();//每一个商品（spu）
            TradeOrderItem tradeOrderItem = new TradeOrderItem();
            tradeOrderItem.setPId(productSpu.getId());
            tradeOrderItem.setIsReturnStatus("0");//是否退货退款,0否、1退货退款、2退款
            tradeOrderItem.setBeginCreateDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24 * 30L)));
            tradeOrderItem.setEndCreateDate(new Date());
            //商品统计（月销）
            Integer monthSales = tradeOrderItemService.sumByWhere(new Wrapper(tradeOrderItem));//某一个商品 最近30天销量
            if (monthSales == null) {
                monthSales = 0;
            }
            ProductSpuAnalyze productSpuAnalyze = productSpuAnalyzeService.selectById(productSpu.getId());//商品统计（商品的扩展表）
            MemberCollectionProduct collectionProduct = collectionProductService.selectOne(new Wrapper().and("a.p_id=", productSpu.getId()));//商品收藏（一个商品只收藏一次）
            //商品统计（总销量）
            TradeOrderItem entity = new TradeOrderItem();
            entity.setPId(productSpu.getId());
            entity.setIsReturnStatus("0");//是否退货退款,0否、1退货退款、2退款
            Integer allSales = tradeOrderItemService.sumByWhere(new Wrapper(entity));//某一个商品 总销量
            if (allSales == null) {
                allSales = 0;
            }
            if (productSpuAnalyze == null) {
                ProductSpuAnalyze productSpuAnalyze1 = new ProductSpuAnalyze();
                productSpuAnalyze1.setPkMode(1);
                productSpuAnalyze1.setPId(productSpu.getId());
                productSpuAnalyze1.setMonthSales(monthSales);
                productSpuAnalyze1.setMonthSalesDate(new Date());
                productSpuAnalyze1.setAllSales(allSales);
                productSpuAnalyze1.setAllSalesDate(new Date());
                productSpuAnalyzeService.insertSelective(productSpuAnalyze1);
            } else {
                productSpuAnalyze.setMonthSales(monthSales);
                productSpuAnalyze.setMonthSalesDate(new Date());
                productSpuAnalyze.setAllSales(allSales);
                productSpuAnalyze.setAllSalesDate(new Date());
                productSpuAnalyzeService.updateByIdSelective(productSpuAnalyze);
            }
            //商品收藏（月销）
            if (collectionProduct != null) {
                collectionProduct.setMonthSales(monthSales);
                collectionProduct.setCreateDate(new Date());
                collectionProductService.updateByIdSelective(collectionProduct);
            }
        }
    }

    /**
     * 定时计算出管理后台首页的统计数据
     */
    public void adminIndexInfo() {
        //初始化开始时间与结束时间
        Date sevenDaysDate = null;//7天前的开始时间
        Date beginDate = null;//今天开始时间
        Date endDate = null;//今天结束时间
        try {
            sevenDaysDate = DateUtils.parseDate(DateUtils.formatDate(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7L)), "yyyy/MM/dd ") + "00:00:00", "yyyy/MM/dd HH:mm:ss");
            beginDate = DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd ") + "00:00:00", "yyyy/MM/dd HH:mm:ss");
            endDate = DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd ") + "23:59:59", "yyyy/MM/dd HH:mm:ss");
        } catch (ParseException e) {
            logger.error("日期转换发生异常", e);
        }

        //搜索订单的时间条件
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBeginCreateDate(beginDate);
        tradeOrder.setEndCreateDate(endDate);

        //交易模块的统计
        BigDecimal moneycountday = new BigDecimal(0);
        Wrapper wrapper1 = new Wrapper(tradeOrder);
        wrapper1.and("a.order_status >", "10");
        wrapper1.and("a.order_status <", "60");

        String sqlId = "selectByWhere";
        Cursor<TradeOrder> cursor1 = tradeOrderService.selectCursor(sqlId, wrapper1);//今日交易订单
        Iterator<TradeOrder> iter1 = cursor1.iterator();
        while (iter1.hasNext()) {
            TradeOrder entity1 = iter1.next();//每一个订单
            if (entity1.getOffsetAmount() != null) {//OffsetAmount调整后的金额、amount_paid订单总金额
                moneycountday = moneycountday.add(entity1.getOffsetAmount());
            } else {
                moneycountday = moneycountday.add(entity1.getAmountPaid());
            }

        }
        int ordercountday = tradeOrderService.countByWhere(wrapper1);//今日订单量
        BigDecimal moneycounts = new BigDecimal(0);
        Wrapper wrapper2 = new Wrapper();
        wrapper2.and("a.order_status >", "10");
        wrapper2.and("a.order_status <", "60");
        Cursor<TradeOrder> cursor2 = tradeOrderService.selectCursor(sqlId, wrapper2);//总交易订单
        Iterator<TradeOrder> iter2 = cursor2.iterator();
        while (iter2.hasNext()) {
            TradeOrder entity2 = iter2.next();//每一个订单
            if (entity2.getOffsetAmount() != null) {//OffsetAmount调整后的金额、amount_paid订单总金额
                moneycounts = moneycounts.add(entity2.getOffsetAmount());
            } else {
                moneycounts = moneycounts.add(entity2.getAmountPaid());
            }
        }
        int ordercounts = tradeOrderService.countByWhere(wrapper2);//总订单量

        //会员模块的统计
        Store store = new Store();
        store.setIsOpen("1");//店铺营业状态(0关闭、1开启)
        int storecount = storeService.countByWhere(new Wrapper(store));//开店的店铺总数

        store.setBeginCreateDate(beginDate);
        store.setEndCreateDate(endDate);
        int storecountday = storeService.countByWhere(new Wrapper(store));//今日新增店铺

        Wrapper wrapper = new Wrapper();
        wrapper.and("a.type_user like", "_________1");//拥有买家角色类型的会员
        int membercount = userMainService.countByWhere(wrapper);//会员总数(买家)
        UserMain userMain = new UserMain();
        userMain.setBeginRegisterDate(beginDate);
        userMain.setEndRegisterDate(endDate);
        wrapper.setEntity(userMain);
        int membercountday = userMainService.countByWhere(wrapper);//今日新增会员数(买家)
        int activemembercount = userMainService.countByWhere(new Wrapper().and("a.type_user like", "_________1").and("a.login_date >", sevenDaysDate));//活跃买家
        int activesellercount = userMainService.countByWhere(new Wrapper().and("a.type_user like", "______1___").and("a.login_date >", sevenDaysDate));//活跃卖家

        //商品模块的统计
        int productSpu = productSpuService.countByWhere(new Wrapper());//商品总量-spu
        int productSku = productSkuService.countByWhere(new Wrapper());//商品总量-sku

        //待处理订单
        TradeReturnOrder tradeReturnOrder = new TradeReturnOrder();
        tradeReturnOrder.setSystemHandle(0L);//平台处理，0未处理、1已审核并退款、2拒绝退款
        int ordercountpending = tradeReturnOrderService.countByWhere(new Wrapper(tradeReturnOrder));
        //将查询信息存入  admin首页数据表
        TaskAdminIndex adminIndex = taskAdminIndexService.selectOne(new Wrapper());
        if (adminIndex == null) {
            TaskAdminIndex adminIndex1 = new TaskAdminIndex();
            adminIndex1 = getAdminIndex(adminIndex1, moneycountday, ordercountpending, ordercountday,
                    storecount, storecountday, membercount, membercountday, moneycounts, ordercounts,
                    productSpu, productSku, activemembercount, activesellercount);
            taskAdminIndexService.insertSelective(adminIndex1);
        } else {
            adminIndex = getAdminIndex(adminIndex, moneycountday, ordercountpending, ordercountday,
                    storecount, storecountday, membercount, membercountday, moneycounts, ordercounts,
                    productSpu, productSku, activemembercount, activesellercount);
            taskAdminIndexService.updateByIdSelective(adminIndex);
        }
    }

    /**
     * 查询并修改超过最晚收货时间的订单
     */
    public void updateTradeOrder() {
        //获取后台最晚收货时间
        SysVariable variable = variableService.getSysVariable("trade_order_delivery_time");
        Long deliveryDay = 15L;//默认值，15代表最晚收货时间为15天
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                deliveryDay = Long.parseLong(variable.getValue());
            }
        }
        long deliveryTime = deliveryDay * 1000 * 60 * 60 * 24L;//最晚收货时间的值
        //查询订单
        String sqlId = "selectByWhere";
        Cursor<TradeOrder> cursor = tradeOrderService.selectCursor(sqlId, new Wrapper().and("a.order_status=", "30"));//10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        Iterator<TradeOrder> iter = cursor.iterator();
        while (iter.hasNext()) {
            TradeOrder tradeOrder = iter.next();//状态为待收货的订单
            if (tradeOrder.getDelayDays() != null && tradeOrder.getDelayDays() != 0) {//延迟收货时间
                deliveryDay = deliveryDay + tradeOrder.getDelayDays();
                deliveryTime = deliveryDay * 1000 * 60 * 60 * 24L;
            }
            long dayTimes = new Date().getTime() - tradeOrder.getPlaceOrderTime().getTime();//未收货时间(long)1000*60*60*24L
            if (dayTimes < deliveryTime) {
                continue;
            }
            //超过了最晚收货时间,将订单状态修改为已收货待评价
            tradeOrder.setOrderStatus("40");
            tradeOrder.setProductReceiptDate(new Date());
            tradeOrderService.delayedReceipt(tradeOrder);
        }
    }

    /**
     * 查询并修改超过最晚收货时间的退款退货订单
     */
    public void updateTradeReturnOrder() {
        //获取后台最晚收货时间
        SysVariable variable = variableService.getSysVariable("trade_order_delivery_time");
        Long deliveryDay = 15L;//默认值，15代表最晚收货时间为15天
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                deliveryDay = Long.parseLong(variable.getValue());
            }
        }
        long deliveryTime = deliveryDay * 1000 * 60 * 60 * 24L;//最晚收货时间的值
        //查询订单
        String sqlId = "selectByWhere";
        //状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        Cursor<TradeReturnOrder> cursor = tradeReturnOrderService.selectCursor(sqlId, new Wrapper().and("a.status=", "40").and("a.type=", "1"));
        Iterator<TradeReturnOrder> iter = cursor.iterator();
        while (iter.hasNext()) {
            try {
                TradeReturnOrder tradeReturnOrder = iter.next();//状态为待收货的退款退货订单
                //如果没有发货时间
                if (tradeReturnOrder.getDeliverProductTime() == null) {
                    continue;
                }
                long dayTimes = new Date().getTime() - tradeReturnOrder.getDeliverProductTime().getTime();//未收货时间(long)1000*60*60*24L
                if (dayTimes < deliveryTime) {
                    continue;
                }
                //超过了最晚收货时间,将退款拖货订单状态修改为待平台审核
                TradeReturnOrder tro = new TradeReturnOrder();
                tro.setReturnOrderId(tradeReturnOrder.getReturnOrderId());

                tro.setStatus("50");
                tradeReturnOrderService.updateByIdSelective(tro);
            } catch (Exception e) {
                logger.error("查询并修改超过最晚收货时间的退款退货订单时出现异常：", e);
            }
        }
    }

    /**
     * 取消过期订单
     */
    public void cancleExpiredTradeOrder() {
        //查询订单
        String sqlId = "selectByWhere";
        Cursor<TradeOrder> cursor = tradeOrderService.selectCursor(sqlId, new Wrapper().and("a.order_status=", "10"));//10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        Iterator<TradeOrder> iter = cursor.iterator();
        while (iter.hasNext()) {
            TradeOrder tradeOrder = iter.next();
            long dayTimes = System.currentTimeMillis() - tradeOrder.getCreateDate().getTime();//当前时间-下单时间
            //判断是否过期(是否超过24小时)
            if (dayTimes < 1000 * 60 * 60 * 24L) {
                continue;
            }
            //取消过期订单
            if (tradeOrder.getPaymentMethodId() == null) {
                tradeOrder.setOrderStatus("60");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrderService.cancelOrder(tradeOrder);
            } else {
                tradeOrderService.queryAndCancelOrder(tradeOrder);
            }
        }
    }

    /**
     * admin首页数据 实体添加属性值
     */
    private TaskAdminIndex getAdminIndex(TaskAdminIndex adminIndex, BigDecimal moneycountday, int ordercountpending, int ordercountday,
                                         int storecount, int storecountday, int membercount, int membercountday, BigDecimal moneycounts, int ordercounts,
                                         int productSpu, int productSku, int activemembercount, int activesellercount) {
        adminIndex.setMoneycountday(moneycountday);//今日交易额
        adminIndex.setOrdercountday(ordercountday);//今日订单量
        adminIndex.setOrdermoneycount(moneycounts);//总交易额
        adminIndex.setOrdercount(ordercounts);//总订单量

        adminIndex.setStorecount(storecount);//商铺总数(统计的是 店铺表)
        adminIndex.setStorecountday(storecountday);//今日新增店铺(统计的是 店铺表)
        adminIndex.setMembercount(membercount);//会员总数(买家)
        adminIndex.setMembercountday(membercountday);//今日新增会员数(买家)
        adminIndex.setActivemembercount(activemembercount);//买家活跃
        adminIndex.setActivesellercount(activesellercount);//卖家活跃

        adminIndex.setProductspucount(productSpu);//商品spu总量
        adminIndex.setProductskucount(productSku);//商品sku总量


        adminIndex.setOrdercountpending(ordercountpending);//待处理订单
        return adminIndex;
    }

    /**
     * 插入对账任务
     */
    public void insertBalanceTask() {
    	tradeErrorPoolService.insertBalanceTask();
    }
    
    /**
     * 执行对账任务
     */
    public void balanceAccount() {
        tradeErrorPoolService.balanceAccount();
    }

    /**
     * 修改超过最晚到期时间的供采模块的采购单
     */
    public void updatePurchase() {
        //查询采购单(供采模块)
        String sqlId = "selectByWhere";
        //采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
        Cursor<Purchase> cursor = purchaseService.selectCursor(sqlId, new Wrapper().and("a.status<", "50"));
        Iterator<Purchase> iter = cursor.iterator();
        while (iter.hasNext()) {
            Purchase purchase = iter.next();//状态为审核中、审核未通过、待拆分、交易中的采购单
            if (purchase.getExpiryTime() == null) {
                continue;//跳过本次的循环体内容,继续下一次
            }
            long dayTimes = System.currentTimeMillis();//当前时间
            long expiryTime = (long) purchase.getExpiryTime().getTime();//到期时间
            if (expiryTime > dayTimes) {
                continue;
            }
            //修改超过最晚到期时间的供采模块的采购单
            purchase.setStatus("50");
            purchaseService.updateByIdSelective(purchase);
        }
    }
}