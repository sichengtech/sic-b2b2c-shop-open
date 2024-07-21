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
package com.sicheng.front.template;

import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.member.service.MemberAddressService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，UserAddressListFunction函数
 * </p>
 * <p>
 * 描述: 根据用户id获取收货地址,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class UserAddressListFunction implements Function {

    public static final String UID = "uid";

    public List<MemberAddress> call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long uid = TagUtils.getLong(tagParamMap, UID);
        if (uid == null) {
            return new ArrayList<>();
        }
        MemberAddressService addressService = SpringContextHolder.getBean(MemberAddressService.class);
        Wrapper wrapper = new Wrapper().and("u_id =", uid).orderBy("is_default desc,update_date desc");
        //执行业务，查询出用户收货地址列表
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<MemberAddress> memberAddressPage = addressService.selectByWhere(new Page<MemberAddress>(1, limit, limit), wrapper);
        return memberAddressPage.getList();
    }
}