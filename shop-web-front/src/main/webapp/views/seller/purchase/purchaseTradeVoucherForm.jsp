<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('上传交易凭证')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<script type="text/javascript">
	var status = ${purchaseTradeVoucher.status}
</script>
<style>
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:250px !important;}
	.purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.purchaseBatch .uploader-container img{vertical-align: bottom;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;}
</style>
</head>
<body>
	<div class="main-content form-style purchaseBatch">
		<div class="main-center">
			<dl class="box" style="height: 560px;">
				<!-- <dt>
                    <div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 交易凭证</div>
                    <i class="sui-icon icon-tb-list"></i> 交易凭证
                    <%@ include file="../include/functionBtn.jsp"%>
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
						<li>${fns:fy('上传交易凭证')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('用户需要上传当前订单的交易凭证。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/purchase/voucher/save2.htm">
					<dd>
						<input type="hidden" name="tradeVoucherId" value="${purchaseTradeVoucher.tradeVoucherId}" class="input-xxlarge input-xfat"/>
						<input type="hidden" name="purchaseTradeId" value="${purchaseTradeVoucher.purchaseTradeId}" class="input-xxlarge input-xfat"/>
						<input type="hidden" name="money" value="${purchaseTradeOrder.price}" class="input-xxlarge input-xfat"/>
						<input type="hidden" name="orderSta" value="${orderSta}" class="input-xxlarge input-xfat"/>
						<input type="hidden" name="accessKey" value="${generateAccessKey}"/>
						<div class="control-group">
							<label class="control-label">${fns:fy('订单号：')}</label> ${purchaseTradeOrder.purchaseTradeId}
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('订单价格：')}</label> ${fns:fy('￥')}${purchaseTradeOrder.price}
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('交易凭证：')}</label>
								<div class="input-append">
									<input id="img_filePath_0" type="hidden" class="imgPath" name="filePath" value="${purchaseTradeVoucher.filePath}"/>
									<div id="vessel"></div>
								</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">
									<c:if test="${not empty purchaseTradeVoucher.filePath}">
				        				${fns:fy('点击可下载查看上传的交易凭证')}
									</c:if>
									<c:if test="${empty purchaseTradeVoucher.filePath}">
										${fns:fy('点击图片可查看原图。修改图片时请先删除原图片，删除按钮在图片上。请上传交易凭证')}
									</c:if>
								</div>
							</div>
						</div>
						<c:if test="${purchaseTradeOrder.status == '40'}">
							<div class="control-group">
								<label class="control-label">${fns:fy('交易类型：')}</label> ${fns:getDictLabel(purchaseTradeVoucher.type, 'purchase_voucher_type', '')}
							</div>
						</c:if>
						<c:if test="${purchaseTradeOrder.status != '40'}">
							<div class="control-group">
								<label class="control-label"><font color="red">*</font>${fns:fy('交易类型：')}</label>
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
										<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input type="hidden" name="type" value="${purchaseTradeVoucher.type}" class="type"><i class="caret"></i>
											<span>
												<c:set value="0" var="isType"></c:set>
												<c:forEach items="${fns:getDictList('purchase_voucher_type')}" var="item">
													<c:if test="${item.value==purchaseTradeVoucher.type}">
														${item.label}
														<c:set value="1" var="isType"></c:set>
													</c:if>
												</c:forEach>
												<c:if test="${isType=='0'}">${fns:fy('请选择')}</c:if>
											</span>
										</a>
									 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									 		<c:forEach items="${fns:getDictList('purchase_voucher_type')}" var="item">
												<li role="presentation" class="${item.value==purchaseTradeVoucher.type?'active':''}">
													<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${item.value}">
														${item.label}
													</a>
												</li>
									 		</c:forEach>
									 	</ul>
									</span>
								</span>
							 	<div class="formPrompt">
									<i class="msg-icon"></i>
									<div class="msg-con">${fns:fy('请选择交易类型')}</div>
								</div>
							</div>
							<c:if test="${purchaseTradeVoucher.status=='20' }">
							<div class="control-group">
								<label class="control-label">${fns:fy('审核状态：')}</label> ${fns:getDictLabel(purchaseTradeVoucher.status, 'purchase_voucher_status', '')}
							</div>
							<div class="control-group">
								<label class="control-label">${fns:fy('审核理由：')}</label> ${purchaseTradeVoucher.auditGrounds}
							</div>
							</c:if>
							<div class="text-align pb20">
								<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('提交')}</button>
							</div>
						</c:if>
					</dd>
				</form>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseTradeVoucherForm.js"></script>
</body>
</html>