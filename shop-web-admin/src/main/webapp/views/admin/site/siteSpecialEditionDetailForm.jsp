<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("特版明细福管理管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSpecialEditionDetailForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteSpecialEditionDetail.seDetailId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑特版明细福管理"):fns:fy("添加特版明细福管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteSpecialEditionDetail/list.do"> <i class="fa fa-user"></i> ${fns:fy("特版明细福管理列表")}</a></li>
				<shiro:hasPermission name="site:siteSpecialEditionDetail:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("特版明细福管理编辑"):fns:fy("特版明细福管理添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.特版管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteSpecialEditionDetail/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="seDetailId" value="${siteSpecialEditionDetail.seDetailId}">
						<input type="hidden" name="seId" value="${siteSpecialEditionDetail.seId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("特版详情内容")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="content" rows="6" class="form-control"  >${siteSpecialEditionDetail.content}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写特版详情内容")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="9" class="form-control input-sm" value="${siteSpecialEditionDetail.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写排序")}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteSpecialEditionDetail:edit">
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