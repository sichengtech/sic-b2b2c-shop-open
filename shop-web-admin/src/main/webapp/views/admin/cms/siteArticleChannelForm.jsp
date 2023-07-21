<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${not empty menu.id==true?fns:fy("编辑文章栏目"):fns:fy("添加文章栏目")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <%@ include file="../include/head_bootstrap-switch.jsp" %>
    <!-- 百度上传js文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
	<!-- MyUploader方法文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/cms/siteArticleChannelForm.js"></script>
</head>
<body>
<!-- panel start -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <h4 class="title">${not empty menu.id==true?fns:fy("编辑文章栏目"):fns:fy("添加文章栏目")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/cms/category.do"> <i class="fa fa-home"></i> ${fns:fy("文章栏目列表")}</a></li>
            <shiro:hasPermission name="cms:category:edit">
                <li class="active"><a href="javaScript:;"> <i
                        class="fa fa-user"></i> ${not empty menu.id==true?fns:fy("文章栏目编辑"):fns:fy("文章栏目添加（大写）")}</a>
                </li>
            </shiro:hasPermission>
        </ul>
    </header>
    <div class="panel-body">
        <!-- 提示 start -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("文章管理.栏目管理.操作提示1")}</li>
            </ul>
        </div>
        <!-- 提示 end -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm" method="post"
                      action="${ctxa}/cms/category/save.do">
                    <input type="hidden" name="id" value="${category.id}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font>${fns:fy("归属机构")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <sys:treeselect id="office" name="office.id" value="${category.office.id}"
                                            labelName="office.name" labelValue="${category.office.name}"
                                            title="${fns:fy('机构')}" url="/sys/office/treeData.do" cssClass="input-sm"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请选择归属机构")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font>${fns:fy("上级栏目")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <sys:treeselect id="category" name="parent.id" value="${category.parent.id}"
                                            labelName="parent.name" labelValue="${category.parent.name}"
                                            title="栏目" url="/cms/category/treeData.do" extId="${category.id}"
                                            cssClass="input-sm"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请选择上级栏目")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("栏目模型")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <select class="selectpicker form-control input-sm" name="module">
                                <option value="">${fns:fy("公共模型")}</option>
                                <c:forEach items="${fns:getDictList('cms_module')}" var="cm">
                                    <option value="${cm.value}" ${category.module eq cm.value?"selected":""}>${cm.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请选择栏目模型")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font>${fns:fy("栏目名称")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input class="form-control input-sm" type="text" name="name" value="${category.name}"
                                   maxlength="100"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请输入栏目名称")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("缩略图")}&nbsp;:</label>
                        <div class="col-sm-5">
							<input type="hidden" class="imgPath" name="image" value="${category.image}"/>
							<div id="vessel"></div>
						</div>	
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请上传栏目缩略图")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("链接")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input class="form-control input-sm" type="text" placeholder="" name="href"
                                   value="${category.href}">
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("文章管理.栏目管理.操作提示2")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("目标")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input class="form-control input-sm" type="text" placeholder="" name="target"
                                   value="${category.target}">
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("文章管理.栏目管理.操作提示3")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("描述")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <textarea rows="3" class="form-control"
                                      name="description">${category.description}</textarea>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写描述")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("关键字")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input class="form-control input-sm" type="text" placeholder="" name="keywords"
                                   value="${category.keywords}">
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("文章管理.栏目管理.操作提示4")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font>${fns:fy("排序")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input class="form-control input-sm" type="text" placeholder="" name="sort"
                                   value="${category.sort}" maxlength="10">
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("栏目的排列次序")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("在导航中显示")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${category.inMenu eq 0?"":"checked"} name="inMenu" data-size="small"
                                   value="1" style="display: none" data-on-text="${fns:fy("显示")}" data-off-text="${fns:fy("隐藏")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("是否在导航中显示该栏目")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("在分类页中显示列表")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${category.inList eq 0?"":"checked"} name="inList" data-size="small"
                                   value="1" style="display: none" data-on-text="${fns:fy("显示")}" data-off-text="${fns:fy("隐藏")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("文章管理.栏目管理.操作提示5")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("是否允许评论")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${category.allowComment eq 0?"":"checked"} name="allowComment"
                                   data-size="small" value="1" style="display: none" data-on-text="${fns:fy("是")}"
                                   data-off-text="${fns:fy("否")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("是否需要审核")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${category.isAudit eq 0?"":"checked"} name="isAudit" value="1"
                                   data-size="small" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("展现方式")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <select class="selectpicker form-control input-sm" name="showModes">
                                <c:forEach items="${fns:getDictList('cms_show_modes')}" var="cms">
                                    <option value="${cms.value}" ${category.showModes eq cms.value?"selected":""}>${cms.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("展现方式")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <c:if test="${not empty category.id}">
                                <button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
                                    <i class="fa fa-times"></i> ${fns:fy("返回")}
                                </button>
                            </c:if>
                            <c:if test="${empty category.id}">
                                <button type="submit" name="submit" class="btn btn-info submitBtn" value="1">
                                    <i class="fa fa-check-circle"></i> ${fns:fy("保存并继续添加")}
                                </button>
                            </c:if>
                            <button type="submit" name="submit" class="btn btn-info submitBtn" value="2">
                                <i class="fa fa-check"></i> ${fns:fy("保存")}
                            </button>
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