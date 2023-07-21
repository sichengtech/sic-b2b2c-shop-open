<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("商家相册管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAlbumSpaceList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("商家相册列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("商家相册列表")}</a></li>
				<%-- <shiro:hasPermission name="store:storeAlbumSpace:edit"><li class=""><a href="${ctxa}/store/storeAlbumSpace/save1.do" > <i class="fa fa-user"></i> 商家相册添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.商家相册.操作提示1")}</li>
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
						<%-- <shiro:hasPermission name="store:storeAlbumSpace:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeAlbumSpace/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<div class="col-sm-7"></div>
				<form action="${ctxa}/store/storeAlbumSpace/list.do" method="get" id="searchForm">
					<div class="col-sm-3">
						<div class="iconic-input right">
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<a href="javaScript:;" class="searchList"><i class="fa fa-search"></i></a>
							<input type="text" name="storeName" class="form-control input-sm pull-right" placeholder="${fns:fy("请输入店铺名称进行搜索")}" value="${storeName}" maxlength="30" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("相册ID")}</th>
						<th>${fns:fy("店铺名称")}</th>
						<th>${fns:fy("图片数量")}</th>
						<th>${fns:fy("相册的数量")}</th>
						<th>${fns:fy("图片占用空间")}</th>
						<th>${fns:fy("图片剩余空间")}</th>
						<th>${fns:fy("图片总空间")}</th>
						<th>${fns:fy("店铺等级")}</th>
						<shiro:hasPermission name="store:storeAlbumSpace:edit"><th>${fns:fy("操作")}</th></shiro:hasPermission>
						<shiro:hasPermission name="store:storeAlbumSpace:view"><th>${fns:fy("操作")}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeAlbumSpace">
					<tr>
						<td>${storeAlbumSpace.albumSpaceId}</td>
						<td>${storeAlbumSpace.store.name}</td>
						<td>${storeAlbumSpace.pictureCount}</td>
						<td>${storeAlbumSpace.albumCount}</td>
						<td>${storeAlbumSpace.pictureSpaceM}</td>
						<td>${storeAlbumSpace.surplusSpaceM}</td>
						<td>${storeAlbumSpace.totalSpaceM}</td>
						<td>${storeAlbumSpace.storeLevel.name}</td>
						<%-- <shiro:hasPermission name="store:storeAlbumSpace:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeAlbumSpace/edit1.do?albumSpaceId=${storeAlbumSpace.albumSpaceId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeAlbumSpace/delete.do?albumSpaceId=${storeAlbumSpace.albumSpaceId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						</td>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="store:storeAlbumSpace:view">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeAlbumSpace/storePictureList.do?albumSpaceId=${storeAlbumSpace.albumSpaceId}">
								<i class="fa fa-eye"></i> ${fns:fy("查看")}
							 </a>
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