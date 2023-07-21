<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商家注册信息管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userMerchantInfoForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userMerchantInfo.UId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商家注册信息</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userMerchantInfo/list.do"> <i class="fa fa-user"></i> 商家注册信息列表</a></li>
				<shiro:hasPermission name="sso:userMerchantInfo:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商家注册信息${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商家注册信息管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userMerchantInfo/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userMerchantInfo.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键,会员的ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${userMerchantInfo.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键,会员的ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 企业类型（字典）&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('use_shop_type')}" var="item">
									<option value="${item.value}" ${item.value==userMerchantInfo.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写企业类型（字典）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 行业属性（字典）&nbsp;:</label>
							<div class="col-sm-5">
								<select name="industry" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('user_industry')}" var="item">
									<option value="${item.value}" ${item.value==userMerchantInfo.industry?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写行业属性（字典）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司介绍&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="introduce"  maxlength="512" class="form-control input-sm" value="${userMerchantInfo.introduce}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司介绍<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${userMerchantInfo.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${userMerchantInfo.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${userMerchantInfo.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司所在地县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${userMerchantInfo.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司所在地县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="128" class="form-control input-sm" value="${userMerchantInfo.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 客服电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="customCall"  maxlength="32" class="form-control input-sm" value="${userMerchantInfo.customCall}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写客服电话<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司网址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyWebsite"  maxlength="32" class="form-control input-sm" value="${userMerchantInfo.companyWebsite}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司网址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 公司品牌&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyBrand"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.companyBrand}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写公司品牌<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 所在部门&nbsp;:</label>
							<div class="col-sm-5">
								<select name="department" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('user_department')}" var="item">
									<option value="${item.value}" ${item.value==userMerchantInfo.department?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写所在部门<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 联系人&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contacts"  maxlength="32" class="form-control input-sm" value="${userMerchantInfo.contacts}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写联系人<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 联系人电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contactsTelephone"  maxlength="64" class="form-control input-sm" value="${userMerchantInfo.contactsTelephone}"/>

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
								<shiro:hasPermission name="sso:userMerchantInfo:edit">
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