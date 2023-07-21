<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('品牌管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productBrandList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('品牌列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="${empty status?'active':''}"><a href="${ctxa}/product/productBrand/list.do"> <i class="fa fa-user"></i> ${fns:fy('品牌列表')}</a></li>
				<li class="${status=='0'?'active':''}"><a href="${ctxa}/product/productBrand/list.do?status=0"> <i class="fa fa-user"></i> ${fns:fy('待审核')}</a></li>
				<li class="${status=='1'?'active':''}"><a href="${ctxa}/product/productBrand/list.do?status=1"> <i class="fa fa-user"></i> ${fns:fy('审核通过')}</a></li>
				<li class="${status=='2'?'active':''}"><a href="${ctxa}/product/productBrand/list.do?status=2"> <i class="fa fa-user"></i> ${fns:fy('审核未通过')}</a></li>
				<shiro:hasPermission name="product:productBrand:save">
				<li class=""><a href="${ctxa}/product/productBrand/save1.do" > <i class="fa fa-user"></i> ${fns:fy('品牌添加')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('品牌管理.品牌列表.操作提示1')}</li>
					<li>${fns:fy('品牌管理.品牌列表.操作提示2')}</li>
					<li>${fns:fy('品牌管理.品牌列表.操作提示3')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<a href="javascript:;" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></a>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="product:productBrand:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/product/productBrand/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="col-sm-5"></div>
				<form action="${ctxa}/product/productBrand/list.do" method="get" id="searchForm">
					<div class="col-sm-2">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('品牌名称')}" value="${productBrand.name}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="firstLetter"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('首字母')}" value="${productBrand.firstLetter}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input name="status" type="hidden"  value="${status}">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>${fns:fy('品牌')}ID</th>
						<th>${fns:fy('品牌名称')}</th>
						<th>${fns:fy('首字母')}</th>
						<th>${fns:fy('品牌')}LOGO</th>
						<th>${fns:fy('是否推荐')}</th>
						<th>${fns:fy('展示方式')}</th>
						<th>${fns:fy('排序')}</th>
						<th>${fns:fy('审核状态')}</th>
						<th>${fns:fy('申请时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="productBrand">
					<tr>
						<td>
							<div>
								<input type="checkbox" brandId="${productBrand.brandId}" class="batchBrandId">
							</div>
						</td>
						<td><a href="${ctxa}/product/productBrand/edit1.do?brandId=${productBrand.brandId}">${productBrand.brandId}</a></td>
						<td>${productBrand.name}</td>
						<td>${productBrand.firstLetter}</td>
						<td>
							<c:if test="${productBrand.logo !=null}">
							 <a href="${ctxfs}${productBrand.logo}" target="_blank">
								<image src="${ctxfs}${productBrand.logo}@85x41"></image>
							 </a>
							</c:if>
							<c:if test="${productBrand.logo == null}">无</c:if>					
						</td>
						<td>${fns:getDictLabel(productBrand.recommend, 'is_recommend', '')}</td>
						<td>${fns:getDictLabel(productBrand.displayMode, 'brand_display_mode', '')}</td>
						<td>${productBrand.sort}</td>
						<td>${fns:getDictLabel(productBrand.status, 'brand_status', '')}</td>
						<td><fmt:formatDate value="${productBrand.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td style="text-align: left">
							<shiro:hasPermission name="product:productBrand:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/product/productBrand/edit1.do?brandId=${productBrand.brandId}">
								<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productBrand:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/product/productBrand/delete.do?brandId=${productBrand.brandId}">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
							</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productBrand:auth">
							<c:if test="${productBrand.status==1}">
								<a href="javascript:;" class="btn btn-danger btn-sm auth-notpass" brandId="${productBrand.brandId}" bName="${productBrand.name}"><i class="fa fa-ban"></i> ${fns:fy('不通过')}</a>
							</c:if>
							<c:if test="${productBrand.status==2}">
								<a href="javascript:;" class="btn btn-success btn-sm auth-pass" url="${ctxa}/product/productBrand/auth.do?brandId=${productBrand.brandId}&auth=true"><i class="fa fa-check-square"></i> ${fns:fy('通过')}</a>
							</c:if>
							<c:if test="${productBrand.status==0}">
								<a href="javascript:;" class="btn btn-success btn-sm auth-pass" url="${ctxa}/product/productBrand/auth.do?brandId=${productBrand.brandId}&auth=true"><i class="fa fa-check-square"></i> ${fns:fy('通过')}</a>
								<a href="javascript:;" class="btn btn-danger btn-sm auth-notpass" brandId="${productBrand.brandId}" bName="${productBrand.name}" ><i class="fa fa-ban"></i> ${fns:fy('不通过')}</a>
							</c:if>
							</shiro:hasPermission>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 批量开始 -->
			<div style="margin-bottom: 20px;">
				<button class="btn btn-info batch-auth" type="button" auth="1">
					<i class="fa fa-check"></i> ${fns:fy('批量通过')}
				</button>
				<button class="btn btn-danger batch-auth" type="button" auth="2">
					<i class="fa fa-ban"></i> ${fns:fy('批量不通过')}
				</button>
			</div>
			<!-- 批量结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<shiro:hasPermission name="product:productBrand:auth">
	<!-- 开始审核不通过模态框 -->
	<div class="modal fade" id="authNotPassModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
		<div class="modal-content">
			<form id="authNotPassForm" action="${ctxa}/product/productBrand/auth.do"> 
				<div class="modal-body form-horizontal adminex-form">
					<div class="form-group">
						<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
						 <li class="m-b-3"> ${fns:fy('此品牌未能通过审核，需要商家重新编辑品牌')}</li>
						</ul>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">${fns:fy('品牌名称')}：</label>
						<div class="col-sm-9" id="brandName">${fns:fy('品牌名称')}</div>
					</div>
					<div class="form-group"> 
						<label class="col-sm-3 control-label text-right">${fns:fy('拒审原因')}：</label>
						<div class="col-sm-9">
							<textarea name="cause" class="form-control"
						placeholder="${fns:fy('请填写品牌被拒审的原因，不能超过100个字符')}" rows="3" maxlength="100"></textarea>
							<input type="hidden" name="brandId" value=""/>
							<input type="hidden" name="auth" value="false"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
						<i class="fa fa-times"></i> ${fns:fy('关闭')}
					</button>
					<button type="submit" class="btn btn-info">
						<i class="fa fa-search"></i> ${fns:fy('保存')}
					</button> 
				</div>
			</form>
		</div>
	</div>
	<!-- 结束审核不通过模态框 -->
	</shiro:hasPermission>
</body>
</html>