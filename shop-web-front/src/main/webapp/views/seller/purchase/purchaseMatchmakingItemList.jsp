<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:fy('撮合采购详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.purchase-MatchmakingItem-list textarea{padding: 5px;background-color: #f9f6f600}
	.sui-msg.msg-block {margin: 10px !important;}
</style>
</head>
<body>
	<div class="main-content clearfix purchase-MatchmakingItem-list purchaseBatch">
	    <div class="main-center">
			<dl class="box">
                <!-- <dt>
                    <div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 撮合采购详情</div>
                    <i class="sui-icon icon-tb-list"></i> 撮合采购详情</span>
                    <%@ include file="/views/seller/include/functionBtn.jsp"%>
                </dt> -->
                <dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('撮合采购详情')}</span>
						<span style="margin-top: 5px; margin-left: 10px;" class="">
							<a href="javascript:void(0)" id="toolbar_operate_tips" class="sui-btn btn-bordered btn-small btn-warning" data-placement="bottom" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('操作提示')}">
								<i class="sui-icon icon-pc-light"></i>
							</a>
						</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('会员中心')}</li>
						<li>${fns:fy('供采管理')}</li>
						<li>${fns:fy('撮合采购详情')}</li>
					</ul>
				</dt>
                <address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('展示当前撮合采购的详情。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
                <dd class="clearfix ml10 mr10 mt10">
					<c:if test="${fn:length(page.list)==0}">
						<div class="noData" style="margin-top: 0px;"><span>${fns:fy('暂无数据')}</span></div>
					</c:if>
					<c:if test="${fn:length(page.list)!=0}">
                    <table class="sui-table table-bordered-simple">
                        <thead>
                            <tr>
                            	<th width="15%">${fns:fy('产品名称')}</th>
                            	<th width="15%">${fns:fy('规格型号')}</th>
                            	<th width="10%">${fns:fy('品牌')}</th>
                            	<th width="10%">${fns:fy('数量')}</th>
                            	<th width="10%">${fns:fy('采购单价(元)')}</th>
                            	<th width="10%">${fns:fy('报价单价(元)')}</th>
                            	<th width="15%">${fns:fy('采购备注')}</th>
                            	<th width="15%">${fns:fy('报价备注')}</th>
                            </tr>
                        </thead>
                        <tbody id="tbody">
                        	<c:forEach items="${page.list}" var="purchaseTradeOrderItem">
                        	<tr>
                            	<td width="15%">${purchaseTradeOrderItem.purchaseItem.name}</td>
                            	<td width="15%">${purchaseTradeOrderItem.purchaseItem.model}</td>
                            	<td width="10%">${purchaseTradeOrderItem.purchaseItem.brand}</td>
                            	<td width="10%">${purchaseTradeOrderItem.purchaseItem.amount}${purchaseTradeOrderItem.purchaseItem.unit}</td>
                            	<td width="10%">${purchaseTradeOrderItem.purchaseItem.priceRequirement}</td>
                            	<td width="10%">${purchaseTradeOrderItem.offerPrice}${fns:fy('元')}</td>
                            	<td width="15%">
                            		<textarea rows="2" cols="20"  disabled="disabled">${purchaseTradeOrderItem.purchaseItem.purchaseRemarks}</textarea>
                            	</td>
                            	<td width="15%">
                            		<textarea rows="2" cols="20"  disabled="disabled">${purchaseTradeOrderItem.offerRemarks}</textarea>
                            	</td>
                            </tr>
                        	</c:forEach>
                        </tbody>
	                </table>
        			<%@ include file="/views/seller/include/page.jsp"%>
	           		</c:if>
	            </dd>
	        </dl>
	    </div>
	</div>
</body>
</html>
