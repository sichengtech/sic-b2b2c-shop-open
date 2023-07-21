<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('咨询管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/trade/tradeConsultationList.js"></script>
<style type="text/css">
	.trSpan{margin-right: 15px;}
	.activeTop{border: 1px solid #4cb9fc!important;color: white!important;background-color: #4cb9fc!important;}
	.error{width: auto;}
	.sui-msg.msg-block {margin: 10px !important;}
	.count{word-break: break-all;}
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
							<span>${fns:fy('咨询管理')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('售后')}</li>
							<li>${fns:fy('售后管理')}</li>
							<li class="active">${fns:fy('咨询管理')}</li>
						</ul>
					</dt>
					<!-- 提示信息开始 -->
					<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
						<div class="sui-msg msg-tips msg-block">
							<div class="msg-con">
								<ul>
									<h4>${fns:fy('提示信息')}</h4>
									<li>${fns:fy('此列表显示了商家的所有咨询信息。')}</li>
									<li>${fns:fy('点击回复按钮可以回复咨询。')}</li>
								</ul>
							</div>
							<s class="msg-icon" style="margin-top: 10px"></s>
						</div>
					</address>
					<!-- 提示信息结束 -->
					<dd class="table-css">
						<div class="sui-btn-group m20">
							<h5>${fns:fy('列表筛选：')}</h5>
							<button id="button1" href="${ctxs}/trade/consultation/list.htm" class="sui-btn btn-bordered btn-primary ${empty isReply?'activeTop':''}">${fns:fy('全部咨询')}</button>
							<button id="button2" href="${ctxs}/trade/consultation/list.htm?isReply=0" class="sui-btn btn-bordered btn-primary ${isReply=='0'?'activeTop':''}">${fns:fy('未回复咨询')}</button>
							<button id="button3" href="${ctxs}/trade/consultation/list.htm?isReply=1" class="sui-btn btn-bordered btn-primary ${isReply=='1'?'activeTop':''}">${fns:fy('已回复咨询')}</button>
						</div>
						<form class="sui-form pull-right" style="margin:20px;" action="${ctxs}/trade/consultation/list.htm" method="get" id="searchForm">
							<input value="${isReply}" name="isReply" type="hidden"/>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner" style="width: 118px;">
									<a id="drop12" role="button" data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
										<input value="${category}" name="category" type="hidden"/>
										<i class="caret"></i>
										<c:set var="a" value="${fns:getDictLabel(category, 'trade_consultation_categor', '')}"></c:set>
										<span>${empty category?fns:fy('请选择咨询类型'):a}</span>
									</a>
									<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
										<c:forEach items="${fns:getDictList('trade_consultation_categor')}" var="consultationCategor">
											<li role="presentation">
												<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${consultationCategor.value}">${consultationCategor.label}</a>
											</li>
										</c:forEach>
									</ul>
								</span>
							</span>
							<button type="submit" class="sui-btn btn-primary">${fns:fy('搜索')}</button>
						</form>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr>
									<th width="70%" class="center">${fns:fy('咨询/回复')}</th>
									<shiro:hasPermission name="trade:tradeConsultation:edit">
										<th width="30%" class="center">${fns:fy('操作')}</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
							<!--循环开始-->
							<c:forEach items="${page.list}" var="consultation">
								<tr style="height:15px;"></tr>
								<tr>
									<th colspan="2">
										<input name="ids" class="trCheck" type="checkbox" value="${consultation.consultationId}">
										<span class="trSpan">
											<a target="_blank" href="${ctxf}/detail/${consultation.productSpu.PId}.htm">${consultation.productSpu.name}</a>
											&nbsp;&nbsp;&nbsp;${fns:fy('咨询用户：')}${consultation.userMain.loginName}&nbsp;&nbsp;&nbsp;
											${fns:fy('咨询类型：')}${fns:getDictLabel(consultation.category, 'trade_consultation_categor', '')}&nbsp;&nbsp;&nbsp;
											${fns:fy('咨询时间：')}<fmt:formatDate value="${consultation.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
										</span>
									</th>
								</tr>
								<tr>
									<td width="70%">
										<strong>${fns:fy('咨询内容:')}</strong>
										<span class="count">${consultation.content}</span></br>
										<strong>${fns:fy('回复内容:')}</strong>
										<span class="count">${consultation.replyTradeConsultation.content}</span>
									</td>
									<shiro:hasPermission name="trade:tradeConsultation:edit">
										<td class="center" width="30%" style="text-align: right;">
											<c:if test="${empty consultation.replyTradeConsultation.replyId}">
												<a id="${consultation.consultationId}" pid="${consultation.productSpu.PId}" content="${consultation.content}" href="javascript:void(0);" class="sui-btn btn-primary delOrder">
													<i class="sui-icon icon-tb-edit"></i>${fns:fy('回复')}
												</a>
											</c:if>
											<button href="${ctxs}/trade/consultation/delete.htm?consultationId=${consultation.consultationId}" class="sui-btn btn-danger deleteSure"><i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}</button>
										</td>
									</shiro:hasPermission>
								</tr>
							</c:forEach>
							<!--循环结束-->
							<!-- 没有数据提示开始 -->
							<c:if test="${fn:length(page.list)=='0'}">
								<tr>
									<td colspan="2" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;">
										<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
									</td>
								</tr>
							</c:if>
							<!-- 没有数据提示结束 -->
							</tbody>
						</table>
						<c:if test="${fn:length(page.list)>'0'}">
							<div class="docs" style="margin-left: -6px;">
								<shiro:hasPermission name="trade:tradeConsultation:edit">
									<div class="sui-btn-group">
										<h5>${fns:fy('批量操作选中商品：')}</h5>
										<button href="${ctxs}/trade/consultation/deleteAll.htm" class="sui-btn btn-danger deleteAll"><i class="sui-icon icon-tb-delete"></i>${fns:fy('删除')}</button>
									</div>
								</shiro:hasPermission>
							</div>
						</c:if>
					</dd>
					<c:if test="${fn:length(page.list)>'0'}">
						<%@ include file="/views/seller/include/page.jsp"%>
					</c:if>
				</dl>
			</div>
		</div>
	</div>
	<!-- 咨询回复弹出框开始 -->
	<div id="delModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
			<div class="modal-content">
				<div class="modal-body" style="padding: 0;">
					<form id="cancelOrdersForm" name="cancelOrdersForm" action="${ctxs}/trade/consultation/save.htm" method="post">
						<input id="orders_id" type="hidden" name="replyId" value="">
						<input id="product_id" type="hidden" name="pId" value="">
						<table class="sui-table table-bordered" id="delTable">
							<tbody>
								<tr>
									<td style="text-align: right;" width="20%">${fns:fy('咨询内容：')}</td>
									<td id="content_id" width="80%"></td>
								</tr>
								<tr>
									<td style="text-align: right;" width="20%">${fns:fy('回复咨询：')}</td>
									<td width="80%">
										<textarea name="content" rows="5" cols="65"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="modal-footer" style="background-color: #ffffff;text-align: right;margin-right: 10px;">
							<button type="submit" data-ok="modal" class="sui-btn btn-primary btn-large" style="margin-right: 10px;">${fns:fy('确定')}</button>
							<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 咨询回复弹出框结束 -->
</body>
</html>