//文件异步上传
$(function(){
	//给file控件挂上传事件
	$('.fileUploadClass').change(ajaxFileUpload_init);
	
	//移除图片
	$(".uploadCloseBtn").click(function(){
		var attrId = $(this).parent().attr('id');
		var attrIds = attrId.split("_");
		$(this).parent().find(".imgPath").val("");//清除图片地址隐藏域
		var existSize = $('.existSize_' + attrIds[1]).val();
		var size = parseInt(existSize);
		$('.existSize_' + attrIds[1]).val(size-1);//改变已上传图片数量
		$(this).parent().find(".preview").attr("src","");
		$(this).parent().find(".result").html("");
		$(this).parent().css("display","none");
		clearFile(attrIds[1]);
	});
});

//初始化、验证、取token
function ajaxFileUpload_init() {
	var suffix = $(this).attr("suffix"); 						//获取id上传的后缀名
	if(suffix==null || suffix=='' || suffix==undefined){
        //翻译
        var fyInfo1=fy.getMsg("缺少参数,后缀");
        if(typeof(fyInfo1)=="undefined" || fyInfo1=='' || fyInfo1==null){
        	fyInfo1="缺少参数:后缀";
        }
		fdp.msg(fyInfo1);
		return false;
	}
	var allowType = $(this).attr("allowType");					//获取上传文件的扩展名
	if(allowType==null || allowType=='' || allowType==undefined){
        //翻译
        var fyInfo2=fy.getMsg("缺少参数,扩展名");
        if(typeof(fyInfo2)=="undefined" || fyInfo2=='' || fyInfo2==null){
        	fyInfo2="缺少参数:扩展名";
        }
		fdp.msg(fyInfo2);
		return false;
	}
	var fileSize = $(this).attr("fileSize");					//获取上传的文件上传大小
	if(fileSize==null || fileSize=='' || fileSize==undefined){
        //翻译
        var fyInfo3=fy.getMsg("缺少参数,文件大小");
        if(typeof(fyInfo3)=="undefined" || fyInfo3=='' || fyInfo3==null){
        	fyInfo3="缺少参数:文件大小";
        }
		fdp.msg(fyInfo3);
		return false;
	}
	var tokenPath = $(this).attr("tokenPath");					//获取token的地址
	if(tokenPath==null || tokenPath=='' || tokenPath==undefined){
        //翻译
        var fyInfo4=fy.getMsg("缺少参数,获取token地址");
        if(typeof(fyInfo4)=="undefined" || fyInfo4=='' || fyInfo4==null){
        	fyInfo4="缺少参数:获取token地址";
        }
		fdp.msg(fyInfo4);
		return false;
	}
	var thumbnail = $(this).attr("thumbnail");					//截取图片大小
	if(thumbnail==null || thumbnail=='' || thumbnail==undefined){
        //翻译
        var fyInfo5=fy.getMsg("缺少参数,截图尺寸");
        if(typeof(fyInfo5)=="undefined" || fyInfo5=='' || fyInfo5==null){
        	fyInfo5="缺少参数:截图尺寸";
        }
		fdp.msg(fyInfo5);
		return false;
	}
	var existSizeSuffix = $(".existSize_"+suffix).val();		//已上传图片数量
	if(existSizeSuffix==null || existSizeSuffix=='' || existSizeSuffix==undefined){
        //翻译
        var fyInfo6=fy.getMsg("缺少参数,已上传图片数量");
        if(typeof(fyInfo6)=="undefined" || fyInfo6=='' || fyInfo6==null){
        	fyInfo6="缺少参数:已上传图片数量";
        }
		fdp.msg(fyInfo6);
		return false;
	}
	var imageCount = $(this).attr("imageCount");				//允许图片数量(上传数量默认为1)
	if(imageCount==null || imageCount=='' || imageCount==undefined){
		imageCount = 1;
	}
	var accessKey = $(this).attr("accessKey");					//访问图片的accessKey
	var token=null;							
	$.ajax({													//ajax获取token							
		  type: 'post',
		  url: tokenPath,
		  dataType: 'json',
		  success: function(data){
			  token=data.token;
			  ajaxFileUpload(suffix,allowType,fileSize,thumbnail,existSizeSuffix,imageCount,token,accessKey);
		  },
		  error: function(data){
			  return false;
		  }
	});
}

