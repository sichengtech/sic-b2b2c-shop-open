<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品分类管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
 
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 业务js-->
<%@include file="/views/admin/include/treetable.jsp" %>
<script type="text/javascript" src="${ctx}/views/admin/product/productCategoryList.js"></script>
<style type="text/css">
	#attrTable th{background: #ffffff;}
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('商品分类管理')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-home"></i> ${fns:fy('分类列表')}</a></li>
				<shiro:hasPermission name="product:productCategory:edit">
				<li class=""><a href="${ctxa}/product/productCategory/form.do" > <i class="fa fa-user"></i> ${fns:fy('分类添加')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>${fns:fy('操作提示')}</h5>
					<ul>
						<li>${fns:fy('商品管理.商品分类管理.操作提示1')}</li>
						<li>${fns:fy('商品管理.商品分类管理.操作提示2')}</li>
						<li>${fns:fy('商品管理.商品分类管理.操作提示3')}</li>
					</ul>
				</div>
				<!-- 提示 end -->
				<sys:message content="${message}"/>
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-5">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
							<shiro:hasPermission name="product:productCategory:edit">
							<!-- 新增记录按钮 -->
							<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/product/productCategory/form.do"><i class="fa fa-plus"></i></a>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productCategory:export">
							<!-- 导入商品分类 -->
							<a class="btn btn-default btn-sm tooltips" title="${fns:fy('导入')}" href="${ctxa}/product/productCategory/importExcel.do"><i class="fa fa-download"></i></a>
							</shiro:hasPermission>
							<!-- 返回 -->
							<a class="btn btn-default btn-sm tooltips" title="${fns:fy('返回')}" href="javaScript:void(0);" onclick="javascript:history.go(-1);" data-original-title="${fns:fy('返回')}"><i class="fa fa-mail-reply"></i></a>
						</div>
					</div>
					<div class="col-sm-4"> </div>
					<form action="${ctxa}/product/productCategory/list.do" method="get" id="searchForm">
						<div class="col-sm-3">
							<div class="iconic-input right">
								<a href="javaScript:;" class="searchList"><i class="fa fa-search"></i></a>
								<input type="text" name="name" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入类别名称进行搜索')}" value="${name}" maxlength="30" style="border-radius: 30px;max-width:250px;">
							</div>
						</div>
					</form>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<table id="treeTable" class="table table-hover table-condensed table-bordered ">
					<thead> 
						<tr>
							<th>${fns:fy('分类')}ID</th>
							<th>${fns:fy('名称')}</th>
							<th>${fns:fy('上级')}</th>
							<th>${fns:fy('首字母')}</th>
							<th>${fns:fy('佣金比例')}</th>
							<th>${fns:fy('参数名')}</th>
							<th>${fns:fy('排序')}</th>
							<th>${fns:fy('是否锁定')}</th>
							<th>${fns:fy('操作')}</th>
						</tr>
					</thead> 
					<tbody>
						<c:forEach var="productCategory" items="${list}">
							<tr>
								<td>${productCategory.categoryId}</td>
								<td>${productCategory.name}</td>
								<td>${productCategory.productParentNames}</td>
								<td>${productCategory.firstLetter}</td>
								<td>${productCategory.commission}%</td>
								<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${productCategory.paramValues}</textarea></td>
								<td>${productCategory.sort}</td>
								<td>${fns:getDictLabel(productCategory.isLocked, 'is_locked', '')}</td>
								<td>
									<c:if test="${productCar.level!=3}">
										<a type="button" class="btn btn-default btn-sm" href="${ctxa}/product/productCategory/list.do?parent.categoryId=${productCategory.categoryId}">
											<i class="fa fa-view"></i> ${fns:fy('查看下级')}
										</a>
									</c:if>
									<shiro:hasPermission name="product:productCategory:edit">
									<div class="btn-group">
										<a href="javascript:;" data-toggle="dropdown" aria-expanded="false" class="btn btn-info btn-sm dropdown-toggle">
											<i class="fa fa-gears"></i> ${fns:fy('操作')}
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu dropdown-menu-left" style="min-width: 125px;">
											<li>
												<a href="${ctxa}/product/productCategory/form.do?categoryId=${productCategory.categoryId}">
													<i class="fa fa-edit"></i> ${fns:fy('编辑')}
												</a>
											</li>
											<c:if test="${productCar.level!=3}">
											<li>
												<a href="${ctxa}/product/productCategory/form.do?parent.categoryId=${productCategory.categoryId}">
													<i class="fa fa-level-down"></i> ${fns:fy('添加下级')}
												</a>
											</li>
											</c:if>
											<li>
												<a href="javaScript:void(0);" class="commissionModal" categoryId="${productCategory.categoryId}" categoryName="${productCategory.name}" commission="${productCategory.commission}">
													<i class="fa fa-plus-square" ></i> ${fns:fy('绑定佣金')}
												</a>
											</li>
											<li>
												<a href="${ctxa}/product/productCategory/catelist.do?categoryId=${productCategory.categoryId}">
													<i class="fa fa-plus-square" ></i> ${fns:fy('绑定参数')}
												</a>
											</li>
											<li>
												<a href="javaScript:void(0);" class="bindBrand" categoryId="${productCategory.categoryId}">
													<i class="fa fa-plus-square" ></i> ${fns:fy('绑定品牌')}
												</a>
											</li>
											<li>
												<a href="javaScript:void(0);" class="bindRecommend" categoryId="${productCategory.categoryId}">
													<i class="fa fa-plus-square" ></i> ${fns:fy('绑定推荐位')}
												</a>
											</li>
										</ul>
									</div>
									</shiro:hasPermission>
									<shiro:hasPermission name="product:productCategory:drop">
									<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/product/productCategory/delete.do?categoryId=${productCategory.categoryId}">
										<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
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
	<shiro:hasPermission name="product:productCategory:edit">
	<!-- 开始分类佣金模态窗口 -->
	<div class="modal fade" id="commissionModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">
			<form id="commissionForm" action="${ctxa}/product/productCategory/commission.do" method="get"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
						 <li class="m-b-3"> ${fns:fy('给此分类进行设置佣金比例，可以同步到子分类')}</li>
						</ul>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label text-right">${fns:fy('分类名称')}：</label>
						<div class="col-sm-8" id="cateName" name="cateName">${fns:fy('分类名称')}</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-4 control-label text-right">${fns:fy('佣金比例')}：</label>
						<div class="col-sm-3">
							<div class="input-group">
								<input type="hidden" name="categoryId" value=""/>
								<input type="text" id="commission" name="commission" class="form-control input-sm" placeholder=""/>
								<div class="input-group-addon">%</div>
							</div>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-4 control-label text-right">${fns:fy('是否同步到子分类下')}：</label>
						<div class="col-sm-8">
							<input type="checkbox" name="synchro" value="1">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
						<i class="fa fa-times"></i> ${fns:fy('关闭')}
					</button>
					<button type="submit" class="btn btn-info">
						<i class="fa fa-search"></i> ${fns:fy('保存')}
					</button> 
				</div>
			</form>
		</div>
	</div>
	<!-- 结束分类佣金模态窗口 -->
	</shiro:hasPermission>
</body>
</html>