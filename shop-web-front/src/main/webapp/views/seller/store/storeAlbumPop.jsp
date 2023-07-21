<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<ul class="sui-nav nav-list">
	<c:forEach items="${storeAlbums}" var="storeAlbum">
		<li class="cli">
			<a href="javaScript:void(0);">
				<input type="hidden" class="albumId" value="${storeAlbum.albumId}"/>
				<i class="sui-icon icon-folder-close"></i>${storeAlbum.albumName}
			</a>
		</li>	
	</c:forEach>
</ul>

