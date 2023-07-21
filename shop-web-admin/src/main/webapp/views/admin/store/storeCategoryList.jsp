<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺商品分类管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeCategoryList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">店铺商品分类列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 店铺商品分类列表</a></li>
				<shiro:hasPermission name="store:storeCategory:edit"><li class=""><a href="${ctxa}/store/storeCategory/save1.do" > <i class="fa fa-user"></i> 店铺商品分类添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺商品分类管理是xxx</li>
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
						<shiro:hasPermission name="store:storeCategory:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeCategory/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeCategory/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="storeCategoryId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${storeCategory.storeCategoryId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="storeId"  maxlength="19" class="form-control input-sm" placeholder="关联(店铺表)" value="${storeCategory.storeId}"/>
					</div>
					<div class="col-sm-1">
					</div>
					<div class="col-sm-1">
						<input type="text" name="parentIds"  maxlength="512" class="form-control input-sm" placeholder="所有父级id" value="${storeCategory.parentIds}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="分类名称" value="${storeCategory.name}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="cateLevel"  maxlength="10" class="form-control input-sm" placeholder="分类层级" value="${storeCategory.cateLevel}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="sort"  maxlength="10" class="form-control input-sm" placeholder="排序" value="${storeCategory.sort}"/>
					</div>
					<div class="col-sm-1">
						<c:forEach items="${fns:getDictList('yes_no')}" var="item">
						<label class="checkbox-inline">
						<input type="radio" name="isOpen" value="${item.value}" ${item.value==storeCategory.isOpen?"checked":""}/> ${item.label}
						</label>
						</c:forEach>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeCategory.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeCategory.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeCategory.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${storeCategory.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
						<th>关联(卖家表)</th>
						<th>父类id（定级父类为0）</th>
						<th>所有父级id</th>
						<th>分类名称</th>
						<th>分类层级</th>
						<th>排序</th>
						<th>是否开启(0否、1是)</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="store:storeCategory:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeCategory">
					<tr>
						<td><a href="${ctxa}/store/storeCategory/edit1.do?storeCategoryId=${storeCategory.storeCategoryId}">${storeCategory.storeCategoryId}</a></td>
						<td>${storeCategory.sellerId}</td>
						<td>${storeCategory.parent.id}</td>
						<td>${storeCategory.parentIds}</td>
						<td>${storeCategory.name}</td>
						<td>${storeCategory.cateLevel}</td>
						<td>${storeCategory.sort}</td>
						<td>${fns:getDictLabel(storeCategory.isOpen, 'yes_no', '')}</td>
						<td><fmt:formatDate value="${storeCategory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${storeCategory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeCategory:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeCategory/edit1.do?storeCategoryId=${storeCategory.storeCategoryId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeCategory/delete.do?storeCategoryId=${storeCategory.storeCategoryId}">
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