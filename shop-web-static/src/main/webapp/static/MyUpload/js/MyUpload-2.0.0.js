"use strict";
/**
 *  @MyUpload - v2.0.0 - 2019-08-07
 *  @author 1097085674@qq.com
 */
(function (root, factory) {
    if (typeof define === "function" && define.amd) {
        define(factory);
    }
    else {
        root.MyUpload = factory();
    }
})(window, function () {
    // 处理预览还没加载完就调用预览，，没有加载先订阅
    var Event = /** @class */ (function () {
        function Event() {
            this.obj = {};
        }
        Event.prototype.on = function (eventType, fn) {
            if (!this.obj[eventType]) {
                this.obj[eventType] = [];
            }
            this.obj[eventType].push(fn);
        };
        Event.prototype.emit = function () {
            var eventType = Array.prototype.shift.call(arguments);
            var arr = this.obj[eventType];
            if (!arr)
                return;
            for (var i = 0; i < arr.length; i++) {
                arr[i].apply(arr[i], arguments);
            }
        };
        return Event;
    }());
    var ev = new Event();
    // 只执行一次
    var once = function (fn, context) {
        var result;
        return function () {
            if (fn) {
                result = fn.apply(context || this, arguments);
                fn = null;
            }
            return result;
        };
    };
    var one = once(function () {
        // 预览原图
        var link = document.createElement('link');
        document.head.appendChild(link);
        link.setAttribute('rel', 'stylesheet');
        link.setAttribute('href', (window.MYUPLOAD_PREFIX_URL || '') + 'MyUpload/jquery-magnific-popup/magnific-popup.css');
        link.setAttribute('type', 'text/css');
        var script = document.createElement('script');
        document.body.appendChild(script);
        script.setAttribute('type', 'text/javascript');
        script.setAttribute('src', (window.MYUPLOAD_PREFIX_URL || '') + 'MyUpload/jquery-magnific-popup/jquery.magnific-popup.min.js');
        // 加载完调用
        script.onload = function () {
            ev.emit('script');
        };
    }, '');
    // 加载样式文件
    var loadingStyle23 = once(function () {
        $('head').prepend('<link rel="stylesheet" href="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/css/button.css">');
    }, '');
    var loadingStyle1 = once(function () {
        $('head').prepend('<link rel="stylesheet" href="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/css/imgButton.css">');
    }, '');
    var defaultValue = function (value) {
        var val;
        if (value || typeof value === 'undefined') {
            val = true;
        }
        else {
            val = false;
        }
        return val;
    };
    // 错误提示信息
    var errHint = function (message, lay) {
        if (lay) {
            lay.msg(message);
        }
        else {
            alert(message);
        }
    };
    /**
     * @function 验证上传个数为number类型,并返回值,可以提出去
     * @param { Number } fileNumLimit
     * @returns { Number }
     */
    var verifyNum = function (fileNumLimit) {
        if (typeof fileNumLimit === 'number') {
            return fileNumLimit;
        }
        else {
            return 10;
        }
    };
    /**
     * @function 验证duplicate为对象,并返回值
     * @param { Boolean } params
     */
    var verifyDuplicate = function (params) {
        if (Object.prototype.toString.call(params) === '[object Boolean]') {
            return params;
        }
        else {
            return true;
        }
    };
    /**
     * @function 验证compress为对象,并返回值
     * @param { Object }
     */
    var verifyCompress = function (params) {
        if (Object.prototype.toString.call(params) === '[object Object]') {
            return params;
        }
        else {
            return '';
        }
    };
    /**
     * @function 根据参数添加fileListid
     * @param { Object } params
     */
    var verifyDnd = function (params) {
        if (typeof params.buttonStyle === 'number' && params.buttonStyle === 1 || typeof params.buttonStyle === 'undefined') {
            return params.container;
        }
        else {
            return '#fileList' + params.container.slice(1);
        }
    };
    var MyUpload = /** @class */ (function () {
        function MyUpload(options) {
            // 提交文件的所有数据
            this.datas = [];
            // 上传数量计数
            this.count = 0;
            // 优化retina, 在retina下这个值是2
            this.ratio = window.devicePixelRatio || 1;
            // 缩略图大小
            this.thumbnailWidth = 100 * this.ratio;
            this.thumbnailHeight = 100 * this.ratio;
            // layer弹层变量
            this.lay = window.layer || null;
            // flash路径 
            this.swf = window.MYUPLOAD_PREFIX_URL + 'MyUpload/webuploader/Uploader.swf';
            // 计算回显后还能上传多少个文件
            this.uploadCount = verifyNum(options.fileNumLimit);
            // 允许上传数量
            this.fileNumLimit = verifyNum(options.fileNumLimit);
            this.againUpload = defaultValue(options.againUpload);
            // 上传成功触发
            this.uploadSuccess = options.uploadSuccess;
            // 上传出错触发
            this.uploadError = options.uploadError;
            // 上传完触发， 不管成功或失败
            this.uploadComplete = options.uploadComplete;
            // 上传删除
            this.uploadDelete = options.uploadDelete;
            // 上传前 获取文件流
            this.beforeFile = options.beforeFile;
            // 创建实例传的参数
            this.options = options;
            // 判断是否有必填参数
            this.expectParam();
            // 加载插件
            one();
        }
        /**
         * @function 回显函数
         * @param { Array } params
         */
        MyUpload.prototype.init = function (params) {
            var _this = this, data = params;
            // 判断传递进来的参数是否是数组
            if (Object.prototype.toString.call(data) === '[object Array]') {
                // 遍历参数 并创建DOM结构
                $.each(data, function (i, n /* { url: string; path: string; } */) {
                    if (!n) {
                        return false;
                    }
                    var $li;
                    if (_this.againUpload) {
                        $li = $('<div class="file-item thumbnail eached"><a><img class="echoed"></a><div class="remove-this"><i class="icon-my"><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/del.png" style="width: 18px; height: 18px; margin-top: 6px;"></i></div><div class="load"></div></div>');
                    }
                    else {
                        $li = $('<div class="file-item thumbnail eached"><a><img class="echoed"></a><div class="load"></div></div>');
                        // $(_this.shape.options.pick).css({'display':'none'});
                    }
                    var $img = $li.find('a img'), $a = $li.find('a'), $load = $li.find('div.load');
                    //翻译
                    var fyInfo1=fy.getMsg("历史图片加载中");
                    console.log(fyInfo1);
                    if(typeof(fyInfo1)=="undefined" || fyInfo1=='' || fyInfo1==null){
                        fyInfo1="历史图片加载中";
                    }
                    $load.text(fyInfo1);
                    // 根据isSafe拼接url
                    // 支持 accessKey 说明是思程系统同是就支持缩略规则
                    var accessKey;
                    if (typeof _this.options.formData !== 'undefined' && _this.options.formData.isSafe) {
                        console.log('abc');
                        if (typeof _this.options.accessKey === 'string') {
                            accessKey = _this.options.accessKey;
                            $a.attr('href', n + '?accessKey=' + accessKey);
                            $img.attr('src', n + '@!100X100?accessKey=' + accessKey);
                        }
                    }
                    else {
                        console.log($img, n);
                        $img.attr('src', n);
                        $a.attr('href', n);
                    }
                    $(_this.options.container + ' .gallery').append($li);
                    // 监听图片加载完成
                    $img.each(function (i, e) {
                        $(e).load(function () {
                            //翻译
                            var fyInfo2=fy.getMsg("图片加载完成");
                            if(typeof(fyInfo2)=="undefined" || fyInfo2=='' || fyInfo2==null){
                                fyInfo2="图片加载完成";
                            }
                            $load.text(fyInfo2);
                            setTimeout(function () {
                                //翻译
                                var fyInfo3=fy.getMsg("历史图片");
                                if(typeof(fyInfo3)=="undefined" || fyInfo3=='' || fyInfo3==null){
                                    fyInfo3="历史图片";
                                }
                                $load.text(fyInfo3);
                            }, 600);
                        });
                    });
                    // _this.datas.push({ path: n.path, url: n.url });
                });
                // 回显后能上传的个数 = 允许上传的数量-回显的数量
                _this.uploadCount = _this.fileNumLimit - data.length;
                _this.removerFileMessage(_this.options, _this.shape.options);
                // 绑定删除事件
                $(_this.options.container + ' .eached .remove-this').click(function () {
                    var imgUrl = $($(this).parent().find('a img')).attr('src').split('@')[0], deleteUrl = imgUrl, dataIndex = 0;
                    // 从实例的data中删除对应的数据
                    $.each(_this.datas, function (i, n) {
                        if (n.url === deleteUrl) {
                            dataIndex = i;
                            data.splice(i, 1);
                        }
                    });
                    // 删除回显图片，通知使用者
                    if (typeof _this.uploadDelete === 'function') {
                        _this.uploadDelete(deleteUrl);
                    }
                    // 从datas中删除该条数据
                    _this.datas.splice(dataIndex, 1);
                    // 删除DOM该条数据的结构
                    $(this).parent().remove();
                    _this.removerFileMessage(_this.options, _this.shape.options);
                    _this.uploadCount = _this.uploadCount + 1;
                });
            }
            else {
                //翻译
                var fyInfo4=fy.getMsg("参数错误","init");
                if(typeof(fyInfo4)=="undefined" || fyInfo4=='' || fyInfo4==null){
                    fyInfo4="init参数错误";
                }
                errHint(fyInfo4, _this.lay);
                return false;
            }
        };
        ;
        /**
         * @function 判断传递进来的参数是对象,参数是否有值初始参数之前
         */
        MyUpload.prototype.expectParam = function () {
            // 参数必须为对象
            if (Object.prototype.toString.call(this.options) !== '[object Object]') {
                //翻译
                var fyInfo5=fy.getMsg("参数必须是对象","createUpload()");
                if(typeof(fyInfo5)=="undefined" || fyInfo5=='' || fyInfo5==null){
                    fyInfo5="createUpload()参数必须是对";
                }
                errHint(fyInfo5, this.lay);
                return false;
            }
            // 根据页面加载按钮样式
            if (this.options.buttonStyle === 2 || this.options.buttonStyle === 3) {
                loadingStyle23();
            }
            else if (this.options.buttonStyle === 1 || typeof this.options.buttonStyle === 'undefined') {
                loadingStyle1();
            }
            if (!(this.options.server) || typeof this.options.server !== 'string') {
                //翻译
                var fyInfo6=fy.getMsg("参数为空或类型错误","server","string");
                if(typeof(fyInfo6)=="undefined" || fyInfo6=='' || fyInfo6==null){
                    fyInfo6="server为空或不是为string";
                }
                errHint(fyInfo6, this.lay);
                return false;
            }
            if (!(this.options.container) || typeof this.options.container !== 'string') {
                //翻译
                var fyInfo7=fy.getMsg("参数为空或类型错误","container","string");
                if(typeof(fyInfo7)=="undefined" || fyInfo7=='' || fyInfo7==null){
                    fyInfo7="container为空或不是为string类型";
                }
                errHint(fyInfo7, this.lay);
                return false;
            }
            if (typeof $(this.options.container)[0] === 'undefined') {
                //翻译
                var fyInfo8=fy.getMsg("上传容器不存在");
                if(typeof(fyInfo8)=="undefined" || fyInfo8=='' || fyInfo8==null){
                    fyInfo8="上传容器不存在";
                }
                errHint(fyInfo8 + this.options.container, this.lay);
                return false;
            }
            // 创建上传的DOM结构
            this.establishInitDOM();
            // 初始WebUploader
            this.createUpload();
        };
        ;
        // 创建上传按钮 容器的DOM结构， 初始参数之前
        /**
         * @function 需要修改，参数不需要传：可以通过this拿
         * @param options
         */
        MyUpload.prototype.establishInitDOM = function () {
            var styleDOM, $div, container = this.options.container;
            if (typeof container !== 'string') {
                //翻译
                var fyInfo9=fy.getMsg("参数为空或类型错误","containerId","string");
                if(typeof(fyInfo9)=="undefined" || fyInfo9=='' || fyInfo9==null){
                    fyInfo9="containerId不是string类型";
                }
                errHint(fyInfo9, this.lay);
                return false;
            }
            //翻译
            var fyInfo10=fy.getMsg("选择上传图片");
            if(typeof(fyInfo10)=="undefined" || fyInfo10=='' || fyInfo10==null){
                fyInfo10="选择上传图片";
            }
            if (typeof this.options.buttonStyle === 'number' || typeof this.options.buttonStyle === 'undefined') {
                if (this.options.buttonStyle === 1 || typeof this.options.buttonStyle === 'undefined') {
                    styleDOM = '<i><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/add.png"></i>';
                    $div = $('<div id="fileList' + container.slice(1) + '" class="fileList-1 uploader-list-1 clear gallery"></div><div id="filePicker' + this.options.container.slice(1) + '" class="filePicker-1">' +
                        styleDOM + '</div>');
                    $(container).append($div).addClass('uploader-container');
                }
                else if (this.options.buttonStyle === 2) {
                    styleDOM = fyInfo10;
                    $div = $('<div id="fileList' + container.slice(1) + '" class="fileList uploader-list clear gallery"></div><div class="filePicker" id="filePicker' + this.options.container.slice(1) + '">' +
                        styleDOM + '</div>');
                    $(container).append($div);
                }
                else if (this.options.buttonStyle === 3) {
                    styleDOM = fyInfo10;
                    $div = $('<div class="filePicker" id="filePicker' + container.slice(1) + '">' +
                        styleDOM + '</div><div id="fileList' + container.slice(1) + '" class="fileList uploader-list clear gallery"></div>');
                    $(container).append($div);
                }
            }
            else {
                //翻译
                var fyInfo11=fy.getMsg("参数为空或类型错误","buttonStyle","number");
                if(typeof(fyInfo11)=="undefined" || fyInfo11=='' || fyInfo11==null){
                    fyInfo11="buttonStyle不是number类型";
                }
                errHint(fyInfo11, this.lay);
                return false;
            }
        };
        ;
        /**
         * @function 超出上传数量限制隐藏上传按钮，参数需要修改：可以通过this拿
         * @param options
         * @param shapeOptions
         */
        MyUpload.prototype.removerFileMessage = function (options, shapeOptions) {
            var uploaderNum = $(options.container).find('.file-item.thumbnail').length;
            if (options.buttonStyle === 1 || typeof options.buttonStyle === 'undefined') {
                if (uploaderNum >= options.fileNumLimit) {
                    $(shapeOptions.pick).hide();
                }
                if (uploaderNum < options.fileNumLimit) {
                    $(shapeOptions.pick).show();
                }
            }
        };
        ;
        /**
         * @function 上传错误信息提示，参数优化
         * @param { String } type  上传错误字段
         * @param options
         */
        MyUpload.prototype.errorType = function (type, options) {
            var accept = options.accept[0].extensions, uploaderNum = $(options.dnd + ' .file-item.thumbnail').length;
            // fileNumLimit = options.fileNumLimit;
            if (type == "Q_TYPE_DENIED") {
                //翻译
                var fyInfo12=fy.getMsg("请上传正确格式文件",accept);
                if(typeof(fyInfo12)=="undefined" || fyInfo12=='' || fyInfo12==null){
                    fyInfo12="请上传" + accept + "格式文件";
                }
                errHint(fyInfo12, this.lay);
            }
            else if (type == "Q_EXCEED_SIZE_LIMIT") {
                //翻译
                var fyInfo13=fy.getMsg("文件总大小不能超过");
                if(typeof(fyInfo13)=="undefined" || fyInfo13=='' || fyInfo13==null){
                    fyInfo13="文件总大小不能超过";
                }
                errHint(fyInfo13 + options.fileSingleSizeLimit / 1024 / 1024 * options.fileNumLimit + 'MB', this.lay);
            }
            else if (type == 'Q_EXCEED_NUM_LIMIT') {
                //翻译
                var fyInfo14=fy.getMsg("文件超出最大个数",uploaderNum);
                if(typeof(fyInfo14)=="undefined" || fyInfo14=='' || fyInfo14==null){
                    fyInfo14="文件超出" + uploaderNum + "个, 自动选取位置靠前的文件";
                }
                errHint(fyInfo14, this.lay);
            }
            else if (type == 'F_EXCEED_SIZE') {
                //翻译
                var fyInfo15=fy.getMsg("文件大小不能超过");
                if(typeof(fyInfo15)=="undefined" || fyInfo15=='' || fyInfo15==null){
                    fyInfo15="文件大小不能超过";
                }
                errHint(fyInfo15 + options.fileSingleSizeLimit / 1024 / 1024 + 'MB', this.lay);
            }
            else if (type == 'F_DUPLICATE') {
                //翻译
                var fyInfo16=fy.getMsg("图片重复");
                if(typeof(fyInfo16)=="undefined" || fyInfo16=='' || fyInfo16==null){
                    fyInfo16="重复的图片、请重新上传";
                }
                errHint(fyInfo16, this.lay);
            }
            else {
                //翻译
                var fyInfo17=fy.getMsg("上传出错");
                if(typeof(fyInfo17)=="undefined" || fyInfo17=='' || fyInfo17==null){
                    fyInfo17="上传出错，请重新上传";
                }
                errHint(fyInfo17, this.lay);
            }
        };

        /**
         * @function 验证formData为对象,并返回值
         * @return { Object }
         */
        MyUpload.prototype.verifyFormData = function () {
            if (Object.prototype.toString.call(this.options.formData) === '[object Object]') {
                if (typeof this.options.isSafe === 'boolean') {
                    this.options.formData.isSafe = this.options.isSafe;
                }
                return this.options.formData;
            }
            else {
                return {};
            }
        };
        ;
        /**
         * @function 验证上传类型,参数优化
         * @param { Object || String } accept
         */

        //翻译
        var fyInfo18=fy.getMsg("上传文件");
        if(typeof(fyInfo18)=="undefined" || fyInfo18=='' || fyInfo18==null){
            fyInfo18="上传文件";
        }
        //翻译
        var fyInfo19=fy.getMsg("自定义");
        if(typeof(fyInfo19)=="undefined" || fyInfo19=='' || fyInfo19==null){
            fyInfo19="自定义";
        }
        //翻译
        var fyInfo20=fy.getMsg("参数错误","accept");
        if(typeof(fyInfo20)=="undefined" || fyInfo20=='' || fyInfo20==null){
            fyInfo20="accept参数有误";
        }
        MyUpload.prototype.verifyType = function (accept) {
            if (accept === 'images') {
                return {
                    title: 'Images',
                    extensions: 'jpg,jpeg,png,bmp',
                };
            }
            else if (accept === 'file') {
                return {
                    title: fyInfo18,
                    extensions: 'jpg,jpeg,bmp,png,pdf,ppt,pptx,xls,xlsx,doc,docx,zip,rar',
                };
            }
            else if (typeof accept === 'object') {
                if (accept.type === 'custom' && typeof accept.extensions === 'string') {
                    return {
                        title: fyInfo19,
                        extensions: accept.extensions,
                    };
                }
                else {
                    errHint(fyInfo20, this.lay);
                    return false;
                }
            }
            else {
                return {
                    title: 'Images',
                    extensions: 'jpg,jpeg,png,bmp',
                };
            }
        };
        ;
        /**
         * @function 初始化webuploader上传参数
         * @param { Object } params
         * @returns { Object }
         */
        MyUpload.prototype.initParams = function () {
            var settings = {
                // 自动上传
                // auto: typeof this.options.auto !== 'undefined' ? this.options.auto : true,
                auto: true,
                // 拖拽容器
                dnd: verifyDnd(this.options),
                // 粘贴容器
                paste: this.options.container,
                // 强制使用flash上传
                // runtimeOrder: 'flash',
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#filePicker' + this.options.container.slice(1),
                // 禁掉页面打开图片的功能
                disableGlobalDnd: true,
                // swf文件路径
                swf: this.swf,
                // 文件接收服务端
                server: this.options.server,
                // 只允许选择图片文件。
                accept: this.verifyType(this.options.accept),
                // method: 'GET',
                // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
                formData: this.verifyFormData(),
                // 上传个数 没依赖WebUploader来限制上传数量
                fileNumLimit: 99,
                //上传文件单个大小单位（B）
                fileSingleSizeLimit: this.options.fileSingleSizeLimit || 1 * 1024 * 1024 * 2,
                // 上传文件总大小单位（B）
                // fileSizeLimit: this.options.fileSingleSizeLimit || 1 * 1024 * 1024 * 2
                // 扩展的参数
                // 允许重复的文件上传
                duplicate: verifyDuplicate(this.options.duplicate),
                // 压缩图片
                compress: verifyCompress(this.options.compress),
                // 是否允许在文件传输时提前把下一个文件准备好
                prepareNextFile: this.options.prepareNextFile || '',
            };
            return settings;
        };

        /**
         * @function 重置上传队列
         */
        MyUpload.prototype.reset = function () {
            var _this = this;
            // 遍历datas, 
            $.each(this.datas, function (i, n) {
                // 从上传队列中删除，
                _this.shape.removeFile(n.WUfile, true);
            });
            // 删除datas该项的数据
            this.datas = [];
            // 删除DOM结构
            $(this.options.container).find('.file-item').remove();
            // 是否显示上传按钮
            this.removerFileMessage(this.options, this.shape.options);
            // 计数器-1
            this.count = 0;
        };
        /**
         * @function 创建上传,包含webuploader的事件
         * @param { Object } options
         */
        MyUpload.prototype.createUpload = function () {
            var _this = this;
            _this.shape = _this.options.container.slice(1);
            if (!WebUploader.Uploader.support()) {
                //翻译
                var fyInfo21=fy.getMsg("浏览器不支持");
                if(typeof(fyInfo21)=="undefined" || fyInfo21=='' || fyInfo21==null){
                    fyInfo21="Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试安装或升级 Adobe Flash Player";
                }
                alert(fyInfo21);
                throw new Error('WebUploader does not support the browser you are using.');
            }
            // 是图片类型上传才使用预览功能
            if (_this.options.accept === 'images' || typeof _this.options.accept === 'undefined') {
                var selector_1 = $('#fileList' + _this.options.container.slice(1) + '.gallery');
                if (selector_1.magnificPopup) {
                    // 调用jquery插件，查看原图
                    selector_1.magnificPopup({
                        delegate: 'a',
                        type: 'image',
                        gallery: {
                            enabled: true
                        }
                    });
                }
                else {
                    ev.on('script', function () {
                        selector_1.magnificPopup({
                            delegate: 'a',
                            type: 'image',
                            gallery: {
                                enabled: true
                            }
                        });
                    });
                }
            }
            // 创建上传
            _this.shape = new WebUploader.Uploader(_this.initParams());
            // 加入队列之前
            _this.shape.on('beforeFileQueued', function (file) {
                // 上传前获取文件流
                if (typeof _this.beforeFile === 'function') {
                    _this.beforeFile(file.source.getSource());
                }
            });
            // 当文件被加入队列时触发
            _this.shape.on('fileQueued', function (file) {
                // 加入队列前 判断可以上传的数量
                if (_this.uploadCount <= _this.count) {
                    _this.errorType('Q_EXCEED_NUM_LIMIT', _this.shape.options);
                    return false;
                }
                _this.count++;
                var $li = $('<div id="' + file.id + '" class="file-item thumbnail"><a><img></a><div class="remove-this"></div></div>'), $img = $li.find('img');
                // $list为容器jQuery实例
                $('#fileList' + _this.options.container.slice(1)).append($li);
                // 创建缩略图
                // 如果为非图片文件，可以不用调用此方法。
                //翻译
                var fyInfo22=fy.getMsg("不能预览");
                if(typeof(fyInfo22)=="undefined" || fyInfo22=='' || fyInfo22==null){
                    fyInfo22="不能预览";
                }
                //翻译
                var fyInfo23=fy.getMsg("上传的文件不支持预览");
                if(typeof(fyInfo23)=="undefined" || fyInfo23=='' || fyInfo23==null){
                    fyInfo23="上传的文件不支持预览";
                }
                _this.shape.makeThumb(file, function (error, src) {
                    if (error) {
                        $li.find('a').attr('href', './images/no-preview.png');
                        // 根据文件类型显示缩略图
                        if (file.ext === 'pdf' || file.ext === 'ppt' || file.ext === 'pptx') {
                            $img.replaceWith('<span class="preview"></span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/pdf.png" class="preview-img" alt='+fyInfo22+'>');
                        }
                        else if (file.ext === 'xls' || file.ext === 'xlsx') {
                            $img.replaceWith('<span class="preview"></span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/xlsx.png" class="preview-img" alt='+fyInfo22+'>');
                        }
                        else if (file.ext === 'doc' || file.ext === 'docx') {
                            $img.replaceWith('<span class="preview"></span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/doc.png" class="preview-img" alt='+fyInfo22+'>');
                        }
                        else if (file.ext === 'zip') {
                            $img.replaceWith('<span class="preview"></span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/zip.png" class="preview-img" alt='+fyInfo22+'>');
                        }
                        else if (file.ext === 'rar') {
                            $img.replaceWith('<span class="preview"></span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/rar.png" class="preview-img" alt='+fyInfo22+'>');
                        }
                        else {
                            $img.replaceWith('<span class="preview">'+fyInfo23+'</span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/no-preview.png" alt='+fyInfo22+'>');
                        }
                        return;
                    }
                    $img.attr('src', src);
                }, _this.thumbnailWidth, _this.thumbnailHeight);
                $li.find('.remove-this').append('<i class="icon-my"><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/del.png" style="width: 18px; height: 18px; margin-top: 6px;"></i>');
                _this.removerFileMessage(_this.options, _this.shape.options);
                // 绑定删除事件
                $('#fileList' + _this.options.container.slice(1)).on('click', '.remove-this', function () {
                    if ($(this).parent().attr('id') == file.id) {
                        var result = 0;
                        // 遍历datas, 
                        $.each(_this.datas, function (i, n) {
                            if (!!n.WUfile && n.WUfile.id === file.id) {
                                result = i;
                                // 通知使用者删除了哪项
                                if (typeof _this.uploadDelete === 'function') {
                                    _this.uploadDelete(n);
                                }
                            }
                        });
                        // 从上传队列中删除，
                        _this.shape.removeFile(file, true);
                        // 删除datas该项的数据
                        _this.datas.splice(result, 1);
                        // 删除DOM结构
                        $(this).parent().remove();
                        // 是否显示上传按钮
                        _this.removerFileMessage(_this.options, _this.shape.options);
                        // 计数器-1
                        _this.count = _this.count - 1;
                    }
                });
                // 是否上传
                if (_this.options.auto) {
                    return false;
                }
                if (_this.options.tokenPath) {
                    // 把ajax请求设置为同步
                    $.ajaxSettings.async = false;
                    // 获取token
                    $.getJSON(_this.options.tokenPath + '?' + new Date().getTime() + Math.random(), function (data) {
                        file.token = data.token;
                    });
                    $.ajaxSettings.async = true;
                }
            });
            // 文件上传过程中创建进度条实时显示。
            _this.shape.on('uploadProgress', function (file, percentage) {
                // 是否上传
                if (_this.options.auto) {
                    return false;
                }
                var $lis = $('#' + file.id), $percent = $lis.find('.progress span');
                // 避免重复创建
                if (!$percent.length) {
                    $percent = $('<p class="progress"><span></span></p>').appendTo($lis).find('span');
                }
                $percent.css('width', percentage * 100 + '%');
            });
            // 当某个文件的分块在发送前触发	block文件对象，data上传附带的参数
            _this.shape.on('uploadBeforeSend', function (block, data) {
                // 是否上传
                if (_this.options.auto) {
                    return false;
                }
                // file的token赋值给data
                data.token = block.file.token;
                // 上传的安全图参数赋值给file 
                block.file.isSafe = data.isSafe || '';
            });
            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            _this.shape.on('uploadSuccess', function (file, response) {
                // 是否上传
                if (_this.options.auto) {
                    return false;
                }
                var $li = $('#' + file.id), $success = $li.find('div.success'), $a = $li.find('a');
                // 避免重复创建
                if (!$success.length) {
                    $success = $('<div class="success"></div>').appendTo($li);
                    var accessKey;
                    // 图片为安全图
                    if (file.isSafe) {
                        // url拼接accessKey
                        if (typeof _this.options.accessKey === 'string') {
                            accessKey = _this.options.accessKey;
                        }
                        else {
                            //翻译
                            var fyInfo24=fy.getMsg("accessKey错误");
                            if(typeof(fyInfo24)=="undefined" || fyInfo24=='' || fyInfo24==null){
                                fyInfo24="accessKey错误";
                            }
                            errHint(fyInfo24, _this.lay);
                            return false;
                        }
                        // 上传类型为图片才能预览
                        if (_this.options.accept === 'images' || typeof _this.options.accept === 'undefined') {
                            $a.attr('href', response.url + '?accessKey=' + accessKey);
                        }
                        else {
                            $a.attr('href', 'javascript:;');
                        }
                    }
                    else {
                        // 上传类型为图片才能预览
                        if (_this.options.accept === 'images' || typeof _this.options.accept === 'undefined') {
                            $a.attr('href', response.url);
                        }
                        else {
                            $a.attr('href', 'javascript:;');
                        }
                    }
                }
                if (response.statusText == '上传成功') {
                    //翻译
                    var fyInfo25=fy.getMsg("上传成功");
                    if(typeof(fyInfo25)=="undefined" || fyInfo25=='' || fyInfo25==null){
                        fyInfo25="上传成功";
                    }
                    $success.text(fyInfo25);
                    response.WUfile = file;
                    _this.datas.push(response);
                }else {
                    //翻译
                    var fyInfo26=fy.getMsg("上传失败");
                    if(typeof(fyInfo26)=="undefined" || fyInfo26=='' || fyInfo26==null){
                        fyInfo26="上传失败";
                    }
                    //翻译
                    var fyInfo27=fy.getMsg("上传文件格式错误");
                    if(typeof(fyInfo27)=="undefined" || fyInfo27=='' || fyInfo27==null){
                        fyInfo27="上传失败，请检查文件格式，再上";
                    }
                    $success.text(fyInfo26).css({ 'color': 'red', });
                    $a.find('span').html(fyInfo27).css({ 'color': 'red', 'font-size': '12px' });
                }
                // 上传成功触发
                if (typeof _this.uploadSuccess === 'function') {
                    _this.uploadSuccess(file, response);
                }
            });
            // 文件上传失败，显示上传出错。
            _this.shape.on('uploadError', function (file, error) {
                var $li = $('#' + file.id), $error = $li.find('p.error');
                // 避免重复创建
                if (!$error.length) {
                    $error = $('<div class="error"></div>').appendTo($li);
                }
                //翻译
                var fyInfo28=fy.getMsg("上传出错");
                if(typeof(fyInfo28)=="undefined" || fyInfo28=='' || fyInfo28==null){
                    fyInfo28="上传出错";
                }
                $error.text(fyInfo28);
                // 上传失败触发
                if (typeof _this.uploadError === 'function') {
                    _this.uploadError(file, error);
                }
            });
            // 完成上传完了，成功或者失败，先删除进度条。
            _this.shape.on('uploadComplete', function (file) {
                $('#' + file.id).find('.progress').remove();
                // 上传完触发
                if (typeof _this.uploadComplete === 'function') {
                    _this.uploadComplete(file);
                }
            });
            // 监听错误信息
            _this.shape.on("error", function (type) {
                _this.errorType(type, _this.shape.options);
            });
            // 刷新WebUploader
            _this.shape.refresh();
        };
        ;
        return MyUpload;
    }());
    return MyUpload;
});
