<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商家申请入驻')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sellerLoginAfter"/>
<%-- 引入head部分 --%>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeEnterAuth.js"></script>
</head>
<body class="seller-join">
<!--头部结束-->
<div class="seller-join join-content">
	<dl class="box">
	<dt class="box-header"><h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('商家申请入驻')}</span></h4><ul class="sui-breadcrumb"><li>${fns:fy('当前位置:')}</li><li><a href="javascript:void(0)">${fns:fy('商家申请入驻')}</a></li><li><a href="javascript:void(0)">${fns:fy('开店状态')}</a></li></ul></dt>
		<c:if test="${storeEnterAuth.status=='40'}">
			<dd class="join-steps-1">
				<img src="${ctxStatic}/sicheng-seller/images/join_ico_2.png" alt="" class="join-ico"/>
				<p>${fns:fy('您的入驻申请已经提交')}</br>
					${fns:fy('请耐心等待审核')}</p>
			</dd>
		</c:if>
		<c:if test="${storeEnterAuth.status=='50'}">
			<dd class="join-steps-1">
				<img src="${ctxStatic}/sicheng-seller/images/join_ico_2.png" alt="" class="join-ico"/>
				<p>${fns:fy('店铺已经开通，退出再次登录后进入店铺管理')}</br>
					<a href="${ctxsso}/logout.htm" class="btn-xxxxxlargeAuth51">${fns:fy('安全退出')}</a>
				</p>
			</dd>
		</c:if>
		<c:if test="${storeEnterAuth.status=='60'}">
			<dd class="join-steps-1">
				<img src="${ctxStatic}/sicheng-seller/images/join_ico_1.png" alt="" class="join-ico"/>
				<p style="color: red;word-wrap:break-word"">
					<span>${fns:fy('您的入驻申请失败')}</span><br>
					<span>${fns:fy('失败原因:')}</span><br>
					<span>${storeEnterAuth.twoAuditOpinion}</span></br>
					<a href="${ctxs}/store/storeEnterAuth/auth4.htm" class="btn-xxxxxlargeAuth61">${fns:fy('重新申请')}</a>
				</p>
			</dd>
		</c:if>
	</dl>
</div>
</body>
</html>