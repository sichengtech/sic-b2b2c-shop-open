<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("文件管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteFileManageList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("文件列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("文件列表")}</a></li>
				<shiro:hasPermission name="site:siteFileManage:save"><li class=""><a href="${ctxa}/site/siteFileManage/save1.do" > <i class="fa fa-user"></i> ${fns:fy("文件添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.文件管理.操作提示3")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteFileManage:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteFileManage/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteFileManage/list.do" method="get" id="searchForm">
					<div class="col-md-offset-5 col-sm-2">
						<select name="category" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("所属分类")}--</option>
							<c:forEach items="${fns:getDictList('site_file_category')}" var="item">
							<option value="${item.value}" ${item.value==siteFileManage.category?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control tooltips input-sm" id="" placeholder="${fns:fy("文件名")}" value="${siteFileManage.name}" name="name" maxlength="20">
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("文件分类")}</th>
						<th>${fns:fy("文件原名")}</th>
						<th>${fns:fy("文件地址")}</th>
						<th>${fns:fy("预览")}</th>
						<th>${fns:fy("备注")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteFileManage">
					<tr>
						<td>${fns:getDictLabel(siteFileManage.category, 'site_file_category', '')}</td>
						<td>${siteFileManage.name}</td>
						<td>
							<textarea class="form-control input-sm" rows="2" cols="5" readonly="readonly">${siteDomain}${ctxfs}${siteFileManage.path}</textarea>
						</td>
						<td>
							<a href="${ctxfs}${siteFileManage.path}" target="_black">
								<c:choose>
									<c:when test="${siteFileManage.suffix eq 'xls' || siteFileManage.suffix eq 'xlsx'}">
										<img alt="" src="${ctxStatic}/images/excel-logo.jpg" style="height:30px;height: 30px; "/>
									</c:when>
									<c:when test="${siteFileManage.suffix eq 'pdf'}">
										<img alt="" src="${ctxStatic}/images/pdf-logo.png" style="height:30px;height: 30px; "/>
									</c:when>
									<c:when test="${siteFileManage.suffix eq 'doc' || siteFileManage.suffix eq 'docx'}">
										<img alt="" src="${ctxStatic}/images/word-logo.png" style="height:30px;height: 30px; "/>
									</c:when>
									<c:otherwise>
										<img alt="" src="${ctxfs}${siteFileManage.path}@!30x30" style="height:30px;height: 30px; "/>
									</c:otherwise>
								</c:choose>
							</a>
						</td>
						<td>
							<textarea class="form-control input-sm" rows="2" cols="5" readonly="readonly">${siteFileManage.remarks}</textarea>
						</td>
						<td>
							<shiro:hasPermission name="site:siteFileManage:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteFileManage/edit1.do?sfId=${siteFileManage.sfId}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteFileManage:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteFileManage/delete.do?sfId=${siteFileManage.sfId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr style="height: 200px;">
							<td colspan="5">${fns:fy("很遗憾，暂无数据")}</td>
						</tr>
					</c:if>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>