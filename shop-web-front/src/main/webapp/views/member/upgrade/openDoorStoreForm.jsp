<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('升级为服务门店')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctxStatic}/fdp/upload.js" type="text/javascript"></script>
<script src="${ctx}/views/member/upgrade/openDoorStoreForm.js" type="text/javascript"></script>
<script src="${ctx}/views/member/logistics/selectArea.js" type="text/javascript"></script>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('用户升级')} > ${fns:fy('升级为服务门店')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('升级为服务门店')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="myform">
				<form class="sui-form form-horizontal" method="post" id="inputForm" action="${ctxm}/upgrade/openDoorStore/save3.htm">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店类型：')}</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner" style="width: 198px;">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="type" name="type" value="${userRepairShop.type}"><i class="caret"></i>
										<c:set var="userRepairShopType" value="${fn:trim(userRepairShop.type)}"/>
										<span>${fns:getDictLabel(userRepairShopType, 'door_store_type', fns:fy('请选择'))}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${fns:getDictList('door_store_type')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
								</span>
						 	</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择门店类型')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店名称：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="name" value="${userRepairShop.name}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入门店名称')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店介绍：')}</label>
						<div class="controls">
							<textarea rows="5" cols="27" name="introduce" maxlength="250">${userRepairShop.introduce}</textarea>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入门店介绍')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店所在地：')}</label>
						<div class="controls">
							<input type="hidden" name="provinceName" value="${userRepairShop.provinceName}">
							<input type="hidden" name="cityName" value="${userRepairShop.cityName}">
							<input type="hidden" name="districtName" value="${userRepairShop.districtName}">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="provinceId" id="provinceId" value="${userRepairShop.provinceId}"  onchange="selectCity(this)"><i class="caret"></i>
										<span id="provinceName">${empty userRepairShop.provinceId?fns:fy('省'):userRepairShop.provinceName}</span>
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
										<input type="hidden" name="cityId" id="cityId" value="${userRepairShop.cityId}" onchange="selectDistrict(this)"><i class="caret"></i>
										<span id="cityName">${empty userRepairShop.cityId?fns:fy('市'):userRepairShop.cityName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="districtId" value="${userRepairShop.districtId}" id="districtId"><i class="caret"></i>
										<span id="districtName">${empty userRepairShop.districtId?fns:fy('县'):userRepairShop.districtName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
								 	</ul>
								</span>
						 	</span>
						 	<input type="text" class="input-large" id="detailedAddress" name="detailedAddress" value="${userRepairShop.detailedAddress}" maxlength="100">
					  </div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店面积：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="burns" value="${userRepairShop.burns}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入门店面积')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('经营品牌：')}</label>
						<div class="controls">
							<c:if test="${fn:length(brandList)==0}">
								<input type="text" class="input-medium" name="brands" maxlength="20" class="brands1">
								<input type="text" class="input-medium" name="brands" maxlength="20" class="brands1">
								<input type="text" class="input-medium" name="brands" maxlength="20" class="brands1">
							</c:if>
							<c:if test="${fn:length(brandList)!=0}">
								<c:forEach items="${brandList}" var="brandName">
									<input type="text" class="input-medium" name="brands" value="${brandName}" maxlength="20">
								</c:forEach>
							</c:if>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入经营品牌')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('建店时间：')}</label>
						<div class="controls">
							<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
							 value="<fmt:formatDate value="${userRepairShop.openDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="openDate" format="yyyy-MM-dd" placeholder="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 190px;">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择建店时间')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('老板姓名：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="bossName" value="${userRepairShop.bossName}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入老板姓名')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('老板电话：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="bossMobile" value="${userRepairShop.bossMobile}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入老板电话')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('门店照片(图1)：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_path1" value="${not empty userRepairShop.path1?'1':'0'}"/>
							<input id="fileUpload_path1" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path1" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_path1_0" style="${not empty userRepairShop.path1?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path1" value="${userRepairShop.path1}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path1}@100x100" style="${not empty userRepairShop.path1?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传门店照片')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('门店照片(图2)：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_path2" value="${not empty userRepairShop.path2?'1':'0'}"/>
							<input id="fileUpload_path2" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path2" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_path2_0" style="${not empty userRepairShop.path2?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path2" value="${userRepairShop.path2}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path2}@100x100" style="${not empty userRepairShop.path2?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传门店照片')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('门店照片(图3)：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_path3" value="${not empty userRepairShop.path3?'1':'0'}"/>
							<input id="fileUpload_path3" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path3" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_path3_0" style="${not empty userRepairShop.path3?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path3" value="${userRepairShop.path3}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path3}@100x100" style="${not empty userRepairShop.path3?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传门店照片')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('门店照片(图4)：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_path4" value="${not empty userRepairShop.path4?'1':'0'}"/>
							<input id="fileUpload_path4" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path4" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_path4_0" style="${not empty userRepairShop.path4?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path4" value="${userRepairShop.path4}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path4}@100x100" style="${not empty userRepairShop.path4?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传门店照片')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('门店照片(图5)：')}</label>
						<div class="controls">
							<input type="hidden" class="existSize_path5" value="${not empty userRepairShop.path5?'1':'0'}"/>
							<input id="fileUpload_path5" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxs}/sys/sysToken/getToken.htm" suffix="path5" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/><br>
						</div>
						<div class="controls">
							<div style="margin-left: 130px">
								<div class="uploadImgDiv" id="img_path5_0" style="${not empty userRepairShop.path5?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="path5" value="${userRepairShop.path5}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${userRepairShop.path5}@100x100" style="${not empty userRepairShop.path5?'':'display:none;'}"/>
									<div class="uploadCloseBtn" style="left: 92px;top:-9px;">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请上传门店照片')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('门店人数：')}</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="peopleCount" name="peopleCount" value="${userRepairShop.peopleCount}"><i class="caret"></i>
										<span>${fns:getDictLabel(userRepairShop.peopleCount, 'user_people_count', fns:fy('请选择'))}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${fns:getDictList('user_people_count')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
					  			</span>
							</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入门店人数')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('营业时间：')}</label>
						<div class="controls">
							<span>${fns:fy('开店时间:')}</span>
							<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
							 value="<fmt:formatDate value="${userRepairShop.openShopDate}" pattern="HH:mm:ss"/>" name="openShopDate" format="HH:mm:ss" placeholder="" onclick="WdatePicker({dateFmt:'HH:mm:ss'});" style="width: 190px;">
							<span>${fns:fy('关店时间:')}</span>
							<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
							 value="<fmt:formatDate value="${userRepairShop.closeShopDate}" pattern="HH:mm:ss"/>" name="closeShopDate" format="HH:mm:ss" placeholder="" onclick="WdatePicker({dateFmt:'HH:mm:ss'});" style="width: 190px;">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择门店时间')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('服务热线：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="hotline" value="${userRepairShop.hotline}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入服务热线')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('店主姓名：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="shopkeeperName" value="${userRepairShop.shopkeeperName}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入店主姓名')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('店主手机：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="shopkeeperMobile" value="${userRepairShop.shopkeeperMobile}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入店主手机')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('联系人姓名：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="contactsName" value="${userRepairShop.contactsName}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入联系人姓名')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('所在部门：')}</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" class="department" name="department" value="${userRepairShop.department}"><i class="caret"></i>
										<c:set var="department" value="${fn:trim(userRepairShop.department)}"/>
										<span>${fns:getDictLabel(department, 'user_department', fns:fy('请选择'))}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${fns:getDictList('user_department')}" var="dict">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${dict.value}">${dict.label}</a></li>
								 		</c:forEach>
								 	</ul>
					  			</span>
							</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请选择所在部门')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('本人手机：')}</label>
						<div class="controls">
							<input type="text" class="input-large" name="mobile" value="${userRepairShop.mobile}" maxlength="64">
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请输入本人手机')}</div>
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
