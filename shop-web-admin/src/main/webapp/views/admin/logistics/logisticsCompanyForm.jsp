<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('快递公司管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/logistics/logisticsCompanyForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${not empty logisticsCompany.lcId?edit:save}${fns:fy('快递公司')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/logistics/logisticsCompany/list.do"> <i class="fa fa-user"></i> ${fns:fy('快递公司列表')}</a></li>
				<shiro:hasPermission name="${not empty logisticsCompany.lcId?'logistics:logisticsCompany:edit':'logistics:logisticsCompany:save'}"><li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('快递公司')}${not empty logisticsCompany.lcId?edit:save}</a></li></shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('快递公司管理.快递公司添加.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/logistics/logisticsCompany/${not empty logisticsCompany.lcId?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="lcId" value="${logisticsCompany.lcId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('公司名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyName" maxlength="32" class="form-control input-sm" value="${logisticsCompany.companyName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写公司名称')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('公司编号')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyNumber" maxlength="32" class="form-control input-sm" value="${logisticsCompany.companyNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写公司编号')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('公司网址')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="companyurl" maxlength="32" class="form-control input-sm" value="${logisticsCompany.companyurl}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写公司网址')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<input name="status" type="checkbox" value="1" ${logisticsCompany.status eq 0?"":"checked"}
								 data-size="small" style="display: none" data-on-text="${fns:fy('启用')}" data-off-text="${fns:fy('禁用')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择状态')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('常用快递')}&nbsp;:</label>
							<div class="col-sm-5">
								<input name="isCommonUse" type="checkbox" value="1" ${logisticsCompany.isCommonUse eq 0?"":"checked"}
								 data-size="small" style="display: none" data-on-text="${fns:fy('是')}" data-off-text="${fns:fy('否')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择是否是常用快递')}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')} 
								</button>
								<shiro:hasPermission name="${not empty logisticsCompany.lcId?'logistics:logisticsCompany:edit':'logistics:logisticsCompany:save'}">
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