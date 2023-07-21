<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>物流配送列表</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	tbody {margin-bottom: 20px;}
	.lastTr td{border-bottom: 1px solid #e5e5e5;}
	.sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	.sui-table.table-bordered-simple{border: none!important;}
	.sui-modal{border: none!important;border-radius:5px;}
</style>
</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header"><h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>物流配送</span></h4><ul class="sui-breadcrumb"><li>当前位置:</li><li><a href="#">首页</a></li><li class="active">店铺导航</li></ul></dt>
			<dd class="sui-row-fluid form-horizontal screen pt20 mb0">
				<div class="sui-msg msg-large msg-tips msg-block">
				 <div class="msg-con">
					 <ul>
						<h4>提示信息</h4>
						<li>xxxxxxxxx</li>
						<li>xxxxxxxxx</li>
						<li>xxxxxxxxx</li>
						<li>xxxxxxxxx</li>
					 </ul>
					<button type="button" data-dismiss="msgs" class="sui-close">×</button>
				 </div>
				 <s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<dd class="table-css">
				<div class="pull-right" style="padding-right: 9px;">
				 	<a href="freightSave.jsp" class="sui-btn btn-bordered btn-large btn-info">新增配送规则</a>
				</div>
				<table class="sui-table table-bordered-simple">
					<tbody>
					 	<tr colspan="5" style="height: 15px;"></tr>
						<tr>
							<th colspan="5">
								<strong>只售西南和西北</strong>
								<span style="float: right;">
									<span>2016-06-24 17:26:10</span>
								 	<a href="javascript:void(0);" class="sui-btn btn-small btn-success">复制</a>
									<a href="freightSave.jsp" class="sui-btn btn-small btn-warning">修改</a>
									<a href="javascript:void(0);" class="sui-btn btn-small btn-danger del">删除</a>
								</span>
							</th>
						</tr>
						<tr>
						 <td width="50%" class="center">运送到</td>
						 <td width="12%" class="center">首件(件)</td>
						 <td width="12%" class="center">运费(元)</td>
						 <td width="12%" class="center">续件(件)</td>
						 <td width="12%" class="center">运费(元)</td>
						</tr>
						<tr>
						 <td width="50%" class="center">重庆_四川_贵州_云南_西藏_陕西_甘肃_青海_宁夏_新疆</td>
						 <td width="12%" class="center">1</td>
						 <td width="12%" class="center">0.00</td>
						 <td width="12%" class="center">1</td>
						 <td width="12%" class="center">0.00</td>
						</tr>
				 </tbody>
				 
					<tbody>
					 	<tr colspan="5" style="height: 15px;"></tr>
						<tr>
							<th colspan="5">
								<strong>只售西南和西北</strong>
								<span style="float: right;">
									<span>2016-06-24 17:26:10</span>
								 	<a href="javascript:void(0);" class="sui-btn btn-small btn-success">复制</a>
									<a href="javascript:void(0);" class="sui-btn btn-small btn-warning">修改</a>
									<a href="javascript:void(0);" class="sui-btn btn-small btn-danger del" freightId="123455">删除</a>
								</span>
							</th>
						</tr>
						<tr>
						 <td width="50%" class="center">运送到</td>
						 <td width="12%" class="center">首件(件)</td>
						 <td width="12%" class="center">运费(元)</td>
						 <td width="12%" class="center">续件(件)</td>
						 <td width="12%" class="center">运费(元)</td>
						</tr>
						<tr>
						 <td width="50%" class="center">重庆_四川_贵州_云南_西藏_陕西_甘肃_青海_宁夏_新疆</td>
						 <td width="12%" class="center">1</td>
						 <td width="12%" class="center">0.00</td>
						 <td width="12%" class="center">1</td>
						 <td width="12%" class="center">0.00</td>
						</tr>
				 </tbody>
				</table>
				</dd>
			</dl>
		</div>
	</div>
 </div>
 <!-- 取消订单弹出框开始 -->
	<div id="delModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="sui-close">×</button>
					<h4 id="myModalLabel" class="modal-title">删除</h4>
				</div>
				<div class="modal-body" style="padding: 0;">
					<div class="sui-msg msg-tips">
					 <div class="msg-con">删除将影响所有使用该运费模板的商品的运费计算，确定继续删除吗？</div>
					 <s class="msg-icon"></s>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large">确定</button>
					<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 取消订单弹出框结束 -->
 <script type="text/javascript">
	//删除
	$(".del").click(function(){
		var freightId=$(this).attr("freightId");
		$('#delModal').modal('show');
	});
 </script>
</body>
</html>