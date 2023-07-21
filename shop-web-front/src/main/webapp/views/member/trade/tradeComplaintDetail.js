$(document).ready(function(){
    /**
     * 初始化MyUpload控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var myupload_id=$("#vessel");
    var upload;
    if(myupload_id.length){
	    upload = new MyUpload({
	        // 获取token的路径
	        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
	        // 文件上传到的服务器
	        server: ctxu+'/upload/webUploadServer.htm',
	        // 容器Id
	        container: '#vessel',
	        buttonStyle: 1,
	        //accept: 'file',
	        fileSingleSizeLimit: 1024 * 1024 * 5,
	        fileNumLimit: 3
	    });
    }

	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"content":{required: true,maxlength:255},
				"count":{required: true,maxlength:1024}
			},
			messages: {
				"content":{required: fy.getMsg('请输入投诉内容'),maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('最大长度不能字符')},
				"count":{required: fy.getMsg('请输入对话内容'),maxlength: fy.getMsg('最大长度不能超过') + 1024 + fy.getMsg('最大长度不能字符')}
			},
			submitHandler: function(form){
				//如果stat=1,说明是发送对话操作，如果stat=2,说明是提交仲裁意见
				if($("#inputForm").attr("stat") ==1){
					var content=$("#messCount").val();
					var complaintId=$("#complaintId").val();
					//如果stat=1,说明是发送对话操作，如果stat=2,说明是提交仲裁意见
					var count=$("#messContent").val();
					var complaintId=$("#complaintId").val();
					$.ajax({
						url:ctxm+"/trade/tradeComplaint/sendMessage.htm",
						type:'POST',
						data:{"count":count,"complaintId":complaintId},
						dataType:'json',
						success:function(data){
							if(data == 1){
								$("#noContent").hide();
								$("#messContent").val("");
								$("<p>"+fy.getMsg('买家')+"："+count+"</p>").prependTo($("#talkBox"));
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('发送失败'),2000);
							}
						}
					});
				}else{
					var datas=upload.datas;
					for(var i=0;i<datas.length;i++){
						$("input[name='img"+(i+1)+"']").val(datas[i].path);
					}
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			}
		});
	}
	
	//发送对话
	$("#sendMessage").click(function(){
		$("#inputForm").attr("stat","1");
		$("#inputForm").submit();
	});
	
	//刷新对话
	$("#refreshMessage").click(function(){
		var complaintId=$("#complaintId").val();
		$.ajax({
			url:ctxm+"/trade/tradeComplaint/refreshMessage.htm",
			type:'POST',
			data:{"complaintId":complaintId},
			dataType:'json',
			success:function(data){
				if(data.length>0){
					$("#talkBox").html("");
					for(var i=0; i<data.length; i++){
						var userType="";
						if(data[i].userType == '1'){
							userType=fy.getMsg('买家');
						}else if(data[i].userType == '2'){
							userType=fy.getMsg('卖家');
						}else if(data[i].userType == '3'){
							userType=fy.getMsg('平台');
						}
						$("#talkBox").append("<p>"+userType+"："+data[i].count+"</p>");
					}
				}
			}
		});
	});
	
	//提交仲裁
	$("#arbitration").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要提交仲裁吗？'),href);
	});
 });