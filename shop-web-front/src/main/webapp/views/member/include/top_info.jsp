<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/member/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/include/topInfo.js"></script>
<div class="member-info">
	<div class="sui-container">
		<div class="group bor-r">
			<div class="face pull-left mr20">
				<c:set var="levelId" value="${userMain.userSeller.store.storeLevel.levelId}"/>
				<c:choose>
					<c:when test="${levelId==1}">
						<b class="vip1"></b>
					</c:when>
					<c:when test="${levelId==2}">
						<b class="vip2"></b>
					</c:when>
					<c:otherwise>
						<b class="vip0"></b>
					</c:otherwise>
				</c:choose>
				<img onerror="fdp.defaultImage('${ctxStatic}/sicheng-member/images/face.png');"/>
			</div>
			<div class="face-info">
			  	<h3 style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"></h3>
				<p class="identity"></p>
				<p class="accountSecurity">&nbsp;${fns:fy('账户安全')}：<span><b></b></span></p>
				<%-- <p>&nbsp;${fns:fy('我的积分')}：0</p> --%>
				<p class="isAuthentication" style="margin-left: 115px;display: none;">
					<%-- <img src="${ctxStatic}/sicheng-member/images/v.png" alt=""/> --%>
					<span style="color: #fff;background-color: #f19430;padding: 3px 10px;border-radius: 15px;display: inline-block;">
						${fns:fy('企业认证')}
					</span>
				</p>
			</div>
		</div>
		<dl class="group bor-r">
			<dt>${fns:fy('我的交易')}</dt>
			<dd>
			  <ul class="icon-group">
				<li class="status10Count"><b>0</b><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=10"><i class="sui-icon icon-tb-pay"></i><p>${fns:fy('待付款')}</p></a></li>
				<li class="status30Count"><b>0</b><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=30"><i class="sui-icon icon-tb-deliver"></i><p>${fns:fy('待收货')}</p></a></li>
				<li class="status40Count"><b>0</b><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=40"><i class="sui-icon icon-tb-evaluate"></i><p>${fns:fy('待评价')}</p></a></li>
			  </ul>
			</dd>
		</dl>
		<dl class="group bor-r">
			<dt>${fns:fy('我喜欢的')}</dt>
			<dd>
			  <ul class="icon-group">
				<li class="memberCollectionStoreCount"><b>0</b><a href="${ctxm}/collect/memberCollectionStore/list.htm"><i class="sui-icon icon-tb-favor"></i><p>${fns:fy('收藏店铺')}</p></a></li>
				<li class="memberCollectionProductCount"><b>0</b><a href="${ctxm}/collect/memberCollectionProduct/list.htm"><i class="sui-icon icon-tb-shop"></i><p>${fns:fy('收藏商品')}</p></a></li>
				<li class="myFootprint"><b>0</b><a href="javascript:;"><i class="sui-icon icon-tb-footprint"></i><p>${fns:fy('我的足迹')}</p></a></li>
			  </ul>
			</dd>
		</dl>
		<dl class="group bor-r">
			<dt>${fns:fy('我的售后')}</dt>
			<dd>
			  <ul class="icon-group">
				<li class="isReturnStatusCount1"><b>0</b><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=1"><i class="sui-icon icon-tb-refund"></i><p>${fns:fy('退货退款')}</p></a></li>
				<li class="isReturnStatusCount2"><b>0</b><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=2"><i class="sui-icon icon-tb-send"></i><p>${fns:fy('退款')}</p></a></li>
				<li class="tradeComplaintCount"><b>0</b><a href="${ctxm}/trade/tradeComplaint/list.htm"><i class="sui-icon icon-tb-form"></i><p>${fns:fy('投诉')}</p></a></li>
			  </ul>
			</dd>
		</dl>
	</div>
</div>
<!--member-info end-->