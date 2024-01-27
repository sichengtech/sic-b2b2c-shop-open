/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 * http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng;

import com.sicheng.common.utils.StringUtils;
import com.sicheng.upload.fileStorage.FileStorageMinio;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>图片文件清理工具,删除存储中多余的图片  </p>
 * <p>
 * 背景:
 * 本系统存储图片文件一般使用阿里云的OSS存储或Minio存储，随着时间的推移在存储中的多余图片文件、孤立图片文件越来越多需要一个工具来清理。
 * 本文件只一个简单的小工具，供临时使用。我使用它分析过一个35MB的SQL文件，未处理过更大的数量。如果你在使用过程中遇到问题需要自行解决。
 * <p>
 * 清理过程与原理：
 * 1、以数据库为准,手动从数据库导出全量数据到shop-cn.sql文件中。【手动】
 * 2、使用本程序，从shop-cn.sql文件中提取出图片文件的path。【程序】
 * 3、取出Minio存储的桶中的全部对象【程序】
 * 4、两者对比，找出存储中多余的文件，并删除。【程序】
 * </p>
 *
 * @author zhaolei
 * @version 2024-01-18 11:11
 */
public class DeleteExcessImagesTools {

    //1、以数据库为准,手动从数据库导出全量数据到 shop.sql文件
//    static String sqlFile = "C:\\Users\\WIN10\\Desktop\\minio工具\\shop-cn.sql";//中文商城的SQL
    static String sqlFile = "C:\\Users\\WIN10\\Desktop\\minio工具\\shop-en.sql";//英文商城的SQL


    ///////////////////////////////////
    // 以下是存储的账号，请根据实际情况修改
    ///////////////////////////////////
    static private String bucketName = "shop-b02";//桶的名称,shop-b01是中文商城的桶，shop-b02是英文商城的桶
    static private String endpoint = "http://192.168.92.43:9000";
    static private String accessKeyId = "i7EFMMUwGG2DQFTSeOHO";
    static private String accessKeySecret = "5fu2qXCgEiFMQVYWtwube4xNqOfCJCSNJipgNRXo";
    static private String folder = "shopImage1";//隔离文件夹。所有文件存储在些文件夹下，若空则存储在根目录下
    static private String folder2 = "upload/filestorage";//前缀目录名称

    public static void main(String[] args) {
        work();
//        DeleteExcessImagesTools tools = new DeleteExcessImagesTools();
//        List<String> formats = tools.searchFormats();//得到被搜索的扩展名
//        String patternAll = tools.pattern();//生成正则
//        String line="<p style=\"margin: 0px; padding: 0px; color: #000000; font-family: Arial; font-size: 12px; font-style: normal; font-weight: 400;\">  <img src=\"/upload/filestorage/alibaba/img/21/1576512380112.jpg\" alt=\"925 Sterling Silver Stackable Wedding Engagement Promise Ring Star Stackable Eternity Promise Rings\">   <img src=\"/upload/filestorage/alibaba/img/21/1576512380362.jpg\" alt=\"925 Sterling Silver Stackable Wedding Engagement Promise Ring Star Stackable Eternity Promise Rings\">   <img src=\"/upload/filestorage/alibaba/img/21/1576512380627.jpg\" alt=\"925 Sterling Silver Stackable Wedding Engagement Promise Ring Star Stackable Eternity Promise Rings\">   <img src=\"/upload/filestorage/alibaba/img/21/1576512380846.jpg\" alt=\"925 Sterling Silver Stackable Wedding Engagement Promise Ring Star Stackable Eternity Promise Rings\">   <img src=\"/upload/filestorage/alibaba/img/21/1576512381065.jpg\" alt=\"925 Sterling Silver Stackable Wedding Engagement Promise Ring Star Stackable Eternity Promise Rings\"> </p> ";
//        Set<String> set =new TreeSet<>();//set用于接收返回结果
//        tools.analyzeText( line,  formats,  patternAll,  set);
//        for (String path : set) {
//            System.out.println("SQL文件中发现 文件path " + path);
//        }
    }

