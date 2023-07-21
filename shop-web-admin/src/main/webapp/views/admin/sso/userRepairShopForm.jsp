<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>汽车服务门店管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userRepairShopForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userRepairShop.UId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}汽车服务门店</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userRepairShop/list.do"> <i class="fa fa-user"></i> 汽车服务门店列表</a></li>
				<shiro:hasPermission name="sso:userRepairShop:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 汽车服务门店${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>汽车服务门店管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userRepairShop/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userRepairShop.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键,会员的ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${userRepairShop.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键,会员的ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 审核状态：0待审、1通过、2未通过&nbsp;:</label>
							<div class="col-sm-5">
								<select name="authType" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('brand_status')}" var="item">
									<option value="${item.value}" ${item.value==userRepairShop.authType?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写审核状态：0待审、1通过、2未通过<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店类型（字典）use_shop_type&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('use_shop_type')}" var="item">
									<option value="${item.value}" ${item.value==userRepairShop.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店类型（字典）use_shop_type<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${userRepairShop.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店介绍&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="introduce"  maxlength="255" class="form-control input-sm" value="${userRepairShop.introduce}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店介绍<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${userRepairShop.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${userRepairShop.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${userRepairShop.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店所在地县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${userRepairShop.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店所在地县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" value="${userRepairShop.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店面积&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="burns"  maxlength="64" class="form-control input-sm" value="${userRepairShop.burns}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店面积<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 经营品牌，号分割&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="brands"  maxlength="64" class="form-control input-sm" value="${userRepairShop.brands}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写经营品牌，号分割<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 建店日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="openDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userRepairShop.openDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写建店日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 老板姓名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bossName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.bossName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写老板姓名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 老板电话&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bossMobile"  maxlength="64" class="form-control input-sm" value="${userRepairShop.bossMobile}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写老板电话<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店照片path1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path1"  maxlength="64" class="form-control input-sm" value="${userRepairShop.path1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店照片path1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店照片path2&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path2"  maxlength="64" class="form-control input-sm" value="${userRepairShop.path2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店照片path2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店照片path3&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path3"  maxlength="64" class="form-control input-sm" value="${userRepairShop.path3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店照片path3<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店照片path4&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path4"  maxlength="64" class="form-control input-sm" value="${userRepairShop.path4}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店照片path4<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店照片path5&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path5"  maxlength="64" class="form-control input-sm" value="${userRepairShop.path5}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店照片path5<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 门店人数（字典）user_people_count&nbsp;:</label>
							<div class="col-sm-5">
								<select name="peopleCount" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('user_people_count')}" var="item">
									<option value="${item.value}" ${item.value==userRepairShop.peopleCount?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写门店人数（字典）user_people_count<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 营业状态（字典）user_business_status&nbsp;:</label>
							<div class="col-sm-5">
								<select name="businessStatus" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('user_business_status')}" var="item">
									<option value="${item.value}" ${item.value==userRepairShop.businessStatus?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写营业状态（字典）user_business_status<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 开店营业时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="openShopDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userRepairShop.openShopDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写开店营业时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 关店营业时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="closeShopDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userRepairShop.closeShopDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写关店营业时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 服务热线&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="hotline"  maxlength="64" class="form-control input-sm" value="${userRepairShop.hotline}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写服务热线<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 店主姓名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="shopkeeperName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.shopkeeperName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写店主姓名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 店主手机&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="shopkeeperMobile"  maxlength="64" class="form-control input-sm" value="${userRepairShop.shopkeeperMobile}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写店主手机<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 联系人姓名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="contactsName"  maxlength="64" class="form-control input-sm" value="${userRepairShop.contactsName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写联系人姓名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 所在部门&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="department"  maxlength="2" class="form-control input-sm" value="${userRepairShop.department}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写所在部门<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 本人手机&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="mobile"  maxlength="64" class="form-control input-sm" value="${userRepairShop.mobile}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写本人手机<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sso:userRepairShop:edit">
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