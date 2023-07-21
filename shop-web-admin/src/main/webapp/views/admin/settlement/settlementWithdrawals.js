function showContent(e){
	if(e==1){
		$("#payTimeDiv").css("display","block");
		$("#refuseReasonDiv").css("display","none");
	}
	if(e==2){
		$("#payTimeDiv").css("display","none");
		$("#refuseReasonDiv").css("display","block");
	}
}