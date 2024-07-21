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

import com.sicheng.admin.site.dao.SiteRecommendDao;
import com.sicheng.admin.site.dao.SiteRecommendItemDao;
import com.sicheng.admin.site.entity.SiteRecommend;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 推荐位 Service
 *
 * @author fxx
 * @version 2017-09-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteRecommendService extends CrudService<SiteRecommendDao, SiteRecommend> {

    @Autowired
    private SiteRecommendItemDao siteRecommendItemDao;

    /**
     * 删除推荐位
     *
     * @param recommendId 推荐位id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteSiteRecommend(Long recommendId) {
        if (recommendId == null) {
            return;
        }
        //删除推荐位
        dao.deleteById(recommendId);
        //删除推荐位详情
        siteRecommendItemDao.deleteByWhere(new Wrapper().and("recommend_id=", recommendId));
    }
}