<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>思程商城</title>
	<%@ include file="include/head.jsp"%>
</head>
<body class="search">
	<div class="sui-page-group" style="height: 100%;">
		<div class="sui-page sui-page-current" id='' style="height: 100%;">
			<div class="search-page show" style="position: relative;">
				<div class="content" style="padding-bottom:0px;">
					<div class="page-search-box weui-border-b">
						<span class="icon icon-arrowleft l" onclick="javascript:window.history.go(-1);"></span>
						<a class="icon icon-all1 r"></a>
						<div class="word-text weui-border-radius"><span class="icon icon-wxbsousuotuiguang"></span><input type="text" class="search-text-input" placeholder="请输入关键词"><input type="submit" class="button" value="搜索"></div>
					</div>
					<dl class="search_hotword weui-border-b clearfix">
						<dt class="">你是不是要找</dt>
						<dd><a href="#">天猫</a></dd>
						<dd><a href="#">淘宝</a></dd>
						<dd><a href="#">特卖商城</a></dd>
						<dd><a href="#">天猫</a></dd>
						<dd><a href="#">淘宝</a></dd>
						<dd><a href="#">特卖商城</a></dd>
					</dl>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="include/foot.jsp"%>
</body>
</html>