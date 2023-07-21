<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>支付密码</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/changePayPassword.js" type="text/javascript"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
</script>
</head>
<body>
	<div class="sso-center">
		<dl>
			<dt>支付密码</dt>
			<dd>
				<sys:message content="${message}"/>
				<form id="inputForm" action="${ctxsso}/accountSecurity/changePayPassword2.htm" method="post">
					<div class="infomain sui-form form-horizontal">
						<c:if test="${not empty userMain.userMember.paymentPassword}">
						<div class="control-group">
							<label class="control-label" style="display: block;">原支付密码：</label>
							<div class="controls">
								<input id="oldPaymentPassword" name="oldPaymentPassword" type="password" class="input-large input-xfat" placeholder="输入支付原密码">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">请输入原支付密码</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<hr>
						</c:if>
						<div class="control-group">
							<label class="control-label" style="display: block;">设置支付新密码：</label>
							<div class="controls">
								<input id="paymentPassword" name="paymentPassword" type="password" class="input-large input-xfat" placeholder="输入支付新密码">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">请输入新支付密码</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="display: block;">重复支付新密码：</label>
							<div class="controls">
								<input id="rePaymentPassword" name="rePaymentPassword" type="password" class="input-large input-xfat" placeholder="重复支付新密码">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">重复新支付密码</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="sui-btn btn-primary input-xfat">保存</button>
							</div>
						</div>
					</div>
				</form>
			</dd>
		</dl>
	</div>
</body>
</html>
