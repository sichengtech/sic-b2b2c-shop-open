<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺文章')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeArticleList.js"></script>

<style type="text/css">
	.sui-msg.msg-block{margin: 10px!important;}
</style>
</head>
<body>
 <sys:message content="${message}"/>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('店铺文章')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${fns:fy('店铺文章')}</li>
				</ul>
			</dt>
			<!-- 提示信息开始 -->
			<dd class="sui-row-fluid form-horizontal screen mb0 ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
					<div class="msg-con">
						 <ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('1.展示添加的文章列表')}</li>
							<li>${fns:fy('2.可将链接复制到"店铺导航-添加导航-导航外链接"的输入框中')}</li>
						 </ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<!-- 提示信息结束 -->
			<dd class="table-css">
				<div class="sui-btn-group m20 pull-left">
					<shiro:hasPermission name="store:storeArticle:edit">
						<a class="sui-btn btn-large btn-primary" href="${ctxs}/store/storeArticle/save1.htm">${fns:fy('新增文章')}</a>
					</shiro:hasPermission>
				</div>
				<div class="sui-btn-group m20 pull-right">
					<form class="sui-form form-inline" id="searchForm" action="${ctxs}/store/storeArticle/list.htm" style="margin: 0 0 7px;" method="get">
						<input type="text" name="title" maxlength="32" placeholder="${fns:fy('请输入文章标题')}" class="input-medium" value="${title}" style="width: 180px;"/>
						<button type="submit" class="sui-btn btn-large btn-primary">${fns:fy('搜索')}</button>
					</form>
				</div>
				<table class="sui-table table-bordered-simple">
					<thead>
						<tr colspan="3">
							<th class="center">${fns:fy('文章标题')}</th>
							<th class="center">${fns:fy('文章排序')}</th>
							<th class="center">${fns:fy('文章详情链接')}</th>
							<shiro:hasPermission name="store:storeArticle:edit">
								<th class="center">${fns:fy('操作')}</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<!--循环开始-->
						<c:forEach items="${page.list}" var="storeArticle">
						<tr>
							<td class="center">${storeArticle.title}</td>
							<td class="center">${storeArticle.sort}</td>
							<td class="center"><a target="_blank" href="${ctxf}/store/${userMain.userSeller.storeId}/article.htm?id=${storeArticle.saId}">/store/${userMain.userSeller.storeId}/article.htm?id=${storeArticle.saId}</a></td>
							<shiro:hasPermission name="store:storeArticle:edit">
								<td class="center">
									<a class="sui-btn btn-large btn-success" href="${ctxs}/store/storeArticle/edit1.htm?saId=${storeArticle.saId}">
										<i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}
									</a>
									<button href="${ctxs}/store/storeArticle/delete.htm?saId=${storeArticle.saId}" class="sui-btn btn-large btn-danger deleteSure">
										<i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}
									</button>
								</td>
							</shiro:hasPermission>
						</tr>
						</c:forEach>
						<!--循环结束-->
					</tbody>
				</table>
			</dd>
			<%@ include file="/views/seller/include/page.jsp"%>
		</dl>
	</div>
	</div>
 </div>
</body>
</html>