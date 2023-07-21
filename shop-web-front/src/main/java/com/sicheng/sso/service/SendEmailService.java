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
package com.sicheng.sso.service;

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.common.email.EmailSender;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.sso.constant.SendMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: SendEmailService</p>
 * <p>描述: 发送邮件</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月8日 下午12:25:57
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SendEmailService {

    @Autowired
    private SiteInfoService siteInfoService;

    @Autowired
    private EmailSender emailSender;

    /**
     * 发送邮件
     *
     * @param email        邮件
     * @param subject      邮件的标题
     * @param text         邮件的正文，支持HTML
     * @return
     */
    public Object sendEmail(String email, String subject, String text) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean async = true;
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        if (siteInfo != null) {
            emailSender.send(email, subject, text, async);
        }
        map.put("status", SendMessageConstants.EMAIL_STATUS_ZERO_STATUS);
        map.put("message", FYUtils.fy(SendMessageConstants.EMAIL_STATUS_ZERO_MESSAGE));
        return map;
    }

}
