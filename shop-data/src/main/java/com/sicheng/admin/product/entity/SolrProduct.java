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
package com.sicheng.admin.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.*;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreAlbumPictureDao;
import com.sicheng.admin.store.dao.StoreCategoryDao;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品搜索 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-05-23
 */
public class SolrProduct extends SolrProductBase<SolrProduct> {

    private static final long serialVersionUID = 1L;

    public SolrProduct() {
        super();
    }

    public SolrProduct(Long id) {
        super(id);
    }

    //一对一映射
    @JsonIgnore
    private Store store; //一个商品--一个店铺

    public Store getStore() {
        if (store == null) {
            StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
            store = dao.selectById(this.getStoreId());
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商品对10个商家
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> storeList = dao.selectByIdIn(ids);
        fill(storeList, "storeId", list, "storeId", "store");//循环填充
    }

    private String storeName;                //店铺名

    public String getStoreName() {
        return storeName;
    }


    //一对一映射
    @JsonIgnore
    private ProductCategory productCategory; //一个商品--商品分类

    public ProductCategory getProductCategory() {
        if (productCategory == null) {
            ProductCategoryDao dao = SpringContextHolder.getBean(ProductCategoryDao.class);
            productCategory = dao.selectById(this.getCategoryId());
        }
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    //一对一映射
    @JsonIgnore
    private StoreCategory storeCategory; //一个商品--商品分类

    public StoreCategory getStoreCategory() {
        if (storeCategory == null) {
            StoreCategoryDao dao = SpringContextHolder.getBean(StoreCategoryDao.class);
            storeCategory = dao.selectById(this.getStoreCategoryId());
        }
        return storeCategory;
    }

    public void setStoreCategory(StoreCategory storeCategory) {
        this.storeCategory = storeCategory;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商品对10个店铺内分类
    //填充 xxx,把1+N改成1+1
    public static void fillStoreCategory(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "categoryId");//批量调用对象的getXxx()方法
        StoreCategoryDao dao = SpringContextHolder.getBean(StoreCategoryDao.class);
        List<StoreCategory> storeCategorylist = dao.selectByIdIn(ids);
        fill(storeCategorylist, "storeCategoryId", list, "storeCategoryId", "storeCategory");//循环填充
    }

    //一对多映射
    @JsonIgnore
    private List<ProductSku> productSkuList;//一个商品--多个SKU

    public List<ProductSku> getProductSkuList() {
        if (productSkuList == null) {
            ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
            productSkuList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("p_id"));//排序
        }
        return productSkuList;
    }

    public void setProductSkuList(List<ProductSku> skuList) {
        this.productSkuList = skuList;
    }

    private List<ProductSku> productSkuJsonList;//一个商品--多个SKU

    public String getProductSkuJsonList() {
        if (productSkuJsonList == null) {
            ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
            productSkuJsonList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("p_id asc"));//排序
        }
        return JsonMapper.getInstance().toJson(productSkuJsonList);
    }

    public void setProductSkuJsonList(List<ProductSku> skuList) {
        this.productSkuJsonList = skuList;
    }

    //一对多映射
    @JsonIgnore
    private List<ProductParamMapping> productParamList;//一个商品--多个参数

