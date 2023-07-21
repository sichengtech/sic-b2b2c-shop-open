<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<div style="margin-top: 5px; margin-left: 10px;" class="pull-left">
	<button id="toolbar_full_screen" type="button" class="btn tooltips btn-warning btn-xs" data-placement="bottom" title="${fns:fy('全屏')}">
		<i class="fa fa-arrows-alt"></i>
	</button>
	<button id="toolbar_operate_tips" class="btn tooltips btn-info btn-xs" title="${fns:fy('操作提示')}" data-placement="bottom">
		<i class="fa fa-lightbulb-o"></i>
	</button>
	<button type="button" class="btn tooltips btn-info btn-xs" data-placement="bottom" title="${fns:fy('后退')}" onclick="javascript:history.go(-1);">
		<i class="fa fa-mail-reply"></i>
	</button>
</div>
