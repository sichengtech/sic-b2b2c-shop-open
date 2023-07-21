$(function(){
	//新增相册
	$("#addRow").click(function(){
		var tr=$(".addLine").html();
		$("#tbody").append(tr);
	});

	$("#tab").delegate(".delButton",'click',function(){
		var albumId = $(this).parent().parent().find("input[name='albumId']").val();
		var picCount = $(this).parent().prev().text();
		if(albumId==null || albumId==''){
			$(this).parent().parent().remove();
		}else{
			var href=$(this).attr("href");
			fdp.confirm(fy.getMsg('当前相册中有')+picCount+fy.getMsg('张图片，确定要删除么?'),href); 
		}
	});
	
	$("#inputForm").submit(function(){
		var pass=true;
		var msg={'sort1':'','sort2':'','sort3':'','albumName1':'','albumName2':''};
		$("#inputForm input[name='sort']").each(function(){
			var sort=$(this).val();
			if(sort==null || sort==""){
				pass=false;
				msg.sort1=fy.getMsg('排序不可为空');
			}
			if(name.length>64){
				pass=false;
				msg.sort2=fy.getMsg('排序不能大于12字符');
			}
			var re = /^(0|[1-9][0-9]*)$/;
			if(!re.test(sort)){
				pass=false;
				msg.sort3=fy.getMsg('排序必须数字');
			}
		});
		$("#inputForm input[name='albumName']").each(function(){
			var albumName=$(this).val();
			if(albumName==null || albumName==""){
				pass=false;
				msg.albumName1=fy.getMsg('相册名不可为空');
			}
			if(name.length>64){
				pass=false;
				msg.albumName2=fy.getMsg('相册名不能大于64字符');
			}
		});
		if(!pass){
			var msgAll=msg.sort1+" "+msg.sort2+" "+msg.sort3+" "+msg.albumName1+" "+msg.albumName2;
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