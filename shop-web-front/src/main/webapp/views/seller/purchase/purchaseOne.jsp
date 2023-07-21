<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>${fns:fy('一句话发布采购')}</title>
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
				<!-- <dt>
                    <div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 一句话发布采购</div>
                    <i class="sui-icon icon-tb-list"></i> 一句话发布采购
                    <%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->
				
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('一句话发布采购')}</span>
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
						<li>${fns:fy('一句话发布采购')}</li>
					</ul>
				</dt>




				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('可以发布一句话进行采购。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/purchase/purchase/oneRelease2.htm">
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
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('一句话：')}</label>
							<textarea rows="10" cols="" name="content" class="input-xxlarge input-xfat" maxlength="255">${purchase.content}</textarea>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('一句话采购内容。')}</div>
							</div>
						</div>
						<shiro:hasPermission name="store:store:edit">
						<div class="text-align pb20">
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存2')}</button>
						</div>
						</shiro:hasPermission>
					</dd>
				</form>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseOne.js"></script>
</body>
</html>
