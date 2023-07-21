$(function(){
	$("#button1").click(function(){
		var href=$(this).attr("href");
		window.location.href=href;
	});
	$("#button2").click(function(){
		var href=$(this).attr("href");
		window.location.href=href;
	});
	$("#button3").click(function(){
		var href=$(this).attr("href");
		window.location.href=href;
	});
	/**
	 * 单搜索
	 */
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	/**
	 * 保存咨询回复内容
	 */
	$('.delOrder').on('click', function(){
			var id=$(this).attr("id");
			var pId=$(this).attr("pid");
			var con=$(this).attr("content");
			$("#orders_id").val(id);
			$("#product_id").val(pId);
			$("#content_id").html(con);
			var content=$("#delModal").html();
			layer.open({
				type: 1,
				title:'<i class="fa fa-search"></i> '+fy.getMsg('咨询回复'),
				area: ['520px', '230px'],
				shadeClose: true, //点击遮罩关闭
				content:content,
				success: function(layero, index){
					if(jsValidate){
						$(layero).find("#cancelOrdersForm").validate({
							rules: {"content":{required: true,maxlength:255}},
						 	messages: {"content":{required: fy.getMsg('回复内容不能为空'),maxlength:fy.getMsg('最大长度不能超过255字符')}},
							submitHandler: function(form){
								layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
								form.submit();
							}
						});
					}
				}
				
			});
		});
	/**
	 * 单条删除
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	/**
	 * 批量删除
	 */
	$(".deleteAll").click(function(){
		if($(".trCheck:checked").length == 0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要删除的行'),2000);
			return false;
		}
		var arr=[];//定义一个数组
		//遍历并获取当前被选中的id
		$(".trCheck:checked").each(function(){
			if ($(this).prop("checked")) {
				var id=$(this).val();
				arr.push(id);//向数组中放入ID
			}
		});
		var href=$(this).attr("href")+"?ids="+arr;
		fdp.confirm(fy.getMsg('确定要删除所选么？'),href); 
	});
});