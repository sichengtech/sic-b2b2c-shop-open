<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
					<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-cogs"></i> <span>在线交易</span><small>交易</small></a>
						<ul class="sub-menu-list">
							<li><a href="${ctx}/views/admin/trade/tradeOrderList.jsp"><i class="fa fa-user"></i> <span>订单管理</span></a></li>
							<li><a href="${ctx}/views/admin/trade/tradeCommentList.jsp"><i class="fa fa-user"></i> <span>评价管理</span></a></li>
							<li><a href="${ctx}/views/admin/trade/tradeReturnProductList.jsp"><i class="fa fa-user"></i> <span>退货退款管理</span></a></li>
							<li><a href="${ctx}/views/admin/trade/tradeReturnMoneyList.jsp"><i class="fa fa-user"></i> <span>退款管理</span></a></li>
							<li><a href="${ctx}/views/admin/trade/tradeConsultationList.jsp"><i class="fa fa-user"></i> <span>咨询管理</span></a></li>
							<li><a href="${ctx}/views/admin/trade/tradeComplaintList.jsp"><i class="fa fa-user"></i> <span>投诉管理</span></a></li>
							<li><a href="${ctx}/views/admin/sysset.jsp"><i class="fa fa-user"></i> <span>消息通知（不做）</span></a></li>
						</ul></li>
					<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-bar-chart-o"></i> <span>财务管理</span><small>财务</small></a>
						<ul class="sub-menu-list">
							<li><a href="${ctx}/views/admin/sysset.jsp"><i class="fa fa-user"></i> <span>余额(是不是没用？)</span></a></li>
							<li><a href="${ctx}/views/admin/settlement/settlementWithdrawalsList.jsp"><i class="fa fa-user"></i> <span>预存款明细</span></a></li>
							<li><a href="${ctx}/views/admin/settlement/settlementRechargeList.jsp"><i class="fa fa-user"></i> <span>充值管理</span></a></li>
							<li><a href="${ctx}/views/admin/settlement/settlementPreDepositList.jsp"><i class="fa fa-user"></i> <span>提现管理</span></a></li>
							<li><a href="${ctx}/views/admin/settlement/settlementPayWayList.jsp"><i class="fa fa-user"></i> <span>支付方式</span></a></li>
							<li><a href="${ctx}/views/admin/settlement/settlementBillList.jsp"><i class="fa fa-user"></i> <span>结算管理</span></a></li>
						</ul></li>						