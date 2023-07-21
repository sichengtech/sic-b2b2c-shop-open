<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('上传图片')}</title>
<%@ include file="../include/head.jsp"%>
<!-- jquery-ui -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ui-1.10.3.min.js"></script>
<!--webuploader上传-->
<script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/third-party/webuploader/webuploader.js"></script>
<!-- jquery-ztree js -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productImgUpload.js"></script>
<script type="text/javascript" src="${ctx}/views/seller/product/webUploaderProduct.js"></script>

<!--webuploader上传-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/baiduUEditor1.4.3.2/third-party/webuploader/webuploader.css"/>
<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
<!-- 本页面css -->
<link rel="stylesheet" type="text/css" href="${ctx}/views/seller/product/productImgUpload.css"/>

<script type="text/javascript">
	//商家相册
	var zNodes=[<c:forEach items="${albumList}" var="album">{id:"${album.albumId}", pId:"0", name:"${album.albumName}",isParent:false},
		</c:forEach>];
</script>
</head>
<body>
	<div class="nc-plus-album list" id="ncscPictureApp">
		<!-- tab开始 -->
		<ul class="tab-nav">
			<li class="upload-tab-title">
		    	<a href="javascript:" id="showUploadPanelBtn">${fns:fy('上传新图片')}</a>
			</li>
			<li class="area-tab-title file-tab active">
				<a href="javascript:" id="showAlbumPanelBtn">${fns:fy('从图片空间选择')}</a>
			</li>
		</ul>
		<!-- tab结束 -->
		<!-- 图片目录开始 -->
		<div class="tree-wrap" id="treeWrap"><i class="arrow-top"></i>
			<div class="tree-title">${fns:fy('图片目录')}</div>
			<input type="hidden" name="albumId" class="albumId"/>
			<div class="mod-folder-tree">
				<ul class="ztree" id="zTreeElement">
				</ul>
			</div>
		</div>
		<!-- 图片目录结束 -->
		<!-- 中间区域开始 -->
		<div class="tab-content">
			<!-- 上传tab-content开始 -->
			<div class="upload-container none">
				<div class="upload-title">
					${fns:fy('上传后的图片将存入图片空间中，当前目录：')}
					<span class="current-folder" id="currentFolder">${fns:fy('图片空间')}</span>
				</div>
				<div class="upload-table-container">
					<div id="uploader" class="wu-example">
					    <div class="queueList">
					        <div id="dndArea" class="placeholder">
					            <div id="filePicker"></div>
					            <div class="upload-info">
									<p>${fns:fy('提示：文件大小请在')}<strong>5M</strong>${fns:fy('以内,')}</p>
						            <p>${fns:fy('可通过截屏将图片粘贴到这里')}</p>
						            <p>${fns:fy('可将照片拖到这里，单次最多可选300张')}</p>
									<p>${fns:fy('移动端尺寸为')}<strong>${fns:fy('宽度480-1242之间，高度小于等于1546')}</strong>${fns:fy('，仅支持')}<strong>JPG、PNG、BMP、JPEG</strong>${fns:fy('格式；')}
								</div>
					        </div>
					    </div>
					</div>
					<!-- <div class=""><i class="sui-icon icon-picture"></i></div>
					<div class="btn-upload-box" id="filePicker">
						<span><input type="file" name="file" id="pickfiles" class="input-file" hidefocus="true" multiple/></span>
						<a href="javascript:;" class="btn btn-white"> <i class="sui-icon icon-upload-alt" aria-hidden="true"></i>点击上传</a>
					</div>
					<div id="html5_1ahvgo4cp1sbc1kus3d181t152c3_container" class="moxie-shim moxie-shim-html5" style="position: absolute; top: 0px; left: 0px; width: 0px; height: 0px; overflow: hidden; z-index: 0;">
						<input id="html5_1ahvgo4cp1sbc1kus3d181t152c3" type="file" style="font-size: 999px; opacity: 0; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;" multiple accept="image/jpeg,image/gif,image/png">
					</div>
					<div class="upload-info">
						<p>提示：文件大小请在<strong>3M</strong>以内,</p>
						<p>移动端尺寸为<strong>宽度480-1242之间，高度小于等于1546</strong>，仅支持<strong>JPG、GIF、PNG</strong>格式；
						<p>建议上传无线详情图片宽度<strong>750px</strong> 以上，效果更佳</p>
					</div> -->
				</div>
			</div>
			<!-- 上传tab-content结束 -->
			<!-- 相册空间tab-content开始 -->
		    <div class="area-container">
		        <div class="mod-container" id="modContainer">
					<!-- 分页图标开始 -->
					<div class="search-container">
						<div class="mod-pagination">
							<a id="mediaIconfontPrev" href="javascript:" data-page="1" class="prev"><i class="sui-icon icon-angle-left" aria-hidden="true"></i></a>
							<a id="mediaIconfontNext" href="javascript:" data-page="2" class="next"><i class="sui-icon icon-angle-right" aria-hidden="true"></i></a>
						</div>
						<input type="hidden" name="pageNo" value="0"/>
						<input type="hidden" name="albumId" value=""/>
					</div>
					<!-- 分页图标结束 -->
		            <ul class="mod-list-title">
		                <li class="mod-img">
		                    <a href="javascript;">${fns:fy('预览')}</a>
		                    <span class="file-name">${fns:fy('文件名')}</span>
		                    <span class="file-size">${fns:fy('大小')}</span>
		                    <span class="file-checkbox">${fns:fy('选择')}</span>
		                </li>
		            </ul>
		            <ul class="search-result" id="searchResult">
		            </ul>
		        </div>
		    </div>
		    <!-- 相册空间tab-content结束 -->
		</div>
		<!-- 中间区域结束 -->
		<!-- 已选图片开始 -->
		<div class="selected-pic-container">
			<div class="selected-pic-title">
				${fns:fy('已选')}<span class="selected-pic-num" id="selectedPicNum">0</span>${fns:fy('张择图片(拖动可修改插入顺序)')}
				<span class="some-tips">${fns:fy('注意：尺寸过大时会做移动端自动适配，图片适配可能会导致一定失真')}</span>
				<span class="some-error">${fns:fy('红框的图片不能适配到宽度480-1242px之间,高度小于等于1546px,请修改')}</span>
			</div>
		    <div class="mod-selected" id="modSelected">
		        <ul class="ui-sortable"></ul>
		    </div>
		</div>
		<!-- 已选图片结束 -->
	</div>
	<script type="text/template" id="imgTpl1">
		<li class="mod-img" data-index="{{d.id}}">
			<a title="${fns:fy('上传时间:')}{{d.date}}">
	        	<img src="${ctxfs}{{d.path}}@!75X75" src1="{{d.path}}" style="width:75px;height:75px;"
				onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
	            <i class="sui-icon icon-ok-sign" aria-hidden="false"></i>
	            <span class="pixel">{{d.pixel}}</span>
			</a>
		</li>
	</script>
	<script type="text/template" id="imgTpl2">
		<li class="mod-img" id="{{d.fileId}}" data-index="{{d.id}}">
			<a title="${fns:fy('上传时间:')}">
				<img src="{{d.path}}" src1="{{d.src1}}">
				<i class="sui-icon icon-tb-delete" aria-hidden="true"></i>
				<span class="pixel">{{d.pixel}}</span>
			</a>
			<p class="imgWrap"></p>
			<p class="progress"><span></span></p>
		</li>
	</script>
	<script type="text/template" id="emptyImgTpl">
		<li class="empty-data">{{d.content}}</li>
	</script>
</body>

</html>