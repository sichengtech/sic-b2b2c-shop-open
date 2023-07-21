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

import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductBrandService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductBrandFunction函数
 * </p>
 * <p>
 * 描述: 根据品牌id或name获取品牌
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author 范秀秀
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductBrandFunction implements Function {

    public static final String BID = "bid";
    public static final String NAME = "name";

    public ProductBrand call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long bid = TagUtils.getLong(tagParamMap, BID);
        String name = TagUtils.getString(tagParamMap, NAME);
        if (bid == null && StringUtils.isBlank(name)) {
            return null;
        }
//			ProductBrand productBrand=new ProductBrand();
//			if(bid!=null){
//				productBrand.setBrandId(bid);
//			}
//			if(StringUtils.isNotBlank(name)){
//				productBrand.setName(name);
//			}
//			//审核状态，0待审、1通过、2未通过
//			productBrand.setStatus("1");
//			ProductBrandService service=SpringContextHolder.getBean(ProductBrandService.class);
//			//执行业务，查询出产品列表
//			ProductBrand brand=service.selectOne(new Wrapper(productBrand));
//			return brand;

        Wrapper w = new Wrapper();
        if (bid != null) {
            w.and("brand_id=", bid);
        }
        if (StringUtils.isNotBlank(name)) {
            w.and("name like %?%", bid);
        }
        //审核状态，0待审、1通过、2未通过
        w.and("status=", "1");
        ProductBrandService service = SpringContextHolder.getBean(ProductBrandService.class);
        //执行业务，查询出产品列表
        ProductBrand brand = service.selectOne(w);
        return brand;
    }
}