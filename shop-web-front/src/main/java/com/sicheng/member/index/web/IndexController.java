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
package com.sicheng.member.index.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.member.service.MemberCollectionProductService;
import com.sicheng.seller.member.service.MemberCollectionStoreService;
import com.sicheng.seller.trade.service.TradeCartService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>标题: memeber会员中心首页</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月17日 下午3:28:05
 */
@Controller
@RequestMapping(value = "${memberPath}/index")
public class IndexController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private MemberCollectionProductService memberCollectionProductService;

    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;

    @Autowired
    private TradeCartService tradeCartService;

    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;

    /**
     * 进入member会员中心主页面，这是member的主入口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //交易提醒
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setUId(userMember.getUId());
        Wrapper wrapper = new Wrapper();
        wrapper.orderBy("a.order_status");
        wrapper.setEntity(tradeOrder);
        Page<TradeOrder> pageTradeOrder = new Page<TradeOrder>(request, response);
        pageTradeOrder.setPageSize(3);
        pageTradeOrder = tradeOrderService.selectByWhere(pageTradeOrder, wrapper);
        //order_status 订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        //is_return_status 是否退货退款,0否、1退货退款、2退款
        int status10Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 10).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status20Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 20).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status30Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 30).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status40Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 40).and("u_id=", userMember.getUId()));
        int status50Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 50).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status60Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 60).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int isReturnStatusCount1 = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 1).and("u_id=", userMember.getUId()));
        int isReturnStatusCoun2 = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 2).and("u_id=", userMember.getUId()));
        model.addAttribute("pageTradeOrder", pageTradeOrder);
        model.addAttribute("status10Count", status10Count);
        model.addAttribute("status20Count", status20Count);
        model.addAttribute("status30Count", status30Count);
        model.addAttribute("status40Count", status40Count);
        model.addAttribute("status50Count", status50Count);
        model.addAttribute("status60Count", status60Count);
        model.addAttribute("isReturnStatusCount1", isReturnStatusCount1);
        model.addAttribute("isReturnStatusCoun2", isReturnStatusCoun2);
        //购物车
        TradeCart tradeCart = new TradeCart();
        tradeCart.setUId(userMember.getUId());
        tradeCart.setIsValid("1");//是否有效，0否、1是
        List<TradeCart> list = tradeCartService.selectByWhere(new Wrapper(tradeCart));
        List<TradeCart> tradeCartList = Lists.newArrayList();
        for (TradeCart cart : list) {
            //去除没有店铺的购物车的记录
            if (cart == null || cart.getStore() == null) {
                continue;
            }
            //去除没有spu、下架的商品、没有销售类型的商品的购物车的记录
            ProductSpu productSpu = cart.getProductSpu();
            if (productSpu == null || !"50".equals(productSpu.getStatus()) || StringUtils.isBlank(productSpu.getType())) {
                continue;
            }
            ProductSku productSku = cart.getProductSku();
            //去除没有sku的商品的购物车的记录
            if (productSku == null) {
                continue;
            }
            tradeCartList.add(cart);
        }
        model.addAttribute("tradeCartList", tradeCartList);
        //我收藏的商品
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMember.getUId());
        Wrapper memberCollectionProductwrapper = new Wrapper();
        memberCollectionProductwrapper.setEntity(memberCollectionProduct);
        memberCollectionProductwrapper.orderBy("a.month_sales DESC");//按月销，降序
        Page<MemberCollectionProduct> pageMemberCollectionProduct = new Page<MemberCollectionProduct>(request, response);
        pageMemberCollectionProduct.setPageSize(8);
        pageMemberCollectionProduct = memberCollectionProductService.selectByWhere(pageMemberCollectionProduct, memberCollectionProductwrapper);
        model.addAttribute("pageMemberCollectionProduct", pageMemberCollectionProduct);
        //我收藏的店铺
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMember.getUId());
        Wrapper memberCollectionStorewrapper = new Wrapper();
        memberCollectionStorewrapper.setEntity(memberCollectionStore);
        Page<MemberCollectionStore> pageMemberCollectionStore = new Page<MemberCollectionStore>(request, response);
        pageMemberCollectionStore.setPageSize(10);
        pageMemberCollectionStore = memberCollectionStoreService.selectByWhere(pageMemberCollectionStore, memberCollectionStorewrapper);
        model.addAttribute("pageMemberCollectionStore", pageMemberCollectionStore);
        //菜单高亮
        MemberMenuInterceptor.menuHighLighting("index");//一级菜单高亮
        return "member/index";
    }
}