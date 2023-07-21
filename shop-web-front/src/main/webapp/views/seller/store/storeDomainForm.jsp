<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺二级域名')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="4"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeDomainForm.js"></script>
<script type="text/javascript">
	var domainForbiddenWords = '${sysVariable3.valueClob}';
</script>
<!--表示使用N号二级菜单 -->
<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('二级域名')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('店铺')}</li>
						<li>${fns:fy('店铺管理')}</li>
						<li class="active">${fns:fy('二级域名')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('修改和查看自己店铺的二级域名。')}</li>
								<li>${fns:fy('每月允许修改:')}${sysVariable2.value}${fns:fy('次。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/store/storeDomain/save2.htm">
					<input type="hidden" name="storeId" value="${storeDomain.storeId}">
					<dd>
						<div class="control-group">
							<label class="control-label">${fns:fy('默认二级域名：')}</label>
								<span>
									shop${storeDomain.storeId}.${siteDomain}
								</span>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('店铺默认二级域名。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺二级域名：')}</label>
								<span>
									<input type="hidden" id="oldDomain" name="oldDomain" value="${storeDomain.domain}">
									<input type="text" style="width: 200px" id="domain" class="input-xlarge" name="domain" value="${storeDomain.domain}"> .${siteDomain}
								</span>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入店铺二级域名。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('域名修改次数：')}</label>
							${storeDomain.modifyCount}${fns:fy('次')}
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('当月域名修改次数')}</div>
							</div>
						</div>
						<shiro:hasPermission name="store:storeDomain:edit">
						<div class="text-align pb20">
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
						</div>
						</shiro:hasPermission>
					</dd>
				</form>
			</dl>
		</div>
	</div>
</body>
</html>