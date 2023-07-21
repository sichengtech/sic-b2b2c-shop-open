<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/finance/financeRechargePay.js"></script>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>当前位置:</span><span>会员中心</span> > 财务中心 > 账户充值</div>
				<i class="sui-icon icon-tb-list"></i> 账户充值
			</dt>
			<ul class="sui-nav nav-tabs nav-large">
				<li class="active"><a href="javascript:;">在线充值</a></li>
				<li class=""><a href="${ctxm}/finance/financeRecharge/list.htm">充值记录</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<div id="recharge" class="my-favor-goods active table-css">
					<dl class="myfinancial-content">
						<div class="gopay" style=""><!--确认金额后显示-->
							<dd class="myfinancial-info">
								<h3>您已申请账户余额充值，请立即在线支付！充值金额：<font style="color:red" class="rechargeMoney">￥${settlementRecharge.rechargeMoney}</font></h3>
								<p>充值单号 :${settlementRecharge.rechargeId} </p>
							</dd>
							<dt>请选择支付方式</dt>
							<dd class="payment">
								<ul>
									<li><a href="javascript:;"><b></b><img src="${ctxStatic}/images/alipay.png"></a></li>
									<li class="cur"><a href="javascript:;"><b></b><img src="${ctxStatic}/images/cupay.png"></a></li>
									<li><a href="javascript:;"><b></b><img src="${ctxStatic}/images/paypal.png"></a></li>
									<li><a href="javascript:;"><b></b><img src="${ctxStatic}/images/wechat.png"></a></li>
									<li><a href="javascript:;"><b></b><img src="${ctxStatic}/images/huipay.png"></a></li>
								</ul>
								<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-danger mt20">去支付</a>
							</dd>
						</div>
					</dl>
				</div>
				<sys:message content="${message}"/>
			</dd>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
