<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('计量单位管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productUnitForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productUnit.id?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${isEdit?edit:save}${fns:fy('计量单位')}</h4>
			<%@ include  file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productUnit/list.do"> <i class="fa fa-user"></i> ${fns:fy('计量单位列表')}</a></li>
				<shiro:hasPermission name="product:productUnit:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('计量单位')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始  -->
		<div class="panel-body">
			<!-- 提示开始  -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('计量单位.计量单位添加.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productUnit/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${productUnit.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${productUnit.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写名称')}<p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 首字母&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="firstLetter"  maxlength="64" class="form-control input-sm" value="${productUnit.firstLetter}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写首字母<p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('排序')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${productUnit.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写排序')}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="product:productUnit:edit">
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
		<!-- panel-body结束  -->
	</section>
	<!-- panel结束 -->
</body>
</html>