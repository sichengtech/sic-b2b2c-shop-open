<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员管理-升级采购商-标题")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userPurchaseList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员管理-升级采购商-标题")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员管理-升级采购商-标题")}</a></li>
				<%-- <shiro:hasPermission name="sso:userPurchase:save"><li class=""><a href="${ctxa}/sso/userPurchase/save1.do" > <i class="fa fa-user"></i> 采购商添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.升级采购商.操作提示1")}</li>
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
						<shiro:hasPermission name="sso:userPurchase:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sso/userPurchase/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> -->
					</div>
				</div>
				<%-- <form action="${ctxa}/sso/userPurchase/list.do" method="get" id="searchForm">
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
						<th>${fns:fy("企业名称")}</th>
						<th>${fns:fy("企业类型")}</th>
						<th>${fns:fy("企业属性")}</th>
						<th>${fns:fy("营业执照")}</th>
						<th>${fns:fy("联系人")}</th>
						<th>${fns:fy("联系人电话")}</th>
						<th>${fns:fy("审核状态")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="userPurchase">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${userPurchase.UId}</td>
						<td>${userPurchase.userMain.loginName}</td>
						<td>${userPurchase.name}</td>
						<td>${fns:getDictLabel(fn:trim(userPurchase.type), 'use_shop_type', '')}</td>
						<td>${fns:getDictLabel(fn:trim(userPurchase.industry), 'user_industry', '')}</td>
						<td>
							 <a target=_blank href="${ctxfs}${userPurchase.businesslicense}" >
							 	<img src="${ctxfs}${userPurchase.businesslicense}@!50x50?accessKey=${generateAccessKey}" style="${not empty userPurchase.businesslicense?'':'display:none;'}"/>
							 </a>
						</td>
						<td>${userPurchase.contacts}</td>
						<td>${userPurchase.contactsTelephone}</td>
						<td>${fns:getDictLabel(fn:trim(userPurchase.authType), 'brand_status', '')}</td>
						<td>
							<shiro:hasPermission name="sso:userPurchase:auth">
							<a class="btn btn-info btn-sm" href="${ctxa}/sso/userPurchase/auth1.do?uId=${userPurchase.UId}">
								<i class="fa fa-edit"></i> ${fns:fy("审核")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sso:userPurchase:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sso/userPurchase/delete.do?uId=${userPurchase.UId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="2" class="dt-grid-cell " >${fns:fy("所在地")} : ${userPurchase.provinceName}&nbsp;${userPurchase.cityName}&nbsp;${userPurchase.districtName}&nbsp;${userPurchase.detailedAddress}</p>
							<p datano="0" columnno="0" class="dt-grid-cell " style="word-wrap: break-word;width: 800px;">${fns:fy("企业介绍")}：${userPurchase.introduce}</p>
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