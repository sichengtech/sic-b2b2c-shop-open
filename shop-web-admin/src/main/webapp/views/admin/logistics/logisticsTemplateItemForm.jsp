<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>运费模板详情管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/logistics/logisticsTemplateItemForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty logisticsTemplateItem.ltiId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}运费模板详情</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/logistics/logisticsTemplateItem/list.do"> <i class="fa fa-user"></i> 运费模板详情列表</a></li>
				<shiro:hasPermission name="logistics:logisticsTemplateItem:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 运费模板详情${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>运费模板详情管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/logistics/logisticsTemplateItem/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="ltiId" value="${logisticsTemplateItem.ltiId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ltiId"  maxlength="19" class="form-control input-sm" value="${logisticsTemplateItem.ltiId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 运费模板id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="ltId"  maxlength="19" class="form-control input-sm" value="${logisticsTemplateItem.ltId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写运费模板id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 运送到&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="area" name="area.id" value="${logisticsTemplateItem.area.id}" labelName="area.name" labelValue="${logisticsTemplateItem.area.name}"
									title="区域" url="/sys/area/treeData.do" cssClass="required" allowClear="true" notAllowSelectParent="true"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写运送到<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 地区名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="areaName"  maxlength="64" class="form-control input-sm" value="${logisticsTemplateItem.areaName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写地区名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 首件(件)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="firstItem"  maxlength="10" class="form-control input-sm" value="${logisticsTemplateItem.firstItem}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写首件(件)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 首重(元)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="firstPrice"  maxlength="12" class="form-control input-sm" value="${logisticsTemplateItem.firstPrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写首重(元)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 续件(件)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="continueItem"  maxlength="10" class="form-control input-sm" value="${logisticsTemplateItem.continueItem}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写续件(件)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 续重(元)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="continuePrice"  maxlength="12" class="form-control input-sm" value="${logisticsTemplateItem.continuePrice}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写续重(元)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="logistics:logisticsTemplateItem:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
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