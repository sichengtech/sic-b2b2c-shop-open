<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSpuList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('商品管理')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('商品管理')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('商品管理.商品列表.操作提示1')}</li>
					<li>${fns:fy('商品管理.商品列表.操作提示2')}</li>
					<li>${fns:fy('商品管理.商品列表.操作提示3')}</li>
					<li>${fns:fy('商品管理.商品列表.操作提示4')}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-6">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--快速查询按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips search" title="${fns:fy('查询')}"><i class="fa fa-search"></i></button>
						<!--导出数据按钮 -->
						<%-- <shiro:hasPermission name="product:productSpu:export">
						<button type="button" class="btn btn-default btn-sm tooltips exp" title="导出数据"><i class="fa fa-download"></i></button>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<form action="${ctxa}/product/productSpu/list.do" method="get" id="searchForm">
					<div class="col-sm-6">
						<div class="iconic-input right">
							<i class="fa fa-search " style="cursor:pointer" id="searchFormButton"></i>
							<input type="text" name="pId" maxlength="18" value="${productSpu.PId}" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入商品ID进行精确搜索')}" style="border-radius: 30px;max-width:250px;">
						</div>
					</div>
					<input name="status" type="hidden" value="${status}"/>
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th></th>
						<th>ID(SPU)</th>
						<th>${fns:fy('商品名称')}</th>
						<th>${fns:fy('图片')}</th>
						<th>${fns:fy('商品分类')}</th>
						<th>${fns:fy('店铺')}</th>
						<th>${fns:fy('卖家')}</th>
						<th>${fns:fy('状态')}</th>
						<th>${fns:fy('品牌')}</th>
						<th>${fns:fy('发布时间')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="productSpu">
					<tr>
						<td width="2%"class="detail"><i class="fa fa-plus"></i></span></td>
						<td width="5%">${productSpu.PId}</td>
						<td width="40%" class="product-title"><a target="_blank" href="${ctxf}/detail/${productSpu.PId}.htm">${productSpu.name}</a></td>
						<td width="5%">
							<a href="${ctxfs}${productSpu.image}" target="_blank">
								<img src="${ctxfs}${productSpu.image}@!50x50" onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');" width="50" height="50"/>
							</a>
						</td>
						<td width="8%">${productSpu.productCategory.name}</td>
						<td width="8%">${productSpu.store.name}</td>
						<td width="8%">${productSpu.userMain.loginName}</td>
						<td width="5%">${fns:getDictLabel(productSpu.status, 'product_spu_status', '')}</td>
						<td width="5%">${productSpu.productBrand.name}</td>
						<td width="8%"><fmt:formatDate value="${productSpu.createDate}" pattern="yyyy-MM-dd"/></td>
						<td width="5%">
							<div class="btn-group">
								<a href="javascript:;" data-toggle="dropdown" aria-expanded="false" class="btn btn-info btn-sm dropdown-toggle">
									<i class="fa fa-gears"></i> ${fns:fy('操作')}
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu dropdown-menu-right" style="min-width: 125px;">
									<shiro:hasPermission name="product:productSpu:view">
									<li><a href="javascript:;" class="sku" pId="${productSpu.PId}" pName="${productSpu.name}"><i class="fa fa-tasks m-r-10"></i>&nbsp;SKU</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="product:productSpu:edit">
									<c:if test="${productSpu.status=='50'}">
										<li><a href="javascript:;" class="forbid-sale" pId="${productSpu.PId}" pName="${productSpu.name}"><i class="fa fa-lock m-r-10"></i>&nbsp; ${fns:fy('禁售')}</a></li>
									</c:if>
									<c:if test="${productSpu.status=='20'}">
										<li><a href="javascript:;" class="start-sale" url="${ctxa}/product/productSpu/forbidSale.do?pId=${productSpu.PId}&forbidSale=false"><i class="fa fa-unlock m-r-10"></i>&nbsp;${fns:fy('解禁')}</a></li>
									</c:if>
									<c:if test="${productSpu.status=='30'}">
										<li><a href="javascript:;" class="auth-pass" url="${ctxa}/product/productSpu/auth.do?pId=${productSpu.PId}&auth=true"><i class="fa fa-check-square m-r-10"></i>&nbsp;${fns:fy('审核通过')}</a></li>
										<li><a href="javascript:;" class="auth-notpass" pId="${productSpu.PId}" pName="${productSpu.name}"><i class="fa fa-unlock m-r-10"></i>&nbsp;${fns:fy('审核不过')}</a></li>
									</c:if>
									<c:if test="${productSpu.status=='40'}">
										<li><a href="javascript:;" class="auth-pass" url="${ctxa}/product/productSpu/auth.do?pId=${productSpu.PId}&auth=true"><i class="fa fa-check-square m-r-10"></i>&nbsp;${fns:fy('审核通过')}</a></li>
									</c:if>
									</shiro:hasPermission>
								</ul>
							</div>
						 </td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="14" >
							<div class="row">
								<div class="col-sm-4">
									<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('商品名称')}:${productSpu.name}</p>
									<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('副标题')}:${productSpu.nameSub}</p>
									<p datano="0" columnno="10" class="dt-grid-cell">${fns:fy('品牌')}:${productSpu.productBrand.name}</p>
									<p datano="0" columnno="7" class="dt-grid-cell">${fns:fy('商品分类')}:${productSpu.productCategory.name}</p>	 
									<p datano="0" columnno="7" class="dt-grid-cell">${fns:fy('销售类型')}:${fns:getDictLabel(productSpu.type,'product_sale_type', '')}</p>
								</div>
								<div class="col-sm-4">
									<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('店铺')}:${productSpu.store.name}</p>
									<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('卖家')}:${productSpu.userMain.loginName}</p>
									<p datano="0" columnno="6" class="dt-grid-cell">${fns:fy('发布时间')}:<fmt:formatDate value="${productSpu.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
									<p datano="0" columnno="10" class="dt-grid-cell">${fns:fy('重量')}:${productSpu.weight}</p> 
									<p datano="0" columnno="10" class="dt-grid-cell">${fns:fy('体积')}:${productSpu.volume}</p> 
								</div>
								<div class="col-sm-4">
									<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('状态')}:${fns:getDictLabel(productSpu.status, 'product_spu_status', '')}</p>
									<p datano="0" columnno="4" class="dt-grid-cell">${fns:fy('原因')}:${productSpu.cause}</p>
									<p datano="0" columnno="6" class="dt-grid-cell">${fns:fy('是否推荐')}:${fns:getDictLabel(productSpu.isRecommend, 'is_recommend', '')}</p>
									<p datano="0" columnno="7" class="dt-grid-cell">${fns:fy('推荐排序')}:${productSpu.recommendSort}</p>
									<p datano="0" columnno="6" class="dt-grid-cell">${fns:fy('单位')}:${productSpu.unit}</p>
								</div>
							</div>
						</td>
					</tr>
					</c:forEach>
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
				<form id="fastSearch" action="${ctxa}/product/productSpu/list.do" method="get"> 
					<div class="modal-body form-horizontal adminex-form">
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">${fns:fy('商品')}ID：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control input-sm" name="pId" maxLength="18" value="${productSpu.PId}" placeholder="${fns:fy('请输入商品ID')}">
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy('商品名称')}：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control input-sm" name="name" maxLength="64" value="${productSpu.name}" placeholder="${fns:fy('请输入商品名称')}">
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy('卖家店铺名')}：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control input-sm" name="storeName" maxLength="64" value="${productSpu.storeName}" placeholder="${fns:fy('请输入卖家店铺名')}">
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy('卖家用户名')}：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control input-sm" name="sellerName" maxLength="64" value="${productSpu.sellerName}" placeholder="${fns:fy('请输入卖家用户名')}">
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy('商品状态')}：</label>
							<div class="col-sm-4">
								<select name="status" class="form-control m-bot15 input-sm">
								<option value="">--${fns:fy('所有商品')}--</option>
								<c:forEach items="${fns:getDictList('product_spu_status')}" var="item">
								<option value="${item.value}" ${item.value==productSpu.status?"selected":""}>${item.label}</option>
								</c:forEach>
							</select>
							</div>
						</div>
						<div class="form-group"> 
							<label class="col-sm-3 control-label text-right">${fns:fy('发布时间')}：</label>
							<div class="col-sm-4">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm J-date-start" nc-format="" name="beginCreateDate" 
									value="<fmt:formatDate value="${productSpu.beginCreateDate}" type="both" pattern="yyyy-MM-dd"/>" 
									format="yyyy-MM-dd" placeholder="${fns:fy('请选择开始时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm J-date-end" nc-format="" name="endCreateDate" 
									value="<fmt:formatDate value="${productSpu.endCreateDate}" type="both" pattern="yyyy-MM-dd"/>" 
									format="yyyy-MM-dd" placeholder="${fns:fy('请选择结束时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
							</div>
						</div>
					 </div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="layer.closeAll()">
							<i class="fa fa-times"></i> ${fns:fy('关闭')}
						</button>
						<button type="button" class="btn btn-warning" onclick="form.reset()">
							<i class="fa fa-reply"></i> ${fns:fy('参数重置')}
						</button> 
						<button type="submit" class="btn btn-info">
							<i class="fa fa-search"></i> ${fns:fy('执行查询')}
						</button> 
					</div>
				 </form>
			</div>
		</div>
		<!-- 结束快速查询模态窗口 --> 
		<shiro:hasPermission name="product:productSpu:export">
		<!-- 开始导出操作模态窗口 -->
		<!-- <div class="modal fade" id="expModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">	 
					<div class="modal-body">
						<div class="alert alert-danger alert-block fade in">
							<h4><i class="fa fa-info-circle m-r-10"></i>数据导出</h4>
							<p>您选择把商品信息导出为CSV文件。您确定要进行导出操作吗?</p>
							<p>当数据量很大时，导出要花较长时间，占用系统较多内存！</p>
						</div>
						<label class="checkbox-inline">
							<input type="checkbox" id="inlineCheckbox1" checked="checked" value="1"> 导出前5000条
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" id="inlineCheckbox1" checked="checked" value="1"> 导出前2万条
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" id="inlineCheckbox1" value="1"> 全部导出(建议按条件查询后分批导出)
						</label>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>放弃操作
						</button>
						<button type="button" class="btn btn-info">
							<i class="fa fa-check"></i>确认导出
						</button> 
					</div> 
				</div>
		</div> -->
		<!-- 结束导出操作模态窗口 -->
		</shiro:hasPermission>
		<shiro:hasPermission name="product:productSpu:view">
		<!-- 商品的SKU列表模态窗口开始 -->
		<div class="modal fade" id="skuModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">	 
					<div class="modal-body">
							<table class="table table-hover table-condensed table-bordered">
							 <thead> 
								<tr>
									<th>SKU编号1</th> 
									<th>图片2</th> 
									<th>价格3</th>
									<th>库存4</th>
									<th>规格5</th> 
								</tr>
							</thead> 
							<tbody>
								<tr class="2">
									<td>806</td> 
									<td><img width="30" heigth="30" src="${ctxStatic}/images/xyj.jpg"/></td>
									<td>299.00</td>
									<td>10</td>
									<td>白色</td>
								</tr>
								<tr class="2">
									<td>807</td> 
									<td><img width="30" heigth="30" src="${ctxStatic}/images/xyj.jpg"/></td>
									<td>299.00</td>
									<td>10</td>
									<td>红色</td>
								</tr>
								<tr class="2">
									<td>808</td> 
									<td><img width="30" heigth="30" src="${ctxStatic}/images/xyj.jpg"/></td>
									<td>310.00</td>
									<td>10</td>
									<td>米白色</td>
								</tr>
							</tbody>
						</table>
					</div>
			</div>
		</div>
		<!-- 商品的SKU列表模态窗口结束 -->
		</shiro:hasPermission>
		<shiro:hasPermission name="product:productSpu:edit">
		<!-- 开始禁售模态框 -->
		<div class="modal fade" id="forbidSaleModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">
					<form id="forbidSaleForm" action="${ctxa}/product/productSpu/forbidSale.do"> 
						<div class="modal-body form-horizontal adminex-form">
							<div class="form-group">
								<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
								 <li class="m-b-3"> ${fns:fy('管理员发现商品有违规问题，需要商家重新编辑商品')}</li>
								</ul>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">${fns:fy('商品名称')}：</label>
								<div class="col-sm-9" id="productName">${fns:fy('商品名称')}</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label text-right">${fns:fy('禁售原因')}：</label>
								<div class="col-sm-9">
									<textarea name="cause" class="form-control"
								placeholder="${fns:fy('请填写商品被禁售的原因，不能超过100个字符')}" rows="3" maxlength="512"></textarea>
									<input type="hidden" name="pId" value=""/>
									<input type="hidden" name="forbidSale" value="true"/>
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
		<!-- 结束禁售模态框 -->
		</shiro:hasPermission>
		<shiro:hasPermission name="product:productSpu:edit">
		<!-- 开始审核不通过模态框 -->
		<div class="modal fade" id="authNotPassModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" style="display: none;"> 
				<div class="modal-content">
					<form id="authNotPassForm" action="${ctxa}/product/productSpu/auth.do"> 
						<div class="modal-body form-horizontal adminex-form">
							<div class="form-group">
								<ul class="collapse in text-info m-b-20 p-l-15" role="tabpanel" id="OperationTipsEditInfo">
								 <li class="m-b-3"> ${fns:fy('此商品未能通过审核，需要商家重新编辑商品')}</li>
								</ul>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">${fns:fy('商品名称')}：</label>
								<div class="col-sm-9" id="productName">${fns:fy('商品名称')}</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label text-right">${fns:fy('拒审原因')}：</label>
								<div class="col-sm-9">
									<textarea name="cause" class="form-control"
								placeholder="${fns:fy('请填写商品被拒审的原因，不能超过100个字符')}" rows="3" maxlength="512"></textarea>
									<input type="hidden" name="pId" value=""/>
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