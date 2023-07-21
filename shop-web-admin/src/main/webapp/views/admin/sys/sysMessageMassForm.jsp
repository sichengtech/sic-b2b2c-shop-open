<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员管理-通知群发-标题")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMessageMassForm.js"></script>
<style type="text/css">
	.chooseUser{border: solid 1px #ccc;height: 134px;overflow-y: auto;border-radius:5px;display: inline-table;width: 100%;}
	.chooseUser button{margin: 5px 5px 5px 5px;border-radius: 30px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员管理-通知群发-标题")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员管理-通知群发-标题")}</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理-通知群发-操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<div class="form-group">
						<label class="control-label col-sm-2" for="inputSuccess"></label>
						<div class="col-sm-5"></div>
						<div class="col-sm-2">
							<select class="form-control m-bot15 input-sm typeUser">
								<option name="typeUser" value="">--${fns:fy("请选择会员类型")}--</option>
								<option name="typeUser" value="1" class="typeUser-1">${fns:fy("个人")}</option>
								<option name="typeUser" value="2" class="typeUser-2">${fns:fy("供应商")}</option>
								<option name="typeUser" value="3" class="typeUser-3">${fns:fy("门店服务商")}</option>
								<option name="typeUser" value="4" class="typeUser-4">${fns:fy("采购商")}</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="text" id="loginName" name="loginName" maxlength="64" class="form-control input-sm" placeholder="${fns:fy("会员名")}"/>
						</div>
						<div class="col-sm-1" style="text-align: right;">
							<button type="button" class="btn btn-info btn-sm search" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
						</div> 
						<label class="control-label col-sm-2" for="inputSuccess" style="margin-top: 10px;">${fns:fy("会员列表")}&nbsp;:</label>
						<div class="col-sm-10" style="margin-top: 15px;">
							<div class="table-responsive">
								<table class="table table-hover table-condensed table-bordered">
									 <thead> 
										<tr>
											<th></th>
											<th>${fns:fy("会员ID")}</th>
											<th>${fns:fy("会员类型")}</th>
											<th>${fns:fy("会员名称")}</th>
										</tr>
									</thead> 
									<tbody id="memberTable"></tbody>
								</table>
							</div>
							<div class="col-sm-12" style="text-align: right;">
								<input type="hidden" name="pageNo" value="0"/>
								<ul class="pagination pagination-sm pull-right">
									<li><a href="javaScript:;" class="lastPage"><i class="fa fa-angle-double-left"></i>${fns:fy("上一页")}</a></li>
									<li><a href="javaScript:;" class="nextPage">${fns:fy("下一页")}<i class="fa fa-angle-double-right"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("已选会员")}&nbsp;:</label>
						<div class="col-sm-10">
							<div class="chooseUser"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("发送消息")}&nbsp;:</label>
						<div class="col-sm-10">
							<textarea rows="6" class="form-control message" name="message"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("消息方式")}&nbsp;:</label>
						<div class="col-sm-10 icheck" style="display: inline-flex;padding-top: 4px;">
							<div class="square-blue single-row" style="width: 95px;">
								<input type="checkbox" name="sysMode" style="display: none" data-switch-no-init value="1"><label style="padding-top: 1px;">${fns:fy("站内信")}</label>
							</div>
							<div class="square-blue single-row" style="width: 95px;">
								<input type="checkbox" name="sysMode" style="display: none" data-switch-no-init value="2"><label style="padding-top: 1px;">${fns:fy("短信")}</label>
							</div>
							<div class="square-blue single-row" style="width: 95px;">
								<input type="checkbox" name="sysMode" style="display: none" data-switch-no-init value="3"><label style="padding-top: 1px;">${fns:fy("邮件")}</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="inputSuccess"></label>
						<div class="col-sm-5">
							<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
								<i class="fa fa-times"></i> ${fns:fy("返回")}
							</button>
							<shiro:hasPermission name="sys:sysMessage:edit">
							<button type="button" class="btn btn-info inputFormSubmit" >
								<i class="fa fa-check"></i> ${fns:fy("发送")}
							</button>
							</shiro:hasPermission>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<script type="text/template" id="messageTpl1">
		<tr>
			<td>
				<div>
					<input type="hidden" class="chooseHiddenUId" loginName="{{d.loginName}}" value="{{d.UId}}">
					<input type="checkbox" class="checkboxUId ischecked-{{d.UId}}" UId={{d.UId}}>
				</div>
			</td>
			<td>{{d.UId}}</td>
			<td>
				 {{#  if(d.isTypeUserMember){ }}${fns:fy("个人")}{{#  } }}
				 {{#  if(d.isTypeUserSeller){ }},${fns:fy("供应商")}{{#  } }}
				 {{#  if(d.isTypeUserService){ }},${fns:fy("门店服务商")}{{#  } }}
				 {{#  if(d.isTypeUserPurchaser){ }},${fns:fy("采购商")}{{#  } }}
			</td>
			<td>{{d.loginName}}</td>
		</tr>
	</script>
	<script type="text/template" id="messageTpl2">
		<tr>
			<td colspan="4" style="height: 200px;">${fns:fy("未搜索到任何结果")}</td>
		</tr>
	</script>
	<script type="text/template" id="messageTpl3">
		<button type="button" class="btn btn-info btn-xs selected_btn">
			<input type="hidden" value="{{d.UId}}" name="uId" class="chooseUId">
			<i class="fa fa-times-circle"></i>&nbsp;<span>{{d.loginName}}</span>
		</button>
	</script>
</body>
</html>