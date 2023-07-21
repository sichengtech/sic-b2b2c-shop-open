<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>投诉详情</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	 .header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
	 .header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	 #delTable td{line-height: 30px;}
	 .checked li{line-height: 25px;}
	 .steps-round-auto{width: 98%!important;}
	 .right{text-align: right!important;}
	 .sui-table th, .sui-table td{border-bottom: 1px dotted #e6e6e6;}
	 .sui-table.table-bordered{border:none!important;}
	 .typographic ul{text-align: left!important;}
	 .sui-form{margin:0!important;}
	 .sui-table{ margin-bottom: 0px!important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="sui-row-fluid">
			<div class="goods-list">
			<dl class="box">
				<dt class="box-header">
					<h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>投诉详情</span></h4>
					<ul class="sui-breadcrumb">
						<li>当前位置:</li>
						<li>售后</li>
						<li>售后管理</li>
						<li>投诉详情</li>
					</ul>
				</dt>
				<!--状态导航开始 -->
				<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0">
					<div class="header header1" style="background: none;height: 37px; box-shadow: none;border-bottom: 1px solid #e5e5e5;margin-bottom: 0px;">
						<div class="sui-container">
							<div class="sui-navbar container nav-box">
								<div class="navbar-inner">
									<ul class="sui-nav">
										<li class=""><a style="height: 37px;line-height: 37px;" href="${ctx}/views/seller/trade/tradeComplaintList.jsp">投诉管理</a></li>
										<li class="active"><a style="height: 37px;line-height: 37px;" href="${ctx}/views/seller/trade/tradeComplaintInfo.jsp">详情</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</dd>
				<!--状态导航结束 --> 
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<div class="sui-steps-round steps-round-auto steps-5">
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>新投诉</label>
							</div>
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>待申诉</label>
							</div>
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>对话中</label>
							</div>
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>待仲裁</label>
							</div>
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>已完成</label>
							</div>
						</div>
						<table class="sui-table table-bordered">
							<tbody>
								<tr class="sep-row" colspan="2" style="height:15px;"></tr>
								<tr>
									<th colspan="2"> 
										<label class="pull-left">投诉信息</label>
									</th>
								</tr>
								<tr>
									<td width="20%" class="right">投诉状态：</td>
									<td width="80%">已完成</td>
								</tr>
								<tr>
									<td width="20%" class="right">投诉时间：</td>
									<td width="80%">2016-09-20 14:16:12</td>
								</tr>
								<tr>
									<td width="20%" class="right">投诉主题：</td>
									<td width="80%">未收到货</td>
								</tr>
								<tr>
									<td width="20%" class="right">投诉内容：</td>
									<td width="80%">发货太慢了</td>
								</tr>
								<tr>
									<td width="20%" class="right">投诉凭证：</td>
									<td width="80%">
										<div class="typographic">
											<img src="${ctxStatic}/sicheng-seller/images/tousu.jpg" width="50px;" height="50px;">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="sui-table table-bordered">
							<tbody>
								<tr>
									<th colspan="2"> 
										<label class="pull-left">申诉信息</label>
									</th>
								</tr>
								<tr>
									<td width="20%" class="right">投诉时间：</td>
									<td width="80%">2016-09-20 14:28:26</td>
								</tr>
								<tr>
									<td width="20%" class="right">投诉内容：</td>
									<td width="80%">发货不晚啊</td>
								</tr>
								<tr>
									<td width="20%" class="right">申诉凭证：</td>
									<td width="80%">
										<div class="typographic">
											<img src="${ctxStatic}/sicheng-seller/images/tousu.jpg" width="50px;" height="50px;">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="sui-table table-bordered">
							<tbody>
								<tr>
									<th colspan="2"> 
										<label class="pull-left">平台最终处理</label>
									</th>
								</tr>
								<tr>
									<td width="20%" class="right">处理时间：</td>
									<td width="80%">2016-09-20 14:33:21</td>
								</tr>
								<tr>
									<td width="20%" class="right">处理内容：</td>
									<td width="80%">发货太慢了，这是最后的仲裁意见</td>
								</tr>
							</tbody>
						</table>
						<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 100px;" href="javascript:;">返回列表</a>
					</div>
					<div class="span5">
						<div class="modal-content">
							<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
								<h4 id="myModalLabel" class="modal-title">相关商品交易信息</h4>
							</div>
							<div class="modal-body" style="padding: 0;">
								<table class="sui-table">
									<tbody>
										<tr>
											<td style="border-top:1px solid #e6e6e6;">
												<div class="typographic">
													<img src="${ctxStatic}/sicheng-seller/images/1-150RQ303470-L.jpg" width="45px;" height="45px;">
													<ul class="unstyled">
														<li><a href="javascript:;" target="_blank">荣耀 Type C 转接头 无缝接头 USB转接...</a></li>
														<li>￥11.99 * 7 (数量)</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>运 费： 33.89</li>
														<li>订单总额： ￥2820.79</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>订单编号： 1360000000001800</li>
														<li>物流单号：</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="支付方式：微信（WAP）</br>下单时间：2016-06-24 11:36:02</br>支付时间：2016-06-24 11:36:02</br>发货时间：2016-06-24 11:36:02）</br>订单完成时间：2016-06-24 11:36:02">
															更多<i class="sui-icon icon-pc-chevron-bottom"></i>
														</span>
													</li>
												</div>
											</td>
										</tr>
										<tr>
											<td style="border-bottom:none;">
												<div class="typographic">
													<ul class="unstyled">
														<li>收货人： 小王</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" title="收货地址：红旗路220号</br>联系电话：13322352353">
															更多<i class="sui-icon icon-pc-chevron-bottom"></i>
														</span>

													</li>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</dl>
			</div>
		</div>
	</div>
</body>
</html>