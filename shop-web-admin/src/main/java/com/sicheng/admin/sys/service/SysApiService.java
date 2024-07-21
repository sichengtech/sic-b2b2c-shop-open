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
package com.sicheng.admin.sys.service;

import com.sicheng.admin.sys.dao.SysApiDao;
import com.sicheng.admin.sys.entity.SysApi;
import com.sicheng.admin.sys.entity.SysApiParam;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 接口 Service
 *
 * @author fxx
 * @version 2018-02-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SysApiService extends CrudService<SysApiDao, SysApi> {

    @Autowired
    private SysApiParamService sysApiParamService;

    /**
     * 添加接口
     *
     * @param sysApi
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveApi(SysApi sysApi, List<SysApiParam> sysApiParamList) {
        if (sysApi == null) {
            return;
        }
        //id为空时，添加新数据，否则更新数据
        if (sysApi.getApiId() == null) {
            super.insertSelective(sysApi);
            if (sysApiParamList != null) {
                for (int i = 0; i < sysApiParamList.size(); i++) {
                    sysApiParamList.get(i).setApiId(sysApi.getApiId());
                }
                sysApiParamService.insertBatch(sysApiParamList);
            }
            return;
        }
        //更新数据
        super.updateByIdSelective(sysApi);
        //删除接口参数，重新添加
        sysApiParamService.deleteByWhere(new Wrapper().and("api_id =", sysApi.getApiId()));
        sysApiParamService.insertBatch(sysApiParamList);
    }


    /**
     * 删除接口
     *
     * @param apiId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteApi(Long apiId) {
        if (apiId == null) {
            return;
        }
        super.deleteById(apiId);
        sysApiParamService.deleteByWhere(new Wrapper().and("api_id =", apiId));
    }
}