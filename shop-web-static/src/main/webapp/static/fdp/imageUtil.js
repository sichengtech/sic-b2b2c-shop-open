/** 
 * 前台的图片工具，保持图片长宽比的情况下缩小图片
 * 使用示例：<img onload="imageUtil.resize(118,240);"/>
 * 
 * @author 赵磊 2009-04-25 
 * http://elf8848.iteye.com/blog/374788
 */
(function(){
	//ADS命名空间 Advanced DOM Scripting
	if(!window.imageUtil){
		window.imageUtil={};
	} 

	// 获得事件对象
	function getEvent(){
		if(window.event){
			return window.event;
		}
		var f=getEvent.caller;
		while(f!=null) {
			var e = f.arguments[0];
			if(e && (e.constructor==MouseEvent||e.constructor==Event||e.constructor==KeyboardEvent)) return e;
			f=f.caller;
		}
	}
	window.imageUtil.getEvent=getEvent; 

	// 保持图片长宽比的情况下缩小图片
	function resize(width,height){
		var e=getEvent();
		var imgObj=e.srcElement?e.srcElement:e.target;
		if(null!=imgObj){
			if("undefined"!=typeof imgObj.src && ""!=imgObj.src){
				var f=new Image;
				f.src=imgObj.src;
				if((f.width>width || f.height>height) && 0<width && 0<height ){
					var w=f.width;
					var h=f.height;
					var g=w/width;
					var l=h/height;
					var key=g>l?g:l;
					imgObj.style.width=w/key+"px";
					imgObj.style.height=h/key+"px";
				}else{
					var w=f.width;
					var h=f.height;
					imgObj.style.width=w+"px";
					imgObj.style.height=h+"px";
				}
			}
		}
	}
	window.imageUtil.resize=resize;

})();	