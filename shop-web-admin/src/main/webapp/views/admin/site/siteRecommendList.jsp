<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("推荐位管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteRecommendList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("推荐位列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("推荐位列表")}</a></li>
				<shiro:hasPermission name="site:siteRecommend:save"><li class=""><a href="${ctxa}/site/siteRecommend/save1.do" > <i class="fa fa-user"></i> ${fns:fy("推荐位添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.推荐位.操作提示1")}</li>
					<li>${fns:fy("网站设置.推荐位.操作提示2")}</li>
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
						<shiro:hasPermission name="site:siteRecommend:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteRecommend/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteRecommend/list.do" method="get" id="searchForm">
					<div class="col-sm-3"></div>
					<div class="col-sm-2">
						<input type="text" name="recommendNumber"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("编号")}" value="${siteRecommend.recommendNumber}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("名称")}" value="${siteRecommend.name}"/>
					</div>
					<div class="col-sm-2">
						<select name="type" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择类型")}--</option>
							<c:forEach items="${fns:getDictList('site_recommend_type')}" var="item">
								<option value="${item.value}" ${item.value==siteRecommend.type?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
					<thead>
						<tr>
							<th>${fns:fy("编号")}</th>
							<th>${fns:fy("推荐位名称")}</th>
							<th>${fns:fy("推荐位说明")}</th>
							<th>${fns:fy("是否开启")}</th>
							<th>${fns:fy("推荐类型")}</th>
							<th>${fns:fy("排序")}</th>
							<th>${fns:fy("创建时间")}</th>
							<th>${fns:fy("操作")}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="siteRecommend">
						<tr>
							<td><a href="${ctxa}/site/siteRecommend/edit1.do?recommendId=${siteRecommend.recommendId}">${siteRecommend.recommendNumber}</a></td>
							<td>${siteRecommend.name}</td>
							<td>
								<textarea class="form-control input-sm" rows="2" readonly="readonly">${siteRecommend.info}</textarea>
							</td>
							<td>${fns:getDictLabel(siteRecommend.isOpen, 'is_open', '')}</td>
							<td>${fns:getDictLabel(siteRecommend.type, 'site_recommend_type', '')}</td>
							<td>${siteRecommend.sort}</td>
							<td><fmt:formatDate value="${siteRecommend.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<shiro:hasPermission name="site:siteRecommendItem:save">
								<button class="btn btn-info btn-sm recommend" type="${siteRecommend.type}" recommendId="${siteRecommend.recommendId}">
									<i class="fa fa-edit"></i> ${fns:fy("推荐")}
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="site:siteRecommend:edit">
								<a class="btn btn-info btn-sm" href="${ctxa}/site/siteRecommend/edit1.do?recommendId=${siteRecommend.recommendId}">
									<i class="fa fa-edit"></i> ${fns:fy("编辑")}
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="site:siteRecommend:drop">
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteRecommend/delete.do?recommendId=${siteRecommend.recommendId}">
									<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
								</button>
								</shiro:hasPermission>
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