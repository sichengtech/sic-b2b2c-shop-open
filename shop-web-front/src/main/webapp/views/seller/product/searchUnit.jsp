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
<title>${fns:fy('发布商品--选择单位')}</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javascript" src="${ctx}/views/seller/product/searchUnit.js"></script>
</head>
<body style="background:#fff;padding:10px">

	<div class="modal-body sui-form form-horizontal">
		<form id="searchForm" class="sui-form form-search" method="post" action="${ctxs}/product/productSpu/selectUnit.htm">
		 <input type="text" name="name" value="${productUnit.name}" class="input-large search-query" placeholder="${fns:fy('搜索计量单位名称或拼音首字母')}">
		 <button type="submit" class="sui-btn btn-primary">${fns:fy('搜索')}</button>
		</form>
		<ul class="sui-tag tag-selected">
		 <li class="tag-selected" value="all">${fns:fy('全部')}</li>
		 <li class="tag-selected" value="A">A</li>
		 <li class="tag-selected" value="B">B</li>
		 <li class="tag-selected" value="C">C</li>
		 <li class="tag-selected" value="D">D</li>
		 <li class="tag-selected" value="E">E</li>
		 <li class="tag-selected" value="F">F</li>
		 <li class="tag-selected" value="G">G</li>
		 <li class="tag-selected" value="H">H</li>
		 <li class="tag-selected" value="I">I</li>
		 <li class="tag-selected" value="J">J</li>
		 <li class="tag-selected" value="K">K</li>
		 <li class="tag-selected" value="L">L</li>
		 <li class="tag-selected" value="M">M</li>
		 <li class="tag-selected" value="N">N</li>
		 <li class="tag-selected" value="O">O</li>
		 <li class="tag-selected" value="P">P</li>
		 <li class="tag-selected" value="Q">Q</li>
		 <li class="tag-selected" value="R">R</li>
		 <li class="tag-selected" value="S">S</li>
		 <li class="tag-selected" value="T">T</li>
		 <li class="tag-selected" value="U">U</li>
		 <li class="tag-selected" value="V">V</li>
		 <li class="tag-selected" value="W">W</li>
		 <li class="tag-selected" value="X">X</li>
		 <li class="tag-selected" value="Y">Y</li>
		 <li class="tag-selected" value="Z">Z</li>
		</ul>
		<table class="sui-table table-bordered-simple">
	<tbody>
		<tr>
		<c:forEach items="${page.list}" var="unit" varStatus="s">
			<td>
			<a href="javascript:void(0);" name="selectUnit" unitId="${unit.id}" unitName="${unit.name}" class="sui-btn btn-small btn-success">${fns:fy('选用')}</a>
			<span>${unit.firstLetter}-${unit.name}</span>
			</td>
		<c:if test="${(s.index+1)%5==0}">
		</tr>
		<tr>	
		</c:if>
		</c:forEach>	
		</tr>
	</tbody>
		</table>
	</div>
	<%@ include file="/views/seller/include/page.jsp"%>
			
    <%@ include file="../include/foot.jsp"%>			
</body>
</html>				