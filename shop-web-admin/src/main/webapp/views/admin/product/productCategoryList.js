$(function(){
	/**
	 * 列表中删除按钮的弹框
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	
	/**
	 * 列表搜索
	 */
	$(".searchList").click(function(){
		$("#searchForm").submit();
	});
	
	/**
	 * 弹出绑定品牌页面
	 */
	$(".bindBrand").click(function(){
		var categoryId=$(this).attr("categoryId");
		layer.open({
			type: 2,//2表示iframe层
			title:' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('绑定品牌'),
			area: ['750px', '500px'],
			btn:[fy.getMsg('确定'),fy.getMsg('取消')],
			shadeClose: true, //点击遮罩关闭
			content: ctxa+'/product/productCategory/bindBrand1.do?categoryId='+categoryId,//在iframe窗口中打开这个地址
			success: function(layero, index){ },
			yes: function(index,layero){ 
				var body = layer.getChildFrame('body', index);
				var brandIds = [];
				body.find(".selectedClass .brandId").each(function(){
					brandIds.push($(this).val());
				})
				if(brandIds.length==0){
					layer.msg(fy.getMsg("请选择品牌"));
					return;
				}
				$.ajax({                       
		              type: 'get',
		              url: ctxa+'/product/productCategory/bindBrand2.do?categoryId='+categoryId+'&brandIds='+brandIds,
		              dataType: 'json',
		              success: function(data){
		            	  var status = data.status;
		            	  var content = data.content;
		            	  if(status=='0'){
		            		  layer.closeAll();
		            		  parent.layer.msg(content);
		            	  }
		            	  if(status=='1'){
		            		  layer.msg(content);
		            		  return false;
		            	  }
		              },
		              error: function(data){
		            	  layer.msg(fy.getMsg('绑定失败'));
		                  return false;
		              }
		        });
			}
		});
	});


	/**
	 * 弹出绑定品推荐位
	 */
	$(".bindRecommend").click(function(){
		var categoryId=$(this).attr("categoryId");
		layer.open({
			type: 2,//2表示iframe层
			title:' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('绑定推荐位置'),
			area: ['750px', '500px'],
			btn:[fy.getMsg('确定'),fy.getMsg('取消')],
			shadeClose: true, //点击遮罩关闭
			content: ctxa+'/product/productCategory/categoryAndRecommend1.do?categoryId='+categoryId,//在iframe窗口中打开这个地址
			success: function(layero, index){ },
			yes: function(index,layero){
				var body = layer.getChildFrame('body', index);
				var brandIds = [];
				body.find(".selectedClass .brandId").each(function(){
					brandIds.push($(this).val());
				})
				if(brandIds.length==0){
					layer.msg(fy.getMsg("请选择品牌"));
					return;
				}
				$.ajax({
					type: 'get',
					url: ctxa+'/product/productCategory/categoryAndRecommend2.do?categoryId='+categoryId+'&brandIds='+brandIds,
					dataType: 'json',
					success: function(data){
						var status = data.status;
						var content = data.content;
						if(status=='0'){
							layer.closeAll();
							parent.layer.msg(content);
						}
						if(status=='1'){
							layer.msg(content);
							return false;
						}
					},
					error: function(data){
						layer.msg(fy.getMsg('绑定失败'));
						return false;
					}
				});
			}
		});
	});

	/**
	 * 弹出佣金框
	 */
	$(".commissionModal").click(function(){
		var categoryId=$(this).attr("categoryId");
		var cateName=$(this).attr("categoryName");
		var commission=$(this).attr("commission");
		var content=$("#commissionModal").html();
		layer.open({
			type: 1,
			title:fy.getMsg('佣金'),
			scrollbar: false,
			area: ['600px', '326px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#cateName").html(cateName);
				$(layero).find("input[name='categoryId']").val(categoryId);
				if(commission!='undefined'){
					$(layero).find("input[name='commission']").val(commission);
				}
				$(layero).find("#commissionForm").submit(function(){
					var commission = $(layero).find("#commission").val();
					if(commission==null || commission==''){
						layer.msg(fy.getMsg('佣金不能为空'));
						return false;
					};
					var reg = /^(\d|[1-9]\d|100)(\.\d{1,3})?$/; 
					var r = commission.match(reg);
					if(r==null){ 
						layer.msg(fy.getMsg('请输入0-100的数(最多小数点后三位)'));
						return false;
					};
					setTimeout(function(){
						layer.close(index);
						//如果1.5秒内未完成操作，则给出请等待的提示
						layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					},1500);
				});
			}
		});
	});
});