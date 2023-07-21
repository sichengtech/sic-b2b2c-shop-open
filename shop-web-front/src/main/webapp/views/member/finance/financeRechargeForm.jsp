<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>账户充值</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/finance/financeRechargeForm.js"></script>
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
						<div class="myfinancial-content1">
							<dd>当前账户可用余额：<font style="color:red">￥${empty userMain.userMember.balance?'0':userMain.userMember.balance}</font> 元</dd>
							<dd class="number">
								<form class="sui-form form-inline" action="${ctxm}/finance/financeRecharge/save2.htm"  method="post" id="inputForm">充值金额：
									<input id="appendedPrependedInput" type="text" class="span2 input-xfat" name="rechargeMoney" placeholder="元">
									<button type="submit" class="sui-btn btn-xlarge btn-danger">确定</button>
								</form>
							</dd>
						</div>
						<div class="gopay" style="display:none;"><!--确认金额后显示-->
							<dd class="myfinancial-info">
								<h3>您已申请账户余额充值，请立即在线支付！充值金额：<font style="color:red" class="rechargeMoney">￥22.00</font></h3>
								<p>充值单号 : 238820011980050492</p>
							</dd>
							<dt>请选择支付方式</dt>
							<dd class="payment">
								<ul>
									<li><a href="#"><b></b><img src="${ctxStatic}/images/alipay.png"></a></li>
									<li class="cur"><a href="#"><b></b><img src="${ctxStatic}/images/cupay.png"></a></li>
									<li><a href="#"><b></b><img src="${ctxStatic}/images/paypal.png"></a></li>
									<li><a href="#"><b></b><img src="${ctxStatic}/images/wechat.png"></a></li>
									<li><a href="#"><b></b><img src="${ctxStatic}/images/huipay.png"></a></li>
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
