<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('我的消息')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/user/memberMessage.js"></script>
</head>
<body>
	<div class="main-center">
	<dl>
		<dt>
			<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('会员资料')} > ${fns:fy('我的消息')}</div>
			<i class="sui-icon icon-tb-list"></i> ${fns:fy('我的消息')}
		</dt>
		<dd>
			<ul class="sui-nav nav-tabs">
				<li class="${empty type?'active':''}"><a href="${ctxm}/user/memberMessage/list.htm" >${fns:fy('全部消息')} <font></font></a></li>
				<li class="${type=='1'?'active':''}"><a href="${ctxm}/user/memberMessage/list.htm?type=1" >${fns:fy('交易消息')} <font>${countType1}</font></a></li>
				<li class="${type=='2'?'active':''}"><a href="${ctxm}/user/memberMessage/list.htm?type=2" >${fns:fy('退换货消息')} <font>${countType2}</font></a></li>
				<li class="${type=='3'?'active':''}"><a href="${ctxm}/user/memberMessage/list.htm?type=3" >${fns:fy('商品消息')} <font>${countType3}</font></a></li>
				<li class="${type=='4'?'active':''}"><a href="${ctxm}/user/memberMessage/list.htm?type=4" >${fns:fy('运营消息')} <font>${countType4}</font> </a></li>
			</ul>
		</dd>
		<sys:message content="${message}"/>
		<dd class="mymessage pl20 pr20">
			<table class="sui-table table-xlarge">
				<thead>
					<tr>
						<td colspan="4">
							<input id="selectAll_id" class="leader" type="checkbox">${fns:fy('全选')}
							<a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger deleteSureBatch">${fns:fy('删除')}</a>
							<a href="javascript:void(0);" class="sui-btn btn-bordered btn-warning readBatch">${fns:fy('标签为已读')}</a>
						</td>
					</tr>
					<tr>
						<th></th>
						<th style="width: 460px;text-align: center;">${fns:fy('内容')}</th>
						<th style="text-align: center;">${fns:fy('是否已读')}</th>
						<th style="text-align: center;">${fns:fy('时间')}</th>
						<th style="text-align: center;">${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${page.list}" var="sysMessage">
						<tr>
							<td><input type="checkbox" informationId = "${sysMessage.informationId}" ></td>
							<td style="width: 460px;text-align: center;"><a href="javascript:void(0);">${sysMessage.content}</a></td>
							<td style="text-align: center;">${fns:getDictLabel(sysMessage.reading, 'is_read', '')}</td>
							<td style="text-align: center;"><fmt:formatDate value="${sysMessage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="text-align: center;"><button href="${ctxm}/user/memberMessage/delete.htm?informationId=${sysMessage.informationId}" class="sui-btn btn-bordered btn-danger deleteSure">${fns:fy('删除')}</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</dd>
		<%@ include file="/views/member/include/page.jsp"%>
	</dl>
	</div>
	<!--main-center-->

</body>
</html>
