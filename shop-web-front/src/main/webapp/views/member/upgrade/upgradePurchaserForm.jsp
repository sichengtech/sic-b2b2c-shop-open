<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('升级为采购商')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctxStatic}/fdp/upload.js" type="text/javascript"></script>
<script src="${ctx}/views/member/upgrade/upgradePurchaserForm.js" type="text/javascript"></script>
<script src="${ctx}/views/member/logistics/selectArea.js" type="text/javascript"></script>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('用户升级')} > ${fns:fy('升级为采购商')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('升级为采购商')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="myform">
				<form class="sui-form form-horizontal" method="post" id="inputForm" action="${ctxm}/upgrade/upgradePurchaser/save3.htm">
					<div class="control-group">
						<label class="control-label">${fns:fy('企业名称：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="name" value="${userPurchase.name}">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入企业姓名')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('企业类型：')}</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner" style="width: 198px;">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="type" name="type" value="${userPurchase.type}"><i class="caret"></i>
										<span>${fns:getDictLabel(userPurchase.type, 'use_shop_type', '请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${fns:getDictList('use_shop_type')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
								 </span>
							</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择企业类型')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('企业属性：')}</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner" style="width: 198px;">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="industry" name="industry" value="${userPurchase.industry}"><i class="caret"></i>
										<span>${fns:getDictLabel(userPurchase.industry, 'user_industry', '请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${fns:getDictList('user_industry')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
								 </span>
							</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择企业属性')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('营业执照：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_businesslicense" value="${not empty userPurchase.businesslicense?'1':'0'}"/>
							<input id="fileUpload_businesslicense" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="businesslicense" allowType="jpg,jpeg,png,bmp" fileSize="5242880" accessKey="${generateAccessKey}" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_businesslicense_0" style="${not empty userPurchase.businesslicense?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="businesslicense" value="${userPurchase.businesslicense}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userPurchase.businesslicense}@100x100?accessKey=${generateAccessKey}" style="${not empty userPurchase.businesslicense?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传营业执照')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('企业介绍：')}</label>
						<div class="controls">
							<textarea rows="5" cols="27" name="introduce" maxlength="250">${userPurchase.introduce}</textarea>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入企业介绍')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('企业所在地：')}</label>
						<div class="controls">
							<span id="area1"> 
								<input type="hidden" name="areas" class="areas" value="${userPurchase.provinceName}${userPurchase.cityName}${userPurchase.districtName}">
								${userPurchase.provinceName}&nbsp;${userPurchase.cityName}&nbsp;${userPurchase.districtName}&nbsp;${userPurchase.detailedAddress}
								<a id="change" style="margin-left: 20px" href="javaScript:;">${fns:fy('编辑')}</a>
							</span>
							<span id="area2" style="display: none">
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input type="hidden" name="provinceId" id="provinceId"  onchange="selectCity(this)"><i class="caret"></i>
											<span id="provinceName">${fns:fy('省')}</span>
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
											<input type="hidden" name="cityId" id="cityId" onchange="selectDistrict(this)"><i class="caret"></i>
											<span id="cityName">${fns:fy('市')}</span>
										</a>
									 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
									 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
									 	</ul>
									</span>
							 	</span>
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input type="hidden" name="districtId" id="districtId"><i class="caret"></i>
											<span id="districtName">${fns:fy('县')}</span>
										</a>
									 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
									 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
									 	</ul>
									</span>
							 	</span>
							 	<input type="text" class="input-large" id="detailedAddress" name="detailedAddress" value="${userPurchase.detailedAddress}" maxlength="200">
						 	</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入企业介绍')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('联系人：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="contacts" value="${userPurchase.contacts}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入联系人')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('联系人电话：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="contactsTelephone" value="${userPurchase.contactsTelephone}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入联系人电话')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group button">
						<label class="control-label">　</label>
						<button type="submit" class="sui-btn btn-xlarge btn-primary"><i class="sui-icon icon-tb-roundcheck"></i> ${fns:fy('提交审核')}</button>
					</div>
				</form>
			</dd>
		</dl>
	</div>
</body>
</html>
