<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="isEdit" value ="${not empty productSpu.PId?true:false}"></c:set >
<title>${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productBrandList.js"></script>
<style type="text/css">
 .sui-msg.msg-block{margin-left:10px!important;margin-right:10px!important;margin-top:-10px!important;margin-bottom:-10px!important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="box" style="min-height: 500px;">
					<dt class="box-header">
						<h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</span></h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('商品')}</li>
							<li>${fns:fy('商品管理')}</li>
							<li class="active">${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</li>
						</ul>
					</dt>
					<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0">
						<!-- 引导条 开始 -->
						<div class="m10">
							<div class="sui-steps steps-large steps-auto">
								<div class="wrap">
									<div class="finished">
										<label><span class="round">1</span><span>${fns:fy('第一步：选择商品分类')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
									</div>
								</div>
								<div class="wrap">
									<div class="current">
										<label><span class="round">2</span><span>${fns:fy('第二步：填写商品详情')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
									</div>
								</div>
								<div class="wrap">
									<div class="current">
										<label><span class="round">3</span><span>${fns:fy('第三步：商品发布成功')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
									</div>
								</div>
							</div>
						</div>
						<!-- 引导条 结束 -->
						<div class="sui-msg msg-large msg-tips msg-block">
							<div class="msg-con">
								<h4><i class="sui-icon icon-tb-roundcheck"></i>${fns:fy('恭喜您，商品发布成功！')}</h4>
								<div style="margin-left: 25px; font-weight: normal;"> 
									<a class="m-l-30" href="${ctxf}/detail/${productSpu.PId}.htm" target="_blank">${fns:fy('去店铺查看商品详情')}&gt;&gt;</a> 
									<shiro:hasPermission name="product:productSpu:edit">
									<a class="m-l-30" href="${ctxs}/product/productSpu/edit1.htm?pId=${productSpu.PId}&sign=${productSpu.status}">${fns:fy('重新编辑刚发布的商品')}&gt;&gt;</a> 
									</shiro:hasPermission>
								</div>
								<h4>${fns:fy('您还可以：')}</h4>
								<ul style="margin-left: 25px;font-weight: normal;margin-bottom: 20px;">
									<shiro:hasPermission name="product:productSpu:edit">
										<li>${fns:fy('1.继续')}" <a href="${ctxs}/product/productSpu/save1.htm">${fns:fy('发布新商品')}</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="product:productSpu:view">
										<li>${fns:fy('2.进入（商家中心）管理')} "<a href="${ctxs}/product/productSpu/list.htm?status=${productSpu.status}">${fns:fy('商品列表')}</a>"</li>
									</shiro:hasPermission>
								</ul>
								<button type="button" data-dismiss="msgs" class="sui-close">×</button>
							</div>
						</div>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>