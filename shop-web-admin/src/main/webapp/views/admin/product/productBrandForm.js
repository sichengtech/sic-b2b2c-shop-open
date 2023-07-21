$(function(){
	
    /**
     * 安全图片的密钥
     */
    var accessKey=$("input[name='accessKey']").val();
   
	/**
     * 初始化MyUpload(品牌LOGO)控件
     */
    var img_path1=$("input[name='logo']").val();//图片地址
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var upload1 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxa+"/sys/sysToken/getToken.do",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel1',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path1)){
        		$("input[name='logo']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(品牌LOGO)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 初始化MyUpload控件
     */
    var img_path2=$("input[name='applyPathP1']").val();//图片地址
    var upload2 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxa+"/sys/sysToken/getToken.do",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel2',
    	buttonStyle: 1,
    	isSafe: true,
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path2)){
        		$("input[name='applyPathP1']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片
     */
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
    
    /**
     * 初始化MyUpload控件
     */
    var img_path3=$("input[name='applyPathP2']").val();//图片地址
    var upload3 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxa+"/sys/sysToken/getToken.do",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel3',
    	buttonStyle: 1,
    	isSafe: true,
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path3)){
        		$("input[name='applyPathP2']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片
     */
    if(img_path3 != "" && img_path3 != null && typeof img_path3 != "undefined"){
    	upload3.init([ctxfs+img_path3]);
    }
	
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"firstLetter":{maxlength:64,},
				"logo":{maxlength:128,},
				"displayMode":{maxlength:1,},
				"recommend":{maxlength:1,},
				"type":{maxlength:1,},
				"sort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"status":{maxlength:1,},
				"url":{maxlength:255,},
				"introduction":{},
				"cause":{maxlength:512,},
				"applyPathP1":{maxlength:64,},
				"applyPathP2":{maxlength:64,},
				"brandOwner":{maxlength:64,},
				"sellerId":{maxlength:19,},
			},
			messages: {
				"name":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"firstLetter":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"logo":{maxlength:fy.getMsg("最大长度不能超过128字符"),},
				"displayMode":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"recommend":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"type":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"sort":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过10字符"),regex:fy.getMsg("排序要输入数字"),},
				"status":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"url":{maxlength:fy.getMsg("最大长度不能超过255字符"),},
				"introduction":{},
				"cause":{maxlength:fy.getMsg("最大长度不能超过512字符"),},
				"applyPathP1":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"applyPathP2":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"brandOwner":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"sellerId":{maxlength:fy.getMsg("最大长度不能超过19字符"),},
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
				
				//品牌Logo
				if(upload1.datas.length!=0){
					$("input[name='logo']").val(upload1.datas[0].path);
				}
				var imgValue=$("input[name='logo']").val();
				if(imgValue==null || imgValue==''){
					layer.msg(fy.getMsg('请上传品牌')+'logo');
					return false;
				}
				//商标注册证(图1) 
				if(upload2.datas.length!=0){
					$("input[name='applyPathP1']").val(upload2.datas[0].path);
				}
				//商标注册证(图2) 
				if(upload3.datas.length!=0){
					$("input[name='applyPathP2']").val(upload3.datas[0].path);
				}
				
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});