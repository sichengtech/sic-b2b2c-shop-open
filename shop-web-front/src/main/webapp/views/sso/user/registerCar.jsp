<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>注册</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/seller/product/ajaxProductCar.js" type="text/javascript"></script>
<script src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctxStatic}/fdp/upload.js" type="text/javascript"></script>
<script src="${ctx}/views/sso/user/registerCar.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/views/seller/logistics/selectArea.js"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var usernameMax=${siteRegister.usernameMax};
	var usernameMin=${siteRegister.usernameMin};
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
	var disableUsername='${siteRegister.disableUsername}';
</script>
<style type="text/css">
	.div_style{background:#F6F6F6;height:35px;border:1px solid #E0E0E0;}
	.span_style{font-size: 16px;font-weight: 700;height: 35px;overflow: hidden;line-height: 35px;padding-left: 10px;}
	.uploadCloseBtn{width: 16px;height: 16px;border: 0px solid #d7d7d7;border-radius: 9px;text-align: center;line-height: 15px;
    	position: absolute;background: #ffffff;cursor: pointer;left:92px;top:22px;}
    .uploadImgDiv{position: relative;}
    .info{width: 800px;}
</style>
</head>
<body>
<div id="agree" style="display: none">${siteRegister.agreement}</div>
<div class="sso-main sso-reg-main-car" style="height:inherit;">
	<div class="order-tabs sso-reg-box sui-container">
		<ul class="sui-nav nav-tabs">
			<li class="member ${ssoReg eq '1' || empty ssoReg?'active':''} pl200"><a href="#memberRe" data-toggle="tab"><b></b>个人注册</a></li>
			<li class="seller ${ssoReg eq '2'?'active':''}"><a href="#sellerRe" data-toggle="tab"><b></b>商家注册</a></li>
			<li class="store ${ssoReg eq '3'?'active':''}"><a href="#storeRe" data-toggle="tab"><b></b>汽车服务门店入驻</a></li>
		</ul>
		<sys:message content="${message}"/>
		<div class="tab-content pl200 pr200">
			<!-- 个人注册 -->
			<ul id="memberRe" class="tab-pane ${ssoReg eq '1' || empty ssoReg?'active':''} table-css">
				<form id="inputForm1" class="sui-form form-horizontal" action="${ctxsso}/register/carSave2.htm?ssoReg=1" method="post">
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">身份选择：</label>
						<div class="controls">
							<label class="radio-pretty inline checked">
							<input type="radio" name="identityType" class="identity_ordinary" value="1"><span>普通用户</span>
							</label>
							<label class="radio-pretty inline">
							<input type="radio" name="identityType" class="identity_car" value="2"><span>车主用户</span>
							</label>							
						</div>
					</div>
					<div class="control-group info" style="display: none">
						<label for="inputInfo" class="control-label v-top">车型选择：</label>
						<div class="controls" style="padding-top: 4px;">
							<span class="carName" style="font-size: 14px;color: #706f6b;"></span>
							<a href="javaScript:void(0);" class="changeCarName">编辑</a>
						</div>
					</div>
					<div class="control-group info identity_apply_car" style="display: none;border: 1px solid #ddd;width: 655px;margin-left: 70px">
						<div style="margin-left: 25px;font-size: 13px;text-decoration:underline">我的品牌车型</div>
						<div class="controls" style="padding-left: 22px;">
							<span>品牌:</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carTwoId" id="carTwoId"  onchange="selectThree(this)"><i class="caret"></i>
										<span id="twoName">请选择</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="twoAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 		<c:forEach items="${productCarTwoList}" var="productCar">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${productCar.carId}">${productCar.name}</a></li>
								 		</c:forEach>
								 	</ul>
								</span>
						 	</span>
							<span>车型:</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carThreeId" id="carThreeId" onchange="selectFour(this)"><i class="caret"></i>
										<span id="threeName">请选择</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="threeAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 	</ul>
								</span>
						 	</span>
							<span>型号:</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carFourId" id="carFourId"><i class="caret"></i>
										<span id="fourName" style="width: 190px;">请选择</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="fourAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 	</ul>
								</span>
						 	</span>
						 	<input type="button" class="sui-btn btn-xlarge btn-primary identity_car_button" value="确定" style="padding-top: 0px;"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName1" class="input-xlarge" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password1" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword1" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>中国+86：</label>
						<div class="controls">
							<input type="text" name="mobile" id="mobile1" class="input-xlarge" value="${userMain.mobile}" placeholder="请输入常用手机号码" value="${userMain.email}" maxlength="11">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode1" name="validateCode" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>短信验证码：</label>
						<div class="controls">
							<input type="text" id="smsVerification1" name="smsVerification" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<button type="button" id="smsSender1" class="post-code">发送验证码</button>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label for="inputInfo" class="control-label"></label>
						<input id="agreeType1" type="checkbox"><span>我已阅读并同意</span>
						<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注 册"/>
					</label>
				</form>
			</ul>
			<!-- 商家注册 -->
			<ul id="sellerRe" class="tab-pane ${ssoReg eq '2'?'active':''}">
				<form id="inputForm2" class="sui-form form-horizontal" action="${ctxsso}/register/carSave2.htm?ssoReg=2" method="post">
					<div class="div_style"><span class="span_style">账户信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName2" class="input-xlarge" value="${userMain.loginName}" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password2" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword2" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="div_style"><span class="span_style">企业属性</span></div>
					<div class="control-group info">
						<span class="control-label"><font color="red">*</font>企业类型：</span>
						<span class="sui-dropdown dropdown-bordered select">
							<span class="dropdown-inner">
								<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input type="hidden" class="type" name="type" value="${userMerchantInfo.type}"><i class="caret"></i>
									<c:set var="type" value="${fn:trim(userMerchantInfo.type)}"/>
									<span>${fns:getDictLabel(type, 'use_shop_type', '请选择')}</span>
								</a>
							 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
							 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
							 		<c:forEach items="${fns:getDictList('use_shop_type')}" var="dict">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
							 		</c:forEach>
							 	</ul>
							 </span>
						</span>
						<span style="font-size:14px; color:#666;margin-left:15px;"><font color="red">*</font>行业属性：</span>
						<span class="sui-dropdown dropdown-bordered select">
							<span class="dropdown-inner">
								<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input type="hidden" class="industry" name="industry" value="${userMerchantInfo.industry}"><i class="caret"></i>
									<c:set var="industry" value="${fn:trim(userMerchantInfo.industry)}"/>
									<span>${fns:getDictLabel(industry, 'user_industry', '请选择')}</span>
								</a>
							 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
							 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
							 		<c:forEach items="${fns:getDictList('user_industry')}" var="dict">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
							 		</c:forEach>
							 	</ul>
							 </span>
						</span>
					</div>
					<div class="div_style"><span class="span_style">企业信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>公司名称：</label>
						<div class="controls">
							<input type="text" id="name1" name="name" class="input-xlarge" value="${userMerchantInfo.name}" placeholder="请输入公司全称" maxlength="32"/>
						</div>
					</div>
					<div class="div_style"><span class="span_style">联系人信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>联系人姓名：</label>
						<div class="controls">
							<input type="text" id="contacts" name="contacts" class="input-xlarge" value="${userMerchantInfo.contacts}" placeholder="请输入联系人姓名" maxlength="32"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>本人手机：</label>
						<div class="controls">
							<input type="text" name="mobile" id="mobile2" class="input-xlarge" value="${userMain.mobile}" placeholder="请输入常用手机号码" value="${userMain.email}" maxlength="11">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>短信验证码：</label>
						<div class="controls">
							<input type="text" id="smsVerification2" name="smsVerification" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<button type="button" id="smsSender2" class="post-code">发送验证码</button>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label for="inputInfo" class="control-label"></label>
						<input id="agreeType2" type="checkbox"><span>我已阅读并同意</span>
						<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注册并进入开店流程"/>
					</label>				
				</form>
			</ul>
			<!-- 汽车服务门店入驻 -->
			<ul id="storeRe" class="tab-pane ${ssoReg eq '3'?'active':''}">
				<form id="inputForm3" class="sui-form form-horizontal" action="${ctxsso}/register/carSave2.htm?ssoReg=3" method="post">
					<div class="div_style"><span class="span_style">账户信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName3" class="input-xlarge" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password3" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword3" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="div_style"><span class="span_style">门店服务</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店类型：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="repairShoptype" name="type" value="${userRepairShop.type}"><i class="caret"></i>
										<c:set var="type" value="${fn:trim(userRepairShop.type)}"/>
										<span>${fns:getDictLabel(type, 'door_store_type', '请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 		<c:forEach items="${fns:getDictList('door_store_type')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
					  			</span>
							</span>
						</div>
					</div>
					<div class="div_style"><span class="span_style">门店信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店名称：</label>
						<div class="controls">
							<input type="text" id="name2" name="name" class="input-xlarge" value="${userRepairShop.name}" maxlength="64" placeholder="请输入门店名称"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店介绍：</label>
						<div class="controls">
							<textarea rows="3" id="introduce2" name="introduce" placeholder="100字以内" maxlength="100">${userRepairShop.introduce}</textarea>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店所在地：</label>
						<input type="hidden" name="provinceName" value="${userRepairShop.provinceName}">
						<input type="hidden" name="cityName" value="${userRepairShop.cityName}">
						<input type="hidden" name="districtName" value="${userRepairShop.districtName}">
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="provinceId" class="repairShopProvinceId" id="provinceId" value="${userRepairShop.provinceId}"  onchange="selectCity(this)"><i class="caret"></i>
										<span id="provinceName">${empty userRepairShop.provinceId?'省':userRepairShop.provinceName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">省</a></li>
										<c:forEach items="${provinceList}" var="province">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${province.id}">${province.name}</a></li>
								 		</c:forEach>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="cityId" class="repairShopCityId" id="cityId" value="${userRepairShop.cityId}" onchange="selectDistrict(this)"><i class="caret"></i>
										<span id="cityName">${empty userRepairShop.cityId?'市':userRepairShop.cityName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">市</a></li>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="districtId" class="repairShopDistrictId" id="districtId" value="${userRepairShop.districtId}"><i class="caret"></i>
										<span id="districtName">${empty userRepairShop.districtId?'县/区':userRepairShop.districtName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">县/区</a></li>
								 	</ul>
								</span>
						 	</span>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店地址：</label>
						<div class="controls">
							<input type="text" id="detailedAddress2" name="detailedAddress" class="input-xlarge" value="${userRepairShop.detailedAddress}" maxlength="100" placeholder="请输入门店地址"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店面积：</label>
						<div class="controls">
							<input type="text" id="burns"  name="burns" class="input-xlarge" value="${userRepairShop.burns}" maxlength="45" placeholder="请输入名店面积"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">经营品牌：</label>
						<div class="controls">
							<input type="text" name="brands" class="input-xlarge" value="${userRepairShop.brands}" maxlength="45" placeholder="请输入经营品牌,多个品牌用逗号分开"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">建店时间：</label>
						<div class="controls">
							<input type="text" id="" name="openDate" class="form-control input-sm J-date-start input-date" nc-format="" 
							value="<fmt:formatDate value="${userRepairShop.openDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="请选择建店时间"/> 
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>老板姓名：</label>
						<div class="controls">
							<input type="text" id="bossName" name="bossName" class="input-xlarge" value="${userRepairShop.bossName}" maxlength="32" placeholder="请输入老板姓名"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>老板手机：</label>
						<div class="controls">
							<input type="text" id="bossMobile" name="bossMobile" class="input-xlarge" value="${userRepairShop.bossMobile}" maxlength="11" placeholder="请输入老板手机号">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">门店照片(图1)：</label>
						<div class="controls">
							<input type="hidden" class="existSize_path1" value="${not empty userRepairShop.path1?'1':'0'}"/>
							<input id="fileUpload_path1" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path1" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
							<div>
								<div class="uploadImgDiv" id="img_path1_0" style="${not empty userRepairShop.path1?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path1" value="${userRepairShop.path1}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path1}@100x100" style="${not empty userRepairShop.path1?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">门店照片(图2)：</label>
						<div class="controls">
							<input type="hidden" class="existSize_path2" value="${not empty userRepairShop.path2?'1':'0'}"/>
							<input id="fileUpload_path2" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path2" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
							<div>
								<div class="uploadImgDiv" id="img_path2_0" style="${not empty userRepairShop.path2?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path2" value="${userRepairShop.path2}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path2}@100x100" style="${not empty userRepairShop.path2?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">门店照片(图3)：</label>
						<div class="controls">
							<input type="hidden" class="existSize_path3" value="${not empty userRepairShop.path3?'1':'0'}"/>
							<input id="fileUpload_path3" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path3" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
							<div>
								<div class="uploadImgDiv" id="img_path3_0" style="${not empty userRepairShop.path3?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path3" value="${userRepairShop.path3}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path3}@100x100" style="${not empty userRepairShop.path3?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">门店照片(图4)：</label>
						<div class="controls">
							<input type="hidden" class="existSize_path4" value="${not empty userRepairShop.path4?'1':'0'}"/>
							<input id="fileUpload_path4" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path4" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
							<div>
								<div class="uploadImgDiv" id="img_path4_0" style="${not empty userRepairShop.path4?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path4" value="${userRepairShop.path4}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path4}@100x100" style="${not empty userRepairShop.path4?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">门店照片(图5)：</label>
						<div class="controls">
							<input type="hidden" class="existSize_path5" value="${not empty userRepairShop.path5?'1':'0'}"/>
							<input id="fileUpload_path5" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path5" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
							<div>
								<div class="uploadImgDiv" id="img_path5_0" style="${not empty userRepairShop.path5?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path5" value="${userRepairShop.path5}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path5}@100x100" style="${not empty userRepairShop.path5?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>门店人数：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="repairShopPeopleCount" name="peopleCount" value="${userRepairShop.peopleCount}"><i class="caret"></i>
										<span>${fns:getDictLabel(userRepairShop.peopleCount, 'user_people_count', '请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 		<c:forEach items="${fns:getDictList('user_people_count')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
					  			</span>
							</span>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">营业时间：</label>
						<div class="controls">
							<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" placeholder="请选择开店时间"
							value="<fmt:formatDate value="${userRepairShop.openShopDate}" pattern="HH:mm:ss"/>"
							name="openShopDate" format="HH:mm:ss" onclick="WdatePicker({dateFmt:'HH:mm:ss'});" style="width: 190px;"/>
							<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" placeholder="请选择闭店时间"
							value="<fmt:formatDate value="${userRepairShop.closeShopDate}" pattern="HH:mm:ss"/>"
							name="closeShopDate" format="HH:mm:ss" onclick="WdatePicker({dateFmt:'HH:mm:ss'});" style="width: 190px;"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>服务热线：</label>
						<div class="controls">
							<input type="text" id="hotline" name="hotline" class="input-xlarge" value="${userRepairShop.hotline}" maxlength="32" placeholder="请输入服务热线"/>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>店长姓名：</label>
						<div class="controls">
							<input type="text" id="shopkeeperName" name="shopkeeperName" class="input-xlarge" value="${userRepairShop.shopkeeperName}" maxlength="32" placeholder="请输入店长姓名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>店长手机：</label>
						<div class="controls">
							<input type="text" id="shopkeeperMobile" name="shopkeeperMobile" class="input-xlarge" value="${userRepairShop.shopkeeperMobile}" maxlength="11" placeholder="请输入店长手机号">
						</div>
					</div>
					<div class="div_style"><span class="span_style">联系人信息</span></div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>联系人姓名：</label>
						<div class="controls">
							<input type="text" id="contactsName" name="contactsName" class="input-xlarge" value="${userRepairShop.contactsName}" maxlength="32" placeholder="请输入联系人姓名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>所在部门：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="repairShopDepartment" name="department" value="${userRepairShop.department}"><i class="caret"></i>
										<c:set var="department" value="${fn:trim(userRepairShop.department)}"/>
										<span>${fns:getDictLabel(department, 'user_department', '请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a></li>
								 		<c:forEach items="${fns:getDictList('user_department')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
					  			</span>
							</span>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top"><font color="red">*</font>本人手机：</label>
						<div class="controls">
							<input type="text" name="mobile" id="mobile3" class="input-xlarge" placeholder="请输入常用手机号码" value="${userMain.mobile}" maxlength="11">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode3" name="validateCode" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label"><font color="red">*</font>短信验证码：</label>
						<div class="controls">
							<input type="text" id="smsVerification3" name="smsVerification" class="input-Code" placeholder="请输入验证码" value="" maxlength="4">
							<button type="button" id="smsSender3" class="post-code">发送验证码</button>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label for="inputInfo" class="control-label"></label>
						<input id="agreeType3" type="checkbox"><span>我已阅读并同意</span>
						<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注册"/>
					</label>
				</form>
			</ul>
		</div>
	</div>
</div>
<!---判断是否被ifram包住,如果包住就跳出iframe--->
<script type="text/javascript">
	if (top.location != self.location)
		top.location = self.location;
</script>
</body>
</html>
