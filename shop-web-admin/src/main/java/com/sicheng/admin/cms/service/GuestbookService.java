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

import com.sicheng.admin.cms.dao.GuestbookDao;
import com.sicheng.admin.cms.entity.Guestbook;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 留言板 Service
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class GuestbookService extends CrudService<GuestbookDao, Guestbook> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public Guestbook get(Long id) {
        return dao.get(id);
    }

    public Page<Guestbook> findPage(Page<Guestbook> page, Guestbook guestbook) {
        //控制数据的查看范围
        guestbook.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        Wrapper w=new Wrapper(guestbook);
        w.orderBy("a.id DESC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Guestbook guestbook, Boolean isRe) {
        dao.delete(guestbook);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    //FIXME 暂不提供
    public Page<Guestbook> search(Page<Guestbook> page, String q, String beginDate, String endDate) {
        return page;
    }

    @Transactional(readOnly = false)
    public void save(Guestbook guestbook) {
        if (guestbook.getIsNewRecord()) {
            guestbook.preInsert(UserUtils.getUser());
            dao.insert(guestbook);
        } else {
            guestbook.preUpdate(UserUtils.getUser());
            dao.update(guestbook);
        }
    }

}