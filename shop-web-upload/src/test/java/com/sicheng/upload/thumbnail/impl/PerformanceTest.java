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
import com.sicheng.upload.thumbnail.MaterialLibrary;
import com.sicheng.upload.thumbnail.graphicsmagick.ImageUtils4GM;
import com.sicheng.upload.thumbnail.simpleimage.ImageUtils4SI;
import com.sicheng.upload.thumbnail.thumbnailator.ImageUtils4TH;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理的性能测试
 * 测试方法：对一张图片处理N次，并计时，算出平均处理时间
 *
 * @author zhaolei 2013-1-17
 */
public class PerformanceTest {

    int width = 180;//生成的缩略图宽度，单位像素
    int height = 180;//生成的缩略图高度，单位像素
    int quality = 90;//画质
    int times = 10;//循环次数
    String format = "jpg";
    String name = "8位cmyk.jpg";//被处理的图片名称
    String path = MaterialLibrary.getImagePath(name);//被处理的图片的路径
    String outFolder = System.getProperty("java.io.tmpdir");//文件输出路径

    /**
     * 性能测试主入口
     * 通过使用3种图片处理方案，来对比3者的性能
     *
     * @throws IOException
     */
    @Test
    public void testMain() throws IOException {
        System.out.println("生成的测试图片的输出路径：" + outFolder);
        resize_loop_gm();
        resize_loop_th();//可运行于OpenJdk8\11\17
//        resize_loop_si();//必须使用Oracle JDK8,不能使用OpenJDK8否则报某类找不到

        //第一次测试结果
        //GM处理10次总耗时：850    (GM是c程序)
        //Thumbnailator处理10次总耗时：2793  (可运行在openjdk8\oralcejdk8)
        //SimpleImage处理10次总耗时：1258   (可运行在oralcejdk8)
        //第二次测试结果
        //GGM处理10次总耗时：763
        //Thumbnailator处理10次总耗时：2068
        //SimpleImage处理10次总耗时：1139

    }

    //使用GraphicsMagick处理图片
    public void resize_loop_gm() throws IOException {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            FileInputStream inputStream = new FileInputStream(path);
            InputStream in = ImageUtils4GM.resize(inputStream, width, height, quality, format);
            File file_out = new File(outFolder + "/out_gm_" + i + "." + format);
            FileUtils.copyInputStreamToFile(in, file_out);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("GM处理"+times+"次总耗时：" + (t2 - t1));
    }

    //使用Thumbnailator处理图片
    public void resize_loop_th() throws IOException {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            InputStream inputStream = new FileInputStream(path);
            InputStream data = ImageUtils4TH.resize(inputStream, width, height, quality, format);
            File file_out = new File(outFolder + "/out_th_" + i + "." + format);
            FileUtils.copyInputStreamToFile(data, file_out);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Thumbnailator处理"+times+"次总耗时：" + (t2 - t1));
    }

    //使用SimpleImage处理图片
    public void resize_loop_si() throws IOException {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            InputStream inputStream = new FileInputStream(path);
            InputStream data = ImageUtils4SI.resize(inputStream, width, height, quality, format);
            File file_out = new File(outFolder + "/out_si_" + i + "." + format);
            FileUtils.copyInputStreamToFile(data, file_out);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("SimpleImage处理"+times+"次总耗时：" + (t2 - t1));
    }

}