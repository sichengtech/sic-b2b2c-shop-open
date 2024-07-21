/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.upload.fileStorage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sicheng.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 文件存储接口--阿里云OSS实现
 * <p>
 * 阿里云对象存储OSS（Object Storage Service）是阿里云提供的海量、安全、低成本、高持久的云存储服务。
 * OSS具有与平台无关的RESTful API接口，您可以在任何应用、任何时间、任何地点存储和访问任意类型的数据。
 * 您可以使用阿里云提供的API、SDK接口或者OSS迁移工具轻松地将海量数据移入或移出阿里云OSS。
 * <p>
 * <b>OSS相关概念</b>
 * 存储类型（Storage Class）
 * OSS提供标准、低频访问、归档、冷归档四种存储类型，全面覆盖从热到冷的各种数据存储场景。
 * <p>
 * 存储空间（Bucket）
 * 存储空间是用于存储对象（Object）的容器，所有的对象都必须隶属于某个存储空间。存储空间具有各种配置属性，包括地域、访问权限、存储类型等。
 * <p>
 * 对象（Object）
 * 对象是OSS存储数据的基本单元，可以理解为OSS的文件。对象由元信息（Object Meta）、用户数据（Data）和文件名（Key）组成。对象由存储空间内部唯一的Key来标识。
 * <p>
 * 对象元信息是一组键值对，表示了对象的一些属性，例如最后修改时间、大小等信息，同时您也可以在元信息中存储一些自定义的信息。
 * <p>
 * 地域（Region）
 * 地域表示OSS的数据中心所在物理位置。您可以根据费用、请求来源等选择合适的地域创建Bucket。
 * <p>
 * 访问域名（Endpoint）
 * Endpoint表示OSS对外服务的访问域名。OSS以HTTP RESTful API的形式对外提供服务，当访问不同地域的时候，需要不同的域名。通过内网和外网访问同一个地域所需要的域名也是不同的。
 * <p>
 * 访问密钥（AccessKey）
 * AccessKey简称AK，指的是访问身份验证中用到的AccessKey ID和AccessKey Secret。OSS通过使用AccessKey ID和AccessKey Secret对称加密的方法来验证某个请求的发送者身份。AccessKey ID用于标识用户；AccessKey Secret是用户用于加密签名字符串和OSS用来验证签名字符串的密钥，必须保密。
 *
 * @author zhaolei
 */
@SuppressWarnings("Duplicates")
public class FileStorageAliyunOss extends FileStorageAbstract {
    private static final Logger log = LoggerFactory.getLogger(FileStorageAliyunOss.class);

    //spring注入
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;//桶的名称
    private String endpoint;
    private String folder;//隔离文件夹。所有文件存储在些文件夹下，若空则存储在根目录下

    private volatile OSSClient client = null;//客户端类

