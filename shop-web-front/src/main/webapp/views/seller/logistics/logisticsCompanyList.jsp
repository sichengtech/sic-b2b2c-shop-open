<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('快递公司')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/logistics/logisticsCompanyList.js"></script>
<style type="text/css">
	 #table td{padding-left: 30px;}
	 #table{margin-bottom: -1px;}
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
					<span>${fns:fy('快递公司')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('运营')}</li>
					<li>${fns:fy('运营管理')}</li>
					<li class="active">${fns:fy('快递公司')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block" style="margin-left: 10px;margin-right: 10px;">
					<div class="msg-con">
						<ul>
							<h4>${fns:fy('操作提示：')}</h4>
							<li>${fns:fy('1、如果商家未选择快递公司，则发货时系统会列出常用的快递公司供商家选择。')}</li>
							<li>${fns:fy('2、如果商家选择使用某些快递公司，则发货时系统只列出商家选择的快递公司。')}</li>
						</ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<dd class="table-css">
				<form id="myForm" action="${ctxs}/logistics/logisticsCompany/save.htm" method="post" class="sui-form form-inline" style="margin: 0;" >
					<sys:message content="${message}"/>
					<table class="sui-table table-bordered-simple" id="table">
						<thead>
							<tr>
								<th class="center" colspan="4">${fns:fy('快递公司')}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="logiticsCom" varStatus="status">
								<c:set var="checked" value=""></c:set>
								<c:forEach items="${storeLcs}" var="item">
									<c:if test="${item.lcId==logiticsCom.lcId}">
										<c:set var="checked" value="checked"></c:set>
									</c:if>
								</c:forEach>
								<c:if test="${(status.index)%4==0 }">
								<tr>
								</c:if>
								<td class="" style="border-top: 0px solid #e6e6e6;">
									<label class="checkbox-pretty inline ${checked}">
										<input name="lcIds" value="${logiticsCom.lcId}" ${checked} type="checkbox"><span>${logiticsCom.companyName}</span>
									</label>
								</td>
								<c:if test="${(status.index+1)%4==0 }">
								</tr>
								</c:if>
							</c:forEach>
							<tr>
								<shiro:hasPermission name="logistics:logisticsCompany:edit">
									<td colspan="4">
										<a id="save" href="javascript:;" class="sui-btn btn-bordered btn-large btn-success">${fns:fy('保存2')}</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</tbody>
					</table>
				</form>
			</dd>
			</dl>
		</div>
	</div>
</div>
 <%@ include file="/views/seller/include/deleteSure.jsp"%>
</body>
</html>