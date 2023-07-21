<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>网站首页楼层管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteIndexFloorList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">网站首页楼层列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 网站首页楼层列表</a></li>
				<shiro:hasPermission name="site:siteIndexFloor:save"><li class=""><a href="${ctxa}/site/siteIndexFloor/save1.do" > <i class="fa fa-user"></i> 网站首页楼层添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>该列表用于显示商城首页楼层形式内容模板情况</li>
					<li>根据相关提示操作，建立所需要的楼层基本数据</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteIndexFloor:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/site/siteIndexFloor/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<%-- <form action="${ctxa}/site/siteIndexFloor/list.do" method="get" id="searchForm">
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> 
				</form> --%> 
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th></th> 
						<th>名称</th>
						<th>色彩风格</th>
						<th>显示</th>
						<th>导航文字</th>
						<th>导航图片</th>
						<th>排序</th>
						<th>操作</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="siteIndexFloor">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${siteIndexFloor.name}</td>
						<td>${siteIndexFloor.color}</td>
						<td>${fns:getDictLabel(siteIndexFloor.isDisplay, 'show_hide', '')}</td>
						<td>${siteIndexFloor.text}</td>
						<td>
							<c:if test="${siteIndexFloor.imgPath !=null}">
							 <a href="${ctxfs}${siteIndexFloor.imgPath}" target="_blank">
								<image width="120" height="80" src="${ctxfs}${siteIndexFloor.imgPath}@!120x80" onerror="fdp.defaultImage('${ctxStatic}/images/noimg.jpg');"/>
							 </a>
							</c:if>
							<c:if test="${siteIndexFloor.imgPath == null}">无</c:if>
						</td>
						<td>${siteIndexFloor.sort}</td>
						<td>
							<shiro:hasPermission name="site:siteIndexFloor:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteIndexFloor/edit1.do?id=${siteIndexFloor.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteIndexFloor:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteIndexFloor/delete.do?id=${siteIndexFloor.id}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
							</shiro:hasPermission>
						 </td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<p datano="0" columnno="0" class="dt-grid-cell">楼层类型 : ${siteIndexFloor.type}</p>
							<p datano="0" columnno="1" class="dt-grid-cell">模板风格 : ${siteIndexFloor.style}</p>
							<p datano="0" columnno="2" class="dt-grid-cell">更新时间 : <fmt:formatDate value="${siteIndexFloor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </p>
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