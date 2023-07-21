<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("特版管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSpecialEditionForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteSpecialEdition.seId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑特版管理"):fns:fy("添加特版管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteSpecialEdition/list.do"> <i class="fa fa-user"></i> ${fns:fy("特版管理列表")}</a></li>
				<shiro:hasPermission name="site:siteSpecialEdition:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("编辑特版管理"):fns:fy("添加特版管理")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.特版管理.操作提示4")}</li>
					<li>${fns:fy("网站设置.特版管理.操作提示5")}</li>
					<li>${fns:fy("网站设置.特版管理.操作提示6")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteSpecialEdition/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="seId" value="${siteSpecialEdition.seId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("编号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="oldNumber" name="oldNumber" type="hidden" value="${siteSpecialEdition.number}">
								<input type="text" name="number"  maxlength="64" class="form-control input-sm" value="${siteSpecialEdition.number}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写编号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("特版名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="seName"  maxlength="64" class="form-control input-sm" value="${siteSpecialEdition.seName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写特版名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("说明信息")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="info"  maxlength="255" class="form-control input-sm" value="${siteSpecialEdition.info}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写说明信息")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteSpecialEdition:edit">
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