<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/seller/include/taglib.jsp" %>

<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
<div class="header">
    <div class="sui-container">
        <div class="logo-box" style="color: #f6403c;font-size: 32px;font-weight: 500;margin-right: 20px">
<%--            <a target="_blank" href="${ctxf}/index.htm">--%>
<%--                <img src="${siteInfo.siteLogo != null ? ctxfs : ctxStatic}${siteInfo.siteLogo != null ? siteInfo.siteLogo : '/sicheng-seller/images/logo.png'}"--%>
<%--                     alt="" style="width:133px;height: 35px;"/>--%>
<%--            </a>--%>
            ${fns:fy('商家后台')}
        </div>
        <div class="sui-navbar container nav-box">
            <div class="navbar-inner">

                <ul class="sui-nav">
                    <li class="${empty menu1id ? ' active' : ''}">
                        <a href="${ctxs}/index.htm">${fns:fy('首页')}</a>
                    </li>
                    <c:forEach items="${fns:getStoreMenuList()}" var="menu1">
                        <c:if test="${menu1.parent.id eq '1' && menu1.isShow eq '1'}">
                            <li class="${menu1id == menu1.id ? ' active' : ''}">
                                <c:if test="${empty menu1.href}">
                                    <a href="javascript:;">${fns:fy(menu1.name)}</a>
                                </c:if>
                                <c:if test="${not empty menu1.href}">
                                    <a href="${fn:indexOf(menu1.href, '://') eq -1 ? ctxs : ''}${menu1.href}">${fns:fy(menu1.name)}</a>
                                </c:if>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
                <ul class="sui-nav pull-right">
                    <c:set value="${fns:getUserMain()}" var="userMain"/>
                    <li><a target="_blank" href="${ctxm}/index.htm"><i
                            class="sui-icon icon-tb-myfill"></i> ${fns:fy('会员中心')}</a></li>
                    <li><a target="_blank" href="${ctx}/store/${userMain.userSeller.storeId}.htm"><i
                            class="sui-icon icon-tb-shop"></i> ${fns:fy('店铺首页')}</a></li>
                    <%--				<li><a href="${ctxs}/sys/sysMessage/list.htm"><i class="sui-icon icon-tb-message"></i> ${fns:fy('消息')}</a></li>--%>
                    <li class="sui-dropdown">
                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle">
                            <i class="sui-icon icon-tb-my"></i> ${userMain.userSeller.store.name} <i class="caret"></i>
                        </a>
                        <ul role="menu" class="sui-dropdown-menu">
                            <li role="presentation"><a role="menuitem" tabindex="-1"
                                                       href="${ctxs}/user/userSeller/edit1.htm?uId=${userMain.UId}">${fns:fy('修改密码')}</a>
                            </li>
                            <li role="presentation"><a role="menuitem" class="zh_cn_btn" tabindex="-1"
                                                       href="${ctxs}/index.htm?language=zh_CN">中文</a></li>
                            <li role="presentation"><a role="menuitem" class="en_us_btn" tabindex="-1"
                                                       href="${ctxs}/index.htm?language=en_US">English</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1"
                                                       href="${ctxsso}/logout.htm">${fns:fy('退出')}</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>