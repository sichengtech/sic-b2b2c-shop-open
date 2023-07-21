<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>采购商管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userPurchaseForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userPurchase.UId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}采购商</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userPurchase/list.do"> <i class="fa fa-user"></i> 采购商列表</a></li>
				<shiro:hasPermission name="sso:userPurchase:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 采购商${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>采购商管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userPurchase/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userPurchase.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员的ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${userPurchase.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写会员的ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 审核状态：0待审、1通过、2未通过&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="authType"  maxlength="2" class="form-control input-sm" value="${userPurchase.authType}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写审核状态：0待审、1通过、2未通过<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${userPurchase.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业类型（字典）use_shop_type&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('use_shop_type')}" var="item">
									<option value="${item.value}" ${item.value==userPurchase.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业类型（字典）use_shop_type<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业属性（字典）user_industry&nbsp;:</label>
							<div class="col-sm-5">
								<select name="industry" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('user_industry')}" var="item">
									<option value="${item.value}" ${item.value==userPurchase.industry?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业属性（字典）user_industry<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 营业执照path&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="businesslicense"  maxlength="64" class="form-control input-sm" value="${userPurchase.businesslicense}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写营业执照path<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业介绍&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="introduce"  maxlength="255" class="form-control input-sm" value="${userPurchase.introduce}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业介绍<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${userPurchase.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${userPurchase.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${userPurchase.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${userPurchase.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${userPurchase.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${userPurchase.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业所在地县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${userPurchase.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业所在地县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${userPurchase.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" value="${userPurchase.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 联系人&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contacts"  maxlength="64" class="form-control input-sm" value="${userPurchase.contacts}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写联系人<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 联系人电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contactsTelephone"  maxlength="64" class="form-control input-sm" value="${userPurchase.contactsTelephone}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写联系人电话<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sso:userPurchase:edit">
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