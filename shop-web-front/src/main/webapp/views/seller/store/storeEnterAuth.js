$(function(){
	
    /**
     * 安全图片的密钥
     */
    var accessKey=$("input[name='accessKey']").val();
   
	/**
     * 初始化MyUpload(身份证正面)控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var img_path1=$("input[name='legalIdCardCodePositive']").val();
    var upload1 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel1',
        buttonStyle: 1,
    	isSafe: true,
    	accessKey: accessKey,
    	formData: {
    		isSafe: true
    	},
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path1)){
        		$("input[name='legalIdCardCodePositive']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(身份证正面)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 初始化MyUpload(身份证反面)控件
     */
    var img_path2=$("input[name='legalIdCardCodeOpposite']").val();
    var upload2 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel2',
    	buttonStyle: 1,
    	isSafe: true,
    	formData: {
    		isSafe: true
    	},
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path2)){
        		$("input[name='legalIdCardCodeOpposite']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(身份证反面)
     */
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
    
    /**
     * 初始化MyUpload(普通营业执照电子版)控件
     */
    var img_path3=$("input[name='sellerLicensePath']").val();
    var upload3 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel3',
    	buttonStyle: 1,
    	isSafe: true,
    	formData: {
    		isSafe: true
    	},
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path3)){
        		$("input[name='sellerLicensePath']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(普通营业执照电子版)
     */
    if(img_path3 != "" && img_path3 != null && typeof img_path3 != "undefined"){
    	upload3.init([ctxfs+img_path3]);
    }
    
    /**
     * 初始化MyUpload(组织机构代码电子版)控件
     */
    var img_path4=$("input[name='organizationCodePath']").val();
    var upload4 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel4',
    	buttonStyle: 1,
    	isSafe: true,
    	formData: {
    		isSafe: true
    	},
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path4)){
        		$("input[name='organizationCodePath']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(组织机构代码电子版)
     */
    if(img_path4 != "" && img_path4 != null && typeof img_path4 != "undefined"){
    	upload4.init([ctxfs+img_path4]);
    }
    
    /**
     * 初始化MyUpload(税务登记电子版)控件
     */
    var img_path5=$("input[name='taxRegistrationNumberPath']").val();
    var upload5 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel5',
    	buttonStyle: 1,
    	isSafe: true,
    	accessKey: accessKey,
    	formData: {
    		isSafe: true
    	},
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path5)){
        		$("input[name='taxRegistrationNumberPath']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(税务登记电子版)
     */
    if(img_path5 != "" && img_path5 != null && typeof img_path5 != "undefined"){
    	upload5.init([ctxfs+img_path5]);
    }
    
    /**
     * 初始化MyUpload(多证合一营业执照电子版)控件
     */
    var img_path6=$("input[name='socialCreditCodePath']").val();
    var upload6 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel6',
    	buttonStyle: 1,
    	isSafe: true,
    	accessKey: accessKey,
    	formData: {
    		isSafe: true
    	},
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path6)){
        		$("input[name='socialCreditCodePath']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(多证合一营业执照电子版)
     */
    if(img_path6 != "" && img_path6 != null && typeof img_path6 != "undefined"){
    	upload6.init([ctxfs+img_path6]);
    }
    
    /**
     * 初始化MyUpload(开户许可证核准电子版)控件
     */
    var img_path7=$("input[name='openAnAccountLicensePath']").val();
    var upload7 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel7',
    	buttonStyle: 1,
    	isSafe: true,
    	formData: {
    		isSafe: true
    	},
    	accessKey: accessKey,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path7)){
        		$("input[name='openAnAccountLicensePath']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(开户许可证核准电子版)
     */
    if(img_path7 != "" && img_path7 != null && typeof img_path7 != "undefined"){
    	upload7.init([ctxfs+img_path7]);
    }
    
    /**
     * 初始化MyUpload控件
     */
    var input_list=$("input[name='sBrand']");
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
	        buttonStyle: 2,
	    	isSafe: true,
	    	formData: {
	    		isSafe: true
	    	},
	    	accessKey: accessKey,
	        //accept: 'file',
	        fileSingleSizeLimit: 1024 * 1024 * 5,
	        fileNumLimit: 10,
	        uploadDelete:function(a){
	        	for(var i=0;i<input_list.length;i++){
	        		if(a==(ctxfs+input_list.eq(i).val())){
		        		input_list.eq(i).val("");
		        	}
	        	}
	        	
	        }
	        
	    });
    }
    //品牌商标注册证 回显
    var myupload_paths=[];
    for(var i=0;i<input_list.length;i++){
    	if(input_list.eq(i).val()){
    		myupload_paths.push(ctxfs+input_list.eq(i).val());
    	}
    }
    if(myupload_paths.length){
    	upload.init(myupload_paths);
    }
    
	/**
	 * 证照类型的切换(多证合一营业执照)
	 */
	$(".businessType1").click(function(){
		$(".bType1").css("display","none");
		$(".bType2").css("display","block");
	});
	
	/**
	 * 证照类型的切换(普通营业执照)
	 */
	$(".businessType2").click(function(){
		$(".bType1").css("display","block");
		$(".bType2").css("display","none");
	});
	
	/**
	 *	企业资质与店铺信息表单验证
	 */
	if(jsValidate){
		var oldStoreName = $("#oldStoreName").val();
		$("#inputForm").validate({
			rules: {
				"companyName":{required: true,maxlength:64,},
				"detailedAddress":{required: true,maxlength:255,},
				"registeredCapital":{regex:/^[1-9]\d*$/,required: true,maxlength:10,},
				"contact":{required: true,maxlength:64,},
				"contactNumber":{regex:/^1\d{10}$/,required: true,maxlength:64,},
				"legalName":{required: true,maxlength:64,},
				"legalIdCardCode":{regex:/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/,required: true,maxlength:64,},
				"sellerLicense":{required: true,minlength:6,maxlength:25,},
				"organizationCode":{required: true,minlength:6,maxlength:25,},
				"taxRegistrationNumber":{required: true,minlength:6,maxlength:25,},
				"socialCreditCode":{required: true,minlength:6,maxlength:25,},
				"openAnAccountLicense":{required: true,minlength:6,maxlength:25,},
				"storeName":{remote: ctxs+"/store/storeEnterAuth/validateStoreName.htm?oldStoreName=" + encodeURIComponent(oldStoreName),required: true,maxlength:18,},
				"storeBrand":{required: true,maxlength:64,},
			},
			messages: {
				"companyName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"detailedAddress":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"registeredCapital":{regex: fy.getMsg('请输入正确注册资金'),required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过10字符'),},
				"contact":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"contactNumber":{regex: fy.getMsg('请输入正确手机号'),required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"legalName":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"legalIdCardCode":{regex: fy.getMsg('请输入正确身份证号'),required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"sellerLicense":{required: fy.getMsg('必填項'),minlength:fy.getMsg('最小长度不能少于6字符'),maxlength:fy.getMsg('最大长度不能超过25字符'),},
				"organizationCode":{required: fy.getMsg('必填項'),minlength:fy.getMsg('最小长度不能少于6字符'),maxlength:fy.getMsg('最大长度不能超过25字符'),},
				"taxRegistrationNumber":{required: fy.getMsg('必填項'),minlength:fy.getMsg('最小长度不能少于6字符'),maxlength:fy.getMsg('最大长度不能超过25字符'),},
				"socialCreditCode":{required: fy.getMsg('必填項'),minlength:fy.getMsg('最小长度不能少于6字符'),maxlength:fy.getMsg('最大长度不能超过25字符'),},
				"openAnAccountLicense":{required: fy.getMsg('必填項'),minlength:fy.getMsg('最小长度不能少于6字符'),maxlength:fy.getMsg('最大长度不能超过25字符'),},
				"storeName":{remote: fy.getMsg('店铺名称已存在'),required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过18字符'),},
				"storeBrand":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示字符置
				if (element.is(":checkbox")||element.is(":radio")|| element.is(".atr") || element.parent().is(".input-append")){
					error.appendTo(element.parent());
				}else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//企业所在地
				var provinceId =$("#provinceId").val();
				var cityId = $("#cityId").val();
				var districtId = $("#districtId").val();
				if(provinceId==null || provinceId==''){
					layer.msg(fy.getMsg('请选择企业所在地省'));
					return false;
				}
				if(cityId==null || cityId==''){
					layer.msg(fy.getMsg('请选择企业所在地市'));
					return false;
				}
				if(districtId==null || districtId==''){
					layer.msg(fy.getMsg('请选择企业所在地区/县'));
					return false;
				}
				//把地区名字赋值到隐藏域中
				$("input[name='provinceName']").val($("#provinceName").text());
				$("input[name='cityName']").val($("#cityName").text());
				$("input[name='districtName']").val($("#districtName").text());
				//身份证正面
				if(upload1.datas.length!=0){
					$("input[name='legalIdCardCodePositive']").val(upload1.datas[0].path);//身份证正面
				}
				var imgValue1=$("input[name='legalIdCardCodePositive']").val();
				if(imgValue1==null || imgValue1==""){
					layer.msg(fy.getMsg('请上传身份证正面'));
					return false;
				}
				//身份证反面
				if(upload2.datas.length!=0){
					$("input[name='legalIdCardCodeOpposite']").val(upload2.datas[0].path);//身份证反面
				}
				var imgValue2=$("input[name='legalIdCardCodeOpposite']").val();
				if(imgValue2==null || imgValue2==""){
					layer.msg(fy.getMsg('请上传身份证反面'));
					return false;
				}
				//开户许可证
				if(upload7.datas.length!=0){
					$("input[name='openAnAccountLicensePath']").val(upload7.datas[0].path);//开户许可证核准电子版
				}
				var imgValue3=$("input[name='openAnAccountLicensePath']").val();
				if(imgValue3==null || imgValue3==""){
					layer.msg(fy.getMsg('请上传开户许可证核准电子版'));
					return false;
				}
				if(imgValue3.length>64){
					layer.msg(fy.getMsg('开户许可证核准电子版不能超过64字符'));
					return false;
				}
				//证照类型
				var businessType1 = $(".businessType1").parent().hasClass('checked');
				var businessType2 = $(".businessType2").parent().hasClass('checked');
				if(businessType1){
					//多证合一营业执照
					if(upload6.datas.length!=0){
						$("input[name='socialCreditCodePath']").val(upload6.datas[0].path);//多证合一营业执照
					}
					var imgValue4=$("input[name='socialCreditCodePath']").val();
					if(imgValue4==null || imgValue4==""){
						layer.msg(fy.getMsg('请上传多证合一营业执照电子版'));
						return false;
					}
					if(imgValue4.length>64){
						layer.msg(fy.getMsg('上传多证合一营业执照电子版不能超过64字符'));
						return false;
					}
				}
				if(businessType2){
					//普通营业执照
					var img_sellerLicensePath = $("#img_sellerLicensePath_0").find(".imgPath").val();
					var img_organizationCodePath = $("#img_organizationCodePath_0").find(".imgPath").val();
					var img_taxRegistrationNumberPath = $("#img_taxRegistrationNumberPath_0").find(".imgPath").val();
					//普通营业执照电子版
					if(upload3.datas.length!=0){
						$("input[name='sellerLicensePath']").val(upload3.datas[0].path);//普通营业执照电子版
					}
					var imgValue5=$("input[name='sellerLicensePath']").val();
					if(imgValue5==null || imgValue5==""){
						layer.msg(fy.getMsg('请上传营业执照电子版'));
						return false;
					}
					if(imgValue5.length>64){
						layer.msg(fy.getMsg('上传营业执照电子版不能超过64字符'));
						return false;
					}
					
					//组织机构代码电子版
					if(upload4.datas.length!=0){
						$("input[name='organizationCodePath']").val(upload4.datas[0].path);//组织机构代码电子版
					}
					var imgValue6=$("input[name='organizationCodePath']").val();
					if(imgValue6==null || imgValue6==""){
						layer.msg(fy.getMsg('请上传组织机构代码电子版'));
						return false;
					}
					if(imgValue6.length>64){
						layer.msg(fy.getMsg('上传组织机构代码电子版不能超过64字符'));
						return false;
					}
					
					//税务登记电子版
					if(upload5.datas.length!=0){
						$("input[name='taxRegistrationNumberPath']").val(upload5.datas[0].path);//税务登记电子版
					}
					
					var imgValue7=$("input[name='taxRegistrationNumberPath']").val();
					if(imgValue7==null || imgValue7==""){
						layer.msg(fy.getMsg('请上传税务登记电子版'));
						return false;
					}
					if(imgValue7.length>64){
						layer.msg(fy.getMsg('上传税务登记电子版不能超过64字符'));
						return false;
					}
				}
				if(!businessType1 && !businessType2){
					layer.msg(fy.getMsg('请选择证照类型'));
					return false;
				}
				//品牌商标注册证
				if(upload.datas.length!=0){
					var datas=upload.datas;
					for(var i=0;i<datas.length;i++){
						$("input[data_index='img"+i+"']").val(datas[i].path);
					}
				}
			    var imgVlues=$("input[name='sBrand']");
			    var k=0;
			    for(var i=0;i<imgVlues.length;i++){
			    	if(imgVlues.eq(i).val()){
			    		k=k+1;
			    	}
			    }
			    if(k==0){
			    	layer.msg(fy.getMsg('请上传店铺品牌商标注册证'));
			    	return false;
			    }
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	/**
	 * 入驻申请审核意见页面点击事件的跳转(二审审核失败，重新提交)
	 */
	$(".btn-xxxxxlargeAuth60").click(function(){
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		window.location = ctxs + "/store/storeEnterAuth/auth4.htm";
	});
	
	/**
	 * 入驻申请审核意见页面点击事件的跳转(二审审核成功)
	 */
	$(".btn-xxxxxlargeAuth50").click(function(){
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		window.location = ctxsso + "/logout.htm";
	});
	
	//入驻信息还原
	$(".reduction").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg(fy.getMsg('确定要还原么？')),href); 
	});
	
});