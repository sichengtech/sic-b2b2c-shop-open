<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("第三方账户资金流水管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountThirdpartySnList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("第三方账户资金流水列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("第三方账户资金流水列表")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("第三方账户资金流水管理是产看第三方账户流水信息")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
				<form action="${ctxa}/account/accountThirdpartySn/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
					</div>
					<div class="col-sm-2">
						<input type="text" name="serialNumber"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("请输入流水号")}" value="${accountThirdpartySn.serialNumber}"/>
					</div>
					<div class="col-sm-2">
						<select name="moneyFlowType" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择资金流类型")}--</option>
							<c:forEach items="${fns:getDictList('account_money_flow_type')}" var="item">
							<option value="${item.value}" ${item.value==accountThirdpartySn.moneyFlowType?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
							<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountThirdpartySn.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:true});" placeholder="${fns:fy("请选择创建开始时间")}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountThirdpartySn.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:true});" placeholder="${fns:fy("请选择创建结束时间")}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("编号")}</th>
						<th>${fns:fy("流水号")}</th>
						<th>${fns:fy("资金流类型")}</th>
						<th>${fns:fy("交易金额")}</th>
						<th>${fns:fy("交易渠道")}</th>
						<th>${fns:fy("外部交易记录号")}</th>
						<th>${fns:fy("交易备注")}</th>
						<th>${fns:fy("创建时间")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountThirdpartySn">
					<tr>
						<td>${accountThirdpartySn.id}</td>
						<td>${accountThirdpartySn.serialNumber}</td>
						<td>${fns:getDictLabel(accountThirdpartySn.moneyFlowType, 'account_money_flow_type', '')}</td>
						<td>${not empty accountThirdpartySn.money && accountThirdpartySn.money ne '0.000'?accountThirdpartySn.money:'0'}</td>
						<td>
							<c:if test="${accountThirdpartySn.payWayName eq '-1'}">${fns:fy("线下支付")}</c:if>
							<c:if test="${accountThirdpartySn.payWayName ne '-1'}">${accountThirdpartySn.payWayName}</c:if>
						</td>
						<td>
							<c:if test="${accountThirdpartySn.outerTradeNo eq '-1'}">${fns:fy("线下支付")}</c:if>
							<c:if test="${accountThirdpartySn.outerTradeNo ne '-1'}">${accountThirdpartySn.outerTradeNo}</c:if>
						</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${accountThirdpartySn.tradeRemarks}</textarea></td>
						<td><fmt:formatDate value="${accountThirdpartySn.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy("无查询结果")}</div></td>
						</tr>
					</c:if>
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