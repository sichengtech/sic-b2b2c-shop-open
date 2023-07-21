 $(document).ready(function(){
	/**
	 * 图片放大控件的使用
	 */
	$('.gallery').each(function() { // 你所有的画廊的容器
		$('.gallery').magnificPopup({
			delegate : 'a', // 画廊项目的选择器
			type : 'image',
			gallery : {
				enabled : true
			}
		});
	});
 });