<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:choose>
	<c:when test="${recommend eq '0'}">
		<c:set var="title" value="${fns:fy('未推荐')}"></c:set>
	</c:when>
	<c:when test="${recommend eq '1'}">
		<c:set var="title" value="${fns:fy('已推荐')}"></c:set>
	</c:when>
	<c:when test="${sign eq '50'}">
		<c:set var="title" value="${fns:fy('出售中')}"></c:set>
	</c:when>
	<c:when test="${sign eq '10'}">
		<c:set var="title" value="${fns:fy('仓库中')}"></c:set>
	</c:when>
	<c:when test="${sign eq '20'}">
		<c:set var="title" value="${fns:fy('禁售')}"></c:set>
	</c:when>
	<c:when test="${sign eq '30'}">
		<c:set var="title" value="${fns:fy('待审核')}"></c:set>
	</c:when>
	<c:when test="${sign eq '40'}">
		<c:set var="title" value="审核失败"></c:set>
	</c:when>
</c:choose>
<title>${title}${fns:fy('的商品')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="2"/><!--表示使用N号二级菜单 -->
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="box">
					<dt class="box-header">
						<h4 class="pull-left">
							<i class="sui-icon icon-tb-addressbook"></i>
							<span>${title}${fns:fy('的商品')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('商品')}</li>
							<li>${fns:fy('商品管理')}</li>
							<li class="active">${fns:fy('商品列表')}</li>
						</ul>
					</dt>
					<!--主要按钮-->
					<%-- <div class="pull-right">
						<a href="javascript:void(0);" class="sui-btn btn-primary m10">填加新商品</a>
						<a href="javascript:void(0);" class="sui-btn btn-info m10">批量填加</a>
						<a href="javascript:void(0);" class="sui-btn btn-info m10">数据包导入</a>
					</div> --%>
					<!--主要按钮-->
					<!-- 提示信息开始 -->
					<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
						<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
							<div class="msg-con">
								<ul>
									<h4>${fns:fy('提示信息')}</h4>
									<li>${fns:fy('列表中显示了当前商家所有的商品。')}</li>
								</ul>
							</div>
							<s class="msg-icon" style="margin-top: 10px"></s>
						</div>
					</address>
					<!-- 提示信息结束 -->
					<!--main-toptab-->
					<div class="main-toptab">
						<c:if test="${sign eq '50'}">
							<ul class="sui-nav">
								<li class="${isRecommend == null?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=50">${fns:fy('出售中的商品')}</a></li>
								<li class="${isRecommend == '1'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=50&recommend=1">${fns:fy('已推荐的商品')}</a></li>
								<li class="${isRecommend == '0'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=50&recommend=0">${fns:fy('未推荐的商品')}</a></li>
							</ul>
						</c:if>
						<c:if test="${sign ne '50'}">
							<ul class="sui-nav">
								<li class="${sign == '10'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=10">${fns:fy('仓库中的商品')}</a></li>
								<li class="${sign == '20'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=20">${fns:fy('禁售的商品')}</a></li>
								<li class="${sign == '30'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=30">${fns:fy('等待审核的商品')}</a></li>
								<li class="${sign == '40'?'active':'' }"><a href="${ctxs}/product/productSpu/list.htm?sign=40">${fns:fy('审核失败的商品')}</a></li>
							</ul>
						</c:if>
					</div>
					<!--main-toptab-->
					<dd class="table-css">
						<!--搜索表单开始 -->
						<div class="pull-right">
							<form class="sui-form form-inline m10" action="${ctxs}/product/productSpu/list.htm" method="post">
								<input type="hidden" name="sign" value="${sign}"/>
								<input type="hidden" name="isRecommend" value="${isRecommend}"/>
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input value="${searchCate}" name="searchCate" type="hidden"><i class="caret"></i>
											<span>
												<c:choose>
													<c:when test="${empty searchCate || searchCate == '1'}">${fns:fy('商品名称')}</c:when>
													<c:when test="${searchCate == '2'}">${fns:fy('商品ID')}</c:when>
												</c:choose>
											</span>
										</a>
										<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
											<li class="${empty searchCate || searchCate == '1' ?'active':'' }" role="presentation" ><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="1">${fns:fy('商品名称')}</a></li>
											<li class="${searchCate == '2' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="2">${fns:fy('商品ID')}</a></li>
										</ul>
									</span>
								</span>
								<input type="text" placeholder="" class="input-medium" name="searchValue" value="${searchValue}" maxLength="64" style="width:135px;">
								<button class="sui-btn btn-primary" type="submit">${fns:fy('搜索')}</button>
							</form>
						</div>
						<sys:message content="${message}"/>
						<!--搜索表单结束 -->
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr colspan="8">
									<th width="1%" class="center"></th>
									<th width="10%" class="center">${fns:fy('缩略图')}</th>
									<th width="28%" class="center">${fns:fy('商品名称')}</th>
									<th width="9%" class="center">${fns:fy('规格/规格值')}</th>
									<th width="7%" class="center">${fns:fy('库存')}</th>
									<th width="15%" class="center">${fns:fy('价格')}</th>
									<th width="7%" class="center">${fns:fy('月销')}</th>
									<th width="10%" class="center">${fns:fy('上架时间')}</th>
									<th width="12%" class="center">${fns:fy('操作')}</th>
								</tr>
							</thead>
							<tbody>
								<!--全选 工具条-->
								<c:if test="${(sign eq '50' || sign eq '10') && fn:length(page.list)>'0'}">
									<tr>
										<td width="1%" class="center">
											<input type="checkbox" class="productCheckAll">
										</td>
										<td width="10%" class="" colspan="30">
											<div class="sui-btn-group">
												<h5>${fns:fy('全选')}&nbsp;&nbsp;&nbsp;&nbsp;</h5>
												<c:if test="${sign eq '50'}">
													<button class="sui-btn editShelf" href="${ctxs}/product/productSpu/offShelf.htm?sign=${sign}&recommend=${recommend}" shelfType="2" id="off_shelf">${fns:fy('商品下架')}</button>
													<button class="sui-btn recommend" href="${ctxs}/product/productSpu/recommend.htm?sign=${sign}&recommend=${recommend}" recommendType="1" id="recommend">${fns:fy('橱窗推荐')}</button>
													<button class="sui-btn recommend" href="${ctxs}/product/productSpu/recommend.htm?sign=${sign}&recommend=${recommend}" recommendType="2" id="calcel_recommend">${fns:fy('取消推荐')}</button>
												</c:if>
												<c:if test="${sign eq '10'}">
													<button class="sui-btn editShelf" href="${ctxs}/product/productSpu/offShelf.htm?sign=${sign}&recommend=${recommend}" shelfType="1" id="up_shelf">${fns:fy('商品上架')}</button>
												</c:if>
											 </div>
										</td>
									</tr>
								</c:if>
								<!--全选 工具条-->

								<!--循环开始-->
								<c:forEach items="${page.list}" var="product">
									<tr>
										<td class="center">
											<input type="checkbox" class="productCheck" value="${product.PId}">
											<c:if test="${product.isRecommend == '1'}">
												<span class="sui-label label-success" style="padding: 2px 2px;">${fns:fy('已推')}</span>
											</c:if>
										</td>
										<td class="center">
											<img src="${ctxfs}${product.image}@!80x80" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');" alt=""/>
										</td>
										<td class="">
											<a target="_blank" href="${ctxf}/detail/${product.PId}.htm">
												<div class="productName">${product.name}</div>
												<div>${fns:fy('商品SPU：')}${product.PId} 
													<c:choose>
														<c:when test="${product.type == '1'}">
															<i class="sui-icon icon-tb-tagfill lingshou" >${fns:fy('零售')}</i>
														</c:when>
														<c:when test="${product.type == '2'}">
															<i class="sui-icon icon-tb-tag pifa">${fns:fy('批发')}</i>
														</c:when>
														<c:when test="${product.type == '3'}">
															<i class="sui-icon icon-tb-tag pifa">${fns:fy('混合')}</i>
														</c:when>
													</c:choose>
												</div>
											</a>
										</td>
										<td class="center">
											<div class="product-spec" style="height: auto;max-height: 55px; overflow-y: hidden;width: 200px;">
												<c:forEach items="${product.productSkuList}" var="sku" varStatus="skuIndex">
													<c:if test="${skuIndex.first}">
														<c:if test="${not empty sku.spec1}">
															<p>${fn:split(sku.spec1, '_')[1]} : <span>${sku.spec1V}</span></p>
														</c:if>
														<c:if test="${not empty sku.spec2}">
															<p>${fn:split(sku.spec2, '_')[1]} : <span>${sku.spec2V}</span></p>
														</c:if>
														<c:if test="${not empty sku.spec3}">
															<p>${fn:split(sku.spec3, '_')[1]} : <span>${sku.spec3V}</span></p>
														</c:if>
													 </c:if>
												</c:forEach>
											</div> 
											<div>
												<a href="javascript:void(0);" productId="${product.PId}" productName="${product.name}" 
													class="sui-btn showSpec btn-info"><i class="sui-icon icon-pencil"></i>${fns:fy('查看全部规格')}</a>
											</div>
										</td>
										<td class="center">
											<c:set var="stock" value="0"></c:set>
											<c:forEach items="${product.productSkuList}" var="sku" varStatus="skuIndex">
												<c:set var="stock" value="${sku.stock + stock}"></c:set>
											</c:forEach>
											${stock}
										</td>
										<td class="center">
											<c:choose>
												<c:when test="${product.type == '1'}">
													<c:choose>
														<c:when test="${product.productSkuList[0].price eq product.productSkuList[fn:length(product.productSkuList)-1].price }">
															${product.productSkuList[0].price}${fns:fy('元')}
														</c:when>
														<c:otherwise>
															${product.productSkuList[0].price}-${product.productSkuList[fn:length(product.productSkuList)-1].price}${fns:fy('元')}
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:forEach items="${product.productSectionPriceList}" var="section" varStatus="index">
														 <c:choose>
														 	<c:when test="${!index.last}">
														 		<div>${section.section} - ${product.productSectionPriceList[index.index+1].section} ${product.unit}&nbsp; ${section.price}${fns:fy('元')}</div>
														 	</c:when>
														 	<c:otherwise>
														 		≥${section.section} ${product.unit}&nbsp; ${section.price}${fns:fy('元')}
														 	</c:otherwise>
														 </c:choose>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="center">${empty product.productSpuAnalyze.monthSales?'0':product.productSpuAnalyze.monthSales}</td>
										<td class="center"><fmt:formatDate value="${product.shelfTime}" pattern="yyyy-MM-dd"/> </td>
										<td class="center">
											<div style="display: ${sign eq '20' && productConfig.set2 eq '1'?'none':''}"><a href="${ctxs}/product/productSpu/edit1.htm?pId=${product.PId}&sign=${sign}&recommend=${recommend}" class="sui-btn btn-small btn-primary" style="margin-bottom:2px;"><i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}</a></div>
											<div><a href="${ctxs}/product/productSpu/edit1.htm?pId=${product.PId}&sign=${sign}&recommend=${recommend}&isCopy=1" class="sui-btn btn-small mt5"><i class="sui-icon icon-pc-copy"></i>${fns:fy('复制')}</a></div>
											<div><button href="${ctxs}/product/productSpu/delete.htm?pId=${product.PId}&sign=${sign}&recommend=${recommend}" class="sui-btn btn-small btn-bordered btn-danger mt5 deleteSure"><i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}</botton></div>
										</td>
									</tr>
								</c:forEach>
								<!--循环结束-->
								<!-- 没有数据提示开始 -->
								<c:if test="${fn:length(page.list)=='0'}">
									<tr>
										<td colspan="9" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;font-size:24px;">
											<i class="sui-icon icon-tb-goods"></i> ${fns:fy('很遗憾，暂无商品！')}
										</td>
									</tr>
								</c:if>
								<!-- 没有数据提示结束 -->
								<!-- 底部操作按钮开始 -->
								<c:if test="${fn:length(page.list)>'0'}">
									<tr>
										<td width="1%" class="center">
											<input type="checkbox" class="productCheckAll">
										</td>
										<td width="10%" class="" colspan="30">
											<div class="sui-btn-group">
												<h5>${fns:fy('全选')}</h5>
												<c:if test="${sign eq '50'}">
													<button class="sui-btn editShelf" href="${ctxs}/product/productSpu/offShelf.htm?sign=${sign}&recommend=${recommend}" shelfType="2" id="off_shelf">${fns:fy('商品下架')}</button>
													<button class="sui-btn recommend" href="${ctxs}/product/productSpu/recommend.htm?sign=${sign}&recommend=${recommend}" recommendType="1" id="recommend">${fns:fy('橱窗推荐')}</button>
													<button class="sui-btn recommend" href="${ctxs}/product/productSpu/recommend.htm?sign=${sign}&recommend=${recommend}" recommendType="2" id="calcel_recommend">${fns:fy('取消推荐')}</button>
												</c:if>
												<c:if test="${sign ne '50'}">
													<button class="sui-btn editShelf" href="${ctxs}/product/productSpu/offShelf.htm?sign=${sign}&recommend=${recommend}" shelfType="1" id="up_shelf">${fns:fy('商品上架')}</button>
												</c:if>
											 </div>
										</td>
									</tr>
								</c:if>
								<!-- 底部操作按钮结束 -->
							</tbody>
						</table>
						<c:if test="${fn:length(page.list)>'0'}">
							<%@ include file="/views/seller/include/page.jsp"%>
						</c:if>
					</dd>
				</dl>
			</div>
		</div>
	</div>
	<script src="${ctx}/views/seller/product/productList.js" type="text/javascript"></script>
</body>
</html>