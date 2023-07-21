<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品发布')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 业务js -->
<script src="${ctxs}/product/productCategory/categorySearch.js.htm" type="text/javascript"></script>
<script src="${ctx}/views/seller/product/productRelease1.js" type="text/javascript"></script>
</head>
<body>
 <div class="main-content">
	<div class="">
	<sys:message content="${message}"/>
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('商品发布')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('商品')}</li>
					<li>${fns:fy('商品管理')}</li>
					<li class="active">${fns:fy('商品发布')}</li>
				</ul>
			</dt>
			<!-- 提示信息开始 -->
			<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
					<div class="msg-con">
						<ul>
							<h4>${fns:fy('提示信息')}</h4>
							<li>${fns:fy('发布商品第一步，选择商品分类。')}</li>
							<li>${fns:fy('可以直接点击分类名称进行选择，也可以进行搜索进行选择。')}</li>
							<li>${fns:fy('选择分类时，必须选到末级分类。')}</li>
						</ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</address>
			<!-- 提示信息结束 -->
			<!-- 引导条 开始 -->
			<div class="m10">
				<div class="sui-steps steps-auto">
				 <div class="wrap">
					<div class="current">
					 <label><span class="round">1</span><span>${fns:fy('第一步：选择商品分类')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
					</div>
				 </div>
				 <div class="wrap">
					<div class="todo">
					 <label><span class="round">2</span><span>${fns:fy('第二步：填写商品详情')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
					</div>
				 </div>
				 <div class="wrap">
					<div class="todo">
					 <label><span class="round">3</span><span>${fns:fy('第三步：商品发布成功')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
					</div>
				 </div>
				</div>
			</div>
			<!-- 引导条 结束 -->
			<dd class="input-big">
				<input id="searchInput" type="text" class="text-big" placeholder="${fns:fy('搜索分类')}">
				<button id="searchButton" type="button" class="btn-big">${fns:fy('搜索')}</button>
			</dd>

			<!-- 三级分类 开始 -->
			<dd class="m10" id="part1">
			<div class="sort_list sui-row-fluid">
			<div class="span4">
				<dl>
				 <dt class="bor-b bg-ffffff sui-form"><div class="input-control control-right input-block-level"><input type="text" id="searchOne" value="" class="input-xfat" placeholder="${fns:fy('请输入名称或拼音首字母')}"><i class="sui-icon icon-touch-magnifier"></i></div></dt>
				 <dd>
					<ul class="" id="cate_level_1">
					</ul>
				 </dd>
				 </dl>
			</div>
			<div class="span4">
				<dl>
				<dt class="bor-b bg-ffffff sui-form"><div class="input-control control-right input-block-level"><input type="text" id="searchTwo" value="" class="input-xfat" placeholder="${fns:fy('请输入名称或拼音首字母')}"><i class="sui-icon icon-touch-magnifier"></i></div></dt>
				<dd>
				 <ul class="" id="cate_level_2">
				 </ul>
				</dd>
			 </dl>
			</div>
			<div class="span4">
			 <dl>
				<dt class="bor-b bg-ffffff sui-form"><div class="input-control control-right input-block-level"><input type="text" id="searchThree" value="" class="input-xfat" placeholder="${fns:fy('请输入名称或拼音首字母')}"><i class="sui-icon icon-touch-magnifier"></i></div></dt>
				<dd>
				 <ul class="" id="cate_level_3">
				 </ul>
				</dd>
			 </dl>
			</div>
			</div>
			<div class="clear"></div>
			<div class="p10">${fns:fy('您当前的选择是：')}<span class="currentCate"></span></div>
			</dd>
			<!-- 三级分类 结束 -->
			<!-- 搜索结果 开始 -->
			<dd class="search-big-box" id="part2" style="display:none;">
			<div class="search-hint">${fns:fy('以下是系统匹配到的分类路径，如没有合适的分类，')}<a href="#">${fns:fy('请重新选择分类。')}</a></div>
				<ul id="filter">
				</ul>
			</dd>
			<!-- 搜索结果 结束 -->
				 
			<div class="text-align pb20">
				<sys:message content="${message}"/>
				<form id="productReleaesStep1" action="${ctxs}/product/productSpu/save2.htm" method="get">
				<shiro:hasPermission name="product:productSpu:edit">
					<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('下一步，填写商品信息')}</button>
				</shiro:hasPermission>
				<input type="hidden" id="product_category_id" name="product_category_id" help="${fns:fy('商品分类的ID')}"/>
				</form>
			</div>
		</dl>
		</div>

	</div>
	</div>
<script type="text/template" id="cate_tpl_1" info="${fns:fy('一级分类的模板 for Mustache')}">
<li>
	<a href="javascript:;" id="{{d.id}}" class="" onclick="showCateLevel_2('{{d.id}}')">
		{{#  if(d.hasChild){ }}<i class="pull-right sui-icon icon-touch-double-angle-right"></i>{{#  } }} {{d.name}}
	</a>
</li>
</script> 
<script type="text/template" id="cate_tpl_2" info="${fns:fy('二级分类的模板 for Mustache')}">
<li>
	<a href="javascript:;" id="{{d.id}}" class="" onclick="showCateLevel_3('{{d.id}}')">
		{{#  if(d.hasChild){ }}<i class="pull-right sui-icon icon-touch-double-angle-right"></i>{{#  } }}{{d.name}}
	</a>
</li>
</script> 
<script type="text/template" id="cate_tpl_3" info="${fns:fy('三级分类的模板 for Mustache')}">
<li>
	<a href="javascript:;" id="{{d.id}}" class="" onclick="selectCategroy('{{d.id}}',3)">
		{{#  if(d.hasChild){ }}<i class="pull-right sui-icon icon-touch-double-angle-right"></i>{{#  } }}{{d.name}}
	</a>
</li>
</script> 
<script type="text/template" id="cate_tpl_4" info="${fns:fy('搜索结果的模板 for Mustache')}">
<li>
	<a onclick="selectRow('{{d.id}}')">{{d.name}}</a>
</li>
</script> 
</body>
</html>