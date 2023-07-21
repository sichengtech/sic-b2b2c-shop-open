<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('个人信息')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysUserInfo.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('个人信息')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('公共.个人信息.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>	 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/user/saveInfo.do" method="post">
						<input type="hidden" name="id" value="${user.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('管理员头像')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="photo" value="${user.photo}"/>
								<div id="vessel"></div>
							</div>	
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('会员头像上传提示')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('姓名')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" readonly="readonly" name="name" value="${user.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写姓名')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">QQ&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="qq" value="${user.qq}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写QQ')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('邮箱')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="email" value="${user.email}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写邮箱')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('电话')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="phone" value="${user.phone}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写电话')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('手机')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="mobile" value="${user.mobile}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写手机')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('用户类型')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${fns:getDictLabel(user.userType, 'sys_user_type', fns:fy('无'))}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('用户角色')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${user.roleNames}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('上次登录')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;${fns:fy('时间')}：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('备注')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control" name="remarks">${user.remarks}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写备注')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')} 
								</button>
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