<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("定时任务管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTimedTaskForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysTimedTask.sttId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑定时任务"):fns:fy("添加定时任务")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysTimedTask/list.do"> <i class="fa fa-user"></i> ${fns:fy("定时任务列表")}</a></li>
				<shiro:hasPermission name="${isEdit?'sys:sysTimedTask:edit':'sys:sysTimedTask:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("定时任务编辑"):fns:fy("定时任务添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.定时任务.操作提示1")}</li>
					<li>${fns:fy("系统设置.定时任务.操作提示2")}</li>
					<li>${fns:fy("系统设置.定时任务.操作提示3")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysTimedTask/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="sttId" value="${sysTimedTask.sttId}">
						<input id="oldNum" type="hidden" value="${sysTimedTask.timedTaskNum}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("编号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="timedTaskNum"  maxlength="10" class="form-control input-sm" value="${sysTimedTask.timedTaskNum}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写编号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("任务名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taskName"  maxlength="64" class="form-control input-sm" value="${sysTimedTask.taskName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写任务名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("任务说明")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taskExplain"  maxlength="64" class="form-control input-sm" value="${sysTimedTask.taskExplain}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写任务说明")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("执行时间")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="taskTime"  maxlength="64" class="form-control input-sm" value="${sysTimedTask.taskTime}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写执行时间")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("执行时间说明")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="timeExplain"  maxlength="64" class="form-control input-sm" value="${sysTimedTask.timeExplain}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写执行时间说明")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("状态")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="status" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('is_status')}" var="item">
									<option value="${item.value}" ${item.value==sysTimedTask.status?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写状态")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="${isEdit?'sys:sysTimedTask:edit':'sys:sysTimedTask:save'}">
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