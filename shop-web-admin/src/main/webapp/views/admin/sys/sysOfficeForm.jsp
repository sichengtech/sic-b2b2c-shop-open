<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${not empty office.id==true?fns:fy("编辑机构"):fns:fy("添加机构")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%> 
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysOfficeForm.js"></script>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${not empty office.id==true?fns:fy("编辑机构"):fns:fy("添加机构")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/office/list.do"> <i class="fa fa-home"></i> ${fns:fy("机构列表")}</a></li>
				<shiro:hasPermission name="sys:office:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${not empty office.id==true?fns:fy("机构编辑"):fns:fy("机构添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.机构管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/office/save.do" method="post">
						<input type="hidden" name="id" value="${office.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("上级机构")}&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
									title="机构" url="/sys/office/treeData.do" extId="${office.id}" cssClass="input-sm"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("归属区域")}&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
									title="区域" url="/sys/area/treeData.do" cssClass="input-sm"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("机构名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="name" value="${office.name}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("机构编码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="code" value="${office.code}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">${fns:fy("机构类型")}&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15 input-sm" name="type">
									<c:forEach items="${fns:getDictList('sys_office_type')}" var="sot">
										<option value="${sot.value}" ${office.type eq la.value?"selected":""}>${sot.label}</option>
									</c:forEach>
								<select> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">${fns:fy("机构类别")}&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15 input-sm" name="grade">
									<c:forEach items="${fns:getDictList('sys_office_grade')}" var="sog">
										<option value="${sog.value}" ${office.grade eq la.value?"selected":""}>${sog.label}</option>
									</c:forEach>
								<select> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("是否可用")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="useable" ${office.useable eq 0?"":"checked"} data-size="small" value="1" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("主负责人")}&nbsp;:</label>
							<div class="col-sm-5">
								 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
									title="用户" url="/sys/office/treeData.do?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-sm"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("副负责人")}&nbsp;:</label>
							<div class="col-sm-5">
								 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
									title="用户" url="/sys/office/treeData.do?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-sm"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("联系地址")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="address" value="${office.address}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("邮政编码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="zipCode" value="${office.zipCode}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("负责人")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="master" value="${office.master}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("电话")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="phone" value="${office.phone}"> 
							</div>
						</div>
						<div class="form-group" >
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("传真")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="fax" value="${office.fax}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("邮箱")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="email" value="${office.email}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("备注")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control" name="remarks">${office.remarks}</textarea>
							</div>
							<div class="col-sm-5"></div>
						</div>
						<c:if test="${empty office.id}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("快速添加下级部门")}:&nbsp;:</label>
								<div class="col-sm-4 icheck">
									<c:forEach items="${fns:getDictList('sys_office_common')}" var="soc" >
										<div class="square-blue single-row">
											<div class="checkbox">
												<c:set var="checked" value=""></c:set>
												<c:forEach items="${office.childDeptList}" var="item" >
													<c:if test="${item==soc.id}">
													<c:set var="checked" value="checked"></c:set>
													</c:if>
												</c:forEach>
												<input type="checkbox" style="display: none" ${checked} data-switch-no-init name="childDeptList" value="${soc.value}">
												<label>${soc.label}</label>
											</div>
										</div> 
									</c:forEach>
								</div>
								<div class="col-sm-5">
									<p class="help-block"><p>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sys:office:edit">
								<button type="submit" name="submit" class="btn btn-info submitBtn" value="2">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>