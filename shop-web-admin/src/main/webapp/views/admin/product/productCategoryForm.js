$(document).ready(function(){
	
	/**
	 * 初始化MyUpload控件
	 */
	window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
	var img_path=$("input[name='bak1']").val();//图片地址
	var upload = new MyUpload({
		// 获取token的路径
		tokenPath: ctxa+"/sys/sysToken/getToken.do",
		// 文件上传到的服务器
		server: ctxu+'/upload/webUploadServer.htm',
		// 容器Id
		container: '#vessel',
		buttonStyle: 1,
		//accept: 'file',
		fileSingleSizeLimit: 1024 * 1024 * 5,
		fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path)){
        		$("input[name='bak1']").val("");
        	}
        }
	});

	/**
	 * 回显页面图片
	 */
	if(img_path != "" && img_path != null && typeof img_path != "undefined"){
		upload.init([ctxfs+img_path]);
	}
	
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"commission":{regex:/^(\d|[1-9]\d|100)(\.\d{1,3})?$/,maxlength:12,max:100},
				"sort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
			},
			messages: {
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"commission":{regex:"请输入0-100的数(最多小数点后三位)",maxlength:"最大长度不能超过12字符",max:"最大不能超过100"},
				"sort":{required: "必填项",maxlength:"最大长度不能超过10字符",regex:"排序要输入数字",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-group")){
					error.appendTo(element.parent().parent());
				} else {
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
				//获取上传图片路径
				if(upload.datas.length!=0){
					$("input[name='bak1']").val(upload.datas[0].path);
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
 });