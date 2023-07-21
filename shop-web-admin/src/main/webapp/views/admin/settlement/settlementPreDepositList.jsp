<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>预存款明细管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementPreDepositList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">预存款明细列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>此处展示了预存款详细的变更日志信息。</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
				<form action="${ctxa}/settlement/settlementPreDeposit/list.do" method="get">
					<div class="col-sm-2 col-md-offset-1">
						<input type="text" name="userName" maxlength="19" class="form-control input-sm" placeholder="会员名称" value="${username}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="operationDescribe" maxlength="1024" class="form-control input-sm" placeholder="操作描述" value="${settlementPreDeposit.operationDescribe}"/>
					</div>
					<div class="col-sm-2">
						<div class="input-group"> 
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${settlementPreDeposit.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="操作开始时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group"> 
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${settlementPreDeposit.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="操作结束时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th></th>
						<!-- <th>日志ID</th> -->
						<th>会员名称</th>
						<th>可用金额(元)</th>
						<th>冻结金额(元)</th>
						<th>操作金额</th>
						<th>操作时间</th>
						<th>操作描述</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="settlementPreDeposit" varStatus="index">
					<tr class="${index.count}">
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<%-- <td><a href="${ctxa}/settlement/settlementPreDeposit/edit1.do?preDepositId=${settlementPreDeposit.preDepositId}">${settlementPreDeposit.preDepositId}</a></td> --%>
						<td>${settlementPreDeposit.userMain.loginName}</td>
						<td>${settlementPreDeposit.availableMoney}</td>
						<td>${settlementPreDeposit.frozenMoney}</td>
						<td>${settlementPreDeposit.operationMoney}</td>
						<td><fmt:formatDate value="${settlementPreDeposit.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${settlementPreDeposit.operationDescribe}</td>
					</tr>
					<tr id="${index.count}" class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">会员ID : ${settlementPreDeposit.UId}</p>
							<p datano="0" columnno="1" class="dt-grid-cell">管理员编号 : ${settlementPreDeposit.adminId}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">管理员 :${settlementPreDeposit.user.loginName}</p>
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