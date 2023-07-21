<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('账号组')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userRoleList.js"></script>
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
							<span>${fns:fy('账号组')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('账号')}</li>
							<li>${fns:fy('账号管理')}</li>
							<li class="active">${fns:fy('账号组')}</li>
						</ul>
					</dt>
					<!-- 提示开始 -->
					<dd class="sui-row-fluid sui-form form-horizontal screen pt10 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
						<div class="sui-msg msg-tips msg-block" style="margin: 0 10px 0px 10px;">
							<div class="msg-con">
								<ul>
									<h4>${fns:fy('提示信息')}</h4>
									<li>${fns:fy('添加店铺子账号，可以为不同账号分配不同的权限。')}</li>
									<li>${fns:fy('添加账号前请首先建立账号组。')}</li>
								</ul>
							</div>
							<s class="msg-icon" style="margin-top: 10px"></s>
						</div>
					</dd>
					<!-- 提示结束-->
					<dd class="table-css">
						<div class="sui-btn-group m20 pull-right">
							<shiro:hasPermission name="store:storeRole:edit">
								<a class="sui-btn btn-large btn-primary" href="${ctxs}/store/storeRole/save1.htm">${fns:fy('新增账号组')}</a>
							</shiro:hasPermission>
						</div>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr colspan="2">
									<th width="10%" class="center">${fns:fy('账号组')}</th>
									<shiro:hasPermission name="store:storeRole:edit">
										<th width="20%" class="center">${fns:fy('操作')}</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
								<!--循环开始-->
								<c:forEach items="${page.list}" var="storeRole">
								<tr>
									<td width="10%" class="center">${fns:fy(storeRole.roleName)}</td>
									<shiro:hasPermission name="store:storeRole:edit">
										<td width="2s0%" class="center">
											<a href="${ctxs}/store/storeRole/edit1.htm?roleId=${storeRole.roleId}" class="sui-btn btn-large btn-success">
												<i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}
											</a>
											<button href="${ctxs}/store/storeRole/delete.htm?roleId=${storeRole.roleId}" class="sui-btn btn-large btn-danger deleteSure">
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