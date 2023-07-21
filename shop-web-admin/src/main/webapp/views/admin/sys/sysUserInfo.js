$(document).ready(function(){
	
	/**
	 * 初始化MyUpload控件
	 */
	window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
	var img_path=$("input[name='photo']").val();//图片地址
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
        		$("input[name='photo']").val("");
        	}
        }
	});

	/**
	 * 回显页面图片
	 */
	if(img_path != "" && img_path != null && typeof img_path != "undefined"){
		upload.init([ctxfs+img_path]);
	}
	
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				qq:{regex:/^[1-9][0-9]{4,}$/,maxlength:64,},
				email:{regex:/^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/,maxlength:64,},
				phone:{regex:/^0\d{2,3}-?\d{7,8}$/,maxlength:64,},
				mobile:{regex:/^1\d{10}$/,maxlength:64,},
				remarks:{maxlength:255,},
			},
			messages: {
				qq:{regex:fy.getMsg("请输入4位以上的数字"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				email:{regex:fy.getMsg("请输入正确格式邮箱"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				phone:{regex:fy.getMsg("请输入正确格式电话"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				mobile:{regex:fy.getMsg("请输入正确格式手机"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				remarks:{maxlength:fy.getMsg("最大长度不能超过255字符"),},
			},
			submitHandler: function(form){
				//获取上传图片路径
				if(upload.datas.length!=0){
					$("input[name='photo']").val(upload.datas[0].path);
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
 });