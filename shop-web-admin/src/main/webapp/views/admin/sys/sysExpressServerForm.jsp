<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("快递通道管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysExpressServerForm.js"></script>
<style>
	.test-express{display:none}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysExpressServer.sesId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑快递通道"):fns:fy("添加快递通道")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<%-- <li class=""><a href="${ctxa}/sys/sysExpressServer/list.do"> <i class="fa fa-user"></i> 快递通道列表</a></li> --%>
				<shiro:hasPermission name="sys:sysExpressServer:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("快递通道编辑"):fns:fy("快递通道添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("快递通道管理")}</li>
					<li>${fns:fy("系统设置.快递通道.操作提示1")}</li>
					<li>${fns:fy("系统设置.快递通道.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysExpressServer/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="sesId" value="${sysExpressServer.sesId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("开关")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="status" value="1" ${"1"==sysExpressServer.status?"checked":""} 
								data-size="small" style="display: none" data-on-text="${fns:fy("启用")}" data-off-text="${fns:fy("停用")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，关闭后无法发出快递信息")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("电商id")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ebusinessId"  maxlength="64" class="form-control input-sm" value="${sysExpressServer.ebusinessId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写电商id")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("电商加密私钥")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="appkey"  maxlength="64" class="form-control input-sm" value="${sysExpressServer.appkey}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写电商加密私钥")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("请求url")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="url"  maxlength="128" class="form-control input-sm" value="${sysExpressServer.url}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写请求url")}<p>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("快递测试")}&nbsp;:</label>
							<div class="col-sm-5">
								<a class="btn btn-warning btn-sm test-express-btn" href="javascript:void(0);">
								<i class="fa fa-stethoscope"></i> ${fns:fy("测试快递")}
								</a> 
							</div>
							<div class="col-sm-2 test-express">
								<input id="companyNumber" class="form-control input-sm" type="text" placeholder="${fns:fy("快递公司编号")}" value="YTO">
							</div>
							<div class="col-sm-2 test-express">
								<input id="expressNumber" class="form-control input-sm" type="text" placeholder="${fns:fy("运单号")}" value="YT4234068948765">
							</div>
							<div class="col-sm-1 test-express">
								<a id="send" class="btn btn-warning btn-sm">
									<i class="fa fa-stethoscope"></i> ${fns:fy("测试")}
								</a>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("使用上面的快递网关发出一个测试快递")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sys:sysExpressServer:edit">
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