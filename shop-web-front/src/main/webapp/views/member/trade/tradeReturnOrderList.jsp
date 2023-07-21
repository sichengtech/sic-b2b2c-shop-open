<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<title>${fns:fy('退货及退货列表')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置：')}</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > ${fns:fy('退款及退货')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('退款及退货')}
			</dt>
			<dd>
				<ul class="sui-nav nav-tabs">
					<li class="${empty type ?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm" >${fns:fy('全部退单')} <font></font></a></li>
					<li class="${type eq '1'?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=1" >${fns:fy('退货退款申请')} <font>(${returnOrderCount})</font></a></li>
					<li class="${type eq '2'?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=2" >${fns:fy('退款申请')}<font>(${returnMoneyCount })</font></a></li>
				</ul>
			</dd>
			<dd class="myrefund pl20 pr20">
				<!-- 搜索表单开始 -->
				<div class="sui-row-fluid">
					<form class="sui-form form-inline" id="searchForm" style="margin-top: 10px;" action="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm" method="get">
						<div class="span1"></div>
						<div class="span6 text-right">
							<input type="hidden" name="type" value="${type}"/>
							<div class="control-group">
								<label class="col-sm-3 control-label text-right">${fns:fy('申请时间：')}</label>
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" id="" name="beginApplyDate"
								value="<fmt:formatDate value="${tradeReturnOrder.beginApplyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								format="yyyy-MM-dd" placeholder="${fns:fy('请选择开始申请时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 145px;"> -
								
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" id="" name="endApplyDate" 
								value="<fmt:formatDate value="${tradeReturnOrder.endApplyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								format="yyyy-MM-dd" placeholder="${fns:fy('请选择结束申请时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 145px;"> 
							</div>
						</div>
						<div class="span4 text-align">
							<div class="control-group">
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input value="${searchCate}" name="searchCate" type="hidden"><i class="caret"></i>
											<span>
												<c:choose>
													<c:when test="${empty searchCate || searchCate == '1'}">${fns:fy('商品名称')}</c:when>
													<c:when test="${searchCate == '2'}">${fns:fy('订单编号')}</c:when>
												</c:choose>
											</span>
										</a>
									  	<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="">
											<li class="${searchCate == '1' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="1">${fns:fy('商品名称')}</a></li>
											<li class="${searchCate == '2' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="2">${fns:fy('订单编号')}</a></li>
										</ul>
									</span>
							  	</span>
							  	<input type="text" name="searchValue" placeholder="" class="input-medium" value="${searchValue}" maxlength="128"/>
							</div>
						</div>
						<div class="span1">
							<div class="text-align">
								<button type="submit" class="sui-btn btn-large btn-primary">${fns:fy('搜索')}</button>
							</div>
						</div>
					</form>
				</div>
				<!--搜索表单结束  -->
				<!-- 列表开始 -->
				<sys:message content="${message}"/>
				<c:forEach items="${page.list}" var="tradeReturnOrder" varStatus="index">
					<table class="sui-table table-bordered-simple mt10 table-xlarge">
						<thead>
							<tr>
								<th colspan="5">
									<span class="mr20">${fns:fy('退款编号：')}${tradeReturnOrder.returnOrderId}</span>
									<span class="mr20">
										${fns:fy('申请时间：')}<fmt:formatDate value="${tradeReturnOrder.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</span>
									<span class="mr20">${tradeReturnOrder.store.name}</span>
								</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td width="50%">
									${tradeReturnOrder.tradeOrderItem.name}<br>
									${fns:fy('订单编号：')} ${tradeReturnOrder.orderId}
								</td>
								<td width="10%" class="center">${tradeReturnOrder.returnMoney}</td>
								<td width="10%" class="center">
									${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}
								</td>
								<td width="15%" class="center">
									${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}
								</td>
								<td width="15%" class="center">
									<a href="${ctxm}/trade/tradeReturnOrder/save1.htm?type=${tradeReturnOrder.type}&returnOrderId=${tradeReturnOrder.returnOrderId}" target="_blank" class="sui-btn btn-info"><i class="sui-icon icon-tb-search"></i> ${fns:fy('查看')}</a>
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
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/member/trade/tradeReturnOrderList.js"></script>
</body>
</html>
