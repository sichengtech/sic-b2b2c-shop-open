<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺二级域名管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeDomainForm.js"></script>
<script type="text/javascript">
	var domainForbiddenWords = '${sysVariable3.valueClob}';
</script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeDomain.id?true:false}"></c:set > 
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("店铺二级域名")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeDomain/list.do"> <i class="fa fa-user"></i> ${fns:fy("店铺二级域名列表")}</a></li>
				<shiro:hasPermission name="store:storeDomain:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("店铺二级域名")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.二级域名.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeDomain/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${storeDomain.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺名称")}&nbsp;:</label>
							<div class="col-sm-3">
								<input type="text" name="storeName" readonly="readonly" maxlength="64" class="form-control input-sm" value="${storeName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("默认二级域名")}&nbsp;:</label>
							<div class="col-sm-3">
								<input type="text" name="storeName" readonly="readonly" maxlength="64" class="form-control input-sm" value="shop${storeDomain.id}.${domain}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("二级域名")}&nbsp;:</label>
							<div class="col-sm-3">
								<input type="hidden" id="oldDomain" name="oldDomain" value="${storeDomain.domain}">
								<input id="domain" type="text" name="domain" maxlength="64" class="form-control input-sm" value="${storeDomain.domain}"/>
							</div>
							<label>.${domain}</label>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="store:storeDomain:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
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