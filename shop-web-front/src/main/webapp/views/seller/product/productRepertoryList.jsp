<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>仓库中的商品</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="2"/><!--表示使用N号二级菜单 -->
<style>
	/* .productName{color:#6699FF;height: 60px;}
	.lingshou{color:#669966}
	.pifa{color:#FF9900}
	.sui-modal .modal-body {
		margin: 0px!important;
		padding: 10px 5px!important;
	} */
</style>
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="box">
					<dt class="box-header">
						<h4 class="pull-left">
							<i class="sui-icon icon-tb-addressbook"></i><span>仓库中的商品</span>
						</h4>
						<ul class="sui-breadcrumb">
							<li>当前位置:</li>
							<li><a href="#">首页</a></li>
							<li><a href="#">手机数码、电脑办公</a></li>
							<li class="active">智能手机</li>
						</ul>
					</dt>
					<!--main-toptab-->
						<div class="main-toptab">
							<ul class="sui-nav">
								<li class="active"><a href="#">仓库中的商品</a></li>
								<li><a href="#">禁售的商品</a></li>
								<li><a href="#">等待审核的商品</a></li>
								<li><a href="#">审核失败的商品</a></li>
							</ul>
						</div>
					<!--main-toptab-->
					<dd class="table-css">
						<!--搜索表单开始 -->
						<div class="pull-right">
							<form class="sui-form form-inline m10" method="post">
										<span class="sui-dropdown dropdown-bordered select">
											<span class="dropdown-inner">
											<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input value="hz" name="city" type="hidden"><i class="caret"></i><span>商品名称</span>
											</a>
											<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
												<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">商品名称</a></li>
												<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">商家账号</a></li>
												<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="sb">spu</a></li>
											</ul>
											</span>
										</span>
										<input type="text" placeholder="" class="input-medium" style="width:135px;">
										<a href="javascript:void(0);" class="sui-btn btn-primary">搜索</a>
							</form>
						</div>
						<table class="sui-table table-bordered-simple">
							<thead>
								<tr colspan="8">
									<th width="1%" class="center"></th>
									<th width="10%" class="center">缩略图</th>
									<th width="28%" class="center">商品名称</th>
									<th width="9%" class="center">规格/规格值</th>
									<th width="8%" class="center">总库存</th>
									<th width="13%" class="center">价格</th>
									<th width="8%" class="center">30天销量</th>
									<th width="10%" class="center">上架时间</th>
									<th width="12%" class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!--全选 工具条-->
								<tr>
									<td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox"
											checked="checked"><span></span></label></td>
									<td width="10%" class="" colspan="30">
										<div class="sui-btn-group">
											<h5>全选&nbsp;&nbsp;&nbsp;&nbsp;</h5>
											<button class="sui-btn ">商品上架</button>
										 </div>
									</td>
								</tr>
								<!--全选 工具条-->

								<!--循环开始-->
								<tr>
									<td class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox"
											checked="checked"><span></span></label><div></div></td>
									<td class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
									<td class="">
										<div class="productName">Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放</div>
										<div>商品SPU：185 <i class="sui-icon icon-tb-tag pifa">批发</i></div>
									</td>
									<td class="">
										<div class="product-spec"> 
										 <p>颜色：<span>红色</span><span>军绿色</span></p>
										 <p>尺码：<span>XXS</span><span>XL</span></p>
										</div> 
										<div><a href="javascript:void(0);" productId="14" productName="Mediheal可莱丝美迪惠尔水润保湿面膜10片水库针剂新老包装随机发放" 
										class="sui-btn showSpec"><i class="sui-icon icon-pencil"></i>查看全部规格</a></div>
									</td>
									<td class="center">198</td>
									<td class="">
										<div>1 - 9 把 100.00</div>
										<div>10 - 49 把 90.00</div>
										<div>≥50 把 80.00</div>
									</td>
									<td class="center">789</td>
									<td class="center">2016-11-13</td>
									<td class="center">
										<div><a href="javascript:void(0);" class="sui-btn btn-small" style="margin-bottom:2px;"><i class="sui-icon icon-tb-edit"></i>编辑</a></div>
										<div><a href="javascript:void(0);" class="sui-btn btn-small btn-bordered btn-danger mt10"><i class="sui-icon icon-tb-delete"></i>删除</a></div>
									</td>
								</tr>
								<tr>
									<td class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox"
											checked="checked"><span></span></label></td>
									<td class="center"><img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" alt="" width="80" height="80"/></td>
									<td class="">
										<div class="productName">华为（HUAWEI）华为路由Q1子母路由/双WiFi覆盖/穿墙稳定/智能家居/网络安全无线路由器</div>
										<div>商品SPU：186 <i class="sui-icon icon-tb-tagfill lingshou" >零售</i></div>
									</td>
									<td class="">
										<div class="product-spec"> 
										 <p>颜色：<span>多色/渐变色</span><span>军绿色</span></p>
										 <p>尺码：<span>XXS</span><span>XL</span></p>
										 <p>预制规格：<span>规格值A</span><span>规格值F</span><span>规格值K</span><span>规格值L</span></p>
										</div>
										<div><a href="javascript:void(0);" productId="15" productName="华为（HUAWEI）华为路由Q1子母路由/双WiFi覆盖/穿墙稳定/智能家居/网络安全无线路由器" 
										class="sui-btn showSpec"><i class="sui-icon icon-pencil"></i>查看全部规格</a></div>
									</td>
									<td class="center">198</td>
									<td class="">
										<div>349.00-389.00	</div>
									</td>
									<td class="center">789</td>
									<td class="center">2016-03-03</td>
									<td class="center">
										<div><a href="javascript:void(0);" class="sui-btn btn-small" style="margin-bottom:2px;"><i class="sui-icon icon-tb-edit"></i>编辑</a></div>
										<div><a href="javascript:void(0);" class="sui-btn btn-small btn-bordered btn-danger mt10"><i class="sui-icon icon-tb-delete"></i>删除</a></div>
									</td>
								</tr>
								<!--循环结束-->
								<tr>
									<td width="1%" class="center"><label id="checkbox2" class="checkbox-pretty inline checked ml5 mr0"><input type="checkbox"
											checked="checked"><span></span></label></td>
									<td width="10%" class="" colspan="30">
										<div class="sui-btn-group">
											<h5>全选&nbsp;&nbsp;&nbsp;&nbsp;</h5>
											<button class="sui-btn ">商品上架</button>
										 </div>
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="/views/seller/include/page.jsp"%>
					</dd>
				</dl>
			</div>
		</div>
	</div>
