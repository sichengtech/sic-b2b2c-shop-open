(function (){
    //fy命名空间,起隔离作用，防止同名变量冲突
    //fy是国际化翻译的简写
    if(!window.fy) {window.fy={};}

    //根据当前环境获取中文值或英文值
    fy.getMsg=function (key, ...rest) {
        var curDate=new Date().getTime();
        //默认十小时更新一次
        var differenceDate=10;
        var oldDate=localStorage.getItem("sellerFyDate");
        if(typeof(oldDate)!="undefined" && oldDate!=null){
            differenceDate=(curDate-oldDate)/(1000*60*60);
        }
        if(differenceDate>=10 || localStorage.getItem("sellerFycnMap")==null || localStorage.getItem("sellerFyenMap")==null || localStorage.getItem("defaultLocale") == null){
            $.ajax({
                type:"GET",
                url:ctxs+"/fy/poperties.htm",
                data:{},
                async:false,
                //请求成功
                success : function(data) {
                    localStorage.setItem("sellerFycnMap",JSON.stringify(data.cnMap));
                    localStorage.setItem("sellerFyenMap",JSON.stringify(data.enMap));
                    localStorage.setItem("defaultLocale",data.defaultLocale);
                    localStorage.setItem("sellerFyDate",new Date().getTime());
                }
            });
        }
        //先从cookike中获取值
        var language=fdp.cookie('fylanguage');
        //cookie中没有使用默认语言
        if((typeof(language)=="undefined" || language == null)){
            if(localStorage.getItem('defaultLocale')!=null){
                language=localStorage.getItem('defaultLocale');
            }else{
                //没有设置默认语言，默认使用浏览器的语言
                language=navigator.language;
            }
        }
        //中文
        var fycnStr=localStorage.getItem('sellerFycnMap');
        var fycnMap=JSON.parse(fycnStr);
        //英文
        var fyenStr=localStorage.getItem("sellerFyenMap");
        var fyenMap=JSON.parse(fyenStr);
        var str="";
        if('zh_CN'==language || "zh-CN"==language){
            str=fycnMap[key];
        }else{
            str=fyenMap[key];
        }
        if(typeof(str)=="undefined" || ""==str){
            return key;
        }
        let arr = rest;
        arr.forEach((item => {
            str = str.replace(/(\{\w\})/, item)
        }))
        return str;
    }

    //获取当前使用的语言，中文返回zh_CN，英文返回en_US
    fy.getCurrentLanguage = function () {
        var language = fdp.cookie('fylanguage');
        if (typeof (language) == "undefined" || language == null) {
            language = navigator.language;
        }
        if ('zh_CN' == language || "zh-CN" == language) {
            return "zh_CN"
        } else {
            return "en_US";
        }
    }
})();
