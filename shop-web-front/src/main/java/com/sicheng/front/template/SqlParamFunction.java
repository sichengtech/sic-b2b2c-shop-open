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
package com.sicheng.front.template;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>标题: 自定义函数，SqlParam函数</p>
 * <p>描述: 配合SqlQueryPageTag自定义标签，用于存储sql的参数</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年5月14日 下午9:00:53
 */
public class SqlParamFunction implements Function {
    public static final String PARAM_KEY_INDEX = "param_key_index";

    public Object call(Object[] paras, Context ctx) {
        if (paras.length != 1) {
            throw new RuntimeException("SqlParam函数缺少参数，必须有一个参数");
        }
        Object p = paras[0];//真实的参数
        Map<String, Object> globalVar = ctx.globalVar;
        if (globalVar != null) {
            Object v = globalVar.get(SqlQueryPageTag.SQL_PARAM_KEY);
            if (v instanceof Map) {
                Map<String, Object> param = (Map<String, Object>) v;
                //生成key,作为map的key .示例：param_0
                Integer index = (Integer) param.get(PARAM_KEY_INDEX);
                if (index == null) {
                    index = 0;
                }
                String mapkey = "p" + (index);//生成mapkey
                String key = "#{param[" + mapkey + "]}";//生成占位符
                param.put(mapkey, p);//把当前sql语句的?占位符的值存储到map中，供后续mybatis使用
                param.put(PARAM_KEY_INDEX, index + 1);//存储index
                return key;// 返回mybatis专用的 #param[p0]占位符
            }
        }
        return null;
    }
}