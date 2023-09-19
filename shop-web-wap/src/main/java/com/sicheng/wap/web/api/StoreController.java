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

import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.store.entity.*;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>标题: StoreController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月16日 下午5:37:00
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreArticleService storeArticleService;
    @Autowired
    private StoreCarouselPictureService storeCarouselPictureService;
    @Autowired
    private StoreCategoryService storeCategoryService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;
    @Autowired
    private SolrStoreService solrStoreService;

    private static final String DESC = "DESC";
    private static final String ASC = "ASC";

    /**
     * 获取店铺总信息
     *
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/store/one/all", produces = {"application/json; charset=UTF-8"})
    public Object adList(String storeId) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        try {
            //查询店铺头部信息
            Map<String, Object> storeNavCount = (Map<String, Object>) this.storeNavCount(storeId).get("data");
//            //查询店铺地址等基本信息
//            Map<String, Object> storeOne = (Map<String, Object>) this.storeOne(storeId).get("data");
            //查询店铺全部基本信息
            Wrapper wrapper = new Wrapper();
            wrapper.and("store_id = ", storeId);
            SolrStore solrStore = solrStoreService.selectOne(wrapper);
            //整合信息并返回
            Map<String, Object> data = new HashMap<>();
            data.put("storeNavCount", storeNavCount);
//            data.put("storeOne", storeOne);
            data.put("solrStore", solrStore);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取店铺总信息成功"), data, null);
        } catch (Exception e) {
            logger.error("获取店铺总信息失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), e, null);
        }
    }

    /**
     * 分页查询店铺列表
     *
     * @param k        搜索关键词
     * @param pageSize 页面大小
     * @param pageNo   当前页码
     * @param sort     可选值：null、allSales、collectionCount
     * @param sortMode 可选值ASC（升序）、DESC（降序）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/store/page", produces = {"application/json; charset=UTF-8"})
    public Object storePage(String k, Integer pageSize, Integer pageNo, String sort, String sortMode) {
        //表单验证
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        if (StringUtils.isBlank(sortMode) || (!DESC.equals(sortMode.toUpperCase()) && !ASC.equals(sortMode.toUpperCase()))) {
            sortMode = DESC;
        }
        sortMode = sortMode.toUpperCase();
        try {
            Wrapper wrapper = new Wrapper();
            wrapper.and("is_open = ", 1);
            wrapper.and("product_count > ", 0);
            if (StringUtils.isNotBlank(k)) {
                wrapper.and("name like ", "%" + k + "%");
            }
            if (StringUtils.isNotBlank(sort) && !"null".equals(sort)) {
                switch (sort) {
                    case "allSales":
                        wrapper.orderBy("all_sales " + sortMode);
                        break;
                    case "collectionCount":
                        wrapper.orderBy("count_collection " + sortMode);
                        break;
                }
            }
            Page<SolrStore> page = new Page<SolrStore>();
            page.setPageSize(pageSize);
            page.setPageNo(pageNo);
            page = solrStoreService.selectByWhere(page, wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("分页查询店铺列表成功"), page.getList(), page);
        } catch (Exception e) {
            logger.error("分页查询店铺列表失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("分页查询店铺列表失败"), null, null);
        }
    }

    /**
     * 根据id获取店铺信息
     *
     * @param storeId 店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/store/one")
    @ResponseBody
    public Map<String, Object> storeOne(String storeId) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        try {
            Store store = storeService.selectById(Long.parseLong(storeId));
            if (store == null) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺信息数据获取成功"), store, null);
            }
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("storeId", store.getStoreId());//店铺id
            map.put("logo", store.getLogo());//店铺logo
            map.put("name", store.getName());//店铺名称
            map.put("provinceId", store.getProvinceId());//省id
            map.put("provinceName", store.getProvinceName());//省名字
            map.put("cityId", store.getCityId());//市id
            map.put("cityName", store.getCityName());//市名字
            map.put("districtId", store.getDistrictId());//区id
            map.put("districtName", store.getDistrictName());//区名字
            map.put("detailedAddress", store.getDetailedAddress());//详细地址
            map.put("storeType", store.getStoreType());//店铺类型
            map.put("storeTel", store.getStoreTel());//店铺客服电话
            map.put("storeQq", store.getStoreQq());//店铺qq
            map.put("storeWechat", store.getStoreWechat());//店铺微信
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺信息数据获取成功"), map, null);
        } catch (Exception e) {
            logger.error("店铺信息数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取多个店铺信息
     *
     * @param storeIds 多个店铺id
     * @param limit    数量
     * @return
     */
    @RequestMapping(value = "/{version}/store/list")
    @ResponseBody
    public Map<String, Object> storeList(String storeIds, String limit) {
        if (StringUtils.isBlank(storeIds)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        String[] storeIdArray = storeIds.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < storeIdArray.length; i++) {
            if (StringUtils.isNumeric(storeIdArray[i])) {
                list.add(Long.parseLong(storeIdArray[i]));
            }
        }
        try {
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(new Store());
            if (!list.isEmpty()) {
                wrapper.and("a.store_id in", list);
            }
            Integer defaultLimit = ApiUtils.getLimit(limit);
            Page<Store> storePage = storeService.selectByWhere(new Page<Store>(1, defaultLimit, defaultLimit), wrapper);
            List<Store> stores = storePage.getList();
            if (stores.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("多个店铺信息数据获取成功"), stores, null);
            }
            List<Object> storeList = new ArrayList<>();
            for (int i = 0; i < stores.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("storeId", stores.get(i).getStoreId());//店铺id
                map.put("logo", stores.get(i).getLogo());//店铺logo
                map.put("name", stores.get(i).getName());//店铺名称
                storeList.add(map);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("多个店铺信息数据获取成功"), storeList, null);
        } catch (Exception e) {
            logger.error("多个店铺信息数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取店铺轮播图
     *
     * @param storeId 店铺id
     * @param limit   数量
     * @return
     */
    @RequestMapping(value = "/{version}/store/carouselPicture/list")
    @ResponseBody
    public Map<String, Object> storeCarouselPictureList(String storeId, String limit) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        try {
            Integer defaultLimit = ApiUtils.getLimit(limit);
            StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
            storeCarouselPicture.setStoreId(Long.parseLong(storeId));
            storeCarouselPicture.setIsOpen("1");//是否开启(0否、1是)
            Page<StoreCarouselPicture> storeCarouselPicturePage = storeCarouselPictureService.selectByWhere(new Page<StoreCarouselPicture>(1, defaultLimit, defaultLimit), new Wrapper(storeCarouselPicture));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺轮播图列表数据获取成功"), storeCarouselPicturePage.getList(), storeCarouselPicturePage);
        } catch (Exception e) {
            logger.error("店铺轮播图数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取店铺文章列表(带分页)
     *
     * @param storeId 店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/store/article/page")
    @ResponseBody
    public Map<String, Object> storeArticlePage(String storeId) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        StoreArticle storeArticle = new StoreArticle();
        storeArticle.setStoreId(Long.parseLong(storeId));
        try {
            Page<StoreArticle> page = Page.newPage();
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(storeArticle);
            wrapper.orderBy("a.sort asc");
            page = storeArticleService.selectByWhere(page, wrapper);
            List<StoreArticle> storeArticleList = page.getList();
            for (int i = 0; i < storeArticleList.size(); i++) {
                storeArticleList.get(i).setContent(StringUtils.abbr(storeArticleList.get(i).getContent(), 100));
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺文章列表数据获取成功"), storeArticleList, page);
        } catch (Exception e) {
            logger.error("店铺文章列表数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取店铺文章（单个）
     *
     * @param saId 店铺文章id
     * @return
     */
    @RequestMapping(value = "/{version}/store/article/one")
    @ResponseBody
    public Map<String, Object> storeArticleOne(String saId) {
        if (saId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺文章id为空"), null, null);
        }
        if (!StringUtils.isNumeric(saId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺文章Id只能为数字"), null, null);
        }
        try {
            StoreArticle storeArticle = storeArticleService.selectById(Long.parseLong(saId));

            // 业务处理
            // 文章详情中的内容是富文本编辑器编辑的，也可能是从其它平台copy来的。
            // 需要处理文章详情中的图片的宽度，设置为width=100%，用以适应移动端。
            String intro=storeArticle.getContent();
            String contentHtml= HtmlUtils.imageWidth(intro);
            contentHtml=HtmlUtils.article(contentHtml);//过滤文本上的样式
            storeArticle.setContent(contentHtml);

            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺文章数据获取成功"), storeArticle, null);
        } catch (Exception e) {
            logger.error("店铺文章数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取店铺商品分类
     *
     * @param storeId 店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/store/category/list")
    @ResponseBody
    public Map<String, Object> storeCategoryList(@PathVariable String version, String storeId) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        try {
            /**
             * v2版本
             */
            if (StringUtils.isNotBlank(version) && version.trim().toLowerCase().equals("v2")) {
                return storeCategoryService.storeCategoryList(storeId);
            }
            /**
             * 最原始版本
             */
            Wrapper wrapper = new Wrapper();
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setStoreId(Long.parseLong(storeId));
            wrapper.setEntity(storeCategory);
            wrapper.orderBy("a.sort ASC");
            List<StoreCategory> storeCategoryList = storeCategoryService.selectByWhere(wrapper);
            if (storeCategoryList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺商品分类获取成功"), storeCategoryList, null);
            }
            List<Object> scList = new ArrayList<>();
            for (int i = 0; i < storeCategoryList.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("storeCategoryId", storeCategoryList.get(i).getStoreCategoryId());//分类id
                map.put("parentId", storeCategoryList.get(i).getParentId());//父级分类id
                map.put("name", storeCategoryList.get(i).getName());//分类名
                scList.add(map);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺商品分类获取成功"), scList, null);
        } catch (Exception e) {
            logger.error("店铺商品分类数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取店铺头部数据
     *
     * @param storeId 店铺id
     * @return
     */
    @RequestMapping(value = "/{version}/store/nav/count")
    @ResponseBody
    public Map<String, Object> storeNavCount(String storeId) {
        if (storeId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id为空"), null, null);
        }
        if (!StringUtils.isNumeric(storeId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("店铺Id只能为数字"), null, null);
        }
        Map<String, Integer> map = new HashMap<>();
        try {
            //店铺收藏数
            MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
            memberCollectionStore.setStoreId(Long.parseLong(storeId));
            int collectionStoreCount = memberCollectionStoreService.countByWhere(new Wrapper(memberCollectionStore));
            map.put("collectionStoreCount", collectionStoreCount);
            //全部商品数量
            ProductSpu productSpu1 = new ProductSpu();
            productSpu1.setStoreId(Long.parseLong(storeId));
            productSpu1.setStatus("50");//10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            int productAllCount = productSpuService.countByWhere(new Wrapper(productSpu1));
            map.put("productAllCount", productAllCount);
            //新品数量
            ProductSpu productSpu2 = new ProductSpu();
            productSpu2.setStoreId(Long.parseLong(storeId));
            productSpu1.setStatus("50");//10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            String date_first = format.format(Calendar.getInstance().getTime());
            date_first = date_first + "-01 00:00:00";
            productSpu2.setBeginUpdateDate(format.parse(date_first));//开始时间
            productSpu2.setEndUpdateDate(new Date());//结束时间
            int productNewCount = productSpuService.countByWhere(new Wrapper(productSpu2));
            map.put("productNewCount", productNewCount);
            //店铺动态数量
            StoreArticle storeArticle = new StoreArticle();
            storeArticle.setStoreId(Long.parseLong(storeId));
            int storeArticleCount = storeArticleService.countByWhere(new Wrapper(storeArticle));
            map.put("storeArticleCount", storeArticleCount);

            //店铺分类个数
            Wrapper wrapper = new Wrapper();
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setStoreId(Long.parseLong(storeId));
            wrapper.setEntity(storeCategory);
            Page<StoreCategory> page = new Page<>();
            page.setPageNo(1);
            page.setPageSize(1);
            page = storeCategoryService.selectByWhere(page, wrapper);
            map.put("StoreCategoryCount", new Integer("" + page.getCount()));

            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("店铺头部数据数据获取成功"), map, null);
        } catch (Exception e) {
            logger.error("店铺文章头部数据参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

}
