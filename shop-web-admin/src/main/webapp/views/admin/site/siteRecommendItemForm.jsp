<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("推荐位管理详情")}</title>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteRecommendItemForm.js"></script>
<!-- 文件上传 -->
<script type="text/javascript" src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/fdp/upload.js"></script>
<style type="text/css">
	body{font-size: 12px;}
	.well {padding: 15px;background: #fff;min-height: 20px;margin-bottom: 30px;}
	.shopdate-list .img-list li {width: 23%;height:138px;line-height: 120px; padding: 1%; margin: 1%;text-align: center;list-style: none outside none;float: left;
		position: relative;z-index: 1; background-color: #FFF;border: dashed 1px #EEE;}
	.shopdate-list .img-list li a img {max-width: 100%;max-height: 120px;}
	.shopdate-list li .del{display: none;position: absolute;z-index: 1;top: 1px;right: 1px;width: 24px; height: 24px;
		text-align: center;line-height: 24px; background-color: #FE9700;}
	.shopdate-list .img-list{margin-bottom: 15px;overflow: hidden;padding-left: 0px;}
	.shopdate-list li.selected .del{display: block;}
	.shopdate-list li.selected{background-color: #FFF3DA;border-color: #FBCFAC;}
	.shopdate-list li .del i.fa {color: #FFF;}
	.first-form-group{border-top: 1px solid #eff2f7;padding-top: 10px;}
	.text-info{color: #49b6d6;font-size: 12px;}
	#point{color: #49b6d6;}
	#point ul{padding-left: 0px;}
	.text-warning{color: #f59c1a;}
	.alert{padding: 0 0 0 15px;}
	#toolbar_operate_tips{margin-left: 5px;}
	.panel-heading{position: fixed;width: 100%;top: 0px;background: #fff;z-index: 9;}
</style>
<script type="text/javascript">
	var type=${type};
	var siteRecommendItemList=${siteRecommendItemList};
</script>
</head>
<body style="background: #ffffff;">
	<!-- panel开始 -->
	<section class="panel">
		<!-- panel-body开始 -->
		<div style="height: 50px;">
			<header class="panel-heading">
			 	${type=='2'?fns:fy("推荐商品"):fns:fy("推荐图片")}
			 	<button id="toolbar_operate_tips" class="btn tooltips btn-info btn-xs" title="${fns:fy("操作提示")}" data-placement="bottom">
					<i class="fa fa-lightbulb-o"></i> ${fns:fy("提示")}
				</button>
			</header>
		</div>
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<ul>
					<li>${fns:fy("点击")} <span class="btn btn-xs btn-default"><i class="fa fa-plus"></i>${fns:fy("添加新内容")}</span> ${fns:fy("网站设置.推荐位.操作提示8")}</li>
					<li>${fns:fy("网站设置.推荐位.操作提示9")}<span class="btn btn-xs btn-warning"><i class="fa fa-trash-o"></i></span>${fns:fy("按钮后再点击")}<span class="btn btn-xs btn-info"><i class="fa fa-check"></i>${fns:fy("保存")}</span>${fns:fy("生效")}</li>
					<c:if test="${type=='2'}">
						<li>${fns:fy("网站设置.推荐位.操作提示10")} <span class="btn btn-xs btn-info"><i class="fa fa-plus"></i>${fns:fy("推荐")}</span>${fns:fy("网站设置.推荐位.操作提示13")}  <span class="btn btn-xs btn-default"><i class="fa fa-edit"></i>${fns:fy("换图")}</span>${fns:fy("按图更换图片")}</li>
					</c:if>
					<li class="text-warning">${fns:fy("网站设置.推荐位.操作提示11")}</li>
					<li>${fns:fy("完成全部设置后，点击")}<span class="btn btn-xs btn-info"><i class="fa fa-check"></i>${fns:fy("保存")}</span>${fns:fy("按钮，完成数据提交")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<!-- 内容区域开始 -->
			<div class="well shopdate-list">
				<ul class="img-list"></ul>
				<button type="button" class="btn btn-sm btn-default addContent" data-nc-new-image-li="" style="display: inline-block;"><i class="fa fa-plus m-r-5"></i> ${fns:fy("添加新内容")} </button>
			</div>
			<!-- 内容区域结束 -->
			<div class="tab-content" style="margin-top:15px;">
				<p>${fns:fy("标签对应内容编辑")}</p>
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="" method="post">
						<input type="hidden" name="recommendId" value="${recommendId}"/>
						<c:if test="${type=='2'}">
							<div class="form-group first-form-group">
								<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("推荐商品")}&nbsp;:</label>
								<div class="col-sm-10">
									<div class="col-sm-8" style="padding-left: 0;">
										<input type="text" name="pId"  maxlength="18" class="form-control input-sm productId" value="" placeholder="${fns:fy("请输入商品编号")}"/>
									</div>
									<div class="col-sm-3" style="text-align: center;">
										<button type="button" class="btn btn-sm btn-info recommend"> <i class="fa fa-plus m-r-5"></i> ${fns:fy("推荐")}</button>
										<button type="button" class="btn btn-sm btn-default uploadBtn changeImg"> <i class="fa fa-edit"></i> ${fns:fy("换图")}</button>
										<input id="fileUpload_photo" name="fileUpload" type="file" thumbnail="120x120" tokenPath="${ctxa}/sys/sysToken/getToken.do" suffix="photo" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass file hide"/>
										<input type="hidden" class="existSize_photo" value="0"/>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${type=='1'}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("图片")}&nbsp;:</label>
								<div class="col-sm-8">
									<p class="text-info">${fns:fy("网站设置.推荐位.操作提示12")}</p>
									<!-- <input type="file" escapeXml="true" name="name" class="file hide"/> -->
									<input id="fileUpload_photo" name="fileUpload" type="file" thumbnail="120x120" tokenPath="${ctxa}/sys/sysToken/getToken.do" suffix="photo" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass file hide"/>
									<button type="button" class="btn btn-sm btn-default uploadBtn"> <i class="fa fa-upload"></i> ${fns:fy("上传图片")}</button>
									<input type="hidden" class="existSize_photo" value="0"/>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("操作")}&nbsp;:</label>
							<div class="col-sm-10">
								<div class="col-sm-2" style="padding-left: 0px;">
									<select class="selectpicker form-control input-sm itemMsg" name="operationType" id="type" style="padding: 5px 4px;"> 
										<!-- <option value="1">无操作</option>
										<option value="2">链接地址</option>
										<option value="3">关键字</option>
										<option value="4">商品编号</option>
										<option value="5">店铺编号</option>
										<option value="6">商品分类</option> -->
										<c:forEach items="${fns:getDictList('site_recommend_operation_type')}" var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-9" style="padding-left: 0px;">
									<input type="text" name="operationContent"  maxlength="255" class="form-control input-sm linkValue itemMsg" value="" readonly="readonly" placeholder="${fns:fy("点击内容不进行任何形式的链接操作")}"/>
								</div>
							</div>
						</div>
						<c:if test="${type=='1'}">
							<div class="form-group first-form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("附加值")}1&nbsp;:</label>
								<div class="col-sm-9">
									<input type="text" name="addInfo1"  maxlength="128" class="form-control input-sm itemMsg" value="" placeholder="${fns:fy("请输入其他的说明信息")}"/>
								</div>
							</div>
							<div class="form-group first-form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("附加值")}2&nbsp;:</label>
								<div class="col-sm-9">
									<input type="text" name="addInfo2"  maxlength="128" class="form-control input-sm itemMsg" value="" placeholder="${fns:fy("请输入其他的说明信息")}"/>
								</div>
							</div>
							<div class="form-group first-form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("附加值")}3&nbsp;:</label>
								<div class="col-sm-9">
									<input type="text" name="addInfo3"  maxlength="128" class="form-control input-sm itemMsg" value="" placeholder="${fns:fy("请输入其他的说明信息")}"/>
								</div>
							</div>
							<div class="form-group first-form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("附加值")}4&nbsp;:</label>
								<div class="col-sm-9">
									<input type="text" name="addInfo4"  maxlength="128" class="form-control input-sm itemMsg" value="" placeholder="${fns:fy("请输入其他的说明信息")}"/>
								</div>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<%-- 引入脚部文件 --%>
	<%@ include file="../include/foot.jsp"%>
	<script type="text/template" id="add_content_tpl" info="${fns:fy("添加新内容模板")}">
		<li class="{{d.class}}" id="{{d.id}}" operationtype="1">
			<input type="hidden" class="imgPath" name="path" value="{{d.path2}}"/>
			<a href="javascript:;">
				<img src="{{d.path}}" alt="" class="preview">
			</a>
			<a href="javascript:;" data-nc-delete="" class="del" title="${fns:fy("删除")}">
				<i class="fa fa-trash-o"></i>
			</a>
		</li>
	</script>
</body>
</html>