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
package com.sicheng.upload.thumbnail.thumbnail;

import com.sicheng.upload.thumbnail.MaterialLibrary;
import com.sicheng.upload.thumbnail.thumbnailator.ImageCMYK;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * <p>标题: ImageCMYKTest</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年9月17日 下午4:33:53
 */
public class ImageCMYKTest {

    /**
     * ImageCMYK类测试
     * 8位cmyk.jpg是CMYK色彩空间的图片，测试ImageCMYK工具类是否能正常读取这种类型的文件
     */
    @Test
    public void test1() throws FileNotFoundException {
        String name = "8位cmyk.jpg";
        String path = MaterialLibrary.getImagePath(name);
        FileInputStream in = new FileInputStream(path);
        BufferedImage image = ImageCMYK.read(in);
        // 图片的宽与高
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        TestCase.assertEquals("图片宽度对比发现错误", 253, imageWidth);
        TestCase.assertEquals("图片高度对比发现错误", 405, imageHeitht);
        System.out.println(name + ",宽:" + imageWidth + ",高:" + imageHeitht);
    }

    /**
     * ImageCMYK类测试
     * 变色2.jpg是能复现偏色问题的图片，测试ImageCMYK工具类是否能正常读取这种类型的文件
     */
    @Test
    public void test2() throws FileNotFoundException {
        String name = "变色2.jpg";
        String path = MaterialLibrary.getImagePath(name);
        FileInputStream in = new FileInputStream(path);
        BufferedImage image = ImageCMYK.read(in);
        // 图片的宽与高
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        TestCase.assertEquals("图片宽度对比发现错误", 800, imageWidth);
        TestCase.assertEquals("图片高度对比发现错误", 800, imageHeitht);
        System.out.println(name + ",宽:" + imageWidth + ",高:" + imageHeitht);
    }

    /**
     * ImageCMYK类测试
     * 透明.png是透明背景图片，测试ImageCMYK工具类是否能正常读取这种类型的文件
     */
    @Test
    public void test3() throws FileNotFoundException {
        String name = "透明.png";
        String path = MaterialLibrary.getImagePath(name);
        FileInputStream in = new FileInputStream(path);
        BufferedImage image = ImageCMYK.read(in);
        // 图片的宽与高
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        TestCase.assertEquals("图片宽度对比发现错误", 191, imageWidth);
        TestCase.assertEquals("图片高度对比发现错误", 193, imageHeitht);
        System.out.println(name + ",宽:" + imageWidth + ",高:" + imageHeitht);
    }

    /**
     * ImageCMYK类测试
     * a1.jpg是普通图片，测试ImageCMYK工具类是否能正常读取这种类型的文件
     */
    @Test
    public void test4() throws FileNotFoundException {
        String name = "a1.jpg";
        String path = MaterialLibrary.getImagePath(name);
        FileInputStream in = new FileInputStream(path);
        BufferedImage image = ImageCMYK.read(in);
        // 图片的宽与高
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        TestCase.assertEquals("图片宽度对比发现错误", 3072, imageWidth);
        TestCase.assertEquals("图片高度对比发现错误", 2304, imageHeitht);
        System.out.println(name + ",宽:" + imageWidth + ",高:" + imageHeitht);
    }
}
