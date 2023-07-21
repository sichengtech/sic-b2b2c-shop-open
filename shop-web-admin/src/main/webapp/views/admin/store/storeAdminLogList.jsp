<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺管理员操作日志管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAdminLogList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">店铺管理员操作日志列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 店铺管理员操作日志列表</a></li>
				<shiro:hasPermission name="store:storeAdminLog:edit"><li class=""><a href="${ctxa}/store/storeAdminLog/save1.do" > <i class="fa fa-user"></i> 店铺管理员操作日志添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺管理员操作日志管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
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
						<shiro:hasPermission name="store:storeAdminLog:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeAdminLog/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeAdminLog/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="logId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${storeAdminLog.logId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" placeholder="关联(卖家表id)" value="${storeAdminLog.sellerId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="type"  maxlength="1" class="form-control input-sm" placeholder="日志类型" value="${storeAdminLog.type}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="title"  maxlength="512" class="form-control input-sm" placeholder="日志标题（操作菜单）" value="${storeAdminLog.title}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="remoteAddr"  maxlength="255" class="form-control input-sm" placeholder="操作用户的IP地址" value="${storeAdminLog.remoteAddr}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="userAgent"  maxlength="255" class="form-control input-sm" placeholder="操作用户代理信息" value="${storeAdminLog.userAgent}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="requestUri"  maxlength="255" class="form-control input-sm" placeholder="操作的URI" value="${storeAdminLog.requestUri}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="method"  maxlength="64" class="form-control input-sm" placeholder="操作的方式(提交方式)" value="${storeAdminLog.method}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="params"  class="form-control input-sm" placeholder="操作提交的数据" value="${storeAdminLog.params}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="exception"  class="form-control input-sm" placeholder="异常信息" value="${storeAdminLog.exception}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="createBy.id"  maxlength="19" class="form-control input-sm" placeholder="创建者（操作用户id）" value="${storeAdminLog.createBy.id}"/>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAdminLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAdminLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAdminLog.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeAdminLog.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>主键</th>
						<th>关联(卖家表id)</th>
						<th>日志类型</th>
						<th>日志标题（操作菜单）</th>
						<th>操作用户的IP地址</th>
						<th>操作用户代理信息</th>
						<th>操作的URI</th>
						<th>操作的方式(提交方式)</th>
						<th>操作提交的数据</th>
						<th>异常信息</th>
						<th>创建者（操作用户id）</th>
						<th>创建时间（操作时间）</th>
						<th>更新时间</th>
						<shiro:hasPermission name="store:storeAdminLog:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeAdminLog">
					<tr>
						<td><a href="${ctxa}/store/storeAdminLog/edit1.do?logId=${storeAdminLog.logId}">${storeAdminLog.logId}</a></td>
						<td>${storeAdminLog.sellerId}</td>
						<td>${storeAdminLog.type}</td>
						<td>${storeAdminLog.title}</td>
						<td>${storeAdminLog.remoteAddr}</td>
						<td>${storeAdminLog.userAgent}</td>
						<td>${storeAdminLog.requestUri}</td>
						<td>${storeAdminLog.method}</td>
						<td>${storeAdminLog.params}</td>
						<td>${storeAdminLog.exception}</td>
						<td>${storeAdminLog.createBy.id}</td>
						<td><fmt:formatDate value="${storeAdminLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${storeAdminLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeAdminLog:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeAdminLog/edit1.do?logId=${storeAdminLog.logId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeAdminLog/delete.do?logId=${storeAdminLog.logId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						</td>
						</shiro:hasPermission>
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