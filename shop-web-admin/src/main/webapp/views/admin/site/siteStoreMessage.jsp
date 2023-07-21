<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>消息模板邮件</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">店铺系统消息</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>操作提示</h5>
					<ul>
						<li>商户类消息模板，主要用于需要操作或有必要通知的商户类信息。</li>
						<li>消息可以以三种形式发送，站内信、短信、邮箱。站内信为必须发送选项，短信、邮箱为可选择发送选项，管理员可以自由设定</li>
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
								<i class="fa fa-volume-down"></i>站内信 
							</button>
							<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#SMSModal">
								<i class="fa fa-mobile"></i>短信 
							</button>
							<button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#emailModal">
								<i class="fa fa-envelope-o"></i>邮件
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
								<i class="fa fa-volume-down"></i>站内信 
							</button>
							<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#SMSModal">
								<i class="fa fa-mobile"></i>短信 
							</button>
							<button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#emailModal">
								<i class="fa fa-envelope-o"></i>邮件
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
		<!-- 开始站内信操作模态窗口 -->
		<div class="modal fade in" id="siteInModal" tabindex="-1" role="dialog" aria-hidden="false" style="display:none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
		 				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title"> 编辑系统消息
							<a class="btn btn-info btn-xs m-l-10" role="button" data-toggle="collapse" href="#OperationTipsEdit1" aria-expanded="true" title="操作提示"> 
								<i class="fa fa-exclamation"></i> &nbsp;提示 
							</a> 
						</h4>
					</div>
					<div class="modal-body">
						<ul class="text-info p-b-10 p-l-15 collapse in" role="tabpanel" id="OperationTipsEdit1" aria-expanded="true">
							<li>插入的变量必需包括花括号“{}”，当应用范围不支持该变量时，该变量将不会在前台显示(变量后边的分隔符也不会显示)，留空为系统默认设置。</li>
							<li>变量函数说明：站点名称 {$siteName} | 时间点 {$time} | 验证码 {$verifyCode}</li>
						</ul>
						<form action="" id="payForm" method="post">
							<div class="form-group">
								<label class="control-label">模板编号&nbsp;: </label>
								<input type="text" id="" name="" class="form-control" disabled="" data-parsley-id="4" value="memberArrivalNotice">
							</div>
							<div class="form-group">
						 		<label class="control-label"> 模板名称&nbsp;: </label>
						 		<input type="text" id="" name="" class="form-control" disabled="" data-parsley-id="6" value="到货通知提醒">
							</div>
							<div class="form-group">
								<label class="control-label">模板内容&nbsp;:</label>
								<textarea id="" name="" class="form-control" rows="5">您的到货通知：商品“{$goodsName}”已经上架，可以正常购买了。</textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer"> 
						<a href="javascript:;" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i>放弃操作</a> 
						<a href="javascript:;" class="btn btn-info" data-loading-text="Loading..." nc-ajax-submit-target="payForm" nc-ajax-submit=""><i class="fa fa-check"></i>确认提交</a> 
					</div>
			 </div>
			</div>
		</div>
		<!-- 结束站内信操作模态窗口 -->
		<!-- 开始站短信操作模态窗口 -->
		<div class="modal fade in" id="SMSModal" tabindex="-1" role="dialog" aria-hidden="false" style="display:none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
		 				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title"> 编辑系统消息
							<a class="btn btn-info btn-xs m-l-10" role="button" data-toggle="collapse" href="#OperationTipsEdit2" aria-expanded="true" title="操作提示"> 
								<i class="fa fa-exclamation"></i> &nbsp;提示 
							</a> 
						</h4>
					</div>
					<div class="modal-body">
						<ul class="text-info p-b-10 p-l-15 collapse in" role="tabpanel" id="OperationTipsEdit2" aria-expanded="true">
							<li>插入的变量必需包括花括号“{}”，当应用范围不支持该变量时，该变量将不会在前台显示(变量后边的分隔符也不会显示)，留空为系统默认设置。</li>
							<li>变量函数说明：站点名称 {$siteName} | 时间点 {$time} | 验证码 {$verifyCode}</li>
						</ul>
						<form action="" id="payForm" method="post">
							<div class="form-group">
								<label class="control-label">模板编号&nbsp;: </label>
								<input type="text" id="" name="" class="form-control" disabled="" data-parsley-id="4" value="memberArrivalNotice">
							</div>
							<div class="form-group">
						 		<label class="control-label"> 模板名称&nbsp;: </label>
						 		<input type="text" id="" name="" class="form-control" disabled="" data-parsley-id="6" value="到货通知提醒">
							</div>
							<div class="form-group">
						 		<label class="control-label"> 是否发送短信&nbsp;: </label>
								<input type="radio" name="sex" value="1">&nbsp;是
								<input type="radio" name="sex" value="2">&nbsp;否
							</div>
							<div class="form-group">
								<label class="control-label">短信模板内容&nbsp;:</label>
								<textarea id="" name="" class="form-control" rows="5">您的到货通知：商品“{$goodsName}”已经上架，可以正常购买了。</textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer"> 
						<a href="javascript:;" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i>放弃操作</a> 
						<a href="javascript:;" class="btn btn-info" data-loading-text="Loading..." nc-ajax-submit-target="payForm" nc-ajax-submit=""><i class="fa fa-check"></i>确认提交</a> 
					</div>
			 </div>
			</div>
		</div>
		<!-- 结束短信操作模态窗口 -->
</body>
</html>