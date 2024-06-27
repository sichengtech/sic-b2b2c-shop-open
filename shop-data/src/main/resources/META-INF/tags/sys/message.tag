<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="https://java.sicheng.net/jsp/jstl/fns" %>
<%@ taglib prefix="fnc" uri="https://java.sicheng.net/jsp/jstl/fnc" %>
<%@ taglib prefix="sys" uri="https://java.sicheng.net/jsp/jstl/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxa" value="${ctx}${fns:getAdminPath()}"/>
<c:set var="ctxu" value="/upload"/>
<c:set var="ctxStatic" value="/static/static"/>
<c:set var="ctxfs" value="${ctxu}${fns:getConfig('filestorage.dir')}"/>
<%@ attribute name="content" type="java.lang.String" required="true" description="消息内容" %>
<%@ attribute name="type" type="java.lang.String" description="消息类型：1成功，0失败" %>
<c:if test="${not empty content}">
	<%
		// 判断消息类型
		// type值是传进来的，若传了，则取传进来的值，否则默认给值 1
		String ctype = type == null ? "1" : type;//默认给值 1
		// 判断是否包含错误信息
		String[] errArr = {"异常", "失败", "错误", "Error"};
		for (String err : errArr) {
			System.out.println("err:" + err);
			if (content.toLowerCase().contains(err.toLowerCase())) {
				ctype = "0";
				break;
			}
		}
		request.setAttribute("ctype_z83748373738873", ctype); // 设置属性供下文读取

		//处理消息内容，替换特殊字符达到可以正确执行JS的目标。
		content = content.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'").replaceAll("\n", " ").replaceAll("\r", " ");
		request.setAttribute("content_z83748373738873", content); // 设置属性供下文读取
	%>
	<script type="text/javascript">
		(function () {
			<c:if test="${ctype_z83748373738873==1}">
			let icon = "<i class='fa fa-smile-o' style='font-size:24px;color:green'></i>";
			let t = 3 * 1000;
			</c:if>
			<c:if test="${ctype_z83748373738873==0}">
			let icon = "<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>";
			let t = 20 * 1000;
			</c:if>
			fdp.msg(icon + " ${content_z83748373738873}", t);
		})();
	</script>
</c:if>