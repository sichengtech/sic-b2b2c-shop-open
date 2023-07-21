<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("商家相册图片管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAlbumPictureList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("商家相册图片列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeAlbumSpace/list.do"> <i class="fa fa-user"></i> ${fns:fy("商家相册列表")}</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("商家相册图片列表")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.商家相册.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<c:if test="${empty page.list}">
				${fns:fy("无数据")}
			</c:if>
			<c:if test="${not empty page.list}">
				<!-- 图片列表开始 -->
				<div class="media-gal">
					<c:forEach items="${page.list}" var="storeAlbumPicture">
						<div class="col-sm-3">
							<div class="images item" >
								<a target=_blank href="${ctxfs}${storeAlbumPicture.path}">
									<img src="${ctxfs}${storeAlbumPicture.path}" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
								</a>
								<p>${fns:fy("上传时间")}：<fmt:formatDate value="${storeAlbumPicture.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
								<p>${fns:fy("原图尺寸")}：${storeAlbumPicture.pixel}</p>
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- 图片列表结束 -->
				<!-- 分页信息开始 -->
				<%@ include file="../include/page.jsp"%>
				<!-- 分页信息结束 -->
			</c:if>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>