    private OSSClient createOrGetClient() {
        if (client == null) {
            synchronized (FileStorageAliyunOss.class) {
                if (client == null) {
                    // 初始化一个OSSClient
                    if (endpoint == null || accessKeyId == null || accessKeySecret == null || bucketName == null) {
                        throw new RuntimeException(FileStorageAliyunOss.class.getName() + "创建缺少必要的参数:endpoint,accessKeyId,accessKeySecret,bucketName");
                    }

                    client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return client;
    }

    /**
     * 写入文件（自动生成文件名）
     * 本方法把文件存储到文件系统.并返回生成的文件名
     *
     * @param inputStream 输入流从中可读取出字节数组，不可为空
     * @param fileExtName 扩展名，不可为空，不需要"."，统一转换为小写字母。
     * @return 生成的文件名+扩展名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
     * @throws IOException
     */
    @Override
    public String write(InputStream inputStream, String fileExtName) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        String fileName = format(this.fileName(fileExtName));
        // 初始化OSSClient
        OSSClient client = createOrGetClient();
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, ossFolder(fileName), inputStream);
        // 打印ETag
        //System.out.println(result.getETag());
        return "/" + fileName;
    }

    /**
     * 写入文件（自动生成文件名） ，使用安全safe目录
     * 本方法把文件存储到文件系统.并返回生成的文件名
     * FileStorageDownloadServlet类负责处理通过http协议对图片的访问。负责权限检查,当访问safe区中的文件时，需要验证accessKey。
     *
     * @param inputStream 输入流从中可读取出字节数组，不可为空
     * @param fileExtName 扩展名，不可为空，不需要"."，统一转换为小写字母。
     * @return 生成的文件名+扩展名，例：/safe/99/99/5263bcec293d4c998b758143525654ee.png
     * @throws IOException
     */
    @Override
    public String writeSafe(InputStream inputStream, String fileExtName) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        String fileName = format(this.fileNameSafe(fileExtName));
        // 初始化OSSClient
        OSSClient client = createOrGetClient();
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, ossFolder(fileName), inputStream);
        // 打印ETag
        //System.out.println(result.getETag());
        return "/" + fileName;
    }

    /**
     * 写入文件（外部传入文件名）
     * 本方法把文件存储到文件系统，使用指定的文件名，存储到文件系统.并返回原文件名
     * <p>
     * 由外部传入文件,可实现主从文件
     * 主文件：/20/99/99/5263bcec293d4c998b758143525654ee.png（原图）
     * 从文件：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b（缩略图）
     *
     * @param inputStream 输入流从中可读取出字节数组
     * @param fileAllPath 文件名，示例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return 原文件名
     * @throws IOException
     */
    @Override
    public String write2(InputStream inputStream, String fileAllPath) throws IOException {
        return write2(inputStream, fileAllPath, null);
    }

    @Override
    public String write2(InputStream inputStream, String fileAllPath, Map<String, String> metadata) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String fileName = format(fileAllPath);
        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 创建上传Object的Metadata
        ObjectMetadata metadata2 = new ObjectMetadata();
        if (metadata != null) {
            metadata2.setUserMetadata(metadata);
        }

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, ossFolder(fileName), inputStream, metadata2);
        // 打印ETag
        //System.out.println(result.getETag());
        return "/" + fileName;
    }

    /**
     * 向已有文件追加内容(用于把一个大文件分批写入)
     *
     * @param inputStream 输入流从中可读取出字节数组
     * @param fileAllPath 远程文件名,例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return true:成功，false:失败
     * @throws IOException
     */
    @Override
    public boolean append(InputStream inputStream, String fileAllPath) throws IOException {
        throw new IOException("NotSupported");
    }

    /**
     * 文件是否存在
     *
     * @param fileAllPath
     * @return
     */
    @Override
    public boolean exists(String fileAllPath) {
        String key = format(fileAllPath);

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        return client.doesObjectExist(bucketName, ossFolder(key));
    }

    /**
     * 读取文件
     * 若想使用流来读取，请使用openInputStream方法。
     *
     * @param fileAllPath 远程文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return 文件内容的字节数组，返回null表示失败。
     * @throws IOException
     */
    @Override
    public byte[] read(String fileAllPath) throws IOException {
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String key = format(fileAllPath);

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        OSSObject object = null;
        try {
            object = client.getObject(bucketName, ossFolder(key));
        } catch (OSSException e) {
            //key不存在
            return null;
        }

        // 获取Object的输入流
        InputStream in = object.getObjectContent();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) > -1) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            // 关闭流
            try {
                in.close();
            } catch (Exception e) {
            }
            try {
                bos.flush();
            } catch (Exception e) {
            }
        }
        return bos.toByteArray();
    }


    /**
     * 替换文件
     *
     * @param inputStream 新文件内容
     * @param fileAllPath 远程文件名,例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return true:成功，false:失败
     * @throws IOException
     */
    @Override
    public boolean modify(InputStream inputStream, String fileAllPath) throws IOException {
        delete(fileAllPath);
        write2(inputStream, fileAllPath);
        return true;
    }

    /**
     * 删除文件
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return true:成功，false:失败
     */
    @Override
    public boolean delete(String fileAllPath) {
        if (StringUtils.isBlank(fileAllPath)) {
            return false;
        }
        String key = format(fileAllPath);
        // 初始化OSSClient
        OSSClient client = createOrGetClient();
        // 删除Object
        client.deleteObject(bucketName, ossFolder(key));
        return true;
    }

    /**
     * 返回文件大小（字节数），用于判断文件的长度
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return 正常时返回文件大小，无此文件时返回-1
     */
    @Override
    public long size(String fileAllPath) {
        if (StringUtils.isBlank(fileAllPath)) {
            //throw new IOException("入参fileAllPath不可为空");
            return 0;
        }
        String key = format(fileAllPath);

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        OSSObject object = null;
        try {
            object = client.getObject(bucketName, ossFolder(key));
        } catch (OSSException e) {
            //key不存在
            return 0;
        }
        long size = object.getResponse().getContentLength();
        return size;
    }

    /**
     * 获得一个输入流
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
     * @return
     * @throws IOException
     */
    @Override
    public InputStream openInputStream(String fileAllPath) throws IOException {
        return openInputStream(fileAllPath, null);
    }

    /**
     * 获得一个输入流
     * 读取图片，并支持图片处理
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
     * @param style       图片处理的样式，示例：String style = "image/resize,m_fixed,w_100,h_100";
     * @return
     * @throws IOException
     */
    private InputStream openInputStream(String fileAllPath, String style) throws IOException {
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String key = format(fileAllPath);

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();

        GetObjectRequest request = new GetObjectRequest(bucketName, ossFolder(key));
        if (style != null) {
            //String style = "image/resize,m_fixed,w_100,h_100";
            request.setProcess(style);//实时缩图
        }

        OSSObject object = null;
        try {
            object = client.getObject(request);
            //object = client.getObject(bucketName, key);
        } catch (OSSException e) {
            //key不存在
            return null;
        }

        // 获取Object的输入流
        InputStream in = object.getObjectContent();
        return in;
    }

    /**
     * 获得一个输出流
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
     * @param append      是否追加
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream(String fileAllPath, boolean append) throws IOException {
        throw new IOException("NotSupported");
    }

    /**
     * 下载文件、下载图片，支持实时缩图
     *
     * @param fileAllPath 目标文件的存储路径,示例：01/85/52/ec2c5fdc861540ac9696234d57df4455.jpg@220x220
     * @return
     */
    @SuppressWarnings("Duplicates")
    public InputStream download(String fileAllPath) throws IOException {
        String style = transform(fileAllPath);
        //把fileAllPath分成两部分
        //第一部分：文件路径，如：/10/63/35/9d68e4c0fcc14f15abf233ee1e746a1e.jpg
        String filePath = "";
        //第二部分：生成缩略图的尺寸，如300x200
        int haveFlag1 = fileAllPath.indexOf("@!");
        int haveFlag2 = fileAllPath.indexOf('@');
        if (haveFlag1 != -1) {
            filePath = fileAllPath.substring(0, haveFlag1);
        } else if (haveFlag2 != -1) {
            filePath = fileAllPath.substring(0, haveFlag2);
        } else {
            filePath = fileAllPath;
        }
        return this.openInputStream(filePath, style);
    }

    /**
     * 格式化文件路径
     *
     * @param key
     * @return
     */
    private String format(String key) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        return key;
    }

    /**
     * 处理隔离文件夹，拼接隔离文件夹。
     * 获得在oss的存储路径，包含一个隔离文件夹，这个隔离文件夹对外看不见的。
     * 有了隔离文件夹，多个项目可以使用一个oss中的同一个桶而互不影响。
     * 隔离文件夹不是必需的，可以为空，若空则文件存储在oss根目录下
     *
     * @param fileName 原path
     * @return 拼接了folder隔离文件夹的新path
     */
    private String ossFolder(String fileName) {
        if (StringUtils.isNoneBlank(folder)) {
            if (fileName.startsWith("\\") || fileName.startsWith("/")) {
                return folder.trim() + fileName;
            } else {
                return folder.trim() + "/" + fileName;
            }
        } else {
            return fileName;
        }
    }

    /**
     * 用于将思程软件缩图、切图的规则，翻译成阿里云的缩图、切图的规则
     * 思程软件的缩图、切图规则：http://wiki.sicheng.net:8090/pages/viewpage.action?pageId=9406199
     * 阿里云oss缩图、切图规则：https://help.aliyun.com/document_detail/44688.html?spm=a2c4g.11186623.6.1399.475615cdSBzlC4
     * 提取出url中思程软件的缩图规则，翻译成阿里云的缩图规则
     *
     * @param fileAllPath 目标文件的存储路径,示例：01/85/52/ec2c5fdc861540ac9696234d57df4455.jpg@220x220
     * @return 阿里云缩图规则
     */
    @SuppressWarnings("Duplicates")
    public static String transform(String fileAllPath) {
        //把fileAllPath分成两部分
        //第一部分：文件路径，如：/10/63/35/9d68e4c0fcc14f15abf233ee1e746a1e.jpg
        String filePath = "";
        //第二部分：生成缩略图的尺寸，如300x200
        String size = null;
        int haveFlag1 = fileAllPath.indexOf("@!");
        int haveFlag2 = fileAllPath.indexOf('@');
        if (haveFlag1 != -1) {
            filePath = fileAllPath.substring(0, haveFlag1);
            size = fileAllPath.substring(haveFlag1 + 2);
        } else if (haveFlag2 != -1) {
            filePath = fileAllPath.substring(0, haveFlag2);
            size = fileAllPath.substring(haveFlag2 + 1);
        } else {
            filePath = fileAllPath;
        }

        if (size == null) {
            //不处理图片
            return null;
        }

        //得到缩图的宽高
        String[] sizeData = size.split("x|X");
        if (sizeData.length != 2) {
            //格式不对,直接响应404
            return null;
        }
        Integer widthSize = 0;
        Integer heightSize = 0;
        try {
            widthSize = Integer.parseInt(sizeData[0]);
            heightSize = Integer.parseInt(sizeData[1]);
        } catch (Exception e) {
            //格式不对,直接响应404
            return null;
        }

        String action = null;
        if (haveFlag1 != -1) {
            action = "b";//缩切
        } else if (haveFlag2 != -1) {
            action = "a";//缩
        }
        //拼接oss的缩图命令
        StringBuffer rule = new StringBuffer();
        switch (action) {
            case "a": {
                //缩图
                rule.append("image/resize,m_lfit,h_");
                rule.append(heightSize);
                rule.append(",w_");
                rule.append(widthSize);
                break;
            }
            case "b": {
                //缩图+切图
                rule.append("image/resize,m_mfit,h_");
                rule.append(heightSize);
                rule.append(",w_");
                rule.append(widthSize);
                rule.append("/crop,w_");
                rule.append(widthSize);
                rule.append(",h_");
                rule.append(heightSize);
                rule.append(",g_center");
                break;
            }
            default: {
                break;
            }
        }
        return rule.toString();
    }

    public static void main(String[] args) {
        //不缩
        System.out.println(transform("http://www.abc.com/filestorage/20/99/99/5263bcec293d4c998b758143525654ee.png"));
        System.out.println(transform("http://www.abc.com/filestorage/20/99/99/5263bcec293d4c998b758143525654ee.png@"));
        System.out.println(transform("http://www.abc.com/filestorage/20/99/99/5263bcec293d4c998b758143525654ee.png@!"));
        //缩图并切图
        System.out.println(transform("http://www.abc.com/filestorage/20/99/99/5263bcec293d4c998b758143525654ee.png@!100x100"));
        //缩图
        System.out.println(transform("http://www.abc.com/filestorage/20/99/99/5263bcec293d4c998b758143525654ee.png@170x100"));
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {
        if (client != null) {
            client.shutdown();
        }
    }
}
