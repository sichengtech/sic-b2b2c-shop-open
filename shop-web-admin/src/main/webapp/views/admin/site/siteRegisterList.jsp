<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>注册设置管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteRegisterList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">注册设置列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 注册设置列表</a></li>
				<shiro:hasPermission name="site:siteRegister:edit"><li class=""><a href="${ctxa}/site/siteRegister/save1.do" > <i class="fa fa-user"></i> 注册设置添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>注册设置管理是设置平台中注册时的一些基本信息</li>
					<li>设置用户名和密码的长度</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-1">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteRegister:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/site/siteRegister/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteRegister/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="id" maxlength="19" class="form-control input-sm" placeholder="主键" value="${siteRegister.id}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="isRegister" maxlength="1" class="form-control input-sm" placeholder="是否开放注册" value="${siteRegister.isRegister}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="usernameMax" maxlength="10" class="form-control input-sm" placeholder="用户名最大长度" value="${siteRegister.usernameMax}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="usernameMin" maxlength="10" class="form-control input-sm" placeholder="用户名最小长度" value="${siteRegister.usernameMin}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pwdMax" maxlength="10" class="form-control input-sm" placeholder="密码最大长度" value="${siteRegister.pwdMax}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pwdMin" maxlength="10" class="form-control input-sm" placeholder="密码最小长度" value="${siteRegister.pwdMin}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="disableUsername" maxlength="1024" class="form-control input-sm" placeholder="禁用用户名" value="${siteRegister.disableUsername}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="agreement" maxlength="1024" class="form-control input-sm" placeholder="注册协议" value="${siteRegister.agreement}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>主键</th>
						<th>是否开放注册</th>
						<th>用户名最大长度</th>
						<th>用户名最小长度</th>
						<th>密码最大长度</th>
						<th>密码最小长度</th>
						<th>禁用用户名</th>
						<th>注册协议</th>
						<shiro:hasPermission name="site:siteRegister:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="siteRegister">
					<tr>
						<td><a href="${ctxa}/site/siteRegister/edit1.do?id=${siteRegister.id}">${siteRegister.id}</a></td>
						<td>${siteRegister.isRegister}</td>
						<td>${siteRegister.usernameMax}</td>
						<td>${siteRegister.usernameMin}</td>
						<td>${siteRegister.pwdMax}</td>
						<td>${siteRegister.pwdMin}</td>
						<td>${siteRegister.disableUsername}</td>
						<td>${siteRegister.agreement}</td>
						<shiro:hasPermission name="site:siteRegister:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteRegister/edit1.do?id=${siteRegister.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteRegister/delete.do?id=${siteRegister.id}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						 </td>
						</shiro:hasPermission>
					</tr>
					</c:forEach>
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