<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("新增商家")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/sso/userSellerForm2.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty userSeller.UId?true:false}"></c:set>
        <h4 class="title">${fns:fy("新增商家")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">
            <li class=""><a href="${ctxa}/sso/userMain/sellerList.do"> <i class="fa fa-user"></i> ${fns:fy("商家管理")}</a>
            </li>
        </ul>
    </header>
    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示开始 -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("店铺管理.商家管理.操作提示3")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm"
                      action="${ctxa}/sso/userMain/sellerSave2.do" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("用户账号")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="loginName" maxlength="64" class="form-control input-sm" value="${userMain.loginName}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("必填项，请填写用户账号")}
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("登陆密码")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="password" name="password" id="pwd" maxlength="64" class="form-control input-sm" value="${userMain.password}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("必填项，请填写登陆密码")}
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("确认密码")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="password" name="password2" id="pwd2" maxlength="64" class="form-control input-sm" />
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("必填项，请重复一次上面的密码")}
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red"></font> ${fns:fy("邮箱")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="email" name="email"  maxlength="64" class="form-control input-sm" value="${userMain.email}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("用户邮箱，可不填")}
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red"></font> ${fns:fy("电话")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <input type="text" name="mobile"  maxlength="64" oninput = "value=value.replace(/[^\d]/g,'')" class="form-control input-sm" value="${userMain.mobile}"/>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">${fns:fy("用户电话，可不填")}
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="sso:seller:save">
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