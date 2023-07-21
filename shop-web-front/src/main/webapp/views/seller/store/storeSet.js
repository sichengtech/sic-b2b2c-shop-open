$(function(){
	
    /**
     * 初始化MyUpload(店铺Logo)控件
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
     * 回显页面图片(店铺Logo)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 初始化MyUpload(店铺横幅)控件
     */
    var img_path2=$("input[name='banner']").val();//图片地址
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
        		$("input[name='banner']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(店铺横幅)
     */
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
	
	if(jsValidate){
		var oldStoreName = $("#oldStoreName").val();
		$("#inputForm").validate({
			rules: {
				"storeId":{required: true,maxlength:19,},
				"logo":{required: true,maxlength:64,},
				"banner":{required: true,maxlength:64,},
				"name":{remote: ctxs+"/store/store/validateStoreName.htm?oldStoreName=" + encodeURIComponent(oldStoreName),required: true,maxlength:18,},
				"productCount":{required: true,maxlength:10,},
				"isOpen":{required: true,maxlength:1,},
				"settlementPeriod":{required: true,maxlength:2,},
				"countryId":{required: true,maxlength:19,},
				"countryName":{required: true,maxlength:64,},
				"provinceId":{required: true,maxlength:19,},
				"provinceName":{required: true,maxlength:64,},
				"cityId":{required: true,maxlength:19,},
				"cityName":{required: true,maxlength:64,},
				"districtId":{required: true,maxlength:19,},
				"districtName":{required: true,maxlength:64,},
				"detailedAddress":{required: true,maxlength:255,},
				"storeTel":{required: true,maxlength:64,},
				"storeQq":{required: true,maxlength:64,},
				"storeWechat":{required: true,maxlength:64,},
				"seoTitle":{required: true,maxlength:255,},
				"seoKeyword":{required: true,maxlength:255,},
				"seoDescribe":{required: true,maxlength:255,},
				"industry":{required: true,maxlength:255,},
			},
			messages: {
				"storeId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"logo":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"banner":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"name":{remote: fy.getMsg('店铺名称已存在'),required: fy.getMsg('必填項'),maxlength:"最大长度不能超过18字符",},
				"productCount":{required: fy.getMsg('必填項'),maxlength:"最大长度不能超过10字符",},
				"isOpen":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过1字符'),},
				"settlementPeriod":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过2字符'),},
				"countryId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"countryName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"provinceId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"provinceName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"cityId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"cityName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"districtId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"districtName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"detailedAddress":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"storeTel":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"storeQq":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"storeWechat":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"seoTitle":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"seoKeyword":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"seoDescribe":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"industry":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
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
				
				//店铺LOGO
				if(upload1.datas.length!=0){
					$("input[name='logo']").val(upload1.datas[0].path);//店铺LOGO
				}
				//店铺横幅
				if(upload2.datas.length!=0){
					$("input[name='banner']").val(upload2.datas[0].path);//店铺横幅
				}
				
				//获取地区
				var provinceId =$("#provinceId").val();
				var cityId = $("#cityId").val();
				var districtId = $("#districtId").val();
				if(provinceId==null || provinceId==''){
					layer.msg(fy.getMsg('请选择店铺所在地省'));
					return false;
				}
				if(cityId==null || cityId==''){
					layer.msg(fy.getMsg('请选择店铺所在地市'));
					return false;
				}
				if(districtId==null || districtId==''){
					layer.msg(fy.getMsg('请选择店铺所在地区/县'));
					return false;
				}
				//把地区名字赋值到隐藏域中
				$("input[name='provinceName']").val($("#provinceName").text());
				$("input[name='cityName']").val($("#cityName").text());
				$("input[name='districtName']").val($("#districtName").text());
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});