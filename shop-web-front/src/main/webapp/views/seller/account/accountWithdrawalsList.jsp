<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('提现列表')}</title>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/account/accountWithdrawalsList.js"></script>
</head>
<body class="withdrawal">
	<div class="main-content" style="overflow:visible">
		<div class="sui-row-fluid">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('提现列表')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('提现列表')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('查看用户的提现记录')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="ml10 mr10">
					<form id="searchForm" action="${ctxs}/account/withdrawals/list.htm" class="sui-form form-horizontal mt20">
						<div class="control-group">
							<input type="hidden" name="accountId" value="${accountWithdrawals.accountId}">
							<label style="margin: 0;">${fns:fy('银行卡号：')}</label>
							<div class="controls">
								<input type="text" id="cardNumber" placeholder="${fns:fy('请输入银行卡号')}" name="bankCardNumber" value="${bankCardNumber}" class="" style="width: 150px;">
							</div>
							<label style="margin-left: 10px;">${fns:fy('提现单号：')}</label>
							<div class="controls">
								<input type="text" id="withdrawalsId" placeholder="${fns:fy('请输入提现单号')}" name="id" value="${accountWithdrawals.id}" class="" style="width: 150px;">
							</div>
							<%--<div class="controls">
								<div class="input-daterange" data-toggle="datepicker">
									<label style="margin-left: 10px;">${fns:fy('时间范围：')}</label>
									<input type="text" class="input-medium input-date" placeholder="${fns:fy('请选择起始日期')}" name="beginCreateDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});" value="<fmt:formatDate value="${accountWithdrawals.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
									<span> ${fns:fy('至')} </span>
									<input type="text" class="input-medium input-date" placeholder="${fns:fy('请选择结束日期')}" name="endCreateDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});" value="<fmt:formatDate value="${accountWithdrawals.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
								</div>
							</div>
							<button type="submit" class="sui-btn btn-primary" style="margin: 0 0 0 5px;">${fns:fy('查询')}</button>
							<a href="${ctxs}/account/withdrawals/save1.htm?accountId=${accountWithdrawals.accountId}" class="sui-btn btn-primary" style="margin: 0 0 0 5px;">${fns:fy('提现')}</a>--%>
						</div>
						<div class="control-group">
							<div class="controls">
								<div class="input-daterange" data-toggle="datepicker">
									<label style="margin-left: 10px;">${fns:fy('时间范围：')}</label>
									<input type="text" class="input-medium input-date" placeholder="${fns:fy('请选择起始日期')}" name="beginCreateDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"
										   value="<fmt:formatDate value="${accountWithdrawals.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 120px;">
									<span> ${fns:fy('至')} </span>
									<input type="text" class="input-medium input-date" placeholder="${fns:fy('请选择结束日期')}" name="endCreateDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});"
										   value="<fmt:formatDate value="${accountWithdrawals.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 120px;">
								</div>
							</div>
							<button type="submit" class="sui-btn btn-primary" style="margin: 0 0 0 5px;">${fns:fy('查询')}</button>
							<a href="${ctxs}/account/withdrawals/save1.htm?accountId=${accountWithdrawals.accountId}" class="sui-btn btn-primary" style="margin: 0 0 0 5px;">${fns:fy('提现')}</a>
						</div>
					</form>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr>
									<th>${fns:fy('提现单号')}</th>
									<th>${fns:fy('提现账户')}</th>
									<th>${fns:fy('银行卡号')}</th>
									<th>${fns:fy('提现金额')}</th>
									<th>${fns:fy('审核状态')}</th>
									<th>${fns:fy('审核理由')}</th>
									<th>${fns:fy('提现状态')}</th>
									<th>${fns:fy('提现时间')}</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="accountWithdrawals">
									<tr>
										<td>${accountWithdrawals.id }</td>
										<td>${fns:getDictLabel(accountWithdrawals.accountUser.accountType, 'account_user_type', '')}</td>
										<td>${accountWithdrawals.accountTiedCard.bankCardNumber }</td>
										<td>${accountWithdrawals.money}${fns:fy('元')}</td>
										<td>${fns:getDictLabel(accountWithdrawals.auditStatus, 'account_audit_status', '')}</td>
										<td><textarea rows="3" cols="20"  disabled="disabled">${accountWithdrawals.auditOpinion}</textarea></td>
										<td>${fns:getDictLabel(accountWithdrawals.isPay, 'account_is_pay', '')}</td>
										<td><fmt:formatDate value="${accountWithdrawals.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
								</c:forEach>
								<c:if test="${fn:length(page.list)=='0'}">
									<tr>
										<td colspan="8" class="no_product" style="height:200px;text-align: center;color: #9a9a9a;font-size:14px;">
											${fns:fy('很遗憾，暂无数据！')}
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
						<%@ include file="/views/seller/include/page.jsp"%>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>