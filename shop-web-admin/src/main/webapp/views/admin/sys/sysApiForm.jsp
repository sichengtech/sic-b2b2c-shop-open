<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("接口管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysApiForm.js"></script>
<script type="text/javascript">
	var apiParamList=${apiParamList};
</script>
<style type="text/css">
	.cmxform .form-group label.error{display: inherit;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right">
			<c:set var="isEdit" value ="${not empty sysApi.apiId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑接口"):fns:fy("添加接口")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysApi/list.do"> <i class="fa fa-user"></i> ${fns:fy("接口列表")}</a></li>
				<shiro:hasPermission name="sys:sysApi:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("接口编辑"):fns:fy("接口添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.接口测试.操作提示5")}</li>
					<li>${fns:fy("系统设置.接口测试.操作提示6")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysApi/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="apiId" value="${sysApi.apiId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("所属分类")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="apiCategory" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('sys_api_category')}" var="item">
									<option value="${item.value}" ${item.value==sysApi.apiCategory?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必选项，请选择所属分类")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("接口名")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="apiName"  maxlength="64" class="form-control input-sm" value="${sysApi.apiName}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写接口名")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("接口版本号")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="apiVersion" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('sys_api_version')}" var="item">
									<option value="${item.value}" ${item.value==sysApi.apiVersion?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必选项，请填写接口版本号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("接口地址")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="apiUrl"  maxlength="256" class="form-control input-sm" value="${sysApi.apiUrl}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写接口地址")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("接口描述")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="apiDescribe"  maxlength="64" class="form-control input-sm" value="${sysApi.apiDescribe}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写接口描述")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("设置参数")}&nbsp;:</label>
							<div class="col-sm-7">
								<table class="table table-hover table-condensed table-bordered api-param-table">
									<thead>
										<tr>
											<th>${fns:fy("参数名")}</th>
											<th>${fns:fy("参数类型")}</th>
											<th>${fns:fy("是否必填")}</th>
											<th>${fns:fy("参数描述")}</th>
											<th>${fns:fy("操作")}</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="5">
												<button type="button" class="btn btn-info add-param"><i class="fa fa-plus"></i> ${fns:fy("添加")}</button>
											</td>
										</tr>	
									</tfoot>
								</table>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sys:sysApi:edit">
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
	<script type="text/template" id="api_param_Tpl" info="${fns:fy("接口参数模板")}">
		<tr>
			<td>
				<input type="text" name="paramName" id="{{d.paramNameId}}" class="form-control input-sm" value="{{d.paramName}}"/>
			</td>
			<td>
				<c:set var="typeaa" value='{{d.paramType}}'/>
				<select name="type" class="form-control m-bot15 input-sm">
					<c:forEach items="${fns:getDictList('sys_api_param_type')}" var="item">
						<option value="${item.value}" type="{{d.paramType}}">${item.label}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select name="isRequired" class="form-control m-bot15 input-sm">
					<c:forEach items="${fns:getDictList('yes_no')}" var="item">
					<option value="${item.value}"} type="{{d.isRequired}}">${item.label}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="text" name="paramDescribe" id="{{d.paramDescribeId}}" maxlength="64" class="form-control input-sm" value="{{d.paramDescribe}}"/>
			</td>
			<td>
				<button type="button" class="btn btn-danger delete-param"><i class="fa fa-trash-o"></i> ${fns:fy("删除")}</button>
			</td>
		</tr>
	</script>
</body>
</html>