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
package com.sicheng.wap.service;

import com.sicheng.admin.site.dao.SiteRecommendProductCategoryDao;
import com.sicheng.admin.site.entity.SiteRecommendProductCategory;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    private SiteRecommendProductCategoryDao siteRecommendProductCategoryDao;

    /**
     * 根据id查询商品分类的推荐位
     *
     * @param categoryId
     * @return
     */
    public List<String> queryProductCategoryRecommend(Long categoryId) {
        return siteRecommendProductCategoryDao.queryProductCategoryRecommend(categoryId);
    }
}