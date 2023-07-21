<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品分类导入')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCategoryImport.js"></script>
<style type="text/css">
    .block{width: 43%;height: 188px;border: solid 5px #F5F5F5;margin: 20px;display: block;}
    .block:hover{border: solid 5px #DD2726;}
    .left{margin-left: 130px;}
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productCategory.categoryId?true:false}"></c:set > 
			<h4 class="title">${fns:fy('商品分类导入')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		 	<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productCategory/list.do"> <i class="fa fa-home"></i> ${fns:fy('分类列表')}</a></li>
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('分类导入')}</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('商品分类.分类导入.操作提示1')}</li>
					<li>${fns:fy('商品分类.分类导入.操作提示2')}</li>
					<li>${fns:fy('商品分类.分类导入.操作提示3')}</li>
				</ul>
			</div>
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post" enctype="multipart/form-data" action="${ctxa}/product/productCategory/saveExcel.do">
						<div class="form-group">
							<shiro:hasPermission name="product:productCategory:import">
							<a href="${ctxStatic}/excel/${fns:fy('商品的分类初始导入模板')}.xls" class="col-sm-6 block" style="margin-left: 70px;text-decoration: none;">
								<p style="font-size: 25px; text-align: center; margin-top: 77px;">${fns:fy('下载商品分类EXCEL模板')}</p>
							</a>
							</shiro:hasPermission>
							<a class="col-sm-6 block" style="text-decoration: none;" href="javascript:;">
								<p style="font-size: 25px; text-align: center; margin-top: 30px;">${fns:fy('上传商品分类EXCEL')}</p>
								<input id="excel" name="excel" type="file" accept=".xls,.xlsx"  class="left"/>
								<div class="left">
									<label>
	    								<input type="radio" name="ststus" value="1" checked>
	    								<span>${fns:fy('追加导入')}</span>
	  								</label>
									<label>
	    								<input type="radio" name="ststus" value="2">
	    								<span>${fns:fy('清空商品分类,再导入')}</span>
	  								</label>
  								</div>
  								<div style="margin-left: 182px;">
									<shiro:hasPermission name="product:productCategory:export">
									<button type="submit" class="btn btn-info">
										<i class="fa fa-check"></i> ${fns:fy('导入')}
									</button>
									</shiro:hasPermission>
								</div>
								<p style="text-align: center;">${fns:fy('成功导入')}：${empty success ?'0':success}${fns:fy('条')}，${fns:fy('失败')}：${empty fail ?'0':fail}${fns:fy('条')}</p>
							</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>