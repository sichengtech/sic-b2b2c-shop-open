<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>买家收货地址管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberAddressList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">买家收货地址列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 买家收货地址列表</a></li>
				<shiro:hasPermission name="member:memberAddress:edit"><li class=""><a href="${ctxa}/member/memberAddress/save1.do" > <i class="fa fa-user"></i> 买家收货地址添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>买家收货地址管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="member:memberAddress:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/member/memberAddress/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/member/memberAddress/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="addressId"  maxlength="19" class="form-control input-sm" placeholder="主键" value="${memberAddress.addressId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="uId"  maxlength="19" class="form-control input-sm" placeholder="关联(会员表)" value="${memberAddress.UId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="收货人" value="${memberAddress.name}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="countryId"  maxlength="19" class="form-control input-sm" placeholder="国家(关联地区表)" value="${memberAddress.countryId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="countryName"  maxlength="64" class="form-control input-sm" placeholder="国家名字" value="${memberAddress.countryName}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="provinceId"  maxlength="19" class="form-control input-sm" placeholder="省(关联地区表)" value="${memberAddress.provinceId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="provinceName"  maxlength="64" class="form-control input-sm" placeholder="省名字" value="${memberAddress.provinceName}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="cityId"  maxlength="19" class="form-control input-sm" placeholder="市(关联地区表)" value="${memberAddress.cityId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="cityName"  maxlength="64" class="form-control input-sm" placeholder="市名字" value="${memberAddress.cityName}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="districtId"  maxlength="19" class="form-control input-sm" placeholder="县(关联地区表)" value="${memberAddress.districtId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="districtName"  maxlength="64" class="form-control input-sm" placeholder="县名字" value="${memberAddress.districtName}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="detailedAddress"  maxlength="255" class="form-control input-sm" placeholder="详细地址" value="${memberAddress.detailedAddress}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="mobile"  maxlength="64" class="form-control input-sm" placeholder="联系方式（手机，电话号码）" value="${memberAddress.mobile}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="zipCode"  maxlength="64" class="form-control input-sm" placeholder="邮编" value="${memberAddress.zipCode}"/>
					</div>
					<div class="col-sm-1">
						<c:forEach items="${fns:getDictList('member_default')}" var="item">
						<label class="checkbox-inline">
						<input type="radio" name="isDefault" value="${item.value}" ${item.value==memberAddress.isDefault?"checked":""}/> ${item.label}
						</label>
						</c:forEach>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberAddress.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberAddress.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束CreateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberAddress.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择开始UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${memberAddress.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请选择结束UpdateDate时间"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>主键</th>
						<th>关联(会员表)</th>
						<th>收货人</th>
						<th>国家(关联地区表)</th>
						<th>国家名字</th>
						<th>省(关联地区表)</th>
						<th>省名字</th>
						<th>市(关联地区表)</th>
						<th>市名字</th>
						<th>县(关联地区表)</th>
						<th>县名字</th>
						<th>详细地址</th>
						<th>联系方式（手机，电话号码）</th>
						<th>邮编</th>
						<th>是否默认(0不默认 1默认)</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="member:memberAddress:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="memberAddress">
					<tr>
						<td><a href="${ctxa}/member/memberAddress/edit1.do?addressId=${memberAddress.addressId}">${memberAddress.addressId}</a></td>
						<td>${memberAddress.UId}</td>
						<td>${memberAddress.name}</td>
						<td>${memberAddress.countryId}</td>
						<td>${memberAddress.countryName}</td>
						<td>${memberAddress.provinceId}</td>
						<td>${memberAddress.provinceName}</td>
						<td>${memberAddress.cityId}</td>
						<td>${memberAddress.cityName}</td>
						<td>${memberAddress.districtId}</td>
						<td>${memberAddress.districtName}</td>
						<td>${memberAddress.detailedAddress}</td>
						<td>${memberAddress.mobile}</td>
						<td>${memberAddress.zipCode}</td>
						<td>${fns:getDictLabel(memberAddress.isDefault, 'member_default', '')}</td>
						<td><fmt:formatDate value="${memberAddress.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${memberAddress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="member:memberAddress:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/member/memberAddress/edit1.do?addressId=${memberAddress.addressId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/member/memberAddress/delete.do?addressId=${memberAddress.addressId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						</td>
						</shiro:hasPermission>
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
</body>
</html>