    public List<ProductParamMapping> getProductParamList() {
        if (productParamList == null) {
            ProductParamMappingDao dao = SpringContextHolder.getBean(ProductParamMappingDao.class);
            productParamList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("sort asc"));//排序
        }
        return productParamList;
    }

    public void setProductParamList(List<ProductParamMapping> productParamList) {
        this.productParamList = productParamList;
    }

    //多对商品图片中间表数据
    @JsonIgnore
    private List<ProductPictureMapping> productPictureMappingList;

    public List<ProductPictureMapping> getProductPictureMappingList() {
        if (productPictureMappingList == null) {
            ProductPictureMappingDao dao = SpringContextHolder.getBean(ProductPictureMappingDao.class);
            productPictureMappingList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("id asc"));//排序
        }
        return productPictureMappingList;
    }

    public void setProductPictureMappingList(List<ProductPictureMapping> productPictureMappingList) {
        this.productPictureMappingList = productPictureMappingList;
    }

    //多对多（一对多+idIn来实现）
    @JsonIgnore
    private List<StoreAlbumPicture> storeAlbumPictureList;//一个商品--多个图片，一个图片--多个商品

    public List<?> getStoreAlbumPictureList() {
        if (storeAlbumPictureList == null) {
            //ProductPictureMappingDao dao=SpringContextHolder.getBean(ProductPictureMappingDao.class);
            //List<ProductPictureMapping> mappinglist= dao.selectByWhere(null,new Wrapper().and("p_id=",this.getPId()).orderBy("sort asc"));//排序
            List<ProductPictureMapping> mappinglist = getProductPictureMappingList();
            List<Object> ids = batchField(mappinglist, "imgId");//批量调用对象的getXxx()方法
            StoreAlbumPictureDao dao2 = SpringContextHolder.getBean(StoreAlbumPictureDao.class);
            storeAlbumPictureList = dao2.selectByIdIn(ids);
        }
        return storeAlbumPictureList;
    }

    public void setStoreAlbumPictureList(List<StoreAlbumPicture> storeAlbumPictureList) {
        this.storeAlbumPictureList = storeAlbumPictureList;
    }

    private ProductDetail productDetail;    //一个商品--一个商品详情

    /**
     * @return the productDetail
     */
    public ProductDetail getProductDetail() {
        if (productDetail == null) {
            ProductDetailDao dao = SpringContextHolder.getBean(ProductDetailDao.class);
            productDetail = dao.selectById(this.getPId());
        }
        return productDetail;
    }

    /**
     * @param productDetail the productDetail to set
     */
    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    private ProductBrand productBrand;//一个商品--一个品牌

    /**
     * @return the productBrand
     */
    public ProductBrand getProductBrand() {
        if (productBrand == null) {
            ProductBrandDao dao = SpringContextHolder.getBean(ProductBrandDao.class);
            productBrand = dao.selectById(this.getBrandId());
        }
        return productBrand;
    }

    /**
     * @param productBrand the productBrand to set
     */
    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商品对10个分类
    //填充 xxx,把1+N改成1+1
    public static void fillProductBrand(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "brandId");//批量调用对象的getXxx()方法
        ProductBrandDao dao = SpringContextHolder.getBean(ProductBrandDao.class);
        List<ProductBrand> productBrandlist = dao.selectByIdIn(ids);
        fill(productBrandlist, "brandId", list, "brandId", "productBrand");//循环填充
    }

    private List<ProductSectionPrice> productSectionPriceList;//一个商品--多个区间价

    /**
     * @return the productSectionPrice
     */
    public List<ProductSectionPrice> getProductSectionPriceList() {
        if (productSectionPriceList == null) {
            ProductSectionPriceDao dao = SpringContextHolder.getBean(ProductSectionPriceDao.class);
            productSectionPriceList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("sort asc"));//排序
        }
        return productSectionPriceList;
    }

    /**
     * @param productSectionPrice the productSectionPrice to set
     */
    public void setProductSectionPriceList(List<ProductSectionPrice> productSectionPriceList) {
        this.productSectionPriceList = productSectionPriceList;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商品对10个分类
    //填充 xxx,把1+N改成1+1
    public static void fillProductCategory(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "categoryId");//批量调用对象的getXxx()方法
        ProductCategoryDao dao = SpringContextHolder.getBean(ProductCategoryDao.class);
        List<ProductCategory> productCategorylist = dao.selectByIdIn(ids);
        fill(productCategorylist, "categoryId", list, "categoryId", "productCategory");//循环填充
    }

    //一对一映射
    /**
     * 获取会员信息
     */
    private UserMain userMain;//一个商品--一个商家

    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getUId());
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    //ListIdIn工具  在一个list中做 一对一，10个一条预存款明细对10个用户
    //填充 xxx,把1+N改成1+1
    public static void fillUserMain(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    /**
     * 获取最高价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMaxPrice() {
        if (super.getMaxPrice() == null) {
            return super.getMaxPrice();
        }
        String maxPrice = super.getMaxPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(maxPrice);
    }

    /**
     * 获取最低价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMinPrice() {
        if (super.getMinPrice() == null) {
            return super.getMinPrice();
        }
        String minPrice = super.getMinPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(minPrice);
    }

    /**
     * 获取最高零售价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMaxPrice1() {
        if (super.getMaxPrice1() == null) {
            return super.getMaxPrice1();
        }
        String maxPrice1 = super.getMaxPrice1().stripTrailingZeros().toPlainString();
        return new BigDecimal(maxPrice1);
    }

    /**
     * 获取最低零售价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMinPrice1() {
        if (super.getMinPrice1() == null) {
            return super.getMinPrice1();
        }
        String minPrice1 = super.getMinPrice1().stripTrailingZeros().toPlainString();
        return new BigDecimal(minPrice1);
    }

    /**
     * 获取最高批发价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMaxPrice2() {
        if (super.getMaxPrice2() == null) {
            return super.getMaxPrice2();
        }
        String maxPrice2 = super.getMaxPrice2().stripTrailingZeros().toPlainString();
        return new BigDecimal(maxPrice2);
    }

    /**
     * 获取最低批发价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMinPrice2() {
        if (super.getMinPrice2() == null) {
            return super.getMinPrice2();
        }
        String minPrice2 = super.getMinPrice2().stripTrailingZeros().toPlainString();
        return new BigDecimal(minPrice2);
    }

    /**
     * 获取商品每个规格的图片（每个规格获取第一张）
     */
    public List<StoreAlbumPicture> getStoreAlbumPictureList2() {
        List<StoreAlbumPicture> pictureList = new ArrayList<>();
        List<ProductSku> skuList = this.getProductSkuList();
        if (skuList == null || skuList.isEmpty()) {
            return pictureList;
        }
        List<ProductPictureMapping> pictureMappinglist = this.getProductPictureMappingList();
        if (pictureMappinglist == null || pictureMappinglist.isEmpty()) {
            return pictureList;
        }
        List<String> colorList = new ArrayList<>();
        for (ProductSku sku : skuList) {
            if (sku == null || StringUtils.isBlank(sku.getSpec1V())) {
                continue;
            }
            if (!colorList.contains(sku.getSpec1V())) {
                colorList.add(sku.getSpec1V());
            }
        }
        List<Long> imgIdList = new ArrayList<>();
        StoreAlbumPictureDao storeAlbumPictureDao = SpringContextHolder.getBean(StoreAlbumPictureDao.class);
        if (colorList.isEmpty()) {
            imgIdList.add(pictureMappinglist.get(0).getImgId());
            pictureList = storeAlbumPictureDao.selectByIdIn(imgIdList);
            return pictureList;
        }
        for (String c : colorList) {
            for (ProductPictureMapping m : pictureMappinglist) {
                if (m == null || StringUtils.isBlank(m.getColor()) || m.getImgId() == null) {
                    continue;
                }
                if (c.equals(m.getColor())) {
                    imgIdList.add(m.getImgId());
                    break;
                }
            }
        }
        pictureList = storeAlbumPictureDao.selectByIdIn(imgIdList);
        if (pictureList.isEmpty()) {
            pictureList.add(storeAlbumPictureDao.selectById(pictureMappinglist.get(0).getImgId()));
        }
        return pictureList;
    }

    @Override
    @Field("create_date")
    public void setCreateDate(Date createDate) {
        super.setCreateDate(createDate);
    }

    @Override
    @Field("update_by")
    public void setUpdateBy(User updateBy) {
        super.setUpdateBy(updateBy);
    }

    @Override
    @Field("id")
    public void setPId(Long pId) {
        super.setPId(pId);
    }

    @Override
    @Field("name")
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    @Field("name_sub")
    public void setNameSub(String nameSub) {
        super.setNameSub(nameSub);
    }

    @Override
    @Field("category_name")
    public void setCategoryName(String categoryName) {
        super.setCategoryName(categoryName);
    }

    @Override
    @Field("category_id")
    public void setCategoryId(Long categoryId) {
        super.setCategoryId(categoryId);
    }

    @Override
    @Field("category_level")
    public void setCategoryLevel(Long categoryLevel) {
        super.setCategoryLevel(categoryLevel);
    }

    @Override
    @Field("cate_first_letter")
    public void setCateFirstLetter(String cateFirstLetter) {
        super.setCateFirstLetter(cateFirstLetter);
    }

    @Override
    @Field("cate_parent_ids")
    public void setCateParentIds(String cateParentIds) {
        super.setCateParentIds(cateParentIds);
    }

    @Override
    @Field("store_cate_name")
    public void setStoreCateName(String storeCateName) {
        super.setStoreCateName(storeCateName);
    }

    @Override
    @Field("store_cate_parent_ids")
    public void setStoreCateParentIds(String storeCateParentIds) {
        super.setStoreCateParentIds(storeCateParentIds);
    }

    @Override
    @Field("store_type")
    public void setStoreType(String storeType) {
        super.setStoreType(storeType);
    }

    @Override
    @Field("store_id")
    public void setStoreId(Long storeId) {
        super.setStoreId(storeId);
    }

    @Override
    @Field("user_name")
    public void setUserName(String userName) {
        super.setUserName(userName);
    }

    @Override
    @Field("u_id")
    public void setUId(Long uId) {
        super.setUId(uId);
    }

    @Override
    @Field("brand_name")
    public void setBrandName(String brandName) {
        super.setBrandName(brandName);
    }

    @Override
    @Field("brand_first_leftter")
    public void setBrandFirstLeftter(String brandFirstLeftter) {
        super.setBrandFirstLeftter(brandFirstLeftter);
    }

    @Override
    @Field("brand_english_name")
    public void setBrandEnglishName(String brandEnglishName) {
        super.setBrandEnglishName(brandEnglishName);
    }

    @Override
    @Field("brand_id")
    public void setBrandId(Long brandId) {
        super.setBrandId(brandId);
    }

    @Override
    @Field("min_price")
    public void setMinPrice(BigDecimal minPrice) {
        super.setMinPrice(minPrice);
    }

    @Override
    @Field("min_price1")
    public void setMinPrice1(BigDecimal minPrice1) {
        super.setMinPrice1(minPrice1);
    }

    @Override
    @Field("min_price2")
    public void setMinPrice2(BigDecimal minPrice2) {
        super.setMinPrice2(minPrice2);
    }

    @Override
    @Field("max_price")
    public void setMaxPrice(BigDecimal maxPrice) {
        super.setMaxPrice(maxPrice);
    }

    @Override
    @Field("max_price1")
    public void setMaxPrice1(BigDecimal maxPrice1) {
        super.setMaxPrice1(maxPrice1);
    }

    @Override
    @Field("max_price2")
    public void setMaxPrice2(BigDecimal maxPrice2) {
        super.setMaxPrice2(maxPrice2);
    }

    @Override
    @Field("unit")
    public void setUnit(String unit) {
        super.setUnit(unit);
    }

    @Override
    @Field("car_ids")
    public void setCarIds(String carIds) {
        super.setCarIds(carIds);
    }

    @Override
    @Field("status")
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    @Field("type")
    public void setType(String type) {
        super.setType(type);
    }

    @Override
    @Field("is_recommend")
    public void setIsRecommend(String isRecommend) {
        super.setIsRecommend(isRecommend);
    }

    @Override
    @Field("param_value")
    public void setParamValue(String paramValue) {
        super.setParamValue(paramValue);
    }

    @Override
    @Field("province_id")
    public void setProvinceId(Long provinceId) {
        super.setProvinceId(provinceId);
    }

    @Override
    @Field("city_id")
    public void setCityId(Long cityId) {
        super.setCityId(cityId);
    }

    @Override
    @Field("province_name")
    public void setProvinceName(String provinceName) {
        super.setProvinceName(provinceName);
    }

    @Override
    @Field("city_name")
    public void setCityName(String cityName) {
        super.setCityName(cityName);
    }

    @Override
    @Field("all_sales")
    public void setAllSales(Long allSales) {
        super.setAllSales(allSales);
    }

    @Override
    @Field("week_sales")
    public void setWeekSales(Long weekSales) {
        super.setWeekSales(weekSales);
    }

    @Override
    @Field("month_sales")
    public void setMonthSales(Long monthSales) {
        super.setMonthSales(monthSales);
    }

    @Override
    @Field("month3_sales")
    public void setMonth3Sales(Long month3Sales) {
        super.setMonth3Sales(month3Sales);
    }

    @Override
    @Field("collection_count")
    public void setCollectionCount(Long collectionCount) {
        super.setCollectionCount(collectionCount);
    }

    @Override
    @Field("comment_count")
    public void setCommentCount(Long commentCount) {
        super.setCommentCount(commentCount);
    }

    @Override
    @Field("image")
    public void setImage(String image) {
        super.setImage(image);
    }

    @Field("store_name")
    @Override
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    @Field("create_by")
    public void setCreateBy(User createBy) {
        super.setCreateBy(createBy);
    }

    @Override
    @Field("update_date")
    public void setUpdateDate(Date updateDate) {
        super.setUpdateDate(updateDate);
    }

    @Override
    public Long getUId() {
        return super.getUId();
    }
}