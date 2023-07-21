<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>撮合交易订单详情管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeOrderItemForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseTradeOrderItem.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}撮合交易订单详情</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseTradeOrderItem/list.do"> <i class="fa fa-user"></i> 撮合交易订单详情列表</a></li>
				<shiro:hasPermission name="purchase:purchaseTradeOrderItem:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 撮合交易订单详情${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>撮合交易订单详情管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseTradeOrderItem/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${purchaseTradeOrderItem.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购订单详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ptoiId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrderItem.ptoiId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购订单详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购订单id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ptoId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrderItem.ptoId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购订单id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseItemId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrderItem.purchaseItemId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 撮合采购详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pmiId"  maxlength="19" class="form-control input-sm" value="${purchaseTradeOrderItem.pmiId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写撮合采购详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 产品名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${purchaseTradeOrderItem.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写产品名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 规格型号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="model"  maxlength="64" class="form-control input-sm" value="${purchaseTradeOrderItem.model}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写规格型号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 品牌&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="brand"  maxlength="64" class="form-control input-sm" value="${purchaseTradeOrderItem.brand}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写品牌<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 数量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="amount"  maxlength="10" class="form-control input-sm" value="${purchaseTradeOrderItem.amount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写数量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 采购单价&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="priceRequirement"  maxlength="12" class="form-control input-sm" value="${purchaseTradeOrderItem.priceRequirement}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写采购单价<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 单位&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="unit"  maxlength="64" class="form-control input-sm" value="${purchaseTradeOrderItem.unit}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写单位<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 采购备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseRemarks"  maxlength="255" class="form-control input-sm" value="${purchaseTradeOrderItem.purchaseRemarks}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写采购备注<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 报价单价&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerPrice"  maxlength="12" class="form-control input-sm" value="${purchaseTradeOrderItem.offerPrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写报价单价<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 报价备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerRemarks"  maxlength="255" class="form-control input-sm" value="${purchaseTradeOrderItem.offerRemarks}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写报价备注<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="purchase:purchaseTradeOrderItem:edit">
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