<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty userMain.id?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userSellerSave.js"></script>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${empty userMain.id?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('账号')}</li>
						<li>${fns:fy('账号管理')}</li>
						<li class="active">${empty userMain.id?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号')}</li>
					</ul>
				</dt>
				<!-- 提示开始 -->
				<dd style="padding-top: 0px;  padding-bottom: 0px;" class="sui-row-fluid sui-form form-horizontal screen pt10 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin-right: 20px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('可以')}${empty userMain.id?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号信息')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</dd>
				<!-- 提示结束-->
				<sys:message content="${message}"/>
				<form id="myFrom" class="sui-form form-inline" method="post" action="${ctxs}/user/userSeller/${empty userMain.id?'save2':'edit2'}.htm">
					<dd>
						<!-- 使用隐藏input来接受浏览器自动填充，这样不会影响用户体验，也可以兼容所有浏览器 -->
						<input type="text" name="userName" style="display:none" disabled="true" disabled="disabled"/>
						<input type="password" name="password" style="display:none" disabled="true" disabled="disabled"/>
						<!-- 表单数据 -->
						<input id="uId" name="uId" value="${userMain.id}" type="hidden"/>
						<input id="usernameMin" name="usernameMin" value="${siteRegister.usernameMin}" type="hidden"/>
						<input id="usernameMax" name="usernameMax" value="${siteRegister.usernameMax}" type="hidden"/>
						<input id="pwdMin" name="pwdMin" value="${siteRegister.pwdMin}" type="hidden"/>
						<input id="pwdMax" name="pwdMax" value="${siteRegister.pwdMax}" type="hidden"/>
						<input name="myType" value="${userMain.typeAccount}" type="hidden"/>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('账号名称：')}</label>
							<input id="oldLoginName" name="oldLoginName" value="${userMain.loginName}" type="hidden"/>
							<input id="loginName" name="loginName" value="${userMain.loginName}" type="text" class="input-xlarge"/>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入账号名称,最小长度')}${siteRegister.usernameMin}${fns:fy('个字符,最大长度')}${siteRegister.usernameMax}${fns:fy('个字符。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('账号密码：')}</label>
							<input id="password" name="password" type="password" class="input-xlarge"/>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入账号密码,最小长度')}${siteRegister.pwdMin}${fns:fy('个字符,最大长度')}${siteRegister.pwdMax}${fns:fy('个字符。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('重复密码：')}</label>
							<input name="nextPassword" type="password" class="input-xlarge"/>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入重复密码,最小长度')}${siteRegister.pwdMin}${fns:fy('个字符,最大长度')}${siteRegister.pwdMax}${fns:fy('个字符。')}</div>
							</div>
						</div>
						<c:if test="${not empty userMain.id && userMain.typeAccount eq '1'}">
							<div class="control-group">
								<label class="control-label"><font color="red">*</font>${fns:fy('邮箱地址码：')}</label>
								<input name="email" value="${userMain.email}" type="text" class="input-xlarge"/>
								<div class="formPrompt">
									<i class="msg-icon"></i>
									<div class="msg-con">${fns:fy('请输入邮箱地址码,长度不要超过32个字符。')}</div>
								</div>
							</div>
						</c:if>
						<div class="control-group" style="${userMain.typeAccount eq '1'?'display:none':''}">
							<label class="control-label"><font color="red">*</font>${fns:fy('账号组：')}</label>
							<c:forEach items="${list}" var="role">
							<c:set var="checked" value=""></c:set>
								<c:forEach items="${listssr}" var="item">
									<c:if test="${item.roleId==role.roleId}">
									<c:set var="checked" value="checked"></c:set>
									</c:if>
								</c:forEach>
								<input name="listRole" type="checkbox" ${checked} value="${role.roleId}"/>&nbsp;<span>${fns:fy(role.roleName)}</span>
							</c:forEach>
						</div>
					</dd>
					<div class="clear"></div>
					<div class="text-align pb20">
						<a href="javascript:void(0);" onclick="javascript:history.go(-1);" class="sui-btn btn-xlarge">${fns:fy('放弃操作')}</a>
						<shiro:hasPermission name="sso:userSeller:edit">
							<button type="submit" class="sui-btn btn-xlarge btn-primary ml20">${fns:fy('确认提交')}</button>
						</shiro:hasPermission>
					</div>
				</form>
			</dl>
		</div>
	</div>
</body>
</html>