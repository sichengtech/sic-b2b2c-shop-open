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
package com.sicheng.upload.thumbnail.simpleimage;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.*;
import com.alibaba.simpleimage.render.ScaleParameter.Algorithm;
import com.alibaba.simpleimage.util.*;
import com.sicheng.upload.thumbnail.ImageInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 阿里巴巴 simpleimage 图片处理工具类 功能 等比例缩放 裁切 加水印 复合操作
 * 必须使用Oracle JDK8,不能使用OpenJDK8否则报某类找不到
 *
 * @author zhaolei
 * @version 2016年7月31日 下午10:14:54
 */
@SuppressWarnings("Duplicates")
public class ImageUtils4SI {
    private static Logger logger = Logger.getLogger(ImageUtils4SI.class);
    public static String WATER_IMAGE_URL = "D:\\img\\watermark.png";
    protected static ImageFormat outputFormat = ImageFormat.JPEG;

    /**
     * 取得图片信息 （宽、高）
     *
     * @param inputStream 文件内容字节数组
     * @return ImageInfo
     */
    public static ImageInfo getImageWH(InputStream inputStream) {
        try {
            long t1 = System.currentTimeMillis();
            ImageWrapper imageWrapper = ImageReadHelper.read(inputStream);
            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            ImageInfo info = new ImageInfo();
            info.setWidth(w);
            info.setHeight(h);
            long t2 = System.currentTimeMillis();
            logger.info("getImageWH 方法执行耗时：" + (t2 - t1) + "ms");
            return info;
        } catch (SimpleImageException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得图片类型
     * 支持判断JPEG, GIF, PNG, BMP and TIFF
     * 注意：
     * 1、图片类型使用大写字母描述。
     * 2、jpg格式使用JPEG来表示。
     *
     * @param inputStream
     * @return 大写的文件类型
     */
    public static String getFormat(InputStream inputStream) {
        try {
            long t1 = System.currentTimeMillis();
            // 把流转为byte[]缓存在内存了，使用流可以重复读取，业务需要可重复读的流
            InputStream inputStream2 = ImageUtils.createMemoryStream(inputStream);
            ImageFormat imageFormat = ImageUtils.identifyFormat(inputStream2);
            String format = imageFormat.getDesc();
            long t2 = System.currentTimeMillis();
            logger.info("getFormat 方法执行耗时：" + (t2 - t1) + "ms");
            return format;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得图片信息 （宽、高、图片类型）
     *
     * @param inputStream 文件内容字节数组
     * @return ImageInfo
     */
    public static ImageInfo getImageInfo(InputStream inputStream) {
        InputStream inputStream2 = null;
        long t1 = System.currentTimeMillis();
        try {
            //把流转为byte[]缓存在内存了，使用流可以重复读取，业务需要可重复读的流
            inputStream2 = ImageUtils.createMemoryStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageInfo imageInfo = ImageUtils4SI.getImageWH(inputStream2);
        if (imageInfo != null) {
            try {
                inputStream2.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String format = ImageUtils4SI.getFormat(inputStream2);
            imageInfo.setFormat(format);
        }
        long t2 = System.currentTimeMillis();
        logger.info("getImageInfo 方法执行耗时：" + (t2 - t1) + "ms");
        return imageInfo;
    }

    /**
     * @param pInput
     * @param pImgeFlag
     * @return
     * @throws Exception
     */
    public static boolean isPicture(String pInput, String pImgeFlag) throws Exception {
        if (StringUtils.isBlank(pInput)) {
            return false;
        }
        String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1);
        String[][] imgeArray = {{"bmp", "0"}, {"dib", "1"}, {"gif", "2"}, {"jfif", "3"}, {"jpe", "4"},
                {"jpeg", "5"}, {"jpg", "6"}, {"png", "7"}, {"tif", "8"}, {"tiff", "9"}, {"ico", "10"}};
        for (int i = 0; i < imgeArray.length; i++) {
            if (!StringUtils.isBlank(pImgeFlag) && imgeArray[i][0].equals(tmpName.toLowerCase())
                    && imgeArray[i][1].equals(pImgeFlag)) {
                return true;
            }
            if (StringUtils.isBlank(pImgeFlag) && imgeArray[i][0].equals(tmpName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成缩略图，保持图片原有长宽比例
     *
     * @param inputStream
     * @param width       宽，像素
     * @param height      高，像素
     * @param quality     清晰度，1-100
     * @param format      文件后缀
     * @return
     */
    public static InputStream resize(InputStream inputStream, int width, int height, int quality, String format) {
        InputStream inputStream2 = null;
        try {
            // 把流转为byte[]缓存在内存了，使用流可以重复读取，业务需要可重复读的流
            inputStream2 = ImageUtils.createMemoryStream(inputStream);

            //先用resize3来处理图片，报错后再使用resize2来处理
            return resize3(inputStream2, width, height, quality, format);
        } catch (Exception e) {
            if (inputStream2 != null) {
                try {
                    inputStream2.reset();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                return resize2(inputStream2, width, height, quality, format);
            } catch (SimpleImageException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @SuppressWarnings("static-access")
    private static InputStream resize2(InputStream inputStream, int width, int height, int quality, String format) throws SimpleImageException {
        try {
            long t1 = System.currentTimeMillis();
            ImageWrapper imageWrapper = ImageReadHelper.read(inputStream);

            // 只缩小不放大
            // 将图像缩略到a x b以内，不足a x b则不做任何处理
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter(width, height, Algorithm.LANCZOS); // 缩放参数
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageWriteHelper.write(imageWrapper, out, outputFormat.getImageFormat(format), new WriteParameter());
            long t2 = System.currentTimeMillis();
            logger.info("resize 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(out.toByteArray());
        } catch (SimpleImageException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream); // 图片文件输入输出流必须记得关闭
        }
//		return null;
    }

    private static InputStream resize3(InputStream inputStream, int width, int height, int quality, String format) throws SimpleImageException {
        long t1 = System.currentTimeMillis();

        // 缩小
        ScaleParameter scaleParam = new ScaleParameter(width, height); // 缩放参数
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WriteRender wr = null;
        try {
            ImageRender rr = new ReadRender(inputStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, out);
            wr.render(); // 触发图像处理
        } catch (SimpleImageException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream); // 图片文件输入输出流必须记得关闭
            if (wr != null) {
                try {
                    wr.dispose(); // 释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                }
            }
        }
        long t2 = System.currentTimeMillis();
        logger.info("resize 方法执行耗时：" + (t2 - t1) + "ms");
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 按“最小边”生成指定宽与高的缩略图，由于图片长宽比不同造成的“超出”，将切掉
     *
     * @param inputStream
     * @param width       宽，像素
     * @param height      高，像素
     * @param quality     清晰度，1-100
     * @param format      文件后缀
     * @return
     */
    public static InputStream resizeAndCut(InputStream inputStream, int width, int height, int quality, String format) {
        try {
            long t1 = System.currentTimeMillis();
            ImageWrapper imageWrapper = ImageReadHelper.read(inputStream);
            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            int cw = w, ch = h, x = 0, y = 0;
            boolean isDeal = true;
            if (height > h && width > w) {
                isDeal = false;
            } else if ((w - width) > (h - height)) {
                ch = h;
                cw = (h * width) / height;
                x = (w - cw) / 2;
                if (cw > h) {
                    cw = w;
                    ch = (w * height) / width;
                    y = (h - ch) / 2;
                    x = 0;
                }

            } else if ((w - width) <= (h - height)) {
                cw = w;
                ch = (w * height) / width;
                y = (h - ch) / 2;
                if (ch > h) {
                    ch = h;
                    cw = (h * width) / height;
                    x = (w - cw) / 2;
                    y = 0;
                }
            }
            //System.out.println("x: " + x + ",y:" + y + ",cw: " + cw + ",ch:" + ch + "");
            if (isDeal) {
                CropParameter cropParam = new CropParameter(Math.abs(x), Math.abs(y), cw, ch);// 裁切参数
                PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
                ScaleParameter scaleParam = new ScaleParameter(width, height, Algorithm.LANCZOS); // 缩放参数
                planrImage = ImageScaleHelper.scale(planrImage, scaleParam);
                imageWrapper = new ImageWrapper(planrImage);
            }
            // 4.输出
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageWriteHelper.write(imageWrapper, out, ImageFormat.getImageFormat(format), new WriteParameter());
            long t2 = System.currentTimeMillis();
            logger.info("resizeAndCut 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(out.toByteArray());
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream); // 图片文件输入输出流必须记得关闭
        }
        return null;
    }

    /**
     * 压缩裁切图片到 指定尺寸,图片比目标图片小则不会变形(有水印）
     *
     * @param src
     * @param target
     * @param width
     * @param height
     */
    public static void scaleWithWaterMark(String src, String target, int width, int height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            int cw = w, ch = h, x = 0, y = 0;
            boolean isDeal = true;
            if (height > h || width > w) {
                isDeal = false;
            } else if ((w - width) > (h - height)) {
                ch = h;
                cw = (h * width) / height;
                x = (w - cw) / 2;
                if (cw > h) {
                    cw = w;
                    ch = (w * height) / width;
                    y = (h - ch) / 2;
                    x = 0;
                }

            } else if ((w - width) <= (h - height)) {
                cw = w;
                ch = (w * height) / width;
                y = (h - ch) / 2;
                if (ch > h) {
                    ch = h;
                    cw = (h * width) / height;
                    x = (w - cw) / 2;
                    y = 0;
                }
            }
            //System.out.println("x: " + x + " y" + y + "cw: " + cw + " ch" + ch + "");
            if (isDeal) {
                CropParameter cropParam = new CropParameter(x, y, cw, ch);// 裁切参数
                PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
                ScaleParameter scaleParam = new ScaleParameter(width, height, Algorithm.LANCZOS); // 缩放参数
                planrImage = ImageScaleHelper.scale(planrImage, scaleParam);
                imageWrapper = new ImageWrapper(planrImage);
            }
            // 3.打水印
            BufferedImage waterImage = ImageIO.read(new File(WATER_IMAGE_URL));
            ImageWrapper waterWrapper = new ImageWrapper(waterImage);
            Point p = calculate(imageWrapper.getWidth(), imageWrapper.getHeight(), waterWrapper.getWidth(),
                    waterWrapper.getHeight());
            WatermarkParameter param = new WatermarkParameter(waterWrapper, 1f, (int) p.getX(), (int) p.getY());
            BufferedImage bufferedImage = ImageDrawHelper.drawWatermark(imageWrapper.getAsBufferedImage(), param);
            imageWrapper = new ImageWrapper(bufferedImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, ImageFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    /**
     * 缩放到指定宽度 高度自适应
     *
     * @param src
     * @param target
     * @param width
     */
    @SuppressWarnings("static-access")
    public static void scaleWithWidth(String src, String target, Integer width) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS); // 缩放参数
            if (w < width) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);
            } else {
                int newHeight = getHeight(w, h, width);
                scaleParam = new ScaleParameter(width, newHeight + 1, Algorithm.LANCZOS);
            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    /**
     * 缩放到指定高度，宽度自适应
     *
     * @param src
     * @param target
     * @param height
     */
    public static void scaleWithHeight(String src, String target, Integer height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS); // 缩放参数
            if (w < height) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);

            } else {
                int newWidth = getWidth(w, h, height);
                scaleParam = new ScaleParameter(newWidth + 1, height, Algorithm.LANCZOS);

            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, ImageFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    /**
     * 根据宽、高和目标宽度 等比例求高度
     *
     * @param w
     * @param h
     * @param width
     * @return
     */
    private static Integer getHeight(Integer w, Integer h, Integer width) {
        return h * width / w;
    }

    /**
     * 根据宽、高和目标高度 等比例求宽度
     *
     * @param w
     * @param h
     * @param height
     * @return
     */
    private static Integer getWidth(Integer w, Integer h, Integer height) {
        return w * height / h;
    }

    /**
     * 从中间裁切需要的大小
     *
     * @param src
     * @param target
     * @param width
     * @param height
     */
    @SuppressWarnings("static-access")
    public static void CutCenter(String src, String target, Integer width, Integer height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();

            int x = (w - width) / 2;

            int y = (h - height) / 2;

            CropParameter cropParam = new CropParameter(x, y, width, height);// 裁切参数
            if (x < 0 || y < 0) {
                cropParam = new CropParameter(0, 0, w, h);// 裁切参数

            }

            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    /**
     * 裁切 为正文形
     *
     * @param src
     * @param target
     */
    public static void Cut(String src, String target) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            int width = 0;
            int height = 0;

            if (w >= h) {
                width = h;
                height = h;
            } else {
                width = w;
                height = w;
            }
            CropParameter cropParam = new CropParameter(0, 0, width, height);// 裁切参数
            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, ImageFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    private static Point calculate(int enclosingWidth, int enclosingHeight, int width, int height) {
        int x = (enclosingWidth / 2) - (width / 2);
        int y = (enclosingHeight / 2) - (height / 2);
        return new Point(x, y);
    }

}