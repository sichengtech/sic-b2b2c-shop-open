<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("日志列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysLogList.js"></script>
<!-- 引入iCheck文件-->
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("日志列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javascript:;"> <i class="fa fa-home"></i> ${fns:fy("日志列表")}</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.日志查询.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-2">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy("查询")}"><i class="fa fa-search"></i></button>
					</div>
				</div>
				<div class="col-sm-2">
				</div>
				<div class="col-sm-2">
				</div>
				<div class="col-sm-2">
				</div>
				<div class="col-sm-1">
				</div>
				<div class="col-sm-3">
				</div>
			</div>
			<!-- 按钮结束--> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
					<thead> 
						<tr>
							<th>${fns:fy("操作用户")}</th>
							<th>URI</th>
							<th>${fns:fy("提交方式")}</th>
							<th>${fns:fy("操作者IP")}</th>
							<th>${fns:fy("操作时间")}</th>
						</tr>
					</thead> 
					<tbody>
						<c:forEach items="${page.list}" var="log">
							<tr>
								<td>${log.user.loginName}</td>
								<td>${log.requestUri}</td>
								<td>${log.method}</td>
								<td>${log.remoteAddr}</td>
								<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
							</tr>
							<c:if test="${not empty log.exception}">
							<tr>
								<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
										${fns:fy("异常信息")}: <br/>
									${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
	</section>
	<!-- panel结束 -->
	<!-- 开始快速查询窗口 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
			<div class="modal-content">	 
				<form id="" method="get" action="${ctxa}/sys/log.do" class="searchForm"> 
					<div class="modal-body form-horizontal adminex-form">
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">${fns:fy("操作用户")}：</label>
							<div class="col-sm-8">
								<input maxlength="50" type="text" class="form-control input-sm searchInput" id="eq_ordersSnStr" name="createBy.name" value="${log.createBy.name}" placeholder="${fns:fy("请输入操作用户")}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">${fns:fy("操作菜单")}：</label>
							<div class="col-sm-8">
								<input maxlength="50" type="text" class="form-control input-sm searchInput" id="eq_ordersSnStr" name="title" value="${log.title}" placeholder="${fns:fy("请输入操作菜单")}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">URI：</label>
							<div class="col-sm-8">
								<input maxlength="50" type="text" class="form-control input-sm searchInput" id="eq_ordersSnStr" name="requestUri" value="${log.requestUri}" placeholder="${fns:fy("请输入URI")}">
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy("日期范围")}：</label>
							<div class="col-sm-4">
								<div class="input-group"> 
									<input type="text" id="" name="beginCreateDate" value="<fmt:formatDate value="${log.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="form-control input-sm searchInput" format="yyyy-MM-dd HH:mm:ss" placeholder="${fns:fy("请选择日期开始时间")}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="input-group"> 
									<input type="text" id="" name="endCreateDate" value="<fmt:formatDate value="${log.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="form-control input-sm searchInput" format="yyyy-MM-dd HH:mm:ss" placeholder="${fns:fy("请选择日期结束时间")}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">${fns:fy("只查询异常信息")}：</label>
							<div class="col-sm-4">
								<input class="searchCheckbox" name="type" type="checkbox" ${log.type eq '2'?' checked':''} value="2"/>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="(function(){layer.closeAll('page');}())">
							<i class="fa fa-times"></i> ${fns:fy("关闭")}
						</button>
						<button type="button" id="resetParam" class="btn btn-warning" onclick="(function(){$('.searchInput').attr('value',''); $('.searchCheckbox').removeAttr('checked');}())">
							<i class="fa fa-reply"></i> ${fns:fy("参数重置")}
						</button> 
						<button type="submit" class="btn btn-info">
							<i class="fa fa-search"></i> ${fns:fy("执行查询")}
						</button> 
					</div> 
				</form> 
		</div>
	</div>
	<!-- 结束快速查询模态窗口 --> 
</body>
</html>