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
package com.sicheng.admin.sys.entity;

import com.sicheng.admin.sys.dao.SysApiParamDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 接口 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2018-02-28
 */
public class SysApi extends SysApiBase<SysApi> {

    private static final long serialVersionUID = 1L;

    public SysApi() {
        super();
    }

    public SysApi(Long id) {
        super(id);
    }

    //一对多映射
    private List<SysApiParam> sysApiParamList;//一个接口--多个参数

    public List<SysApiParam> getSysApiParamList() {
        if (sysApiParamList == null) {
            SysApiParamDao dao = SpringContextHolder.getBean(SysApiParamDao.class);
            sysApiParamList = dao.selectByWhere(null, new Wrapper().and("api_id=", this.getApiId()).orderBy("param_id"));
        }
        return sysApiParamList;
    }

    public void setSysApiParamList(List<SysApiParam> sysApiParamList) {
        this.sysApiParamList = sysApiParamList;
    }

}