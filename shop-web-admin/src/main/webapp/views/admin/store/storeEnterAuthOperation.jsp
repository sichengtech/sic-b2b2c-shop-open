<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("入驻")}${attr=='1'? fns:fy("查看") : fns:fy("审核") }</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeEnterAuthOperation.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeEnterAuth.enterId?true:false}"></c:set >
			<h4 class="title">${fns:fy("入驻")}${attr=='1'?fns:fy("查看"):fns:fy("审核")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeEnterAuth/list.do"> <i class="fa fa-user"></i> ${fns:fy("入驻审核列表")}</a></li>
				<shiro:hasPermission name="store:storeEnterAuth:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("入驻")}${attr=='1'?fns:fy("查看"):fns:fy("审核")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.入驻申请.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeEnterAuth/edit2.do" method="post">
						<input type="hidden" name="enterId" value="${storeEnterAuth.enterId}">
						<!--企业及法人信息开始 -->
						<h4>${fns:fy("企业及法人信息")}</h4>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("企业名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.companyName}</label>
							</div>
							<div class="col-sm-5"></div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("企业所在地")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.countryName}&nbsp;${storeEnterAuth.provinceName}&nbsp;${storeEnterAuth.cityName}&nbsp;${storeEnterAuth.districtName}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("企业详细地址")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.detailedAddress}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("注册资金")}&nbsp;:</label>
							<div class="col-sm-5">
							 <div class="input-group"> 
								<label class="control-label">${storeEnterAuth.registeredCapital}${fns:fy("万元")}</label>
							 </div>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("联系人姓名")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.contact}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("联系人电话")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.contactNumber}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("法人姓名")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.legalName}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("法人身份证号")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.legalIdCardCode}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("法人身份证正面")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">
									<c:if test="${storeEnterAuth.legalIdCardCodePositive !=null}">
										<a href="${ctxfs}${storeEnterAuth.legalIdCardCodePositive}?accessKey=${generateAccessKey}" target="_blank">
											<image src="${ctxfs}${storeEnterAuth.legalIdCardCodePositive}@200x200?accessKey=${generateAccessKey}"></image>
										</a>
									</c:if>
									<c:if test="${storeEnterAuth.legalIdCardCodePositive == null}">${fns:fy("无")}</c:if>
								</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("法人身份证反面")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">
									<c:if test="${storeEnterAuth.legalIdCardCodeOpposite !=null}">
										<a href="${ctxfs}${storeEnterAuth.legalIdCardCodeOpposite}?accessKey=${generateAccessKey}" target="_blank">
											<image src="${ctxfs}${storeEnterAuth.legalIdCardCodeOpposite}@200x200?accessKey=${generateAccessKey}"></image>
										</a>
									</c:if>
									<c:if test="${storeEnterAuth.legalIdCardCodeOpposite == null}">${fns:fy("无")}</c:if>
								</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<!--企业及法人信息结束 -->
						<!--证件开始 -->
						<h4>${fns:fy("证件")}</h4>
						<c:if test="${storeEnterAuth.businessType=='2'}">
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("统一社会信用代码")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">${storeEnterAuth.socialCreditCode}</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("多证合一营业执照电子版")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">
										<c:if test="${storeEnterAuth.socialCreditCodePath !=null}">
											<a href="${ctxfs}${storeEnterAuth.socialCreditCodePath}?accessKey=${generateAccessKey}" target="_blank">
												<image src="${ctxfs}${storeEnterAuth.socialCreditCodePath}@200x200?accessKey=${generateAccessKey}"></image>
											</a>
										</c:if>
										<c:if test="${storeEnterAuth.socialCreditCodePath == null}">${fns:fy("无")}</c:if>
									</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
						</c:if>
						<c:if test="${storeEnterAuth.businessType=='1'}">
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("营业执照注册号")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">${storeEnterAuth.sellerLicense}</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("营业执照电子版")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">
										<c:if test="${storeEnterAuth.sellerLicensePath !=null}">
											<a href="${ctxfs}${storeEnterAuth.sellerLicensePath}?accessKey=${generateAccessKey}" target="_blank">
												<image src="${ctxfs}${storeEnterAuth.sellerLicensePath}@200x200?accessKey=${generateAccessKey}"></image>
											</a>
										</c:if>
										<c:if test="${storeEnterAuth.sellerLicensePath == null}">${fns:fy("无")}</c:if>
									</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("组织机构代码")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">${storeEnterAuth.organizationCode}</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("组织机构代码电子版")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">
										<c:if test="${storeEnterAuth.organizationCodePath !=null}">
											<a href="${ctxfs}${storeEnterAuth.organizationCodePath}?accessKey=${generateAccessKey}" target="_blank">
												<image src="${ctxfs}${storeEnterAuth.organizationCodePath}@200x200?accessKey=${generateAccessKey}"></image>
											</a>
										</c:if>
										<c:if test="${storeEnterAuth.organizationCodePath == null}">${fns:fy("无")}</c:if>
									</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("税务登记号")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">${storeEnterAuth.taxRegistrationNumber}</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("税务登记电子版")}&nbsp;:</label>
								<div class="col-sm-5">
									<label class="control-label">
										<c:if test="${storeEnterAuth.taxRegistrationNumberPath !=null}">
											<a href="${ctxfs}${storeEnterAuth.taxRegistrationNumberPath}?accessKey=${generateAccessKey}" target="_blank">
												<image src="${ctxfs}${storeEnterAuth.taxRegistrationNumberPath}@200x200?accessKey=${generateAccessKey}"></image>
											</a>
										</c:if>
										<c:if test="${storeEnterAuth.taxRegistrationNumberPath == null}">${fns:fy("无")}</c:if>
									</label>
								</div>
								<div class="col-sm-5">
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("开户许可证核准号")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.openAnAccountLicense}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("开户许可证核准电子版")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">
									<c:if test="${storeEnterAuth.openAnAccountLicensePath !=null}">
										<a href="${ctxfs}${storeEnterAuth.openAnAccountLicensePath}?accessKey=${generateAccessKey}" target="_blank">
											<image src="${ctxfs}${storeEnterAuth.openAnAccountLicensePath}@200x200?accessKey=${generateAccessKey}"></image>
										</a>
									</c:if>
									<c:if test="${storeEnterAuth.openAnAccountLicensePath == null}">${fns:fy("无")}</c:if>
								</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<!--证件结束 -->
						<!--店铺经营信息开始 -->
						<h4>${fns:fy("店铺经营信息")}</h4>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("申请店铺类型")}  &nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${fns:getDictLabel(storeEnterAuth.storeType, 'store_type', '')}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("店铺名称")} &nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.storeName}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("店铺品牌名")} &nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${storeEnterAuth.storeBrand}</label>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("店铺品牌商标证书")}&nbsp;:</label>
							<div class="col-sm-10">
								<div>
									<c:forEach var="storeBrandPath" items="${storeBrandPathsList}">
										<a href="${ctxfs}${storeBrandPath}?accessKey=${generateAccessKey}" target="_blank">
											<image src="${ctxfs}${storeBrandPath}@200x200?accessKey=${generateAccessKey}"></image>
										</a>
									</c:forEach>
								</div>
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("应付总金额")} &nbsp;:</label>
							<div class="col-sm-2">
								<div class="input-group"> 
									<input class="form-control input-sm summaryOfCoping-input" isOneEnter="${storeEnterAuth.status=='10'?'0':'1'}"  ${storeEnterAuth.status=='10'?'':'readonly'} name="summaryOfCoping" type="text" placeholder="" value="${empty storeEnterAuth.summaryOfCoping?'0':storeEnterAuth.summaryOfCoping}">
									<div class="input-group-addon">${fns:fy("元")}</div>
								</div>
								<div class="change" style="border: 1px solid #ccc;display: none;"> 
									<div class="change1" style="cursor: pointer;margin: 5px;">10000</div>
									<div class="change2" style="cursor: pointer;margin: 5px;">20000</div>
									<div class="change3" style="cursor: pointer;margin: 5px;">${fns:fy("支持自定义填写金额")}</div>
								</div>
							</div>
							<div class="col-sm-5">
								 <p class="help-block">10000，20000，${fns:fy("支持自定义填写金额")}<p>
							</div>
						</div>
						<c:choose>
							<c:when test="${storeEnterAuth.status=='10' || storeEnterAuth.status=='20' || storeEnterAuth.status=='30'}">
								<div class="form-group">
									<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("一审审核意见")}&nbsp;:</label>
									<div class="col-sm-5">
										<textarea class="form-control" ${storeEnterAuth.status=='10'?'':'readonly'}  rows="5" name="oneAuditOpinion" >${storeEnterAuth.oneAuditOpinion}</textarea>
									</div>
									<div class="col-sm-5">
										<p class="help-block"><p>
									</div>
								</div>
							</c:when>
						</c:choose> --%>
						<!--店铺经营信息结束 -->
						<!--付款信息开始 -->
						<%-- <c:choose>
							<c:when test="${storeEnterAuth.status!='10' && storeEnterAuth.status!='20' && storeEnterAuth.status!='30'}">
								<h4>${fns:fy("付款信息")}</h4>
								<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
									<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("付款凭证")}&nbsp;:</label>
									<div class="col-sm-10">
										<div>
											<c:forEach var="paymentVoucherPath" items="${paymentVoucherPathsList}">
												<a href="${ctxfs}${paymentVoucherPath}?accessKey=${generateAccessKey}" target="_blank">
													<image src="${ctxfs}${paymentVoucherPath}@200x200?accessKey=${generateAccessKey}"></image>
												</a>
											</c:forEach>
										</div>
									</div>
									<div class="col-sm-5">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("付款说明")}&nbsp;:</label>
									<div class="col-sm-5">
										<textarea class="form-control" readonly="readonly" rows="5" name="paymentInstructions" >${storeEnterAuth.paymentInstructions}</textarea>
									</div>
									<div class="col-sm-5">
										<p class="help-block"><p>
									</div>
								</div>
								<c:if test="${storeEnterAuth.status=='40' || storeEnterAuth.status=='50' || storeEnterAuth.status=='60'}">
									<div class="form-group">
										<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("二审审核意见")}&nbsp;:</label>
										<div class="col-sm-5">
											<textarea class="form-control" ${storeEnterAuth.status=='40'?'':'readonly'}  rows="5" name="twoAuditOpinion" >${storeEnterAuth.twoAuditOpinion}</textarea>
										</div>
										<div class="col-sm-5">
											<p class="help-block"><p>
										</div>
									</div>
								</c:if>
							</c:when>
						</c:choose> --%>
						<!--付款信息结束 -->
						<!--二审审核意见开始 -->
						<c:if test="${storeEnterAuth.status=='40' || storeEnterAuth.status=='50' || storeEnterAuth.status=='60'}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("审核意见")}&nbsp;:</label>
								<div class="col-sm-5">
									<textarea class="form-control" ${storeEnterAuth.status=='40'?'':'readonly'}  rows="5" name="twoAuditOpinion" >${storeEnterAuth.twoAuditOpinion}</textarea>
								</div>
								<div class="col-sm-5">
									<p class="help-block"><p>
								</div>
							</div>
						</c:if>
						<!--二审审核意见结束 -->
						<!--入驻信息开始 -->
						<c:choose>
							<c:when test="${storeEnterAuth.status=='70' || storeEnterAuth.status=='80' || storeEnterAuth.status=='90'}">
								<h4>${fns:fy("入驻信息更改审核")}</h4>
								<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
									<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("入驻信息审核意见")}&nbsp;:</label>
									<div class="col-sm-5">
										<textarea class="form-control" ${storeEnterAuth.status=='70'?'':'readonly'} rows="5" name="auditOpinion" >${storeEnterAuth.auditOpinion}</textarea>
									</div>
									<div class="col-sm-5">
										<p class="help-block"><p>
									</div>
								</div>
							</c:when>
						</c:choose>
						<!--入驻信息结束 -->
						<c:if test="${storeEnterAuth.status=='40' || storeEnterAuth.status=='70'}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess"></label>
								<div class="col-sm-5">
									<shiro:hasPermission name="store:storeEnterAuth:auth">
									<c:if test="${storeEnterAuth.status=='40'}">
										<button type="submit" class="btn btn-danger" name="auth" value="3">
											<i class="fa fa-ban"></i> ${fns:fy("审核失败")}
										</button>
										<button type="submit" class="btn btn-success" name="auth" value="4">
											<i class="fa fa-check-square"></i> ${fns:fy("审核成功")}
										</button>
									</c:if>
									<c:if test="${storeEnterAuth.status=='70'}">
										<button type="submit" class="btn btn-danger" name="auth" value="5">
											<i class="fa fa-ban"></i> ${fns:fy("审核失败")}
										</button>
										<button type="submit" class="btn btn-success" name="auth" value="6">
											<i class="fa fa-check-square"></i> ${fns:fy("审核成功")}
										</button>
									</c:if>
									</shiro:hasPermission>
								</div>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>