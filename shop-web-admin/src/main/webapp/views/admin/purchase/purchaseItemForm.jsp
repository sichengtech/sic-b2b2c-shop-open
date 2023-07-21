<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>采购单明细管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseItemForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseItem.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}采购单明细</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseItem/list.do"> <i class="fa fa-user"></i> 采购单明细列表</a></li>
				<shiro:hasPermission name="purchase:purchaseItem:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 采购单明细${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>采购单明细管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseItem/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${purchaseItem.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseItemId"  maxlength="19" class="form-control input-sm" value="${purchaseItem.purchaseItemId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 采购单id(关联purchaes_order(采购单表))&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseId"  maxlength="19" class="form-control input-sm" value="${purchaseItem.purchaseId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写采购单id(关联purchaes_order(采购单表))<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 会员ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${purchaseItem.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写会员ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 产品名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${purchaseItem.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写产品名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 产品型号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="model"  maxlength="64" class="form-control input-sm" value="${purchaseItem.model}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写产品型号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 产品品牌&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="brand"  maxlength="64" class="form-control input-sm" value="${purchaseItem.brand}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写产品品牌<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 产品封装&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="encapsulation"  maxlength="64" class="form-control input-sm" value="${purchaseItem.encapsulation}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写产品封装<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 产品数量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="amount"  maxlength="10" class="form-control input-sm" value="${purchaseItem.amount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写产品数量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 交货期限&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cycle"  maxlength="64" class="form-control input-sm" value="${purchaseItem.cycle}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写交货期限<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 价格要求&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="priceRequirement"  maxlength="12" class="form-control input-sm" value="${purchaseItem.priceRequirement}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写价格要求<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 地区&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="area"  maxlength="64" class="form-control input-sm" value="${purchaseItem.area}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写地区<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 批号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="batchNumber"  maxlength="64" class="form-control input-sm" value="${purchaseItem.batchNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写批号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 单位&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="unit"  maxlength="64" class="form-control input-sm" value="${purchaseItem.unit}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写单位<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购状态:10.交易中，20.完成交易，30.取消&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="2" class="form-control input-sm" value="${purchaseItem.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购状态:10.交易中，20.完成交易，30.取消<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="purchase:purchaseItem:edit">
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