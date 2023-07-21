<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>收藏商品管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberCollectionProductForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty memberCollectionProduct.collectionId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}收藏商品</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/member/memberCollectionProduct/list.do"> <i class="fa fa-user"></i> 收藏商品列表</a></li>
				<shiro:hasPermission name="member:memberCollectionProduct:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 收藏商品${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>收藏商品管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/member/memberCollectionProduct/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="collectionId" value="${memberCollectionProduct.collectionId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="collectionId"  maxlength="19" class="form-control input-sm" value="${memberCollectionProduct.collectionId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(会员表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="mId"  maxlength="19" class="form-control input-sm" value="${memberCollectionProduct.MId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(会员表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(商品SPU表)(SPU级别)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId"  maxlength="19" class="form-control input-sm" value="${memberCollectionProduct.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(商品SPU表)(SPU级别)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 封面图path，冗余&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="image"  maxlength="64" class="form-control input-sm" value="${memberCollectionProduct.image}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写封面图path，冗余<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pictureName"  maxlength="64" class="form-control input-sm" value="${memberCollectionProduct.pictureName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品价格(取SKU中最低价)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="picturePrice"  maxlength="12" class="form-control input-sm" value="${memberCollectionProduct.picturePrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品价格(取SKU中最低价)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="member:memberCollectionProduct:edit">
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