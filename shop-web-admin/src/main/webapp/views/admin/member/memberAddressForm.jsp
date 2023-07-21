<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>买家收货地址管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberAddressForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty memberAddress.addressId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}买家收货地址</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/member/memberAddress/list.do"> <i class="fa fa-user"></i> 买家收货地址列表</a></li>
				<shiro:hasPermission name="member:memberAddress:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 买家收货地址${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>买家收货地址管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/member/memberAddress/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="addressId" value="${memberAddress.addressId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="addressId"  maxlength="19" class="form-control input-sm" value="${memberAddress.addressId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(会员表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="mId"  maxlength="19" class="form-control input-sm" value="${memberAddress.MId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(会员表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 收货人&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${memberAddress.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写收货人<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${memberAddress.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${memberAddress.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${memberAddress.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${memberAddress.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${memberAddress.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${memberAddress.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${memberAddress.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${memberAddress.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" value="${memberAddress.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 联系方式（手机，电话号码）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="mobile"  maxlength="64" class="form-control input-sm" value="${memberAddress.mobile}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写联系方式（手机，电话号码）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 邮编&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="zipCode"  maxlength="64" class="form-control input-sm" value="${memberAddress.zipCode}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写邮编<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否默认(0不默认 1默认)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('member_default')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isDefault" value="${item.value}" ${item.value==memberAddress.isDefault?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否默认(0不默认 1默认)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="member:memberAddress:edit">
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