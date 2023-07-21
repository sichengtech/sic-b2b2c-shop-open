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
package com.sicheng.common.apppush;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.sicheng.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  <p>标题: EmailSender</p>
 *  <p>描述: 执行发送app通知</p>
 *  <p>公司: 思程科技 www.sicheng.net</p>
 *  @author zhangjiali
 *  @version 2019年10月9日 上午10:18:26
 */
public class AppPushImpl implements AppPush {

    //消息模板常量
    public final static Integer MASSAGE_TEMPLATE_1 = 1;//自定义消息模板
    public final static Integer MASSAGE_TEMPLATE_2 = 2;//点击消息打开应用首页模板
    public final static Integer MASSAGE_TEMPLATE_3 = 3;//点击消息打开打开浏览器网页

    //个推的sdk地址
    private final String URL = "http://sdk.open.api.igexin.com/apiex.htm";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    /**
     *  执行发送app通知
     *  @param massageTemplateNum 通知模板类型:1.自定义消息模板、2.点击消息打开应用首页模板、3.点击消息打开打开浏览器网页
     *  @param map 通知参数
     *
     * @param async true表示异步发送，false表示同步发送 公共参数
     */
    public void sendAllAppMessage(Integer massageTemplateNum, Map<String, String> map, boolean async) {
        if (massageTemplateNum == null) {
            return;
        }
        final IGtPush push = new IGtPush(URL, Global.getConfig("sc_app_key"), Global.getConfig("sc_master_secret"));
        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<>();
        appIds.add(Global.getConfig("sc_app_id"));
        final AppMessage message = new AppMessage();
        //消息模板
        if (massageTemplateNum == MASSAGE_TEMPLATE_1) {
            TransmissionTemplate template = transmissionTemplateDemo(map);//自定义
            message.setData(template);
        }
        if (massageTemplateNum == MASSAGE_TEMPLATE_2) {
            NotificationTemplate template = notificationTemplateDemo(map);//跳转首页
            message.setData(template);
        }
        if (massageTemplateNum == MASSAGE_TEMPLATE_3) {
            LinkTemplate template = linkTemplateDemo(map);//跳转网页链接
            message.setData(template);
        }
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);  // 时间单位为毫秒
        // STEP6：执行推送
        if (async) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    IPushResult ret = push.pushMessageToApp(message);
                    logger.info(ret.getResponse().toString());
                }
            });
        } else {
            IPushResult ret = push.pushMessageToApp(message);
            if (ret != null) {
                logger.info(ret.getResponse().toString());
            } else {
                logger.error("服务器响应异常");
            }
        }
    }

    /**
     *  给单一设备发送app通知
     *  @param massageTemplateNum 通知模板类型:1.自定义消息模板、2.点击消息打开应用首页模板、3.点击消息打开打开浏览器网页
     *  @param alias 每个用户设定的标识
     *
     * @param async true表示异步发送，false表示同步发送 公共参数
     *               * @param map 通知参数
     */
    public void sendOneAppMessage(Integer massageTemplateNum, String alias, Map<String, String> map, boolean async) {
        if (massageTemplateNum == null) {
            return;
        }
        final IGtPush push = new IGtPush(URL, Global.getConfig("sc_app_key"), Global.getConfig("sc_master_secret"));
        // STEP5：定义"SingleMessage"类型消息对象,设置推送消息有效期等推送参数
        final SingleMessage message = new SingleMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);// 离线有效时间，单位为毫秒
        //消息模板
        if (massageTemplateNum == MASSAGE_TEMPLATE_1) {
            TransmissionTemplate template = transmissionTemplateDemo(map);//自定义
            message.setData(template);
        }
        if (massageTemplateNum == MASSAGE_TEMPLATE_2) {
            NotificationTemplate template = notificationTemplateDemo(map);//跳转首页
            message.setData(template);
        }
        if (massageTemplateNum == MASSAGE_TEMPLATE_3) {
            LinkTemplate template = linkTemplateDemo(map);//跳转网页链接
            message.setData(template);
        }
        message.setPushNetWorkType(0); // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        final Target target = new Target();
        target.setAppId(Global.getConfig("sc_app_id"));
        target.setAlias(alias);
        try {
            if (async) {
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        IPushResult ret = push.pushMessageToSingle(message, target);
                        if (ret != null) {
                            logger.info(ret.getResponse().toString());
                        } else {
                            logger.error("服务器响应异常");
                        }
                    }
                });
            } else {
                IPushResult ret = push.pushMessageToSingle(message, target);
                if (ret != null) {
                    logger.info(ret.getResponse().toString());
                } else {
                    logger.error("服务器响应异常");
                }
            }

        } catch (final RequestException e) {
            e.printStackTrace();
            if (async) {
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        IPushResult ret = push.pushMessageToSingle(message, target, e.getRequestId());
                        if (ret != null) {
                            logger.info(ret.getResponse().toString());
                        } else {
                            logger.error("服务器响应异常");
                        }
                    }
                });
            } else {
                IPushResult ret = push.pushMessageToSingle(message, target, e.getRequestId());
                if (ret != null) {
                    logger.info(ret.getResponse().toString());
                } else {
                    logger.error("服务器响应异常");
                }
            }
        }
    }

    /**
     * 自定义消息模板
     *  @param map
     *  @return
     */
    private TransmissionTemplate transmissionTemplateDemo(Map<String, String> map) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));
        template.setTransmissionType(2);//1：立即启动APP、2：客户端收到消息后需要自行处理
        template.setTransmissionContent(JSON.toJSON(map).toString());
        return template;
    }

    /**
     * 跳转app首页的模板
     *  @param map
     *  @return
     */
    private NotificationTemplate notificationTemplateDemo(Map<String, String> map) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));
        template.setTransmissionType(1);//1：立即启动APP、2：客户端收到消息后需要自行处理

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(map.get("title"));
        style.setText(map.get("text"));
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannelLevel(3);
        template.setStyle(style);
        return template;
    }

    /**
     * 跳转外部链接的模板
     *  @param map
     *  @return
     */
    private LinkTemplate linkTemplateDemo(Map<String, String> map) {
        LinkTemplate template = new LinkTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(map.get("title"));
        style.setText(map.get("text"));
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannelLevel(3);
        template.setStyle(style);
        // 设置打开的网址地址
        template.setUrl(map.get("go_url"));
        return template;
    }
}