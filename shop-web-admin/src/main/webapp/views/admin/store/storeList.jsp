<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("店铺管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/store/storeList.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <h4 class="title">${fns:fy("店铺列表")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("店铺列表")}</a></li>
            <%-- <shiro:hasPermission name="store:store:edit"><li class=""><a href="${ctxa}/store/store/save1.do" > <i class="fa fa-user"></i> 店铺添加</a></li></shiro:hasPermission>--%>
        </ul>
    </header>

    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示 start -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("店铺管理.店铺管理.操作提示1")}</li>
                <li>${fns:fy("店铺管理.店铺管理.操作提示2")}</li>
                <li>${fns:fy("店铺管理.店铺管理.操作提示3")}</li>
            </ul>
        </div>
        <!-- 提示 end -->
        <sys:message content="${message}"/>
        <!-- 按钮开始 -->
        <div class="row" style="margin-bottom:10px">
            <div class="col-sm-5">
                <div class="btn-group">
                    <!-- 刷新按钮 -->
                    <button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}"
                            onclick="location.reload();"><i class="fa fa-refresh"></i></button>
                    <!-- 添加记录按钮 -->
                    <%-- <shiro:hasPermission name="store:store:edit">
                        <a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/store/save1.do"><i class="fa fa-plus"></i></a>
                    </shiro:hasPermission> --%>
                </div>
            </div>
            <div class="col-sm-4"></div>
            <form action="${ctxa}/store/store/list.do" method="get" id="searchForm">
                <div class="col-sm-3">
                    <div class="iconic-input right">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <a href="javaScript:;" class="searchList"><i class="fa fa-search"></i></a>
                        <input type="text" name="name" class="form-control input-sm pull-right"
                               placeholder="${fns:fy("请输入店铺名称进行搜索")}" value="${store.name}" maxlength="30"
                               style="border-radius: 30px;max-width:250px;">
                    </div>
                </div>
            </form>
        </div>
        <!-- 按钮结束 -->
        <!-- Table开始 -->
        <table class="table table-hover table-condensed table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>${fns:fy("企业名称")}</th>
                <th>${fns:fy("店铺名")}</th>
                <th>${fns:fy("用户名")}</th>
                <th>${fns:fy("店铺类型")}</th>
                <th>${fns:fy("等级")}</th>
                <th>${fns:fy("商品数")}</th>
                <th>${fns:fy("佣金比例")}</th>
                <th>${fns:fy("绑定品牌")}</th>
                <th>${fns:fy("保证金")}</th>
                <th>${fns:fy("是否开启")}</th>
                <th>${fns:fy("开店日期")}</th>
                <shiro:hasPermission name="store:store:edit">
                    <th>${fns:fy("操作")}</th>
                </shiro:hasPermission>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="store">
                <tr>
                    <td>${store.storeId}</td>
                    <td>${store.storeEnter.companyName}</td>
                    <td>${store.name}</td>
                    <td>${store.userMain.loginName}</td>
                    <td>${fns:getDictLabel(fn:trim(store.storeType), 'store_type', '')}</td>
                    <td>${store.storeLevel.name}</td>
                    <td>${store.productCount}</td>
                    <td>${store.commission}%</td>
                    <td>${store.isBindingBrand eq '1'? fns:fy("是") : fns:fy("否")}</td>
                    <td>${store.markingImgPath eq '1' ? fns:fy("是") : fns:fy("否")}</td>
                    <td>${fns:getDictLabel(store.isOpen, 'is_open', '')}</td>
                    <td><fmt:formatDate value="${store.createDate}" pattern="yyyy-MM-dd"/></td>
                    <shiro:hasPermission name="store:store:edit">
                        <td>
                            <div class="btn-group">
                                <a href="javascript:;" data-toggle="dropdown" aria-expanded="false"
                                   class="btn btn-info btn-sm dropdown-toggle">
                                    <i class="fa fa-gears"></i> ${fns:fy("操作")}
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right" style="min-width: 125px;">
                                    <li>
                                        <a href="${ctxa}/store/store/edit1.do?storeId=${store.storeId}">
                                            <i class="fa fa-edit"></i> ${fns:fy("编辑")}
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javaScript:void(0);" class="bindBrand" storeId="${store.storeId}">
                                            <i class="fa fa-edit"></i> ${fns:fy("绑定品牌")}
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javaScript:void(0);" class="bindCommission" storeId="${store.storeId}">
                                            <i class="fa fa-edit"></i> ${fns:fy("绑定佣金")}
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javaScript:void(0);" class="storeMarking" storeId="${store.storeId}">
                                            <i class="fa fa-edit"></i> ${fns:fy("保证金")}
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxa}/store/store/storeInit.do?storeId=${store.storeId}">
                                            <i class="fa fa-edit"></i> ${fns:fy("信息")}
                                        </a>
                                    </li>
                                </ul>
                            </div>

                        </td>
                    </shiro:hasPermission>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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