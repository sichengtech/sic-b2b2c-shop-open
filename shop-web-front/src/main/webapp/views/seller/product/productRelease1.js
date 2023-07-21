var parentId_1 = null;
var parentId_2 = null;
//var tips='输入名称或拼音首字母';
//var style='style="background-image:none!important;"';//清除分类名称右侧的尖括号

$(document).ready(function(){
	 //搜索类目文本框挂事件
	$("#searchInput").keyup(function(){
		var word=$(this).val();
		if(word!=null && word!=""){
			 search(word);
			 switchCate2();
		}else{
			switchCate1();
		}
	});

	 //搜索类目搜索按钮挂事件
	$("#searchButton").click(function(){
		var word=$("#searchInput").val();
		if(word!=null && word!=""){
			search(word);
			switchCate2();
		}else{
			switchCate1();
		}
	});

	//刚进页面1级分类加载
	showCateLevel_1();

	//1级分类在搜索框中搜索
		$("#searchOne").keyup(function(){
			var sOne = $("#searchOne").val();
			//if(""!=sOne && null!=sOne){
				showCateLevel_1(sOne);
			//}
		});
	//2级分类在搜索框中搜索
		$("#searchTwo").keyup(function(){
			var sTwo = $("#searchTwo").val();
			//if(""!=sTwo && null!=sTwo){
				showCateLevel_2(parentId_1,sTwo);
			//}
		});
	//3级分类在搜索框中搜索
		$("#searchThree").keyup(function(){
			var sThree = $("#searchThree").val();
			//if(""!=sThree && null!=sThree){
				showCateLevel_3(parentId_2,sThree);
			//}
		});
});

/**
 * 显示区切换开关
 */
function switchCate(){
	var a=$("#part1").css("display");
	if("none"==a){
		$("#part1").css("display","block");
		$("#part2").css("display","none");
	}else{
		$("#part1").css("display","none")
		$("#part2").css("display","block")
	}
}
/**
 * 显示区切换（跳转搜索页面）
 */
function switchCate1(){
	var a=$("#part1").css("display");
	if("none"==a){
		$("#part1").css("display","block");
		$("#part2").css("display","none");
	}
}
/**
 * 显示区切换（跳转搜索页面）
 */
function switchCate2(){
	var a=$("#part2").css("display");
	if("none"==a){
		$("#part1").css("display","none")
		$("#part2").css("display","block")
	}
}

/*
 * 取一级分类
 */
function getCategoryLevel_1() {
	return getCategoryLevel(0);
}

/*
 * 取二级分类
 */
function getCategoryLevel_2(parentId) {
	return getCategoryLevel(parentId);
}
/*
 * 取三级分类
 */
function getCategoryLevel_3(parentId) {
	return getCategoryLevel(parentId);
}


/*
 * 根据参数取分类
 */
function getCategoryLevel(parentId) {
	var array=[];
	for (var i = 0; i < arr.length; i++) {
		if (typeof(arr[i])!="undefined" && typeof(arr[i].parent_id)!="undefined" && arr[i].parent_id == parentId) {
			array.push(arr[i]);
		}
	}
	return array;
}

/**
 * 刚进入页面一级分类显示
 */
function showCateLevel_1(word){
	$("#cate_level_1").html("");
	selectCategroy("");
	var array = getCategoryLevel_1();
	var h="";
	for (var i = 0; i < array.length; i++) {
		var names=array[i].name;
		var ids=array[i].id;
		var pinyin=array[i].pinyin;
		//$("#cate_level_1").html("");
		if(word!=null && word!=''){
			if(names.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 && pinyin.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 ){
				continue;
			}
			names=names.replace(word,"<span style='color:red'>"+word+"</span>")
		}
		var hasChild=true;
		if(getCategoryLevel_2(ids).length==0){ hasChild=false; }
		var rowData={"id":ids,"name":names,"hasChild":hasChild};//模板的数据
		var cate_tpl_1=$("#cate_tpl_1").html();
		var s=render(cate_tpl_1,rowData)//渲染模板
		//var s='<p><a href="javascript:;" id="'+ids+'" style="width:177px;display:block;" onclick="showCateLevel_2(\''+ids+'\')">'+names+'</a></p>';
		h=h+s+"\n";
	}
	
	$("#cate_level_1").append(h);
	//1级分类蓝色高亮
	$('#cate_level_1 a').click(function() {
		$('#cate_level_1 a').each(function() {
			$(this).css('background-color', '');
			$(this).css('border', '');
		});
		$(this).css('background-color', '#ffeebd');
		$(this).css('border', '1px solid #fcb187');
	});

}

