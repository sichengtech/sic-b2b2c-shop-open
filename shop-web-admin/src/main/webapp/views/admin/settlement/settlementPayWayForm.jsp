<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('支付方式管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<script type="text/javascript">
	var payWayAttrList=${payWayAttrList};
</script>
<style type="text/css">
	.cmxform .form-group label.error{display: inherit;}
</style>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementPayWayForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementPayWay.payWayId?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set> 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set> 
			<h4 class="title">${isEdit?edit:save}${fns:fy('支付方式')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementPayWay/list.do"> <i class="fa fa-user"></i> ${fns:fy('支付方式列表')}</a></li>
				<shiro:hasPermission name="settlement:settlementPayWay:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('支付方式')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('支付方式.支付方式添加.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementPayWay/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="payWayId" value="${settlementPayWay.payWayId}">
						<input id="oldNum" type="hidden" value="${settlementPayWay.payWayNum}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('支付方式')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${settlementPayWay.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写支付方式')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('支付方式编号')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayNum"  maxlength="64" class="form-control input-sm" value="${settlementPayWay.payWayNum}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写支付方式编号')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('支付服务费用')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="poundage"  maxlength="11" class="form-control input-sm" value="${settlementPayWay.poundage}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写支付服务费用')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('使用终端')}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="useTerminal" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy('请选择')}--</option>
									<c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="item">
									<option value="${item.value}" ${item.value==settlementPayWay.useTerminal?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写使用终端')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('支付方式')}logo&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="payWayLogo" value="${settlementPayWay.payWayLogo}"/>
								<div id="vessel"></div>
							</div>
							<div class="col-sm-5">
								<h4>${fns:fy('上传支付方式')}logo</h4>
								<p class="help-block">${fns:fy('LOGO尺寸要求宽度为120像素、高度为50像素；支持格式jpg,jpeg,png,bmp。')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="status" value="1" ${settlementPayWay.status eq "0"?"":"checked"}
								data-size="small" style="display: none" data-on-text="${fns:fy('开启')}" data-off-text="${fns:fy('关闭')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必选项，请选择状态')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('设置属性')}&nbsp;:</label>
							<div class="col-sm-7">
								<table class="table table-hover table-condensed table-bordered pay-way-attr-table">
									<thead>
										<tr>
											<th>${fns:fy('属性键')}</th>
											<th>${fns:fy('属性值')}</th>
											<th>${fns:fy('属性描述')}</th>
											<th>${fns:fy('操作')}</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="5">
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
								<shiro:hasPermission name="settlement:settlementPayWay:edit">
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
	<script type="text/template" id="pay_way_attr_tpl" info="支付方式属性模板">
		<tr>
			<td>
				<input type="text" id="{{d.payWayKeyId}}" name="payWayKey" maxlength="64" class="form-control input-sm payWayKey" value="{{d.payWayKey}}"/>
			</td>
			<td>
				<textarea class="form-control input-sm" rows="2" id="{{d.payWayValueId}}" name="payWayValue" maxlength="4000">{{d.payWayValue}}</textarea>
			</td>
			<td>
				<input type="text" id="{{d.payWayDescribeId}}" name="payWayDescribe" maxlength="255" class="form-control input-sm" value="{{d.payWayDescribe}}"/>
			</td>
			<td>
				<button type="button" class="btn btn-danger delete-attr"><i class="fa fa-trash-o"></i> 删除</button>
			</td>
		</tr>
	</script>
</body>
</html>