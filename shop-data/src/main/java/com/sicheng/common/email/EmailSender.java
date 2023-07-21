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
package com.sicheng.common.email;

import java.util.Map;

/**
 * <p>标题: EmailSender 邮箱发送者</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月2日 下午5:28:42
 */
public interface EmailSender {

    /**
     * 发送一封邮件（最常用方法）
     * <p>
     * 邮件服务器配置信息需要从sys_email_service表中获取
     * 所以本方法能否成功运行，取决于sys_email_service表中要有正确的服务器配置信息
     *
     * @param toMail  收件人邮箱地址
     * @param subject 邮件的标题
     * @param text    邮件的正文，支持HTML
     * @param async   true表示异步发送，false表示同步发送
     * @return Map<String, String>
     *         map.put("status", "1");//测试邮件发送结果：0失败，1成功
     *         map.put("msg", "");//提示信息
     */
    Map<String, String> send(String toMail, String subject, String text, boolean async);

    /**
     * <p>描述: 发送一封邮件 （基础方法）</p>
     *
     * @param sender       发件人的显示名，一般是站点名称
     * @param smtpFromMail 发件人邮箱地址
     * @param smtpHost     smtp发邮件服务器的地址
     * @param smtpPort     smtp发邮件服务器的端口
     * @param smtpUsername smtp发邮件服务器的用户名
     * @param smtpPassword smtp发邮件服务器的密码
     * @param toMail       收件人邮箱地址
     * @param subject      邮件的标题
     * @param text         邮件的正文，支持HTML
     * @param async        true表示异步发送，false表示同步发送
     */
    void send(String sender, String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername,
              String smtpPassword, String toMail, String subject, String text, boolean async);


}