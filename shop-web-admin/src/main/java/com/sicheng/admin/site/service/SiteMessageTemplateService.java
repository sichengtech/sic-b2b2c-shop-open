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
package com.sicheng.admin.site.service;

import com.sicheng.admin.site.dao.SiteMessageTemplateDao;
import com.sicheng.admin.site.entity.SiteMessageTemplate;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理网站的消息模板 Service
 *
 * @author 张加利
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteMessageTemplateService extends CrudService<SiteMessageTemplateDao, SiteMessageTemplate> {

    /**
     *  获得新的站内信模板内容
     *  @param map 模板变量参数
     *  @param messageTemplateNum 消息模板编号
     *  @return 返回新的站内信模板内容，如果返回null，将不在发送消息通知
     */
    public String getMsgContent(Map<String, String> map, String messageTemplateNum) {
        SiteMessageTemplate entity = selectMessageTemplate(messageTemplateNum);
        if (entity == null) {
            logger.warn("在管理后台，未设置短信消息消息模板");
            return null;
        }

        if ("0".equals(entity.getSmsOpen())) {//(状态，0停用，1启用)
            logger.warn("在管理后台，短信消息模板停用");
            return null;
        }
        String msgContent = entity.getMsgContent();//站内信模板内容
        if (StringUtils.isNotBlank(msgContent)) {
            msgContent = getReplaceParam(map, msgContent);
        }
        return msgContent;
    }

    /**
     *  获得新的短信模板内容
     *  @param map 模板变量参数
     *  @param messageTemplateNum 消息模板编号
     *  @return 返回新的短信模板内容，如果返回null，将不在发送消息通知
     */
    public String getSmsContent(Map<String, String> map, String messageTemplateNum) {
        SiteMessageTemplate entity = selectMessageTemplate(messageTemplateNum);
        if (entity == null) {
            logger.warn("在管理后台，未设置短信消息消息模板");
            return null;
        }

        if ("0".equals(entity.getSmsOpen())) {  //(状态，0停用，1启用)
            logger.warn("在管理后台，短信消息模板停用");
            return null;
        }
        String smsContent = entity.getSmsContent();//短信模板内容
        if (StringUtils.isNotBlank(smsContent)) {
            smsContent = getReplaceParam(map, smsContent);
        }
        return smsContent;
    }

    /**
     *  获得新的邮件模板内容
     *  @param map 邮件模板变量参数
     *  @param messageTemplateNum
     *  @return 返回新的邮件标题和新的邮件内容，如果返回null，或者返回的邮件标题是空，或者返回的邮件内容是空，将不在发送消息通知
     */
    public Map<String, String> getEmailInfo(Map<String, String> map, String messageTemplateNum) {
        SiteMessageTemplate entity = selectMessageTemplate(messageTemplateNum);
        if (entity == null) {
            logger.warn("在管理后台，未设置邮件消息模板");
            return null;
        }

        if ("0".equals(entity.getEmailOpen())) {  //(状态，0停用，1启用)
            logger.warn("在管理后台，邮件消息模板停用");
            return null;
        }
        String emailTitle = entity.getEmailTitle();//短信模板标题
        String emailContent = entity.getEmailContent();//短信模板内容
        if (StringUtils.isNotBlank(emailTitle)) {
            emailTitle = getReplaceParam(map, emailTitle);
        }
        if (StringUtils.isNotBlank(emailContent)) {
            emailContent = getReplaceParam(map, emailContent);
        }
        Map<String, String> mapImfo = new HashMap<>();
        mapImfo.put("emailTitle", emailTitle);
        mapImfo.put("emailContent", emailContent);
        return mapImfo;
    }

    /**
     *  将消息模板参数的变量替换成变量的值，获得新的消息参数
     *
     * @param map 模板变量参数
     *             * @param param 消息模板参数(消息模板内容,邮件模板的标题)
     *             * @return 返回的是新的消息模板参数
     */
    public String getReplaceParam(Map<String, String> map, String param) {
        if (map == null || map.isEmpty()) {
            return param;
        }
        for (String key : map.keySet()) {
            if (key != null) {
                //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
                param = StringUtils.replace(param, "${" + key + "}", map.get(key));
            }
        }
        return param;
    }

    /**
     *  按编号查出消息模板
     *
     * @param num 消息模板编号
     *             * @return 返回的是一个消息模板的实体
     */
    private SiteMessageTemplate selectMessageTemplate(String num) {
        SiteMessageTemplate messageTemplate = new SiteMessageTemplate();
        messageTemplate.setNum(num);
        SiteMessageTemplate entity = super.selectOne(new Wrapper(messageTemplate));
        return entity;
    }

}