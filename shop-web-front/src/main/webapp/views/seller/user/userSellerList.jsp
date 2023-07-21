<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺管理员')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userSellerList.js"></script>
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
							<span>${fns:fy('账号管理')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('账号')}</li>
							<li>${fns:fy('账号管理')}</li>
							<li class="active">${fns:fy('账号管理')}</li>
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
							<shiro:hasPermission name="sso:userSeller:edit">
								<a class="sui-btn btn-large btn-primary" href="${ctxs}/user/userSeller/save1.htm">${fns:fy('新增账号')}</a>
							</shiro:hasPermission>
						</div>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr colspan="3">
									<th width="60%" class="center">${fns:fy('账号名称')}</th>
									<th width="10%" class="center">${fns:fy('账号类型')}</th>
									<shiro:hasPermission name="sso:userSeller:edit">
										<th width="30%" class="center">${fns:fy('操作')}</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="60%" class="center">${userMain.loginName}</td>
									<td width="10%" class="center">${userMain.typeAccount==1?fns:fy('主账号'):fns:fy('子管理员')}</td>
									<shiro:hasPermission name="sso:userSeller:edit">
										<td width="30%" class="center">
											<a class="sui-btn btn-large btn-success" href="${ctxs}/user/userSeller/edit1.htm?uId=${userMain.UId}">
												<i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}
											</a>
										</td>
									</shiro:hasPermission>
								</tr>
								<!--循环开始-->
								<c:forEach items="${page.list}" var="userMain">
								<tr>
									<td width="60%" class="center">${userMain.loginName}</td>
									<td width="10%" class="center">${userMain.typeAccount==1?fns:fy('主账号'):fns:fy('子管理员')}</td>
									<shiro:hasPermission name="sso:userSeller:edit">
										<td width="30%" class="center">
											<a class="sui-btn btn-large btn-success" href="${ctxs}/user/userSeller/edit1.htm?uId=${userMain.UId}">
												<i class="sui-icon icon-tb-edit"></i>${fns:fy('编辑')}
											</a>
											<button href="${ctxs}/user/userSeller/delete.htm?uId=${userMain.UId}" class="sui-btn btn-large btn-danger deleteSure">
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