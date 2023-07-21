<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>编辑字典</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysDictSave.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">编辑字典</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctx}/views/admin/sys/sysDictList.jsp"> <i class="fa fa-user"></i> 字典管理</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 编辑字典</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示 end -->	 
			 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 值&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">值不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写数字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 显示标签&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">显示标签不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写显示标签<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 类型&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">类型不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="10"> 
								<label for="firstname" class="error">排序不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存 
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel end -->
</body>
</html>