<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品统计管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSpuAnalyzeForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productSpuAnalyze.PId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商品统计</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productSpuAnalyze/list.do"> <i class="fa fa-user"></i> 商品统计列表</a></li>
				<shiro:hasPermission name="product:productSpuAnalyze:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商品统计${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品统计管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productSpuAnalyze/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pId" value="${productSpuAnalyze.PId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商品spu的id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pId"  maxlength="19" class="form-control input-sm" value="${productSpuAnalyze.PId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商品spu的id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 总浏览量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="allBrowse"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.allBrowse}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写总浏览量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 总浏览量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="allBrowseDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.allBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写总浏览量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 周浏览量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="weekBrowse"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.weekBrowse}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写周浏览量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 周浏览量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="weekBrowseDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.weekBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写周浏览量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 月浏览量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="monthBrowse"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.monthBrowse}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写月浏览量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 月浏览量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="monthBrowseDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.monthBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写月浏览量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 三个月浏览量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="month3Browse"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.month3Browse}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写三个月浏览量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 三个月浏览量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="month3BrowseDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.month3BrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写三个月浏览量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 总销量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="allSales"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.allSales}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写总销量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 总销量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="allSalesDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.allSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写总销量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 周销量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="weekSales"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.weekSales}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写周销量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 周销量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="weekSalesDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.weekSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写周销量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 月销量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="monthSales"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.monthSales}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写月销量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 月销量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="monthSalesDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.monthSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写月销量更新日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 三个月销量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="month3Sales"  maxlength="10" class="form-control input-sm" value="${productSpuAnalyze.month3Sales}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写三个月销量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 三个月销量更新日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="month3SalesDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${productSpuAnalyze.month3SalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写三个月销量更新日期<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productSpuAnalyze:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
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