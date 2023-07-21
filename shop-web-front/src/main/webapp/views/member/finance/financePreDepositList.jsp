<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>账户余额</title>
<meta name="decorator" content="member"/>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>当前位置:</span><span>会员中心</span> > 财务中心 > 账户余额</div>
				<i class="sui-icon icon-tb-list"></i> 账户余额
			</dt>
			<dd class="mybalance">当前账户可用余额：<font style="color:red">￥${empty userMain.userMember.balance?'0':userMain.userMember.balance}</font> 元</dd>
			<dt><i class="sui-icon icon-tb-list"></i> 消费记录</dt>
			<dd class="pl20 pr20">
				<table class="default-table ">
					<thead>
						<tr>
							<th class="w250">创建时间</th>
							<th class="w200">金额(元)</th>
							<th class="tl">变更说明</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="deposit" varStatus="index">
							<tr class="bd-line">
								<td class="goods-time"><fmt:formatDate value="${deposit.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="green">${deposit.availableMoney}</td>
								<td class="tl">${deposit.operationDescribe}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</dd>
			<%@ include file="/views/member/include/page.jsp"%>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
