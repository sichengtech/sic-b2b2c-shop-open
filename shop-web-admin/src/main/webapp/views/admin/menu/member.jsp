<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-book"></i> <span>会员管理</span><small>会员</small></a>
	<ul class="sub-menu-list">
		<li><a href="${ctx}/views/admin/member/memberUserList.jsp"><i class="fa fa-user"></i> <span>会员管理</span></a></li>
		<li><a href="${ctx}/views/admin/member/memberExperienceList.jsp"><i class="fa fa-user"></i> <span>经验明细（不做）</span></a></li>
		<li><a href="${ctx}/views/admin/member/memberLevelList.jsp"><i class="fa fa-user"></i> <span>等级管理（不做）</span></a></li>
		<li><a href="#"><i class="fa fa-user"></i> <span>会员标签（不做）</span></a></li>
		<li><a href="${ctx}/views/admin/member/memberNotify.jsp"><i class="fa fa-user"></i> <span>会员通知</span></a></li>
	</ul>
</li>
<li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-compass"></i> <span>积分管理</span><small>会员</small></a>
	<ul class="sub-menu-list">
		<li><a href="${ctx}/views/admin/score/scoreItemsList.jsp"><i class="fa fa-compass"></i> <span>积分明细</span></a></li>
		<li><a href="${ctx}/views/admin/score/scoreRuleForm.jsp"><i class="fa fa-compass"></i> <span>规则设置</span></a></li>
	</ul>
</li>