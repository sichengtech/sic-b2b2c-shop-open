<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>撮合采购管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseMatchmakingForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseMatchmaking.purchaseMatchmakingId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}撮合采购</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseMatchmaking/list.do"> <i class="fa fa-user"></i> 撮合采购列表</a></li>
				<shiro:hasPermission name="purchase:purchaseMatchmaking:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 撮合采购${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>撮合采购管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseMatchmaking/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="purchaseMatchmakingId" value="${purchaseMatchmaking.purchaseMatchmakingId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 撮合采购id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseMatchmakingId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmaking.purchaseMatchmakingId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写撮合采购id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmaking.purchaseId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseItemId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmaking.purchaseItemId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 订单id(关联trade_order订单表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseTradeId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmaking.purchaseTradeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写订单id(关联trade_order订单表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 内容&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="content" rows="6" class="form-control"  maxlength="2000" >${purchaseMatchmaking.content}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写内容<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员id(供应方id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmaking.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写会员id(供应方id)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 10待采购   20已采购&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="2" class="form-control input-sm" value="${purchaseMatchmaking.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写10待采购   20已采购<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="purchase:purchaseMatchmaking:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>