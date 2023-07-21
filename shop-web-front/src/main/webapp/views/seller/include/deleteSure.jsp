<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!-- 删除弹出框开始 -->
	<div id="Mymodel" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade" style="padding:0!important;">
	 <div class="modal-dialog">
		<div class="modal-content">
		 <div class="modal-header">
			<button type="button" data-dismiss="modal" aria-hidden="true" class="sui-close">×</button>
			<h4 id="myModalLabel" class="modal-title">${fns:fy('操作提醒')}</h4>
		 </div>
		 <div class="blankLine"></div>
		 <div class="modal-body">
			<input id="thisVal" type="hidden" >
			<div class="sui-msg msg-large msg-block msg-error" style="margin-left:0px!important;margin-right:0 px!important;">
			 <div class="msg-con isSure">${fns:fy('您确定要删除么？')}</div>
			 <s class="msg-icon"></s>
			</div>
		 </div>
		 <div class="modal-footer">
			<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large">${fns:fy('确定')}</button>
			<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large">${fns:fy('取消')}</button>
		 </div>
		</div>
	 </div>
	</div>
	<!-- 删除弹出框结束 -->
	<script type="text/javascript">
	$(function(){
		//删除
		$(".deleteSure").click(function(){
			var deleteSureId=$(this).attr("deleteSureId");
			var isSure=$(this).attr("isSure");
			$("#thisVal").val(deleteSureId);
			$(".isSure").html(isSure);
			$('#Mymodel').modal('show');
		});
	});
	</script>