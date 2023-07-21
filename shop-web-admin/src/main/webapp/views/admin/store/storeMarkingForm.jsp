<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeMarkingForm.js"></script>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<style type="text/css">
.preview{margin-left: -183px;}
.result{margin-left: -183px;}
</style>
</head>
<body style="background: #f5f5f5;">
	<!-- 开始打标模态窗口 -->
	<div class="modal-content">
		<form id="storeMarkingForm" action="${ctxa}/store/store/storeMarkingSave2.do" method="post"> 
			<div class="modal-body form-horizontal adminex-form">
				<div class="form-group">
					<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
					 <li class="m-b-3"> ${fns:fy("设置店铺是否缴纳保证金")}</li>
					</ul>
				</div>
				<input type="hidden" id="storeId" name="storeId" value="${store.storeId}"/>
				<div class="form-group">
					<label class="col-sm-4 control-label text-right">${fns:fy("店铺名称")}：</label>
					<div class="col-sm-8" id="storeName" name="storeName" style="display: inline;">${store.name}</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-4 control-label text-right">${fns:fy("保证金")}：</label>
					<div class="col-sm-8" style="margin-top: -28px;margin-left: 90px;">
						<select name="markingImgPath" class="form-control m-bot15 input-sm imgPath">
							<option value="1" ${store.markingImgPath eq 1 ? "" :"selected"}>${fns:fy("缴纳")}</option>
							<option value="0" ${store.markingImgPath eq 0 ? "selected" :""}>${fns:fy("未缴纳")}</option>
						</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- 结束打标模态窗口 -->
	<%-- 引入脚部文件 --%>
	<%@ include file="../include/foot.jsp"%>
</body>
</html>