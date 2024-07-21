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

import com.sicheng.admin.member.dao.MemberAddressDao;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
  * <p>标题: MemberAddressService</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2018年3月6日 下午4:42:35
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MemberAddressService extends CrudService<MemberAddressDao, MemberAddress> {
    //请在这里编写业务逻辑

    /**
      * 添加收货地址 
      * @param isDefault是否默认地址(0不默认,1默认)
      * @param memberAddress
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertAddress(String isDefault, MemberAddress memberAddress) {
        if ("1".equals(isDefault)) {
            MemberAddress entity = new MemberAddress();
            entity.setIsDefault(isDefault);
            entity.setUId(memberAddress.getUId());
            entity = super.selectOne(new Wrapper(entity));
            if (entity != null && entity.getAddressId() != null) {
                entity.setIsDefault("0");
                super.updateByIdSelective(entity);
            }
            memberAddress.setIsDefault(isDefault);
            super.insertSelective(memberAddress);
        } else {
            memberAddress.setIsDefault("0");
            super.insertSelective(memberAddress);
        }
    }

    /**
      * 编辑收货地址 
      * @param isDefault是否默认地址(0不默认,1默认)
      * @param memberAddress
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(String isDefault, MemberAddress memberAddress) {
        MemberAddress address = new MemberAddress();//更新条件
        address.setAddressId(memberAddress.getAddressId());
        address.setUId(AppTokenUtils.findUser().getUId());
        if ("1".equals(isDefault)) {
            MemberAddress entity = new MemberAddress();
            entity.setIsDefault(isDefault);
            entity.setUId(memberAddress.getUId());
            entity = super.selectOne(new Wrapper(entity));
            if (entity != null && entity.getAddressId() != memberAddress.getAddressId()) {
                entity.setIsDefault("0");
                super.updateByIdSelective(entity);
            }
            memberAddress.setIsDefault(isDefault);
            super.updateByWhereSelective(memberAddress, new Wrapper(address));
        } else {
            memberAddress.setIsDefault("0");
            super.updateByWhereSelective(memberAddress, new Wrapper(address));
        }
    }
}
