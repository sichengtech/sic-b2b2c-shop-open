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

import com.sicheng.admin.product.entity.ProductParam;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductParamService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductParamFunction函数
 * </p>
 * <p>
 * 描述: 根据参数id获取商品参数
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductParamFunction implements Function {

    public static final String PARAMID = "paramId";

    public ProductParam call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long paramId = TagUtils.getLong(tagParamMap, PARAMID);
        if (paramId == null) {
            return null;
        }
        ProductParam productParam = new ProductParam();
        productParam.setParamId(paramId);
        //是否显示：1是，0否
        productParam.setIsDisplay("1");
        ProductParamService service = SpringContextHolder.getBean(ProductParamService.class);
        ProductParam param = service.selectOne(new Wrapper(productParam));
        return param;
    }
}