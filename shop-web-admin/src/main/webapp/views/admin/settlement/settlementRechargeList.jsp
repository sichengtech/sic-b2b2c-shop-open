<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>充值管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementRechargeList.js"></script>
<style type="text/css">
	#rechargeTable td{line-height: 29px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">充值列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 充值列表</a></li>
				<shiro:hasPermission name="settlement:settlementRecharge:save">
				<li class=""><a href="${ctxa}/settlement/settlementRecharge/save1.do" > <i class="fa fa-user"></i> 充值添加</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>如果系统平台已确认收到充值款，但系统的充值单还是未支付状态，可以点击审核按钮手动更改成已支付状态。</li>
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
						<shiro:hasPermission name="settlement:settlementRecharge:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/settlement/settlementRecharge/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/settlement/settlementRecharge/list.do" method="get" id="searchForm">
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
			<table class="table table-hover table-condensed table-bordered" id="rechargeTable">
				<thead>
					<tr>
						<th></th>
						<th>充值编号</th>
						<th>会员登录名</th>
						<th>充值金额</th>
						<th>充值时间</th>
						<th>支付方式</th>
						<th>支付状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="settlementRecharge">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${settlementRecharge.rechargeId}</td>
						<td>${settlementRecharge.userMain.loginName}</td>
						<td>${settlementRecharge.rechargeMoney}</td>
						<td><fmt:formatDate value="${settlementRecharge.rechargeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${settlementRecharge.settlementPayWay.name}</td>
						<td>${fns:getDictLabel(settlementRecharge.staus, 'settlement_pay_status', '')}</td>
						<td>
							<shiro:hasPermission name="settlement:settlementRecharge:edit">
							<%-- <a class="btn btn-info btn-sm" href="${ctxa}/settlement/settlementRecharge/edit1.do?rechargeId=${settlementRecharge.rechargeId}">
								<i class="fa fa-edit"></i> 编辑
							</a> --%>
							</shiro:hasPermission>
							<c:if test="${settlementRecharge.staus==0}">
								<shiro:hasPermission name="settlement:settlementRecharge:auth">
								<a class="btn btn-info btn-sm examine" href="javascript:;" reId="${settlementRecharge.rechargeId}" reNumber="${settlementRecharge.rechargeNumber}" 
								reMoney="${settlementRecharge.rechargeMoney}" uId="${settlementRecharge.UId}" reMemName="${settlementRecharge.userMain.loginName}">
									<i class="fa fa-check-square-o"></i> 审核
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="settlement:settlementRecharge:drop">
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/settlement/settlementRecharge/delete.do?rechargeId=${settlementRecharge.rechargeId}">
									<i class="fa fa-trash-o"></i> 删除
								</button>
								</shiro:hasPermission>
							</c:if>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">支付时间 : <fmt:formatDate value="${settlementRecharge.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
							<p datano="0" columnno="1" class="dt-grid-cell">支付终端 : ${fns:getDictLabel(settlementRecharge.payTerminal, 'settlement_terminal_type', '')}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">管理员编号 : ${settlementRecharge.user.no}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">管理员 : ${settlementRecharge.user.loginName}</p>
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
	<!-- 开始审核模态框 -->
	<div class="modal fade" id="examineModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
			<div class="modal-content">
				<form id="examineForm" action="${ctxa}/settlement/settlementRecharge/examine.do" method="post"> 
					<div class="modal-body form-horizontal adminex-form">
						<div class="form-group">
							<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo" style="padding-right: 15px;">
								<li class="m-b-3"> 手动审核预存款充值信息是否充值成功，审核后将自动增加相应会员的预存款金额且该充值记录将不可再进行修改，请慎重操作。</li>
							</ul>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">充值编号：</label>
							<div class="col-sm-8" id="reNumber_id">充值编号</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">充值金额(元)：</label>
							<div class="col-sm-8" id="reMoney_id">充值金额</div>
							<input type="hidden" name="rechargeMoney"/>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">会员名称：</label>
							<div class="col-sm-8" id="reMemName_id">会员名称</div>
							<input type="hidden" name="uId"/>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">支付时间：</label>
							<div class="col-sm-8">
								<input name="payDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
								value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">付款方式：</label>
							<div class="col-sm-8">
								<select name="payWayId" class="form-control m-bot15 input-sm">
									<c:forEach items="${listPW}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">付款终端：</label>
							<div class="col-sm-8">
								<select name="payTerminal" class="form-control m-bot15 input-sm">
									<c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">第三方付款平台交易号：</label>
							<div class="col-sm-8">
								<input type="text" name="tradeNumber"  maxlength="24" class="form-control input-sm" value=""/>
								<input type="hidden" name="rechargeId" value=""/>
							</div>
						</div>
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
</body>
</html>