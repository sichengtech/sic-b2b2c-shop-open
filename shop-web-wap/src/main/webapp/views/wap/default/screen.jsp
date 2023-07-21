<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>思程商城</title>
	<%@ include file="include/head.jsp"%>
	<!-- 业务js -->
	<script type="text/javascript" src="screen.js"></script>
	<style type="text/css">
		.sift-expand .deal-price dd input{font-size: 13px;}
	</style>
</head>
<body ontouchstart>
	<div class="page-group" style="width: 80%;">
		<div class="page page-current" id='screen'>
			<div class="sift-expand show">
				<!-- <div class="title">筛选</div> -->
				<div class="header-default-box weui-border-b" style="border-bottom: 1px solid #f5f5f5;">
					<a href="prod_list.jsp" class="icon icon-arrowleft l back"></a>
					<h1>筛选</h1>
				</div>
				<div class="sift-iscroll" style="padding-top:0px;">
					<dl class="deal-price weui-border-b">
						<dt>价格区间</dt>
						<dd class="clearfix">
							<span><input type="number" class="start-price" data-eventvalue="from_price" placeholder="请输入数字"/></span>
							<span><input type="number" class="end-price" data-eventvalue="to_price" placeholder="请输入数字"/></span>
						</dd>
					</dl>
					<dl class="deal-type weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">天猫</dd>
						<dd class="selected">淘宝</dd>
						<dd class="selected">特卖商城</dd>
						<dd class="selected">天猫</dd>
						<dd class="selected">淘宝</dd>
						<dd class="selected">特卖商城</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
					<dl class="deal-category weui-border-b">
						<dt class=""><span class="icon icon-74"></span>商品类型</dt>
						<dd class="selected">男士户外运动</dd>
						<dd class="selected">女士帆布鞋</dd>
						<dd class="selected">帆布鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
						<dd class="selected">低帮休闲鞋</dd>
						<dd class="selected">运动鞋</dd>
					</dl>
				</div>
				<div class="bottom-btn">
					<span><a id="btn-reset" class="reset weui-border-radius" data-eventvalue="reset">重置</a></span>
					<span><a id="btn-finish" class="finish weui-border-radius" data-eventvalue="finish">完成</a></span>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="include/foot.jsp"%>
</body>
</html>