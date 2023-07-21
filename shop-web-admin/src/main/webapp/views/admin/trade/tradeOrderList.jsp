<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('订单管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeOrderList.js"></script>
<style type="text/css">
	.btn.disabled, .btn[disabled], fieldset[disabled] .btn{
		background-color: #b6c2c9!important; border-color: #b6c2c9!important;
	}
	.memo{word-break:break-all;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('订单列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>

		<!-- panel-body开始 --> 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('订单管理.订单列表.操作提示1')}</li>
					<li>${fns:fy('订单管理.订单列表.操作提示2')}</li>
					<li>${fns:fy('订单管理.订单列表.操作提示3')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-3">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<%--<shiro:hasPermission name="trade:tradeOrder:edit">
						 <a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/trade/tradeOrder/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>--%>
						<!-- 快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy('查询')}" ><i class="fa fa-search"></i></button>
					</div>
				</div>
				<form action="${ctxa}/trade/tradeOrder/list.do" method="get" id="searchForm">
					 <div class="col-sm-6 col-md-offset-3">
						<div class="iconic-input right">
							<a href="javaScript:;" class="searchBtn"><i class="fa fa-search"></i></a>
							<input type="text" name="orderId" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入订单编号进行精确搜索')}" style="border-radius: 30px;max-width:250px;" value="${tradeOrder.orderId}" maxlength="18"/>
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						</div>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th></th> 
						<th>${fns:fy('订单编号')}</th>
						<th>${fns:fy('下单时间')}</th>
						<th>${fns:fy('订单总金额')}(${fns:fy('元')})</th>
						<th>${fns:fy('订单状态')}</th>
						<th>${fns:fy('第三方平台交易号')}</th>
						<th>${fns:fy('支付时间')}</th>
						<th>${fns:fy('完成时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="tradeOrder" varStatus="index">
					<tr class="${index.count}">
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${tradeOrder.orderId}</td>
						<td><fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}</td>
						<td>${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}</td>
						<td>${tradeOrder.thirdPayOrderNumber}</td>
						<td><fmt:formatDate value="${tradeOrder.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${tradeOrder.productReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<shiro:hasPermission name="trade:tradeOrder:edit">
							<c:if test="${tradeOrder.isReturnStatus eq '0' || empty tradeOrder.isReturnStatus}">
								<div class="btn-group">
									<a href="${ctxa}/trade/tradeOrder/edit1.do?orderId=${tradeOrder.orderId}" ${tradeOrder.orderStatus == '10' || tradeOrder.orderStatus == '20' ?"":"disabled='disabled'"} data-toggle="dropdown" aria-expanded="false" class="btn btn-info btn-sm dropdown-toggle">
										<i class="fa fa-edit"></i> ${fns:fy('编辑')}
										<span class="caret"></span>
									</a>
									<ul class="dropdown-menu dropdown-menu-right" style="min-width: 125px;">
										<li>
											<a href="javascript:;" class="cancel deleteSure" data-no="1" href1="${ctxa}/trade/tradeOrder/cancelOrder.do?orderId=${tradeOrder.orderId}">
												<i class="fa fa-ban m-r-10"></i> ${fns:fy('取消订单')}
											</a>
										</li>
										<c:if test="${tradeOrder.orderStatus == '10'}">
											<li>
												<a href="${ctxa}/trade/tradeOrder/receivePayment1.do?orderId=${tradeOrder.orderId}" data-no="1">
													<i class="fa fa-jpy m-r-10"></i>&nbsp;${fns:fy('收到货款')}
												</a>
											</li>
										</c:if>
									</ul>
								 </div>
							</c:if>
							<c:if test="${not empty tradeOrder.isReturnStatus && tradeOrder.isReturnStatus eq '1' && tradeOrder.orderStatus ne '40'}">
								<p>${fns:fy('退货退款中')}</p>
							</c:if>
							<c:if test="${not empty tradeOrder.isReturnStatus && tradeOrder.isReturnStatus eq '2' && tradeOrder.orderStatus ne '40'}">
								<p>${fns:fy('退款中')}</p>
							</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="trade:tradeOrder:view">
							 <a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeOrder/tradeOrderDetail.do?orderId=${tradeOrder.orderId}">
								<i class="fa fa-eye"></i> ${fns:fy('查看')}
							 </a>
							 </shiro:hasPermission>
						 </td>
					</tr>
					<tr id="${index.count}" class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">${fns:fy('来源')}: ${fns:getDictLabel(tradeOrder.sources, 'settlement_terminal_type', '')}</p>
							<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('运费')}(${fns:fy('元')}): ${tradeOrder.freight}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">${fns:fy('支付方式')}:${tradeOrder.paymentMethodName}</p>
							<p datano="0" columnno="3" class="dt-grid-cell">${fns:fy('支付终端')}: ${fns:getDictLabel(tradeOrder.payTerminal, 'settlement_terminal_type', '')}</p>
							<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('预存款支付')}(${fns:fy('元')}):${tradeOrder.preDepositPay}</p>	 
							<p datano="0" columnno="4" class="dt-grid-cell memo">${fns:fy('商家留言')}:${tradeOrder.sellerMemo}</p>	 
							<p datano="0" columnno="4" class="dt-grid-cell memo">${fns:fy('买家留言')}:${tradeOrder.memo}</p>	 
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
	<!-- 开始快速查询窗口 -->
	<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<form id="fastSearch" action="${ctxa}/trade/tradeOrder/list.do" class="searchForm"> 
			<div class="modal-body form-horizontal adminex-form">
				<div class="form-group">
					<label class="col-sm-3 control-label text-right">${fns:fy('订单编号')}：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control input-sm searchInput" id="orderId" name="orderId" placeholder="${fns:fy('请输入订单编号')}" value="${tradeOrder.orderId}" maxlength="18"/>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('来源')}：</label>
					<div class="col-sm-4"> 
						<select class="form-control input-sm" id="eq_ordersFrom" name="sources">
							 <option value="" class="firstOption">${fns:fy('全部')}</option> 
							 <c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="tt">
								<option ${tt.value eq tradeOrder.sources?"selected":""} value="${tt.value}">${tt.label}</option>
							 </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('下单时间')}：</label>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-start searchInput" nc-format="" id="ge_createTime" name="beginPlaceOrderTime" 
								value="<fmt:formatDate value="${tradeOrder.beginPlaceOrderTime}" pattern="yyyy-MM-dd"/>" format="yyyy-MM-dd HH:mm:ss" 
								placeholder="${fns:fy('请选择开始下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-end searchInput" nc-format="" id="endDate" name="endPlaceOrderTime" 
								value="<fmt:formatDate value="${tradeOrder.endPlaceOrderTime}" pattern="yyyy-MM-dd"/>" format="yyyy-MM-dd HH:mm:ss" 
								placeholder="${fns:fy('请选择结束下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('订单状态')}：</label>
					<div class="col-sm-4"> 
						<select class="form-control input-sm" id="eq_ordersState" name="orderStatus">
							<option value="" class="firstOption">${fns:fy('全部')}</option> 
							<c:forEach items="${fns:getDictList('trade_order_status')}" var="sd">
								<option ${sd.value eq tradeOrder.orderStatus?"selected":""} value="${sd.value}">${sd.label}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('支付方式')}：</label>
					<div class="col-sm-4"> 
						<%-- 支付方式做完后，此处的支付方式要从数据库中查询 --%>
						<select class="form-control input-sm" id="eq_paymentCode" name="paymentMethodId">
							<option value="" class="firstOption">${fns:fy('全部')}</option>
							 <c:forEach items="${payWays}" var="way">
								<option value="${way.payWayId}">${way.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('支付单号')}：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control input-sm searchInput" id="eq_paySnStr" name="payOrderNumber" placeholder="${fns:fy('请输入支付单号')}" value="${tradeOrder.payOrderNumber}"/>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('支付时间')}：</label>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-start searchInput" nc-format="" id="ge_paymentTime" name="beginPayOrderTime" 
								value="<fmt:formatDate value="${tradeOrder.beginPayOrderTime}" pattern="yyyy-MM-dd"/>" format="yyyy-MM-dd HH:mm:ss" 
								placeholder="${fns:fy('请选择开始支付时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-end searchInput" nc-format="" id="le_paymentTime" name="endPayOrderTime" 
								value="<fmt:formatDate value="${tradeOrder.endPayOrderTime}" pattern="yyyy-MM-dd"/>" format="yyyy-MM-dd HH:mm:ss" 
								placeholder="${fns:fy('请选择结束支付时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
				</div>
				<div class="form-group"> 
					<label class="col-sm-3 control-label text-right">${fns:fy('完成时间')}：</label>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-start searchInput" nc-format="" id="ge_finishTime" name="beginProductReceiptDate"
								value="<fmt:formatDate value="${tradeOrder.beginProductReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" format="yyyy-MM-dd" 
								placeholder="${fns:fy('请选择开始完成时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group"> 
							<input type="text" class="form-control input-sm J-date-end searchInput" nc-format="" id="le_finishTime" name="endProductReceiptDate" 
								value="<fmt:formatDate value="${tradeOrder.endProductReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" format="yyyy-MM-dd" 
								placeholder="${fns:fy('请选择结束完成时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> 
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
				</div> 
			</div>
			<div class="modal-footer">
				 <button type="button" class="btn btn-default" onclick="(function(){layer.closeAll('page');}())">
					 <i class="fa fa-times"></i> ${fns:fy('关闭')}
				 </button>
				 <button type="button" id="resetParam" class="btn btn-warning" onclick="(function(){$('.searchInput').attr('value',''); $('.searchCheckbox').removeAttr('checked');$('.firstOption').attr('selected','selected');}())">
					 <i class="fa fa-reply"></i> ${fns:fy('参数重置')}
				 </button> 
				 <button type="submit" class="btn btn-info" id="search">
					 <i class="fa fa-search"></i> ${fns:fy('执行查询')}
				 </button> 
			 </div> 
		</form> 
	</div>
	<!-- 结束快速查询模态窗口 --> 
</body>
</html>