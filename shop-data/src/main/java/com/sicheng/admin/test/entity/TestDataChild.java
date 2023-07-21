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

import com.sicheng.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 主子表生成Entity
 *
 * @author zhaolei
 * @version 2015-04-06
 */
public class TestDataChild extends DataEntity<TestDataChild> {

    private static final long serialVersionUID = 1L;
    private TestDataMain testDataMain;        // 业务主表 父类
    private String name;        // 名称

    public TestDataChild() {
        super();
    }

    public TestDataChild(Long id) {
        super(id);
    }

    public TestDataChild(TestDataMain testDataMain) {
        this.testDataMain = testDataMain;
    }

    @Length(min = 0, max = 64, message = "业务主表长度必须介于 0 和 64 之间")
    public TestDataMain getTestDataMain() {
        return testDataMain;
    }

    public void setTestDataMain(TestDataMain testDataMain) {
        this.testDataMain = testDataMain;
    }

    @Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}