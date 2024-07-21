/**
 * 参数管理器
 * 参数例子：www.a.b.c.htm?attr=1_北京:上海,price_60-100:10-50&sort=collectionCount&sortMode=DESC
 * @author cl
 * @version 2017-06-06
 */
(function(){
	/**
	 * manager命名空间,起隔离作用，防止同名变量冲突
	 */
	if(!window.manager) {window.manager={};};
	
	/**
	 * 往urlCache临时缓存放入url
	 * 调用示例：manager.putUrl();
	 */
	manager.putUrl = function(url){
		window.manager.urlCache = url;
	};
	
	/**
	 * 获取urlCache临时缓存的url
	 * 调用示例：manager.getUrl();
	 */
	manager.getUrl = function(){
		if(window.manager.urlCache){
			return window.manager.urlCache;
		}else{
			var url = window.location.href;
			window.manager.urlCache = url;
			return url;
		}
	};
	
	/**
	 * 获取url中的domain(从urlCache中的获取的url)
	 * 调用示例：manager.getDomain();
	 */
	manager.getDomain = function(){
		var url = manager.getUrl();
		var url_arr = url.split("?");
		var domain = url_arr[0];
		return domain;
	};
	
	/**
	 * 获取url中的param(从urlCache中的获取的url)
	 * 调用示例：manager.getParam();
	 */
	manager.getParam = function(){
		var url = manager.getUrl();
		var url_arr = url.split("?");
		var param = "";
		if(url_arr.length > 1){
			param = url_arr[1];
		}
		return decodeURIComponent(param);
	};
	
	/**
	 * 获取url中的param
	 * 调用示例：manager.getParamOld();
	 */
	manager.getParamOld = function(){
		var url = window.location.href;
		var url_arr = url.split("?");
		var param = "";
		if(url_arr.length > 1){
			param = url_arr[1];
		}
		return decodeURIComponent(param);
	};
	
	/**
	 * 参数进行拆分成map(从urlCache中的获取的url)
	 * 调用示例：manager.getParamMap();
	 */
	manager.getParamMap = function(){
		var param = manager.getParam();
		var map = {};
		if(param == ""){
			return map;
		}
		var params = param.split("&");
		for (var i = 0; i < params.length; i++) {
			var paramss = params[i].split("=");
			var key ="";
			var value ="";
			if(paramss.length > 1){
				key = paramss[0];
				value = paramss[1];
			}
			map[key] = value;
		}
		return map;
	};
	
	/**
	 * 参数进行拆分成map
	 * 调用示例：manager.getParamMapOld();
	 */
	manager.getParamMapOld = function(){
		var param = manager.getParamOld();
		var map = {};
		if(param == ""){
			return map;
		}
		var params = param.split("&");
		for (var i = 0; i < params.length; i++) {
			var paramss = params[i].split("=");
			var key ="";
			var value ="";
			if(paramss.length > 1){
				key = paramss[0];
				value = paramss[1];
			}
			map[key] = value;
		}
		return map;
	};
	
	/**
	 * 获取url中某个参数的值(从urlCache中的获取的url)
	 * 调用示例：manager.get("sort");
	 */
	manager.get = function(k){
		var value = "";
		var map = manager.getParamMap();
		if(k == null || k == ""){
			return value;
		}
		if(map[k]){
			return map[k];
		}else{
			return value;
		}
	};
	
	/**
	 * 获取url中某个参数的值
	 * 调用示例：manager.getOld("sort");
	 */
	manager.getOld = function(k){
		var value = "";
		var map = manager.getParamMapOld();
		if(k == null || k == ""){
			return value;
		}
		if(map[k]){
			return map[k];
		}else{
			return value;
		}
	};
	
	/**
	 * 获取url中某个参数中某个的值(从urlCache中的获取的url)
	 * 调用示例：manager.getAttr("attr","sort");
	 */
	manager.getAttr = function(attr,k){
		var value = "";
		var param = manager.get(attr);
		if(attr == null || attr == "" || k == null || k == "" ){
			return value;
		}
		if(param == null || param == ""){
			return value;
		}
		var params = param.split(",");
		for (var i = 0; i < params.length; i++) {
			var paramss = params[i].split("_");
			if(paramss.length > 1){
				if(k == paramss[0]){
					value = paramss[1];
					break;
				}
			}
		}
		return value;
	};
	
	/**
	 * 往url中加参数
	 * 调用示例：manager.add("attr","price_60-100:200-300");
	 */
	manager.add = function(k,v){
		manager.baseAdd(k,v);
//		manager.baseDel("pageNo");
//		manager.baseDel("pageSize");
	};
	
	/**
	 * 往url中某个key中放参数
	 * 调用示例：manager.addAttr("attr","price","60-100:200-300");
	 */
	manager.addAttr = function(attr,k,v){
		manager.baseAddAttr(attr,k,v);
	};
	
	/**
	 * 删url中的参数
	 * 调用示例：manager.del("price");
	 */
	manager.del = function(k){
		manager.baseDel(k);
	};
	
	/**
	 * 删url中参数中某个属性
	 * 调用示例：manager.delAttr("attr","price");
	 */
	manager.delAttr = function(attr,k){
		manager.baseDelAttr(attr,k);
	};
	
	/**
	 * 对url进行添加参数
	 * 调用示例：manager.baseAdd("price","60-100:200-300");
	 */
	manager.baseAdd = function(k,v){
		var domain = manager.getDomain();
		var param = manager.getParam();
		var map = manager.getParamMap();
		var pa = "";
		if(k == null || k ==""){
			return;
		}
		map[k] = v;//加入新参数，重名参数会被替换
		for(key in map){  
			var value = map[key];
			pa += "&" + key + "=" + value;
		}
		var url = domain + "?" + pa.substr(1);
		manager.putUrl(url);
	};
	
	/**
	 * 对url中的某个参数进行添加属性
	 * 调用示例：manager.baseAdd("attr","price","60-100:200-300");
	 */
	manager.baseAddAttr = function(attr,k,v){
		var domain = manager.getDomain();
		var param = manager.getParam();
		var map = manager.getParamMap();
		var pa = "";
		var value = "";
		var status = true;
		if(attr == null || attr=="" || k == null || k =="" || v == null || v == ""){
			return;
		}
		if(Object.keys(map).length == 0){
			pa += "&" + attr + "=" + k + "_" + v;
		}else{
			for(key in map){ 
				value = map[key];
				if(attr == key){
					var value = map[key];
					map[key]=value + "," + k + "_" + v;
					status = false;
					break;
				}
			}
			if(status){
				map[attr] = k + "_" + v ;
			}
		}
		for(key in map){  
			var value = map[key];
			pa += "&" + key + "=" + value;
		}
		var url = domain + "?" + pa.substr(1);
		manager.putUrl(url);
	};
	
	/**
	 * 对url进行减少参数
	 * 调用示例：manager.baseAdd("price");
	 */
	manager.baseDel = function(k){
		var domain = manager.getDomain();
		var param = manager.getParam();
		var map1 = manager.getParamMap();//旧参数
		var map2 = {};//新参数
		var delList = new Array();
		var pa = "";
		var url = "";
		if(k == null || k =="" || param == null || param == "" || Object.keys(map1).length == 0){
			return;
		}
		delList.push(k);
		for(key in map1){  
			var value = map1[key];
			for(var i = 0 ; i < delList.length ; i++){
				var delk = delList[i];
				if(delk != key){
					map2[key] = value;
				}
			}
		}
		for(key in map2){  
			var value = map2[key];
			pa += "&" + key + "=" + value;
		}
		if(pa==null || pa==""){
			url = domain;
		}else{
			url = domain + "?" + pa.substr(1);
		}
		manager.putUrl(url);
	};
	
	/**
	 * 对url的某个参数进行减少属性
	 * 调用示例：manager.baseAdd("attr","price");
	 */
	manager.baseDelAttr = function(attr,k){
		var domain = manager.getDomain();
		var param = manager.getParam();
		var map1 = manager.getParamMap();//参数
		var map2 = {};//某个参数属性的map
		var map3 = {};
		var pa = "";
		var pa1 = "";
		var val = manager.get(attr);
		if(attr == null || attr == "" || k == null || k == "" || param == null || param == "" || Object.keys(map1).length == 0){
			return domain;
		}
		if(val == null  || val == ""){
			return domain;
		}
		var vals = val.split(",");
		for (var i = 0; i < vals.length; i++) {
			var valss = vals[i].split("_");
			if(valss.length > 1){
				var key = valss[0];
				var value = valss[1];
				map2[key] = value;
			}
		}
		for(key in map2){
			if(k != key){
				map3[key] = map2[key];
			}
		}
		if(Object.keys(map3).length == 0){
			manager.del(attr);
		}else{
			for(key in map3){
				pa1 += "," + key + "_" + map3[key];
			}
			manager.add(attr,pa1.substr(1));
		}
		
	};
	
	
	//======================================测试用例start==============================================
//	var a1 = manager.getDomain();
//	
//	var a2 = manager.getParam();
//	
//	var a3 = manager.getParamOld();
//	
//	var a4 = manager.getParamMap();
//	
//	var a5 = manager.getParamMapOld();
//	
//	var a6 = manager.get("sort");
//	
//	var a7 = manager.getOld("sort");
//	
//	manager.addAttr("attr","2","手机:电脑");
//	var a7_1 = manager.getUrl();
//	
//	manager.add("attr","price_60-100:200-300");
//	var a8 = manager.getUrl();
//	
//	var a9 = manager.getParam();
//	
//	var a10 = manager.getParamOld();
//	
//	var a11 = manager.getParamMap();
//	
//	var a12 = manager.getParamMapOld();
//	
//	var a13 = manager.get("attr");
//	
//	var a14 = manager.getOld("attr");
//	
//	manager.add("pageNo","1");
//	var a16 = manager.getUrl();
//	
//	var a17 = manager.getAttr("attr","price");
//	
//	manager.addAttr("attr","1","北京:上海");
//	var a18 = manager.getUrl();
//	
//	manager.addAttr("attr","商品所在地","Y-1670");
//	var a18_1 = manager.getUrl();
//	
//	manager.delAttr("attr","price");
//	var a19 = manager.getUrl();
	//======================================测试用例end==============================================
	
})();