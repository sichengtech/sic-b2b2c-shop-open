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
package com.sicheng.admin.site.entity;

import com.sicheng.admin.site.dao.SiteRecommendItemDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 推荐位 Entity 子类，请把你的业务代码写在这里
 *
 * @author fanxiuxiu
 * @version 2017-09-25
 */
public class SiteRecommend extends SiteRecommendBase<SiteRecommend> {

    private static final long serialVersionUID = 1L;

    public SiteRecommend() {
        super();
    }

    public SiteRecommend(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    //一对多映射
    private List<SiteRecommendItem> siteRecommendItemList;//一个推荐位--多个推荐位详情

    public List<SiteRecommendItem> getSiteRecommendItemList() {
        if (siteRecommendItemList == null) {
            SiteRecommendItemDao dao = SpringContextHolder.getBean(SiteRecommendItemDao.class);
            siteRecommendItemList = dao.selectByWhere(null, new Wrapper().and("recommend_id=", this.getRecommendId()).orderBy("sort"));//排序
        }
        return siteRecommendItemList;
    }

    public void setSiteRecommendItemList(List<SiteRecommendItem> siteRecommendItemList) {
        this.siteRecommendItemList = siteRecommendItemList;
    }
}