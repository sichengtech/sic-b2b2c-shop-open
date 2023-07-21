<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${empty storeArticle.saId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('店铺文章')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeArticleForm.js"></script>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${empty storeArticle.saId ? fns:fy('添加文章') : fns:fy('编辑文章')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${empty storeArticle.saId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('店铺文章')}</li>
				</ul>
			</dt>
			<!-- 提示信息开始 -->
			<dd style="padding-top: 0px;  padding-bottom: 0px;" class="sui-row-fluid form-horizontal screen mb0 ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block" style="margin-right: 20px;">
					<div class="msg-con">
						 <ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('可以')}${empty storeArticle.saId?fns:fy('添加'):fns:fy('编辑')}${fns:fy('店铺文章')}</li>
						 </ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<!-- 提示信息结束 -->
			<sys:message content="${message}"/>
			<form class="sui-form form-inline goods-add-form" method="post" id="myForm" action="${ctxs}/store/storeArticle/${empty storeArticle.saId?'save2':'edit2'}.htm">
				<dd>
					<input type="hidden" name="saId" value="${storeArticle.saId}"/>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('文章标题：')}</label>
						<input type="text" class="input-xlarge" name="title" value="${storeArticle.title}" maxlength="32"/>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入文章标题')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('排序：')}</label>
						<input type="text" class="input-xlarge" name="sort" value="${storeArticle.sort}" maxlength="10">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入排序')}</div>
						</div>
					</div>
					<dl style="border-top: none;">
						<dt class="control-label"><b style="color: #f00;">*</b>${fns:fy('文章内容：')}</dt>
						<dd>
							<div class="controls">
								<!-- 加载编辑器的容器 -->
							    <script id="container" name="content" type="text/plain">${storeArticle.content}</script>
							    <!-- 配置文件 -->
							    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.config.js"></script>
							    <!-- 编辑器源码文件 -->
							    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.all.min.js"></script>
							    <!-- 实例化编辑器 -->
							    <script type="text/javascript">
							        var ue = UE.getEditor('container');
							        //传入参数表,添加到已有参数表里
							        ue.ready(function() {
							            ue.execCommand('serverparam', {'accessKey': '${accessKey}'});
							        });
							    </script>
						    </div>
						</dd>
					</dl>
					<div class="text-align pb20">
						<a href="javascript:void(0);" onclick="javascript:history.go(-1);" class="sui-btn btn-xlarge">${fns:fy('返回')}</a>
						<shiro:hasPermission name="store:storeArticle:edit">
							<button type="submit" name="submitBtn" class="sui-btn btn-xlarge btn-primary" value="1">${fns:fy('保存')}</button>
						</shiro:hasPermission>
					</div>
				</dd>
			</form>
		</dl>
		</div>
	</div>
</body>
</html>