<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺菜单管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeMenuForm.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<style type="text/css">
	div.jbox .jbox-title-panel {
		padding: 0;
	}
	div.jbox .jbox-close, div.jbox .jbox-close-hover {
		margin: 0px;
	}
	.jbox-button-panel{height: 35px!important;}
	table .jbox-title-panel{height: 30px!important;}
	table .jbox-title{line-height:29px!important; }
	table .jbox-close{top:15px!important;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeMenu.menuId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("店铺菜单")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeMenu/list.do"> <i class="fa fa-user"></i> ${fns:fy("店铺菜单列表")}</a></li>
				<shiro:hasPermission name="store:storeMenu:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("店铺菜单")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.菜单管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeMenu/save.do" method="post">
						<input type="hidden" name="menuId" value="${storeMenu.menuId}">
						<input id="oldMenuNum" type="hidden" name="menuId" value="${storeMenu.menuNum}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("上级菜单")}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="controls">
									<sys:treeselect id="menu" name="parent.id" value="${storeMenu.parent.id}" labelName="parent.name" labelValue="${storeMenu.parent.name}"
										title="菜单" url="/store/storeMenu/treeData.do" extId="${storeMenu.id}" cssClass="required"/>
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${storeMenu.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请输入菜单名称")}<p>
							</div>
						</div>
<%--						<div class="form-group">--%>
<%--							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("编号")}&nbsp;:</label>--%>
<%--							<div class="col-sm-5">--%>
<%--								<input type="text" name="menuNum"  maxlength="64" class="form-control input-sm" value="${storeMenu.menuNum}"/>--%>

<%--							</div>--%>
<%--							<div class="col-sm-5">--%>
<%--								<p class="help-block">${fns:fy("请输入菜单编号")}<p>--%>
<%--							</div>--%>
<%--						</div>--%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("链接")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="href"  maxlength="2000" class="form-control input-sm" value="${storeMenu.href}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("点击菜单跳转的页面")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("目标")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="target"  maxlength="64" class="form-control input-sm" value="${storeMenu.target}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("链接地址打开的目标窗口")}${fns:fy("默认")}:mainFrame<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${storeMenu.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("排列顺序")}${fns:fy("升序")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("可见")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" value="${storeMenu.isShow}" ${storeMenu.isShow eq 0?"":"checked"} data-size="small" value="1" style="display: none" data-on-text="${fns:fy("显示")}" data-off-text="${fns:fy("隐藏")}" name="isShow" id="isShow"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.菜单管理.操作提示3")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("权限标识")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="permission"  maxlength="512" class="form-control input-sm" value="${storeMenu.permission}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.菜单管理.操作提示4")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<c:if test="${not empty storeMenu.id}">
									<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
										<i class="fa fa-times"></i> ${fns:fy("返回")}
									</button>
								</c:if>
								<shiro:hasPermission name="store:storeMenu:edit">
								<c:if test="${empty storeMenu.id}">
									<button name="submitBtn" value="1" type="submit" class="btn btn-info">
										<i class="fa fa-check"></i> ${fns:fy("保存并继续添加")}
									</button>
								</c:if>
								<button name="submitBtn" value="2" type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>