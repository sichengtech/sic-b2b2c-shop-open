<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="isEdit" value ="${not empty storeNavigation.storeNavigationId?true:false}"></c:set >
<title>${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('导航')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeNavigationForm.js"></script>
<style type="text/css">
	.sui-msg.msg-block{margin:10px!important;}
</style>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			
			<dt class="box-header">
				<h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('导航')}</span></h4>
				<ul class="sui-breadcrumb">
				<li>${fns:fy('当前位置:')}</li>
				<li>${fns:fy('>店铺')}</li>
				<li>${fns:fy('店铺管理')}</li>
				<li class="active">${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('导航')}</li></ul>
			</dt>
			<div class="sui-msg msg-tips msg-block">
			 <div class="msg-con">
				 <ul>
					<h4>${fns:fy('提示信息')}</h4>
					<li>${fns:fy('建立店铺导航，导航内容将出现在店铺主导航上')}</li>
				 </ul>
				<button type="button" data-dismiss="msgs" class="sui-close">×</button>
			 </div>
			 <s class="msg-icon" style="margin-top: 10px"></s>
			</div>
			<sys:message content="${message}"/>
			<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/store/storeNavigation/${isEdit?'edit2':'save2'}.htm">
				<dd>
					<input type="hidden" name="storeNavigationId" value="${storeNavigation.storeNavigationId}">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('导航名称：')}</label>
						<input type="text" class="input-xlarge" name="name" value="${storeNavigation.name}">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入导航名称')}</div>
						</div>
					</div>
					<div class="control-group">
						<input type="hidden" name="isOpenHidden" value="${storeNavigation.isOpen}">
						<label class="control-label"><font color="red">*</font>${fns:fy('是否显示：')}</label>
							<label class="radio-pretty inline ${storeNavigation.isOpen == 1 || empty storeNavigation.isOpen?'checked':''}" >
								<input type="radio" name="isOpen" value="1"><span>${fns:fy('显示')}</span>
							</label>
							<label class="radio-pretty inline ${storeNavigation.isOpen== 0 ?'checked':''}">
								<input type="radio" name="isOpen" value="0"><span>${fns:fy('不显示')}</span>
							</label>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请选择是否显示')}</div>
						</div>
					</div>
					<div class="control-group">
						<input type="hidden" value="${storeNavigation.target}" name="targetHidden">
						<label class="control-label"><font color="red">*</font>${fns:fy('是否新窗口打开：')}</label>
							<label class="radio-pretty inline ${storeNavigation.target=='2' || empty storeNavigation.target?'checked':''}">
								<input type="radio" name="target" value="2"><span>${fns:fy('是')}</span>
							</label>
							<label class="radio-pretty inline ${storeNavigation.target=='3'?'checked':''}" >
								<input type="radio" name="target" value="3"><span>${fns:fy('否')}</span>
							</label>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请选择是否新窗口打开')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('排序：')}</label>
						<input type="text" class="input-xlarge" name="sort" value="${isEdit?storeNavigation.sort:'10'}">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入排序')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('导航链接：')}</label>
						<input id="url" type="text" class="input-xlarge" name="url" value="${storeNavigation.url}">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入导航链接')}</div>
						</div>
					</div>
					<shiro:hasPermission name="store:storeNavigation:edit">
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