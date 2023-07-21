$(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required:true,maxlength:100},
			},
			messages: {
				"name":{required:fy.getMsg("地区名称不能为空"),maxlength:fy.getMsg("地区名称不能超过100字符")},
			},
			submitHandler: function(form){
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});
function areaTreeselectCallBack(){
	var parentIds=$("#areaId").attr("parentids")+$("#areaId").val();
	$("#preIds").val(parentIds);
	var parentids= new Array(); 
	parentids=$("#areaId").attr("parentids").split(",");
	if(parentids.length != 2){
		$("#largeArea").hide();
	}else{
		$("#largeArea").show();
	}
}