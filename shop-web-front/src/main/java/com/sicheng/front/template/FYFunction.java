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
package com.sicheng.front.template;

import com.sicheng.common.utils.FYUtils;
import com.sicheng.front.template.util.TagUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，AreaProvinceListFunction函数
 * </p>
 * <p>
 * 描述: 根据键获取国际化信息
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2019年11月26日 上午09:20:10
 */
public class FYFunction implements Function {

    public static final String KEY = "key";//键
    public static final String PARAMS = "params";//参数，多个参数以逗号分隔

    public String call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String key = TagUtils.getString(tagParamMap, KEY);
        String params = TagUtils.getString(tagParamMap, PARAMS);
        if (StringUtils.isBlank(key)) {
            return key;
        }
        String[] paramArr={};
        if(StringUtils.isNotBlank(params)){
            paramArr=params.split(",");
        }
        return FYUtils.fyParams(key,paramArr);
    }
}