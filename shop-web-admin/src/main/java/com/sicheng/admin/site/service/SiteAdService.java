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

import com.sicheng.admin.site.dao.SiteAdContentDao;
import com.sicheng.admin.site.dao.SiteAdDao;
import com.sicheng.admin.site.entity.SiteAd;
import com.sicheng.admin.site.entity.SiteAdContent;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 广告位 Service
 *
 * @author 蔡龙
 * @version 2017-01-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteAdService extends CrudService<SiteAdDao, SiteAd> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private SiteAdDao SiteAdDao;

    @Autowired
    private SiteAdContentDao siteAdContentDao;

    /**
     * 新增广告
     *
     * @param siteAd
     * @param siteAdContent
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean changeSiteAd(SiteAd siteAd, SiteAdContent siteAdContent) {
        if (siteAd.getAdId() != null) {
            //修改广告位内容
            SiteAdDao.updateByIdSelective(siteAd);
        } else {
            //把广告位存到广告表中
            SiteAdDao.insertSelective(siteAd);
        }
        //把广告表原先表的所有数据保存成无效状态
        SiteAdContent scontent = new SiteAdContent();
        scontent.setStatus("0");//0删除、1有效
        SiteAdContent scontent1 = new SiteAdContent();
        scontent1.setAdId(siteAd.getId());
        siteAdContentDao.updateByWhereSelective(scontent, new Wrapper(scontent1));
        //往广告位表中存一条新有效记录
        siteAdContent.setAdId(siteAd.getId());
        siteAdContent.setStatus("1");//0删除、1有效
        siteAdContentDao.insertSelective(siteAdContent);
        return true;
    }

    /**
     * 删除广告
     *
     * @param siteAd
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAd(SiteAd siteAd) {
        //删除广告位
        SiteAdDao.deleteById(siteAd.getAdId());
        //删除广告位内容
        SiteAdContent siteAdContent = new SiteAdContent();
        siteAdContent.setAdId(siteAd.getAdId());
        siteAdContentDao.deleteByWhere(new Wrapper(siteAdContent));
        return true;
    }
}