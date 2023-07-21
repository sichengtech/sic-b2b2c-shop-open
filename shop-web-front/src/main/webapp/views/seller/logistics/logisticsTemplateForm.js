$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64},
			},
			messages: {
				"name":{required: fy.getMsg('请输入运费模块名称'),maxlength:fy.getMsg('最大长度不能超过64字符')},
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
				//isFreeShipping：是否包邮，1：卖家承担运费，0自定义运费，如果是1，首件、运费、续件输入框不用做验证
				var isFreeShipping=$("input[name='isFreeShipping']:checked").val();
				if(isFreeShipping == "0"){
					//判断是否添加地区
					if($.trim($("#mesTable tbody").html())== ""){
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请指定地区城市'),2000);
						return false;
					}
					//判断选择的地区是否超长
					var flag=true;
					$(".areaIds").each(function(){
						var areaIds=$(this).val();
						if(areaIds!="" && areaIds.length>1024){
							flag=false;
							return flag;
						}
					});
					$(".areaNames").each(function(){
						var areaNames=$(this).val();
						if(areaNames!="" && areaNames.length>1024){
							flag=false;
							return flag;
						}
					});
					if(!flag){
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 指定地区超长",2000);
						return false;
					}
					$("#mesTable tbody").find("input").each(function(){
						var va=$(this).val()
						if(va == ""){
							$("#errorMsg").css("display","block");
							flag= false;
						}
						if($(this).attr("ty")== "1"){
							var pattern1=/^[0-9]*[1-9][0-9]*$/;
							if(!pattern1.test(va)){
								flag= false;
							}
						}
						if($(this).attr("ty")== "2"){
							//var pattern2=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
							var pattern2=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
							if(!pattern2.test(va)){
								flag= false;
							}
						}
					});
					if(!flag){
						$("#errorMsg").css("display","block");
						return false;
					}
				}else{
					$("#errorMsg").css("display","none");
				}
						
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	//循环填充选择区域弹出框的td
	(function(){
		//获取到最长的tr中的td的个数
		var tdCount=0;
		$(".areaTable tr").each(function(){
			var tds1=$(this).find("td");
			if(tds1.length>tdCount){
				tdCount=tds1.length;
			}
		});
		//合并每一行的最后一个td
		$(".areaTable tr").each(function(){
			var tds2=$(this).find("td");
			$(tds2).last().attr("colspan",tdCount-$(tds2).length+1);
		});
	})();
});

/**
 * 渲染模板
 */
function render(tpl,data){
	return laytpl(tpl).render(data);
}

/**
 * 为指定地区添加运费(添加行)
 * */
$("#setFreight").click(function(){
	//设置输入框的名字
	var itemIndex=$("#itemIndex").val()*1;
	itemIndex+=1;
	$("#itemIndex").val(itemIndex);
	//如果取消批量是显示的，说明当前处于批量操作的状态，要显示复选框
	var editDisplay="display:none";
	if(!$("#cancelBatchEdit").is(':hidden')){
		editDisplay="";
	}else{
		//如果不是批量操作状态，显示批量操作按钮
		$("#batchEdit").css("display","");
	}
	var add_area_Tpl=$("#add_area_Tpl").html();
	var dataJson = {"itemIndex":"logisticsTemplateItemList["+itemIndex+"].","editDisplay":editDisplay,"areaNames":fy.getMsg('未添加地区'),
			"areaIds":"","firstItem":"","firstPrice":"","continueItem":"","continuePrice":""};
	var tr = render(add_area_Tpl,dataJson);
	$("#tbody").append(tr);
	//$(".addLine .firstTableTd .trCheck").remove();
});

/**
 * 编辑时回显详细信息
 * */
(function(){
	if(typeof(logisticsTemplateList)!="undefined" && logisticsTemplateList!=null){
		for(var i=0;i<logisticsTemplateList.length;i++){
			var add_area_Tpl=$("#add_area_Tpl").html();
			var dataJson = {"itemIndex":"logisticsTemplateItemList["+i+"].","editDisplay":"display:none","areaNames":logisticsTemplateList[i].names,
					"areaIds":logisticsTemplateList[i].ids,"firstItem":logisticsTemplateList[i].firstItem,"firstPrice":logisticsTemplateList[i].firstPrice,
					"continueItem":logisticsTemplateList[i].continueItem,"continuePrice":logisticsTemplateList[i].continuePrice};
			var tr = render(add_area_Tpl,dataJson);
			$("#tbody").append(tr);
		}
	}
})();
/**
 * 选择区域
 * */
