<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品订单</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #explainModal{width: 500px;margin-left:-250px;border: none;padding: 0;}
	 #delTable td{line-height: 30px;}
	 .sui-modal .modal-header{border-bottom:none!important;}
	 .sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	 .trSpan{margin-right: 15px;}
	 
</style>
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>商品订单</span></h4>
					<ul class="sui-breadcrumb">
						<li>当前位置:</li>
						<li><a href="#">订单</a></li>
						<li><a href="#">商品评价</a></li>
					</ul>
				</dt>
				<dd class="table-css">
					<form class="sui-form form-inline" method="post">
						<div class="span3" style="margin-bottom: 10px;margin-left: 10px;">
							<div class="control-group">
								<label class="col-sm-3 control-label text-right">商品名称：</label>
								<input type="text" placeholder="请输入商品名称" class="input-fat">
							</div>
						</div>
						<div class="span3">
							<div class="control-group">
								<label class="col-sm-3 control-label text-right">评价人：</label>
								<input type="text" placeholder="请输入评价人" class="input-fat">
							</div>
						</div>
						<div class="span1">
							<div class="text-align">
								<a href="javascript:void(0);" class="sui-btn btn-large btn-primary">搜索</a>
							</div>
						</div>
					</form>
					<!--搜索表单结束 -->
					<!--table开始 -->
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th width="89%" class="">评价信息</th>
								<th width="11%" class="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr style="height:15px;"></tr>
							<tr>
								<th colspan="2"> 
									<span class="trSpan"><a href="">Edifier/漫步者 E235电视家庭2.1音响无线蓝牙多媒体音箱重低音炮</a></span>
									<span class="trSpan">商品评分：
										<span><i class="sui-icon icon-tb-favorfill"></i> <i class="sui-icon icon-tb-favorfill"></i> 
											<i class="sui-icon icon-tb-favorfill"></i><i class="sui-icon icon-tb-favorfill"></i>
											<i class="sui-icon icon-tb-favorfill"></i>
										</span>
									</span>
									<span class="trSpan">评价人：<span>hbjbuyer [2016-09-18 11:21:30]</span></span>
								</th>
							</tr>
							<tr>
								<td width="87%">
									<strong>买家评价:</strong>
									<span class="count">质量不错，非常愉快的一次购物</span>
								</td>
								<td class="" width="13%" style="text-align: right;">
									<a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-primary explain">买家解释</a>
								</td>
							</tr>

							<tr style="height:15px;"></tr>
							<tr>
								<th colspan="2"> 
									<span class="trSpan"><a href="">Edifier/漫步者 E235电视家庭2.1音响无线蓝牙多媒体音箱重低音炮</a></span>
									<span class="trSpan">商品评分：
										<span><i class="sui-icon icon-tb-favorfill"></i> <i class="sui-icon icon-tb-favorfill"></i> 
											<i class="sui-icon icon-tb-favorfill"></i><i class="sui-icon icon-tb-favorfill"></i>
											<i class="sui-icon icon-tb-favorfill"></i>
										</span>
									</span>
									<span class="trSpan">评价人：<span>hbjbuyer [2016-09-18 11:21:30]</span></span>
								</th>
							</tr>
							<tr>
								<td width="87%">
									<strong>买家评价:</strong>
									<span class="count">质量不错，非常愉快的一次购物</span>
								</td>
								<td class="center" width="13%" style="text-align: right;">
									<a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-primary explain">买家解释</a>
								</td>
							</tr>

							<tr style="height:15px;"></tr>
							<tr>
								<th colspan="2"> 
									<span class="trSpan"><a href="">Edifier/漫步者 E235电视家庭2.1音响无线蓝牙多媒体音箱重低音炮</a></span>
									<span class="trSpan">商品评分：
										<span><i class="sui-icon icon-tb-favorfill"></i> <i class="sui-icon icon-tb-favorfill"></i> 
											<i class="sui-icon icon-tb-favorfill"></i><i class="sui-icon icon-tb-favorfill"></i>
											<i class="sui-icon icon-tb-favorfill"></i>
										</span>
									</span>
									<span class="trSpan">评价人：<span>hbjbuyer [2016-09-18 11:21:30]</span></span>
								</th>
							</tr>
							<tr>
								<td width="87%">
									<strong>买家评价:</strong>
									<span class="count">质量不错，非常愉快的一次购物</span>
								</td>
								<td class="center" width="13%" style="text-align: right;">
									<a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-primary explain">买家解释</a>
								</td>
							</tr>
						</tbody>
					</table>
					 <!-- table结束 -->
				</dd>
			</dl>
			</div>
		</div>
	</div>
	<!-- 卖家解释弹出框开始 -->
	<div id="explainModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="sui-close">×</button>
					<h4 id="myModalLabel" class="modal-title">取消订单</h4>
				</div>
				<div class="modal-body" style="padding: 0;">
					<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
						<input type="hidden" name="ordersId" value="142">
						<table class="sui-table table-bordered" id="delTable">
							<tbody>
								<tr>
									<td class="" style="text-align: right;">评价内容：</td>
									<td><div class="text-box"><span class="c-success" id="countModal" name="ordersSn"> 质量不错，非常愉快的一次购物</span></div></td>
								</tr>
								<tr>
									<td class="" style="text-align: right;"><font color="red">*</font>解释内容：</td>
									<td>
										<textarea rows="4" cols="30" style="width:325px;"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer" style="background-color: #ffffff;">
					<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large">确定</button>
					<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 卖家解释弹出框结束 -->
	<script type="text/javascript">
	//取消订单
	$(".explain").click(function(){
		var count=$(this).parent().parent().find(".count").text();
		$("#countModal").html(count);
		$('#explainModal').modal('show');
	});
	</script>
</body>
</html>