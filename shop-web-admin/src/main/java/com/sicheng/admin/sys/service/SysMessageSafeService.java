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
package com.sicheng.admin.sys.service;

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.service.SiteInfoService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.common.email.EmailSender;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.sms.SmsSender;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 会员通知(线程发通知)
 *
 * @author 蔡龙
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SysMessageSafeService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定时执行器
     */
    @Resource(name = "scheduledExecutor")
    private ScheduledThreadPoolExecutor scheduledExecutor;

    /**
     * 短信接口
     */
    @Autowired
    private SmsSender smsSender;

    /**
     * 邮件接口
     */
    @Autowired
    private EmailSender emailSender;

    /**
     * 会员对象
     */
    @Autowired
    private UserMainService userMainService;

    /**
     * 通知
     */
    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 网站名称
     */
    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 会员通知线程
     * uIds		会员ids
     * message	消息内容
     * sysMode	消息方式//1站内信2短信3邮件
     */
    public void messageMass(final String uIds, final String message, final String sysMode) {
        //在线程池中，异步立即执行任务
        scheduledExecutor.execute(new Runnable() {
            AtomicLong times = new AtomicLong(0);//失败计数器
            String taskName = "messageMass";//任务名称，写方法名就可以，用于日志显示
            int maxFailTimes = 100;//最多失败次数

            public void run() {
                try {
                    String[] uIdss = uIds.split(",");
                    String[] sysModes = sysMode.split(",");
                    String subject="";//邮件标题
                    SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper());//查询网站名称
                    if (siteInfo != null && StringUtils.isNotBlank(siteInfo.getName())) {
                        String siteName = siteInfo.getName();
                        subject = FYUtils.fyParams("邮件通知title_1",siteName);
                    }else{
                        subject = FYUtils.fyParams("邮件通知title_2");
                    }
                    //查询用户
                    List<Long> uIdsList = new ArrayList<Long>();
                    for (int i = 0; i < uIdss.length; i++) {
                        uIdsList.add(Long.parseLong(uIdss[i]));
                    }
                    List<UserMain> userMainList = userMainService.selectByIdIn(uIdsList);
                    List<SysMessage> sysMessageList = new ArrayList<SysMessage>();
                    //异步执行任务
                    for (int i = 0; i < userMainList.size(); i++) {
                        SysMessage sysMessage = new SysMessage();
                        sysMessage.setSender("0");//0系统
                        sysMessage.setUId(userMainList.get(i).getUId());
                        sysMessage.setType("5");//类型1.交易信息 2退换货信息 3商品信息 4 运营信息5群发
                        for (int j = 0; j < sysModes.length; j++) {
                            //1.站内信
                            if ("1".equals(sysModes[j])) {
                                sysMessage.setStatusMsg("1");//站内信：0未发送、1已发送
                                sysMessage.setReading("0");//0未读、1已读 （只限站内信）
                            }
                            //2.短信
                            if ("2".equals(sysModes[j])) {
                                if (StringUtils.isNotBlank(userMainList.get(i).getMobile()) && "1".equals(userMainList.get(i).getMobileValidate())) {
                                    smsSender.sendSmsMessage(userMainList.get(i).getMobile(), message, new HashMap<String, String>(), null, true);
                                    sysMessage.setStatusSms("1");//短信：0未发送、1已发送
                                }
                            }
                            //3邮件
                            if ("3".equals(sysModes[j])) {
                                if (StringUtils.isNotBlank(userMainList.get(i).getEmail()) && "1".equals(userMainList.get(i).getEmailValidate())) {
                                    emailSender.send( userMainList.get(i).getEmail(), subject, message, true);
                                    sysMessage.setStatusEmail("1");//邮件：0未发送、1已发送
                                }
                            }
                        }
                        sysMessage.setContent(message);
                        sysMessage.setCreateDate(new Date());
                        sysMessage.setUpdateDate(new Date());
                        sysMessageList.add(sysMessage);
                    }
                    sysMessageService.insertSelectiveBatch(sysMessageList);
                    logger.debug("异步任务:" + this.taskName + "执行成功，重试过{}次", this.times.get());
                } catch (Exception e) {
                    //如果任务发生异常，过x分钟后重试
                    long failTimes = this.times.incrementAndGet();//累计失败次数
                    if (failTimes < maxFailTimes) {//最多失败n次
                        long t = failTimes;
                        scheduledExecutor.schedule(this, t, TimeUnit.MINUTES);//n分钟后重试
                        logger.error("异步任务:" + this.taskName + "执行失败{}次，过{}分钟后重试", failTimes, t, e);
                    } else {
                        logger.error("异步任务:" + this.taskName + "执行失败{}次，达到最大重试次数限制，不再重试，丢弃", failTimes, e);
                    }
                }
            }
        });
    }
}