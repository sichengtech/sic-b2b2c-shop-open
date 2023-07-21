<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty dict.id?fns:fy("添加字典"):fns:fy("编辑字典")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysDictForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${empty dict.id?fns:fy("添加字典"):fns:fy("编辑字典")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/dict.do"> <i class="fa fa-user"></i> ${fns:fy("字典管理")}</a></li>
				<shiro:hasPermission name="sys:dict:edit">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("添加字典")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.字典管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示 结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" action="${ctxa}/sys/dict/save.do" id="inputForm" method="post">
						<input type="hidden" value="${dict.id}" name="id">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("值")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="value" value="${dict.value}" maxlength="50"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，字典的值，同一类型内不可重复")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("显示标签")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="label" value="${dict.label}" maxlength="50"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，字典的显示名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("类型")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="type" value="${dict.type}" maxlength="50"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，按类型对字典进行分类，按类型查找字典")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("排序")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="sort" value="${dict.sort}" maxlength="10"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写排序")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("描述")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="description" value="${dict.description}" maxlength="100"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写描述")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("备注")}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="remarks" value="${dict.remarks}" maxlength="255"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("可空，也可作为第三个值")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sys:dict:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
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
	<!-- panel开始 -->
</body>
</html>