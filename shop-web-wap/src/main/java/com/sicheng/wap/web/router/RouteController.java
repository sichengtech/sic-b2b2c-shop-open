/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 * http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.wap.web.router;

import com.sicheng.common.config.Global;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * <p>标题: front系统的入口页面(路由页面)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月17日 下午3:28:05
 */
@Controller
@RequestMapping(value = "${wapPath}")
public class RouteController extends BaseController {

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("ctx", R.getCtx());
        model.addAttribute("ctxf", R.getCtx() + Global.getFrontPath());
        model.addAttribute("ctxa", R.getCtx() + Global.getAdminPath());
        model.addAttribute("ctxs", R.getCtx() + Global.getSellerPath());
        model.addAttribute("ctxm", R.getCtx() + Global.getMemberPath());
        model.addAttribute("ctxsso", R.getCtx() + Global.getSsoPath());
        model.addAttribute("ctxw", R.getCtx() + Global.getWapPath());
        model.addAttribute("ctxu", "/upload");

        model.addAttribute("ctxStatic", "/static/static");
        model.addAttribute("ctxfs", "/upload" + Global.getConfig("filestorage.dir"));
    }

    /**
     *  进入微信系统的首页
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("footHighlight", "indexHighlight");//首页高亮
        return "/index";
    }

    /**
     *  进入微信系统的分类首页
     */
    @RequestMapping(value = "/categoryIndex")
    public String categoryIndex(Model model) {
        model.addAttribute("footHighlight", "categoryIndexHighlight");//分类高亮
        return "/categoryIndex";
    }

    /**
     *  进入微信系统的注册页面
     */
    @RequestMapping(value = "/user/register/form")
    public String userRegister() {
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());
//		if(openId==null){
//			String flag="2";//入口类型
//			WeiXinUtils.weixinAccredit(R.getResponse(), flag);
//		}
        return "/register";
    }

    /**
     *  进入微信系统的登录页面 
     */
    @RequestMapping(value = "/user/login/form")
    public String userLogin() {
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());
//		if(openId==null){
//			String flag="3";//入口类型
//			WeiXinUtils.weixinAccredit(R.getResponse(),flag);
//		}
        return "/login";
    }

    /**
     *  进入微信系统的登录页面 
     */
    @RequestMapping(value = "/user/forgetPwd/form")
    public String userForgetPwd() {
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());
//		if(openId==null){
//			String flag="4";//入口类型
//			WeiXinUtils.weixinAccredit(R.getResponse(), flag);
//		}
        return "/forgetPwd";
    }

    /**
     *  进入微信系统的用户信息表单页面 
     */
    @RequestMapping(value = "/user/userInfo/form")
    public String userInfo() {
        return "/userInfo";
    }

    /**
     *  进入微信系统的用户中心页面 
     *  @param model model
     */
    @RequestMapping(value = "/user/userCentral")
    public String userCentral(Model model) {
        model.addAttribute("footHighlight", "userCentralHighlight");//个人中心高亮
        return "/userCentral";
    }

    /**
     *  进入微信系统的收货地址列表页面
     */
    @RequestMapping(value = "/user/address/list")
    public String addressList() {
        return "/addressList";
    }

    /**
     *  进入微信系统的收货地址表单页面
     */
    @RequestMapping(value = "/user/address/form")
    public String addressForm() {
        return "/addressForm";
    }

    /**
     *  进入微信系统的我的消息页面
     */
    @RequestMapping(value = "/user/userMessage/list")
    public String userMessage() {
        return "/userMessage";
    }

    /**
     *  进入微信系统的店铺收藏页面 
     */
    @RequestMapping(value = "/user/collectionStore/list")
    public String userCollectionStoreList() {
        return "/userCollectionStore";
    }

    /**
     *  进入微信系统的商品收藏页面 
     *  @return
     */
    @RequestMapping(value = "/user/collectionProduct/list")
    public String usercollectionProductList() {
        return "/userCollectionProduct";
    }

    /**
     * 进入微信商城的商品详情页
     */
    @RequestMapping(value = "/product/detail")
    public String productDetail() {
        return "/productDetail";
    }

    /**
     * 进入微信商城的商品列表页
     */
    @RequestMapping(value = "/product/list")
    public String productList() {
        return "/productList";
    }

    /**
     * 进入微信商城的商品不存在页
     */
    @RequestMapping(value = "/product/error")
    public String productError() {
        return "/productError";
    }

    /**
     * 进入微信商城的店铺主页
     */
    @RequestMapping(value = "/store/index")
    public String storeIndex(Model model) {
        model.addAttribute("storeHighlight", "storeIndexHighlight");//店铺主页高亮
        return "/storeIndex";
    }

    /**
     * 进入微信商城店铺不存在页面
     */
    @RequestMapping(value = "/store/error")
    public String storeError() {
        return "/storeError";
    }

    /**
     * 进入微信商城的店铺文章列表页面
     */
    @RequestMapping(value = "/store/article/list")
    public String storeArticleList(Model model) {
        model.addAttribute("storeHighlight", "storeArticleHighlight");//店铺动态高亮
        return "/storeArticleList";
    }

    /**
     * 进入微信商城的店铺文章详情页面
     */
    @RequestMapping(value = "/store/article/detail")
    public String storeArticleDetail(Model model) {
        model.addAttribute("storeHighlight", "storeArticleHighlight");//店铺动态高亮
        return "/storeArticleDetail";
    }

    /**
     * 进入微信商城的商品咨询列表
     */
    @RequestMapping(value = "/trade/consultation/list")
    public String tradeConsultationList() {
        return "/tradeConsultationList";
    }

    /**
     * 进入微信商城的商品咨询表单页
     */
    @RequestMapping(value = "/trade/consultation/form")
    public String tradeConsultationForm() {
        return "/tradeConsultationForm";
    }

    /**
     * 进入微信商城我的咨询列表页面
     */
    @RequestMapping(value = "/trade/myConsultation/list")
    public String myTradeConsultationList() {
        return "/tradeMyConsultationList";
    }

    /**
     * 进入微信商城的商品评价列表页
     */
    @RequestMapping(value = "/trade/comment/list")
    public String tradeCommentList() {
        return "/tradeCommentList";
    }

    /**
     * 进入微信商城的我的评价列表页
     */
    @RequestMapping(value = "/trade/myComment/list")
    public String tradeMyCommentList() {
        return "/tradeMyCommentList";
    }

    /**
     * 进入微信商城的物流信息页
     */
    @RequestMapping(value = "/trade/logistics/info")
    public String trderLogisticsInfo() {
        return "/tradeLogisticsInfo";
    }

    /**
     * 进入微信商城的购物车页
     */
    @RequestMapping(value = "/trade/cart/list")
    public String tradeCartList(Model model) {
        model.addAttribute("footHighlight", "cartHighlight");//分类高亮
        return "/tradeCartList";
    }

    /**
     * 进入微信商城的确认订单页
     */
    @RequestMapping(value = "/trade/order/confirmOrder")
    public String tradeConfirmOrder() {
        return "/tradeConfirmOrder";
    }

    /**
     * 进入微信商城的订单列表页面
     */
    @RequestMapping(value = "/trade/order/list")
    public String tradeOrderList() {
        return "/tradeOrderList";
    }

    /**
     * 进入微信商城的订单详情页面
     */
    @RequestMapping(value = "/trade/order/detail")
    public String tradeOrderDetail() {
        return "/tradeOrderDetail";
    }

    /**
     * 进入微信商城的发票列表页面
     */
    @RequestMapping(value = "/trade/deliver/list")
    public String tradeDeliverList() {
        return "/tradeDeliverList";
    }

    /**
     * 进入微信商城的发票列表页面
     */
    @RequestMapping(value = "/trade/deliver/save")
    public String tradeDeliverSave() {
        return "/tradeDeliverForm";
    }

    /**
     * 进入微信商城的发票表单页面
     */
    @RequestMapping(value = "/trade/deliver/edit")
    public String tradeDeliverEdit() {
        return "/tradeDeliverForm";
    }

    /**
     * 进入微信商城的评价表单页面
     */
    @RequestMapping(value = "/trade/comment/form")
    public String tradeCommentForm() {
        return "/tradeCommentForm";
    }

    /**
     * 网络请求发生异常，返回错误页面
     */
    @RequestMapping(value = "/webRequest/error")
    public String webRequestError() {
        return "/webRequestError";
    }
}
