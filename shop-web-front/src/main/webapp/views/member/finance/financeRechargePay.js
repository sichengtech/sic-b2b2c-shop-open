$(document).ready(function(){
	$(".payment li").click(function(){
		$(".payment .cur").removeClass("cur");
		$(this).addClass("cur");
	});
});