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
package com.sicheng.upload.thumbnail.graphicsmagick;

import com.sicheng.common.utils.FileUtils;
import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.MaterialLibrary;
import com.sicheng.upload.thumbnail.impl.ImageProcessGMImpl;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CommonImageOperateGM 测试类
 *
 * @author zhaolei 2013-1-17
 */
public class ImageUtils4GMTest2 {

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
        InputStream data = new ImageProcessGMImpl().resizeAndCut(inputStream, 185, 185, 95, "jpg");
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

    @Test
    public void testResizeAndFiller() throws Exception {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = ImageUtils4GM.resizeAndFiller(inputStream, width, height, quality, format, null);
        File file_out = new File(outFolder + "/gm_resizeAndFiller." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }

    @Test
    public void testSetQuality() throws Exception {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = ImageUtils4GM.setQuality(inputStream, quality, "jpg");
        File file_out = new File(outFolder + "/gm_resizeAndFiller." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }

    @Test
    public void testCropImg() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        ImageInfo info = ImageUtils4GM.getImageInfo(inputStream);
        System.out.println(info);

        FileInputStream fis2 = new FileInputStream(path);
        InputStream in2 = ImageUtils4GM.cut(fis2, 0, 0, 100, 100, "png");
        ImageInfo info2 = ImageUtils4GM.getImageInfo(in2);
        System.out.println(info2);
        TestCase.assertEquals(100, info2.getWidth());
        TestCase.assertEquals(100, info2.getHeight());
    }

    @Test
    public void testConvertImgType() throws IOException {
        FileInputStream fis = new FileInputStream(path);
        InputStream in = ImageUtils4GM.convertFormat(fis, "bmp");
        String format = ImageUtils4GM.getFormat(in);
        Assert.assertEquals("BMP", format);
    }

}