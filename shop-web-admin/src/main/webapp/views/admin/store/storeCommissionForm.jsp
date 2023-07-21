<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<style type="text/css">
</style>
</head>
<body style="background: #f5f5f5;">
	<!-- 开始店铺佣金模态窗口 -->
	<div class="modal-content">
		<div class="modal-body form-horizontal adminex-form">
			<div class="form-group">
				<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
				 <li class="m-b-3"> ${fns:fy("给店铺进行绑定佣金")}</li>
				</ul>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">${fns:fy("店铺名称")}：</label>
				<div class="col-sm-8" id="storeName" name="storeName" style="display: inline;">${store.name}</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">${fns:fy("佣金比例")}：</label>
				<div class="col-sm-3" style="display: inline-table;vertical-align: middle;">
					<div class="input-group">
						<input type="text" id="commission" name="commission" class="form-control input-sm" placeholder="" value="${store.commission}"/>
						<div class="input-group-addon">%</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 结束店铺佣金模态窗口 -->
	<%-- 引入脚部文件 --%>
	<%@ include file="../include/foot.jsp"%>
</body>
</html>