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
package com.sicheng.member.collect.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.seller.member.service.MemberCollectionProductService;
import com.sicheng.seller.product.service.ProductSkuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 商品收藏</p>s
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月19日 下午12:23:45
 */
@Controller
@RequestMapping(value = "${memberPath}/collect/memberCollectionProduct")
public class MemberCollectionProductController extends BaseController {

    @Autowired
    private MemberCollectionProductService memberCollectionProductService;

    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("collectionProduct");//三级菜单高亮
    }

    /**
     * 商品收藏列表
     *
     * @param memberCollectionProduct
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(MemberCollectionProduct memberCollectionProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
        memberCollectionProduct.setUId(SsoUtils.getUserMain().getUId());
        Page<MemberCollectionProduct> page = memberCollectionProductService.selectByWhere(new Page<MemberCollectionProduct>(request, response), new Wrapper(memberCollectionProduct));
        int count = memberCollectionProductService.countByWhere(new Wrapper(memberCollectionProduct));
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        return "member/collect/memberCollectionProduct";
    }

    /**
     * 删除
     *
     * @param sysMessage
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(MemberCollectionProduct memberCollectionProduct, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (memberCollectionProduct == null || memberCollectionProduct.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("收藏的商品不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        memberCollectionProduct.setUId(SsoUtils.getUserMain().getUId());
        memberCollectionProductService.deleteByWhere(new Wrapper(memberCollectionProduct));
        addMessage(redirectAttributes, FYUtils.fyParams("删除收藏商品成功！"));
        return "redirect:" + memberPath + "/collect/memberCollectionProduct/list.htm?repage";
    }

    /**
     * 加入购物车规格页面
     *
     * @param memberCollectionProduct
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "addTradeCart")
    public String addTradeCart(MemberCollectionProduct memberCollectionProduct, Model model) {
        ProductSku productSku = new ProductSku();
        productSku.setPId(memberCollectionProduct.getPId());
        List<ProductSku> productSkus = productSkuService.selectByWhere(new Wrapper(productSku));
        LinkedHashMap<String, List<String>> spec1Map = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> spec2Map = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> spec3Map = new LinkedHashMap<String, List<String>>();
        if (!productSkus.isEmpty()) {
            List<String> spec1VList = new ArrayList<>();
            List<String> spec2VList = new ArrayList<>();
            List<String> spec3VList = new ArrayList<>();
            for (int i = 0; i < productSkus.size(); i++) {
                //规格1值（组装）
                String spec1 = productSkus.get(i).getSpec1();
                String spec1V = productSkus.get(i).getSpec1V();
                if (StringUtils.isNotBlank(spec1) && StringUtils.isNotBlank(spec1V)) {
                    String[] spec1a = spec1.split("_");
                    spec1VList.add(spec1V);
                    List<String> spec1List = spec1Map.get(spec1a[1]);
                    if (spec1List == null) {
                        spec1Map.put(spec1a[1], spec1VList);
                    }
                }
                //规格2值（组装）
                String spec2 = productSkus.get(i).getSpec2();
                String spec2V = productSkus.get(i).getSpec2V();
                if (StringUtils.isNotBlank(spec2) && StringUtils.isNotBlank(spec2V)) {
                    String[] spec2a = spec2.split("_");
                    spec2VList.add(spec2V);
                    List<String> spec2List = spec2Map.get(spec2a[1]);
                    if (spec2List == null) {
                        spec1Map.put(spec2a[1], spec2VList);
                    }
                }
                //规格3值（组装）
                String spec3 = productSkus.get(i).getSpec3();
                String spec3V = productSkus.get(i).getSpec3V();
                if (StringUtils.isNotBlank(spec3) && StringUtils.isNotBlank(spec3V)) {
                    String[] spec3a = spec3.split("_");
                    spec3VList.add(spec3V);
                    List<String> spec3List = spec3Map.get(spec3a[1]);
                    if (spec3List == null) {
                        spec1Map.put(spec3a[1], spec3VList);
                    }
                }
            }
        }
        model.addAttribute("spec1Map", spec1Map);
        model.addAttribute("spec2Map", spec2Map);
        model.addAttribute("spec3Map", spec3Map);
        return "member/collect/addCartSpec";
    }
}
