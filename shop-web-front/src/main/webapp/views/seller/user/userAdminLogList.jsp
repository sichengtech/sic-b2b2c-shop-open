<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('操作日志')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userAdminLogList.js"></script>
<style type="text/css">
	.searchSpan{margin-right: 10px;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="box">
					<dt class="box-header">
						<h4 class="pull-left">
							<i class="sui-icon icon-tb-addressbook"></i>
							<span>${fns:fy('操作日志')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('账号')}</li>
							<li>${fns:fy('账号管理')}</li>
							<li class="active">${fns:fy('操作日志')}</li>
						</ul>
					</dt>
					<!-- 提示开始 -->
					<dd class="sui-row-fluid sui-form form-horizontal screen pt10 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
						<div class="sui-msg msg-tips msg-block" style="margin: 0 10px 0px 10px;">
							<div class="msg-con">
								<ul>
									<h4>${fns:fy('提示信息')}</h4>
									<li>${fns:fy('查看商家的操作日志，系统对商家的关键操作进行了记录')}</li>
								</ul>
							</div>
							<s class="msg-icon" style="margin-top: 10px"></s>
						</div>
					</dd>
					<!-- 提示结束-->
					<dd class="table-css">
						<form id="searchForm" action="${ctxs}/store/storeAdminLog/list.htm" class="sui-form m10" style="margin-right: 0px;" method="get">
							<div class="sui-row">
								<div class="span5"></div>
								<div class="span2">
									<input name="title" value="${log.title}" type="text" placeholder="${fns:fy('日志标题')}"/>
								</div>
								<div class="span5">
									 <input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${log.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginCreateDate" format="yyyy-MM-dd" placeholder="${fns:fy('请选择开始时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
									<span>-</span>
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${log.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endCreateDate" format="yyyy-MM-dd" placeholder="${fns:fy('请选择结束时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
									<button type="submit" class="sui-btn btn-primary">${fns:fy('搜索')}</button>
								</div>
							</div>
						</form>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr colspan="4">
									<th width="10%" class="center">${fns:fy('账号')}</th>
									<th width="10%" class="center">${fns:fy('操作的url')}</th>
									<th width="10%" class="center">IP</th>
									<th width="10%" class="center">${fns:fy('操作时间')}</th>
								</tr>
							</thead>
							<tbody>
								<!--循环开始-->
								<c:forEach items="${page.list}" var="storeLog">
									<tr>
										<td width="10%" class="center">${storeLog.userMain.loginName}</td>
										<td width="10%" class="center">${storeLog.requestUri}</td>
										<td width="10%" class="center">${storeLog.remoteAddr}</td>
										<td width="10%" class="center">
											<fmt:formatDate value="${storeLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
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