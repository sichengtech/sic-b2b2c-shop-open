$(document).ready(function(){
	
	//根据首字母搜索时做回显
	if(typeof(firstLetter)=="undefined" || firstLetter==""){
		$(".zm li[value='all']").addClass("cur");
	}else{
		$(".zm li[value="+firstLetter+"]").addClass("cur");
	}
	
	//只显示有值的的首字母
	$("ul.zm li").not(".all").each(function(){
		var firstLetter=$(this).attr("value");
		if(typeof(firstLetterMap)=="undefined" || typeof(firstLetterMap[firstLetter])=="undefined"){
			$(this).remove();
		}
	});
	
	//parent是js自带的全局对象，可用于操作父页面
	var index = parent.layer.getFrameIndex(window.name); //获取父页面中的layer弹层的窗口索引
	//让弹层自适应iframe
	//parent.layer.iframeAuto(index);
	
	/**
	 * 按首字母搜索
	 */
	$("ul.zm li").click(function(){
		$(this).siblings(".cur").removeClass("cur");
		$(this).addClass("cur");
		var value=$(this).attr("value");
		var storeId=$("#storeId").val();
		if("all"==value){
			value="";
		}
		if("all"==value){
			window.location.href=ctxs+"/product/productSpu/selectBrand.htm"; //搜索全部
		}else{
			window.location.href=ctxs+"/product/productSpu/selectBrand.htm?firstLetter="+value+"&storeId="+storeId;//按首字母搜索
		}
	});
	
	function addBrand(data){
		$.ajax({
			url:ctxs+"/product/productSpu/selectBrand2.htm",
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data!=null){
					$(".brandsel-list").html("");
					for(var i=0;i<data.length;i++){
						var brandTpl = $("#brandTpl").html();//模板
						var dataJson={"brand":data[i]};
						var brandLi=render(brandTpl,dataJson);
						$(".brandsel-list").append(brandLi);
					}
				}
			}
		});
	}
	
	/**
	 * 选用一个品牌
	 */
	$("a[name='selectBrand']").click(function(){
		var brandId=$(this).attr("brandId");
		var brandName=$(this).attr("brandName");
		//在iframe中查找父页面元素，并传值
		$('#brandId', window.parent.document).val(brandId);
		$('#brandName', window.parent.document).val(brandName);
		//关闭弹出窗口
		window.parent.layer.close(index);
	});
	
	/**
	 * 表单验证
	 * 第一步 选择商品分类，的表单验证
	 */
	$("#searchForm").submit(function(){
		var name=$("input[name='name']").val();
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
		return true;
	});
	
	//渲染模板
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
});