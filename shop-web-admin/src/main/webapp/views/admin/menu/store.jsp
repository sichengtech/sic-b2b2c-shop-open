<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
					<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-home"></i> <span>店铺管理</span><small>店铺</small></a>
						<ul class="sub-menu-list">
							<li><a href="${ctx}/views/admin/store/storeList.jsp"><i class="fa fa-user"></i> <span>店铺管理</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeSellerList.jsp"><i class="fa fa-user"></i> <span>商家管理</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeEnterList.jsp"><i class="fa fa-user"></i> <span>入驻申请</span></a></li>
							<li><a href="#"><i class="fa fa-user"></i> <span>续签申请(暂定)</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeLevelList.jsp"><i class="fa fa-user"></i> <span>店铺等级</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeIndustryList.jsp"><i class="fa fa-user"></i> <span>主营行业</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeAlbumSpaceList.jsp"><i class="fa fa-user"></i> <span>商家相册</span></a></li>
							<li><a href="${ctx}/views/admin/store/storeDomainList.jsp"><i class="fa fa-user"></i> <span>二级域名</span></a></li>
						</ul></li>	