$(function () {

    /**
     * 添加参数
     * */
    $(".add-tr").click(function () {
        var api_param_edit_Tpl = $("#api_param_edit_Tpl").html();
        var api_param_edit_data = {"display": "none", "paramName": ""};
        var paramHtml = render(api_param_edit_Tpl, api_param_edit_data);//渲染模板
        $(this).parents("table").find("tbody").append(paramHtml);
    });

    /**
     * 删除参数
     * */
    $("table").delegate(".delete-tr", "click", function () {
        $(this).parent().parent().remove();
    });

    /**
     * 渲染模板
     * */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }

    /**
     * 回显接口的参数
     * */
    if (apiParamList != null && apiParamList.length != 0) {
        var api_param_Html = "";
        var param_edit_html = "";
        var api_param_Tpl = $("#api_param_Tpl").html();
        var api_param_edit_Tpl = $("#api_param_edit_Tpl").html();
        for (var i = 0; i < apiParamList.length; i++) {
            //回显所有参数
            var api_param_data = {
                "paramName": apiParamList[i].paramName, "paramDescribe": apiParamList[i].paramDescribe,
                "paramType": apiParamList[i].paramTypeLabel, "isRequired": apiParamList[i].isRequiredLabel
            };
            api_param_Html += render(api_param_Tpl, api_param_data);//渲染模板
            //回显参数到编辑参数的table
            //是否显示必填的提示(*)
            var display = apiParamList[i].isRequired == '0' ? 'none' : '';
            var api_param_edit_data = {"display": display, "paramName": apiParamList[i].paramName};
            param_edit_html += render(api_param_edit_Tpl, api_param_edit_data);//渲染模板
        }
        $(".api-param-total-table tbody").append(api_param_Html);
        $(".api-param-table tbody").append(param_edit_html);
    } else {
        var api_no_param_Tpl = $("#api_no_param_Tpl").html();
        var api_no_param_data = {};
        var apiNoParamHtml = render(api_no_param_Tpl, api_no_param_data);//渲染模板
        $(".api-param-total-table tbody").append(apiNoParamHtml);
    }

    /**
     * 测试
     * */
    $("button.test").click(function () {
        layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        //请求url
        var url = $("input[name='url']").val();
        if (url == "") {
            layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + fy.getMsg('接口地址不能为空'));
            return false;
        }
        url = ctxw + url;
        //请求类型
        var methodType = $("select[name='methodType']").val();
        //参数
        var data = {};
        $(".api-param-table tbody tr").each(function () {
            var paramName = $(this).find(".name").val();
            var paramValue = $(this).find(".value").val();
            if (paramName != "" && paramValue != "") {
                data[paramName] = paramValue;
            }
        });
        //请求头
        var headers = {};
        $(".api-header-table tbody tr").each(function () {
            var name = $(this).find(".name").val();
            var value = $(this).find(".value").val();
            if (name != "" && value != "") {
                headers[name] = value;
            }
        });

        //签名
        var date1 = new Date();
        // var newSalt = hex_md5(salt_d + hex_md5(salt_e));
        var newSalt = hex_md5(salt_d );
        var base64 = Base.encode(JSON.stringify(data)) + Date.parse(date1) + newSalt;
        var random = hex_md5(sha3_256(base64));

        var newData = {
            "data": Base.encode(JSON.stringify(data)),
            "make": Date.parse(date1),
            "random": random,
        };

        //发送请求

        $.ajax({
            type: methodType,
            url: url,
            data: newData,
            headers: headers,
            dataType: 'JSON',
            success: function (data, status, xmlHttpRequest) {
                var date2 = new Date();
                var dateHtml = fy.getMsg('执行时间') + ":" + (date2.getTime() - date1.getTime()) / 1000 + "秒";
                var statusCode = "Status Code:" + xmlHttpRequest.status + " " + xmlHttpRequest.statusText + "";
                //Response Body
                $(".api-response-table tbody tr .body textarea").text(JSON.stringify(data));
                //执行时间
                $(".api-response-table tbody tr .header div.date").text(dateHtml);
                //状态码
                $(".api-response-table tbody tr .header div.statusCode").text(statusCode);
                //response-hreader
                $(".api-response-table tbody tr .header div.response-hreader").text(xmlHttpRequest.getAllResponseHeaders());
                layer.closeAll();
            },
            error: function (xmlHttpRequest, textStatus, data) {
                var date2 = new Date();
                var dateHtml = fy.getMsg('执行时间') + ":" + (date2.getTime() - date1.getTime()) / 1000 + "秒";
                var statusCode = "Status Code:" + xmlHttpRequest.status + " " + xmlHttpRequest.statusText + "";
                //Response Body
                $(".api-response-table tbody tr .body textarea").text(xmlHttpRequest.responseText);
                //执行时间
                $(".api-response-table tbody tr .header div.date").text(dateHtml);
                //状态码
                $(".api-response-table tbody tr .header div.statusCode").text(statusCode);
                //response-hreader
                $(".api-response-table tbody tr .header div.response-hreader").text(xmlHttpRequest.getAllResponseHeaders());
                layer.closeAll();
            }
        });
    });

    /**
     * 复制
     * */
    $("button.copy").click(function () {
        var content = $(".api-response-table tbody tr .body textarea");
        content.select();
        document.execCommand("Copy");
        layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> " + fy.getMsg('已复制到剪贴板'));
    })
});
