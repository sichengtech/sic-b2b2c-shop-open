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
package com.sicheng.admin.app.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * App引导页 Entity 父类
 *
 * @author 贺东泽
 * @version 2019-11-07
 */
public class AppAdBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String backgroundColor;         // 背景颜色
    private String backgroundImage;         // 背景图
    private String isShow;                  // 是否展示：0为否，1为是
    private String wordOne;                 // 文本描述1
    private String wordOneColor;            // 文本1颜色
    private String wordThree;               // 文本描述3
    private String wordTwo;                 // 文本描述2
    private String buttonWord;              // 第三页按钮文字
    private String wordTwoColor;            // 文本2颜色
    private String buttonColour;            // 第三页按钮颜色
    private String wordThreeColor;          // 文本3颜色
    private String bak1;                    // 备用字段1
    private String bak2;                    // 备用字段2
    private String bak3;                    // 备用字段3
    private String bak4;                    // 备用字段4
    private String bak5;                    // 备用字段5
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 修改日期
    private Date endUpdateDate;             // 结束 修改日期

    public AppAdBase() {
        super();
    }

    public AppAdBase(Long id) {
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
     * getter backgroundColor(背景颜色)
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * setter backgroundColor(背景颜色)
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * getter backgroundImage(背景图)
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * setter backgroundImage(背景图)
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * getter isShow(是否展示：0为否，1为是)
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * setter isShow(是否展示：0为否，1为是)
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * getter wordOne(文本描述1)
     */
    public String getWordOne() {
        return wordOne;
    }

    /**
     * setter wordOne(文本描述1)
     */
    public void setWordOne(String wordOne) {
        this.wordOne = wordOne;
    }

    /**
     * getter wordOneColor(文本1颜色)
     */
    public String getWordOneColor() {
        return wordOneColor;
    }

    /**
     * setter wordOneColor(文本1颜色)
     */
    public void setWordOneColor(String wordOneColor) {
        this.wordOneColor = wordOneColor;
    }

    /**
     * getter wordThree(文本描述3)
     */
    public String getWordThree() {
        return wordThree;
    }

    /**
     * setter wordThree(文本描述3)
     */
    public void setWordThree(String wordThree) {
        this.wordThree = wordThree;
    }

    /**
     * getter wordTwo(文本描述2)
     */
    public String getWordTwo() {
        return wordTwo;
    }

    /**
     * setter wordTwo(文本描述2)
     */
    public void setWordTwo(String wordTwo) {
        this.wordTwo = wordTwo;
    }

    /**
     * getter buttonWord(第三页按钮文字)
     */
    public String getButtonWord() {
        return buttonWord;
    }

    /**
     * setter buttonWord(第三页按钮文字)
     */
    public void setButtonWord(String buttonWord) {
        this.buttonWord = buttonWord;
    }

    /**
     * getter wordTwoColor(文本2颜色)
     */
    public String getWordTwoColor() {
        return wordTwoColor;
    }

    /**
     * setter wordTwoColor(文本2颜色)
     */
    public void setWordTwoColor(String wordTwoColor) {
        this.wordTwoColor = wordTwoColor;
    }

    /**
     * getter buttonColour(第三页按钮颜色)
     */
    public String getButtonColour() {
        return buttonColour;
    }

    /**
     * setter buttonColour(第三页按钮颜色)
     */
    public void setButtonColour(String buttonColour) {
        this.buttonColour = buttonColour;
    }

    /**
     * getter wordThreeColor(文本3颜色)
     */
    public String getWordThreeColor() {
        return wordThreeColor;
    }

    /**
     * setter wordThreeColor(文本3颜色)
     */
    public void setWordThreeColor(String wordThreeColor) {
        this.wordThreeColor = wordThreeColor;
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
     * getter createDate(创建时间)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间)
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

}