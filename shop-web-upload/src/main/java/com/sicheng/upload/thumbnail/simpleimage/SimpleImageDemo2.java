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

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.CropParameter;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleParameter.Algorithm;
import com.alibaba.simpleimage.render.WatermarkParameter;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 阿里巴巴 simpleimage 图片处理工具类 功能 等比例缩放 裁切 加水印 复合操作
 *
 * @author 13643
 */
public class SimpleImageDemo2 {

    public static String WATER_IMAGE_URL = "D:\\img\\watermark.png";
    protected static ImageFormat outputFormat = ImageFormat.JPEG;

    public static void main(String[] args) {
        // 1.等比例缩放
        // scaleNormal("D:\\img\\src.jpg", "D:\\img\\scaleNormal.jpg", 720,
        // 1080);
        // 2.等比例缩放加水印
        // scaleWithWaterMark("D:\\img\\src.jpg",
        // "D:\\img\\scaleWithWaterMark.jpg",720, 1080);
        // 3.缩放到指定宽度
        // scaleWithWidth("D:\\img\\src.jpg", "D:\\img\\scaleWithWidth.jpg",
        // 400);
        // 4.缩放到指定高度
        // scaleWithHeight("D:\\img\\src.jpg", "D:\\img\\scaleWithHeight.jpg",
        // 600);
        // 5.裁切成正方形
        // Cut("D:\\img\\src.jpg", "D:\\img\\cut.jpg");
        // 6.从中间裁切
        // CutCenter("D:\\img\\src.jpg", "D:\\img\\cutCenter.jpg", 600,800);
        // print(40,20, 4,2);
        // print(20,40, 4,2);
        // print(40,20, 4,3);

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
     * 等比例缩放 会裁切部分内容
     *
     * @param src
     * @param target
     * @param width
     * @param height
     */
    @SuppressWarnings("static-access")
    public final static void scale(String src, String target, int width, int height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();

            float w1 = 0f, h1 = 0f;
            float sp = (float) w / h;
            float rp = (float) width / height;
            if (sp > rp) {
                w1 = (width * h) / (float) w;
                h1 = width;

            } else if (sp < rp) {
                h1 = (height * w) / (float) h;
                w1 = width;
            } else {
                w1 = width;
                h1 = height;
            }
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter((int) w1, (int) h1, Algorithm.LANCZOS); // 缩放参数
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

    public final static void scaleNormal(String src, String target, int width, int height) {
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
     * 压缩图片到 指定尺寸,图片比目标图片小则不会变形(有水印）
     *
     * @param src
     * @param target
     * @param width
     * @param height
     */
    public final static void scaleWithWaterMark(String src, String target, int width, int height) {
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
    public final static void scaleWithWidth(String src, String target, Integer width) {
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
    public final static void scaleWithHeight(String src, String target, Integer height) {
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
    public static Integer getHeight(Integer w, Integer h, Integer width) {

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
    public static Integer getWidth(Integer w, Integer h, Integer height) {
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
    public final static void CutCenter(String src, String target, Integer width, Integer height) {
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
    public final static void Cut(String src, String target) {
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

    public static Point calculate(int enclosingWidth, int enclosingHeight, int width, int height) {
        int x = (enclosingWidth / 2) - (width / 2);
        int y = (enclosingHeight / 2) - (height / 2);
        return new Point(x, y);
    }

}