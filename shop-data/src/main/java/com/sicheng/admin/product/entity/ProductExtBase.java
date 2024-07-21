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
package com.sicheng.admin.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 商品扩展表 Entity 父类
 *
 * @author cl
 * @version 2017-09-25
 */
public class ProductExtBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // 商品ID
    private String carIds;                  // 商品&ndash;车型一对一关系。所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开
    @JsonIgnore
    private String bak1;                    // 备用字段1
    @JsonIgnore
    private String bak2;                    // 备用字段2
    @JsonIgnore
    private String bak3;                    // 备用字段3
    @JsonIgnore
    private String bak4;                    // 备用字段4
    @JsonIgnore
    private String bak5;                    // 备用字段5
    @JsonIgnore
    private String bak6;                    // 备用字段6
    @JsonIgnore
    private String bak7;                    // 备用字段7
    @JsonIgnore
    private String bak8;                    // 备用字段8
    @JsonIgnore
    private String bak9;                    // 备用字段9
    @JsonIgnore
    private String bak10;                   // 备用字段10
    @JsonIgnore
    private String bak11;                   // 备用字段11
    @JsonIgnore
    private String bak12;                   // 备用字段12
    @JsonIgnore
    private String bak13;                   // 备用字段13
    @JsonIgnore
    private String bak14;                   // 备用字段14
    @JsonIgnore
    private String bak15;                   // 备用字段15
    @JsonIgnore
    private Integer n1;                     // 备用字段n1
    @JsonIgnore
    private Integer n2;                     // 备用字段n2
    @JsonIgnore
    private Integer n3;                     // 备用字段n3
    @JsonIgnore
    private Double f1;                      // 备用字段f1
    @JsonIgnore
    private Double f2;                      // 备用字段f2
    @JsonIgnore
    private Double f3;                      // 备用字段f3
    @JsonIgnore
    private Date d1;                        // 备用字段d1
    @JsonIgnore
    private Date d2;                        // 备用字段d2
    @JsonIgnore
    private Date d3;                        // 备用字段d3
    private String allCar;                  // 是否全车系（0否，1是）
    @JsonIgnore
    private Date beginD1;                   // 开始 备用字段d1
    @JsonIgnore
    private Date endD1;                     // 结束 备用字段d1
    @JsonIgnore
    private Date beginD2;                   // 开始 备用字段d2
    @JsonIgnore
    private Date endD2;                     // 结束 备用字段d2
    @JsonIgnore
    private Date beginD3;                   // 开始 备用字段d3
    @JsonIgnore
    private Date endD3;                     // 结束 备用字段d3

    public ProductExtBase() {
        super();
    }

    public ProductExtBase(Long id) {
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
     * getter pId(商品ID)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品ID)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter carIds(商品&ndash;车型一对一关系。所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开)
     */
    public String getCarIds() {
        return carIds;
    }

    /**
     * setter carIds(商品&ndash;车型一对一关系。所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开)
     */
    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    /**
     * getter bak1(备用字段1)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段1)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段2)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段2)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段3)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段3)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段4)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段4)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段5)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段5)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }

    /**
     * getter bak6(备用字段6)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段6)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段7)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段7)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段8)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段8)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段9)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段9)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段10)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段10)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
    }

    /**
     * getter bak11(备用字段11)
     */
    public String getBak11() {
        return bak11;
    }

    /**
     * setter bak11(备用字段11)
     */
    public void setBak11(String bak11) {
        this.bak11 = bak11;
    }

    /**
     * getter bak12(备用字段12)
     */
    public String getBak12() {
        return bak12;
    }

    /**
     * setter bak12(备用字段12)
     */
    public void setBak12(String bak12) {
        this.bak12 = bak12;
    }

    /**
     * getter bak13(备用字段13)
     */
    public String getBak13() {
        return bak13;
    }

    /**
     * setter bak13(备用字段13)
     */
    public void setBak13(String bak13) {
        this.bak13 = bak13;
    }

    /**
     * getter bak14(备用字段14)
     */
    public String getBak14() {
        return bak14;
    }

    /**
     * setter bak14(备用字段14)
     */
    public void setBak14(String bak14) {
        this.bak14 = bak14;
    }

    /**
     * getter bak15(备用字段15)
     */
    public String getBak15() {
        return bak15;
    }

    /**
     * setter bak15(备用字段15)
     */
    public void setBak15(String bak15) {
        this.bak15 = bak15;
    }

    /**
     * getter n1(备用字段n1)
     */
    public Integer getN1() {
        return n1;
    }

    /**
     * setter n1(备用字段n1)
     */
    public void setN1(Integer n1) {
        this.n1 = n1;
    }

    /**
     * getter n2(备用字段n2)
     */
    public Integer getN2() {
        return n2;
    }

    /**
     * setter n2(备用字段n2)
     */
    public void setN2(Integer n2) {
        this.n2 = n2;
    }

    /**
     * getter n3(备用字段n3)
     */
    public Integer getN3() {
        return n3;
    }

    /**
     * setter n3(备用字段n3)
     */
    public void setN3(Integer n3) {
        this.n3 = n3;
    }

    /**
     * getter f1(备用字段f1)
     */
    public Double getF1() {
        return f1;
    }

    /**
     * setter f1(备用字段f1)
     */
    public void setF1(Double f1) {
        this.f1 = f1;
    }

    /**
     * getter f2(备用字段f2)
     */
    public Double getF2() {
        return f2;
    }

    /**
     * setter f2(备用字段f2)
     */
    public void setF2(Double f2) {
        this.f2 = f2;
    }

    /**
     * getter f3(备用字段f3)
     */
    public Double getF3() {
        return f3;
    }

    /**
     * setter f3(备用字段f3)
     */
    public void setF3(Double f3) {
        this.f3 = f3;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d1(备用字段d1)
     */
    public Date getD1() {
        return d1;
    }

    /**
     * setter d1(备用字段d1)
     */
    public void setD1(Date d1) {
        this.d1 = d1;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d2(备用字段d2)
     */
    public Date getD2() {
        return d2;
    }

    /**
     * setter d2(备用字段d2)
     */
    public void setD2(Date d2) {
        this.d2 = d2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter d3(备用字段d3)
     */
    public Date getD3() {
        return d3;
    }

    /**
     * setter d3(备用字段d3)
     */
    public void setD3(Date d3) {
        this.d3 = d3;
    }

    /**
     * getter allCar(是否全车系（0否，1是）)
     */
    public String getAllCar() {
        return allCar;
    }

    /**
     * setter allCar(是否全车系（0否，1是）)
     */
    public void setAllCar(String allCar) {
        this.allCar = allCar;
    }

    /**
     * getter d1(备用字段d1)
     */
    public Date getBeginD1() {
        return beginD1;
    }

    /**
     * setter d1(备用字段d1)
     */
    public void setBeginD1(Date beginD1) {
        this.beginD1 = beginD1;
    }

    /**
     * getter d1(备用字段d1)
     */
    public Date getEndD1() {
        return endD1;
    }

    /**
     * setter d1(备用字段d1)
     */
    public void setEndD1(Date endD1) {
        this.endD1 = endD1;
    }

    /**
     * getter d2(备用字段d2)
     */
    public Date getBeginD2() {
        return beginD2;
    }

    /**
     * setter d2(备用字段d2)
     */
    public void setBeginD2(Date beginD2) {
        this.beginD2 = beginD2;
    }

    /**
     * getter d2(备用字段d2)
     */
    public Date getEndD2() {
        return endD2;
    }

    /**
     * setter d2(备用字段d2)
     */
    public void setEndD2(Date endD2) {
        this.endD2 = endD2;
    }

    /**
     * getter d3(备用字段d3)
     */
    public Date getBeginD3() {
        return beginD3;
    }

    /**
     * setter d3(备用字段d3)
     */
    public void setBeginD3(Date beginD3) {
        this.beginD3 = beginD3;
    }

    /**
     * getter d3(备用字段d3)
     */
    public Date getEndD3() {
        return endD3;
    }

    /**
     * setter d3(备用字段d3)
     */
    public void setEndD3(Date endD3) {
        this.endD3 = endD3;
    }

}