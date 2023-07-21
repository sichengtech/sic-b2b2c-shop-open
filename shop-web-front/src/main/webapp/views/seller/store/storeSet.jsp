<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('店铺设置')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="4"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeSet.js"></script>
<script type="text/javascript" src="${ctx}/views/seller/logistics/selectArea.js"></script>
<!--表示使用N号二级菜单 -->
<style type="text/css">
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:111px !important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('店铺设置')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('店铺')}</li>
						<li>${fns:fy('店铺管理')}</li>
						<li class="active">${fns:fy('店铺设置')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('下面信息可以在前台店铺相关页面展示出来。如：店铺首页、店铺商品列表页、商品详情页等等。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/store/store/save2.htm">
					<dd>
						<input type="hidden" name="storeId" value="${store.storeId}" class="input-xlarge">
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺名称：')}</label>
							<input type="hidden" id="oldStoreName" name="oldStoreName" value="${store.name}" class="input-xlarge">
							<input type="text" name="name" value="${store.name}" class="input-xlarge" maxlength="18">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入公司名称,长度不要超过18个字符。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺所在地区：')}</label>
							<input type="hidden" name="provinceName" value="${store.provinceName}">
							<input type="hidden" name="cityName" value="${store.cityName}">
							<input type="hidden" name="districtName" value="${store.districtName}">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="provinceId" id="provinceId" value="${store.provinceId}"  onchange="selectCity(this)"><i class="caret"></i>
										<span id="provinceName">${empty store.provinceId?fns:fy('省'):store.provinceName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('省')}</a></li>
								 		<c:forEach items="${provinceList}" var="province">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${province.id}">${province.name}</a></li>
								 		</c:forEach>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="cityId" id="cityId" value="${store.cityId}" onchange="selectDistrict(this)"><i class="caret"></i>
										<span id="cityName">${empty store.cityId?fns:fy('市'):store.cityName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="cityAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('市')}</a></li>
								 	</ul>
								</span>
						 	</span>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input type="hidden" name="districtId" id="districtId" value="${store.districtId}"><i class="caret"></i>
										<span id="districtName">${empty store.districtId?fns:fy('县'):store.districtName}</span>
									</a>
								 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="districtAttr">
								 		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('县')}</a></li>
								 	</ul>
								</span>
						 	</span>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写店铺所在地址')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('完整详细地址：')}</label> 
							<input type="text" name="detailedAddress" value="${store.detailedAddress}" class="input-xlarge" maxlength="255">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入完整详细地址,长度不要超过64个字符。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺主营产品：')}</label>
							<textarea rows="3" cols="" name=industry class="input-xlarge" maxlength="255">${store.industry}</textarea>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请用,分隔；例如：男装,女装；不要超过50个字符。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('店铺LOGO：')}</label> 
					        <div class="input-append">
			        			<input type="hidden" class="imgPath" name="logo" value="${store.logo}"/>
			        			<div id="vessel1"></div>
			        		</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('建议上传宽340*164像素内的图片')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺横幅：')}</label> 
					        <div class="input-append">
			        			<input type="hidden" class="imgPath" name="banner" value="${store.banner}"/>
			        			<div id="vessel2"></div>
			        		</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('建议上传1920*150像素图片（主要内容在1200以内，超出屏幕尺寸图片会被截断）')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺客服电话：')}</label>
							<input type="text" name="storeTel" value="${store.storeTel}" class="input-xlarge" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请输入用于交易联系的店铺客服，方便买家进行咨询沟通。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺联系QQ：')}</label> 
							<input type="text" name="storeQq" value="${store.storeQq}" class="input-xlarge" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('如您使用腾讯QQ进行交易联系方式，可在此处填写。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('店铺联系微信：')}</label>
							<input type="text" name="storeWechat" value="${store.storeWechat}" class="input-xlarge" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('如您使用微信进行交易联系方式，可在此处填写。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('SEO-Title：')}</label> 
							<input type="text" name="seoTitle" value="${store.seoTitle}" class="input-xlarge" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('首页的标题，建议使用：“主营产品关键词_公司名称”。2至4个关键词为佳，重要关键词靠前。如：胎压监测_倒车雷达_XXXX有限公司。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('SEO关键字：')}</label> 
							<input type="text" name="seoKeyword" value="${store.seoKeyword}" class="input-xlarge" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('主营产品关键词，建议使用：“主营产品关键词_区域关键词_品牌关键词”。3至6个关键词为佳，重要关键词靠前。如：胎压监测，倒车雷达。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('SEO店铺描述：')}</label>
							<textarea rows="3" cols="" name="seoDescribe" class="input-xlarge" maxlength="255">${store.seoDescribe}</textarea>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('网站的描述，相当于一篇文章的总结。通常在百度、google等搜索的结果页中显示，良好的描述相当于网页的广告词，直接吸引用户来点击贵公司的网站。建议使用：公司简介与主营产品关键词连成一句畅通的话，并且尽可能的出现一次title标题中的关键词。如：XXXX电子股份有限公司在市场营销、品质保证、售后服务等方面取得了令人瞩目的成绩，主要制造、销售安全防范产品、防盗设备、电子产品、汽车音响等。')}</div>
							</div>
						</div>
						<shiro:hasPermission name="store:store:edit">
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