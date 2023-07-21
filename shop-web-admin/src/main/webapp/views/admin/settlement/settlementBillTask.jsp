<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>结算任务</title>
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
			<h4 class="title">任务列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>每月由定时任务自动结算，任务列表自动添加任务，管理员可以手动添加结算任务</li>
					<li>点击运行可以重新结算本月的账单</li>
					<li>如果有失败的任务可以点击重算，重新执行错误的任务</li>
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
						<!-- 添加 -->
						<shiro:hasPermission name="settlement:settlementPayWay:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/settlement/settlementPayWay/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>名称</th>
						<th>任务起止时间</th>
						<th>任务状态</th>
						<th>任务结果</th>
						<shiro:hasPermission name="settlement:settlementBill:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2017-01</td>
						<td>2017-01-01至2017-01-31</td>
						<td>已运行</td>
						<td>成功100，失败0</td>
						<td>
							<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> 运行
							</button>
						</td>
					</tr>
					<tr>
						<td>2017-01</td>
						<td>2017-01-01至2017-01-31</td>
						<td>已运行</td>
						<td>成功99，失败1</td>
						<td>
							<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> 运行
							</button>
							<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> 重算
							</button>
						</td>
					</tr>
					<tr>
						<td>2017-01</td>
						<td>2017-01-01至2017-01-31</td>
						<td>未运行</td>
						<td></td>
						<td>
							<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> 运行
							</button>
							<button type="button" class="btn btn-info btn-sm retry" id="${settlementBill.billId}" ${settlementBill.status != '40'?'':'disabled="disabled"'}>
								<i class="fa fa-pencil"></i> 重算
							</button>
						</td>
					</tr>
					<tr>
						<td>2017-01</td>
						<td>2017-01-01至2017-01-31</td>
						<td>运行中</td>
						<td></td>
						<td>
						</td>
					</tr>
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
	<!-- 开始支付模态窗口 -->
	<div class="modal fade in" id="payModal" tabindex="-1" role="dialog" aria-hidden="false" style="display:none;">
		<div class="modal-content">
			<div class="modal-body" style="padding-bottom: 0;">
				<form action="${ctxa}/settlement/settlementBill/pay2.do" id="payForm">
					<input type="hidden" name="billId" value=""/>
					<div class="form-group">
						<select class="form-control input-sm" id="eq_ordersFrom" name="sources">
							 <option value="" class="firstOption">2010</option>
							 <option value="" class="firstOption">2011</option>
							 <option value="" class="firstOption">2012</option>
							 <option value="" class="firstOption">2013</option>
							 <option value="" class="firstOption">2014</option>
							 <option value="" class="firstOption">2015</option>
						</select>
					</div>
				 	<div class="modal-footer" style="padding: 0 0 15px 0;border-top: none;"> 
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="(function(){layer.closeAll('page');}())">
							 <i class="fa fa-times"></i> 关闭
						 </button>	
						 <button type="submit" class="btn btn-info" id="search">
						 	<i class="fa fa-check"></i> 保存
					 	</button>		 		
				 	</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 结束支付模态窗口 -->
</body>
</html>