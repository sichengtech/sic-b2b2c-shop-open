<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('结算主任务管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementTaskMainForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementTaskMain.taskMainId?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${isEdit?edit:save}${fns:fy('结算主任务')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementTaskMain/list.do"> <i class="fa fa-user"></i> ${fns:fy('结算主任务列表')}</a></li>
				<shiro:hasPermission name="settlement:settlementTaskMain:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('结算主任务')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('结算任务.结算添加.操作提示1')}</li>
					<li>${fns:fy('结算任务.结算添加.操作提示2')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementTaskMain/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="taskMainId" value="${settlementTaskMain.taskMainId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('任务日期')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format=""  name="beginTime" autocomplete="off"
									   value="<fmt:formatDate value="${tradeOrder.beginPlaceOrderTime}" pattern="yyyy-MM-dd"/>"  format="yyyy-MM-dd" placeholder="${fns:fy('任务日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
								<input type="hidden" class="form-control input-sm J-date-start input-date" nc-format=""  name="endTime"
									   value="<fmt:formatDate value="${tradeOrder.beginPlaceOrderTime}" pattern="yyyy-MM-dd"/>"  format="yyyy-MM-dd" placeholder="${fns:fy('任务日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必选项，请选择任务日期')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="settlement:settlementTaskMain:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
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