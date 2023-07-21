<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("消息模板管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteEmailTemplateForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteMessageTemplate.id?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑邮件消息模板"):fns:fy("添加邮件消息模板")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteMessageTemplate/list.do"> <i class="fa fa-user"></i> </a></li>
				<shiro:hasPermission name="site:siteMessageTemplate:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("编辑邮件消息模板"):fns:fy("添加邮件消息模板")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("无")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteMessageTemplate/email2.do" method="post">
						<input type="hidden" name="id" value="${siteMessageTemplate.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("模板编号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" readonly="readonly" name="num"  maxlength="64" class="form-control input-sm" value="${siteMessageTemplate.num}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写模板编号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("模板名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" readonly="readonly" name="name"  maxlength="64" class="form-control input-sm" value="${siteMessageTemplate.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写模板名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("邮件")}：${fns:fy("是否发送邮件")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="emailOpen" value="1" ${"1"==siteMessageTemplate.emailOpen?"checked":""} 
								data-size="small" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写邮件")}：${fns:fy("是否发送邮件")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("邮件")}：${fns:fy("模板标题")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="emailTitle"  maxlength="255" class="form-control input-sm" value="${siteMessageTemplate.emailTitle}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写邮件标题")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("邮件")}：${fns:fy("邮件模板内容")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="emailContent" class="form-control input-sm" rows="10" data-parsley-id="8">${siteMessageTemplate.emailContent}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写邮件模板内容")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteMessageTemplate:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
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