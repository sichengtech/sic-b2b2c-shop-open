<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品SKU管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSkuList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">商品SKU列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 商品SKU列表</a></li>
				<shiro:hasPermission name="product:productSku:edit"><li class=""><a href="${ctxa}/product/productSku/save1.do" > <i class="fa fa-user"></i> 商品SKU添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品SKU管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="product:productSku:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/product/productSku/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/product/productSku/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="skuId"  maxlength="19" class="form-control input-sm" placeholder="SKU ID" value="${productSku.skuId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pId"  maxlength="19" class="form-control input-sm" placeholder="商品ID(SPU)" value="${productSku.PId}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>SKU ID</th>
						<th>商品ID(SPU)</th>
						<th>排序</th>
						<th>是否无规格</th>
						<th>规格1</th>
						<th>规格1值</th>
						<th>规格2</th>
						<th>规格2值</th>
						<th>规格3</th>
						<th>规格3值</th>
						<th>零售价格</th>
						<th>库存</th>
						<th>商家编号</th>
						<th>创建日期</th>
						<shiro:hasPermission name="product:productSku:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="productSku">
					<tr>
						<td><a href="${ctxa}/product/productSku/edit1.do?skuId=${productSku.skuId}">${productSku.skuId}</a></td>
						<td>${productSku.PId}</td>
						<td>${productSku.sort}</td>
						<td>${fns:getDictLabel(productSku.isNotSpec, 'yes_no', '')}</td>
						<td>${productSku.spec1}</td>
						<td>${productSku.spec1V}</td>
						<td>${productSku.spec2}</td>
						<td>${productSku.spec2V}</td>
						<td>${productSku.spec3}</td>
						<td>${productSku.spec3V}</td>
						<td>${productSku.price}</td>
						<td>${productSku.stock}</td>
						<td>${productSku.sn}</td>
						<td><fmt:formatDate value="${productSku.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="product:productSku:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/product/productSku/edit1.do?skuId=${productSku.skuId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/product/productSku/delete.do?skuId=${productSku.skuId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						</td>
						</shiro:hasPermission>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>