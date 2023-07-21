<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺装修')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeDecorationSave1.js"></script>

<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
	.store_decoration .store_decoration_div1 {display: inline-block;width: 30%;border: solid 4px #AFAAAA;text-align: center}
	.store_decoration h2 {text-align: center;}
	.store_decoration img {height: 500px;margin: 0 auto;}
	.footer-main{ margin:0px;}
</style>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('店铺装修')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('店铺')}</li>
					<li>${fns:fy('店铺管理')}</li>
					<li class="active">${fns:fy('店铺装修')}</li>
				</ul>
			</dt>
			<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block">
					<div class="msg-con">
						<ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('请选择店铺模板，如未选择默认模板一')}</li>
							<li>${fns:fy('模板一：按照系统预设值风格进行显示。')}</li>
							<li>${fns:fy('模板二：店铺首页背景、头部以及店铺整体风格都将根据店铺主人设置的内容进行显示，建议上传宽度为1200px至1920px的图片。')}</li>
							<li>${fns:fy('模板三：店铺首页背景、头部以及店铺整体风格都将根据店铺主人设置的内容进行显示并显示店铺推荐商品，建议上传宽度为1200px至1920px的图片。')}</li>
						</ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</address>
			<sys:message content="${message}"/>
			<form id="inputForm" class="sui-form form-inline" action="${ctxs}/store/storeDecoration/save2.htm" method="post">
				<input id="solution" type="hidden" name="solution" value="${storeDecorate.solution}">
				<dd class="store_decoration">
					<div class="store_decoration_div1" style="${storeDecorate.solution==1?'border: solid 5px #DD2726':''}" >
						<h2>${fns:fy('模板一')}</h2>
						<img alt="" src="${ctxStatic}/sicheng-seller/images/zhuangxiumoban/moban01.png" width="80%">
					</div>
					<div class="store_decoration_div1" style="margin-left: 3%;${storeDecorate.solution==2?'border: solid 5px #DD2726':''}">
						<h2>${fns:fy('模板二')}</h2>
						<img alt="" src="${ctxStatic}/sicheng-seller/images/zhuangxiumoban/moban02.png" width="80%">
					</div>
					<div class="store_decoration_div1" style="margin-left: 3%;${storeDecorate.solution==3?'border: solid 5px #DD2726':''}">
						<h2>${fns:fy('模板三')}</h2>
						<img alt="" src="${ctxStatic}/sicheng-seller/images/zhuangxiumoban/moban03.png" width="80%">
					</div>
				</dd>
				<dd class="bs-docs-example" style="${storeDecorate.solution!= 1?'':'display:none'}">
					<h3>${fns:fy('店铺装修内容')}</h3>
					<dl class="bor-t-fff">
						<dd>
							<div class="controls">
								<!-- 加载编辑器的容器 -->
							    <script id="container" name="content" type="text/plain">${storeDecorate.content}</script>
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
				</dd>
				<shiro:hasPermission name="store:storeDecoration:edit">
				<div class="text-align pb20">
					<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
				</div>
				</shiro:hasPermission>
			</form>
		</dl>
	</div>
</div>
</body>
</html>