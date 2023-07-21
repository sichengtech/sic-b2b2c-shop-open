<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>自定义表</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysVariableTableList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">自定义表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> Table列表</a></li>
				<li class=""><a href="${ctx}/views/admin/sys/sysVariableTableSave.jsp"> <i class="fa fa-user"></i> Table添加</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>自定义表的结构灵活，可满足不可预期的业务需求。</li>
					<li>自定义表不适合存储大量数据，总数据建议在1万条以内。</li>
					<li>应用场景：如项目开发完成并成功上线后，想增加一些系统配置数据，数据量只有几条，又不想重新开发，可在这里通过配置就可以“自定义一张表”。业务可以通过标签库取出本表中的数据。</li>
				</ul>
			</div>
			<!-- 提示 end --> 
				<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-2">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 新增记录按钮 -->
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctx}/views/admin/sys/sysVariableTableSave.jsp"><i class="fa fa-plus"></i></a>
					</div>
				</div>
				<div class="col-sm-2"> </div>
				<div class="col-sm-2"> </div>
				<div class="col-sm-2"> </div>
				<div class="col-sm-1"> </div>
				<div class="col-sm-3">
					<div class="iconic-input right">
						<i class="fa fa-search"></i>
						<input type="text" class="form-control input-sm pull-right" placeholder="请输入表名称进行搜索" style="border-radius: 30px;max-width:250px;">
					</div>
				</div>
			</div>
			<!-- 按钮结束--> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th>ID</th> 
						<th>表名</th> 
						<th>描述</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>管理操作</th>
					</tr>
				</thead> 
				<tbody>
					<tr>
						<td>1</td>
						<td>注册设置表</td>
						<td>注册相关的配置信息</td>
						<td>启用</td>
						<td>2016-11-20</td>
						<td>
							 <a href="${ctx}/views/admin/sys/sysVariableColumnList.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-eye"></i> 查看列
							 </a>
							 <a href="${ctx}/views/admin/sys/sysVariableTableSave.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-edit"></i> 修改
							 </a>
							 <button type="button" class="btn btn-danger btn-sm deleDate">
								<i class="fa fa-edit"></i> 删除
							 </button>
						</td> 
					</tr>
					<tr>
						<td>2</td>
						<td>注册设置表</td>
						<td>注册相关的配置信息</td>
						<td>启用</td>
						<td>2016-11-20</td>
						<td>
							<a href="${ctx}/views/admin/sys/sysVariableColumnList.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-eye"></i> 查看列
							 </a>
							 <a href="${ctx}/views/admin/sys/sysVariableTableSave.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-edit"></i> 修改
							 </a>
							 <button type="button" class="btn btn-danger btn-sm deleDate">
								<i class="fa fa-edit"></i> 删除
							 </button>
						</td> 
					</tr>
					<tr>
						<td>3</td>
						<td>注册设置表</td>
						<td>注册相关的配置信息</td>
						<td>启用</td>
						<td>2016-11-20</td>
						<td>
							<a href="${ctx}/views/admin/sys/sysVariableColumnList.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-eye"></i> 查看列
							 </a>
							 <a href="${ctx}/views/admin/sys/sysVariableTableSave.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-edit"></i> 修改
							 </a>
							 <button type="button" class="btn btn-danger btn-sm deleDate">
								<i class="fa fa-edit"></i> 删除
							 </button>
						</td> 
					</tr>
				</tbody>
			 	</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
</body>
</html>