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
package com.sicheng.admin.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 主子表生成Entity
 *
 * @author zhaolei
 * @version 2015-04-06
 */
public class TestDataMain extends DataEntity<TestDataMain> {

    private static final long serialVersionUID = 1L;
    private User user;        // 归属用户
    private Office office;        // 归属部门
    private Area area;        // 归属区域
    private String name;        // 名称
    private String sex;        // 性别
    private Date inDate;        // 加入日期
    private List<TestDataChild> testDataChildList = Lists.newArrayList();        // 子表列表

    public TestDataMain() {
        super();
    }

    public TestDataMain(Long id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 1, message = "性别长度必须介于 0 和 1 之间")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public List<TestDataChild> getTestDataChildList() {
        return testDataChildList;
    }

    public void setTestDataChildList(List<TestDataChild> testDataChildList) {
        this.testDataChildList = testDataChildList;
    }
}