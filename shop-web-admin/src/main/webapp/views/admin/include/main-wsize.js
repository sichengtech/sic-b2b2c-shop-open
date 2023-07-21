/**
 * ***********************
 * 计算foot脚部条的显示位置
 * ***********************
 * 名词解释：
 * 工作区的高度：页面内容高度
 * 窗口(视口)的高度：浏览器窗口的高度，视口是可看见的口子。
 * 
 * 目标：
 * foot脚部条 根据 页面内容高度 自动决定是否显示在第一屏上。
 * 当页面内容高度很高很高时，说明要展示数据很多，希望foot脚部条出现在页面内容的底下(不出现在第一屏)，从而增大窗口(视口)的可视面积。
 * 当页面内容高度不高时，说明要展示数据少，都不够一屏，希望foot脚部条出现在窗口(视口)的底下(出现在第一屏)，紧贴着窗口的底边。
 * 
 * @author zhaolei
 * @version 2016-11-22
 */
$(function(){
	if(!Array.prototype.map)
		Array.prototype.map = function(fn,scope) {
		var result = [],ri = 0;
		for (var i = 0,n = this.length; i < n; i++){
			if(i in this){
			result[ri++]	= fn.call(scope ,this[i],i,this);
			}
		}
		return result;
	};
	var getWindowSize = function(){
		return ["Height","Width"].map(function(name){
			return window["inner"+name] ||
			document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
		});
	};
	
	//////////////////
	//调整foot的位置
	//////////////////
	function wSize(){
		var content = $("section.panel");
		var head = $(".header-section");
		var foot = $(".footer")
		
		//////////////////
		//计算工作区的高度
		//////////////////
		var minHeight = 50;	//最小高度,550
		var workHeight=0;		//工作区的原始高度
		var workHeightUse=0;	//工作区的高度(可用版),是经过计算后的，应使用此高度值。
		if(content){
			workHeight=content.height()+head.height()+foot.height() +10;//工作区的高度
			if(workHeight > minHeight){
				workHeightUse=workHeight;//工作区的高度(可用版)为工作区的高度
			}else{
				workHeightUse=minHeight;//工作区的高度(可用版)为最小高度
			}
		}
		
		//////////////////
		//计算foot脚部条的位置
		//////////////////
		var strs = getWindowSize().toString().split(",");//窗口(视口)的宽与高
		var windowH=strs[0];//窗口(视口)的高
		if(windowH < workHeightUse ){
			var h=workHeightUse;
			$("div.main-content").css({"min-height":h+"px"});
			$(".footer").css({"position":"static"});
		}else{
			var h=windowH;
			$("div.main-content").css({"min-height":h+"px"});
			if(workHeight>0){
				var h2=windowH-workHeight;
				$(".footer").css({"position":"relative"});//相对定位,因为被富文本编辑器遮挡，所以选择不浮动
				$(".footer").css({"top":h2+"px"});//向下移动
			}
		}
	}
	//窗口大小变动时，重新计算
	$(window).resize(function(){
		wSize();
	});
	wSize(); //设置调整目标
});