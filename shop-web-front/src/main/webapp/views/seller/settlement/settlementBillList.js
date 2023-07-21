//确认账单
$(".sure").click(function(){
	var href=$(this).attr("href");
	fdp.confirm(fy.getMsg('您确认账单数据计算无误吗？'),href);
});
