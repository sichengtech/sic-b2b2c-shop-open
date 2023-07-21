<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺导航')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeNavigationList.js"></script>
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
					<span>${fns:fy('店铺导航')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${fns:fy('店铺导航')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
				 <div class="msg-con">
					 <ul>
						<h4>${fns:fy('提示信息')}</h4>
						<li>${fns:fy('建立店铺导航，导航内容将出现在店铺主导航上')}</li>
					 </ul>
				 </div>
				 <s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<sys:message content="${message}"/>
			<dd class="table-css">
				<div class="pull-right">
				 	<a href="${ctxs}/store/storeNavigation/save1.htm" class="sui-btn btn-large btn-primary m16">${fns:fy('新增导航')}</a>
				</div>
				<table class="sui-table table-bordered-simple">
				 <thead>
					 <tr colspan="2">
						<th width="20%" class="center">${fns:fy('导航名称')}</th>
						<th width="20%" class="center">${fns:fy('导航链接')}</th>
						<th width="10%" class="center">${fns:fy('是否显示')}</th>
						<th width="10%" class="center">${fns:fy('新窗口显示')}</th>
						<th width="10%" class="center">${fns:fy('排序')}</th>
						<th width="30%" class="center">${fns:fy('管理操作')}</th>
					 </tr>
				 </thead>
				 <tbody>
					 <!--循环开始-->
					 <c:forEach items="${page.list}" var="storeNavigation">
					 	<tr>
						 	<td width="20%"  class="center">${storeNavigation.name}</td>
						 	<td width="20%"  class="center">${storeNavigation.url}</td>
						 	<td width="10%"  class="center">${fns:getDictLabel(storeNavigation.isOpen, 'yes_no', '')}</td>
						 	<td width="10%"  class="center">${storeNavigation.target=='2'?fns:fy('是'):fns:fy('否')}</td>
						 	<td width="10%"  class="center">${storeNavigation.sort}</td>
						 	<td width="30%" class="center">
						 		<shiro:hasPermission name="store:storeNavigation:edit">
								<a href="${ctxs}/store/storeNavigation/edit1.htm?storeNavigationId=${storeNavigation.storeNavigationId}" class="sui-btn btn-large btn-success"><i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}</a>
								<button href="${ctxs}/store/storeNavigation/delete.htm?storeNavigationId=${storeNavigation.storeNavigationId}" class="sui-btn btn-large btn-danger deleteSure"><i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}</button>
								</shiro:hasPermission>
							</td>
						</tr>
					 </c:forEach>
					<!--循环结束-->
				 </tbody>
				</table>
				</dd>
				<%@ include file="/views/seller/include/page.jsp"%>
			</dl>
		</div>
	</div>
 </div>
</body>
</html>