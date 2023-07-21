 $(document).ready(function(){
	//如果js验证开关是开的就进行js验证
/*	if(jsValidate){
		$("#inputForm").validate({
			rules: {"content":{required: true,maxlength:1024,}},
			messages: {"content":{required: "请填入申诉内容",maxlength:"最大长度不能超过255字符",}},
			submitHandler: function(form){
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}*/
	 
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
	 
	//发送对话
	$("#sendMessage").click(function(){
		$("#inputForm").attr("stat","1");
		$("#inputForm").submit();
	});
	
	//提交发送对话表单
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {"content":{required: true,maxlength:1024},"count":{required: true,maxlength:1024}},
		 	messages: {"content":{required: fy.getMsg('请输入申诉内容'),maxlength:fy.getMsg('最大长度不能超过255字符')},"count":{required: fy.getMsg('请输入对话内容'),maxlength:fy.getMsg('最大长度不能超过1024字符')}},
			submitHandler: function(form){
				//如果stat=1,说明是发送对话操作，如果stat=2,说明是提交仲裁意见
				if($("#inputForm").attr("stat") ==1){
					var count=$("#messCount").val();
					var complaintId=$("#complaintId").val();
					$.ajax({
						url:ctxs+"/trade/tradeComplaint/sendMessage.htm",
						type:'POST',
						data:{"count":count,"complaintId":complaintId},
						dataType:'json',
						success:function(data){
							if(data == 1){
								$("#noContent").hide();
								$("#messCount").val("");
								$("<p>"+fy.getMsg('商家：')+count+"</p>").prependTo($("#talkBox"));
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
	
	//提价仲裁
	$("#arbitration").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要提交仲裁吗？'),href);
	});
	
 });