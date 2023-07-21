<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员通知")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMessageList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员通知")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员通知")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.会员通知.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-3">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy("查询")}"><i class="fa fa-search"></i></button>
					</div>
				</div>
				<form action="${ctxa}/sys/sysMessage/list.do" method="get" id="searchForm">
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>${fns:fy("消息ID")}</th>
						<th>${fns:fy("发送人类型")}</th>
						<th>${fns:fy("接收人")}</th>
						<th>${fns:fy("消息类型")}</th>
						<th>${fns:fy("站内信是否发送")}</th>
						<th>${fns:fy("短信是否发送")}</th>
						<th>${fns:fy("邮件是否发送")}</th>
						<th>${fns:fy("微信是否发送")}</th>
						<th>${fns:fy("消息内容")}</th>
						<th>${fns:fy("站内信是否已读")}</th>
						<th>${fns:fy("发送时间")}</th>
						<shiro:hasPermission name="sys:sysMessage:edit"><th>${fns:fy("操作")}</th></shiro:hasPermission>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="sysMessage">
					<tr>
						<td>${sysMessage.informationId}</td>
						<td>${fns:getDictLabel(sysMessage.sender, 'message_sender_type', '')}</td>
						<td>${sysMessage.userMain.loginName}</td>
						<td>${fns:getDictLabel(sysMessage.type, 'sys_message', '')}</td>
						<td>${fns:getDictLabel(sysMessage.statusMsg, 'yes_no', '')}</td>
						<td>${fns:getDictLabel(sysMessage.statusSms, 'yes_no', '')}</td>
						<td>${fns:getDictLabel(sysMessage.statusEmail, 'yes_no', '')}</td>
						<td>${fns:getDictLabel(sysMessage.statusWeixin, 'yes_no', '')}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${sysMessage.content}</textarea></td>
						<td>${fns:getDictLabel(sysMessage.reading, 'is_read', '')}</td>
						<td><fmt:formatDate value="${sysMessage.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="sys:sysMessage:edit">
						<td>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysMessage/delete.do?informationId=${sysMessage.informationId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
						 </td>
						</shiro:hasPermission>
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
	<!-- 开始快速查询窗口 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">	 
					<form action="${ctxa}/sys/sysMessage/list.do" method="get" id="searchFormMyModal"> 
						<div class="modal-body form-horizontal adminex-form">
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">${fns:fy("发送人")}：</label>
								<div class="col-sm-4">
									<select name="sender" class="form-control m-bot15 input-sm">
									<option value="" class="firstOption">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('message_sender_type')}" var="item">
									<option value="${item.value}" ${item.value==sysMessage.sender?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">${fns:fy("接收人")}：</label>
								<div class="col-sm-4">
									<input type="text" name="loginName" maxlength="24" placeholder="${fns:fy("请输入接收人")}" class="form-control input-sm searchInput" value="${loginName}"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">${fns:fy("消息内容")}：</label>
								<div class="col-sm-8">
									<textarea name="content" rows="3" class="form-control searchInput" maxlength="512" placeholder="${fns:fy("请输入消息内容")}">${sysMessage.content}</textarea>
								</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label text-right">${fns:fy("消息发送时间")}：</label>
								<div class="col-sm-4">
									<div class="input-group"> 
										<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("开始时间")}" class="form-control input-sm J-date-start searchInput"
										value="<fmt:formatDate value="${sysMessage.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
										<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="input-group"> 
										<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("结束时间")}" class="form-control input-sm J-date-start searchInput"
										value="<fmt:formatDate value="${sysMessage.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
										<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" onclick="(function(){layer.closeAll('page');}())">
									<i class="fa fa-times"></i> ${fns:fy("关闭")}
								</button>
								<button type="button" id="resetParam" class="btn btn-warning" onclick="(function(){$('.searchInput').attr('value',''); $('.firstOption').attr('selected','selected');}())">
									 <i class="fa fa-reply"></i> ${fns:fy("参数重置")}
								</button> 
								<button type="submit" class="btn btn-info">
									<i class="fa fa-search"></i>${fns:fy("执行查询")}
								</button> 
							</div>
						</div>
					</form> 
				</div>
		</div>
		<!-- 结束快速查询模态窗口 --> 
</body>
</html>