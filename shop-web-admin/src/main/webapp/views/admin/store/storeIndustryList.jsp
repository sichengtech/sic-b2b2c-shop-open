<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>	
<title>${fns:fy("主营行业管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeIndustryList.js"></script>	
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
	    <header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("主营行业列表")}</h4>
			<%@ include  file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("主营行业列表")}</a></li>
				<shiro:hasPermission name="store:storeIndustry:save">
				<li class=""><a href="${ctxa}/store/storeIndustry/save1.do" > <i class="fa fa-user"></i> ${fns:fy("主营行业添加")}</a></li>
				</shiro:hasPermission>
			</ul>				
		</header>
		
		<!-- panel-body开始 -->          
        <div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
			    <h5>${fns:fy("操作提示")}</h5>
               	<ul>
               		<li>${fns:fy("店铺管理.主营行业.操作提示1")}</li>
               		<li>${fns:fy("店铺管理.主营行业.操作提示2")}</li>
               	</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
            <!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
			    <div class="col-sm-2">
	                <div class="btn-group">
	                    <!-- 刷新按钮 -->
	                    <button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
	                    <!-- 添加记录按钮 -->
	                    <shiro:hasPermission name="store:storeIndustry:save">
	                    <a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/store/storeIndustry/save1.do"><i class="fa fa-plus"></i></a>
	                    </shiro:hasPermission>
	                </div>
			    </div>
			    <div class="col-sm-7"></div>
			   <form action="${ctxa}/store/storeIndustry/list.do" method="get" id="searchForm">
				    <div class="col-sm-3">
						<div class="iconic-input right">
						   <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						   <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	                       <a href="javaScript:;" class="searchList"><i class="fa fa-search"></i></a>
	                       <input type="text" name="industryName" class="form-control input-sm pull-right" placeholder="${fns:fy("请输入主营行业进行搜索")}" value="${storeIndustry.industryName}" style="border-radius: 30px;max-width:250px;">
	                   </div>
					</div> 
				</form>   
			</div>                
            <!-- 按钮结束  --> 
	        <!-- Table开始 -->
	        <div class="table-responsive">
           		<table class="table table-hover table-condensed table-bordered">
                 <thead> 
                    <tr>   
						<th>${fns:fy("行业ID")}</th>
						<th>${fns:fy("主营行业名称")}</th>
						<th>${fns:fy("保证金")}(${fns:fy("元")})</th>
						<th>${fns:fy("排序")}</th>
						<th>${fns:fy("是否开启")}</th>
						<th>${fns:fy("操作")}</th>
                    </tr>
                </thead> 
                <tbody>
					<c:forEach items="${page.list}" var="storeIndustry">
					<tr>
						<td><a href="${ctxa}/store/storeIndustry/edit1.do?industryId=${storeIndustry.industryId}">${storeIndustry.industryId}</a></td>
						<td>${storeIndustry.industryName}</td>
						<td>${storeIndustry.industryMoney}</td>
						<td>${storeIndustry.sort}</td>
						<td>${fns:getDictLabel(storeIndustry.isOpen, 'is_open', '')}</td>
						<td>
							<shiro:hasPermission name="store:storeIndustry:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeIndustry/edit1.do?industryId=${storeIndustry.industryId}">
							  	<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="store:storeIndustry:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeIndustry/delete.do?industryId=${storeIndustry.industryId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
               			 </td>
					</tr>
					</c:forEach>
                </tbody>    
            	</table>
            </div>
            <!-- table结束 -->
            <!-- 分页信息开始 -->
            <%@ include  file="../include/page.jsp"%>
            <!-- 分页信息结束 -->
        </div>
        <!-- panel-body结束 -->
	</section>
	<!-- panel结束  -->
</body>
</html>