<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('相册管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeAlbumList.js"></script>
<style type="text/css">
	.sui-msg.msg-block{margin-left:10px!important;margin-right:10px!important;margin-top:-10px!important;margin-bottom:-10px!important;}
</style>
</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('相册管理')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li><a href="#">${fns:fy('首页')}</a>
					<li><a href="${ctxs}/store/storeAlbum/info.htm">${fns:fy('图片空间')}</a></li>
					<li class="active">${fns:fy('相册管理')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
				 <div class="msg-con">
					 <ul>
						<h4>${fns:fy('提示信息')}</h4>
						<li>${fns:fy('显示当前店铺的所有图片相册。')}</li>
						<li>${fns:fy('可以添加、编辑、删除相册,点击"保存"按钮后生效。')}</li>
					 </ul>
				 </div>
				 <s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<dd class="table-css">
				<shiro:hasPermission name="store:storeAlbum:edit">
				<div class="pull-right">
				 	<a id="addRow" href="javaScript:void(0);" class="sui-btn btn-large btn-primary m16">${fns:fy('新增相册')}</a>
				</div>
				</shiro:hasPermission>
				<sys:message content="${message}"/>
				<form id="inputForm" action="${ctxs}/store/storeAlbum/save.htm" method="post">
					<table id="tab" class="sui-table table-bordered-simple sui-form" id="tab">
					 <thead>
						 <tr colspan="2">
							<th width="20%" class="center">${fns:fy('排序')}</th>
							<th width="40%" class="center">${fns:fy('相册名字')}</th>
							<th width="10%" class="center">${fns:fy('图片数量')}</th>
							<th width="30%" class="center">${fns:fy('管理操作')}</th>
						 </tr>
					 </thead>
					 <tbody id="tbody">
					 	<c:forEach items="${page.list}" var="storeAlbum">
					 		<tr colspan="2">
					 			<td width="20%" class="center">
					 				<input type="text" class="input-small" name="sort" value="${storeAlbum.sort}" maxlength="5">
					 			</td>
					 			<td width="30%" class="center">
						 			<input type="hidden" class="input-small" name="albumId" value="${storeAlbum.albumId}" maxlength="5">
						 			<input type="text" class="input-large" name="albumName" value="${storeAlbum.albumName}" maxlength="10">
						 		</td>
					 			<td width="20%" class="center">${storeAlbum.pictureCount}</td>
					 			<td width="30%" class="center">
					 				<shiro:hasPermission name="store:storeAlbum:edit">
									<button type="button" href="${ctxs}/store/storeAlbum/delete.htm?albumId=${storeAlbum.albumId}" class="sui-btn btn-large btn-danger delButton">
										<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
									</button>
									</shiro:hasPermission>
								</td>
					 		</tr>		
					 	</c:forEach>
					 </tbody>
					</table>
					<div class="clear"></div>
					<div class="text-align pb20">
						<shiro:hasPermission name="store:storeAlbum:edit">
						<a href="${ctxs}/store/storeAlbum/info.htm" class="sui-btn btn-xlarge btn-primary">${fns:fy('返回')}</a> 
						<button type="submit" class="sui-btn btn-xlarge btn-primary ml20">${fns:fy('保存')}</button>
						</shiro:hasPermission>
					</div>
				</form>
			</dd>
		</dl>
	</div>
	</div>
 </div>
 <!-- 隐藏的开始 -->
<table style="display: none;">
	<tbody class="addLine">
		 <tr colspan="2">
 			<td width="20%" class="center">
 				<input type="text" class="input-small" name="sort" value="10" maxlength="5">
 			</td>
 			<td width="30%" class="center">
 				<input type="hidden" class="input-small" name="albumId" value="" maxlength="5">
 				<input type="text" class="input-large" name="albumName" maxlength="10">
 			</td>
 			<td width="20%" class="center">0</td>
 			<td width="30%" class="center">
 				<shiro:hasPermission name="store:storeAlbum:edit">
				<button type="button" class="sui-btn btn-large btn-danger delButton">
					<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
				</button>
				</shiro:hasPermission>
			</td>
 		</tr>
	</tbody>
</table>
<!-- 隐藏的行结束 -->
</body>
</html>