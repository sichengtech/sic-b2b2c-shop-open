<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<c:forEach items="${addressList}" var="address" varStatus="addressIndex">
	<li>
		<input type="radio" name="address" ${(empty addressId && addressIndex.first) || (not empty addressId && addressId eq address.addressId)?'checked="checked"':'' } addressId="${address.addressId}" class="addressRadio"/>
		<label>
			<span class="name">${address.name}</span>
			<span class="address">${address.provinceName} ${address.cityName} ${address.districtName} ${address.detailedAddress}</span>
			<span><i class="sui-icon icon-tb-phone"></i><span class="mobile">${address.mobile}</span></span>
			<span class="sui-label label-" class="isDefault" style="display:${addressList[0].isDefault eq '1'?'':'none'} ">${fns:fy('默认地址')}</span>
		</label>
		<a href="javascript:;" class="deleAddress" addressId="${address.addressId}">${fns:fy('删除')}</a>
	</li>
</c:forEach>