    public static void work() {
        DeleteExcessImagesTools tools = new DeleteExcessImagesTools();
        List<String> formats = tools.searchFormats();//得到被搜索的扩展名
        String patternAll = tools.pattern();//生成正则

        // 1、以数据库为准,手动从数据库导出全量数据到 shop.sql文件
        List<String> lines = tools.readFile(sqlFile);//按行读文本文件

        //2、使用本程序，从shop.sql文件中提取出文件的path
        Set<String> set = new TreeSet<>();//set用于接收返回结果
        for (String line : lines) {
            tools.analyzeText(line, formats, patternAll, set);// 分析一行文本
        }
        System.out.println("SQL文件中发现 文件path " + set.size() + "个");

        // 3、取出Minio的桶中的全部对象
        Iterable<Result<Item>> it = listObjects(); // Iterable 懒加载的迭代器，可安全取出海量数据，不受maxKeys的约束
        Iterator<Result<Item>> iter = it.iterator();

        //4、两者对比，找出存储中多余的文件，并删除。
        int count = 0;//计数器
        while (iter.hasNext()) {
            try {
                count++;
                Result<Item> result = iter.next();
                Item item = result.get();
                String objectPath = item.objectName();//取得对象的名称
                if (objectPath.contains("shop_init")) {
                    continue; //跳过 ，不可删除"shop_init"目录下的文件
                }
                if (objectPath.contains("00/78/60/userinfo.jpg")) {
                    continue; //跳过 ，不删除00/78/60/userinfo.jpg文件
                }

                //物理查找
                boolean isFind = false;
                for (String s : set) {
                    String filePath = s;

                    //清理对齐字符串
                    String objectPath2 = clean(objectPath);
                    String filePath2 = clean(filePath);

                    //重点，在这里对比判断，请认真检查逻辑，仿止误删文件。
                    if ((objectPath2).equals(filePath2)) {
                        isFind = true; //找到了，说明这个文件在使用，不可删除。
                        System.out.println("###发现有用文件###" + count);
                        System.out.println("     objectPath=" + objectPath);
                        System.out.println("       filePath=" + filePath);
                        break;
                    }
                }
                if (!isFind) {
                    System.out.println("删除多余文件" + count + "：" + objectPath);
                    delete(objectPath); //从存储中删除文件
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("运行结束！");
    }

    static private String clean(String path) {
        String key1 = "/"+folder2;
        String key2 = folder2;
        String key3 = "/" + folder;
        String key4 = folder;
        String key5 = "/";
        if (path.startsWith(key1)) {
            path = path.substring(key1.length());
        }
        if (path.startsWith(key2)) {
            path = path.substring(key2.length());
        }
        if (path.startsWith(key3)) {
            path = path.substring(key3.length());
        }
        if (path.startsWith(key4)) {
            path = path.substring(key4.length());
        }
        if (path.startsWith(key5)) {
            path = path.substring(key5.length());
        }
        return path;
    }

    /**
     * 得到被搜索的扩展名
     * 用途：将用于从SQL文件中提出包含此扩展名的文件
     *
     * @return
     */
    private List<String> searchFormats() {
        List<String> formats = new ArrayList<String>();
        String[] arr1 = {"jpg", "jpeg", "png", "gif", "bmp", "webp", "tif", "svg", "psd"};//图片
        String[] arr2 = {"mp3", "mp4", "avi", "wav", "aif", "avi", "mov"};//音频、视频
        String[] arr3 = {"txt", "doc", "docx", "xls", "xlsx", "csv", "ppt", "pptx", "pdf"};//文档
        String[] arr4 = {"zip", "rar"};//压缩文件
        String[] arr5 = {"html", "xml", "json"};//结构化文档
        String[] arr6 = {"wgt", "apk", "json"};//App包
        //这些是常用的扩展名，将接扩展名来查找字符串
        formats.addAll(Arrays.asList(arr1));
        formats.addAll(Arrays.asList(arr2));
        formats.addAll(Arrays.asList(arr3));
        formats.addAll(Arrays.asList(arr4));
        formats.addAll(Arrays.asList(arr5));
        formats.addAll(Arrays.asList(arr6));
        return formats;
    }

    /**
     * 生成正则
     * 作用：此正则用于在文本搜索 文件
     *
     * @return 正则
     */
    private String pattern() {
        //提取图片path的正则表达式
        String patternAll = "([A-Za-z0-9_/\\-\\.\\!\\:]+\\.(jpg|png))";
        String pattern1 = "([A-Za-z0-9_/\\-\\.\\!\\:]+\\.(";//正则表达式-左半
        String pattern2 = "))";  //正则表达式2-右半

        //接装正则表达式，用于提取path
        StringBuilder sblAll = new StringBuilder();
        StringBuilder sblsub = new StringBuilder();
        sblAll.append(pattern1);
        List<String> formats = searchFormats();
        for (int i = 0; i < formats.size(); i++) {
            String ext = formats.get(i);
            if (StringUtils.isNotBlank(ext)) {
                sblsub.append(ext);
                sblsub.append("|");
            }
        }
        if (sblsub.length() > 0) {
            String s = sblsub.substring(0, sblsub.length() - 1);
            sblAll.append(s);
        }
        sblAll.append(pattern2);
        patternAll = sblAll.toString();
        System.out.println("正则：" + patternAll);
        return patternAll;
    }

    /**
     * 按行读文本文件
     *
     * @param filePath 文本文件的路径
     */
    private List<String> readFile(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 分析一行文本
     * @param line 一行文本
     * @param set 容器，用于存储结果
     */
    int count3 = 0;

    private void analyzeText(String line, List<String> formats, String patternAll, Set<String> set) {
        if (StringUtils.isNotBlank(line)) {
            boolean bl = this.containImage(line, formats); //文本中是否包含 指定的扩展名
            if (bl) {
//                System.out.println("发现图片：" + line);
                //正则分组提取  (不区分大小写)
                Matcher matcher = Pattern.compile(patternAll, Pattern.CASE_INSENSITIVE).matcher(line);
//                System.out.println((++count) +"行，原文:" +line); //打印行号+原文
                System.out.print((++count3) + "行，"); //打印行号
                while (matcher.find()) {
//                    System.out.println("    "+matcher.group(0) + " " + matcher.group(1) );
                    System.out.println("    " + matcher.group(1));
                    set.add(matcher.group(1));
                }
            }
        }
    }

    /**
     * 判断一段文本中是否包含 指定的扩展名 ，忽略字母大小写
     *
     * @param line 一段文本
     * @param formats 扩展名
     * @return true包含，false不包含
     */
    private boolean containImage(String line, List<String> formats) {
        int size = formats.size();
        for (int i = 0; i < size; i++) {
            boolean bl = line.toLowerCase().contains("." + formats.get(i).toLowerCase());
            if (bl) {
                return true;
            }
        }
        return false;
    }


    //////////////////////////////////////////////////////////////////
    // 以下是Minio存储操作专用工具
    //////////////////////////////////////////////////////////////////

    static private volatile MinioClient client = null; //客户端类

    static private MinioClient createOrGetClient() {
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
     * ListObjects 取出存储中的所有文件
     * @return Iterable 懒加载的迭代器，可安全取出海量数据，不受maxKeys的约束
     */
    static public Iterable<Result<Item>> listObjects() {
        // 初始化Client
        MinioClient client = createOrGetClient();
        try {
//            maxKeys 指定返回Object的最大数。
//            取值：大于0小于等于1000
//            默认值：100
            ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).maxKeys(100).recursive(true).build();
            Iterable<Result<Item>> it = client.listObjects(args);
            return it;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @param fileAllPath 文件名，例：/20/99/99/5263bcec293d4c998b758143525654ee.png@!118x99b
     * @return true:成功，false:失败
     */
    static public boolean delete(String fileAllPath) {
        if (StringUtils.isBlank(fileAllPath)) {
            return false;
        }
        String key = format(fileAllPath);
        // 初始化Client
        MinioClient client = createOrGetClient();
        // 删除Object
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(key).build());
//            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(ossFolder(key)).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 格式化文件路径
     *
     * @param key
     * @return
     */
    static private String format(String key) {
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
//    static private String ossFolder(String fileName) {
//        if (StringUtils.isNoneBlank(folder)) {
//            if (fileName.startsWith("\\") || fileName.startsWith("/")) {
//                return folder.trim() + fileName;
//            } else {
//                return folder.trim() + "/" + fileName;
//            }
//        } else {
//            return fileName;
//        }
//    }
}
