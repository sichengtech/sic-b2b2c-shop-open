/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.upload.fileStorage;

import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.IOUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.upload.thumbnail.ImageProcess;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件存储接口--自建Minio实现。
 * 2023年我使用Minio自建了一个S3对象储存，用于测试环境、演示环境等非正式场景。
 * Minio官网地址：https://docs.min.io/
 * 中文文档：http://docs.minio.org.cn/docs/
 * SDK下载：https://min.io/docs/minio/linux/developers/minio-drivers.html?ref=docs#java-sdk
 * Java Quickstart Guide：https://min.io/docs/minio/linux/developers/java/minio-java.html#minio-java-quickstart
 * Java Client API Reference：https://min.io/docs/minio/linux/developers/java/API.html
 * <p>
 * Minio 是个基于 Golang 编写的开源对象存储套件，基于Apache License v2.0开源协议，虽然轻量，却拥有着不错的性能。
 * 它兼容亚马逊S3云存储服务接口。使用 Amazon S3 v2 / v4 API。
 * 可以使用Minio SDK，Minio Client，AWS SDK 和 AWS CLI 访问Minio服务器。
 * <p>
 * MinIO教程：
 * TrueNAS SCALE 安装配置MinIO，S3对象储存，并使用s3cmd 备份网站和数据库
 * https://www.bilibili.com/video/av809356021/?vd_source=27939d7f88756edc13b2a75b298b2720
 * <p>
 * minio 搭建OSS服务器
 * https://blog.csdn.net/yu1xue1fei/article/details/118186826
 * <p>
 * OSS服务器（MinIO）的搭建和应用
 * https://blog.csdn.net/w13528476101/article/details/127193819
 * <p>
 * MINIO使用说明（附文件上传下载）
 * https://lebron.blog.csdn.net/article/details/129336760
 *
 * @author zhaolei
 */
