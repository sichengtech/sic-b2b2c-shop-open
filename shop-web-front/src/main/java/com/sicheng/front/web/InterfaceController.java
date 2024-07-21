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
package com.sicheng.front.web;

import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.product.entity.*;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.front.service.ProductCarService;
import com.sicheng.seller.member.service.MemberCollectionProductService;
import com.sicheng.seller.member.service.MemberCollectionStoreService;
import com.sicheng.seller.product.service.ProductSectionPriceService;
import com.sicheng.seller.product.service.ProductSkuService;
import com.sicheng.seller.product.service.ProductSpuAnalyzeService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.store.service.StoreService;
import com.sicheng.seller.trade.service.TradeCommentService;
import com.sicheng.seller.trade.service.TradeConsultationService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>标题: front系统的方法(方法)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年6月14日 上午10:19:41O
 */
@Controller
@RequestMapping(value = "${frontPath}/method")
public class InterfaceController extends BaseController {

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
    private ProductCarService productCarService;
    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;
    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private TradeConsultationService tradeConsultationService;


    private static String COLLECTION_ZERO = "0";        //收藏成功！
    private static String COLLECTION_ONE = "1";            //收藏失败:账号未登录！
    private static String COLLECTION_TWO = "2";            //收藏失败:店铺不存在！
    private static String COLLECTION_THREE = "3";        //收藏失败:商品不存在！
    private static String COLLECTION_FOUR = "4";        //收藏失败:已收藏！


    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("ctx", R.getCtx());
        model.addAttribute("ctxsso", R.getCtx() + Global.getSsoPath());
        model.addAttribute("ctxs", R.getCtx() + Global.getSellerPath());
        model.addAttribute("ctxf", R.getCtx() + Global.getFrontPath());
        model.addAttribute("ctxm", R.getCtx() + Global.getMemberPath());
        model.addAttribute("ctxStatic", "/static/static");
        model.addAttribute("ctxfs", "/upload" + Global.getConfig("filestorage.dir"));
        model.addAttribute("ctxu", "/upload");
    }

    /**
     * ajax调用获取登录对象
     *
     * @return
     */
    @RequestMapping(value = "/getUserMain")
    @ResponseBody
    public Object getUserMain() {
        UserMain userMain = SsoUtils.getUserMain();
        Map<String, String> map = new HashMap<>();
        if (userMain == null) {
            map.put("uId", null);
            map.put("loginName", null);
            map.put("headPicPath", null);
            map.put("isTypeUserPurchaser", null);
            return map;
        }
        String isTypeUserPurchaser = "false";
        if (userMain.isTypeUserPurchaser()) {
            isTypeUserPurchaser = "true";
        }
        map.put("uId", userMain.getUId().toString());
        map.put("loginName", userMain.getLoginName());
        map.put("headPicPath", userMain.getUserMember().getHeadPicPath());
        map.put("isTypeUserPurchaser", isTypeUserPurchaser);
        return JsonMapper.getInstance().toJson(map);
    }

    /**
     * 车型品牌车品报价库(jTree数据显示)
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ajaxCarSearch")
    public Object ajaxCarSearch(Long id) {
        Wrapper wrapper = new Wrapper();
        List<Long> carIds = new ArrayList<>();
        if (id == null) {
            Wrapper w = new Wrapper();
            w.and("a.parent_id=", 0);
            List<ProductCar> productCars = productCarService.selectByWhere(w);
            if (!productCars.isEmpty()) {
                for (int i = 0; i < productCars.size(); i++) {
                    carIds.add(productCars.get(i).getCarId());
                }
            }
        } else {
            carIds.add(id);
        }
        wrapper.and("a.parent_id in", carIds);
        List<ProductCar> productCars = productCarService.selectByWhere(wrapper);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < productCars.size(); i++) {
            Map<String, String> map = new TreeMap<>();
            map.put("id", productCars.get(i).getCarId().toString());
            map.put("pId", productCars.get(i).getParentId().toString());
            map.put("pIds", productCars.get(i).getParentIds());
            map.put("name", productCars.get(i).getName());
            map.put("firstLetter", productCars.get(i).getFirstLetter());
            list.add(map);
        }
        return list;
    }

    /**
     * 收藏店铺
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/collectionStore")
    public Object collectionStore() {
        Map<String, Object> map = new HashMap<>();
        UserMain userMain = SsoUtils.getUserMain();
        Long storeId = R.getLong("storeId");
        if (userMain == null) {
            //账号未登录
            map.put("status", COLLECTION_ONE);
            return map;
        }
        if (storeId == null) {
            //店铺id为空
            map.put("status", COLLECTION_TWO);
            return map;
        }
        Store store = storeService.selectById(storeId);
        if (store == null) {
            //店铺不存在
            map.put("status", COLLECTION_TWO);
            return map;
        }
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setStoreId(storeId);
        memberCollectionStore.setUId(userMain.getUId());
        List<MemberCollectionStore> memberCollectionStoreList = memberCollectionStoreService.selectByWhere(new Wrapper(memberCollectionStore));
        if (!memberCollectionStoreList.isEmpty()) {
            //已收藏
            map.put("status", COLLECTION_FOUR);
            return map;
        }
        memberCollectionStoreService.insertSelective(memberCollectionStore);
        map.put("status", COLLECTION_ZERO);
        return map;
    }

    /**
     * 收藏商品
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/collectionProduct")
    public Object collectionProduct() {
        Map<String, Object> map = new HashMap<>();
        UserMain userMain = SsoUtils.getUserMain();
        Long pId = R.getLong("pId");
        if (userMain == null) {
            //账号未登录
            map.put("status", COLLECTION_ONE);
            return map;
        }
        if (pId == null) {
            //商品id为空
            map.put("status", COLLECTION_THREE);
            return map;
        }
        ProductSpu productSpu = productSpuService.selectById(pId);
        if (productSpu == null) {
            //商品不存在
            map.put("status", COLLECTION_THREE);
            return map;
        }
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());
        memberCollectionProduct.setPId(pId);
        List<MemberCollectionProduct> memberCollectionProductList = memberCollectionProductService.selectByWhere(new Wrapper(memberCollectionProduct));
        if (!memberCollectionProductList.isEmpty()) {
            //商品已收藏
            map.put("status", COLLECTION_FOUR);
            return map;
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
            sku.setPId(pId);
            wrapper.setEntity(sku);
            wrapper.orderBy("a.price ASC");
            List<ProductSku> productSkuList = productSkuService.selectByWhere(wrapper);
            if (productSkuList.isEmpty()) {
                //商品不存在
                map.put("status", COLLECTION_THREE);
                return map;
            } else {
                memberCollectionProduct.setPicturePrice(productSkuList.get(0).getPrice());
            }
        }
        if ("2".equals(productSpu.getType()) || "3".equals(productSpu.getType())) {
            //批发型，零售加批发
            Wrapper wrapper = new Wrapper();
            ProductSectionPrice productSectionPrice = new ProductSectionPrice();
            productSectionPrice.setPId(pId);
            wrapper.setEntity(productSectionPrice);
            wrapper.orderBy("a.price ASC");
            List<ProductSectionPrice> productSectionPriceList = productSectionPriceService.selectByWhere(wrapper);
            if (productSectionPriceList.isEmpty()) {
                //商品不存在
                map.put("status", COLLECTION_THREE);
                return map;
            } else {
                memberCollectionProduct.setPicturePrice(productSectionPriceList.get(0).getPrice());
            }
        }
        //商品月销
        ProductSpuAnalyze productSpuAnalyze = productSpuAnalyzeService.selectById(pId);//商品统计
        if (productSpuAnalyze != null) {
            memberCollectionProduct.setMonthSales(productSpuAnalyze.getMonthSales());
        }
        memberCollectionProductService.insertSelective(memberCollectionProduct);
        map.put("status", COLLECTION_ZERO);
        return map;
    }

    /**
     * ajax调用获取商品批发价
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/getPrice")
    public Object getPrice() {
        UserMain userMain = SsoUtils.getUserMain();
        Map<String, BigDecimal> map = new HashMap<>();
        Long pId = R.getLong("pId");
        if (pId == null || !userMain.isTypeUserPurchaser()) {
            return JsonMapper.getInstance().toJson(map);
        }
        ProductSectionPrice productSectionPrice = new ProductSectionPrice();
        productSectionPrice.setPId(pId);
        List<ProductSectionPrice> productSectionPriceServiceList = productSectionPriceService.selectByWhere(new Wrapper(productSectionPrice));
        if (productSectionPriceServiceList.isEmpty()) {
            return JsonMapper.getInstance().toJson(map);
        }
        BigDecimal[] prices = new BigDecimal[productSectionPriceServiceList.size()];
        for (int i = 0; i < productSectionPriceServiceList.size(); i++) {
            prices[i] = productSectionPriceServiceList.get(i).getPrice();
        }
        BigDecimal max = prices[0];
        BigDecimal min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i].compareTo(max) > 0) {
                max = prices[i];
            }
            if (prices[i].compareTo(min) < 0) {
                min = prices[i];
            }
        }
        map.put("maxPrice2", max);
        map.put("minPrice2", min);
        return JsonMapper.getInstance().toJson(map);
    }

    /**
     * 根据商品id和商品的数量获取批发价
     *
     * @param pId   商品id
     * @param count 商品数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSectionPrice")
    public Map<String, Object> getSectionPrice(String pId, String count) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(pId) || !StringUtils.isNumeric(pId) || StringUtils.isBlank(count) || !StringUtils.isNumeric(count)) {
            map.put("success", false);
            return map;
        }
        BigDecimal price = productSpuService.calculatePrice(Long.parseLong(pId), Integer.parseInt(count));
        map.put("success", true);
        map.put("price", price);
        return map;
    }

    /**
     * 获取商品评价信息
     *
     * @param pId    商品id
     * @param grade  评分
     * @param pageNo 页码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tradeComment/list")
    public Map<String, Object> commentList(String pId, String grade, String pageNo) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(pId) || !StringUtils.isNumeric(pId)) {
            return map;
        }
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        //执行业务，查询出产品分类列表
        TradeComment comment = new TradeComment();
        comment.setPId(Long.parseLong(pId));
        // 是否显示，0否、1是
        comment.setIsShow("1");
        // 类型，0评论、1追评、2回复
        comment.setType("0");
        if (StringUtils.isNotBlank(grade)) {
            comment.setGrade(grade);
        }
        Page<TradeComment> page = new Page<>(R.getRequest(), R.getResponse());
        page.setPageNo(Integer.parseInt(pageNo));
        page = tradeCommentService.selectByWhere(page, new Wrapper(comment));
        Boolean isFirst = R.getBoolean("isFirst");
        if (isFirst) {
            int grade1Count = tradeCommentService.selectByWhere(new Wrapper(comment).and("grade=", 1)).size();//好评
            int grade2Count = tradeCommentService.selectByWhere(new Wrapper(comment).and("grade=", 2)).size();//中评
            int grade3Count = tradeCommentService.selectByWhere(new Wrapper(comment).and("grade=", 3)).size();//差评
            map.put("grade1Count", grade1Count);
            map.put("grade2Count", grade2Count);
            map.put("grade3Count", grade3Count);
        }
        map.put("page", page);
        map.put("prev", page.getPrev());
        map.put("next", page.getNext());
        map.put("last", page.getLast());
        map.put("count", page.getCount());
        map.put("isFirstPage", page.isFirstPage());
        map.put("isLastPage", page.isLastPage());
        return map;
    }

    /**
     * 获取商品咨询信息
     *
     * @param pId      商品id
     * @param category 咨询分类
     * @param pageNo   页码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tradeConsultation/list")
    public Map<String, Object> consultationList(String pId, String category, String pageNo) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(pId) || !StringUtils.isNumeric(pId)) {
            return map;
        }
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        //执行业务，查询出产品分类列表
        TradeConsultation tradeConsultation = new TradeConsultation();
        tradeConsultation.setPId(Long.parseLong(pId));
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        // 类型，0咨询、1回复
        tradeConsultation.setType("0");
        Boolean isFirst = R.getBoolean("isFirst");
        if (!isFirst && StringUtils.isNotBlank(category)) {
            tradeConsultation.setCategory(category);
        }
        Page<TradeConsultation> page = new Page<>(R.getRequest(), R.getResponse());
        page.setPageNo(Integer.parseInt(pageNo));
        page = tradeConsultationService.selectByWhere(page, new Wrapper(tradeConsultation));
        if (isFirst) {
            int category1Count = tradeConsultationService.selectByWhere(new Wrapper(tradeConsultation).and("category=", 1)).size();//好评
            int category2Count = tradeConsultationService.selectByWhere(new Wrapper(tradeConsultation).and("category=", 2)).size();//中评
            int category3Count = tradeConsultationService.selectByWhere(new Wrapper(tradeConsultation).and("category=", 3)).size();//差评
            int category4Count = tradeConsultationService.selectByWhere(new Wrapper(tradeConsultation).and("category=", 4)).size();//差评
            map.put("category1Count", category1Count);
            map.put("category2Count", category2Count);
            map.put("category3Count", category3Count);
            map.put("category4Count", category4Count);
        }
        map.put("page", page);
        map.put("prev", page.getPrev());
        map.put("next", page.getNext());
        map.put("last", page.getLast());
        map.put("count", page.getCount());
        map.put("isFirstPage", page.isFirstPage());
        map.put("isLastPage", page.isLastPage());
        return map;
    }

    /**
     * 发起咨询
     *
     * @param tradeConsultation 咨询实体
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tradeConsultation/save")
    public Map<String, Object> consultationSave(TradeConsultation tradeConsultation) {
        Map<String, Object> map = new HashMap<>();
        if (tradeConsultation == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("缺少参数"));
            return map;
        }
        if (tradeConsultation.getPId() == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("商品编号不能为空"));
            return map;
        }
        if (tradeConsultation.getStoreId() == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("店铺编号不能为空"));
            return map;
        }
        if (StringUtils.isBlank(tradeConsultation.getContent())) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("咨询内容不能为空"));
            return map;
        }
        if (StringUtils.isBlank(tradeConsultation.getCategory())) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("咨询类型不能为空"));
            return map;
        }
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        // 是否回复，0否、1是
        tradeConsultation.setIsReply("0");
        tradeConsultation.setUId(SsoUtils.getUserMain().getUId());
        // 类别，0咨询、1回复
        tradeConsultation.setType("0");
        tradeConsultationService.insert(tradeConsultation);
        map.put("success", true);
        return map;
    }

}
