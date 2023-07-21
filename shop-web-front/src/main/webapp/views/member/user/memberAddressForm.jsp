<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty memberAddress.addressId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('收货地址')}</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/logistics/selectArea.js"></script>
<script type="text/javascript" src="${ctx}/views/member/user/memberAddressForm.js"></script>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl>
			<dt>
				<c:set var="isEdit" value ="${not empty memberAddress.addressId?true:false}"></c:set >
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('会员资料')} > <a href="${ctxm}/user/memberAddress/list.htm">${fns:fy('收货地址')}</a> > ${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('收货地址')}</div>
				<i class="sui-icon icon-tb-list"></i> ${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('收货地址')}
			</dt>
			<dd class="myaddress">
				<sys:message content="${message}"/>
				<form class="sui-form form-horizontal" action="${ctxm}/user/memberAddress/${isEdit?'edit2':'save2'}.htm" method="post" id="inputForm">
					<input type="hidden" name="addressId" value="${memberAddress.addressId}"/>
					<h3 class="sui-form form-horizontal" style="margin-bottom:15px;">
						<span><input type="text" class="input-small" name="addressName" value="${memberAddress.addressName}" maxlength="64"></span>
						<div class="sui-msg msg-tips msg-naked">
							<div class="msg-con" style="font-weight:normal">${fns:fy('给新地址添加一个名称（如：公司）。')}</div>
							<s class="msg-icon"></s>
						</div>
					</h3>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('收货人')}:</label>
						<div class="controls">
							<input type="text" class="input-medium" name="name" value="${memberAddress.name}" maxlength="64"/>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请填写真实姓名。')}。</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('所在地区')}:</label>
						<input type="hidden" name="provinceName" value="${memberAddress.provinceName}"/>
						<input type="hidden" name="cityName" value="${memberAddress.cityName}"/>
						<input type="hidden" name="districtName" value="${memberAddress.districtName}"/>
						<input type="hidden" name="countryId" value="1"/>
						<input type="hidden" name="countryName" value="中国"/>
						<div class="controls">
							<div style="display:${isEdit?'':'none'}" id="areaEdit">
								${memberAddress.provinceName} &nbsp;&nbsp;${memberAddress.cityName}&nbsp;&nbsp; ${memberAddress.districtName}&nbsp;&nbsp;
								<a href="javascript:void(0);" class="sui-btn btn-small">${fns:fy('编辑')}</a>
							</div>
							<span id="area2" style="display:${isEdit?'none':''}">
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input type="hidden" name="provinceId" id="provinceId"  onchange="selectCity(this)" value="${memberAddress.provinceId}"/><i class="caret"></i>
											<span id="provinceName">${isEdit?memberAddress.provinceName:fns:fy('省')}</span>
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
											<input type="hidden" name="cityId" id="cityId" onchange="selectDistrict(this)" value="${memberAddress.cityId}"/><i class="caret"></i>
											<span id="cityName">${isEdit?memberAddress.cityName:fns:fy('市')}</span>
										</a>
										<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
										</ul>
									</span>
								</span>
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input type="hidden" name="districtId" id="districtId" value="${memberAddress.districtId}"/><i class="caret"></i>
											<span id="districtName">${isEdit?memberAddress.districtName:fns:fy('县')}</span>
										</a>
										<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
										</ul>
									</span>
								</span>
							</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('地址')}:</label>
						<div class="controls">
							<input type="text" class="input-xlarge" name="detailedAddress" value="${memberAddress.detailedAddress}" maxlength="255"/>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请具体到门牌号。')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('电话')}:</label>
						<div class="controls">
							<input type="text" class="input-medium" name="mobile" value="${memberAddress.mobile}"/>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请确保真实有效。')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('邮编')}:</label>
						<div class="controls">
							<input type="text" class="input-medium" name="zipCode" value="${memberAddress.zipCode}"/>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请确保真实有效。')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group mt20">
						<label class="control-label"></label>
						<div class="control-group">
							<button class="sui-text-danger sui-btn btn-bordered btn-primary" name="isDefault" value="0" type="submit"><i class="sui-icon icon-tb-check"></i> ${fns:fy('保存')}</button>
							<button class="mr10 sui-btn btn-bordered btn-primary" name="isDefault" value="1" type="submit"><i class="sui-icon icon-tb-location"></i> ${fns:fy('保存并设为默认')}</button> 
						</div>
					</div>
				</form>
			</dd>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
