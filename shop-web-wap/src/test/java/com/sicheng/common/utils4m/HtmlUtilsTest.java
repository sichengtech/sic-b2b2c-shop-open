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

package com.sicheng.common.utils4m;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>标题: 商品详情处理工具的单元测试 </p>
 * <p>描述: 商品详情处理工具  </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2019-11-27 17:33
 *
 * <p>重要修改历史记录1: xxxx  。修改人：xx</p>
 * <p>重要修改历史记录2: xxxx  。修改人：xx</p>
 */
public class HtmlUtilsTest {

    @Test
    public void TestImageWidth1() {
        String html = "Jsoup会把< >转为&lt; &gt;";
        String html2 = HtmlUtils.imageWidth(html);
        System.out.println("原始的HTML是：" + html);
        System.out.println("替换后的HTML是：" + html2);
        String ok="Jsoup会把&lt; &gt;转为&lt; &gt;";
        Assert.assertEquals(ok,html2);
    }

    @Test
    public void TestImageWidth2() {
        String html = "<img src=\"https://img.alicdn.com/imgextra/i1/201749140/TB2wfzZXhTxLuJjy1XcXXb.gXXa-201749140.jpg\">";
        String html2 = HtmlUtils.imageWidth(html);
        System.out.println("原始的HTML是：" + html);
        System.out.println("替换后的HTML是：" + html2);
    }

    @Test
    public void TestImageWidth3() {
        String html = "<img alt=\"undefined\" height=\"472.2826086956522\" src=\"https://cbu01.alicdn.com/img/ibank/2016/220/532/3459235022_1732048805.jpg\" width=\"790\" style=\"border: none; visibility: visible; vertical-align: bottom; max-width: 790px; color: rgb(51, 51, 51); font-family: &quot;Hiragino Sans GB&quot;, Tahoma, Arial, 宋体, sans-serif; font-size: 12px; white-space: normal; background-color: rgb(255, 255, 255); zoom: 1;\">";
        String html2 = HtmlUtils.imageWidth(html);
        System.out.println("原始的HTML是：" + html);
        System.out.println("替换后的HTML是：" + html2);
    }

}
