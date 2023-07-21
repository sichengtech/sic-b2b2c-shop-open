<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<div class="table-responsive" style="margin-top: 10px;">
    <table class="table table-condensed table-bordered">
        <thead>
        <tr>
            <th>${fns:fy("参数名")}</th>
            <th>${fns:fy("参数类型")}</th>
            <th>${fns:fy("是否必填")}</th>
            <th>${fns:fy("参数描述")}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sysApiParamList}" var="sysApiParam">
            <tr>
                <td>${sysApiParam.paramName}</td>
                <td>${fns:getDictLabel(sysApiParam.paramType, 'sys_api_param_type', '')}</td>
                <td>${fns:getDictLabel(sysApiParam.isRequired, 'yes_no', '')}</td>
                <td style="width: 320px;">${sysApiParam.paramDescribe}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty sysApiParamList}">
        <tr>
            <td colspan="4" style="line-height: 150px;color: #9c9a9a;">${fns:fy("暂无参数")}</td>
        <tr>
            </c:if>
        </tbody>
    </table>
</div>
