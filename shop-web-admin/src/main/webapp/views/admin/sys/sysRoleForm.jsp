<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty role.id?fns:fy("添加角色"):fns:fy("编辑角色")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- jquery-ztree css -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
	//用户-菜单
 	var zNodes=[<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:fns:fy("权限列表")}"},
			</c:forEach>];

	// 用户-机构
	var zNodes2=[<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
			</c:forEach>]; 
	//角色中所有的菜单
	var menuIds='${role.menuIds}';
	var officeIds='${role.officeIds}';
</script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysRoleForm.js"></script>

<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
<style type="text/css">
	.form-control{font-size: 12px;}
	div.jbox .jbox-title-panel {padding: 0;}
	div.jbox .jbox-close, div.jbox .jbox-close-hover {margin: 0px;}
	.jbox-button-panel{height: 35px!important;}
	table .jbox-title-panel{height: 30px!important;}
	table .jbox-title{line-height:29px!important; }
	table .jbox-close{top:15px!important;}
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${empty role.id?fns:fy("添加角色"):fns:fy("编辑角色")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/role.do"> <i class="fa fa-home"></i> ${fns:fy("角色列表")}</a></li>
				<shiro:hasPermission name="sys:role:edit">
				<li class="active"><a href="javascript:;"> <i class="fa fa-user"></i> ${empty role.id?fns:fy("添加角色"):fns:fy("编辑角色")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.角色管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" action="${ctxa}/sys/role/save.do" id="inputForm" method="post">
						<input type="hidden" name="id" value="${role.id}"/>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("归属机构")}:</label>
							<div class="col-sm-5">
								<sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
									title="${fns:fy('机构')}" url="/sys/office/treeData.do" extId="${role.id}" cssClass="required"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择归属机构")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("角色名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="oldName" name="oldName" type="hidden" value="${role.name}">
								<input class="form-control input-sm" type="text" placeholder="" name="name" value="${role.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写角色名称")} <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("英文名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
								<input class="form-control input-sm" type="text" placeholder="" name="enname" value="${role.enname}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写英文名称")} <p>
							</div>
						</div>
						<%-- 
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">角色类型&nbsp;: </label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name="roleType"> 
									<option value="assignment">任务分配</option>
									<option value="security-role">管理角色</option>
									<option value="user">普通角色</option>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否系统数据&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15" name="sysData">
									<c:forEach items="${fns:getDictList('yes_no')}" var="sd">
										<option ${sd.value eq role.sysData?"selected":""} value="${sd.value}">${sd.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否可用&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15" name="useable">
									<c:forEach items="${fns:getDictList('yes_no')}" var="sd">
										<option ${sd.value eq role.useable?"selected":""} value="${sd.value}">${sd.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">“是”代表此数据可用，“否”则表示此数据不可用 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">数据范围&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15" name="dataScope" id="">
									<c:forEach items="${fns:getDictList('sys_data_scope')}" var="dc">
										<option ${dc.value eq role.dataScope?"selected":""} value="${dc.value}">${dc.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">特殊情况下，设置为“按明细设置”，可进行跨机构授权 <p>
							</div>
						</div>
						--%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("角色授权")}&nbsp;:</label>
							<div class="col-sm-5">
								<!-- <div id="tree" class="ztree" style="margin-top:3px;float:left;"></div> -->
								<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
								<input type="hidden" value="${menuList}" name="menuIds" id="menuIds"/>
								<%-- <div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
								<input type="hidden" value="${officeList}" name="officeIds" id="officeIds"/> --%>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("备注")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea id="adminMessage" name="adminMessage" class="form-control" rows="3"></textarea>
							</div>
						</div> 
						<div class="form-group">
							<div class="col-lg-6 col-md-offset-2">
								<c:if test="${not empty role.id}">
									<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
										<i class="fa fa-times"></i> ${fns:fy("返回")}
									</button>
								</c:if>
								<c:if test="${(role.sysData eq fns:getDictValue(fns:fy('是'), 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue(fns:fy('是'), 'yes_no', '1'))}">
									<shiro:hasPermission name="sys:role:edit">
										<c:if test="${empty role.id}">
											<button type="submit" name="submitBtn" class="btn btn-info submitBtn" value="1">
												<i class="fa fa-check-circle"></i> ${fns:fy("保存并继续添加")}
											</button>
										</c:if>
										<button type="submit" name="submitBtn" class="btn btn-info submitBtn" value="2">
											<i class="fa fa-check"></i> ${fns:fy("保存")}
										</button>
									</shiro:hasPermission>
								</c:if>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- panel结束 -->
</body>
</html>