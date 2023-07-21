<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('对账差错池管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeErrorPoolForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty tradeErrorPool.epId?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${isEdit?edit:save}${fns:fy('对账差错池')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/trade/tradeErrorPool/list.do"> <i class="fa fa-user"></i> ${fns:fy('对账差错池列表')}</a></li>
				<shiro:hasPermission name="trade:tradeErrorPool:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('对账差错池')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('差错池管理.差错池编辑.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/trade/tradeErrorPool/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="epId" value="${tradeErrorPool.epId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('处理状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="handlestatus" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy('请选择')}--</option>
									<c:forEach items="${fns:getDictList('error_pool_status')}" var="item">
									<option value="${item.value}" ${item.value==tradeErrorPool.handlestatus?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写处理状态')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('处理备注')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="handleremark" rows="6" class="form-control" maxlength="256" >${tradeErrorPool.handleremark}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写处理备注')}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="trade:tradeErrorPool:edit">
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