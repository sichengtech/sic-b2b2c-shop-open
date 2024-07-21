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
import com.sicheng.upload.thumbnail.ImageInfo;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.log4j.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * <p>标题: Java实现的图片处理工具类 （缩略图、裁剪）</p>
 * <p>描述: 使用了Thumbnails图片处理库，Thumbnailator 是一个优秀的图片处理的Google开源Java类库，可运行于OpenJdk8\11\17</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2016年7月31日 下午10:14:54
 */
@SuppressWarnings("restriction")
public class ImageUtils4TH {
    private static final Logger logger = Logger.getLogger(ImageUtils4TH.class);

    /**
     * 清晰度取值范围 1-100，请不要轻易调整此值。默认值 90
     */
    public static final int QUALITY = 95;

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
        long t1 = System.currentTimeMillis();
        try {
            BufferedImage image = ImageCMYK.read(inputStream);

            // 图片的宽与高
            int imageWidth = image.getWidth();
            int imageHeitht = image.getHeight();

            // 只缩小不放大
            if (width > imageWidth) {
                width = imageWidth;
            }
            if (height > imageHeitht) {
                height = imageHeitht;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Builder<?> builder = Thumbnails.of(image).size(width, height).outputFormat(format);
            builder.toOutputStream(outputStream);

            long t2 = System.currentTimeMillis();
            logger.info("resize 执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("resize异常", e);
        } finally {
            //谁读了流，谁负责关，流被读了就不能再被使用了
            IOUtils.closeQuietly(inputStream);
        }
        return null;
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
        long t1 = System.currentTimeMillis();
        try {
            BufferedImage image = ImageCMYK.read(inputStream);

            // 图片的宽与高
            int imageWidth = image.getWidth();
            int imageHeitht = image.getHeight();

            // 只缩小不放大
            if (width > imageWidth) {
                width = imageWidth;
            }
            if (height > imageHeitht) {
                height = imageHeitht;
            }

            // 计算比例
            float w_scale = (float) width / imageWidth;
            float h_scale = (float) height / imageHeitht;
            float new_scale = w_scale > h_scale ? w_scale : h_scale;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 按比例缩小图片(缩小或放大)
            image = Thumbnails.of(image).scale(new_scale).asBufferedImage();
            final Builder<BufferedImage> builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height);
            builder.outputQuality(quality / 100F).size(width, height);
            builder.outputFormat(format);
            builder.toOutputStream(outputStream);

            long t2 = System.currentTimeMillis();
            logger.info("resizeAndCut 执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("resizeAndCut 异常", e);
        } finally {
            //谁读了流，谁负责关，流被读了就不能再被使用了
            IOUtils.closeQuietly(inputStream);
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
     * @param fileContent
     * @return 大写的文件类型
     */
    public static String getFormat(byte[] fileContent) {
        if (fileContent == null) {
            return "";
        }
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(fileContent);
            return getFormat(in);
        } catch (Exception e) {
            logger.error("getFormat" + e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取图片文件类型
     *
     * @param filePathName 文件路径
     * @return jpg\gif\bmp\gif
     */
    public static String getFormat(String filePathName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePathName);
            return getFormat(fis);
        } catch (IOException e) {
            logger.error("getFormat" + e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    /**
     * 获取图片文件类型
     *
     * @param inputStream 输入流
     * @return jpg\gif\bmp\gif
     */
    public static String getFormat(InputStream inputStream) {
        try {
            long t1 = System.currentTimeMillis();
            // 创建图片输入流
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            final Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
            if (!iter.hasNext()) {
                throw new RuntimeException("No readers found!");
            }
            final ImageReader reader = iter.next();
//            System.out.println("Net imageType: " + reader.getFormatName());
            String suffix = reader.getFormatName();
            long t2 = System.currentTimeMillis();
            logger.info("getFormat 方法执行耗时：" + (t2 - t1) + "ms");
            return suffix;
        } catch (Exception e) {
            logger.error("getFormat" + e.getMessage(), e);
            return "UNKNOW";
        }

//        String format = null;
//        ByteArrayInputStream bais = null;
//        MemoryCacheImageInputStream mcis = null;
//        long t1 = System.currentTimeMillis();
//        try {
//            mcis = new MemoryCacheImageInputStream(inputStream);
//            Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
//            while (itr.hasNext()) {
//                ImageReader reader = itr.next();
//                if (reader instanceof GIFImageReader) {
//                    format = "GIF";
//                } else if (reader instanceof JPEGImageReader) {
//                    format = "JPEG";
//                } else if (reader instanceof BMPImageReader) {
//                    format = "BMP";
//                } else if (reader instanceof PNGImageReader) {
//                    format = "PNG";
//                } else if (reader instanceof WBMPImageReader) {
//                    format = "WBMP";
//                } else {
//                    format = "UNKNOW";
//                }
//            }
//        } catch (Exception e) {
//            logger.error("getFormat" + e.getMessage(), e);
//        } finally {
//            //谁创建了流，谁负责关
//            if (bais != null) {
//                try {
//                    bais.close();
//                } catch (IOException ioe) {
//                }
//            }
//            if (mcis != null) {
//                try {
//                    mcis.close();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        long t2 = System.currentTimeMillis();
//        logger.info("getFormat 方法执行耗时：" + (t2 - t1) + "ms");
//        return format;
    }

    /**
     * 取得图片信息 （宽、高、图片类型）
     *
     * @param inputStream 文件内容字节数组
     * @return ImageInfo
     */
    public static ImageInfo getImageInfo(InputStream inputStream) {
        long t1 = System.currentTimeMillis();
        // 创造一个可以反复读取的流
        InputStream input = inputStream;
        if ((!(input instanceof ByteArrayInputStream)) && (!(input instanceof java.io.ByteArrayInputStream))) {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            try {
                IOUtils.copy(input, temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            input = new ByteArrayInputStream(temp.toByteArray());
        }

        //取得图片信息 （宽、高）
        ImageInfo info = getImageWH(input);
        try {
            input.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取图片文件类型
        String format = getFormat(input);
        if (info != null) {
            info.setFormat(format);
        }
        long t2 = System.currentTimeMillis();
        logger.info("getImageInfo 方法执行耗时：" + (t2 - t1) + "ms");
        return info;
    }

    /**
     * 取得图片信息 （宽、高）
     *
     * @param inputStream
     * @return
     */
    public static ImageInfo getImageWH(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ImageInfo info = new ImageInfo();
        try {
            long t1 = System.currentTimeMillis();
            BufferedImage image = ImageIO.read(inputStream); // 将in作为输入流，读取图片存入image中;
            info.setHeight(image.getHeight(null));
            info.setWidth(image.getWidth(null));
            long t2 = System.currentTimeMillis();
            logger.info("getImageWH 方法执行耗时：" + (t2 - t1));
        } catch (Exception e) {
            throw new RuntimeException("getImageWH异常", e);
        } finally {
            //谁读了流，谁负责关，流被读了就不能再被使用了
            IOUtils.closeQuietly(inputStream);
        }
        return info;
    }
}