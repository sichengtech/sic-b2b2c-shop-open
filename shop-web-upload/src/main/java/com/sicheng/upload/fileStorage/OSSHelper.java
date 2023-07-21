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

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * OSS 客户端
 *
 * @author zhaolei
 */
public class OSSHelper {
    private static final Logger log = LoggerFactory.getLogger(OSSHelper.class);

    private static String accessKeyId = "accessKeyId值";//使用时需要替换为正常的accessKeyId
    private static String accessKeySecret = "accessKeySecret值";//使用时需要替换为正常的accessKeySecret

    //注意，上线时要使用内网域名
    private static String endpoint = "http://oss-cn-hangzhou-internal.aliyuncs.com";// 杭州OSS内网域名

    //注意，在开发环境要连接外网域名
//	public static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";// 杭州OSS外网域名


    private static volatile OSSClient client = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        String bucketName = "d04";
        String key = "test/test-001";
        String filePath = "/Users/zhaolei/Desktop/美工、图标/6.png";
        try {
            putObject(bucketName, key, filePath);

            byte[] data = getObject(bucketName, key);
            System.out.println(data == null ? "null" : data.length);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static OSSClient createOrGetClient() {
        if (client == null) {
            synchronized (OSSHelper.class) {
                if (client == null) {
                    // 初始化一个OSSClient
                    client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return client;
    }

    /**
     * 新建bucket
     *
     * @param bucketName
     */
    public static void createBucket(String bucketName) {
        // 初始化OSSClient
        OSSClient client = createOrGetClient();
        // 新建一个Bucket
        client.createBucket(bucketName);
    }

    /**
     * 列出用户所有的Bucket
     */
    public static void listBudkets() {
        OSSClient client = createOrGetClient();
        // 获取用户的Bucket列表
        List<Bucket> buckets = client.listBuckets();
        // 遍历Bucket
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    /**
     * 判断Bucket是否存在
     *
     * @param bucketName
     */
    public static void isBucketExist(String bucketName) {
        // 获取Bucket的存在信息
        OSSClient client = createOrGetClient();
        boolean exists = client.doesBucketExist(bucketName);

        // 输出结果
        if (exists) {
            System.out.println("Bucket exists");
        } else {
            System.out.println("Bucket not exists");
        }
    }


    /**
     * 上传Object
     *
     * @param bucketName
     * @param key
     * @param filePath
     * @throws FileNotFoundException
     */
    public static void putObject(String bucketName, String key, String filePath) throws FileNotFoundException {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();

        // 必须设置ContentLength
        meta.setContentLength(file.length());

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content, meta);

        // 打印ETag
//	    System.out.println(result.getETag());
    }


    /**
     * 上传Object到OSS
     *
     * @param bucketName 桶名
     * @param key        文件的key
     * @param data       数据
     * @param metaData   元数据
     */
    public static void putObject(String bucketName, String key, byte[] data, ObjectMetadata metaData) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 创建上传Object的Metadata
        if (metaData == null) {
            metaData = new ObjectMetadata();
        }

        // 必须设置ContentLength
        metaData.setContentLength(data.length);

        //ByteArrayInputStream 字节数组转流
        //ByteArrayOutputStream 流转字节数组
        ByteArrayInputStream content = new ByteArrayInputStream(data);

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content, metaData);

        // 打印ETag
//	    System.out.println(result.getETag());
    }

    /**
     * 上传Object到OSS
     *
     * @param bucketName
     * @param key
     * @param data
     */
    public static void putObject(String bucketName, String key, byte[] data) {
        putObject(bucketName, key, data, null);
    }

    /**
     * 列出所有Object
     *
     * @param bucketName
     */
    public static List<String> listObjects(String bucketName) {

        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);

        // 遍历所有Object
        List<String> list = new ArrayList<String>();
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            //System.out.println(objectSummary.getKey());
            list.add(objectSummary.getKey());
        }
        return list;
    }

