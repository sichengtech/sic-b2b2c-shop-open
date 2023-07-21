<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>${fns:fy('账户列表')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
</head>
<body>
	<!-- 右侧栏主区main content start-->
	<div class="main-content walletlist" style="overflow:visible">
		<!--body wrapper start-->
		<div class="sui-row-fluid">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('账户列表')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('账户列表')}</li>
					</ul>
				</dt>

				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('当前页面显示您的账户列表，可以点击进入账户进行提现绑卡等操作')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<dd class="clear ml10 mr10">
				<c:forEach items="${list}" var="accountUser">
					<ul class="">
						<li>
						<c:choose>
						   <c:when test="${accountUser.accountType== 0}">
						   ${fns:fy('商家账户')}
						   </c:when>
						   <c:when test="${accountUser.accountType== 1}">
						   ${fns:fy('服务账户')}
						   </c:when>
						   <c:otherwise>
						   ${fns:fy('其它账户')}
						   </c:otherwise>
						</c:choose>
						</li>
						<li>${fns:fy('余额：')}<span>${accountUser.ownMoney+accountUser.frozenMoney}</span></li>
						<li><a href="${ctxs}/account/myWallet/detail.htm?auId=${accountUser.auId}" class="sui-btn btn-primary">${fns:fy('进入账户')}</a></li>
					</ul>
				</c:forEach>
				</dd>
			</dl>
		</div>
		<!--main-center-->
		<!--body wrapper end-->
	</div>
	<!-- 右侧栏主区main content end-->
</body>
</html>