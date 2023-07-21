<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("广告位管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/site/siteAdForm.js"></script>
    <!-- 引入bootstrap-switch文件 -->
    <%@ include file="../include/head_bootstrap-switch.jsp" %>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty siteAd.adId?true:false}"></c:set>
        <h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("广告位")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/site/siteAd/list.do"> <i class="fa fa-user"></i> ${fns:fy("广告位列表")}</a></li>
            <shiro:hasPermission name="${isEdit?'site:siteAd:edit':'site:siteAd:save'}">
                <li class="active"><a href="javaScript:;"> <i
                        class="fa fa-user"></i> ${fns:fy("广告位")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
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
                <li>${fns:fy("网站设置.广告位.操作提示1")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/site/siteAd/${isEdit?'edit2':'save2'}.do" method="post">
                    <input type="hidden" name="adId" value="${siteAd.adId}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("编号")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="hidden" id="oldAdNumber" name="oldAdNumber" maxlength="24"
                                   class="form-control input-sm" value="${siteAd.adNumber}"/>
                            <input type="text" name="adNumber" maxlength="24" class="form-control input-sm"
                                   value="${siteAd.adNumber}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.广告位.操作提示2")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("广告标题")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" escapeXml="true" name="name" maxlength="255"
                                   class="form-control input-sm" value="${siteAd.name}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.广告位.操作提示3")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("广告内容")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <textarea escapeXml="true" name="content" class="form-control input-sm"
                                      rows="5">${sContent.content}</textarea>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.广告位.操作提示4")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("排序")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="sort" maxlength="5" class="form-control input-sm"
                                   value="${isEdit?siteAd.sort:10}"/>

                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.广告位.操作提示5")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font
                                color="red">*</font> ${fns:fy("是否启用")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="checkbox" ${siteAd.isOpen eq 0?"":"checked"} data-size="small" name="isOpen"
                                   value="1" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("网站设置.广告位.操作提示6")}<p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="${isEdit?'site:siteAd:edit':'site:siteAd:save'}">
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
    <c:if test="${isEdit}">
        <!-- 广告内容结束 -->
        <div class="panel-body">
            <h4>${fns:fy("广告位内容历史版本")}</h4>
            <!-- Table开始 -->
            <div class="table-responsive">
                <table class="table table-hover table-condensed table-bordered">
                    <thead>
                    <tr>
                        <th>${fns:fy("广告内容ID")}</th>
                        <th>${fns:fy("广告内容")}</th>
                        <th>${fns:fy("是否有效")}</th>
                        <th>${fns:fy("创建时间")}</th>
                        <th>${fns:fy("操作")}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="siteAdContent">
                        <tr>
                            <td>${siteAdContent.adContentId}</td>
                            <td><textarea escapeXml="true" readonly="readonly"
                                          class="form-control input-sm">${siteAdContent.content}</textarea></td>
                            <td>${fns:getDictLabel(siteAdContent.status, 'is_status', '')}</td>
                            <td><fmt:formatDate value="${siteAdContent.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                    <%-- <shiro:hasPermission name="site:siteAdContent:edit">
                                    <a class="btn btn-info btn-sm" href="${ctxa}/site/siteAdContent/edit1.do?adContentId=${siteAdContent.adContentId}">
                                        <i class="fa fa-edit"></i> 编辑
                                    </a>
                                    </shiro:hasPermission> --%>
                                <shiro:hasPermission name="site:siteAdContent:drop">
                                    <button type="button" class="btn btn-danger btn-sm deleteSure"
                                            href="${ctxa}/site/siteAdContent/delete.do?adContentId=${siteAdContent.adContentId}">
                                        <i class="fa fa-trash-o"></i> ${fns:fy("删除")}
                                    </button>
                                </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- table结束 -->
            <!-- 分页信息开始 -->
            <%@ include file="../include/page.jsp" %>
            <!-- 分页信息结束 -->
        </div>
        <!-- 广告内容结束 -->
    </c:if>
</section>
<!-- panel结束 -->
</body>
</html>