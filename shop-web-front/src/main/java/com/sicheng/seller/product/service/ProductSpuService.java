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
package com.sicheng.seller.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductCarMapping;
import com.sicheng.admin.product.entity.ProductDetail;
import com.sicheng.admin.product.entity.ProductExt;
import com.sicheng.admin.product.entity.ProductParamMapping;
import com.sicheng.admin.product.entity.ProductPictureMapping;
import com.sicheng.admin.product.entity.ProductSectionPrice;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 商品 Service
 *
 * @author zhaolei
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductSpuService extends CrudService<ProductSpuDao, ProductSpu> {

    @Autowired
    private ProductParamMappingService productParamMappingService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSectionPriceService productSectionPriceService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductPictureMappingService productPictureMappingService;
    @Autowired
    private SysVariableService variableService;
    @Autowired
    private ProductExtService productExtService;
    @Autowired
    private ProductCarMappingService productCarMappingService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 发布、编辑商品
     *
     * @param paramMap        参数map
     * @param sections        商品区间价
     * @param productSku      商品sku
     * @param productSpu      商品spu
     * @param detail          商品详情
     * @param productImgArray 商品图片
     * @param carIds          选择车型库ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void publishProduct(Map<String, String[]> paramMap, String[] sections, String[] productSku, ProductSpu productSpu, String detail, String[] productImgArray, String[] carIds, String allCar) {
        if (productSpu == null) {
            return;
        }
        //pId==null代表插入，pId!=null 代表更新
        if (productSpu.getPId() == null) {
            //插入商品spu
            dao.insertSelective(productSpu);
            //增加店内商品数
            updateStoreSpuCount(productSpu);
        } else {
            //删除商品相关联的数据
            deleteProductRelationMsg(productSpu);
            //插入商品spu
            ProductSpu spu = dao.selectById(productSpu.getId());
            dao.updateByWhere(productSpu, new Wrapper(spu));
        }
        //如果商品的销售类型是批发型或混合行，则插入区间值
        if (!"1".equals(productSpu.getType()) && sections.length > 0) {
            insertSectionPrice(productSpu, sections);
        }
        //商品参数中间表插入数据
        if ((!paramMap.isEmpty()) && paramMap.get("params") != null) {
            insertParamMapping(productSpu, paramMap);
        }
        //商品sku
        if (productSku.length > 0) {
            insertProductSku(productSpu, productSku);
        }
        //插入商品详情
        if (StringUtils.isNotBlank(detail)) {
            insertProductDetial(productSpu, detail);
        }
        //插入商品图片
        if (productImgArray.length > 0) {
            List<ProductPictureMapping> pictureList = insertProductImg(productSpu, productImgArray);
            if (!pictureList.isEmpty()) {
                StoreAlbumPicture picture = pictureList.get(0).getStoreAlbumPicture();
                productSpu.setImage(picture.getPath());
                dao.updateByIdSelective(productSpu);
            }
        }
        //是否是全车系
        if ("1".equals(allCar)) {
            //插入或更新车型库
            ProductExt productExt = new ProductExt();
            productExt.setPId(productSpu.getPId());
            productExt.setAllCar("1");//是否全车系（0否，1是）
            productExt.setCarIds("");
            productExtService.insertOrUpdate(productExt);
            //删除商品与车系车型中间表
            ProductCarMapping pcm = new ProductCarMapping();
            pcm.setPId(productSpu.getPId());
            productCarMappingService.deleteByWhere(new Wrapper(pcm));
        } else {
            if (carIds.length == 0) {
                ProductExt productExt = new ProductExt();
                productExt.setPId(productSpu.getPId());
                productExt.setAllCar("0");//是否全车系（0否，1是）
                productExtService.insertOrUpdate(productExt);
            } else {
                //插入车型库
                Set<String> cSet = new HashSet<>();
                for (int i = 0; i < carIds.length; i++) {
                    String[] carIdss = carIds[i].split(",");
                    for (int j = 0; j < carIdss.length; j++) {
                        cSet.add(carIdss[j]);
                    }
                }
                StringBuilder buider = new StringBuilder();
                Iterator<String> iterator = cSet.iterator();
                while (iterator.hasNext()) {
                    if (buider.length() > 3990) {//3990是为了防止加上下一个id的时候导致超过4000
                        break;
                    }
                    buider.append(",");
                    buider.append(iterator.next());
                }
                String cIds = buider.substring(1);
                ProductExt productExt = new ProductExt();
                productExt.setPId(productSpu.getPId());
                productExt.setCarIds(cIds);
                productExt.setAllCar("0");//是否全车系（0否，1是）
                productExtService.insertOrUpdate(productExt);
                //删除商品与车系车型中间表
                ProductCarMapping pcm = new ProductCarMapping();
                pcm.setPId(productSpu.getPId());
                productCarMappingService.deleteByWhere(new Wrapper(pcm));
                //插入商品与车系车型中间表
                List<ProductCarMapping> productCarMappingList = new ArrayList<>(carIds.length);
                for (int i = 0; i < carIds.length; i++) {
                    ProductCarMapping productCarMapping = new ProductCarMapping();
                    productCarMapping.setPId(productSpu.getPId());
                    productCarMapping.setCarIds(carIds[i]);
                    productCarMappingList.add(productCarMapping);
                }
                productCarMappingService.insertBatch(productCarMappingList);
            }
        }
        //需要审核的商品，向管理员发送短信
        if ("30".equals(productSpu.getStatus())) {
            //短信模板参数
            Map<String, String> map = new HashMap<>();
            map.put("sellerName", SsoUtils.getUserMain().getLoginName());
            map.put("productName", productSpu.getName());
            //给后台管理员发送短信
            siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PRODUCT_ADD);
        }
    }

    /**
     * 删除商品
     *
     * @param productSpu 商品spu
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(ProductSpu productSpu) {
        if (productSpu == null || productSpu.getPId() == null) {
            return;
        }
        //删除车型库
        productExtService.deleteById(productSpu.getPId());
        //删除商品相关联的数据
        deleteProductRelationMsg(productSpu);
        //删除商品
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productSpu.setStoreId(userSeller.getStoreId());//属主检查
        dao.deleteByWhere(new Wrapper(productSpu));
    }

    /**
     * 插入区间价
     *
     * @param productSpu 商品spu
     * @param sections   区间价
     */
    public void insertSectionPrice(ProductSpu productSpu, String[] sections) {
        for (int i = 0; i < sections.length; i++) {
            String[] section = sections[i].split("_");
            if (section.length > 1) {
                ProductSectionPrice sectionPrice = new ProductSectionPrice();
                sectionPrice.setPId(productSpu.getPId());
                sectionPrice.setSection(section[0]);
                sectionPrice.setPrice(new BigDecimal(section[1]));
                sectionPrice.setSort(i);
                productSectionPriceService.insertSelective(sectionPrice);
            }
        }
    }

    /**
     * 商品参数中间表插入数据
     *
     * @param productSpu 商品spu
     * @param paramMap   参数map
     */
    public void insertParamMapping(ProductSpu productSpu, Map<String, String[]> paramMap) {
        List<ProductParamMapping> productParamList = new ArrayList<>();
        String[] params = paramMap.get("params");
        String[] valuesImg = paramMap.get("valuesImg");
        String[] type = paramMap.get("type");
        String[] format = paramMap.get("format");
        String[] name = paramMap.get("name");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (StringUtils.isNotBlank(params[i])) {
                    ProductParamMapping productParamMapping = new ProductParamMapping();
                    String[] param = params[i].split("_");
                    if (param.length > 1) {
                        productParamMapping.setParamId(Long.parseLong(param[0]));
                        productParamMapping.setValue(param[1]);
                    }
                    if (valuesImg.length >= i && StringUtils.isNotBlank(valuesImg[i])) {
                        productParamMapping.setValueImg(valuesImg[i]);
                    }
                    if (type.length >= i && StringUtils.isNotBlank(type[i])) {
                        productParamMapping.setType(type[i]);
                    }
                    if (format.length >= i && StringUtils.isNotBlank(format[i])) {
                        productParamMapping.setFormat(format[i]);
                    }
                    if (name.length >= i && StringUtils.isNotBlank(name[i])) {
                        productParamMapping.setName(name[i]);
                    }
                    productParamMapping.setPId(productSpu.getPId());
                    productParamList.add(productParamMapping);
                }
            }
        }
        productParamMappingService.insertBatch(productParamList);
    }

    /**
     * 插入商品sku数据
     *
     * @param productSpu 商品spu
     * @param productSku 商品sku
     */
    public void insertProductSku(ProductSpu productSpu, String[] productSku) {
        //先去重
        Map<String, String> map = new HashMap<>();
        for (String sku : productSku) {
            map.put(sku, "");
        }
        String[] newProductSku = new String[map.size()];
        map.keySet().toArray(newProductSku);

        List<ProductSku> productSkuList = new ArrayList<>();
        for (int i = 0; i < newProductSku.length; i++) {
            ProductSku productSku2 = new ProductSku();
            if (StringUtils.isNotBlank(newProductSku[i])) {
                String[] specs = newProductSku[i].split(",");
                if (specs.length > 0) {
                    for (int j = 0; j < specs.length; j++) {
                        String[] spec = specs[j].split("_");
                        if (spec.length == 4) {
                            if (spec[0].equals("spec1")) {
                                productSku2.setSpec1(spec[1] + "_" + spec[2]);
                                productSku2.setSpec1V(spec[3]);
                            }
                            if (spec[0].equals("spec2")) {
                                productSku2.setSpec2(spec[1] + "_" + spec[2]);
                                productSku2.setSpec2V(spec[3]);
                            }
                            if (spec[0].equals("spec3")) {
                                productSku2.setSpec3(spec[1] + "_" + spec[2]);
                                productSku2.setSpec3V(spec[3]);
                            }
                            if (spec[0].equals("price") && !"2".equals(productSpu.getType())) {
                                productSku2.setPrice(new BigDecimal((spec[3])));
                            }
                            if (spec[0].equals("stock")) {
                                productSku2.setStock(Integer.valueOf(spec[3]));
                            }
                            if (spec[0].equals("sn") && StringUtils.isNotBlank(spec[3])) {
                                productSku2.setSn(spec[3]);
                            }
                        }
                    }
                }
            }
            //是否无规格
            productSku2.setIsNotSpec("1");
            productSku2.setPId(productSpu.getPId());
            productSkuList.add(productSku2);
        }
        productSkuService.insertSelectiveBatch(productSkuList);
    }

    /**
     * 插入商品详情
     *
     * @param productSpu 商品spu
     * @param detail     商品详情
     */
    public void insertProductDetial(ProductSpu productSpu, String detail) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setPkMode(1);
        productDetail.setPId(productSpu.getPId());
        productDetail.setIntroduction(detail);
        productDetailService.insertSelective(productDetail);
    }

    /**
     * 更新店内商品数
     *
     * @param productSpu 商品spu
     */
    public void updateStoreSpuCount(ProductSpu productSpu) {
        Store store = productSpu.getStore();
        if (store != null) {
            Integer productCount = 0;
            if (store.getProductCount() != null) {
                productCount = store.getProductCount();
            }
            productCount += 1;
            Store storeNew = new Store();
            storeNew.setProductCount(productCount);
            storeNew.setStoreId(productSpu.getStoreId());
            storeService.updateByIdSelective(storeNew);
        }
    }

    /**
     * 插入商品图片
     *
     * @param productSpu  商品spu
     * @param productImgs 商品图片
     */
    public List<ProductPictureMapping> insertProductImg(ProductSpu productSpu, String[] productImgs) {
        List<ProductPictureMapping> pictureList = new ArrayList<>();
        for (int i = 0; i < productImgs.length; i++) {
            String[] imgs = productImgs[i].split(":");
            if (imgs.length > 1) {
                String[] imgss = imgs[1].split("-");
                for (int j = 0; j < imgss.length; j++) {
                    ProductPictureMapping picture = new ProductPictureMapping();
                    picture.setPId(productSpu.getPId());
                    picture.setColor(imgs[0]);
                    picture.setImgId(Long.parseLong(imgss[j]));
                    logger.info("商品图片id:" + Long.parseLong(imgss[j]));
                    picture.setSort(pictureList.size() + 1);
                    pictureList.add(picture);
                }
            }
        }
        productPictureMappingService.insertBatch(pictureList);
        return pictureList;
    }

    /**
     *  发布商品时验证商品信息是否存在违禁词 
     *  @param param 需要验证的参数(商品名称,商品卖点描述,商品规格,商品描述)
     *  @return 返回的是违禁词。如果返回值是空，商品信息无违禁词，发布的商品合格。
     */
    public String productForbiddenWords(String param) {
        String result = "";//false表示参数不包含违禁词，true表示参数包含违禁词
        SysVariable sysVariable = variableService.getSysVariable("product_forbidden_words");
        if (sysVariable != null && StringUtils.isNotBlank(sysVariable.getValueClob())) {
            String[] forbiddenWords = sysVariable.getValueClob().split(",");
            if (forbiddenWords.length > 0) {
                for (int i = 0; i < forbiddenWords.length; i++) {
                    if (param.indexOf(forbiddenWords[i]) != -1) {//参数包含违禁词
                        result = forbiddenWords[i];
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 批发模式下,根据商品数量计算价格
     *
     * @param pId        商品id
     * @param totalCount 商品数量
     * @return
     */
    public BigDecimal calculatePrice(Long pId, Integer totalCount) {
        List<ProductSectionPrice> sectionPriceList = productSectionPriceService.selectByWhere(new Wrapper().and("p_id = ", pId).orderBy("sort"));
        BigDecimal price = new BigDecimal("0");
        if (!sectionPriceList.isEmpty()) {
            if (totalCount <= Long.parseLong(sectionPriceList.get(0).getSection())) {
                price = sectionPriceList.get(0).getPrice();
            }
            if (totalCount >= Long.parseLong(sectionPriceList.get(sectionPriceList.size() - 1).getSection())) {
                price = sectionPriceList.get(sectionPriceList.size() - 1).getPrice();
            }
            if (price.equals(new BigDecimal("0"))) {
                for (int i = 0; i < sectionPriceList.size() - 1; i++) {
                    Long section1 = Long.parseLong(sectionPriceList.get(i).getSection());
                    Long section2 = Long.parseLong(sectionPriceList.get(i + 1).getSection());
                    if (totalCount >= section1 && totalCount < section2) {
                        price = sectionPriceList.get(i).getPrice();
                        break;
                    }
                }
            }
        }
        return price;
    }

    /**
     * 删除与商品相关的数据
     *
     * @param productSpu
     */
    private void deleteProductRelationMsg(ProductSpu productSpu) {
        if (productSpu == null || productSpu.getPId() == null) {
            return;
        }
        //删除商品与车系车型中间表
        productCarMappingService.deleteById(productSpu.getPId());
        //删除区间价
        productSectionPriceService.deleteByWhere(new Wrapper().and("p_id =", productSpu.getPId()));
        //删除sku信息
        productSkuService.deleteByWhere(new Wrapper().and("p_id =", productSpu.getPId()));
        //删除商品参数中间表数据
        productParamMappingService.deleteByWhere(new Wrapper().and("p_id = ", productSpu.getPId()));
        //删除商品详情
        productDetailService.deleteByWhere(new Wrapper().and("p_id = ", productSpu.getPId()));
        //删除商品图片中间表数据
        productPictureMappingService.deleteByWhere(new Wrapper().and("p_id = ", productSpu.getPId()));
    }

    /**
     * 获取商品起购量
     * 零售型商品直接获取，
     * 批发型商品获取商品阶梯价的最低价
     * 零售加批发型商品根据用户类型获取，采购商类型获取商品阶梯价的最低价，非采购商不限制起购量，默认是1
     *
     * @param productSpu
     * @return
     */
    public String getPurchasingAmount(ProductSpu productSpu) {
        String purchasingAmount = "1";
        if (productSpu == null || StringUtils.isBlank(productSpu.getPurchasingAmount())) {
            return purchasingAmount;
        }
        //销售类型type:1零售，2批发，3混批
        if ("1".equals(productSpu.getType())) {
            return productSpu.getPurchasingAmount();
        }
        UserMain userMain = SsoUtils.getUserMain();
        if ((userMain == null || !userMain.isTypeUserPurchaser()) && "3".equals(productSpu.getType())) {
            return purchasingAmount;
        }
        List<ProductSectionPrice> productSectionPriceList = productSpu.getProductSectionPriceList();
        if (productSectionPriceList.isEmpty() || StringUtils.isBlank(productSectionPriceList.get(0).getSection())) {
            return purchasingAmount;
        }
        return productSectionPriceList.get(0).getSection();
    }
}