<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!-- 发货弹出框开始 -->
<div id="" tabindex="-1" role="dialog" data-hasfoot="" class="" style="border-radius: 5px;">
<div class="modal-content">
<div class="modal-body" style="padding: 0;">
<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
	<input type="hidden" name="ordersId" value="142">
	<table class="sui-table table-bordered" id="delTable">
		<tbody>
			<tr>
				<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
				<td><div class="text-box"><span class="c-success freightOrderId" name="orderId">${tradeOrder.orderId}</span></div></td>
			</tr>
			<tr>
				<td class="" style="text-align: right;">${fns:fy('收货人信息：')}</td>
				<td class="">
					<div class="text-box">
						<span class="c-success freightOrderId" name="oldConsigneeMsg">
							${tradeOrder.consignee},${tradeOrder.phone},${tradeOrder.provinceName}&nbsp;${tradeOrder.cityName}&nbsp;${tradeOrder.districtName}&nbsp;${tradeOrder.detailedAddress}
						</span>
						<input type="hidden" name="oldProvinceName" value="${tradeOrder.provinceName}"/>
						<input type="hidden" name="oldCityName" value="${tradeOrder.cityName}"/>
						<input type="hidden" name="oldDistrictName" value="${tradeOrder.districtName}"/>
						<a href="javascript:void(0);" class="sui-btn btn-small" id="editMess" onclick="(function(){$('#sui-msg').css('display','block');})();">${fns:fy('编辑')}</a>
					</div>
					<div class="sui-msg msg-large msg-default msg-notice" id="sui-msg" style="display: none;">
						<div class="msg-con" style="border:1px dashed #FEC500;padding-left: 14px;">
							<dd class="bs-docs-example" style="margin-bottom: 0px; padding: 0;background-color:#FFFFF1; border: none; ">
								<div class="control-group">
									<label class="control-label"><font color="red">　　*</font>${fns:fy('收货人：')}</label>
									<input type="text" class="input-xlarge" placeholder="${fns:fy('请填写真实姓名')}" name="consignee" maxLength="64" value="${tradeOrder.consignee}"/>
								</div>
								<div class="control-group">
									<label class="control-label"><font color="red">*</font>${fns:fy('收货人信息：')}</label>
								 	<span id="area2" class="mt5" style="display: none;">
										<span class="sui-dropdown dropdown-bordered select">
											<span class="dropdown-inner">
												<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
													<input type="hidden" name="provinceId" id="provinceId"  onchange="selectCity(this)" value=""/><i class="caret"></i>
													<span id="provinceName">${fns:fy('省')}</span>
												</a>
												<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
													<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('省')}</a></li>
													<c:forEach items="${provinceList}" var="province">
														<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${province.id}">${province.name}</a></li>
													</c:forEach>
												</ul>
											</span>
										</span>
										<span class="sui-dropdown dropdown-bordered select">
											<span class="dropdown-inner">
												<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
													<input type="hidden" name="cityId" id="cityId" onchange="selectDistrict(this)" value=""/><i class="caret"></i>
													<span id="cityName">${fns:fy('市')}</span>
												</a>
												<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
													<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
												</ul>
											</span>
										</span>
										<span class="sui-dropdown dropdown-bordered select">
											<span class="dropdown-inner">
												<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
													<input type="hidden" name="districtId" id="districtId" value=""/><i class="caret"></i>
													<span id="districtName">${fns:fy('县')}</span>
												</a>
												<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
													<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
												</ul>
											</span>
										</span>
									</span>
									<small id="address" class="mt5" style="" onclick="(function(){$('#address').css('display','none');$('#area2').css('display','inline-block');})();">
										${tradeOrder.provinceName} &nbsp;&nbsp;${tradeOrder.cityName}&nbsp;&nbsp; ${tradeOrder.districtName}
										<a href="javascript:void(0);" class="sui-btn btn-small">${fns:fy('编辑')}</a>
									</small>
								</div>
								<div class="control-group mt5">
									<label class="control-label"><font color="red">　*</font>${fns:fy('街道地址：')}</label>
									<input type="text" class="input-xlarge" placeholder="${fns:fy('不必重复填写地区')}" name="detailedAddress" maxlength="255" value="${tradeOrder.detailedAddress}"/>
								</div>
								<div class="control-group mt5">
									<label class="control-label"><font color="red">　*</font>${fns:fy('联系电话：')}</label>
									<input type="text" class="input-xlarge" placeholder="${fns:fy('请输入联系电话')}" name="phone" value="${tradeOrder.phone}" maxLength="64"/>
								</div>
								<div class="align-right pb20" style="margin-top: 10px;padding-bottom: 10px;">
									<a href="javascript:void(0);" class="sui-btn btn-warning finishEdit">${fns:fy('完成编辑')}</a>
								</div>
							</dd>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="" style="text-align: right;">${fns:fy('快递公司：')}</td>
				<td>
					<span class="sui-dropdown dropdown-bordered select">
						<span class="dropdown-inner" style="width: 118px;">
							<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
								<input value="-1" name="logisticsTemplateId" companyName="${company.companyName}" type="hidden"/><i class="caret"></i>
								<span name="logisticsTemplateName">${empty tradeOrder.logisticsTemplateId?fns:fy('不使用物流公司'): tradeOrder.logisticsTemplateName}</span>
							</a>
							<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
								<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="-1">${fns:fy('不使用物流公司')}</a></li>
								<c:forEach items="${logisticsCompanyList}" var="company">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${company.lcId}">${company.companyName}</a></li>
								</c:forEach>
							</ul>
						</span>
					</span>
				</td>
			</tr>
			<tr>
				<td class="" style="text-align: right;">${fns:fy('快递单号：')}</td>
				<td><input type="text" name="trackingNo" placeholder="${fns:fy('请输入快递单号')}" class="input-fat freightModelBuyer" maxLength="64" style="line-height: 23px;height: 23px;" value="${tradeOrder.trackingNo}"/></td>
			</tr>
			<tr>
				<td class="" style="text-align: right;">${fns:fy('发货备注：')}</td>
				<td><textarea placeholder="${fns:fy('您可以输入一些发货备忘信息（仅卖家自己可见）')}" name="sellerMemo" class="sellerMemo" maxlength="255" rows="4" cols="50">${tradeOrder.sellerMemo}</textarea></td>
			</tr>
		</tbody>
	</table>
	<div class="span3" style="margin-right: 20px;"></div>
	<div class="modal-footer" id="delModel-footer">
		<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large deliverGoods" style="margin-right: 10px;" orderId="">${fns:fy('确定')}</button>
		<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large"  onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
	</div>
</form>
</div>
</div>
</div>
<!-- 发货弹出框结束 -->