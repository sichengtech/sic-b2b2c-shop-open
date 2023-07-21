$(function(){
	$("#searchWeek").val();
	
	//更新周select数据
	$("#serchMonth").bind("change", function(){
		var year = $("#year").val();
		var month = $(this).val();
		$("#searchWeek").html('');
		var result = '';
		$.ajax({
            url:ctxa+'/report/user/getWeek.do',
            type:'post',
            data:{year:year,month:month},
            dataType: 'json',
            success:function(data){
            	if(data != null){
	             	for(i in data){
	             		result+='<option value="'+data[i]+'">'+data[i]+'</option>';
	             	}
	             	$("#searchWeek").append(result);
	             }
            }
		});
	});
});