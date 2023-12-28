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

import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.product.entity.ProductSectionPrice;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.entity.ProductSpuAnalyze;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: UserCollectionController</p>
 * <p>描述: 收藏 </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月18日 下午3:10:04
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class UserCollectionController extends BaseController {

    @Autowired
    private MemberCollectionProductService memberCollectionProductService;
    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSectionPriceService productSectionPriceService;
    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;

    /**
     * 商品收藏（列表）
     *
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionProduct/list")
    @ResponseBody
    public Map<String, Object> userCollectionProductPage() {
        UserMain userMain = AppTokenUtils.findUser();
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());//属主检查
        try {
            Page<MemberCollectionProduct> page = Page.newPage();
            page = memberCollectionProductService.selectByWhere(page, new Wrapper(memberCollectionProduct));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("商品收藏数据获取成功"), page.getList(), page);
        } catch (Exception e) {
            logger.error("商品收藏数据参数错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 收藏商品
     *
     * @param pid 商品id
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionProduct/save")
    @ResponseBody
    public Map<String, Object> userCollectionProductSave(String pid) {
        if (StringUtils.isBlank(pid)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("商品Id不能为空"), null, null);
        }
        if (!StringUtils.isNumeric(pid)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("商品Id只能为数字"), null, null);
        }
        UserMain userMain = AppTokenUtils.findUser();
        try {
            ProductSpu productSpu = productSpuService.selectById(Long.parseLong(pid));
            if (productSpu == null) {
                //商品不存在
                return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("商品不存在"), null, null);
            }
            MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
            memberCollectionProduct.setUId(userMain.getUId());//属主检查
            memberCollectionProduct.setPId(Long.parseLong(pid));
            List<MemberCollectionProduct> memberCollectionProductList = memberCollectionProductService.selectByWhere(new Wrapper(memberCollectionProduct));
            if (!memberCollectionProductList.isEmpty()) {
                //商品已收藏
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("商品已收藏"), null, null);
            }
            memberCollectionProduct.setImage(productSpu.getImage());
            memberCollectionProduct.setPictureName(productSpu.getName());
            if ("10".equals(productSpu.getStatus()) || "20".equals(productSpu.getStatus()) || "30".equals(productSpu.getStatus()) || "40".equals(productSpu.getStatus())) {
                //10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品  下架
                memberCollectionProduct.setStatus("0");
            }
            if ("50".equals(productSpu.getStatus())) {
                //50在售商品  上架
                memberCollectionProduct.setStatus("1");
            }
            if ("1".equals(productSpu.getType())) {
                //零售型
                Wrapper wrapper = new Wrapper();
                ProductSku sku = new ProductSku();
                sku.setPId(Long.parseLong(pid));
                wrapper.setEntity(sku);
                wrapper.orderBy("a.price ASC");
                List<ProductSku> productSkuList = productSkuService.selectByWhere(wrapper);
                if (productSkuList.isEmpty()) {
                    //商品不存在
                    return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("商品不存在"), null, null);
                } else {
                    memberCollectionProduct.setPicturePrice(productSkuList.get(0).getPrice());
                }
            }
            if ("2".equals(productSpu.getType()) || "3".equals(productSpu.getType())) {
                //批发型，零售加批发
                Wrapper wrapper = new Wrapper();
                ProductSectionPrice productSectionPrice = new ProductSectionPrice();
                productSectionPrice.setPId(Long.parseLong(pid));
                wrapper.setEntity(productSectionPrice);
                wrapper.orderBy("a.price ASC");
                List<ProductSectionPrice> productSectionPriceList = productSectionPriceService.selectByWhere(wrapper);
                if (productSectionPriceList.isEmpty()) {
                    //商品不存在
                    return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("商品不存在"), null, null);
                } else {
                    memberCollectionProduct.setPicturePrice(productSectionPriceList.get(0).getPrice());
                }
            }
            //商品月销
            ProductSpuAnalyze productSpuAnalyze = productSpuAnalyzeService.selectById(Long.parseLong(pid));//商品统计
            if (productSpuAnalyze != null) {
                memberCollectionProduct.setMonthSales(productSpuAnalyze.getMonthSales());
            }
            memberCollectionProductService.insertSelective(memberCollectionProduct);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("商品收藏成功"), memberCollectionProduct, null);
        } catch (Exception e) {
            logger.error("商品收藏参数错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 取消收藏商品
     *
     * @param collectionIds 多个收藏商品id
     * @param isAll         是否全部删除失效
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionProduct/cancel")
    @ResponseBody
    public Map<String, Object> userCollectionProductCancel(String[] collectionIds, boolean isAll) {
        if (!isAll) {
            if (collectionIds == null || collectionIds.length == 0) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("取消商品参数不正确"), null, null);
            }
        }
        UserMain userMain = AppTokenUtils.findUser();
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());//属主检查
        Wrapper wrapper = new Wrapper();
        if (!isAll) {
            List list = java.util.Arrays.asList(collectionIds);
            //单个删除
            wrapper.and("a.collection_id in", list);
        } else {
            //删除所有下架的
            memberCollectionProduct.setStatus("0");//0、下架，1、上架
        }
        wrapper.setEntity(memberCollectionProduct);
        try {
            memberCollectionProductService.deleteByWhere(wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("取消收藏商品成功"), null, null);
        } catch (Exception e) {
            logger.error("商品取消收藏错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取失效商品数量
     *
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionProduct/invalidCount")
    @ResponseBody
    public Map<String, Object> userCollectionProductInvalidCount() {
        UserMain userMain = AppTokenUtils.findUser();
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());//属主检查
        memberCollectionProduct.setStatus("0");//0、下架，1、上架
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(memberCollectionProduct);
        try {
            int count = memberCollectionProductService.countByWhere(wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取失效商品数量成功"), count, null);
        } catch (Exception e) {
            logger.error("获取失效商品数量错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 店铺收藏（列表）
     *
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionStore/list")
    @ResponseBody
    public Map<String, Object> userCollectionStorePage() {
        UserMain userMain = AppTokenUtils.findUser();
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMain.getUId());//属主检查
        try {
            Page<MemberCollectionStore> page = Page.newPage();
            page = memberCollectionStoreService.selectByWhere(page, new Wrapper(memberCollectionStore));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺收藏数据获取成功"), page.getList(), page);
        } catch (Exception e) {
            logger.error("店铺收藏数据参数错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 收藏店铺
     *
     * @param storeId 店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionStore/save")
    @ResponseBody
    public Map<String, Object> userCollectionStoreSave(String storeId) {
        if (StringUtils.isBlank(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id不能为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        UserMain userMain = AppTokenUtils.findUser();
        try {
            Store store = storeService.selectById(Long.parseLong(storeId));
            if (store == null) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("店铺不存在"), null, null);
            }
            MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
            memberCollectionStore.setStoreId(Long.parseLong(storeId));
            memberCollectionStore.setUId(userMain.getUId());//属主检查
            List<MemberCollectionStore> memberCollectionStoreList = memberCollectionStoreService.selectByWhere(new Wrapper(memberCollectionStore));
            if (!memberCollectionStoreList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺已收藏"), null, null);
            }
            memberCollectionStoreService.insertSelective(memberCollectionStore);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺收藏成功"), memberCollectionStore, null);
        } catch (Exception e) {
            logger.error("店铺收藏参数错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 取消收藏店铺
     *
     * @param collectionIds 多个收藏店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/user/collectionStore/cancel")
    @ResponseBody
    public Map<String, Object> userCollectionStoreCancel(String[] collectionIds) {
        if (collectionIds == null || collectionIds.length == 0) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("取消店铺id参数不正确"), null, null);
        }
        UserMain userMain = AppTokenUtils.findUser();
        List list = java.util.Arrays.asList(collectionIds);
        memberCollectionStoreService.deleteByIdIn(list);
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMain.getUId());//属主检查
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(memberCollectionStore);
        wrapper.and("a.collection_store_id in", list);
        try {
            memberCollectionStoreService.deleteByWhere(wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("取消收藏店铺成功"), null, null);
        } catch (Exception e) {
            logger.error("店铺删除参数错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 判断商品是否收藏
     *
     * @param pid 商品id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/collectionProduct/isCollection")
    public Map<String, Object> isCollectionProduct(String pid) {
        UserMain userMain = AppTokenUtils.findUser();
        String message = FYUtils.fy("没有收藏");
        if (userMain == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        }
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid)) {
            errorList.add(FYUtils.fy("商品id不能为空"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id只能是数字"));
        }
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());//属主检查
        memberCollectionProduct.setPId(Long.parseLong(pid));
        try {
            List<MemberCollectionProduct> memberCollectionProductList = memberCollectionProductService.selectByWhere(new Wrapper(memberCollectionProduct));
            if (!memberCollectionProductList.isEmpty()) {
                //商品已收藏
                message = FYUtils.fy("商品已收藏");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, memberCollectionProductList.get(0).getId(), null);
            }
            message = FYUtils.fy("商品没有收藏");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("查询商品是否收藏错误:" , e );
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 判断店铺是否收藏
     *
     * @param storeId 店铺id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/collectionStore/isCollection")
    public Map<String, Object> isCollectionStore(String storeId) {
        UserMain userMain = AppTokenUtils.findUser();
        String message = FYUtils.fy("没有收藏");
        if (userMain == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, false, null);
        }
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(storeId)) {
            errorList.add(FYUtils.fy("店铺Id不能为空"));
        }
        if (StringUtils.isNotBlank(storeId) && !StringUtils.isNumeric(storeId)) {
            errorList.add(FYUtils.fy("店铺Id只能为数字"));
        }
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMain.getUId());//属主检查
        memberCollectionStore.setStoreId(Long.parseLong(storeId));
        try {
            List<MemberCollectionStore> memberCollectionStoreList = memberCollectionStoreService.selectByWhere(new Wrapper(memberCollectionStore));
            if (!memberCollectionStoreList.isEmpty()) {
                //店铺已收藏
                message = FYUtils.fy("店铺已收藏");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, memberCollectionStoreList.get(0).getId(), null);
            }
            message = FYUtils.fy("店铺没有收藏");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("查询店铺是否收藏错误:" , e );
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }
}
