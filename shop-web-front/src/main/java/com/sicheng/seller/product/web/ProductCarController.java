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
package com.sicheng.seller.product.web;

import com.sicheng.admin.product.entity.ProductCar;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.front.service.ProductCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>标题: 车型</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月30日 下午11:10:28
 */
@Controller
@RequestMapping(value = "${sellerPath}/product/productCar")
public class ProductCarController extends BaseController {

    @Autowired
    private ProductCarService productCarService;

    /**
     * ajax车系三级联动
     *
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectProductCar")
    public Object ajaxCarSearch(HttpServletResponse response, String parentId, Model model) throws IOException {
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.parent_id=", parentId);
        List<ProductCar> productCarList = productCarService.selectByWhere(wrapper);
        String json = JsonMapper.getInstance().toJson(productCarList);
        R.writeJson(response, json, "UTF-8");
        return null;
    }
}