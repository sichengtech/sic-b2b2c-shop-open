$(function(){
	var status=$(".status").val();
	if(status == '2'){
		var i = 5; 
		var intervalid; 
		var fun=function fun(){ 
			if (i == 0) { 
				window.location.href = ctxsso+"/login/loginSuccess.htm"; 
				clearInterval(intervalid); 
			} 
			$("#mes").text(i); 
			i--; 
		}
		intervalid = setInterval(fun, 1000); 
	}
});