<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="refunds" value ="${fns:fy('退货退款')}"></c:set> 
<c:set var="refund" value ="${fns:fy('退款')}"></c:set> 
<title>${type eq 1?refunds:refund}${fns:fy('订单管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeReturnOrderList.js"></script>
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
	    <header class="panel-heading custom-tab tab-right">
			<h4 class="title">${type eq 1?refunds:refund }${fns:fy('订单列表')}</h4>
			<%@ include  file="../include/functionBtn.jsp"%>
		</header>
		
		<!-- panel-body开始 -->
        <div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
			    <h5>${fns:fy('操作提示')}</h5>
               	<ul>
               		<li>${fns:fy('退款管理.退货退款.操作提示1')}</li>
               		<li>${fns:fy('退款管理.退货退款.操作提示2')}</li>
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
			   <form action="${ctxa}/trade/tradeReturnOrder/list.do" method="get" id="searchForm">
				    <div class="col-sm-3 col-md-offset-7">
				        <div class="iconic-input right">
	                       	<a href="javaScript:;" class="searchBtn"><i class="fa fa-search"></i></a>
	                       	<input type="text" name="returnOrderId" class="form-control input-sm pull-right" maxlength="18" 
	                       		placeholder="${fns:fy('请输入退单单号进行精确搜索')}" style="border-radius: 30px;max-width:250px;" value="${tradeReturnOrder.returnOrderId}">
	                   		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	                   		<input type="hidden" name="type" value="${tradeReturnOrder.type}"/>
	                    </div>
	                 </div>
				</form>
			</div>
            <!-- 按钮结束  -->
	        <!-- Table开始 -->
	        <div class="table-responsive">
           		<table class="table table-hover table-condensed table-bordered">
                 <thead>
                    <tr>
                        <th></th>
                        <th>${fns:fy('退单单号')}</th>
                        <th>${fns:fy('申请人(买家)')}</th>
                        <th>${fns:fy('退款金额')}(${fns:fy('元')})</th>
                        <th>${fns:fy('申请时间')}</th>
                        <th>${fns:fy('商家处理')}</th>
                        <th>${fns:fy('商家处理时间')}</th>
                        <th>${fns:fy('平台处理')}</th>
                        <th>${fns:fy('退款/退货状态')}</th>
						<th>${fns:fy('操作')}</th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach items="${page.list}" var="tradeReturnOrder" varStatus="index">
					<tr class="${index.count}">
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${tradeReturnOrder.returnOrderId}</td>
						<td>${tradeReturnOrder.userMain.loginName}</td>
						<td>${tradeReturnOrder.returnMoney}</td>
						<td><fmt:formatDate value="${tradeReturnOrder.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}</td>
						<td><fmt:formatDate value="${tradeReturnOrder.businessHandleDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}</td>
						<td>${fns:getDictLabel(tradeReturnOrder.status, 'trade_return_order_status', '')}</td>
						<td>
							<c:if test="${tradeReturnOrder.type eq '1'}">
								<c:if test="${tradeReturnOrder.status eq '10' || tradeReturnOrder.status eq '50'}">
									<shiro:hasPermission name="trade:tradeReturnOrder:auth">
									<a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeReturnOrder/handle1.do?returnOrderId=${tradeReturnOrder.returnOrderId}&stat=1&type=${type}" ${tradeReturnOrder.systemHandle == 0 ?"":"disabled='disabled'"} >
		                               <i class="fa fa-gears"></i> ${fns:fy('处理')}
		                            </a>
		                            </shiro:hasPermission>
								</c:if>
							</c:if>
							<c:if test="${tradeReturnOrder.type eq '2'}">
								<c:if test="${tradeReturnOrder.businessHandle ne '2'}">
									<shiro:hasPermission name="trade:tradeReturnOrder:auth">
									<a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeReturnOrder/handle1.do?returnOrderId=${tradeReturnOrder.returnOrderId}&stat=1&type=${type}" ${tradeReturnOrder.systemHandle == 0 ?"":"disabled='disabled'"} >
		                               <i class="fa fa-gears"></i> ${fns:fy('处理')}
		                            </a>
		                            </shiro:hasPermission>
								</c:if>
							</c:if>
						
                            <shiro:hasPermission name="trade:tradeReturnOrder:view">
                            <a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeReturnOrder/handle1.do?returnOrderId=${tradeReturnOrder.returnOrderId}&stat=2&type=${type}">
                               <i class="fa fa-eye"></i> ${fns:fy('查看')}
                            </a>
                            </shiro:hasPermission>
               			 </td>
					</tr>
					<tr id="${index.count}" class="detail-extra">
                        <td datano="0" colspan="14" >
                            <p datano="0" columnno="0" class="dt-grid-cell">${fns:fy('申请原因')}: ${fns:getDictLabel(tradeReturnOrder.returnReason, 'trade_return_reason', '')}</p>       
                            <p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('申请举证')}:
                            	<c:forEach items="${tradeReturnOrder.tradeReturnOrderVoucherList}" var="voucher">
                            		<c:if test="${voucher.returnOrderId ==tradeReturnOrder.returnOrderId}">
                            			<a target="_blank" href="${ctxfs}${voucher.path}">
	                            			<img alt="" src="${ctxfs}${voucher.path}@40x40" style="width: 40px;height: 40px;"
	                            			onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
                            			</a>
                            		</c:if>
                            	</c:forEach>
                             </p>
                            <p datano="0" columnno="2" class="dt-grid-cel ">${fns:fy('涉及商品')} :
                            	<a target="_blank" href="${ctxf}/detail/${tradeReturnOrder.tradeOrderItem.PId}.htm">
                            		${tradeReturnOrder.tradeOrderItem.name}
                            	</a>
                            </p>
                            <p datano="0" columnno="3" class="dt-grid-cell">${fns:fy('商品')}ID :${tradeReturnOrder.tradeOrderItem.PId}</p>
                            <p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('商品图片')}  :
                            	<c:if test="${not empty tradeReturnOrder.tradeOrderItem.thumbnailPath}">
				 					<a target="_blank" href="${ctxf}/detail/${tradeReturnOrder.tradeOrderItem.PId}.htm">
					 					<img src="${ctxfs}${tradeReturnOrder.tradeOrderItem.thumbnailPath}" style="width: 60px;height: 60px;"
					 					onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
				 					</a>
				 				</c:if>
                            </p>
                            <p datano="0" columnno="5" class="dt-grid-cell">${fns:fy('店铺名称')} :${tradeReturnOrder.tradeOrder.BName} </p>
                            <p datano="0" columnno="6" class="dt-grid-cell" style="word-break: break-all;">${fns:fy('商家处理备注')} :${tradeReturnOrder.businessHandleRemarks}</p>
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