<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('审核采购单')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseForm.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('审核采购单')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchase/list.do"> <i class="fa fa-user"></i> ${fns:fy('采购单列表')}</a></li>
				<shiro:hasPermission name="purchase:purchase:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('采购单审核')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('审核中的采购单下的明细不在前台显示')}</li>
					<li>${fns:fy('审核采购单，对审核中的采购单进行审核')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchase/auth2.do" method="post">
						<input type="hidden" name="type" value="${purchase.type}">
						<input type="hidden" name="purchaseId" value="${purchase.purchaseId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('采购单号')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchase.purchaseId}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('采购标题')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchase.title}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('采购内容')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="content" rows="6" class="form-control"  maxlength="2000" readonly="readonly">${purchase.content}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('审核结果')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${purchase.status eq 20?"":"checked"} data-size="small" name="authResult" value="1" style="display: none" data-on-text="${fns:fy('通过')}" data-off-text="${fns:fy('不通过')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择审核结果')}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="purchase:purchase:auth">
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