<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购咨询管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseConsultationList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购咨询列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('采购咨询列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('采购咨询.咨询列表.操作提示1')}</li>
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
						<th>${fns:fy('用户id')}</th>
						<th>${fns:fy('商品id')}</th>
						<th>${fns:fy('描述')}</th>
						<th>${fns:fy('数量')}</th>
						<th>${fns:fy('联系方式')}</th>
						<th>${fns:fy('是否联系')}</th>
						<th>${fns:fy('创建时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseConsultation">
					<tr>
						<td><a href="${ctxa}/purchase/purchaseConsultation/edit1.do?pcId=${purchaseConsultation.pcId}">${purchaseConsultation.pcId}</a></td>
						<td>${purchaseConsultation.PId}</td>
						<td>${purchaseConsultation.purchaseDescribe}</td>
						<td>${purchaseConsultation.quantity}</td>
						<td>${purchaseConsultation.contactInfo}</td>
						<td>${fns:getDictLabel(purchaseConsultation.isContact, 'yes_no', '')}</td>
						<td><fmt:formatDate value="${purchaseConsultation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<shiro:hasPermission name="purchase:purchaseConsultation:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseConsultation/edit1.do?pcId=${purchaseConsultation.pcId}">
								<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchase:purchaseConsultation:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/purchase/purchaseConsultation/delete.do?pcId=${purchaseConsultation.pcId}">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
							</button>
							</shiro:hasPermission>
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