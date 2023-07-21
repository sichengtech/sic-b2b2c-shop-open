<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购交易凭证管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeVoucherList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购交易凭证列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('采购交易凭证列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('采购交易凭证管理是查看交易凭证信息')}</li>
					<li>${fns:fy('管理员可以对交易凭证进行审核')}</li>
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
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>${fns:fy('交易凭证号')}</th>
						<th>${fns:fy('订单号')}</th>
						<th>${fns:fy('凭证文件')}</th>
						<th>${fns:fy('上传用户')}</th>
						<th>${fns:fy('用户手机号')}</th>
						<th>${fns:fy('审核状态')}</th>
						<th>${fns:fy('上传时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseTradeVoucher">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${purchaseTradeVoucher.tradeVoucherId}</td>
						<td><a href="${ctxa}/purchase/purchaseTradeOrder/list.do?purchaseTradeId=${purchaseTradeVoucher.purchaseTradeId}">${purchaseTradeVoucher.purchaseTradeId}</a></td>
						<td>
							<c:if test="${not empty purchaseTradeVoucher.filePath}">
								 <a href="${ctxfs}${purchaseTradeVoucher.filePath}?accessKey=${generateAccessKey}"  target="_blank">
										 ${fns:fy('查看')}
								 </a>
							</c:if>
							<c:if test="${empty purchaseTradeVoucher.filePath}">${fns:fy('无')}</c:if>
						</td>
						<td>${purchaseTradeVoucher.userMain.loginName}</td>
						<td>${purchaseTradeVoucher.userMain.mobile}</td>
						<td>${fns:getDictLabel(purchaseTradeVoucher.status, 'purchase_voucher_status', '')}</td>
						<td><fmt:formatDate value="${purchaseTradeVoucher.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<shiro:hasPermission name="purchase:purchaseTradeVoucher:auth">
							<c:if test="${purchaseTradeVoucher.status!=30}">
								<a class="btn btn-success btn-sm" href="${ctxa}/purchase/purchaseTradeVoucher/auth1.do?tradeVoucherId=${purchaseTradeVoucher.tradeVoucherId}">
									<i class="fa fa-edit"></i> ${fns:fy('审核')}
								</a>
							</c:if>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="16" >
							<p datano="0" columnno="0" class="dt-grid-cell">
								${fns:fy('交易金额：')}${purchaseTradeVoucher.money}${fns:fy('元')}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell">
								${fns:fy('交易凭证类型：')}${fns:getDictLabel(purchaseTradeVoucher.type, 'purchase_voucher_type', '')}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break: break-all;">
								${fns:fy('审核理由：')}${purchaseTradeVoucher.auditGrounds}
							</p>
						</td> 
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy('无查询结果')}</div></td>
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