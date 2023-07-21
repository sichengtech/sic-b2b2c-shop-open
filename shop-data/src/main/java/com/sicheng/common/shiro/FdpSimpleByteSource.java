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
package com.sicheng.common.shiro;

import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * <p>
 * 标题: FdpSimpleByteSource 是一个可序列化的SimpleByteSource，可放入缓存中
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2017年7月8日 下午11:06:19
 */
public class FdpSimpleByteSource extends SimpleByteSource implements Serializable {

    private static final long serialVersionUID = 1L;


    public FdpSimpleByteSource(byte[] bytes) {
        super(bytes);
    }

    /**
     * Creates an instance by converting the characters to a byte array (assumes
     * UTF-8 encoding).
     *
     * @param chars the source characters to use to create the underlying byte
     *              array.
     * @since 1.1
     */
    public FdpSimpleByteSource(char[] chars) {
        super(chars);
    }

    /**
     * Creates an instance by converting the String to a byte array (assumes
     * UTF-8 encoding).
     *
     * @param string the source string to convert to a byte array (assumes UTF-8
     *               encoding).
     * @since 1.1
     */
    public FdpSimpleByteSource(String string) {
        super(string);
    }

    /**
     * Creates an instance using the sources bytes directly - it does not create
     * a copy of the argument's byte array.
     *
     * @param source the source to use to populate the underlying byte array.
     * @since 1.1
     */
    public FdpSimpleByteSource(ByteSource source) {
        super(source);
    }

    /**
     * Creates an instance by converting the file to a byte array.
     *
     * @param file the file from which to acquire bytes.
     * @since 1.1
     */
    public FdpSimpleByteSource(File file) {
        super(file);
    }

    /**
     * Creates an instance by converting the stream to a byte array.
     *
     * @param stream the stream from which to acquire bytes.
     * @since 1.1
     */
    public FdpSimpleByteSource(InputStream stream) {
        super(stream);
    }
}
