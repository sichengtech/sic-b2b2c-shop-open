<%@ page import="com.sicheng.admin.app.entity.AppAd" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("App引导页管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 百度上传js文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
	<!-- MyUploader方法文件 -->
	<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/app/appAdForm.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty appAd.id?true:false}"></c:set>
        <h4 class="title">${isEdit?fns:fy("编辑App引导页"):fns:fy("添加App引导页")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/app/appAd/list.do"> <i class="fa fa-user"></i> ${fns:fy("App引导页列表")}</a></li>
            <shiro:hasPermission name="app:appAd:edit">
                <li class="active"><a href="javaScript:"> <i class="fa fa-user"></i> ${isEdit?fns:fy("App引导页编辑"):fns:fy("App引导页添加")}</a>
                </li>
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
                <li>${fns:fy("App版本.App引导页.操作提示3")}</li>
                <li>${fns:fy("App版本.App引导页.操作提示4")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/app/appAd/${isEdit?'edit2':'save2'}.do" method="post">
                    <input type="hidden" name="id" value="${appAd.id}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("背景色")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="backgroundColor" maxlength="32" class="form-control input-sm" value="${appAd.backgroundColor}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写背景色，例如")}#FF6699
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("背景图")}&nbsp;:</label>
                       	<div class="col-sm-5">
							<input type="hidden" class="imgPath" name="backgroundImage" value="${appAd.backgroundImage}"/>
							<div id="vessel"></div>
						</div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请上传背景图")}
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("是否展示")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <select name="isShow" class="form-control m-bot15 input-sm">
                                <c:forEach items="${fns:getDictList('hot_search_word_show')}" var="item">
                                    <option value="${item.value}" ${item.value==appAd.isShow?"selected":""}>${item.label}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请选择是否展示")}
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本描述")}1&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordOne" maxlength="128" class="form-control input-sm"
                                   value="${appAd.wordOne}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本描述")}1
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本")}1${fns:fy("颜色")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordOneColor"  maxlength="32" class="form-control input-sm" value="${appAd.wordOneColor}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本")}1${fns:fy("颜色")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本描述")}2&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordTwo" maxlength="128" class="form-control input-sm"
                                   value="${appAd.wordTwo}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本描述")}2
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本")}2${fns:fy("颜色")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordTwoColor"  maxlength="32" class="form-control input-sm" value="${appAd.wordTwoColor}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本")}2${fns:fy("颜色")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本描述")}3&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordThree" maxlength="128" class="form-control input-sm"
                                   value="${appAd.wordThree}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本描述")}3
                            <p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本")}3${fns:fy("颜色")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="wordThreeColor"  maxlength="32" class="form-control input-sm" value="${appAd.wordThreeColor}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写文本")}3${fns:fy("颜色")}<p>
                        </div>
                    </div>
                    <%
                        //只有第三页才能编辑按钮相关的属性
                        AppAd appAd = (AppAd) request.getAttribute("appAd");
                        System.out.println(appAd.getId());
                        if (appAd.getId() == 3) {
                            %>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("按钮文字")}&nbsp;:</label>
                                    <div class="col-sm-5">
                                        <input type="text" name="buttonWord" maxlength="64" class="form-control input-sm"
                                               value="<%=appAd.getButtonWord()%>"/>

                                    </div>
                                    <div class="col-sm-5">
                                        <p class="help-block">${fns:fy("请填写按钮文字")}
                                        <p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("按钮颜色")}&nbsp;:</label>
                                    <div class="col-sm-5">
                                        <input type="text" name="buttonColour" maxlength="32" class="form-control input-sm"
                                               value="<%=appAd.getButtonColour()%>"/>

                                    </div>
                                    <div class="col-sm-5">
                                        <p class="help-block">${fns:fy("请填写按钮颜色，例如")}#FF6699
                                        <p>
                                    </div>
                                </div>
                            <%
                        }
                    %>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="app:appAd:edit">
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