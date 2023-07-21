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
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品 Entity 子类，请把你的业务代码写在这里
 *
 * @author 赵磊
 * @version 2017-02-06
 */
public class ProductSpu extends ProductSpuBase<ProductSpu> {


    private static final long serialVersionUID = 1L;

    public ProductSpu() {
        super();
    }

    public ProductSpu(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private String storeName;                //店铺名

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    private String sellerName;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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
    private StoreCategory storeCategory; //一个商品--店铺商品分类

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
            productSkuList = dao.selectByWhere(null, new Wrapper().and("p_id=", this.getPId()).orderBy("p_id asc"));//排序
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

    public List<StoreAlbumPicture> getStoreAlbumPictureList() {
        if (storeAlbumPictureList == null) {
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

    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
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
    public void setProductSectionPriceList(List<ProductSectionPrice> productSectionPrice) {
        this.productSectionPriceList = productSectionPrice;
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
    @JsonIgnore
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

    //一对一映射
    @JsonIgnore
    private ProductSpuAnalyze productSpuAnalyze; //一个商品--一个商品统计

    public ProductSpuAnalyze getProductSpuAnalyze() {
        if (productSpuAnalyze == null) {
            ProductSpuAnalyzeDao productSpuAnalyzeDao = SpringContextHolder.getBean(ProductSpuAnalyzeDao.class);
            productSpuAnalyze = productSpuAnalyzeDao.selectById(this.getPId());
        }
        return productSpuAnalyze;
    }

    public void setProductSpuAnalyze(ProductSpuAnalyze productSpuAnalyze) {
        this.productSpuAnalyze = productSpuAnalyze;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商品对10个商品统计
    //填充 xxx,把1+N改成1+1
    public static void fillProductSpuAnalyze(List<ProductSpu> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSpuAnalyzeDao productSpuAnalyzeDao = SpringContextHolder.getBean(ProductSpuAnalyzeDao.class);
        List<ProductSpuAnalyze> productSpuAnalyzeList = productSpuAnalyzeDao.selectByIdIn(ids);
        fill(productSpuAnalyzeList, "pId", list, "pId", "productSpuAnalyze");//循环填充
    }

    /**
     * 获取市场价（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMarketPrice() {
        if (super.getMarketPrice() == null) {
            return super.getMarketPrice();
        }
        String marketPrice = super.getMarketPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(marketPrice);
    }

    /**
     * 获取运费（去掉无用小数点0）
     */
    @Override
    public BigDecimal getExpressPrice() {
        if (super.getExpressPrice() == null) {
            return super.getExpressPrice();
        }
        String expressPrice = super.getExpressPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(expressPrice);
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

    //用来获取展示价格，当有批发阶梯价时优先展示批发的阶梯价
    @JsonIgnore
    private BigDecimal showPrice;

    public Object getShowPrice() {
        List<ProductSectionPrice> productSectionPriceList = this.getProductSectionPriceList();
        String yuan = FYUtils.fyParams("元");
        if (productSectionPriceList != null && productSectionPriceList.size() > 0) {
            //批发价格能展示区间价格优先展示区间价格
            if (productSectionPriceList.size() > 1) {
                //展示最小价格~最大价格
                return yuan + productSectionPriceList.get(productSectionPriceList.size() - 1).getPrice() + "~" + yuan + productSectionPriceList.get(0).getPrice();
            }
            return yuan + productSectionPriceList.get(0).getPrice();
        }
        List<ProductSku> productSkuList = this.getProductSkuList();
        return yuan + productSkuList.get(0).getPrice();
    }

    //商品销售量
    @JsonIgnore
    private Long allSales;

    public Long getAllSales() {
        if (this.allSales == null) {
            if (this.getPId() == null) {
                return 0L;
            }
            SolrProductDao solrProductDao = SpringContextHolder.getBean(SolrProductDao.class);
            this.allSales = solrProductDao.selectAllSalesById(this.getPId());
            if (this.allSales == null) {
                return 0L;
            }
        }
        return allSales;
    }
}