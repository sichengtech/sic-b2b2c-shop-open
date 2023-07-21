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
package com.sicheng.admin.store.entity;

import com.sicheng.admin.store.dao.StoreCategoryDao;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺商品分类 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-13
 */
public class StoreCategory extends StoreCategoryBase<StoreCategory> {

    private static final long serialVersionUID = 1L;

    public StoreCategory() {
        super();
    }

    public StoreCategory(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private Long level; //等级

    public Long getLevel() {
        String[] storeCategoryIds = this.getParentIds().split(",");
        if (storeCategoryIds.length == 1) {
            level = 1L;
        }
        if (storeCategoryIds.length == 2) {
            level = 2L;
        }
        return level;
    }

    /**
     * 一对多，当前分类下的子分类
     */
    private List<StoreCategory> subsetStoreCategory;

    public List<StoreCategory> getSubsetStoreCategory() {
        if (this.getStoreId() != null && this.getStoreCategoryId() != null) {
            StoreCategoryDao dao = SpringContextHolder.getBean(StoreCategoryDao.class);
            Wrapper wrapper = new Wrapper();
            wrapper.and("store_id = ", this.getStoreId());
            wrapper.and("parent_id = ", this.getStoreCategoryId());
            Page<StoreCategory> page = new Page<>();
            page.setPageNo(1);
            page.setPageSize(9999);
            this.subsetStoreCategory = dao.selectByWhere(page, wrapper);
                for (StoreCategory buffer : this.subsetStoreCategory) {
                buffer.setCreateDate(null);
                buffer.setUpdateDate(null);
                buffer.setIsOpen(null);
                buffer.setSort(null);
            }
        }
        return subsetStoreCategory;
    }
}