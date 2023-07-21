<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('账单管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementBillList.js"></script>
<style type="text/css">
	.btn.disabled, .btn[disabled], fieldset[disabled] .btn{
		background-color: #b6c2c9!important;
	    border-color: #b6c2c9!important;
	}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('账单列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<%--<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 账单列表</a></li>
				 <shiro:hasPermission name="settlement:settlementBill:edit"><li class=""><a href="${ctxa}/settlement/settlementBill/save1.do" > <i class="fa fa-user"></i> 账单添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('账单管理.账单列表.操作提示1')}</li>
					<li>${fns:fy('账单管理.账单列表.操作提示2')}=${fns:fy('账单管理.账单列表.操作提示3')}</li>
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
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy('查询')}" ><i class="fa fa-search"></i></button>
					</div>
				</div>
				<form action="${ctxa}/settlement/settlementBill/list.do" method="get" id="searchForm">
					<div class="col-sm-6 col-md-offset-4">
						<div class="iconic-input right">
							<a href="javaScript:;" class="searchBtn"><i class="fa fa-search"></i></a>
							<input type="text" name="billId" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入账单编号进行精确搜索')}" maxLength="19" style="border-radius: 30px;max-width:250px;" value="${settlementBill.billId}"/>
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
						<th>${fns:fy('账单编号')}</th>
						<th>${fns:fy('开始日期')}</th>
						<th>${fns:fy('结束日期')}</th>
						<th>${fns:fy('本期应结')}(${fns:fy('元')})</th>
						<th>${fns:fy('账单状态')}</th>
						<th>${fns:fy('店铺')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="settlementBill">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td class="billId">${settlementBill.billId}</td>
						<td><fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd"/></td>
						<td class="billAmount">${not empty settlementBill.billAmount && settlementBill.billAmount ne '0.000'?settlementBill.billAmount:'0'}</td>
						<td class="">${fns:getDictLabel(settlementBill.status, 'settlement_bill_status', '')}</td>
						<td class="storeName">${settlementBill.store.name}</td>
						<shiro:hasPermission name="settlement:settlementBill:edit">
						<td>
							<shiro:hasPermission name="settlement:settlementBill:edit">
							<%--<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> ${fns:fy('重算')}
							</button>--%>
							<c:if test="${settlementBill.status == '30'}">
								<button type="button" class="btn btn-warning btn-sm pay" id="${settlementBill.billId}">
									<i class="fa fa-credit-card"></i> ${fns:fy('支付')}
								</button>
							</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="settlement:settlementBill:auth">
							<c:if test="${settlementBill.status == '20'}">
								<button type="button" class="btn btn-success btn-sm audit" href="${ctxa}/settlement/settlementBill/audit.do?billId=${settlementBill.billId}">
									<i class="fa fa-check-square-o"></i> ${fns:fy('审核')}
								</button>
							</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="settlement:settlementBill:view">
							<c:if test="${settlementBill.status == '10' || settlementBill.status == '40'}">
								<a class="btn btn-info btn-sm" href="${ctxa}/settlement/settlementBill/detail.do?billId=${settlementBill.billId}">
									<i class="fa fa-eye"></i> ${fns:fy('查看')}
								 </a>
							</c:if>
							</shiro:hasPermission>
						</td>
						</shiro:hasPermission>
					</tr>
					<tr id="1" class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell hidden ">${fns:fy('账单编号')} :${settlementBill.billId}</p>
							<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('订单金额')}(${fns:fy('元')}):${not empty settlementBill.orderAmount && settlementBill.orderAmount ne '0.000'?settlementBill.orderAmount:'0'}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">${fns:fy('收取佣金')}(${fns:fy('元')}):${not empty settlementBill.platformCommission && settlementBill.platformCommission ne '0.000'?settlementBill.platformCommission:'0'}</p>
							<p datano="0" columnno="3" class="dt-grid-cell">${fns:fy('退单金额')}(${fns:fy('元')}):${not empty settlementBill.refund && settlementBill.refund ne '0.000'?settlementBill.refund:'0'}</p>
							<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('退还佣金')}(${fns:fy('元')}):${not empty settlementBill.returnCommission && settlementBill.returnCommission ne '0.000'?settlementBill.returnCommission:'0'}</p>
							<p datano="0" columnno="10" class="dt-grid-cell">${fns:fy('店铺')}id:${settlementBill.storeId}</p>
							<p datano="0" columnno="8" class="dt-grid-cell">${fns:fy('出账日期')} :<fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy-MM-dd"/></p>
							<p datano="0" columnno="11" class="dt-grid-cell">${fns:fy('付款时间')}:<fmt:formatDate value="${settlementBill.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p> 
							<p datano="0" columnno="10" class="dt-grid-cell">${fns:fy('付款备注')}:${settlementBill.payRemarks}</p>
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
		<div class="modal-content">	 
			<form id="fastSearch" action="${ctxa}/settlement/settlementBill/list.do" > 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('账单编号')}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" id="eq_ordersSnStr" name="billId" value="${settlementBill.billId}" 
								placeholder="${fns:fy('请输入账单编号')}">
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('出账日期')}：</label>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input type="text" name="beginBalanceDate" value="<fmt:formatDate value="${settlementBill.beginBalanceDate}" pattern="yyyy-MM-dd"/>" class="form-control input-sm J-date-end searchInput" nc-format="" format="yyyy-MM-dd" 
									placeholder="${fns:fy('请选择开始出账日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input type="text" name="endBalanceDate" value="<fmt:formatDate value="${settlementBill.endBalanceDate}" pattern="yyyy-MM-dd"/>" class="form-control input-sm J-date-end searchInput" nc-format="" format="yyyy-MM-dd" 
									placeholder="${fns:fy('请选择结束出账日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('账单状态')}：</label>
						<div class="col-sm-4"> 
							<select class="form-control input-sm" id="" name="status">
								<option value="" class="firstOption">${fns:fy('全部')}</option> 
								<c:forEach items="${fns:getDictList('settlement_bill_status')}" var="bs">
									<option ${bs.value eq settlementBill.status?"selected":""} value="${bs.value}">${bs.label}</option>
								</c:forEach>
						 	</select>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('店铺')}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" id="eq_paySnStr" name="storeName" value="${storeName}" 
								placeholder="${fns:fy('请输入店铺名')}">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					 <button type="button" class="btn btn-default" data-dismiss="modal" onclick="(function(){layer.closeAll('page');}())">
						 <i class="fa fa-times"></i> ${fns:fy('关闭')}
					 </button>
					 <button type="button" class="btn btn-warning" onclick="(function(){$('.searchInput').attr('value',''); $('.firstOption').attr('selected','selected');}())">
						 <i class="fa fa-reply"></i> ${fns:fy('参数重置')}
					 </button> 
					 <button type="submit" class="btn btn-info" id="search">
						 <i class="fa fa-search"></i> ${fns:fy('执行查询')}
					 </button>
				 </div>
			</form>
		</div>
	</div>
	<!-- 结束快速查询模态窗口 -->
	<shiro:hasPermission name="settlement:settlementBill:edit">
	<!-- 开始支付模态窗口 -->
	<div class="modal fade in" id="payModal" tabindex="-1" role="dialog" aria-hidden="false" style="display:none;">
		<div class="modal-content">
			<div class="modal-body" style="padding-bottom: 0;">
				<ul class="text-info p-b-10 p-l-15 collapse in" role="tabpanel" id="OperationTipsEdit" aria-expanded="true">
					<li>${fns:fy('账单确认支付后，此账单的处理流程全部结束。')}</li>
				</ul>
				<form action="${ctxa}/settlement/settlementBill/pay2.do" id="payForm">
				<div class="modal-body form-horizontal adminex-form">
					<input type="hidden" name="billId" value=""/>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"> ${fns:fy('账单编号')}&nbsp;: </label>
						<div class="col-sm-4">
							<div class="clearfix m-b-15" id="billId" nctype="billSn"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"> ${fns:fy('账单金额')}&nbsp;: </label>
						<div class="col-sm-4">
							<div class="clearfix m-b-15" id="billAmount" nctype="billAmount"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right" > ${fns:fy('商家')}&nbsp;: </label>
						<div class="col-sm-4">
							<div class="clearfix m-b-15" id="sellerName" nctype="storeName"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('付款时间')}&nbsp;:</label>
						<div class="col-sm-9">
							<input type="text" name="payDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" 
								placeholder="${fns:fy('请选择付款时间')}" format="yyyy-MM-dd HH:mm:ss" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('付款备注')}&nbsp;:</label>
						<div class="col-sm-9">
							<textarea placeholder="${fns:fy('汇款单号、支付方式等付款凭证')}" name="payRemarks" id="paymentNote" class="form-control" maxlength="100" data-parsley-id="6"></textarea>
						</div>
					</div>
				</div>
				 	<div class="modal-footer" style="padding: 0 0 15px 0;border-top: none;"> 
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="(function(){layer.closeAll('page');}())">
							 <i class="fa fa-times"></i> ${fns:fy('关闭')}
						 </button>	
						 <button type="submit" class="btn btn-info" id="search">
						 	<i class="fa fa-check"></i> ${fns:fy('保存')}
					 	</button>		 		
				 	</div>
				 	
				</form>
			</div>
		</div>
	</div>
	<!-- 结束支付模态窗口 -->
	</shiro:hasPermission>
</body>
</html>