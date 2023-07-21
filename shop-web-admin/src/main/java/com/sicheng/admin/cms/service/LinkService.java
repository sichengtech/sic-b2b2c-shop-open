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

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.dao.LinkDao;
import com.sicheng.admin.cms.entity.Link;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 友情链接 Service
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class LinkService extends CrudService<LinkDao, Link> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Transactional(readOnly = false)
    public Page<Link> findPage(Page<Link> page, Link link, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) super.cache.get(CacheConstant.ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_LINK);
        if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null && updateExpiredWeightDate.getTime() < new Date().getTime())) {
            dao.updateExpiredWeight(link);
            super.cache.put(CacheConstant.ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_LINK, DateUtils.addHours(new Date(), 6), 3600L);
        }
        link.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

        return findPage1(page, link);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Link link, Boolean isRe) {
        link.setDelFlag(isRe != null && isRe ? Link.DEL_FLAG_NORMAL : Link.DEL_FLAG_DELETE);
        dao.delete(link);
    }

    /**
     * 通过编号获取内容标题
     */
    public List<Object[]> findByIds(String ids) {
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        if (idss.length > 0) {
            List<Link> l = dao.findByIdIn(idss);
            for (Link e : l) {
                list.add(new Object[]{e.getId(), StringUtils.abbr(e.getTitle(), 50)});
            }
        }
        return list;
    }

    public Page<Link> findPage1(Page<Link> page, Link link) {
        Wrapper w=new Wrapper(link);
        w.orderBy("a.id DESC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public Link get(Long id) {
        return dao.get(id);
    }

    @Transactional(readOnly = false)
    public void save(Link link) {
        if (link.getIsNewRecord()) {
            link.preInsert(UserUtils.getUser());
            dao.insert(link);
        } else {
            link.preUpdate(UserUtils.getUser());
            dao.update(link);
        }
    }

}