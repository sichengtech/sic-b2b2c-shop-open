<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp" %>
<%@ page import="com.sicheng.admin.site.entity.SiteInfo" %>
<div class="left-side sticky-left-side">
    <!--logo and iconic logo start-->
    <%
        //该session在com.sicheng.admin.index.web.LoginController里设置的
        SiteInfo siteInfo = (SiteInfo) session.getAttribute("siteInfo");
        if (siteInfo == null) {
            siteInfo = new SiteInfo();
            siteInfo.setName("shop");
        }
    %>
    <!--log和图标 start-->
    <div class="logo">
        <%--				<a target="_blank" href="${ctxf}/index.htm"><img src="${ctxStatic}/sicheng-admin/images/logo.png" alt=""></a>--%>
        <a target="_blank" href="/"><%=siteInfo.getName()%>
        </a>
    </div>
    <div class="logo-icon text-center">
        <a target="_blank" href="${ctxf}/index.htm"><img src="${ctxStatic}/sicheng-admin/images/logo_icon.png"
                                                         alt=""></a>
    </div>
    <!--logo和图标 end-->
    <div class="left-side-inner">
        <!-- 二级菜单 sidebar nav start-->
        <c:set value="menu_" var="prefix2"/>
        <ul id="menu_2" class="nav nav-pills nav-stacked custom-nav">
            <c:forEach items="${fns:getChildMenuList(menu1id)}" var="menu2">
                <c:set value="0,1,${menu1id}," var="parentIds1"/>
                <c:if test="${menu2.parentIds == parentIds1 && menu2.isShow eq '1'}">
                    <li class="menu-list nav-active">
                        <a href="javascript:void(0);"><i class="fa fa-th-list"></i><span>${fns:fy(prefix2.concat(menu2.name))}</span>
                            <small>${fn:substring(menu2.name,"0","2")}</small>
                        </a>
                        <ul class="sub-menu-list">
                            <c:forEach items="${fns:getChildMenuList(menu2.id)}" var="menu3">
                                <c:set value="${menu2.parentIds}${menu2.id}," var="parentIds2"/>
                                <c:if test="${parentIds2 == menu3.parentIds && menu3.isShow eq '1'}">
                                    <li class="${menu3.id == menu3id?'nav-hover':'' }"
                                        style="background:${menu3.id == menu3id?'#2a323f':'' }"><a
                                            href="${fn:indexOf(menu3.href, '://') eq -1 ? ctxa : ''}${menu3.href}"><i
                                            class="fa fa-user"></i> <span>${fns:fy(prefix2.concat(menu3.name))}</span></a></li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        <!-- 二级菜单 sidebar nav end-->
    </div>
</div>
		