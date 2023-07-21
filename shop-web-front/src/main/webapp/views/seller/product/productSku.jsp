<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<div class="modal-body sui-form form-horizontal">

	<table class="sui-table table-bordered-simple">
		<thead>
			<tr>
				<th width="10%"> </th>
				<th width="45%">${fns:fy('商品规格')}</th>
				<th width="15%">${fns:fy('价格')}</th>
				<th width="15%">${fns:fy('库存')}</th>
				<th width="15%">${fns:fy('商家编号')}</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${productSkuList}" var="sku">
				<tr>
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
					<td><span>
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
					</span></td>
					<td><span class="distributor-num">${sku.stock}</span></td>
					<td>${sku.sn}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
