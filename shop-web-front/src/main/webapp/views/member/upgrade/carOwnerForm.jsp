<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('完善车主信息')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script src="${ctx}/views/seller/product/ajaxProductCar.js" type="text/javascript"></script>
<script src="${ctx}/views/member/upgrade/carOwnerForm.js" type="text/javascript"></script>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('用户升级')} > ${fns:fy('完善车主信息')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('完善车主信息')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="myform">
				<form class="sui-form form-horizontal" method="post" id="inputForm" action="${ctxm}/upgrade/carOwner/save2.htm">
					<div class="control-group">
						<label class="control-label">${fns:fy('车主信息：')}</label>
						<div class="controls">
							${applyCarName}
							<a href="javaScript:void(0);" class="change">${fns:fy('编辑')}</a>
						</div>
					</div>
					<div class="control-group apply_carIds" style="display: none">
						<label class="control-label">&nbsp;</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carTwoId" id="carTwoId"  onchange="selectThree(this)"><i class="caret"></i>
										<span id="twoName">${fns:fy('请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="twoAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 		<c:forEach items="${productCarTwoList}" var="productCar">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${productCar.carId}">${productCar.name}</a></li>
								 		</c:forEach>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carThreeId" id="carThreeId" onchange="selectFour(this)"><i class="caret"></i>
										<span id="threeName">${fns:fy('请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="threeAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="carFourId" id="carFourId"><i class="caret"></i>
										<span id="fourName">${fns:fy('请选择')}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="fourAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a></li>
								 	</ul>
								</span>
						 	</span>
							<div class="sui-msg msg-tips msg-naked">
								<div class="msg-con">${fns:fy('提示信息，不可更改')}</div>
								<s class="msg-icon"></s>
							</div>
						</div>
					</div>
					<div class="control-group button">
						<label class="control-label">　</label>
						<button type="submit" class="sui-btn btn-xlarge btn-primary"><i class="sui-icon icon-tb-roundcheck"></i> ${fns:fy('保存')}</button>
					</div>
				</form>
			</dd>
		</dl>
	</div>
</body>
</html>
