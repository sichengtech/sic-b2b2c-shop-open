$(function(){
	$("#addRow").click(function(){
		var tr=$(".addLine").html();
		$("#tbody").append(tr);
	});

	$("#tab").delegate(".delButton",'click',function(){
		$(this).parent().parent().remove();
	});
	
	$("#inputForm").submit(function(){
		var pass=true;
		var msg={'name1':'','name2':'','paramSort1':'','paramSort2':'','paramSort3':''};
		$("#inputForm input[name='name']").each(function(){
			var name=$(this).val();
			if(name==null || name==""){
				pass=false;
				msg.name1=fy.getMsg("参数名不能为空");
			}
			if(name.length>64){
				pass=false;
				msg.name2=fy.getMsg("参数名不能大于64字符");
			}
		});
		$("#inputForm input[name='paramSort']").each(function(){
			var paramSort=$(this).val();
			if(paramSort==null || paramSort==""){
				pass=false;
				msg.paramSort1=fy.getMsg("排序不能为空");
			}
			if(name.length>64){
				pass=false;
				msg.paramSort2=fy.getMsg("排序不能大于10字符");
			}
			var re = /^(0|[1-9][0-9]*)$/;
			if(!re.test(paramSort)){
				pass=false;
				msg.paramSort3=fy.getMsg("排序必须数字");
			}
		});
		if(!pass){
			var msgAll=msg.name1+" "+msg.name2+" "+msg.paramSort1+" "+msg.paramSort2+" "+msg.paramSort3;
			layer.msg(msgAll);
			return false;
		}
		$("input[type=checkbox]").each(function(){
			var name=$(this).attr("name");
			if(name.indexOf("isNull")!=-1){
				//对checkbox处理，0：选中；1：未选中
				$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
						+($(this).attr("checked")?"0":"1")+"\"/>");//注意：这里的0：选中；1：未选中 与其它的模块是反的
				$(this).attr("name", "_"+$(this).attr("name"));
			}else{
				//对checkbox处理，1：选中；0：未选中
				$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
						+($(this).attr("checked")?"1":"0")+"\"/>");
				$(this).attr("name", "_"+$(this).attr("name"));
			}
		});
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
});