<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${store.name}${fns:fy("初始化成功-小写")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeInit.js"></script>
<style type="text/css">
	.initStore_div1{text-align:center;margin:0 auto;}
	.initStore_div2{margin-left: 45%;}
	.initStore_div2 h5{margin-top: 15px;color: green;};
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("初始化成功-大写")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/store/list.do"> <i class="fa fa-user"></i> ${fns:fy("店铺列表")}</a></li>
				<li class="active" style="display:inline-block;overflow: hidden;white-space:nowrap;width: 350px;"><a href="javaScript:;" > <i class="fa fa-user"></i> ${store.name}${fns:fy("初始化成功-小写")}</a></li>
			</ul>
		</header>
		<sys:message content="${message}"/>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 店铺初始化内容 start -->
			<div class="initStore_div1">
				<h3>${store.name}${fns:fy("初始化成功-小写")}</h3>
			</div>
			<div class="initStore_div2">
				<h5><i class="fa fa-check"></i> ${fns:fy("会员已经升级成供应商")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已初始化完毕")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已分配二级域名")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已分配相册空间")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已分配默认相册夹")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已分配默认商品分类")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已默认分配装修模板一")}</h5>
				<h5><i class="fa fa-check"></i> ${fns:fy("已创建默认文章")}</i></h5>
			</div>
			<!-- 店铺初始化内容 end -->
			<!-- 绑定品牌，绑定佣金，店铺打标 start-->
			<div class="initStore" style="margin-top: 30px;">
				<h3>${fns:fy("请管理员操作")}</h3>
			</div>
			<table class="table table-condensed table-bordered" style="margin-top: 15px;">
				<thead>
					<th>
						<c:if test="${fn:length(storeBrandList)==0}">
							<span>${fns:fy("绑定品牌状态")}：${fns:fy("未绑定")}</span>
						</c:if>
						<c:if test="${fn:length(storeBrandList)!=0}">
							<span style="color: green;">${fns:fy("绑定品牌状态")}：${fns:fy("已绑定")}</span>
						</c:if>
					</th>
					<th>
						<c:if test="${empty store.commission}">
							<span>${fns:fy("绑定佣金状态")}：${fns:fy("未绑定")}</span>
						</c:if>
						<c:if test="${not empty store.commission}">
							<span style="color: green;">${fns:fy("绑定佣金状态")}：${fns:fy("已绑定")}${fns:fy("默认绑定0%")}</span>
						</c:if>
					</th>
					<th>
						<c:if test="${store.markingImgPath eq '1'}">
							<span style="color: green;">${fns:fy("店铺保证金状态")}：${fns:fy("缴纳")}</span>
						</c:if>
						<c:if test="${store.markingImgPath eq '0'}">
							<span>${fns:fy("店铺保证金状态")}：${fns:fy("未缴纳")}</span>
						</c:if>
					</th>
				</thead>
				<tbody>
					<td>
						<a href="javaScript:void(0);" class="btn btn-info btn-sm bindBrand" storeId="${store.storeId}">
							<i class="fa fa-edit"></i> ${fns:fy("绑定品牌")}
						</a>
						<a target="_blank" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${store.userMain.UId}&attr=1" class="btn btn-warning btn-sm">
							<i class="fa fa-eye"></i> ${fns:fy("查看入驻信息")}
						</a>
		            	<a target="_blank" href="${ctxa}/product/productBrand/save1.do" class="btn btn-success btn-sm">
							<i class="fa fa-plus"></i> ${fns:fy("添加品牌")}
						</a>
					</td>
					<td>
						<a href="javaScript:void(0);" class="btn btn-info btn-sm bindCommission" storeId="${store.storeId}">
							<i class="fa fa-edit"></i> ${fns:fy("绑定佣金")}
						</a>
					</td>
					<td>
						<a href="javaScript:void(0);" class="btn btn-info btn-sm storeMarking" storeId="${store.storeId}">
							<i class="fa fa-edit"></i> ${fns:fy("保证金")}
						</a>
					</td>
				</tbody>
			</table>
			<!-- 绑定品牌，绑定佣金，店铺打标 end-->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>