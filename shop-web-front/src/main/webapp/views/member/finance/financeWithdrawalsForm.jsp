<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/finance/financeWithdrawalsForm.js"></script>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>当前位置:</span><span>会员中心</span> > 财务中心 > 余额提现</div>
				<i class="sui-icon icon-tb-list"></i> 余额提现
			</dt>
			<ul class="sui-nav nav-tabs nav-large">
				<li class="active"><a href="javascript:;">余额提现</a></li>
				<li><a href="${ctxm}/finance/financeWithdrawals/list.htm">提现记录</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<div id="recharge" class=" active table-css">
					<dl class="myfinancial-content">
					<sys:message content="${message}"/>
						<dd>当前账户可提现余额：<font style="color:red">￥<span class="balance">${userMember.balance}</span></font> 元</dd>
						<dd class="myform">
							<form class="sui-form form-horizontal" action="${ctxm}/finance/financeWithdrawals/save2.htm" method="post" id="inputForm">
								<div class="control-group">
									<label class="control-label">提现金额：</label>
									<input id="appendedPrependedInput" type="text" name="money" value="${settlementWithdrawals.money}" class="span2 input-fat" placeholder="元">
								</div>
								<div class="control-group">
									<label class="control-label">收款方式：</label>
									<span class="sui-dropdown dropdown-bordered dropdown-large select">
										<span class="dropdown-inner" style="width: 160px;">
											<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input type="hidden" name="payWayId" id=""  value="${not empty payWayList[0].payWayId?payWayList[0].payWayId:''}"/><i class="caret"></i>
												<span id="">${not empty payWayList[0].name?payWayList[0].name:'请选择'}</span>
											</a>
											<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="">
												<c:forEach items="${payWayList}" var="way">
													<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${way.payWayId}">${way.name}</a></li>
												</c:forEach>
											</ul>
										</span>
									</span>
								</div>
								<div class="control-group">
									<label class="control-label">收款账号：</label>
									<input id="" name="receivablesNumber" value="${settlementWithdrawals.receivablesNumber}" type="text" class="span2 input-fat">
								</div>
								<div class="control-group">
									<label class="control-label">开户名：</label>
									<input id="" name="accountName" value="${settlementWithdrawals.accountName}" type="text" class="span2 input-fat">
								</div>
								<div class="control-group button">
									<label class="control-label">　</label>
									<button type="submit" class="sui-btn btn-xlarge btn-danger">确定</button>
								</div>
							</form>
						</dd>
						<%-- <div class="gopay" style="display:block"><!--确认金额后显示-->
							<dd class="myfinancial-info">
								<h3>您已申请账户余额提现！提现金额：<font style="color:red">￥101.00</font><span>（含手续费￥1.00元）</span></h3>
								<p>提现单号 : 238820011980050492</p>
							</dd>
							<dt>请选择提现方式</dt>
							<dd class="b-list">
								<ul>
									<li class="cur"><a href="#"><b><i class="sui-icon icon-tb-roundcheckfill"></i> 已选择</b><span class="b-name"><img src="${ctxStatic}/sicheng-front/images/bank_icon/alipay.png">支付宝帐号：883***@qq.com</span><span class="b-info">（单笔：200000.00元）</span></a></li>
									<li><a href="#"><b><i class="sui-icon icon-tb-roundcheckfill"></i> 已选择</b><span class="b-name"><img src="${ctxStatic}/sicheng-front/images/bank_icon/wechat.png">微信帐号：133****2222</span><span class="b-info">（单笔：200000.00元）</span></a></li>
									<li><a href="#"><b><i class="sui-icon icon-tb-roundcheckfill"></i> 已选择</b><span class="b-name"><img src="${ctxStatic}/sicheng-front/images/bank_icon/icbc.png">中国工商银行 尾号8829</span><span class="b-info">（单笔：200000.00元）</span></a></li>
								</ul>
								<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-danger mt20">下一步</a>
							</dd>
						</div> --%>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
