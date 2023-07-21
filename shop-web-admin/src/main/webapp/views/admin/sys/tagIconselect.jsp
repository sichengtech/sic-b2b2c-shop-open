<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<head>
	<title>图标选择</title>

	<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#icons li").click(function(){
				$("#icons li").removeClass("active");
				$("#icons li i").removeClass("icon-white");
				$(this).addClass("active");
				$(this).children("i").addClass("icon-white");
				$("#icon").val($(this).text());
			});
			$("#icons li").each(function(){
				if ($(this).text()=="${value}"){
					$(this).click();
				}
			});
			$("#icons li").dblclick(function(){
				top.$.jBox.getBox().find("button[value='ok']").trigger("click");
			});
		});
	</script>
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/fonts/css/font-awesome.min.css">
	<style type="text/css">
		.page-header {clear:both;margin:0 20px;padding-top:20px;}
		.the-icons {padding:0px 10px 15px;list-style:none;}
		.the-icons li {float:left;width:22%;line-height:25px;margin:2px 5px;cursor:pointer;}
		.the-icons i {margin:1px 5px;font-size:16px;} .the-icons li:hover {background-color:#efefef;}
		.the-icons li.active {background-color:#0088CC;color:#ffffff;}
		.the-icons li:hover i{font-size:20px;}
		.page-header{color: #317EB6;}
	</style>
	
</head>
<body>
<input type="hidden" id="icon" value="${value}"/>
<div id="icons">

		<h4 class="page-header"> Web 应用的图标</h4>
		<ul class="the-icons">
		 <li><i class="icon-adjust"></i> icon-adjust</li>
		 <li><i class="fa fa-adjust"></i> fa-adjust</li>
		 <li><i class="fa icon-anchor"></i> fa-anchor</li>
		 <li><i class="fa fa-archive"></i>fa-archive</li>
		 <li><i class="fa fa-arrows"></i>fa-arrows</li>
		 <li><i class="fa fa-arrows-h"></i>fa-arrows-h</li>
		 <li><i class="fa fa-arrows-v"></i>fa-arrows-v</li>
		 <li><i class="fa fa-asterisk"></i>fa-asterisk</li>
		 <li><i class="fa fa-ban"></i> fa-ban</li>
		 <li><i class="fa fa-bar-chart-o"></i>fa-bar-chart-o</li>
		 <li><i class="fa fa-barcode"></i>fa-barcode</li>
		 <li><i class="fa fa-bars"></i>fa-bars</li>
		 <li><i class="fa fa-beer"></i>fa-beer</li>
		 <li><i class="fa fa-bell"></i>fa-bell</li>
		 <li><i class="fa fa-bell-o"></i>fa-bell-o</li>
		 <li><i class="fa fa-bolt"></i>fa-bolt</li>
		 <li><i class="fa fa-book"></i> fa-book</li>
		 <li><i class="fa fa-bookmark"></i>fa-bookmark</li>
		 <li><i class="fa fa-bookmark-o"></i>fa-bookmark-o</li>
		 <li><i class="fa fa-briefcase"></i>fa-briefcase</li>
		 <li><i class="fa fa-bug"></i>fa-bug</li>
		 <li><i class="fa fa-building-o"></i>fa-building-o</li>
		 <li><i class="fa fa-bullhorn"></i>fa-bullhorn</li>
		 <li><i class="fa fa-bullseye"></i>fa-bullseye</li>
		</ul>

	 
		<h4 class="page-header">文本编辑器图标</h4>
		<ul class="the-icons">
		 <li><i class="fa fa-align-center"></i>fa-align-center</li>
		 <li><i class="fa fa-align-justify"></i>fa-align-justify</li>
		 <li><i class="fa fa-align-left"></i>fa-align-left</li>
		 <li><i class="fa fa-align-right"></i> fa-align-right</li>
		 <li><i class="fa fa-bold"></i>fa-bold</li>
		 <li><i class="fa fa-chain (alias)"></i>fa-chain (alias)</li>
		 <li><i class="fa fa-chain-broken"></i>fa-chain-broken</li>
		 <li><i class="fa fa-clipboard"></i>fa-clipboard</li>


		 <li><i class="fa fa-columns"></i>fa-columns</li>
		 <li><i class="fa fa-copy (alias)"></i>fa-copy (alias)</li>
		 <li><i class="fa fa-cut (alias)"></i> fa-cut (alias)</li>
		 <li><i class="fa fa-dedent (alias)"></i> fa-dedent (alias)</li>
		 <li><i class="fa fa-eraser"></i>fa-eraser</li>
		 <li><i class="fa fa-file"></i>fa-file</li>
		 <li><i class="fa fa-file-o"></i>fa-file-o</li>
		 <li><i class="fa fa-file-text"></i>fa-file-text</li>
		</ul>

		<h4 class="page-header">指示方向的图标</h4>
		<ul class="the-icons">
		 <li><i class="fa fa-angle-double-down"></i>fa-angle-double-down</li>
		 <li><i class="fa fa-angle-double-left"></i>fa-angle-double-left</li>
		 <li><i class="fa fa-angle-double-right"></i>fa-angle-double-right</li>
		 <li><i class="fa fa-angle-double-up"></i>fa-angle-double-up</li>
		 <li><i class="fa fa-angle-down"></i> fa-angle-down</li>
		 <li><i class="fa fa-arrow-circle-left"></i>fa-arrow-circle-left</li>
		 <li><i class="fa fa-arrow-circle-o-down"></i>fa-arrow-circle-o-down</li>
		 <li><i class="fa fa-arrow-circle-o-left"></i>fa-arrow-circle-o-left</li>


		 <li><i class="fa fa-arrow-circle-o-right"></i>fa-arrow-circle-o-right</li>
		 <li><i class="fa fa-arrow-circle-o-up"></i>fa-arrow-circle-o-up</li>
		 <li><i class="fa fa-arrow-circle-right"></i>fa-arrow-circle-right</li>
		 <li><i class="fa fa-arrow-circle-up"></i>fa-arrow-circle-up</li>
		 <li><i class="fa fa-arrow-down"></i> fa-arrow-down</li>
		 <li><i class="fa fa-arrow-left"></i> fa-arrow-left</li>
		 <li><i class="fa fa-arrow-right"></i> fa-arrow-right</li>
		</ul>

		<h4 class="page-header">视频播放器图标</h4>
		<ul class="the-icons">
		 <li><i class="fa fa-arrows-alt"></i> fa-arrows-alt</li>
		 <li><i class="fa fa-backward"></i> fa-backward</li>
		 <li><i class="fa fa-compress"></i> fa-compress</li>
		 <li><i class="fa fa-eject"></i> fa-eject</li>

		 <li><i class="fa fa-expand"></i> fa-expand</li>
		 <li><i class="fa fa-fast-backward"></i>fa-fast-backward</li>
		 <li><i class="fa fa-fast-forward"></i> fa-fast-forward</li>
		 <li><i class="fa fa-forward"></i> fa-forward</li>

		 <li><i class="fa fa-pause"></i>fa-pause</li>
		 <li><i class="fa fa-play"></i> fa-play</li>
		 <li><i class="fa fa-play-circle"></i> fa-play-circle</li>
		 <li><i class="fa fa-play-circle-o"></i> fa-play-circle-o</li>
		 
		 <li><i class="fa fa-step-backward"></i>fa-step-backward</li>
		 <li><i class="fa fa-step-forward"></i> fa-step-forward</li>
		 <li><i class="fa fa-stop"></i> fa-stop</li>
		 <li><i class="fa fa-youtube-play"></i> fa-youtube-play</li>
		</ul>

		<h4 class="page-header">SNS图标</h4>
		<ul class="the-icons">
		 <li><i class="fa fa-adn"></i>fa-adn</li>
		 <li><i class="fa fa-android"></i>fa-android</li>
		 <li><i class="fa fa-apple"></i>fa-apple</li>
		 <li><i class="fa fa-bitbucket"></i> fa-bitbucket</li>

		 <li><i class="fa fa-bitbucket-square"></i> fa-bitbucket-square</li>
		 <li><i class="fa fa-bitcoin (alias)"></i> fa-bitcoin (alias)</li>
		 <li><i class="fa fa-btc"></i>fa-btc</li>
		 <li><i class="fa fa-css3"></i>fa-css3</li>

		 <li><i class="fa fa-dribbble"></i> fa-dribbble</li>
		 <li><i class="fa fa-dropbox"></i> fa-dropbox</li>
		 <li><i class="fa fa-facebook"></i> fa-facebook</li>
		 <li><i class="fa fa-facebook-square"></i> fa-facebook-square</li>


		 <li><i class="fa fa-flickr"></i> fa-flickr</li>
		 <li><i class="fa fa-foursquare"></i> fa-foursquare</li>
		 <li><i class="fa fa-github"></i> fa-github</li>
		 <li><i class="fa fa-github-alt"></i> fa-github-alt</li>
		</ul>
	 
	 
		<h4 class="page-header">医疗图标</h4>
		<ul class="the-icons">
		 <li><i class="fa fa-ambulance"></i>fa-ambulance</li>
		 <li><i class="fa fa-h-square"></i>fa-h-square</li>
		 <li><i class="fa fa-hospital-o"></i>fa-hospital-o</li>
		 <li><i class="fa fa-medkit"></i>fa-medkit</li>

		 <li><i class="fa fa-plus-square"></i>fa-plus-square</li>
		 <li><i class="fa fa-stethoscope"></i> fa-stethoscope</li>
		 <li><i class="fa fa-user-md"></i>fa-user-md</li>
		 <li><i class="fa fa-wheelchair"></i>fa-wheelchair</li>
		</ul>
	<br/><br/>
</div>
</body>