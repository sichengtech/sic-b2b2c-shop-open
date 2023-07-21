<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>车系车型</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@include file="/views/admin/include/treetable.jsp" %>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCarList.js"></script>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">车系车型</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javascript:;"> <i class="fa fa-home"></i> 车系车型</a></li>
				<li class=""><a href="${ctxa}/product/productCar/form.do"> <i class="fa fa-user"></i> 添加车系车型</a></li>
			</ul>
		</header>
			<sys:message content="${message}"/>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>操作提示</h5>
					<ul>
						<li>点击"查看下级"按钮可以查看当前车型的下级车型。</li>
						<li>"添加下级"可以给当前车型添加下级车型。</li>
					</ul>
				</div>
				<!-- 提示 end --> 
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-5">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
							<shiro:hasPermission name="product:productCar:edit">
							<!-- 新增记录按钮 -->
							<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/product/productCar/form.do"><i class="fa fa-plus"></i></a>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productCar:import">
							<!-- 导入车系车型 -->
							<a class="btn btn-default btn-sm tooltips" title="导入" href="${ctxa}/product/productCar/importExcel.do"><i class="fa fa-download"></i></a>
							</shiro:hasPermission>
							<!-- 返回 -->
							<a class="btn btn-default btn-sm tooltips" title="" href="javaScript:void(0);" onclick="javascript:history.go(-1);" data-original-title="返回"><i class="fa fa-mail-reply"></i></a>
						</div>
					</div>
					<div class="col-sm-4"></div>
					<form action="${ctxa}/product/productCar/list.do" method="get" id="searchForm" >
						<div class="col-sm-3">
							<div class="iconic-input right">
								<a href="javaScript:;" class="searchList"><i class="fa fa-search"></i></a>
								<input type="text" class="form-control input-sm pull-right" placeholder="名称" name="name" value="${name}" style="border-radius: 30px;max-width:250px;" maxlength="64">
							</div>
						</div>
					</form>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<table id="treeTable" class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>车系ID</th>
						<th>名字</th>
						<th>上级</th>
						<th>首字母</th>
						<th>图片</th>
						<th>操作</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach var="productCar" items="${list}">
						<tr>
							<td>${productCar.carId}</td>
							<td>${productCar.name}</td>
							<td>${productCar.carParentNames}</td>
							<td>
								${productCar.firstLetter}
							</td>
							<td>
							<c:set var="parentIds" value="0,${productCar.parentId},"></c:set>
							<c:if test="${productCar.parentIds==parentIds}">
							 	<img alt="" src="${productCar.imgPath}" width="50" height="50" onerror="fdp.defaultImage('${ctxStatic}/images/productCar/product_car_default.png');">
							</c:if>
							</td>
							<td>
								<c:if test="${productCar.level!=4}">
									<a type="button" class="btn btn-default btn-sm" href="${ctxa}/product/productCar/list.do?parent.carId=${productCar.carId}">
										<i class="fa fa-view"></i> 查看下级
									</a>
								</c:if>
								<shiro:hasPermission name="product:productCar:edit">
								<a type="button" class="btn btn-info btn-sm" href="${ctxa}/product/productCar/form.do?carId=${productCar.carId}">
									<i class="fa fa-edit"></i> 编辑
								</a>
								<c:if test="${productCar.level!=4}">
									<a type="button" class="btn btn-info btn-sm" href="${ctxa}/product/productCar/form.do?parent.carId=${productCar.carId}">
										<i class="fa fa-level-down"></i> 添加下级
									</a>
								</c:if>
								</shiro:hasPermission>
								<shiro:hasPermission name="product:productCar:drop">
								<button type="button" href="${ctxa}/product/productCar/delete.do?carId=${productCar.carId}" class="btn btn-danger btn-sm deleteSure">
									<i class="fa fa-trash-o"></i> 删除
								</button>
								</shiro:hasPermission>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
				<!-- table结束 -->
			</div>
	</section>
	<!-- panel end -->
</body>
</html>