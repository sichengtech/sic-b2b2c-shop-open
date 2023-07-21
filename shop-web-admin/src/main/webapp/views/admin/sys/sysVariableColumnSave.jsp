<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>添加字段</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysVariableColumnSave.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">添加字段</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> Column列表</a></li>
				<li class=""><a href="${ctx}/views/admin/sys/sysVariableColumnSave.jsp"> <i class="fa fa-user"></i> Column添加</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>为表添加一个字段</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 槽位键&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">本字段占用的槽位<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 字段名&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">字段显示名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 数据类型&nbsp;: </label>
							<div class="col-sm-5">
								<select class="selectpicker form-control input-sm " > 
								 <option value="1">String</option> 
								 <option value="2">Date</option> 
								 <option value="3">Integer</option> 
								</select>
								<label for="firstname" class="error">类型不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">存储的数据的类型：string,date,integer<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 最大长度&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="10"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">内容最大长度，0表示不限<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 最小长度&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="1"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">内容最小长度，0表示不限<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">非空&nbsp;:</label>
							<div class="col-sm-5">
							 <input type="checkbox" checked data-size="small" style="display: none" data-on-text="非空" data-off-text="可空"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">本字段是否不可为为空值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 正则表达式&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="10"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">验证表单输入的正则表达式<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 正则的说明&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="10"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">正则表达式的中文说明信息<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 提示信息&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="您输入的数据不合格"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">验证失败提示信息<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 帮助提示信息&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="请输入您的身份证号码"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">帮助提示信息<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 字典&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" value="type"> 
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请输入字典的名称（下拉选、复选框 的数据来自字典）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 控件类型&nbsp;: </label>
							<div class="col-sm-5">
								<select class="selectpicker form-control input-sm " > 
								 <option value="0">请选择</option> 
								 <option value="1">单行文本框</option> 
								 <option value="2">下拉选</option> 
								 <option value="3">单选组</option> 
								 <option value="4">复选组</option>
								 <option value="5">多行文本框</option>
								</select>
								<label for="firstname" class="error">必填项</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">控件类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 样式&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" value="maring:10px;"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">style样式,原样输出到页面上<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" value="10"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">启用&nbsp;:</label>
							<div class="col-sm-5">
							 <input type="checkbox" checked data-size="small" style="display: none" data-on-text="启用" data-off-text="停用"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block"> <p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								 <button type="submit" class="btn btn-info">
									<i class="fa fa-check-circle"></i> 保存并继续添加
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
	<!-- panel结束 -->
</body>
</html>