$(function(){
	//表单验证
	$("#myForm").submit(function(){
		var pass=true;
		var msg={'name1':'','name2':'','tool':'','account1':'','account2':'',
				'type':'','sort1':'','sort2':'','sort3':''};
		$("#myForm input[name='name']").each(function(){
			var name=$(this).val();
			if(name==null || name==""){
				pass=false;
				msg.name1=fy.getMsg('请输入客服名称');
			}
			if(name.length>64){
				pass=false;
				msg.name2=fy.getMsg('客服名称长度不能超过64字符');
			}
		});
		$("#myForm input[name='tool']").each(function(){
			var tool=$(this).val();
			if(tool==null || tool==""){
				pass=false;
				msg.tool=fy.getMsg('请选择客服工具');
			}
		});
		$("#myForm input[name='account']").each(function(){
			var account=$(this).val();
			if(account==null || account==""){
				pass=false;
				msg.account1=fy.getMsg('请输入客服账号');
			}
			if(account.length>64){
				pass=false;
				msg.account2=('客服账号长度不能超过64字符');
			}
		});
		$("#myForm input[name='type']").each(function(){
			var type=$(this).val();
			if(type==null || type==""){
				pass=false;
				msg.type=fy.getMsg('请选择客服类型');
			}
		});
		$("#myForm input[name='sort']").each(function(){
			var sort=$(this).val();
			if(sort==null || sort==""){
				pass=false;
				msg.sort1=fy.getMsg('请输入客服排序');
			}
			if(sort.length>64){
				pass=false;
				msg.sort2=fy.getMsg('排序长度不能超过10字符');
			}
			var re = /^(0|[1-9][0-9]*)$/;
			if(!re.test(sort)){
				pass=false;
				msg.sort3=fy.getMsg('排序必须数字');
			}
		});
		if(!pass){
			var msgAll=msg.name1+" "+msg.name2+" "+msg.tool+" "+msg.account1+" "+msg.account2+" "+msg.type
			+" "+msg.sort1+" "+msg.sort2+" "+msg.sort3;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	//添加行
	$("#addCustSer").click(function(){
		var tr=$(".addLine").html();
		$("#tbody").append(tr);
	});
	//删除行
//	$("#tbody").delegate("a[class='sui-btn btn-danger delLine']",'click',function(e){
//		//询问框
//		layer.confirm('确定要删除么？', {
//			btn: ['确定','关闭'] //按钮
//		}, function(){
//			layer.msg('确定删除', {icon: 1});
//			$("#delLine").parent().parent().remove();
//			});
//	});
	$("#tbody").delegate("a[class='sui-btn btn-danger delLine']",'click',function(e){
		$(this).parent().parent().remove();
	});
});