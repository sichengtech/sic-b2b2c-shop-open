$(function(){
	//删除商品
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	
	//商品下架
	$(".editShelf").click(function(){
		var arr=new Array();
		$(".productCheck:checked").each(function(){
			var id=$(this).val();
			arr.push(id);//向数组中放入ID
		});
		if(arr.length==0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要操作的行'),2000);
			return false;
		}
		//type:1上架，2下架
		var type=$(this).attr("shelfType");
		var href=$(this).attr("href")+"&type="+type+"&ids="+arr;
		var msg="";
		if(type == "1"){
			msg=fy.getMsg('上架');
		}else{
			msg=fy.getMsg('下架');
		}
		fdp.confirm(fy.getMsg('确定要')+msg+fy.getMsg('么？'),href); 
	});
	
	//橱窗推荐、取消推荐
	$(".recommend").click(function(){
		var arr=new Array();
		$(".productCheck:checked").each(function(){
			var id=$(this).val();
			arr.push(id);//向数组中放入ID
		});
		if(arr.length==0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要操作的行'),2000);
			return false;
		}
		//type:1推荐，2取消推荐
		var type=$(this).attr("recommendType");
		var href=$(this).attr("href")+"&type="+type+"&ids="+arr;
		var msg="";
		if(type == "1"){
			msg=fy.getMsg('推荐');
		}else{
			msg=fy.getMsg('取消推荐');
		}
		fdp.confirm(fy.getMsg('确定要')+msg+fy.getMsg('么？'),href); 
	});
	
	//查看规格
	$(".showSpec").click(function(){
		var productId=$(this).attr("productId");
		var productName=$(this).attr("productName");
		var myTitle=fy.getMsg('查看（')+productName+fy.getMsg('）的规格');
		var myRemoteUrl=ctxs+"/product/productSpu/showSpec.htm?id="+productId;
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
	
	//全选：全选控制每行记录
	$(".productCheckAll").click(function(){
		if ($(this).prop("checked")) {
			$(".productCheck").prop("checked", true);
			$(".productCheckAll").not(this).prop("checked", true);
		}else {
			$(".productCheck").prop("checked", false);
			$(".productCheckAll").not(this).prop("checked", false);
		} 
	});
	 
	//每行记录控制全选
	//市级地区反控省级地区和大区
	$(".productCheck").click(function(){
		if ($(this).prop("checked")) {
			var bl1=true;
			var bl2=true;
			//找到其他行记录
			$(".productCheck").not(this).each(function(){
				if (!$(this).prop("checked")){
					bl1=false;
				}
			});
			if(bl1){
				$(".productCheckAll").prop("checked",true);
			}
		}else {
			$(".productCheckAll").prop("checked",false);
		} 
	});
	
});