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
package com.sicheng.upload.thumbnail.simpleimage;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import org.apache.commons.io.IOUtils;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * <p>
 * 标题: ImageUtils
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2017年9月17日 下午3:54:49
 */
public class SimpleImageDemo {
    static String rootPath = "/Users/zhaolei/Desktop";
    String srcFilePath = rootPath + "/test_image/JPEG/c1.jpg";

    /**
     * Java判断图片色彩空间是RGB还是CMYK
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static boolean isRgbOrCmyk(String filename) throws IOException {
        File file = new File(filename);
        boolean isRgb = true;// true是Rgb否则是Cmyk
        // 创建输入流
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if (readers == null || !readers.hasNext()) {
            throw new RuntimeException("No ImageReaders found");
        }
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);
        // 获取文件格式
        BufferedImage image;
        try {
            // 尝试读取图片 (包括颜色的转换).
            reader.read(0); // RGB
            isRgb = true;
        } catch (IIOException e) {
            // 读取Raster (没有颜色的转换).
            Raster r = reader.readRaster(0, null);// CMYK
            isRgb = false;
        }
        return isRgb;
    }

    // SimpleImage是阿里巴巴的一个Java图片处理的类库，可以实现图片缩略、水印等处理。
    public static void main(String[] args) {
        // String srcFilePath = "/Users/zhaolei/Desktop/变色2.jpg";
        // String srcFilePath = "/Users/zhaolei/Desktop/8位cmyk.jpg";
        // String srcFilePath = "/Users/zhaolei/Desktop/sj2.png";
        String srcFilePath = "/Users/zhaolei/Desktop/test_image/JPEG/b1.jpg";

        String outFilePath = rootPath + "/test_image/JPEG/3/e1_out_java_777.jpg";

        File in = new File(srcFilePath); // 原图片
        File out = new File(outFilePath); // 目的图片
        ScaleParameter scaleParam = new ScaleParameter(1024, 1024); // 将图像缩略到1024x1024以内，不足1024x1024则不做任何处理

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        WriteRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);

            wr.render(); // 触发图像处理
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    wr.dispose(); // 释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                    // skip ...
                }
            }
        }
    }
}