$("#tbody").delegate("button[class='sui-btn btn-primary editArea']",'click',function(e){
	//先去掉输入框的disabled
	$("#areaModal input").attr("disabled",false);
	$("#areaModal input").attr("checked",false);
	//获取当前行，在后面用来修改当前行的数据
	var thisTr=$(this).parent().parent();
	//获取当前行已经选中的地区，用于回显
	var areaIds=$(thisTr).find("input[class='areaIds']").val();
	if(areaIds!=""){
		var idss=areaIds.split(",");
		console.log("areaIds:"+areaIds);
		console.log("长度"+idss.length);
		for(var i=0;i<idss.length;i++){
			var checkArea=$("#areaModal").find("input[value="+idss[i]+"]").not(".lageArea");
			$(checkArea).attr("checked", true);
			//$(checkArea).prop("checked", true);
			//如果当前选中的地区是省级地区，则要找到改省级下的市级地区，置为已选中状态
			if($(checkArea).attr("class") == 'province'){
				/*$("#areaModal").find("input[parentId="+idss[i]+"]").each(function(){
					$(this).attr("checked", true);
				});*/
				selectProvince($(checkArea));
			}
		}
	}
	//把其他行已经选中的地区置为不可选的状态
	var appendTrs=$(thisTr).parent().find(".appendTr").not(thisTr);
	var aids="";
	for(var i=0;i<appendTrs.length;i++){
		aids+=$(appendTrs[i]).find("input[class='areaIds']").val()+",";
	}
	if(aids !=""){
		var aidss=aids.split(",");
		for(var i=0;i<aidss.length;i++){
			//$("#areaModal").find("input[value="+aidss[i]+"]").prop("disabled","disabled");
			var selectArea=$("#areaModal").find("input[value="+aidss[i]+"]").not(".lageArea");
			$(selectArea).attr("disabled","disabled");
			//找到当前市所属省的所有没有被选中市
			var notSelectedCitys=$(selectArea).parent().parent().find(".city:not(:disabled)");
			//notSelectedCitys==0说明都被选中了，要把当前市所属省置为不可选中的状态
			if(notSelectedCitys.length==0){
				$(selectArea).parents("td").find(".province").attr("disabled","disabled");
			}
			//如果当前选中的地区是省级地区，则要找到改省级下的市级地区，置为已选中状态
			if($(selectArea).attr("class") == 'province'){
				$(selectArea).parent().parent().find("input[type='checkbox']").attr("disabled","disabled");
				//如果一个大区中的所有省都被选中，则当前大区也不能在被选了
				//找到一个大区中没有被选中的省份
				var noSelectPriv=$(selectArea).parents("tr").find(".province:not(:disabled)");
				if(noSelectPriv.length==0){
					$(selectArea).parents("tr").find(".lageArea").attr("disabled","disabled");
				}
			}
		}
	}
	
	var content=$("#areaModal").html();
	layer.open({
		type: 1,
		title:fy.getMsg('选择区域'),
		area: ['700px', '480px'],
		shadeClose: true, //点击遮罩关闭
		content: content,
		success: function(layero, index){
			//选择区域：大区控制省级地区
			$(layero).find(".lageArea").click(function(){
				if ($(this).prop("checked")) {
					//选中当前大区下没有被选过的所有省
					$(this).parents("tr").find("input[type='checkbox']:not(:disabled)").prop("checked", true);
				}else {
					//取消所选中的省
					$(this).parents("tr").find("input[type='checkbox']:not(:disabled)").prop("checked", false);
				} 
			});
			
			//选择区域：省级控制市级地区,省级地区反控大区
			$(layero).find(".province").click(function(){
				if ($(this).prop("checked")) {
					//将所有市级数据都选中
					$(this).parent().parent().find("input[type='checkbox']:not(:disabled)").prop("checked", true);
					var bl1=true;
					//找到和当前省级地区在同一大区的省级地区
					$(this).parents("tr").find(".province:not(:disabled)").each(function(){
						if (!$(this).prop("checked")){
							bl1=false;
						}
					});
					if(bl1){
						$(this).parents("tr").find(".lageArea").prop("checked", true);
					}
				}else {
					$(this).parent().parent().find("input[type='checkbox']").prop("checked", false);
					$(this).parents("tr").find(".lageArea").prop("checked", false);
				}
			});
			
			//市级地区反控省级地区和大区
			$(layero).find(".city").click(function(){
				if ($(this).prop("checked")) {
					var bl1=true;
					var bl2=true;
					//找到和当前市级地区在同一省的市
					$(this).parent().parent().find("input[type='checkbox']").each(function(){
						if (!$(this).prop("checked")){
							bl1=false;
						}
					});
					if(bl1){
						$(this).parents("td").find(".province").prop("checked", true);
					}
					//找到前市所属大区下的省
					$(this).parents("tr").find(".province").each(function(){
						if (!$(this).prop("checked")){
							bl2=false;
						}
					});
					if(bl2){
						$(this).parents("tr").find(".lageArea").prop("checked", true);
					}
				}else {
					$(this).parents("td").find(".province").prop("checked", false);
					$(this).parents("tr").find(".lageArea").prop("checked", false);
				}
				
			});
		
			//选择区域：确定
			$(layero).find(".selectAreaBtn").click(function(){
				var ids=",";
				var names="";
				$(layero).find(".province:checked").each(function(){
					//获取当前省下不可选择的市
					var disCity=$(this).parents("td").find(".city:disabled");
					//如果没有不可选的市，就添加省
					if(disCity.length==0){
						$(this).parent().parent().find(".city").prop("checked", false);
						names+=$(this).siblings("label").text()+"_";
						ids+=$(this).val()+",";
					}
				});
				$(layero).find(".city:checked").not(".city:disabled").each(function(){
					ids+=$(this).val()+",";
					names+=$(this).siblings("label").text()+"_";
				});
				//ids=ids.substring(0, ids.length-1);
				names=names.substring(0, names.length-1);
				$(thisTr).find("input[class='areaIds']").val(ids);
				$(thisTr).find("input[class='areaNames']").val(names);
				$(thisTr).find("span[name='areaNames']").text(names);
				layer.closeAll('page');
				//把页面中隐藏的地区弹出框中的复选框都置为未选中状态
				$("#areaModal").find("input[type='checkbox']").attr("checked", false);
			});
		}
	}); 
});

