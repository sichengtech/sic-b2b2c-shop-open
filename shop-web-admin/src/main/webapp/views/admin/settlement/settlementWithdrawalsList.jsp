<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>提现管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementWithdrawalsList.js"></script>
<style type="text/css">
	#withdrawalsTable td{line-height: 29px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">提现列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<%-- <ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 提现列表</a></li>
				<shiro:hasPermission name="settlement:settlementWithdrawals:save"><li class=""><a href="${ctxa}/settlement/settlementWithdrawals/save1.do" > <i class="fa fa-user"></i> 提现添加</a></li></shiro:hasPermission>
			</ul> --%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>未支付的提现单可以点击查看选项更改提现单的支付状态。</li>
					<li>点击删除可以删除未支付的提现单。</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<%-- <shiro:hasPermission name="settlement:settlementWithdrawals:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/settlement/settlementWithdrawals/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<form action="${ctxa}/settlement/settlementWithdrawals/list.do" method="get" id="searchForm">
					<div class="col-sm-3 col-md-offset-7">
						<div class="iconic-input right">
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<a href="javaScript:;" class="search"><i class="fa fa-search"></i></a>
							<input type="text" name="loginName" maxlength="64" value="${loginName}" class="form-control input-sm pull-right" placeholder="请输入会员登录名" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered" id="withdrawalsTable">
				<thead>
					<tr>
						<th></th>
						<th>提现编号</th>
						<th>会员登录名</th>
						<th>提现金额</th>
						<th>收款账号</th>
						<th>收款方式</th>
						<th>提现状态</th>
						<th>交易号</th>
						<th>提现终端</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="settlementWithdrawals">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${settlementWithdrawals.rechargeNumber}</td>
						<td>${settlementWithdrawals.userMain.loginName}</td>
						<td>${settlementWithdrawals.money}</td>
						<td>${settlementWithdrawals.receivablesNumber}</td>
						<td>${settlementWithdrawals.settlementPayWay.name}</td>
						<td>${fns:getDictLabel(settlementWithdrawals.status, 'settlement_withdrawals_status', '')}</td>
						<td>${settlementWithdrawals.transactionNumber}</td>
						<td>${fns:getDictLabel(settlementWithdrawals.payTerminal, 'settlement_terminal_type', '')}</td>
						<td>
							<%-- <a class="btn btn-info btn-sm" href="${ctxa}/settlement/settlementWithdrawals/edit1.do?withdrawalsId=${settlementWithdrawals.withdrawalsId}">
								<i class="fa fa-edit"></i> 编辑
							</a> --%>
							<c:if test="${settlementWithdrawals.status==0}">
								<shiro:hasPermission name="settlement:settlementWithdrawals:auth">
								<a class="btn btn-info btn-sm examine" href="javascript:;" swId="${settlementWithdrawals.withdrawalsId}" swNumber="${settlementWithdrawals.rechargeNumber}" swMoney="${settlementWithdrawals.money}" 
								swMemName="${settlementWithdrawals.userMain.loginName}" uId="${settlementWithdrawals.UId}" swPayWay="${settlementWithdrawals.payWayId}" swReNumber="${settlementWithdrawals.receivablesNumber}" swAccountName="${settlementWithdrawals.accountName}">
									<i class="fa fa-check-square-o"></i> 审核
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="settlement:settlementWithdrawals:drop">
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/settlement/settlementWithdrawals/delete.do?withdrawalsId=${settlementWithdrawals.withdrawalsId}">
									<i class="fa fa-trash-o"></i> 删除
								</button>
								</shiro:hasPermission>
							</c:if>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">申请时间 : <fmt:formatDate value="${settlementWithdrawals.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
							<p datano="0" columnno="0" class="dt-grid-cell">开户名 :  ${settlementWithdrawals.accountName}</p>
							<p datano="0" columnno="0" class="dt-grid-cell">支付时间 : <fmt:formatDate value="${settlementWithdrawals.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
							<p datano="0" columnno="2" class="dt-grid-cell">管理员 : ${settlementWithdrawals.user.loginName}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">管理员编号 : ${settlementWithdrawals.user.no}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">拒绝提现理由 : ${settlementWithdrawals.rejectionReason}</p>
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
	<shiro:hasPermission name="settlement:settlementWithdrawals:auth">
	<!-- 开始审核模态框 -->
	<div class="modal fade" id="examineModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
			<div class="modal-content">
				<form id="examineForm" action="${ctxa}/settlement/settlementWithdrawals/examine.do" method="post"> 
					<div class="modal-body form-horizontal adminex-form">
						<div class="form-group">
							<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo" style="padding-right: 15px;">
								<li class="m-b-3"> 手动审核预存款提现信息，审核后将自动减少相应会员的预存款金额且该提现记录将不可再进行修改，请慎重操作</li>
							</ul>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">提现编号：</label>
							<div class="col-sm-8" id="swNumber_id">提现编号</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">提现金额(元)：</label>
							<div class="col-sm-8" id="swMoney_id">提现金额</div>
							<input name="money" type="hidden">
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">会员名称：</label>
							<div class="col-sm-8" id="swMemName_id">会员名称</div>
							<input type="hidden" name="uId"/>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">收款方式：</label>
							<div class="col-sm-8" id="swPayWay_id">收款方式</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">收款账号：</label>
							<div class="col-sm-8" id="swReNumber_id">收款账号</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">开户名：</label>
							<div class="col-sm-8" id="swAccountName_id">开户名</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">状态：</label>
							<div class="col-sm-8">
								<input id="radio1" type="radio" name="status" value="1" checked="checked"/>&nbsp;已支付
								<input id="radio2" type="radio" name="status" value="2"/>&nbsp;拒绝提现
							</div>
						</div>
						<div class="form-group dateTime" style="">
							<label class="col-sm-3 control-label text-right">支付时间：</label>
							<div class="col-sm-8">
								<input name="payTime" type="text"  maxlength="20" class="form-control input-sm J-date-start"
								value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group reason" style="display:none"> 
							<label class="col-sm-3 control-label text-right">拒绝提现理由：</label>
							<div class="col-sm-8">
								<textarea rows="3" cols="45" name="rejectionReason"></textarea>
								<!-- <input type="text" name="rejectionReason"  maxlength="521" class="form-control input-sm" value=""/> -->
							</div>
						</div>
						<input type="hidden" name="id" value=""/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
							<i class="fa fa-times"></i> 关闭
						</button>
						<button type="submit" class="btn btn-info">
							<i class="fa fa-check"></i> 保存
						</button> 
					</div>
				</form>
			</div>
	</div>
	<!-- 结束审核模态框 -->
	</shiro:hasPermission>
</body>
</html>