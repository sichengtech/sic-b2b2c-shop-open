<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺分类')}</title>
<%@ include file="../include/head.jsp"%>
<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
<!-- jquery-ztree js -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productStoreCategory.js"></script>
<script type="text/javascript">
	//店铺分类
	var zNodes=[<c:forEach items="${storeCategoryList}" var="cate">{id:"${cate.storeCategoryId}", pId:"${not empty cate.parent.id?cate.parent.id:0}", name:"${not empty cate.parent.id?cate.name:'店铺分类'}",sort:"${cate.sort}"},
				</c:forEach>];
	var zTree;
</script>
<style type="text/css">
	.ztree li span.button.add {margin-left: 2px;margin-right: -1px;background-position: -144px 0;vertical-align: top;}
	div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
	div#rMenu ul li{margin: 1px 0;padding: 0 5px;cursor: pointer;list-style: none outside none;background-color: #DFDFDF;}
	#categoryTree{margin-left: 10px;}
</style>
</head>
<body>
	<div class="nc-plus-album list" id="ncscPictureApp">
		<!-- 搜索框开始 -->
		<form class="sui-form">
			<div id="search" class="form-search" style="padding:10px 0 0 13px;">
				<label for="name" class="control-label" style="padding:5px 10px 3px 0;display: inline-block">${fns:fy('分类名：')}</label>
				<input type="text" class="input-fat input-medium empty" id="name" name="name" maxlength="50" style="width: 155px;">
				<button class="sui-btn btn-primary" id="search" type="button">${fns:fy('搜索')}</button>
			</div>
		</form>
		<!-- 搜索框结束 -->
		<!-- tree开始 -->
		<div class="tree-wrap" id="treeWrap"><i class="arrow-top"></i>
			<div class="mod-folder-tree">
				<ul class="ztree" id="categoryTree">
				</ul>
			</div>
		</div>
		<!-- tree结束 -->
		<button type="button" class="selectOk" style="display: none;"></button>
		<div id="rMenu">
			<ul>
				<li id="m_add" onclick="addTreeNode();">${fns:fy('增加节点')}</li>
				<li id="m_del" onclick="removeTreeNode();">${fns:fy('删除节点')}</li>
				<li id="m_edit" onclick="beforeEditName(true);">${fns:fy('编辑节点')}</li>
				<li id="m_unCheck" onclick="checkTreeNode(false);">${fns:fy('unCheck节点')}</li>
				<li id="m_reset" onclick="resetTree();">${fns:fy('恢复zTree')}</li>
			</ul>
		</div>
	</div>
</body>

</html>