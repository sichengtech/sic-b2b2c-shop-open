$(function(){
	/**
	 * 删除已选品牌
	 */
	$("#brand").delegate(".selected_btn","click",function(){
		$(this).removeClass("btn btn-info btn-xs selected_btn");
		$(this).addClass("btn btn-default btn-xs unselected_btn");
		$(this).children("i").removeClass("fa fa-times-circle");
		$(this).children("i").addClass("fa fa-check-square");
		$(".unselectedClass").append($(this).parent());
	});
	
	/**
	 * 选中品牌
	 */
	$("#brand").delegate(".unselected_btn","click",function(){
		$(this).removeClass("btn btn-default btn-xs unselected_btn");
		$(this).addClass("btn btn-info btn-xs selected_btn");
		$(this).children("i").removeClass("fa fa-check-square");
		$(this).children("i").addClass("fa fa-times-circle");
		$(".selectedClass").append($(this).parent());
	});
	
	/**
	 * 快速搜索品牌(键盘抬起事件)
	 */
	$(".searchBrand").keyup(function(){
		$(this).parent().parent().next().next().css("display","block");
		$(this).parent().parent().next().next().next().css("display","none");
		var brandName = $(".searchBrand").val();
		$(".unselected_btn span").each(function(){
			$(this).parent().parent().removeAttr("style");
			var name = $(this).html();
			if(name.indexOf(brandName)==-1){
				$(this).parent().parent().css("display","none");
			}
		});
		var length = $(".class_div:visible").length;
		if(length==0){
			$(this).parent().parent().next().next().css("display","none");
			$(this).parent().parent().next().next().next().css("display","block");
		}
	});
	
	/**
	 * 点击首字母搜索
	 */
	$(".firstLetter").click(function(){
		$(this).parent().next().css("display","block");
		$(this).parent().next().next().css("display","none");
		var brandName = $(".searchBrand").val();
		var f = $(this).html();
		$(".unselected_btn .firstLetter").each(function(){
			if(f == '全部'){
				$(this).parent().parent().removeAttr("style");
			}else{
				var firstLetter = $(this).val();
				$(this).parent().parent().removeAttr("style");
				if(f != firstLetter){
					$(this).parent().parent().css("display","none");
				}
			}
		});
		var length = $(".class_div:visible").length;
		if(length==0){
			$(this).parent().next().css("display","none");
			$(this).parent().next().next().css("display","block");
		}
	});
});