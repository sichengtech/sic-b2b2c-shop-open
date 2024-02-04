$(function(){
	
	/**
	 * 刚进页面，没有库存时，立即购买按钮显示灰色的
	 * */
	var skuStock=$("span.skuStock").text();
	if(skuStock == "0"){
		$(".isHasStock").text(fy.getMsg("库存不足"));
		$(".skuStock").parent().siblings("div").css("display","none");
		$(".buy-btn .addOrder").css("background-color","#d6d6d6");
	}else{
		$(".isHasStock").text("");
		$(".skuStock").parent().siblings("div").css("display","");
		$(".buy-btn .addOrder").css("background-color","#e8410b");
	}
	
	/**
	 * 不同的规格对应不同的库存信息
     * 选择规格,查询库存
     * */
    $(".summary-attrs label").click(function(){
    	var specArray=new Array();
    	var specCurrent=$(this).find("font").text();
    	var specNameCurrent=$(this).parent().prev(".dt").text();
    	$(".summary-attrs .checked").each(function(){
    		if(specNameCurrent ==$(this).parent().prev(".dt").text()){
    			specArray.push(specCurrent);
    		}else{
    			specArray.push($(this).find("font").text());
    		}
    	});
    	if(skuList!=null){
    		for(var i=0;i<skuList.length;i++){
    			var flag=true;
    			for(var j=0;j<specArray.length;j++){
    				var specv="spec"+(j+1)+"V";
    				var sku="";
    				if(specv=="spec1V"){
    					sku=skuList[i].spec1V;
    				}
    				if(specv=="spec2V"){
    					sku=skuList[i].spec2V;
    				}
    				if(specv=="spec3V"){
    					sku=skuList[i].spec3V;
    				}
    				if(sku !=specArray[j]){
    					flag=false;
    				}
    			}
    			if(flag){
    				$(".skuStock").text(skuList[i].stock);
    				$(".skuStock").siblings("input[name='skuId']").val(skuList[i].skuId);
    				$(".skuStock").parent().siblings(".btn").find(".l").attr("stock",skuList[i].stock);
    				$(".skuStock").parent().siblings(".input").find("input").attr("stock",skuList[i].stock);
    				$(".tqm_Pirce").text(skuList[i].price);
    				if(skuList[i].stock == "0"){
    					$(".isHasStock").text("(库存不足)");
    					$(".skuStock").parent().siblings("div").css("display","none");
    					$(".buy-btn .addOrder").css("background-color","#d6d6d6");
    				}else{
    					$(".isHasStock").text("");
    					$(".skuStock").parent().siblings("div").css("display","");
    					$(".buy-btn .addOrder").css("background-color","#e8410b");
    				}
    			}
    		}
    	}
    	//如果当前点击的是颜色，则根据当前选中的颜色更新左侧图片
    	if($(this).find("img").length>0){
    		selectColorSpec($(this).find("img"));
    	}
    });
    
	/**
	 * 图片居中
	 * */
	$(".goods-detailed img").parent().addClass("texCenter");
	
	/**
	 * 根据收货地址查询有没有商品
	*/
	function anyGoods(pid,provinceId,cityId){
		if(provinceId=="" || provinceId==null || cityId=="" || cityId==null){
			$(".sendProduct").text(fy.getMsg("有货"));
			return false;
		}
		$.ajax({
			url:ctxm+"/trade/tradeOrder/calculateFreight.htm",
			type:'POST',
			data:{pid:pid,provinceId:provinceId,cityId:cityId,count:1},
			dataType:'json',
			success:function(data){
				if(data!=null && data!="-1"){
					$(".sendProduct").text(fy.getMsg("有货"));
				}else{
					$(".sendProduct").text(fy.getMsg("无货"));
				}
			},
			error:function(data){
				$(".sendProduct").text(fy.getMsg("有货"));
			}
		});
	}
	
	/**
	 * 刚进页面查询是，如果有收货地址就查库看有没有货
	 * */
	var provinceId=$("input[name='provinceId']").val();
	var cityId=$("input[name='cityId']").val();
	if(provinceId!="" && typeof(provinceId)!="undefined" && cityId!="" && typeof(cityId)!="undefined"){
		anyGoods(pid,provinceId,cityId);
	}
	
	/**
	 * 选择配送地址
	*/
	$("body :not(:has(#summary-freight),#summary-freight)").click(function(e){
		$(".summary-freight span.dd").removeClass("hover");
	 });
	
	$("#summary-freight").click(function(e){
		var tarClass=$(e.target).attr("class");
		if($(e.target).parents(".districtPane").length!=0 || tarClass=="dd" || tarClass=="sendProduct" || tarClass=="dt logistics"){
			$(".summary-freight span.dd").removeClass("hover");
		}else{
			$(".summary-freight span.dd").addClass("hover");
		}
	 });
	
	/**
	 * 地区三级联动
	 * 根据省id查询市
	*/
	$(".areaMsg .provincePane ul li").click(function(){
		
		selectArea(this,"province","city");
	});
	
	/**
	 * 地区三级联动
	 * 根据市id查询县
	*/
	$(".areaMsg .cityPane ul").delegate("li","click",function(){
		selectArea(this,"city","district");
	});
	
	/**
	 * 地区三级联动
	 * 选择收货地址
	*/
	$(".areaMsg .districtPane ul").delegate("li","click",function(){
		var provinceName=$(".provinceTab a").text();
		var cityName=$(".cityTab a").text();
		var districtName=$(this).find("a").text();
		var provinceId=$(".provinceTab input").val();
		var cityId=$(".cityTab input").val();
		var districtId=$(this).attr("id");
		var pId=$(".sendAddress").attr("pId");
		$(".adds .sendAddress span").text(provinceName+" "+cityName+" "+districtName);
		$(".adds .sendAddress .provinceId").val(provinceId);
		$(".adds .sendAddress .cityId").val(cityId);
		$(".adds .sendAddress .districtId").val(districtId);
		$(".districtTab a").text(districtName);
		$(".districtTab input").val(districtId);
		$(".summary-freight span.dd").removeClass("hover");
		anyGoods(pId,provinceId,cityId);
	});
	
	/**
	 * 地区三级联动
	 * 查询当前省下的市,或市下的县
	*/
	function selectArea(e,area1,area2){
		var areaId=$(e).attr("id");
		var areaName=$(e).find("a").text();
		var selectAreaName="select";
		if(area2=="city"){
			selectAreaName=fy.getMsg("市");
		}
		if(area2=="district"){
			selectAreaName=fy.getMsg("县");
		}
		$.ajax({
			url:ctxs + "/sys/area/selectArea.htm",
			type:'POST',
			data:{parentId:areaId},
			dataType:'json',
			success:function(data){
				if(data!=null){
					$(".areaMsg ."+area2+"Pane ul").html("");
					var area_tpl = $("#area_tpl").html();//模板
					for(var i = 0; i < data.length; i++){
						var rowData={"id":data[i].id,"name":data[i].name};//模板的数据
						var city=render(area_tpl,rowData);//渲染模板
						$(".areaMsg ."+area2+"Pane ul").append(city);
					}
					$("."+area1+"Tab").removeClass("active");
					$("."+area2+"Tab").addClass("active");
					$("."+area1+"Pane").removeClass("active");
					$("."+area2+"Pane").addClass("active");
					$("."+area2+"Tab").css("display","block");
					$("."+area2+"Tab a").text(selectAreaName);
					$("."+area1+"Tab a").text(areaName);
					$("."+area1+"Tab input").val(areaId);
				}
			}
		});
	}
	
	/**
	 * 地区三级联动
	 * 点击省级tab,隐藏市和县的tab,
	 * 点击市级tab,隐藏线的tab
	 */
	$(".goods-wl .areaTabs li").click(function(){
		$(this).nextAll("li").css("display","none");
	});
	
	/**
	 * 减数量
	 */
	$(".summary-attrs-1").delegate(".input-number .r","click",function(){
    	var num=parseInt($(this).parents(".input-number").find("input[class='buyNum']").val());
    	var id=$(this).parent().parent().attr("id");
    	var minStock=0;
    	if(id=="retail"){
    		minStock=1;
    	}
    	if(num>minStock){
    		$(this).parents(".input-number").find("input[class='buyNum']").val(num-1);
    	}
    	//选个规格表格中，设置批发价格
    	setSectionPrice($(this));
    });
	
    /**
	 * 加数量
	 */
	$(".summary-attrs-1").delegate(".input-number .l","click",function(){
    	var num=parseInt($(this).parents(".input-number").find("input[class='buyNum']").val());
    	var stock=$(this).attr("stock");
    	if(num<stock){
    		$(this).parents(".input-number").find("input[class='buyNum']").val(num+1);
    	}
    	//选个规格表格中，设置批发价格
    	setSectionPrice($(this));
    });
	
	/**
     * 购买数量
     * 购买数量小于1时默认值为1,大于库存值是置为库存值
     * */
	function countChange(e){
		var num=parseInt($(e).val());
    	var stock=parseInt($(e).attr("stock"));
    	var id=$(e).parents(".input-number").attr("id");
    	var minStock=0;
    	if(id=="retail"){
    		minStock=1;
    	}
    	//如果购买数量不是数字，把购买数量置为最小值
    	var pattern=/^[1-9]\d*$/;
    	var buyNumber=$(e).val();
    	if(!pattern.test($.trim(buyNumber))){
    		$(e).val(minStock);
    	}
    	//如果购买数量小于最小值,把购买数量置为最小值
    	if(num<minStock){
    		$(e).val(minStock);
    	}
    	//如果购买数量大于库存量,把购买数量置为库存量
    	if(num>stock){
    		$(e).val(stock);
    	}
    	//选个规格表格中，设置批发价格
    	setSectionPrice($(e));
	}
	
	$(".summary-attrs-1").delegate(".input-number .buyNum","keyup",function(){
		countChange(this);
    });
    
    /**
     * 设置价格
     * 批发模式下，不同的数量对应不同的价格
    */
    function setSectionPrice(e){
    	var isSection=e.attr("isSection");
    	if("section"== isSection){
    		var num=0;
    		$(".summary-attrs-1 .buyNum").each(function(){
    			num+=parseInt($(this).val());
        	});
    		var price=getSectionPrice(pid,num);
    		$(".summary-list-box-section .sectionPrice").find("span").text(price);
    	}
    }
    
    /**
	 * 立即购买
	 */
    $(".buyNow").click(function(){
    	var status = usm.isLogin();//usm-判断用户是否登录
		if(status == false){
	    	layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '390px'],
			  content:ctxf+"/custom/login.htm"
			});
    	}else{
    		var userSellerId=$("input[name='userSellerId']").val();
    		var userMemberId=$("input[name='userMemberId']").val();
    		if(userSellerId== userMemberId){
    			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("不能购买自己店铺的商品"),2000);
    			return false;
    		}
	    	var pId=$("input[name='pId']").val();
	    	if(pId == "" || typeof(pId)=="undefined"){
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("请选择要购买的商品"),2000);
				return false;
			}
	    	var type=$(this).attr("type");
	    	//type==1:零售，2：批发
	    	var skuArray=new Array();
	    	//购买总数量
	    	var totalNum=0;
	    	if(type=="1"){
	    		var numInput=$("#retail").find("input[class='buyNum']");
	    		var stock=parseInt(numInput.attr("stock"));
	    		if(stock==0){
	    			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("库存不足"),2000);
	    			return false;
	    		}
	    		countChange(numInput);
	    		var skuId=$(".skuStock").siblings("input[name='skuId']").val();
	    		var num=numInput.val();
	    		skuArray.push(skuId+"-"+num);
	    		totalNum+=parseInt(num);
	    	}else{
	    		var isHasStock=false;
	    		$(".summary-attrs-1 .buyNum").each(function(){
	    			var stock=parseInt($(this).attr("stock"));
	    			if(stock==0){
	    				isHasStock=false;
	    			}else{
	    				isHasStock=true;
	    			}
	    			countChange(this);
	    			var num=$(this).val();
	    			totalNum+=parseInt(num);
	    			if(num !=0){
	    				var skuId=$(this).attr("skuId");
	    				skuArray.push(skuId+"-"+num);
	    			}
	    		});
	    		if(!isHasStock){
	    			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("库存不足"),2000);
	    			return false;
	    		}
	    	}
	    	if(skuArray.length==0){
	    		fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("请选择规格数量"),2000);
				return false;
	    	}
	    	//判断购买数量是否小于起购量
    		var purchasingAmount=$("input[name='purchasingAmount']").val();
    		if(purchasingAmount=="" || typeof(purchasingAmount)=="undefined" || totalNum<parseInt(purchasingAmount)){
    			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("购买数量必须大于起购量，起购量是")+purchasingAmount,2000);
    			return false;
    		}
	    	$("input[name='totalNum']").val(totalNum);
	    	$("input[class='skuMsg']").val(skuArray);
	    	$("#productFrom").submit();
    	}
    });
    
    /**
	 * 加入购物车
	 */
    $(".addCart").click(function(){
    	var status = usm.isLogin();//usm-判断用户是否登录
		if(status == false){
	    	layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '390px'],
			  content:ctxf+"/custom/login.htm",
			  //layer层销毁后触发的回调
			  end:function(){
				  var status = usm.isLogin();//usm-判断用户是否登录
				  var purch=usm.isTypeUserPurchaser();//usm-判断用户是否是采购商
				  if(status && purch){
					  location.reload();
				  }
			  }
			});
    	}else{
	    	var pId=$("input[name='pId']").val();
	    	if(pId == "" || typeof(pId)=="undefined"){
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("请选择要加入购物车商品"),2000);
				return false;
			}
	    	var type=$(this).attr("type");
	    	//type==1:零售，2：批发
	    	var skuMsg="";
	    	if(type=="1"){
	    		var numInput=$("#retail").find("input[class='buyNum']");
	    		countChange(numInput);
	    		var skuId=$(".skuStock").siblings("input[name='skuId']").val();
	    		var num=numInput.val();
	    		skuMsg+=skuId+"-"+num+",";
	    	}else{
	    		$(".sectionSpec .buyNum").each(function(){
	    			countChange(this);
	    			var num=$(this).val();
	    			if(num !=0){
	    				var skuId=$(this).attr("skuId");
	    				skuMsg+=skuId+"-"+num+",";
	    			}
	    		});
	    	}
	    	if(skuMsg==""){
	    		fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("请选择规格数量"),2000);
				return false;
	    	}
	    	$.ajax({
				url:ctxf + "/trade/cart/save.htm",
				type:'POST',
				data:{pId:pId,skuMsg:skuMsg,type:type},
				dataType:'json',
				success:function(data){
					if(data==1){
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg("加入购物车成功"),2000);
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("加入购物车失败"),2000);
					}
				}
	    	});
    	}
    });
    
    $(".pagination-large a").each(function(){
    	var href=$(this).attr("href");
    	if(href!="javascript:;"){
    		href+="#comment";
    	}
    	$(this).attr("href",href);
    	$(".pagination-large .comment").val("#comment");
    });
    
    /**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
    
    /**
     * 商品图片鼠标移入事件
     * 
     */
	$(".spec-scroll .items ul li").mouseover(function(){
		$(".spec-scroll .items ul li.cur").removeClass("cur");
		$(this).addClass("cur");
	});
	
	/**
	 * 根据当前选中的颜色显示相应的图片
	 * */
	function selectColorSpec(e){
		$(".spec-scroll .items ul li.cur").removeClass("cur");
		var picId="";
		var color="";
		if(typeof(e)!="undefined"){
			picId=$(e).attr("picId");
			$("#preview img").attr("src",ctxfs+$(e).attr("src1")+"@!438x438");
			$("#preview img").attr("jqimg",ctxfs+$(e).attr("src1"));
			color=$(e).siblings("font").text();
			//只显示当前颜色对应的图片
			$(".spec-scroll .items ul li").each(function(){
				if($(this).find("img").attr("color")!=color){
					$(this).css("display","none");
				}else{
					$(this).css("display","");
				}
			});
		}else{
			$(".specImage").each(function(){
				if($(this).parent().hasClass("checked")){
					picId=$(this).attr("picId");
					$("#preview img").attr("src",ctxfs+$(this).attr("src1"));
					$("#preview img").attr("jqimg",ctxfs+$(e).attr("src1"));
					//color=$(this).siblings("span").text();
				}
			});
		}
		$(".spec-scroll .items ul li img[picId="+picId+"]").parent().addClass("cur");
		//var li=$(".spec-scroll .items ul li img[picId="+picId+"]").parent();
		//li.remove();
		//$(".spec-scroll .items ul").prepend(li);
	}
	
	/**
	 * 刚进页面默认加载当前选中规格的图片
	 * */
	if($("span.specdd label.checked").length>0 && $("span.specdd label.checked").find("img").length>0){
		var img=$("span.specdd label.checked img");
		selectColorSpec(img);
	}
	
	
	/**
	 * 批发模式下，根据数量获取商品价格
	 **/
	function getSectionPrice(pId,count){
		var price="";
		$.ajax({
			url:ctxf+"/method/getSectionPrice.htm",
			type:'post',
			data:{"pId":pId,"count":count},
			dataType:'json',
			async:false,
	        success: function(data) {
	           if(data!=null && data.success && data.price!=null){
	        	   price=data.price;
	           }
	        }
		});
		return price;
	}
	
	/**
	 * 商品详情选项卡点击事件
	 * */
	$('#detail_tabs a').click(function(e){
		var id=$(this).attr("hrefs");
		if("#goods-main" == id){
			$(".detail_tab").show();
		}else{
			$(".detail_tab").hide();
			$(id).show();
		}
		if("#more_param_div" != id){
			$("#more_param_div").hide();
		}else{
			$("#more_param_div").show();
		}
	    $(this).tab('show');
	});
	
	/**
	 * 更多参数点击事件
	 * */
	$(".moreParam").click(function(){
		$('#detail_tabs a').eq(1).click();
	});
	
	/**
	 * 图片放大控件的使用
	 */
	function magnificPopup(){
		$('.comment-pic').each(function() { // 你所有的画廊的容器
			$('.comment-pic').magnificPopup({
				delegate : 'a', // 画廊项目的选择器
				type : 'image',
				gallery : {
					enabled : true
				}
			});
		});
	}
	magnificPopup();
	
	/**
	 * 商品小图片的鼠标移入事件
	 * 鼠标移进去时显示当前图片
	 * */
	$(".spec-scroll ul img").mousemove(function(){
		preview(this);
	});
	
	function preview(img){
		$("#preview .jqzoom img").attr("src",$(img).attr("bimg")+"@!438x438");
		$("#preview .jqzoom img").attr("jqimg",$(img).attr("bimg")+"@!438x438");
		$(img).add("class","cur");
	}

	/**
	 * 图片放大镜效果
	 * */
	$(function(){
		$(".jqzoom").jqueryzoom({xzoom:525,yzoom:525});
	});

	/**
	 * 商品小图片的轮播效果
	 */
	var tempLength = 0; //临时变量,当前移动的长度
	var viewNum = 3; //设置每次显示图片的个数量
	var moveNum = 2; //每次移动的数量
	var moveTime = 300; //移动速度,毫秒
	var scrollDiv = $(".spec-scroll .items ul"); //进行移动动画的容器
	var scrollItems = $(".spec-scroll .items ul li:visible"); //移动容器里的集合
	var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); //计算总长度,总个数*单个长度
		  
	//下一张
	$(".spec-scroll .next").bind("click",function(){
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	//上一张
	$(".spec-scroll .prev").bind("click",function(){
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
	
	/**
	 * 交易保障的hover事件
	 * */
	$(".infoli li").hover(function(){
		$(this).addClass("cur")
	},function(){
		$(this).removeClass("cur")
	});
	
	/**
	 * 批发模式：隐藏规格，显示下拉按钮
	 * */
	if($('.summary-list-box ul').size() > 2){
		var html = '';
		$.each($('.summary-list-box ul:gt(2)'),function(){
			html += $(this).prop("outerHTML");
			$(this).remove();
		});
		$('.summary-list-box').after('<span class="dd summary-list-box-more summary-list-box-section">' + html + '<div class="more show"><a></a></div><div class="more hide" style="display:none;"><a></a></div>' + '</span>')
	}
	
	/**
	 * 批发模式：显示更多的规格
	 * */
	$(".summary-list-box-more .show").click(function() {
		$(this).siblings("ul").addClass("cur");
		$(this).css("display","none");
		$(this).siblings(".hide").css("display","block");
	});
	
	/**
	 * 批发模式：隐藏更多的规格
	 * */
	$(".summary-list-box-more .hide").click(function() {
		$(this).siblings("ul").removeClass("cur");
		$(this).css("display","none");
		$(this).siblings(".show").css("display","block");
	});
	
	/**
	 * 获取商品评价
	 * */
	function getCommentList(pageNo,isFirst){
		var grade=$(".commentPageList .grade").val();
		$.ajax({
			url:ctxf+"/method/tradeComment/list.htm",
			type:'POST',
			data:{"pId":pid,"grade":grade,"pageNo":pageNo,"isFirst":isFirst},
			dataType:'json',
			success:function(data){
				if(data==null || data.page ==null || data.page.list==null){
					//无评价模板
					var commentNoTpl = $("#comment_no_tpl").html();//模板
					var commentNoTplData={"":""};//模板的数据
					var commentNoHtml=render(commentNoTpl,commentNoTplData);//渲染模板
					$("dl.comment-list").html(commentNoHtml);
					return;
				}
				var comentContent="";
				var commentList=data.page.list;
				for(var i=0;i<commentList.length;i++){
					var comment=commentList[i];
					//评论图片
					var commentImgHtml=getCommentImg(comment);
					//评价解释
					var commentExplain=getCommentExplain(comment);
					//追评
					var commentAddContent=getCommentAddContent(comment);
					//追评图片
					var commentAddImgHtml=getCommentImg(comment.tradeCommentAdd);
					//追评解释
					var commentAddExplainHtml="";
					if(comment.tradeCommentAdd!=null && comment.tradeCommentAdd.tradeCommentExplain!=null){
						commentAddExplainHtml=getCommentExplain(comment.tradeCommentAdd.tradeCommentExplain);
					}
					//评价模板
					var commentTpl = $("#comment_tpl").html();//模板
					var loginName="";
					if(comment.userMain!=null){
						loginName=comment.userMain.loginName;
					}
					var commentTplData={"commentScore":comment.productScore,"commentDate":comment.createDate,"commentLoginName":loginName,
							"commentContent":comment.content,"commentImg":commentImgHtml,"commentExplain":commentExplain,
							"commentAdd":commentAddContent,"commentAddImg":commentAddImgHtml,"commentAddExplain":commentAddExplainHtml};//模板的数据
					comentContent+=render(commentTpl,commentTplData);//渲染模板
				}
				$("dl.comment-list").html(comentContent);
				if(isFirst){
					//评论总数
					$(".commentTabs span.commentCount").text(data.count);
					//好评数量
					$(".commentTabs span.commentGrade1Count").text(data.grade1Count);
					//中评数量
					$(".commentTabs span.commentGrade2Count").text(data.grade2Count);
					//差评数量
					$(".commentTabs span.commentGrade3Count").text(data.grade3Count);
				}
				//当前页数
				$(".commentPageList .pageNo").val(data.page.pageNo);
				//上一页
				$(".commentPageList li.prev").attr("pageNo",data.prev);
				//下一页
				$(".commentPageList li.next").attr("pageNo",data.next);
				//尾页
				$(".commentPageList li.last").attr("pageNo",data.last);
				$(".goods-comment .commentPage").show();
				$(".commentPageList .noComment").removeClass("noComment");
				if(data.isFirstPage){
					$(".commentPageList li.prev, .commentPageList li.fist").addClass("noComment");
				}
				if(data.isLastPage){
					$(".commentPageList li.next, .commentPageList li.last").addClass("noComment");
				}
				//图片放大函数
				magnificPopup();
			}
		});
	}
	getCommentList("1",true);
	
	/**
	 * 获取追评
	 * */
	function getCommentAddContent(comment){
		var commentAddContent="";
		if(comment==null){
			return commentAddContent;
		}
		var comentAdd=comment.tradeCommentAdd;
		if(comentAdd!=null){
			//追评模板
			var commentAddTpl = $("#comment_add_tpl").html();//模板
			var commentAddTplData={"commentDate":comentAdd.createDate,"commentAddContent":comentAdd.content};//模板的数据
			commentAddContent=render(commentAddTpl,commentAddTplData);//渲染模板
		}
		return commentAddContent;
	}
	
	/**
	 * 获取评价图片
	 * */
	function getCommentImg(comment){
		var commentImgHtml="";
		if(comment==null){
			return commentImgHtml;
		}
		if(comment.tradeCommentImageList!=null && comment.tradeCommentImageList.length>0){
			var commentImgTpl = $("#comment_img_tpl").html();//模板
			for(var j=0;j<comment.tradeCommentImageList.length;j++){
				var commentImg=comment.tradeCommentImageList[j];
				//评价图片模板
				var commentImgTplData={"path":ctxfs+commentImg.path};//模板的数据
				commentImgHtml+=render(commentImgTpl,commentImgTplData);//渲染模板
			}
		}
		return commentImgHtml;
	}
	
	/**
	 * 获取评价解释
	 * */
	function getCommentExplain(comment){
		var commentExplain="";
		if(comment==null){
			return commentExplain;
		}
		if(comment.tradeCommentExplain!=null){
			//评价解释模板
			var commentExplainTpl = $("#comment_explain_tpl").html();//模板
			var commentExplainTplData={"commentExplain":comment.tradeCommentExplain.content};//模板的数据
			commentExplain=render(commentExplainTpl,commentExplainTplData);//渲染模板
		}
		return commentExplain;
	}
	
	/**
	 * 评价分页点击事件
	 * */
	$(".commentPageList li").click(function(){
		if($(this).hasClass("noComment")){
			return false;
		}
		var pageNo=$(this).attr("pageNo");
		getCommentList(pageNo,false);
	});
	
	/**
     * 商品评价点击事件（好评、中评、差评）
     */
    $(".commentTabs li").click(function(){
    	var grade=$(this).attr("grade");
    	$(".commentPageList .grade").val(grade);
    	getCommentList("1",false);
    	$(".commentTabs li.active").removeClass("active");
    	$(this).addClass("active");
    });
	
	/**
	 * 获取商品咨询
	 * */
	function getConsultationList(pageNo,isFirst){
		var category=$(".askPageList .category").val();
		$.ajax({
			url:ctxf+"/method/tradeConsultation/list.htm",
			type:'POST',
			data:{"pId":pid,"category":category,"pageNo":pageNo,"isFirst":isFirst},
			dataType:'json',
			success:function(data){
				if(data==null || data.page ==null || data.page.list==null){
					//无评价模板
					var consultationNoTpl = $("#consultation_no_tpl").html();//模板
					var consultationNoTplData={"":""};//模板的数据
					var consultationNoHtml=render(consultationNoTpl,consultationNoTplData);//渲染模板
					$("dd.goods-ask-list").html(consultationNoHtml);
					return;
				}
				var consultationContent="";
				var consultationList=data.page.list;
				for(var i=0;i<consultationList.length;i++){
					var consultation=consultationList[i];
					var answer=fy.getMsg("暂无回答");
					var disabled="disabled";
					//回复咨询
					var replayConsultation=consultation.replyTradeConsultation;
					if(replayConsultation!=null){
						answer=replayConsultation.content;
						disabled="";
					}
					//咨询模板
					var consultationTpl=$("#consultation_tpl").html();//模板
					var loginName="";
					if(consultation.userMain!=null){
						loginName=consultation.userMain.loginName;
					}
					var consultationTplData={"ask":consultation.content,"data":consultation.createDate,
							"userName":loginName,"answer":answer,"disabled":disabled};//模板的数据
					consultationContent+=render(consultationTpl,consultationTplData);//渲染模板
				}
				$("dd.goods-ask-list").html(consultationContent);
				if(isFirst){
					//全部咨询
					$(".askTabs span.askCount").text(data.count);
					//商品咨询
					$(".askTabs span.askCate1Count").text(data.category1Count);
					//支付问题
					$(".askTabs span.askCate2Count").text(data.category2Count);
					//发票及报修
					$(".askTabs span.askCate3Count").text(data.category3Count);
					//促销及赠品
					$(".askTabs span.askCate4Count").text(data.category4Count);
				}
				//当前页数
				$(".askPageList .pageNo").val(data.page.pageNo);
				//上一页
				$(".askPageList li.prev").attr("pageNo",data.prev);
				//下一页
				$(".askPageList li.next").attr("pageNo",data.next);
				//尾页
				$(".commentPageList li.last").attr("pageNo",data.last);
				$(".goods-ask .askPage").show();
				$(".askPageList .noConsultation").removeClass("noConsultation");
				if(data.isFirstPage){
					$(".askPageList li.prev, .askPageList li.fist").addClass("noConsultation");
				}
				if(data.isLastPage){
					$(".askPageList li.next, .askPageList li.last").addClass("noConsultation");
				}
				//图片放大函数
				magnificPopup();
				//第一次加载或发起咨询后加载，默认选中全部咨询
				if(isFirst){
					$(".askTabs li.active").removeClass("active");
					$(".askTabs li").first().addClass("active");
				}
			}
		});
	}
	getConsultationList("1",true);
	
	/**
	 * 我要咨询
	 * */
	$(".btn-ask").click(function(){
		var status = usm.isLogin();//usm-判断用户是否登录
		if(status == false){
	    	layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '390px'],
			  content:ctxf+"/custom/login.htm",
			  //layer层销毁后触发的回调
			  end:function(){
				  var status = usm.isLogin();//usm-判断用户是否登录
				  var purch=usm.isTypeUserPurchaser();//usm-判断用户是否是采购商
				  if(status && purch){
					  location.reload();
				  }
			  }
			});
	    	return false;
    	}
		var content=$("#askModal").html();
		//type:1在列表中的操作，2表示订单详情中的操作
		layer.open({
			type: 1,
			title:'咨询',
			area: ['500px', '300px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find(".askBtn").click(function(){
					if(jsValidate){
						$(layero).find("#askForm").validate({
							rules: {
								"content":{required: true,maxlength:200},
							},
							messages: {
								"content":{required: fy.getMsg("咨询内容不能为空"),maxlength:fy.getMsg("最大长度不能超过200字符"),},
							},
							submitHandler: function(form){
								addAsk(layero,index);
							}
						});
					}else{
						addAsk(layero,index);
					}
				});
			}
		}); 
	});
	
	/**
	 * 发起咨询方法
	 * */
	function addAsk(layero,index){
		var askContent=$(layero).find("textarea[name='content']").val();
		var category=$(layero).find("input[name='category']:checked").val();
		$.ajax({
			url:ctxf+"/method/tradeConsultation/save.htm",
			type:'POST',
			data:{"category":category,"content":askContent,pId:pid,"storeId":storeId},
			dataType:'json',
			success:function(data){
				if(data.success){
					layer.close(index);
					getConsultationList("1",true);
					return;
				}
				var message=fy.getMsg("保存咨询失败");
				if(typeof(data.message)!="undefined" && data.message!=null){
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+data.message,2000);
				}
			}
		});
	}
	
	/**
	 * 咨询分页点击事件
	 * */
	$(".askPageList li").click(function(){
		if($(this).hasClass("noConsultation")){
			return false;
		}
		var pageNo=$(this).attr("pageNo");
		getConsultationList(pageNo,false);
	});
	
	/**
     * 咨询分类点击事件（商品咨询、支付问题、发票及报修、促销及赠品）
     */
    $(".askTabs li").click(function(){
    	var category=$(this).attr("category");
    	$(".askPageList .category").val(category);
    	getConsultationList("1",false);
    	$(".askTabs li.active").removeClass("active");
    	$(this).addClass("active");
    });
});