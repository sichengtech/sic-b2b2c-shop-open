<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
</head>
<body>
<div class="sui-row-fluid">
	<input type="hidden" class="pictureSpaceM" value="${storeAlbumSpace.pictureSpaceM}">
	<input type="hidden" class="pictureSpace" value="${storeAlbumSpace.pictureSpace}">
	<input type="hidden" class="totalSpace" value="${storeAlbumSpace.totalSpace}">
	<%-- <input type="text" class="albumId" value="${storeAlbumPicture.albumId}"> --%>
	<ul class="gallery">
		<sys:message content="${message}"/>
		<c:forEach items="${page.list}" var="storeAlbumPicture">
			<li class="noHover" pictureId="${storeAlbumPicture.pictureId}">
				<div class="iconlist">
					<i class="sui-icon icon-touch-folder-right mobile"></i>
					<i class="sui-icon icon-tb-delete deleteSure"></i>
					<i class="sui-icon icon-pc-right"></i>
				</div>
				<input type="hidden" class="pictureId" value="${storeAlbumPicture.pictureId}"> 
				<a class="abc"  href="${ctxfs}${storeAlbumPicture.path}">
				<img src="${ctxfs}${storeAlbumPicture.path}@!135x135" onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');"/>
				</a>
				<p>${fns:fy('添加时间：')}<fmt:formatDate value="${storeAlbumPicture.createDate}" type="both" pattern="yyyy-MM-dd"/></p>
				<p>${fns:fy('原图尺寸：')}${storeAlbumPicture.pixel}</p>
			</li>
		</c:forEach>
	</ul>
</div>
<c:if test="${fn:length(page.list)!=0}">
	<%@ include file="/views/seller/include/storeAlbumPage.jsp"%>
</c:if>
</body>
</html>