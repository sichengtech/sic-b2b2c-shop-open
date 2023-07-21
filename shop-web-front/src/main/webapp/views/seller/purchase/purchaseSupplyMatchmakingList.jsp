<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:fy('报价方报价管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<style type="text/css">
	.myOffer-list .purchaseOK-td{position: relative;}
	.myOffer-list .purchaseOK-td .purchaseOK-div{position: absolute;top: -8px;right: 220px;width: 140px;height: 113px;}
	.myOffer-list textarea{padding: 5px;background-color: #f9f6f600}
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:150px !important;}
	/* .purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.purchaseBatch .uploader-container img{vertical-align: bottom;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;} */
</style>
</head>
<body>
	<div class="main-content clearfix myOffer-list purchaseBatch">
		<div class="main-center">
			<dl class="box">
				<!-- <dt>
					<div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 报价方报价管理</div>
					<i class="sui-icon icon-tb-list"></i> 报价方报价管理</span>
					<%@ include file="/views/seller/include/functionBtn.jsp"%>
				</dt> -->



				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('报价方报价管理')}</span>
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
						<li>${fns:fy('报价方报价管理')}</li>
					</ul>
				</dt>



				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('报价方可以查看自己的报价信息。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dd class="clearfix ml10 mr10 mt10">
					<div class="sui-row-fluid">
						<form class="sui-form form-inline" action="${ctxs}/purchase/matchmaking/list.htm?sta=${sta}" style="margin-top: 10px;" method="post" id="searchForm">
							<div class="span12">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('报价单号：')}</label>
									<input type="text" class="form-control input-sm" name="purchaseMatchmakingId" value="${purchaseMatchmaking.purchaseMatchmakingId}" placeholder="${fns:fy('请添加报价单号')}"> 
									<label class="col-sm-3 control-label text-right">${fns:fy('报价时间：')}</label>
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${purchaseMatchmaking.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginCreateDate" placeholder="${fns:fy('请选择报价开始时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"> -
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
									id="" value="<fmt:formatDate value="${purchaseMatchmaking.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endCreateDate" placeholder="${fns:fy('请选择报价结束时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'});">
									<label class="col-sm-3 control-label text-right">${fns:fy('状态：')}</label>
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
											<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input value="${purchaseMatchmaking.status}" name="status" type="hidden">
												<i class="caret"></i>
												<span>${empty purchaseMatchmaking.status?fns:fy('请选择'):fns:getDictLabel(purchaseMatchmaking.status, 'purchase_matchmaking_status', '')}</span>
											</a>
											<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
												<li role="presentation">
													<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('请选择')}</a>
												</li>
												<c:forEach items="${fns:getDictList('purchase_matchmaking_status')}" var="item">
													<li role="presentation">
														<a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${item.value}">${item.label}</a>
													</li>
												</c:forEach>
											</ul>
										</span>
									</span>
									<button type="submit" class="sui-btn btn-primary formBtn" id="searchBtn">${fns:fy('搜索')}</button>
								</div>
							</div>
						</form>
					</div>
					<c:if test="${fn:length(page.list)==0}">
						<!-- <div class="noData" style="margin-top: 0px;"><span>暂无数据</span></div> -->
						<div class="no_product" style="height:400px;text-align: center;color: #9a9a9a;line-height: 400px;">
							<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
						</div>
					</c:if>
					<c:if test="${fn:length(page.list)!=0}">
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th width="10%">${fns:fy('报价ID')}</th>
								<th width="10%">${fns:fy('采购单ID')}</th>
								<th width="20%">${fns:fy('采购标题')}</th>
								<th width="20%" style="text-align: left;">${fns:fy('采购方联系方式')}</th>
								<th width="5%">${fns:fy('总价(元)')}</th>
								<th width="10%">${fns:fy('采购状态')}</th>
								<th width="15%">${fns:fy('报价时间')}</th>
								<th width="20%">${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${page.list}" var="purchaseMatchmaking">
							<tr>
								<td width="10%">${purchaseMatchmaking.purchaseMatchmakingId }</td>
								<td width="10%">${purchaseMatchmaking.purchaseId }</td>
								<td width="20%">${purchaseMatchmaking.purchase.title }</td>
								<td width="20%" style="text-align: left;">
									${fns:fy('公司名称：')}${purchaseMatchmaking.purchaseStoreEnter.companyName }<br>
									${fns:fy('联系人：')}${purchaseMatchmaking.purchaseStoreEnter.contact }<br>
									${fns:fy('电话：')}${purchaseMatchmaking.purchaseStoreEnter.contactNumber }<br>
								</td>
								<td width="5%">${purchaseMatchmaking.price }${fns:fy('元')}</td>
								<td width="10%">${fns:getDictLabel(purchaseMatchmaking.status, 'purchase_matchmaking_status', '')}</td>
								<td width="15%"><fmt:formatDate value="${purchaseMatchmaking.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td width="20%" class="purchaseOK-td">
									<a href="${ctxs}/purchase/matchmakingItem/list.htm?pmId=${purchaseMatchmaking.purchaseMatchmakingId}&sta=${sta}" class="sui-btn btn-primary">${fns:fy('查看详情')}</a>
									<c:if test="${purchaseMatchmaking.status=='20' }">
									<div class="purchaseOK-div"><img src="${ctxStatic}/images/purchaseOK.png"></div>
									</c:if>
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
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseSupplyMatchmakingList.js"></script>
</body>
</html>
