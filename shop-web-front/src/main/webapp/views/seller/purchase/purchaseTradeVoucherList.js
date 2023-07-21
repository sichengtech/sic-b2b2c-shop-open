$(function(){
	/**
	 * 图片放大
	 */
	$('.comment-pic').magnificPopup({
		delegate : 'a', // 画廊项目的选择器
		type : 'image',
		gallery : {
			enabled : true
		}
	});
});