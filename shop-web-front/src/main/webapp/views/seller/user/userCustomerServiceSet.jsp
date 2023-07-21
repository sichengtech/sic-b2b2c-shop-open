<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('客服设置')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="6"/><!--表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/user/userCustomerServiceSet.js"></script>
<style type="text/css">
	#formDd {padding-top: 0px;padding-bottom:0px; }
	.header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
	.header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
	.store-set dd{padding-top: 0px!important;}
	#orderTable{border-right: none; width: 98%;margin-left: 10px;border-top:none; }
	#point{font-size: 12px;font-weight: normal;}
	.sui-msg.msg-block {margin: 10px !important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy('客服设置')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li><a href="javascript:;">${fns:fy('运营')}</a></li>
						<li><a href="javascript:;">${fns:fy('客服管理')}</a></li>
						<li class="active">${fns:fy('客服设置')}</li>
					</ul>
				</dt>
				<!-- 提示开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen pt10 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('说明：客服信息需要填写完整，不完整信息将不会被保存。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示结束-->
				<!-- 客服设置开始 -->
				<sys:message content="${message}"/>
				<form id="myForm" action="${ctxs}/store/customer/save.htm" method="post" class="sui-form form-inline">
					<dd id="formDd">
						<div class="control-group">
							<label class="control-label" style="float: left;">${fns:fy('客服设置：')}</label> 
							<table class="sui-table table-bordered-simple" style="width: 85%;">
								<thead>
									<tr>
										<th width="17%" class="center">${fns:fy('客服名称')}</th>
										<th width="17%" class="center">${fns:fy('客服工具')}</th>
										<th width="17%" class="center">${fns:fy('客服账号')}</th>
										<th width="17%" class="center">${fns:fy('类型')}</th>
										<th width="17%" class="center">${fns:fy('排序')}</th>
										<th width="15%" class="center">${fns:fy('操作2')}</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach items="${customerList}" var="customer" varStatus="status">
										<input name="scsId" value="${customer.scsId}" type="hidden"/>
										<tr class="appendTr">
											<td class="firstTableTd center" width="17%">
												<input name="name" value="${customer.name}" type="text" placeholder="" class="input-small" value=""/>
											</td>
											<td width="17%" class="center">
												<span class="sui-dropdown dropdown-bordered select">
													<span class="dropdown-inner">
														<a id="drop12" role="button" data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
															<input name="tool" value="${customer.tool}" type="hidden"><i class="caret"></i>
															<c:set var="tool" value="${customer.tool}"></c:set>
															<c:set var="a" value="${fns:getDictLabel(tool, 'store_customer_tool', defaultValue)}"></c:set>
															<span>${empty customer.tool?fns:fy('请选择'):a}</span>
														</a>
														<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
															<c:forEach items="${fns:getDictList('store_customer_tool')}" var="customerTool">
																<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${customerTool.value}">${customerTool.label}</a></li>
															</c:forEach>
														</ul>
													 </span>
												 </span>
											</td>
											<td width="17%" class="center">
												<input name="account" value="${customer.account}" type="text" placeholder="" class="input-medium" value=""/>
											</td>
											<td width="17%" class="center">
												<span class="sui-dropdown dropdown-bordered select">
												 	<span class="dropdown-inner">
													 	<a id="drop12" role="button" data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
														 	<input name="type" value="${customer.type}" type="hidden"><i class="caret"></i>
														 	<c:set var="type" value="${customer.type}"></c:set>
															<c:set var="b" value="${fns:getDictLabel(type, 'store_customer_type', defaultValue)}"></c:set>
															<span>${empty customer.type?fns:fy('请选择'):b}</span>
														</a>
														<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
															<c:forEach items="${fns:getDictList('store_customer_type')}" var="customerType">
																<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${customerType.value}">${customerType.label}</a></li>
															</c:forEach>
														</ul>
													</span>
											 	</span>
											</td>
											<td width="17%" class="center">
												<input name="sort" value="${customer.sort}" type="text" placeholder="" class="input-small" value=""/>
											</td>
											<td width="15%" class="center">
												<a class="sui-btn btn-danger delLine" id="delLine">${fns:fy('删除')}</a>
											</td>
										</tr>									
									</c:forEach>
								</tbody>
								<tfoot>
									<tr colspan="6"> 
										<td colspan="6">
											<a href="javascript:void(0);" class="sui-btn btn-bordered" id="addCustSer">${fns:fy('添加客服')}</a>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="control-group">
							<label class="control-label" style="float: left;">${fns:fy('工作时间：')}</label> 
							<textarea name="remarks" rows="3" cols="30" style="width: 84%;">${remarks}</textarea>
						</div>
						<shiro:hasPermission name="store:storeCustomer:edit">
							<div class="control-group" style="padding-left: 53px;">
								<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
								<a href="http://shang.qq.com/v3/widget.html" target="_blank" class="sui-btn btn-bordered btn-xlarge btn-danger" style="float: right;">${fns:fy('去腾讯进行官方授权')}</a>
								<div class="formPrompt" style="text-align: right;">
									<i class="msg-icon"></i>
									<div class="msg-con">${fns:fy('授权后的QQ账号，买家不需要加你为好友即可直接沟通')}</div>
								</div>
							</div>
							</div>
						</shiro:hasPermission>
					</dd>
				</form>
				<div class="clear"></div>
				<!-- 客服设置结束 -->
			</dl>
		</div>
	</div>

	<!-- 隐藏的开始 -->
	<table style="display: none;">
		<tbody class="addLine">
		<tr class="appendTr">
			<td class="firstTableTd center" width="17%">
				<input name="name" type="text" placeholder="" class="input-small" value=""/>
			</td>
			<td width="17%" class="center">
				<span class="sui-dropdown dropdown-bordered select">
					<span class="dropdown-inner">
						<a id="drop12" role="button" data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
							<input value="1" name="tool" type="hidden"><i class="caret"></i><span>QQ</span>
						</a>
						<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
							<c:forEach items="${fns:getDictList('store_customer_tool')}" var="customerTool">
								<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${customerTool.value}">${customerTool.label}</a></li>
							</c:forEach>
						</ul>
					 </span>
				 </span>
			</td>
			<td width="17%" class="center">
				<input name="account" type="text" placeholder="" class="input-medium" value=""/>
			</td>
			<td width="17%" class="center">
				<span class="sui-dropdown dropdown-bordered select">
				 	<span class="dropdown-inner">
					 	<a id="drop12" role="button" data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
						 	<input value="" name="type" type="hidden"><i class="caret"></i><span>${fns:fy('请选择')}</span>
						</a>
						<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
							<c:forEach items="${fns:getDictList('store_customer_type')}" var="customerType">
								<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${customerType.value}">${customerType.label}</a></li>
							</c:forEach>
						</ul>
					</span>
			 	</span>
			</td>
			<td width="17%" class="center">
				<input name="sort" type="text" placeholder="" class="input-small" value=""/>
			</td>
			<td width="15%" class="center">
				<a class="sui-btn btn-danger delLine" id="delLine">${fns:fy('删除')}</a>
			</td>
		</tr>
		</tbody>
	</table>
	<!-- 隐藏的行结束 -->
</body>
</html>