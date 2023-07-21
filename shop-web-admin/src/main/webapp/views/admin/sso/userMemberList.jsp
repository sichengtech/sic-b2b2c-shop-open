<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userMemberList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员列表")}</a></li>
				<%--<shiro:hasPermission name="sso:userMember:edit"><li class=""><a href="${ctxa}/sso/userMember/save1.do" > <i class="fa fa-user"></i> 会员（买家）添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.会员管理.操作提示1")}</li>
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
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy("查询")}"><i class="fa fa-search"></i></button>
						<%--  添加记录按钮 
						<shiro:hasPermission name="sso:userMember:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sso/userMember/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>  --%>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
					 <thead> 
						<tr> 
							<th></th> 
							<th>${fns:fy("会员ID")}</th>
							<th>${fns:fy("会员名称")}</th>
							<th>${fns:fy("是否是主账号")}</th>
							<th>${fns:fy("会员类型")}</th>
							<th>${fns:fy("会员邮箱")}</th>
							<th>${fns:fy("会员手机")}</th>
							<th>${fns:fy("是否锁定")}</th>
							<!-- <th>可用预存款(元)</th> -->
							<th>${fns:fy("操作")}</th>
						</tr>
					</thead> 
					<tbody>
						<c:forEach items="${page.list}" var="userMain">
						<tr>
							<td class="detail"><i class="fa fa-plus"></i></span></td>
							<td>${userMain.UId}</td>
							<td>${userMain.loginName}</td>
							<td>${fns:getDictLabel(userMain.typeAccount, 'store_seller_type', '')}</td>
							<td>
								<c:if test="${userMain.typeUserMember}">${fns:fy("个人")}</c:if>
								<c:if test="${userMain.typeUserSeller}">、${fns:fy("供应商")}</c:if>
								<c:if test="${userMain.typeUserService}">、${fns:fy("门店服务商")}</c:if>
								<c:if test="${userMain.typeUserPurchaser}">、${fns:fy("采购商")}</c:if>
							</td>
							<td>${userMain.email}</td>
							<td>${userMain.mobile}</td>
							<td>${fns:getDictLabel(userMain.isLocked, 'yes_no', '')}</td>
							<%-- <td>${userMain.userMember.balance}</td> --%>
							<td>
								<shiro:hasPermission name="sso:member:drop">
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sso/userMain/memberDelete.do?uId=${userMain.UId}">
									<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="sso:member:edit">
								<a class="btn btn-info btn-sm" href="${ctxa}/sso/userMain/edit1.do?uId=${userMain.UId}">
									<i class="fa fa-edit"></i> ${fns:fy("编辑")}
								</a>
								<c:if test="${userMain.isLocked=='0'}">
									<button type="button" class="btn btn-danger btn-sm locked" href="${ctxa}/sso/userMain/memberLocked.do?uId=${userMain.UId}">
										<i class="fa fa-edit"></i> ${fns:fy("锁定")}
									</button>
								</c:if>
								<c:if test="${userMain.isLocked=='1'}">
									<button type="button" class="btn btn-info btn-sm unLocked" href="${ctxa}/sso/userMain/memberUnLocked.do?uId=${userMain.UId}">
										<i class="fa fa-edit"></i> ${fns:fy("解锁")}
									</button>
								</c:if>
								</shiro:hasPermission>
								<%--<a class="btn btn-info btn-sm" href="${ctxa}/member/userMember/scoreAdd.do?mId=${userMember.MId}">
									<i class="fa fa-edit"></i> 修改积分
								 </a> --%>
							 </td>
						</tr>
						<tr class="detail-extra">
							<td datano="0" colspan="14" >
								<p datano="0" columnno="0" class="dt-grid-cell hidden ">${fns:fy("会员ID")}：${userMain.userMember.UId}</p>
								<p datano="0" columnno="1" class="dt-grid-cell hidden">${fns:fy("会员名称")} : ${userMain.loginName}</p>
								<p datano="0" columnno="2" class="dt-grid-cell ">${fns:fy("真实姓名")} : ${userMain.userMember.realName}</p>
								<p datano="0" columnno="2" class="dt-grid-cell ">${fns:fy("所在地")} : ${userMain.userMember.provinceName}&nbsp;${userMain.userMember.cityName}&nbsp;${userMain.userMember.districtName}&nbsp;${userMain.userMember.detailedAddress}</p>
								<p datano="0" columnno="4" class="dt-grid-cell ">${fns:fy("出生日期")} : <fmt:formatDate value="${userMain.userMember.birthday}" type="both" dateStyle="full"/></p>
								<p datano="0" columnno="5" class="dt-grid-cell ">QQ : ${userMain.userMember.qq}</p>
								<%-- <p datano="0" columnno="6" class="dt-grid-cell ">预存款 : ${userMain.userMember.balance}元</p> --%>
								<%-- <p datano="0" columnno="8" class="dt-grid-cell ">会员积分 : ${userMain.userMember.point}</p> --%>
								<p datano="0" columnno="11" class="dt-grid-cell ">${fns:fy("最后登录IP")} : ${userMain.loginIp}</p>
								<p datano="0" columnno="7" class="dt-grid-cell ">${fns:fy("注册时间")} : <fmt:formatDate value="${userMain.registerDate}" type="both" dateStyle="full"/></p>
								<p datano="0" columnno="10" class="dt-grid-cell ">${fns:fy("最后登录时间")} : <fmt:formatDate value="${userMain.loginDate}" type="both" dateStyle="full"/></p>
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
	<!-- 开始快速查询窗口 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">	 
			<form action="${ctxa}/sso/userMain/memberList.do" method="get" id="searchFormMyModal"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("会员名称")}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="loginName" value="${userMain.loginName}" placeholder="${fns:fy("请输入会员名称")}" maxlength="20"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("会员邮箱")}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="email" value="${userMain.email}" placeholder="${fns:fy("请输入会员邮箱")}"  maxlength="200"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("会员手机")}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="mobile" value="${userMain.mobile}" placeholder="${fns:fy("请输入会员手机")}"  maxlength="20"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy("真实姓名")}：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="realName" value="${userMember.realName}" placeholder="${fns:fy("请输入会员真实姓名")}"  maxlength="20"/>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("出生日期")}：</label>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="beginBirthday" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("请输入出生日期(开始)")}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${userMember.beginBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="endBirthday" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("请输入出生日期(结束)")}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${userMember.endBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("注册时间")}：</label>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="beginRegisterDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("请输入注册时间(开始)")}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${userMain.beginRegisterDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="endRegisterDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("请输入注册时间(结束)")}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${userMain.endRegisterDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy("会员状态")}：</label>
						<div class="col-sm-4"> 
							<select name="isLocked" class="form-control input-sm">
								<option value="" class="firstOption">${fns:fy("请选择")}</option>
								<c:forEach items="${fns:getDictList('is_locked')}" var="item">
								<option value="${item.value}" ${item.value==userMain.isLocked?"selected":""}>${item.label}</option>
								</c:forEach>
							</select>
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