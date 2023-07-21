<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("商品销售-按月统计")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- echarts的js -->
<script type="text/javascript" src="${ctxStatic}/echarts3.6.2/echarts.min.js"></script>
</head>
<body>
    <!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("商品销售")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/report/product/countDayNum.do"> <i class="fa fa-user"></i> ${fns:fy("按天统计")}</a></li>
				<li class=""><a href="${ctxa}/report/product/countWeekNum.do"> <i class="fa fa-user"></i> ${fns:fy("按周统计")}</a></li>
				<li class="active"><a href="javascript:;"> <i class="fa fa-user"></i> ${fns:fy("按月统计")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("报表管理.店铺销售.操作提示4")}</li>
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
					</div>
				</div>
				<form action="${ctxa}/report/product/countMonthNum.do" method="get" id="searchForm">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-2">
						<select class="form-control" name="pCategory" style="height: 30px;font-size: 12px;${empty listCategory?'display:none':''}">
							<option value="">${fns:fy("请选择商品分类")}</option>
							<c:forEach items="${listCategory}" var="category">
								<option value="${category.PCATEGORY}" ${category.PCATEGORY eq pCategory?"selected":""}>${category.PCATEGORY}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("商品名称")}" name="proName" value="${proName}">
					</div>
					<div class="col-sm-2">
						<div class="input-group"> 
							<input name="searchTime" type="text" readonly="readonly" maxlength="20" placeholder="${fns:fy("请输入搜索日期")}" class="form-control input-sm J-date-start searchInput"
							value="<fmt:formatDate value="${searchTime}" pattern="yyyy-MM"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
			<c:if test="${not empty list}">
				<div id="main" style="width:100%;height:400px;"></div>
			</c:if>
		    <script type="text/javascript">
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('main'));
		        // 指定图表的配置项和数据
		        var option = {
		        	    title: {
		        	        text: '${fns:fy("商品销售统计")}',
		        	        subtext: ''
		        	    },
		        	    tooltip: {
		        	        trigger: 'axis',
		        	        axisPointer: {
		        	            type: 'shadow'
		        	        }
		        	    },
		        	    legend: {
		        	        data: ['${fns:fy("下单金额")}', '${fns:fy("下单量")}', '${fns:fy("下单商品数")}']
		        	    },
		        	    grid: {
		        	        left: '3%',
		        	        right: '4%',
		        	        bottom: '3%',
		        	        containLabel: true
		        	    },
		        	    xAxis: {
		        	    	name: '${fns:fy("商品ID")}',
		        	        type: 'category',
		        	        data: [<c:forEach items="${list}" var="report">${report.PRODUCTID},</c:forEach>]
		        	    },
		        	    yAxis: {
		        	        
		        	        type: 'value',
		        	        boundaryGap: [0, 0.01]
		        	    },
		        	    series: [
		        	        {
		        	            name: '${fns:fy("下单金额")}',
		        	            type: 'bar',
		        	            data: [<c:forEach items="${list}" var="report">${report.PAYMONEY},</c:forEach>]
		        	        },
		        	        {
		        	            name: '${fns:fy("下单量")}',
		        	            type: 'bar',
		        	            data: [<c:forEach items="${list}" var="report">${report.ORDERNUM},</c:forEach>]
		        	        },
		        	        {
		        	            name: '${fns:fy("下单商品数")}',
		        	            type: 'bar',
		        	            data: [<c:forEach items="${list}" var="report">${report.PRODUCTNUM},</c:forEach>]
		        	        }
		        	    ]
		        	};
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
		    </script>
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>${fns:fy("商品ID")}</th>
						<th>${fns:fy("商品名称")}</th>
						<th>${fns:fy("下单金额")}(${fns:fy("元")})</th>
						<th>${fns:fy("下单量")}</th>
						<th>${fns:fy("下单商品数")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${list}" var="report">
						<tr>
							<td>${report.PRODUCTID}</td>
							<td><a href="${ctxa}/product/productSpu/list.do?name=${report.PRODUCTNAME}">${report.PRODUCTNAME}</a></td>
							<td>${report.PAYMONEY}</td>
							<td>${report.ORDERNUM}</td>
							<td>${report.PRODUCTNUM}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
					<tr>
						<td datano="0" colspan="14" ><div class="notData">${fns:fy("无查询结果")}</div></td>
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
</body>
</html>