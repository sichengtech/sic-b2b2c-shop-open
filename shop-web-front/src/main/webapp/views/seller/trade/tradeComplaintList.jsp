<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('投诉管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
	
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/trade/tradeComplaintList.js"></script>
<style type="text/css">
	 .header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
	 .header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	 #delTable td{line-height: 30px;}
	 .checked li{line-height: 25px;}
	 #searchForm .input-date{width: 145px;}
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
						<span>${fns:fy('投诉管理')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('售后')}</li>
						<li>${fns:fy('售后管理')}</li>
						<li>${fns:fy('投诉管理')}</li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('此列表显示了商家所有的投诉信息。')}</li>
								<li>${fns:fy('新投诉或已经处理完的投诉可以点击"查看"按钮查看投诉详情。')}</li>
								<li>${fns:fy('未处理投诉可以点击"处理"按钮查看并处理投诉。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<sys:message content="${message}"/>
				<dd class="table-css">
					<form class="sui-form form-inline" action="${ctxs}/trade/tradeComplaint/list.htm" style="margin: 0 0 7px;" method="get" id="searchForm">
						<div class="sui-row-fluid" style="margin-top: 15px;margin-left: 5px;">
							<div class="span1"></div>
							<div class="span5">
								<div class="control-group">
									<label class="col-sm-3 control-label text-right">${fns:fy('投诉时间：')}</label>
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" id="" 
									value="<fmt:formatDate value="${tradeComplaint.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									name="beginCreateDate" format="yyyy-MM-dd" placeholder="${fns:fy('请选择开始投诉时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> -
									
									<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" id="" 
									value="<fmt:formatDate value="${tradeComplaint.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									name="endCreateDate" format="yyyy-MM-dd" placeholder="${fns:fy('请选择结束投诉时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
								</div>
							</div>
							<div class="span5">
								<div class="control-group"><label class="control-label">${fns:fy('投诉状态：')}</label>
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
										<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input value="" name="status" type="hidden"><i class="caret"></i>
											<span>
												<c:choose>
													<c:when test="${not empty tradeComplaint.status}">
														${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}
													</c:when>
													<c:otherwise>${fns:fy('全部')}</c:otherwise>
												</c:choose>
											</span>
										</a>
										<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">${fns:fy('全部')}</a></li>
											<c:forEach items="${fns:getDictList('trade_complaint_status')}" var="cs">
												<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${cs.value}">${cs.label}</a></li>
											</c:forEach>
										</ul>
										</span>
									</span>
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
										<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
											<input name="searchCate" type="hidden" value="${searchCate}"/><i class="caret"></i>
											<span>
												<c:choose>
													<c:when test="${searchCate == '1'}">${fns:fy('买家会员名')}</c:when>
													<c:when test="${searchCate == '2'}">${fns:fy('商品名称')}</c:when>
													<c:when test="${searchCate == '3'}">${fns:fy('投诉编号')}</c:when>
													<c:otherwise>${fns:fy('请选择')}</c:otherwise>
												</c:choose>
											</span>
										</a>
										<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
											<li class="${searchCate == '1' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="1">${fns:fy('买家会员名')}</a></li>
											<li class="${searchCate == '2' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="2">${fns:fy('商品名称')}</a></li>
											<li class="${searchCate == '3' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="3">${fns:fy('投诉编号')}</a></li>
										</ul>
										</span>
									</span>
									<input type="text" name="searchValue" placeholder="" class="input-medium" value="${searchValue}" maxLength="64"/>
								</div>
							</div>
							<div class="span1">
								<div class="text-align">
									<button type="submit" class="sui-btn btn-large btn-primary">${fns:fy('搜索')}</button>
								</div>
							</div>
						</div>
					</form>
					<!--搜索表单结束 -->
					<!--table开始 -->
					<table class="sui-table table-bordered-simple">
						<thead>
							<tr>
								<th class="center">${fns:fy('投诉商品')}</th>
								<th class="center">${fns:fy('投诉编号')}</th>
								<th class="center">${fns:fy('投诉主题')}</th>
								<th class="center">${fns:fy('投诉时间')}</th>
								<th class="center">${fns:fy('投诉状态')}</th>
								<th class="center">${fns:fy('操作')}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="tradeComplaint">
							<tr>
								<td>
									<div class="typographic">
										<img src="${ctxfs}${tradeComplaint.tradeOrderItem.thumbnailPath}" style="width: 80px;height: 80px;"
										onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
										<ul class="unstyled">
											<li><a href="" target="_blank">${tradeComplaint.tradeOrderItem.name}</a></li>
										</ul>
									</div>
								</td>
								<td class="center">${tradeComplaint.complaintId}</td>
								<td class="center">${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</td>
								<td class="center"><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}</td>
								<td class="center">
									<shiro:hasPermission name="trade:tradeComplaint:edit">
										<c:choose>
											<c:when test="${tradeComplaint.status == '10' || tradeComplaint.status == '50'}">
												<a href="${ctxs}/trade/tradeComplaint/edit1.htm?complaintId=${tradeComplaint.complaintId}" class="sui-btn" style="margin-bottom:2px;padding:2px 10px;">
													<i class="sui-icon icon-tb-edit"></i>${fns:fy('查看')}
											</c:when>
											<c:otherwise>
												<a href="${ctxs}/trade/tradeComplaint/edit1.htm?complaintId=${tradeComplaint.complaintId}" class="sui-btn btn-primary" style="margin-bottom:2px;">
													<i class="sui-icon icon-touch-edit-rect"></i>${fns:fy('处理')}
												</a>
											</c:otherwise>
										</c:choose>
									</shiro:hasPermission>
								</td>
							</tr>
							</c:forEach>
							<!-- 没有数据提示开始 -->
							<c:if test="${fn:length(page.list)=='0'}">
								<tr>
									<td colspan="6" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;">
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
</body>
</html>