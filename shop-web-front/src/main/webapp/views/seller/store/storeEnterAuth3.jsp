<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商家申请入驻')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sellerLoginAfter"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeEnterAuth.js"></script>
<script type="text/javascript" src="${ctx}/views/seller/logistics/selectArea.js"></script>

<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
	.control-label {text-align: right;}
	.goods-add .control-group label{width: 165px;}
	.goods-add-form .formPrompt{margin-left: 185px;}
	.area .dropdown-inner{width:111px !important;}
</style>
</head>
<body class="seller-join">
<!--头部结束-->
<div class="seller-join join-content">
	<dl class="box goods-add">
	<dt class="box-header">
		<h4 class="pull-left">
			<i class="sui-icon icon-tb-addressbook"></i>
			<span>${fns:fy('商家入驻申请')}</span>
		</h4>
			<ul class="sui-breadcrumb">
				<li>${fns:fy('当前位置:')}</li>
				<li>
					<a href="javascript:void(0)">${fns:fy('商家入驻申请')}</a>
				</li>
				<li>
					<a href="javascript:void(0)">${fns:fy('企业资质与店铺信息')}</a>
				</li>
			</ul>
		</dt>
		<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
			<div class="sui-msg msg-tips msg-block">
				<c:if test="${storeEnterAuth.status=='60'}">
					<div class="sui-msg msg-large msg-block msg-error">
					 	<div class="msg-con" style="margin-left: -10px;width: 886px"><i class="sui-icon icon-tb-warnfill"></i>${fns:fy('您的入驻申请失败')}:${storeEnterAuth.twoAuditOpinion}</div>
					</div>
				</c:if>
			</div>
		</address>
		<sys:message content="${message}"/>
		<form class="sui-form form-inline goods-add-form" method="post" id="inputForm" action="${ctxs}/store/storeEnterAuth/auth5.htm">
			<input type="hidden" name="accessKey" value="${generateAccessKey}">
			<dd class="bs-docs-example">
				<h3>${fns:fy('企业及法人信息')}</h3>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('企业名称：')}
					</label> <input type="text" class="input-xlarge" id="companyName" name="companyName" value="${storeEnterAuth.companyName}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入企业名称,长度不要超过64个字符。')}</div>
					</div>
				</div>
				<div class="control-group area">
					<label class="control-label"><font color="red">*</font>${fns:fy('企业所在地：')}</label>
					<input type="hidden" name="provinceName" value="${storeEnterAuth.provinceName}">
					<input type="hidden" name="cityName" value="${storeEnterAuth.cityName}">
					<input type="hidden" name="districtName" value="${storeEnterAuth.districtName}">
					<span class="sui-dropdown dropdown-bordered select">
						<span class="dropdown-inner">
							<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
								<input type="hidden" name="provinceId" value="${storeEnterAuth.provinceId}" id="provinceId"  onchange="selectCity(this)"><i class="caret"></i>
								<span id="provinceName">${empty storeEnterAuth.provinceId?fns:fy('省'):storeEnterAuth.provinceName}</span>
							</a>
						 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
						 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('省')}</a></li>
						 		<c:forEach items="${provinceList}" var="province">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${province.id}">${province.name}</a></li>
						 		</c:forEach>
						 	</ul>
						</span>
				 	</span>
					<span class="sui-dropdown dropdown-bordered select">
						<span class="dropdown-inner">
							<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
								<input type="hidden" name="cityId" id="cityId" value="${storeEnterAuth.cityId}" onchange="selectDistrict(this)"><i class="caret"></i>
								<span id="cityName">${empty storeEnterAuth.cityId?fns:fy('市'):storeEnterAuth.cityName}</span>
							</a>
						 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
						 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
						 	</ul>
						</span>
				 	</span>
					<span class="sui-dropdown dropdown-bordered select">
						<span class="dropdown-inner">
							<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
								<input type="hidden" name="districtId" id="districtId" value="${storeEnterAuth.districtId}"><i class="caret"></i>
								<span id="districtName">${empty storeEnterAuth.districtId?fns:fy('县'):storeEnterAuth.districtName}</span>
							</a>
						 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
						 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
						 	</ul>
						</span>
				 	</span>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请填写企业所在地')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('企业详细地址：')}</label> 
					<input type="text" class="input-xlarge" name="detailedAddress" value="${storeEnterAuth.detailedAddress}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入企业详细地址,长度不要超过64个字符。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('注册资金：')}</label>
					<div class="input-append">
						<input type="text" class="input-xlarge" name="registeredCapital" value="${storeEnterAuth.registeredCapital}" style="width: 302px;"> 
						<span class="add-on" style="margin-left: -4px;height: 14px;">${fns:fy('万元')}</span>
					</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入注册资金。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('联系人名称：')}</label> 
					<input type="text" class="input-xlarge" name="contact" value="${storeEnterAuth.contact}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入联系人名称。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('联系人手机：')}</label> 
					<input type="text" class="input-xlarge" name="contactNumber" value="${storeEnterAuth.contactNumber}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入联系人电话。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('法人名称：')}</label> 
					<input type="text" class="input-xlarge" name="legalName" value="${storeEnterAuth.legalName}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入法人名称,长度不要超过20个字符。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('法人身份证号：')}</label> 
					<input type="text" class="input-xlarge" name="legalIdCardCode" value="${storeEnterAuth.legalIdCardCode}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入法人身份证号。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('法人身份证正面：')}</label> 
			        <div class="input-append">
	        			<input type="hidden" class="imgPath" name="legalIdCardCodePositive" value="${storeEnterAuth.legalIdCardCodePositive}"/>
	        			<div id="vessel1"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('法人身份证反面：')}</label> 
			        <div class="input-append">
	        			<input type="hidden" class="imgPath" name="legalIdCardCodeOpposite" value="${storeEnterAuth.legalIdCardCodeOpposite}"/>
	        			<div id="vessel2"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
			</dd>
			<dd class="bs-docs-example">
				<h3>${fns:fy('证件')}</h3>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('证照类型：')}</label> 
					<label class="radio-pretty inline ${storeEnterAuth.businessType eq '2' || storeEnterAuth.businessType eq null?'checked':''}">
						<input type="radio" name="businessType" ${storeEnterAuth.businessType eq '2' || storeEnterAuth.businessType eq null?'checked="checked"':''}" class="businessType1" value="2"><span>${fns:fy('多证合一营业执照')}</span>
					</label>
					<label class="radio-pretty inline ${storeEnterAuth.businessType eq '1'?'checked':''}">
						<input type="radio" name="businessType" ${storeEnterAuth.businessType eq '1'?'checked="checked"':''}" class="businessType2" value="1"><span>${fns:fy('普通营业执照')}</span>
					</label>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请选择证照类型。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('营业执照注册号：')}</label> 
					<input type="text" class="input-xlarge" name="sellerLicense" value="${storeEnterAuth.sellerLicense}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入营业执照注册号。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('营业执照电子版：')}</label> 
					<div class="input-append">
	        			<input type="hidden" class="imgPath" name="sellerLicensePath" value="${storeEnterAuth.sellerLicensePath}"/>
	        			<div id="vessel3"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('组织机构代码：')}</label> 
					<input type="text" class="input-xlarge" name="organizationCode" value="${storeEnterAuth.organizationCode}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入组织机构代码。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('组织机构代码电子版：')}</label> 
					<div class="input-append">
	        			<input type="hidden" class="imgPath" name="organizationCodePath" value="${storeEnterAuth.organizationCodePath}"/>
	        			<div id="vessel4"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('税务登记号：')}</label> 
					<input type="text" class="input-xlarge" name="taxRegistrationNumber" value="${storeEnterAuth.taxRegistrationNumber}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入税务登记号。')}</div>
					</div>
				</div>
				<div class="control-group bType1" style="${storeEnterAuth.businessType=='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('税务登记电子版：')}</label> 
					<div class="input-append">
	        			<input type="hidden" class="imgPath" name="taxRegistrationNumberPath" value="${storeEnterAuth.taxRegistrationNumberPath}"/>
	        			<div id="vessel5"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
				<div class="control-group bType2" style="${storeEnterAuth.businessType!='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('统一社会信用代码：')}</label> 
					<input type="text" class="input-xlarge" name="socialCreditCode" value="${storeEnterAuth.socialCreditCode}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入统一社会信用代码,如果没有请填写营业执照注册号。')}</div>
					</div>
				</div>
				<div class="control-group bType2" style="${storeEnterAuth.businessType!='1'?'':'display:none'}">
					<label class="control-label"><font color="red">*</font>${fns:fy('多证合一营业执照电子版：')}</label> 
					<div class="input-append">
	        			<input type="hidden" class="imgPath" name="socialCreditCodePath" value="${storeEnterAuth.socialCreditCodePath}"/>
	        			<div id="vessel6"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('开户许可证核准号：')}</label> 
					<input type="text" class="input-xlarge" name="openAnAccountLicense" value="${storeEnterAuth.openAnAccountLicense}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入开户许可证核准号。')}</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('开户许可证核准电子版：')}</label> 
					<div class="input-append">
	        			<input type="hidden" class="imgPath" name="openAnAccountLicensePath" value="${storeEnterAuth.openAnAccountLicensePath}"/>
	        			<div id="vessel7"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证照扫描件/电子版图片大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败。')}</div>
					</div>
				</div>
			</dd>
			<dd class="bs-docs-example">
				<h3>${fns:fy('店铺经营信息')}</h3>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('申请店铺类型：')}</label> 
					<label class="radio-pretty inline ${storeEnterAuth.storeType eq '1' || storeEnterAuth.storeType eq null?'checked':''}">
						<input type="radio" name="storeType" ${storeEnterAuth.storeType eq '1' || storeEnterAuth.storeType eq null?'checked="checked"':''}  class="storeType1" value="1"><span>${fns:fy('普通店铺')}</span>
					</label>
					<label class="radio-pretty inline ${storeEnterAuth.storeType eq '2'?'checked':''}">
						<input type="radio" name="storeType" ${storeEnterAuth.storeType eq '2'?'checked="checked"':''} class="storeType2" value="2"><span>${fns:fy('品牌旗舰店')}</span>
					</label>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">
							<li>${fns:fy('商家持正规品牌授权书入驻，可以申请普通店铺')}</li>
							<li>${fns:fy('商家以企业自有品牌入驻，可以申请品牌旗舰店')}</li>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><font color="red">*</font>${fns:fy('店铺名称：')}</label>
					<input type="hidden" class="input-xlarge" id="oldStoreName" name="oldStoreName" value="${storeEnterAuth.storeName}">
					<input type="text" class="input-xlarge" id="storeName" name="storeName" value="${storeEnterAuth.storeName}">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">
							<ul>
								<li>${fns:fy('普通店铺名称格式：品牌+专卖店或公司名称,例如:XXXXX专卖店或XXXXX电子股份有限公司')}</li>
								<li>${fns:fy('品牌旗舰店铺名称格式：品牌+旗舰店,例如：XXXXX品牌旗舰店')}</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="control-group sType">
					<label class="control-label"><font color="red">*</font>${fns:fy('店铺品牌名：')}</label>
					<input type="text" class="input-xlarge" id="storeBrand" name="storeBrand" value="${storeEnterAuth.storeBrand}" maxlength="255">
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请输入店铺品牌,多个品牌用,号分割')}</div>
					</div>
				</div>
				<div class="control-group sType">
					<label class="control-label"><font color="red">*</font>${fns:fy('品牌商标注册证：')}</label> 
					<div class="input-append">
	        			<input type="hidden" name="sBrand" value="${storeBrandPath0}" data_index="img0"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath1}" data_index="img1"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath2}" data_index="img2"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath3}" data_index="img3"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath4}" data_index="img4"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath5}" data_index="img5"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath6}" data_index="img6"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath7}" data_index="img7"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath8}" data_index="img8"/>
	        			<input type="hidden" name="sBrand" value="${storeBrandPath9}" data_index="img9"/>
	        			<div id="vessel" style="width: 610px;"></div>
	        		</div>
					<div class="formPrompt">
						<i class="msg-icon"></i>
						<div class="msg-con">${fns:fy('请压缩证图片到大小为1M以内，格式为jpg,jpeg,png,bmp并确保文字清晰、以免上传或审核失败，品牌商标注册证最多只能上传10张。')}</div>
					</div>
				</div>
			</dd>
			<div class="text-align pb20">
				<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('提交审核')}</button> 
			</div>
		</form>
	</dl>
</div>
</body>
</html>