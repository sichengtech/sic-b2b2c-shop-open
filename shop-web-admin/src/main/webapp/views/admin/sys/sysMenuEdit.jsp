<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>新增菜单</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- jquery-ztree js -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMenuSave.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>

<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">新增菜单</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="sysRoleList.jsp"> <i class="fa fa-home"></i>菜单列表</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i>添加菜单</a></li>
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
							<label class="control-label col-sm-2" for="inputSuccess">上级菜单&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input type="text" class="form-control" data-toggle="modal" data-target="#menuModal">
									<span class="input-group-btn">
									 <button type="button" class="btn btn-default"><i class="fa fa-search"></i></button>
									</span>
								</div> 
								<label for="firstname" class="error">请选择上级菜单</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>名称&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">名称不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请输入菜单名称 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">链接&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">点击菜单跳转的页面 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">目标&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">链接地址打开的目标窗口，默认：mainFrame	<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">图标&nbsp;: </label>
							<div class="col-sm-5">
								<label>无</label>
								<input type="hidden">
								<a type="button" class="btn btn-default selectIcon" href="javascript:;">选择</a>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请选择图标 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">排序&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">排列顺序，升序。 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">可见&nbsp;: </label>
							<div class="col-sm-5">
							 	<input type="checkbox" checked data-size="small" style="display: none" data-on-text="显示" data-off-text="隐藏"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该菜单或操作是否显示到系统菜单中 <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">权限标识&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">控制器中定义的权限标识，如：@RequiresPermissions("权限标识") <p>
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
				<h4 class="modal-title" id="">操作提醒</h4>	 
				</div>
				<!-- model-body开始 -->
				<div class="modal-body">
					<div class="row">
						<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">名称&nbsp;: </label>
								<div class="col-sm-8">
									<input class="form-control input-sm" type="text" placeholder=""> 
								</div>
								<div class="col-sm-2">
									<button type="submit" class="btn btn-primary" data-form-sbm="1480153408065.6372">搜索</button>
								</div>
							</div>
						</form>
					</div>
					<div class="row">
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<div id="tree" class="ztree" style="margin-top:3px;float:left;"></div>
							<input type="hidden" name="menuIds"/>
							<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
							<input type="hidden" name="officeIds"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../include/head_bootstrap-switch.jsp"%>
</body>
</html>