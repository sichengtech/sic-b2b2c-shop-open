<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购单一句话拆分')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseSpilt.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购内容拆分')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchase/list.do"> <i class="fa fa-user"></i> ${fns:fy('采购单列表')}</a></li>
				<shiro:hasPermission name="purchase:purchase:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('采购内容拆分')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('对一句话的采购内容进行拆分，拆分成多条采购明细')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/purchase/purchase/split2.do" method="post">
						<input type="hidden" name="purchaseId" value="${purchase.purchaseId}">
						<input type="hidden" name="uId" value="${purchase.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('采购标题')}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">${purchase.title}</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('采购内容')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="content" rows="6" class="form-control"  maxlength="2000" readonly="readonly">${purchase.content}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 采购到期时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input style="background: #fff;" readonly="readonly" type="text" class="form-control input-sm" name="expiryTime" format="yyyy-MM-dd" 
									value="<fmt:formatDate value="${empty purchase.expiryTime?expiryTime:purchase.expiryTime}" pattern="yyyy-MM-dd"/>" 
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'});"> 
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请选择采购单的到期时间<p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('采购明细')}&nbsp;:</label>
							<div class="col-sm-10">
								<table class="table table-hover table-condensed table-bordered purchase-spilt-table">
									<thead>
										<tr>
											<th><font color="red">*</font>${fns:fy('产品名称')}</th>
											<th><font color="red">*</font>${fns:fy('规格型号')}</th>
											<th>${fns:fy('品牌')}</th>
											<th><font color="red">*</font>${fns:fy('数量')}</th>
											<th>${fns:fy('单价(元)')}</th>
											<th><font color="red">*</font>${fns:fy('单位')}</th>
											<th>${fns:fy('采购备注')}</th>
											<th>${fns:fy('操作')}</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="9">
												<button type="button" class="btn btn-info add-attr"><i class="fa fa-plus"></i> ${fns:fy('添加')}</button>
											</td>
										</tr>	
									</tfoot>
								</table>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="purchase:purchase:auth">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<!-- 支付方式属性模板 -->
	<script type="text/template" id="purchase_spilt_tpl" info="${fns:fy('模板')}">
		<tr>
			<td>
				<input id="{{d.nameId}}" type="text" name="name" maxlength="64" class="form-control input-sm" value=""/>
			</td>
			<td>
				<input id="{{d.modelId}}" type="text" name="model" maxlength="64" class="form-control input-sm" value=""/>
			</td>
			<td>
				<input id="{{d.brandId}}" type="text" name="brand" maxlength="64" class="form-control input-sm" value=""/>
			</td>
			<td>
				<input id="{{d.amountId}}" type="text" name="amount" maxlength="9" class="form-control input-sm" value=""/>
			</td>
			<td>
				<input id="{{d.priceRequirementId}}" type="text" name="priceRequirement" maxlength="12" class="form-control input-sm" value=""/>
			</td>
			<td>
				<input id="{{d.unitId}}" type="text" name="unit" maxlength="64" class="form-control input-sm" value=""/>
			</td>
			
			<td>
				<input id="{{d.purchaseRemarksId}}" type="text" name="purchaseRemarks" maxlength="255" class="form-control input-sm" value=""/>
			</td>
			<td>
				<button type="button" class="btn btn-danger delete-attr"><i class="fa fa-trash-o"></i> ${fns:fy('删除')}</button>
			</td>
		</tr>
	</script>
</body>
</html>