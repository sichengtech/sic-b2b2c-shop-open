<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("app版本管理管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/app/appVersionList.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <h4 class="title">${fns:fy("app版本管理列表")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("app版本管理列表")}</a></li>
            <shiro:hasPermission name="app:appVersion:save">
                <li class=""><a href="${ctxa}/app/appVersion/save1.do"> <i class="fa fa-user"></i> ${fns:fy("app版本管理添加")}</a></li>
            </shiro:hasPermission>
        </ul>
    </header>

    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示 start -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("App版本.App版本管理.操作提示1")}</li>
                <li>${fns:fy("App版本.App版本管理.操作提示2")}</li>
                <li>${fns:fy("App版本.App版本管理.操作提示3")}</li>
                <li>${fns:fy("App版本.App版本管理.操作提示4")}</li>
            </ul>
        </div>
        <!-- 提示 end -->
        <sys:message content="${message}"/>
        <!-- 按钮开始 -->
        <div class="row" style="margin-bottom:10px">
            <div class="col-sm-2">
                <div class="btn-group">
                    <!-- 刷新按钮 -->
                    <button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}"
                            onclick="location.reload();"><i class="fa fa-refresh"></i></button>
                    <!-- 添加记录按钮 -->
                    <shiro:hasPermission name="app:appVersion:save">
                        <a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/app/appVersion/save1.do"><i
                                class="fa fa-plus"></i></a>
                    </shiro:hasPermission>
                </div>
            </div>
            <form action="${ctxa}/app/appVersion/list.do" method="get" id="searchForm">
                <div class="col-sm-1"></div>
                <div class="col-sm-2">
                    <input type="text" name="id" maxlength="19" class="form-control input-sm" placeholder="ID"
                           value="${appVersion.id}"/>
                </div>
                <div class="col-sm-2">
                    <input type="text" name="version" maxlength="32" class="form-control input-sm" placeholder="${fns:fy("版本号")}"
                           value="${appVersion.version}"/>
                </div>
                <div class="col-sm-2">
                    <select name="type" class="form-control m-bot15 input-sm">
                        <option value="">${fns:fy("请选择安装包类型")}</option>
                        <c:forEach items="${fns:getDictList('app_type')}" var="item">
                            <option value="${item.value}" ${item.value==appVersion.type?"selected":""}>${item.label}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-2">
                    <select name="isNewVersion" class="form-control m-bot15 input-sm">
                        <option value="">${fns:fy("请选择是否为最新版本")}</option>
                        <c:forEach items="${fns:getDictList('app_is_new_version')}" var="item">
                            <option value="${item.value}" ${item.value==appVersion.isNewVersion?"selected":""}>${item.label}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-1" style="text-align: right;">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <button type="submit" class="btn btn-info btn-sm"><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
                </div>
            </form>
        </div>
        <!-- 按钮结束 -->
        <!-- Table开始 -->
        <div class="table-responsive">
            <table class="table table-hover table-condensed table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>${fns:fy("版本号")}</th>
                    <th>${fns:fy("安装包类型")}</th>
                    <th>${fns:fy("最新版本")}</th>
                    <th>${fns:fy("下载路径")}</th>
                    <th>${fns:fy("版本说明")}</th>
                    <th>${fns:fy("发布时间")}</th>
                    <th>${fns:fy("操作")}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="appVersion">
                    <tr>
                        <td><a href="${ctxa}/app/appVersion/edit1.do?id=${appVersion.id}">${appVersion.id}</a></td>
                        <td>${appVersion.version}</td>
                        <td>${appVersion.type == "1"?"apk":"wgt"}</td>
                        <td>${appVersion.isNewVersion == "1"?fns:fy("是"):fns:fy("否")}</td>
                        <td><a href="${ctxfs}${appVersion.downloadPath}">${fns:fy("点我下载")}</a></td>
                        <td>${appVersion.explain}</td>
                        <td><fmt:formatDate value="${appVersion.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <shiro:hasPermission name="app:appVersion:edit">
                                <a class="btn btn-info btn-sm"
                                   href="${ctxa}/app/appVersion/edit1.do?id=${appVersion.id}">
                                    <i class="fa fa-edit"></i> ${fns:fy("编辑")}
                                </a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="app:appVersion:drop">
                                <button type="button" class="btn btn-danger btn-sm deleteSure"
                                        href="${ctxa}/app/appVersion/delete.do?id=${appVersion.id}">
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
    <!-- panel-body结束 -->
</section>
<!-- panel结束 -->
</body>
</html>