<script>
$(function(){
	$(".showSpec").click(function(){
		var productId=$(this).attr("productId");
		var productName=$(this).attr("productName");
		var myTitle="查看“"+productName+"”的规格";
		var myRemoteUrl="./sku.jsp?id="+productId;

		 $.alert({
			backdrop: true		//决定是否为模态对话框添加一个背景遮罩层。另外，该属性指定static时，表示添加遮罩层，同时点击模态对话框的外部区域不会将其关闭。
			,bgcolor: '#000000'	//背景遮罩层颜色，默认是黑色。可以用rgba设置透明度
			,keyboard: true	//是否可由esc按键关闭
			,title: myTitle	//'自定义标题'
			,body: 'html' 		//必填
			,okBtn : '好的'
			,cancelBtn : '雅达'
			,closeBtn: true	//是否显示右上角叉叉（html使用方式无需配置，直接改html结构即可）
			,transition: true	//是否以transition动画方式显示隐藏对话框（html使用方式调用对话框时，也可直接删除.sui-modal元素上的类名fade即可）
			,hasfoot: false	//是否显示脚部 默认true
			,width: "900px"		//{number|string(px)|'small'|'normal'|'large'}推荐优先使用后三个描述性字符串，统一样式
			//,height: 			//{number|string(px)} 内容区（.modal-body）高度
			,remote: myRemoteUrl //{string} 如果提供了远程url地址，就会加载远端内容
			//,timeout: 1000	//{number} 1000	单位毫秒ms ,对话框打开后多久自动关闭
			//,show: function(){console.log('show')}
			//,shown: function(){console.log('shown')}
			//,hide: function(){console.log('hide')}
			//,hidden: function(){console.log('hiden')}
			//,okHide: function(){var a=confirm('true or false');if(!a) return false}
			//,okHidden: function(){console.log('okHidden')}
			//,cancelHide: function(){console.log('cancelHide')}
			//,cancelHidden: function(){console.log('cancelHidden')}
		 })
	});
});
</script>
</body>
</html>