<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('首页')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/index.js"></script>

<style type="text/css">
	.text_hidden{ white-space:nowrap;overflow:hidden;text-overflow:ellipsis;display: inline-block;}
</style>
</head>
<body>
<div class="sui-container">
	<div class="main-content">
	<div class="sui-row-fluid">
	<div class="w75">
		<dl class="box">
			<dt class="box-header"><h4><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('我的店铺')}</span></h4></dt>
			<dd class="sui-row-fluid borsub">
				<dl class="w33">
					<dt class="box-header-sub"><a><i class="sui-icon icon-tb-deliver"></i><p>${fns:fy('商品通知')}</p></a></dt>
					<dd class="docs mt10">
						<a href="${ctxs}/product/productSpu/list.htm?status=50"><span id="spu1"></span>${fns:fy('出售中的商品：')}</a>
						<a href="${ctxs}/product/productSpu/list.htm?status=10"><span id="spu2"></span>${fns:fy('仓库中的商品：')}</a>
						<a href="${ctxs}/product/productSpu/list.htm?status=20"><span id="spu3"></span>${fns:fy('禁售的商品：')}</a>
					</dd>
				</dl>
				<dl class="w33">
					<dt class="box-header-sub"><a><i class="sui-icon icon-tb-deliver"></i><p>${fns:fy('订单通知')}</p></a></dt>
					<dd class="docs mt10">
						<a href="${ctxs}/trade/tradeOrder/list.htm"><span id="tradeOrder1"></span>${fns:fy('订单总数：')}</a>
						<a href="javascript:;"><span id="tradeOrderMonthNumber"></span>${fns:fy('当月订单数：')}</a>
						<a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=10"><span id="tradeOrder2"></span>${fns:fy('待付款订单：')}</a>
						<a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=20"><span id="tradeOrder3"></span>${fns:fy('待发货订单：')}</a>
					</dd>
				</dl>
				<dl class="w33">
					<dt class="box-header-sub"><a><i class="sui-icon icon-tb-deliver"></i><p>${fns:fy('售后通知')}</p></a></dt>
					<dd class="docs mt10">
						<a href="${ctxs}/trade/tradeReturnOrder/tradeReturnMoneyList.htm"><span id="returnMoney"></span>${fns:fy('退款通知：')}</a>
						<a href="${ctxs}/trade/consultation/list.htm"><span id="consul"></span>${fns:fy('咨询通知：')}</a>
						<a href="${ctxs}/trade/tradeComplaint/list.htm"><span id="complaint"></span>${fns:fy('投诉通知：')}</a>
					</dd>
				</dl>
			</dd>
		</dl>
		<img src="${ctxStatic}/sicheng-seller/images/seller_img01.png" alt="" class="adimg"/>
		<dl class="box">
				<dt class="box-header">
					<h4><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('店铺导航')}</span></h4>
				</dt>
				<dd class="docs icos-list">
					<ul class="sui-row-fluid">
					 <li class="span2"><a href="${ctxs}/product/productSpu/save1.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_01.png" alt=""/><p>${fns:fy('商品发布')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/trade/tradeOrder/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_02.png" alt=""/><p>${fns:fy('商品订单')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/logistics/logisticsTemplate/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_03.png" alt=""/><p>${fns:fy('物流配送')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/trade/tradeComment/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_04.png" alt=""/><p>${fns:fy('评价管理')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/store/store/save1.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_05.png" alt=""/><p>${fns:fy('店铺设置')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/store/storeNavigation/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_06.png" alt=""/><p>${fns:fy('店铺导航')}</p></a></li>
					</ul>
					<ul class="sui-row-fluid">
					 <li class="span2"><a href="${ctxs}/store/storeDecoration/save1.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_07.png" alt=""/><p>${fns:fy('店铺装修')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/trade/tradeReturnOrder/tradeReturnMoneyList.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_08.png" alt=""/><p>${fns:fy('退款管理')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/trade/tradeComplaint/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_01.png" alt=""/><p>${fns:fy('投诉管理')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/logistics/logisticsCompany/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_02.png" alt=""/><p>${fns:fy('快递公司')}</p></a></li>
					 <li class="span2"><a href="${ctxs}/store/customer/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_03.png" alt=""/><p>${fns:fy('客服设置')}</p></a></li>
					 <li class="span2">
						 <a target="_blank" href="${ctxs}/sys/sysMessage/list.htm"><img src="${ctxStatic}/sicheng-seller/images/icon_04.png" alt=""/>
						 	<p>${fns:fy('消息')}</p>
						 </a>
					 </li>
					</ul>
				</dd>
			</dl>

		</div>
		<div class="w25">
				<dl class="ml20 box">
					<dt class="box-header"><h4><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('我的店铺')}</span></h4></dt>
					<dd class="docs">
						<p id="storeId" storeId="${userMain.userSeller.storeId}" class="text_hidden" data-toggle="tooltip" data-original-title="${userMain.loginName}">
							${fns:fy('登录账号：')}${userMain.loginName}
						</p>
						<p class="text_hidden" data-toggle="tooltip" data-original-title="${userMain.userSeller.store.name}">${fns:fy('店铺名称：')} ${userMain.userSeller.store.name}</p>
						<p class="text_hidden" data-toggle="tooltip" data-original-title="${fns:getDictLabel(fn:trim(userMain.userSeller.store.storeType), 'store_type', '')}">
							${fns:fy('店铺类型：')}${fns:getDictLabel(fn:trim(userMain.userSeller.store.storeType), 'store_type', '')}
						</p>
						<p>${fns:fy('店铺等级：')}${userMain.userSeller.store.storeLevel.name}</p>
						<p>
							<span style="color: #fff;background-color: #f19430;padding: 3px 10px;border-radius: 15px;display: inline-block;">
								${fns:fy('企业认证')}
							</span>
						</p>
						<div class="mt10 pt10 bordastop">
							<p class="text_hidden" data-toggle="tooltip" data-original-title="<fmt:formatDate value="${userMain.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
								${fns:fy('上次登录时间：')}<fmt:formatDate value="${userMain.loginDate}" pattern="yyyy-MM-dd HH:mm"/>
							</p>
							<p class="text_hidden" data-toggle="tooltip" data-original-title="${userMain.loginIp}">${fns:fy('上次登录IP：')}${userMain.loginIp}</p>
						</div>
					</dd>
				</dl>

				<dl class="ml20 mt20 box" style="height:325px">
					<dt class="box-header"><h4><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('平台公告')}</span></h4></dt>
					<dd class="docs" style="line-height:25px; overflow:hidden;">
						<div style="overflow:hidden;text-overflow:ellipsis; height:254px; padding:0px;overflow-y: scroll;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${fns:fy('思程商城，为每一位顾客提供网购体验。顾客不出家门，即能享受到来自全国各地的商品和服务，省力、省钱、省时间。目前，思程商城在线销售涵盖工程机械、家电产品、3C数码、安防监控、汽车汽配、等行业的商品。思程商城以“诚信”为本，思程商城严格保障商品和服务的高质量，遵照国家有关“三包”的法律法规，让顾客放心购买。')}
       					</div>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
</body>
</html>