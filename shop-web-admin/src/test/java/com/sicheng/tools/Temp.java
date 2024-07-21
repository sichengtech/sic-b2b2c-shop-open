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
package com.sicheng.tools;

import com.sicheng.common.config.Global;

/**
 * <p>标题: Temp</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年10月1日 上午8:09:15
 */
public class Temp {
    public static void main(String[] a) {
        String s = Global.getConfig("web.view.index");
        int times = 3;
        long t1 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            String rr = Global.getConfig("web.view.index");
        }
        long t2 = System.nanoTime();
        System.out.println((t2 - t1) / times);
    }
}
