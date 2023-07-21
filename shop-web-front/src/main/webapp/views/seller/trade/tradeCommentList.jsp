<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('评价管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #explainModal{width: 500px;margin-left:-250px;border: none;padding: 0;}
	 #delTable td{line-height: 30px;}
	 .sui-modal .modal-header{border-bottom:none!important;}
	 .sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	 .trSpan{margin-right: 15px;}
	 #searchForm{margin-top: 10px;}
	 .icon-tb-favorfill{color:#F1C40F;}
	 .count{word-break:break-all;}
	 .sui-msg.msg-block {margin: 10px !important;}
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
						<span>${fns:fy('评价管理')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('订单')}</li>
						<li>${fns:fy('订单管理')}</li>
						<li>${fns:fy('评价管理')}</li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('此列表显示了所有的评价信息。')}</li>
								<li>${fns:fy('在此列表中，商家可以对买家的评价进行解释操作。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<sys:message content="${message}"/>
				<dd class="table-css">
					<div class="sui-row-fluid">
						<form class="sui-form form-inline" id="searchForm" method="get" action="${ctxs}/trade/tradeComment/list.htm">
							<div class="span2"></div>
							<div class="span3">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('商品评分：')}</label>
									<input type="text" placeholder="${fns:fy('商品评分(1-5之间的数字)')}" maxLength="1" name="productScore" class="input-fat" value="${tradeComment.productScore}"/>
								</div>
							</div>
							<div class="span3" style="">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('商品名称：')}</label>
									<input type="text" placeholder="${fns:fy('商品名称')}" maxLength="64" name="productName" class="input-fat" value="${productName}"/>
								</div>
							</div>
							<div class="span3">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('评价人：')}</label>
									<input type="text" placeholder="${fns:fy('评价人')}" maxLength="64" name="userName" class="input-fat" value="${userName}"/>
								</div>
							</div>
							<div class="span1">
								<div class="text-align">
									<button class="sui-btn btn-large btn-primary">${fns:fy('搜索')}</button>
								</div>
							</div>
						</form>
					</div>
					<!--搜索表单结束 -->
					<!--table开始 -->
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th width="89%" class="">${fns:fy('评价信息')}</th>
								<th width="11%" class="center">${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="tradeComment" varStatus="index">
								<tr style="height:15px;"></tr>
								<tr>
									<th colspan="2"> 
										<span class="trSpan"><a href="${ctxf}/detail/${tradeComment.productSpu.PId}.htm" target="_blank">${tradeComment.productSpu.name}</a></span>
										<span class="trSpan">${fns:fy('商品评分：')}
											<span>
												<c:forEach begin="1" end="${tradeComment.productScore}" step="1" var="i">
													<i class="sui-icon icon-tb-favorfill"></i>
												</c:forEach>
											</span>
										</span>
										<span class="trSpan">${fns:fy('评价人：')}
											<span>
												${tradeComment.userMain.loginName} 
												[<fmt:formatDate value="${tradeComment.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/> ]
											</span>
										</span>
									</th>
								</tr>
								<tr>
									<td width="87%" colspan="${not empty tradeComment.tradeCommentExplain.content?'2':'1'}">
										<strong>${fns:fy('买家评价:')}</strong>
										<span class="count">${tradeComment.content}</span>
									</td>
									<c:if test="${empty tradeComment.tradeCommentExplain.content}">
										<shiro:hasPermission name="trade:tradeComment:edit">
											<td class="" width="13%" style="text-align: right;">
												<a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-primary explain" id="${tradeComment.commentId}" explain="1">${fns:fy('评价解释')}</a>
											</td>
										</shiro:hasPermission>
									</c:if>
								</tr>
								 <c:if test="${not empty tradeComment.tradeCommentExplain.content}">
									<tr style="background-color: #FCF8E3;">
										<td width="87%" colspan="2">
											<strong>${fns:fy('商家解释:')}</strong>
											<span class="count">${tradeComment.tradeCommentExplain.content}</span>
										</td>
									</tr>
								</c:if>
								
								<c:if test="${not empty tradeComment.tradeCommentAdd.content}">
									<tr>
										<td width="87%" colspan="${not empty tradeComment.tradeCommentAdd.tradeCommentExplain.content?'2':'1'}">
											<strong>${fns:fy('买家追评:')}</strong>
											<span class="count">${tradeComment.tradeCommentAdd.content}</span>
										</td>
										<c:if test="${empty tradeComment.tradeCommentAdd.tradeCommentExplain.content}">
											<td class="" width="13%" style="text-align: right;">
												<a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-warning explain" id="${tradeComment.tradeCommentAdd.commentId}" explain="2">${fns:fy('追评解释')}</a>
											</td>
										</c:if>
									</tr>
								</c:if>
								<c:if test="${not empty tradeComment.tradeCommentAdd.tradeCommentExplain.content}">
									<tr style="background-color: #FCF8E3;">
										<td width="87%" colspan="2">
											<strong>${fns:fy('商家解释:')}</strong>
											<span class="count">${tradeComment.tradeCommentAdd.tradeCommentExplain.content}</span>
										</td>
									</tr>
								</c:if>
							</c:forEach>
							<!-- 没有数据提示开始 -->
							<c:if test="${fn:length(page.list)=='0'}">
								<tr>
									<td colspan="2" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;">
										<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
									</td>
								</tr>
							</c:if>
							<!-- 没有数据提示结束 -->
						</tbody>
					</table>
					 <!-- table结束 -->
				</dd>
				<c:if test="${fn:length(page.list)>'0'}">
				<%@ include file="/views/seller/include/page.jsp"%>
				</c:if>
			</dl>
			</div>
		</div>
	</div>
	<!-- 卖家解释弹出框开始 -->
	<div id="explainModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-content">
			<div class="modal-body" style="padding: 0;">
				<form id="explainForm" name="explainForm" action="${ctxs}/trade/tradeComment/explainComment.htm" method="post">
					<input type="hidden" name="replyId" class="replyId" value="">
					<table class="sui-table table-bordered" id="delTable">
						<tbody>
							<tr>
								<td class="explainTitle" style="text-align: right;width:68px;">${fns:fy('评价内容：')}</td>
								<td><div class="text-box" style="word-break: break-all;"><span class="c-success" id="commentContent" name="ordersSn"> ${fns:fy('质量不错，非常愉快的一次购物')}</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;"><font color="red">*</font>${fns:fy('解释内容：')}</td>
								<td>
									<textarea rows="4" cols="30" style="width:400px;" name="content"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="modal-footer" style="background-color: #ffffff;text-align: right;margin-right: 10px;">
						<button type="submit" data-ok="modal" class="sui-btn btn-primary btn-large" style="margin-right: 10px;">${fns:fy('确定')}</button>
						<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 卖家解释弹出框结束 -->
	<script type="text/javascript" src="${ctx}/views/seller/trade/tradeCommentList.js"></script>
</body>
</html>