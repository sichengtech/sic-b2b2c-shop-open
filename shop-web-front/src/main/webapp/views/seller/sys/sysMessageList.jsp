<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('系统消息')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/sys/sysMessageList.js"></script>
<style type="text/css">
	 .header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
	 .header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	 #delTable td{line-height: 30px;}
	 .checked li{line-height: 25px;}
	 #sui-msg{font-size: 12px;line-height: 27px;font-weight: normal;}
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
							<span>${fns:fy('系统消息')}</span>
							<%@ include file="../include/functionBtn.jsp"%>
						</h4>
						<ul class="sui-breadcrumb">
							<li>${fns:fy('当前位置:')}</li>
							<li>${fns:fy('运营')}</li>
							<li>${fns:fy('客服管理')}</li>
							<li class="active">${fns:fy('系统消息')}</li>
						</ul>
					</dt>
					<dd class="sui-row-fluid form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
						<div class="sui-msg msg-tips msg-block" style="margin-left: 10px;margin-right: 10px;">
							<div class="msg-con">
								 <ul>
									<h4>${fns:fy('操作提示：')}</h4>
									<li>${fns:fy('查看并管理本店铺收到的所有消息')}</li>
								 </ul>
							</div>
							<s class="msg-icon" style="margin-top: 10px"></s>
						</div>
					</dd>
					<!--状态导航开始 -->
					<dd class="sui-row-fluid sui-form form-horizontal screen mb0">
						<div class="header header1" style="background: none;height: 37px; box-shadow: none;border-bottom: 1px solid #e5e5e5;margin-bottom: 0px;">
							<div class="sui-container">
								<div class="sui-navbar container nav-box">
									<div class="navbar-inner">
										<ul class="sui-nav">
											<li class="${empty type?'active':''}"><a style="height: 37px;line-height: 37px;" href="${ctxs}/sys/sysMessage/list.htm">${fns:fy('全部消息')}</a></li>
											<li class="${type==1?'active':''}"><a style="height: 37px;line-height: 37px;" href="${ctxs}/sys/sysMessage/list.htm?type=1">${fns:fy('交易消息')}</a></li>
											<li class="${type==2?'active':''}"><a style="height: 37px;line-height: 37px;" href="${ctxs}/sys/sysMessage/list.htm?type=2">${fns:fy('退换货消息')}</a></li>
											<li class="${type==3?'active':''}"><a style="height: 37px;line-height: 37px;" href="${ctxs}/sys/sysMessage/list.htm?type=3">${fns:fy('商品消息')}</a></li>
											<li class="${type==4?'active':''}"><a style="height: 37px;line-height: 37px;" href="${ctxs}/sys/sysMessage/list.htm?type=4">${fns:fy('运营消息')}</a></li>
											<%-- <li><a style="height: 37px;line-height: 37px;" href="${ctxs}/message/seller/list.htm">${fns:fy('接收设置')}</a></li> --%>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</dd>
					<!--状态导航结束 --> 
					<dd class="table-css">
						<!--table开始 -->
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr>
									<th class="center">${fns:fy('全选')}</th>
									<th class="center">${fns:fy('状态')}</th>
									<th class="center">${fns:fy('消息内容')}</th>
									<th class="center">${fns:fy('发送时间')}</th>
									<shiro:hasPermission name="sys:sysMessage:edit">
										<th class="center">${fns:fy('操作')}</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody id="tbody">
								<tr class="sep-row" style="">
									<td class="center">
										<input class="leader" type="checkbox" name="all" value="1"/>
									</td>
									<shiro:hasPermission name="sys:sysMessage:edit">
										<td colspan="4">
											<button href="${ctxs}/sys/sysMessage/deleteAll.htm" class="sui-btn btn-bordered btn-large btn-danger deleteAll">${fns:fy('删除')}</button>
											<button href="${ctxs}/sys/sysMessage/edit.htm" class="sui-btn btn-bordered btn-large btn-primary read">${fns:fy('标记已读')}</button>
										</td>
									</shiro:hasPermission>
								</tr>
								<c:forEach items="${page.list}" var="message">
									<tr class="sep-row" style="">
										<td class="center">
											<input name="ids" class="trCheck" type="checkbox" value="${message.informationId}"/>
										</td>
										<td class="center">
											${fns:getDictLabel(message.reading, 'is_read', '')}
										</td>
										<td class="center">
											<form class="form-inline sui-form" style="margin:0 0 0">
												<textarea rows="2" cols="60" readonly="readonly">${message.content}</textarea>
											</form>
										</td>
										<td class="center">
											<fmt:formatDate value="${message.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>   
										</td>
										<shiro:hasPermission name="sys:sysMessage:edit">
											<td class="center">
												<button href="${ctxs}/sys/sysMessage/delete.htm?informationId=${message.informationId}" class="sui-btn btn-bordered btn-large btn-danger deleteSure" messId="12345">${fns:fy('删除')}</button>
											</td>
										</shiro:hasPermission>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						 <!-- table结束 -->
					</dd>
					<%@ include file="/views/seller/include/page.jsp"%>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>