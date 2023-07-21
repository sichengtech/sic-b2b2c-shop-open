<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>定时任务日志管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTimedTaskLogForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysTimedTaskLog.sttlId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}定时任务日志</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysTimedTaskLog/list.do"> <i class="fa fa-user"></i> 定时任务日志列表</a></li>
				<shiro:hasPermission name="sys:sysTimedTaskLog:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 定时任务日志${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>查看定时任务日志。</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysTimedTaskLog/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="sttlId" value="${sysTimedTaskLog.sttlId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 执行开始时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="startTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${sysTimedTaskLog.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写执行开始时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 执行结束时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="endTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${sysTimedTaskLog.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写执行结束时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 执行结果&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="result"  maxlength="64" class="form-control input-sm" value="${sysTimedTaskLog.result}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写执行结果<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 执行状态&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="64" class="form-control input-sm" value="${sysTimedTaskLog.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写执行状态<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sys:sysTimedTaskLog:edit">
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