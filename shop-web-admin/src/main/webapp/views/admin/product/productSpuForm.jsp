<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSpuForm.js"></script>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productSpu.PId?true:false}"></c:set > 
			<h4 class="title">${isEdit?'编辑':'添加'}商品</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productSpu/list.do"> <i class="fa fa-user"></i> 商品列表</a></li>
				<shiro:hasPermission name="${isEdit?'product:productSpu:edit':'product:productSpu:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商品${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>上架状态分为：出售中、仓库中和禁售三种状态。</li>
					<li>审核状态分为：审核通过、等待审核和审核失败三种状态。</li>
					<li>商品只有在审核通过并且出售中的商品才能正常出售。商品是否需审核可以在“商品设置”中由管理员进行设置。</li>
					<li>禁售或审核失败的商品，商家只能重新编辑后才能够进行出售。</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productSpu/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pId" value="${productSpu.PId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ID(SPU)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId" maxlength="19" class="form-control input-sm" value="${productSpu.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写ID(SPU)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name" maxlength="128" class="form-control input-sm" value="${productSpu.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品分类&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="categoryId" maxlength="19" class="form-control input-sm" value="${productSpu.categoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品分类<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 店内分类&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeCategoryId" maxlength="19" class="form-control input-sm" value="${productSpu.storeCategoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写店内分类<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 店铺&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeId" maxlength="19" class="form-control input-sm" value="${productSpu.storeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写店铺<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 卖家&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeId" maxlength="19" class="form-control input-sm" value="${productSpu.storeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写卖家<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 状态&nbsp;:</label>
							<div class="col-sm-5">
								<select name="status" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('product_spu_status')}" var="item">
									<option value="${item.value}" ${item.value==productSpu.status?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写状态<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="image" maxlength="128" class="form-control input-sm" value="${productSpu.image}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 品牌&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="brandId" maxlength="19" class="form-control input-sm" value="${productSpu.brandId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写品牌<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 副标题&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="nameSub" maxlength="255" class="form-control input-sm" value="${productSpu.nameSub}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写副标题<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 计量单位&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="unit" maxlength="12" class="form-control input-sm" value="${productSpu.unit}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写计量单位<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 销售类型&nbsp;:</label>
							<div class="col-sm-5 icheck">
								<c:forEach items="${fns:getDictList('product_sale_type')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<div class="radio ">
											<input type="radio" name="type" value="${item.value}" ${item.value==productSpu.type?"checked":""} style="display: none" data-switch-no-init/>
											<label>${item.label}</label>
										</div>
									</div>
								</div>
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写销售类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 赠品&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="isGift" value="${productSpu.isGift}" ${1==productSpu.isGift?"checked":""} data-size="small" style="display: none" data-on-text="是" data-off-text="否"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写赠品<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否推荐&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="isRecommend" value="${productSpu.isRecommend}" ${1==productSpu.isRecommend?"checked":""} data-size="small" style="display: none" data-on-text="是" data-off-text="否"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否推荐<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 推荐排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="recommendSort" maxlength="10" class="form-control input-sm" value="${productSpu.recommendSort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写推荐排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 市场价&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="marketPrice" class="form-control input-sm" value="${productSpu.marketPrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写市场价<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 赠送积分&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="point" maxlength="64" class="form-control input-sm" value="${productSpu.point}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写赠送积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 重量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="weight" maxlength="12" class="form-control input-sm" value="${productSpu.weight}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写重量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 体积&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="volume" maxlength="12" class="form-control input-sm" value="${productSpu.volume}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写体积<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 发票&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('product_spu_invoice')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<div class="radio ">
											<input type="radio" name="invoice" value="${item.value}" ${item.value==productSpu.invoice?"checked":""} style="display: none" data-switch-no-init/>
											<label>${item.label}</label>
										</div>
									</div>
								</div>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写发票<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 促销活动&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<div class="radio ">
											<input type="radio" name="action" value="${item.value}" ${item.value==productSpu.action?"checked":""} style="display: none" data-switch-no-init/>
											<label>${item.label}</label>
										</div>
									</div>
								</div>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写促销活动<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 运费方式&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('product_express_type')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<div class="radio ">
											<input type="radio" name="expressType" value="${item.value}" ${item.value==productSpu.expressType?"checked":""} style="display: none" data-switch-no-init/>
											<label>${item.label}</label>
										</div>
									</div>
								</div>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写运费方式<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 运费价格&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="expressPrice" class="form-control input-sm" value="${productSpu.expressPrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写运费价格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 运费模板ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ltId" maxlength="19" class="form-control input-sm" value="${productSpu.ltId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写运费模板ID<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="${isEdit?'product:productSpu:edit':'product:productSpu:save'}">
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