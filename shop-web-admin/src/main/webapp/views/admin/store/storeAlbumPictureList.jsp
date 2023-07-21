<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商家相册图片管理</title>
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
			<h4 class="title">商家相册图片列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 商家相册图片列表</a></li>
				<shiro:hasPermission name="store:storeAlbumPicture:edit"><li class=""><a href="${ctxa}/store/storeAlbumPicture/save1.do" > <i class="fa fa-user"></i> 商家相册图片添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>可以查看店铺所有上传过的图片。</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="store:storeAlbumPicture:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeAlbumPicture/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeAlbumPicture/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="pictureId" maxlength="19" class="form-control input-sm" placeholder="主键" value="${storeAlbumPicture.pictureId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="sellerId" maxlength="19" class="form-control input-sm" placeholder="关联(卖家表)" value="${storeAlbumPicture.sellerId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="albumId" maxlength="19" class="form-control input-sm" placeholder="store_album(相册夹表)" value="${storeAlbumPicture.albumId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="path" maxlength="255" class="form-control input-sm" placeholder="图片的存储路径" value="${storeAlbumPicture.path}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pixel" maxlength="64" class="form-control input-sm" placeholder="原图像素，例如：200x300，单位px" value="${storeAlbumPicture.pixel}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="fileSize" maxlength="10" class="form-control input-sm" placeholder="文件大小，单位byte" value="${storeAlbumPicture.fileSize}"/>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAlbumPicture.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAlbumPicture.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>主键</th>
						<th>关联(卖家表)</th>
						<th>store_album(相册夹表)</th>
						<th>图片的存储路径</th>
						<th>原图像素，例如：200x300，单位px</th>
						<th>文件大小，单位byte</th>
						<th>创建时间、上传时间</th>
						<shiro:hasPermission name="store:storeAlbumPicture:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeAlbumPicture">
					<tr>
						<td><a href="${ctxa}/store/storeAlbumPicture/edit1.do?pictureId=${storeAlbumPicture.pictureId}">${storeAlbumPicture.pictureId}</a></td>
						<td>${storeAlbumPicture.sellerId}</td>
						<td>${storeAlbumPicture.albumId}</td>
						<td>${storeAlbumPicture.path}</td>
						<td>${storeAlbumPicture.pixel}</td>
						<td>${storeAlbumPicture.fileSize}</td>
						<td><fmt:formatDate value="${storeAlbumPicture.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeAlbumPicture:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeAlbumPicture/edit1.do?pictureId=${storeAlbumPicture.pictureId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeAlbumPicture/delete.do?pictureId=${storeAlbumPicture.pictureId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
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