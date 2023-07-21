<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('绑卡申请')}</title>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/account/accountTiedCardForm.js"></script>
</head>
<body class="wallet fetch">
	<div class="main-content" style="overflow:visible">
		<c:set var="isEdit" value ="${not empty accountTiedCard.tiedCardId?true:false}"></c:set >
		<div class="sui-row-fluid">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('绑卡申请')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('绑卡申请')}</li>
					</ul>
				</dt>

				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('用户进行申请绑卡')}</li>
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
					<form id="inputForm" action="${ctxs}/account/tiedCard/${isEdit?'edit2':'save2'}.do" method="post" class="sui-form form-inline">

						<input type="hidden" name="tiedCardId" value="${accountTiedCard.tiedCardId }">
						<div class="control-group">
							<label for="name1" class="control-label"><b style="color: #f00;">*</b>${fns:fy('银行卡号：')}</label>
							<input type="hidden" name="oldBankCardNumber" value="${accountTiedCard.bankCardNumber}" id="oldBankCardNumber">
							<input type="text" id="bankCardNumber" placeholder="" name="bankCardNumber" class="input-xfat input-xxlarge" value="${accountTiedCard.bankCardNumber}">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入银行卡号')}</div>
							</div>
						</div>
						<div class="control-group">
							<label for="name2" class="control-label"><b style="color: #f00;">*</b>${fns:fy('收款人：')}</label>
							<input type="text" id="payee" placeholder="" name="payee" class="input-xfat input-xxlarge" value="${accountTiedCard.payee}">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入开户人姓名')}</div>
							</div>
						</div>
						<div class="control-group">
							<label for="name3" class="control-label"><b style="color: #f00;">*</b>${fns:fy('身份证号：')}</label>
							<input type="text" id="idNumber" placeholder="" name="idNumber" class="input-xfat input-xxlarge" value="${accountTiedCard.idNumber}">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入开户人身份证号')}</div>
							</div>
						</div>
						<div class="control-group">
							<label for="name4" class="control-label"><b style="color: #f00;">*</b>${fns:fy('开户银行：')}</label>
							<input type="text" id="accountOpeningBank" placeholder="" name="accountOpeningBank" class="input-xfat input-xxlarge" value="${accountTiedCard.accountOpeningBank}">								
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入开户银行')}</div>
							</div>
						</div>
						<div class="control-group">
							<label for="name4" class="control-label"><b style="color: #f00;">*</b>${fns:fy('开户手机号：')}</label>
							<input type="text" id="mobilePhoneNumber" placeholder="" name="mobilePhoneNumber" class="input-xfat input-xxlarge" value="${accountTiedCard.mobilePhoneNumber}">								
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入开户银行')}</div>
							</div>
						</div>

						<div class="control-group">
							<button type="submit" class="sui-btn btn-primary btn-block input-xfat" style="width: 145px; margin: 0 0 0 250px;text-align: center">${fns:fy('申请绑定')}</button>
						</div>
					</form>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>