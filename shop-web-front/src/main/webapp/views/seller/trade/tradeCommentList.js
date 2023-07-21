	//卖家解释弹出框
	$(".explain").click(function(){
		var replyId=$(this).attr("id");
		var con=$(this).parent().parent().find("span[class='count']").text();
		$("#explainForm .replyId").attr("value",replyId);
		$("#explainForm #commentContent").text(con);
		var count=$(this).parent().parent().find(".count").text();
		$("#countModal").html(count);
		var content=$("#explainModal").html();
		var stat=$(this).attr("explain");
		var title="";
		if(stat == "1"){
			title=fy.getMsg('评价解释');
		}
		if(stat == "2"){
			title=fy.getMsg('追评解释');
		}
		layer.open({
			type: 1,
			title:title,
			area: ['520px', '230px'],
			shadeClose: true, //点击遮罩关闭
			content:content,
			success: function(layero, index){
				if(jsValidate){
					$(layero).find("#explainForm").validate({
						rules: {"content":{required: true,maxlength:512}},
					 	messages: {"content":{required: fy.getMsg('解释内容不能为空'),maxlength:fy.getMsg('最大长度不能超过512字符')}},
						submitHandler: function(form){
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					});
				}
			}
			
		}); 
	});