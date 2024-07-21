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

import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.utils.StringUtils;
import org.springframework.beans.factory.DisposableBean;

import java.util.Random;
import java.util.UUID;

/**
 * @author zhaolei
 */
public abstract class FileStorageAbstract implements FileStorage, DisposableBean {

    Random random = new Random();// 定义随机类
    int layer1 = 20;//1层目录00~19，不包含20
    int layer2 = 100;//2层目录00~99，不包含100
    int layer3 = 100;//3层目录00~99，不包含100

    public FileStorageAbstract() {
        super();
    }

    /**
     * 生成文件名（无扩展名）
     * 目录随机选择，文件名是使用UUID
     *
     * @return 文件名： /20/99/99/5263bcec293d4c998b758143525654ee
     */
    public String fileName() {
        int r1 = random.nextInt(layer1);// 返回[0,20)集合中的整数，注意不包括20
        int r2 = random.nextInt(layer2);// 返回[0,100)集合中的整数，注意不包括100
        int r3 = random.nextInt(layer3);// 返回[0,100)集合中的整数，注意不包括100

        String dir1 = r1 < 10 ? "0" + r1 : Integer.toString(r1);
        String dir2 = r2 < 10 ? "0" + r2 : Integer.toString(r2);
        String dir3 = r3 < 10 ? "0" + r3 : Integer.toString(r3);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sbl = new StringBuilder();
        sbl.append("/").append(dir1).append("/").append(dir2).append("/").append(dir3).append("/").append(uuid);
        return sbl.toString();
    }

    /**
     * 生成文件名（无扩展名），使用安全safe目录
     * 目录随机选择，文件名是使用UUID
     * FileStorageDownloadServlet类负责处理通过http协议对图片的访问。负责权限检查,当访问safe区中的文件时，需要验证accessKey。
     *
     * @return 文件名： /safe/99/99/5263bcec293d4c998b758143525654ee
     */
    public String fileNameSafe() {
        //int r1=random.nextInt(layer1);// 返回[0,20)集合中的整数，注意不包括20
        int r2 = random.nextInt(layer2);// 返回[0,100)集合中的整数，注意不包括100
        int r3 = random.nextInt(layer3);// 返回[0,100)集合中的整数，注意不包括100

        String dir1 = "safe";
        String dir2 = r2 < 10 ? "0" + r2 : Integer.toString(r2);
        String dir3 = r3 < 10 ? "0" + r3 : Integer.toString(r3);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sbl = new StringBuilder();
        sbl.append("/").append(dir1).append("/").append(dir2).append("/").append(dir3).append("/").append(uuid);
        return sbl.toString();
    }

    /**
     * 生成文件名（含扩展名）
     * 目录随机选择，文件名是使用UUID
     *
     * @param fileExtName 文件扩展名,不需要带.只传入jpg就可
     * @return 文件名： /20/99/99/5263bcec293d4c998b758143525654ee.png
     */
    public String fileName(String fileExtName) {
        if (StringUtils.isNotBlank(fileExtName)) {
            if (!fileExtName.startsWith(".")) {
                fileExtName = "." + fileExtName.toLowerCase();//扩展名统一转成小写字节
            } else {
                fileExtName = fileExtName.toLowerCase();//扩展名统一转成小写字节
            }
            return fileName() + fileExtName;
        } else {
            return fileName();
        }
    }

    /**
     * 生成文件名（含扩展名），使用安全safe目录
     * 目录随机选择，文件名是使用UUID
     * FileStorageDownloadServlet类负责处理通过http协议对图片的访问。负责权限检查,当访问safe区中的文件时，需要验证accessKey。
     *
     * @param fileExtName 文件扩展名,不需要带.只传入jpg就可
     * @return 文件名： /safe/99/99/5263bcec293d4c998b758143525654ee.png
     */
    public String fileNameSafe(String fileExtName) {
        if (StringUtils.isNotBlank(fileExtName)) {
            if (!fileExtName.startsWith(".")) {
                fileExtName = "." + fileExtName.toLowerCase();//扩展名统一转成小写字节
            } else {
                fileExtName = fileExtName.toLowerCase();//扩展名统一转成小写字节
            }
            return fileNameSafe() + fileExtName;
        } else {
            return fileNameSafe();
        }
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    public abstract void destroy() throws Exception;

}