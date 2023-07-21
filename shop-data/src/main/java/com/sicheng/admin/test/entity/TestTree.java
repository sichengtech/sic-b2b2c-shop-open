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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sicheng.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 树结构生成Entity
 *
 * @author zhaolei
 * @version 2015-04-06
 */
public class TestTree extends TreeEntity<TestTree> {

    private static final long serialVersionUID = 1L;
    private TestTree parent;        // 父级编号
    private String parentIds;        // 所有父级编号
    private String name;        // 名称
    private Integer sort;        // 排序

    public TestTree() {
        super();
    }

    public TestTree(Long id) {
        super(id);
    }

    @JsonBackReference
    @NotNull(message = "父级编号不能为空")
    public TestTree getParent() {
        return parent;
    }

    public void setParent(TestTree parent) {
        this.parent = parent;
    }

    @Length(min = 1, max = 2000, message = "所有父级编号长度必须介于 1 和 2000 之间")
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "排序不能为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : 0L;
    }
}