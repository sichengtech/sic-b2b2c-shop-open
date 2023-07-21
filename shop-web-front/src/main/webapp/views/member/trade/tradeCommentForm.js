 $(document).ready(function(){
    /**
     * 初始化MyUpload控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var myupload_len= $(".myupload").length;
    for(var i=0;i<myupload_len;i++){
        var upload = new MyUpload({
            // 获取token的路径
            tokenPath: ctxs+"/sys/sysToken/getToken.htm",
            // 文件上传到的服务器
            server: ctxu+'/upload/webUploadServer.htm',
            // 容器Id
            container: '#vessel'+i,
            buttonStyle: 1,
            //accept: 'file',
            fileSingleSizeLimit: 1024 * 1024 * 5,
            fileNumLimit: 5,
            uploadSuccess: function(data, datas){
            	var con_input = $(this.options.container).siblings('input');
            	for(var i=0; i< con_input.length; i++) {
            		console.log(con_input.eq(i).val())
            		if(!con_input.eq(i).val()){
            			con_input.eq(i).val(datas.path);
            			return false;
            		}
            	}
            }
        });
    }
    
    //评价星星点击事件
	$(".scoreRadio").click(function(){
		$(this).prevAll().andSelf().find("i").attr("class","sui-icon icon-tb-favorfill starNew");
		$(this).nextAll().find("i").attr("class","sui-icon icon-tb-favorfill starOld");
	});
 });