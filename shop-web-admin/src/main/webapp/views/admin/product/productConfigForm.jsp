<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品设置')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productConfigForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('商品设置')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('商品设置')}</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('商品管理.商品设置.操作提示1')}</li>
					<li>${fns:fy('商品管理.商品设置.操作提示2')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/> 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productConfig/edit2.do" method="post">
						<input type="hidden" name="id" value="${productConfig.id}">

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('是否审核')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="set1" value="1" ${"1"==productConfig.set1?"checked":""} 
								data-size="small" style="display: none" data-on-text="${fns:fy('开启')}" data-off-text="${fns:fy('关闭')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('新发商品是否需要审核')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('禁售商品配置')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="col-sm-4 icheck " style="padding-left:0px;">
									<div class="square-blue single-row">
										<label class="checkbox-inline">
											<input type="radio" name="set2" value="1" ${productConfig.set2=='1'?"checked":""} style="display: none" data-switch-no-init/>${fns:fy('配置')}1
										</label>
									</div>
								</div>
								<div class="col-sm-4 icheck " style="padding-left:0px;">
									<div class="square-blue single-row">
										<label class="checkbox-inline">
											<input type="radio" name="set2" value="2" ${productConfig.set2=='2'?"checked":""} style="display: none" data-switch-no-init/>${fns:fy('配置')}2
										</label>
									</div>
								</div>
								<div class="col-sm-4 icheck " style="padding-left:0px;">
									<div class="square-blue single-row">
										<label class="checkbox-inline">
											<input type="radio" name="set2" value="3" ${productConfig.set2=='3'?"checked":""} style="display: none" data-switch-no-init/>${fns:fy('配置')}3
										</label>
									</div>
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('配置')}1:${fns:fy('禁售的商品不允许再编辑，再售出')}<p>
								<p class="help-block">${fns:fy('配置')}2:${fns:fy('禁售的商品可以编辑，但编辑后必须由管理员审核，审核通过后可以再次售出')}<p>
								<p class="help-block">${fns:fy('配置')}3:${fns:fy('禁售的商品可以编辑，根据商品审核开关判断是否需要管理员审核')}<p>
							</div>
						</div>
						<shiro:hasPermission name="product:productConfig:edit">						
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')} 
								</button>
								<shiro:hasPermission name="product:productConfig:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
						</shiro:hasPermission>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>