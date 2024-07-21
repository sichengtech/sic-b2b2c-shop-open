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
package com.sicheng.upload.thumbnail;

import java.io.InputStream;

/**
 * <p>
 * 标题: 图片处理接口
 * </p>
 * <p>
 * 描述:生成缩略图、裁剪、取图片格式、取图片宽度
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2017年9月13日 下午3:07:03
 */
public interface ImageProcess {

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
    InputStream resize(InputStream inputStream, int width, int height, int quality, String format);

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
    InputStream resizeAndCut(InputStream inputStream, int width, int height, int quality, String format);

    /**
     * 取得图片信息 （宽、高、图片类型）
     * 支持判断JPEG, GIF, PNG, BMP and TIFF
     *
     * @param inputStream 文件内容字
     * @return ImageInfo
     */
    ImageInfo getImageInfo(InputStream inputStream);

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
    String getFormat(InputStream inputStream);

    /**
     * 取得图片信息 （宽、高）
     *
     * @param inputStream
     * @return
     */
    ImageInfo getImageWH(InputStream inputStream);

}
