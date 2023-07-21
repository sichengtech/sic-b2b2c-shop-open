<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("seo设置管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSeoForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteSeo.id?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("seo设置")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteSeo/list.do"> <i class="fa fa-user"></i> ${fns:fy("seo设置列表")}</a></li>
				<shiro:hasPermission name="site:siteSeo:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("seo设置")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>
						${fns:fy("seo设置使用的变量")}：1.<span>$</span>{siteName}--${fns:fy("商城名")}、2.<span>$</span>{categoryName}--${fns:fy("分类名")}、
						3.<span>$</span>{productName}--${fns:fy("商品名")}、4.<span>$</span>{storeName}--${fns:fy("店铺名")}、5.<span>$</span>{searchKey}--${fns:fy("搜索关键字")}
					</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteSeo/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteSeo.id}">
						<input id="oldType" name="oldType" type="hidden" value="${type}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("业务类型")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('site_seo_type')}" var="item">
									<option value="${item.label}" ${item.label==siteSeo.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请选择业务类型")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> Title&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="title"  maxlength="255" class="form-control input-sm" value="${siteSeo.title}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("示例")}：<span>$</span>{siteName}-${fns:fy("中小企业B2B汽车产业供应链垂直电商平台")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> Keywords&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="keywords" class="form-control input-sm" maxlength="255" rows="5" data-parsley-id="8">${siteSeo.keywords}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">
									${fns:fy("示例")}：<span>$</span>{searchKey}${fns:fy("现货")},<span>$</span>{searchKey}${fns:fy("报价")},
									<span>$</span>{searchKey}${fns:fy("市场")},<span>$</span>{searchKey}${fns:fy("行情")},<span>$</span>{searchKey}${fns:fy("交易")}
								<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> Description&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="description" class="form-control input-sm" maxlength="255" rows="5" data-parsley-id="8">${siteSeo.description}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("建议25-256字之间")}${fns:fy("示例")}：<span>$</span>{siteName}-<span>$</span>{storeName}-<span>$</span>{productName}<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteSeo:edit">
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
	<!-- panel结束 -->
</body>
</html>