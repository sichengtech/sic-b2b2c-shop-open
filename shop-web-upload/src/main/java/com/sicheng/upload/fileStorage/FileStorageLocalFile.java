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

import com.aliyun.oss.model.ObjectMetadata;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.IOUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.upload.thumbnail.ImageProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * 文件存储接口--本地文件系统实现
 * <p>
 * 海量小文件存储服务设计
 * 什么是“小文件”：网站中用户上传的图片(原图70K，缩略图5~15K)、生成的静态化页面，平均大小 50K。
 * 文件存储服务是一个基础服务，以接口形式提供。有多种不同的实现，每个实现适用的场景不同、成本不同。
 *
 * @author zhaolei
 * @version 2016年7月3日 下午1:04:44
 */
@SuppressWarnings("Duplicates")
public class FileStorageLocalFile extends FileStorageAbstract {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageLocalFile.class);

    //文件存储的根目录,会根据fdp.properties中的userfiles.basedir配置来决定
    //是存储在某个决对路径中，还是存储在tomcat的webapps中的工程目录中。
    private String getBase() {
        StringBuilder sbl = new StringBuilder();
        sbl.append(getUserfilesBaseDir());
        sbl.append(Global.getConfig("filestorage.dir"));
        return sbl.toString();
    }

    /**
     * 获取上传文件的根目录
     *
     * @return
     */
    private String getUserfilesBaseDir() {
        String dir = Global.getConfig("userfiles.basedir");
        if (StringUtils.isBlank(dir)) {
            dir = R.getWebRoot();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return dir;
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

        String fileName = this.fileName(fileExtName);
        File file = new File(getBase() + fileName);
        check(file);
        FileUtils.copyInputStreamToFile(inputStream, file);//关闭流
        return fileName;
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
        String fileName = this.fileNameSafe(fileExtName);
        File file = new File(getBase() + fileName);
        check(file);
        FileUtils.copyInputStreamToFile(inputStream, file);//关闭流
        return fileName;
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
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);
        check(file);
        FileUtils.copyInputStreamToFile(inputStream, file);//关闭流
        return fileName;
    }


    /**
     * 为兼容阿里云OSS而保留的接口，此方法只有阿里云OSS的实现类需要
     */
    @Override
    public String write2(InputStream inputStream, String fileAllPath, Map<String, String> metadata) throws IOException {
        //TODO 不支持处理metadata，所以就丢弃了。
        return write2(inputStream, fileAllPath);
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
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);

        FileOutputStream outputStream = FileUtils.openOutputStream(file, true);
        IOUtils.inputStream2OutStream(inputStream, outputStream);//关闭流
        return true;
    }

    /**
     * 文件是否存在
     *
     * @param fileAllPath
     * @return
     */
    @Override
    public boolean exists(String fileAllPath) {
        File file = new File(getBase() + fileAllPath);
        return file.exists();
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
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);
        check2(file);
        return FileUtils.readFileToByteArray(file);//关闭流
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
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);
        FileUtils.deleteQuietly(file);
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
            return 0;
        }
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);

        return FileUtils.sizeOf(file);
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
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);
        check2(file);
        return new FileInputStream(file);
    }

    /**
     * 获得一个输出流
     *
     * @param fileAllPath 目标文件的存储路径，例：/20/99/99/5263bcec293d4c998b758143525654ee.png
     * @param append      是否追加
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream(String fileAllPath, boolean append) throws IOException {
        if (StringUtils.isBlank(fileAllPath)) {
            throw new IOException("入参fileAllPath不可为空");
        }
        String fileName = fileAllPath;
        File file = new File(getBase() + fileName);

        check(file);
        return new FileOutputStream(file, append);
    }

    /**
     * 下载文件、下载图片，支持实时缩图
     *
     * @param fileAllPath 目标文件的存储路径,示例：01/85/52/ec2c5fdc861540ac9696234d57df4455.jpg@220x220
     * @return
     */
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
            return this.openInputStream(fileAllPath);
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
     * 安全检查1
     *
     * @param file
     * @throws IOException
     */
    private void check(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
    }

    /**
     * 安全检查2
     *
     * @param file
     * @throws IOException
     */
    private void check2(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {

    }
}
