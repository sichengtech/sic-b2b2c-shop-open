<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>会员等级</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">会员等级</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 会员等级列表</a></li>
				<li class=""><a href="${ctx}/views/admin/member/memberLevelSave.jsp"> <i class="fa fa-user"></i> 添加等级</a></li>
			</ul>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>操作提示</h5>
					<ul>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
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
							<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctx}/views/admin/member/memberLevelSave.jsp"><i class="fa fa-plus"></i></a>
						</div>
					</div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-1"></div>
					<div class="col-sm-3">
						<div class="iconic-input right">
							<i class="fa fa-search"></i>
							<input type="text" class="form-control input-sm pull-right" placeholder="请输入级别名称进行搜索" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>等级ID</th> 
						<th>级别名称</th> 
						<th>所需经验</th> 
						<th>等级图标</th>
						<th>管理操作</th>
					</tr>
				</thead> 
				<tbody>
					<tr>
						<td>1</td> 
						<td>会员级别V1</td> 
						<td>0</td>
						<td></td>
						<td>
							 <a class="btn btn-info btn-sm" href="${ctx}/views/admin/member/memberLevelEdit.jsp">
								<i class="fa fa-edit"></i> 编辑
							 </a>
						</td> 
					</tr>
					<tr>
						<td>2</td> 
						<td>会员级别V2</td> 
						<td>1000</td>
						<td></td>
						<td>
							 <a class="btn btn-info btn-sm" href="${ctx}/views/admin/member/memberLevelEdit.jsp">
								<i class="fa fa-edit"></i> 编辑
							 </a>
						</td> 
					</tr>
					<tr>
						<td>3</td> 
						<td>会员级别V3</td> 
						<td>2000</td>
						<td></td>
						<td>
							 <a class="btn btn-info btn-sm" href="${ctx}/views/admin/member/memberLevelEdit.jsp">
								<i class="fa fa-edit"></i> 编辑
							 </a>
						</td> 
					</tr>
					<tr>
						<td>4</td> 
						<td>会员级别V4</td> 
						<td>5000</td>
						<td></td>
						<td>
							 <a class="btn btn-info btn-sm" href="${ctx}/views/admin/member/memberLevelEdit.jsp">
								<i class="fa fa-edit"></i> 编辑
							 </a>
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
	</section>
</body>
</html>