$(function(){

	/**
	 * 排序(按销量、收藏、评论、价格)
	 * */
	$(".sortType li").click(function(){
		//获取排序条件
		var sortType=$(this).attr("id");
		//已经根据当前条件排序了，直接返回false
		if($(this).hasClass("active") && sortType!="minPrice"){
			return false;
		}
		if(sortType=="" || typeof(sortType)=="undefined"){
			manager.del("sort");
			manager.del("sortMode");
		}else{
			var sortMode=$(this).find(".sortMode").attr("id");
			manager.add("sort",sortType);
			if(sortType=="minPrice"){
				manager.add("sortMode",sortMode);
			}else{
				manager.add("sortMode","DESC");
			}
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	});
	
	/**
	 * 根据价格搜索
	 * */
	$(".searchBtn").click(function(){
		//最小值
		var minPrice=$("input[name='minPrice']").val();
		var patrn=/^\d{0,9}$|^\d{0,9}[.]\d{1,2}$/;
		var re = new RegExp(patrn);
		if (minPrice!="" && !re.test(minPrice)) {
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 最小价格请输入正整数或两位以内的小数",2000);
            return false;
	    }
		//最大值
		var maxPrice=$("input[name='maxPrice']").val();
		if (maxPrice!="" && !re.test(maxPrice)) {
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 最大价格请输入正整数或两位以内的小数",2000);
            return false;
	    }
		if(minPrice=="" && maxPrice==""){
			manager.del("price");
		}else{
			var price=minPrice+"-"+maxPrice;
			manager.add("price",price);
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	});
	
	/**
	 * 更多(显示隐藏的参数)
	 * */
	$(".moreA").click(function(){
		//如果当前处于多选的状态，就返回false;
		var isMultiselect=$(this).parent().attr("multiselect");
		if(isMultiselect =="Y"){
			return false;
		}
		//显示 隐藏的参数
		var isBrand=$(this).parent().parent().attr("isBrand");
		if("1"==isBrand){
			$(this).parent().parent().removeClass("brand-none-more");
			$(this).parent().parent().addClass("brand-show-more");
		}else{
			$(this).parent().parent().removeClass("attr-none-more");
			$(this).parent().parent().addClass("attr-show-more");
		}
		//显示"收回"按钮,隐藏"更多"按钮
		$(this).siblings(".retractA").css("display","inline-block");
		$(this).css("display","none");
	});
	
	/**
	 * 收起(隐藏 更多的参数)
	 * */
	$(".retractA").click(function(){
		//隐藏更多的数据
		var isBrand=$(this).parent().parent().attr("isBrand");
		if("1"==isBrand){
			$(this).parent().parent().removeClass("brand-show-more");
			$(this).parent().parent().addClass("brand-none-more");
		}else{
			$(this).parent().parent().removeClass("attr-show-more");
			$(this).parent().parent().addClass("attr-none-more");
		}
		//隐藏"收回"按钮,显示"更多"按钮
		$(this).siblings(".moreA").css("display","inline-block");
		$(this).css("display","none");
	});
	
	 /**
     * 是否显示属性右侧的更多按钮
     */
	function showExtMore(){
		var _dl=$(".attr-list-box dl");
		_dl.each(function(){
			var maxWidth = 0;
			var _ul=$(this).find("ul");
			var _li=_ul.find("li");
			_li.each(function(){
				maxWidth += ($(this).outerWidth(true) + 0);
			});
			if (maxWidth > _ul.width()) {
				$(this).find(".moreA").show();
			} else {
				$(this).find(".moreA").hide();
			}
		});
	}
	showExtMore();
	
	/**
	 * 多选
	 * */
	$(".multiselect").click(function(){
		//取消其他的参数的多选状态
		$(".more[multiselect]").each(function(){
			cancleMultiselect($(this).parent().find(".cancleMultiselect"));
		});
		//显示 隐藏的参数
		var _dl=$(this).parent().parent();
		var isBrand=_dl.attr("isBrand");
		if("1"==isBrand){
			_dl.removeClass("brand-none-more");
			_dl.addClass("brand-show-more");
		}else{
			_dl.removeClass("attr-none-more");
			_dl.addClass("attr-show-more");
		}
		//显示取消按钮
		$(this).parent().siblings("dd").find(".select-btn").css("display","block");
		//标识多选状态
		$(this).parent().attr("multiselect","Y");
	});
	
	/**
	 * 取消多选
	 * */
	$(".cancleMultiselect").click(function(){
		cancleMultiselect(this);
	});
	
	/**
	 * 取消多选(需要多次调用，所有单写一个方法)
	 * */
	function cancleMultiselect(e){
		//隐藏更多的数据
		//显示 隐藏的参数
		var _dl=$(e).parents("dl");
		var isBrand=_dl.attr("isBrand");
		if("1"==isBrand){
			_dl.removeClass("brand-show-more");
			_dl.addClass("brand-none-more");
		}else{
			_dl.removeClass("attr-show-more");
			_dl.addClass("attr-none-more");
		}
		//清除选中的数据
		$(e).parent().siblings("#brandSelectedDiv").find(".brandSelected").html("");
		//隐藏按钮和已选条件
		$(e).parent().siblings("#brandSelectedDiv").css("display","none");
		$(e).siblings(".sureBrand").css("display","none");
		$(e).parent().css("display","none");
		//取消多选状态
		$(e).parents("dl").find("div[class='more']").attr("multiselect","N");
		$(e).parents("dl").find(".selected").removeClass("selected");
		$(e).parents("dl").find(".active").removeClass("active");
	}
	
	/**
	 * 多选状态下选择参数、品牌
	 * */
	function selectParamByMult(e){
		if(!$(e).hasClass("active")){
			//把当前参数置为选中状态
			$(e).addClass("active");
			//显示确定按钮
			$(e).parent().siblings(".selectCondition").find(".sureAttr").css("display","inline-block");
		}else{
			//移除选中状态
			$(e).removeClass("active");
			//没有选中任何参数时,隐藏确定按钮
			var length=$(e).parents(".param").find(".active").length;
			if(length==0){
				//隐藏确定按钮
				$(e).parent().siblings(".selectCondition").find(".sureAttr").css("display","none");
			}
		}
	}
	
	/**
	 * 单选状态下选择参数、品牌
	 * */
	function selectParamBySingle(e){
		var id=$(e).parents(".param").attr("id");
		if("attr"==id){
			var attrId=$(e).attr("attrId");
			var attrName=$(e).find("a").text();
			if(manager.get("attr")==""){
				manager.add("attr",attrId+"_"+attrName);
			}else{
				manager.addAttr("attr",attrId,attrName);
			}
		}else if("category"==id){
			var categoryId=$(e).attr("categoryId");
			manager.add("cid",categoryId);
		}else if("brand"==id){
			var brandId=$(e).attr("brandId");
			manager.add("bid",brandId);
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	}
	
	/**
	 * 选择参数
	 * */
	$(".param li").click(function(){
		//是多选状态就选择参数，不是多选就拼接查询条件
		var isMultiselect=$(this).parents(".param").find(".more").attr("multiselect");
		if(isMultiselect =="Y"){
			selectParamByMult($(this));
			return;
		}
		selectParamBySingle($(this));
	});
	
	/**
	 * 品牌：多选(确定按钮点击事件)
	 * */
	function brandSure(){
		//循环找到所有选中的品牌id
		var bids="";
		$("dl.productBrand li.active").each(function(){
			bids+=$(this).attr("brandId")+",";
		});
		bids=bids.substring(0,bids.length-1);
		manager.add("bid",bids);
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	};
	
	/**
	 * 参数：多选(确定按钮点击事件)
	 * */
	function attrSure(e){
		var attrId=$(e).attr("attrId");
		//循环找到所有选中的参数值
		var attrValue="";
		$(e).parent().parent().siblings("ul").find("li.active").each(function(){
			attrValue+=$(this).find("a").text()+":";
		});
		attrValue=attrValue.substring(0,attrValue.length-1);
		if(manager.get("attr")==""){
			manager.add("attr",attrId+"_"+attrValue);
		}else{
			manager.addAttr("attr",attrId,attrValue);
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	}
	
	/**
	 * 参数、品牌：多选(确定按钮点击事件)
	 * */
	$("dl.param .sureAttr").click(function(){
		var id=$(this).attr("id");
		if("brand"==id){
			brandSure();
			return;
		}
		attrSure($(this));
	});
	
	/**
	 * 移除搜索参数
	 * */
	$(".removeParam").click(function(){
		var k=$(this).attr("id");
		if("attr"==k){
			var paramId=$(this).attr("paramId");
			manager.delAttr("attr",paramId);
		}else{
			manager.del(k);
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		window.location.href = url;
	});
	
    /**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
	
	/**
	 * 搜索结果中分类的hover事件
	 * */
	$("#navul > li").not(".navhome").hover(function(){
		$(this).addClass("navmoon")
	},function(){
		$(this).removeClass("navmoon")
	});
	
	/**
	 * 商品小图片的hover事件
	 * */
	$(".go-color img").hover(function(){
		$(this).parent().find(".cur").removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().siblings(".p-picture").find("img").attr("src",ctxfs+$(this).attr("src1")+"@220X220");
	});
});

$(document).ready(function(){
	  $("#search-list-box").children("dl").slice(5).css({"display":"none"});
  
	  $(".search-box-show").click(function(){
		 $(this).css({"display":"none"});
		 $(".search-box-hide").css({"display":"block"});
	 	 $("#search-list-box").children("dl").slice(5).css({"display":"block"});
	  });

	  $(".search-box-hide").click(function(){
		 $(this).css({"display":"none"});
		 $(".search-box-show").css({"display":"block"});
	 	 $("#search-list-box").children("dl").slice(5).css({"display":"none"});
	  });

});


$(document).ready(function(){
  $(".goods-pic-list li").mouseover(function(){
    $(this).addClass("cur");
  });
  $(".goods-pic-list li").mouseout(function(){
    $(this).removeClass("cur");
  });
});



