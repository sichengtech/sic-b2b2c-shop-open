<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>规格添加</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

</head>
<body>
<div class="main-content">
	<div class="">
	<div class="goods-list">
		<dl class="box goods-add">
			<dt class="box-header"><h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>我的店铺</span></h4><ul class="sui-breadcrumb"><li>当前位置:</li><li><a href="#">首页</a></li><li><a href="#">手机数码、电脑办公</a></li><li class="active">智能手机</li></ul></dt>
			<form class="sui-form form-inline" method="post">
			<dd class="bs-docs-example">
				<h3>商品基本信息</h3>
				<div class="control-group"><label class="control-label">商品分类：</label><ul class="sui-breadcrumb" style="display:inline"><li>母婴用品</li><li>喂养用品</li><li>暖乃消毒</li></ul><a href="javascript:void(0);" class="sui-btn btn-small btn-primary">选择分类</a></div>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-xxlarge">
											<div class="sui-msg msg-naked msg-info"><i class="msg-icon"></i><div class="msg-con">商品标题名称长度至少3个字符，最长50个汉字</div></div>
				</div>
				<div class="control-group"><label class="control-label">卖点描述：</label><textarea style="height: 50px;" class="input-xxlarge"></textarea>
											<div class="sui-msg msg-naked msg-info"><i class="msg-icon"></i><div class="msg-con">商品卖点可填写商品的简介或特点，最长不能超过140个汉字</div></div>
				</div>
				<div class="input-prepend input-append control-group"><label class="control-label">商品名称：</label><span class="add-on">$</span><input id="appendedPrependedInput" type="text" class="span2 input-fat"><span class="add-on">.00</span></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>商品交易信息</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>商品规格及图片</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>商品详情描述</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>商品物流信息</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>发票信息</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			<dd class="bs-docs-example">
				<h3>其他信息</h3>
				<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
			</dd>
			</form>

			<div class="clear"></div>
			<div class="text-align pb20">
				<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary">保存</a>
				<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary ml20">保存 并 增加新产品</a>
			</div>
		</dl>
		</div>
	</div>
	</div>
</body>
</html>