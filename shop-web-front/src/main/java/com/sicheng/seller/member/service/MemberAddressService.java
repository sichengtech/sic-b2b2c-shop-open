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
package com.sicheng.seller.member.service;

import com.sicheng.admin.member.dao.MemberAddressDao;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 买家收货地址 Service
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MemberAddressService extends CrudService<MemberAddressDao, MemberAddress> {

    @Transactional(rollbackFor = Exception.class)
    public void addAddress(MemberAddress memberAddress) {
        if ("1".equals(memberAddress.getIsDefault())) {
            MemberAddress address = new MemberAddress();
            address.setIsDefault("0");
            dao.updateByWhereSelective(address, new Wrapper().and("u_id =", memberAddress.getUId()));
        }
        dao.insertSelective(memberAddress);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editAddress(MemberAddress memberAddress) {
        if ("1".equals(memberAddress.getIsDefault())) {
            MemberAddress address = new MemberAddress();
            address.setIsDefault("0");
            dao.updateByWhereSelective(address, new Wrapper().and("u_id =", memberAddress.getUId()).and("address_id <>", memberAddress.getAddressId()));
        }
        dao.updateByWhereSelective(memberAddress, new Wrapper().and("u_id =", memberAddress.getUId()).and("address_id =", memberAddress.getAddressId()));
    }
}