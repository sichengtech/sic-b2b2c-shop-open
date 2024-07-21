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
package com.sicheng.member.trade.web;

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.trade.service.TradeCartService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeCart")
public class TradeCartController extends BaseController {

    @Autowired
    private TradeCartService tradeCartService;

    /**
     * 进入列表页
     *
     * @param tradeCart 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(TradeCart tradeCart, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeCart.setUId(userMember.getUId());
        List<TradeCart> tradeCartList = tradeCartService.selectByWhere(new Wrapper(tradeCart));
        TradeCart.fillStore(tradeCartList);
        TradeCart.fillProductSpu(tradeCartList);
        TradeCart.fillProductSku(tradeCartList);
        Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
        for (TradeCart cart : tradeCartList) {
            if (!cartMap.isEmpty()) {
                if (cartMap.get(cart.getStore().getName()) != null) {
                    cartMap.get(cart.getStore().getName()).add(cart);
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(cart.getStore().getName(), list);
                }
            } else {
                List<TradeCart> list = new ArrayList<>();
                list.add(cart);
                cartMap.put(cart.getStore().getName(), list);
            }
        }
        model.addAttribute("cartMap", cartMap);
        return "member/trade/tradeCartList";
    }

    /**
     * 删除
     *
     * @param tradeCart          实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(TradeCart tradeCart, RedirectAttributes redirectAttributes) {
        String isBatch = R.get("isBatch");
        if ("1".equals(isBatch)) {
            String[] cartIds = R.get("cartIds").split(",");
            List<Object> cartList = new ArrayList<>();
            for (String cartId : cartIds) {
                cartList.add(cartId);
            }
            tradeCartService.deleteByIdIn(cartList);
        } else {
            //用户id
            UserMember userMember = SsoUtils.getUserMain().getUserMember();
            tradeCart.setUId(userMember.getUId());
            tradeCartService.deleteByWhere(new Wrapper(tradeCart));
        }
        addMessage(redirectAttributes, FYUtils.fy("删除成功"));
        return "redirect:" + frontPath + "/trade/cart/list.htm?repage";
    }

    /**
     * 编辑数量
     *
     * @param tradeCart          实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "editCount")
    public String editCount(TradeCart tradeCart, RedirectAttributes redirectAttributes) {
        String stat = "0";
        if (tradeCart == null || tradeCart.getCartId() == null || tradeCart.getCount() == null) {
            return stat;
        }
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        TradeCart tradeCart2 = new TradeCart();
        tradeCart2.setUId(userMember.getUId());
        tradeCart2.setCartId(tradeCart.getCartId());
        tradeCartService.updateByWhereSelective(tradeCart, new Wrapper(tradeCart2));
        stat = "1";
        return stat;
    }
}