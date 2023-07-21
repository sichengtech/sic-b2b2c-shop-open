<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${fns:fy("Solr索引管理")}</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="decorator" content="admin"/>
    <!-- 业务js -->
    <script type="text/javascript" src="${ctx}/views/admin/site/SiteCreateSolrIndexController.js"></script>
</head>
<body>
<!-- panel开始 -->
<section class="panel">
    <header class="panel-heading custom-tab tab-right ">
        <c:set var="isEdit" value="${not empty cmsArticle.id?true:false}"></c:set>
        <h4 class="title">${fns:fy("生成Solr索引")}</h4>
        <%@ include file="../include/functionBtn.jsp" %>
        <ul class="nav nav-tabs pull-right">

        </ul>
    </header>
    <!-- panel-body开始 -->
    <div class="panel-body">
        <!-- 提示开始 -->
        <div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}"
             id="point">
            <h5>${fns:fy("操作提示")}</h5>
            <ul>
                <li>${fns:fy("网站设置.Solr索引.操作提示1")}</li>
                <li>${fns:fy("网站设置.Solr索引.操作提示2")}</li>
                <li>${fns:fy("网站设置.Solr索引.操作提示3")}</li>
                <li>${fns:fy("网站设置.Solr索引.操作提示4")}</li>
            </ul>
        </div>
        <!-- 提示结束 -->
        <sys:message content="${message}"/>
        <div class="tab-content" style="margin-top:15px;">
            <div class="tab-pane active" id="home-3">
                <form class="cmxform form-horizontal adminex-form" id="staticArticle" onsubmit="return false;">
                    <%--当前索引类型只有商品，如果有多个索引选项请解开下列注释，并删除下面这行<input>--%>
                    <input type="hidden" value="product" name="subType" id="subType">
<%--                    <div class="form-group">--%>
<%--                        <label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>--%>
<%--                            索引类型&nbsp;:</label>--%>
<%--                        <div class="col-sm-5">--%>
<%--                            <select name="subType" id="subType" class="form-control m-bot15 input-sm">--%>
<%--                                <option value="product" selected>商品</option>--%>
<%--                                <option value="store" >店铺</option>--%>
<%--                            </select>--%>
<%--                        </div>--%>
<%--                        <div class="col-sm-5">--%>
<%--                            <p class="help-block">必填项，请选择生成商品索引或店铺索引--%>
<%--                            <p>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("生成日志")}&nbsp;:</label>
                        <div class="col-sm-5">
                            <textarea id="showLogs" rows="8" class="form-control" disabled style="height: auto"></textarea>
                        </div>
                        <div class="col-sm-5">
                            <p class="help-block">
                            <p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="inputSuccess"></label>
                        <div class="col-sm-5">
                            <button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
                                <i class="fa fa-times"></i> ${fns:fy("返回")}
                            </button>
                            <shiro:hasPermission name="site:SiteCreateSolrIndexController:index">
                                <button type="submit" id="start" class="btn btn-info">
                                    <i class="fa fa-check"></i> ${fns:fy("启动")}
                                </button>
                                <button type="submit" id="stop" class="btn btn-warning">
                                    <i class="fa fa-check"></i> ${fns:fy("停止")}
                                </button>
                                <button type="submit" id="logs" class="btn btn-success">
                                    <i class="fa fa-check"></i> ${fns:fy("查看日志")}
                                </button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                </form>
                <script>
                    $(document).ready(function () {
                        $("#start").click(function () {
                            // if (check()) {
                            //     start();
                            // }
                            start();
                        });

                        $("#stop").click(function () {
                            stop();
                        });

                        $("#logs").click(function () {
                            logs();
                        });
                    });

                    /**
                     * 表单验证
                     * @returns {boolean}
                     */
                    function check() {
                        var threadNumber = $("#threadNumber").val();
                        if (threadNumber > 0 && threadNumber <= 10) {
                            return true;
                        }
                        layer.msg("${fns:fy("线程数不能小于1大于10")}");
                        return false;
                    }

                    /**
                     * 开始静态化
                     */
                    function start() {
                        var data = {
                            "subType": $("#subType").val(),
                        };
                        $.post("${ctxa}/site/SiteCreateSolrIndexController/start.do", data, function (result) {
                            layer.msg(result.msg);
                        }, "json");
                    }

                    /**
                     * 停止静态化
                     */
                    function stop() {
                        $.post("${ctxa}/site/SiteCreateSolrIndexController/stop.do", null, function (result) {
                            console.log(result);
                            layer.msg(result.msg);
                        }, "json");
                    }

                    /**
                     * 查看静态化日志
                     */
                    function logs() {
                        $.post("${ctxa}/site/SiteCreateSolrIndexController/logs.do", null, function (result) {
                            $("#showLogs").html($("#showLogs").html() + result.data + "\n");
                        }, "json");
                    }
                </script>
            </div>
        </div>
    </div>
    <!-- panel-body结束 -->
</section>
<!-- panel结束 -->
</body>
</html>