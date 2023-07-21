<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品区间价管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSectionPriceForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productSectionPrice.PId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商品区间价</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productSectionPrice/list.do"> <i class="fa fa-user"></i> 商品区间价列表</a></li>
				<shiro:hasPermission name="product:productSectionPrice:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商品区间价${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品区间价管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productSectionPrice/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pId" value="${productSectionPrice.PId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId"  maxlength="19" class="form-control input-sm" value="${productSectionPrice.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 价格区间&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="section"  maxlength="64" class="form-control input-sm" value="${productSectionPrice.section}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写价格区间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 批发价格&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="price"  maxlength="12" class="form-control input-sm" value="${productSectionPrice.price}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写批发价格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${productSectionPrice.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productSectionPrice:edit">
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