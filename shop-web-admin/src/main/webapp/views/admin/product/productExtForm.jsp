<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品扩展表管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productExtForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productExt.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商品扩展表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productExt/list.do"> <i class="fa fa-user"></i> 商品扩展表列表</a></li>
				<shiro:hasPermission name="product:productExt:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商品扩展表${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品扩展表管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productExt/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${productExt.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId"  maxlength="19" class="form-control input-sm" value="${productExt.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="carIds"  maxlength="4000" class="form-control input-sm" value="${productExt.carIds}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段n1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="n1"  maxlength="10" class="form-control input-sm" value="${productExt.n1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段n1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段n2&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="n2"  maxlength="10" class="form-control input-sm" value="${productExt.n2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段n2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段n3&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="n3"  maxlength="10" class="form-control input-sm" value="${productExt.n3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段n3<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段f1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="f1"  maxlength="12" class="form-control input-sm" value="${productExt.f1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段f1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段f2&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="f2"  maxlength="12" class="form-control input-sm" value="${productExt.f2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段f2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段f3&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="f3"  maxlength="12" class="form-control input-sm" value="${productExt.f3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段f3<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段d1&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="d1" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productExt.d1}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段d1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段d2&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="d2" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productExt.d2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段d2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段d3&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="d3" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productExt.d3}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段d3<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productExt:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
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
</body>
</html>