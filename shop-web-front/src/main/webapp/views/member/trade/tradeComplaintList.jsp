<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('交易投诉')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeComplaintList.js"></script>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > ${fns:fy('交易投诉')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('交易投诉')}
			</dt>
			<dd>
				<ul class="sui-nav nav-tabs">
					<li class="${empty stat?'active':''}"><a href="${ctxm}/trade/tradeComplaint/list.htm">${fns:fy('全部投诉')}</a></li>
					<li class="${stat eq '1'?'active':''}"><a href="${ctxm}/trade/tradeComplaint/list.htm?stat=1" >${fns:fy('进行中')}</a></li>
					<li class="${stat eq '2'?'active':''}"><a href="${ctxm}/trade/tradeComplaint/list.htm?stat=2" >${fns:fy('已完成')}</a></li>
				</ul>
			</dd>
			<dd class="myrefund pl20 pr20">
				<!-- 列表开始 -->
				<sys:message content="${message}"/>
				<table class="sui-table">
					<thead>
						<tr>
							<th width="10%"></th>
							<th width="30%" class="center">${fns:fy('投诉商品')}</th>
							<th width="10%" class="center">${fns:fy('投诉主题')}</th>
							<th width="15%" class="center">${fns:fy('投诉时间')}</th>
							<th width="10%" class="center">${fns:fy('投诉状态')}</th>
							<th width="20%" class="center">${fns:fy('操作')}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="tradeComplaint">
							<tr>
								<td class="picture">
									<img src="${ctxfs}${tradeComplaint.tradeOrderItem.thumbnailPath}" style="width: 80px;height: 80px;"
									onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
								</td>
								<td class="">
									<div class="productName"><a href="${ctxf}/detail/${tradeComplaint.tradeOrderItem.PId}.htm" target="_blank">${tradeComplaint.tradeOrderItem.name}</a></div>
									<div class="unstyled">${fns:fy('商家：')}${tradeComplaint.store.name}</div>
								</td>
								<td class="center">${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</td>
								<td class="center"><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}</td>
								<td class="center">
									<a href="${ctxm}/trade/tradeComplaint/save1.htm?complaintId=${tradeComplaint.complaintId}" class="sui-btn btn-info"><i class="sui-icon icon-tb-search"></i> ${fns:fy('查看详情')}</a>
									<c:if test="${tradeComplaint.status eq '10'}">
										<button type="button" class="sui-btn btn-danger deleteSure" style="margin-top: 10px;" href="${ctxm}/trade/tradeComplaint/delete.htm?complaintId=${tradeComplaint.complaintId}">
											<i class="sui-icon icon-ban-circle"></i> ${fns:fy('删除投诉')}
										</button>
									</c:if>
								</td>
							</tr>
						</c:forEach>
						<!-- 没有数据提示开始 -->
						<c:if test="${fn:length(page.list)=='0'}">
							<tr>
								<td colspan="6" class="no_product" style="text-align: center;color: #9a9a9a;height: 500px;">
									<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
								<td>
							</tr>
						</c:if>
						<!-- 没有数据提示结束 -->
					</tbody>
				</table>
				<!-- 列表结束 -->
			</dd>
			<c:if test="${fn:length(page.list)>'0'}">
				<%@ include file="/views/member/include/page.jsp"%>
			</c:if>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
