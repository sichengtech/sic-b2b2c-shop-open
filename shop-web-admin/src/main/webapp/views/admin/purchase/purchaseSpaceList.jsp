<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购空间管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseSpaceList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购空间列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('采购空间列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('采购空间管理是查看会员的采购空间信息')}</li>
					<li>${fns:fy('管理员可以修改采购空间的状态')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
					</div>
				</div>
				<form action="${ctxa}/purchase/purchaseSpace/list.do" method="get" id="searchForm">
					<div class="col-sm-5">
					</div>
					<div class="col-sm-2">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('请输入空间名称')}" value="${purchaseSpace.name}"/>
					</div>
					<div class="col-sm-2">
						<select name="isOpen" class="form-control input-sm">
							<option value="" class="firstOption">--${fns:fy('请选择空间状态')}--</option>
							<c:forEach items="${fns:getDictList('is_open')}" var="item">
							<option value="${item.value}" ${item.value==purchaseSpace.isOpen?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('空间id')}</th>
						<th>${fns:fy('会员名称')}</th>
						<th>${fns:fy('空间名称')}</th>
						<th>${fns:fy('空间banner')}</th>
						<th>${fns:fy('空间logo')}</th>
						<th>${fns:fy('空间简介')}</th>
						<th>${fns:fy('空间状态')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseSpace">
					<tr>
						<td>${purchaseSpace.spaceId}</td>
						<td>${purchaseSpace.userMain.loginName}</td>
						<td>${purchaseSpace.name}</td>
						<td>
							<c:if test="${not empty purchaseSpace.banner}">
								 <a href="${ctxfs}${purchaseSpace.banner}"  target="_blank">
									<image src="${ctxfs}${purchaseSpace.banner}@384x46"/>
								 </a>
							</c:if>
							<c:if test="${empty purchaseSpace.banner}">${fns:fy('无')}</c:if>
						</td>
						<td>
							<c:if test="${not empty purchaseSpace.logo}">
								 <a href="${ctxfs}${purchaseSpace.logo}"  target="_blank">
									<image src="${ctxfs}${purchaseSpace.logo}@100x50"/>
								 </a>
							</c:if>
							<c:if test="${empty purchaseSpace.logo}">${fns:fy('无')}</c:if>
						</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseSpace.synopsis}</textarea></td>
						<td>${fns:getDictLabel(purchaseSpace.isOpen, 'is_open', '')}</td>
						<td>
							<shiro:hasPermission name="purchase:purchaseSpace:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseSpace/edit1.do?spaceId=${purchaseSpace.spaceId}">
								<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<%-- <shiro:hasPermission name="purchase:purchaseSpace:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/purchase/purchaseSpace/delete.do?spaceId=${purchaseSpace.spaceId}">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
							</button>
							</shiro:hasPermission> --%>
						</td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy('无查询结果')}</div></td>
						</tr>
					</c:if>
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