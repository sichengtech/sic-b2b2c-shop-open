<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("系统变量管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysVariableForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysVariable.varId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑系统变量"):fns:fy("添加系统变量")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysVariable/list.do"> <i class="fa fa-user"></i> ${fns:fy("系统变量列表")}</a></li>
				<shiro:hasPermission name="${isEdit?'sys:sysVariable:edit':'sys:sysVariable:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("系统变量编辑"):fns:fy("系统变量添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.系统变量.操作提示2")}</li>
					<li>${fns:fy("系统设置.系统变量.操作提示3")}</li>
					<li>${fns:fy("系统设置.系统变量.操作提示4")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysVariable/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="varId" value="${sysVariable.varId}">
						<input id="oldName" name="oldName" value="${sysVariable.name}" type="hidden"/>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("变量名")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${sysVariable.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写变量名")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("变量值")}1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="value"  maxlength="1024" class="form-control input-sm" value="${sysVariable.value}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写变量值")}1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("变量值")}2&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="valueClob" class="form-control input-sm" rows="5" data-parsley-id="8">${sysVariable.valueClob}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写变量值")}2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("描述")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="bewrite" class="form-control input-sm" maxlength="255" rows="5" data-parsley-id="8">${sysVariable.bewrite}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写描述")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="${isEdit?'sys:sysVariable:edit':'sys:sysVariable:save'}">
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