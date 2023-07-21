<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('账户信息')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/user/userMemberSave.js"></script>
<script type="text/javascript" src="${ctx}/views/member/logistics/selectArea.js"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var usernameMax=${siteRegister.usernameMax};
	var usernameMin=${siteRegister.usernameMin};
	var disableUsername='${siteRegister.disableUsername}';
</script>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('会员资料')} > ${fns:fy('账户信息')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('账户信息')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="myform">
				<form class="sui-form form-horizontal" method="post" id="inputForm" action="${ctxm}/user/userMember/save2.htm">
					<div class="control-group">
						<label class="control-label">${fns:fy('头像')}:</label>
						<div class="controls">
							<div class="input-append">
								<input type="hidden" class="imgPath" name="headPicPath" value="${userMain.userMember.headPicPath}"/>
								<div id="vessel"></div>
							</div>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('请上传用户头像')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group">
					  <label class="control-label"><font color="red">*</font>${fns:fy('用户名称')}:</label>
					  <div class="controls">
					  	<input type="hidden" class="input-large" id="oldLoginName" name="oldLoginName" value="${userMain.loginName}" maxlength="64">
					  	<input type="text" class="input-large" id="loginName" name="loginName" value="${userMain.loginName}" maxlength="64">
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写用户名称')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('邮箱')}:</label>
					  <div class="controls">
						${userMain.email}
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('用户邮箱,如果未绑定请尽快绑定')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('手机号')}:</label>
					  <div class="controls">
						${userMain.mobile}
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('用户手机号,如果未绑定请尽快绑定')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('真实姓名')}:</label>
					  <div class="controls">
						<input type="text" class="input-large" name="realName" value="${userMain.userMember.realName}" maxlength="64">
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写用户真是姓名')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('性别')}:</label>
					  <div class="controls">
					  	<c:forEach items="${fns:getDictList('sex')}" var="item">
	                     	<input type="radio" name="sex" value="${item.value}" ${item.value==userMain.userMember.sex?"checked":""}/><span>${item.label}</span>
	                    </c:forEach> 
	                    <div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请选择用户性别')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('生日')}:</label>
					  <div class="controls">
						<input type="text" class="input-large" name="birthday" 
						value="<fmt:formatDate value="${userMain.userMember.birthday}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请选择用户生日')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('所在地区')}:</label>
					  <input type="hidden" name="provinceName" value="${userMain.userMember.provinceName}">
					  <input type="hidden" name="cityName" value="${userMain.userMember.cityName}">
					  <input type="hidden" name="districtName" value="${userMain.userMember.districtName}">
					  <div class="controls">
						<span class="sui-dropdown dropdown-bordered select">
							<span class="dropdown-inner">
								<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input type="hidden" name="provinceId" id="provinceId" value="${userMain.userMember.provinceId}"  onchange="selectCity(this)"><i class="caret"></i>
									<span id="provinceName">${empty userMain.userMember.provinceId?fns:fy('省'):userMain.userMember.provinceName}</span>
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
									<input type="hidden" name="cityId" id="cityId" value="${userMain.userMember.cityId}" onchange="selectDistrict(this)"><i class="caret"></i>
									<span id="cityName">${empty userMain.userMember.cityId?fns:fy('市'):userMain.userMember.cityName}</span>
								</a>
							 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
							 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
							 	</ul>
							</span>
					 	</span>
						<span class="sui-dropdown dropdown-bordered select">
							<span class="dropdown-inner">
								<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input type="hidden" name="districtId" id="districtId" value="${userMain.userMember.districtId}"><i class="caret"></i>
									<span id="districtName">${empty userMain.userMember.districtId?fns:fy('县'):userMain.userMember.districtName}</span>
								</a>
							 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
							 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
							 	</ul>
							</span>
					 	</span>
						<input type="text" class="input-large" name="detailedAddress" value="${userMain.userMember.detailedAddress}" maxlength="40">
					 	<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写所在地')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('qq')}:</label>
					  <div class="controls">
						<input type="text" class="input-large" name="qq" value="${userMain.userMember.qq}" maxlength="64">
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写qq')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('微博')}:</label>
					  <div class="controls">
						<input type="text" class="input-large" name="microblog" value="${userMain.userMember.microblog}" maxlength="64">
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写微博')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label">${fns:fy('微信')}:</label>
					  <div class="controls">
						<input type="text" class="input-large" name="weChat" value="${userMain.userMember.weChat}" maxlength="64">
						<div class="sui-msg msg-tips msg-naked">
						  <div class="msg-con">${fns:fy('请填写微信')}</div>
						  <s class="msg-icon"></s>
						</div>
					  </div>
					</div>
			        <div class="control-group button">
		              <label class="control-label">　</label>
		              	<button type="submit" class="sui-btn btn-xlarge btn-primary"><i class="sui-icon icon-tb-roundcheck"></i> ${fns:fy('保存')}</button>
		            </div>
				</form>
			</dd>
		</dl>
	</div>
</body>
</html>
