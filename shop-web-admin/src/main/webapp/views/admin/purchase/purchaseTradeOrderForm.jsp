<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>撮合交易订单管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeOrderForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseTradeOrder.purchaseTradeId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}撮合交易订单</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseTradeOrder/list.do"> <i class="fa fa-user"></i> 撮合交易订单列表</a></li>
				<shiro:hasPermission name="purchase:purchaseTradeOrder:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 撮合交易订单${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>撮合交易订单管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseTradeOrder/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="purchaseTradeId" value="${purchaseTradeOrder.purchaseTradeId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 订单id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseTradeId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrder.purchaseTradeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写订单id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购方&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseUId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrder.purchaseUId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购方<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 报价方&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerUId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrder.offerUId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写报价方<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrder.purchaseId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseItemId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrder.purchaseItemId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购内容&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseContent"  maxlength="2000" class="form-control input-sm" value="${purchaseTradeOrder.purchaseContent}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购内容<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 报价内容&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerContent"  maxlength="2000" class="form-control input-sm" value="${purchaseTradeOrder.offerContent}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写报价内容<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 总价格&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="price"  maxlength="12" class="form-control input-sm" value="${purchaseTradeOrder.price}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写总价格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 报价备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerRemarks"  maxlength="2000" class="form-control input-sm" value="${purchaseTradeOrder.offerRemarks}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写报价备注<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="2" class="form-control input-sm" value="${purchaseTradeOrder.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="purchase:purchaseTradeOrder:edit">
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