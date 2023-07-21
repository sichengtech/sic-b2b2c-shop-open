<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>撮合采购详情管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseMatchmakingItemForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseMatchmakingItem.pmiId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}撮合采购详情</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseMatchmakingItem/list.do"> <i class="fa fa-user"></i> 撮合采购详情列表</a></li>
				<shiro:hasPermission name="purchase:purchaseMatchmakingItem:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 撮合采购详情${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>撮合采购详情管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseMatchmakingItem/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pmiId" value="${purchaseMatchmakingItem.pmiId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 撮合采购详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pmiId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmakingItem.pmiId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写撮合采购详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 撮合采购id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pmId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmakingItem.pmId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写撮合采购id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购单详情id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="purchaseItemId"  maxlength="19" class="form-control input-sm" value="${purchaseMatchmakingItem.purchaseItemId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写采购单详情id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 数量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="amount"  maxlength="10" class="form-control input-sm" value="${purchaseMatchmakingItem.amount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写数量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 报价单价&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerPrice"  maxlength="12" class="form-control input-sm" value="${purchaseMatchmakingItem.offerPrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写报价单价<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 报价备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="offerRemarks"  maxlength="255" class="form-control input-sm" value="${purchaseMatchmakingItem.offerRemarks}"/>

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
								<shiro:hasPermission name="purchase:purchaseMatchmakingItem:edit">
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