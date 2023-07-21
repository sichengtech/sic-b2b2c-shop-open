$(function(){
	/**
	 * 删除提示
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	
	/**
	 * 审核通过
	 */
	$(".auth-pass").click(function(){
		var url=$(this).attr("url");
		fdp.confirm(fy.getMsg('确定要审核通过么？'),url);
	});
	
	/**
	 * 搜索
	 */
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	
	/**
	 * 审核不通过
	 */
	$('.auth-notpass').on('click', function(){
		var brandId=$(this).attr("brandId");
		var bName=$(this).attr("bName");
		var content=$("#authNotPassModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-edit"></i> '+fy.getMsg('拒审品牌'),
			area: ['550px', '340px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#brandName").html(bName);
				$(layero).find("input[name='brandId']").val(brandId);
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
	
	/**
	 * 批量审核通过
	 */
	$(".batch-auth").click(function(){
		var brandIds = new Array();
		$(".batchBrandId").each(function(){
			if($(this).is(':checked')){
				brandIds.push($(this).attr('brandId'));
			}
		});
		if(brandIds.length==0){
			layer.msg(fy.getMsg('请选择品牌'));
			return false;
		}
		var auth = $(this).attr('auth');
		var url = '';
		var info = '';
		if(auth == '1'){
			info = fy.getMsg("确定要批量审核通过么");
			url = ctxa + "/product/productBrand/batchAuth.do?auth="+auth+"&brandIds="+brandIds;
		}
		if(auth == '2'){
			info = fy.getMsg("确定要批量审核不通过么");
			url = ctxa + "/product/productBrand/batchAuth.do?auth="+auth+"&brandIds="+brandIds;
		}
		fdp.confirm(info,url);
	});
	
});