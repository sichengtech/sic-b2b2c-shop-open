<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty menu.id?fns:fy("添加菜单"):fns:fy("编辑菜单")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<%-- <!-- jquery-ztree css -->
<link href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
<!-- jquery-ztree js -->
<script src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script> --%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMenuForm.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
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
			<h4 class="title">${empty menu.id?fns:fy("添加菜单"):fns:fy("编辑菜单")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/menu.do"> <i class="fa fa-home"></i> ${fns:fy("菜单列表")}</a></li>
				<shiro:hasPermission name="sys:menu:edit">
				<li class="active"><a href="javascript:;" > <i class="fa fa-user"></i> ${empty menu.id?fns:fy("添加菜单"):fns:fy("编辑菜单")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.菜单管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->	
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/menu/save.do">
						<input type="hidden" name="id" value="${menu.id}"/>
						<input type="hidden" name="stat" id="stat" value=""/>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("上级菜单")}&nbsp;:</label>
							<div class="col-sm-5">
								<!-- <div class="input-group">
									<input type="text" class="form-control" data-toggle="modal" data-target="#menuModal">
									<span class="input-group-btn">
									 <button type="button" class="btn btn-default"><i class="fa fa-search"></i></button>
									</span>
								</div> -->
								<div class="controls">
									<sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
										title="菜单" url="/sys/menu/treeData.do" extId="${menu.id}" cssClass="required"/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" maxLength="20" name="name" value="${menu.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请输入菜单名称")} <p>
							</div>
						</div>
<%--						<div class="form-group">--%>
<%--							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("编号")}&nbsp;:</label>--%>
<%--							<div class="col-sm-5">--%>
<%--								<input id="oldMenuNum" name="oldMenuNum" type="hidden" value="${menu.menuNum}">--%>
<%--								<input class="form-control input-sm" type="text" placeholder="" name="menuNum" maxLength="20" value="${menu.menuNum}"> --%>
<%--							</div>--%>
<%--							<div class="col-sm-5">--%>
<%--								<p class="help-block">${fns:fy("请输入菜单编号")} <p>--%>
<%--							</div>--%>
<%--						</div>--%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("链接")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" maxLength="2000" name="href" value="${menu.href}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("点击菜单跳转的页面")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("目标")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="target" maxLength="20" value="${menu.target}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("系统设置.菜单管理.操作提示3")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("图标")}&nbsp;: </label>
							<div class="col-sm-5">
								<sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择图标")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("排序")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="sort" maxLength="10" value="${menu.sort}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("排列顺序，升序")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("可见")}&nbsp;: </label>
							<div class="col-sm-5">
							 	<input type="checkbox" ${menu.isShow eq 0?"":"checked"} data-size="small" value="1" style="display: none" data-on-text="${fns:fy("显示")}" data-off-text="${fns:fy("隐藏")}" name="isShow" id="isShow"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("该菜单或操作是否显示到系统菜单中")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("权限标识")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="permission" maxLength="200" value="${menu.permission}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("系统设置.菜单管理.操作提示4")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("备注")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea id="adminMessage" class="form-control" rows="3" name="remarks" maxLength="255">${menu.remarks}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-6 col-md-offset-2">
								<c:if test="${not empty menu.id}">
									<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
										<i class="fa fa-times"></i> ${fns:fy("返回")}
									</button>
								</c:if>
								<shiro:hasPermission name="sys:menu:edit">
								<c:if test="${empty menu.id}">
									<button type="submit" name="submitBtn" class="btn btn-info submitBtn" value="1">
										<i class="fa fa-check-circle"></i> ${fns:fy("保存并继续添加")}
									</button>
								</c:if>
								<button type="submit" name="submitBtn" class="btn btn-info submitBtn" value="2">
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
	<div class="modal fade" id="menuModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-dialog">
			<div class="modal-content">	 
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="">${fns:fy("操作提醒")}</h4>
				</div>
				<!-- model-body开始 -->
				<div class="modal-body">
					<div class="row">
						<form class="cmxform form-horizontal adminex-form">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("名称")}&nbsp;: </label>
								<div class="col-sm-8">
									<input class="form-control input-sm" type="text" placeholder=""> 
								</div>
								<div class="col-sm-2">
									<button type="submit" class="btn btn-primary" data-form-sbm="1480153408065.6372">${fns:fy("搜索")}</button>
								</div>
							</div>
						</form>
					</div>
					<div class="row">
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<div id="tree" class="ztree" style="margin-top:3px;float:left;"></div>
							<form class="hidden" path="menuIds"/>
							<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
							<form class="hidden" path="officeIds"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../include/head_bootstrap-switch.jsp"%>
</body>
</html>