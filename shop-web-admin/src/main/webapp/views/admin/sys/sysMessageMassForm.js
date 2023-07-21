$(function(){
	/**
	 * 页面初始化加载
	 */
	showUserList("","",1);
	
	/**
	 *	上一页
	 */
	$(".lastPage").click(function(){
		var typeUser = $(".typeUser option:selected").val();
		var loginName = $("input[name='loginName']").val();
		var pageNo = parseInt($("input[name='pageNo']").val());
		pageNo-=1;
		showUserList(typeUser,loginName,pageNo);
	});
	
	/**
	 *	下一页
	 */
	$(".nextPage").click(function(){
		var typeUser = $(".typeUser  option:selected").val();
		var loginName = $("input[name='loginName']").val();
		var pageNo = parseInt($("input[name='pageNo']").val());
		pageNo+=1;
		showUserList(typeUser,loginName,pageNo);
	});
	
	/**
	 * 搜索条件
	 */
	$(".search").click(function(){
		 searchContent(); 
	});
	
	/**
	 * 搜索回车事件
	 */
	$("#loginName").keydown(function(e){
		 if (e.keyCode == 13) {  
			 searchContent(); 
		 }
	});
	
	function searchContent(){
		var typeUser = $(".typeUser  option:selected").val();
		var loginName = $("input[name='loginName']").val();
		showUserList(typeUser,loginName,1);
	};
	
	/**
	 *	拼接会员列表模板
	 *	userType 	用户类型
	 *	loginName 	用户名
	 *	pageNo	 	当前页码
	 */
	function showUserList(typeUser,loginName,pageNo){
		var index = layer.msg(fy.getMsg('正在加载，请稍等'), {icon: 16,shade: 0.30,time: 0});
		$.ajax({
			url:ctxa+"/sys/sysMessage/userList.do",
			type:"POST",
			data:{typeUser:typeUser,loginName:loginName,pageNo:pageNo},
			dataType:"json",
			success:function(data){
				if(data!=null){
					$("#memberTable").html("");
					if(typeof(data.page)!="undefined" && data.page.list!=null && typeof(data.page.list)!="undefined"){
						var length = data.page.list.length;
						var userMainList = data.page.list;
						var pageNo = data.page.pageNo;
						$("input[name='pageNo']").val(pageNo);
						$("input[name='loginName']").val(data.loginName);
						$("typeUser-"+data.typeUser+"").attr("selected",true);
						var tpl = $("#messageTpl1").html();//模板
						for(var i=0;i<length;i++){
							var UId = userMainList[i].id;
							var isTypeUserMember = userMainList[i].typeUserMember;
							var isTypeUserSeller = userMainList[i].typeUserSeller;
							var isTypeUserService = userMainList[i].typeUserService;
							var isTypeUserPurchaser = userMainList[i].typeUserPurchaser;
							var loginName = userMainList[i].loginName;
							var dataJson = {"UId":UId,"isTypeUserMember":isTypeUserMember,"isTypeUserSeller":isTypeUserSeller,"isTypeUserService":isTypeUserService,"isTypeUserPurchaser":isTypeUserPurchaser,"loginName":loginName};//jsonData是从库中查出来的数据
							var userMainTr = render(tpl,dataJson);
							$("#memberTable").append(userMainTr);
						}
						//如果有已选会员在会员列表上选中
						var chooseUser = $("chooseUser").html();
						$(".chooseUId").each(function(){
							var chooseHiddenUId = $(this).val();
							$(".ischecked-"+chooseHiddenUId+"").attr("checked", true);
						});
					}else{
						var tpl = $("#messageTpl2").html();//模板
						$("#memberTable").append(tpl);
					}
					if(data.isFirst){
						$(".lastPage").parent().addClass("disabled");
					}else{
						$(".lastPage").parent().removeClass("disabled");
					}
					if(data.isLast){
						$(".nextPage").parent().addClass("disabled");
					}else{
						$(".nextPage").parent().removeClass("disabled");
					}
				}else{
					var tpl = $("#messageTpl2").html();//模板
					$("#memberTable").append(tpl);
				}
				layer.close(index);
			}
		});
	}
	
	/**
	 * 选中或取消会员
	 */
	$("#memberTable").delegate(".checkboxUId","click",function(){
		var isChoose = $(this).is(':checked');
		var chooseUId = $(this).attr("UId");
		if(isChoose){
			//选中
			var isExistence = true;
			$(".chooseUId").each(function(){
				var chooseHiddenUId = $(this).val();
				if(chooseUId == chooseHiddenUId){
					isExistence = false;
				}
			});
			if(isExistence){
				var loginName = $(this).parent().find(".chooseHiddenUId").attr("loginName");
				var UId = $(this).parent().find(".chooseHiddenUId").val();
				var tpl = $("#messageTpl3").html();//模板
				var dataJson = {"UId":UId,"loginName":loginName};
				var userMainBtn = render(tpl,dataJson);
				$(".chooseUser").append(userMainBtn);
			}
		}else{
			//取消
			$(".chooseUId").each(function(){
				var chooseHiddenUId = $(this).val();
				if(chooseUId == chooseHiddenUId){
					$(this).parent().remove();
				}
			});
		}
	});
	
	/**
	 * 取消已选会员
	 */
	$(".chooseUser").delegate(".selected_btn","click",function(){
		var chooseHiddenUId = $(this).find(".chooseUId").val();
		$(this).remove();
		$(".ischecked-"+chooseHiddenUId+"").attr("checked", false);
	});
	
	/**
	 * 提交表单
	 */
	$(".inputFormSubmit").click(function(){
		var uIds = "";
		$(".chooseUId").each(function(){
			uIds += "," + $(this).val();
		});
		var message = $(".message").val();
		if(uIds==null || uIds==''){
			layer.msg(fy.getMsg('请选择会员'));
			return false;
		}
		if(message==null || message==''){
			layer.msg(fy.getMsg('请输入消息内容'));
			return false;
		}
		var sysMode = "";
		$(".single-row").each(function(){
			var type = $(this).children().is('.checked');
			if(type == true){
				sysMode += "," + $(this).children().find($("input[name='sysMode']")).val();
			}
		});
		if(sysMode==null || sysMode==''){
			return false;
		}
		var index = layer.msg(fy.getMsg('正在加载，请稍等') + '...', {icon: 16,shade: 0.30,time: 0});
		$.ajax({
			url:ctxa + "/sys/sysMessage/mass2.do",
			type:"POST",
			data:{"uIds":uIds.substr(1,uIds.length),"message":message,"sysMode":sysMode.substr(1,sysMode.length)},
			dataType:"json",
			success:function(data){
				var status = data.status;
				var content = data.content;
				if(status=='1'){
					layer.msg(content); 
				}else{
					layer.msg(content); 
				}
				layer.close(index);
			},
			error: function(data){
				layer.msg(fy.getMsg('通知群发失败'));
				return false;
			}
		});
	});
	
	/**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
	
});