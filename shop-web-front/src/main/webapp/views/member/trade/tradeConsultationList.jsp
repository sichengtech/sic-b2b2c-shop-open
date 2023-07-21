<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品咨询')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > ${fns:fy('商品咨询')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('商品咨询')}
			</dt>
			<dd>
				<ul class="sui-nav nav-tabs">
					<li class="${empty isReply?'active':'' }"><a href="${ctxm}/trade/consultation/list.htm" >${fns:fy('全部咨询')} </a></li>
					<li class="${isReply eq '0'?'active':'' }"><a href="${ctxm}/trade/consultation/list.htm?isReply=0" >${fns:fy('未回复咨询')}</a></li>
					<li class="${isReply eq '1'?'active':'' }"><a href="${ctxm}/trade/consultation/list.htm?isReply=1" >${fns:fy('已回复咨询')}</a></li>
				</ul>
			</dd>
			<dd class="myrefund pl20 pr20">
				<!-- 列表开始 -->
				<c:forEach items="${page.list}" var="consultation">
					<table class="sui-table table-bordered-simple mt10 table-xlarge">
						<thead>
							<tr>
								<th colspan="5">
									<span class="mr20"><a href="${ctxf}/detail/${consultation.productSpu.PId}.htm">${consultation.productSpu.name}</a></span>
									<span class="mr20"><a href="javascript:void(0);">${fns:fy('咨询类型：')}${fns:getDictLabel(consultation.category, 'trade_consultation_categor', '')}</a></span>
									<span class="mr20"><a href="javascript:void(0);">${fns:fy('咨询时间：')}<fmt:formatDate value="${consultation.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></a></span>
								</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td width="55%">
									<div style="word-break: break-all;">${fns:fy('咨询内容：')}${consultation.content}</div>
									<div style="word-break: break-all;">${fns:fy('回复内容：')}${consultation.replyTradeConsultation.content}</div>
								</td>
							</tr>
						</tbody>
					</table>
				</c:forEach>
				<!-- 列表结束 -->
				<!-- 没有数据提示开始 -->
				<c:if test="${fn:length(page.list)=='0'}">
					<div class="no_product" style="text-align: center;color: #9a9a9a;margin-top: 300px;">
						<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
					<div>
				</c:if>
				<!-- 没有数据提示结束 -->
			</dd>
			<c:if test="${fn:length(page.list)>'0'}">
				<%@ include file="/views/member/include/page.jsp"%>
			</c:if>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
