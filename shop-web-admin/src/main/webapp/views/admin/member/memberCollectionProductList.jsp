<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>收藏商品管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberCollectionProductList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">收藏商品列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 收藏商品列表</a></li>
				<shiro:hasPermission name="member:memberCollectionProduct:edit"><li class=""><a href="${ctxa}/member/memberCollectionProduct/save1.do" > <i class="fa fa-user"></i> 收藏商品添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>收藏商品管理是xxx</li>
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
						<shiro:hasPermission name="member:memberCollectionProduct:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/member/memberCollectionProduct/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/member/memberCollectionProduct/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="collectionId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${memberCollectionProduct.collectionId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="mId"  maxlength="19" class="form-control input-sm" placeholder="关联(会员表)" value="${memberCollectionProduct.MId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pId"  maxlength="19" class="form-control input-sm" placeholder="关联(商品SPU表)(SPU级别)" value="${memberCollectionProduct.PId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="image"  maxlength="64" class="form-control input-sm" placeholder="封面图path，冗余" value="${memberCollectionProduct.image}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="pictureName"  maxlength="64" class="form-control input-sm" placeholder="商品名称" value="${memberCollectionProduct.pictureName}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="picturePrice"  maxlength="12" class="form-control input-sm" placeholder="商品价格(取SKU中最低价)" value="${memberCollectionProduct.picturePrice}"/>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionProduct.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionProduct.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionProduct.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberCollectionProduct.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
						<th>关联(商品SPU表)(SPU级别)</th>
						<th>封面图path，冗余</th>
						<th>商品名称</th>
						<th>商品价格(取SKU中最低价)</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="member:memberCollectionProduct:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="memberCollectionProduct">
					<tr>
						<td><a href="${ctxa}/member/memberCollectionProduct/edit1.do?collectionId=${memberCollectionProduct.collectionId}">${memberCollectionProduct.collectionId}</a></td>
						<td>${memberCollectionProduct.MId}</td>
						<td>${memberCollectionProduct.PId}</td>
						<td>${memberCollectionProduct.image}</td>
						<td>${memberCollectionProduct.pictureName}</td>
						<td>${memberCollectionProduct.picturePrice}</td>
						<td><fmt:formatDate value="${memberCollectionProduct.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${memberCollectionProduct.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="member:memberCollectionProduct:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/member/memberCollectionProduct/edit1.do?collectionId=${memberCollectionProduct.collectionId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/member/memberCollectionProduct/delete.do?collectionId=${memberCollectionProduct.collectionId}">
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