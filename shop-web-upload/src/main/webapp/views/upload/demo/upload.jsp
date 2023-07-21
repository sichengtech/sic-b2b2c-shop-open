<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/views/upload/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>上传Demo</title>
<%@ include file="../include/head.jsp"%>
<script src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctxStatic}/fdp/upload.js" type="text/javascript"></script>
</head>
<body>

<div class="form-group">
	<h2> 上传图片测试:（停用）</h2>
	<p>
		<input id="fileUpload" name="fileUpload" type="file"/>
	</p>
	<p>	
		上传结果：<div id="result"></div>
	</p>
	<p>		
		服务器返回的图片路径：<input type="text" id="imgPath" name="headPicPath" value="" style="width:500px;"/>
	</p>
	<p>		
		<img id="picUrlUploadImg" src="" />
	</p>
	<div>
		<p class="help-block">会员头像尺寸要求宽度为120像素、高度为120像素，比例为1:1的图片；支持格式gif，jpg，png。<p>
	</div>
</div>

</body>
</html>