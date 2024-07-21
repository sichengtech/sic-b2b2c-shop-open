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
package com.sicheng.sso.shiro;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.shiro.SsoClearAuthorizationCache;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.shiro.SsoAuthorizingRealm.Principal;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>标题: SsoClearAuthorizationCache</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年9月27日 下午12:50:16
 */
public class SsoClearAuthorizationCacheImpl implements SsoClearAuthorizationCache {

    @Autowired
    private SsoAuthorizingRealm ssoAuthorizingRealm;
    @Autowired
    private UserMainService userMainService;

    /**
     * <p>描述: admin调用清理单个sso权限缓存 </p>
     */
    @Override
    public void clearAuthorizationCache(Long uId) {
        UserMain userMain = userMainService.selectById(uId);
        Principal principal = new Principal(userMain, false);
        ssoAuthorizingRealm.clearCachedAuthorizationInfo(principal);
    }

    /**
     * <p>描述: admin调用清理所有sso权限缓存 </p>
     */
    @Override
    public void clearAuthorizationCache() {
        ssoAuthorizingRealm.clearAllCachedAuthorizationInfo();
    }

}
