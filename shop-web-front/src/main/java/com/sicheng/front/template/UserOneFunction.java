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
package com.sicheng.front.template;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.sso.service.UserMainService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，UserOneFunction函数
 * </p>
 * <p>
 * 描述: 根据用户id获取会员
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author 范秀秀
 * @version 2017年5月22日 上午10:59:10
 */
public class UserOneFunction implements Function {

    public static final String UID = "uid";

    public UserMain call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long uid = TagUtils.getLong(tagParamMap, UID);
        if (uid == null) {
            return null;
        }
        UserMainService userMainService = SpringContextHolder.getBean(UserMainService.class);
        //执行业务，查询出会员
        UserMain user = userMainService.selectById(uid);
        return user;
    }
}