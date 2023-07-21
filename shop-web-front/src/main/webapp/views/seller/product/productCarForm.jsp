<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content=""/>
<title>${fns:fy('发布商品--选择车系')}</title>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<%-- jTree --%>
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productCarForm.js"></script>
<%-- jTree --%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/> 
<script type="text/javascript">
	//2级品牌
	var carTwoNodes = ${productCarTwoList};
</script>
<style type="text/css">
.ztree{height: 247px;overflow-y: auto;}
.brandClass{color:#000 !important; background:url(${ctxStatic}/sicheng-seller/images/carlogocur.gif) right bottom no-repeat; border:#e4393c solid 1px !important;}
.productCarTwo{overflow: hidden;}
</style>
</head>
<body style="background:javaScript:void(0);fff;padding:0px; margin:0; overflow:hidden" id="productCar">
	<div class="car-modal-body">
		<dl class="selected-l">
			<dt class="h1">${fns:fy('筛选车型')}</dt>
			<dt class="zm clearfix">
				<a href="javaScript:void(0);" class="cur firstLetter">A</a>
				<a href="javaScript:void(0);" class="firstLetter">B</a>
				<a href="javaScript:void(0);" class="firstLetter">C</a>
				<a href="javaScript:void(0);" class="firstLetter">D</a>
				<a href="javaScript:void(0);" class="firstLetter">E</a>
				<a href="javaScript:void(0);" class="firstLetter">F</a>
				<a href="javaScript:void(0);" class="firstLetter">G</a>
				<a href="javaScript:void(0);" class="firstLetter">H</a>
				<a href="javaScript:void(0);" class="firstLetter">I</a>
				<a href="javaScript:void(0);" class="firstLetter">J</a>
				<a href="javaScript:void(0);" class="firstLetter">K</a>
				<a href="javaScript:void(0);" class="firstLetter">L</a>
				<a href="javaScript:void(0);" class="firstLetter">M</a>
				<a href="javaScript:void(0);" class="firstLetter">N</a>
				<a href="javaScript:void(0);" class="firstLetter">O</a>
				<a href="javaScript:void(0);" class="firstLetter">P</a>
				<a href="javaScript:void(0);" class="firstLetter">Q</a>
				<a href="javaScript:void(0);" class="firstLetter">R</a>
				<a href="javaScript:void(0);" class="firstLetter">S</a>
				<a href="javaScript:void(0);" class="firstLetter">T</a>
				<a href="javaScript:void(0);" class="firstLetter">U</a>
				<a href="javaScript:void(0);" class="firstLetter">V</a>
				<a href="javaScript:void(0);" class="firstLetter">W</a>
				<a href="javaScript:void(0);" class="firstLetter">X</a>
				<a href="javaScript:void(0);" class="firstLetter">Y</a>
				<a href="javaScript:void(0);" class="firstLetter">Z</a>
			</dt>
			<dd class="con">
				<ul class="clearfix carsel-list"></ul>
			</dd>
		</dl>
		<dl class="selected-c">
			<dt class="h1">${fns:fy('选择车型')}</dt>
			<dt class="search-box"><i class="sui-icon icon-tb-search"></i>
				<input type="text" placeholder="${fns:fy('关键词搜索')}" class="input-search" id="key">
			</dt>
			<ul id="carNameTree" class="ztree"></ul>
		</dl>
		<dl class="selected-r">
			<dt class="h1">${fns:fy('已选车型')}</dt>
			<dd class="con">
				<ul id="con_choose"></ul>
			</dd>
			<dd class="ok"><a href="javaScript:void(0);">${fns:fy('选好了')}</a></dd>
		</dl>
	</div>
	<script type="text/template" id="productCar_tpl1" info="${fns:fy('筛选车型')}">
		<li>
			<a class="productCarTwo" href="javaScript:void(0);" carId="{{d.carId}}" title="{{d.name}}">
				<img src="{{d.imgPath}}" onerror="fdp.defaultImage('${ctxStatic}/images/productCar/product_car_default.png');"/>
				<span> {{d.name}}</span>
			</a>
		</li>
	</script>
	<script type="text/template" id="productCar_tpl2" info="${fns:fy('已选车型')}">
		<li>
			<input class="carIds" type="hidden" value="{{d.carIds}}" name="carIds">
			<a href="javaScript:void(0);" title="{{d.name}}"><i class="sui-icon icon-tb-close removeCar"></i>{{d.name}}</a>
		</li>
	</script>
	<%-- 引入脚部文件 --%>
	<%@ include file="../include/foot.jsp"%>
</body>
</html>			