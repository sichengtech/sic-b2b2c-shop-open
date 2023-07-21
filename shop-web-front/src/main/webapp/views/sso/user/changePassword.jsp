<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('修改密码')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/changePassword.js" type="text/javascript"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
</script>
<style type="text/css">
	.control-label{display: block;}
	.sui-form.form-horizontal .control-label { width: 180px; display: block; }
	.infomain .control-group .controls { width: 360px; }
</style>
</head>
<body>
	<div class="sso-center">
		<dl>
			<dt>${fns:fy('修改密码')}</dt>
			<dd>
				<sys:message content="${message}"/>
				<form id="inputForm" action="${ctxsso}/accountSecurity/changePassword2.htm" method="post">
					<!-- 使用隐藏input来接受浏览器自动填充，这样不会影响用户体验，也可以兼容所有浏览器 -->
					<input type="password" name="oldPassword" style="display:none" disabled="true" disabled="disabled"/>
					<!-- 表单数据 -->
					<div class="infomain sui-form form-horizontal">
						<c:if test="${not empty userMain.password}">
						<div class="control-group">
							<label class="control-label" style="display: block;">${fns:fy('原密码：')}</label>
							<div class="controls">
								<input id="oldPassword" name="oldPassword" type="password" class="input-large input-xfat" placeholder="${fns:fy('请输入原密码')}">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请输入原密码')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<hr>
						</c:if>
						<div class="control-group">
							<label class="control-label" style="display: block;">${fns:fy('设置新密码：')}</label>
							<div class="controls">
								<input id="password" name="password" type="password" class="input-large input-xfat" placeholder="${fns:fy('请输入新密码')}">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请输入新密码')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="display: block;">${fns:fy('重复新密码：')}</label>
							<div class="controls">
								<input id="repassword" name="repassword" type="password" class="input-large input-xfat" placeholder="${fns:fy('重复新密码')}">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('重复新密码')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="sui-btn btn-primary input-xfat">${fns:fy('保存')}</button>
							</div>
						</div>
					</div>
				</form>
			</dd>
		</dl>
	</div>
</body>
</html>
