<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<html lang="zh-CN">
<title>${fns:fy('收货地址列表')}</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/user/memberAddressList.js"></script>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('会员资料')} > ${fns:fy('收货地址')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('收货地址')}<span style="font-size: 12px;color:#bbb8b8;">(${fns:fy('最多可添加20个有效地址')})</span>
			</dt>
			<sys:message content="${message}"/>
			<c:forEach items="${addressList}" var="memberAddress">
				<dd class="myaddress">
					<button href="${ctxm}/user/memberAddress/delete.htm?addressId=${memberAddress.addressId}" type="button" class="sui-text-danger pull-right sui-btn btn-bordered btn-primary deleteSure"><i class="sui-icon icon-tb-close"></i> ${fns:fy('删除')}</button>
					<a href="${ctxm}/user/memberAddress/edit1.htm?addressId=${memberAddress.addressId}" class="sui-text-danger pull-right mr10 sui-btn btn-bordered btn-primary"><i class="sui-icon icon-tb-edit"></i> ${fns:fy('编辑')}</a>
					<c:if test="${memberAddress.isDefault eq '1'}">
						<a href="javascript:void(0);" class="sui-btn pull-right mr10"><i class="sui-icon icon-tb-locationfill"></i> ${fns:fy('已设为默认收货地址')}</a>
					</c:if>
					<h3><span>${memberAddress.addressName}</span></h3>
					<form class="sui-form form-horizontal">
						<div class="control-group">
							<label class="control-label">${fns:fy('收货人')}:</label>
							<div class="controls">
								${memberAddress.name}
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('所在地区')}:</label>
							<div class="controls">
								${memberAddress.provinceName} ${memberAddress.cityName} ${memberAddress.districtName}
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('地址')}:</label>
							<div class="controls">
								${memberAddress.detailedAddress}
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('电话')}:</label>
							<div class="controls">
								${memberAddress.mobile}
							</div>
						</div>
					</form>
				</dd>
			</c:forEach>
			<c:if test="${fn:length(addressList) < '20'}">
				<dd href="#" class="myaddress addbtn">
					<a href="${ctxm}/user/memberAddress/save1.htm"><i class="sui-icon icon-tb-add"></i> ${fns:fy('添加一个新地址')}</a>
				</dd>
			</c:if>
			<!-- 没有数据提示开始 -->
			<c:if test="${fn:length(addressList)=='0'}">
				<div class="no_product" style="text-align: center;color: #9a9a9a;margin-top: 300px;">
					<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
				<div>
			</c:if>
			<!-- 没有数据提示结束 -->
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
