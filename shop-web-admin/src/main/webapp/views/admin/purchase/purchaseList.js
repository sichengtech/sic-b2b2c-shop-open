$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	
	/**
	 * 快速查询
	 */
	$('.search').on('click', function(){
		var content=$("#myModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-search"></i> '+fy.getMsg('查询'),
			area: ['600px', '350px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				//出账日期
				//快速搜索
				if(jsValidate){
					$(layero).find("#fastSearch").validate({
						rules: {
							"purchaseId":{regex:/^[0-9]*$/},
						},
						messages: {
							"purchaseId":{regex:fy.getMsg('采购单号只能为数字')},
							
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
							var endCreateDatesdadsa=$("#fastSearch").find('input[name="endCreateDate"]').val();
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
});