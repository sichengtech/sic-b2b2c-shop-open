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
package com.sicheng.seller.user.interceptor;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.seller.store.service.StoreEnterAuthService;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: 是否开店</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月5日 下午12:32:14
 */
public class VerificationIsOpenShopInterceptor implements HandlerInterceptor {

    @Value("${sellerPath}")
    protected String sellerPath;

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserMain userMain = SsoUtils.getUserMain();
        if ("1".equals(userMain.getTypeAccount())) {
            //主账号
            //判断是否开店
            StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userMain.getUId());
            if (storeEnterAuth == null) {
                //商家还没入驻
                request.getRequestDispatcher(sellerPath + "/store/storeEnterAuth/auth4.htm").forward(request, response);
                return false;
            } else {
                if ("70".equals(storeEnterAuth.getStatus()) || "80".equals(storeEnterAuth.getStatus()) || "90".equals(storeEnterAuth.getStatus()) || "50".equals(storeEnterAuth.getStatus())) {
                    //商家完成入驻
                    return true;
                } else {
                    //商家还没完成入驻
                    request.getRequestDispatcher(sellerPath + "/store/storeEnterAuth/auth4.htm").forward(request, response);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception {

    }
}
