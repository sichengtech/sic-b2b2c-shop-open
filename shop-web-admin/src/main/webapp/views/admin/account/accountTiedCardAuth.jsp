<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("绑卡审核")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountTiedCardAuth.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("绑卡审核")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/account/accountTiedCard/list.do"> <i class="fa fa-user"></i> ${fns:fy("绑卡列表")}</a></li>
				<shiro:hasPermission name="account:accountTiedCard:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("绑卡审核")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("对绑卡进行审核")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/account/accountTiedCard/auth2.do" method="post">
						<input type="hidden" name="tiedCardId" value="${accountTiedCard.tiedCardId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("审核状态：")}</label>
							<div class="col-sm-5">
								<select name="auditStatus" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('account_audit_status')}" var="item">
									<option value="${item.value}" ${item.value==accountTiedCard.auditStatus?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择审核状态")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("审核理由：")}</label>
							<div class="col-sm-5">
								<textarea class="form-control" rows="5" name="auditOpinion" >${accountTiedCard.auditOpinion}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写审核理由")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="account:accountTiedCard:auth">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("审核")}
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