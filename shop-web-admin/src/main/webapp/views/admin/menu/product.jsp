<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
					<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-laptop"></i> <span>商品模块</span><small>商品</small></a>
						<ul class="sub-menu-list">
							<li><a href="${ctx}/views/admin/product/productList.jsp"><i class="fa fa-user"></i> <span>在售商品</span></a></li>
							<li><a href="${ctx}/views/admin/product/productList2.jsp"><i class="fa fa-user"></i> <span>禁售商品</span></a></li>
							<li><a href="${ctx}/views/admin/product/productList3.jsp"><i class="fa fa-user"></i> <span>待审商品</span></a></li>
							<li><a href="${ctx}/views/admin/product/productSet.jsp"><i class="fa fa-user"></i> <span>商品设置</span></a></li>
							<li><a href="${ctx}/views/admin/product/productCategoryList.jsp"><i class="fa fa-user"></i> <span>分类管理</span></a></li>
							<li><a href="${ctx}/views/admin/product/productSpecList.jsp"><i class="fa fa-user"></i> <span>规格管理</span></a></li>
							<li><a href="${ctx}/views/admin/product/productBrandList.jsp"><i class="fa fa-user"></i> <span>品牌管理</span></a></li>
							<li><a href="${ctx}/views/admin/product/productBrandAudList.jsp"><i class="fa fa-user"></i> <span>品牌审核</span></a></li>
							<li><a href="${ctx}/views/admin/product/productBrandTagList.jsp"><i class="fa fa-user"></i> <span>品牌标签（不做）</span></a></li>

						</ul></li>