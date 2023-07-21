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
package com.sicheng.admin.site.service;

import com.sicheng.admin.site.dao.SiteRecommendItemDao;
import com.sicheng.admin.site.entity.SiteRecommendItem;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 推荐位详情 Service
 *
 * @author fanxiuxiu
 * @version 2017-09-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteRecommendItemService extends CrudService<SiteRecommendItemDao, SiteRecommendItem> {

    /**
     * 添加推荐位详情
     *
     * @param siteRecommendItemList 推荐位详情list
     * @param recommendId           推荐位id
     */
    @Transactional(rollbackFor = Exception.class)
    public void addSiteRecommendItem(List<SiteRecommendItem> siteRecommendItemList, Long recommendId) {
        if (recommendId != null) {
            dao.deleteByWhere(new Wrapper().and("recommend_id=", recommendId));
        }
        if (siteRecommendItemList.size() > 0) {
            super.insertBatch(siteRecommendItemList);
        }
    }

}