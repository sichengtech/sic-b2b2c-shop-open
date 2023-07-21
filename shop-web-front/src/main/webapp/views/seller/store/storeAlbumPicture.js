$(document).ready(function() {
	/**
	 * 图片放大控件的使用
	 */
	function magnificPopup(){
		$('.gallery').each(function() { // 你所有的画廊的容器
			$('.gallery').magnificPopup({
				delegate : 'a', // 画廊项目的选择器
				type : 'image',
				gallery : {
					enabled : true
				}
			});
		});
	};
	magnificPopup();
	
	/**
	 * 显示图片列表
	 */
	function showPicList(albumId){
		var pageNo=$("input[name='pageNo']").val();
		var url=ctxs+"/store/storeAlbumPicture/switch.htm?albumId="+albumId+"&pageNo="+pageNo;
		$.ajax({
			url: url,
			type:'post',
			data: '',
			dataType: 'html',
			success: function(data){
				//全选按钮置为可点击
				$(".selectAll").attr("ischecked","0");
				if($(data).find(".gallery li").length == 0){
					 $(".change").html("<p class='noImgTip'>"+fy.getMsg('对不起，您还没有上传图片，请上传图片！')+"</p>");
				}else{
					 $(".change").html(data);
					//更新空间容量
					var pictureSpaceM = $(data).find(".pictureSpaceM").val();
					var pictureSpace = $(data).find(".pictureSpace").val();
					var totalSpace = $(data).find(".totalSpace").val();
					$("#storePictureSpaceM").html(pictureSpaceM);
					$("#storePictureSpace").css("width",pictureSpace/totalSpace*100+"%");
					//绑定图片放大事件
					magnificPopup();
				 }
			}
		});
	}
	var albumId=$("input[name='albumId']").val();
	showPicList(albumId);
	
	/**
	 * 切换相册变动右边的图片
	 */
	$(".switch").click(function(){
		var albumId=$(this).attr("albumId");
		$(this).parent().parent().find("a").attr("class","switch");
		$(this).attr("class","active switch");
		$("input[name=albumId]").val(albumId);
		showPicList(albumId);
	});

	/**
	 * hover移进，移出，加蓝框
	 */
	$("#fun").delegate(".noHover","mouseenter",function(){
		 $(this).attr("class","hover");
	});
	
	$("#fun").delegate(".hover","mouseleave",function(){
		$(this).attr("class","noHover");
	});

	/**
	 * 选中与取消2
	 */
	$("#fun").delegate(".icon-pc-right","click",function(){
		if($(this).attr("class")=="sui-icon icon-pc-right"){
			$(this).css("display","block");
			$(this).addClass("active");
		}else{
			$(this).removeClass("active");
		}
	});
	
	/**
	 * 全选
	 */
	$(".selectAll").click(function(){
		var checked=$(this).attr("isChecked");
		if("0"==checked){
			$(".gallery .icon-pc-right").addClass("active");
			$(this).attr("isChecked","1");
		}else{
			$(".gallery .icon-pc-right").removeClass("active");
			$(this).attr("isChecked","0");
		}
	});
	
	
	/**
	 * 移动照片到相册(单一图片)
	 */
	$("#fun").delegate(".mobile","click",function(){
		var pictureId = new Array();
		pictureId.push($(this).parent().siblings('.pictureId').val());
		var albumId=$("input[name='albumId']").val();
		if(pictureId.length==0){
			fdp.msg(fy.getMsg('请选择需要移动的图片'),3000);
			return false;
		}
		if(albumId==null || typeof(albumId)=="undefined"){
			fdp.msg(fy.getMsg('相册不能为空'),3000);
			return false;
		}
		removePicture1(pictureId,albumId,1);
	});
	
	/**
	 * 移动照片到相册(多个图片)
	 */
	$("#fun").delegate(".mobileBatch","click",function(){
		var selectedPicLen =$(".gallery .active").length;
		if(selectedPicLen == 0){
			fdp.msg(fy.getMsg('请选择需要移动的图片'),3000);
			return false;
		}
		var pictureIds = new Array();
		$(".gallery .active").each(function(){
			pictureIds.push($(this).parent().parent().find("input[class='pictureId']").val());
		});
		var albumId=$("input[name='albumId']").val();
		if(albumId==null || typeof(albumId)=="undefined"){
			fdp.msg(fy.getMsg('相册不能为空'),3000);
			return false;
		}
		var picCount=pictureIds.length;
		removePicture1(pictureIds,albumId,picCount);
	});
	
	/**
	 * 移动图片-打开相册
	 * pictureId 一个或多个图片id，多个图片id之间有逗号分割
	 * albumId 相册id
	 * picCount 移动图片数量
	 */
	function removePicture1(pictureId,albumId,picCount){
		if(pictureId==null || typeof(pictureId)=="undefined"){
			fdp.msg(fy.getMsg('请选择需要移动的图片'),3000);
			return false;
		}
		if(albumId==null || typeof(albumId)=="undefined"){
			fdp.msg(fy.getMsg('相册不能为空'),3000);
			return false;
		}
		if(picCount==null || typeof(picCount)=="undefined"){
			fdp.msg(fy.getMsg('图片数量不能为空'),3000);
			return false;
		}
		var url=ctxs+"/store/storeAlbumPicture/albumList.htm";
		$.post(url, {}, function(str){
			layer.open({
				type: 1,
				title:' <i class="fa fa-search"></i> 选择目标相册',
				area: ['300px', '400px'],
				shadeClose: true, //点击遮罩关闭
				content: str, //注意，如果str是object，那么需要字符拼接。
				btn:[fy.getMsg('确定'),fy.getMsg('取消')],
				success: function(layero, index){
					//弹出相册页面点击被选中，在点击取消
					$(layero).find(".cli").click(function(){
						$(this).parent().find("li").attr("class","cli");
						$(this).attr("class","cli active");
					});
				},
				yes: function(index, layero){
					var targetAlbum = $(layero).find(".active");
					if(targetAlbum.length ==0){
						fdp.msg(fy.getMsg('请选择相册'),3000);
						return false;
					}
					var targetAlbumId = targetAlbum.children().children().val();
					removePicture2(pictureId,albumId,targetAlbumId,picCount);
					layer.close(index);
				}
			});
		});
	}
	
	/**
	 * 移动照片到相册
	 * pictureId 一个或多个图片id，多个图片id之间有逗号分割
	 * albumId 相册id
	 * targetAlbumId 目标相册
	 * picCount 移动图片数量
	 */
	function removePicture2(pictureId,albumId,targetAlbumId,picCount){
		var url=ctxs+"/store/storeAlbumPicture/moveAlbumPicBatch.htm?pictureId="+pictureId+"&albumId="+albumId+"&targetAlbumId="+targetAlbumId;
		var layerIndex=null;
		$.ajax({
			url: url,
			type:'post',
			data:'',
			dataType: 'json',
			success: function(data){
				if(data==null){
					fdp.msg(fy.getMsg('图片移动失败'),3000);
					return false;
				}
				if(!data.success){
					fdp.msg(data.message,3000);
					return false;
				}
				//移除图片
				/*for(var i=0;i<pictureId.length;i++){
					$(".gallery li[pictureId="+pictureId[i]+"]").remove();
				}*/
				showPicList(albumId);
				//修改相册图片的数量
				var picCountCurrent=parseInt($("a.switch[albumId="+albumId+"] .picCount").text());
				var picCountTarget=parseInt($("a.switch[albumId="+targetAlbumId+"] .picCount").text());
				$("a.switch[albumId="+albumId+"] .picCount").text(picCountCurrent-parseInt(picCount));
				$("a.switch[albumId="+targetAlbumId+"] .picCount").text(picCountTarget+parseInt(picCount));
				fdp.msg(data.message,3000);
			}
		});
	}
	
	/**
	 * 删除单个图片
	 */
	$("#fun").delegate(".deleteSure","click",function(){
		var pictureId = new Array();
		pictureId.push($(this).parent().siblings('.pictureId').val());
		var albumId=$("input[name='albumId']").val();
		if(pictureId==null || typeof(pictureId)=="undefined"){
			fdp.msg(fy.getMsg('请选择需要删除的图片'),3000);
			return false;
		}
		if(albumId==null || typeof(albumId)=="undefined"){
			fdp.msg(fy.getMsg('相册不能为空'),3000);
			return false;
		}
		deletePicture(pictureId,albumId,1);
	});
	
	/**
	 * 批量删除
	 */
	$(".deleteBatch").click(function(){
		var selectedPicLen =$(".gallery .active").length;
		if(selectedPicLen == 0){
			fdp.msg(fy.getMsg('请选择需要删除的图片'),3000);
			return false;
		}
		var pictureIds = new Array();
		$(".gallery .active").each(function(){
			pictureIds.push($(this).parent().parent().find("input[class='pictureId']").val());
		});
		var albumId=$("input[name='albumId']").val();
		if(albumId==null || typeof(albumId)=="undefined"){
			fdp.msg(fy.getMsg('相册不能为空'),3000);
			return false;
		}
		var count=pictureIds.length;
		deletePicture(pictureIds,albumId,count);
	});
	
	/**
	 * 删除图片
	 * pictureId 一个或多个图片id,多个图片id之间用逗号分割
	 * albumId 相册id
	 * picCount 删除图片的数量
	 */
	function deletePicture(pictureId,albumId,picCount){
		fdp.confirm(fy.getMsg('确定要删除么？'),"",function(){
			var url=ctxs+"/store/storeAlbumPicture/deleteBatch.htm?pictureId="+pictureId+"&albumId="+albumId;
			var layerIndex=null;
			$.ajax({
				url: url,
				type:'post',
				data:'',
				dataType: 'json',
				success: function(data){
					if(data==null){
						fdp.msg("删除失败",3000);
						return false;
					}
					if(!data.success){
						fdp.msg(data.message,3000);
						return false;
					}
					//移除图片
					/*for(var i=0;i<pictureId.length;i++){
						$(".gallery li[pictureId="+pictureId[i]+"]").remove();
					}*/
					showPicList(albumId);
					//减少相册图片的数量
					var picCountCurrent=parseInt($("a.switch[albumId="+albumId+"] .picCount").text());
					var picCountNew=picCountCurrent-parseInt(picCount)<0?'0':picCountCurrent-parseInt(picCount);
					$("a.switch[albumId="+albumId+"] .picCount").text(picCountNew);
					//修改相册使用空间
					var pictureSpaceM = data.pictureSpaceM;
					var pictureSpace =  data.pictureSpace;
					var totalSpace =  data.totalSpace;
					$("#storePictureSpaceM").html(pictureSpaceM);
					$("#storePictureSpace").css("width",pictureSpace/totalSpace*100+"%");
					fdp.msg(data.message,3000);
				}
			});
		});
	}
});

