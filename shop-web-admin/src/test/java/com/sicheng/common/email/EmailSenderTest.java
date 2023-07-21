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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>标题: EmailSender 邮箱发送者 单元测试</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月2日 下午5:57:43
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class EmailSenderTest {
    @Autowired
    private EmailSender emailSender;


    /**
     * 发送一封邮件（最常用方法）
     * <p>
     * 邮件服务器配置信息需要从sys_email_service表中获取
     * 所以本方法能否成功运行，取决于sys_email_service表中要有正确的服务器配置信息
     */
    @Test
    public void test_sendEmail1() {
        String toMail = "zhaolei@sicheng.net";
        String subject = "我是测试邮件";
        String text = "这是邮件正文(由单元测试发送)，这是邮件正文(由单元测试发送)，这是邮件正文(由单元测试发送)，这是邮件正文(由单元测试发送)，这是邮件正文(由单元测试发送)";
        boolean async = false;//在测试业务中请使用false,在正式业务中请使用true
        emailSender.send( toMail, subject, text, async);
    }


}