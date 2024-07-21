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

import com.sicheng.common.utils.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.Template;

/**
 * <p>
 * 标题: 自定义函数，BindVarFunction函数
 * </p>
 * <p>
 * 描述: 根据分类id或name获取分类
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class BindVarFunction implements Function {

    public Object call(Object[] args, Context ctx) {
        if (args.length <= 1) {
            return null;
        }
        String k = args[0].toString();
        String v = args[1].toString();
        if (StringUtils.isBlank(k) || StringUtils.isBlank(v)) {
            return null;
        }
        Template template = ctx.template;
        template.binding(k, v);
        return null;
    }
}