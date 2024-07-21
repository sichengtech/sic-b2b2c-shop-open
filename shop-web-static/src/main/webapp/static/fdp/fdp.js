/**
 * fdp库，fdp.js文件是一个js组件库，封装了常用的js工具，如alert、confirm、msg、cookie操作等等。
 * @author zhaolei
 * @version 2016-12-25
 */
(function(){
	//fdp命名空间,起隔离作用，防止同名变量冲突
	//fdp是快速开发平台的简写
	if(!window.fdp) {window.fdp={};}

	/**
	 * 扩展示例
	 * 当你需要扩展fdp工具时，请参考本示例复制以下代码再进行扩展
	 * 调用示例：fdp.xxx()
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.xxx=function(){
		//do something
	}

	/**
	 * 通用msg提示框
	 * 
	 * 调用示例：fdp.msg("你好",3000)
	 * @author zhaolei
	 * @version 2016-12-26
	 */
	fdp.msg=function(message,showTime){
		//var index=layer.msg(message, {time: 3000,offset: '250px'});
		var closeLayer=function(){
			layer.close(index);
		}
		var keyHandle=function(e){//键盘事件处理函数
			if(e.keyCode==27){//Esc键
				layer.close(index);
			}
			if(e.keyCode==13){//回车键
				layer.close(index);
			}
		}
		var index=layer.msg(message, {
			time: showTime?showTime:3000,
			offset: '250px',
			success:function(layero, index){
				$("body").keyup(keyHandle);//挂载键盘处理事件
				//2017-8-3 zl 因click事件触发调用的fdp.msg("")会被立即关闭，所有下面使用了延时
				setTimeout(function(){
					$("body").click(closeLayer);
				},500);
				
			},
			end:function(){//当框关闭时
				$("body").unbind("keyup",keyHandle);//卸载键盘处理事件
				$("body").unbind("click", closeLayer);
			},
		});
	}

	/**
	 * 通用alert提示窗口
	 * 
	 * 调用示例：fdp.alert("你好");
	 * @author zhaolei
	 * @version 2016-12-26
	 */
	fdp.alert=function(message,closed){
		var keyHandle=function(e){//键盘事件处理函数
			if(e.keyCode==27){//Esc键
				layer.close(index);
			}
		}
		var title=fy.getMsg("系统提示");
		if(typeof(title)=="undefined" || title==''){
			title="系统提示";
		}
		var btnOk=fy.getMsg("确定");
		if(typeof(btnOk)=="undefined" || btnOk==''){
			btnOk="确定";
		}
		var index=layer.open({//弹出layer提示框
			type:0,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			title:title,
			//offset: '250px',
			shadeClose:true,
			content:message?message:'',
			btn: [btnOk], //按钮
			success:function(layero, index){
				//当框弹出时，给框加焦点，加键盘处理事件，按回车时可提交，按esc时可取消。
				$(".layui-layer .layui-layer-btn0").attr("href","javascript:void(0);")
				$(".layui-layer .layui-layer-btn0").focus();//给确认按键加焦点
				$("body").keyup(keyHandle);//挂载键盘处理事件
			},
			end:function(){
				//当框关闭时
				$("body").unbind("keyup",keyHandle);//卸载键盘处理事件
			},
			yes:function(index, layero){
				layer.close(index);//关闭layer提示框
				if (typeof closed == 'function') {
					//如果外部指定了“确认”的处理函数，则调用此函数
					return closed();
				}
			},
		}); 
	}

	/**
	 * 通用confirm确认框
	 * 		一般用于删除数据时的确认提示
	 * 调用示例：
	 * 		fdp.confirm('确定要删除么？','http://www.abc.com/user/del.do');
	 * 		fdp.confirm('确定要删除么？',function(){});
	 * @author zhaolei
	 * @version 2016-12-25
	 */
	fdp.confirm=function(message,href,closed){
		var keyHandle=function(e){//键盘事件处理函数
			if(e.keyCode==27){//Esc键
				layer.close(index);
			}
			if(e.keyCode==13){//回车键
				$(".layui-layer .layui-layer-btn0").trigger("click");
			}
		}
		var title=fy.getMsg("系统提示");

		if(typeof(title)=="undefined" || title==''){
			title="系统提示";
		}
		var btnOk=fy.getMsg("确定");
		if(typeof(btnOk)=="undefined" || btnOk==''){
			btnOk="确定";
		}
		var btnClose=fy.getMsg("关闭");
		if(typeof(btnClose)=="undefined" || btnClose==''){
			btnClose="关闭";
		}
		var index=layer.open({//弹出layer提示框
			type:0,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			title:title,
			content:message?message:'你确定要执行本操作么？',
			btn: [btnOk,btnClose], //按钮
			success:function(layero, index){
				//当框弹出时，给框加焦点，加键盘处理事件，按回车时可提交，按esc时可取消。
				$(".layui-layer .layui-layer-btn0").attr("href","javascript:void(0);")
				//$(".layui-layer .layui-layer-btn0").focus();//给确认按键加焦点
				$(fdp.getEventObj()).blur();//让事件源失去焦点
				$("body").keyup(keyHandle);//挂载键盘处理事件
			},
			end:function(){
				//当框关闭时
				$("body").unbind("keyup",keyHandle);//卸载键盘处理事件
			},
			yes:function(index, layero){
				layer.close(index);//关闭layer提示框
				if (typeof closed == 'function') {
					//如果外部指定了“确认”的处理函数，则调用此函数
					return closed();
				}else{
					if (typeof href == 'function') {
						return href();//如果href是函数，则执行函数
					}else{
						//2017-8-5 zl 由于下面window.location.href=href;会刷新页面，
						//所以可以安全的使用定时器，页面刷新后“请稍等”提示就没有了
						//如果外部未指定“确认”的处理函数，则默认执行以下代码
						setTimeout(function(){
							//如果1.5秒内未完成操作，则给出请等待的提示
							var submitTip=fy.getMsg("正在提交，请稍等");
							if(typeof(submitTip)=="undefined" || submitTip==''){
								submitTip="正在提交，请稍等";
							}
							layer.msg(submitTip+'...', {icon: 16,shade: 0.30,time: 0})
						},1500);						
						//跳转到某个URL地址
						window.location.href=href;
					}
				}
			},
		}); 
	}

	/**
	 * 引入js和css文件
	 * 调用示例：fdp.include()
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.include=function (id, path, file){
		if (document.getElementById(id)==null){
			var files = typeof file == "string" ? [file] : file;
			for (var i = 0; i < files.length; i++){
				var name = files[i].replace(/^\s|\s$/g, "");
				var att = name.split('.');
				var ext = att[att.length - 1].toLowerCase();
				var isCSS = ext == "css";
				var tag = isCSS ? "link" : "script";
				var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
				var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
				document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
			}
		}
	}

	/**
	 * 获取URL地址中的参数
	 * 调用示例：fdp.getQueryString()
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.getQueryString=function (name, url) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		if (!url || url == ""){
			url = window.location.search;
		}else{
			url = url.substring(url.indexOf("?"));
		}
		r = url.substr(1).match(reg)
		if (r != null) return unescape(r[2]); return null;
	}

	/**
	 * 获取字典标签
	 * 调用示例：fdp.getDictLabel()
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.getDictLabel=function (data, value, defaultValue){
		for (var i=0; i<data.length; i++){
			var row = data[i];
			if (row.value == value){
				return row.label;
			}
		}
		return defaultValue;
	}

	/**
	 * 打开一个窗体
	 * 调用示例：fdp.windowOpen()
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.windowOpen=function (url, name, width, height){
		var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
			options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
			"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
		window.open(url ,name , options);
	}

	/**
	 * 缩略字符串
	 * 截取字符串，若超过最大宽度，在文本后追回“...”
	 * 如果字符串中含有html标签，会被替掉
	 * 一个字母算1个长度，一个汉字算2个长度
	 * 
	 * 应用场景：
	 * 在网页上有一个文章列表，固定宽度是200px，里面要显示很多行的文章标题。文章标题有中文的、有英文的、有中英混合的。
	 * 如果文章的标题超长就要被截掉后面的部分，不能超出列表的200px最大宽度。
	 * 当文章的标题同时有中英文时，保留多少个字符合适呢？如何在固定的宽度内保留更多的字符呢？
	 * 例1："北京发生严重的雾霾"有9个字符，但宽度是200px。
	 * 例2："Beijing is Smog"有15个字符，但宽度是200px。
	 * 为在固定的宽度内保留更多的字符，要这样计算：一个字母占1个长度，一个汉字占2个长度。
	 * 
	 * 调用示例：fdp.abbr("北京发生严重的雾霾污染",18);	结果：北京发生严重的雾霾...
	 * 
	 * @param str 目标字符串
	 * @param length 保留长度（一个文字要按2个长度算）
	 * @return 返回被缩略后的字符串
	 * @version 2017-xx-xx
	 */
	fdp.abbr=function (name, maxLength) {
		if (!maxLength) {
			maxLength = 20;
		}
		if (name == null || name.length < 1) {
			return "";
		}
		var w = 0;// 字符串长度，一个汉字长度为2
		var s = 0;// 汉字个数
		var p = false;// 判断字符串当前循环的前一个字符是否为汉字
		var b = false;// 判断字符串当前循环的字符是否为汉字
		var nameSub;
		for (var i = 0; i < name.length; i++) {
			if (i > 1 && b == false) {
				p = false;
			}
			if (i > 1 && b == true) {
				p = true;
			}
			var c = name.charCodeAt(i);
			// 单字节加1
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				w++;
				b = false;
			} else {
				w += 2;
				s++;
				b = true;
			}
			if (w > maxLength && i <= name.length - 1) {
				if (b == true && p == true) {
					nameSub = name.substring(0, i - 2) + "...";
				}
				if (b == false && p == false) {
					nameSub = name.substring(0, i - 3) + "...";
				}
				if (b == true && p == false) {
					nameSub = name.substring(0, i - 2) + "...";
				}
				if (p == true) {
					nameSub = name.substring(0, i - 2) + "...";
				}
				break;
			}
		}
		if (w <= maxLength) {
			return name;
		}
		return nameSub;
	}

	/**
	 * 操作cookie的通用函数
	 * 
	 * 示例0：写入一个临时cookie，关闭浏览器后失效 fdp.cookie('键', '值');
	 * 示例1：写入一个持久cookie，fdp.cookie(key, '0', {path:'/',expires:7}); //7表示有效期7天
	 * 示例2：删除cookie  fdp.cookie(key, '0', {path:'/',expires:-1}); //清空，把时间设置为负数，会让cookie立即过期
	 * （注意path要相同才能删除成功，如果cookie存储的path是/，删除时则要指定path为/）
	 * 示例3：写入一个cookie，7天后失效  fdp.cookie('键', '值', {path:'/',expires:7});//7表示有效期7天
	 * 示例4：写入一个cookie，指定域名、路径、有效期 fdp.cookie('键', '值', {domain:"www.test.com",path:'/',expires:7});
	 * 
	 * options.domain:指定域名
	 * options.path:指定路径，一般可指定/,若为null则写入当前页面的URL中取出当前路径
	 * options.expires:有效期，单位天
	 * options.secure:安全，true表示，该cookie只会在https下发送，而不会再http下发送
	 * 
	 * 示例5：取cookie, fdp.cookie('键') ,不能指定path路径,一个key存储在多个路径下，先返回目录层级长的。
	 */
	fdp.cookie=function (name, value, options) {
		if (typeof value != 'undefined') { // name and value given, set cookie
			options = options || {};
			if (value === null) {
				value = '';
				options.expires = -1;
			}
			var expires = '';
			if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
				var date;
				if (typeof options.expires == 'number') {
					date = new Date();
					date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
				} else {
					date = options.expires;
				}
				expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
			}
			var path = options.path ? '; path=' + options.path : '';
			var domain = options.domain ? '; domain=' + options.domain : '';
			var secure = options.secure ? '; secure' : '';
			document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
		} else { // only name given, get cookie
			var cookieValue = null;
			if (document.cookie && document.cookie != '') {
				var cookies = document.cookie.split(';');
				for (var i = 0; i < cookies.length; i++) {
					var cookie = jQuery.trim(cookies[i]);
					// Does this cookie string begin with the name we want?
					if (cookie.substring(0, name.length + 1) == (name + '=')) {
						cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
						break;
					}
				}
			}
			return cookieValue;
		}
	}

	/**
	 * 显示默认图
	 * 当在页面中显示图片，因图片不存在发生错误时，显示一张默认图
	 * 
	 * 使用示例
	 * <img src="${ctxStatic}/sicheng-front/images/face.png" onerror="fdp.defaultImage('${ctxStatic}/pic/default.jpg');">
	 * 
	 * 调用示例：fdp.defaultImage("/pic/default.jpg")
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */
	fdp.defaultImage=function(path){
		if(path==null || path==""){
			return ;
		}
		var event=fdp.getEvent();//获得事件对象
		if(event){
			var node = event.srcElement ? event.srcElement:event.target;
			$(node).attr('src',path);//显示默认图
			$(node).unbind('error');//清除onerror事件
			node.onerror=null;//清除onerror事件
		}
		//当在页面中显示图片，因图片不存出错时会触发onerror事件，
		//执行fdp.defaultImage("/pic/default.jpg")显示默认图，
		//但default.jpg默认图也不存在，再次触发onerror事件，这样就发生了死循环
		//所以这里清除onerror事件，onerror事件只能触发一次。
	}

	/**
	 * 获得事件对象
	 * <button type="button" onclick="abc(123)"/> 在abc函数内可获得事件对象
	 * <a href="javascript:abc(123);" /> 在abc函数内无法获得事件对象
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */	
	fdp.getEvent=function getEvent(){  
        if(window.event){   
            return window.event;  
        }  
        var f=getEvent.caller;  
        while(f!=null){  
            var e = f.arguments[0];                       
            if(e && (e.constructor==MouseEvent||e.constructor==Event||e.constructor==KeyboardEvent)) return e;                          
            f=f.caller;  
        }  
    } 
	
	/**
	 * 获得事件源
	 * @author zhaolei
	 * @version 2017-xx-xx
	 */	
	fdp.getEventObj=function getEventObj(){  
		var event = fdp.getEvent();
		if(event){
			var obj = event.srcElement ? event.srcElement : event.target; 
			return obj;
		}else{
			return null;
		}
	} 

})();