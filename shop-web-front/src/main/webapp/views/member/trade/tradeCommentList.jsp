<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('评价列表')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeCommentList.js"></script>
<!--图片放大控件-->
<script type="text/javascript" src="${ctxStatic}/sicheng-seller/js/jquery.magnific-popup.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-seller/css/magnific-popup.css">
<style type="text/css">
	.myevaluate ul li{display: flow-root;}
	.myevaluate ul li .words .content{height: auto;word-break:break-all;}
	.myevaluate ul li .words .contentAdd{border-top: 1px dashed #E6E6E6;margin-top: 10px;}
	.myevaluate ul li .words .commentImg{margin: 10px 0;}
	.myevaluate ul li .words{min-height: 80px;height: auto;}
</style>
</head>
<body>
	<div class="main-center">
	<dl>
		<dt>
			<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('交易中心')} > ${fns:fy('交易评价')}</div>
			<i class="sui-icon icon-tb-list"></i> ${fns:fy('交易评价')}
		</dt>
		<dd class="myevaluate pl20 pr20">
			<sys:message content="${message}"/>
			<ul>
				<c:forEach items="${page.list}" var="tradeComment" varStatus="index">
					<li>
						<div class="goodspic">
							<a href="${ctxf}/detail/${tradeComment.productSpu.PId}.htm" target="_blank">
								<img src="${ctxfs}${tradeComment.productSpu.image}@!80x80" width="80" height="80"
								onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
							</a>
						</div>
						<div class="words">
							<div class="goodsinfo">
								<span class="pull-right comment-star">${fns:fy('评分')}：<i class="star${tradeComment.productScore}"></i></span>
								<span class="pull-right">${fns:fy('评价时间')}：<fmt:formatDate value="${tradeComment.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/></span>
								<span class="pull-right">
									<c:if test="${tradeComment.productSku.spec1 ne null && tradeComment.productSku.spec1V ne null}">
										${fn:split(tradeComment.productSku.spec1, '_')[1]} : ${tradeComment.productSku.spec1V}
									</c:if>
									<c:if test="${tradeComment.productSku.spec2 ne null && tradeComment.productSku.spec2V ne null}">
										, ${fn:split(tradeComment.productSku.spec2, '_')[1]} : ${tradeComment.productSku.spec1V}
									</c:if>
									<c:if test="${tradeComment.productSku.spec3 ne null && tradeComment.productSku.spec3V ne null}">
										, ${fn:split(tradeComment.productSku.spec3, '_')[1]} : ${tradeComment.productSku.spec1V}
									</c:if>
								</span>
								<a href="${ctxf}/detail/${tradeComment.productSpu.PId}.htm" target="_blank">
									<span class="goodsname">${tradeComment.productSpu.name}</span>
								</a>
							</div>
							<div class="content">${tradeComment.content}</div>
							<div class="commentImg gallery">
								<c:forEach items="${tradeComment.tradeCommentImageList}" var="img">
									<a class=""  href="${ctxfs}${img.path}">
										<img alt="" src="${ctxfs}${img.path}@!30x30" width="30" height="30">
									</a>
								</c:forEach>
							</div>
							<c:if test="${not empty tradeComment.tradeCommentExplain}">
								<div class="content" style="color: #a7a4a4;">
									[${fns:fy('解释')}] ${tradeComment.tradeCommentExplain.content}
								</div>
							</c:if>
							<c:if test="${not empty tradeComment.tradeCommentAdd}">
								<div class="content contentAdd">
									<div style="color: #999;">
										[<fmt:formatDate value="${tradeComment.tradeCommentAdd.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/> ${fns:fy('追加评价')}]
									</div>
									 ${tradeComment.tradeCommentAdd.content}
								</div>
								<div class="commentImg gallery">
									<c:forEach items="${tradeComment.tradeCommentAdd.tradeCommentImageList}" var="img">
										<a class=""  href="${ctxfs}${img.path}">
											<img alt="" src="${ctxfs}${img.path}@!30x30" width="30" height="30">
										</a>
									</c:forEach>
								</div>
								<c:if test="${not empty tradeComment.tradeCommentAdd.tradeCommentExplain}">
									<div class="content" style="color: #a7a4a4;">
										[${fns:fy('解释')}] ${tradeComment.tradeCommentAdd.tradeCommentExplain.content}
									</div>
								</c:if>
							</c:if>
						</div>
					</li>
				</c:forEach>
				<!-- 没有数据提示开始 -->
				<c:if test="${fn:length(page.list)=='0'}">
					<div class="no_product" style="text-align: center;color: #9a9a9a;margin-top: 300px;">
						<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
					</div>
				</c:if>
				<!-- 没有数据提示结束 -->
			</ul>
		<!--myevaluate end-->
		</dd>
		<c:if test="${fn:length(page.list)>'0'}">
			<%@ include file="/views/member/include/page.jsp"%>
		</c:if>
	</dl>
	</div>
	<!--main-center-->

</body>
</html>
