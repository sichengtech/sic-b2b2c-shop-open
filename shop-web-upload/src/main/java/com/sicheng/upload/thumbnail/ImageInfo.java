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
package com.sicheng.upload.thumbnail;

/**
 * 图像信息描述类
 * 图片的宽、高 、类型
 *
 * @author zhaolei 2013-4-28
 */
public class ImageInfo {
    private int width;// 图片宽
    private int height;// 图片高
    private String format;// 图片格式,全大写，如：支持判断JPEG, GIF, PNG, BMP and TIFF

    public String toString() {
        StringBuilder sbl = new StringBuilder();
        sbl.append("width:");
        sbl.append(getWidth());
        sbl.append(",height:");
        sbl.append(getHeight());
        sbl.append(",format:");
        sbl.append(getFormat());
        return sbl.toString();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
