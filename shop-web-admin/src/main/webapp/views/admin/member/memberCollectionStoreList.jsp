<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>收藏店铺管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberCollectionStoreList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">收藏店铺列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 收藏店铺列表</a></li>
				<shiro:hasPermission name="member:memberCollectionStore:edit"><li class=""><a href="${ctxa}/member/memberCollectionStore/save1.do" > <i class="fa fa-user"></i> 收藏店铺添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>收藏店铺管理是xxx</li>
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
						<shiro:hasPermission name="member:memberCollectionStore:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/member/memberCollectionStore/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/member/memberCollectionStore/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="collectionStoreId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${memberCollectionStore.collectionStoreId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="mId"  maxlength="19" class="form-control input-sm" placeholder="关联(会员表)" value="${memberCollectionStore.MId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="storeId"  maxlength="19" class="form-control input-sm" placeholder="关联(店铺表)" value="${memberCollectionStore.storeId}"/>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionStore.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionStore.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionStore.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionStore.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
						<th>关联(会员表)</th>
						<th>关联(店铺表)</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="member:memberCollectionStore:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="memberCollectionStore">
					<tr>
						<td><a href="${ctxa}/member/memberCollectionStore/edit1.do?collectionStoreId=${memberCollectionStore.collectionStoreId}">${memberCollectionStore.collectionStoreId}</a></td>
						<td>${memberCollectionStore.MId}</td>
						<td>${memberCollectionStore.storeId}</td>
						<td><fmt:formatDate value="${memberCollectionStore.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${memberCollectionStore.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="member:memberCollectionStore:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/member/memberCollectionStore/edit1.do?collectionStoreId=${memberCollectionStore.collectionStoreId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/member/memberCollectionStore/delete.do?collectionStoreId=${memberCollectionStore.collectionStoreId}">
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