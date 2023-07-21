<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("商家注册信息管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userMerchantInfoList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("商家注册信息管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("商家注册信息管理")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.商家信息.操作提示1")}</li>
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
					</div>
				</div>
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
						<th>${fns:fy("企业类型")}</th>
						<th>${fns:fy("行业属性")}</th>
						<th>${fns:fy("联系人")}</th>
						<th>${fns:fy("联系人电话")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="userMerchantInfo">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${userMerchantInfo.UId}</td>
						<td>${userMerchantInfo.userMain.loginName}</td>
						<td>${fns:getDictLabel(fn:trim(userMerchantInfo.type),'use_shop_type','')}</td>
						<td>${fns:getDictLabel(fn:trim(userMerchantInfo.industry),'user_industry','')}</td>
						<td>${userMerchantInfo.contacts}</td>
						<td>${userMerchantInfo.contactsTelephone}</td>
						<td>
							<shiro:hasPermission name="sso:userMerchantInfo:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sso/userMerchantInfo/delete.do?uId=${userMerchantInfo.UId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="8" >
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("公司名称")}：${userMerchantInfo.name}</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("公司介绍")}：${userMerchantInfo.introduce}</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">
									${fns:fy("公司地址")} : ${userMerchantInfo.provinceName}&nbsp;
								${userMerchantInfo.cityName}&nbsp;
								${userMerchantInfo.districtName}&nbsp;
								${userMerchantInfo.detailedAddress}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("所在部门")}：${fns:getDictLabel(fn:trim(userMerchantInfo.department),'user_department','')}</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("客服电话")}：${empty userMerchantInfo.customCall? fns:fy("无") :userMerchantInfo.customCall}</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("公司网址")}：${empty userMerchantInfo.companyWebsite? fns:fy("无") :userMerchantInfo.companyWebsite}</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all;">${fns:fy("公司品牌")}：${empty userMerchantInfo.companyBrand? fns:fy("无") :userMerchantInfo.companyBrand}</p>
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