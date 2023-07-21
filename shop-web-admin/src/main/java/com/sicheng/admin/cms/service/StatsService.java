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
package com.sicheng.admin.cms.service;

import com.sicheng.admin.cms.dao.ArticleDao;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.common.service.BaseService;
import com.sicheng.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计Service
 *
 * @author zhaolei
 * @version 2013-05-21
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StatsService extends BaseService {

    @Autowired
    private ArticleDao articleDao;

    public List<Category> article(Map<String, Object> paramMap) {
        Category category = new Category();

        Site site = new Site();
        site.setId(Site.getCurrentSiteId());
        category.setSite(site);

        Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
        if (beginDate == null) {
            beginDate = DateUtils.setDays(new Date(), 1);
            paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
        }
        category.setBeginDate(beginDate);
        Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
        if (endDate == null) {
            endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
            paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
        }
        category.setEndDate(endDate);

        Long categoryId = (Long) paramMap.get("categoryId");
        if (categoryId != null && !("".equals(categoryId))) {
            category.setId(categoryId);
            category.setParentIds(String.valueOf(categoryId));
        }

        Long officeId = (Long) (paramMap.get("officeId"));
        Office office = new Office();
        if (officeId != null && !("".equals(officeId))) {
            office.setId(officeId);
            category.setOffice(office);
        } else {
            category.setOffice(office);
        }

        List<Category> list = articleDao.findStats(category);
        return list;
    }

}
