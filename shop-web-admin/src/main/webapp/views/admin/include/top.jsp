<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
			<!-- header section start-->
			<div class="header-section" style="z-index:1000">
				<!--toggle button start-->
				<a class="toggle-btn"><i class="fa fa-bars"></i></a>
				<!--toggle button end-->
				<!-- 一级菜单左侧 topnavbar start-->
				<div class="topnavbar">
					<ul id="menu_1" class="nav navbar-nav">
					<c:set value="menu_" var="prefix"/>
					<c:forEach items="${fns:getMenuList()}" var="menu1">
					<c:if test="${menu1.parent.id eq '1' && menu1.isShow eq '1'}">
						<li class="menu ${menu1id == menu1.id ? ' active' : ''}">
						<c:if test="${empty menu1.href}">
							<a class="menuChild menua" href="javascript:" data-id="${menu1.id}"><span>${fns:fy(prefix.concat(menu1.name))}</span></a>
						</c:if>
						<c:if test="${not empty menu1.href}">
							<a class="menu" id="menu" href="${fn:indexOf(menu1.href, '://') eq -1 ? ctxa : ''}${menu1.href}" data-id="${menu1.id}"><span>${fns:fy(prefix.concat(menu1.name))}</span></a>
						</c:if>
						</li>
					</c:if>
					</c:forEach>
					</ul>
				</div>
				<!-- 一级菜单左侧 topnavbar end-->
				<!-- 右侧通知 menu start -->
				<div class="menu-right">
					<ul class="notification-menu">
						<li class="no1"><a target="_blank" href="${ctxf}/index.htm" class="btn btn-default dropdown-toggle info-number" title="${fns:fy('访问网站首页')}"><i class="fa fa-home"></i></a></li>
						<li class="no2"><a href="javascript:;" class="btn btn-default dropdown-toggle info-number" title="${fns:fy('站点地图')}"> <i class="fa fa-th-large"></i></a></li>
						<li class="no3">
							<a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
								<c:set value="${fns:getUser()}" var="user"/>
								<c:if test="${empty user.photo}">
									<img src="${ctxStatic}/sicheng-admin/images/photos/user-avatar.png" alt=""/> ${user.loginName} <span class="caret"></span>
								</c:if>
								<c:if test="${not empty user.photo}">
									<img src="${ctxfs}${user.photo}" onerror="fdp.defaultImage('${ctxStatic}/sicheng-admin/images/photos/user-avatar.png');" alt=""/> ${user.loginName} <span class="caret"></span>
								</c:if>
							</a>
							<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
								<li><a href="${ctxa}/sys/user/info.do"><i class="fa fa-user"></i> ${fns:fy('个人信息')}</a></li>
								<li><a href="${ctxa}/sys/user/modifyPwd.do"><i class="fa fa-cog"></i> ${fns:fy('修改密码')}</a></li>
								<li><a href="${ctxa}/sys/user/editLanguage.do"><i class="fa fa-cog"></i> ${fns:fy('语言')}</a></li>
								<li><a href="${ctxa}/logout.do"><i class="fa fa-sign-out"></i> ${fns:fy('退出')}</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<!-- 右侧通知 menu end -->
			</div>
			<!-- header section end-->
