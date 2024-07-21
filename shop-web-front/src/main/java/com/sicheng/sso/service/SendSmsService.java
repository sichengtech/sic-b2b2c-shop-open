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
package com.sicheng.sso.service;

import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.sms.SmsSender;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.site.service.SiteMessageTemplateService;
import com.sicheng.sso.constant.SendMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: SendSmsService</p>
 * <p>描述: 发送短信</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年7月8日 下午12:22:12
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SendSmsService {

    @Autowired
    private SmsSender smsSender;
    
    private static String MESSAGE_TEMPLATE_NUM = "activateAccount";        //短信模板编号！

    /**
     * 发送短信方法
     *
     * @param mobile 手机号
     * @param smsVerification 验证码
     * @return
     */
    public Object sendSms(String mobile, String smsVerification) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean async = true;//true表示异步发送，false表示同步发送
        SiteMessageTemplateService messageTemplateService = SpringContextHolder.getBean(SiteMessageTemplateService.class);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", smsVerification);
        String completeContent = messageTemplateService.getSmsContent(paramMap, MESSAGE_TEMPLATE_NUM);//使用本地消息模板获得的完整内容
        if (StringUtils.isNotBlank(completeContent)) {
            smsSender.sendSmsMessage(mobile, completeContent, paramMap, MESSAGE_TEMPLATE_NUM, async);
        }
        map.put("status", SendMessageConstants.SMS_STATUS_ZERO_STATUS);
        map.put("message", FYUtils.fy(SendMessageConstants.SMS_STATUS_ZERO_MESSAGE));
        return map;
    }

}
