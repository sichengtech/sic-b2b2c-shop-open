$(function(){
	
	/**
	 * 初始化MyUpload控件
	 */
	window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
	var img_path=$("input[name='headPicPath']").val();//图片地址
	var upload = new MyUpload({
		// 获取token的路径
		tokenPath: ctxs+"/sys/sysToken/getToken.htm",
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
        		$("input[name='headPicPath']").val("");
        	}
        }
	});

	/**
	 * 回显页面图片
	 */
	var img_path=$("input[name='headPicPath']").val();
	if(img_path != "" && img_path != null && typeof img_path != "undefined"){
		upload.init([ctxfs+img_path]);
	}
	
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"loginName":{regex:/^[1-9]\d*$/,maxlength:64,required:true,},
				"realName":{maxlength:64,},
				"birthday":{regex:/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/,},
				"countryId":{maxlength:19,},
				"countryName":{maxlength:64,},
				"provinceId":{maxlength:19,},
				"provinceName":{maxlength:64,},
				"cityId":{maxlength:19,},
				"cityName":{maxlength:64,},
				"districtId":{maxlength:19,},
				"districtName":{maxlength:64,},
				"detailedAddress":{maxlength:255,},
				"qq":{maxlength:64,},
				"microblog":{maxlength:64,},
				"weChat":{maxlength:64,},
			},
			messages: {
				"loginName":{regex:fy.getMsg('请输入正确用户名'),maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),required:fy.getMsg('用户名不能为空')},
				"realName":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"birthday":{regex:fy.getMsg('请输入正确格式'),},
				"countryId":{maxlength:fy.getMsg('最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"countryName":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"provinceId":{maxlength:fy.getMsg('最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"provinceName":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"cityId":{maxlength:fy.getMsg('最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"cityName":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"districtId":{maxlength:fy.getMsg('最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"districtName":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"detailedAddress":{maxlength:fy.getMsg('最大长度不能超过') + '255' + fy.getMsg('字符'),},
				"qq":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"microblog":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"weChat":{maxlength:fy.getMsg('最大长度不能超过') + '64' + fy.getMsg('字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
				var loginName =$("#loginName").val();
				if(disableUsername!=""){
					var disableName = disableUsername.split(",");
					for (i=0;i<disableName.length;i++){
						if(disableName[i]==loginName){
							layer.msg(fy.getMsg('用户名不能为')+disableName[i]);
							return false;
						}
					} 
				}
				//把地区名字赋值到隐藏域中
				$("input[name='provinceName']").val($("#provinceName").text());
				$("input[name='cityName']").val($("#cityName").text());
				$("input[name='districtName']").val($("#districtName").text());
				
				//图片
				if(upload.datas.length!=0){
					$("input[name='headPicPath']").val(upload.datas[0].path);
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});