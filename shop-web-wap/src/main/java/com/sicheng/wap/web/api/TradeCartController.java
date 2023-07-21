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
package com.sicheng.wap.web.api;

import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.ProductSpuService;
import com.sicheng.wap.service.TradeCartService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: TradeController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fanxiuxiu
 * @version 2017年12月18日 下午2:39:50
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class TradeCartController extends BaseController {

    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private ProductSpuService productSpuService;

    /**
     * 加入购物车
     *
     * @param pid    商品id
     * @param skuMsg sku信息(skuId-数量，shuId-数量，...)
     * @param type   商品类型(1零售，2批发)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/cart/save")
    public Map<String, Object> tradeCartSave(String pid, String skuMsg, String type) {
        List<String> errorList = new ArrayList<>();
        String message = FYUtils.fy("商品id不能为空");
        if (StringUtils.isBlank(pid)) {
            errorList.add(message);
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            message = FYUtils.fy("商品id必须是数字");
            errorList.add(message);
        }
        if (StringUtils.isBlank(skuMsg)) {
            message = FYUtils.fy("商品规格和数量不能为空");
            errorList.add(message);
        }
        if (StringUtils.isBlank(type)) {
            message = FYUtils.fy("商品类型不能为空");
            errorList.add(message);
        }
        message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        Long pId = Long.parseLong(pid);
        //用户
        UserMember userMember = AppTokenUtils.findUser().getUserMember();
        String[] skuMsgs = skuMsg.split(",");
        String skuMsgNew = "";
        List<TradeCart> tradeCartOldList = new ArrayList<>();
        for (int i = 0; i < skuMsgs.length; i++) {
            String[] skus = skuMsgs[i].split("-");
            if (skus.length < 2) {
                continue;
            }
            String skuIdStr = skus[0];
            String countStr = skus[1];
            if (!StringUtils.isNumeric(skuIdStr) || !StringUtils.isNumeric(countStr)) {
                continue;
            }
            Long skuId = Long.parseLong(skuIdStr);
            //如果当前商品已经加入过购物车，就添加购物车中的数量
            TradeCart tradeCartOld = tradeCartService.selectOne(new Wrapper().and("p_id=", pid).and("sku_id=", skuId).and("u_id=", userMember.getUId()));
            if (tradeCartOld == null) {
                skuMsgNew += skuMsgs[i] + ",";
                continue;
            }
            Integer count = tradeCartOld.getCount() == null ? 0 : tradeCartOld.getCount();
            TradeCart tradeCartOld2 = new TradeCart();
            Integer count2 = count + Integer.parseInt(countStr);
            tradeCartOld2.setCartId(tradeCartOld.getCartId());
            tradeCartOld2.setCount(count2);
            ProductSpu productSpu = tradeCartOld.getProductSpu();
            ProductSku productSku = tradeCartOld.getProductSku();
            if (productSpu == null || productSku == null) {
                continue;
            }
            if ("1".equals(productSpu.getType())) {
                tradeCartOld2.setPriceSum(productSku.getPrice().multiply(new BigDecimal(count2)));
            } else {
                BigDecimal price = productSpuService.calculatePrice(pId, count2);
                tradeCartOld2.setPriceSum(price.multiply(new BigDecimal(count2)));
            }
            tradeCartOldList.add(tradeCartOld2);
        }

        try {
            //加入过购物车就添加数量
            if (!tradeCartOldList.isEmpty()) {
                tradeCartService.updateSelectiveBatch(tradeCartOldList);
            }
            if (StringUtils.isNotBlank(skuMsgNew)) {
                //没有加入过购物车，就插入数据
                List<TradeCart> tradeCartList = tradeCartService.selectBySkus(userMember, pId, skuMsgNew, type);
                if (tradeCartList.isEmpty()) {
                    message = FYUtils.fy("加入购物车失败");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
                }
                tradeCartService.insertSelectiveBatch(tradeCartList);
            }
            message = FYUtils.fy("加入购物车成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("加入购物车异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }

    }

    /**
     * 获取购物车数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/cart/list")
    public Map<String, Object> tradeCartList() {
        //用户id
        TradeCart tradeCart = new TradeCart();
        UserMain userMain = AppTokenUtils.findUser();
        tradeCart.setUId(userMain.getUId());//属主检查
        tradeCart.setIsValid("1");//是否有效，0否、1是
        try {
            List<TradeCart> tradeCartList = tradeCartService.selectByWhere(new Wrapper(tradeCart));
            TradeCart.fillProductSku(tradeCartList);
            TradeCart.fillProductSpu(tradeCartList);
            TradeCart.fillStore(tradeCartList);
            Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
            for (TradeCart cart : tradeCartList) {
                if (cart == null) {
                    continue;
                }
                if (cart.getStore() == null) {
                    cart.setIsOffShelf(true);
                }
                ProductSpu productSpu = cart.getProductSpu();
                if (productSpu == null || !"50".equals(productSpu.getStatus()) || StringUtils.isBlank(productSpu.getType())) {
                    cart.setIsOffShelf(true);
                }
                ProductSku productSku = cart.getProductSku();
                if (productSku == null) {
                    cart.setIsOffShelf(true);
                }
                if (productSpu != null && !"1".equals(productSpu.getType())) {
                    BigDecimal price = productSpuService.calculatePrice(productSpu.getPId(), cart.getCount());
                    cart.setPrice(price);
                }
                if (productSku != null && productSpu != null && "1".equals(productSpu.getType())) {
                    cart.setPrice(productSku.getPrice());
                }
                String storeName = "";
                if (cart.getStore() != null) {
                    storeName = cart.getStore().getName();
                }
                String key = storeName + "-" + cart.getStoreId();
                if (cartMap.get(key) != null) {
                    cartMap.get(key).add(cart);
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(key, list);
                }
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询购物车数据成功"), cartMap, null);
        } catch (Exception e) {
            logger.error("查询购物车数据异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 删除购物车数据
     *
     * @param isAll   是否删除全部，0否、1是
     * @param cartIds 多个购物车id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/cart/delete")
    public Map<String, Object> tradeCartDelete(String isAll, String cartIds) {
        List<String> errorList = new ArrayList<>();
		/*if(StringUtils.isBlank(isAll)){
			errorList.add("是否删除全部isAll不能为空");
		}*/
        if ("0".equals(isAll) && StringUtils.isBlank(cartIds)) {
            errorList.add(FYUtils.fy("购物车id不能为空"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            if ("1".equals(isAll)) {
                //用户id
                TradeCart tradeCart = new TradeCart();
                UserMember userMember = AppTokenUtils.findUser().getUserMember();
                tradeCart.setUId(userMember.getUId());//属主检查
                tradeCartService.deleteByWhere(new Wrapper(tradeCart));
                message = FYUtils.fy("删除成功");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
            }
            String[] cartIdss = cartIds.split(",");
            List<Object> cartList = new ArrayList<>();
            for (String cartId : cartIdss) {
                cartList.add(cartId);
            }
            tradeCartService.deleteByIdIn(cartList);
            message = FYUtils.fy("删除成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("查询购物车数据异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 更新购物车的商品数量
     *
     * @param count  数量
     * @param cartId 购物车id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/cart/updateCount")
    public Map<String, Object> tradeCartUpdateCount(String count, String cartId) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(count)) {
            errorList.add(FYUtils.fy("数量不能为空"));
        }
        if (StringUtils.isNotBlank(count) && StringUtils.isBlank(count)) {
            errorList.add(FYUtils.fy("数量只能为数字"));
        }
        if (StringUtils.isBlank(cartId)) {
            errorList.add(FYUtils.fy("购物车id不能为空"));
        }
        if (StringUtils.isNotBlank(cartId) && StringUtils.isBlank(cartId)) {
            errorList.add(FYUtils.fy("购物车id只能为数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        UserMember userMember = AppTokenUtils.findUser().getUserMember();
        TradeCart tradeCart = new TradeCart();
        tradeCart.setCount(Integer.parseInt(count));
        TradeCart entity = new TradeCart();
        entity.setUId(userMember.getUId());//属主检查
        entity.setCartId(Long.parseLong(cartId));
        try {
            tradeCartService.updateByWhereSelective(tradeCart, new Wrapper(entity));
            message = FYUtils.fy("修改成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("更新购物车的商品数量异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }
}
