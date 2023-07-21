<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('供应商报价')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseMatchmakingList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('供应商报价')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li><a href="${ctxa}/purchase/purchase/list.do"> <i class="fa fa-user"></i> ${fns:fy('采购单列表')}</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('供应商报价')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('供应商报价管理是供应商对采购单的报价信息')}</li>
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
						<th>${fns:fy('供应单号')}</th>
						<th>${fns:fy('采购单号')}</th>
						<th>${fns:fy('采购联系方式')}</th>
						<th>${fns:fy('供应联系方式')}</th>
						<th>${fns:fy('总价格')}</th>
						<th>${fns:fy('交易状态')}</th>
						<th>${fns:fy('创建时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseMatchmaking">
					<tr>
						<td>
							${purchaseMatchmaking.purchaseMatchmakingId}
						</td>
						<td>
							<a href="${ctxa}/purchase/purchase/list.do?purchaseId=${purchaseMatchmaking.purchaseId}">
								${purchaseMatchmaking.purchaseId}
							</a>
						</td>
						<td>
                           	${fns:fy('联系人：')}${purchaseMatchmaking.purchaseStoreEnter.contact}<br>
 							${fns:fy('电话：')}${purchaseMatchmaking.purchaseStoreEnter.contactNumber}<br>
 							${fns:fy('公司名称：')}${purchaseMatchmaking.purchaseStoreEnter.companyName}<br>
						</td>
						<td>
                           	${fns:fy('联系人：')}${purchaseMatchmaking.offerStoreEnter.contact}<br>
 							${fns:fy('电话：')}${purchaseMatchmaking.offerStoreEnter.contactNumber}<br>
 							${fns:fy('公司名称：')}${purchaseMatchmaking.offerStoreEnter.companyName}<br>
						</td>
						<td>${not empty purchaseMatchmaking.price && purchaseMatchmaking.price ne '0.000'?purchaseMatchmaking.price:'0'}${fns:fy('元')}</td>
						<td>${fns:getDictLabel(purchaseMatchmaking.status, 'purchase_matchmaking_status', '')}</td>
						<td><fmt:formatDate value="${purchaseMatchmaking.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseMatchmakingItem/list.do?pmId=${purchaseMatchmaking.purchaseMatchmakingId}">
								<i class="fa fa-eye"></i> ${fns:fy('查看明细')}
							</a>
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