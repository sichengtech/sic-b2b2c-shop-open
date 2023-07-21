<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('评价管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeCommentList.js"></script>
<style type="text/css">
	.fa-star{color:#F1C40F;}
	.addComment{word-break:break-all;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('评价列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('评价管理.评价列表操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
				<form action="${ctxa}/trade/tradeComment/list.do" method="get" id="searchForm">
					<div class="col-sm-2 col-md-offset-1">
						<input type="text" name="userName" maxlength="64" class="form-control input-sm" placeholder="${fns:fy('评价人')}" value="${username}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="productScore" maxlength="64" class="form-control input-sm" placeholder="${fns:fy('评分(请输入1-5之间的数字)')}" value="${tradeComment.productScore}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="productName" maxlength="128" class="form-control input-sm" placeholder="${fns:fy('被评商品')}" value="${productName}"/>
					</div>
					<div class="col-sm-2">
						<!-- todo -->
						<input type="text" name="storeName" maxlength="64" class="form-control input-sm" placeholder="${fns:fy('店铺名')}" value="${storeName}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th></th>
						<th>${fns:fy('评价人')}</th> 
						<th>${fns:fy('评价时间')}</th> 
						<th>${fns:fy('评分')}</th> 
						<th>${fns:fy('评价内容')}</th>
						<th>${fns:fy('晒单图片')}</th> 
						<shiro:hasPermission name="trade:tradeComment:drop"><th>${fns:fy('操作')}</th></shiro:hasPermission>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="tradeComment" varStatus="index">
						<tr class="${index.count}">
							<td class="detail"><i class="fa fa-plus"></i></span></td>
							<td>${tradeComment.userMain.loginName}</td>
							<td><fmt:formatDate value="${tradeComment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
							 	<c:forEach begin="1" end="${tradeComment.productScore}" step="1" var="i">
									<i class="fa fa-star"></i>
								</c:forEach>
							</td>
							<td>
								<textarea class="form-control input-sm" rows="2" readonly="readonly">${tradeComment.content}</textarea>
							</td>
							<td>
								<c:forEach items="${tradeComment.tradeCommentImageList}" var="img">
									<a target="_blank" href="${ctxfs}${img.path}">
										<img alt="" src="${ctxfs}${img.path}@!40X40" width="40" height="40"
										onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');">
									</a>
								</c:forEach>
							</td>
							<shiro:hasPermission name="trade:tradeComment:drop">
							<td>
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/trade/tradeComment/delete.do?commentId=${tradeComment.commentId}">
									<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
								</button>
							 </td>
							</shiro:hasPermission>
						</tr>
						<tr id="${index.count}" class="detail-extra">
							<td datano="0" colspan="14" >
								<p datano="0" columnno="0" class="dt-grid-cell">${fns:fy('订单编号')} :${tradeComment.orderId} </p>
								<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('被评商品')} :${tradeComment.productSpu.name}</p>
								<p datano="0" columnno="2" class="dt-grid-cell">${fns:fy('所属店铺')} :${tradeComment.store.name}</p> 
								<p datano="0" columnno="3" class="dt-grid-cell addComment">${fns:fy('追评内容')} :
									${tradeComment.tradeCommentAdd.content}
									<c:set var="commentId" value="${tradeComment.tradeCommentAdd.commentId}"/>
									<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('追评晒单')} :
										<c:forEach items="${tradeComment.tradeCommentAdd.tradeCommentImageList}" var="img">
											<a target="_blank" href="${ctxfs}${img.path}">
												<img alt="" src="${ctxfs}${img.path}@!40X40" width="40" height="40">
											</a>
										</c:forEach>
									</p>
								</p>
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