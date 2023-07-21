<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("推荐位管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/site/siteRecommendForm.js"></script>
    <!-- 引入iCheck文件-->
    <%@ include file="../include/head_iCheck.jsp" %>
    <%@ include file="../include/head_bootstrap-switch.jsp" %>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty siteRecommend.recommendId?true:false}"></c:set>
        <h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("推荐位")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/site/siteRecommend/list.do"> <i class="fa fa-user"></i> ${fns:fy("推荐位列表")}</a>
            </li>
            <shiro:hasPermission name="site:siteRecommend:edit">
                <li class="active"><a href="javaScript:;"> <i
                        class="fa fa-user"></i> ${fns:fy("推荐位")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
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
                <li>${fns:fy("网站设置.推荐位.操作提示1")}</li>
                <li>${fns:fy("网站设置.推荐位.操作提示2")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/site/siteRecommend/${isEdit?'edit2':'save2'}.do" method="post">
                    <input type="hidden" name="recommendId" value="${siteRecommend.recommendId}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("编号")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="hidden" id="oldRecommendNumber" name="oldRecommendNumber" maxlength="24"
                                   class="form-control input-sm" value="${siteRecommend.recommendNumber}"/>
                            <input type="text" name="recommendNumber" maxlength="64" class="form-control input-sm"
                                   value="${siteRecommend.recommendNumber}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.推荐位.操作提示3")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("推荐位名称")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="name" maxlength="64" class="form-control input-sm"
                                   value="${siteRecommend.name}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.推荐位.操作提示4")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("推荐位说明")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="info" maxlength="255" class="form-control input-sm"
                                   value="${siteRecommend.info}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.推荐位.操作提示5")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <%-- <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否开启&nbsp;:</label>
                        <div class="col-sm-5">
                            <c:forEach items="${fns:getDictList('')}" var="item">
                            <label class="checkbox-inline">
                            <input type="radio" name="isOpen" value="${item.value}" ${item.value==siteRecommend.isOpen?"checked":""}/> ${item.label}
                            </label>
                            </c:forEach>

                        </div> --%>

                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("是否启用")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${siteRecommend.isOpen eq 0?"":"checked"} data-size="small"
                                   name="isOpen" value="1" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.推荐位.操作提示6")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("推荐类型")}&nbsp;:</label>
                        <div class="col-sm-5 icheck">
                            <c:forEach items="${fns:getDictList('site_recommend_type')}" var="item"
                                       varStatus="item_index">
                                <div class="square-blue">
                                    <div class="radio">
                                        <input type="radio"
                                               style="display: none" ${(not empty siteRecommend.type && item.value==siteRecommend.type) || (empty siteRecommend.type && item_index.first)?"checked='checked'":''}
                                               data-switch-no-init name="type" value="${item.value}">
                                        <label>${item.label}</label>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.推荐位.操作提示7")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("排序")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="sort" maxlength="10" class="form-control input-sm"
                                   value="${empty siteRecommend.sort?10:siteRecommend.sort}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("请填写排序")}<p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="site:siteRecommend:edit">
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