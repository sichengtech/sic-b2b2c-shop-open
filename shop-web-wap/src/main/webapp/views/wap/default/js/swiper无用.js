$(function  () {
	var swiper = new Swiper('.swiper-container', {
		pagination: {
			el: '.swiper-pagination',
		},
	});
	
	TagNav('#activitynav',{
		type: 'scrollToFirst',
	});
	$('.weui_tab').tab({
		defaultIndex: 0,
		activeClass:'weui_bar_item_on',
		onToggle:function(index){
			if(index>0){
				alert(index)
			}
		}
	});
	$('.searchbar_wrap').searchBar({
		cancelText:"取消",
		searchText:'关键字',
		onfocus: function (value) {
		   
		},
		onblur:function(value) {
	
		},
		oninput: function(value) {
	
		},
		onsubmit:function(value){
		},
		oncancel:function(){
	
		},
		onclear:function(){
	
		}
	});
});
