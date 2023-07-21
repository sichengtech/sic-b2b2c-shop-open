<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty storeRole.roleId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号组')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userRoleSave.js"></script>
</head>
<body>
<sys:message content="${message}"/>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${empty storeRole.roleId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号组')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('账号')}</li>
						<li>${fns:fy('账号管理')}</li>
						<li class="active">${empty storeRole.roleId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号组')}</li>
					</ul>
				</dt>
				<!-- 提示开始 -->
				<dd style="padding-top: 0px;  padding-bottom: 0px;" class="sui-row-fluid sui-form form-horizontal screen pt10 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin-right: 20px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${empty storeRole.roleId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('账号组信息')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</dd>
				<!-- 提示结束-->
				<form id="myFrom" class="sui-form form-inline" method="post" action="${ctxs}/store/storeRole/${empty storeRole.roleId?'save2':'edit2'}.htm">
					<input name="roleId" value="${storeRole.roleId}" type="hidden"/>
					<dd>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('账号组名称：')}</label>
							<input id="oldRoleName" name="oldRoleName" value="${storeRole.roleName}" type="hidden"/>
							<input name="roleName" value="${storeRole.roleName}" type="text" class="input-xlarge "/>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入账号组名称,长度不要超过64个字符。')}</div>
							</div>
						</div>
						<div class="control-group selectAll">
							<label class="control-label"><font color="red">*</font>${fns:fy('基本权限：')}</label>
							<input type="checkbox" name="all" value="1" id="selectAll_id"/>&nbsp;<span>${fns:fy('全选')}</span>
						</div>
						<div id="tbody">
							<c:forEach items="${listNew2}" var="menu2" varStatus="status">
							<div class="control-group" >
								<label class="control-label"></label>
								<input name="listMenuId" type="checkbox" class="leader" value="${menu2.menuId}"/>&nbsp;<span>${fns:fy(menu2.name)}</span>
								<c:forEach items="${listNew3}" var="menu3" varStatus="sta">
								<c:set var="checked" value=""></c:set>
									<c:if test="${menu2.menuId==menu3.parent.id}">
										<c:forEach items="${list}" var="item2">
											<c:if test="${item2.menuId==menu3.menuId}">
											<c:set var="checked" value="checked"></c:set>
											</c:if>
										</c:forEach>
										<input name="listMenuId" ${checked} type="checkbox" value="${menu3.menuId}"/>&nbsp;<span>${fns:fy(menu3.name)}</span>
									</c:if>
								</c:forEach>
							</div>
							</c:forEach>
						</div>
					</dd>
					<div class="clear"></div>
					<div class="text-align pb20">
						<a href="javascript:void(0);" class="sui-btn btn-xlarge" onclick="javascript:history.go(-1);">${fns:fy('放弃操作')}</a>
						<shiro:hasPermission name="store:storeRole:edit">
							<button type="submit" class="sui-btn btn-xlarge btn-primary ml20">${fns:fy('确认提交')}</button>
						</shiro:hasPermission>
					</div>
				</form>
			</dl>
		</div>
	</div>
</body>
</html>