/**
 * 点击1级分类出现的二级分类
 * @param id
 */
function showCateLevel_2(id,word){
	$(".currentCate").html($("#"+id).text());
	parentId_1=id;
	$("#cate_level_2").html("");
	var array = getCategoryLevel_2(id);
	if(array.length==0){
		selectCategroy(id);
	}else{
		selectCategroy("");
		var h="";
		for (var i = 0; i < array.length; i++) {
			var names=array[i].name;
			var ids=array[i].id;
			var pinyin=array[i].pinyin;
			//$("#cate_level_2").html("");
			if(word!=null && word!=''){
				if(names.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 && pinyin.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 ){
					continue;
				}
				names=names.replace(word,"<span style='color:red'>"+word+"</span>")
			}
			//判断是否显示分类名称右侧的尖括号
			//var innerStyle="";
			var hasChild=true;
			if(getCategoryLevel_3(ids).length==0){ hasChild=false; }
			var rowData={"id":ids,"name":names,"hasChild":hasChild};//模板的数据
			var cate_tpl_2=$("#cate_tpl_2").html();
			var s=render(cate_tpl_2,rowData)//渲染模板
			//var s='<p '+innerStyle+'><a href="javascript:;" id="'+ids+'" style="width:177px;display:block;" onclick="showCateLevel_3(\''+ids+'\')">'+names+'</a></p>';
			h=h+s+"\n";
		}
		$("#cate_level_2").append(h);
		//2级分类按名称和首字母搜索完点击高亮
			$('#cate_level_2 a').click(function() {
				$('#cate_level_2 a').each(function() {
					$(this).css('background-color', '');
					$(this).css('border', '');
				});
				$(this).css('background-color', '#ffeebd');
				$(this).css('border', '1px solid #fcb187');
			});
	}
}

/**
 * 点击2级分类出现三级分类
 * @param id
 */
function showCateLevel_3(id,word){
	var cate=$(".currentCate").html();
	if(cate.indexOf('&gt') != -1){
		cate=cate.substring(0,cate.indexOf('&gt'));
	}
	$(".currentCate").html(cate+"&gt"+$("#"+id).text());
	parentId_2=id;
	$("#cate_level_3").html("");
	var array = getCategoryLevel_3(id);
	if(array.length==0){
		selectCategroy(id);
//		$("#cate_level_3").css("background","");
	}else{
		$("#product_category_id").val("");
		var h="";
		for (var i = 0; i < array.length; i++) {
			var names=array[i].name;
			var ids=array[i].id;
			var pinyin=array[i].pinyin;
			//$("#cate_level_3").html("");
			if(word!=null && word!=''){
				if(names.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 && pinyin.toUpperCase( ).indexOf(word.toUpperCase( )) == -1 ){
					continue;
				}
				names=names.replace(word,"<span style='color:red'>"+word+"</span>")
			}
			var rowData={"id":ids,"name":names,"hasChild":false};//模板的数据
			var cate_tpl_3=$("#cate_tpl_3").html();
			var s=render(cate_tpl_3,rowData)//渲染模板
			//var s='<p '+style+'><a href="javascript:selectCategroy(\''+ids+'\');" id="'+ids+'" style="width:177px;display:block;">'+names+'</a></p>';
			h=h+s+"\n";
		}
		$("#cate_level_3").append(h);
		//3级分类按名称和首字母搜索完点击高亮
			$('#cate_level_3 a').click(function() {
				$('#cate_level_3 a').each(function() {
					$(this).css('background-color', '');
					$(this).css('border', '');
				});
				$(this).css('background-color', '#ffeebd');
				$(this).css('border', '1px solid #fcb187');
			});
	}
}

/*
 * 搜索的索引，存放预先处理好的二维数据
 */
var searchIndex=null;

/*
 * 初始化搜索的索引
 */
