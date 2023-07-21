<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购单BOM上传')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseBomUpload.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购单BOM上传')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/purchase/purchase/list.do"> <i class="fa fa-user"></i>${fns:fy('采购单列表')}</a></li>
				<shiro:hasPermission name="purchase:purchase:auth">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('采购单BOM上传')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('将用户上传的BOM信息按照BOM模板的格式添加到新的excel中，并上传新的excel。')}</li>
					<li>${fns:fy('产品名称、产品型号、需求数量、价格要求是必填项。')}</li>
					<li>${fns:fy('需求数量要填写数字，最大长度为11位。价格要求要填写数字或2位小数，最大长度为12位。')}</li>
					<li>${fns:fy('产品描述最大255位，其他信息最大64位。')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post" enctype="multipart/form-data" action="${ctxa}/purchase/purchase/bomUpload2.do">
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
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('BOM模板')}&nbsp;:</label>
							<div class="col-sm-5">
								<a href="${ctxStatic}/excel/${fns:fy('采购单BOM模板')}.xlsx" style="text-decoration: none;" download="${fns:fy('采购单BOM模板')}.xlsx">
									<p style="font-size: 15px;">${fns:fy('下载BOM模板')}</p>
								</a>
							</div>
							<div class="col-sm-5">
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
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('上传BOM文件')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input id="excel" name="excel" type="file" accept=".xls,.xlsx"/>									
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择你要上传的BOM文件')}<p>
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
</body>
</html>