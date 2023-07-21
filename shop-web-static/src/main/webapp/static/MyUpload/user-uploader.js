// window.MYUPLOAD_PREFIX_URL = '/aidianqi/webuploader-demo/';
window.MYUPLOAD_PREFIX_URL = '/';
var upload = new MyUpload({
	// 获取token的路径
	// tokenPath: "http://192.168.0.165/seller/sys/sysToken/getToken.htm",

	// 文件上传到的服务器
	// server: 'http://211.159.169.133:3000/upload',
	server: 'http://127.0.0.1:7001/upload',

	// 容器选择器
	container: '#container-a',


	fileSingleSizeLimit: 1 * 1024 * 1024 * 3,

	fileNumLimit: 1,

	accept: 'file',
	// prepareNextFile: true
	againUpload: false

});

var b = new MyUpload({
	// 获取token的路径
	// tokenPath: "http://192.168.0.165/seller/sys/sysToken/getToken.htm",

	// 文件上传到的服务器
	// server: 'http://192.168.0.108:3000/upload',
	server: 'http://127.0.0.1:7001/upload',

	// 容器选择器
	container: '#container-b',

	// accessKey的值, 
	accessKey: 'abc124rfdsasd',

	// // 根据参数动态创建上传组件的css
	buttonStyle: 2,

	// 允许上传的类型
	accept: 'file',	

	// 上传个数
	// fileNumLimit: 5,

	// 上传安全图片
	isSafe: true,

	// 文件上传请求的参数表，每次发送都会发送此对象中的参数。
	formData: {},

	// 单个文件大小
	fileSingleSizeLimit: 1 * 1024 * 1024 * 5,

	// 允许重复的文件上传
	duplicate: false,

	// 压缩图片
	compress: {
		width: 1600,
		height: 1600,

		// 图片质量，只有type为`image/jpeg`的时候才有效。
		quality: 90,

		// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
		allowMagnify: false,

		// 是否允许裁剪。
		crop: false,

		// 是否保留头部meta信息。
		preserveHeaders: true,

		// 如果发现压缩后文件大小比原来还大，则使用原来图片
		// 此属性可能会影响图片自动纠正功能
		noCompressIfLarger: false,

		// 单位字节，如果图片大小小于此值，不会采用压缩。
		compressSize: 0
	},

	// 是否允许在文件传输时提前把下一个文件准备好
	prepareNextFile: false,
	againUpload: true,

	// 上传成功触发
	uploadSuccess: function (file, success) {
		// console.log(file, success);
	},

	// 上传失败触发
	uploadError: function(file, err) {
		// console.log(file, err);
	},

	// 上传完成触发
	uploadComplete: function(file) {
		// console.log(file);
	},

	// 删除触发
	uploadDelete: function(file) {
		console.log(file);
	}

}); 
// upload.init(['http://192.168.0.165:80/upload/filestorage/03/93/04/7f5ae1b517fa40fab1e4952da577f4fe.png'])
// upload.init([
// 	'http://www.runoob.com/wp-content/uploads/2013/12/java.jpg'
// ])
// b.init([
// 	'http://192.168.0.165:80/filestorage/09/89/55/9ed365d6c14d4fd59c6843b0056d16ee.png'
// ]);

