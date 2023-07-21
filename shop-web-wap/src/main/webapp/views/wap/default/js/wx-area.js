$(function(){
	var status="false";//是否已加载省数据(false:未加载、true:已加载)
	
	/**
	 * 关闭layer弹层
	 */
	$("body").delegate(".close","click",function(){
    	layer.closeAll();
    });
	
	/**
	 * 点击地区选择框,初始化省的数据
	 */
	$("body").delegate("#areaId","click",function(){
		//换取页面的地区数据
		var provinceId=$("#provinceId").val();//省id
		var provinceName=$("#provinceName").val();//省名字
		var cityId=$("#cityId").val();//市id
		var cityName=$("#cityName").val();//市名字
		var districtId=$("#districtId").val();//县id
		var districtName=$("#districtName").val();//县名字
		var areaData=$("#areaId").val();//省、市、县
		//回显地区数据
		if((areaData!=null && areaData!="" && typeof(areaData)!="undefined")){
			var p="0";
			var c="0";
			var d="0";
			//加载省数据
			$.ajax({
	        	type: "get",
	            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId=1',
	            dataType: 'json',
	            async: false,
	            success: function(data){
	            	if(data.status!="200"){
	            		layer.open({content: data.message,skin: 'msg',time: 2});
						return false;
	            	}
	            	var areaListHtml="";
	            	var province_tpl=$("#province_tpl").html();
					for(var i=0;i<data.data.length;i++){
						var area=data.data[i];
						var areaData={"provinceId":area.id,"provinceName":area.name};
						areaListHtml+=wx_common.render(province_tpl,areaData);//渲染模板
					}
					$(".provinceList ul").html(areaListHtml);
			    	p="1";
	            },
	            error: function(){
	                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	            }
	        });
			//加载当前省的市数据
	    	$.ajax({
	        	type: "get",
	            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId='+provinceId,
	            dataType: 'json',
	            async: false,
	            success: function(data){
	            	if(data.status!="200"){
	            		layer.open({content: data.message,skin: 'msg',time: 2});
						return false;
	            	}
	            	var areaListHtml="";
	            	var city_tpl=$("#city_tpl").html();
					for(var i=0;i<data.data.length;i++){
						var area=data.data[i];
						var areaData={"cityId":area.id,"cityName":area.name};
						areaListHtml+=wx_common.render(city_tpl,areaData);//渲染模板
					}
					$(".cityList ul").html(areaListHtml);
					c="1";
	            },
	            error: function(){
	                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	            }
	        });
	    	//加载当前市的县数据
	    	$.ajax({
	        	type: "get",
	            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId='+cityId,
	            dataType: 'json',
	            async: false,
	            success: function(data){
	            	if(data.status!="200"){
	            		layer.open({content: data.message,skin: 'msg',time: 2});
						return false;
	            	}
	            	var areaListHtml="";
	            	var district_tpl=$("#district_tpl").html();
					for(var i=0;i<data.data.length;i++){
						var area=data.data[i];
						var areaData={"districtId":area.id,"districtName":area.name};
						areaListHtml+=wx_common.render(district_tpl,areaData);//渲染模板
					}
					$(".districtList ul").html(areaListHtml);
					d="1";
	            },
	            error: function(){
	                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	            }
	        });
	    	//加载完省、市、县数据,使用layer层将渲染的数据弹出
	    	if(p=="1" && c=="1" && d=="1"){
	    		//弹出层顶部省数据和页面元素样式的处理
	    		$(".provinceData").attr("provinceId",provinceId);
				$(".provinceData").text(provinceName);
				$(".provinceData").css("color","#000");
				$(".provinceData").css("display","");
				$(".provinceData").parent().parent().css("float","left");
				//弹出层顶部市数据和页面元素样式的处理
				$(".cityData").attr("cityId",cityId);
				$(".cityData").text(cityName);
				$(".cityData").css("color","#000");
				$(".cityData").css("display","");
				$(".cityData").parent().parent().css("float","left");
				//弹出层顶部县数据和页面元素样式的处理
				$(".districtData").attr("districtId",districtId);
				$(".districtData").text(districtName);
				$(".districtData").css("color","red");
				$(".districtData").css("display","");
				//将回显的省、市、县在对顶的列表上添加高亮
				$(".provinceList li").css("color","#000");
				$(".cityList li").css("color","#000");
				$(".districtList li").css("color","#000");
				$("#"+provinceId).css("color","red");
				$("#"+cityId).css("color","red");
				$("#"+districtId).css("color","red");//3382
				//隐藏省、市的列表,显示当前市的县的列表
				$(".provinceList ul").css("display","none");
	    		$(".cityList ul").css("display","none");
	    		$(".districtList ul").css("display","");
	    		//获取当前地区的页面片段,使用layer层弹出
	    		var content=$("#areaContent").html();
		    	layer.open({
		    		 title: [
		    		         '所在地区',
		    		         //'background-color: #FF4351; color:#fff;'
		    		       ],
		            type: 1,
		            content: content,
		            anim: 'up',
		            style: 'position:fixed; bottom:0; left:0; width: 100%; height: 400px; border:none;',
		          });
	    	}
	    	status="true";
	    	return false;
		}
		//无回显数据并且没有加载过数据,进行初始化省的数据
		if((areaData==null || areaData=="" || typeof(areaData)=="undefined")){
	    	$.ajax({
	        	type: "get",
	            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId=1',
	            dataType: 'json',
	            success: function(data){
	            	if(data.status!="200"){
	            		layer.open({content: data.message,skin: 'msg',time: 2});
						return false;
	            	}
	            	var areaListHtml="";
	            	var province_tpl=$("#province_tpl").html();
					for(var i=0;i<data.data.length;i++){
						var area=data.data[i];
						var areaData={"provinceId":area.id,"provinceName":area.name};
						areaListHtml+=wx_common.render(province_tpl,areaData);//渲染模板
					}
					$(".provinceList ul").html(areaListHtml);
					var content=$("#areaContent").html();
			    	layer.open({
			    		 title: [
			    		         '所在地区',
			    		         //'background-color: #FF4351; color:#fff;'
			    		       ],
			            type: 1,
			            content: content,
			            anim: 'up',
			            style: 'position:fixed; bottom:0; left:0; width: 100%;height: 400px;border:none;',
			          });
			    	status="true";
			    	
			    	//隐藏市、县的列表,显示省的列表数据
			    	$(".provinceList ul").css("display","");
		    		$(".cityList ul").css("display","none");
		    		$(".districtList ul").css("display","none");
			    	
	            },
	            error: function(){
	                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	            }
	        });
	    	return false;
		}
		//页面已经加载了一次数据,下次不再在加载数据,直接将地区数据弹出
		if("true"==status && (areaData!=null && areaData!="" && typeof(areaData)!="undefined")){
			$(".provinceList li").css("color","#000");
			$(".cityList li").css("color","#000");
			$(".districtList li").css("color","#000");
			$("#"+provinceId).css("color","red");
			$("#"+cityId).css("color","red");
			$("#"+districtId).css("color","red");
			var content=$("#areaContent").html();
	    	layer.open({
	    		title: [
	    		         '所在地区',
	    		         //'background-color: #FF4351; color:#fff;'
	    		       ],
	            type: 1,
	            content: content,
	            anim: 'up',
	            style: 'position:fixed; bottom:0; left:0; width: 100%; height: 400px; border:none;',
	    	});
	    	return false;
		}
    });
	
	/**
	 * 点击省,获取市的数据
	 */
	$("body").delegate(".province","click",function(){
		$(this).parent().find("li").css("color","#000");
		$(this).css("color","red");
		var provinceId=$(this).attr("id");
		var provinceName=$(this).find(".weui-flex-item").text();
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId='+provinceId,
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
            		layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
            	}
            	var areaListHtml="";
            	var city_tpl=$("#city_tpl").html();
				for(var i=0;i<data.data.length;i++){
					var area=data.data[i];
					var areaData={"cityId":area.id,"cityName":area.name};
					areaListHtml+=wx_common.render(city_tpl,areaData);//渲染模板
				}
				$(".cityList ul").html(areaListHtml);
				//隐藏省、县的列表,显示当前省的市列表数据
		    	$(".provinceList ul").css("display","none");
	    		$(".cityList ul").css("display","");
	    		$(".districtList ul").css("display","none");
	    		//弹出层顶部省数据和页面元素样式的处理
	    		$(".provinceData").text(provinceName);
	    		$(".provinceData").attr("provinceId",provinceId);
	    		$(".provinceData").parent().parent().css("float","left");
	    		$(".provinceData").css("color","#000");
	    		//弹出层顶部市数据和页面元素样式的处理
	    		$(".cityData").css("display","");
	    		$(".cityData").css("color","red");
	    		$(".cityData").text("请选择");
	    		$(".cityData").attr("cityId","");
	    		$(".cityData").parent().parent().css("float","");
	    		//弹出层顶部县数据和页面元素样式的处理
	    		$(".districtData").text("请选择");
	    		$(".districtData").attr("districtId","");
	    		$(".districtData").css("display","none");
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	});
	
	/**
	 * 点击市，获取县的数据
	 */
	$("body").delegate(".city","click",function(){
		$(this).parent().find("li").css("color","#000");
		$(this).css("color","red");
		var cityId=$(this).attr("id");
		var cityName=$(this).find(".weui-flex-item").text();
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/sys/area/selectArea.htm?parentId='+cityId,
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
            		layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
            	}
            	var areaListHtml="";
            	var district_tpl=$("#district_tpl").html();
				for(var i=0;i<data.data.length;i++){
					var area=data.data[i];
					var areaData={"districtId":area.id,"districtName":area.name};
					areaListHtml+=wx_common.render(district_tpl,areaData);//渲染模板
				}
				$(".districtList ul").html(areaListHtml);
				//隐藏省、市的列表,显示当前市的县列表数据
		    	$(".provinceList ul").css("display","none");
	    		$(".cityList ul").css("display","none");
	    		$(".districtList ul").css("display","");
	    		//弹出层顶部市数据和页面元素样式的处理
	    		$(".cityData").text(cityName);
	    		$(".cityData").attr("cityId",cityId);
	    		$(".cityData").parent().parent().css("float","left");
	    		$(".cityData").css("color","#000");
	    		//弹出层顶部县数据和页面元素样式的处理
	    		$(".districtData").css("display","");
	    		$(".districtData").css("color","red");
	    		$(".districtData").text("请选择");
	    		$(".districtData").attr("districtId","");
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	});
	
	/**
	 * 点击县,选择地区数据
	 */
	$("body").delegate(".district","click",function(){
		$(this).parent().find("li").css("color","#000");
		$(this).css("color","red");
		//获取省、市、县数据
		var provinceId=$(".provinceData").attr("provinceId");
		var provinceName=$(".provinceData").html();
		var cityId=$(".cityData").attr("cityId");
		var cityName=$(".cityData").html();
		var districtId=$(this).attr("id");
		var districtName=$(this).find(".weui-flex-item").text();
		var area=provinceName+" "+cityName+" "+districtName;
		//弹出层顶部县数据的处理
		$(".districtData").text(districtName);
		$(".districtData").attr("districtId",districtId);
		//将省、市、县数据赋值到页面的隐藏框
       	$("#areaId").val(area);   
       	$("#provinceId").val(provinceId);   
       	$("#provinceName").val(provinceName);   
       	$("#cityId").val(cityId);   
       	$("#cityName").val(cityName);   
       	$("#districtId").val(districtId);   
       	$("#districtName").val(districtName);   
		layer.closeAll();
	});
	
	/**
	 * 点击弹出层顶部省
	 */
	$("body").delegate(".provinceData","click",function(){
		//弹出层顶部省、市、县的高亮
		$(".provinceData").css("color","red");
		$(".cityData").css("color","#000");
		$(".districtData").css("color","#000");
		//省、市、县列表数据的显示与隐藏
		$(".provinceList ul").css("display","");
		$(".cityList ul").css("display","none");
		$(".districtList ul").css("display","none");
	});
	
	/**
	 * 点击弹出层顶部市
	 */
	$("body").delegate(".cityData","click",function(){
		//弹出层顶部省、市、县的高亮
		$(".provinceData").css("color","#000");
		$(".cityData").css("color","red");
		$(".districtData").css("color","#000");
		//省、市、县列表数据的显示与隐藏
		$(".provinceList ul").css("display","none");
		$(".cityList ul").css("display","");
		$(".districtList ul").css("display","none");
	});
	
	/**
	 * 点击弹出层顶部县
	 */
	$("body").delegate(".districtData","click",function(){
		//弹出层顶部省、市、县的高亮
		$(".provinceData").css("color","#000");
		$(".cityData").css("color","#000");
		$(".districtData").css("color","red");
		//省、市、县列表数据的显示与隐藏
		$(".provinceList ul").css("display","none");
		$(".cityList ul").css("display","none");
		$(".districtList ul").css("display","");
	});
}); 


