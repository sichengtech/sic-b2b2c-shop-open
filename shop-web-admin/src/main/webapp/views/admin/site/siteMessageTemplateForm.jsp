<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("消息模板管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteMessageTemplateForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteMessageTemplate.id?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑消息模板"):fns:fy("添加消息模板")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteMessageTemplate/list.do"> <i class="fa fa-user"></i> ${fns:fy("消息模板列表")}</a></li>
				<shiro:hasPermission name="${isEdit?'site:siteMessageTemplate:edit':'site:siteMessageTemplate:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("编辑消息模板"):fns:fy("添加消息模板")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.消息模板.操作提示1")}</li>
					<li>${fns:fy("网站设置.消息模板.操作提示3")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteMessageTemplate/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteMessageTemplate.id}">
						<input id="oldNum" name="oldNum" value="${siteMessageTemplate.num}" type="hidden"/>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("模板编号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="num"  maxlength="64" class="form-control input-sm" value="${siteMessageTemplate.num}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写模板编号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("模板名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${siteMessageTemplate.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写模板名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("类型")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('site_message_type')}" var="item">
									<option value="${item.value}" ${item.value==siteMessageTemplate.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择消息类型")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${empty siteMessageTemplate.sort?'10':siteMessageTemplate.sort}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写排序")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否开启站内信")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${siteMessageTemplate.isOpen eq 0?"":"checked"} data-size="small" style="display: none"
								value="1" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}" name="isOpen" />
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择是否开启")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("站内信模板内容")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="msgContent" class="form-control input-sm" maxlength="512" rows="10" data-parsley-id="8">${siteMessageTemplate.msgContent}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写站内信模板内容")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否发送短信")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${siteMessageTemplate.smsOpen eq 0?"":"checked"} data-size="small" style="display: none"
								value="1" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}" name="smsOpen" />

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择是否发送短信")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("短信模板内容")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="smsContent" class="form-control input-sm" maxlength="512" rows="10" data-parsley-id="8">${siteMessageTemplate.smsContent}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写短信模板内容")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否发送邮件")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${siteMessageTemplate.emailOpen eq 0?"":"checked"} data-size="small" style="display: none"
								value="1" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}" name="emailOpen" />
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择是否发送邮件")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("邮件模板标题")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="emailTitle"  maxlength="255" class="form-control input-sm" value="${siteMessageTemplate.emailTitle}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写邮件模板标题")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("邮件模板内容")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="emailContent" class="form-control input-sm" rows="10" data-parsley-id="8">${siteMessageTemplate.emailContent}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写邮件模板内容")}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="${isEdit?'site:siteMessageTemplate:edit':'site:siteMessageTemplate:save'}">
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