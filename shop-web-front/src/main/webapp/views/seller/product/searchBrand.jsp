<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="author" content="SiC B2B2C Shop"/>
<meta name="renderer" content="webkit">
<title>${fns:fy('发布商品--选择品牌')}</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javascript" src="${ctx}/views/seller/product/searchBrand.js"></script>
<script type="text/javascript">
	var firstLetter='${firstLetter}';
	var firstLetterMap=${firstLetterMap};
</script>
<style type="text/css">
	.modal-body-brand .brandsel-list li{width: 172px;}
	.zm li{cursor:pointer;}
	.no_brand{height: 200px;text-align: center;line-height: 200px;}
	.modal-body-brand .brandsel-list{display: block;}
	.modal-body-brand ul.zm{display: block;height: 35px;}
</style>
</head>
<body style="background:#fff;padding:0px">
	<div class="modal-body-brand">
		<form id="searchForm" class="search" method="post" action="${ctxs}/product/productSpu/selectBrand.htm">
			<input type="text" name="name" value="${productBrand.name}" class="text name" placeholder="${fns:fy('搜索品牌名称或拼音首字母')}">
			<input type="hidden" id="storeId" name="storeId" value="${storeId}"/>
			<button type="submit" class="sui-btn btn-primary searchBtn">${fns:fy('搜索')}</button>
		</form>
		<ul class="zm">
			<li class="all" value="all">${fns:fy('全部')}</li>
			<li value="A">A</li>
			<li value="B">B</li>
			<li value="C">C</li>
			<li value="D">D</li>
			<li value="E">E</li>
			<li value="F">F</li>
			<li value="G">G</li>
			<li value="H">H</li>
			<li value="I">I</li>
			<li value="J">J</li>
			<li value="K">K</li>
			<li value="L">L</li>
			<li value="M">M</li>
			<li value="N">N</li>
			<li value="O">O</li>
			<li value="P">P</li>
			<li value="Q">Q</li>
			<li value="R">R</li>
			<li value="S">S</li>
			<li value="T">T</li>
			<li value="U">U</li>
			<li value="V">V</li>
			<li value="W">W</li>
			<li value="X">X</li>
			<li value="Y">Y</li>
			<li value="Z">Z</li>
		</ul>
		<div class="clear"></div>
		<ul class="brandsel-list">
			<c:forEach items="${page.list}" var="brand" varStatus="s">
				<li>
					<a href="javascript:void(0);" name="selectBrand" brandId="${brand.id}" brandName="${brand.name}">
					<b>${fns:fy('选择')}</b><span>${brand.firstLetter}-${brand.name}</span></a>
				</li>
			</c:forEach>
			<c:if test="${fn:length(page.list)==0}">
				<div class="no_brand">${fns:fy('很遗憾，暂无品牌！')}</div>
			</c:if>
		</ul>
	</div>
	<div class="clear"></div>
	<c:if test="${fn:length(page.list)>0}">
		<%@ include file="/views/seller/include/page.jsp"%>
	</c:if>
    <%@ include file="../include/foot.jsp"%>
    <script type="text/template" id="brandTpl">
		<li>
			<a href="javascript:void(0);" name="selectBrand" brandId="{{d.brand.id}}" brandName="{{d.brand.name}}">
			<b>${fns:fy('选择')}</b><span>{{d.brand.firstLetter}}-{{d.brand.name}}</span></a>
		</li>
	</script>
</body>
</html>			