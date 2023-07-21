<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>${fns:fy('我的钱包')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/account/myWalletDetail.js"></script>
	<style>
	</style>
</head>
<body>
	<!-- 右侧栏主区main content start-->
	<div class="main-content wallet" style="overflow:visible">
		<!--body wrapper start-->
		<div class="sui-row-fluid">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('我的钱包')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('运营')}</li>
						<li>${fns:fy('资金管理')}</li>
						<li class="active">${fns:fy('我的钱包')}</li>
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

				<dd class="ml10 mr10">
					<div class="wallet-balance">
						<ul class="">
							<li class="balance-f">
								<div class="f-freeze">
									<p>
										<c:choose>
										   <c:when test="${accountUser.accountType== 0}">${fns:fy('商家账户余额(元)：')}</c:when>
										   <c:when test="${accountUser.accountType== 1}">${fns:fy('服务账户余额(元)：')}</c:when>
										   <c:otherwise>${fns:fy('其它账户余额(元)：')}</c:otherwise>
										</c:choose>
										<span>${accountUser.ownMoney+accountUser.frozenMoney}</span>  <a href="${ctxs}/account/tiedCard/list.htm">${fns:fy('绑卡')}</a>
									</p>
								</div>
								<div class="f-usable">
									<p>${fns:fy('可用余额(元)：')}<span>${accountUser.ownMoney}</span><a href="${ctxs}/account/withdrawals/list.htm?accountId=${accountUser.auId}">${fns:fy('提现')}</a></p>
									<p>${fns:fy('冻结余额(元)：')}<span>${accountUser.frozenMoney}</span><i class="iconfont icon-huaban"></i></p>
									<div>
										<p>${fns:fy('冻结金额')}</p>
										<span></span>
										<em></em>
									</div>
								</div>
							</li>
						</ul>
					</div>

					<div class="wallet-condition">
						<p>${fns:fy('账户明细')}</p>
						<form action="" class="sui-form form-horizontal mt20">
							<div class="control-group">
								<input type="hidden" name="auId" value="${auId }" />
								<label class="control-label">${fns:fy('时间范围：')}</label>
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${accountUserSn.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginCreateDate" placeholder="${fns:fy('请选择起始日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});">
								<span> ${fns:fy('至')} </span>
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${accountUserSn.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endCreateDate" placeholder="${fns:fy('请选择结束日期')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});">
								<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('查询')}</button>
							</div>
						</form>
					</div>

					<div class="wallet-detail">
						<ul class="">
							<li class="detail-f">
								<p>
									<i class="iconfont icon-shou"></i>${fns:fy('收入(元)')}
									<span>${m1Count }</span>
								</p>
							</li>
							<li class="detail-cen"></li>
							<li class="detail-t">
								<p>
									<i class="iconfont icon-zhi"></i>${fns:fy('支出(元)')}
									<span>${m2Count }</span>
								</p>
							</li>
						</ul>
					</div>

					<c:choose>
					<c:when test="${fn:length(page.list)==0}">
						<div class="noData">
							<span>${fns:fy('暂无数据')}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="wallet-detail-table">
							<table class="sui-table table-bordered">
								<tbody>
								<tr>
									<th>${fns:fy('时间')}</th>
									<th>${fns:fy('流水号')}</th>
									<th>${fns:fy('备注')}</th>
									<th>${fns:fy('收入金额(元)')}</th>
									<th>${fns:fy('支出余额(元)')}</th>
								</tr>
								<c:forEach items="${page.list}" var="sn">
									<tr>
										<td><fmt:formatDate value="${sn.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${sn.serialNumber}</td>
										<td>${sn.payRemarks}</td>
										<td>${sn.incomeMoney}</td>
										<td>${sn.expensesMoney}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
						<%@ include file="/views/seller/include/page.jsp"%>
					</c:otherwise>
					</c:choose>
				</dd>
			</dl>
		</div>
		<!--main-center-->
		<!--body wrapper end-->
	</div>
	<!-- 右侧栏主区main content end-->
</body>
</html>