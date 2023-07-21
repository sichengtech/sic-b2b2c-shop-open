<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品收藏')}</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/collect/memberCollectionProduct.js"></script>
<style type="text/css">
	.main-content .main-center .tab-pane li .sui-btn{margin-top: 0px;margin-left: 5px;}
</style>
</head>
<body>
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('收藏中心')} > ${fns:fy('商品收藏')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('商品收藏')}
			</dt>
			<ul class="sui-nav nav-tabs nav-large">
				<li class="active"><a href="#all" data-toggle="tab">${fns:fy('全部收藏商品')} (${count})</a></li>
			</ul>
			<sys:message content="${message}"/>
			<dd class="tab-content">
				<div id="all" class="my-favor-goods tab-pane active table-css">
					<ul class="s-item-list">
						<c:forEach items="${page.list}" var="memberCollectionProduct">
							<c:if test="${memberCollectionProduct.status=='0'}">
								<li class="s-invalid">
									<div class="s-words">${fns:fy('已下架')}</div>
									<div class="s-pic">
										<a href="${ctx}/detail/${memberCollectionProduct.PId}.htm" target="_blank" >
											<c:choose>
												<c:when test="${not empty memberCollectionProduct.image}">
													<img style="width: 185px;height: 185px;" src="${ctxfs}${memberCollectionProduct.image}@!185x185">
												</c:when>
												<c:otherwise>
													<img src="https://asearch.alicdn.com/bao/uploaded/i4/148570305435535450/TB2UpSaavSM.eBjSZFNXXbgYpXa_!!0-saturn_solar.jpg_220x220.jpg" alt="${fns:fy('手绘板')}" title="${fns:fy('影拓手绘板')}">
												</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div class="s-price-box">
										<span class="s-price">
											<em class="s-price-sign">${fns:fy('￥')}</em>
											<em class="s-value">${memberCollectionProduct.picturePrice}</em>
										</span>
									</div>
									<div class="s-title">
										<a href="${ctx}/detail/${memberCollectionProduct.PId}.htm" target="_blank">${memberCollectionProduct.pictureName}</a>
									</div>
									<div class="s-extra-box">
										<button href="${ctxm}/collect/memberCollectionProduct/delete.htm?collectionId=${memberCollectionProduct.collectionId}" class="sui-btn btn-bordered btn-small btn-success deleteSure">${fns:fy('删除')}</button>
										<span class="s-sales">${fns:fy('月销')}: ${empty memberCollectionProduct.monthSales?'0':memberCollectionProduct.monthSales}</span>
									</div>
								</li>
							</c:if>
							<c:if test="${memberCollectionProduct.status=='1'}">
								<li>
									<div class="s-pic">
										<a href="${ctx}/detail/${memberCollectionProduct.PId}.htm" target="_blank" >
											<c:choose>
												<c:when test="${not empty memberCollectionProduct.image}">
													<img style="width: 185px;height: 185px;" src="${ctxfs}${memberCollectionProduct.image}@!185x185">
												</c:when>
												<c:otherwise>
													<img src="https://asearch.alicdn.com/bao/uploaded/i4/148570305435535450/TB2UpSaavSM.eBjSZFNXXbgYpXa_!!0-saturn_solar.jpg_220x220.jpg" alt="${fns:fy('手绘板')}" title="${fns:fy('影拓手绘板')}">
												</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div class="s-price-box">
										<span class="s-price">
											<em class="s-price-sign">${fns:fy('￥')}</em>
											<em class="s-value">${memberCollectionProduct.picturePrice}</em>
										</span>
									</div>
									<div class="s-title">
										<a href="${ctx}/detail/${memberCollectionProduct.PId}.htm" target="_blank">${memberCollectionProduct.pictureName}</a>
									</div>
									<div class="s-extra-box">
										<button href="${ctxm}/collect/memberCollectionProduct/delete.htm?collectionId=${memberCollectionProduct.collectionId}" class="sui-btn btn-bordered btn-small btn-success deleteSure">${fns:fy('删除')}</button>
										<span class="s-sales">${fns:fy('月销')}: ${empty memberCollectionProduct.monthSales?'0':memberCollectionProduct.monthSales}</span>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</dd>
			<div class="clear"></div>
			<%@ include file="/views/member/include/page.jsp"%>
		</dl>
	</div>
</body>
</html>
