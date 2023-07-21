$(function(){
	
	/**
     * 初始化MyUpload(品牌Logo)控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var img_path1=$("input[name='logo']").val();//图片地址
    var upload1 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
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
     * 回显页面图片(品牌Logo)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 初始化MyUpload(商标注册证(图1))控件
     */
    var img_path2=$("input[name='applyPathP1']").val();
    var upload2 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel2',
        buttonStyle: 1,
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
     * 回显页面图片(商标注册证(图1))
     */
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
    
    /**
     * 初始化MyUpload(商标注册证(图2))控件
     */
    var img_path3=$("input[name='applyPathP2']").val();
    var upload3 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel3',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path3)){
        		$("input[name='applyPathP2" +
        				"']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(商标注册证(图2))
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
				"sort":{maxlength:10,},
				"status":{maxlength:1,},
				"url":{maxlength:255,},
				"introduction":{},
				"cause":{maxlength:512,},
				"applyPathP1":{maxlength:64,},
				"applyPathP2":{maxlength:64,},
				"brandOwner":{required: true,maxlength:64,},
				"sellerId":{maxlength:19,},
			},
			messages: {
				"name":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"firstLetter":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"logo":{maxlength:fy.getMsg('最大长度不能超过128字符'),},
				"displayMode":{maxlength:fy.getMsg('最大长度不能超过1字符'),},
				"recommend":{maxlength:fy.getMsg('最大长度不能超过1字符'),},
				"type":{maxlength:fy.getMsg('最大长度不能超过1字符'),},
				"sort":{maxlength:fy.getMsg('最大长度不能超过10字符'),},
				"status":{maxlength:fy.getMsg('最大长度不能超过1字符'),},
				"url":{maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"introduction":{},
				"cause":{maxlength:fy.getMsg('最大长度不能超过512字符'),},
				"applyPathP1":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"applyPathP2":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"brandOwner":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"sellerId":{maxlength:fy.getMsg('最大长度不能超过19字符'),},
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
			    	$("input[name='logo']").val(upload1.datas[0].path);//品牌LOGO
			    }
				var imgValue=$("input[name='logo']").val();
				if(imgValue==null || imgValue==""){
					layer.msg(fy.getMsg('请上传品牌logo'));
					return false;
				}
			    //商标注册证图1
			    if(upload2.datas.length!=0){
			    	$("input[name='applyPathP1']").val(upload2.datas[0].path);//商标注册证图1
			    }
				var imgValue2=$("input[name='applyPathP1']").val();
				if(imgValue2==null || imgValue2==""){
					layer.msg(fy.getMsg('请上传商标注册证(图1)'));
					return false;
				}
			    //商标注册证图2
			    if(upload3.datas.length!=0){
			    	$("input[name='applyPathP2']").val(upload3.datas[0].path);//商标注册证图2
			    }
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});