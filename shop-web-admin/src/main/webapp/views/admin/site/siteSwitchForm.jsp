<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("站点开关管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSwitchForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteSwitch.id?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("站点开关")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<%-- <li class=""><a href="${ctxa}/site/siteSwitch/list.do"> <i class="fa fa-user"></i> 站点开关列表</a></li> --%>
				<shiro:hasPermission name="site:siteSwitch:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("站点开关")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.站点开关.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteSwitch/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteSwitch.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否开放网站")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="isOpen" value="1" ${"1"==siteSwitch.isOpen?"checked":""} 
								data-size="small" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.站点开关.操作提示2")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("网站关闭消息")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="msg" maxlength="1024" class="form-control input-sm" placeholder="" rows="5" data-parsley-id="8">${siteSwitch.msg}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.站点开关.操作提示3")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteSwitch:edit">
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