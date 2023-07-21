<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺等级")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctxStatic}/echarts3.6.2/echarts.min.js"></script>
</head>
<body>
    <!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("店铺等级")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("店铺明细")}</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("报表管理.店铺等级.操作提示1")}</li>
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
				<form action="${ctxa}/site/siteCarouselPicture/list.do" method="get" id="searchForm">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-2">
					</div>
					<div class="col-sm-2">
					</div>
					<div class="col-sm-2">
					</div>
					<div class="col-sm-1" style="text-align: right;">
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
		    <c:if test="${not empty list}">
		    <div id="main" style="width: 1200px;height:400px;"></div>
		    </c:if>
	        <script type="text/javascript">
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('main'));
		        // 指定图表的配置项和数据
		        var option = {
		        	    title : {
		        	        text: '${fns:fy("店铺等级统计")}',
		        	        x:'center'
		        	    },
		        	    tooltip : {
		        	        trigger: 'item',
		        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		        	    },
		        	    legend: {
		        	        orient: 'vertical',
		        	        left: 'left',
		        	        data: [<c:forEach items="${list}" var="report" >'${report.LEVELNAME}',</c:forEach>]
		        	    },
		        	    series : [
		        	        {
		        	            name: '${fns:fy("店铺等级")}',
		        	            type: 'pie',
		        	            radius : '55%',
		        	            center: ['50%', '60%'],
		        	            data:[
									<c:forEach items="${list}" var="report" >{value:${report.STORENUM}, name:'${report.LEVELNAME}'},</c:forEach>
		        	            ],
		        	            itemStyle: {
		        	                emphasis: {
		        	                    shadowBlur: 10,
		        	                    shadowOffsetX: 0,
		        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		        	                }
		        	            }
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
						<th>${fns:fy("店铺等级")}</th>
						<th>${fns:fy("店铺个数")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${list}" var="report" >
						<tr>
							<td>${report.LEVELNAME}</td>
							<td>${report.STORENUM}</td>
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
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>