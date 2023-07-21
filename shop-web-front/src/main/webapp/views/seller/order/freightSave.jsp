<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>新增物流配送规则</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
 .sui-msg.msg-block{margin:10px!important;}
 .firstTd{border: 1px dotted #e6e6e6;}
 .sui-modal{border: none!important;border-radius:5px;}
</style>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			<dt class="box-header"><h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>新增物流配送规则</span></h4><ul class="sui-breadcrumb"><li>当前位置:</li><li><a href="#">首页</a></li><li class="active">店铺设置</li></ul></dt>
			<div class="sui-msg msg-large msg-tips msg-block">
			 <div class="msg-con">
				 <ul>
					<h4>提示信息</h4>
					<li>xxxxxxxxx</li>
					<li>xxxxxxxxx</li>
					<li>xxxxxxxxx</li>
					<li>xxxxxxxxx</li>
				 </ul>
				<button type="button" data-dismiss="msgs" class="sui-close">×</button>
			 </div>
			 <s class="msg-icon" style="margin-top: 10px"></s>
			</div>
			<form class="sui-form form-inline" method="post">
				<dd>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>运费模板名称：</label>
						<input type="text" class="input-xlarge input-error input-error">
						<div class="sui-msg msg-error help-inline">
							<div class="msg-con"><span>不能为空</span></div>
							<i class="msg-icon"></i>
						</div>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">请输入运费模板名称。</div>
						</div>
					</div>
					<div class="control-group"><label class="control-label">是否包邮：</label>
						<label class="radio-pretty inline checked" style="text-align: left;">
							<input type="radio" checked="checked" name="radio"><span>自定义运费</span>
						</label>
						<label class="radio-pretty inline" style="text-align: left;">
							<input type="radio" name="radio"><span> 卖家承担运费</span>
						</label>
					</div>
					<div class="control-group"><label class="control-label">计价方式：</label>
						<label class="radio-pretty inline checked" style="text-align: left;">
							<input type="radio" checked="checked" name="radio"><span> 按件数</span>
						</label>
						<label class="radio-pretty inline" style="text-align: left;">
							<input type="radio" name="radio"><span> 按重量</span>
						</label>
						<label class="radio-pretty inline" style="text-align: left;">
							<input type="radio" name="radio"><span> 按体积</span>
						 </label>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>设置详细信息：</label>
						<table class="sui-table table-bordered-simple" style="width: 85%;float: right;">
							<thead>
								<th>运送到</th>
								<th>首件(件)</th>
								<th>首费(元)</th>
								<th>续件(件)</th>
								<th>续费(元)</th>
								<th>操作</th>
							</thead>
							<tbody id="tbody">

							</tbody>
							<tfoot>
								<tr colspan="6" class="batchEditTr" style="display: none;">
									<td colspan="6">
									<label class="checkbox-pretty inline checked" style="padding: 0;width: 42px;">
										<input type="checkbox" checked="checked"><span>全选</span>
									</label>
									<a href="javascript:void(0);" class="sui-btn btn-bordered" id="">批量设置</a>
									<a href="javascript:void(0);" class="sui-btn btn-bordered" id="">批量删除</a>
									</td>
								</tr>
								<tr>
									<td colspan="6">
									<a href="javascript:void(0);" class="sui-btn btn-bordered" id="setFreight"><i class="sui-icon icon-tb-locationfill"></i>为指定地区设置运费</a>
									<a href="javascript:void(0);" class="sui-btn btn-bordered" id="batchEdit" style="display:none;">批量操作</a>
									<a href="javascript:void(0);" class="sui-btn btn-bordered" id="cancelBatchEdit" style="display:none;">取消批量</a>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="control-group">
						<div class="clear"></div>
						<div class="" style="margin-left: 140px; margin-top: 10px;">
							<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary">保存</a>
						</div>
					</div>
				</dd>
			</form>
		</dl>
	</div>
</div>

<!-- 隐藏的开始 -->
<table style="display: none;">
	<tbody class="addLine">
	<tr class="appendTr">
		<td class="firstTableTd" width="40%">未添加地区 </td>
		<td width="6%"><input type="text" placeholder=".input-small" class="input-small" value="1"></td>
		<td width="10%">
			<div class="input-append">
				<input type="text" placeholder="" class="input-small"><span class="add-on">￥</span>
			</div>
		</td>
		<td width="6%"><input type="text" placeholder="" class="input-small" value="1"></td>
			<td width="10%">
			<div class="input-append">
				<input type="text" placeholder="" class="input-small"><span class="add-on">￥</span>
			</div>
		</td>
		<td width="18%">
			<a href="javascript:void(0);" class="sui-btn btn-success" data-toggle="modal" data-target="#areaModal">编辑</a>
			<a class="sui-btn btn-danger delLine" id="delLine">删除</a>
		</td>
	</tr>
	</tbody>
</table>
<!-- 隐藏的行结束 -->
<!-- 编辑弹出框开始 -->
	<div id="areaModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="sui-close">×</button>
					<h4 id="myModalLabel" class="modal-title">选择区域</h4>
				</div>
				<div class="modal-body" style="padding: 0;">
						<form class="sui-form" method="post">
						<table class="sui-table table-zebra">
						<tbody>
							<tr>
								<td class="firstTd">
								 <label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>华北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>北京</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>天津</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>河北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>山西</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>内蒙</span>
								 </label>
								</td>
							</tr>
							 	<tr>
								<td class="firstTd">
								 <label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>华北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>北京</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>天津</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>河北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>山西</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>内蒙</span>
								 </label>
								</td>
							</tr>
							 	<tr>
								<td class="firstTd">
								 <label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>华北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>北京</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>天津</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>河北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>山西</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>内蒙</span>
								 </label>
								</td>
							</tr>
							 	<tr>
								<td class="firstTd">
								 <label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>华北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>北京</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>天津</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>河北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>山西</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>内蒙</span>
								 </label>
								</td>
							</tr>
							 	<tr>
								<td class="firstTd">
								 <label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>华北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>北京</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>天津</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>河北</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>山西</span>
								 </label>
								</td>
								<td>
								<label class="checkbox-pretty inline checked">
									<input type="checkbox" checked="checked"><span>内蒙</span>
								 </label>
								</td>
							</tr>
						</tbody>
						</table>
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large">确定</button>
					<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 编辑弹出框结束 -->
<script type="text/javascript">
	//为指定地区添加运费
	$("#setFreight").click(function(){
		var tr=$(".addLine").html();
		$("#tbody").append(tr);
		$("#batchEdit").css("display","");
	});
	//批量操作
	$("#batchEdit").click(function(){
		$(".firstTableTd").prepend("<input type=\"checkbox\" checked=\"checked\" class=\"check\">");
		$(".batchEditTr").css("display","block");
		$(this).css("display","none");
		$("#cancelBatchEdit").css("display","");
	});
	//删除
	$("#tbody").delegate("a[class='sui-btn btn-danger delLine']",'click',function(e){
		if($(".appendTr").length == 2){
			$("#batchEdit").css("display","none");
		}
		$(this).parent().parent().remove();
	});
	//取消批量
	$("#cancelBatchEdit").click(function(){
		$(".firstTableTd").find("input[class='check']").remove();
		$(".batchEditTr").css("display","none");
		$(this).css("display","none");
		$("#batchEdit").css("display","");
	});
</script>
</body>
</html>