<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCategoryAndRecommend.js"></script>
<style type="text/css">
	.prompt{margin-top: 10px;color: cadetblue;}
	.prompt li{font-size: 13px;}
	.brandDiv{margin: 10px 10px 10px 10px;}
	.brandDiv .firstLetter{width: 21px;padding: 0px;}
	.selectedClass{border: solid 1px #353f4f;height: 80px;overflow-y: auto;}
	.selectedClass div{display: inline;}
	.selectedClass button{margin: 2px 2px 2px 2px;border-radius: 30px;}
	.unselectedClass{height: 135px;overflow-y: auto;}
	.unselectedClass div{display: inline;}
	.unselectedClass button{margin: 2px 2px 2px 2px;border-radius: 30px;}
	.searchBrand{border-radius: 30px;max-width:180px;}
	.no{height: 135px;padding-left: 320px;padding-top: 48px;}
</style>
</head>
<body style="background: #f5f5f5;">
	<!-- 开始店铺推荐位模态窗口 -->
	<div id="brand">
		<ul class="prompt">
			<li>${fns:fy('商品分类.绑定推荐位.操作提示1')}</li>
			<li>${fns:fy('商品分类.绑定推荐位.操作提示2')}</li>
			<li>${fns:fy('商品分类.绑定推荐位.操作提示3')}</li>
		</ul>
		<div class="brandDiv">
			<span>${fns:fy('已选推荐位')}:</span>
			<div class ="selectedClass">
				<c:forEach var="productBrand" items="${bindList}">
					<div>
						<input type="hidden" value="${productBrand.recommendId}" class="brandId">
						<button class="btn btn-info btn-xs selected_btn">
							<i class="fa fa-times-circle"></i>&nbsp;<span>${productBrand.name}</span>
						</button>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="brandDiv">
			<div class="iconic-input">
				<i class="fa fa-search"></i><input type="text" class="form-control input-sm searchBrand" placeholder="${fns:fy('快速搜索推荐位')}">
			</div>
		</div>
		<div class="brandDiv" style="${unselectedProductBrand.size()==0?'display:none':'display:block'}">
			<div class ="unselectedClass">
				<c:forEach var="siteRecommend" items="${siteRecommends}">
					<div class="class_div">
						<input type="hidden" value="${siteRecommend.recommendId}" class="brandId">
						<button class="btn btn-default btn-xs unselected_btn">
							<i class="fa fa-check-square"></i>&nbsp;<span>${siteRecommend.name}</span>
						</button>
					</div>
				</c:forEach>
			</div>
		</div>

		<div class="brandDiv" style="${siteRecommends.size()==0?'display:block':'display:none'}">
			<div class ="no">${fns:fy('当前无结果!')}</div>
		</div>
	</div>
	<!-- 结束店铺推荐位佣金模态窗口 -->
	<%-- 引入脚部文件 --%>
	<%@ include file="../include/foot.jsp"%>
</body>
</html>