<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:fy('全部订单')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.purchase-order textarea{padding: 5px;background-color: #f9f6f600}
	.sui-msg.msg-block {margin: 10px !important;}
</style>
</head>
<body class="purchase-order-list">
	<div class="main-content clearfix purchase-order" style="overflow:visible">
		<div class="main-center">
			<dl class="box">
				<!-- <dt>
					<div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 全部订单</div>
					<i class="sui-icon icon-tb-list"></i> 全部订单</span>
					<%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->


				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('全部订单')}</span>
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
						<li>${fns:fy('全部订单')}</li>
					</ul>
				</dt>



				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('展示我的全部订单信息。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="clearfix ml10 mr10 mt10">
					<div class="sui-row-fluid">
						<form class="sui-form form-inline" action="${ctxs}/purchase/order/list.htm?orderSta=${orderSta}" style="margin-top: 10px;" method="post" id="searchForm">
							<div class="span12">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('订单号：')}</label>
									<input type="text" class="form-control input-sm" name="purchaseTradeId" value="${purchaseTradeOrder.purchaseTradeId}" placeholder="${fns:fy('请输入订单号')}">
									<label class="col-sm-3 control-label text-right">${fns:fy('下单时间：')}</label>
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${purchaseTradeOrder.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginCreateDate" placeholder="${fns:fy('请选择开始下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"> -
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${purchaseTradeOrder.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endCreateDate" placeholder="${fns:fy('请选择结束下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});">
									<label class="col-sm-3 control-label text-right">${fns:fy('状态：')}</label>
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
											<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input value="${purchaseTradeOrder.status}" name="status" type="hidden">
												<i class="caret"></i>
												<span>${empty purchaseTradeOrder.status?fns:fy('请选择'):fns:getDictLabel(purchaseTradeOrder.status, 'purchase_order_status', '')}</span>
											</a>
											<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
												<li role="presentation">
													<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a>
												</li>
												<c:forEach items="${fns:getDictList('purchase_order_status')}" var="item">
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
								<th width="7%">${fns:fy('订单号')}</th>
								<th width="15%">${fns:fy('采购标题')}</th>
								<th width="15%">${fns:fy('采购方')}</th>
								<th width="15%">${fns:fy('供应方')}</th>
								<th width="10%">${fns:fy('交易额(元)')}</th>
								<th width="10%">${fns:fy('状态')}</th>
								<th width="15%">${fns:fy('成单时间')}</th>
								<th width="13%">${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${page.list}" var="purchaseTradeOrder">
							<tr>
								<td width="7%">${purchaseTradeOrder.purchaseTradeId}</td>
								<td width="15%">${purchaseTradeOrder.title}</td>
								<td width="15%" style="text-align: left;">
									${fns:fy('公司名称：')}${purchaseTradeOrder.purchaseStoreEnter.companyName }<br>
									${fns:fy('联系人：')}${purchaseTradeOrder.purchaseStoreEnter.contact }<br>
									${fns:fy('电话：')}${purchaseTradeOrder.purchaseStoreEnter.contactNumber }<br>
								</td>
								<td width="15%" style="text-align: left;">
									${fns:fy('公司名称：')}${purchaseTradeOrder.offerStoreEnter.companyName }<br>
									${fns:fy('联系人：')}${purchaseTradeOrder.offerStoreEnter.contact }<br>
									${fns:fy('电话：')}${purchaseTradeOrder.offerStoreEnter.contactNumber }<br>
								</td>
								<td width="10%">${purchaseTradeOrder.price}${fns:fy('元')}</td>
								<td width="10%">${fns:getDictLabel(purchaseTradeOrder.status, 'purchase_order_status', '')}</td>
								<td width="15%"><fmt:formatDate value="${purchaseTradeOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td width="13%">
									<a href="${ctxs}/purchase/orderItem/list.htm?orderSta=${orderSta}&ptoId=${purchaseTradeOrder.purchaseTradeId}" class="sui-btn btn-primary">${fns:fy('查看订单详情')}</a><br><br>
									<c:if test="${purchaseTradeOrder.status=='10' || purchaseTradeOrder.status=='30'}">
									<a href="${ctxs}/purchase/voucher/save1.htm?orderSta=${orderSta}&purchaseTradeId=${purchaseTradeOrder.purchaseTradeId}" class="sui-btn btn-primary">${fns:fy('上传交易凭证')}</a>
									</c:if>
									<c:if test="${purchaseTradeOrder.status=='40'}">
									<a href="${ctxs}/purchase/voucher/save1.htm?orderSta=${orderSta}&purchaseTradeId=${purchaseTradeOrder.purchaseTradeId}" class="sui-btn btn-primary">${fns:fy('查看交易凭证')}</a>
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
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseTradeOrderList.js"></script>
</body>
</html>
