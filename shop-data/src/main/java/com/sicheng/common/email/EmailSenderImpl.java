/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 * http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.common.email;

import com.sicheng.admin.sys.dao.SysEmailServerDao;
import com.sicheng.admin.sys.entity.SysEmailServer;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>标题: EmailSender 邮箱发送者</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月2日 下午5:46:39
 */
public class EmailSenderImpl implements EmailSender {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "javaMailSender")
    private JavaMailSenderImpl javaMailSender;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Autowired
    private SysEmailServerDao sysEmailServerDao;

    /**
     * 添加邮件发送任务到独立的线程池，实现异步发送邮件，对业务无阻塞。
     * 这个线程池是由spring提供的
     *
     * @param mimeMessage MimeMessage
     */
    private void addSendTask(final MimeMessage mimeMessage) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    javaMailSender.send(mimeMessage);
                } catch (Exception e) {
                    logger.error("异步发送邮件时异常，只能记个日志了。", e);
                }
            }
        });
    }

    /**
     * <p>描述: 发送一封邮件 （最常用方法）</p>
     *
     * @param toMail  收件人邮箱地址
     * @param subject 邮件的标题
     * @param text    邮件的正文，支持HTML
     * @param async   true表示异步发送，false表示同步发送
     */
    public Map<String, String> send(String toMail, String subject, String text, boolean async) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "1");//测试邮件发送结果：0失败，1成功
        map.put("msg", "");//提示信息

        SysEmailServer entity = selectEmailSet();//查出email通道设置信息
        if (entity == null) {
            String msg = "在管理后台，未设置邮件通道相关信息，无法发出邮件，请让管理员配置正确的邮件服务信息。";
            logger.warn(msg);
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        }

        if ("0".equals(entity.getStatus())) {  //(状态，0停用，1启用)
            String msg = "在管理后台，邮件通道被停用了，无法发出邮件，请联系管理员。";
            logger.warn(msg);
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        }

        String sender = entity.getSender();//发件人展示名称或网站名称,发件人的显示名，一般是站点名称
        String smtpHost = entity.getSmtp();//SMTP服务器地址
        Integer smtpPort = Integer.valueOf(entity.getPort());//SMTP服务器端口
        String smtpUsername = entity.getUsername();//SMTP用户名
        String smtpPassword = entity.getPwd();//SMTP密码
        String smtpFromMail = entity.getUsername();//用户名就是发件的邮箱，两者要一致才能通过smtp服务器的验证
        String safe = entity.getSafe();//是否使用安全连接，0否，1是

        if (StringUtils.isBlank(smtpHost)) {
            String msg = "SMTP服务器地址为空，无法发送邮件";
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        }
        if (smtpPort == null) {
            String msg = "SMTP服务器端口为空，无法发送邮件";
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        }
        if (StringUtils.isBlank(smtpUsername)) {
            String msg = "SMTP用户名为空，无法发送邮件";
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        }

        try {
            send(sender, smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, text, async, safe);
            String msg = "发送邮件成功";
            map.put("status", "1");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg);//提示信息
            return map;
        } catch (Exception e) {
            String msg = "发送邮件时异常，";
            logger.error(msg, e);
            map.put("status", "0");//测试邮件发送结果：0失败，1成功
            map.put("msg", msg + e.getMessage());//提示信息
            return map;
        }
    }

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
     * @param safe         是否使用SSL安全连接，0否，1是
     */
    public void send(String sender, String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername,
                     String smtpPassword, String toMail, String subject, String text, boolean async, String safe) {
//        Assert.hasText(sender);
//        Assert.hasText(smtpFromMail);
//        Assert.hasText(smtpHost);
//        Assert.notNull(smtpPort);
//        Assert.hasText(smtpUsername);
//        Assert.hasText(smtpPassword);
//        Assert.hasText(toMail);
//        Assert.hasText(subject);
//        Assert.hasText(text);

        try {
            if (sender == null) {
                sender = "FDP快速开发平台";
            }

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.mime.encodefilename", "true");
            // 发送服务器需要身份验证
            props.put("mail.smtp.auth", "true");

            //是否使用SSL安全连接，0否，1是
            if("1".equals(safe)) {
                //MailSSLSocketFactory是提高发送邮件兼容性的逻辑。
                //如果你连接一些知名大厂的SMTP服务器发送邮件，一般都是很顺利的。 但是，如果你连接一些小厂商的SMTP服务器或企业自建的SMTP服务器，可能会出现一些问题。
                //例如：服务端的证书不被信任，导致发送邮件失败。  比如证书过期了，再比如证书是xx.abc.com域名的通配符证书但smtp域名是mail.xx.abc.com等于没证书。
                //所以，MailSSLSocketFactory就是为了解决这个问题的。目标是让 java邮件客户端 信任所有SSL证书，就可提高发送邮件兼容性，遇到奇葩的smtp服务器也能成功发送。
                MailSSLSocketFactory sslfac = new MailSSLSocketFactory();
                sslfac.setTrustAllHosts(true); //信任所有SSL证书

                // 设置使用ssl
                props.setProperty("mail.smtp.ssl.enable", "true");
//                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.ssl.socketFactory", sslfac);
                props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            }

            javaMailSender.setJavaMailProperties(props);
            javaMailSender.setHost(smtpHost); // 设置邮件服务器主机名
            javaMailSender.setPort(smtpPort); // 设置邮件服务器端口号
            javaMailSender.setUsername(smtpUsername); //用户名
            javaMailSender.setPassword(smtpPassword); //密码
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setFrom(MimeUtility.encodeWord(sender) + " <" + smtpFromMail + ">");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setText(textTemplate(text), true);
            if (async) {//异步发送邮件
                addSendTask(mimeMessage);
            } else {//同步发送邮件
                try {
                    javaMailSender.send(mimeMessage);
                } catch (Exception e) {
                    logger.error("同步发送邮件时异常。", e);
                    throw new RuntimeException("同步发送邮件时异常。", e);
                }
            }
        } catch (Exception e) {
            logger.error("发送邮件时异常", e);
            throw new RuntimeException("发送邮件时异常", e);
        }
    }


    /**
     * 查出email通道设置信息
     * SysEmail表可以保存多行，但只使用一行记录
     * 从库中查出ID最小的一个
     */
    private SysEmailServer selectEmailSet() {
        SysEmailServer entity = null;
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SysEmailServer> page = new Page<SysEmailServer>();
            page.setOrderBy("id asc");//按ID排序
            SysEmailServer emailServer = new SysEmailServer();

            List<SysEmailServer> list = sysEmailServerDao.selectByWhere(page, new Wrapper(emailServer));

            if (list.size() > 0) {
                entity = list.get(0);//取ID最小的一个
            }
        }
        return entity;
    }

    /**
     * 邮件内容模板
     * @param text 邮件内容
     * @return
     */
    private String textTemplate(String text) {
        text = "<p height='40px;'><br/></p>"
                + "<p style='width: 400px; text-align: left; margin: 0px auto; line-height: 30px; text-indent: 2em;font-size: 18px'>"
                + text
                + "</p>"
                + "<p height='40px;'><br/></p>";
        return text;
    }
}