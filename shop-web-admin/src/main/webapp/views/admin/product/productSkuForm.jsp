<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品SKU管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSkuForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productSku.skuId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商品SKU</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productSku/list.do"> <i class="fa fa-user"></i> 商品SKU列表</a></li>
				<shiro:hasPermission name="product:productSku:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商品SKU${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品SKU管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productSku/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="skuId" value="${productSku.skuId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> SKU ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="skuId"  maxlength="19" class="form-control input-sm" value="${productSku.skuId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写SKU ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品ID(SPU)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId"  maxlength="19" class="form-control input-sm" value="${productSku.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品ID(SPU)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${productSku.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 是否无规格&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isNotSpec" value="${item.value}" ${item.value==productSku.isNotSpec?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写是否无规格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec1"  maxlength="64" class="form-control input-sm" value="${productSku.spec1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格1值&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec1V"  maxlength="64" class="form-control input-sm" value="${productSku.spec1V}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格1值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格2&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec2"  maxlength="64" class="form-control input-sm" value="${productSku.spec2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格2值&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec2V"  maxlength="64" class="form-control input-sm" value="${productSku.spec2V}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格2值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格3&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec3"  maxlength="64" class="form-control input-sm" value="${productSku.spec3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格3<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格3值&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec3V"  maxlength="64" class="form-control input-sm" value="${productSku.spec3V}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格3值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格4&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec4"  maxlength="64" class="form-control input-sm" value="${productSku.spec4}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格4<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 规格4值&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="spec4V"  maxlength="64" class="form-control input-sm" value="${productSku.spec4V}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写规格4值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 零售价格&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="price"  maxlength="12" class="form-control input-sm" value="${productSku.price}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写零售价格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 库存&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="stock"  maxlength="10" class="form-control input-sm" value="${productSku.stock}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写库存<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 商家编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sn"  maxlength="64" class="form-control input-sm" value="${productSku.sn}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写商家编号<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productSku:edit">
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