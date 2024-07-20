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
import com.sicheng.common.utils.IOUtils;
import com.sicheng.upload.thumbnail.ImageInfo;
import org.apache.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.Pipe;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * GraphicsMagick+Im4Java图片处理工具类
 * 为了提高缩略图上的画质与图片处理的稳定性，图片处理使用GraphicsMagick+Im4Java。
 *
 * @author zhaolei 2012-12-26
 */
public class ImageUtils4GM {
    private static Logger logger = Logger.getLogger(ImageUtils4GM.class);
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * 清晰度取值范围 1-100，请不要轻易调整此值。默认值 90
     */
    public static final int QUALITY = 90;

    static {
        // true：使用GM , false使用：IM
        System.setProperty("im4java.useGM", "true");
        //环境变量 IM4JAVA_TOOLPATH 指明了GM的安装位置
        // 大多数时候，请保留空，程序会根据环境变量找到gm命令
        //im4java会主动找环境变量 IM4JAVA_TOOLPATH,若找就就使用
        //只有当出现意外找不到gm命令时，才在这里配置一下，告诉java程序gm命令的位置
        //ProcessStarter.setGlobalSearchPath(searchPath);
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
        ImageInfo info = getImageInfo(inputStream);
        return info.getFormat();
    }

    /**
     * 取得图片信息 （宽、高、图片类型）
     *
     * @param inputStream 文件内容字节数组
     * @return ImageInfo
     */
    public static ImageInfo getImageInfo(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        try {
            long t1 = System.currentTimeMillis();
            Map<String, String> map = System.getenv();
            //System.out.println(map);

            IMOperation op = new IMOperation();
            op.format("%m\n%w\n%h\n%g\n%z\n%r");
            op.addImage("-");

            IdentifyCmd identifyCmd = new IdentifyCmd(true);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            Pipe pipeIn = new Pipe(new BufferedInputStream(inputStream), null);
            identifyCmd.setOutputConsumer(output);
            identifyCmd.setInputProvider(pipeIn);
            identifyCmd.run(op);
            ArrayList<String> cmdOutput = output.getOutput();
            Iterator<String> localIterator = cmdOutput.iterator();

            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("Format", localIterator.next());
            map1.put("Width", localIterator.next());
            map1.put("Height", localIterator.next());
            map1.put("Geometry", localIterator.next());
            map1.put("Depth", localIterator.next());//位深度
            map1.put("Class", localIterator.next());

            ImageInfo info = new ImageInfo();
            info.setFormat(map1.get("Format"));
            info.setWidth(Integer.valueOf(map1.get("Width")));
            info.setHeight(Integer.valueOf(map1.get("Height")));

            long t2 = System.currentTimeMillis();
            logger.info("getImageInfo 方法执行耗时：" + (t2 - t1) + "ms");
            return info;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得图片信息 （宽、高、大小）,如果是GIF会有多帧，返回每一帧的信息
     * <p>
     * 返回信息格式： 分隔符：多个帧用#分隔， 一 帧内信息用,分隔。 帧分隔示例：第一帧#第二帧#第三帧#
     * 帧内信息用分隔示例：width:250,height:200,name:465k.gif,type:GIF,size:465.8K#
     * 注：jpg,png图片只有一帧，gif图片会有多帧。
     *
     * @param imagePath 源图片
     * @return 字符串：width:250,height:200,name:465k.gif,format:GIF,size:465.8K#
     */
    public static String getFrames(String imagePath) {
        // 格式化文件路径
        // java可以识别不规范的路径，如 ： e:\imguppic\pic\\15/credit/187\b\证书.jpg
        // 但GM无法识别不规范的路径，要处理成规范的路径格式。
        imagePath = new File(imagePath).getAbsolutePath();
        String line = null;
        try {
            long t1 = System.currentTimeMillis();
            IMOperation op = new IMOperation();
            op.format("width:%w,height:%h,name:%f,format:%m,size:%b#");
            op.addImage(1);
            IdentifyCmd identifyCmd = new IdentifyCmd(true);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identifyCmd.setOutputConsumer(output);
            identifyCmd.run(op, imagePath);
            ArrayList<String> cmdOutput = output.getOutput();
            assert cmdOutput.size() == 1;
            line = cmdOutput.get(0);
            long t2 = System.currentTimeMillis();
            logger.info("getImageInfoFrame 方法执行耗时：" + (t2 - t1) + "ms," + imagePath);
        } catch (Exception e) {
            logger.error(imagePath, e);
        }
        return line;
    }

    /**
     * 生成缩略图，保持图片原有长宽比例 只有当图片的宽与高，大于给定的宽与高时，才进行“缩小”操作 默认处理多帧的GIF时，会保留动画效果.
     * 如不想保留GIF的动画效果，只取第一帧，请使用"d:\\380k.gif[0]"做为源图片文件名。
     *
     * @param inputStream  源图片
     * @param width      宽，像素
     * @param height     高，像素
     * @param suffix     文件后最
     */
    public static InputStream resize(InputStream inputStream, int width, int height, String suffix) {
        return resize(inputStream, width, height, QUALITY, suffix);
    }

    public static InputStream resize(InputStream inputStream, int width, int height, int quality, String suffix) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }

        long t1 = System.currentTimeMillis();
        try {
            IMOperation op = new IMOperation();

            // 性能优化，对于处理200x200以下的图片，加速效果明显
            // 在这个例子中,'-size 120x120'给了JPEG解码器一个暗示,图像将会缩减到120x120,
            // 允许它运行得更快通过避免返回GraphicsMagick全分辨率图像对后续的调整操作。
            // 这里不可以采用过大的值，如果你设置了400x400,但实际图片尺寸是 230x200(小于你设置的值),只会降低性能。
            if (width <= 200 && height <= 200) {
                op.size(width, height);
            }

            // 根据宽度、高度缩放图片
            // 只有当图片的宽与高，大于给定的宽与高时，才进行“缩小”操作
            // 为进行放大操作。
            op.resize(width, height, ">");

            // IE6-8,无法显示大于8位（windows的24位）的jpg图片，这里把图片统一处理成8位。
            // GM使用1个像素8位，windows使用3个像素24位，所以GM的8位=windows的24位。
            // 图片32位转24位
            op.colorspace("RGB");

            // 设置清晰度
            op.quality(Double.valueOf(quality));
            // 去掉照片中的Exif信息
            op.strip();
            // 锐化图片,建议值：arg0=1.0 arg1=5.0，会使图片变得清晰好看，不可以多次锐化
            // 决定不使用锐化
            // op.sharpen(1.0, 5.0);
            op.addImage("-");
            op.addImage(suffix == null ? "jpg" : suffix + ":-");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(new BufferedInputStream(inputStream), null);
            Pipe pipeOut = new Pipe(null, outputStream);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("resize 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("resize异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 按“最小边”生成指定宽与高的缩略图，由于图片长宽比不同造成的“超出”，将切掉
     * <p>
     * gm convert input.jpg -thumbnail "100x100^" -gravity center -extent
     * 100x100 output_3.jpg 裁剪后保证等比缩图
     * 生成的图片大小是：100x100，还保证了比例。不过图片经过了裁剪，剪了图片左右两边才达到1:1
     * <p>
     * 注：本功能，第一个用户是，为搜狗搜索、360搜索准备合格的图片
     *
     * @param inputStream
     * @param width
     * @param height
     * @param quality
     * @param suffix
     * @return
     */
    public static InputStream resizeAndCut(InputStream inputStream, int width, int height, int quality, String suffix) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        long t1 = System.currentTimeMillis();
        try {
            IMOperation op = new IMOperation();

            // 根据宽度、高度缩放图片
            // 按“最小边”生成指定宽与高的缩略图
            // 由于图片长宽比不同造成的“超出”，将切掉
            op.resize(width, height, "^");
            // 设置清晰度
            op.quality(Double.valueOf(quality));
            // 去掉照片中的Exif信息
            op.strip();

            // op.background(bgColor==null?"white":bgColor);//填充的颜色
            op.gravity("center");// 从中心点开始计算
            op.extent(width, height);// 填充的宽与高，从op.gravity("center")的位置计算

            op.addImage("-");
            op.addImage(suffix == null ? "jpg" : suffix + ":-");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(inputStream, null);
            Pipe pipeOut = new Pipe(null, outputStream);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("resizeAndCut 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("resizeAndCut异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 缩小图片并补白 缩小图片，由于长宽比不同，补白的位置，补指定颜色的背景。产出目标尺寸的图片 如果源图小于目标尺寸，会放大原图
     * 注：本功能，第一个用户是，为搜狗搜索、360搜索准备合格的图片
     *
     * @param inputStream
     * @param width       目标宽
     * @param height      目标高
     * @param quality     清晰度
     * @param suffix      扩展名
     * @param bgColor     用于填充的背景景色
     * @return
     */
    public static InputStream resizeAndFiller(InputStream inputStream, int width, int height, int quality,
                                              String suffix, String bgColor) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        long t1 = System.currentTimeMillis();
        try {
            IMOperation op = new IMOperation();

            // 根据宽度、高度缩放图片(会放大图片)
            op.resize(width, height);
            // 设置清晰度
            op.quality(Double.valueOf(quality));
            // 去掉照片中的Exif信息
            op.strip();

            op.background(bgColor == null ? "white" : bgColor);// 填充的颜色
            op.gravity("center");// 从中心点开始计算
            op.extent(width, height);// 填充的宽与高，从op.gravity("center")的位置计算

            op.addImage("-");
            op.addImage(suffix == null ? "jpg" : suffix + ":-");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(inputStream, null);
            Pipe pipeOut = new Pipe(null, outputStream);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("resizeAndFiller 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("resizeAndFiller异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 设定图片的品质(清晰度) 品质下降后，图片大小(字节)也会变小
     *
     * @param inputStream  源图片路径
     * @param quality    品质 1-100 ，一般85比较合适
     * @param suffix 后缀
     */
    public static InputStream setQuality(InputStream inputStream, int quality, String suffix) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        try {
            long t1 = System.currentTimeMillis();
            IMOperation op = new IMOperation();
            op.quality(Double.valueOf(quality));// 清晰度，品质
            op.addImage("-");
            op.addImage(suffix + ":-");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(inputStream, null);
            Pipe pipeOut = new Pipe(null, outputStream);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("setQuality(byte)方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("setQuality设定图片的品质为" + quality + "异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 裁剪图片
     *
     * @param inputStream  来源图片
     * @param x          保留区的起点坐标
     * @param y          保留区的起点坐标
     * @param width      保留区的宽度
     * @param height     保留区的高度
     */
    public static InputStream cut(InputStream inputStream, int x, int y, int width, int height, String suffix) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        try {
            long t1 = System.currentTimeMillis();
            IMOperation op = new IMOperation();
            op.addImage("-");
            // 切割图片 Integer width, Integer height, Integer x, Integer y
            op.append().crop(width, height, x, y);
            op.addImage(suffix + ":-");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(inputStream, null);
            Pipe pipeOut = new Pipe(null, outputStream);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("cut 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("cut时异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 旋转图片
     *
     * @param inImagePath  输入图片
     * @param outImagePath 输出图片
     * @param angle        角度
     */
    public static void rotate(String inImagePath, String outImagePath, double angle) {
        // 格式化文件路径
        // java可以识别不规范的路径，如 ： e:\imguppic\pic\\15/credit/187\b\证书.jpg
        // 但GM无法识别不规范的路径，要处理成规范的路径格式。
        inImagePath = new File(inImagePath).getAbsolutePath();
        outImagePath = new File(outImagePath).getAbsolutePath();
        FileUtils.createDirectory(new File(outImagePath).getParent());
        try {
            long t1 = System.currentTimeMillis();
            IMOperation op = new IMOperation();
            op.rotate(angle);
            op.addImage(inImagePath);
            op.addImage(outImagePath);
            ConvertCmd cmd = new ConvertCmd(true);
            cmd.run(op);
            long t2 = System.currentTimeMillis();
            logger.info("rotate 方法执行耗时：" + (t2 - t1) + "ms," + inImagePath + "," + outImagePath);
        } catch (Exception e) {
            logger.error("rotate时异常，" + inImagePath + "," + outImagePath, e);
        }
    }

    /**
     * 转换图片格式
     *
     * @param inputStream
     * @param format       图片的格式：jpg\png\bmp
     * @return
     */
    public static InputStream convertFormat(InputStream inputStream, String format) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }
        try {
            long t1 = System.currentTimeMillis();
            IMOperation op = new IMOperation();
            op.addImage("-"); // read from stdin
            op.addImage(format + ":-");// write to stdout in jpg-format
            // 注意这里的jpg可以根据你的图片格式而改变，但是必须要有，否则不知道你要以何格式生成转换后的图片

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Pipe pipeOut = new Pipe(null, outputStream);
            Pipe pipeIn = new Pipe(inputStream, null);
            ConvertCmd convert = new ConvertCmd(true);
            convert.setInputProvider(pipeIn);
            convert.setOutputConsumer(pipeOut);
            convert.run(op);

            long t2 = System.currentTimeMillis();
            logger.info("convertFormat(byte) 方法执行耗时：" + (t2 - t1) + "ms");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("convertFormat(byte) 时异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }
}