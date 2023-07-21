<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺商品分类管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeCategoryForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeCategory.storeCategoryId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}店铺商品分类</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeCategory/list.do"> <i class="fa fa-user"></i> 店铺商品分类列表</a></li>
				<shiro:hasPermission name="store:storeCategory:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 店铺商品分类${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺商品分类管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeCategory/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="storeCategoryId" value="${storeCategory.storeCategoryId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeCategoryId"  maxlength="19" class="form-control input-sm" value="${storeCategory.storeCategoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(卖家表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" value="${storeCategory.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(卖家表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 父类id（定级父类为0）&nbsp;:</label>
							<div class="col-sm-5">

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写父类id（定级父类为0）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 所有父级id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="parentIds"  maxlength="512" class="form-control input-sm" value="${storeCategory.parentIds}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写所有父级id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 分类名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${storeCategory.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写分类名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 分类层级&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cateLevel"  maxlength="10" class="form-control input-sm" value="${storeCategory.cateLevel}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写分类层级<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${storeCategory.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否开启(0否、1是)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isOpen" value="${item.value}" ${item.value==storeCategory.isOpen?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否开启(0否、1是)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeCategory:edit">
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