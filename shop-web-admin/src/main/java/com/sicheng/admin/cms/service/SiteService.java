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

import com.sicheng.admin.cms.dao.SiteDao;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 站点表 Service
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteService extends CrudService<SiteDao, Site> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public Page<Site> findPage(Page<Site> page, Site site) {
        site.getSqlMap().put("site", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return findPage1(page, site);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Site site) {
        if (site.getCopyright() != null) {
            site.setCopyright(StringEscapeUtils.unescapeHtml4(site.getCopyright()));//转回来（还原）
        }
        save1(site);
    }

    @Transactional(readOnly = false)
    public void delete(Site site, Boolean isRe) {
        site.setDelFlag(isRe != null && isRe ? Site.DEL_FLAG_NORMAL : Site.DEL_FLAG_DELETE);
        delete(site);
    }

    public Page<Site> findPage1(Page<Site> page, Site site) {
        Wrapper w=new Wrapper(site);
        w.orderBy("a.id DESC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public void delete(Site site) {
        dao.delete(site);
    }

    @Transactional(readOnly = false)
    public Site get(Long id) {
        return dao.get(id);
    }

    @Transactional(readOnly = false)
    public void save1(Site site) {
        if (site.getIsNewRecord()) {
            site.preInsert(UserUtils.getUser());
            dao.insert(site);
        } else {
            site.preUpdate(UserUtils.getUser());
            dao.update(site);
        }
    }

}