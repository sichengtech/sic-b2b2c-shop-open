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

import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.ImageProcess;
import com.sicheng.upload.thumbnail.simpleimage.ImageUtils4SI;

import java.io.InputStream;

/**
 * <p>标题: ImageProcessSimpleImageImpl</p>
 * <p>描述: SimpleImage是阿里巴巴的一个Java图片处理的类库，可以实现图片缩略、水印等处理。必须使用Oracle JDK8,不能使用OpenJDK8否则报某类找不到</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年9月17日 下午9:58:21
 */
public class ImageProcessSIImpl implements ImageProcess {

    /**
     * <p>描述: TODO(这里用一句话描述这个方法的作用) </p>
     *
     * @param inputStream
     * @param width
     * @param height
     * @param quality
     * @param format
     * @return
     * @see com.sicheng.upload.thumbnail.ImageProcess#resize(java.io.InputStream, int, int, int, java.lang.String)
     */
    @Override
    public InputStream resize(InputStream inputStream, int width, int height, int quality, String format) {
        return ImageUtils4SI.resize(inputStream, width, height, quality, format);
    }

    /**
     * <p>描述: TODO(这里用一句话描述这个方法的作用) </p>
     *
     * @param inputStream
     * @param width
     * @param height
     * @param quality
     * @param format
     * @return
     * @see com.sicheng.upload.thumbnail.ImageProcess#resizeAndCut(java.io.InputStream, int, int, int, java.lang.String)
     */
    @Override
    public InputStream resizeAndCut(InputStream inputStream, int width, int height, int quality, String format) {
        return ImageUtils4SI.resizeAndCut(inputStream, width, height, quality, format);
    }

    /**
     * <p>描述: TODO(这里用一句话描述这个方法的作用) </p>
     *
     * @param inputStream
     * @return
     * @see com.sicheng.upload.thumbnail.ImageProcess#getImageInfo(java.io.InputStream)
     */
    @Override
    public ImageInfo getImageInfo(InputStream inputStream) {
        return ImageUtils4SI.getImageInfo(inputStream);
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
    public String getFormat(InputStream inputStream) {
        return ImageUtils4SI.getFormat(inputStream);
    }

    /**
     * 取得图片信息 （宽、高）
     *
     * @param inputStream
     * @return
     */
    public ImageInfo getImageWH(InputStream inputStream) {
        return ImageUtils4SI.getImageWH(inputStream);
    }

}
