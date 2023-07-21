// 图片上传demo
jQuery(function() {
	var $ = jQuery,
    $list = $('#fileList'),
    // 优化retina, 在retina下这个值是2
    ratio = window.devicePixelRatio || 1,
    // 缩略图大小
    thumbnailWidth = 135 * ratio,
    thumbnailHeight = 135 * ratio,
    // Web Uploader实例
    uploader;
	if ( !WebUploader.Uploader.support() ) {
    	fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> Web Uploader "+fy.getMsg('不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器'),3000);
    }
    // 初始化Web Uploader实例
    uploader = WebUploader.create({
        // 自动上传。
        auto: true,
        // swf文件路径
        swf: ctxStatic + '/baiduUEditor1.4.3.2/third-party/webuploader/Uploader.swf',
        //文件上传请求的参数，每次发送都会发送此对象中的参数
        formData: {},
        // 文件接收服务端。
        server: ctxu + '/upload/webUploadAlbumServer.htm',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        // 只允许选择图片类型的文件。
        //accept: {
        //    title: 'Images',
        //    extensions: 'gif,jpg,jpeg,bmp,png',
        //    mimeTypes: 'image/*'
        //}
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        var $li = $(
            '<div id="' + file.id + '" style="display:block;">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
           ),
       $img = $li.find('img');
       $("#addFile").append( $li );
        // 创建缩略图
        uploader.makeThumb( file, function( error, src ) {
            if (error){
                $img.replaceWith('<span>'+fy.getMsg('不能预览')+'</span>');
                return;
            }
           // $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });
    
    //上传前触发
    //uploadStart  某个文件开始上传前触发，一个文件只会触发一次
    uploader.on('uploadStart', function () {
    	$albumId = $('.albumId').val();//获取相册ID
    	//ajax获取token
    	var tokenPath= ctxs+"/sys/sysToken/getToken.htm";
    	var token=null;
    	$.ajax({
			  type: 'post',
			  url: tokenPath,
			  async: false,   //非异步
			  dataType: 'json',
			  success: function(data){
				  token=data.token;
			  }
		});
    	//文件上传请求的参数表，每次发送都会发送此对象中的参数。
    	//携带相册ID、携带token
    	uploader.options.formData = {"albumId":$albumId,"token":token};
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function(file, percentage){
        var $li = $( '#'+file.id ),
        $percent = $li.find('.progress span');
        // 避免重复创建
        if (!$percent.length) {
           $percent = $('<p class="progress"><span></span></p>').appendTo( $li ).find('span');
        }
        $percent.css('width', percentage * 100 + '%');
    });
    
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, ret) {
    	//$( '#'+file.id ).addClass('upload-state-done');
    	var $file = $('#' + file.id);
	    if (ret.state == 'SUCCESS') {
	        $file.append('<span class="success" style="color:green;">'+ret.statusText+'</span>');
	        var albumId=$("input[name='albumId']").val();
	        $("input[name='pageNo']").val("1");
	        $(".switch[albumId="+albumId+"]").click();
	        //更新相册图片数量
	        var picCountCurrent=parseInt($("a.switch[albumId="+albumId+"] .picCount").text());
			$("a.switch[albumId="+albumId+"] .picCount").text(picCountCurrent+1);
	    } else {
	    	$file.append('<span class="error" style="color:red;">'+ret.statusText+'</span>');
	    	setTimeout("window.location.reload()",5000);
	    }
    });
    
    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function (file, code) {
    	/* var $li = $( '#'+file.id ),
         $error = $li.find('div.error');
	     // 避免重复创建
	     if ( !$error.length ) {
	         $error = $('<div class="error"></div>').appendTo( $li );
	     }
	     $error.text('上传失败');*/
    });
    
    //当validate不通过时，会以派送错误事件的形式通知调用者
    uploader.on('error', function (code, file) {
        if (code == 'Q_TYPE_DENIED' || code == 'F_EXCEED_SIZE') {
            addFile(file);
        }
    });
    
    // 完成上传完了，成功或者失败，先删除进度条
    uploader.on('uploadComplete', function (file, ret) {
    	$("#addFile").find("#"+file.id).remove();
    	//$( '#'+file.id ).find('.progress').remove();
    });
});