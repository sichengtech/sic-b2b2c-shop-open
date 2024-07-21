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
package com.sicheng.upload.thumbnail.thumbnailator;

import com.sicheng.common.utils.IOUtils;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 纯Java处理CMYK格式(32位色深)的JPEG文件 的工具类
 * <p>
 * 直接使用ImageIO.read()读取图片，可正常处理24位色深的图片，但在处理32位色深的图片时会报错。
 * ImageCMYK工具类，就是用来解决这个问题的，可以正常读取 24位、32位色深的图片
 *
 * @author zhaolei
 */
public class ImageCMYK {

    /**
     * 将一个图片文件读入内存（兼容24位、32位色深的图片）
     *
     * @param img 图片文件
     * @return 图片对象
     */
    public static BufferedImage read(InputStream img) {
        // 创造一个可以反复读取的流
        InputStream input = img;
        if ((!(input instanceof ByteArrayInputStream)) && (!(input instanceof java.io.ByteArrayInputStream))) {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            try {
                IOUtils.copy(input, temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            input = new ByteArrayInputStream(temp.toByteArray());
        }

        try {
            // 采购普通的方式读取流
            return ImageIO.read(input);
        } catch (IOException e) {
            // 当走到这里，说明是CMYK格式(32位色深)的JPEG文件，要用特殊方式读取
            try {
                input.reset();// 重置，第二次读流
                return readJpeg(input);
            } catch (IOException e1) {
                throw new RuntimeException("ImageIO.read()读取图片时异常，虽然我已努力兼容各种图片，但今天还是遇到了另类图片，请人工介入解决！",e1);
            }
        }
    }

    /**
     * 尝试读取JPEG文件的高级方法,可读取32位的jpeg文件
     * <p/>
     * 来自:
     * http://stackoverflow.com/questions/2408613/problem-reading-jpeg-image-
     * using-imageio-readfile-file
     */
    static BufferedImage readJpeg(InputStream in) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPEG");
        ImageReader reader = null;
        while (readers.hasNext()) {
            reader = readers.next();
            if (reader.canReadRaster()) {
                break;
            }
        }
        ImageInputStream input = ImageIO.createImageInputStream(in);
        reader.setInput(input);
        // Read the image raster
        Raster raster = reader.readRaster(0, null);
        BufferedImage image = createJPEG4(raster);
        return image;
    }

    /**
     * Java's ImageIO can't process 4-component images and Java2D can't apply
     * AffineTransformOp either, so convert raster data to RGB. Technique due to
     * MArk Stephens. Free for any use.
     */
    private static BufferedImage createJPEG4(Raster raster) {
        int w = raster.getWidth();
        int h = raster.getHeight();
        byte[] rgb = new byte[w * h * 3];

        float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
        float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
        float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
        float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);

        for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
            float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i], cr = 255 - Cr[i];

            double val = y + 1.402 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);

            val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);

            val = y + 1.772 * (cb - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);
        }

        raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3,
                new int[]{0, 1, 2}, null);

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, (WritableRaster) raster, true, null);
    }

    /**
     * 在一个RGB画布上重新绘制Image,解决CMYK图像偏色的问题
     */
    public static BufferedImage redraw(BufferedImage img, Color bg) {
        BufferedImage rgbImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = rgbImage.createGraphics();
        g2d.drawImage(img, 0, 0, bg, null);
        g2d.dispose();
        return rgbImage;
    }

    /**
     * 写入一个 JPG 图像
     *
     * @param im        图像对象
     * @param targetJpg 目标输出 JPG 图像文件
     * @param quality   质量 0.1f ~ 1.0f
     */
    public static void writeJpeg(RenderedImage im, Object targetJpg, float quality) {
        try {
            ImageWriter writer = ImageIO.getImageWritersBySuffix("jpg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            ImageOutputStream os = ImageIO.createImageOutputStream(targetJpg);
            writer.setOutput(os);
            writer.write(null, new IIOImage(im, null, null), param);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException("写入一个 JPG 图像异常", e);
        }
    }
}