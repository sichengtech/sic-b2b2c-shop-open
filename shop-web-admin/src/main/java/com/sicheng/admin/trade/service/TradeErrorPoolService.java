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
package com.sicheng.admin.trade.service;

import com.sicheng.admin.account.entity.AccountBalanceProorder;
import com.sicheng.admin.account.entity.AccountBalanceProreturnorder;
import com.sicheng.admin.account.service.AccountBalanceProorderService;
import com.sicheng.admin.account.service.AccountBalanceProreturnorderService;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.trade.dao.TradeErrorPoolDao;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.dao.TradeReturnOrderDao;
import com.sicheng.admin.trade.entity.TradeErrorPool;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 对账差错池 Service
 *
 * @author fxx
 * @version 2018-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeErrorPoolService extends CrudService<TradeErrorPoolDao, TradeErrorPool> {



    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeOrderDao tradeOrderDao;
    @Autowired
    private TradeReturnOrderDao tradeReturnOrderDao;
    /*@Autowired
    private ProducerService producerService;*/
    @Autowired
    private AccountBalanceProorderService accountBalanceProorderService;
    @Autowired
    private AccountBalanceProreturnorderService proreturnorderService;
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private AccountService accountService;

    @Transactional(rollbackFor=Exception.class)
    public void insertBalanceTask(){
        //插入对账任务-商品订单
        insertTaskProOrder();

        //插入对账任务-商品退单
        insertTaskProReturnOrder();
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void balanceAccount(){

        //执行对账任务-执行失败的和未执行的商品订单
       selectTaskProOrder();

        //查询并执行对账任务-执行失败的和未执行的商品退单
       selectTaskProReturnOrder();
    }

    /**
     * 查询对账任务-商品订单
     */
    @Transactional(rollbackFor=Exception.class)
    public void insertTaskProOrder(){
        Long minId=0L;
        //查询订单
        Date endDate=new Date();
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -10);
        Date beforeDate = beforeTime.getTime();
        Wrapper wrapper=new Wrapper();
        wrapper.and("b.status is null ");
        wrapper.and("a.order_status in ", Arrays.asList(10,20));
        wrapper.and("a.is_return_status = ",0);
        wrapper.and("a.payment_method_id is not null");
        wrapper.andNew("a.pay_order_time between ",beforeDate,endDate);
        wrapper.or("a.pay_order_time is null ");
        wrapper.orderBy("a.order_id desc");
        for(;;){
            Page<TradeOrder> page= new Page<>();
            page.setPageNo(1);
            page.setPageSize(100);
            if(minId != 0){
                wrapper.and("a.order_id<",minId);
            }
            List<TradeOrder> tradeOrderList=tradeOrderDao.joinSelectByWhere(page, wrapper);
            if(tradeOrderList.isEmpty()){
                break;
            }
            minId=tradeOrderList.get(tradeOrderList.size()-1).getOrderId();
            List<AccountBalanceProorder> accountBalanceProorderList=new ArrayList<>();
            for(int i=0;i<tradeOrderList.size();i++){
                TradeOrder tradeOrder=tradeOrderList.get(i);
                AccountBalanceProorder accountBalanceProorder=new AccountBalanceProorder();
                accountBalanceProorder.setPkMode(1);
                accountBalanceProorder.setOrderId(tradeOrder.getOrderId());
                //状态: 0待处理，1对账中，2对账失败，3对账成功
                accountBalanceProorder.setStatus("0");
                accountBalanceProorderList.add(accountBalanceProorder);
                //producerService.sendMessage(MqConstants.ACCOUNT_PRO_ORDER,tradeOrder.getOrderId());
                //accountBalanceProorderService.insertSelective(accountBalanceProorder);
            }
           Boolean insertFlag= accountBalanceProorderService.insertSelectiveBatch(accountBalanceProorderList);
           System.out.println(insertFlag);
        }
    }

    /**
     * 查询对账任务-商品退单
     */
    @Transactional(rollbackFor=Exception.class)
    public void insertTaskProReturnOrder(){
        Long minId=0L;
        //查询订单
        Date endDate=new Date();
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -10);
        Date beforeDate = beforeTime.getTime();
        Wrapper wrapper=new Wrapper();
        wrapper.and("b.status is null");
        //退单状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        wrapper.and("a.status =",60);
        wrapper.and("a.pay_way_id is not null");
        wrapper.and("a.system_agree_time between",beforeDate,endDate);
        wrapper.orderBy("a.return_order_id desc");
        for(;;){
            Page<TradeReturnOrder> page= new Page<>();
            page.setPageNo(1);
            if(minId != 0){
                wrapper.and("a.return_order_id<",minId);
            }
            List<TradeReturnOrder> tradeReturnOrderList=tradeReturnOrderDao.joinSelectByWhere(page, wrapper);
            if(tradeReturnOrderList.isEmpty()){
                break;
            }
            minId=tradeReturnOrderList.get(tradeReturnOrderList.size()-1).getReturnOrderId();
            List<AccountBalanceProreturnorder> proReturnOrderList=new ArrayList<>();
            for(int i=0;i<tradeReturnOrderList.size();i++){
                TradeReturnOrder tradeReturnOrder=tradeReturnOrderList.get(i);
                AccountBalanceProreturnorder returnOrder=new AccountBalanceProreturnorder();
                returnOrder.setPkMode(1);
                returnOrder.setReturnId(tradeReturnOrder.getReturnOrderId());
                //状态: 0待处理，1对账中，2对账失败，3对账成功
                returnOrder.setStatus("0");
                proReturnOrderList.add(returnOrder);
                //producerService.sendMessage(MqConstants.ACCOUNT_PRO_RETURN_ORDER,tradeReturnOrder.getReturnOrderId());
            }
            proreturnorderService.insertSelectiveBatch(proReturnOrderList);
        }
    }

    /**
     * 执行对账任务-执行错误的商品订单任务
     */
    public void selectTaskProOrder(){
        Long minId=0L;
        //定时任务执行错误最大次数
        String fileTimes= Global.getConfig("task.failTimes");
        Wrapper wrapper=new Wrapper();
        wrapper.and("status=",0);
        wrapper.orNew("status=",2);
        wrapper.and("fail_times <",fileTimes);
        wrapper.orderBy("order_id desc");
        for(;;){
            Page<AccountBalanceProorder> page= new Page<>();
            page.setPageNo(1);
            page.setPageSize(100);
            if(minId != 0){
                wrapper.and("order_id<",minId);
            }
            page=accountBalanceProorderService.selectByWhere(page,wrapper);
            if(page.getList().isEmpty() || page.isLastPage()){
                break;
            }
            minId=page.getList().get(page.getList().size()-1).getOrderId();
            for(int i=0;i<page.getList().size();i++){
                balanceProOrder(page.getList().get(i).getOrderId());
            }
        }
    }

    /**
     * 执行对账任务-执行错误的商品退单任务
     */
    public void selectTaskProReturnOrder(){
        Long minId=0L;
        //定时任务执行错误最大次数
        String fileTimes=Global.getConfig("task.failTimes");
        Wrapper wrapper=new Wrapper();
        wrapper.and("status=",0);
        wrapper.orNew("status=",2);
        wrapper.and("fail_times <",fileTimes);
        wrapper.orderBy("return_id desc");
        for(;;){
            Page<AccountBalanceProreturnorder> page= new Page<>();
            page.setPageNo(1);
            if(minId != 0){
                wrapper.and("return_id<",minId);
            }
            Page<AccountBalanceProreturnorder>  returnOrderPage=proreturnorderService.selectByWhere(page,wrapper);
            if(returnOrderPage.getList().isEmpty()){
                break;
            }
            minId=returnOrderPage.getList().get(returnOrderPage.getList().size()-1).getReturnId();
            for(int i=0;i<returnOrderPage.getList().size();i++){
                balanceProReturnOrder(returnOrderPage.getList().get(i).getReturnId());
            }
        }
    }

    /**
     * 执行对账-商品订单
     * @param orderId 商品订单id
     */
    @Transactional(rollbackFor=Exception.class)
    public void balanceProOrder(Long orderId){
    	
    	if(orderId==null){
            return;
        }
    	
    	AccountBalanceProorder proorder=accountBalanceProorderService.selectById(orderId);
        if(proorder==null){
        	return;
        }
        //订单状态，SUCCESS支付成功、FAIL支付失败
        String orderStatus="";
        //订单总金额
        String totalAmount="";
        //将对账-商品订单表修改为“1对账中”
        updateBalanceProorder(orderId, "1", null);
        TradeOrder tradeOrder=tradeOrderDao.selectById(orderId);
        try { 
	        if(tradeOrder==null){
	        	//订单不存在，对账失败
	        	throw new PayException();
	        }
	        
	        SettlementPayWay settlementPayWay=tradeOrder.getSettlementPayWay();
	        if(settlementPayWay==null){
	        	//支付方式不存在，不进行对账，标为成功
	        	updateBalanceProorder(orderId, "3", null);
	            return;
	        }
        
	        //查询第三方订单
	        Map<String,Object> paramMap=new HashMap<>();
	        paramMap.put(PayConstants.OUT_TRADE_NO,tradeOrder.getOutTradeNo());
	        ShopPay shopPay= ShopPayFactory.get(settlementPayWay.getPayWayNum());
	       
        
            Map<String,String> mapResult=(Map<String,String>)shopPay.query(paramMap);
            //订单状态，SUCCESS支付成功、FAIL支付失败
            orderStatus=mapResult.get("tradeState");
            //订单总金额
            totalAmount=mapResult.get("totalAmount");
            //掉单情况
            //订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
            if("10".equals(tradeOrder.getOrderStatus()) && "SUCCESS".equals(orderStatus)){
                //第三方交易号
                String tradeNo = (String) mapResult.get("tradeNo");

                //修改订单状态
                TradeOrder tradeOrder2=new TradeOrder();
                tradeOrder2.setOrderId(tradeOrder.getOrderId());
                //订单状态，20待发货
                tradeOrder2.setOrderStatus("20");
                tradeOrderDao.updateByIdSelective(tradeOrder2);

                //插入差错池数据
                insertErrorPoolProOrder(tradeOrder, orderStatus, totalAmount,"1");

                //查询商户订单号下的订单
                List<TradeOrder> tradeOrderList = tradeOrderService.selectByWhere(new Wrapper().and("out_trade_no=",tradeOrder.getOutTradeNo()));

                //调取账户接口
                try {
                    for (int i = 0; i < tradeOrderList.size(); i++) {
                        //订单
                        TradeOrder tr = tradeOrderList.get(i);
                        //订单详情
                        List<TradeOrderItem> tradeOrderItemList = tr.getTradeOrderItemList();
                        if(tradeOrderItemList.size()==1){
                            accountService.paymentProduct(tr.getOrderId(), tr.getActualMoney() ,new BigDecimal("0"), tr.getActualMoney(), settlementPayWay.getPayWayId(), settlementPayWay.getName(), tradeNo);
                        }else{
                            for (int j = 0; j < tradeOrderItemList.size(); j++) {
                                BigDecimal tradeOrderItemMoney = (tradeOrderItemList.get(j).getPrice()).multiply(new BigDecimal(tradeOrderItemList.get(j).getQuantity()));
                                accountService.paymentProduct(tradeOrderItemList.get(j).getOrderItemId(), tradeOrderItemMoney ,new BigDecimal("0"), tr.getActualMoney(), settlementPayWay.getPayWayId(), settlementPayWay.getName(), tradeNo);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                updateBalanceProorder(orderId, "3", null);
                return;
            }
            //没有支付，对账完成
            if(StringUtils.isBlank(totalAmount) && "10".equals(tradeOrder.getOrderStatus())){
                //将对账-商品订单表修改为“3对账成功”
                updateBalanceProorder(orderId, "3", null);
                return;
            }
            //本商城订单总额
            BigDecimal onlinePayMoney=tradeOrder.getOnlinePayMoney();
            //第三方支付金额
            //支付金额不一致
            if(StringUtils.isBlank(totalAmount) || onlinePayMoney.compareTo(new BigDecimal(totalAmount))!=0){
                //插入差错池数据
                insertErrorPoolProOrder(tradeOrder, orderStatus, totalAmount,"0");
            }
            //将对账-商品订单表修改为“3对账成功”
            updateBalanceProorder(orderId, "3", null);
        } catch (PayException e) {
            logger.error("对账-商品订单发生错误：",e.getMessage());
            //将对账-商品订单表修改为“2对账失败”
            updateBalanceProorder(orderId, "2", proorder.getFailTimes()+1);
            //定时任务执行错误最大次数
            String fileTimes=Global.getConfig("task.failTimes");
            //查过错误次数，放入差错池
            if(proorder.getFailTimes()+1>=Integer.parseInt(fileTimes)){
                insertErrorPoolProOrder(tradeOrder, orderStatus, totalAmount, "0");
            }
        }
    }

    /**
     * 执行对账-商品退单
     * @param returnOrderId 商品退单id
     */
    @Transactional(rollbackFor=Exception.class)
    public void balanceProReturnOrder(Long returnOrderId){
        if(returnOrderId==null){
            return;
        }
        AccountBalanceProreturnorder proorder=proreturnorderService.selectById(returnOrderId);
        if(proorder==null){
        	return;
        }
        //订单状态，SUCCESS支付成功、FAIL支付失败
        String orderStatus="";
        //订单总金额
        String refundFee="";
        //将对账-商品退单表修改为“1对账中”
        updateBalanceProReturnOrder(returnOrderId, "1", null);
        TradeReturnOrder tradeReturnOrder=tradeReturnOrderService.selectById(returnOrderId);
        try {
        	if(tradeReturnOrder==null){
            	//退单不存在,将对账-商品退单修改为“2对账失败”
               throw new PayException();
            }
            SettlementPayWay settlementPayWay=tradeReturnOrder.getSettlementPayWay();
            if(settlementPayWay==null){
            	//支付方式不存在，不进行对账，将对账-商品退单修改为“3对账成功”
                updateBalanceProReturnOrder(returnOrderId, "3", null);
                return;
            }
            //查询第三方订单
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put(PayConstants.OUT_TRADE_NO,tradeReturnOrder.getTradeOrder().getOutTradeNo());
            paramMap.put(PayConstants.OUT_REFUND_NO, tradeReturnOrder.getReturnOrderId().toString());
            ShopPay shopPay=ShopPayFactory.get(settlementPayWay.getPayWayNum());
        	
            Map<String,String> mapResult=(Map<String,String>)shopPay.queryRefund(paramMap);
            //订单状态，SUCCESS支付成功、FAIL支付失败
            orderStatus=mapResult.get("tradeState");
            //订单总金额
            refundFee=mapResult.get("refundFee");
            //退款失败情况
            if("60".equals(tradeReturnOrder.getStatus()) && !("SUCCESS".equals(orderStatus))){
                //插入差错池数据
                insertErrorPoolProReturnOrder(tradeReturnOrder, orderStatus,refundFee,"0");
                updateBalanceProReturnOrder(returnOrderId, "3", null);
                return;
            }
            //本商城退款金额
            BigDecimal returnMoney=tradeReturnOrder.getReturnMoney();
            //第三方退款金额
            BigDecimal totalAmountDec=new BigDecimal(refundFee);
            //支付金额不一致
            if(returnMoney.compareTo(totalAmountDec)!=0){
                //插入差错池数据
                insertErrorPoolProReturnOrder(tradeReturnOrder, orderStatus,refundFee,"0");
            }
            //将对账-商品退单表修改为“3对账成功”
            updateBalanceProReturnOrder(returnOrderId, "3", null);
        } catch (PayException e) {
            logger.error("对账-商品退单发生错误：",e.getMessage());
            //将对账-商品退单表修改为“2对账失败”
            updateBalanceProReturnOrder(returnOrderId, "2", proorder.getFailTimes()+1);
            //定时任务执行错误最大次数
            String fileTimes=Global.getConfig("task.failTimes");
            //查过错误次数，放入差错池
            if(proorder.getFailTimes()+1>=Integer.parseInt(fileTimes)){
                insertErrorPoolProReturnOrder(tradeReturnOrder, orderStatus,refundFee, "0");
            }
        }
    }

    /**
     * 根据商品订单、订单订单状态和第三方订单金额插入差错池
     * @param tradeOrder 商品订单
     * @param orderStatus 第三方订单状态
     * @param totalAmount 订单订单总金额
     * @param handleStatus 处理状态，0未处理，1已处理
     * @return
     */
    private void insertErrorPoolProOrder(TradeOrder tradeOrder,String orderStatus,String totalAmount,String handleStatus){
    	try{
    		TradeErrorPool tradeErrorPool=new TradeErrorPool();
            //订单编号
            tradeErrorPool.setOrderId(tradeOrder.getOrderId());
            //店铺id
            tradeErrorPool.setStoreId(tradeOrder.getStoreId());
            //店铺名称
            if(tradeOrder.getStore()!=null){
            	tradeErrorPool.setStoreName(tradeOrder.getStore().getName());
            }
            //第三方交易号
            tradeErrorPool.setTransactionId(tradeOrder.getThirdPayOrderNumber());
            //订单类型 ,1商品订单、2商品退单、3服务订单、4服务退单
            tradeErrorPool.setBillType("1");
            //支付方式id
            tradeErrorPool.setPayWayId(tradeOrder.getPaymentMethodId());
            //支付方式名
            tradeErrorPool.setPayWayName(tradeOrder.getPaymentMethodName());
            //本商城的订单状态
            String status= DictUtils.getDictLabel(tradeOrder.getOrderStatus(),"trade_order_status" ,"");
            tradeErrorPool.setOrderStatus(status);
            //第三方平台订单状态
            tradeErrorPool.setTransactionStatus("SUCCESS".equals(orderStatus)?"支付成功":"支付失败");
            //下单时间
            tradeErrorPool.setOrdertime(tradeOrder.getPlaceOrderTime());
            //本商城订单金额
            tradeErrorPool.setOrderMoney(tradeOrder.getOnlinePayMoney());
            //第三方的订单金额
            if(StringUtils.isNotBlank(totalAmount)){
                tradeErrorPool.setTransactionMoney(new BigDecimal(totalAmount));
            }
            //处理状态
            tradeErrorPool.setHandlestatus(handleStatus);
            //处理备注
            if("1".equals(handleStatus)){
                tradeErrorPool.setHandleremark("系统自动处理完成");
            }
            super.insertSelective(tradeErrorPool);
    	}catch(Exception e){
    		logger.info("对账-订单：插入差错池发生出错误，",e);
    	}
    }

    /**
     * 根据商品退单、订单订单状态和第三方订单金额插入差错池
     * @param tradeReturnOrder 商品退单
     * @param orderStatus 第三方订单状态
     * @param totalAmount 订单订单总金额
     * @param handleStatus 处理状态，0未处理，1已处理
     * @return
     */
    private void insertErrorPoolProReturnOrder(TradeReturnOrder tradeReturnOrder,String orderStatus,String totalAmount,String handleStatus){
    	try{
	    	TradeErrorPool tradeErrorPool=new TradeErrorPool();
	        tradeErrorPool.setOrderId(tradeReturnOrder.getReturnOrderId());
	        tradeErrorPool.setStoreId(tradeReturnOrder.getStore().getStoreId());
	        if(tradeReturnOrder.getStore()!=null){
	        	tradeErrorPool.setStoreName(tradeReturnOrder.getStore().getName());
	        }
	        //订单类型 ,1商品订单、2商品退单、3服务订单、4服务退单
	        tradeErrorPool.setBillType("2");
	        //支付方式id
	        tradeErrorPool.setPayWayId(tradeReturnOrder.getPayWayId());
	        //支付方式名
	        tradeErrorPool.setPayWayName(tradeReturnOrder.getSettlementPayWay().getName());
	        //本商城的订单状态
	        String satus=DictUtils.getDictLabel(tradeReturnOrder.getStatus(),"trade_return_order_status" ,"");
	        tradeErrorPool.setOrderStatus(satus);
	        //第三方平台订单状态
	        tradeErrorPool.setTransactionStatus("SUCCESS".equals(orderStatus)?"退款成功":"退款失败");
	        //下单时间
	        tradeErrorPool.setOrdertime(tradeReturnOrder.getTradeOrder().getPlaceOrderTime());
	        //本商城退款金额
	        tradeErrorPool.setOrderMoney(tradeReturnOrder.getReturnMoney());
	        //第三方的退款金额
	        tradeErrorPool.setTransactionMoney(new BigDecimal(totalAmount));
	        //处理状态
	        tradeErrorPool.setHandlestatus(handleStatus);
	        //第三方交易号
	        tradeErrorPool.setTransactionId(tradeReturnOrder.getTradeOrder().getThirdPayOrderNumber());
	        //处理备注
	        if("1".equals(handleStatus)){
	            tradeErrorPool.setHandleremark("系统自动处理完成");
	        }
	        super.insertSelective(tradeErrorPool);
    	}catch(Exception e){
    		logger.info("对账-退单：插入差错池发生错误，",e);
    	}
    }

    /**
     * 根据订单id、状态和错误次数更新商品订单对账任务
     * @param orderId 订单任务
     * @param status  订单状态
     * @param failTimes 失败次数
     */
    public void updateBalanceProorder(Long orderId,String status,Integer failTimes){
        AccountBalanceProorder accountBalanceProorder2=new AccountBalanceProorder();
        accountBalanceProorder2.setOrderId(orderId);
        //状态: 0待处理，1对账中，2对账失败，3对账成功
        accountBalanceProorder2.setStatus(status);
        if("1".equals(status)){
            accountBalanceProorder2.setBeginTime(new Date());
        }else{
            accountBalanceProorder2.setEndTime(new Date());
        }
        if(failTimes!=null){
            //失败次数
            accountBalanceProorder2.setFailTimes(failTimes);
        }
        accountBalanceProorderService.updateByIdSelective(accountBalanceProorder2);
    }

    /**
     * 根据订单id、状态和错误次数更新商品订单对账任务
     * @param returnOrderId 订单任务
     * @param status  订单状态
     * @param failTimes 失败次数
     */
    public void updateBalanceProReturnOrder(Long returnOrderId,String status,Integer failTimes){
        AccountBalanceProreturnorder accountBalanceProReturnOrder2=new AccountBalanceProreturnorder();
        accountBalanceProReturnOrder2.setReturnId(returnOrderId);
        //状态: 0待处理，1对账中，2对账失败，3对账成功
        accountBalanceProReturnOrder2.setStatus(status);
        if("1".equals(status)){
            accountBalanceProReturnOrder2.setBeginTime(new Date());
        }else{
            accountBalanceProReturnOrder2.setEndTime(new Date());
        }
        if(failTimes!=null){
            //失败次数
            accountBalanceProReturnOrder2.setFailTimes(failTimes);
        }
        proreturnorderService.updateByIdSelective(accountBalanceProReturnOrder2);
    }

}