$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"money1":{maxlength:12,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"money2":{maxlength:12,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"money3":{maxlength:12,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"cause":{required: true,maxlength:255,},
			},
			messages: {
				"money1":{maxlength:fy.getMsg('最大长度不能超过12字符'),regex:fy.getMsg('输入正整数或两位以内的正小数')},
				"money2":{maxlength:fy.getMsg('最大长度不能超过12字符'),regex:fy.getMsg('输入正整数或两位以内的正小数')},
				"money3":{maxlength:fy.getMsg('最大长度不能超过12字符'),regex:fy.getMsg('输入正整数或两位以内的正小数')},
				"cause":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else if("money1"==element.attr("name") || "money2"==element.attr("name") || "money3"==element.attr("name")){
					error.appendTo(element.parent().parent());
				}else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				//未选择操作用户
				var accountAll = $("input[type='radio']").is(':checked');
				if(!accountAll){
					layer.msg(fy.getMsg('请选择一个操作账户'));
					return false;
				}
				//选择具体操作用户验证
				var lock=true;
				var result=[];
				$(".accountAll").each(function(i, n){
					var radio_cheaked = $(n).find("input[type='radio']").is(':checked') && $(n).find("input[type='radio']:checked");
					if(radio_cheaked){
						result.push({radio_cheaked:radio_cheaked,n:n, index:i});
						//选择的操作账户
						var select_value=$(radio_cheaked).parent().parent().find("select").val();
						var select_text=$(radio_cheaked).parent().parent().find("select option:selected").text();
						//选择的操作账户的操作值
						var input_value=$(radio_cheaked) && $(n).find(".sel_req").val();
						var input_text=$(radio_cheaked) && $(n).find(".operation").text();
						//选择的具体操作账户为空
						if(select_value==null || select_value=="" || typeof select_value == "undefined"){
							layer.msg(fy.getMsg('选择的操作账户')+(i+1)+fy.getMsg('为空'));
							lock=false;
							return false;
						}
						//选择的操作账户的操作值为空
						if(input_value==null || input_value=="" || typeof input_value == "undefined"){
							layer.msg(fy.getMsg('选择的操作账户')+(i+1)+fy.getMsg('的操作值为空'));
							lock=false;
							return false;
						}
					}
					
				});
				if(!lock){
					return false;
				}
				
				var accountHtml="";//渲染模板
				for(var i=0;i<result.length;i++){
					var radio_cheaked=result[i].radio_cheaked;
					var n=result[i].n;
					var index=result[i].index;
					
					var account_index=index+1;
					var select_text=$(radio_cheaked).parent().parent().find("select option:selected").text();
					var input_value=$(n).find(".sel_req").val();
					var input_text=$(n).find(".operation").text();
					
					var handle_account=$("#handle_account").html();
					var handle_account_data={"account_index":account_index,"select_text":select_text,
							"input_value":input_value,"input_text":input_text};
					accountHtml+=render(handle_account,handle_account_data);//渲染模板
				}
				
				layer.open({
					type: 1,
					title:fy.getMsg('操作内容'),
					shadeClose: true,
					area: ['550px', '260px'],
					shadeClose: true, //点击遮罩关闭
					content: accountHtml,
					btn:[fy.getMsg('确定'),fy.getMsg('取消')],
					yes:function(index,layero){
						form.submit();
						layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
						
					}
				});
			}
		});
	}
	
	/**
	 * 选择操作账户
	 */
	$(".account-type").change(function(){
		var value=$(this).val();//账户id
		var oldVal=$(this).parent().find(".oldAccount").val();//账户类型1.平台账户、2.会员账户
		var newVal=oldVal+","+value;
		$(this).parent().find("input[type='radio']").val(newVal);
	});
	
	/**
	 * 选择账户操作类型
	 */
	$(".handle-item li").click(function(){
		var lable=$(this).find("a").text();
		var value=$(this).find("a").attr("cvalue");
		$(this).parent().parent().find(".operation").text(lable);
		$(this).parent().parent().find("input[optype='operation']").val(value);
	});
	
	/**
	 * 渲染模板
	 * */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
});