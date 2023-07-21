<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("站点设置管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 百度上传js文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
	<!-- MyUploader方法文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/site/siteInfoForm.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty siteInfo.id?true:false}"></c:set>
        <h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("站点设置")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <%-- <li class=""><a href="${ctxa}/site/siteInfo/list.do"> <i class="fa fa-user"></i> 站点设置列表</a></li> --%>
            <shiro:hasPermission name="site:siteInfo:edit">
                <li class="active"><a href="javaScript:;"> <i
                        class="fa fa-user"></i> ${fns:fy("站点设置")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
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
                <li>${fns:fy("网站设置.站点设置.操作提示1")}</li>
                <li>${fns:fy("网站设置.站点设置.操作提示2")}</li>
                <li>${fns:fy("网站设置.站点设置.操作提示3")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/site/siteInfo/${isEdit?'edit2':'save2'}.do" method="post">
                    <input type="hidden" name="id" value="${siteInfo.id}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("网站名称")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="name" maxlength="128" class="form-control input-sm"
                                   value="${siteInfo.name}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.站点设置.操作提示4")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("网站LOGO")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="hidden" class="imgPath" name="siteLogo" value="${siteInfo.siteLogo}"/>
                            <div id="vessel1"></div>
                        </div>
                        <div class="col-sm-5">
                            <h4>${fns:fy("首页LOGO图片")}</h4>
                            <p class="help-block">${fns:fy("网站设置.站点设置.操作提示5")}<p>
                        </div>
                    </div>
                    <%--                    <div class="form-group">--%>
                    <%--                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("会员中心LOGO")}&nbsp;:</label>--%>
                    <%--						<div class="col-sm-5">--%>
                    <%--							<input type="hidden" class="imgPath" name="sellerLogo" value="${siteInfo.sellerLogo}"/>--%>
                    <%--							<div id="vessel2"></div>--%>
                    <%--						</div>--%>
                    <%--                        <div class="col-sm-5">--%>
                    <%--                            <h4>${fns:fy("会员中心LOGO")}</h4>--%>
                    <%--                            <p class="help-block">${fns:fy("网站设置.站点设置.操作提示6")}<p>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("ICP备案号")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="icp" maxlength="128" class="form-control input-sm"
                                   value="${siteInfo.icp}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("如果没有请留空")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("站点联系邮箱")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="email" maxlength="128" class="form-control input-sm"
                                   value="${siteInfo.email}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.站点设置.操作提示7")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("站点联系电话")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="telephone" maxlength="128" class="form-control input-sm"
                                   value="${siteInfo.telephone}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.站点设置.操作提示8")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("第三方流量统计代码")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <textarea name="code" class="form-control input-sm" maxlength="1024"
                                      placeholder="${fns:fy("请把第三方的访问量统计代码粘贴到这里")}" rows="5"
                                      data-parsley-id="8">${siteInfo.code}</textarea>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("如果没有请留空")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="site:siteInfo:edit">
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