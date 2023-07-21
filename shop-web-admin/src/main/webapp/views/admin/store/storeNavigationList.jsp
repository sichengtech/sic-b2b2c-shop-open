<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺导航管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeNavigationList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">店铺导航列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 店铺导航列表</a></li>
				<shiro:hasPermission name="store:storeNavigation:edit"><li class=""><a href="${ctxa}/store/storeNavigation/save1.do" > <i class="fa fa-user"></i> 店铺导航添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺导航管理是xxx</li>
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
						<shiro:hasPermission name="store:storeNavigation:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeNavigation/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeNavigation/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="storeNavigationId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${storeNavigation.storeNavigationId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="navNumber"  maxlength="10" class="form-control input-sm" placeholder="编号" value="${storeNavigation.navNumber}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" placeholder="关联(卖家表)" value="${storeNavigation.sellerId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="导航名称" value="${storeNavigation.name}"/>
					</div>
					<div class="col-sm-1">
						<c:forEach items="${fns:getDictList('yes_no')}" var="item">
						<label class="checkbox-inline">
						<input type="radio" name="isOpen" value="${item.value}" ${item.value==storeNavigation.isOpen?"checked":""}/> ${item.label}
						</label>
						</c:forEach>
					</div>
					<div class="col-sm-1">
						<input type="text" name="sort"  maxlength="10" class="form-control input-sm" placeholder="排序" value="${storeNavigation.sort}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="action"  maxlength="10" class="form-control input-sm" placeholder="动作，字典" value="${storeNavigation.action}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="url"  maxlength="255" class="form-control input-sm" placeholder="目标连接" value="${storeNavigation.url}"/>
					</div>
					<div class="col-sm-1">
						<select name="target" class="form-control m-bot15 input-sm">
							<option value="">--请选择--</option>
							<c:forEach items="${fns:getDictList('target')}" var="item">
							<option value="${item.value}" ${item.value==storeNavigation.target?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeNavigation.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeNavigation.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeNavigation.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeNavigation.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
						<th>编号</th>
						<th>关联(卖家表)</th>
						<th>导航名称</th>
						<th>是否开启(0.是 1否)</th>
						<th>排序</th>
						<th>动作，字典</th>
						<th>目标连接</th>
						<th>窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="store:storeNavigation:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeNavigation">
					<tr>
						<td><a href="${ctxa}/store/storeNavigation/edit1.do?storeNavigationId=${storeNavigation.storeNavigationId}">${storeNavigation.storeNavigationId}</a></td>
						<td>${storeNavigation.navNumber}</td>
						<td>${storeNavigation.sellerId}</td>
						<td>${storeNavigation.name}</td>
						<td>${fns:getDictLabel(storeNavigation.isOpen, 'yes_no', '')}</td>
						<td>${storeNavigation.sort}</td>
						<td>${storeNavigation.action}</td>
						<td>${storeNavigation.url}</td>
						<td>${fns:getDictLabel(storeNavigation.target, 'target', '')}</td>
						<td><fmt:formatDate value="${storeNavigation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${storeNavigation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeNavigation:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeNavigation/edit1.do?storeNavigationId=${storeNavigation.storeNavigationId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeNavigation/delete.do?storeNavigationId=${storeNavigation.storeNavigationId}">
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