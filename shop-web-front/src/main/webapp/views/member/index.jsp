<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('首页')}</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/index.js"></script>
</head>
<body>
	<dl class="mycart" style="height: 1450px">
		<dt>
			<a target="_blank" href="${ctxf}/trade/cart/list.htm"><span class="pull-right">${fns:fy('查看全部')}</span></a>
			<i class="sui-icon icon-tb-cart"></i> ${fns:fy('购物车')}
		</dt>
		<dd>
		<c:if test="${empty tradeCartList}">
			<div style="padding: 150px 0px 150px 0px;text-align: center;">${fns:fy('购物车暂无商品')}</div>
		</c:if>
			<ul>
				<c:forEach items="${tradeCartList}" var="tradeCart" end="9">
					<li>
						<div class="int">
							<div class="s-cart-price">￥${tradeCart.priceSum}</div>
						</div>
						<a target="_blank" href="${ctxf}/detail/${tradeCart.productSpu.PId}.htm">
							<img src="${ctxfs}${tradeCart.productSpu.image}@!46x46" width="46" height="46" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
							<div class="s-cart-title">${tradeCart.productSpu.name}</div>
						</a>
					</li>
				</c:forEach>
			</ul>
		</dd>
		<dd class="pl10 pr10">
			<a target="_blank" href="${ctxf}/trade/cart/list.htm" class="sui-btn btn-block btn-xlarge btn-danger">${fns:fy('购物车列表')}</a>
		</dd>
    </dl>
	<!--mycart end-->
	<div class="main-center pull-left w707" style="background:#fff url(${ctxStatic}/sicheng-member/images/member-main-bg.gif) left repeat-y; min-height:902px;">
	<dl>
		<dt><i class="sui-icon icon-tb-list"></i> ${fns:fy('交易提醒')}</dt>
		<dd>
			<div class="order-tabs">
				<ul class="sui-nav nav-tabs">
					<li class="active"><a href="${ctxm}/trade/tradeOrder/list.htm" >${fns:fy('全部订单')}<font></font></a></li>
					<li class="${orderStatus eq '10' && isReturnStatus eq '0'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=10" >${fns:fy('待付款')} <font>(${status10Count})</font></a></li>
					<li class="${orderStatus eq '20' && isReturnStatus eq '0'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=20" >${fns:fy('待发货')}<font>(${status20Count})</font></a></li>
					<li class="${orderStatus eq '30' && isReturnStatus eq '0'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=30" >${fns:fy('待收货')} <font>(${status30Count})</font></a></li>
					<li class="${orderStatus eq '40' && isReturnStatus eq '0'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=40" >${fns:fy('待评价')}<font>(${status40Count})</font></a></li>
					<li class="${orderStatus eq '60' && isReturnStatus eq '0'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=60" >${fns:fy('已取消')}<font>(${status60Count})</font></a></li>
					<li class="${isReturnStatus eq '1'?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=1" >${fns:fy('退货退款')}<font>(${isReturnStatusCount1})</font></a></li>
					<li class="${isReturnStatus eq '2'?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=2" >${fns:fy('退款')}<font>(${isReturnStatusCoun2})</font> </a></li>
				</ul>
				<div class="tab-content pl20 pr20">
					<ul class="tab-pane active table-css">
						<c:forEach items="${pageTradeOrder.list}" var="tradeOrder">
							<c:forEach items="${tradeOrder.tradeOrderItemList}" var="orderItem" end="0">
								<c:if test="${fn:length(tradeOrder.tradeOrderItemList)==1}">
									<li>
										<c:choose>
											<c:when test="${tradeOrder.isReturnStatus==1 || tradeOrder.isReturnStatus==2 
												|| (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==20) || (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==30) 
												|| (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==40) || (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==60)}">
												<a href="${ctxm}/trade/tradeOrder/tradeOrderDetail.htm?orderId=${tradeOrder.orderId}" class="sui-btn btn-xlarge btn-info">${fns:fy('查看详情')}</a>
											</c:when>
											<c:otherwise>
												<a orderId="${tradeOrder.orderId}" href="${ctxf}/trade/order/tradeCheckorder.htm?orderIds=${tradeOrder.orderId}" class="sui-btn btn-xlarge btn-info">${fns:fy('去付款')}</a>
											</c:otherwise>
										</c:choose>
										<div class="picture">
											<c:choose>
												<c:when test="${not empty orderItem.thumbnailPath}">
													<img src="${ctxfs}${orderItem.thumbnailPath}@!76x76" width="76" height="76"/>
												</c:when>
												<c:otherwise>
													<img src="${ctxStatic}/sicheng-member/images/57fe0daeNcc1bf78a.jpg" alt=""/>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="words">
											<h4>${orderItem.name}...</h4>
											<p>${fns:fy('下单时间：')}<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	  ${fns:fy('订单金额')} ：${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}</p>  
											<p>
												${fns:fy('订单状态')}：
												<c:if test="${tradeOrder.orderStatus eq '10' }">${fns:fy('付款')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '20' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('订单退款')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '30' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('确认收货')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '40' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('我要评价')}</c:if>
												<c:if test="${tradeOrder.isReturnStatus eq '1'}">${fns:fy('退货退款中')}</c:if>
												<c:if test="${tradeOrder.isReturnStatus eq '2'}">${fns:fy('退款中')}</c:if>
											</p>
										</div>
									</li>
								</c:if>
								<c:if test="${fn:length(tradeOrder.tradeOrderItemList)>1}">
									<li>
										<c:choose>
											<c:when test="${tradeOrder.isReturnStatus==1 || tradeOrder.isReturnStatus==2 
												|| (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==20) || (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==30) 
												|| (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==40) || (tradeOrder.isReturnStatus==0 && tradeOrder.orderStatus==60)}">
												<%-- <a href="${ctxm}/trade/tradeOrder/tradeOrderDetail.htm?orderId=${tradeOrder.orderId}" class="sui-btn btn-xlarge btn-info">查看详情</a> --%>
											</c:when>
											<c:otherwise>
												<%-- <a href="${ctx}/views/front/checkorder.jsp" class="sui-btn btn-xlarge btn-info">去付款</a> --%>
											</c:otherwise>
										</c:choose>
										<div class="picture">
											<div class="picture bg"><b>${fn:length(tradeOrder.tradeOrderItemList)}</b>
											<c:choose>
												<c:when test="${not empty orderItem.thumbnailPath}">
													<img src="${ctxfs}${orderItem.thumbnailPath}@!76x76" width="76" height="76">
												</c:when>
												<c:otherwise>
													<img src="${ctxStatic}/sicheng-member/images/57fe0daeNcc1bf78a.jpg" alt=""/>
												</c:otherwise>
											</c:choose>
											</div>
										</div>
										<div class="words">
											<h4>${orderItem.name}...  ${fns:fy('等')} <font>${fn:length(tradeOrder.tradeOrderItemList)}</font> ${fns:fy('种商品')}</h4>
											<p>${fns:fy('下单时间')}:<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	  ${fns:fy('订单金额')} ：${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}</p>  
											<p>
												${fns:fy('订单状态')}：
												<c:if test="${tradeOrder.orderStatus eq '10' }">${fns:fy('付款')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '20' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('订单退款')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '30' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('确认收货')}</c:if>
												<c:if test="${tradeOrder.orderStatus eq '40' && tradeOrder.isReturnStatus eq '0'}">${fns:fy('我要评价')}</c:if>
												<c:if test="${tradeOrder.isReturnStatus eq '1'}">${fns:fy('我要评价')}</c:if>
												<c:if test="${tradeOrder.isReturnStatus eq '2'}">${fns:fy('退款中')}</c:if>
											</p>
										</div>
									</li>
								</c:if>
							</c:forEach>
						</c:forEach>
					</ul>
					<a href="${ctxm}/trade/tradeOrder/list.htm"><div class="order-more"><p>${fns:fy('全部交易记录')}</p><i class="sui-icon icon-tb-unfold"></i></div></a>
				</div>
			</div>
			<!--order-tabs end-->
		</dd>
	</dl>
	<!--logistics end-->
	<dl class="Collection-goods bor-t">
		<dt><div class="refresh"></div><i class="sui-icon icon-tb-favor"></i> ${fns:fy('我收藏的商品')}</dt>
		<dd class="pl10">
		<c:if test="${empty pageMemberCollectionProduct.list}">
			<div style="padding: 50px 0px 50px 0px;text-align: center;">${fns:fy('暂无收藏商品')}</div>
		</c:if>
			<ul class="s-item-list">
				<c:forEach items="${pageMemberCollectionProduct.list}" var="memberCollectionProduct">
					<li>
						<div class="s-pic">
							<a href="${ctxf}/detail/${memberCollectionProduct.PId}.htm" target="_blank" >
								<img src="${ctxfs}${memberCollectionProduct.image}@!150x150" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');" width="150" height="150"/>
							</a>
						</div>
						<div class="s-price-box">
							<span class="s-price"><em class="s-price-sign">>${fns:fy('￥')}</em><em class="s-value">${memberCollectionProduct.picturePrice}</em></span>
							<span class="s-p4p-item">${fns:fy('掌柜热卖')}</span>
						</div>
						<div class="s-title">
							<a href="${ctxf}/detail/${memberCollectionProduct.PId}.htm">${memberCollectionProduct.pictureName}</a>
						</div>
						<div class="s-extra-box">
							<span class="s-sales">${fns:fy('月销')}: ${empty memberCollectionProduct.monthSales?'0':memberCollectionProduct.monthSales}</span>
						</div>
					</li>
				</c:forEach>
				<div class="clear"></div>
			</ul>
		</dd>
	</dl>
	<!--Collection-goods end-->
	<dl class="Collection-shop bor-t">
		<dt><div class="refresh"></div><i class="sui-icon icon-tb-deliver"></i> ${fns:fy('我收藏的店铺')}</dt>
		<dd class="pl10">
		<c:if test="${empty pageMemberCollectionStore.list}">
			<div style="padding: 50px 0px 50px 0px;text-align: center;">${fns:fy('暂无收藏店铺')}</div>
		</c:if>
		<ul class="s-store-list">
			<c:forEach items="${pageMemberCollectionStore.list}" var="memberCollectionStore">
				<li>
					<div class="s-pic">
						<a href="${ctxf}/store/${memberCollectionStore.store.id}.htm" target="_blank">
							<img src="${ctxfs}${memberCollectionStore.store.logo}@!148x74" onerror="fdp.defaultImage('${ctxfs}/shop_init/store_logo.png');" width="148" height="74">
						</a>
					</div>
					<div class="s-title">
						<a href="${ctxf}/store/${memberCollectionStore.store.id}.htm" target="_blank">${memberCollectionStore.store.name}</a>
					</div>
				</li>
			</c:forEach>
		<div class="clear"></div>
		</ul>
		</dd>
	</dl>
	<!--Collection-shop end-->
	</div>
	<!--main-center-->
</body>
</html>
