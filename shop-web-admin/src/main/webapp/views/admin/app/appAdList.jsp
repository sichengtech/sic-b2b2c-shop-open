<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("App引导页管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/app/appAdList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("App引导页列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:"> <i class="fa fa-user"></i> ${fns:fy("App引导页列表")}</a></li>
				<shiro:hasPermission name="app:appAd:save">
<%--					<li class=""><a href="${ctxa}/app/appAd/save1.do" > <i class="fa fa-user"></i> App引导页添加</a></li>--%>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("App版本.App引导页.操作提示1")}</li>
					<li>${fns:fy("App版本.App引导页.操作提示2")}</li>
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
						<shiro:hasPermission name="app:appAd:save">
<%--						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/app/appAd/save1.do"><i class="fa fa-plus"></i></a>--%>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/app/appAd/list.do" method="get" id="searchForm">
					<div class="col-sm-7"></div>
					<div class="col-sm-2">
						<select name="isShow" class="form-control m-bot15 input-sm">
							<option value=""}>--${fns:fy("请选择显示或隐藏")}--</option>
							<c:forEach items="${fns:getDictList('hot_search_word_show')}" var="item">
								<option value="${item.value}" ${item.value==appAd.isShow?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>

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
						<th>${fns:fy("页码")}</th>
						<th>${fns:fy("背景色")}</th>
						<th>${fns:fy("背景图")}</th>
						<th>${fns:fy("是否展示")}</th>
						<th>${fns:fy("文本")}1</th>
						<th>${fns:fy("文本")}2</th>
						<th>${fns:fy("文本")}3</th>
						<th>${fns:fy("按钮文字")}</th>
						<th>${fns:fy("修改日期")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="appAd">
					<tr>
						<td><a href="${ctxa}/app/appAd/edit1.do?id=${appAd.id}">${appAd.id}</a></td>
						<td style="color:${appAd.backgroundColor}">${appAd.backgroundColor}</td>
						<td><img class="preview" width="68" height="146" src="${ctxfs}${appAd.backgroundImage}@!68X146" style="${not empty appAd.backgroundImage?'':'display:none;'}"/></td>
						<td>${fns:getDictLabel(appAd.isShow, 'hot_search_word_show', '')}</td>
						<td style="color:${appAd.wordOneColor}">${appAd.wordOne}</td>
						<td style="color:${appAd.wordTwoColor}">${appAd.wordTwo}</td>
						<td style="color:${appAd.wordThreeColor}">${appAd.wordThree}</td>
						<td style="color:${appAd.buttonColour}">${appAd.buttonWord}</td>
						<td><fmt:formatDate value="${appAd.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<shiro:hasPermission name="app:appAd:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/app/appAd/edit1.do?id=${appAd.id}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="app:appAd:drop">
<%--							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/app/appAd/delete.do?id=${appAd.id}">--%>
<%--								<i class="fa fa-trash-o"></i> 删除--%>
<%--							</button>--%>
							</shiro:hasPermission>
						</td>
					</tr>
					</c:forEach>
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