<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>${fns:fy('批量采购')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:111px !important;}
	.purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;}
</style>
</head>
<body>
    <div class="main-content form-style purchaseBatch">
		<div class="main-center">
			<dl class="box">
				<!-- <dt class="box-header">
                    <div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采</span> &gt; 批量采购</div>
                    <i class="sui-icon icon-tb-list"></i> 批量采购
                    <%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('批量采购')}</span>
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
						<li>${fns:fy('批量采购')}</li>
					</ul>
				</dt>

				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('可以点击增加或删除，发布多条采购。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/purchase/purchase/batchRelease2.htm">
					<dd>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购标题：')}</label> 
							<input type="text" class="input-xxlarge input-xfat" name="title" maxlength="64" value="${purchase.title }">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写采购标题。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('交货周期：')}</label> 
							<input type="text" class="input-xxlarge input-xfat" name="cycle" maxlength="64" value="${purchase.cycle }">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写交货周期。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('采购说明：')}</label> 
							<input type="text" class="input-xxlarge input-xfat" name="purchaseExplain" maxlength="255" value="${purchase.purchaseExplain }">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写采购说明。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购到期时间：')}</label> 
							<input type="text" class="input-xxlarge input-xfat" name="expiryTime" value="<fmt:formatDate value="${purchase.expiryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'});">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请选择采购到期时间。')}</div>
							</div>
						</div>
						<div style="margin: 0 10px;">
							<a href="javascript:void(0);" class="sui-btn btn-warning addPurchaseRow">${fns:fy('新增')}</a>
							<table id="listTable" class="sui-table table-bordered-simple">
								<thead>
									<!-- <tr>
									</tr> -->
									<tr>
										<th><font color="red">*</font>${fns:fy('产品名称')}</th>
										<th><font color="red">*</font>${fns:fy('型号')}</th>
										<th>${fns:fy('品牌')}</th>
										<th><font color="red">*</font>${fns:fy('数量')}</th>
										<th>${fns:fy('单价(元)')}</th>
										<th><font color="red">*</font>${fns:fy('单位')}</th>
										<th>${fns:fy('备注')}</th>
										<th>${fns:fy('操作')}</th>
									</tr>
								</thead>
								<tbody id="purchaseTbody"></tbody>
							</table>
							<div class="text-align pb20">
								<button type="submit" class="sui-btn btn-xlarge btn-primary ml20">${fns:fy('保存2')}</button>
							</div>
						</div>
					</dd>
				</form>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseBatch.js"></script>
	<script type="text/template" id="purchase_batch_tr_tpl" info="${fns:fy('模板')}">
		<tr class="purchase_content">
			<td>
				<input name="name" id="{{d.nameId}}" type="text" htmlEscape="false" maxlength="64" class="input-large input-xfat" /></br>
			</td>
			<td>
				<input name="model" id="{{d.modelId}}" type="text" htmlEscape="false" maxlength="64" class="input-medium input-xfat" /></br>
			</td>
			<td>
				<input name="brand" type="text" htmlEscape="false" htmlEscape="false" maxlength="64" class="input-small input-xfat"/></br>
			</td>
			<td>
				<input name="amount" id="{{d.amountId}}" type="text" htmlEscape="false" maxlength="9" class="input-small input-xfat" /></br>
			</td>
			<td>
				<input name="priceRequirement" type="text" htmlEscape="false" maxlength="12" class="input-small input-xfat" /></br>
			</td>
			<td>
				<input name="unit" id="{{d.unitId}}" type="text" htmlEscape="false" maxlength="64" class="input-small input-xfat" /></br>
			</td>
			<td>
				<input name="purchaseRemark" type="text" htmlEscape="false" maxlength="64" class="input-large input-xfat" /></br>
			</td>
			<td>
				<input type="button" name="delButton" class="sui-btn btn-bordered btn-danger" value="${fns:fy('删除')}" />
			</td>
		</tr>
	</script>
</body>
</html>
