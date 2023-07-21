(function($) {
	"use strict";
	// custom scrollbar
	$("html").niceScroll({styler:"fb",cursorcolor:"#65cea7", cursorwidth: '6', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0',	zindex: '1000'});
	$(".left-side").niceScroll({styler:"fb",cursorcolor:"#65cea7", cursorwidth: '3', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0'});

	$(".left-side").getNiceScroll();
	if ($('body').hasClass('left-side-collapsed')) {
		$(".left-side").getNiceScroll().hide();
	}

	// Toggle Left Menu
	jQuery("#menu_2").delegate(".menu-list > a","click",function(){
		var parent = jQuery(this).parent();
		var sub = parent.find('> ul');
		
		if(!jQuery('body').hasClass('left-side-collapsed')) {
			if(sub.is(':visible')) {
				sub.slideUp(200, function(){
					parent.removeClass('nav-active');
					jQuery('.main-content').css({height: ''});
					mainContentHeightAdjust();
				});
			}else{
				//visibleSubMenuClose();//关闭其它菜单
				parent.addClass('nav-active');
				sub.slideDown(200, function(){
					mainContentHeightAdjust();
				});
			}
		}
		return false;
	});

	function visibleSubMenuClose() {
		jQuery('.menu-list').each(function() {
			var t = jQuery(this);
			if(t.hasClass('nav-active')) {
				t.find('> ul').slideUp(200, function(){
					t.removeClass('nav-active');
				});
			}
		});
	}

	function mainContentHeightAdjust() {
		// Adjust main content height
		var docHeight = jQuery(document).height();
		if(docHeight > jQuery('.main-content').height())
		 jQuery('.main-content').height(docHeight);
	}

	//	class add mouse hover
	//jQuery('.custom-nav > li').hover(function(){
	//	jQuery(this).addClass('nav-hover');
	//}, function(){
	//	jQuery(this).removeClass('nav-hover');
	//});
	
	jQuery(".custom-nav").delegate("li", "mouseenter", function (event) {	
		jQuery(this).addClass('nav-hover');
	}).delegate("li", "mouseleave", function (event) {	
		jQuery(this).removeClass('nav-hover');
	});

	// Menu Toggle（二级菜单的收起与展开）
	jQuery('.toggle-btn').click(function(){
		$(".left-side").getNiceScroll().hide();
		
		if ($('body').hasClass('left-side-collapsed')) {
			$(".left-side").getNiceScroll().hide();
		}
		var body = jQuery('body');
		var bodyposition = body.css('position');
		
		var key="fdp_menuFoldingState";//存储在cookei中的key
		var t=7;//cookie有效期，单位天
		
		if(bodyposition != 'relative') {
			//宽屏时
			if(!body.hasClass('left-side-collapsed')) {
				body.addClass('left-side-collapsed');
				jQuery('.custom-nav ul').attr('style','');
				jQuery(this).addClass('menu-collapsed');
				fdp.cookie(key, '0', {path:'/',expires:t}); //0表示隐藏
			} else {
				body.removeClass('left-side-collapsed chat-view');
				jQuery('.custom-nav li.active ul').css({display: 'block'});
				jQuery(this).removeClass('menu-collapsed');
				fdp.cookie(key, '1', {path:'/',expires:t}); //1表示显示
			}
		} else {
			//窄屏时--移动设备
			if(body.hasClass('left-side-show')){
				body.removeClass('left-side-show');
			}else{
				body.addClass('left-side-show');
			}
			fdp.cookie(key, '0', {path:'/',expires:-1}); //清空，把时间设置为负数，会让cookie立即过期
			mainContentHeightAdjust();
		}
	});
	
	searchform_reposition();

	jQuery(window).resize(function(){
		if(jQuery('body').css('position') == 'relative') {
			jQuery('body').removeClass('left-side-collapsed');
		} else {
			jQuery('body').css({left: '', marginRight: ''});
		}
		searchform_reposition();
	});

	function searchform_reposition() {
		if(jQuery('.searchform').css('position') == 'relative') {
			jQuery('.searchform').insertBefore('.left-side-inner .logged-user');
		} else {
			jQuery('.searchform').insertBefore('.menu-right');
		}
	}

	// panel collapsible
	$('.panel .tools .fa').click(function () {
		var el = $(this).parents(".panel").children(".panel-body");
		if ($(this).hasClass("fa-chevron-down")) {
			$(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
			el.slideUp(200);
		} else {
			$(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
			el.slideDown(200); }
	});

	$('.todo-check label').click(function () {
		$(this).parents('li').children('.todo-title').toggleClass('line-through');
	});

	$(document).on('click', '.todo-remove', function () {
		$(this).closest("li").remove();
		return false;
	});

	$("#sortable-todo").sortable();

	// panel close
	$('.panel .tools .fa-times').click(function () {
		$(this).parents(".panel").parent().remove();
	});

	// tool tips
	$('.tooltips').tooltip();

	// popovers
	$('.popovers').popover();

})(jQuery);