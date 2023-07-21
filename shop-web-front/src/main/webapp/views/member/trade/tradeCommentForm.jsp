<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('发布评价')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeCommentForm.js"></script>
<style type="text/css">
	.evaluate-post .text .evaluate-content .upmyimg .upmyimg-list{padding: 0;}
	.evaluate-post .mystart .stars li label{margin-right: 5px;}
	.ks-simplestar .starOld{font-size: 25px;color: #d4d4d4;}
	.ks-simplestar .starNew{font-size: 25px;color: #ffb024;}
	.evaluate dt{font-size: 16px;font-weight: normal;color: #666;border-bottom: none;float: left;padding:0;padding-right: 30px;}
	.evaluate ul li{float: left;margin-right:20px;}
	.evaluate{height: 12px;}
</style>
</head>
<body>
	<!-- main-center开始 -->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('交易中心')} > ${fns:fy('发布评价')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('发布评价')}
			</dt>
			<form action="${ctxm}/trade/tradeComment/save2.htm" method="post" id="inputForm" class="sui-form">
				<input type="hidden" name="orderId" value="${tradeOrder.orderId}"/>
				<input type="hidden" name="storeId" value="${tradeOrder.storeId}"/>
				<input type="hidden" name="isAdditionalComment" value="${isAdditionalComment}">
				<dd class="evaluate-post pl20 pr20">
					<c:forEach items="${tradeOrder.tradeOrderItemList}" var="item" varStatus="itemIndex">
						<div class="text">
							<div class="goodspic">
								<a href="#">
									<img src="${ctxfs}${item.thumbnailPath}@!120x120" style="width: 120px;height: 120px;"
									onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
									<div class="goodsname">${item.name}</div>
									<input type="hidden" name="pId" value="${item.PId}"/>
									<input type="hidden" name="skuId" value="${item.skuId}"/>
								</a>
							</div>
							<div class="evaluate-content">
								<c:if test="${isAdditionalComment ne '1'}">
									<dl class="evaluate">
										<dt style="font-size: 16px;font-weight: normal;color: #666;border-bottom: none;float: left;padding:0;padding-right: 30px;">${fns:fy('商品评价')}</dt>
										<dd>
											<ul class="">
												<li>
													<label class="radio-pretty inline checked">
													<input type="radio" checked="checked" name="grade" value="1" class="grade"><span>${fns:fy('好评')}</span>
												</li>
												<li>
													<label class="radio-pretty inline">
													<input type="radio" name="grade" value="2" class="grade"><span>${fns:fy('中评')}</span>
												</li>
												<li>
													<label class="radio-pretty inline">
													<input type="radio" name="grade" value="3" class="grade"><span>${fns:fy('差评')}</span>
												</li>
											</ul>
										</dd>
									</dl>
								</c:if>
								<textarea class="evaluate-text" name="content" placeholder="${fns:fy('这家伙很懒，啥都没写。')}" maxlength="512"></textarea>
								<dl class="upmyimg" style="padding-top: 30px;">
									<dt>${fns:fy('我要晒图')}</dt>
									<dd style="border-bottom: none;">
										<div class="input-append a a${itemIndex.index}">
						        			<input type="hidden" name="img1"/>
						        			<input type="hidden" name="img2"/>
						        			<input type="hidden" name="img3"/>
						        			<input type="hidden" name="img4"/>
						        			<input type="hidden" name="img5"/>
						        			<div id="vessel${itemIndex.index}" class="myupload"></div>
						        		</div>
									</dd>
								</dl>
							</div>
						</div>
					</c:forEach>
					<div class="mystart">
						<c:if test="${isAdditionalComment ne '1'}">
							<div class="title">${fns:fy('给店铺评分')}</div>
							<ul class="stars">
								<li>
									<label style="width: 161px;display: inline-block;text-align: right;">${fns:fy('宝贝与描述相符')}</label>
									<span data-type="description" class="rate-stars" style="position: absolute; left: -9999px;">
										<label><input type="radio" value="1" name="productScore" id="productScoreRadio1" title="${fns:fy('很不满意，差得太离谱，与卖家描述的严重不符，非常不满')}"/>${fns:fy('一星')}</label>
										<label><input type="radio" value="2" name="productScore" id="productScoreRadio2" title="${fns:fy('不满意，部分有破损，与卖家描述的不符，不满意')}"/>${fns:fy('二星')}</label>
										<label><input type="radio" value="3" name="productScore" id="productScoreRadio3" title="${fns:fy('一般，质量一般，没有卖家描述的那么好')}"/>${fns:fy('三星')}</label>
										<label><input type="radio" value="4" name="productScore" id="productScoreRadio4" title="${fns:fy('满意，质量不错，与卖家描述的基本一致，还是挺满意的')}"/>${fns:fy('四星')}</label>
										<label><input type="radio" value="5" name="productScore" id="productScoreRadio5" title="${fns:fy('很满意，卖家发货速度非常快')}" checked="checked"/>${fns:fy('五星')}</label>
									</span>
									<span class="ks-simplestar">
										<label for="productScoreRadio1" class="scoreRadio" title="${fns:fy('很不满意，差得太离谱，与卖家描述的严重不符，非常不满')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="productScoreRadio2" class="scoreRadio" title="${fns:fy('不满意，部分有破损，与卖家描述的不符，不满意')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="productScoreRadio3" class="scoreRadio" title="${fns:fy('一般，质量一般，没有卖家描述的那么好')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="productScoreRadio4" class="scoreRadio" title="${fns:fy('满意，质量不错，与卖家描述的基本一致，还是挺满意的')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="productScoreRadio5" class="scoreRadio" title="${fns:fy('很满意，卖家发货速度非常快')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
									</span>
									<span class="ks-ss-tips"></span>
								</li>
								<li>
									<label style="width: 161px;display: inline-block;text-align: right;">${fns:fy('卖家的服务态度')}</label>
									<span data-type="attitude" class="rate-stars" style="position: absolute; left: -9999px;">
										<label><input type="radio" value="1" name="serviceAttitudeScore" id="serviceScoreRadio1" title="${fns:fy('很不满意，再三提醒下，卖家超过一天才发货，耽误我的时间')}"/>${fns:fy('一星')}</label>
										<label><input type="radio" value="2" name="serviceAttitudeScore" id="serviceScoreRadio2" title="${fns:fy('不满意，卖家有点不耐烦，承诺的服务也兑现不了')}"/>${fns:fy('二星')}</label>
										<label><input type="radio" value="3" name="serviceAttitudeScore" id="serviceScoreRadio3" title="${fns:fy('一般，卖家发货速度一般，提醒后才发货的')}"/>${fns:fy('三星')}</label>
										<label><input type="radio" value="4" name="serviceAttitudeScore" id="serviceScoreRadio4" title="${fns:fy('满意，卖家服务挺好的，沟通挺顺畅的，总体满意')}"/>${fns:fy('四星')}</label>
										<label><input type="radio" value="5" name="serviceAttitudeScore" id="serviceScoreRadio5" title="${fns:fy('很满意，卖家发货速度非常快')}" checked="checked"/>${fns:fy('五星')}</label>
									</span>
									<span class="ks-simplestar">
										<label for="serviceScoreRadio1" class="scoreRadio" title="${fns:fy('很不满意，再三提醒下，卖家超过一天才发货，耽误我的时间')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="serviceScoreRadio2" class="scoreRadio" title="${fns:fy('不满意，卖家有点不耐烦，承诺的服务也兑现不了')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="serviceScoreRadio3" class="scoreRadio" title="${fns:fy('一般，卖家发货速度一般，提醒后才发货的')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="serviceScoreRadio4" class="scoreRadio" title="${fns:fy('满意，卖家服务挺好的，沟通挺顺畅的，总体满意')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="serviceScoreRadio5" class="scoreRadio" title="${fns:fy('很满意，卖家发货速度非常快')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
									</span>
									<span class="ks-ss-tips"></span>
								</li>
								<li>
									<label style="width: 161px;display: inline-block;text-align: right;">${fns:fy('卖家发货的速度')}</label>
									<span data-type="delivery" class="rate-stars" style="position: absolute; left: -9999px;">
										<label><input type="radio" value="1" name="deliverySpeedScore" id="deliveryScore1" title="${fns:fy('很不满意，再三提醒下，卖家超过一天才发货，耽误我的时间')}"/>${fns:fy('一星')}</label>
										<label><input type="radio" value="2" name="deliverySpeedScore" id="deliveryScore2" title="${fns:fy('不满意，卖家发货有点慢的，催了几次终于发货了')}"/>${fns:fy('二星')}</label>
										<label><input type="radio" value="3" name="deliverySpeedScore" id="deliveryScore3" title="${fns:fy('一般，卖家发货速度一般，提醒后才发货的')}"/>${fns:fy('三星')}</label>
										<label><input type="radio" value="4" name="deliverySpeedScore" id="deliveryScore4" title="${fns:fy('满意，卖家发货还算及时')}"/>${fns:fy('四星')}</label>
										<label><input type="radio" value="5" name="deliverySpeedScore" id="deliveryScore5" title="${fns:fy('很满意，卖家发货速度非常快')}" checked="checked"/>${fns:fy('五星')}</label>
									</span>
									<span class="ks-simplestar">
										<label for="deliveryScore1" class="scoreRadio" title="${fns:fy('很不满意，再三提醒下，卖家超过一天才发货，耽误我的时间')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="deliveryScore2" class="scoreRadio" title="${fns:fy('不满意，卖家发货有点慢的，催了几次终于发货了')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="deliveryScore3" class="scoreRadio" title="${fns:fy('一般，卖家发货速度一般，提醒后才发货的')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="deliveryScore4" class="scoreRadio" title="${fns:fy('满意，卖家服务挺好的，沟通挺顺畅的，总体满意')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
										<label for="deliveryScore5" class="scoreRadio" title="${fns:fy('很满意，卖家发货速度非常快')}"><i class="sui-icon icon-tb-favorfill starNew"></i></label>
									</span>
									<span class="ks-ss-tips"></span>
								</li>
							</ul>
						</c:if>
						<div class="evaluate-ok"><button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('提交')}</button></div>
					</div>
				</dd>
			</form>
		</dl>
	</div>
	<!-- main-center结束 -->
</body>
</html>
