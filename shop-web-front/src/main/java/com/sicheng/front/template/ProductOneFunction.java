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

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.product.service.SolrProductService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductOneFunction函数
 * </p>
 * <p>
 * 描述: 根据pid获取一个商品
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductOneFunction implements Function {

    public static final String PID = "pid";

    public SolrProduct call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long pid = TagUtils.getLong(tagParamMap, PID);
        if (pid == null) {
            return null;
        }
        ProductSpuService productSpuservice = SpringContextHolder.getBean(ProductSpuService.class);
        SolrProductService service = SpringContextHolder.getBean(SolrProductService.class);
        //执行业务，查询出产品列表
        SolrProduct solrProduct = service.selectOne(new Wrapper().and("p_id=", pid));
        ProductSpu productSpu = productSpuservice.selectOne(new Wrapper().and("p_id=", pid));
        if (solrProduct != null) {
            solrProduct.setPurchasingAmount(productSpuservice.getPurchasingAmount(productSpu));
        }
        return solrProduct;
    }

}