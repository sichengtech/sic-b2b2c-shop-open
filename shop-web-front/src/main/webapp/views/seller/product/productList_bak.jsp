<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>规格添加</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header"><h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>填家商品</span></h4><ul class="sui-breadcrumb"><li>当前位置:</li><li><a href="#">首页</a></li><li><a href="#">手机数码、电脑办公</a></li><li class="active">智能手机</li></ul></dt>
			<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0">
			<form class="sui-form form-inline mb0" method="post">
				<div class="span4">
					<div class="control-group"><label class="control-label">商品名称：</label><input type="text" class="input-large"></div>
					<div class="control-group"><label class="control-label">商品编码：</label><input type="text" class="input-large"></div>
					<div class="control-group"><label class="control-label">商品货号：</label><input type="text" class="input-large"></div>
					<div class="control-group"><label class="control-label">商品品牌：</label>
						<span class="sui-dropdown dropdown-bordered select"><span class="dropdown-inner"><a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
						<input value="hz" name="city" type="hidden"><i class="caret"></i><span>请选择品牌阿斯蒂芬</span></a>
						 <ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
							<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">北京</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">圣彼得堡</a></li>
							<li role="presentation" class="divider"></li>
							<li role="presentation" class="active"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="hz">杭州</a></li>
						 </ul></span>
						</span>
					</div>
				</div>
				<div class="span4">
					<div class="control-group"><label class="control-label">京东SKU：</label><input type="text" class="input-large"></div>
					<div class="control-group"><label class="control-label">价格：</label><input id="" type="text" class="span2 input-small"><span class="add-on"> 元</span> ~ <input id="" type="text" class="span2 input-small"><span class="add-on"> 元</span></div>
					<div class="control-group"><label class="control-label">京东SKU：</label><input type="text" class="input-large"></div>
				</div>
				<div class="span4"></div>
				<div class="clear"></div>
				<div class="sui-row-fluid text-align pb20 bor-b">
					<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary">首要</a>
					<a href="javascript:void(0);" class="sui-btn btn-xlarge ml20">默认</a>
				</div>
			</form>
			</dd>
			<dd class="table-css">

					<div class="sui-btn-group m20">
					 <h5>列表筛选：</h5>
					 <button class="sui-btn btn-bordered btn-primary">首页推荐</button>
					 <button class="sui-btn btn-bordered btn-primary">钻展推荐</button>
					 <button class="sui-btn btn-bordered btn-primary">焦点推荐</button>
					 <button class="sui-btn btn-bordered btn-primary">促销商品</button>
					 <button class="sui-btn btn-bordered btn-primary">什么什么</button>
					 <button class="sui-btn btn-bordered btn-primary">什么事吗</button>
					</div>
					<div class="pull-right"><a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary m16">填加新商品</a><a href="javascript:void(0);" class="sui-btn btn-xlarge btn-info m16">批量填加</a><a href="javascript:void(0);" class="sui-btn btn-xlarge btn-info m16">数据包导入</a></div>
				<table class="sui-table table-bordered-simple">
				 <thead>
					 <tr colspan="8">
						<th width="1%" class="center"></th>
						<th width="10%" class="center">缩略图</th>
						<th width="35%" class="center">商品名称</th>
						<th width="10%" class="center">总库存</th>
						<th width="10%" class="center">价格</th>
						<th width="10%" class="center">30天销量</th>
						<th width="10%" class="center">上架时间</th>
						<th width="14%" class="center">操作</th>
					 </tr>
				 </thead>
				 <tbody>
				 
				 <!--循环开始-->
					<tr>
					 <td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox" checked="checked"><span></span></label></td>
					 <td width="10%" class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
					 <td width="35%">
Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放
					 </td>
					 <td width="10%" class="center">
						198
					 </td>
					 <td width="10%" class="center">￥199.00</td>
					 <td width="10%" class="center">789</td>
					 <td width="10%" class="center">2016-3-3</td>
					 <td width="14%" class="center">
						<a href="javascript:void(0);" class="sui-btn"><i class="sui-icon icon-tb-edit"></i>编辑</a>
					 </td>
					</tr>
					<!--循环结束-->
				 
				 <!--循环开始-->
					<tr>
					 <td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox" checked="checked"><span></span></label></td>
					 <td width="10%" class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
					 <td width="35%">
Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放
					 </td>
					 <td width="10%" class="center">
						198
					 </td>
					 <td width="10%" class="center">￥199.00</td>
					 <td width="10%" class="center">789</td>
					 <td width="10%" class="center">2016-3-3</td>
					 <td width="14%" class="center">
						<a href="javascript:void(0);" class="sui-btn"><i class="sui-icon icon-tb-edit"></i>编辑</a>
					 </td>
					</tr>
					<!--循环结束-->
				 
				 <!--循环开始-->
					<tr>
					 <td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox" checked="checked"><span></span></label></td>
					 <td width="10%" class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
					 <td width="35%">
Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放
					 </td>
					 <td width="10%" class="center">
						198
					 </td>
					 <td width="10%" class="center">￥199.00</td>
					 <td width="10%" class="center">789</td>
					 <td width="10%" class="center">2016-3-3</td>
					 <td width="14%" class="center">
						<a href="javascript:void(0);" class="sui-btn"><i class="sui-icon icon-tb-edit"></i>编辑</a>
					 </td>
					</tr>
					<!--循环结束-->
				 
				 <!--循环开始-->
					<tr>
					 <td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox" checked="checked"><span></span></label></td>
					 <td width="10%" class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
					 <td width="35%">
Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放
					 </td>
					 <td width="10%" class="center">
						198
					 </td>
					 <td width="10%" class="center">￥199.00</td>
					 <td width="10%" class="center">789</td>
					 <td width="10%" class="center">2016-3-3</td>
					 <td width="14%" class="center">
						<a href="javascript:void(0);" class="sui-btn"><i class="sui-icon icon-tb-edit"></i>编辑</a>
					 </td>
					</tr>
					<!--循环结束-->
				 </tbody>
				</table>
				<div class="docs">
					<div class="sui-btn-group">
					 <h5>批量操作选中商品：</h5>
					 <button class="sui-btn btn-bordered btn-primary">复制</button>
					 <button class="sui-btn btn-bordered btn-primary">粘贴</button>
					 <button class="sui-btn btn-bordered btn-primary">编辑</button>
					 <button class="sui-btn btn-bordered btn-primary">撤销</button>
					 <button class="sui-btn btn-bordered btn-primary">查找</button>
					 <button class="sui-btn btn-bordered btn-primary">删除</button>
				 </div>
			 </div>
			</dd>
		</dl>
		</div>
	</div>
	</div>
</body>
</html>