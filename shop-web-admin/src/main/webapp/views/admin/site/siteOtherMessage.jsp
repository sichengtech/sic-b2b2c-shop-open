<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>其他消息</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">其他消息</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>操作提示</h5>
					<ul>
						<li>只要用于手机或邮箱的发码类消息，如：手机注册、手机绑定、找回密码等。</li>
						<li>在编辑消息模板时不能添加新的变量函数，但可以修改变量函数的位置。</li>
					</ul>
				</div>
				<!-- 提示 end --> 
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-6">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="iconic-input right">
							<i class="fa fa-search"></i>
							<input type="text" class="form-control input-sm pull-right" placeholder="请输入模板名称" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>模板编号</th>
						<th>模板名称</th> 
						<th>管理操作</th> 
					</tr>
				</thead> 
				<tbody>
					<tr>
						<td>memberArrivalNotice	</td> 
						<td>到货通知提醒</td> 
						<td>
							<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#siteInModal">
								<i class="fa fa-edit"></i>编辑 
							</button>
							 <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#delModal">
								<i class="fa fa-trash-o"></i>删除
							</button>
						</td> 
					</tr>
					 <tr class="2">
						<td>memberArrivalNotice	</td> 
						<td>预定订单尾款支付提醒</td> 
						<td>
							<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#siteInModal">
								<i class="fa fa-edit"></i>编辑 
							</button>
							 <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#delModal">
								<i class="fa fa-trash-o"></i>删除
							</button>
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
	</section>
	<!-- panel end -->
		<!-- 开始删除操作模态窗口 -->
		<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
			<div class="modal-dialog">
				<div class="modal-content">	 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" id="">
							操作提醒
						</h4>	 
					</div>
					<div class="modal-body">
						<div class="alert alert-danger alert-block fade in">
							<h4><i class="fa fa-info-circle m-r-10"></i>删除操作</h4>
							<p>是否确定删除</p>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>放弃操作
						</button>
						<button type="button" class="btn btn-info">
							<i class="fa fa-check"></i>确认操作
						</button> 
					</div> 
				</div>
			</div>
		</div>
		<!-- 结束删除操作提醒窗口 -->
</body>
</html>