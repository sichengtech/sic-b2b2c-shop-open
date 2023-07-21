<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("商家管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/sso/userSellerList.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <h4 class="title">${fns:fy("商家管理")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("商家管理")}</a></li>
            <%-- <shiro:hasPermission name="sso:userSeller:edit"><li class=""><a href="${ctxa}/sso/userSeller/save1.do" > <i class="fa fa-user"></i> 会员（卖家）添加</a></li></shiro:hasPermission>--%>
        </ul>
    </header>

    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示 start -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("店铺管理.商家管理.操作提示1")}</li>
                <li>${fns:fy("店铺管理.商家管理.操作提示2")}</li>
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
                    <shiro:hasPermission name="sso:seller:save">
                        <a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sso/userMain/sellerSave1.do"><i class="fa fa-plus"></i></a>
                    </shiro:hasPermission>
                </div>
            </div>
            <div class="col-sm-4"></div>
            <form action="${ctxa}/sso/userMain/sellerList.do" method="get" id="searchForm">
                <div class="col-sm-6">
                    <div class="iconic-input right">
                        <i class="fa fa-search " style="cursor:pointer" id="searchFormButton"></i>
                        <input type="text" name="loginName" maxlength="24" value="${userMain.loginName}"
                               class="form-control input-sm pull-right" placeholder="${fns:fy("请输入商家名称进行精确搜索")}"
                               style="border-radius: 30px;max-width:250px;">
                    </div>
                </div>
                <div class="col-sm-1" style="text-align: right;">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                </div>
            </form>
        </div>
        <!-- 按钮结束 -->
        <!-- Table开始 -->
        <div class="table-responsive">
            <table class="table table-hover table-condensed table-bordered">
                <thead>
                <tr>
                    <th>${fns:fy("商家ID")}</th>
                    <th>${fns:fy("商家名称")}</th>
                    <th>${fns:fy("店铺名称")}</th>
                    <th>${fns:fy("商家邮箱")}</th>
                    <th>${fns:fy("是否开店")}</th>
                    <th>${fns:fy("是否锁定")}</th>
                    <th>${fns:fy("最后登录日期")}</th>
                    <th>${fns:fy("注册日期")}</th>
                    <th>${fns:fy("操作")}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userSeller">
                    <tr>
                        <td>${userSeller.UId}</td>
                        <td>${userSeller.userMain.loginName}</td>
                        <td>
                            <c:if test="${not empty userSeller.storeId}">${userSeller.store.name}</c:if>
                        </td>
                        <td>${userSeller.userMain.email}</td>
                        <td>${fns:getDictLabel(userSeller.isOpen, 'yes_no', '')}</td>
                        <td>${fns:getDictLabel(userSeller.userMain.isLocked, 'yes_no', '')}</td>
                        <td><fmt:formatDate value="${userSeller.userMain.loginDate}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${userSeller.userMain.registerDate}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                                <%-- <a class="btn btn-info btn-sm" href="${ctxa}/store/storeSeller/edit1.do?sellerId=${storeSeller.sellerId}">
                                    <i class="fa fa-edit"></i> 编辑
                                </a> --%>
                            <shiro:hasPermission name="sso:seller:drop">
                                <button type="button" class="btn btn-danger btn-sm deleteSure"
                                        href="${ctxa}/sso/userMain/sellerDelete.do?uId=${userSeller.userMain.UId}">
                                    <i class="fa fa-trash-o"></i> ${fns:fy("删除")}
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sso:seller:edit">
                                <c:if test="${userSeller.userMain.isLocked=='0'}">
                                    <button type="button" class="btn btn-danger btn-sm locked"
                                            href="${ctxa}/sso/userMain/sellerLocked.do?uId=${userSeller.userMain.UId}">
                                        <i class="fa fa-edit"></i> ${fns:fy("锁定")}
                                    </button>
                                </c:if>
                                <c:if test="${userSeller.userMain.isLocked=='1'}">
                                    <button type="button" class="btn btn-info btn-sm unLocked"
                                            href="${ctxa}/sso/userMain/sellerUnLocked.do?uId=${userSeller.userMain.UId}">
                                        <i class="fa fa-edit"></i> ${fns:fy("解锁")}
                                    </button>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sso:seller:save">
                                <c:if test="${userSeller.storeId == null}">
                                    <a target="_self" class="btn btn-info btn-sm storeOperate" href="javaScript:" UId="${userSeller.userMain.UId}" isLocked="${userSeller.userMain.isLocked}" >
                                        <i class="fa fa-eye"></i> ${fns:fy("替他开店")}
                                    </a>
                                </c:if>
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
<!-- 开始快速查询窗口 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true"
     style="display: none;">
    <div class="modal-content">
        <form action="${ctxa}/sso/userMain/sellerList.do" method="get" id="searchFormMyModal">
            <div class="modal-body form-horizontal adminex-form">
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("商家名称")}：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm searchInput" name="loginName"
                               value="${userMain.loginName}" placeholder="${fns:fy("请输入商家名称")}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("店铺名称")}：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm searchInput" name="name" value="${store.name}"
                               placeholder="${fns:fy("请输入店铺名称")}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("商家会员邮箱")}：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm searchInput" name="email"
                               value="${userMain.email}" placeholder="${fns:fy("请输入商家会员邮箱")}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("注册日期")}：</label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input name="beginRegisterDate" type="text" readonly="readonly" maxlength="20"
                                   placeholder="${fns:fy("请输入注册日期(开始)")}"
                                   class="form-control input-sm J-date-start searchInput"
                                   value="<fmt:formatDate value="${userMain.beginRegisterDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input name="endRegisterDate" type="text" readonly="readonly" maxlength="20"
                                   placeholder="${fns:fy("请输入注册日期(结束)")}"
                                   class="form-control input-sm J-date-start searchInput"
                                   value="<fmt:formatDate value="${userMain.endRegisterDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("最后登录日期")}：</label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input name="beginLoginDate" type="text" readonly="readonly" maxlength="20"
                                   placeholder="${fns:fy("最后登录日期(开始)")}"
                                   class="form-control input-sm J-date-start searchInput"
                                   value="<fmt:formatDate value="${userMain.beginLoginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input name="endLoginDate" type="text" readonly="readonly" maxlength="20"
                                   placeholder="${fns:fy("最后登录日期(结束)")}"
                                   class="form-control input-sm J-date-start searchInput"
                                   value="<fmt:formatDate value="${userMain.endLoginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label text-right">${fns:fy("登录状态")}：</label>
                    <div class="col-sm-4">
                        <select name="isLocked" class="form-control input-sm">
                            <option value="" class="firstOption">--${fns:fy("请选择")}--</option>
                            <c:forEach items="${fns:getDictList('is_locked')}" var="item">
                                <option value="${item.value}" ${item.value==userMain.isLocked?"selected":""}>${item.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" onclick="(function(){layer.closeAll('page');}())">
                        <i class="fa fa-times"></i> ${fns:fy("关闭")}
                    </button>
                    <button type="button" id="resetParam" class="btn btn-warning"
                            onclick="(function(){$('.searchInput').attr('value',''); $('.firstOption').attr('selected','selected');}())">
                        <i class="fa fa-reply"></i> ${fns:fy("参数重置")}
                    </button>
                    <button type="submit" class="btn btn-info">
                        <i class="fa fa-search"></i>${fns:fy("执行查询")}
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 结束快速查询模态窗口 -->
</body>
</html>