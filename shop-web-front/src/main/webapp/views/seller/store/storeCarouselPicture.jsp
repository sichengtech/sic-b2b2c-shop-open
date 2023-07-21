<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺幻灯片')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/fdp/upload.js"></script>
<script type="text/javascript" src="${ctx}/views/seller/store/storeCarouselPicture.js"></script>
<style type="text/css">
	.sui-msg.msg-block{margin:10px!important;}
	.fileUploadClass{width:165px!important;}
</style>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('店铺幻灯片')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${fns:fy('店铺幻灯片')}</li>
				</ul>
			</dt>
			<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
					<div class="msg-con">
						 <ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('设置店铺幻灯片，幻灯片内容将出现在店铺主页上。')}</li>
							<li>${fns:fy('建议上传1920*500像素的图，避免导致图片不清晰。')}</li>
						 </ul>
					 </div>
					 <sys:message content="${message}"/>
					 <s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</address>
			<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/store/storeCarouselPicture/save2.htm">
				<dd>
					<div class="grid-demo">
						 <div class="sui-row-fluid">
						 <c:forEach items="${list}" var="storeCarouselPicture" varStatus = "s">
						 	<div class="focus-manage">
						 		<input type="hidden" class="existSize_${s.index}" value="${not empty storeCarouselPicture.picturePath?'1':'0'}"/>
						 		<div class="result_${s.index}"></div>
						 		<div class="thumbnail">
							 		<div class="uploadImgDiv" id="img_${s.index}_0">
							            <input type="hidden" class="imgPath" name="picturePath" value="${storeCarouselPicture.picturePath}"/>
							            <div class="result"></div>
							            <img class="preview" src="${ctxfs}${storeCarouselPicture.picturePath}@565x180" onerror="fdp.defaultImage('${ctxStatic}/sicheng-seller/images/nopreview.png');" width="565"/>
							        </div>
							    </div>
								<div class="docs">
									<label>${fns:fy('上传：')}</label>
									<input id="fileUpload_${s.index}" name="fileUpload" type="file" thumbnail="565x180" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="${s.index}" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="input-large fileUploadClass"/>
									<a href="javaScript:void(0);" class="sui-btn btn-bordered btn-danger clean" attr = "${s.index}">${fns:fy('清除')}</a>
								</div>
								<div class="docs">
									 <label>${fns:fy('链接地址：')}</label>
									 <input type="text" name="url" value="${storeCarouselPicture.url}" class="input-large">
								</div>
								<div class="docs">
									 <label>${fns:fy('排')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${fns:fy('序：')}</label>
									 <input type="text" name="sort" value="${storeCarouselPicture.sort}" class="input-large">
								</div>
								<div class="clear"></div>
							</div>
						 </c:forEach>
						 </div>
						 <shiro:hasPermission name="store:storeCarouselPicture:edit">
						 <div class="text-align pb20">
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
						</div>
						</shiro:hasPermission>
					 </div>
				</dd>
			</form>
		</dl>
		</div>
	</div>
</body>
</html>