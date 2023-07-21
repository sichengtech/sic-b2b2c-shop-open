<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title></title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseConsultationForm.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty purchaseConsultation.pcId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}&nbsp;${fns:fy('采购咨询')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchaseConsultation/list.do"> <i class="fa fa-user"></i> ${fns:fy('采购咨询列表')}</a></li>
				<shiro:hasPermission name="purchase:purchaseConsultation:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('采购咨询')} &nbsp;${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('采购咨询.咨询表单.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchaseConsultation/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pcId" value="${purchaseConsultation.pcId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('用户id')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name=""  maxlength="19" class="form-control input-sm" value="${purchaseConsultation.UId}" readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('商品id')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name=""  maxlength="19" class="form-control input-sm" value="${purchaseConsultation.PId}"  readonly="readonly"/>

							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('描述')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name=""  maxlength="500" class="form-control input-sm" value="${purchaseConsultation.purchaseDescribe}"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('数量')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name=""  maxlength="10" class="form-control input-sm" value="${purchaseConsultation.quantity}"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('联系方式')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name=""  maxlength="64" class="form-control input-sm" value="${purchaseConsultation.contactInfo}"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('是否联系')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${purchaseConsultation.isContact eq 0?"":"checked"} data-size="small" name="isContact" value="1" style="display: none" data-on-text="${fns:fy('是')}" data-off-text="${fns:fy('否')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请选择是否联系')}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="purchase:purchaseConsultation:edit">
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