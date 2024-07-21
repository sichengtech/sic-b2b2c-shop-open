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

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * <p>标题: BeetlDemo</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年5月6日 上午7:49:28
 */
public class BeetlDemo {

    /**
     * TODO(这里用一句话描述这个方法的作用)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String root = System.getProperty("user.dir") + File.separator + "src/main/webapp/";
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

//		gt.registerTag("sql.queryPage", SqlQueryPageTag.class);
//		gt.registerFunction("sql.param", new SqlParamFunction());
//		gt.registerFunction("sql.include", new SqlIncludeFunction());
//		gt.registerFunction("productList", new ProductListFunction());

        System.out.println(gt.getTemplate("/default/index.html").render());
//		System.out.println(gt.getTemplate("/demo/sqlTag.html").render());
//		System.out.println(gt.getTemplate("/demo/sqlTag_include.html").render());
//		System.out.println(gt.getTemplate("/demo/productList.html").render());
//		System.out.println(gt.getAjaxTemplate("/demo/sql/a_block.sql","select_xxxx").render());
    }
}