@SuppressWarnings("Duplicates")
public class FileStorageMinio extends FileStorageAbstract {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageMinio.class);

    //spring注入
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;//桶的名称
    private String endpoint;
    private String folder;//隔离文件夹。所有文件存储在些文件夹下，若空则存储在根目录下

    private volatile MinioClient client = null; //客户端类

    private MinioClient createOrGetClient() {
        if (client == null) {
            synchronized (FileStorageMinio.class) {
                if (client == null) {
                    // 初始化一个OSSClient
                    if (endpoint == null || accessKeyId == null || accessKeySecret == null || bucketName == null) {
                        throw new RuntimeException(FileStorageMinio.class.getName() + "创建缺少必要的参数:endpoint,accessKeyId,accessKeySecret,bucketName");
                    }
                    client = MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(accessKeyId, accessKeySecret)
                            .build();
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
        // 初始化Client
        MinioClient client = createOrGetClient();
        // 上传Object
        ObjectWriteResponse response = null;
        try {
            response = client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) //桶名称
                            .object(ossFolder(fileName)) //文件全路径名
                            .stream(inputStream, -1, 10485760)//inputStream 输入流，objectSize 对象大小,partSize分片大小
                            .build());
        } catch (Exception e) {
            //client.putObject()声明会抛出多种异常：ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException
            //我的父接口要求抛出IOException异常，所以这里做一下包装，包装成IOException异常再抛出
            throw new IOException(e);
        }
        // 打印ETag
        System.out.println(response.etag());
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
        // 初始化Client
        MinioClient client = createOrGetClient();
        // 上传Object
        ObjectWriteResponse response = null;
        try {
            response = client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) //桶名称
                            .object(ossFolder(fileName)) //文件全路径名
                            .stream(inputStream, -1, 10485760)//inputStream 输入流，objectSize 对象大小,partSize分片大小
                            .build());
        } catch (Exception e) {
            //client.putObject()声明会抛出多种异常：ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException
            //我的父接口要求抛出IOException异常，所以这里做一下包装，包装成IOException异常再抛出
            throw new IOException(e);
        }
        // 打印ETag
        System.out.println(response.etag());
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
        // 初始化Client
        MinioClient client = createOrGetClient();
        // 上传Object
        ObjectWriteResponse response = null;
        try {
            response = client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) //桶名称
                            .object(ossFolder(fileName)) //文件全路径名
                            .stream(inputStream, -1, 10485760)//inputStream 输入流，objectSize 对象大小,partSize分片大小
                            .userMetadata(metadata)//元数据
                            .build());
        } catch (Exception e) {
            //client.putObject()声明会抛出多种异常：ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException
            //我的父接口要求抛出IOException异常，所以这里做一下包装，包装成IOException异常再抛出
            throw new IOException(e);
        }
        // 打印ETag
        System.out.println(response.etag());
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
        // 初始化Client
        MinioClient client = createOrGetClient();
        StatObjectResponse response = null;
        try {
            response = client.statObject(StatObjectArgs.builder().bucket(bucketName).object(ossFolder(key)).build());
            response.size();
            return true;
        } catch (Exception e) {
            return false;
        }
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

        // 初始化Client
        MinioClient client = createOrGetClient();

        // get object given the bucket and object name
        try {
            InputStream in = client.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(ossFolder(key))
                            .build());
            // Read data from stream
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                byte[] buffer = new byte[8192];
                int len = 0;
                while ((len = in.read(buffer)) > -1) {
                    bos.write(buffer, 0, len);
                }
            } catch (Exception e) {
                logger.error("", e);
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

        } catch (Exception e) {
            //key不存在
            return null;
        }
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
        // 初始化Client
        MinioClient client = createOrGetClient();
        // 删除Object
        try{
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(ossFolder(key)).build());
        }catch (Exception e){
            logger.error("删除文件出错，fileAllPath="+fileAllPath,e);
            return false;
        }
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
        MinioClient client = createOrGetClient();

        StatObjectResponse response = null;
        try {
            response = client.statObject(StatObjectArgs.builder().bucket(bucketName).object(ossFolder(key)).build());
            return response.size();
        } catch (Exception e) {
            return 0L;
        }
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
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String key = format(fileAllPath);
        // 初始化OSSClient
        MinioClient client = createOrGetClient();
        // get object given the bucket and object name
        try {
            InputStream in = client.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(ossFolder(key))
                            .build());
            return in;
        } catch (Exception e) {
            //key不存在
            return null;
        }
    }

