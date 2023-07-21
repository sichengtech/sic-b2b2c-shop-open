<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('账单详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="6"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/settlement/settlementBillDetail.js"></script>
<!--表示使用N号二级菜单 -->
<style type="text/css">
#formDd {padding-top: 0px;padding-bottom:0px; }
.header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
.header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
.store-set dd{padding-top: 0px!important;}
#orderTable{border-right: none; width: 98%;margin-left: 10px;border-top:none; }
.sui-table.table-bordered-simple th:first-child, .sui-table.table-bordered-simple td:first-child{border-left: none!important;}
.noDateTd{line-height: 150px!important;text-align: center!important;color:#8c8c8c;}
.settlementBillPage{text-align: center;margin-bottom: 15px;margin-top: 15px;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('账单详情')}</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('运营管理')}</li>
						<li class="active">${fns:fy('账单详情')}</li>
					</ul>
				</dt>

				<!--导航开始 -->
				<dd class="sui-row-fluid sui-form form-horizontal screen" style="padding-bottom: 0px;width: 98%;">
					<div class="header header1" style="background: none;height: 37px; box-shadow: none;border-bottom: 1px solid #e5e5e5;margin-bottom: 0px;">
						<div class="sui-container">
							<div class="sui-navbar container nav-box">
								<div class="navbar-inner">
									<ul class="sui-nav">
										<li><a style="height: 37px;line-height: 37px;" href="${ctxs}/settlement/settlementBill/list.htm">${fns:fy('结算管理')}</a></li>
										<li class="active"><a style="height: 37px;line-height: 37px;" href="javascript:;">${fns:fy('账单详情')}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</dd>
				<!--导航结束 --> 
				<!-- 账单详情开始 -->
				<form class="sui-form form-inline" method="post">
					<dd id="formDd">
						<div class="control-group">
							<label class="control-label">${fns:fy('账单编号：')}</label> 
							<small class="billId">${settlementBill.billId}</small>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('商家：')}</label> 
							<small>${settlementBill.store.name}</small>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('账单起始时间：')}</label> 
							<small>
								<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd"/> ${fns:fy('至')} 
								<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd"/>
							</small>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('出账日期：')}</label> 
							<small><fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy-MM-dd"/></small>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('平台应结金额（元）：')}</label> 
							<small>
								${not empty settlementBill.billAmount && settlementBill.billAmount ne '0.000'?settlementBill.billAmount:'0'}= 
								${not empty settlementBill.orderAmount && settlementBill.orderAmount ne '0.000'?settlementBill.orderAmount:'0'}(${fns:fy('订单金额')}) 
								- ${not empty settlementBill.platformCommission && settlementBill.platformCommission ne '0.000'?settlementBill.platformCommission:'0'}(${fns:fy('收取佣金')})
								- ${not empty settlementBill.refund && settlementBill.refund ne '0.000'?settlementBill.refund:'0'}(${fns:fy('退单金额')}) 
								+ ${not empty settlementBill.returnCommission && settlementBill.returnCommission ne '0.000'?settlementBill.returnCommission:'0'}(${fns:fy('退还佣金')})
							</small>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('结算状态：')}</label> 
							<small>${fns:getDictLabel(settlementBill.status, 'settlement_bill_status', '')}</small>
						</div>
						<div class="control-group" style="padding-left: 53px;">
							<shiro:hasPermission name="settlement:settlementBill:edit">
								<a href="${ctxs}/settlement/settlementBill/print.htm?billId=${settlementBill.billId}" class="sui-btn btn-xlarge btn-primary" target="_Blank">${fns:fy('打印')}</a>
							</shiro:hasPermission>
						</div>
					</dd>
				</form>
				<div class="clear"></div>
				<!-- 账单详情结束 -->
				<!--订单状态导航开始 -->
				<input type="hidden" name="beginPlaceOrderTime" id="beginPlaceOrderTime" value="<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<input type="hidden" name="endPlaceOrderTime" id="endPlaceOrderTime" value="<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<dd class="sui-row-fluid sui-form form-horizontal screen mb0" style="padding-bottom: 0px;margin-bottom: 0px;width: 98%;">
					<div class="header header1" style="background: none;height: 37px; box-shadow: none;border-bottom: 1px solid #e5e5e5;margin-bottom: 0px;">
						<div class="sui-container">
							<div class="sui-navbar container nav-box">
								<div class="navbar-inner">
									<ul class="sui-nav">
										<li class="search active" type="1"><a style="height: 37px;line-height: 37px;" href="javascript:;">${fns:fy('订单列表')}</a></li>
										<li class="search" type="2"><a style="height: 37px;line-height: 37px;" href="javascript:;">${fns:fy('退单列表')}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</dd>
				<!--订单状态导航结束 --> 
				<!-- 订单列表开始 -->
				<div id="tableDiv"></div>
				<!-- 订单列表结束 -->
			</dl>
		</div>
	</div>
	<%@ include file="/views/seller/include/deleteSure.jsp"%>
</body>
</html>