<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('升级为采购商')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="member"/>
</head>
<body>
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('用户升级')} > ${fns:fy('升级为采购商')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('升级为采购商')}
			</dt>
			<sys:message content="${message}"/>
			<dd class="myform">
				<c:if test="${empty fn:trim(userPurchase.authType)}">
					<div class="sui-msg msg-tips msg-block" style="margin-top: 10px;">
						<div class="msg-con" style="height: 125px;padding-top: 90px;font-size: 20px;padding-left: 333px;">
							${fns:fy('你还没填写,请点击编辑！')}
							<a href="${ctxm}/upgrade/upgradePurchaser/save2.htm">${fns:fy('编辑')}</a>
						</div>
						<s class="msg-icon" style="padding-top: 86px;font-size: 28px;padding-left: 297px"></s>
					</div>
				</c:if>
				<c:if test="${fn:trim(userPurchase.authType)=='0'}">
					<div class="sui-msg msg-tips msg-block" style="margin-top: 10px;">
						<div class="msg-con" style="height: 125px;padding-top: 90px;font-size: 20px;padding-left: 332px;">
							${fns:fy('正在审核资料中，请稍等！')}
						</div>
						<s class="msg-icon" style="padding-top: 86px;font-size: 28px;padding-left: 299px"></s>
					</div>
				</c:if>
				<c:if test="${fn:trim(userPurchase.authType)=='1'}">
					<div class="sui-msg msg-tips msg-block" style="margin-top: 10px;">
						<div class="msg-con" style="height: 125px;padding-top: 90px;font-size: 20px;padding-left: 333px;">
							${fns:fy('恭喜您，您已经是采购商！')}
							<a href="${ctxm}/upgrade/upgradePurchaser/save2.htm">${fns:fy('编辑')}</a>
						</div>
						<s class="msg-icon" style="padding-top: 86px;font-size: 28px;padding-left: 297px"></s>
					</div>
				</c:if>
				<c:if test="${fn:trim(userPurchase.authType)=='2'}">
					<div class="sui-msg msg-tips msg-block" style="margin-top: 10px;">
						<div class="msg-con" style="height: 125px;padding-top: 90px;font-size: 20px;padding-left: 250px;color: red;">
							${fns:fy('你的填写信息有误,审核未通过,请重新填写!')}
							<a href="${ctxm}/upgrade/upgradePurchaser/save2.htm">${fns:fy('编辑')}</a>
						</div>
						<s class="msg-icon" style="padding-top: 87px;font-size: 28px;padding-left: 215px;color: red;"></s>
					</div>
				</c:if>
			</dd>
		</dl>
	</div>
</body>
</html>
