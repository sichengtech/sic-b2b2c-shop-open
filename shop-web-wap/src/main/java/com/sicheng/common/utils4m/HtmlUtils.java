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
package com.sicheng.common.utils4m;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>标题: 商品详情处理工具  </p>
 * <p>描述: 商品详情处理工具  </p>
 * <p>使用示例: </P>
 * <pre>
 *    Window win = new Window(parent);//请作者手动完善此信息
 *    win.show();
 * </pre>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2019-11-27 17:33
 *
 * <p>重要修改历史记录1: xxxx  。修改人：xx</p>
 * <p>重要修改历史记录2: xxxx  。修改人：xx</p>
 */
@SuppressWarnings("ALL")
public class HtmlUtils {

    /**
     * 方法说明：HTML过滤，针对商品详情
     *
     * 商品详情中的内容是富文本编辑器编辑的，也可能是从其它平台copy来的。
     * 需要处理商品详情中的图片的宽度，设置为width=100%，用以适应移动端.
     *
     * @param html 商品详情的内容，是富文本
     * @return java.lang.String
     * @author zhalei
     * @version 2019-11-27 17:36
     */
    public static String imageWidth(String html) {
        if (html == null) {
            return null;
        }

        //解析html片段
        Document docContent = Jsoup.parse(html);
        //抽取正文中的图片
        Elements imgs = docContent.select("img");
        //logger.debug("商品id={},商品详情有图片{}张", pid, imgs.size());
        for (int j = 0; j < imgs.size(); j++) {
            Element e = imgs.get(j);
            String img_src = e.attr("src");
            //删除无用的属性
            e.removeAttr("href");//有一些图片上有指向外站的超连接要去掉
            e.removeAttr("width");//删除宽度
            e.removeAttr("height");//删除高度
            e.removeAttr("class");
            e.removeAttr("style");
            e.removeAttr("alt");
            e.removeAttr("title");
            e.removeAttr("align");
            e.removeAttr("type");
            //为图片设置属性
            e.attr("src", img_src);//设置图片的src
            e.attr("style", "max-width:100%; width: auto; height: auto");//这是前端要求的样式
        }

        //文章的正文的HTML（已替换完图片的url）
        String contentHtml = docContent.body().html();
        return contentHtml;
    }

    /**
     * 方法说明：HTML过滤，针对文章详情
     * 文章详情过滤掉图片的宽度设置，过滤掉文本p span标签的宽度设置
     *
     * @param html
     * @return java.lang.String
     * @author zhalei
     * @version 2019-11-27 20:08
     */
    public static String article(String html) {

        if (html == null) {
            return null;
        }

        String html2 = HtmlUtils.imageWidth(html);

        //解析html片段
        Document docContent = Jsoup.parse(html2);
        Elements imgs = docContent.select("p");
        for (int j = 0; j < imgs.size(); j++) {
            Element e = imgs.get(j);
            //删除无用的属性
            e.removeAttr("href");//有一些图片上有指向外站的超连接要去掉
            e.removeAttr("width");//删除宽度
            e.removeAttr("height");//删除高度
            e.removeAttr("class");
            e.removeAttr("style");
            e.removeAttr("alt");
            e.removeAttr("title");
            e.removeAttr("align");
            e.removeAttr("type");
        }
        Elements imgs2 = docContent.select("span");
        for (int j = 0; j < imgs2.size(); j++) {
            Element e = imgs2.get(j);
            //删除无用的属性
            e.removeAttr("href");//有一些图片上有指向外站的超连接要去掉
            e.removeAttr("width");//删除宽度
            e.removeAttr("height");//删除高度
            e.removeAttr("class");
            e.removeAttr("style");
            e.removeAttr("alt");
            e.removeAttr("title");
            e.removeAttr("align");
            e.removeAttr("type");
        }
        String contentHtml = docContent.body().html();
        return contentHtml;
    }
}
