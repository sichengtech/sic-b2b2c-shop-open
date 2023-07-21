<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('品牌产品销售申请')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productBrandList.js"></script>
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
					<span>${fns:fy('品牌产品销售申请')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('商品')}</li>
					<li>${fns:fy('商品管理')}</li>
					<li>${fns:fy('品牌产品销售申请')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
					<div class="msg-con">
						 <ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('显示了当前商家申请的所有品牌。')}</li>
							<li>${fns:fy('品牌产品销售申请需要管理的审核,管理员审核之后发布商品时才可以使用。')}</li>
						 </ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<sys:message content="${message}"/>
			<dd class="table-css">
				<div class="pull-right">
				 	<a href="${ctxs}/product/productBrand/save1.htm" class="sui-btn btn-large btn-primary m16">${fns:fy('品牌产品销售申请')}</a>
				</div>
				<table class="sui-table table-bordered-simple">
				 <thead>
					 <tr colspan="2">
						<th width="10%" class="center">${fns:fy('首字母')}</th>
						<th width="20%" class="center">${fns:fy('品牌名称')}</th>
						<th width="20%" class="center">${fns:fy('品牌LOGO')}</th>
						<th width="10%" class="center">${fns:fy('状态')}</th>
						<th width="20%" class="center">${fns:fy('管理操作')}</th>
					 </tr>
				 </thead>
				 <tbody>
					 <!--循环开始-->
					 <c:forEach items="${page.list}" var="productBrand">
					 	<tr>
						 	<td width="10%"  class="center">${productBrand.firstLetter}</td>
						 	<td width="20%"  class="center">${productBrand.name}</td>
						 	<td width="20%"  class="center">
								<c:if test="${productBrand.logo !=null}">
									 <a href="${ctxfs}${productBrand.logo}" target="_blank">
										<image src="${ctxfs}${productBrand.logo}@85x41"></image>
									 </a>
								</c:if>
								<c:if test="${productBrand.logo == null}">${fns:fy('无')}</c:if>					
						 	</td>
						 	<td width="10%"  class="center">${fns:getDictLabel(productBrand.status, 'brand_status', '')}</td>
						 	<td width="20%" class="center">
						 		<shiro:hasPermission name="product:productBrand:edit">
						 		<c:if test="${productBrand.status!='1'}">
									<a href="${ctxs}/product/productBrand/edit1.htm?brandId=${productBrand.brandId}" class="sui-btn btn-large btn-success"><i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}</a>
									<%-- <button href="${ctxs}/product/productBrand/delete.htm?brandId=${productBrand.brandId}" class="sui-btn btn-large btn-danger deleteSure"><i class="sui-icon icon-tb-delete"></i>删除</button> --%>
								</c:if>
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