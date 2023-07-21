<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="isEdit" value ="${not empty storeCategory.storeCategoryId?true:false}"></c:set >
<title>${isEdit?fns:fy('编辑'):fns:fy('新增')}${fns:fy('商品分类')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="4"/>
<!-- 业务js -->
<script type="text/javascript"src="${ctx}/views/seller/store/storeCategoryForm.js"></script>
<script type="text/javascript">
	jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
</script>
<!--表示使用N号二级菜单 -->
<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i><span>${isEdit?fns:fy('编辑'):fns:fy('新增')}${fns:fy('商品分类')}</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('店铺')}</li>
						<li>${fns:fy('店铺管理')}</li>
						<li class="active">${isEdit?fns:fy('编辑'):fns:fy('新增')}${fns:fy('商品分类')}</li>
					</ul>
				</dt>
				<div class="sui-msg msg-tips msg-block">
					<div class="msg-con">
						<ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('上级分类、分类名称、排序、是否开启都是必填项。')}</li>
						</ul>
						<button type="button" data-dismiss="msgs" class="sui-close">×</button>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/store/storeCategory/save.htm">
					<dd>
						<input type="hidden" name="storeCategoryId" value="${storeCategory.storeCategoryId}">
						<div class="control-group">
							<label class="control-label">${fns:fy('上级分类：')}</label>
							<sys:treeselect id="parent" name="parent.id" value="${storeCategory.parent.id}" labelName="parent.name" labelValue="${storeCategory.parent.name}"
								title="上级分类" url="/store/storeCategory/treeData.htm" extId="${storeCategory.id}" cssClass="" allowClear="true"/>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请选择上级分类')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('分类名称：')}</label> 
							<input type="text" class="input-xlarge" name="name" value="${storeCategory.name}">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请添加分类名称')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('排序：')}</label> 
							<input type="text" class="input-xlarge" name="sort" value="${storeCategory.sort}" oninput = "value=value.replace(/[^\d]/g,'')">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请添加排序')}</div>
							</div>
						</div>
						<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('是否开启：')}</label>
							<input type="hidden" name="isOpenHidden" value="${storeCategory.isOpen}">
							<label class="radio-pretty inline ${storeCategory.isOpen== 0 ?'checked':''}">
								<input type="radio" name="isOpen" value="0"><span>${fns:fy('关闭')}</span>
							</label>
							<label class="radio-pretty inline ${storeCategory.isOpen == 1 || empty storeCategory.isOpen?'checked':''}" >
								<input type="radio" name="isOpen" value="1"><span>${fns:fy('开启')}</span>
							</label>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请选择是否开启')}</div>
						</div>
					</div>
					<shiro:hasPermission name="store:storeCategory:edit">
						<div class="text-align pb20">
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
						</div>
					</shiro:hasPermission>
					</dd>
				</form>
			</dl>
		</div>
	</div>
</body>
</html>