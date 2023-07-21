<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('物流配送列表')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
 tbody {margin-bottom: 20px;}
 .lastTr td{border-bottom: 1px solid #e5e5e5;}
 .sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
 .sui-table.table-bordered-simple{border: none!important;}
 .sui-modal{border: none!important;border-radius:5px;}
 .sui-msg.msg-block{margin: 10px!important;}
</style>
</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('物流配送')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('订单')}</li>
					<li>${fns:fy('订单管理')}</li>
					<li class="active">${fns:fy('物理配送')}</li>
				</ul>
			</dt>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<dd class="sui-row-fluid form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
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
			</dd>
			<dd class="table-css" style="margin-bottom: 5px;">
				<div class="pull-right" style="padding-right: 9px;padding-top: 10px;">
				 	<a href="${ctxs}/logistics/logisticsTemplate/save1.htm" class="sui-btn btn-bordered btn-large btn-info">${fns:fy('添加配送规则')}</a>
				</div>
				<table class="sui-table table-bordered-simple">
					<c:forEach items="${page.list}" var="template">
						<tbody>
							<tr colspan="5" style="height: 15px;"></tr>
							<tr>
								<th colspan="5">
									<strong>${template.name}</strong>
										<span style="float: right;">
										<span>
											<fmt:formatDate value="${template.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</span>
										<button href="${ctxs}/logistics/logisticsTemplate/copy.htm?ltId=${template.ltId}" class="sui-btn btn-small btn-success copyBtn">${fns:fy('复制')}</button>
										<a href="${ctxs}/logistics/logisticsTemplate/edit1.htm?ltId=${template.ltId}" class="sui-btn btn-small btn-warning">${fns:fy('编辑')}</a>
										<button href="${ctxs}/logistics/logisticsTemplate/delete.htm?ltId=${template.ltId}" class="sui-btn btn-small btn-danger del">${fns:fy('删除')}</button>
									</span>
								</th>
							</tr>
							<tr>
								<td width="50%" class="center">${fns:fy('运送到')}</td>
								<td width="12%" class="center">
									${fns:fy('首')}${template.valuationMethod eq '0'?fns:fy('件'):fns:fy('重')}(${fns:getDictLabel(template.valuationMethod, 'logistics_template_valuation_method', '')})
								</td>
								<td width="12%" class="center">${fns:fy('运费(元)')}</td>
								<td width="12%" class="center">${fns:fy('续')}${template.valuationMethod eq '0'?fns:fy('件'):fns:fy('重')}(${fns:getDictLabel(template.valuationMethod, 'logistics_template_valuation_method', '')})</td>
								<td width="12%" class="center">${fns:fy('运费(元)')}</td>
							</tr>
							<c:forEach items="${template.logisticsTemplateItemList}" var="item">
								<tr>
									<td width="50%" class="center">${item.names}</td>
									<td width="12%" class="center">${item.firstItem}</td>
									<td width="12%" class="center">${item.firstPrice}</td>
									<td width="12%" class="center">${item.continueItem}</td>
									<td width="12%" class="center">${item.continuePrice}</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
				</table>
				<!-- 没有数据提示信息开始 -->
				<c:if test="${fn:length(page.list)==0}">
					<div class="no_product" style="height:400px;text-align: center;color: #9a9a9a;line-height: 400px;">
						<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂数据！')}
					</div>
				</c:if>
				<!-- 没有数据提示信息结束 -->
			</dd>
		</dl>
	</div>
	</div>
 </div>
	<script type="text/javascript" src="${ctx}/views/seller/logistics/logisticsTemplateList.js"></script>
</body>
</html>