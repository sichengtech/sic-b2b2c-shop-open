(function (window) {

	// 只执行一次
	function once(fn, context) {
		var result;
		return function () {
			if (fn) {
				result = fn.apply(context || this, arguments);
				fn = null;
			}

			return result;
		};
	};
	var canOnlyFireOnce = once(function () {
		// 预览原图
		$('head').append('<link rel="stylesheet" href="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/jquery-magnific-popup/magnific-popup.css">');
		$('body').append('<script type="text/javascript" src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/jquery-magnific-popup/jquery.magnific-popup.min.js"></script>');

	});

	function MyUpload(options) {
		// flash路径 
		this.swf = window.MYUPLOAD_PREFIX_URL + 'MyUpload/webuploader/Uploader.swf';

		// 提交文件的所有数据
		this.datas = [];

		// 计算回显后还能上传多少个文件
		this.uploadCount = this.verifyNum(options.fileNumLimit);

		// 允许上传数量
		this.fileNumLimit = this.verifyNum(options.fileNumLimit);

		// 上传数量计数
		this.count = 0;

		// 优化retina, 在retina下这个值是2
		this.ratio = window.devicePixelRatio || 1;
		// 缩略图大小
		this.thumbnailWidth = 100 * this.ratio;
		this.thumbnailHeight = 100 * this.ratio;

		// layer弹层变量
		this.lay = window.layer || "";

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

		// 只执行一次
		canOnlyFireOnce();

		// 判断是否有必填参数
		this.expectParam();

	};

	function defaultValue(value) {
		var val = '';
		if (value || typeof value === 'undefined') {
			val = true;
		} else {
			val = false;
		}
		return val;
	}

	// 回显函数
	MyUpload.prototype.init = function (params) {
		var _this = this,
			data = params,
			reg = /\/\d{2}\//;

		// 判断传递进来的参数是否是数组
		if (Object.prototype.toString.call(data) === '[object Array]') {

			// 遍历参数 并创建DOM结构
			$.each(data, function (i, n) {
				if(!n) {
					
					return false;
				}
				var $li = '';
					if(_this.againUpload) {
						$li = $(
							'<div class="file-item thumbnail eached"><a><img class="echoed"></a><div class="remove-this"><i class="icon-my"><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/del.png" style="width: 18px; height: 18px; margin-top: 6px;"></i></div><div class="load"></div></div>'
						);
					} else {
						$li = $(
							'<div class="file-item thumbnail eached"><a><img class="echoed"></a><div class="load"></div></div>'
						);
						// $(_this.shape.options.pick).css({'display':'none'});
					}
				var $img = $li.find('a img'),
					$a = $li.find('a'),
					$load = $li.find('div.load');

				$load.text('历史图片加载中');

				// 根据isSafe拼接url
				var accessKey;
				if (typeof _this.options.formData !== 'undefined' && _this.options.formData.isSafe) {


					if (typeof _this.options.accessKey === 'string') {
						accessKey = _this.options.accessKey;
					} else {
						_this.errHint('accessKey错误');
					}

					$a.attr('href', n + '?accessKey=' + accessKey);
					$img.attr('src', n + '@!100X100?accessKey=' + accessKey);

				} else {

					$img.attr('src', n + '@!100X100');
					$a.attr('href', n);
				}

				$(_this.options.container + ' .gallery').append($li);

				// 监听图片加载完成
				$img.each(function (i, e) {
					// var imgsrc = $(e).attr("src");
					$(e).load(function () {
						$load.text('图片加载完成');
						setTimeout(function () {
							$load.text('历史图片');
						}, 600);

					});

				});

				_this.datas.push({ path: n.split('filestorage')[1] });
			});

			// 回显后能上传的个数 = 允许上传的数量-回显的数量 
			_this.uploadCount = _this.fileNumLimit - data.length;
			_this.removerFileMessage(_this.options, _this.shape.options);

			// 绑定删除事件
			$(_this.options.container + ' .eached .remove-this').click(function () {
				var imgUrl = $($(this).parent().find('a img')).attr('src').split('@')[0],
					deleteUrl = imgUrl.split('filestorage')[1],
					dataIndex;

				$.each(_this.datas, function (i, n) {
					if (n.path === deleteUrl) {
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
				_this.uploadCount = _this.fileNumLimit - data.length;

			});

		} else {

			_this.errHint('init参数错误');
			return false;
		}
	};

	// 判断传递进来的参数是对象, 参数是否有值初始参数之前
	MyUpload.prototype.expectParam = function () {

		// 参数必须为对象
		if (Object.prototype.toString.call(this.options) !== '[object Object]') {
			this.errHint('createUpload()参数必须是对象');
			return false;
		}

		// 根据页面加载按钮样式
		if (this.options.buttonStyle === 2 || this.options.buttonStyle === 3) {
			$('head').prepend('<link rel="stylesheet" href="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/css/button.css">');
		} else if (this.options.buttonStyle === 1 || typeof this.options.buttonStyle === 'undefined') {
			$('head').prepend('<link rel="stylesheet" href="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/css/imgButton.css">');
		}

		// if (!(this.options.tokenPath) || typeof this.options.tokenPath !== 'string') {
		// 	this.errHint('tokenPath为空或不是为string');
		// 	return false;
		// }
		if (!(this.options.server) || typeof this.options.server !== 'string') {
			this.errHint('server为空或不是为string');
			return false;
		}
		if (!(this.options.container) || typeof this.options.container !== 'string') {
			this.errHint('container为空或不是为string类型');
			return false;
		}
		if (typeof $(this.options.container)[0] === 'undefined') {
			this.errHint('上传容器不存在' + this.options.container);
			return false;
		}

		// 创建上传的DOM结构
		this.establishInitDOM(this.options);

		// 初始WebUploader
		this.createUpload(this.options);
	};

	// 创建上传按钮 容器的DOM结构， 初始参数之前
	MyUpload.prototype.establishInitDOM = function (options) {
		var styleDOM, $div,
			container = options.container;
		if (typeof container !== 'string') {
			this.errHint('containerId不是string类型');
			return false;
		}

		if (typeof options.buttonStyle === 'number' || typeof options.buttonStyle === 'undefined') {

			if (options.buttonStyle === 1 || typeof options.buttonStyle === 'undefined') {

				styleDOM = '<i><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/add.png"></i>';

				$div = $('<div id="fileList' + options.container.slice(1) + '" class="fileList-1 uploader-list-1 clear gallery"></div><div id="filePicker' + options.container.slice(1) + '" class="filePicker-1">' +
					styleDOM + '</div>');

				$(container).append($div).addClass('uploader-container');

			} else if (options.buttonStyle === 2) {

				styleDOM = '选择上传图片';

				$div = $('<div id="fileList' + options.container.slice(1) + '" class="fileList uploader-list clear gallery"></div><div class="filePicker" id="filePicker' + options.container.slice(1) + '">' +
					styleDOM + '</div>');

				$(container).append($div);

			} else if (options.buttonStyle === 3) {

				styleDOM = '选择上传图片';

				$div = $('<div class="filePicker" id="filePicker' + options.container.slice(1) + '">' +
					styleDOM + '</div><div id="fileList' + options.container.slice(1) + '" class="fileList uploader-list clear gallery"></div>');

				$(container).append($div);
			}

		} else {

			this.errHint('buttonStyle不是number类型');
			return false;
		}
	};

	// 超出上传数量限制隐藏上传按钮
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

	// 上传错误信息提示
	MyUpload.prototype.errorType = function (type, options) {
		var accept = options.accept[0].extensions,
			uploaderNum = $(options.dnd + ' .file-item.thumbnail').length;
		// console.log(uploaderNum)
		// fileNumLimit = options.fileNumLimit;

		if (type == "Q_TYPE_DENIED") {
			this.errHint("请上传" + accept + "格式文件");
		} else if (type == "Q_EXCEED_SIZE_LIMIT") {
			this.errHint("文件总大小不能超过" + options.fileSingleSizeLimit / 1024 / 1024 * options.fileNumLimit + 'MB');
		} else if (type == 'Q_EXCEED_NUM_LIMIT') {
			this.errHint("文件超出" + uploaderNum + "个, 自动选取位置靠前的文件");
		} else if (type == 'F_EXCEED_SIZE') {
			this.errHint("文件大小不能超过" + options.fileSingleSizeLimit / 1024 / 1024 + 'MB');
		} else if (type == 'F_DUPLICATE') {
			this.errHint("重复的图片、请重新上传");
		} else {
			this.errHint("上传出错，请重新上传");
		}
	};

	// 错误提示信息
	MyUpload.prototype.errHint = function (message) {
		if (this.lay) {
			this.lay.msg(message);
		} else {
			alert(message);
		}
	};

	// 验证上传类型 
	MyUpload.prototype.verifyType = function (accept) {
		if (accept === 'images') {
			return {
				title: 'Images',
				extensions: 'jpg,jpeg,png,bmp',
			};
		} else if (accept === 'file') {
			return {
				title: '上传文件',
				extensions: 'jpg,jpeg,bmp,png,pdf,ppt,pptx,xls,xlsx,doc,docx,zip,rar',
			};
		} else if (typeof accept === 'object') {

			if (accept.type === 'custom' && typeof accept.extensions === 'string') {
				return {
					title: '自定义',
					extensions: accept.extensions,
				};
			} else {
				this.errHint('accept参数有误');
				return false;
			}

		} else {
			return {
				title: 'Images',
				extensions: 'jpg,jpeg,png,bmp',
			};
		}
	};

	// 验证formData为对象, 并返回值
	MyUpload.prototype.verifyFormData = function (params) {
		if (Object.prototype.toString.call(params.formData) === '[object Object]') {
			if (typeof params.isSafe === 'boolean') {
				params.formData.isSafe = params.isSafe;
				return params.formData;
			}
			return params.formData;
		} else {
			params.formData = {};
			if (typeof params.isSafe === 'boolean') {
				params.formData.isSafe = params.isSafe;
				return params.formData;
			}
			return params.formData;
		}
	};
	// 验证compress为对象, 并返回值
	MyUpload.prototype.verifyCompress = function (params) {
		if (Object.prototype.toString.call(params) === '[object Object]') {
			return params;
		} else {
			return '';
		}
	};

	// 验证上传个数为number类型, 并返回值
	MyUpload.prototype.verifyNum = function (fileNumLimit) {
		if (typeof fileNumLimit === 'number') {
			return fileNumLimit;
		} else {
			return 10;
		}
	};

	// 根据参数添加fileListid
	MyUpload.prototype.verifyDnd = function (params) {
		if (typeof params.buttonStyle === 'number' && params.buttonStyle === 1 || typeof params.buttonStyle === 'undefined') {
			return params.container;
		} else {
			return '#fileList' + params.container.slice(1);
		}
	};

	// 验证duplicate为对象, 并返回值
	MyUpload.prototype.verifyDuplicate = function (params) {
		if (Object.prototype.toString.call(params) === '[object Boolean]') {
			return params;
		} else {
			return true;
		}
	};

	// 初始上传参数
	MyUpload.prototype.initParams = function (params) {
		var settings = {
			// 自动上传
			auto: true,

			// 拖拽容器
			dnd: this.verifyDnd(params),

			// 粘贴容器
			paste: params.container,

			// 强制使用flash上传
			// runtimeOrder: 'flash',

			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick: '#filePicker' + params.container.slice(1),

			// 禁掉页面打开图片的功能
			disableGlobalDnd: true,

			// swf文件路径
			swf: this.swf,

			// 文件接收服务端
			server: params.server,

			// 只允许选择图片文件。
			accept: this.verifyType(params.accept),
			// method: 'GET',

			// 文件上传请求的参数表，每次发送都会发送此对象中的参数。
			formData: this.verifyFormData(params),

			// 上传个数 没依赖WebUploader来限制上传数量
			fileNumLimit: 99,

			//上传文件单个大小单位（B）
			fileSingleSizeLimit: params.fileSingleSizeLimit || 1 * 1024 * 1024 * 2,

			// 上传文件总大小单位（B）
			// fileSizeLimit: params.fileSingleSizeLimit || 1 * 1024 * 1024 * 2

			// 扩展的参数
			// 允许重复的文件上传
			duplicate: this.verifyDuplicate(params.duplicate),

			// 压缩图片
			compress: this.verifyCompress(params.compress),

			// 是否允许在文件传输时提前把下一个文件准备好
			prepareNextFile: params.prepareNextFile || '',
		};

		return settings;
	};

	// 重置队列
	MyUpload.prototype.reset = function() {
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
	}

	// 创建上传功能，uploader方法都包含在内
	MyUpload.prototype.createUpload = function (options) {
		var _this = this;
		_this.shape = options.container.slice(1);

		if (!WebUploader.Uploader.support()) {
			alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试安装或升级 Adobe Flash Player');
			throw new Error('WebUploader does not support the browser you are using.');
		}

		// 调用jquery插件，查看原图
		$('.gallery').magnificPopup({
			delegate: 'a',
			type: 'image',
			gallery: {
				enabled: true
			}
		});

		_this.shape = new WebUploader.Uploader(_this.initParams(options));

		// 加入队列之前
		_this.shape.on('beforeFileQueued', function(file) {
			// 上传前获取文件流
			if (typeof _this.beforeFile === 'function') {
				_this.beforeFile(file.source.getSource());
			}

		})

		// 当文件被加入队列是触发
		_this.shape.on('fileQueued', function (file) {

			// 加入队列前 判断可以上传的数量
			if (_this.uploadCount <= _this.count) {
				_this.errorType('Q_EXCEED_NUM_LIMIT', _this.shape.options);
				return false;
			}

			_this.count++;

			var $li = $(
				'<div id="' + file.id + '" class="file-item thumbnail"><a><img></a><div class="remove-this"></div></div>'
			),
				$img = $li.find('img');

			// $list为容器jQuery实例
			$('#fileList' + options.container.slice(1)).append($li);

			// 创建缩略图
			// 如果为非图片文件，可以不用调用此方法。
			_this.shape.makeThumb(file, function (error, src) {
				if (error) {
					$li.find('a').attr('href', './images/no-preview.png');
					$img.replaceWith('<span class="preview">上传的文件不支持预览。</span><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/no-preview.png" alt="不能预览">');
					return;
				}

				$img.attr('src', src);

			}, _this.thumbnailWidth, _this.thumbnailHeight);

			$li.find('.remove-this').append('<i class="icon-my"><img src="' + window.MYUPLOAD_PREFIX_URL + 'MyUpload/images/del.png" style="width: 18px; height: 18px; margin-top: 6px;"></i>');
			_this.removerFileMessage(options, _this.shape.options);

			// 绑定删除事件
			$('#fileList' + options.container.slice(1)).on('click', '.remove-this', function () {

				if ($(this).parent().attr('id') == file.id) {
					var result;

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
					_this.removerFileMessage(options, _this.shape.options);

					// 计数器-1
					_this.count = _this.count - 1;
				}
			});

			// 是否上传
			if(_this.options.auto) {
				return false;
			}
			console.log(123)

			// 把ajax请求设置为同步
			// $.ajaxSettings.async = false;

			// // 获取token
			// $.getJSON(options.tokenPath + '?' + Date.parse(new Date()) + Math.random(), function (data) {
			// 	file.token = data.token;
			// });

			// $.ajaxSettings.async = true;
		});

		// 文件上传过程中创建进度条实时显示。
		_this.shape.on('uploadProgress', function (file, percentage) {
			// 是否上传
			if(_this.options.auto) {
				return false;
			}
			var $lis = $('#' + file.id),
				$percent = $lis.find('.progress span');

			// 避免重复创建
			if (!$percent.length) {
				$percent = $('<p class="progress"><span></span></p>').appendTo($lis).find('span');
			}

			$percent.css('width', percentage * 100 + '%');
		});

		// 当某个文件的分块在发送前触发	block文件对象，data上传附带的参数
		_this.shape.on('uploadBeforeSend', function (block, data) {
			// 是否上传
			if(_this.options.auto) {
				return false;
			}
			// file的token赋值给data
			// data.token = block.file.token;

			// 上传的安全图参数赋值给file 
			block.file.isSafe = data.isSafe || '';

		});

		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		_this.shape.on('uploadSuccess', function (file, response) {
			// 是否上传
			if(_this.options.auto) {
				return false;
			}
			var $li = $('#' + file.id),
				$success = $li.find('div.success'),
				$a = $li.find('a');

			// 避免重复创建
			if (!$success.length) {
				$success = $('<div class="success"></div>').appendTo($li);

				var accessKey;

				// 图片为安全图
				if (file.isSafe) {

					// url拼接accessKey
					if (typeof options.accessKey === 'string') {
						accessKey = options.accessKey;
					} else {
						_this.errHint('accessKey错误');
					}

					$a.attr('href', response.url + '?accessKey=' + accessKey);
				} else {

					$a.attr('href', response.url);
				}

			}

			if (response.statusText == '上传成功') {
				$success.text('上传成功');

				response.WUfile = file;

				_this.datas.push(response);
			} else {
				$success.text('上传失败').css({ 'color': 'red', });
				$a.find('span').html('上传失败，请检查文件格式，再上传').css({ 'color': 'red', 'font-size': '12px' });
			}

			// 上传成功触发
			if (typeof _this.uploadSuccess === 'function') {
				_this.uploadSuccess(file, response);
			}
		});

		// 文件上传失败，显示上传出错。
		_this.shape.on('uploadError', function (file, error) {
			var $li = $('#' + file.id),
				$error = $li.find('p.error');

			// 避免重复创建
			if (!$error.length) {
				$error = $('<div class="error"></div>').appendTo($li);
			}
			$error.text('上传出错');

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

	window.MyUpload = MyUpload;

})(window);