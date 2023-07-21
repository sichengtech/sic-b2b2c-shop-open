<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('首页')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
</head>
<body>

	<!--body wrapper start-->
	<div class="wrapper">
		<!--breadcrumbs start -->
		<ul class="breadcrumb panel">
			<li><i class="fa fa-home"></i> ${fns:fy('首页')}</li>
			<li>${fns:fy('平台首页')}</li>
			<li class="active">${fns:fy('平台首页')}</li>
		</ul>
		<!--breadcrumbs end -->
		<!--alert start-->
		<!-- <div class="alert alert-block alert-danger fade in">
			<button type="button" class="close close-sm" data-dismiss="alert">
				<i class="fa fa-times"></i>
			</button>
			<strong>升级提醒!</strong> 升级提醒:最新版本为xxshop v2.0 [ 更新日期2016-5-5 ] 您的版本是v1.0 【<a href="#">点此升级</a>】
		</div> -->
		<!--alert end-->

		<!-- 首页八图 start -->
		<div class="row states-info">
			<div class="col-xs-3">
				<div class="panel red-bg"  title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('今日订单量')}</span>
								<h4>  ${adminIndex.ordercountday}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel blue-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('今日交易额')}</span>
								<h4>${fns:fy('￥')}${empty adminIndex.moneycountday?'0':adminIndex.moneycountday}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel green-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('总订单量')}</span>
								<h4>${adminIndex.ordercount}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel yellow-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('总交易额')}</span>
								<h4>${fns:fy('￥')}${empty adminIndex.ordermoneycount?'0':adminIndex.ordermoneycount}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 首页八图第二行 -->
		<div class="row states-info">
			<div class="col-xs-3">
				<div class="panel blue-bg" title="${fns:fy('数据每小时更新一次')},${fns:fy('一周内登录的是活跃会员')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-male"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('活跃买家/卖家')}</span>
								<h4>${adminIndex.activemembercount}/${adminIndex.activesellercount}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel yellow-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-male"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('会员总数')}/${fns:fy('今日新增')}</span>
								<h4>${adminIndex.membercount}/${adminIndex.membercountday}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel red-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-male"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">${fns:fy('商铺总数')}/${fns:fy('今日新增')}</span>
								<h4>${adminIndex.storecount}/${adminIndex.storecountday}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel green-bg" title="${fns:fy('数据每小时更新一次')}">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-4">
								<i class="fa fa-th"></i>
							</div>
							<div class="col-xs-8">
								<span class="state-title">spu/sku${fns:fy('商品总数')}</span>
								<h4>${adminIndex.productspucount}/${adminIndex.productskucount}</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 首页八图 end -->

		<!-- 销售额走势图 start -->
		<div class="row">
			<div class="col-xs-8">
				<section class="panel">
					<header class="panel-heading">${fns:fy('销售额区域分布')} 
						<span class="tools pull-right"> 
							<!-- <a href="javascript:;" class="fa fa-chevron-down"></a> 
							<a href="javascript:;" class="fa fa-times"></a> -->
						</span>
					</header>
					<div class="panel-body">
						<div id="visitors-chart">
							<div id="visitors-container" style="width: 100%; height: auto; text-align: center; margin: 0 auto;">
								<a href="${ctxa}/report/orderMap/countDayNum.do" target="_blank"><img style="width: 100%; height: 100%;" alt="" src="${ctxStatic}/sicheng-admin/images/orderMap.png"></a>
							</div>
						</div>
					</div>
				</section>
			</div>

			<div class="col-xs-4">
				<div class="panel">
					<header class="panel-heading">${fns:fy('商家月销量排行')} 
						<span class="tools pull-right"> 
							<!-- <a href="javascript:;" class="fa fa-chevron-down"></a> 
							<a href="javascript:;"class="fa fa-times"></a> -->
						</span>
					</header>
					<c:if test="${empty list}">
						<div style="text-align: center;padding-bottom: 229px;padding-top: 229px;">${fns:fy('商家暂无销售')}</div>
					</c:if>
					<div class="panel-body">
						<ul class="goal-progress">
							<c:forEach items="${list}" var="storeSale">
								<li>
									<div class="prog-avatar">
										<img src="${ctxfs}${storeSale.store.logo}" alt="" onerror="fdp.defaultImage('${ctxStatic}/images/noimg.jpg');"/>
									</div>
									<div class="details">
										<div class="title">
											<a href="${ctxa}/store/store/list.do?name=${storeSale.store.name}">${storeSale.store.name}</a>
										</div>
										<div class="progress progress-xs">
											<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width:${storeSale.count}%">
												<span class="">${storeSale.PAYMONEY}</span>
											</div>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
						<div class="text-center">
							<a target="_blank" href="${ctxa}/report/storeSale/countMonthNum.do">${fns:fy('查看更多')}</a>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- 销售额走势图 end -->

	</div>
	<!--body wrapper end-->

</body>
</html>