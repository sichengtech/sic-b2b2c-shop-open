<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("消息模板管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteMessageTemplateList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("消息模板列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("消息模板列表")}</a></li>
				<shiro:hasPermission name="site:siteMessageTemplate:save"><li class=""><a href="${ctxa}/site/siteMessageTemplate/save1.do" > <i class="fa fa-user"></i> ${fns:fy("消息模板添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.消息模板.操作提示1")}</li>
					<li>${fns:fy("网站设置.消息模板.操作提示2")}</li>
					<li>${fns:fy("网站设置.消息模板.操作提示3")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteMessageTemplate:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteMessageTemplate/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteMessageTemplate/list.do" method="get" id="searchForm">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-2">
						<input type="text" name="num"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('模板编号')}" value="${siteMessageTemplate.num}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("模板名称")}" value="${siteMessageTemplate.name}"/>
					</div>
					<div class="col-sm-2">
						<select name="type" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择")}--</option>
							<c:forEach items="${fns:getDictList('site_message_type')}" var="item">
							<option value="${item.value}" ${item.value==siteMessageTemplate.type?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("模板编号")}</th>
						<th>${fns:fy("模板名称")}</th>
						<th>${fns:fy("类型")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteMessageTemplate">
					<tr>
						<td><a href="${ctxa}/site/siteMessageTemplate/edit1.do?id=${siteMessageTemplate.id}">${siteMessageTemplate.num}</a></td>
						<td>${siteMessageTemplate.name}</td>
						<td>${fns:getDictLabel(siteMessageTemplate.type, 'site_message_type', '')}</td>
						<td>
							<shiro:hasPermission name="site:siteMessageTemplate:edit">
							<a class="btn btn-info btn-sm siteMessage" href="javascript:;" messageId="${siteMessageTemplate.id}" messageNum="${siteMessageTemplate.num}" 
							messageName="${siteMessageTemplate.name}" messageIsOpen="${siteMessageTemplate.isOpen}" msgContent="${siteMessageTemplate.msgContent}">
								<i class="fa fa-volume-down"></i> ${fns:fy("站内信")}
							</a>
							<a class="btn btn-success btn-sm sms" href="javascript:;" smsId="${siteMessageTemplate.id}" thirdTemplateCode="${siteMessageTemplate.thirdtemplatecode}" smsNum="${siteMessageTemplate.num}" 
							smsName="${siteMessageTemplate.name}" smsOpen="${siteMessageTemplate.smsOpen}" smsContent="${siteMessageTemplate.smsContent}">
								<i class="fa fa-mobile"></i> ${fns:fy("短信")}
							</a>
							<a class="btn btn-warning btn-sm" href="${ctxa}/site/siteMessageTemplate/email1.do?id=${siteMessageTemplate.id}">
								<i class="fa fa-envelope-o"></i> ${fns:fy("邮件")}
							</a>
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteMessageTemplate/edit1.do?id=${siteMessageTemplate.id}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteMessageTemplate:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteMessageTemplate/delete.do?id=${siteMessageTemplate.id}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<shiro:hasPermission name="site:siteMessageTemplate:edit">
	<!-- 开始站内信模态框 -->
	<div class="modal fade" id="siteMessageModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">
			<form id="forbidSaleForm" action="${ctxa}/site/siteMessageTemplate/siteMessage.do" method="post"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
						 <li class="m-b-3"> ${fns:fy("网站设置.消息模板.操作提示4")}</li>
						 <li class="m-b-3"> ${fns:fy("网站设置.消息模板.操作提示5")}</li>
						</ul>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("模板编号")}：</label>
						<div class="col-sm-9" id="messageNum_id">${fns:fy("模板编号")}</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("模板名称")}：</label>
						<div class="col-sm-9" id="messageName_id">${fns:fy("模板名称")}</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("是否开启")}：</label>
						<div class="col-sm-9">
							<input type="radio" id="radio1" name="isOpen" value="1"/>&nbsp;${fns:fy("是")}
							<input type="radio" id="radio2" name="isOpen" value="0"/>&nbsp;${fns:fy("否")}
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("模板内容")}：</label>
						<div class="col-sm-9">
							<textarea id="msgContent_id" name="msgContent" class="form-control"placeholder="" rows="3" maxlength="512"></textarea>
							<input type="hidden" name="messageId" value=""/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
						<i class="fa fa-times"></i> ${fns:fy("关闭")}
					</button>
					<button type="submit" class="btn btn-info">
						<i class="fa fa-check"></i> ${fns:fy("保存")}
					</button> 
				</div>
			</form>
		</div>
	</div>
	<!-- 结束站内信模态框 -->
	</shiro:hasPermission>
	<shiro:hasPermission name="site:siteMessageTemplate:edit">
	<!-- 开始短信模态框 -->
	<div class="modal fade" id="smsModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">
			<form id="forbidSaleForm" action="${ctxa}/site/siteMessageTemplate/sms.do" method="post"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
						 <li class="m-b-3">${fns:fy("网站设置.消息模板.操作提示6")}</li>
						 <li class="m-b-3">${fns:fy("网站设置.消息模板.操作提示7")}</li>
						</ul>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("模板编号")}：</label>
						<div class="col-sm-9" id="smsNum_id">${fns:fy("模板编号")}</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("模板名称")}：</label>
						<div class="col-sm-9" id="smsName_id">${fns:fy("模板名称")}</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("是否发送短信")}：</label>
						<div class="col-sm-9">
							<input type="radio" id="radio3" name="smsOpen" value="1"/>&nbsp;${fns:fy("是")}
							<input type="radio" id="radio4" name="smsOpen" value="0"/>&nbsp;${fns:fy("否")}
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("短信模板内容")}：</label>
						<div class="col-sm-9">
							<textarea id="smsContent_id" name="smsContent" class="form-control"placeholder="" rows="3" maxlength="512"></textarea>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("第三方短信模板id")}：</label>
						<div class="col-sm-9">
							<input  id="thirdTemplateCode_id" name="thirdTemplateCode" value="" class="form-control" maxlength="128"/>
							<input type="hidden" name="smsId" value=""/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
						<i class="fa fa-times"></i> ${fns:fy("关闭")}
					</button>
					<button type="submit" class="btn btn-info">
						<i class="fa fa-check"></i> ${fns:fy("保存")}
					</button> 
				</div>
			</form>
		</div>
	</div>
	<!-- 结束短信模态框 -->
	</shiro:hasPermission>
</body>
</html>