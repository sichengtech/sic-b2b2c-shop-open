<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购单管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('采购单列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('采购单列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('拆分一句话采购，按正确的采购单明细格式进行拆分')}</li>
					<li>${fns:fy('整理BOM，将用户上传的BOM文件整理城正确格式的BOM文件，并上传整理后的BOM文件')}</li>
					<li>${fns:fy('点击删除按钮，删除采购单，并删除采购单明细')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy('查询')}"><i class="fa fa-search"></i></button>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>${fns:fy('采购单号')}</th>
						<th>${fns:fy('发布人')}</th>
						<th>${fns:fy('发布人手机号')}</th>
						<th>${fns:fy('采购标题')}</th>
						<th>${fns:fy('采购方式')}</th>
						<th>${fns:fy('状态')}</th>
						<th>${fns:fy('到期时间')}</th>
						<th>${fns:fy('发布时间')}</th>
						<th>${fns:fy('报价信息')}</th>
						<th>${fns:fy('BOM管理')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="viewPurchase">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${viewPurchase.purchaseId}</td>
						<td>${viewPurchase.umLoginName}</td>
						<td>${viewPurchase.umMobile}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${viewPurchase.purchaseTitle}</textarea></td>
						<td>${fns:getDictLabel(viewPurchase.purchaseType, 'purchase_type', '')}</td>
						<td>${fns:getDictLabel(viewPurchase.purchaseStatus, 'purchase_status', '')}</td>
						<td><fmt:formatDate value="${viewPurchase.purchaseExpiryTime}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${viewPurchase.purchaseCreateDate}" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:if test="${viewPurchase.purchaseStatus==35 || viewPurchase.purchaseStatus==40 || viewPurchase.purchaseStatus==50 || viewPurchase.purchaseStatus==60 }">
								${fns:fy('有')}${viewPurchase.purchaseCounts}${fns:fy('家报价')}<br>
								<a href="${ctxa}/purchase/purchaseMatchmaking/list.do?purchaseId=${viewPurchase.purchaseId}">${fns:fy('查看')}</a>
							</c:if>
						</td>
						<td>
							<c:if test="${viewPurchase.purchaseType==20}">
								<a href="${ctxfs}${viewPurchase.purchaseBomPath}" download="BOM.xlsx">${fns:fy('下载BOM采购单')}</a><br>
								<c:if test="${viewPurchase.purchaseStatus==30}">
								<a href="${ctxa}/purchase/purchase/bomUpload1.do?purchaseId=${viewPurchase.purchaseId}">${fns:fy('上传BOM采购单')}</a>
								</c:if>
							</c:if>
						</td>
						<td>
							<shiro:hasPermission name="purchase:purchaseItem:view">
								<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseItem/list.do?purchaseId=${viewPurchase.purchaseId}">
									<i class="fa fa-eye"></i> ${fns:fy('查看明细')}
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchase:purchase:edit">
								<c:if test="${viewPurchase.purchaseType==10 && viewPurchase.purchaseStatus==30}">
									<a class="btn btn-warning btn-sm" href="${ctxa}/purchase/purchase/split1.do?purchaseId=${viewPurchase.purchaseId}">
										<i class="fa fa-edit"></i> ${fns:fy('拆分')}
									</a>
								</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchase:purchase:auth">
								<c:if test="${viewPurchase.purchaseStatus==10 || viewPurchase.purchaseStatus==20}">
									<a class="btn btn-success btn-sm" href="${ctxa}/purchase/purchase/auth1.do?purchaseId=${viewPurchase.purchaseId}">
										<i class="fa fa-edit"></i> ${fns:fy('审核')}
									</a>
								</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchase:purchase:drop">
								<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/purchase/purchase/delete.do?purchaseId=${viewPurchase.purchaseId}">
									<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
								</button>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="16" >
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break: break-all;">
								${fns:fy('采购内容：')}${viewPurchase.purchaseContent}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break: break-all;">
								${fns:fy('交货周期：')}${viewPurchase.puCycle}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell" style="word-break: break-all;">
								${fns:fy('采购说明：')}${viewPurchase.purchaseExplain}
							</p>
						</td> 
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy('无查询结果')}</div></td>
						</tr>
					</c:if>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<!-- 开始快速查询窗口 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">	 
			<form action="${ctxa}/purchase/purchase/list.do" method="get" id="fastSearch"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('发布日期：')}</label>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="beginPurchaseCreateDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy('请输入发布日期(开始)')}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${viewPurchase.beginPurchaseCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="input-group"> 
								<input name="endPurchaseCreateDate" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy('请输入发布日期(结束)')}" class="form-control input-sm J-date-start searchInput"
								value="<fmt:formatDate value="${viewPurchase.endPurchaseCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:true});"/>
								<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('采购单号：')}</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="purchaseId" value="${viewPurchase.purchaseId}" placeholder="${fns:fy('请输入采购单号')}" maxlength="10"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('采购标题：')}</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm searchInput" name="title" value="${title}" placeholder="${fns:fy('请输入采购标题')}" maxlength="64"/>
						</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('采购单状态：')}</label>
						<div class="col-sm-4"> 
							<select name="purchaseStatus" class="form-control input-sm">
								<option value="" class="firstOption">--${fns:fy('请选择')}--</option>
								<c:forEach items="${fns:getDictList('purchase_status')}" var="item">
								<option value="${item.value}" ${item.value==viewPurchase.purchaseStatus?"selected":""}>${item.label}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="(function(){layer.closeAll('page');}())">
							<i class="fa fa-times"></i> ${fns:fy('关闭')}
						</button>
						<button type="button" id="resetParam" class="btn btn-warning" onclick="(function(){$('.searchInput').attr('value',''); $('.firstOption').attr('selected','selected');}())">
							 <i class="fa fa-reply"></i> ${fns:fy('参数重置')}
						</button> 
						<button type="submit" class="btn btn-info">
							<i class="fa fa-search"></i> ${fns:fy('执行查询')}
						</button> 
					</div>
				</div>
			</form> 
		</div>
	</div>
</body>
</html>