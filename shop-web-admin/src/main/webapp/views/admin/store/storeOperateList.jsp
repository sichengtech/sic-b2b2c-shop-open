<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("代运营管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeOperateList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("代运营列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:"> <i class="fa fa-user"></i> ${fns:fy("店铺列表")}</a></li>
				<%-- <shiro:hasPermission name="store:store:edit"><li class=""><a href="${ctxa}/store/store/save1.do" > <i class="fa fa-user"></i> 店铺添加</a></li></shiro:hasPermission>--%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.代运营.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<%-- <shiro:hasPermission name="store:store:edit">
							<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/store/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<div class="col-sm-4"></div>
				<form action="${ctxa}/store/store/operatelist.do" method="get" id="searchForm">
					<div class="col-sm-6">
						<div class="iconic-input right">
							<i class="fa fa-search " style="cursor:pointer" id="searchFormButton"></i>
							<input type="text" name="loginName" maxlength="24" value="${userMain.loginName}" class="form-control input-sm pull-right" placeholder="${fns:fy("请输入会员名称进行精确搜索")}" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th>${fns:fy("会员ID")}</th>
						<th>${fns:fy("会员名称")}</th>
						<th>${fns:fy("是否锁定")}</th>
						<th>${fns:fy("店铺名")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="userMain">
					<tr>
						<td>${userMain.UId}</td>
						<td>${userMain.loginName}</td>
						<td>${fns:getDictLabel(userMain.isLocked, 'yes_no', '')}</td>
						<td>${userMain.userSeller.store.name}</td>
						<shiro:hasPermission name="store:store:edit">
							<td>
								<a target="_self" class="btn btn-info btn-sm storeOperate" href="javaScript:" UId="${userMain.UId}" isLocked="${userMain.isLocked}" >
									<i class="fa fa-eye"></i> ${fns:fy("代运营")}
								</a>
							</td>
						</shiro:hasPermission> 
					</tr>
					</c:forEach>
				</tbody>
			</table>
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