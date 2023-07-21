<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:fy('交易凭证')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!--图片空间相关放大控件-->
<script type="text/javascript" src="${ctxStatic}/sicheng-seller/js/jquery.magnific-popup.min.js"></script>
<!--图片空间放大控件-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-seller/css/magnific-popup.css">
<style>
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:111px !important;}
	.purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.purchaseBatch .uploader-container img{vertical-align: bottom;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;}
</style>
</head>
<body>
	<div class="main-content clearfix purchase-voucher-list purchaseBatch">
		<div class="main-center">
			<dl class="box">
				<!-- <dt>
					<div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 交易凭证</div>
					<i class="sui-icon icon-tb-list"></i> 交易凭证</span>
					<%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('交易凭证')}</span>
						<span style="margin-top: 5px; margin-left: 10px;" class="">
							<a href="javascript:void(0)" id="toolbar_operate_tips" class="sui-btn btn-bordered btn-small btn-warning" data-placement="bottom" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('操作提示')}">
								<i class="sui-icon icon-pc-light"></i>
							</a>
						</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('会员中心')}</li>
						<li>${fns:fy('供采管理')}</li>
						<li>${fns:fy('交易凭证')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('展示我的交易凭证信息。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="clearfix ml10 mr10 mt10">
					<c:if test="${fn:length(page.list)==0}">
						<!-- <div class="noData" style="margin-top: 0px;"><span>暂无数据</span></div> -->
						<div class="no_product" style="height:400px;text-align: center;color: #9a9a9a;line-height: 400px;">
								<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
							</div>
					</c:if>
					<c:if test="${fn:length(page.list)!=0}">
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th>${fns:fy('订单号')}</th>
								<th>${fns:fy('上传文件')}</th>
								<th>${fns:fy('凭证类型')}</th>
								<th>${fns:fy('交易金额')}</th>
								<th>${fns:fy('审核状态')}</th>
								<th>${fns:fy('上传时间')}</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${page.list}" var="purchaseTradeVoucher">
								<tr>
									<td>${purchaseTradeVoucher.purchaseTradeOrder.purchaseTradeId}</td>
									<td>
										<a target="_blank" href="${ctxfs}${purchaseTradeVoucher.filePath}?accessKey=${generateAccessKey}">${fns:fy('查看')}</a>
										<%-- <div class="comment-pic">
											<a class="J-thumb-img" href="${ctxfs}${purchaseTradeVoucher.filePath}?accessKey=${generateAccessKey}">
												<img src="${ctxfs}${purchaseTradeVoucher.filePath}@50x50?accessKey=${generateAccessKey}" />
											</a>  
										</div>  --%>
									</td>
									<td>${fns:getDictLabel(purchaseTradeVoucher.type, 'purchase_voucher_type', '')}</td>
									<td>${purchaseTradeVoucher.money}${fns:fy('元')}</td>
									<td>${fns:getDictLabel(purchaseTradeVoucher.status, 'purchase_voucher_status', '')}</td>
									<td><fmt:formatDate value="${purchaseTradeVoucher.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/views/seller/include/page.jsp"%>
					</c:if>
				</dd>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseTradeVoucherList.js"></script>
</body>
</html>
