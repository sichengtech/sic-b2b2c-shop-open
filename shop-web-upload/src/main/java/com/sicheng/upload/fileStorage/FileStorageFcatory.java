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
package com.sicheng.upload.fileStorage;

import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

/**
 * FileStorageFcatory
 * <p>
 * FileStorage的工厂类，通过工厂获得FileStorage接口的实现
 *
 * <p>标题: </p>
 * <p>描述: </p>
 *
 * @author zhaolei
 * @version 2016年7月3日 下午8:13:52
 */
public class FileStorageFcatory {

    /**
     * 取得一个默认的FileStorage接口的实现方案
     * 默认方案是：本地文件系统方案
     *
     * @return
     */
    public static FileStorage get() {
        return get("1");
        //FileStorage fileStorage=SpringContextHolder.getBean(FileStorage.class);
        //return fileStorage;
    }

    /**
     * 取得一个默认的FileStorage接口的实现方案
     * 默认方案是：本地文件系统方案
     *
     * @param name 方案名称 1：本地文件系统方案，目前暂无其它实现方案
     * @return
     */
    public static FileStorage get(String name) {

        if (StringUtils.isBlank(name)) {
            return null;
        }

        if ("1".equals(name.toLowerCase())) {
            FileStorage fileStorage = SpringContextHolder.getBean("fileSystem");
            return fileStorage;
        } else if ("2".equals(name.toLowerCase())) {
            FileStorage fileStorage = SpringContextHolder.getBean("fileSystem");
            return fileStorage;
        } else if ("3".equals(name.toLowerCase())) {
            FileStorage fileStorage = SpringContextHolder.getBean("staticPage");
            return fileStorage;
        } else {
            return null;
        }
    }
}