function initSearchIndex() {
	var arr1 = getCategoryLevel_1();
	var arrRow=[];
	for (var i = 0; i < arr1.length; i++) {
		var category_l1=arr1[i];
		var arr2 = getCategoryLevel_2(category_l1.id);
		if(arr2.length==0){
			//如果没有2级分类
			var row=category_l1.name;
			var _ids=category_l1.id;
			var _pinyin=category_l1.pinyin;
			arrRow.push({names:row,ids:_ids,pinyin:_pinyin});
		}else{
			//如果有2级分类
			for (var j = 0; j < arr2.length; j++) {
				var category_l2=arr2[j];
				var arr3 = getCategoryLevel_3(category_l2.id);
				if(arr3.length==0){
					//如果没有3级分类
					var row=category_l1.name+" > "+category_l2.name;
					var _ids=category_l1.id+","+category_l2.id;
					var _pinyin=category_l1.pinyin+" > "+category_l2.pinyin;
					arrRow.push({names:row,ids:_ids,pinyin:_pinyin});
				}else{
					//如果有3级分类
					for (var k = 0; k < arr3.length; k++) {
						var category_l3=arr3[k];
						var row=category_l1.name+" > "+category_l2.name+" > "+category_l3.name;
						var _ids=category_l1.id+","+category_l2.id+","+category_l3.id;
						var _pinyin=category_l1.pinyin+" > "+category_l2.pinyin+" > "+category_l3.pinyin;
						arrRow.push({names:row,ids:_ids,pinyin:_pinyin});
					}
				}
			}
		}
	}
	return arrRow;
}

/*
 * 搜索并显示结果
 */
function search(word){
	if(searchIndex==null ){
		searchIndex=initSearchIndex();
	}
	$("#filter").html("");
	var html="";
	for (var i = 0; i < searchIndex.length; i++) {
		var names=searchIndex[i].names;
		var ids=searchIndex[i].ids;
		var pinyin=searchIndex[i].pinyin;
		if(names.toUpperCase( ).indexOf(word.toUpperCase( )) != -1 || pinyin.toUpperCase( ).indexOf(word.toUpperCase( )) != -1){
			names=names.replace(word,"<span style='color:red'>"+word+"</span>")
			var rowData={"id":ids,"name":names};//模板的数据
			var cate_tpl_4=$("#cate_tpl_4").html();
			var s=render(cate_tpl_4,rowData)//渲染模板
			//var s='<p><input id="Category" name="Category" type="radio" value="'+ids+'" /><a href="javascript:;" onclick="selectRow(\''+ids+'\')">'+names+'</a></p>';
			html=html+s+"\n";
		}
	}
	$("#filter").append(html);
	$('#filter a').click(function() {
		$('#filter a').each(function() {
			$(this).css('background-color', '');
			$(this).css('border', '');
		});
		$(this).css('background-color', '#ffeebd');
		$(this).css('border', '1px solid #fcb187');
	});
}

/*
 * 选中一个未级分类
 */
function selectCategroy(id,cate_tpl_3){
	$("#product_category_id").val(id);
	var cate=$(".currentCate").html();
	if(cate_tpl_3 == 3){
		var count = cate.split("&gt").length-1;
		if(count == 2){
			cate=cate.substring(0,cate.lastIndexOf('&gt'));
		}
		$(".currentCate").html(cate+"&gt"+$("#"+id).text());
	}
}

/*
 * 选中一个搜索结果
 */
function selectRow(ids){
	var strs= new Array();
	strs=ids.split(",");
	if(strs.length==3){
		$("#product_category_id").val(strs[2]);
	}
	if(strs.length==2){
		$("#product_category_id").val(strs[1]);
	}
	if(strs.length==1){
		$("#product_category_id").val(str[0]);
	}
	var event=getEvent();
	var obj;
	if(event.target){
		obj=event.target;//通过事件对象获取事件源（FF）
	}else{
		obj=e.srcElement;// IE
	}
	var p=$(obj).prev();
	p.attr("checked","checked");
}
/*
 * 获取事件对象
 * @returns
 */
function getEvent(){
	if(window.event)
	{
		return window.event;
	}
	var f=getEvent.caller;
	while(f!=null)
	{
		var e = f.arguments[0];
		if(e && (e.constructor==MouseEvent||e.constructor==Event||e.constructor==KeyboardEvent)) return e;
		f=f.caller;
	}
}

/**
 * 渲染模板
 */
function render(tpl,data){
	return laytpl(tpl).render(data);
}

/**
 * 表单验证
 * 第一步 选择商品分类，的表单验证
 */
$("#productReleaesStep1").submit(function(){
	var id=$("#product_category_id").val();
	if(id==null || id==""){
		fdp.msg(fy.getMsg('请选择商品的分类'));
		return false;
	}
	setTimeout(function(){
		//如果1.5秒内未完成操作，则给出请等待的提示
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
	},1500);
	return true;
});
