<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>参数和参数值管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productParamForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productParam.paramId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}参数和参数值</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productParam/list.do"> <i class="fa fa-user"></i> 参数和参数值列表</a></li>
				<shiro:hasPermission name="product:productParam:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 参数和参数值${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>参数和参数值管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productParam/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="paramId" value="${productParam.paramId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paramId"  maxlength="19" class="form-control input-sm" value="${productParam.paramId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 分类ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="categoryId"  maxlength="19" class="form-control input-sm" value="${productParam.categoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写分类ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paramSort"  maxlength="10" class="form-control input-sm" value="${productParam.paramSort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 参数名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${productParam.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写参数名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 参数值文字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paramValues"  maxlength="1024" class="form-control input-sm" value="${productParam.paramValues}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写参数值文字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 参数值图片&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="valuesImg"  maxlength="1024" class="form-control input-sm" value="${productParam.valuesImg}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写参数值图片<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 参数类型&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('product_param_type')}" var="item">
									<option value="${item.value}" ${item.value==productParam.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写参数类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 格式&nbsp;:</label>
							<div class="col-sm-5">
								<select name="format" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('product_param_format')}" var="item">
									<option value="${item.value}" ${item.value==productParam.format?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写格式<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否显示，0否1是&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="isDisplay"  maxlength="1" class="form-control input-sm" value="${productParam.isDisplay}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否显示，0否1是<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否必填，0否1是，商家发布商品的时候必填项必须填写&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="isRequired"  maxlength="1" class="form-control input-sm" value="${productParam.isRequired}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否必填，0否1是，商家发布商品的时候必填项必须填写<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productParam:edit">
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