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
package com.sicheng.admin.site.service;

import com.sicheng.admin.site.dao.SiteRecommendProductCategoryDao;
import com.sicheng.admin.site.entity.SiteRecommendProductCategory;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 推荐位与一级商品类型关系表 Service
 *
 * @author hdz
 * @version 2019-11-08
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteRecommendProductCategoryService extends CrudService<SiteRecommendProductCategoryDao, SiteRecommendProductCategory> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 绑定商品分类和推荐位
     *
     * @param categoryId 商品分类id
     * @param ids        推荐位id列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void bind(Long categoryId, String[] ids) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("category_id = ", categoryId);
        this.deleteByWhere(wrapper);
        //遍历保存
        for (String id : ids) {
            SiteRecommendProductCategory siteRecommendProductCategory = new SiteRecommendProductCategory();
            siteRecommendProductCategory.setCategoryId(categoryId);
            siteRecommendProductCategory.setRecommendId(new Long(id));
            this.insertSelective(siteRecommendProductCategory);
        }
    }
}