<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("文件管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 百度上传js文件 -->
    <script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
    <!-- MyUploader方法文件 -->
    <script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
    <style type="text/css">
        .uploadImgDiv {
            float: left;
            margin-right: 10px;
        }

        .preview {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty siteFileManage.sfId?true:false}"></c:set>
        <h4 class="title">${isEdit?fns:fy("编辑文件"):fns:fy("添加文件")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/site/siteFileManage/list.do"> <i class="fa fa-user"></i> ${fns:fy("文件列表")}</a>
            </li>
            <shiro:hasPermission name="site:siteFileManage:edit">
                <li class="active"><a href="javaScript:;"> <i
                        class="fa fa-user"></i> ${isEdit?fns:fy("编辑文件"):fns:fy("添加文件")}</a></li>
            </shiro:hasPermission>
        </ul>
    </header>
    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示开始 -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("网站设置.文件管理.操作提示1")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/site/siteFileManage/${isEdit?'edit2':'save2'}.do" method="post">
                    <input type="hidden" name="sfId" value="${siteFileManage.sfId}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("文件分类")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <select name="category" class="form-control m-bot15 input-sm">
                                <option value="">--${fns:fy("请选择")}--</option>
                                <c:forEach items="${fns:getDictList('site_file_category')}" var="item">
                                    <option value="${item.value}" ${item.value==siteFileManage.category?"selected":""}>${item.label}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("必填项，请选择文件分类")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("文件")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="hidden" class="imgPath" name="path" value="${siteFileManage.path}"/>
                            <input type="hidden" class="imgPath" name="name" value="${siteFileManage.name}"/>
                            <div id="vessel"></div>
                        </div>

                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.文件管理.操作提示2")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess">${fns:fy("文件说明")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <textarea class="form-control" rows="4" cols="" name="remarks"
                                      maxlength="200">${siteFileManage.remarks}</textarea>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("必填项，请选择文件分类")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="site:siteFileManage:edit">
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
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteFileManageForm.js"></script>
</body>
</html>