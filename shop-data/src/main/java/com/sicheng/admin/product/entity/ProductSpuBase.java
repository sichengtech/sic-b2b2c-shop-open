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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品 Entity 父类
 *
 * @author 赵磊
 * @version 2017-10-12
 */
public class ProductSpuBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // id
    private String name;                    // 商品名称
    private Long categoryId;                // 商品分类（平台）
    private Long storeCategoryId;           // 商品分类（本店）
    private Long storeId;                   // 关联(店铺表)
    private String status;                  // 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
    private String image;                   // 封面图path，冗余，用于搜索列表页
    private Long brandId;                   // 品牌
    private Long uId;                       // 关联(卖家扩展表)
    private String nameSub;                 // 副标题、商品卖点,显示名称下面的小红字
    private String unit;                    // 计量单位，从计量单位表取中文值冗余在这里
    private String type;                    // 1零售型、2批发型
    private String isGift;                  // 是否为赠品，0非，1是
    private String isRecommend;             // 是否推荐，0非，1是
    private Integer recommendSort;          // 推荐排序
    private BigDecimal marketPrice;         // 市场价（只用于显示）
    private String point;                   // 赠送积分
    private Double weight;                  // 重量
    private Double volume;                  // 体积
    private String benefit;                 // 优惠
    private String invoice;                 // 发票，0不可开发票，1可开发票
    private String action;                  // 是否参加促销活动，0否，1是
    private String deliverGoodsDate;        // 发货日期，1天内，2天内，3天内
    private String purchasingAmount;        // 起购量
    private String expressType;             // 运费方式，0固定运费，1使用运费模板
    private BigDecimal expressPrice;        // 运费价格,express_type=0时有值
    private Long ltId;                      // 运费模板的ID,express_type=1时有值
    private String cause;                   // 禁售原因，审核失败原因
    private Date shelfTime;                 // 上架时间
    @JsonIgnore
    private String bak1;                    // 备用字段
    @JsonIgnore
    private String bak2;                    // 备用字段
    @JsonIgnore
    private String bak3;                    // 备用字段
    @JsonIgnore
    private String bak4;                    // 备用字段
    @JsonIgnore
    private String bak5;                    // 备用字段
    @JsonIgnore
    private String bak6;                    // 备用字段
    @JsonIgnore
    private String bak7;                    // 备用字段
    @JsonIgnore
    private String bak8;                    // 备用字段
    @JsonIgnore
    private String bak9;                    // 备用字段
    @JsonIgnore
    private String bak10;                   // 备用字段
    @JsonIgnore
    private String bak11;                   // 备用字段
    @JsonIgnore
    private String bak12;                   // 备用字段
    @JsonIgnore
    private String bak13;                   // 备用字段
    @JsonIgnore
    private String bak14;                   // 备用字段
    @JsonIgnore
    private String bak15;                   // 备用字段
    @JsonIgnore
    private Integer n1;                     // 备用字段
    @JsonIgnore
    private Integer n2;                     // 备用字段
    @JsonIgnore
    private Integer n3;                     // 备用字段
    @JsonIgnore
    private Double f1;                      // 备用字段
    @JsonIgnore
    private Double f2;                      // 备用字段
    @JsonIgnore
    private Double f3;                      // 备用字段
    @JsonIgnore
    private Date d1;                        // 备用字段
    @JsonIgnore
    private Date d2;                        // 备用字段
    @JsonIgnore
    private Date d3;                        // 备用字段
    private String publish;                 // 发布，0放入仓库中，1立即发布
    private Date beginShelfTime;            // 开始 上架时间
    private Date endShelfTime;              // 结束 上架时间
    private Date beginCreateDate;           // 开始 创建日期
    private Date endCreateDate;             // 结束 创建日期
    private Date beginUpdateDate;           // 开始 修改日期
    private Date endUpdateDate;             // 结束 修改日期
    private Date beginD1;                   // 开始 备用字段
    private Date endD1;                     // 结束 备用字段
    private Date beginD2;                   // 开始 备用字段
    private Date endD2;                     // 结束 备用字段
    private Date beginD3;                   // 开始 备用字段
    private Date endD3;                     // 结束 备用字段

    public ProductSpuBase() {
        super();
    }

    public ProductSpuBase(Long id) {
        super(id);
        this.pId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return pId;
    }

    /**
     * 描述: 设置ID
     *
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
        this.pId = id;
    }

    /**
     * getter pId(id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(id)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter name(商品名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(商品名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter categoryId(商品分类（平台）)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(商品分类（平台）)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter storeCategoryId(商品分类（本店）)
     */
    public Long getStoreCategoryId() {
        return storeCategoryId;
    }

    /**
     * setter storeCategoryId(商品分类（本店）)
     */
    public void setStoreCategoryId(Long storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    /**
     * getter storeId(关联(店铺表))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(关联(店铺表))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter status(10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter image(封面图path，冗余，用于搜索列表页)
     */
    public String getImage() {
        return image;
    }

    /**
     * setter image(封面图path，冗余，用于搜索列表页)
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * getter brandId(品牌)
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * setter brandId(品牌)
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * getter uId(关联(卖家扩展表))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(关联(卖家扩展表))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter nameSub(副标题、商品卖点,显示名称下面的小红字)
     */
    public String getNameSub() {
        return nameSub;
    }

    /**
     * setter nameSub(副标题、商品卖点,显示名称下面的小红字)
     */
    public void setNameSub(String nameSub) {
        this.nameSub = nameSub;
    }

    /**
     * getter unit(计量单位，从计量单位表取中文值冗余在这里)
     */
    public String getUnit() {
        return unit;
    }

    /**
     * setter unit(计量单位，从计量单位表取中文值冗余在这里)
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * getter type(1零售型、2批发型)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(1零售型、2批发型)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter isGift(是否为赠品，0非，1是)
     */
    public String getIsGift() {
        return isGift;
    }

    /**
     * setter isGift(是否为赠品，0非，1是)
     */
    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    /**
     * getter isRecommend(是否推荐，0非，1是)
     */
    public String getIsRecommend() {
        return isRecommend;
    }

    /**
     * setter isRecommend(是否推荐，0非，1是)
     */
    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * getter recommendSort(推荐排序)
     */
    public Integer getRecommendSort() {
        return recommendSort;
    }

    /**
     * setter recommendSort(推荐排序)
     */
    public void setRecommendSort(Integer recommendSort) {
        this.recommendSort = recommendSort;
    }

    /**
     * getter marketPrice(市场价（只用于显示）)
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * setter marketPrice(市场价（只用于显示）)
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * getter point(赠送积分)
     */
    public String getPoint() {
        return point;
    }

    /**
     * setter point(赠送积分)
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * getter weight(重量)
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * setter weight(重量)
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * getter volume(体积)
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * setter volume(体积)
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * getter benefit(优惠)
     */
    public String getBenefit() {
        return benefit;
    }

    /**
     * setter benefit(优惠)
     */
    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    /**
     * getter invoice(发票，0不可开发票，1可开发票)
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * setter invoice(发票，0不可开发票，1可开发票)
     */
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    /**
     * getter action(是否参加促销活动，0否，1是)
     */
    public String getAction() {
        return action;
    }

    /**
     * setter action(是否参加促销活动，0否，1是)
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * getter deliverGoodsDate(发货日期，1天内，2天内，3天内)
     */
    public String getDeliverGoodsDate() {
        return deliverGoodsDate;
    }

    /**
     * setter deliverGoodsDate(发货日期，1天内，2天内，3天内)
     */
    public void setDeliverGoodsDate(String deliverGoodsDate) {
        this.deliverGoodsDate = deliverGoodsDate;
    }

    /**
     * getter purchasingAmount(起购量)
     */
    public String getPurchasingAmount() {
        return purchasingAmount;
    }

    /**
     * setter purchasingAmount(起购量)
     */
    public void setPurchasingAmount(String purchasingAmount) {
        this.purchasingAmount = purchasingAmount;
    }

    /**
     * getter expressType(运费方式，0固定运费，1使用运费模板)
     */
    public String getExpressType() {
        return expressType;
    }

    /**
     * setter expressType(运费方式，0固定运费，1使用运费模板)
     */
    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    /**
     * getter expressPrice(运费价格,express_type=0时有值)
     */
    public BigDecimal getExpressPrice() {
        return expressPrice;
    }

    /**
     * setter expressPrice(运费价格,express_type=0时有值)
     */
    public void setExpressPrice(BigDecimal expressPrice) {
        this.expressPrice = expressPrice;
    }

    /**
     * getter ltId(运费模板的ID,express_type=1时有值)
     */
    public Long getLtId() {
        return ltId;
    }

    /**
     * setter ltId(运费模板的ID,express_type=1时有值)
     */
    public void setLtId(Long ltId) {
        this.ltId = ltId;
    }

    /**
     * getter cause(禁售原因，审核失败原因)
     */
    public String getCause() {
        return cause;
    }

    /**
     * setter cause(禁售原因，审核失败原因)
     */
    public void setCause(String cause) {
        this.cause = cause;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter shelfTime(上架时间)
     */
    public Date getShelfTime() {
        return shelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    /**
     * getter bak1(备用字段)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }

    /**
     * getter bak6(备用字段)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
    }

    /**
     * getter bak11(备用字段)
     */
    public String getBak11() {
        return bak11;
    }

    /**
     * setter bak11(备用字段)
     */
    public void setBak11(String bak11) {
        this.bak11 = bak11;
    }

    /**
     * getter bak12(备用字段)
     */
    public String getBak12() {
        return bak12;
    }

    /**
     * setter bak12(备用字段)
     */
    public void setBak12(String bak12) {
        this.bak12 = bak12;
    }

    /**
     * getter bak13(备用字段)
     */
    public String getBak13() {
        return bak13;
    }

    /**
     * setter bak13(备用字段)
     */
    public void setBak13(String bak13) {
        this.bak13 = bak13;
    }

    /**
     * getter bak14(备用字段)
     */
    public String getBak14() {
        return bak14;
    }

    /**
     * setter bak14(备用字段)
     */
    public void setBak14(String bak14) {
        this.bak14 = bak14;
    }

    /**
     * getter bak15(备用字段)
     */
    public String getBak15() {
        return bak15;
    }

    /**
     * setter bak15(备用字段)
     */
    public void setBak15(String bak15) {
        this.bak15 = bak15;
    }

    /**
     * getter n1(备用字段)
     */
    public Integer getN1() {
        return n1;
    }

    /**
     * setter n1(备用字段)
     */
    public void setN1(Integer n1) {
        this.n1 = n1;
    }

    /**
     * getter n2(备用字段)
     */
    public Integer getN2() {
        return n2;
    }

    /**
     * setter n2(备用字段)
     */
    public void setN2(Integer n2) {
        this.n2 = n2;
    }

    /**
     * getter n3(备用字段)
     */
    public Integer getN3() {
        return n3;
    }

    /**
     * setter n3(备用字段)
     */
    public void setN3(Integer n3) {
        this.n3 = n3;
    }

    /**
     * getter f1(备用字段)
     */
    public Double getF1() {
        return f1;
    }

    /**
     * setter f1(备用字段)
     */
    public void setF1(Double f1) {
        this.f1 = f1;
    }

    /**
     * getter f2(备用字段)
     */
    public Double getF2() {
        return f2;
    }

    /**
     * setter f2(备用字段)
     */
    public void setF2(Double f2) {
        this.f2 = f2;
    }

    /**
     * getter f3(备用字段)
     */
    public Double getF3() {
        return f3;
    }

    /**
     * setter f3(备用字段)
     */
    public void setF3(Double f3) {
        this.f3 = f3;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d1(备用字段)
     */
    public Date getD1() {
        return d1;
    }

    /**
     * setter d1(备用字段)
     */
    public void setD1(Date d1) {
        this.d1 = d1;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d2(备用字段)
     */
    public Date getD2() {
        return d2;
    }

    /**
     * setter d2(备用字段)
     */
    public void setD2(Date d2) {
        this.d2 = d2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d3(备用字段)
     */
    public Date getD3() {
        return d3;
    }

    /**
     * setter d3(备用字段)
     */
    public void setD3(Date d3) {
        this.d3 = d3;
    }

    /**
     * getter publish(发布，0放入仓库中，1立即发布)
     */
    public String getPublish() {
        return publish;
    }

    /**
     * setter publish(发布，0放入仓库中，1立即发布)
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * getter shelfTime(上架时间)
     */
    public Date getBeginShelfTime() {
        return beginShelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setBeginShelfTime(Date beginShelfTime) {
        this.beginShelfTime = beginShelfTime;
    }

    /**
     * getter shelfTime(上架时间)
     */
    public Date getEndShelfTime() {
        return endShelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setEndShelfTime(Date endShelfTime) {
        this.endShelfTime = endShelfTime;
    }

    /**
     * getter createDate(创建日期)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建日期)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建日期)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建日期)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    /**
     * getter updateDate(修改日期)
     */
    public Date getBeginUpdateDate() {
        return beginUpdateDate;
    }

    /**
     * setter updateDate(修改日期)
     */
    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    /**
     * getter updateDate(修改日期)
     */
    public Date getEndUpdateDate() {
        return endUpdateDate;
    }

    /**
     * setter updateDate(修改日期)
     */
    public void setEndUpdateDate(Date endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }

    /**
     * getter d1(备用字段)
     */
    public Date getBeginD1() {
        return beginD1;
    }

    /**
     * setter d1(备用字段)
     */
    public void setBeginD1(Date beginD1) {
        this.beginD1 = beginD1;
    }

    /**
     * getter d1(备用字段)
     */
    public Date getEndD1() {
        return endD1;
    }

    /**
     * setter d1(备用字段)
     */
    public void setEndD1(Date endD1) {
        this.endD1 = endD1;
    }

    /**
     * getter d2(备用字段)
     */
    public Date getBeginD2() {
        return beginD2;
    }

    /**
     * setter d2(备用字段)
     */
    public void setBeginD2(Date beginD2) {
        this.beginD2 = beginD2;
    }

    /**
     * getter d2(备用字段)
     */
    public Date getEndD2() {
        return endD2;
    }

    /**
     * setter d2(备用字段)
     */
    public void setEndD2(Date endD2) {
        this.endD2 = endD2;
    }

    /**
     * getter d3(备用字段)
     */
    public Date getBeginD3() {
        return beginD3;
    }

    /**
     * setter d3(备用字段)
     */
    public void setBeginD3(Date beginD3) {
        this.beginD3 = beginD3;
    }

    /**
     * getter d3(备用字段)
     */
    public Date getEndD3() {
        return endD3;
    }

    /**
     * setter d3(备用字段)
     */
    public void setEndD3(Date endD3) {
        this.endD3 = endD3;
    }

}