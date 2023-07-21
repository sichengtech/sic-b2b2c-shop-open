$(document).ready(function(){
	
	//parent是js自带的全局对象，可用于操作父页面
	var index = parent.layer.getFrameIndex(window.name); //获取父页面中的layer弹层的窗口索引
	//让弹层自适应iframe
	parent.layer.iframeAuto(index);
	
	/**
	 * 按首字母搜索
	 */
	$("ul .tag-selected").click(function(){
		var value=$(this).attr("value");
		if("all"==value){
			window.location.href=ctxs+"/product/productSpu/selectUnit.htm"; //搜索全部
		}else{
			window.location.href=ctxs+"/product/productSpu/selectUnit.htm?firstLetter="+value;//按首字母搜索
		}
	});
	
	/**
	 * 选用一个品牌
	 */
	$("a[name='selectUnit']").click(function(){
		var unitId=$(this).attr("unitId");
		var unitName=$(this).attr("unitName");
		//在iframe中查找父页面元素，并传值
		$('#unitId', window.parent.document).val(unitId);
		$('#unitName', window.parent.document).val(unitName);
		$('#unitName1', window.parent.document).val(unitName);
		//销售规则表中的单位
		$("#wholesaleTable .add-on-unit",window.parent.document).text(unitName);
		//销售规则设置预览表中的单位
		$("#setViewTable .unitNameView",window.parent.document).text(unitName);
		//隐藏行中的单位
		$("#addLineContent .add-on-unit",window.parent.document).text(unitName);
		$("#addViewContent .unitNameView",window.parent.document).text(unitName);
		//关闭弹出窗口
		window.parent.layer.close(index);
	});
	
	/**
	 * 表单验证
	 * 第一步 选择商品分类，的表单验证
	 */
	$("#searchForm").submit(function(){
		var name=$("input[name='name']").val();
		if(name==null || name==""){
			fdp.msg(fy.getMsg('请输入分类名称'));
			return false;
		}
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
		return true;
	});
	
});