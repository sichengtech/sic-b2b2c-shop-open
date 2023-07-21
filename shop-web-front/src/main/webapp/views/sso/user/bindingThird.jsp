<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('第三方登录')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
</head>
<body>
	<div class="sso-center">
	<dl>
		<dt>
			${fns:fy('第三方登录绑定')}
		</dt>
		<dd>
		<div class="infomain sui-form form-horizontal">

        <dl class="bindapilist">
          <dt class="sinaweibo"></dt>
          <dd>
            <h3>${fns:fy('新浪微博')}</h3>
            <div class="status_bind"><i class="sui-icon icon-tb-infofill"></i><span>${fns:fy('未绑定')}</span></div>
            <a class="th_action_btn" href="javascript:void(0);" title="">${fns:fy('添加绑定')}</a>
          </dd>
        </dl>
		
        <dl class="bindapilist">
          <dt class="qq"></dt>
          <dd>
            <h3>${fns:fy('qq')}</h3>
            <div class="status_bind"><i class="sui-icon icon-tb-infofill"></i><span>${fns:fy('未绑定')}</span></div>
            <a class="th_action_btn" href="javascript:void(0);" title="">${fns:fy('添加绑定')}</a>
          </dd>
        </dl>
		
        <dl class="bindapilist binding">
          <dt class="weixin"></dt>
          <dd>
            <h3>${fns:fy('微信')}</h3>
            <div class="status_bind"><i class="sui-icon icon-tb-roundcheckfill"></i><span>${fns:fy('已绑定')}</span></div>
            <a class="th_action_btn" href="javascript:void(0);" title="">${fns:fy('取消绑定')}</a>
          </dd>
        </dl>
		
		
		</div>
		</dd>
	</dl>
	</div>
	<!--main-center-->
</body>
</html>
