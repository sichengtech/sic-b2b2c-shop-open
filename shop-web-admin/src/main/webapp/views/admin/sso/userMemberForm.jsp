<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>会员（买家）管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userMemberForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userMember.UId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}会员（买家）</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userMember/list.do"> <i class="fa fa-user"></i> 会员（买家）列表</a></li>
				<shiro:hasPermission name="sso:userMember:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 会员（买家）${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>会员（买家）管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userMember/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userMember.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${userMember.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 积分（积分规则设置送的积分数）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="point"  maxlength="64" class="form-control input-sm" value="${userMember.point}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写积分（积分规则设置送的积分数）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否允许购买(0不允许、1允许)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('member_buy')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isBuy" value="${item.value}" ${item.value==userMember.isBuy?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否允许购买(0不允许、1允许)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 禁止购买日期（开始时间）&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="notBuyDateStart" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userMember.notBuyDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写禁止购买日期（开始时间）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="notBuyDateEnd" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userMember.notBuyDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写禁止购买日期（结束时间）(需要有定时程序来检查并解锁)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 预存款，账户余额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="balance"  maxlength="12" class="form-control input-sm" value="${userMember.balance}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写预存款，账户余额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 冻结金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="frozenMoney"  maxlength="12" class="form-control input-sm" value="${userMember.frozenMoney}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写冻结金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付密码&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="paymentPassword"  maxlength="64" class="form-control input-sm" value="${userMember.paymentPassword}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付密码<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 会员标签（给会员打标）(关联member_tag表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="memberTagId"  maxlength="19" class="form-control input-sm" value="${userMember.memberTagId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写会员标签（给会员打标）(关联member_tag表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 真实姓名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="realName"  maxlength="64" class="form-control input-sm" value="${userMember.realName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写真实姓名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 会员头像path&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="headPicPath"  maxlength="64" class="form-control input-sm" value="${userMember.headPicPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写会员头像path<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 性别(1男、2女、3保密)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('sex')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="sex" value="${item.value}" ${item.value==userMember.sex?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写性别(1男、2女、3保密)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 生日&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="birthday" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${userMember.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写生日<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 邮编&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="postcode"  maxlength="64" class="form-control input-sm" value="${userMember.postcode}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写邮编<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 国家(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" value="${userMember.countryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写国家(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 国家名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" value="${userMember.countryName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写国家名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 省(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" value="${userMember.provinceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写省(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 省名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" value="${userMember.provinceName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写省名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 市(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" value="${userMember.cityId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写市(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 市名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" value="${userMember.cityName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写市名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 县(关联地区表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" value="${userMember.districtId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写县(关联地区表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 县名字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" value="${userMember.districtName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写县名字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 详细地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" value="${userMember.detailedAddress}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写详细地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> qq号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="qq"  maxlength="64" class="form-control input-sm" value="${userMember.qq}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写qq号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 微博&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="microblog"  maxlength="64" class="form-control input-sm" value="${userMember.microblog}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写微博<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 微信&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="weChat"  maxlength="64" class="form-control input-sm" value="${userMember.weChat}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写微信<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sso:userMember:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
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