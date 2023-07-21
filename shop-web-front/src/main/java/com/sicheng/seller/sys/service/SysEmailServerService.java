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
package com.sicheng.seller.sys.service;

import com.sicheng.admin.sys.dao.SysEmailServerDao;
import com.sicheng.admin.sys.entity.SysEmailServer;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理邮件的发送 Service
 *
 * @author 张加利
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SysEmailServerService extends CrudService<SysEmailServerDao, SysEmailServer> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 查出email设置信息
     * SysEmail表可以保存多行，但只使用一行记录
     * 从库中查出ID最小的一个
     */
    public SysEmailServer selectEmailSet() {
        SysEmailServer entity = null;
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SysEmailServer> page = new Page<SysEmailServer>();
            page.setOrderBy("id asc");//按ID排序
            SysEmailServer emailServer = new SysEmailServer();
            page = this.selectByWhere(page, new Wrapper(emailServer));
            if (page.getList().size() > 0) {
                entity = page.getList().get(0);//取ID最小的一个
            }
        }
        return entity;
    }

}