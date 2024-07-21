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
package com.sicheng.upload.thumbnail.impl;

import com.sicheng.common.utils.FileUtils;
import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.MaterialLibrary;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ImageProcessGMImplTest 单元测试类
 * 测试内容“GraphicsMagick + Im4Java 图片处理能力
 *
 * @author zhaolei 2013-1-17
 */
public class ImageProcessGMImplTest {

    int width = 180;//生成的缩略图宽度，单位像素
    int height = 180;//生成的缩略图高度，单位像素
    int quality = 90;//画质
    int times = 10;//循环次数
    String format = "jpg";
    String name = "f1.jpg";//被处理的图片名称
    String path = MaterialLibrary.getImagePath(name);//被处理的图片的路径
    String outFolder = System.getProperty("java.io.tmpdir");//文件输出路径

    @Test
    public void test_resize() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = new ImageProcessGMImpl().resize(inputStream, 185, 185, 95, "jpg");
        File file_out = new File(outFolder + "/gm_resize." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }


    @Test
    public void test_resizeAndCut() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = new ImageProcessGMImpl().resizeAndCut(inputStream, 200, 100, 95, "jpg");
        File file_out = new File(outFolder + "/gm_resizeAndCut." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }

    @Test
    public void test_getImageInfo() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        ImageInfo info = new ImageProcessGMImpl().getImageInfo(inputStream);
        System.out.println(info.toString());
        TestCase.assertEquals("JPEG", info.getFormat());
        TestCase.assertEquals(4000, info.getWidth());
        TestCase.assertEquals(3000, info.getHeight());
    }

    @Test
    public void test_getFormat() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        String format = new ImageProcessGMImpl().getFormat(inputStream);
        TestCase.assertEquals("JPEG", format);
    }

    @Test
    public void test_getImageWH() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        ImageInfo info = new ImageProcessGMImpl().getImageWH(inputStream);
        System.out.println(info.toString());
        TestCase.assertEquals(4000, info.getWidth());
        TestCase.assertEquals(3000, info.getHeight());
    }

}