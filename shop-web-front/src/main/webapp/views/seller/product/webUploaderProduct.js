jQuery(function() {
    var $ = jQuery,    // just in case. Make sure it's not an other libaray.
	$wrap = $('#uploader'),
    // 图片容器
    $queue = $("#modSelected ul"),
    // 状态栏，包括进度和控制按钮
    $statusBar = $wrap.find('.statusBar'),
    // 文件总体选择信息。
    $info = $statusBar.find('.info'),
    // 没选择文件之前的内容。
    $placeHolder = $wrap.find('.placeholder'),
    // 总体进度条
    $progress = $statusBar.find('.progress').hide(),
    // 添加的文件数量
    fileCount = 0,
    // 添加的文件总大小
    fileSize = 0,
    // 优化retina, 在retina下这个值是2
    ratio = window.devicePixelRatio || 1,
    // 缩略图大小
    thumbnailWidth = 80 * ratio,
    thumbnailHeight = 80 * ratio,
    // 可能有pedding, ready, uploading, confirm, done.
    state = 'pedding',
    statusText="",
    // 所有文件的进度信息，key为file id
    percentages = {},
    supportTransition = (function(){
    	var s = document.createElement('p').style,
            r = 'transition' in s ||
                  'WebkitTransition' in s ||
                  'MozTransition' in s ||
                  'msTransition' in s ||
                  'OTransition' in s;
        s = null;
        return r;
    })(),
    // WebUploader实例
    uploader;
    if ( !WebUploader.Uploader.support() ) {
    	fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> Web Uploader "+fy.getMsg('不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器'),3000);
        $(".upload-info").html("<p style='margin-top: 60px;'><strong>Web Uploader "+fy.getMsg('不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器')+"</strong></p>");
    	throw new Error( 'WebUploader does not support the browser you are using.' );
    }

    // 实例化
    uploader = WebUploader.create({
    	// 选完文件后，是否自动上传。
        auto: true,
        pick: {
            id: '#filePicker',
            label: fy.getMsg('点击选择图片')
        },
        //指定Drag And Drop拖拽的容器，如果不指定，则不启动
        dnd: '#uploader .queueList',
        //通过粘贴来添加截屏的图片
        paste: document.body,
        //指定接受哪些类型的文件
        accept: {
        	title: 'Images',
            extensions: 'jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
        // swf文件路径
        swf: ctxStatic + '/baiduUEditor1.4.3.2/third-party/webuploader/Uploader.swf',
        //否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开
        disableGlobalDnd: true,
        server: ctxu + '/upload/webUploadAlbumServer.htm',
        //重复上传
        duplicate :true,
        //验证文件总数量, 超出则不允许加入队列
        fileNumLimit: 30,
        //验证文件总大小是否超出限制, 超出则不允许加入队列
        fileSizeLimit: 150 * 1024 * 1024,    // 150 M
        //验证单个文件大小是否超出限制, 超出则不允许加入队列
        fileSingleSizeLimit: 5 * 1024 * 1024    // 5 M
    });

    var liArray=new Array();
    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
    	layer.load(1, {shade: [0.1,'#fff']});
    	var tpl = $("#imgTpl2").html();//模板
		var fileId=file.id;
		var data = {"fileId":fileId,id:"",path:"",pixel:""};
		var $li=$(render(tpl,data)),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),
            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = fy.getMsg('文件大小超出');
                        layer.closeAll('loading');
                        break;
                    
                    case 'interrupt':
                        text = fy.getMsg('上传暂停');
                        layer.closeAll('loading');
                        break;

                    default:
                        text = fy.getMsg('上传失败，请重试');
                    	layer.closeAll('loading');
                        break;
                }
                $info.text( text ).appendTo( $li );
          };
        if (file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap.text( fy.getMsg('预览中') );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $wrap.text( fy.getMsg('不能预览') );
                    return;
                }
                $li.find("img").attr("src",src);
            }, thumbnailWidth, thumbnailHeight );
            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

		//statuschange:文件状态变化
        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
               // $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                console.log( file.statusText );
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append('<span class="success"></span>' );
            }
            $li.removeClass('state-' + prev ).addClass( 'state-' + cur );
        });
      //  $li.appendTo( $queue );
        liArray.push($li);
    }

    function updateTotalProgress() {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );
        percent = total ? loaded / total : 0;
        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus();
    }

    function updateStatus() {
        var text = '', stats;
        if ( state === 'ready' ) {
            text = fy.getMsg('选中') + fileCount + fy.getMsg('张图片，共') +
                    WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = fy.getMsg('已成功上传') + stats.successNum+ fy.getMsg('张照片至相册，')+
                    stats.uploadFailNum + fy.getMsg('张照片上传失败，')+'<a class="retry" href="#">'+fy.getMsg('重新上传')+'</a>'+fy.getMsg('失败图片或')+'<a class="ignore" href="#">'+fy.getMsg('忽略')+'</a>'
            }
        } else {
            stats = uploader.getStats();
            text = fy.getMsg('共') + fileCount + fy.getMsg('张（') +
                    WebUploader.formatSize( fileSize )  +
                    fy.getMsg('），已上传') + stats.successNum + fy.getMsg('张');

            if ( stats.uploadFailNum ) {
                text += fy.getMsg('，失败') + stats.uploadFailNum + fy.getMsg('张');
            }
        }
        //layer.closeAll('loading');
        $info.html( text );
    }

    function setState( val ) {
        var file, stats;
        if ( val === state ) {
            return;
        }
        state = val;
        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $(".queueList").removeClass('filled');
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $(".queueList").addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;
            case 'error':
            	fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+statusText,2000);
            	break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg('上传成功'),2000);
                } else {
                    // 没有成功的图片，重设
                   state = 'done';
                   //location.reload();
                }
                break;
        }
        updateStatus();
    }
    
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

	//上传过程中触发，携带上传进度。
    uploader.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages[ file.id ][ 1 ] = percentage;
        updateTotalProgress();
    };

	//当一批文件添加进队列以后触发。
    uploader.onFileQueued = function( file ) {
        fileCount++;
        fileSize += file.size;

        if ( fileCount === 1 ) {
            $placeHolder.addClass( 'element-invisible' );
            $statusBar.show();
        }

        addFile( file );
        setState( 'ready' );
        updateTotalProgress();
    };

	//当文件被移除队列后触发.
    uploader.onFileDequeued = function( file ) {
        fileCount--;
        fileSize -= file.size;
        if ( !fileCount ) {
            setState( 'pedding' );
        }
        removeFile( file );
        updateTotalProgress();
    };

    uploader.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState( 'confirm' );
                break;

            case 'startUpload':
                setState( 'uploading' );
                break;

            case 'stopUpload':
                setState( 'paused' );
                break;
        }
    });
    
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, ret) {
    	var $file = $('#' + file.id);
	    if (ret.state == 'SUCCESS') {
	    	//$("#"+file.id).find("img").attr("src1",ret.url);
	    	//$("#"+file.id).attr("data-index",ret.pictureId);
	    	//$("#"+file.id).find(".pixel").text(ret.pixel);
	    	for(var i=0;i<liArray.length;i++){
	    		if(liArray[i].attr("id")==file.id){
	    			liArray[i].find("img").attr("src1",ret.url);
	    			liArray[i].attr("data-index",ret.pictureId);
	    			liArray[i].find(".pixel").text(ret.pixel);
	    			liArray[i].appendTo( $queue );
	    		}
	    	}
	    	layer.closeAll('loading');
	    } else {
	    	statusText=ret.statusText;
	    	$queue.html("");
	    	setState( 'error' );
	    	updateStatus();
	    	layer.closeAll('loading');
	    }
    });

    uploader.onError = function(code) {
    	var msg="";
    	switch (code){
	    	case 'Q_EXCEED_NUM_LIMIT':
	    	    msg = fy.getMsg('只能上传一个文件');
	    	    break;
	    	case 'F_EXCEED_SIZE':
	    	    msg = fy.getMsg('上传文件大小过大');
	    	    break;
	    	case 'Q_TYPE_DENIED':
	    		msg = fy.getMsg('文件类型上传错误');
	    		break;
    	}
    	//layer.closeAll('loading');
    	fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+msg,2000);
    };

    $info.on( 'click', '.retry', function() {
        uploader.retry();
    } );

    //忽略错误文件，继续上传
    $info.on( 'click', '.ignore', function() {
        alert( 'todo' );
    } );

    updateTotalProgress();
    
  //渲染模板
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
});