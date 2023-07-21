<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('新增物流配送规则')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	.sui-msg.msg-block{margin:10px!important;}
	.firstTd{border: 1px dotted #e6e6e6;}
	.sui-modal{border: none!important;border-radius:5px;}
	.city-sub{position: absolute;top: 25px;background: #FFFEC6;border: 1px solid #F7E4A5;width:186px;z-index:-1;
		display: none;}
	.areas{line-height: 20px;display: inline-block;padding:4px 0px 4px 4px;margin-right: 4px;}
	.closeCity{margin-bottom:5px;text-align: right;margin-right: 10px;} 
	.currentCitys{cursor: pointer;}
	.provinces{padding: 5px;border-width: 1px 1px 0 1px;border-style: solid;border-color: rgba(249, 249, 249, 0);}
	.newSpan{position: relative;padding: 5px;border-width: 1px 1px 0 1px;border-style: solid;background: #FFFEC6;z-index: 2;
		border-color: #F7E4A5 #F7E4A5 transparent #F7E4A5;}
	.provinceName{margin-right: 0!important;}
	.store-set dd{padding-top: 0px;}
</style>
<script type="text/javascript">
	var logisticsTemplateList=${logisticsTemplateList};
</script>

</head>
<body>
<div class="main-content">
	<c:set var="isEdit" value ="${not empty logisticsTemplate.ltId?true:false}"></c:set >
	<div class="goods-list">
		<dl class="box store-set">
			<dt class="box-header">
				<h4 class="pull-left">
				<i class="sui-icon icon-tb-addressbook"></i>
				<span>
					${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('物流配送规则')}
					<%@ include file="../include/functionBtn.jsp"%>
				</span>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('订单')}</li>
					<li>${fns:fy('订单管理')}</li>
					<li class="active">${isEdit?fns:fy('编辑'):fns:fy('添加')}${fns:fy('物流配送规则')}</li>
				</ul>
			</dt>
			<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
			<div class="sui-msg msg-tips msg-block">
				<div class="msg-con">
					 <ul>
						<h4>${fns:fy('提示信息')}</h4>
						<li>${fns:fy('1.如果商品选择使用了配送规则，则该商品只售卖配送规则中指定的地区，运费为指定地区的运费。')}</li>
						<li>${fns:fy('2.正在被商品使用的配送规则不允许删除。')}</li>
					 </ul>
				</div>
				<s class="msg-icon" style="margin-top: 10px"></s>
			</div>
			</address>
			<sys:message content="${message}"/>
			<form class="sui-form form-inline" id="inputForm" method="post" action="${ctxs}/logistics/logisticsTemplate/${isEdit?'edit2':'save2'}.htm">
				<input type="hidden" name="ltId" value="${logisticsTemplate.ltId}">
				<dd>
					<dl class="form-layout">
						<dt><font color="red">*</font>${fns:fy('运费模板名称：')}</dt>
						<dd>
						<input type="text" class="input-xlarge" name="name" value="${logisticsTemplate.name}">
							<i class="msg-icon"></i>
							<div class="hint">${fns:fy('请输入运费模板名称。')}</div>
						</dd>
					</dl>
					<dl class="form-layout">
						<dt>${fns:fy('是否包邮：')}</dt>
						<dd>
						<c:forEach items="${fns:getDictList('logistics_template_free_shipping')}" var="tfs" varStatus="index">
							<label class="radio-pretty inline ${!isEdit && index.first || isEdit && logisticsTemplate.isFreeShipping eq tfs.value ?'checked':''}" type="isFreeShipping" style="text-align: left;">
								<input type="radio" class="isFreeShipping" name="isFreeShipping" value="${tfs.value}" ${!isEdit && index.last || isEdit && logisticsTemplate.isFreeShipping eq tfs.value ?"checked='checked'":""}/><span>${tfs.label}</span>
							</label>
						</c:forEach>
						</dd>
					</dl>
					<dl class="form-layout">
						<input type="hidden" name="oldValuationMethod" value="${logisticsTemplate.valuationMethod}"/>
						<dt>${fns:fy('计价方式：')}</dt>
						<dd>
							<c:forEach items="${fns:getDictList('logistics_template_valuation_method')}" var="tvm" varStatus="index">
								<label class="radio-pretty inline ${!isEdit && index.last || isEdit && logisticsTemplate.valuationMethod eq tvm.value?'checked':''}" style="text-align: left;">
									<input type="radio" name="valuationMethod" value="${tvm.value}" ${!isEdit && index.last || isEdit && logisticsTemplate.valuationMethod eq tvm.value?"checked='checked'":""}/><span>${tvm.label}</span>
								</label>
							</c:forEach>
						</dd>
					</dl>
					<dl class="form-layout">
						<dt><font color="red">*</font>${fns:fy('设置详细信息：')}</dt>
						<dd>
						<table class="sui-table table-bordered-simple" id="mesTable">
							<thead>
								<th width="30%">${fns:fy('运送到')}</th>
								<th width="10%" style="text-align: center;">${fns:fy('首件(件)')}</th>
								<th width="10%" style="text-align: center;">${fns:fy('运费(元)')}</th>
								<th width="10%" style="text-align: center;">${fns:fy('续件(件)')}</th>
								<th width="10%" style="text-align: center;">${fns:fy('运费(元)')}</th>
								<th width="30%" style="text-align: center;">${fns:fy('操作')}</th>
							</thead>
							<tbody id="tbody">
							</tbody>
							<tfoot>
								<tr class="batchEditTr" style="display:none;">
									<td colspan="6">
										<input type="checkbox" id="selectAll"><span>${fns:fy('全选')}</span>
										<a href="javascript:void(0);" class="sui-btn btn-bordered" id="batchSet">${fns:fy('批量设置')}</a>
										<a href="javascript:void(0);" class="sui-btn btn-bordered" id="batchDelete">${fns:fy('批量删除')}</a>
									</td>
								</tr>
								<tr>
									<td colspan="6">
										<p  style="margin-bottom:5px;display: none;" id="errorMsg"><font color="red">
											<i class="sui-icon icon-pc-error-circle"></i> ${fns:fy('指定地区城市为空或指定错误')}　
											<i class="sui-icon icon-pc-error-circle"></i> ${fns:fy('首件、续件应输入正整数')}
											<i class="sui-icon icon-pc-error-circle"></i> ${fns:fy('运费应输入0.00至999.99的数字')}　
										</font></p>
										<a href="javascript:void(0);" class="sui-btn btn-bordered" id="setFreight">${fns:fy('添加一行')}</a>
										<a href="javascript:void(0);" class="sui-btn btn-bordered" id="batchEdit" style="display:none;">${fns:fy('批量操作')}</a>
										<a href="javascript:void(0);" class="sui-btn btn-bordered" id="cancelBatchEdit" style="display:none;">${fns:fy('取消批量')}</a>
									</td>
								</tr>
							</tfoot>
						</table>
						</dd>
						<dd>
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
						</dd>
					</dl>
				</dd>
			</form>
		</dl>
	</div>
</div>

<!-- itemIndex用来做添加行中name的下标 -->
<input type="hidden" value="${itemSize}" id="itemIndex"/>
<!-- 选择地区弹出框开始 -->
<div id="areaModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
	<div class="modal-dialog" id="areaSelect">
		<div class="modal-content">
			<div class="modal-body" style="padding: 0;">
				<form class="sui-form" method="post">
					<table class="sui-table areaTable">
					<tbody>
						<c:forEach items="${fns:getDictList('large_area')}" var="la">
							<tr>
								<th class="firstTd">
									<span>
								        <input type="checkbox" class="lageArea" value="${la.value}"/>
								        <label for="J_City_84">${la.label}</label>
							        </span>
								</th>
								<c:forEach items="${areaList}" var="province">
									<c:if test="${la.value eq province.largeArea}">
									<td>
										<span class="provinces">
											<span class="areas">
										        <input type="checkbox" class="province" id="J_City_84" value="${province.id}"/>
										        <label for="J_City_84" class="provinceName">${province.name}</label>
									        </span>
											<i class="sui-icon icon-tb-unfold currentCitys" onclick="currentCitys(this)"></i>
											<div class="city-sub">
												<c:forEach items="${areaList}" var="city">
													<c:if test="${province.id eq city.parent.id}">
														<span class="areas">
													        <input type="checkbox" class="city" parentId="${province.id}" value="${city.id}"/>
													        <label for="J_City_84" >${city.name}</label>
												        </span>
													</c:if>
												</c:forEach>
												 <p class="closeCity">
										          	<a href="javascript:void(0);" class="sui-btn btn-danger" onclick="coloseCurrentCity(this);">${fns:fy('关闭')}</a>
										         </p>
											</div>
										</span>
									</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
					</table>
				</form>
			</div>
			<div class="modal-footer" style="text-align: right;margin-right: 10px;">
				<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large selectAreaBtn">${fns:fy('确定')}</button>
				<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
			</div>
		</div>
	</div>
</div>
<!-- 选择地区弹出框结束 -->

<!-- 批量设置弹出框开始 -->
<div id="batchSetModal" style="display: none;">
	<form class="sui-form" method="post" style="margin:30px 30px 4px 67px;">
		<div>
			<label>${fns:fy('首件：')}</label>
			<input type="text" placeholder="" class="input-small firstItem" name="firstItem" value=""/>
			<label>${fns:fy('运费：')}</label>
			<div class="input-append" style="margin-bottom: 1.5px;">
				<input type="text" placeholder="" class="input-small firstPrice" name="firstPrice"/><span class="add-on small">￥</span>
			</div>
			<label>${fns:fy('续件：')}</label>
			<input type="text" placeholder="" class="input-small continueItem" name="continueItem" value=""/>
			<label>${fns:fy('运费：')}</label>
			<div class="input-append" style="margin-bottom: 1.5px;">
				<input type="text" placeholder="" class="input-small continuePrice" name="continuePrice"/><span class="add-on small">￥</span>
			</div>
		</div>
		<div class="modal-footer" style="text-align: right;margin:14px 10px 0px;">
			<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large batchSetBtn">${fns:fy('确定')}</button>
			<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
		</div>
	</form>
</div>
<!-- 批量设置弹出框结束 -->
<script type="text/template" id="add_area_Tpl" info="${fns:fy('添加地区')}">
	<tr class="appendTr">
		<td class="firstTableTd">
			<input type="checkbox" class="trCheck" style="{{d.editDisplay}}"/>
			<span style="margin-bottom: 0;" name="areaNames">{{d.areaNames}}</span>
			<input type="hidden" class="areaIds" name="{{d.itemIndex}}Ids" value="{{d.areaIds}}"/>
			<input type="hidden" class="areaNames" name="{{d.itemIndex}}Names" value="{{d.areaNames}}"/>
		</td>
		<td style="text-align: center;"><input type="text" placeholder="" class="input-small firstItem" ty="1" name="{{d.itemIndex}}firstItem" value="{{d.firstItem}}"/></td>
		<td style="text-align: center;">
			<div class="input-append">
				<input type="text" placeholder="" class="input-small firstPrice" ty="2" name="{{d.itemIndex}}firstPrice" value="{{d.firstPrice}}"/><span class="add-on small">￥</span>
			</div>
		</td>
		<td style="text-align: center;"><input type="text" placeholder="" class="input-small continueItem" ty="1" name="{{d.itemIndex}}continueItem" value="{{d.continueItem}}"/></td>
		<td style="text-align: center;">
			<div class="input-append">
				<input type="text" placeholder="" class="input-small continuePrice" ty="2" name="{{d.itemIndex}}continuePrice" value="{{d.continuePrice}}"/><span class="add-on small">￥</span>
			</div>
		</td>
		<td style="text-align: center;">
			<button type="button" class="sui-btn btn-primary editArea">${fns:fy('编辑')}</button>
			<a class="sui-btn btn-danger delLine" id="delLine">${fns:fy('删除')}</a>
		</td>
	</tr>
</script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/logistics/logisticsTemplateForm.js"></script>
</body>
</html>