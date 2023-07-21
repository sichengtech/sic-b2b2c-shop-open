<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${not empty article.id==true?fns:fy("编辑文章"):fns:fy("添加文章")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%> 
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/cms/siteArticleForm.js"></script>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${not empty article.id==true?fns:fy("编辑文章"):fns:fy("添加文章")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/cms/article.do"> <i class="fa fa-home"></i> ${fns:fy("文章列表")}</a></li>
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${not empty article.id==true?fns:fy("编辑文章"):fns:fy("添加文章")}</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("文章管理.文章管理.操作提示3")}</li>
					<li>${fns:fy("文章管理.文章管理.操作提示4")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/cms/article/save.do" method="post">
						<input type="hidden" name="id" value="${article.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("归属栏目")}&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
								title="${fns:fy('栏目')}" url="/cms/category/treeData.do" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="input-sm"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("外部链接")}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="col-sm-4">
									 <input type="checkbox" id="url" data-switch-no-init onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()">
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group" style="display: none" id="linkBody">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("外部链接")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="link" class="form-control input-sm" type="text" name="link" value="${article.link}"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("标题")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="title" value="${article.title}"> 
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2">颜色&nbsp;:</label>
							<div class="col-sm-5">
								<select class="form-control m-bot15 input-sm" name="color">
									<option value="">请选择颜色</option>
									<c:forEach items="${fns:getDictList('color')}" var="la">
										<option value="${la.value}" ${article.color eq la.value?"selected":""}>${la.label}</option>
									</c:forEach>
								<select> 
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("关键字")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="keywords" value="${article.keywords}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("多个关键字，用逗号分隔")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="weight" class="form-control input-sm" type="text" name="weight" value="${empty(article.weight)?'10':article.weight}"> 
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否置顶&nbsp;:</label>
							<div class="col-sm-5 ">
								<input type="checkbox" id="weightTop" data-switch-no-init onclick="$('#weight').val(this.checked?'999':'0')">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">过期时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input type="text" name="weightDate" class="form-control input-sm" format="yyyy-MM-dd" readonly="readonly" 
									value="<fmt:formatDate value="${article.weightDate}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"> 
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">数值越大排序越靠前，过期时间可为空，过期后取消置顶。<p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("描述、摘要")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="description" value="${article.description}">
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写描述、摘要")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("缩略图")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="image" value="${article.image}"/>
								<div id="vessel"></div>
							</div>	
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("文章管理.文章管理.操作提示5")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("正文")}&nbsp;:</label>
							<div class="col-sm-10">
								<div class="controls">
									<!-- 加载编辑器的容器 -->
								    <script id="container" name="articleData.content" type="text/plain">${article.articleData.content}</script>
								    <!-- 配置文件 -->
								    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.config.js"></script>
								    <!-- 编辑器源码文件 -->
								    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.all.min.js"></script>
								    <!-- 实例化编辑器 -->
								    <script type="text/javascript">
								        var ue = UE.getEditor('container',{
								        	topOffset:50
											,maximumWords:10000000
								        });
								        //传入参数表,添加到已有参数表里
								        ue.ready(function() {
								            ue.execCommand('serverparam', {'accessKey': '${accessKey}'});
								        });								        
								    </script>
							    </div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("来源")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="copyfrom" class="form-control input-sm" type="text" placeholder="" name="articleData.copyfrom" value="${article.articleData.copyfrom}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">相关文章&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" id="articleDataRelation" name="articleData.relation" value="${article.articleData.relation}">
								<ol id="articleSelectList"></ol>
								<a id="relationButton" href="javascript:" class="btn btn-info">添加相关</a>
								<script type="text/javascript">
									var articleSelect = [];
									function articleSelectAddOrDel(id,title){
										var isExtents = false, index = 0;
										for (var i=0; i<articleSelect.length; i++){
											if (articleSelect[i][0]==id){
												isExtents = true;
												index = i;
											}
										}
										if(isExtents){
											articleSelect.splice(index,1);
										}else{
											articleSelect.push([id,title]);
										}
										articleSelectRefresh();
									}
									function articleSelectRefresh(){
										$("#articleDataRelation").val("");
										$("#articleSelectList").children().remove();
										for (var i=0; i<articleSelect.length; i++){
											$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
											$("#articleDataRelation").val($("#articleDataRelation").val()+articleSelect[i][0]+",");
										}
									}
									$.getJSON("${ctxa}/cms/article/findByIds.do",{ids:$("#articleDataRelation").val()},function(data){
										for (var i=0; i<data.length; i++){
											articleSelect.push([data[i][1],data[i][2]]);
										}
										articleSelectRefresh();
									});
								 function la(){
										layer.open({
											 type: 2,
											 title: '添加相关',
											 shadeClose: true,
											 shade: 0.8,
											 area: ['80%', '90%'],
											 btn: ['确定'], //按钮
											 content: '${ctxa}/cms/article/selectList.do?pageSize=8' //iframe的url
											}); 
									} 
									$("#relationButton").click(function(){
										/*	top.$.jBox.open("iframe:${ctxa}/cms/article/selectList.do?pageSize=8", "添加相关",width=1200,height=450,{
											buttons:{"确定":true}, loaded:function(h){
												$(".jbox-content", top.document).css("overflow-y","hidden");
											}
										});	*/
										 la(); 
									});
								</script>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div> --%>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否允许评论&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="articleData.allowComment" ${article.articleData.allowComment eq 0?"":"checked"} data-size="small" value="1" style="display: none" data-on-text="是" data-off-text="否"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">推荐位&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('cms_posid')}" var="cp" >
									<div class="col-sm-5 icheck ">
										<div class="square-blue single-row">
											<c:set var="checked" value=""></c:set>
											<c:forEach items="${article.posid}" var="item" >
												<c:if test="${item==cp.value}">
												<c:set var="checked" value="checked"></c:set>
												</c:if>
											</c:forEach>
											<input type="checkbox" style="display: none" ${checked} data-switch-no-init name="posidList" value="${cp.value}">
											<label>${cp.label}</label>
										</div> 
									</div>
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">发布时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input id="createDate" readonly="readonly" type="text" class="form-control input-sm" name="createDate" format="yyyy-MM-dd"
										value="<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"> 
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div> 
							</div>
							<div class="col-sm-5">
								<p class="help-block"><p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("发布状态")}&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('cms_del_flag')}" var="la">
									<div class="col-sm-4 icheck ">
										<div class="square-blue single-row">
											<div class="radio ">
												<input value="${la.value}" type="radio" ${article.delFlag eq la.value?"checked":""} name="delFlag" style="display: none" data-switch-no-init>
												<label>${la.label}</label>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("如果栏目不需要审核，则将该内容设为发布状态")}<p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">查看评论&nbsp;:</label>
							<div class="col-sm-5">
								<input id="btnComment" class="btn" type="button" value="查看评论" onclick="viewComment('${ctxa}/cms/comment.do?module=article&contentId=${article.id}&status=0')"/>
								<script type="text/javascript">
									function viewComment(href){
										top.$.jBox.open('iframe:'+href,'查看评论',width=1200,height=450,{
											buttons:{"关闭":true},
											loaded:function(h){
												$(".jbox-content", top.document).css("overflow-y","hidden");
												$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
												$("body", h.find("iframe").contents()).css("margin","10px");
											}
										});
										return false;
									}
								</script>
							</div>
							<div class="col-sm-5">
								<p class="help-block">查看评论<p>
							</div>
						</div> --%>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<c:if test="${not empty article.id}">
									<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
										<i class="fa fa-times"></i> ${fns:fy("返回")}
									</button>
								</c:if>
								<shiro:hasPermission name="cms:article:edit">
								<c:if test="${empty article.id}">
									<button type="submit" name="submit" class="btn btn-info submitBtn" value="1">
										<i class="fa fa-check-circle"></i> ${fns:fy("保存并继续添加")}
									</button>
								</c:if>
								<button type="submit" name="submit" class="btn btn-info submitBtn" value="2">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>