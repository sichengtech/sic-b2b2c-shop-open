$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var url=$(this).attr("url");
		fdp.confirm(fy.getMsg('确定要删除么？'),url);
	});
	//解禁，开始销售
	$(".start-sale").click(function(){
		var url=$(this).attr("url");
		fdp.confirm(fy.getMsg('确定要解禁么？'),url);
	});
	//审核通过
	$(".auth-pass").click(function(){
		var url=$(this).attr("url");
		fdp.confirm(fy.getMsg('确定要审核通过么？'),url);
	});

	//一般搜索(列表上的搜索)
	$("#searchForm").submit(function(){
		var reg = new RegExp("^[0-9]*$");
		var pId = $("input[name='pId']").val();
	    if(!reg.test(pId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('商品编号只能是数字'));
	        return false;
	    }
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});

	//一般搜索，点击放大镜执行搜索
	$("#searchFormButton").click(function(){
		$("#searchForm").submit();
	});

	//禁售
	$('.forbid-sale').on('click', function(){
		var pId=$(this).attr("pId");
		var pName=$(this).attr("pName");
		var content=$("#forbidSaleModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-edit"></i> '+fy.getMsg('禁售商品'),
			area: ['550px', '340px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#productName").html(pName);
				$(layero).find("input[name='pId']").val(pId);
				$(layero).find("#forbidSaleForm").submit(function(){
					setTimeout(function(){
						layer.close(index);
						//如果1.5秒内未完成操作，则给出请等待的提示
						layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					},1500);
				});
			}
		});
	});

	//审核不过
	$('.auth-notpass').on('click', function(){
		var pId=$(this).attr("pId");
		var pName=$(this).attr("pName");
		var content=$("#authNotPassModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-edit"></i> '+fy.getMsg('拒审商品'),
			area: ['550px', '340px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#productName").html(pName);
				$(layero).find("input[name='pId']").val(pId);
				$(layero).find("#authNotPassForm").submit(function(){
					setTimeout(function(){
						layer.close(index);
						//如果1.5秒内未完成操作，则给出请等待的提示
						layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					},1500);
				});
			}
		});
	});

	//快速查询
	$('.search').on('click', function(){
		var content=$("#myModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-search"></i> '+fy.getMsg('查询'),
			area: ['550px', '430px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				//处理日期
				var endCreateDate=$(this).find('input[name="endCreateDate"]').val();
				if(endCreateDate!=null && endCreateDate!=""){
					$(this).find('input[name="endCreateDate"]').val(endCreateDate+" 23:59:59");
				}
				//快速搜索
				if(jsValidate){
					$(layero).find("#fastSearch").validate({
						rules: {
							"pId":{maxlength:18,regex:/^[0-9]*$/},
						},
						messages: {
							"pId":{maxlength:fy.getMsg("最大长度不能超过18字符"),regex:fy.getMsg("商品Id只能为数字")},
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
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					});
				}else{
					$(layero).find("#fastSearch").submit(function(){
						setTimeout(function(){
							layer.close(index);
							//如果1.5秒内未完成操作，则给出请等待的提示
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
						},1500);
					});
				}
				
			}
		});
	});

	//数据导出
	/*$('.exp').on('click', function(){
		var content=$("#expModal").html();
		layer.open({
		type: 1,
		title:' <i class="fa fa-search"></i> 数据导出',
		area: ['550px', '300px'],
		shadeClose: true, //点击遮罩关闭
		content: content
		});
	});*/

	//查看SKU
	$('.sku').on('click', function(){
		/*var content=$("#skuModal").html();
		layer.open({
		type: 1,
		title:' <i class="fa fa-search"></i> 查看“100394”SKU',
		area: ['600px', '500px'],
		shadeClose: true, //点击遮罩关闭
		content: content
		});*/
		var pId=$(this).attr("pId");
		var myTitle=fy.getMsg('查看spu的sku', pId);;
		var myRemoteUrl=ctxa+"/product/productSpu/showSku.do?pId="+pId;
		$.post(myRemoteUrl, {}, function(str){
		 layer.open({
			 type: 1,
			 title:myTitle,
			 area: ['800px', '450px'],
			 shadeClose: true, //点击遮罩关闭
			 content: str, //注意，如果str是object，那么需要字符拼接。
		 });
		});
	});

});