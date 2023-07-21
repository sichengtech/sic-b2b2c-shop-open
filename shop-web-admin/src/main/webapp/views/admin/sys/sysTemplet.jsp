<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("模板管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<%@include file="/views/admin/include/treeview.jsp" %>

<!-- codemirror html编辑器 -->
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/lib/codemirror.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/hint/show-hint.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/hint/xml-hint.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/hint/html-hint.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/mode/xml/xml.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/mode/javascript/javascript.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/mode/css/css.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/mode/htmlmixed/htmlmixed.js"></script>
<!-- 当前行高亮 -->
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/selection/active-line.js"></script> 
<!-- 全屏 -->
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/display/fullscreen.js"></script>
<!-- 寻找闭合标签 -->
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/fold/xml-fold.js"></script>
<script type="text/javascript" src="${ctxStatic}/codemirror-5.25.0/addon/edit/matchtags.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTemplet.js"></script>

<!-- codemirror html编辑器 -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/codemirror-5.25.0/lib/codemirror.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/codemirror-5.25.0/addon/hint/show-hint.css">
<!-- 全屏 -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/codemirror-5.25.0/addon/display/fullscreen.css">
<!-- codemirror html编辑器的css -->
<style type="text/css">
  .CodeMirror {border-top: 1px solid #888; border-bottom: 1px solid #888;}
  .CodeMirror {
    border: 1px solid #eee;
    height: 360px;//编辑器高度
  }
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("模板管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right"></ul>
		</header>
		<div class="panel-body">
			<div class="col-sm-3" style="height:600px; overflow:auto;">
				<div class="alert alert-default alert-block fade in" style="margin-left: -25px">
					<h5>${fns:fy("模板文件列表")}
					<button id="treeRefresh" type="button" class="tooltips btn btn-success btn-xs" data-placement="bottom" title="${fns:fy("刷新")}">
						<i class="fa fa-rotate-right"></i>
					</button>
					</h5>
					<ul style="margin-left: -25px">
						<div id="tree" class="ztree"></div>
					</ul>
				</div>
			</div>
			<div class="col-sm-9">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>${fns:fy("操作提示")}</h5>
					<ul>
						<li><strong>F11</strong> ${fns:fy("进入全屏")}，<strong>Esc</strong> ${fns:fy("退出全屏")}.</li>
						<li><strong>ctrl+space</strong> ${fns:fy("代码提示")}</li>
						<li>${fns:fy("单击Html标签寻找闭合标签(高亮)")}<strong>ctrl+J</strong> ${fns:fy("跳到配对的闭合标签")}</li>
					</ul>
				</div>
				<!-- 提示 end --> 
				<!-- 多行文本框开始 -->
				<div class="form-actions">
					<shiro:hasPermission name="cms:template:edit">
						<button id="create" type="button" class="btn btn-info">
							<i class="glyphicon glyphicon-plus"></i> ${fns:fy("创建模板")}
						</button>
					</shiro:hasPermission>
				</div>
				<div style="margin-bottom: 10px;margin-top: 10px;">
					${fns:fy("当前目录")}:<span id="path">${name}</span>
					<span id="templateName" style="margin-left:100px; display:none"">
					${fns:fy("文件名")}：<input id="fileName" name="fileName" value="" placeholder="${fns:fy("请输入文件名.扩展名")}"/>
					</span>
				</div>
				<div id="templateContent" style="margin-top: 10px;">
					<%-- <form id="inputForm" action="${ctxa}/cms/template/save.do" method="post" class="form-horizontal"> --%>
					<input id="fullName" type="hidden" name="name" value="${template.name}"/>
					<sys:message content="${message}"/>
					<div class="form-group">
						<div id="code"></div>
					</div>
					<div class="form-actions">
						<shiro:hasPermission name="cms:template:edit">
							<button id="save" type="button" class="btn btn-info">
								<i class="fa fa-check"></i> ${fns:fy("保存")}
							</button>
						</shiro:hasPermission>
							<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
								<i class="fa fa-times"></i> ${fns:fy("返回")}
							</button>
					</div>
					<!-- </form> -->
				</div>
				<div id="directoryList" style="margin-top: 10px;display:none">
					<table class="table table-hover table-condensed table-bordered">
						<thead>
							<tr>
								<th>${fns:fy("文件名")}</th>
								<th>${fns:fy("大小")}</th>
								<th>${fns:fy("最后修改时间")}</th>
								<th>${fns:fy("操作")}</th>
							</tr>
						</thead>
						<tbody id="tbody"></tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
<script type="text/template" id="tplTable_tpl" info="${fns:fy("表格模板")}">
<tr>
	<td>{{d.fileName}}</td>
	<td>{{d.size}}KB</td>
	<td>{{d.lastModifiedDate}}</td>
	<td fullName="{{d.fullName}}" fileName="{{d.fileName}}" dir="{{d.dir}}" isDirectory="{{d.isDirectory}}" >
		<a class="btn btn-info btn-sm editTempateFile" style="margin-right: 5px;"><i class="fa fa-edit"></i> ${fns:fy("编辑")}</a>
		<a class="btn btn-danger btn-sm deleteSure"><i class="fa fa-trash-o"></i> ${fns:fy("删除")}</a>
	</td>
</tr>
</script>	
</body>
</html>