<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>页面导航管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%> 
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteNavForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteNav.id?true:false}"></c:set > 
			<h4 class="title">${isEdit?'编辑':'添加'}页面导航</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteNav/list.do"> <i class="fa fa-user"></i> 页面导航列表</a></li>
				<shiro:hasPermission name="${isEdit?'site:siteNav:edit':'site:siteNav:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 页面导航${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>系统内置三个导航区域，分别为顶部导航、中部主导航、底部导航，可以向这三个导航区域添加页面导航。</li>
					<li>可以修改导航的基本信息</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteNav/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteNav.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 导航名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name" maxlength="64" class="form-control input-sm" value="${siteNav.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写导航名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 显示位置&nbsp;:</label>
							<div class="col-sm-5">
								<select name="location" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_nav_location')}" var="item">
									<option value="${item.value}" ${item.value==siteNav.location?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请选择菜单显示位置<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 新窗口导航&nbsp;:</label>
							<div class="col-sm-5">
								<input name="isNewWindow" type="checkbox" value="1" ${siteNav.isNewWindow==1?"checked":""} data-size="small" style="display: none" data-on-text="新窗口" data-off-text="原窗口"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请选择是否在新窗口打开<p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 启用&nbsp;:</label>
							<div class="col-sm-5">
								<select name="isOpen" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('is_status')}" var="item">
									<option value="${item.value}" ${item.value==siteNav.isOpen?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写启用<p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 导航动作&nbsp;:</label>
							<div class="col-sm-2" style="padding-right: 0px;width:130px">
								<select name="action" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_nav_action')}" var="item">
									<option value="${item.value}" ${item.value==siteNav.action?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3" style="padding-left: 0px;width:339px">
								<input type="text" name="target" maxlength="255" class="form-control input-sm" value="${siteNav.target}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">打开连接：请输入目标的URL。搜索关键字：请输入一个词组。打开商品：请输入商品的ID。打开店铺：请输入店铺的ID。打开商品分类：请输入分类的ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort" maxlength="10" class="form-control input-sm" value="${siteNav.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 目标&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="target" maxlength="255" class="form-control input-sm" value="${siteNav.target}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写目标<p>
							</div>
						</div> --%>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="${isEdit?'site:siteNav:edit':'site:siteNav:save'}">
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