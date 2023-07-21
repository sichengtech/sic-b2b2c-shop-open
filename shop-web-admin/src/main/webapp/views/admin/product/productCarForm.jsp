<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>车系车型管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCarForm.js"></script>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productCar.carId?true:false}"></c:set > 
			<h4 class="title">${isEdit?'编辑':'添加'}车系车型</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		 	<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productCar/list.do"> <i class="fa fa-home"></i> 车系车型</a></li>
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?'编辑':'添加'}车系车型</a></li>
			</ul>
		</header>
		<div class="panel-body">

			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>上级车型可以任意选择</li>
				</ul>
			</div>
			<!-- 提示 end -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post" action="${ctxa}/product/productCar/save.do">
						<input type="hidden" name="carId" value="${productCar.carId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">上级分类&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="parent" name="parent.id" value="${productCar.parent.id}" labelName="parent.name" labelValue="${productCar.parent.name}"
								title="父类id" url="/product/productCar/treeData.do" extId="${productCar.id}" cssClass="" allowClear="true"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请选择上级<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 车系车型名称&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" name="name" type="text"  maxlength="20" value="${productCar.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="product:productCar:edit">
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
	</section>
	<!-- panel end -->
</body>
</html>