<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品参数')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productParamListForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('商品参数')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right"></ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('商品管理.商品参数.操作提示1')}</li>
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
					</div>
				</div>
				<div class="col-sm-8"></div>
				<div class="col-sm-2" style="text-align: right;">
					<a id="addRow" href="javaScript:void(0);" class="btn btn-primary">${fns:fy('新增参数')}</a>
				</div>			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<form id="inputForm" action="${ctxa}/product/productCategory/saveCatelist.do" method="post">
			<input type="hidden" name="categoryId" value="${categoryId}">
			<table class="table table-hover table-condensed table-bordered" id="tab">
				<thead>
					<tr>
						<th>${fns:fy('参数名')}</th>
						<th>${fns:fy('参数值')}</th>
						<th>${fns:fy('排序')}</th>
						<th>${fns:fy('是否必填')}</th>
						<th>${fns:fy('是否显示')}</th>
						<shiro:hasPermission name="product:productCategory:edit"><th>${fns:fy('操作')}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${page.list}" var="productParam">
					<tr>
                        <input type="hidden" class="form-control input-sm" name="paramId"  maxlength="12" class="form-control input-sm" value="${productParam.paramId}" placeholder="">
						<td style="text-align:center">
                            <input type="text" class="form-control input-sm" name="name"  maxlength="64" class="form-control input-sm" value="${productParam.name}" placeholder="" style="width:150px;margin:0;padding:0;text-align:center;">
						</td>
						<td>
                            <textarea class="form-control input-sm" rows="3" name="paramValues">${productParam.paramValues}</textarea>
						</td>
						<td style="text-align:center">
							<input type="text" name="paramSort"  maxlength="64" class="form-control input-sm" value="${productParam.paramSort}" style="width:50px;margin:0;padding:0;text-align:center;"/>
                        </td>
						<td>
							<input type="checkbox" ${productParam.isRequired eq 0?"":"checked"} value="1" name="isRequired" data-switch-no-init/>
						</td>
						<td>
							<input type="checkbox" ${productParam.isDisplay eq 0?"":"checked"} value="1" name="isDisplay" data-switch-no-init/>
						</td>
						<shiro:hasPermission name="product:productCategory:edit">
						<td>
							<button type="button" class="btn btn-danger btn-sm delButton">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
							</button>
						</td>
						</shiro:hasPermission>
					</tr>
					</c:forEach>
				</tbody>
				</table>
				<div class="col-sm-5">
					<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
						<i class="fa fa-times"></i> ${fns:fy('返回')}
					</button>
					<shiro:hasPermission name="product:productCategory:edit">
					<button type="submit" class="btn btn-info">
						<i class="fa fa-check"></i> ${fns:fy('保存')} 
					</button>
					</shiro:hasPermission>
				</div>
				</form>
			</div>
			<!-- table结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<!-- 隐藏的开始 -->
	<table style="display: none;">
		<tbody class="addLine">
			 <tr>
                <input type="hidden" class="form-control input-sm" name="paramId"  maxlength="12" class="form-control input-sm" placeholder="">
				<td style="text-align:center">
					<input type="text" class="form-control input-sm" name="name"  maxlength="64" class="form-control input-sm"  placeholder="" style="width:150px;margin:0;padding:0;text-align:center;">
				</td>
				<td>
					<textarea class="form-control input-sm" rows="3" name="paramValues"></textarea>
				</td>
				<td style="text-align:center">
					<input type="text" name="paramSort"  maxlength="10" class="form-control input-sm" value="10" style="width:50px;margin:0;padding:0;text-align:center;"/>
                      </td>
				<td>
					<input type="checkbox" checked value="1"  name="isRequired" data-switch-no-init/>
				</td>
				<td>
					<input type="checkbox" checked value="1" name="isDisplay" data-switch-no-init/>
				</td>
				<shiro:hasPermission name="product:productCategory:edit">
				<td>
					<button type="button" class="btn btn-danger btn-sm delButton">
						<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
					</button>
				</td>
				</shiro:hasPermission>
			 </tr>
		</tbody>
	</table>
	<!-- 隐藏的行结束 -->
</body>
</html>