/**
 * 选择区域：选择市
 * */
function currentCitys(e){
	//关闭其他省的市弹出框
	$(".city-sub").not($(e).next(".city-sub")).hide();
	$(".newSpan").not($(e).parent()).removeClass("newSpan");
	var cityDiv=$(e).parent().find("div[class='city-sub']");
	if($(cityDiv).is(":hidden")){
		$(cityDiv).show();
		$(e).parent().addClass("newSpan");
	}else{
		$(cityDiv).hide();
		$(e).parent().removeClass("newSpan");
	}
}


function selectProvince(e){
	if ($(e).attr("checked") || $(this).prop("checked")) {
		//将所有市级数据都选中
		$(e).parent().parent().find("input[type='checkbox']:not(:disabled)").attr("checked", true);
		var bl1=true;
		//找到和当前省级地区在同一大区的省级地区
		$(e).parents("tr").find(".province:not(:disabled)").each(function(){
			if (!$(this).attr("checked")){
				bl1=false;
			}
		});
		if(bl1){
			$(e).parents("tr").find(".lageArea").attr("checked", true);
		}
	}else {
		$(e).parent().parent().find("input[type='checkbox']").attr("checked", false);
		$(e).parents("tr").find(".lageArea").attr("checked", false);
	}
}

/**
 * 关闭弹出的市级框
 * */
function coloseCurrentCity(e){
	$(e).parent().parent().hide();
	$(e).parent().parent().parent().removeClass("newSpan");
}


/**
 * 批量操作
 * */
$("#batchEdit").click(function(){
	$("#mesTable tbody .firstTableTd").prepend("<input type=\"checkbox\" class=\"trCheck\">");
	$(".batchEditTr").css("display","");
	$(this).css("display","none");
	$("#cancelBatchEdit").css("display","");
});

/**
 * 删除
 * */
$("#tbody").delegate("a[class='sui-btn btn-danger delLine']",'click',function(e){
	if($(".appendTr").length == 2){
		$("#batchEdit").css("display","none");
	}
	$(this).parent().parent().remove();
});

/**
 * 取消批量
 * */
$("#cancelBatchEdit").click(function(){
	$("#mesTable .trCheck").each(function(){
		$(this).remove();
	});
	$(".batchEditTr").css("display","none");
	$(this).css("display","none");
	$("#batchEdit").css("display","");
});

