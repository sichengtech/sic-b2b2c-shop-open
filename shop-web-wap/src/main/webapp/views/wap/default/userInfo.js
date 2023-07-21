var registerInfo=null;
$(function(){
	/**
	 * 获取性别的字典数据
	 */
	function dictList(){
		var dictListArray = [];
		$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/sys/dict/list.htm?type=sex',
			dataType: 'json',
			async:false,
			success: function(data){
				if(data==null || data.status==""){
					layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
					return;
				}
	        	var status = data.status;//状态码
				if(status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 2 });
					return false;
				}
				var dictList = data.data;
				for (var i = 0; i < dictList.length; i++) {
					var map = {}; 
					map['title'] = dictList[i].label;
					map['value'] = dictList[i].value;
					dictListArray.push(map);
				}
			},
			error: function(data){
				layer.open({content: "获取性别报错",skin: 'msg',time: 2 });
			}
		});
		return dictListArray;
	}
	
	/**
	 * 用户信息
	 */
	(function(){
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/one.htm',
            dataType: 'json',
            success: function(data){
            	var userMain=data.data;
            	$("#loginName").val(userMain.loginName);
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
		$.ajax({
	    	type: "get",
	        url:ctxw+'/api/v1/user/member.htm',
	        dataType: 'json',
	        success: function(data){
	        	if(data.status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
				}
				$("#headPicPath").val(data.data.headPicPath);
				$("#headPicture").attr("src",ctxfs+data.data.headPicPath+"@100x100");
				$("#birthday").attr("value",data.data.birthday);
				$("#sex").val(data.data.sex);
				$("#sexName").val(wx_common.getDictLabel("sex",data.data.sex));
				if(data.data.provinceName!=null && data.data.provinceName!="" && typeof(data.data.provinceName)!="undefined"){
					$("#areaId").val(data.data.provinceName+" "+data.data.cityName+" "+data.data.districtName);
					$("#provinceId").val(data.data.provinceId);
					$("#provinceName").val(data.data.provinceName);
					$("#cityId").val(data.data.cityId);
					$("#cityName").val(data.data.cityName);
					$("#districtId").val(data.data.districtId);
					$("#districtName").val(data.data.districtName);
				}
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
	})();
	
	/**
	 * 出生日期
	 */
	$("#birthday").datetimePicker({
		title:"选择日期",
		m:1,
		onClick: function(d) {$("#birthday").val(d.values)}
	});
	
	/**
	 * 性别
	 */
	$("#sexName").select({
		title: "选择性别",
	    items: dictList(),
	    onChange: function(d) {$("#sex").val(d.values)}
	});
	
	/**
	 * 获取注册设置信息
	 */
	var siteRegisterInfo=function(){
		if(registerInfo!=null){
			return registerInfo;
		}
		$.ajax({
	    	type: 'get',
	        url:ctxw+'/api/v1/siteRegister/info.htm',
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data.status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 5});
					return false;
				}
				registerInfo=data.data;
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
		return registerInfo;
	}
	
	/**
	 * 提交表单
	 */
	var $form = $("#form");
	$form.form();
	$("#formSubmitBtn").on("click", function(){
		$form.validate(function(error){
			var loginName=$("#loginName").val();
			var regex=/^[A-Za-z0-9]+$/;//字母或数字的正则表达式
			var registerInfo=siteRegisterInfo();//注册设置信息
			//判断用户名是否为空
			if(loginName==null || loginName==""){
				layer.open({content: "请输入用户名",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名是否是字母或数字
			if(!regex.test(loginName)){
				layer.open({content: "用户名请输入字母或数字",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最大长度
			if(registerInfo==null && loginName.length>64){
				layer.open({content: "用户名不能超过64字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最大长度
			if(registerInfo!=null && loginName.length>registerInfo.usernameMax){
				layer.open({content: "用户名不能超过"+registerInfo.usernameMax+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最小长度
			if(registerInfo!=null && loginName.length<registerInfo.usernameMin){
				layer.open({content: "用户名不能小于"+registerInfo.usernameMin+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名是否有违禁
			if(registerInfo!=null && registerInfo.disableUsername!=null && registerInfo.disableUsername!=""){
				var disableName = registerInfo.disableUsername.split(",");
				for (i=0;i<disableName.length;i++){
					if(disableName[i]==loginName){
						layer.open({content: "用户名不能为"+disableName[i],skin: 'msg',time: 2});
						return false;
					}
				} 
			}
			if(!error){
				//下载图片
				var uploadImgId=$(".serverIds").val();
				if(uploadImgId!=null && uploadImgId!=""){
					var flag=weixin.downloadImg(uploadImgId,$("#uploadHeadPic"));
					if(!flag){
						layer.open({content:data.message==""?'下载图片发生错误':data.message,skin: 'msg',time: 2});
						return false;
					}
					$("#headPicPath").val($("#uploadHeadPic").val());
				}
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/userInfo/edit.htm',
	                data: $("#form").serialize(),
	                dataType: 'json',
	                async: false,
	                success: function(data){
	                	if(data.status=="401"){
		            		wx_common.routerLogin();
	                		return false;
		            	}
	                	if(data.status!="200"){
	                		layer.open({content: data.message,skin: 'msg',time: 5});
	    					return false;
	                	}
    					location.href = ctxw+data.data;
    					layer.open({content: data.message,skin: 'msg',time: 2});
    					return false;
	                },
	                error: function(){
	                    layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	                }
	            });
			}
		});
	});
	
    /**
     * 微信上传图片
     */
	//图片的点击事件（调用微信的选择图片和上传图片的接口）
	//通过ready接口处理成功验证，config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后 
	wx.ready(function(){
		//得到上传图片按钮 21 
		$("body").delegate("#headPicture",'click',function(e){
			var images = {localId:[],serverId:[]};
			//最多可上传图片图片数
			var count=1;
			//调用 拍照或从手机相册中选图接口 24
			wx.chooseImage({
				count: count,//最多可上传5张
				success: function(res) {
					var imgUrl=res.localIds[0];
					$("#headPicture").attr("src",imgUrl);
					//调用上传图片接口
					images.localIds= res.localIds;
					var upload = function() {
						wx.uploadImage({
							localId:images.localIds[0],
							success: function(res) {
								$(".serverIds").val(res.serverId);
							}
						});
					};
                    upload();
				 }
			});
		});
	});
});