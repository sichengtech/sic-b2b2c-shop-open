<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>入驻申请（业务审核）管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeEnterAuthForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeEnterAuth.enterId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}入驻申请（业务审核）</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeEnterAuth/list.do"> <i class="fa fa-user"></i> 入驻申请（业务审核）列表</a></li>
				<shiro:hasPermission name="store:storeEnterAuth:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 入驻申请（业务审核）${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>点击审核按钮可以对商家入驻申请进行审核。</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeEnterAuth/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="enterId" value="${storeEnterAuth.enterId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 入驻ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="enterId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.enterId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写入驻ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 申请状态&nbsp;:</label>
							<div class="col-sm-5">
								<select name="status" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('store_enter_status')}" var="item">
									<option value="${item.value}" ${item.value==storeEnterAuth.status?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写申请状态<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 入驻信息一审是否完善，0不完善、1完善&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="isPerfect"  maxlength="1" class="form-control input-sm" value="${storeEnterAuth.isPerfect}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写入驻信息一审是否完善，0不完善、1完善<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 公司名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.companyName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写公司名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 公司_详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写公司_详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 公司电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyPhone"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.companyPhone}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写公司电话<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 员工人数&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="staffCount"  maxlength="10" class="form-control input-sm" value="${storeEnterAuth.staffCount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写员工人数<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 注册资金，单位万元&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="registeredCapital"  maxlength="10" class="form-control input-sm" value="${storeEnterAuth.registeredCapital}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写注册资金，单位万元<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 联系人&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contact"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.contact}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写联系人<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 联系电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contactNumber"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.contactNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写联系电话<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> QQ&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="qq"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.qq}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写QQ<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 电子邮箱&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="email"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.email}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写电子邮箱<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 营业执照号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerLicense"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.sellerLicense}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写营业执照号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 营业执照电子版图片路径&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerLicensePath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.sellerLicensePath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写营业执照电子版图片路径<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 法定经营范围&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerScope"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.sellerScope}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写法定经营范围<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 组织机构代码&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="organizationCode"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.organizationCode}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写组织机构代码<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 组织机构代码电子版图片路径&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="organizationCodePath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.organizationCodePath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写组织机构代码电子版图片路径<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 一般人纳税证明电子版图片路径&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="generalTaxpayerPath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.generalTaxpayerPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写一般人纳税证明电子版图片路径<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 银行开户名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="accountName"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.accountName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写银行开户名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 银行账号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankAccount"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.bankAccount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写银行账号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行支行名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankName"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.bankName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行支行名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支行联行号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="branchLineContact"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.branchLineContact}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写支行联行号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankCountryId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.bankCountryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankCountryName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.bankCountryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankProvinceId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.bankProvinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankProvinceName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.bankProvinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankCityId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.bankCityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankCityName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.bankCityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankDistrictId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.bankDistrictId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bankDistrictName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.bankDistrictName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 开户银行许可证电子版&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtImgPath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.districtImgPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写开户银行许可证电子版<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号银行开户名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementAccountName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementAccountName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号银行开户名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号银行账号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementBankAccount"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementBankAccount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号银行账号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号开户银行支行名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementBankName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementBankName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号开户银行支行名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号支行联行号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementBranchLineContact"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementBranchLineContact}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号支行联行号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementCountryId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.settlementCountryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementCountryName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementCountryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementProvinceId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.settlementProvinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementProvinceName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementProvinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementCityId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.settlementCityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementCityName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementCityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementDistrictId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.settlementDistrictId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 结算账号县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="settlementDistrictName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.settlementDistrictName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写结算账号县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 税务登记证号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taxRegistrationNumber"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.taxRegistrationNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写税务登记证号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 税务登记，纳税人识别号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taxIdentificationNumber"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.taxIdentificationNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写税务登记，纳税人识别号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 税务登记，税务登记证号电子版path&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taxRegistrationNumberPath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.taxRegistrationNumberPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写税务登记，税务登记证号电子版path<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 店铺名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeName"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.storeName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写店铺名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 店铺等级（关联店铺等级id）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="levelId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.levelId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写店铺等级（关联店铺等级id）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主营行业（关联主营行业id）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="industryId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.industryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主营行业（关联主营行业id）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 经营类目（关联平台商品分类）（一级分类）多个用逗号分割，0表示入驻所有分类&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="categoryId"  maxlength="19" class="form-control input-sm" value="${storeEnterAuth.categoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写经营类目（关联平台商品分类）（一级分类）多个用逗号分割，0表示入驻所有分类<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 应付总金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="summaryOfCoping"  maxlength="12" class="form-control input-sm" value="${storeEnterAuth.summaryOfCoping}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写应付总金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商家上传付款凭证path&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paymentVoucherPath"  maxlength="64" class="form-control input-sm" value="${storeEnterAuth.paymentVoucherPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商家上传付款凭证path<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商家付款凭证说明&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paymentInstructions"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.paymentInstructions}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商家付款凭证说明<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 一审审核意见&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="oneAuditOpinion"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.oneAuditOpinion}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写一审审核意见<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 二审审核意见&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="twoAuditOpinion"  maxlength="255" class="form-control input-sm" value="${storeEnterAuth.twoAuditOpinion}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写二审审核意见<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 分佣比例&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="commission"  maxlength="12" class="form-control input-sm" value="${storeEnterAuth.commission}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写分佣比例<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeEnterAuth:auth">
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