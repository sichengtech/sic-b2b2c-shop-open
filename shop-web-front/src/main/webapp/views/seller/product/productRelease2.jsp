<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="isEdit" value ="${not empty productSpu.PId?true:false}"></c:set>
<title>${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.error{width: auto;}
	#setViewTable{border: solid 1px #F2DCBC;}
	#setViewTable th{background: #FFF5EC;}
	#setViewTable td{border-top: solid 1px #F2DCBC;}
	#setViewDiv{display:inline-block;vertical-align:top;margin-left:30px;}
	.spec_color{height:25px;margin-left: 15px;}
	.none{ display:none;}
	ul.attrul{ display:inline-block; margin-left:-4px;margin-left: 10px;}
	ul.attrul li{ margin-right:10px; display:inline-block;}
	li.attrli{ position:relative;}
	li.attrli input{width:150px;height:30px;text-indent:0.5rem;  background-color:#fff; border:1px solid #ccc;
		vertical-align:middle; line-height:30px;padding:3px 0!important;}
	li.attrli .local{ background:url(${ctxStatic}/sicheng-seller/images/bot_imgs.png) no-repeat;background-position:100% center;}
	li.attrli .bomb_con_style{ background-color:#fff; position:absolute;left:0px;z-index:999999; cursor:pointer;
		f3478c; border:1px solid #ccc; height:169px; overflow:auto;}
	li.attrli .bomb_con_style dd{background-color:#fff;height:28px;line-height:28px;color:#f3478c;font-size:12px;
		color:#666; text-indent:0.5em;}
	li.attrli .bomb_con_style dd:hover{ background-color:#33affc; color:#fff;}
	li.attrli .bomb_con_style dd.selected{ background-color:#33affc; color:#fff;}
	.carDiv{border: solid 1px #e6e6e6;margin-left: 2px;margin-top: 10px;width: 63%;height: 150px;overflow-y: auto;padding-bottom: 9px;}
	.carDiv li{margin-top: 9px;}
	.carDiv li a{color:#222;padding-left:10px;}
	#selectProductCar{margin-left: 2px;}
	.removeCar{color: red;padding-right: 5px;}
	sup {top: -0.2em;}
	select.deliverGoodsSelect{min-width: 120px;}
	.delStoreCategory{cursor: pointer;}
	.sui-form .input-control .sui-icon{top:7px;}
</style>
<script type="text/javascript">
	//商品sku的json数据，编辑商品时用来回显
	var productSkuJsonList ='${productSkuJsonList}';
	//商品图片
	var pictureMap='${pictureMap}';
	//车型
	var productCarlistMap='${productCarlistMap}';
	//商品类型
	var type='${productSpu.type}';

	//String转JSON
	pictureMap = JSON.parse(pictureMap);
	productSkuJsonList = JSON.parse(productSkuJsonList);
	productCarlistMap = JSON.parse(productCarlistMap);
</script>
<!-- jquery-ui -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ui-1.10.3.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productRelease2.js"></script>
<!-- jquery-ztree css -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
<!-- jquery-ztree js -->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box goods-add">
				<!-- 面包屑开始 -->
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('商品')}</li>
						<li>${fns:fy('商品管理')}</li>
						<li class="active">${fns:fy('商品')}${isEdit?fns:fy('编辑'):fns:fy('发布')}</li>
					</ul>
				</dt>
				<!-- 面包屑结束 -->
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block" style="margin: 10px;">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('1.发布商品第二步，填写商品的基本信息。')}</li>
								<li>${fns:fy('2.商品名称、品牌、价格、库存、图片、描述为必填项。')}</li>
								<li>${fns:fy('3.选择几个颜色要相应的上传几组图片。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<!-- 浮动导航开始 -->
				<div class="pordadd-position">
					<ul>
						<li><a href="#baseMsg">${fns:fy('基本信息')}</a></li>
						<li><a href="#tradeMsg">${fns:fy('交易信息')}</a></li>
						<li><a href="#specMsg">${fns:fy('规格图片')}</a></li>
						<li><a href="#detailMsg">${fns:fy('详情描述')}</a></li>
						<li><a href="#logisticsMsg">${fns:fy('物流信息')}</a></li>
						<li><a href="#driveMsg">${fns:fy('发票信息')}</a></li>
						<li><a href="#otherMsg">${fns:fy('其他信息')}</a></li>
					</ul>
				</div>
				<!-- 浮动导航结束 -->
				<!-- 引导条 开始 -->
				<div class="m10">
					<div class="sui-steps steps-large steps-auto">
						<div class="wrap">
							<div class="finished">
								<label><span class="round">1</span><span>${fns:fy('第一步：选择商品分类')}</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>
							</div>
						</div>
						<div class="wrap">
							<div class="current">
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
				<!-- 表单开始 -->
				<form class="sui-form form-inline goods-add-form sui-validate" method="post" id="inputForm" action="${ctxs}/product/productSpu/${isEdit?'edit2':'save3'}.htm"  novalidate="novalidate">
					<input type="hidden" name="pId" value="${productSpu.PId}"/>
					<input type="hidden" id="productSku" name="productSku" class="productSku" value=""/>
					<input type="hidden" name="section" class="section" value=""/>
					<input type="hidden" class="proSku" value="${productSkuList}"/>
					<input type="hidden" class="sign"  name="sign" value="${sign}"/>
					<input type="hidden" class="recommend"  name="recommend" value="${recommend}"/>
					<input type="hidden" class="isCopy"  name="isCopy" value="${isCopy}"/>
					<input type="hidden" name="productImgs" class="productImgs" value="${pictureMap}"/>
					<input type="hidden" name="isGift" class="isGift" value="${productSpu.isGift}"/>
					<input type="hidden" name="shelfTime" class="shelfTime" value="<fmt:formatDate value="${productSpu.shelfTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					<input type="hidden" name="createDate" class="createDate" value="<fmt:formatDate value="${productSpu.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					<input type="hidden" name="updateDate" class="updateDate" value="<fmt:formatDate value="${productSpu.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					<!-- 商品基本信息开始 -->
					<dd class="bs-docs-example">
						<h3 id="baseMsg">${fns:fy('商品基本信息')}</h3>
						<dl class="bor-t-fff">
							<dt>${fns:fy('商品分类：')}</dt>
							<dd>
								<ul class="sui-breadcrumb" style="display: inline">
									<input type="hidden" name="categoryId" value="${product_category_id}"/>
									<c:forEach items="${category_list}" var="category">
										<li>${category.name}</li>
									</c:forEach>
								</ul>
								<a href="${ctxs}/product/productSpu/save1.htm" class="sui-btn btn-small btn-primary">${fns:fy('选择分类')}</a>
							</dd>
						</dl>
						<dl>
							<dt><b style="color: #f00;">*</b>${fns:fy('商品名称：')}</dt>
							<dd>
								<input id="name" type="text" class="input-xxlarge" name="name" value="${productSpu.name}" data-rules="required|minlength=2|maxlength=128" data-error-msg="${fns:fy('商品名称必须是2-6位')}" data-empty-msg="${fns:fy('请填写商品名称')}">
								<div class="hint">${fns:fy('建议包含：品牌+型号+商品关键词 。')}</div>
							</dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('卖点描述：')}</dt>
							<dd>
								<input type="text" class="input-xxlarge" id="nameSub" name="nameSub" value="${productSpu.nameSub}"/>
								<div class="hint">${fns:fy('商品卖点可填写商品的简介或特点。')}</div>
							</dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('商品品牌：')}</dt>
							<dd>
								<input type="text" class="input-medium" readonly="readonly" id="brandName" name="brandName" value="${isEdit?productSpu.productBrand.name:productBrand.name}"/>
								<input type="hidden" class="" id="brandId" name="brandId" value="${isEdit?productSpu.productBrand.brandId:productBrand.brandId}"/>
								<input type="hidden" class="" id="storeId" value="${storeId}"/>
								<a href="javascript:void(0);" id="selectBrand" class="sui-btn btn-right btn-primary">${fns:fy('选择品牌')}</a>
								<a target="_blank" href="${ctxs}/product/productBrand/save1.htm">${fns:fy('新增品牌')}</a>
								<span></span>
								<div class="hint">${fns:fy('有利于通过品牌索引方式找到商品；如没有您想要的品牌，可从通过“品牌申请”功能发起申请。')}</div>
							</dd>
						</dl>
						<dl>
							<dt>${fns:fy('优惠：')}</dt>
							<dd>
								<input id="" type="text" class="input-xxlarge" name="benefit" value="${productSpu.benefit}" maxLength="128"/>
								<div class="hint">${fns:fy('请输入商品的优惠信息，用于在商品详情页中显示。')}</div>
							</dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('店铺分类：')}</dt>
							<dd>
			                    <div class="input-control control-right">
			                    	<input type="text" class="input-default input-large" name="storeCategoryName" readonly="readonly" value="${productSpu.storeCategory.name}"><i class="sui-icon icon-pc-error-circle delStoreCategory"></i>
			                    </div>
								<%-- <input id="" type="text" class="input-large" name="storeCategoryName" value="${productSpu.storeCategory.name}" maxLength="128"/> --%>
								<input id="" type="hidden" class="input-large" name="storeCategoryId" value="${productSpu.storeCategoryId}"/>
								<div class="hint">${fns:fy('选择店铺分类后，商品会发布到所选的店铺分类下，便于对商品进行管理。')}</div>
							</dd>
						</dl>
						<%-- <dl>
							<dt class="control-label">${fns:fy('适用车型：')}</dt>
							<dd>
								<button type="button" id="selectProductCar" class="sui-btn btn-right btn-primary">${fns:fy('选择车型')}</button>
								<input type="checkbox" id="all_car" name="allCar" value="1" style="margin-left: 20px;" ${productExt.allCar eq '1'?'checked="checked"':''}/><span>${fns:fy('全车系')}</span>
								<div class="carDiv">
									<ul class="carDiv-ul"></ul>
								</div>
								<div class="hint">${fns:fy('选择本商品适用的车系车型。')}</div>
							</dd>
						</dl> --%>
						<dl>
							<dt class="control-label pull-left">${fns:fy('商品参数：')}</dt>
							<dd>
								<input type="hidden" name="selectParams" class="selectParams"/>
								<c:forEach items="${productParamList}" var="proParam" varStatus="paramIndx">
									<span class="property" style="position:relative;"> 
										<input type="hidden" name="" class="valuesImg" value="${proParam.valuesImg}"/>
										<input type="hidden" name="" class="paramType" value="${proParam.type}"/>
										<input type="hidden" name="" class="format" value="${proParam.format}"/>
										<input type="hidden" name="" class="paramName" value="${proParam.name}"/>
										<span class="m-r-5">
											${proParam.isRequired=="1"?'<b style="color: #f00;">*</b>':''}
											${proParam.name}
										</span>
										<c:set var="paraVal" value=""></c:set>
										<c:if test="${not empty productParamMappingList}">
											<c:forEach items="${productParamMappingList}" var="pml">
												<c:if test="${pml.paramId eq proParam.paramId}">
													<c:set var="paraVal" value="${pml.value}"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
										<ul class="attrul">
											<li class="attrli">
												<input type="text" name="selectParam_${paramIndx.index}" class="local select_style attr_input selectParam" placeholder="${fns:fy('可直接输入内容')}" paramId="${proParam.paramId}" value="${paraVal}"
												${proParam.isRequired=="1"?'data-rule-required="true" maxlength="1024"':''}/>
												<div class="bomb_con_style attr_con none">
													<dl>
														<c:forEach items="${fn:split(proParam.paramValues,',')}" var="pra">
															<dd class="${paraVal eq pra?'selected=\"selected\"':''}" style="margin: 0;" value="${proParam.paramId}_${pra}">${pra}</dd>
														</c:forEach>
													</dl>
												</div>
											</li>
										</ul>
									</span>
								</c:forEach>
								<div class="hint">${fns:fy('商品参数用在按分类搜索时的筛选条件。')}</div>
							</dd>
						</dl>
					</dd>
					<!-- 商品基本信息结束 -->
					<!-- 商品交易信息开始 -->
					<dd class="bs-docs-example">
						<h3 id="tradeMsg">${fns:fy('商品交易信息')}</h3>
						<dl class="bor-t-fff">
							<dt class="control-label">${fns:fy('计量单位：')}</dt>
							<dd>
								<input type="text" class="input-medium" disabled="" id="unitName" name="unit" value="${productSpu.unit}">
								<input type="hidden" class="input-medium"  id="unitName1" name="unit" value="">
								<input type="hidden" class="" disabled="" id="unitId" name="unitId" value="0">
								<a href="javascript:void(0);" id="selectUnit" class="sui-btn btn-right btn-primary">${fns:fy('选择计量单位')}</a>
								<div class="hint">${fns:fy('有利于客户购买你的商品。')}</div>
							</dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('销售模式：')}</dt>
							<dd>
								<label data-toggle="radio" class="radio-pretty inline ${productSpu.type eq '1' || productSpu.type eq null?'checked':''} text-left">
									<input type="radio" ${productSpu.type eq '1' || productSpu.type eq null?'checked="checked"':''} name="type" value="1" class="retailRadio saleType"><span>${fns:fy('零售型')}</span>
								</label>
								<a href="#" data-placement="right" data-toggle="tooltip" title="<div><img src='${ctxStatic}/sicheng-seller/images/product/product_mod1.png'/></div>"><i class="sui-icon icon-pc-question-circle"></i></a>
								<label data-toggle="radio" class="radio-pretty inline ${productSpu.type eq '2'?'checked':''}" style="margin-left: 15px;">
									<input type="radio" ${productSpu.type eq '2'?'checked="checked"':''} name="type" value="2" class="saleRadio saleType"><span>${fns:fy('批发型')}</span>
								</label>
								<a href="#" data-placement="right" data-toggle="tooltip" title="<div><img src='${ctxStatic}/sicheng-seller/images/product/product_mod2.png'/></div>"><i class="sui-icon icon-pc-question-circle"></i></a>
								<%-- <label data-toggle="radio" class="radio-pretty inline ${productSpu.type eq '3'?'checked':''}" style="margin-left: 15px;">
									<input type="radio" ${productSpu.type eq '3'?'checked="checked"':''} name="type" value="3" class="retailAndSalRadio saleType"><span>${fns:fy('零售加批发')}</span>
								</label>
								<a href="#" data-placement="right" data-toggle="tooltip" title="<div><img src='${ctxStatic}/sicheng-seller/images/product/product_mod2.png'/></div>"><i class="sui-icon icon-pc-question-circle"></i></a> --%>
								<div class="hint">${fns:fy('请选择您的商品所属销售模式，“零售型”适用于多规格、且有可能规格价格不一的商品，但零售商品不可设置购买数量阶梯价格段；')}</div>
								<div class="hint">${fns:fy('“批发型”适用于多规格但价格统一的商品，此类型可设定整体购买数量阶梯价格段规则。根据选择的销售模式不同，销售规则将对应改变。')}</div>
							</dd>
						</dl>
						<dl id="saleRule" style="display:${productSpu.type ne '1' && productSpu.type ne null ?'':'none'}">
							<dt class="control-label">${fns:fy('销售规则：')}</dt>
							<dd>
								<div>
									<div style="display: inline-block;">
										<table id="wholesaleTable" border="0" cellpadding="0" cellspacing="0" class="spec_table">
											<thead id="spec-div-th">
												<tr>
													<th data-spec-title="priceRangeTitle0" class="w90">
														<span class="red" style="color: red;">*</span> <span class="specName" id="wholesaleStock">${fns:fy('购买数量')}</span>
													</th>
													<th class="w60">
														<span class="red" style="color: red;">*</span> <span class="specName" id="wholesalePrice">${fns:fy('商品单价')}</span>
													</th>
													<th></th>
												</tr>
											</thead>
											<tbody id="spec-div-body">
												<c:if test="${empty productSectionPricesList}">
													<tr data-goods-id="0">
														<td>${fns:fy('购买量')}
	 														<!-- <input name="stock_0" data-rule-required="true" data-rule-digits="true" class="text stock buyNumber" data-stock="" type="text" value="1" data-rule-ncrangenum="true" data-rule-ncdec="true"><em class="add-on add-on-unit">${fns:fy('座')}</em> -->
															<div class="input-append fixedFreightDiv">
																<input name="stock_0" data-rule-required="true" data-rule-digits="true" type="text" class="span2 input-fat input-small text stock buyNumber" value="1" data-rule-ncrangenum="true" data-rule-ncdec="true" maxLength="10">
																<span class="add-on add-on-unit" style="margin-left: -5px;">${fns:fy('件')}</span>
															</div>
														</td>
														<td>${fns:fy('及以上：')}
															<!-- <input class="text price salePrice" name="salePrice_0" data-rule-ncdec="true" data-goods-price0="0.00" type="text" data-rule-required="true" data-rule-number="number" data-rule-min="0.01" value="0.00"><em class="add-on">${fns:fy('元')}</em> -->
															<div class="input-append fixedFreightDiv">
																<input class="span2 input-fat input-small text price salePrice" name="salePrice_0" data-rule-ncdec="true" data-goods-price0="0.00" data-rule-required="true" type="text" data-rule-number="number" data-rule-min="0.01" value="0.00" maxLength="12">
																<span class="add-on" style="margin-left: -5px;">${fns:fy('元')}</span>
															</div>
														</td>
														<td></td>
													</tr>
												</c:if>
												<c:if test="${not empty productSectionPricesList}">
													<c:forEach items="${productSectionPricesList}" var="section" varStatus="sectIndex">
														<tr data-goods-id="0">
															<td>${fns:fy('起购量')}
																<div class="input-append fixedFreightDiv">
																	<input name="stock_${sectIndex.index}" data-rule-required="true" data-rule-digits="true" type="text" class="span2 input-fat input-small text stock buyNumber" value="${section.section}" data-rule-ncrangenum="true" data-rule-ncdec="true" maxLength="10">
																	<span class="add-on add-on-unit" style="margin-left: -5px;">${productSpu.unit}</span>
																</div>
															</td>
															<td>${fns:fy('及以上：')}
																<div class="input-append fixedFreightDiv">
																	<input class="span2 input-fat input-small text price salePrice" name="salePrice_0" data-rule-ncdec="true" data-goods-price0="0.00" data-rule-required="true" maxLength="12" type="text" data-rule-number="number" data-rule-min="0.01" value="${section.price}">
																	<span class="add-on" style="margin-left: -5px;">${fns:fy('元')}</span>
																</div>
															</td>
															<td>
																<c:if test="${(!sectIndex.first) && (sectIndex.last) }">
																	<a href="javascript:void(0);" class="sui-btn btn-small btn-danger" id="removeTr">${fns:fy('删除')}</a>
																</c:if>
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
										<div style="margin-top: 10px;" id="addLineBtn">
											<button type="button" class="sui-btn btn-xlarge"><i class="sui-icon icon-plus-sign"></i>${fns:fy('添加价格区间')}</button>
										</div>
									</div>
									<div id="setViewDiv">
										<table id="setViewTable" border="0" cellpadding="0" cellspacing="0" class="spec_table">
											<thead id="spec-div-th">
												<tr>
													<th data-spec-title="priceRangeTitle0" class="w90">
														<span class="red" style="color: red;">*</span> <span class="specName" id="price">${fns:fy('设置预览')}</span>
													</th>
												</tr>
											</thead>
											<tbody id="spec-div-body">
												<c:if test="${empty productSectionPricesList}">
													<tr data-goods-id="0"  name="0">
														<td>${fns:fy('销售规则')}<span class="rule">1</span>${fns:fy('：当商品购买数量≥')}<span class="buyNumberView">1</span>${fns:fy('时，售价为')}<span class="priceView">0.00</span>${fns:fy('元')}/<span class="unitNameView">${fns:fy('件')}</span></td>
													</tr>
												</c:if>
												<c:if test="${not empty productSectionPricesList}">
													<c:forEach items="${productSectionPricesList}" var="section" varStatus="sectIndex">
														<tr data-goods-id="0"  name="${sectIndex.index}">
															<td style="text-align:center;">${fns:fy('销售规则')}<span class="rule">${sectIndex.index+1}</span>${fns:fy('：当商品购买数量≥')}<span class="buyNumberView">${section.section}</span>${fns:fy('时，售价为')}<span class="priceView">${section.price}</span>${fns:fy('元')} / <span class="unitNameView">${productSpu.unit}</span></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
								<div class="hint">${fns:fy('该项设置用于设定商品最低起购量及更多起购量时所对应的价格，最多可设3个起购点，即形成3段阶梯价格。购买数量与商品单价为必填项。')}</div>
								<div class="hint">${fns:fy('操作技巧：阶梯起定价应为购买数量与价格成反比，即购买数量越大价格越低。如商品有规格设定，可从下方规格表中具体进行价格设置。')}</div>
							</dd>
						</dl>
					</dd>
					<!-- 商品交易信息结束 -->
					<!-- 商品规格及图片开始 -->
					<dd class="bs-docs-example">
						<h3 id="specMsg">${fns:fy('商品规格及图片')}</h3>
						<dl class="bor-t-fff">
							<dt class="control-label">${fns:fy('商品规格：')}</dt>
							<dd class="ml0">
								<div class="spec-title bor-none">
									<h5>${fns:fy('规格值')}<span>${fns:fy('（通过选择规格值建立商品与商品规格的关系）')}</span></h5>
					
								</div>
								<ul class="spec" id="specPanel">
									<c:forEach items="${productSpecList}" var="spec" varStatus="specIndex">
										<c:set var="spec1" value="${productSkuList[0].spec1}"></c:set>
										<c:set var="spec2" value="${productSkuList[0].spec2}"></c:set>
										<c:set var="spec3" value="${productSkuList[0].spec3}"></c:set>
										<dl id="spec_${specIndex.index}">
											<dt id="spec-h4-1">
												<c:choose>
													<c:when test="${not empty spec1 && fn:split(spec1, '_')[0] eq spec.specId}">
														<span class="spec_title" style="display: none;">${fn:split(spec1, '_')[1]}</span>
														<input type="text" value="${fn:split(spec1, '_')[1]}" class="input-small spec_input select_spec" id="${fn:split(spec1, '_')[0]}" maxLength="10"/>
													</c:when>
													<c:when test="${not empty spec2 && fn:split(spec2, '_')[0] eq spec.specId}">
														<span class="spec_title" style="display: none;">${fn:split(spec2, '_')[1]}</span>
														<input type="text" value="${fn:split(spec2, '_')[1]}" class="input-small spec_input select_spec" id="${fn:split(spec2, '_')[0]}" maxLength="10"/>
													</c:when>
													<c:when test="${not empty spec3 && fn:split(spec3, '_')[0] eq spec.specId}">
														<span class="spec_title" style="display: none;">${fn:split(spec3, '_')[1]}</span>
														<input type="text" value="${fn:split(spec3, '_')[1]}" class="input-small spec_input select_spec" id="${fn:split(spec3, '_')[0]}" maxLength="10"/>
													</c:when>
													<c:otherwise>
														<span class="spec_title">${spec.name}</span>
														<input type="text" value="${spec.name}" class="input-small spec_input" id="${spec.specId}" style="display: none;" maxLength="10"/>
													</c:otherwise>
												</c:choose>
											</dt>
											<dd class="spec-value-box">
												<c:if test="${fn:length(skuMap)> '0'}">
													<c:set var="stat" value="0"></c:set>
													<c:set var="begin" value="0"></c:set>
													<c:forEach items="${skuMap}" var="map" varStatus="mapIndex">  
														<c:if test="${stat eq '0'}">
															<c:if test="${not empty map.key && fn:split(map.key, '_')[0] eq spec.specId}">
																<c:set var="stat" value="1"></c:set>
																<c:set var="begin" value="${fn:length(map.value)}"></c:set>
																<c:forEach items="${map.value}" var="va" varStatus="index">
																	<div style="display: inline-block;width: 20%;">
																		<label class="spec-val inline" style="width:auto;">
																			<input type="checkbox" class="checkbox spec_checkbox" checked="checked" id="spec-value-checkbox-1-15" style="margin-right: 5px;"/>
																			<span class="spec_view" id="spec-value-text-1-15" style="display: none;">${va}</span>
																			<input type="text" value="${va}" class="input-medium spec_input" id="${spec.specId}${index.index}" maxLength="20"/>
																		</label>
																	</div>
																</c:forEach>
															</c:if>
														</c:if>
													</c:forEach>
													<c:forEach items="${fn:split(spec.specValues, ',')}" var="va" begin="${begin}" varStatus="index">
														<div style="display: inline-block;width: 20%;">
															<c:set var="stat" value="1"></c:set>
															<label class="spec-val inline" style="width:auto;">
																<input type="checkbox" class="checkbox spec_checkbox" id="spec-value-checkbox-1-15" style="margin-right: 5px;"/>
																<span class="spec_view" id="spec-value-text-1-15">${va}</span>
																<input type="text" value="${va}" class="input-medium spec_input" style="display: none;" id="${spec.specId}${index.index}" maxLength="20"/>
															</label>
														</div>
													</c:forEach>
												</c:if>
												<c:if test="${fn:length(skuMap) eq '0'}">
													<c:forEach items="${fn:split(spec.specValues, ',')}" var="va" varStatus="index">
														<div style="display: inline-block;width: 20%;">
															<label class="spec-val inline" style="width:auto;">
																<input type="checkbox" class="checkbox spec_checkbox" id="spec-value-checkbox-1-15" style="margin-right: 5px;"/>
																<span class="spec_view" id="spec-value-text-1-15">${va}</span>
																<input type="text" value="${va}" class="input-medium spec_input" style="display: none;" id="${spec.specId}${index.index}" maxLength="20"/>
															</label>
														</div>
													</c:forEach>
												</c:if>
											</dd>
										</dl>
									</c:forEach>
								</ul>
							</dd>
							<dd>
								<p class="hint">
									${fns:fy('可勾选商品对应的规格及规格值，规格及规格值名称都可以自定义；当勾勾选两种不同规格的规格值后将组合成一种商品SKU，可从下方表格中进行具体设定。规格可自定义添加，但最多不超过3组。')}
								</p>
							</dd>
							<div class="productSpecs"></div>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('价格和库存：')}</dt>
							<dd>
								<div class="spec-div" style="overflow-x:auto; "></div>
								<p class="hint"> ${fns:fy('点击')} <i class="fa fa-pencil-square-o" aria-hidden="true"></i> ${fns:fy('可批量修改所在列的值。')} <br>
							 	${fns:fy('当规格值较多时，可在操作区域通过滑动滚动条查看超出隐藏区域。')}</p>
							</dd>
						</dl>
						<dl class="purchasingAmountDl"  style="display:${productSpu.type eq '1' || productSpu.type eq null ?'':'none'}">
							<dt class="control-label">${fns:fy('最小起订量：')}</dt>
							<dd>
								<input id="purchasingAmount" type="text" class="input-medium" name="purchasingAmount" value="${empty productSpu.purchasingAmount?'1':productSpu.purchasingAmount}">
							</dd>
						</dl>
						<dl class="specImg">
							<dt class="control-label product-img-title"><b style="color: #f00;">*</b>${fns:fy('商品图片：')}</dt>
						</dl>
					</dd>
					<!-- 商品规格及图片结束 -->
					<!-- 商品描述开始 -->
					<dd class="bs-docs-example">
						<h3 id="detailMsg">${fns:fy('商品详情描述')}</h3>
						<dl class="bor-t-fff specImg">
							<dt class="control-label"><b style="color: #f00;">*</b> ${fns:fy('商品描述：')}</dt>
							<dd>
								<div class="controls">
									<!-- 加载编辑器的容器 -->
								    <script id="container" name="introduction" type="text/plain">${detail.introduction}</script>
								    <!-- 配置文件 -->
								    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.config.js"></script>
								    <!-- 编辑器源码文件 -->
								    <script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/ueditor.all.min.js"></script>
								    <!-- 实例化编辑器 -->
								    <script type="text/javascript">
								        var ue = UE.getEditor('container');
								      	//传入参数表,添加到已有参数表里
								        ue.ready(function() {
								            ue.execCommand('serverparam', {'accessKey': '${accessKey}'});
								        });
								    </script>
							    </div>
							    <div class="hr8" id="editorPicPanel">
									<div class="btn-upload-box" style="margin-top: 10px;">
										<a href="javascript:void(0);" class="sui-btn btn-bordered btn-xlarge btn-primary imgupload2" isDetail="1"><i class="sui-icon icon-pc-picture"></i> ${fns:fy('打开相册图片')}</a>
									</div>
									<p class="hint">${fns:fy('图片大小要求：为了图片更好的显示，建议使用宽度不超过960像素的图片。')}</p>
							    </div>
							</dd>
						</dl>
					</dd>
					<!-- 商品描述结束 -->
					<!-- 商品物流信息开始 -->
					<dd class="bs-docs-example">
						<h3 id="logisticsMsg">${fns:fy('商品物流信息')}</h3>
						<dl class="">
							<dt class="control-label">${fns:fy('运费：')}</dt>
							<dd>
								<label data-toggle="invoice" class="radio-pretty inline ${productSpu.expressType eq '3' || productSpu.expressType eq null?'checked':'' }">
									<input type="radio" name="expressType" value="3" ${productSpu.expressType eq '3' || productSpu.expressType eq null ?'checked="checked"':'' } class="expressToPayRadio"><span>${fns:fy('卖家承担运费')}</span>
								</label>
							</dd>
							<dd>
								<label data-toggle="invoice" class="radio-pretty inline ${productSpu.expressType eq '2'?'checked':'' }">
									<input type="radio" name="expressType" value="2" ${productSpu.expressType eq '2'?'checked="checked"':'' } class="expressToPayRadio"><span>${fns:fy('运费到付(买家承担运费)')}</span>
								</label>
							</dd>
							<dd>
								<label data-toggle="invoice" class="radio-pretty inline ${productSpu.expressType eq '0'?'checked':'' }">
									<input type="radio" ${productSpu.expressType eq '0'?'checked="checked"':'' } name="expressType" class="fixedFreightRadio" value="0"><span>${fns:fy('使用固定运费')}</span>
									<!-- <input type="text" class="input-medium" value="" name="expressPrice">${fns:fy('元')} -->
								</label>
								<div class="input-append fixedFreightDiv" style="${productSpu.expressType eq '0' || productSpu.expressType eq null?'':'display:none' }">
									<input id="appendedInput" type="text" class="span2 input-fat input-small" value="${productSpu.expressPrice eq null?'0':productSpu.expressPrice}" name="expressPrice" maxLength="123">
									<span class="add-on" style="margin-left: -5px;">${fns:fy('元')}</span>
								</div>
							</dd>
							<c:if test="${isEnglish eq '0'}">
								<dd>
									<label data-toggle="invoice" class="radio-pretty inline ${productSpu.expressType eq '1'?'checked':'' }">
										<input type="radio" name="expressType" value="1" ${productSpu.expressType eq '1'?'checked="checked"':'' } class="expressTypeRadio"><span>${fns:fy('使用运费规则')}</span>
									</label>
									<div id="freigthtTemplateDiv" style="${productSpu.expressType eq '1'?'display:inline-block':'display:none'}">
										<select class="valid" name="ltId">
											<option value="" class="default">${fns:fy('--请选择--')}</option>
											&lt;%&ndash; <c:forEach items="${templateList}" var="template">
											<option value="${template.ltId}" ${productSpu.ltId eq template.ltId?'selected = "selected"' :'' }>${template.name}</option>
										</c:forEach> &ndash;%&gt;
										</select>
										<a target="_blank" href="${ctxs}/logistics/logisticsTemplate/save1.htm" id="selectUnit" class="sui-btn btn-primary"><i class="sui-icon icon-tb-deliver "></i>${fns:fy('新增物流规则')}</a>
										<a href="javascript:void(0);" class="sui-btn btn-primary" id="refreshTemplate" ltId="${productSpu.ltId}"><i class="sui-icon icon-refresh"></i>${fns:fy('刷新')}</a>
									</div>
									<div class="hint">${fns:fy('新增物流规则后，点击"刷新"按钮可以看到新添加的物流规则。')}</div>
								</dd>

							</c:if>
						</dl>
						<dl style="display:${productSpu.expressType eq '1'?'':'none'};" class="logistics-param-dl">
							<dt class="control-label">${fns:fy('物流参数：')}</dt>
							<dd>
								${fns:fy('重量：')}<div class="input-append"><input id="appendedInput" type="text" class="span2 input-fat input-small" value="${empty productSpu.weight?'1':productSpu.weight}" name="weight"/><span class="add-on">KG</span></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								${fns:fy('体积：')}<div class="input-append"><input id="appendedInput" type="text" class="span2 input-fat input-small" value="${empty productSpu.volume?'1':productSpu.volume}" name="volume"/><span class="add-on">m<sup>3</sup></span></div>
								<div class="hint">${fns:fy('如选择物流规则具有体积或重量运费时，需设定商品物流参数，默认为“1”单位，小数点后保留2位。')}</div>
							</dd>
						</dl>
						<dl class="">
							<dt class="control-label">${fns:fy('发货期限：')}</dt>
							<dd>
								<select class="deliverGoodsSelect" name="deliverGoodsDate">
									<c:forEach items="${fns:getDictList('product_deliver_goods_date')}" var="date">
										<option ${date.value == productSpu.deliverGoodsDate?"selected":""} value="${date.value}">${date.label}</option>
									</c:forEach>
								</select>
								<div class="hint">${fns:fy('自买家付款之日起')}</div>
							</dd>
						</dl>
					</dd>
					<!-- 商品物流信息结束 -->
					<!-- 商品其他信息开始 -->
					<dd class="bs-docs-example">
						<h3 id="otherMsg">${fns:fy('其他信息')}</h3>
						<dl>
							<dt class="control-label">${fns:fy('市场价：')}</dt>
							<dd>
								<div class="input-append">
									<input id="appendedInput" type="text" class="span2 input-fat input-small" value="${productSpu.marketPrice eq null?'':productSpu.marketPrice}" name="marketPrice" maxLength="">
									<span class="add-on" style="margin-left: -5px;">${fns:fy('元')}</span>
								</div>
								<div class="hint">${fns:fy('用于在首页和商品详情页中显示。')}</div>
							</dd>
						</dl>
						<dl class="">
							<dt class="control-label">${fns:fy('本商品的发票：')}</dt>
							<dd>
								<label data-toggle="invoice" class="radio-pretty inline ${productSpu.invoice eq '0' || productSpu.invoice eq null?'checked':'' }">
									<input type="radio" ${productSpu.invoice eq '0' || productSpu.invoice eq null?'checked="checked"':'' } name="invoice" value="0"><span>${fns:fy('无发票')}</span>
								</label>
								<label data-toggle="invoice" class="radio-pretty inline ${productSpu.invoice eq '1'?'checked':'' }">
									<input type="radio" ${productSpu.invoice eq '1'?'checked="checked"':'' } name="invoice" value="1"><span>${fns:fy('有发票')}</span>
								</label>
								<div class="hint">${fns:fy('选择"无发票"，买家在下单时不能填写发票信息；选择"有发票"，买家在下单时可以填写发票信息。')}</div>
							 </dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('发布：')}</dt>
							<dd>
								<label data-toggle="status" class="radio-pretty inline ${productSpu.publish eq '1' || productSpu.publish eq null?'checked':''}">
									<input type="radio" ${productSpu.publish eq '1' || productSpu.publish eq null?'checked="checked"':'' } name="publish" value="1"><span>${fns:fy('立即发布')}</span>
								</label>
								<label data-toggle="status" class="radio-pretty inline ${productSpu.publish eq '0'?'checked':''}">
									<input type="radio" ${productSpu.publish eq '0'?'checked="checked"':'' } name="publish" value="0"><span>${fns:fy('放入仓库')}</span>
								</label>
								<div class="hint">${fns:fy('选择"立即发布"，在保存后或管理员审核成功后商品会直接成为在售商品，否则商品就会放入仓库中，需要商家手动上架。')}</div>
							</dd>
						</dl>
						<dl>
							<dt class="control-label">${fns:fy('推荐：')}</dt>
							<dd>
								<label data-toggle="isRecommend" class="radio-pretty inline ${productSpu.isRecommend eq '1' || productSpu.isRecommend eq null?'checked':'' }">
									<input type="radio" ${productSpu.isRecommend eq '1' || productSpu.isRecommend eq null?'checked="checked"':'' } name="isRecommend" value="1"><span>${fns:fy('推荐')}</span>
								</label>
								<label data-toggle="isRecommend" class="radio-pretty inline ${productSpu.isRecommend eq '0'?'checked':'' }">
									<input type="radio" ${productSpu.isRecommend eq '0'?'checked="checked"':'' } name="isRecommend" value="0"><span>${fns:fy('不推荐')}</span>
								</label>
								<div class="hint">${fns:fy('选择"推荐",该商品会在本店铺商品的详情页的"掌柜推荐"模块显示。')}</div>
							</dd>
						</dl>
						<%-- <dl>
							<dt class="control-label">${fns:fy('促销活动：')}</dt>
							<dd>
								<label data-toggle="action" class="radio-pretty inline ${productSpu.action eq '1' || productSpu.action eq null?'checked':'' }">
									<input type="radio" ${productSpu.action eq '1' || productSpu.action eq null?'checked="checked"':'' } name="action" value="1"><span>${fns:fy('参加')}</span>
								</label>
								<label data-toggle="action" class="radio-pretty inline ${productSpu.action eq '0'?'checked':'' }">
									<input type="radio" ${productSpu.action eq '0'?'checked="checked"':'' } name="action" value="0"><span>${fns:fy('不参加')}</span>
								</label>
							</dd>
						</dl> --%>
					</dd>
					<!-- 商品其他信息结束 -->
					<!-- <dd class="bs-docs-example"></dd> -->
					<div class="clear"></div>
					<div class="text-align pb20">
						<shiro:hasPermission name="product:productSpu:edit">
							<button type="submit" name="submitBtn" class="sui-btn btn-xlarge btn-primary" value="1">${fns:fy('保存2')}</button>
							<button type="submit" name="submitBtn" class="sui-btn btn-xlarge btn-primary" value="2">${fns:fy('保存并增加新产品')}</button>
						</shiro:hasPermission>
					</div>
				</form>
			</dl>
		</div>
	</div>
	<script type="text/template" id="productCar_tpl" info="已选车型">
		<li>
			<input class="carIds" type="hidden" value="{{d.carIds}}" name="carIds">
			<a href="javaScript:void(0);"><i class="sui-icon icon-tb-close removeCar"></i>{{d.name}}</a>
		</li>
	</script>
	<script type="text/template" id="spec_salePriceTh_tpl" info="价格库存表批发价th的模板">
		<th data-spec-title="priceRangeTitle0" class="salePriceTh">
		<span class="specName" id="sectionPrice">${fns:fy('起购量≥')}<span id="{{d.id}}">{{d.newBuyNumber}}</span></span></th>
	</script>
	<script type="text/template" id="spec_salePriceTd_tpl" info="价格库存表中价格td的模板">
		<td class="salePriceTd" style="text-align:center;">
			<div class="input-append fixedFreightDiv">
				<input class="span2 input-fat input-small text price" name='price' data-rule-ncdec="true" data-goods-price0="0.00" type="hidden" data-rule-number="number" data-rule-required="true" data-rule-min="0.01" value="{{d.price}}">
				<span class="price">{{d.price}}</span>
				<span>${fns:fy('元')}</span>
			</div>
		</td>
	</script>
	<script type="text/template" id="spec_table_tpl" info="价格库存表模板">
		<table id="spec_div" border="0" cellpadding="0" cellspacing="0" class="spec_table"></table>
	</script>
	<script type="text/template" id="spec_tableTh_tpl" info="价格库存表th模板">
		<th class=""><span class="specName {{d.id}}" id="spec{{d.indx}}" specId="{{d.id}}">{{d.val}}</span></th>
	</script>
	<script type="text/template" id="spec_tableTh_tpl2" info="价格库存表th模板">
		<th class="stockTh"><span class="specName" id="stock"><span class="red" style="color: red;">*</span>${fns:fy('库存')}</span></th><th style="width:70px;"><span class="specName" id="sn">${fns:fy('商家编号')}</span></th>
	</script>
	<script type="text/template" id="spec_salePriceTh_tpl1" info="价格库存表批发价th的模板">
		<th class="retailPriceTh"><span class="red" style="color: red;">*</span><span class="specName" id="price">${fns:fy('零售价')}</span></th>
		<th class="salePriceTh" style="display: none;">
		<span class="specName" id="sectionPrice">${fns:fy('起购量≥')} <span id="0">1</span></span></th>
	</script>
	<script type="text/template" id="spec_salePriceTh_tpl2" info="价格库存表批发价th的模板">
		<th class="retailPriceTh" style="display: none;"><span class="red" style="color: red;">*</span><span class="specName" id="price">${fns:fy('价格')}</span></th>
		<th class="salePriceTh"><span class="specName" id="sectionPrice">${fns:fy('起购量≥')} <span id="0">1</span></span></th>
	</script>
	<script type="text/template" id="spec_salePriceTh_tpl3" info="价格库存表批发价th的模板">
		<th class="retailPriceTh"><span class="red" style="color: red;">*</span><span class="specName" id="price">${fns:fy('零售价')}</span></th>
		<th class="salePriceTh"><span class="specName" id="sectionPrice">${fns:fy('起购量≥')} <span id="0">1</span></span></th>
	</script>
	<script type="text/template" id="spec_tableTd_tpl" info="价格库存表批发价th的模板">
		<td style="text-align: center;" class="{{d.id}}">{{d.val}}</td>
	</script>
	<script type="text/template" id="fixedFreightDiv_tpl1" info="价格库存表批发价th的模板">
		<div class="input-append fixedFreightDiv">
			<input class="span2 input-fat input-small text price" name='price_{{d.index}}'data-rule-ncdec="true" data-goods-price0="0.00" type="text" maxLength="12" data-rule-number="number" data-rule-required="true" data-rule-min="0.01" value='{{d.price}}'>
			<span class="add-on add-on-unit" style="margin-left: -5px;">${fns:fy('元')}</span>
		</div>
	</script>
	<script type="text/template" id="fixedFreightDiv_tpl2" info="价格库存表批发价th的模板">
		<div class="input-append fixedFreightDiv">
			<input class="span2 input-fat input-small text price" name='price_{{d.index}}'data-rule-ncdec="true" data-goods-price0="0.00" type="hidden" data-rule-number="number" data-rule-required="true" data-rule-min="0.01" value='0.00'>
			<span class="price">{{d.price}}</span>
			<span>${fns:fy('元')}</span>
		</div>
	</script>
	<script type="text/template" id="retailPriceTd_tpl" info="价格库存表批发价th的模板">
		<td class="retailPriceTd" style="text-align: center;display:{{d.display}}">{{d.priceTd}}</td>
	</script>
	<script type="text/template" id="salePriceTd_tpl" info="价格库存表零售价th的模板">
		<td class="salePriceTd" style="text-align: center;display:{{d.display}}">{{d.priceTd}}</td>
	</script>
	<script type="text/template" id="stockTd_tpl" info="价格库存表库存th的模板">
		<td class="stockTd" style="text-align: center;"><input name='stock_{{d.index}}' data-rule-required="true" data-rule-digits="true" class="text stock" data-stock="" type="text" value='{{d.stock}}' maxLength="10"/></td>
	</script>
	<script type="text/template" id="snTd_tpl" info="价格库存表商家编号th的模板">
		<td style="text-align: center;"><input type="text" class="input-small" name="sn" value='{{d.sn}}' maxLength="24"/></td>
	</script>
	<script type="text/template" id="selectImg_Tpl" info="上传图片模板">
		<li class="upload-thumb" picId={{d.picId}}>
			<a href="javascript:void(0)" class="del" title="${fns:fy('查看')}"><i class="sui-icon icon-tb-close"></i></a>
			<a href="javascript:void(0)" class="del removeImg" title="${fns:fy('移除')}"><i class="sui-icon icon-tb-close"></i></a>
			<input type="hidden" name="productImg_input" value="{{d.src1}}"/>
			<img src="{{d.path}}" src1="{{d.src1}}" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
		</li>
	</script>
	<script type="text/template" id="specImg_Tpl" info="打开商品相册模板">
		<dd class="product-img" isDefault="{{d.isDefault}}">
			<div class="spec_color">
				<span class="{{d.specnId}}" id="">{{d.specn}}</span>
				<span class="specvId {{d.specvId}}" id="">{{d.specv}}</span>
			</div>
			<a href="javascript:void(0);" class="sui-btn btn-bordered btn-xlarge btn-primary ml10 imgupload2"><i class="sui-icon icon-pc-picture"></i> ${fns:fy('打开相册图片')}</a>
			<ul></ul>
			<div class="clear"></div>
		</dd>
	</script>
	<script type="text/template" id="detailImg_Tpl" info="商品描述图片描述">
		<p picId={{d.picId}}>
			<img src="{{d.path}}"/>
		</p>
	</script>
	<script type="text/template" id="logistics_Tpl" info="物流模板下拉选模板">
		<option value="{{d.ltId}}" {{d.select}}>{{d.name}}</option>
	</script>
	<script type="text/template" id="wholesaledel_Tpl" info="销售规则删除按钮">
		<a href="javascript:void(0);" class="sui-btn btn-small btn-danger" id="removeTr">${fns:fy('删除')}</a>
	</script>
	<script type="text/template" id="wholesale_Tpl" info="销售规则添加一行的模板">
		<tr data-goods-id="0">
			<td>${fns:fy('购买量')}
				<div class="input-append fixedFreightDiv">
					<input name="stock_{{d.index}}" data-rule-required="true" data-rule-digits="true" type="text" class="span2 input-fat input-small text stock buyNumber" value="{{d.buyNumber}}" maxLength="10" data-rule-min="{{d.minData}}"/>
					<span class="add-on add-on-unit" style="margin-left: -5px;">{{d.unitName}}</span>
				</div>
			</td>
			<td>${fns:fy('及以上：')}
				<div class="input-append fixedFreightDiv">
					<input class="span2 input-fat input-small text price salePrice" name="salePrice_{{d.index}}" data-rule-ncdec="true" data-goods-price0="0.00" data-rule-required="true" type="text" data-rule-number="number" data-rule-min="0.01" value="0.00" maxLength="12"/>
					<span class="add-on" style="margin-left: -5px;">${fns:fy('元')}</span>
				</div>
			</td>
			<td class="removeTd"><a href="javascript:void(0);" class="sui-btn btn-small btn-danger" id="removeTr">${fns:fy('删除')}</a></td>
		</tr>
	</script>
	<script type="text/template" id="setViewTr_Tpl" info="销售规则预览tr">
		<tr data-goods-id="0" name="{{d.name}}">
			<td style="text-align:center;">${fns:fy('销售规则')}<span class="rule">{{d.rule}}</span>${fns:fy('：当商品购买数量≥')}<span class="buyNumberView">{{d.buyNumber}}</span>${fns:fy('时，售价为')}<span class="priceView">0.00</span>${fns:fy('元')} / <span class="unitNameView">{{d.unitName}}</span>。</td>
		</tr>
	</script>
	<script type="text/template" id="spec_input_Tpl" info="销售规则预览tr">
		<input type="hidden" name="productSpec" value="{{d.spec}}"/>
	</script>
</body>
</html>