<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<title>${fns:fy('店铺收藏')}</title>
<html lang="zh-CN">
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/collect/memberCollectionStore.js"></script>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('收藏中心')} > ${fns:fy('店铺收藏')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('店铺收藏')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="p20 my-favor-store">
				<c:forEach items="${page.list}" var="memberCollectionStore">
					<div class="store-info">
						<div class="store-logo">
							<a href="${ctx}/store/${memberCollectionStore.storeId}.htm" target="_blank" >
								<c:choose>
									<c:when test="${not empty memberCollectionStore.store.logo}">
										<img style="width: 80px;height: 40px;" src="${ctxfs}${memberCollectionStore.store.logo}@!80x40">
									</c:when>
									<c:otherwise>
										<img src="http://i2ya.oss-cn-hangzhou.aliyuncs.com/WOMS/Brand/Raw/598908287a2a421fb8ba5fd9c34d1453.jpg" alt="${fns:fy('看购贵州官方旗舰店')}" title="${fns:fy('看购贵州官方旗舰店')}">
									</c:otherwise>
								</c:choose>
							</a>
						</div>
						<div class="store-name">
							<button href="${ctxm}/collect/memberCollectionStore/delete.htm?collectionStoreId=${memberCollectionStore.collectionStoreId}" class="pull-right sui-btn btn-bordered btn-danger deleteSure">
								<i class="sui-icon icon-tb-delete"></i>
							</button>
							<a href="${ctx}/store/${memberCollectionStore.storeId}.htm" target="_blank" ><span>${memberCollectionStore.store.name}</span></a>
						</div>
						<div class="store-service">
							<c:forEach items="${memberCollectionStore.storeCustomerServiceList}" var="storeCustomerService">
								<a target="_blank" rel="nofollow" href="http://wpa.qq.com/msgrd?v=3&amp;uin=${storeCustomerService.account}&amp;site=qq&amp;menu=yes">
									<img style="CURSOR: pointer" src="http://wpa.qq.com/pa?p=1:599686:10" alt="${fns:fy('请点击咨询')}">
	      						</a>
							</c:forEach>
						 </div>
					</div>
					<div class="store-goods">
						<ul class="s-item-list">
							<c:forEach items="${memberCollectionStore.solrProductList}" var="productSpu" begin="0" end="3">
								<li>
									<div class="s-pic">
										<a href="${ctx}/detail/${productSpu.PId}.htm" target="_blank">
											<img style="width: 120px;height: 120px;" src="${ctxfs}${productSpu.image}@!120x120" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');">
										</a>
									</div>
									<div class="s-title">
										<a href="${ctx}/detail/${productSpu.PId}.htm" target="_blank">${productSpu.name}</a>
									</div>
									<div class="s-price-box">
										<span class="s-price"><em class="s-price-sign">${fns:fy('¥')}</em>
											<em class="s-value">
												<c:if test="${productSpu.type=='2'}">
													${productSpu.minPrice2}
												</c:if> 
												<c:if test="${productSpu.type=='1' || productSpu.type=='3'}">
												 	${productSpu.minPrice1}
												</c:if> 
											</em>
										</span>
									</div>
								</li>
							</c:forEach>
							<div class="clear"></div>
						</ul>
					</div>
				</c:forEach>
			</dd>
			<div class="clear"></div>
			<%@ include file="/views/member/include/page.jsp"%>
		</dl>
	</div>
</body>
</html>
