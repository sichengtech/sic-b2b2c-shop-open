<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>新增角色</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- jquery-ztree js -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysRoleSave.js"></script>

<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">新增角色</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="sysRoleList.jsp"> <i class="fa fa-home"></i>角色列表</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i>添加角色</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in">
				<h5>操作提示</h5>
				<ul>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>角色名称&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="买家管理角色"> 
								<label for="firstname" class="error">角色名称不能为空</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>英文名称&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="买家管理角色"> 
								<label for="firstname" class="error">英文名称不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">工作流用户组标识 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">角色类型&nbsp;: </label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name=""> 
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
								<select class="form-control input-sm" id="" name=""> 
									<option value="assignment">是</option>
									<option value="security-role">否</option>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否可用&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name=""> 
									<option value="assignment">是</option>
									<option value="security-role">否</option>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">“是”代表此数据可用，“否”则表示此数据不可用 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">数据范围&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name=""> 
									<option value="assignment">所有数据</option>
									<option value="security-role">所在公司及一下数据</option>
									<option value="assignment">所在公司数据</option>
									<option value="security-role">所在部分及一下数据</option>
									<option value="assignment">所在部门数据</option>
									<option value="security-role">仅本人数据</option>
									<option value="assignment">按明细设置</option>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">特殊情况下，设置为“按明细设置”，可进行跨机构授权 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">角色授权&nbsp;:</label>
							<div class="col-sm-5">
								<div id="tree" class="ztree" style="margin-top:3px;float:left;"></div>
								<input type="hidden" name="menuIds"/>
								<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
								<input type="hidden" name="officeIds"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">备注&nbsp;:</label>
							<div class="col-sm-5">
								<textarea id="adminMessage" name="adminMessage" class="form-control" rows="3"></textarea>
							</div>
						</div> 
						<div class="form-group">
							<div class="col-lg-6 col-md-offset-2">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check-circle"></i> 保存并继续添加
								</button>
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存 
								</button>
							</div>
						</div>
						<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>