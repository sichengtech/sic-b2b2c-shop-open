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

import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.interceptor.ExecuteTimeInterceptor;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.text.DecimalFormat;

/**
 * <p>标题: 获取当前页面执行时间</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月24日 上午11:21:22
 */
public class SiteExecuteTimeFunction implements Function {
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String call(Object[] paras, Context ctx) {
        //获取到threadLocal中存储的时间
//        ExecuteTimeInterceptor executeTimeInterceptor=SpringContextHolder.getBean(ExecuteTimeInterceptor.class);
//        if(executeTimeInterceptor==null){
//            return null;
//        }
        long startTime = ExecuteTimeInterceptor.getThreadLocal().get();
        if (startTime <= 0) {
            return null;
        }
        long endTime = System.currentTimeMillis();
        return df.format((endTime - startTime) / 1000D);
    }
}
