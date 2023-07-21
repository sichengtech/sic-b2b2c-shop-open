<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>会员（卖家）管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userSellerForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userSeller.UId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}会员（卖家）</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userSeller/list.do"> <i class="fa fa-user"></i> 会员（卖家）列表</a></li>
				<shiro:hasPermission name="sso:userSeller:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 会员（卖家）${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>会员（卖家）管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userSeller/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userSeller.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键(卖家表，入驻申请用1个id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${userSeller.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键(卖家表，入驻申请用1个id)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isOpen" value="${item.value}" ${item.value==userSeller.isOpen?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 店铺id(店铺表，店铺二级域名，店铺相册空间信息表用1个id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeId"  maxlength="19" class="form-control input-sm" value="${userSeller.storeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写店铺id(店铺表，店铺二级域名，店铺相册空间信息表用1个id)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sso:userSeller:edit">
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