//    /**
//     * 获得一个输入流
//     * 读取图片，并支持图片处理
//     *
//     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
//     * @param style       图片处理的样式，示例：String style = "image/resize,m_fixed,w_100,h_100";
//     * @return
//     * @throws IOException
//     */
//    private InputStream openInputStream(String fileAllPath, String style) throws IOException {
//        if (StringUtils.isBlank(fileAllPath)) {
//            throw new IOException("入参fileAllPath不可为空");
//        }
//        //TODO 不支持style，所以没有处理style
//        String key = format(fileAllPath);
//        // 初始化OSSClient
//        MinioClient client = createOrGetClient();
//        // get object given the bucket and object name
//        try {
//            InputStream in = client.getObject(
//                    GetObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(ossFolder(key))
//                            .build());
//            return in;
//        } catch (Exception e) {
//            //key不存在
//            return null;
//        }
//    }

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

        //目标文件扩展名
        String suffix = FileUtils.fileSuff(filePath);

        if (this.exists(fileAllPath)) {
            //若目标文件存在，就直接读出目标文件，响应请求
            if (logger.isDebugEnabled()) {
                logger.debug("文件存在，直接响应请求" + fileAllPath);
            }
            return new BufferedInputStream(this.openInputStream(fileAllPath));//读原图
        } else {
            //非图片（pdf、xls文件）都不需要缩图处理，都从这里放走
            if (suffix == null || !(suffix.equalsIgnoreCase("jpg")
                    || suffix.equalsIgnoreCase("jpeg")
                    || suffix.equalsIgnoreCase("gif")
                    || suffix.equalsIgnoreCase("bmp")
                    || suffix.equalsIgnoreCase("png"))) {

                if (logger.isDebugEnabled()) {
                    logger.debug("非图片（pdf、xls文件）都从这里放走" + filePath);
                }
                return null;
            }

            if (!this.exists(filePath)) {
                //若目标文件不存在，就找它的原图，原图也不存在，直接响应404
                return null;
            } else {
                //得到缩图的宽高
                if (StringUtils.isBlank(size)) {
                    //格式不对,直接响应404
                    return null;
                }
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

                //判断文件格式,png文件要照顾透明背景的图片
                String format = null;
                if (suffix.equalsIgnoreCase("png")) {
                    format = "png";//有一小部分png图片是透明背景的，格式一定要使用png格式,不然透明背景会变黑
                } else {
                    format = "jpg";//非PNG格式都转为jpg存储，可以在保持较高清析度的同时占用较少的存储空间。
                }

                //目标文件的原图存在,就使用原图生成目标图，存储后再响应请求
                if (haveFlag1 != -1) {
                    //执行缩切
                    if (logger.isDebugEnabled()) {
                        logger.debug("执行缩切" + fileAllPath);
                    }
                    return do2(filePath, fileAllPath, widthSize, heightSize, format);
                } else if (haveFlag2 != -1) {
                    //执行等比缩放
                    if (logger.isDebugEnabled()) {
                        logger.debug("执行等比缩放" + filePath);
                    }
                    return do1(filePath, fileAllPath, widthSize, heightSize, format);
                } else {
                    //格式不对,直接响应404
                    return null;
                }
            }
        }
    }

    /**
     * 使用原图生成目标图，并存储   执行等比缩放
     *
     * @param filePath    原图的路径
     * @param fileAllPath 缩略图的路径
     * @param widthSize   宽
     * @param heightSize  高
     * @param format      扩展名
     * @return
     * @throws IOException
     */
    private InputStream do1(String filePath, String fileAllPath, Integer widthSize, Integer heightSize, String format) throws IOException {
        BufferedInputStream in = new BufferedInputStream(this.openInputStream(filePath));//读原图
        ImageProcess imageProcess = SpringContextHolder.getBean(ImageProcess.class);
        InputStream inputStream = imageProcess.resize(in, widthSize, heightSize, 95, format);//执行等比缩放
        byte[] data = IOUtils.inputStream2ByteArray(inputStream);//一份数据写向两个目标
        FileStorageFcatory.get().write2(new ByteArrayInputStream(data), fileAllPath);//存储到文件系统
        IOUtils.closeQuietly(in);//关闭
        IOUtils.closeQuietly(inputStream);//关闭
        return new ByteArrayInputStream(data);
    }

    /**
     * 使用原图生成目标图，并存储   执行缩切
     *
     * @param filePath    原图的路径
     * @param fileAllPath 缩略图的路径
     * @param widthSize   宽
     * @param heightSize  高
     * @param format      扩展名
     * @return
     * @throws IOException
     */
    private InputStream do2(String filePath, String fileAllPath, Integer widthSize, Integer heightSize, String format) throws IOException {
        BufferedInputStream in = new BufferedInputStream(this.openInputStream(filePath));//读原图
        ImageProcess imageProcess = SpringContextHolder.getBean(ImageProcess.class);
        InputStream inputStream = imageProcess.resizeAndCut(in, widthSize, heightSize, 95, format);//执行等比缩放
        byte[] data = IOUtils.inputStream2ByteArray(inputStream);//一份数据写向两个目标
        FileStorageFcatory.get().write2(new ByteArrayInputStream(data), fileAllPath);//存储到文件系统
        IOUtils.closeQuietly(in);//关闭
        IOUtils.closeQuietly(inputStream);//关闭
        return new ByteArrayInputStream(data);
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
            //todo 如何关闭client?
            //client.shutdown();
        }
    }
}
