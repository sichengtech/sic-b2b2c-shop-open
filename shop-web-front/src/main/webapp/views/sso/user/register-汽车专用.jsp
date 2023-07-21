<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>注册</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/register.js" type="text/javascript"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var usernameMax=${siteRegister.usernameMax};
	var usernameMin=${siteRegister.usernameMin};
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
	var disableUsername='${siteRegister.disableUsername}';
</script>
</head>
<body>
<div id="agree" style="display: none">${siteRegister.agreement}</div>
<div class="sso-main sso-reg-main-car">
	<div class="order-tabs sso-reg-box sui-container">
		<ul class="sui-nav nav-tabs">
			<li class="member active pl200"><a href="#memberRe" data-toggle="tab"><b></b>个人注册</a></li>
			<li class="seller"><a href="#sellerRe" data-toggle="tab"><b></b>商家注册</a></li>
			<li class="store"><a href="#storeRe" data-toggle="tab"><b></b>汽车服务门店入驻</a></li>
		</ul>
		<sys:message content="${message}"/>
		<div class="tab-content pl200 pr200">
			<ul id="memberRe" class="tab-pane active table-css">
				<form id="inputForm1" class="sui-form form-horizontal" action="${ctxsso}/register/save2.htm?ssoReg=1" method="post">
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">身份选择：</label>
						<div class="controls">
						  <label class="radio-pretty inline checked">
							<input type="radio" checked="checked" name="radio"><span>普通用户</span>
						  </label>
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>车主用户</span>
						  </label>							
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">中国+86：</label>
						<div class="controls">
							<input type="text" name="mobile" id="mobile" class="input-xlarge" placeholder="请输入常用手机号码" value="${userMain.email}">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">短信验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode1" name="validateCode" class="input-Code" placeholder="请输入验证码" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label for="inputInfo" class="control-label"></label>
						<input id="agreeType1" type="checkbox"><span>我已阅读并同意</span>
						<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注 册"/>
					</label>
				</form>
			</ul>
			<ul id="sellerRe" class="tab-pane">
				<form id="inputForm2" class="sui-form form-horizontal" action="${ctxsso}/register/save2.htm?ssoReg=2" method="post">
					<div class="titlebox">账户信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="titlebox">企业属性</div>
					<div class="control-group info" style="float:left">
						<label for="inputInfo" class="control-label">企业类型：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">行业属性：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="titlebox">企业信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">公司名称：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">公司介绍：</label>
						<div class="controls">
							  <textarea rows="5"></textarea>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">公司所在地：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">公司地址：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">客服电话：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">公司网址：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">公司品牌：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
						</div>
					</div>
					<div class="titlebox">联系人信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">联系人姓名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">所在部门：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>市场部</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">市场部</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">本人手机：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="请输入验证码" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">短信验证码：</label>
						<div class="controls">
							<input type="text" id="smsVerification" name="smsVerification" class="input-Code" placeholder="请输入验证码" value="">
							<button type="button" id="smsSender" class="post-code">发送验证码</button>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label id="checkbox2" class="">
							<label for="inputInfo" class="control-label"></label>
							<input id="agreeType2" type="checkbox"><span>我已阅读并同意</span>
							<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
						</label>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注册"/>
					</label>
				</form>
			</ul>
			<ul id="storeRe" class="tab-pane">
				<form id="inputForm2" class="sui-form form-horizontal" action="${ctxsso}/register/save2.htm?ssoReg=2" method="post">
					<div class="titlebox">账户信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">用 户 名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="${userMain.loginName}" placeholder="请设置${siteRegister.usernameMin}~${siteRegister.usernameMax}字节的用户名">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">设置密码：</label>
						<div class="controls">
							<input type="password" name="password" id="password" class="input-xlarge"  placeholder="请设置${siteRegister.pwdMin}~${siteRegister.pwdMax}字节的密码">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">确认密码：</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword" class="input-xlarge" placeholder="重复一次上面的密码">
						</div>
					</div>
					<div class="titlebox">门店服务</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">门店类型：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店类型：</label>
						<div class="controls">
						  <label class="radio-pretty inline checked">
							<input type="radio" name="radio"><span>洗车美容</span>
						  </label>
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>汽车养护</span>
						  </label>							
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>汽车贴膜</span>
						  </label>							
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>影音升级</span>
						  </label>
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>汽车改装</span>
						  </label><br>
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>汽车轮胎</span>
						  </label>							
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>装璜装饰</span>
						  </label>							
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>钣金喷漆</span>
						  </label>
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>快修快保</span>
						  </label>							
						  <label class="radio-pretty inline">
							<input type="radio" name="radio"><span>汽车维修</span>
						  </label>							
						</div>
					</div>
					<div class="titlebox">门店信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店名称：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店介绍：</label>
						<div class="controls">
							  <textarea rows="5"></textarea>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">门店所在地：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店地址：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店面积：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">经营品牌：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
							<input type="text" name="loginName" id="loginName" class="input-medium" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">建店时间：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">老板姓名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">老板手机：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">门店照片：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">门店人数：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>杭州</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">营业时间：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>开店</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">9：00</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">10：00</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>闭店</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">18：00</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">22：00</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">服务热线：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">店长姓名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">店长手机：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="titlebox">联系人信息</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">联系人姓名：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">所在部门：</label>
						<div class="controls">
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
									<input value="hz" name="city" type="hidden"><i class="caret"></i><span>市场部</span></a>
								  <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
									<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">市场部</a></li>
								  </ul>
					  			</span>
							</span>
					  
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">本人手机：</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="" placeholder="">
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">验证码：</label>
						<div class="controls">
							<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="请输入验证码" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="验证码" class="input-Code-img">
							</a>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">短信验证码：</label>
						<div class="controls">
							<input type="text" id="smsVerification" name="smsVerification" class="input-Code" placeholder="请输入验证码" value="">
							<button type="button" id="smsSender" class="post-code">发送验证码</button>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label id="checkbox2" class="">
							<label for="inputInfo" class="control-label"></label>
							<input id="agreeType2" type="checkbox"><span>我已阅读并同意</span>
							<a class="agreement" href="javascript:void(0);">《会员注册条款》</a>
						</label>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="注册"/>
					</label>
				</form>
			</ul>
		</div>
	</div>
</div>
</body>
</html>
