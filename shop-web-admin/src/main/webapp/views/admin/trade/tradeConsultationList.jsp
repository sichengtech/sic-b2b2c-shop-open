<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('咨询管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeConsultationList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('咨询列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('咨询管理.咨询列表.操作提示1')}</li>
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
				<form action="${ctxa}/trade/tradeConsultation/list.do" method="get" id="searchForm">
					<div class="col-sm-6 col-md-offset-4">
						<div class="iconic-input right">
							<a href="javaScript:;" class="searchBtn"><i class="fa fa-search"></i></a>
							<input type="text" name="content" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入咨询内容')}" maxlength="255" style="border-radius: 30px;max-width:250px;" value="${tradeConsultation.content}"/>
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
						<th></th>
						<th>${fns:fy('咨询人')}</th>
						<th>${fns:fy('咨询内容')}</th>
						<th>${fns:fy('咨询时间')}</th>
						<th>${fns:fy('回复时间')}</th>
						<th>${fns:fy('商家名称')}</th>
						<shiro:hasPermission name="trade:tradeConsultation:drop"><th>${fns:fy('操作')}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="tradeConsultation">
					<c:if test="${tradeConsultation.type =='0'}">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${tradeConsultation.userMain.loginName}</td>
						<td>${tradeConsultation.content}</td>
						<td><fmt:formatDate value="${tradeConsultation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<fmt:formatDate value="${tradeConsultation.replyTradeConsultation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${tradeConsultation.store.name}</td>
						<shiro:hasPermission name="trade:tradeConsultation:drop">
						<td>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/trade/tradeConsultation/delete.do?consultationId=${tradeConsultation.consultationId}">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
							</button>
						</td>
						</shiro:hasPermission>
					</tr>
					<tr id="1" class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">${fns:fy('回复内容')} : ${tradeConsultation.replyTradeConsultation.content}</p>
							<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('咨询商品')} :
								<a href="${ctxf}/detail/${tradeConsultation.productSpu.id}.htm" target="_blank">${tradeConsultation.productSpu.name}</a>
							</p>
							<p datano="0" columnno="2" class="dt-grid-cell">${fns:fy('咨询类型')} : ${fns:getDictLabel(tradeConsultation.category, 'trade_consultation_categor', '')}</p>
						</td>
					</tr>
					</c:if>
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