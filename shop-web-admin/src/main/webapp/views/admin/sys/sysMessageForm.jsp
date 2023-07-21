<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>会员通知管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMessageForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysMessage.informationId?true:false}"></c:set > 
			<h4 class="title">${isEdit?'编辑':'添加'}会员通知</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysMessage/list.do"> <i class="fa fa-user"></i> 会员通知列表</a></li>
				<shiro:hasPermission name="sys:sysMessage:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 会员通知${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>通过会员通知，可以看到所有会员收到的通知。</li>
				</ul>
			</div>
			<!-- 提示结束 -->	
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysMessage/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="informationId" value="${sysMessage.informationId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 消息ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="informationId" maxlength="19" class="form-control input-sm" value="${sysMessage.informationId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写消息ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 发送人类型&nbsp;:</label>
							<div class="col-sm-5">
								<select name="sender" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('message_sender_type')}" var="item">
									<option value="${item.value}" ${item.value==sysMessage.sender?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写发送人类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 接收人类型&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('message_receive_type')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="receiveType" value="${item.value}" ${item.value==sysMessage.receiveType?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写接收人类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 买家&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="mId" maxlength="19" class="form-control input-sm" value="${sysMessage.MId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写买家<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 卖家&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId" maxlength="19" class="form-control input-sm" value="${sysMessage.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写卖家<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 消息类型&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('sys_message')}" var="item">
									<option value="${item.value}" ${item.value==sysMessage.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写消息类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 站内信是否发送&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="statusMsg" value="${item.value}" ${item.value==sysMessage.statusMsg?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写站内信是否发送<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 短信是否发送&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="statusSms" value="${item.value}" ${item.value==sysMessage.statusSms?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写短信是否发送<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 邮件是否发送&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="statusEmail" value="${item.value}" ${item.value==sysMessage.statusEmail?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写邮件是否发送<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 微信是否发送&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="statusWeixin" value="${item.value}" ${item.value==sysMessage.statusWeixin?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写微信是否发送<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 预留是否发送&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="statusB" value="${item.value}" ${item.value==sysMessage.statusB?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写预留是否发送<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 消息标题，预留&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="title" maxlength="64" class="form-control input-sm" value="${sysMessage.title}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写消息标题，预留<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 消息内容&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="content" rows="6" class="form-control" maxlength="512" >${sysMessage.content}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写消息内容<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 站内信是否已读&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('is_read')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="reading" value="${item.value}" ${item.value==sysMessage.reading?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写站内信是否已读<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="sys:sysMessage:edit">
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