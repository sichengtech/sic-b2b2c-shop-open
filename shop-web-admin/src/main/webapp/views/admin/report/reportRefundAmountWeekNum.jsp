<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("退款金额-按周统计")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- echarts的js -->
<script type="text/javascript" src="${ctxStatic}/echarts3.6.2/echarts.min.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/report/getWeekDate.js"></script>
</head>
<body>
    <!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("退款金额")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/report/refundAmount/countDayNum.do"> <i class="fa fa-user"></i> ${fns:fy("按天统计")}</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("按周统计")}</a></li>
				<li class=""><a href="${ctxa}/report/refundAmount/countMonthNum.do"> <i class="fa fa-user"></i> ${fns:fy("按月统计")}</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("统计图展现了时间段内退款金额的走势情况")}</li>
					<li>${fns:fy("列表展示了时间段内退款详细数据，便于详细了解退款记录的具体信息")}</li>
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
					</div>
				</div>
				<form action="${ctxa}/report/refundAmount/countWeekNum.do" method="get" id="searchForm">
					<div class="col-sm-5">
					</div>
					<div class="col-sm-1">
						<select id="year" class="form-control" name="year" style="height: 30px;font-size: 12px;">
							<c:forEach items="${fns:getDictList('sys_year')}" var="year">
								<option value="${year.label}" ${year.label eq year?"selected":""}>${year.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1">
						<select id="serchMonth" class="form-control" name="serchMonth" style="height: 30px;font-size: 12px;">
							<c:forEach items="${months}" var="month">
								<option value="${month}" ${month eq serchMonth?"selected":""}>${month}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<select id="searchWeek" class="form-control" name="searchTime" style="height: 30px;font-size: 12px;">
							<c:forEach items="${listWeek}" var="week">
								<option value="${week}" ${week eq searchTime?"selected":""}>${week}</option>
							</c:forEach>
						</select>
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
		    <div id="main" style="width:100%;height:400px;"></div>
		        <script type="text/javascript">
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('main'));
		        // 指定图表的配置项和数据
		        var option = {
		        	    title: {
		        	        text: ''
		        	    },
		        	    tooltip: {
		        	        trigger: 'axis'
		        	    },
		        	    legend: {
		        	        data:[]
		        	    },
		        	    grid: {
		        	        left: '3%',
		        	        right: '4%',
		        	        bottom: '3%',
		        	        containLabel: true
		        	    },
		        	    toolbox: {
		        	    	right:25,
		        	        feature: {
		        	            saveAsImage: {}
		        	        }
		        	    },
		        	    xAxis: {
		        	    	name:'${fns:fy("星期")}',
		        	        type: 'category',
		        	        boundaryGap: false,
		        	        data: ['${fns:fy("周一")}','${fns:fy("周二")}','${fns:fy("周三")}','${fns:fy("周四")}','${fns:fy("周五")}','${fns:fy("周六")}','${fns:fy("周日")}']
		        	    },
		        	    yAxis: {
		        	    	name:'${fns:fy("退款金额")}(${fns:fy("元")})',
		        	        type: 'value'
		        	    },
		        	    series: [
		        	        {
		        	            name:'${fns:fy("退款金额")}(${fns:fy("元")})',
		        	            type:'line',
		        	            stack: '',
		        	            smooth: true,
		        	            data:${jsontData}
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
						<th>${fns:fy("退款编号")}</th>
						<th>${fns:fy("店铺名称")}</th>
						<th>${fns:fy("商品名称")}</th>
						<th>${fns:fy("会员名称")}</th>
						<th>${fns:fy("申请时间")}</th>
						<th>${fns:fy("退款金额")}(${fns:fy("元")})</th>
						<th>${fns:fy("商家处理")}</th>
						<th>${fns:fy("平台处理")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="tradeReturnOrder">
						<tr>
							<td>${tradeReturnOrder.id}</td>
							<td>${tradeReturnOrder.tradeOrder.BName}</td>
							<td>${tradeReturnOrder.tradeOrderItem.PId}</td>
							<td>${tradeReturnOrder.userMain.loginName}</td>
							<td><fmt:formatDate value="${tradeReturnOrder.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${tradeReturnOrder.returnMoney}</td>
							<td>${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}</td>
							<td>${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
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