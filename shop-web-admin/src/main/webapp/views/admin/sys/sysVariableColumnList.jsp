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
<script type="text/javascript" src="${ctx}/views/admin/sys/sysVariableColumnList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">自定义表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> Column列表</a></li>
				<li class=""><a href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp"> <i class="fa fa-user"></i> Column添加</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>查看自定义表有哪些Column列。</li>
					<li>为自定义表添加Column列。</li>
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
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp"><i class="fa fa-plus"></i></a>
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
						<th>槽位</th> 
						<th>字段名</th> 
						<th>数据类型</th>
						<th>内容最大长度</th>
						<th>内容最小长度</th>
						<th>非空</th>
						<th>正则</th>
						<th>验证失败提示信息</th>
						<th>帮助提示信息</th>
						<th>字典</th>
						<th>控件类型</th>
						<th>style</th>
						<th>排序</th>
						<th>状态</th>
						<th>管理操作</th>
					</tr>
				</thead> 
				<tbody>
					<tr>
						<td>v1</td> 
						<td>姓名</td> 
						<td>String</td>
						<td>64</td>
						<td>1</td>
						<td>否</td>
						<td>./*.*</td>
						<td>请输入姓名</td>
						<td>这里输入你的姓名</td>
						<td><input type="text" class="form-control input-sm" value="sex" style="width:40px;"></td>
						<td>
							<select class="selectpicker form-control input-sm " > 
							 <option value="0">请选择</option> 
							 <option value="1">单行文本框</option> 
							 <option value="2">下拉选</option> 
							 <option value="3">单选组</option> 
							 <option value="4">复选组</option>
							 <option value="5">多行文本框</option>
							</select>
						</td>
						<td>width:50px;</td>
						<td>10</td>
						<td>启用</td>
						<td>
							 <a href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-edit"></i> 修改
							 </a>
							 <button type="button" class="btn btn-danger btn-sm deleDate">
								<i class="fa fa-edit"></i> 删除
							 </button>
						</td> 
					</tr>
					<tr>
						<td>v2</td> 
						<td>年龄</td> 
						<td>String</td>
						<td>64</td>
						<td>1</td>
						<td>否</td>
						<td>./*.*</td>
						<td>请输入姓名</td>
						<td>这里输入你的姓名</td>
						<td><input type="text" class="form-control input-sm" value="sex" style="width:40px;"></td>
						<td>
							<select class="selectpicker form-control input-sm " > 
							 <option value="0">请选择</option> 
							 <option value="1">单行文本框</option> 
							 <option value="2">下拉选</option> 
							 <option value="3">单选组</option> 
							 <option value="4">复选组</option>
							 <option value="5">多行文本框</option>
							</select>
						</td>
						<td>width:50px;</td>
						<td>10</td>
						<td>启用</td>
						<td>
							 <a href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp" class="btn btn-info btn-sm">
								<i class="fa fa-edit"></i> 修改
							 </a>
							 <button type="button" class="btn btn-danger btn-sm deleDate">
								<i class="fa fa-edit"></i> 删除
							 </button>
						</td> 
					</tr>
					<tr>
						<td>v3</td> 
						<td>地址</td> 
						<td>String</td>
						<td>64</td>
						<td>1</td>
						<td>否</td>
						<td>./*.*</td>
						<td>请输入姓名</td>
						<td>这里输入你的姓名</td>
						<td><input type="text" class="form-control input-sm" value="sex" style="width:40px;"></td>
						<td>
							<select class="selectpicker form-control input-sm " > 
							 <option value="0">请选择</option> 
							 <option value="1">单行文本框</option> 
							 <option value="2">下拉选</option> 
							 <option value="3">单选组</option> 
							 <option value="4">复选组</option>
							 <option value="5">多行文本框</option>
							</select>
						</td>
						<td>width:50px;</td>
						<td>10</td>
						<td>启用</td>
						<td>
							 <a href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp" class="btn btn-info btn-sm">
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
	<!-- panel结束 -->
</body>
</html>