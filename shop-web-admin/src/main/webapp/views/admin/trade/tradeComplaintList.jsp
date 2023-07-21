<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('投诉管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeComplaintList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('投诉列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('投诉管理.投诉列表.操作提示1')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示2')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示3')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示4')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
				<form action="${ctxa}/trade/tradeComplaint/list.do" method="get" id="searchForm">
					<div class="col-sm-6 col-md-offset-4">
						<div class="iconic-input right">
							<a href="javaScript:;" class="searchBtn"><i class="fa fa-search"></i></a>
							<input type="text" name="userName" class="form-control input-sm pull-right" maxLength="64" placeholder="${fns:fy('请输入会员名')}" style="border-radius: 30px;max-width:250px;" value="${userName}"/>
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						</div>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('投诉编号')}</th>
						<th>${fns:fy('会员名')}</th>
						<th>${fns:fy('投诉内容')}</th>
						<th>${fns:fy('投诉主题')}</th>
						<th>${fns:fy('被投诉店铺')}</th>
						<th>${fns:fy('投诉时间')}</th>
						<th>${fns:fy('投诉状态')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="tradeComplaint">
					<tr>
						<td><a href="${ctxa}/trade/tradeComplaint/edit1.do?complaintId=${tradeComplaint.complaintId}">${tradeComplaint.complaintId}</a></td>
						<td>${tradeComplaint.userMain.loginName}</td>
						<td>
							<textarea class="form-control input-sm" rows="2" readonly="readonly">${tradeComplaint.content}</textarea>
						</td>
						<td>${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</td>
						<td>${tradeComplaint.store.name}</td>
						<td><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}</td>
						<td>
							<shiro:hasPermission name="trade:tradeComplaint:auth">
							<c:if test="${tradeComplaint.status != '50'}">
								<a class="btn btn-info btn-sm"  href="${ctxa}/trade/tradeComplaint/edit1.do?complaintId=${tradeComplaint.complaintId}&stat=1">
									<i class="fa fa-gears"></i> ${fns:fy('处理')}
								</a>
							</c:if>
							</shiro:hasPermission>
							<c:if test="${tradeComplaint.status == '50'}">
								<a class="btn btn-info btn-sm" style="background-color: #5bc0de;border-color: #5bc0de;" href="${ctxa}/trade/tradeComplaint/edit1.do?complaintId=${tradeComplaint.complaintId}&stat=2">
									<i class="fa fa-eye"></i> ${fns:fy('查看')}
								</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>