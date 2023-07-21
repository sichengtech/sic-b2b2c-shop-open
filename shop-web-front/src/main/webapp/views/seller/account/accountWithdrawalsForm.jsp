<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('提现申请')}</title>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/account/accountWithdrawalsForm.js"></script>
<style>
	.sui-dropup.dropdown-bordered, .sui-dropdown.dropdown-bordered {line-height: 23px;height: 31px;}
</style>
</head>
<body class="wallet">
	<div class="main-content" style="overflow:visible">
		<div class="sui-row-fluid">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('提现列表')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('提现申请')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('用户进行对当前账户进行申请提交')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
						<c:if test="${accountTiedCard.auditStatus eq '2' }">
							<div class="sui-msg msg-large msg-block msg-error">
								<div class="msg-con" style="margin-left: -10px;width: 995px;padding-left: 22px;"><i class="sui-icon icon-tb-warnfill"></i>${accountTiedCard.auditOpinion}</div>
							</div>
						</c:if>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd>
					<form id="inputForm" action="${ctxs}/account/withdrawals/save2.htm" method="post" class="sui-form form-inline">
						<input type="hidden" name="accountId" value="${accountId}">

						<div class="control-group">
							<label for="name1" class="control-label"><b style="color: #f00;">*</b>${fns:fy('提现账户：')}</label>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner" style="width: 347px;height: 28px;">
									<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input id="tiedCardId" name="tiedCardId" type="hidden"><i class="caret"></i>
										<span>${fns:fy('请选择')}</span>
									</a>
									<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
										<c:forEach items="${accountTiedCardList}" var="item">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${item.tiedCardId}">${fns:fy('收款人：')}<${item.payee}&nbsp;${fns:fy('银行卡号：')}${item.bankCardNumber}</a></li>
										</c:forEach>
									</ul>
								</span>
							</span>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请选择提现账户')}</div>
							</div>
						</div>
						<div class="control-group">
							<label for="name1" class="control-label"><b style="color: #f00;">*</b>${fns:fy('提现金额：')}</label>
							<input type="text" id="money" placeholder="${fns:fy('请输入提现金额')}" name="money" class="input-xfat input-xlarge">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入提现金额')}</div>
							</div>
						</div>
						<div class="control-group">
							<button type="submit" class="sui-btn btn-primary btn-block input-xfat" style="width: 160px; margin: 0 0 0 250px;text-align: center">${fns:fy('确认提现')}</button>
						</div>
					</form>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>