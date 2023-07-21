<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>积分明细</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/score/scoreItemsList.js"></script>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">积分明细</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right"></ul>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>操作提示</h5>
					<ul>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
						<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					</ul>
				</div>
				<!-- 提示 end --> 
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-2">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
							<!--快速查询按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips search" title="查询" ><i class="fa fa-search"></i></button>
						</div>
					</div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-1"></div>
					<div class="col-sm-3">
						<div class="iconic-input right">
							<i class="fa fa-search"></i>
							<input type="text" class="form-control input-sm pull-right" placeholder="请输入会员名称进行搜索" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>日志ID</th> 
						<th>会员名称</th> 
						<th>增减类型</th> 
						<th>积分</th> 
						<th>操作时间</th>
						<th>操作阶段</th> 
						<th>操作描述</th> 
						<th>管理员</th> 
					</tr>
				</thead> 
				<tbody>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
					<tr>
						<td>247</td> 
						<td>baiyaozhong</td> 
						<td>增加</td> 
						<td>10</td>
						<td>2016-11-24 15:08:02</td>
						<td>会员注册</td>
						<td>注册会员</td>
						<td></td>
					</tr>
				</tbody>
			</table>
				</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			 <!-- 分页信息结束 -->
			</div>
	</section>
	<!-- panel end -->
		<!-- 开始快速查询窗口 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">	 
					<form id=""> 
						<div class="modal-body form-horizontal adminex-form">
							 <div class="form-group">
								<label class="col-sm-3 control-label text-right">会员名：</label>
								<div class="col-sm-4">
									<input type="text" class="form-control input-sm" id="eq_ordersSnStr" name="eq_ordersSnStr" placeholder="请输入会员名">
								</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label text-right">操作时间：</label>
								<div class="col-sm-4">
									<div class="input-group"> 
										<input type="text" class="form-control input-sm" format="yyyy-MM-dd HH:mm:ss" placeholder="请输入开始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"> 
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="input-group"> 
										<input type="text" class="form-control input-sm" format="yyyy-MM-dd HH:mm:ss" placeholder="请输入结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"> 
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label text-right">操作阶段：</label>
								<div class="col-sm-4"> 
									<select class="form-control input-sm" id="eq_ordersState" name="eq_ordersState">
										<option value="">全部</option> 
										<option value="0">会员登录</option> 
										<option value="1">商品评论</option> 
										<option value="2">订单消费</option> 
										<option value="3">会员注册</option> 
										<option value="4">管理员增减</option> 
									</select>
								</div>
							</div>
							 <div class="form-group">
								<label class="col-sm-3 control-label text-right">操作描述：</label>
								<div class="col-sm-4">
									<input type="text" class="form-control input-sm" id="eq_ordersSnStr" name="eq_ordersSnStr" placeholder="请输入操作描述">
								</div>
							</div>
						</div>
					</form> 
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>关闭
						</button>
						<button type="button" class="btn btn-warning">
							<i class="fa fa-reply"></i>参数重置
						</button> 
						<button type="button" class="btn btn-info">
							<i class="fa fa-search"></i>执行查询
						</button> 
					</div> 
				</div>
		</div>
		<!-- 结束快速查询模态窗口 --> 
</body>
</html>