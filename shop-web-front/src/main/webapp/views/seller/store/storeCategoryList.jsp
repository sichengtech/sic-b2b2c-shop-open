<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店内商品分类')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<%@ include file="/views/seller/include/treetable.jsp"%>
<script type="text/javascript" src="${ctx}/views/seller/store/storeCategoryList.js"></script>
<script type="text/javascript">
	var jsonData = ${fns:toJson(list)};
	var storeCategoryIsOpen = ${fns:toJson(fns:getDictList('is_open'))};
	var rootId = ${not empty storeCategory.id ? storeCategory.id : 0};
</script>
<style type="text/css">
 .sui-msg.msg-block{margin-left:10px!important;margin-right:10px!important;margin-top:-10px!important;margin-bottom:-10px!important;}
</style>
</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('店内商品分类')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${fns:fy('店内商品分类')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
				 <div class="msg-con">
					 <ul>
						<h4>${fns:fy('提示信息')}</h4>
						<li>${fns:fy('查看店铺内所有的商品分类，在发布商品时选择。')}</li>
						<li>${fns:fy('点击"添加夏季分类"可以给当前商品分类添加下级分类。')}</li>
						<li>${fns:fy('点击"编辑"可以修改分类的信息。')}</li>
					 </ul>
				 </div>
				 <s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<sys:message content="${message}"/>
			<dd class="table-css">
				<div class="pull-right">
				 	<a href="${ctxs}/store/storeCategory/form.htm" class="sui-btn btn-large btn-primary m16">${fns:fy('新增店内商品分类')}</a>
				</div>
				<!-- Table开始 -->
				<table id="treeTable" class="sui-table table-bordered-simple">
				 <thead>
					 <tr colspan="2">
						<th width="30%" style="text-align:left">${fns:fy('分类名称')}</th>
						<th width="15%" class="center">${fns:fy('是否开启')}</th>
						<th width="15%" class="center">${fns:fy('排序')}</th>
						<th width="40%" class="center">${fns:fy('管理操作')}</th>
					 </tr>
				 </thead>
				 <tbody id="treeTableList"></tbody> 
				</table>
				<script type="text/template" id="treeTableTpl">
					<tr id="{{d.row.id}}" pId="{{d.pid}}">
						<td width="30%" style="text-align:left">{{d.row.name || ''}}</td> 
						<td width="15%" class="center">{{d.dict.isOpen}}</td>
						<td width="15%" class="center">{{d.row.sort || ''}}</td>
						<td width="40%" class="center">
							<shiro:hasPermission name="store:storeCategory:edit">
							<a href="${ctxs}/store/storeCategory/form.htm?storeCategoryId={{d.row.storeCategoryId}}" class="sui-btn btn-large btn-success "><i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}</a>
							<button href="${ctxs}/store/storeCategory/delete.htm?storeCategoryId={{d.row.storeCategoryId}}" class="sui-btn btn-large btn-danger deleteSure"><i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}</button>
							<a href="${ctxs}/store/storeCategory/form.htm?parent.storeCategoryId={{d.row.storeCategoryId}}" class="sui-btn btn-large btn-primary">${fns:fy('添加下级分类')}</a>
							</shiro:hasPermission>
						</td> 
					</tr>
				</script>
				<!-- table结束 -->
				</dd>
			</dl>
		</div>
	</div>
 </div>
</body>
</html>