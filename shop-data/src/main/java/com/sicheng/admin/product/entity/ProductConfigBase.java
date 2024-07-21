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


import com.sicheng.common.persistence.DataEntity;

/**
 * 商品设置 Entity 父类
 *
 * @author zhaolei
 * @version 2017-02-07
 */
public class ProductConfigBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String set1;                    // 设置1，新发的商品是否需要审核，0否、1是
    private String set2;                    // 设置2，禁售商品的设置，1.禁售后不允许再编辑，再售出，2.禁售后可以编辑，但编辑后必须由管理员审核，审核通过后可以再次售出，3.禁售后可以编辑，根据商品审核开关判断是否需要管理员审核
    private String set3;                    // 设置3
    private String set4;                    // 设置4
    private String set5;                    // 设置5
    private String set6;                    // 设置6
    private String set7;                    // 设置7
    private String set8;                    // 设置8
    private String set9;                    // 设置9
    private String set10;                   // 设置10

    public ProductConfigBase() {
        super();
    }

    public ProductConfigBase(Long id) {
        super(id);
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return id;
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
    }

    /**
     * getter set1(设置1，新发的商品是否需要审核，0否、1是)
     */
    public String getSet1() {
        return set1;
    }

    /**
     * setter set1(设置1，新发的商品是否需要审核，0否、1是)
     */
    public void setSet1(String set1) {
        this.set1 = set1;
    }

    /**
     * getter set2(设置2)
     */
    public String getSet2() {
        return set2;
    }

    /**
     * setter set2(设置2)
     */
    public void setSet2(String set2) {
        this.set2 = set2;
    }

    /**
     * getter set3(设置3)
     */
    public String getSet3() {
        return set3;
    }

    /**
     * setter set3(设置3)
     */
    public void setSet3(String set3) {
        this.set3 = set3;
    }

    /**
     * getter set4(设置4)
     */
    public String getSet4() {
        return set4;
    }

    /**
     * setter set4(设置4)
     */
    public void setSet4(String set4) {
        this.set4 = set4;
    }

    /**
     * getter set5(设置5)
     */
    public String getSet5() {
        return set5;
    }

    /**
     * setter set5(设置5)
     */
    public void setSet5(String set5) {
        this.set5 = set5;
    }

    /**
     * getter set6(设置6)
     */
    public String getSet6() {
        return set6;
    }

    /**
     * setter set6(设置6)
     */
    public void setSet6(String set6) {
        this.set6 = set6;
    }

    /**
     * getter set7(设置7)
     */
    public String getSet7() {
        return set7;
    }

    /**
     * setter set7(设置7)
     */
    public void setSet7(String set7) {
        this.set7 = set7;
    }

    /**
     * getter set8(设置8)
     */
    public String getSet8() {
        return set8;
    }

    /**
     * setter set8(设置8)
     */
    public void setSet8(String set8) {
        this.set8 = set8;
    }

    /**
     * getter set9(设置9)
     */
    public String getSet9() {
        return set9;
    }

    /**
     * setter set9(设置9)
     */
    public void setSet9(String set9) {
        this.set9 = set9;
    }

    /**
     * getter set10(设置10)
     */
    public String getSet10() {
        return set10;
    }

    /**
     * setter set10(设置10)
     */
    public void setSet10(String set10) {
        this.set10 = set10;
    }


}