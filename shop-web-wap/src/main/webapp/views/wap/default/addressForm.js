$(function(){
	var addressId=$("#addressId").val();//收货地址id
	if(addressId!=null && addressId!=""){
		/**
		 * 获取用户的收货地址信息
		 */
		$(".title").html("编辑收货地址");
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/userAddress/one.htm?addressId='+addressId,
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
            		layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
            	}
				var address=data.data;
				$("#name").val(address.name);
				$("#mobile").val(address.mobile);
				$("#areaId").val(address.provinceName+" "+address.cityName+" "+address.districtName);
				$("#provinceId").val(address.provinceId);
				$("#provinceName").val(address.provinceName);
				$("#cityId").val(address.cityId);
				$("#cityName").val(address.cityName);
				$("#districtId").val(address.districtId);
				$("#districtName").val(address.districtName);
				$("#detailedAddress").val(address.detailedAddress);
				if("1"==address.isDefault){
					$("#isDefault").attr("checked","checked")
				}
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	}
	
	/**
	 * 提交表单
	 * */
	$("body").off('click','#formSubmitBtn').delegate("#formSubmitBtn","click",function(){
		var url=ctxw+'/api/v1/user/userAddress/save.htm';
		if(addressId!=null && addressId!=""){
			url=ctxw+'/api/v1/user/userAddress/edit.htm';
		}
		var $form = $("#form");
		$form.form();
		$form.validate(function(error){
			if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:url,
	                data: $("#form").serialize(),
	                dataType: 'json',
	                success: function(data){
	                	if(data.status=="401"){
		            		wx_common.routerLogin();
	                		return false;
		            	}
	                	if(data.status!="200"){
	    					layer.open({content: data.message,skin: 'msg',time: 2});
	    					return false;
	    				}
	    				layer.open({content: data.message,skin: 'msg',time: 2});
	    				$.router.back();
	                },
	                error: function(){
	                    layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	                }
	            });
			}
		});
	});
	
	/**
	 * 选择地区
	 * */
	$("body").delegate("#city","click",function(){
		$("#city").cityPicker({
		    title: "选择地区"
		});
		$("#city").click();
	});
});