/**
 * 批量设置
 * */
$("#batchSet").click(function(){
	if($(".trCheck:checked").length == 0){
		fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要设置的行'),2000);
		return false;
	}
	var content=$("#batchSetModal").html();
	layer.open({
		type: 1,
		title:fy.getMsg('批量设置'),
		area: ['650px', '170px'],
		shadeClose: true, //点击遮罩关闭
		content: content,
		success: function(layero, index){
			$(layero).find(".batchSetBtn").click(function(){
				//获取到当前的设置的信息（首件、运费、续件、运费）
				var firstItem=$(layero).find("input[name='firstItem']").val();
				var firstPrice=$(layero).find("input[name='firstPrice']").val();
				var continueItem=$(layero).find("input[name='continueItem']").val();
				var continuePrice=$(layero).find("input[name='continuePrice']").val();
				//把获取到的值赋给表格中相应的input
				if(firstItem !=""){
					$(".trCheck:checked").parent().parent().find(".firstItem").val(firstItem);
					$("#batchSetModal .firstItem").attr("value",firstItem);
				}
				if(firstPrice !=""){
					$(".trCheck:checked").parent().parent().find(".firstPrice").val(firstPrice);
					$("#batchSetModal .firstPrice").attr("value",firstPrice);
				}
				if(continueItem !=""){
					$(".trCheck:checked").parent().parent().find(".continueItem").val(continueItem);
					$("#batchSetModal .continueItem").attr("value",continueItem);
				}
				if(continuePrice !=""){
					$(".trCheck:checked").parent().parent().find(".continuePrice").val(continuePrice);
					$("#batchSetModal .continuePrice").attr("value",continuePrice);
				}
				layer.closeAll('page');
			});
		}
	});
});

/**
 * 卖家承担运费点击事件
*/
$(".isFreeShipping[value='1']").click(function(){
	var inp=$(this).parent();
	layer.confirm(fy.getMsg('卖家承担运费后，详细设置中的价格信息会失效，仅地区有效，确定继续吗？'), {
		btn: [fy.getMsg('确定'),fy.getMsg('放弃')] //按钮
	}, function(){
		layer.closeAll('dialog');
		$(inp).addClass("checked");
		//如果选择了卖家承担运费，则首件、运费、续件不需要做验证，要隐藏错误提示提示信息
		$("#errorMsg").css("display","none");
	}, function(){
		layer.closeAll('dialog');
		$(".isFreeShipping[value='0']").parent().addClass("checked");
		$(inp).removeClass("checked");
	});
});

/**
 * 批量操作：全选和反选
 * 全选或全不选
*/
$("#selectAll").click(function(){
	if ($(this).prop("checked")) {
		$("#mesTable tbody").find("input[type='checkbox']").prop("checked", true);
	}else {
		$("#mesTable tbody").find("input[type='checkbox']").prop("checked", false);
	} 
});

/**
 * 每行复选框  反控制 全选
*/
$("#tbody").delegate("input[class='trCheck']",'click',function(e){
	if ($(this).prop("checked")) {
		var bl=true;
		$("#mesTable tbody").find("input[type='checkbox']").each(function(){
			if (!$(this).prop("checked")){
				bl=false;
			}
		});
		if(bl){
			$("#selectAll").prop("checked", true);
		}
	}else {
		$("#selectAll").prop("checked", false);
	} 
});

/**
 * 批量删除
*/
$("#batchDelete").click(function(){
	if($(".trCheck:checked").length == 0){
		fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要删除的行'),2000);
		return false;
	}
	layer.confirm(fy.getMsg('确定要删除么？'), {
		btn: [fy.getMsg('确定'),fy.getMsg('放弃')] //按钮
	}, function(){
		//遍历并移除当前被选中的行
		$(".trCheck:checked").each(function(){
			if ($(this).prop("checked")) {
				var parent=$(this).parent().parent().remove();
			}
		});
		//把全选置为未选中状态
		$("#selectAll").prop("checked", false);
		//如果表格中的行都被删除了，要隐藏批量设置、取消批量、批量删除按钮
		if($.trim($("#mesTable tbody").html())== ""){
			$(".batchEditTr").css("display","none");
			$("#batchEdit").css("display","none");
			$("#cancelBatchEdit").css("display","none");
		}
		layer.closeAll('dialog');
	}, function(){
		layer.closeAll('dialog');
	});
	
});

