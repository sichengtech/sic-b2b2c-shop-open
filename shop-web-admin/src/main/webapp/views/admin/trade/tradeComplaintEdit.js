$(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {"count":{required: true,maxlength:1024},"systemHandleHandelOpinion":{required: true,maxlength:1024}},
		 	messages: {"count":{required: fy.getMsg("请输入内容"),maxlength:fy.getMsg("最大长度不能超过1024字符")},"systemHandleHandelOpinion":{required: fy.getMsg("请输入内容"),maxlength:fy.getMsg("最大长度不能超过1024字符")}},
			submitHandler: function(form){
				//如果stat=1,说明是发送对话操作，如果stat=2,说明是提交仲裁意见
				if($("#inputForm").attr("stat") ==1){
					var count=$("#messCount").val();
					var complaintId=$("#complaintId").val();
					$.ajax({
						url:ctxa+"/trade/tradeComplaintDetail/sendMessage.do",
						type:'POST',
						data:{"count":count,"complaintId":complaintId},
						dataType:'json',
						success:function(data){
							if(data!=null &&  data.message == 1){
								$("#noContent").hide();
								$("#messCount").val("");
								$("<p><span class='userType'>"+fy.getMsg('平台')+"：</span>"+count+"<a class='m-l-15 shield' id='"+data.tradeComplaintDetail.cdId+"' href='javascript:;' data-talk-id='9'>"+fy.getMsg('屏蔽')+"</a></p>").prependTo($(".messageDetail #talkBox"));
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('发送失败'),2000);
							}
						}
					});
				}else{
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			}
		});
	}
	
	//直接仲裁投诉按钮的点击事件
	$("#endComplaint").click(function(){
		$("#handelDiv").show();
		$("#handelBtn").hide();
	});
	
	//发送对话
	$("#sendMessage").click(function(){
		$("#inputForm").attr("stat","1");
		$("#inputForm").submit();
	});
	
	//刷新对话
	$("#refreshMessage").click(function(){
		var complaintId=$("#complaintId").val();
		$.ajax({
			url:ctxa+"/trade/tradeComplaintDetail/refreshMessage.do",
			type:'POST',
			data:{"complaintId":complaintId},
			dataType:'json',
			success:function(data){
				if(data.length>0){
					$("#talkBox").html("");
					for(var i=0; i<data.length; i++){
						var userType="";
						if(data[i].userType == '1'){
							userType=fy.getMsg("买家");
						}else if(data[i].userType == '2'){
							userType=fy.getMsg("卖家");
						}else if(data[i].userType == '3'){
							userType=fy.getMsg("平台");
						}
						var count=data[i].count+"<a class='m-l-15 shield' id='"+data[i].cdId+"' href='javascript:;' data-talk-id='9'>"+fy.getMsg('屏蔽')+"</a>";
						if(data[i].isShield =='1'){
							count=fy.getMsg("内容已被管理员屏蔽");
						}
						$(".messageDetail #talkBox").append("<p><span class='userType'>"+userType+":</span>"+count);
					}
				}
			}
		});
	});
	
	//屏蔽对话记录
	$(".panel").delegate("a[class='m-l-15 shield']",'click',function(e){
	//$(".shield").click(function(){
		var cdId=$(this).attr("id");
		if(cdId!="" && cdId!=null){
			$.ajax({
				url:ctxa+"/trade/tradeComplaintDetail/shieldMessage.do",
				type:'POST',
				data:{"cdId":cdId},
				dataType:'json',
				success:function(data){
					if(data==1){
						var type=$("#"+cdId).parent().find(".userType").text();
						$("#"+cdId).parent().text(type+fy.getMsg("内容已被管理员屏蔽"));
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('屏蔽失败'),2000);
					}
				}
			});
		}
	});
	
	//提交仲裁意见
	$("#messBtn").click(function(){
		//提交仲裁时不验证对话的输入框
		$("#messCount").attr("name","count1");
		$("#inputForm").attr("stat","2");
		$("#inputForm").attr("action",ctxa+"/trade/tradeComplaint/edit2.do");
		$("#inputForm").submit();
	});
	
	//审核并交有商家申诉
	$("#auditBtn").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要审核么？'),href);
	});
 });