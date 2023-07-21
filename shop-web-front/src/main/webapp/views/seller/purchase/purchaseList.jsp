<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:fy('我发布的采购')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.purchase-list textarea{padding: 5px;background-color: #f9f6f600}
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:150px !important;}
	.purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.purchaseBatch .uploader-container img{vertical-align: bottom;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;}
</style>
</head>
<body>
	<div class="main-content clearfix purchase-list">
		<div class="main-center">
			<dl class="box">
				<!-- <dt>
					<div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 我发布的采购</div>
					<i class="sui-icon icon-tb-list"></i> 我发布的采购</span>
					<%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->


				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('我发布的采购')}</span>
						<span style="margin-top: 5px; margin-left: 10px;" class="">
							<a href="javascript:void(0)" id="toolbar_operate_tips" class="sui-btn btn-bordered btn-small btn-warning" data-placement="bottom" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('操作提示')}">
								<i class="sui-icon icon-pc-light"></i>
							</a>
						</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('会员中心')}</li>
						<li>${fns:fy('供采管理')}</li>
						<li>${fns:fy('我发布的采购')}</li>
					</ul>
				</dt>

				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('展示我的发布的采购信息。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="clearfix ml10 mr10 mt10">
					<div class="sui-row-fluid">
						<form class="sui-form form-inline" action="${ctxs}/purchase/purchase/list.htm" style="margin-top: 10px;" method="post" id="searchForm">
							<div class="span12">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('采购单号：')}</label>
									<input type="text" class="form-control input-sm" name="purchaseId" value="${viewPurchase.purchaseId}" placeholder="${fns:fy('请填写采购单号')}"> 
									<label class="col-sm-3 control-label text-right">${fns:fy('发布时间：')}</label>
									<input type="text" class="form-control input-sm J-date-start input-date" readonly="readonly" nc-format="" 
									id="" value="<fmt:formatDate value="${viewPurchase.beginPurchaseCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginPurchaseCreateDate" placeholder="${fns:fy('请选择采购开始时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"> -
									<input type="text" class="form-control input-sm J-date-start input-date" readonly="readonly" nc-format="" 
									id="" value="<fmt:formatDate value="${viewPurchase.endPurchaseCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endPurchaseCreateDate" placeholder="${fns:fy('请选择采购结束时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});">
									<label class="col-sm-3 control-label text-right">${fns:fy('状态：')}</label>
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
											<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input value="${viewPurchase.purchaseStatus}" name="purchaseStatus" type="hidden">
												<i class="caret"></i>
												<span>${empty viewPurchase.purchaseStatus?fns:fy('请选择'):fns:getDictLabel(viewPurchase.purchaseStatus, 'purchase_status', '')}</span>
											</a>
											<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
												<li role="presentation">
													<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a>
												</li>
												<c:forEach items="${fns:getDictList('purchase_status')}" var="item">
													<li role="presentation">
														<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${item.value}">${item.label}</a>
													</li>
												</c:forEach>
											</ul>
										</span>
									</span>
									<button type="submit" class="sui-btn btn-primary formBtn" id="searchBtn">${fns:fy('搜索')}</button>
								</div>
							</div>
						</form>
					</div>
					<c:if test="${fn:length(page.list)==0}">
						<!-- <div class="noData" style="margin-top: 0px;"><span>暂无数据</span></div> -->
						<div class="no_product" style="height:400px;text-align: center;color: #9a9a9a;line-height: 400px;">
							<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
						</div>
					</c:if>
					<c:if test="${fn:length(page.list)!=0}">
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th width="10%">${fns:fy('采购单号')}</th>
								<th width="10%">${fns:fy('发布方式')}</th>
								<th width="15%">${fns:fy('采购标题')}</th>
								<th width="10%">${fns:fy('交货周期')}</th>
								<th width="10%">${fns:fy('采购说明')}</th>
								<th width="10%">${fns:fy('状态')}</th>
								<th width="10%">${fns:fy('发布时间')}</th>
								<th width="10%">${fns:fy('报价信息')}</th>
								<th width="15%">${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${page.list}" var="viewPurchase">
							<tr>
								<td width="10%">${viewPurchase.purchaseId}</td>
								<td width="10%">${fns:getDictLabel(viewPurchase.purchaseType, 'purchase_type', '')}</td>
								<td width="15%">${viewPurchase.purchaseTitle}</td>
								<td width="10%">${viewPurchase.puCycle}</td>
								<td width="10%"><textarea rows="3" cols="20"  disabled="disabled">${viewPurchase.purchaseExplain}</textarea></td>
								<td width="10%">${fns:getDictLabel(viewPurchase.purchaseStatus, 'purchase_status', '')}</td>
								<td width="10%"><fmt:formatDate value="${viewPurchase.purchaseCreateDate}" pattern="yyyy-MM-dd"/></td>
								<td width="10%">
									<c:if test="${viewPurchase.purchaseStatus==35 || viewPurchase.purchaseStatus==40 || viewPurchase.purchaseStatus==50 || viewPurchase.purchaseStatus==60 }">
										${fns:fy('有')}${viewPurchase.purchaseCounts}${fns:fy('家报价')}<br>
										<a href="${ctxs}/purchase/matchmaking/list.htm?purchaseId=${viewPurchase.purchaseId}&sta=1">${fns:fy('查看')}</a>
									</c:if>
								</td>
								<td width="15%">
									<c:if test="${viewPurchase.purchaseType == '30'}">
										<a href="${ctxs}/purchase/purchaseItem/list.htm?purchaseId=${viewPurchase.purchaseId}" class="sui-btn btn-primary">${fns:fy('查看详情')}</a><br><br>
									</c:if>
									<c:if test="${viewPurchase.purchaseType == '10' || viewPurchase.purchaseType == '20'}">
										<c:if test="${viewPurchase.purchaseStatus eq '35' || viewPurchase.purchaseStatus eq '40' || viewPurchase.purchaseStatus eq '50' || viewPurchase.purchaseStatus eq '60'}">
											<a href="${ctxs}/purchase/purchaseItem/list.htm?purchaseId=${viewPurchase.purchaseId}" class="sui-btn btn-primary">${fns:fy('查看详情')}</a><br><br>
										</c:if>
									</c:if>
									<c:if test="${viewPurchase.purchaseStatus eq '10' || viewPurchase.purchaseStatus eq '20' || viewPurchase.purchaseStatus eq '30'}">
									<button type="button" href="${ctxs}/purchase/purchase/purchaseCancel.htm?purchaseId=${viewPurchase.purchaseId}" class="sui-btn btn-danger purchaseCancel">${fns:fy('取消采购')}</button>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/views/seller/include/page.jsp"%>
					</c:if>
				</dd>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseList.js"></script>
</body>
</html>
