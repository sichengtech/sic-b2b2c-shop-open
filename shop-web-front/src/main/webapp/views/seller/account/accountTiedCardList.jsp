<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('绑卡列表')}</title>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/account/accountTiedCardList.js"></script>
</head>
<body class="withdrawal">
	<div class="main-content" style="overflow:visible">
		<div class="sui-row-fluid">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('绑卡列表')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('绑卡列表')}</li>
					</ul>
				</dt>

				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('查看用户的绑卡记录')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="ml10 mr10">
					<form id="searchForm" action="${ctxs}/account/tiedCard/list.htm" class="sui-form form-horizontal mt20">
						<div class="control-group">
							<label style="margin: 0;">${fns:fy('开户行：')}</label>
							<div class="controls">
								<input type="text" id="accountOpeningBank" placeholder="" name="accountOpeningBank" class="input-large" value="${accountTiedCard.accountOpeningBank}">
							</div>
							<label style="margin-left: 10px;">${fns:fy('银行卡号：')}</label>
							<div class="controls">
								<input type="text" id="bankCardNumber" placeholder="" name="bankCardNumber" class="input-large" value="${accountTiedCard.bankCardNumber}">
							</div>
							<button type="submit" class="sui-btn btn-primary" style="margin: 0 0 0 10px;">${fns:fy('查询')}</button>
							<a href="${ctxs}/account/tiedCard/save1.htm" class="sui-btn btn-primary" style="margin: 0 0 0 15px;">${fns:fy('添加银行卡')}</a>
						</div>
					</form>
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th>${fns:fy('开户行')}</th>
								<th>${fns:fy('银行卡号')}</th>
								<th>${fns:fy('开户人')}</th>
								<th>${fns:fy('身份证号')}</th>
								<th>${fns:fy('审核状态')}</th>
								<th>${fns:fy('申请时间')}</th>
								<th>${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="accountTiedCard">
								<tr>
									<td>${accountTiedCard.accountOpeningBank }</td>
									<td>${accountTiedCard.bankCardNumber }</td>
									<td>${accountTiedCard.payee }</td>
									<td>${accountTiedCard.idNumber }</td>
									<td>${fns:getDictLabel(accountTiedCard.auditStatus, 'account_audit_status', '')}</td>
									<td><fmt:formatDate value="${accountTiedCard.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>
										<c:if test="${accountTiedCard.auditStatus eq '2' }">
										<a href="${ctxs}/account/tiedCard/edit1.htm?tiedCardId=${accountTiedCard.tiedCardId}">${fns:fy('编辑')}</a>
										</c:if>
										<button type="button" href="${ctxs}/account/tiedCard/delete.htm?tiedCardId=${accountTiedCard.tiedCardId}" class="sui-btn btn-danger deleteCancel">${fns:fy('删除')}</button>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${fn:length(page.list)=='0'}">
								<tr>
									<td colspan="7" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;font-size: 14px;">
										<i class="sui-icon icon-tb-goods"></i> ${fns:fy('很遗憾，暂无数据！')}
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<c:if test="${fn:length(page.list)>'0'}">
						<%@ include file="/views/seller/include/page.jsp"%>
					</c:if>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>