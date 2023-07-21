<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购交易凭证管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeVoucherForm.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('审核采购交易凭证')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseTradeVoucher/list.do"> <i class="fa fa-user"></i> ${fns:fy('采购交易凭证列表')}</a></li>
				<shiro:hasPermission name="purchase:purchaseTradeVoucher:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('采购交易凭证审核')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('管理员审核交易凭证信息')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseTradeVoucher/auth2.do" method="post">
						<input type="hidden" name="tradeVoucherId" value="${purchaseTradeVoucher.tradeVoucherId}"/>
						<input type="hidden"name="purchaseTradeId" value="${purchaseTradeVoucher.purchaseTradeId}"/>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('凭证交易号')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchaseTradeVoucher.tradeVoucherId}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('订单号')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchaseTradeVoucher.purchaseTradeId}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('金额')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchaseTradeVoucher.money}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('凭证文件')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">
									<a href="${ctxfs}${purchaseTradeVoucher.filePath}?accessKey=${generateAccessKey}"  target="_blank">
										${fns:fy('查看')}
									</a>
								</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('交易凭证类型')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${fns:getDictLabel(purchaseTradeVoucher.type, 'purchase_voucher_type', '')}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('审核结果')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${purchaseTradeVoucher.status eq 20?"":"checked"} data-size="small" name="authResult" value="1" style="display: none" data-on-text="OK" data-off-text="NO"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写审核结果')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('审核理由')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control valid" name="auditGrounds" maxlength="255">${purchaseTradeVoucher.auditGrounds}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写审核理由')}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="purchase:purchaseTradeVoucher:auth">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>