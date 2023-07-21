<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购单明细管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseItemList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购单明细列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('采购单明细列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('采购单明细管理是展示每个采购单的明细信息')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-1">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
					</div>
				</div>
				<%-- <form action="${ctxa}/purchase/purchaseItem/list.do" method="get" id="searchForm">
					<div class="col-sm-2">
						<input type="text" name="purchaseId"  maxlength="64" class="form-control input-sm" placeholder="请输入采购单号" value="${purchaseItem.purchaseId}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="model"  maxlength="64" class="form-control input-sm" placeholder="请输入产品型号" value="${purchaseItem.model}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="brand"  maxlength="64" class="form-control input-sm" placeholder="请输入产品品牌" value="${purchaseItem.brand}"/>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
							<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${purchaseItem.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:true});" placeholder="请选择发布开始时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${purchaseItem.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:true});" placeholder="请选择发布结束时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form> --%>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('采购单号')}</th>
						<th>${fns:fy('产品名称')}</th>
						<th>${fns:fy('规格型号')}</th>
						<th>${fns:fy('品牌')}</th>
						<th>${fns:fy('数量')}</th>
						<th>${fns:fy('单价(元)')}</th>
						<th>${fns:fy('单位')}</th>
						<th>${fns:fy('采购说明')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseItem">
					<tr>
						<td>${purchaseItem.purchaseId}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseItem.name}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseItem.model}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseItem.brand}</textarea></td>
						<td>${purchaseItem.amount}</td>
						<td>${purchaseItem.priceRequirement}</td>
						<td>${purchaseItem.unit}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseItem.purchaseRemarks}</textarea></td>
						<td>
							<shiro:hasPermission name="purchase:purchase:view">
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchase/list.do?purchaseId=${purchaseItem.purchaseId}">
								<i class="fa fa-eye"></i> ${fns:fy('查看采购单')}
							</a>
							</shiro:hasPermission>
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