    /**
     * 列出所有Object
     *
     * @param bucketName
     * @param dir        目录名,例如"fun/"
     * @param marker     如果需要遍历所有的object，而object数量大于1000，则需要进行多次迭代。每次迭代时，将上次迭代列取最后一个object的key作为本次迭代中的Marker即可
     * @param maxKeys    限定此次返回object的最大数，如果不设定，默认为100，MaxKeys取值不能大于1000。
     * @return
     */
    public static List<String> listObjects(String bucketName, String dir, String marker, Integer maxKeys) {

        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // "/" 为文件夹的分隔符
        listObjectsRequest.setDelimiter("/");
        // 递归的起点，oss默认已按字母排序
        if (marker != null) {
            listObjectsRequest.setMarker(marker);
        }
        if (maxKeys != null) {
            listObjectsRequest.setMaxKeys(maxKeys);
        }
        // 列出fun目录下的所有文件
        if (dir != null) {
            listObjectsRequest.setPrefix(dir);
        }

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(listObjectsRequest);

        // 遍历所有Object
        List<String> list = new ArrayList<String>();
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            //System.out.println(objectSummary.getKey());
            list.add(objectSummary.getKey());
        }
        return list;
    }

    public static void copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        // 拷贝Object
        CopyObjectResult result = client.copyObject(srcBucketName, srcKey, destBucketName, destKey);

//	    // 打印结果
//	    System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
    }

    /**
     * 判断某个object是否存在
     *
     * @param bucketName
     * @param key
     * @return
     */
    public static boolean isObjectExist(String bucketName, String key) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        return client.doesObjectExist(bucketName, key);
    }

    /**
     * 获取ObjectMetadata
     *
     * @param bucketName
     * @param key
     * @throws IOException
     */
    public static ObjectMetadata getObjectMetadata(String bucketName, String key) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        ObjectMetadata object = null;
        try {
            object = client.getObjectMetadata(bucketName, key);
        } catch (OSSException e) {
            //key不存在
            return null;
        }
        return object;
    }

    /**
     * 获取指定Object
     *
     * @param bucketName
     * @param key
     * @throws IOException
     */
    public static byte[] getObject(String bucketName, String key) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 初始化OSSClient
        // 获取Object，返回结果为OSSObject对象
        OSSClient client = createOrGetClient();
        OSSObject object = null;
        try {
            object = client.getObject(bucketName, key);
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
     * 删除Object
     *
     * @param bucketName
     * @param key
     */
    public static void deleteObject(String bucketName, String key) {
        // 初始化OSSClient
        OSSClient client = createOrGetClient();

        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }

        // 删除Object
        client.deleteObject(bucketName, key);
    }

    /**
     * 专用“静态化”开发的 写oss的方法
     *
     * @param bucketName
     * @param key
     * @param outSteam
     */
    public static void writeOss(String bucketName, String key, ByteArrayOutputStream outSteam) {
        if (key.startsWith("\\")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        if (key.startsWith("/")) {
            key = key.substring(1);//目录不能以“/”或者“\”开头
        }
        //原数据
        ObjectMetadata metaData = new ObjectMetadata();
//		metaData.setCacheControl(CmsSite.cacheControl);//7天
//		metaData.setContentType(CmsSite.contentType);

        byte[] dataArr = outSteam.toByteArray();

        //执行gzip压缩
        dataArr = gzip(dataArr);
        metaData.setContentEncoding("gzip");//是否压缩的标记，目前未压缩

        OSSHelper.putObject(bucketName, key, dataArr, metaData);
    }

    /**
     * gzip压缩
     *
     * @param ungzipped the bytes to be gzipped
     * @return gzipped bytes
     */
    private static byte[] gzip(byte[] ungzipped) {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bytes);
            gzipOutputStream.write(ungzipped);
            gzipOutputStream.close();
        } catch (Exception e) {
            log.error("gzip压缩异常", e);
        }
        return bytes.toByteArray();
    }
}
