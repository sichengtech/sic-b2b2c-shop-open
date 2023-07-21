<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<div class="modal-content">	 
	<div class="modal-body">
		<table class="table table-hover table-condensed table-bordered">
			<thead> 
				<tr>
					<th>SKU${fns:fy('编号')}</th>
					<th>${fns:fy('图片')}</th>
					<th>${fns:fy('规格')}</th> 
					<th>${fns:fy('价格')}</th>
					<th>${fns:fy('库存')}</th>
				</tr>
			</thead> 
			<tbody>
				<c:forEach items="${productSkuList}" var="sku">
					<tr>
					<td>${sku.skuId}</td>
					<td>
						<c:set var="spec1" value="${fn:length(fn:split(sku.spec1, '_'))>1?fn:split(sku.spec1, '_')[1]:''}"></c:set>
						<c:set var="spec2" value="${fn:length(fn:split(sku.spec2, '_'))>1?fn:split(sku.spec2, '_')[1]:''}"></c:set>
						<c:set var="spec3" value="${fn:length(fn:split(sku.spec3, '_'))>1?fn:split(sku.spec3, '_')[1]:''}"></c:set>
						<c:set var="color" value="${fns:fy(color)}"></c:set>
						<c:if test="${spec1 eq color}">
							<c:set var="flag1" value="0"></c:set>
							<c:forEach items="${productPictureMappingList}" var="mapping">
								<c:if test="${mapping.color eq sku.spec1V && flag1 eq '0'}">
									<img src="${ctxfs}${mapping.storeAlbumPicture.path}@!40X40" class="specImage" onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');"/>
									<c:set var="flag1" value="1"></c:set>
								</c:if>
							</c:forEach>
						</c:if>
						
						<c:if test="${spec2 eq color}">
							<c:set var="flag2" value="0"></c:set>
							<c:forEach items="${product.productPictureMappingList}" var="mapping">
								<c:if test="${mapping.color eq sku.spec2V && flag2 eq '0'}">
									<img src="${ctxfs}${mapping.storeAlbumPicture.path}@!40X40" class="specImage" onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');"/>
									<c:set var="flag2" value="1"></c:set>
								</c:if>
							</c:forEach>
						</c:if>
						
						<c:if test="${spec3 eq color}">
							<c:set var="flag3" value="0"></c:set>
							<c:forEach items="${product.productPictureMappingList}" var="mapping">
								<c:if test="${mapping.color eq sku.spec3V && flag3 eq '0'}">
									<img src="${ctxfs}${mapping.storeAlbumPicture.path}@!40X40" class="specImage" onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');"/>
									<c:set var="flag3" value="1"></c:set>
								</c:if>
							</c:forEach>
						</c:if>
					</td>
					<td>
						<div>
							<c:if test="${not empty sku.spec1}">
								${fn:split(sku.spec1, '_')[1]}:${sku.spec1V};
							</c:if>
							<c:if test="${not empty sku.spec2}">
								${fn:split(sku.spec2, '_')[1]}:${sku.spec2V};
							</c:if>
							<c:if test="${not empty sku.spec3}">
								${fn:split(sku.spec3, '_')[1]}:${sku.spec3V};
							</c:if>
						</div>
						<%-- <div style="color: #999;">SKU:${sku.skuId}</div> --%>
					</td>
					<td>
						<span>
							<c:choose>
								<c:when test="${productSpu.type == '1'}">
									${sku.price}
								</c:when>
								<c:otherwise>
									<c:forEach items="${productSpu.productSectionPriceList}" var="section" varStatus="index">
										 <c:choose>
										 	<c:when test="${!index.last}">
										 		<div>${section.section} - ${productSpu.productSectionPriceList[index.index+1].section} ${productSpu.unit}&nbsp; ${section.price}${fns:fy('元')}</div>
										 	</c:when>
										 	<c:otherwise>
										 		≥${section.section} ${productSpu.unit}&nbsp; ${section.price}${fns:fy('元')}
										 	</c:otherwise>
										 </c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</span>
					</td>
					<td><span class="distributor-num">${sku.stock}</span></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>



<%-- <div class="modal-body sui-form form-horizontal">
	<div class="sui-msg msg-block msg-default msg-tips">
		<div class="msg-con">以下为供销平台上已经获得小二授权经营您的品牌但还未被您进行收编的供应商</div>
		<s class="msg-icon"></s>
	</div>
	<table class="sui-table table-bordered-simple">
		<thead>
			<tr>
				<th>SKU编号</th>
				<th>图片</th>
				<th>价格</th>
				<th>库存</th>
				<th>规格</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${productSkuList}" var="sku">
				<tr>
					<td>${sku.skuId}</td>
					<td>
						<c:choose>
							<c:when test="${not empty productSpu.image}">
								<img src="${ctxfs}${productSpu.image}@!80x80" width="32" height="32"></img>
							</c:when>
							<c:otherwise>
								<img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" width="32" height="32"></img>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<div>
							<c:if test="${not empty sku.spec1}">
								${fn:split(sku.spec1, '_')[1]}:${sku.spec1V};
							</c:if>
							<c:if test="${not empty sku.spec2}">
								${fn:split(sku.spec2, '_')[1]}:${sku.spec2V};
							</c:if>
							<c:if test="${not empty sku.spec3}">
								${fn:split(sku.spec3, '_')[1]}:${sku.spec3V};
							</c:if>
						</div>
						<div style="color: #999;">SKU:${sku.skuId}</div>
					</td>
					<td>
						<span>
							<c:choose>
								<c:when test="${productSpu.type == '1'}">
									<c:choose>
										<c:when test="${productSpu.productSkuList[0].price eq productSpu.productSkuList[fn:length(productSpu.productSkuList)-1].price }">
											${productSpu.productSkuList[0].price}${fns:fy('元')}
										</c:when>
										<c:otherwise>
											${productSpu.productSkuList[0].price}-${productSpu.productSkuList[fn:length(productSpu.productSkuList)-1].price}${fns:fy('元')}
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:forEach items="${productSpu.productSectionPriceList}" var="section" varStatus="index">
										 <c:choose>
										 	<c:when test="${!index.last}">
										 		<div>${section.section} - ${productSpu.productSectionPriceList[index.index+1].section} ${productSpu.unit}&nbsp; ${section.price}${fns:fy('元')}</div>
										 	</c:when>
										 	<c:otherwise>
										 		≥${section.section} ${productSpu.unit}&nbsp; ${section.price}${fns:fy('元')}
										 	</c:otherwise>
										 </c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</span>
					</td>
					<td><span class="distributor-num">${sku.stock}</span></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> --%>
