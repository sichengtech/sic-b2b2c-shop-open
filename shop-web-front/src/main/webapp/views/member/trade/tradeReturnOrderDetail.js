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

    //提交表单
	$(".submitBtn").click(function(){
		if($(this).attr("isNew") == '1'){
			$("#inputForm").submit();
		}else if($(this).attr("isNew") == '0'){
			$("#inputForm").attr("action",ctxm+"/trade/tradeReturnOrder/deliverGoods.htm");
			$("input[name='logisticsTemplateName']").val($("span[name='logisticsTemplateName']").text());
			$("#inputForm").submit();
		}
	});

	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		var maxReturnMoney=parseFloat($("span[name='maxReturnMoney']").text());
		var oldReturnCount=$("input[name='oldReturnCount']").val();
		$("#inputForm").validate({
			rules: {
				"returnReason":{required: true},	
				"returnMoney":{required: true,regex:/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,max:maxReturnMoney},
				"returnCount":{required: true,regex:/^[1-9]\d*$/,range:[1,oldReturnCount]},
				"returnExplain":{required: true,},
			},
			messages: {
				"returnReason":{required: fy.getMsg('请选择退款原因')},
				"returnMoney":{required: fy.getMsg('请输入退款金额'),regex:fy.getMsg('请输入正整数或两位以内的小数'),max:fy.getMsg('退款金额不能超过')+maxReturnMoney+fy.getMsg('元')},
				"returnCount":{required: fy.getMsg('请输入退款数量'),regex:fy.getMsg('请输入正确的数字'),range:fy.getMsg('数量最小为1，最大不能超过原有数量')+oldReturnCount},
				"returnExplain":{required: fy.getMsg('请选择输入退款说明')},
			},
			submitHandler: function(form){
				if(myupload_id.length){
					var datas=upload.datas;
					for(var i=0;i<datas.length;i++){
						$("input[name='img"+(i+1)+"']").val(datas[i].path);
					}
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
 });