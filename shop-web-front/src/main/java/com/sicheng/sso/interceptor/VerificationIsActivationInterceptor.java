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
package com.sicheng.sso.interceptor;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: 验证是否激活账号</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月5日 下午12:29:47
 */
public class VerificationIsActivationInterceptor implements HandlerInterceptor {

    @Value("${ssoPath}")
    protected String ssoPath;

    @Autowired
    private SysVariableService variableService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取后台注册时是否强制要验证邮箱
        SysVariable variable = variableService.getSysVariable("sys_is_variable_email");
        Long isVariableEmail = 1L;//默认值，1代表要验证邮箱是否激活
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                isVariableEmail = Long.parseLong(variable.getValue());
            }
        }
        //验证邮箱是否激活
        if (isVariableEmail != 0) {
            UserMain userMain = SsoUtils.getUserMain();
            if ("1".equals(userMain.getTypeAccount())) {
                //判断账号是否被激活
                if (userMain != null && !userMain.isOperation()) {
                    request.getRequestDispatcher(ssoPath + "/email/verificationEmail.htm").forward(request, response);
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
