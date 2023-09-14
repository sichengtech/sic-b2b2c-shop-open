<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("接口管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->

    <script type="text/javascript" src="${ctx}/views/admin/sys/sysApiTest.js"></script>
    <script src="${ctxStatic}/sicheng-admin/js/sign/md5.js" type="text/javascript"></script>
    <script src="${ctxStatic}/sicheng-admin/js/sign/base64.js" type="text/javascript"></script>
    <script src="${ctxStatic}/sicheng-admin/js/sign/sha3.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var apiParamList =${apiParamList};
    </script>
</head>
<body>
<script>
    var salt_d = "${salt_d}";
    <%--var salt_e = "${salt_e}";--%>
</script>

<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty sysApi.apiId?true:false}"></c:set>
        <h4 class="title">${fns:fy("测试接口")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
    </header>
    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示开始 -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("系统设置.接口测试.操作提示7")}</li>
                <li>${fns:fy("系统设置.接口测试.操作提示8")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="ceshi"></div>
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="inputForm" action="" method="post">
                    <div class="form-group">
                        <div class="col-sm-1 col-md-offset-1">
                            <select name="methodType" class="form-control m-bot15 input-sm">
                                <option>POST</option>
                                <option>GET</option>
                            </select>
                        </div>
                        <div class="col-sm-5">
                            <input type="text" name="url" maxlength="64" class="form-control input-sm"
                                   value="${sysApi.apiUrl}" placeholder="http://"/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-info test">${fns:fy("测试")}</button>
                        </div>
                        <div class="col-sm-1" style="padding-left: 5px;">
                            <button type="button" class="btn btn-info copy">${fns:fy("复制结果")}</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-8 col-md-offset-1">
                            <table class="table table-condensed table-bordered api-param-table">
                                <thead>
                                <tr>
                                    <th>${fns:fy("参数名")}</th>
                                    <th>${fns:fy("参数值")}</th>
                                    <th>${fns:fy("操作")}</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="5">
                                        <button type="button" class="btn btn-info add-tr add-param"
                                                id="api-param-table"><i class="fa fa-plus"></i> ${fns:fy("添加")}
                                        </button>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-8 col-md-offset-1">
                            <table class="table table-condensed table-bordered api-header-table">
                                <thead>
                                <tr>
                                    <th>${fns:fy("Header名")}</th>
                                    <th>${fns:fy("Header值")}</th>
                                    <th>${fns:fy("操作")}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <input type="text" name="" class="form-control input-sm name" value="AppToken"
                                               style="width: 90%"/>
                                    </td>
                                    <td>
                                        <input type="text" name="" class="form-control input-sm value" value=""
                                               style="width: 90%"/>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger delete-tr delete-param"><i
                                                class="fa fa-trash-o"></i> ${fns:fy("删除")}
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="text" name="" class="form-control input-sm name"
                                               value="TerminalType" style="width: 90%"/>
                                    </td>
                                    <td>
                                        <input type="text" name="" class="form-control input-sm value" value="app"
                                               style="width: 90%"/>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger delete-tr delete-param"><i
                                                class="fa fa-trash-o"></i> ${fns:fy("删除")}
                                        </button>
                                    </td>
                                </tr>


                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="5">
                                        <button type="button" class="btn btn-info add-tr add-header"
                                                id="api-header-table"><i class="fa fa-plus"></i> ${fns:fy("添加")}
                                        </button>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-8 col-md-offset-1">
                            <table class="table table-bordered api-response-table">
                                <thead>
                                <tr>
                                    <th>Response Header</th>
                                    <th>Response Body</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr style="height:200px;">
                                    <td class="header" style="width:50%;padding-left: 50px;text-align: left;">
                                        <div class="date"></div>
                                        <div class="statusCode"></div>
                                        <div class="response-hreader" style="white-space: pre-wrap;"></div>
                                    </td>
                                    <td class="body" style="width:50%;padding: 0;">
                                        <!-- <div style="width: 400px;height:200px;overflow:auto;text-align: left;"></div> -->
                                        <textarea rows="" cols=""
                                                  style="width: 400px;height: 200px;border: 0;"></textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-1" style="height: 30px;padding-left: 15px;">${fns:fy("接口所有参数")}:</div>
                        <div class="col-sm-8 col-md-offset-1">
                            <table class="table table-condensed table-bordered api-param-total-table">
                                <thead>
                                <tr>
                                    <th>${fns:fy("参数名")}</th>
                                    <th>${fns:fy("参数类型")}</th>
                                    <th>${fns:fy("是否必填")}</th>
                                    <th>${fns:fy("参数描述")}</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- panel-body结束 -->
</section>
<!-- panel结束 -->
<script type="text/template" id="api_param_edit_Tpl" info="接口参数模板">
    <tr>
        <td>
            <font color="red" style="display:{{d.display}}"> * </font><input type="text" name=""
                                                                             class="form-control input-sm name"
                                                                             value="{{d.paramName}}"
                                                                             style="width: 90%"/>
        </td>
        <td>
            <input type="text" name="" class="form-control input-sm value" value="" style="width: 90%"/>
        </td>
        <td>
            <button type="button" class="btn btn-danger delete-tr delete-param"><i class="fa fa-trash-o"></i> ${fns:fy("删除")}
            </button>
        </td>
    </tr>
</script>
<script type="text/template" id="api_param_Tpl" info="接口参数模板（回显所有参数）">
    <tr>
        <td>{{d.paramName}}</td>
        <td>{{d.paramType}}</td>
        <td>{{d.isRequired}}</td>
        <td>{{d.paramDescribe}}</td>
    </tr>
</script>
<script type="text/template" id="api_no_param_Tpl" info="接口参数模板（回显所有参数）">
    <tr>
        <td colspan="4" style="height: 150px;color: #8a8888;">${fns:fy("暂无参数")}</td>
    </tr>
</script>
</body>
</html>