//清理input type="file" 的值
function clearFile(suffix){
	$('#fileUpload_'+suffix).val("");
}

function ajaxFileUpload (suffix,allowType,fileSize,thumbnail,existSizeSuffix,imageCount,token,accessKey){
	var data={format:'json','__fileUpload':'1','token':token};
	if(accessKey!=null){
		data.isSafe='true';
	}
	$.ajaxFileUpload({
		url : ctxu + "/common/ajaxfileupload.htm",		// 默认为post请求
		secureuri : false, 								// 是否启用安全提交,默认为false
		fileElementId:'fileUpload_' + suffix ,			//文件选择框的id属性
		method:'post',									//文件post提交
		data:data,										//告诉服务器请给我返回json格式的数据，spring mvc内容协商支持本参数
		dataType:'json',								//本组件按什么格式接收服务器返回的数据,可以是json或xml等,空表示直接返回data
		fileSize:fileSize,								//文件大小限制，可选，0 为无限制(IE浏览器不兼容) 
		allowType:allowType,							//可选，限定上传文件的格式
		success : function(data) {
			if(data[0].errMsg){
				$('.result_' + suffix).html(data[0].errMsg);
			}else if(data[0].errorToken!=null || data[0].errorToken!=null || data[0].errorToken!=undefined){
				$('.result_' + suffix).html(data[0].errorToken);
			}else{
				for(var i = 0;i < data.length;i++){
					if(data[i].fileStorageName !=null && data[i].fileStorageName !="" && data[i].fileStorageName !=undefined){
						for(var j = 0;j < imageCount;j++){
							var size = $('.existSize_' + suffix).val();//取已上传图片数量
							size = parseInt(size);
							if(size >= imageCount){
								clearFile(suffix);
						        //翻译
						        var fyInfo7=fy.getMsg("超出最多上传个数",imageCount);
						        if(typeof(fyInfo7)=="undefined" || fyInfo7=='' || fyInfo7==null){
						        	fyInfo7="最多只能上传"+imageCount+"张";
						        }
								fdp.msg(fyInfo7);
								return;
							}
							var imgpath = $('#img_'+ suffix +'_'+ j).find(".imgPath").val();
							if(imgpath==null || imgpath=='' || imgpath==undefined){
								$('#img_'+ suffix +'_'+ j).find(".result").html(data[i].errorMsg);//文件上传后的提示语
								$('#img_'+ suffix +'_'+ j).find(".preview").attr("src",ctxfs+data[i].fileStorageName+"@"+thumbnail+"?accessKey="+accessKey);//给img标签赋图片地址
								$('#img_'+ suffix +'_'+ j).find(".imgPath").val(data[i].fileStorageName);//给图片地址隐藏域赋值
								$('#img_'+ suffix +'_'+ j).find(".preview").css("display","");
								$('#img_'+ suffix +'_'+ j).css("display","");
								$('.existSize_' + suffix).val(1 + size);//改变已上传图片数量
								$('#img_'+ suffix +'_'+ j).find(".big_img_url").attr("href",ctxfs+data[i].fileStorageName+"?accessKey="+accessKey);//放大图片的链接
								break;
							}
						}
					}else{
						$('#img_'+ suffix +'_'+ j).find(".preview").css("display","");
						$('#img_'+ suffix +'_'+ j).find(".result").html(data[i].errorMsg);//上传图片的错误提示
					}
				}
			}
			clearFile(suffix); 
		},
		error : function(data) {
			// 服务器响应失败时的处理函数
	        //翻译
	        var fyInfo8=fy.getMsg("上传失败");
	        if(typeof(fyInfo8)=="undefined" || fyInfo8=='' || fyInfo8==null){
	        	fyInfo8="上传失败";
	        }
			$('.result_' + suffix).html(fyInfo8);	
		}
	});
}