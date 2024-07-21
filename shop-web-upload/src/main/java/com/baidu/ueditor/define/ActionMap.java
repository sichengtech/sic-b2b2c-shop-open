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
package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义请求action类型
 *
 * @author zhaolei
 */
@SuppressWarnings("serial")
public final class ActionMap {

    public static final Map<String, Integer> mapping;
    // 获取配置请求
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    static {
        mapping = new HashMap<String, Integer>() {{
            put("config", ActionMap.CONFIG);
            put("uploadimage", ActionMap.UPLOAD_IMAGE);
            put("uploadscrawl", ActionMap.UPLOAD_SCRAWL);
            put("uploadvideo", ActionMap.UPLOAD_VIDEO);
            put("uploadfile", ActionMap.UPLOAD_FILE);
            put("catchimage", ActionMap.CATCH_IMAGE);
            put("listfile", ActionMap.LIST_FILE);
            put("listimage", ActionMap.LIST_IMAGE);
        }};
    }

    public static int getType(String key) {
        return ActionMap.mapping.get(key);
    }

}
