<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("汽车服务门店列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userRepairShopList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("汽车服务门店列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("汽车服务门店列表")}</a></li>
				<%-- <shiro:hasPermission name="sso:userRepairShop:save"><li class=""><a href="${ctxa}/sso/userRepairShop/save1.do" > <i class="fa fa-user"></i> 汽车服务门店添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.服务门店.操作提示1")}</li>
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
						<!-- 添加记录按钮 
						<shiro:hasPermission name="sso:userRepairShop:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sso/userRepairShop/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> -->
					</div>
				</div>
				<%-- <form action="${ctxa}/sso/userRepairShop/list.do" method="get" id="searchForm">
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form> --%>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>ID</th>
						<th>${fns:fy("会员名")}</th>
						<th>${fns:fy("门店名称")}</th>
						<th>${fns:fy("门店类型")}</th>
						<th>${fns:fy("服务热线")}</th>
						<th>${fns:fy("联系人姓名")}</th>
						<th>${fns:fy("审核状态")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="userRepairShop">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${userRepairShop.UId}</td>
						<td>${userRepairShop.userMain.loginName}</td>
						<td>${userRepairShop.name}</td>
						<td>${fns:getDictLabel(fn:trim(userRepairShop.type),'door_store_type','')}</td>
						<td>${userRepairShop.hotline}</td>
						<td>${userRepairShop.contactsName}</td>
						<td>${fns:getDictLabel(userRepairShop.authType, 'brand_status', '')}</td>
						<td>
							<shiro:hasPermission name="sso:userRepairShop:auth">
								<a class="btn btn-info btn-sm" href="${ctxa}/sso/userRepairShop/auth1.do?uId=${userRepairShop.UId}">
									<i class="fa fa-edit"></i> ${fns:fy("审核")}
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sso:userRepairShop:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sso/userRepairShop/delete.do?uId=${userRepairShop.UId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell ">${fns:fy("经营品牌")}：${userRepairShop.brands}	 </p>
							<p datano="0" columnno="1" class="dt-grid-cell ">${fns:fy("建店日期")} : <fmt:formatDate value="${userRepairShop.openDate}" type="both" dateStyle="full"/></p>
							<p datano="0" columnno="2" class="dt-grid-cell ">${fns:fy("老板姓名")} : ${userRepairShop.bossName}</p>
							<p datano="0" columnno="2" class="dt-grid-cell ">${fns:fy("老板电话")} : ${userRepairShop.bossMobile}</p>
							<p datano="0" columnno="2" class="dt-grid-cell ">${fns:fy("门店地址")}: ${userRepairShop.countryName}&nbsp;${userRepairShop.provinceName}&nbsp;${userRepairShop.cityName}&nbsp;${userRepairShop.districtName}&nbsp;${userRepairShop.detailedAddress}</p>
							<p datano="0" columnno="4" class="dt-grid-cell ">${fns:fy("开店营业时间")} : <fmt:formatDate value="${userRepairShop.openShopDate}" type="both" dateStyle="full"/></p>
							<p datano="0" columnno="5" class="dt-grid-cell ">${fns:fy("关店营业时间")} :  <fmt:formatDate value="${userRepairShop.closeShopDate}" type="both" dateStyle="full"/></p>
							<p datano="0" columnno="6" class="dt-grid-cell ">${fns:fy("店主姓名")}: ${userRepairShop.shopkeeperName}</p>
							<p datano="0" columnno="7" class="dt-grid-cell ">${fns:fy("店主手机")} : ${userRepairShop.shopkeeperMobile}</p>
							<p datano="0" columnno="8" class="dt-grid-cell ">${fns:fy("所在部门")} : ${fns:getDictLabel(fn:trim(userRepairShop.department), 'user_department', '')}</p>
							<p datano="0" columnno="10" class="dt-grid-cell ">
									${fns:fy("门店照片")}:
								<c:if test="${not empty userRepairShop.path1}">
									<a target="_blank" href="${ctxfs}${userRepairShop.path1}"><img alt="" src="${ctxfs}${userRepairShop.path1}" width="50px" height="50px"></a>
								</c:if>
								<c:if test="${not empty userRepairShop.path2}">
									<a target="_blank" href="${ctxfs}${userRepairShop.path2}"><img alt="" src="${ctxfs}${userRepairShop.path2}" width="50px" height="50px"></a>
								</c:if>
								<c:if test="${not empty userRepairShop.path3}">
									<a target="_blank" href="${ctxfs}${userRepairShop.path3}"><img alt="" src="${ctxfs}${userRepairShop.path3}" width="50px" height="50px"></a>
								</c:if>
								<c:if test="${not empty userRepairShop.path4}">
									<a target="_blank" href="${ctxfs}${userRepairShop.path4}"><img alt="" src="${ctxfs}${userRepairShop.path4}" width="50px" height="50px"></a>
								</c:if>
								<c:if test="${not empty userRepairShop.path5}">
									<a target="_blank" href="${ctxfs}${userRepairShop.path5}"><img alt="" src="${ctxfs}${userRepairShop.path5}" width="50px" height="50px"></a>
								</c:if>
							</p>
						</td>
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