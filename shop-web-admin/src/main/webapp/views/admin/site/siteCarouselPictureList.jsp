<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("网站轮播图片管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteCarouselPictureList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("网站轮播图片列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("网站轮播图片列表")}</a></li>
				<shiro:hasPermission name="site:siteCarouselPicture:save"><li class=""><a href="${ctxa}/site/siteCarouselPicture/save1.do" > <i class="fa fa-user"></i> ${fns:fy("网站轮播图片添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.轮播图片.操作提示1")}</li>
					<li>${fns:fy("网站设置.轮播图片.操作提示2")}</li>
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
						<shiro:hasPermission name="site:siteCarouselPicture:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteCarouselPicture/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteCarouselPicture/list.do" method="get" id="searchForm">
					<div class="col-xs-3">
					</div>
					<div class="col-xs-2">
						<input type="text" name="title" maxlength="64" class="form-control input-sm" placeholder="${fns:fy("标题")}" value="${siteCarouselPicture.title}"/>
					</div>
					<div class="col-sm-2">
						<select name="status" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择状态")}--</option>
							<c:forEach items="${fns:getDictList('is_status')}" var="item">
							<option value="${item.value}" ${item.value==siteCarouselPicture.status?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<select name="type" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择类型")}--</option>
							<c:forEach items="${fns:getDictList('carousel_picture_type')}" var="item">
							<option value="${item.value}" ${item.value==siteCarouselPicture.type?"selected":""}>${item.label}</option>
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
						<th>${fns:fy("图片")}</th>
						<th>${fns:fy("目标连接")}</th>
						<th>${fns:fy("标题")}</th>
						<th>${fns:fy("排序")}</th>
						<th>${fns:fy("状态")}</th>
						<th>${fns:fy("类型")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="siteCarouselPicture">
					<tr>
						<td>
							<c:if test="${siteCarouselPicture.path !=null}">
							 <a href="${ctxfs}${siteCarouselPicture.path}" target="${fns:getDictLabel(siteCarouselPicture.target, 'target', '')}">
								<image src="${ctxfs}${siteCarouselPicture.path}@190x75"></image>
							 </a>
							</c:if>
							<c:if test="${siteCarouselPicture.path == null}">${fns:fy("无")}</c:if>
						</td>
						<td>${siteCarouselPicture.url}</td>
						<td>${siteCarouselPicture.title}</td>
						<td>${siteCarouselPicture.sort}</td>
						<td>${fns:getDictLabel(siteCarouselPicture.status, 'is_status', '')}</td>
						<td>${fns:getDictLabel(siteCarouselPicture.type, 'carousel_picture_type', '')}(${siteCarouselPicture.type})</td>
						<td>
							<shiro:hasPermission name="site:siteCarouselPicture:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteCarouselPicture/edit1.do?id=${siteCarouselPicture.id}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteCarouselPicture:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteCarouselPicture/delete.do?id=${siteCarouselPicture.id}">
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