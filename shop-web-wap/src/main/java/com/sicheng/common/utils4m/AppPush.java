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
package com.sicheng.common.utils4m;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IAliasResult;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: AppPush</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2019年9月29日 上午11:58:56
 */

public class AppPush {

    //个推的sdk地址
    private final static String URL = "http://sdk.open.api.igexin.com/apiex.htm";

    //消息模板常量
    private final static Integer MASSAGE_TEMPLATE_1 = 1;//自定义消息模板
    private final static Integer MASSAGE_TEMPLATE_2 = 2;//点击消息打开应用首页模板
    private final static Integer MASSAGE_TEMPLATE_3 = 3;//点击消息打开打开浏览器网页

    //发送app消息的参数键
    private final static String TITLE = "title";//自定义消息模板
    private final static String TEXT = "text";//点击消息打开应用首页模板
    private final static String GO_URL = "go_url";//点击消息打开打开浏览器网页

    /**
      * 给所有设备发送app通知
      * @param massageTemplateNum 通知模板类型
      * @param map 通知参数
     */
    public static void sendAllAppMessage(Integer massageTemplateNum, Map<String, String> map) {
        if (massageTemplateNum == null) {
            return;
        }
        IGtPush push = new IGtPush(URL, Global.getConfig("sc_app_key"), Global.getConfig("sc_master_secret"));
        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<>();
        appIds.add(Global.getConfig("sc_app_id"));
        AppMessage message = new AppMessage();
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
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }

    /**
      * 给单一设备发送app通知
      * @param massageTemplateNum 通知模板类型
      * @param alias 每个用户设定的标识
      * @param map 通知参数
     */
    public static void sendOneAppMessage(Integer massageTemplateNum, String alias, Map<String, String> map) {
        if (massageTemplateNum == null) {
            return;
        }
        IGtPush push = new IGtPush(URL, Global.getConfig("sc_app_key"), Global.getConfig("sc_master_secret"));
        // STEP5：定义"SingleMessage"类型消息对象,设置推送消息有效期等推送参数
        SingleMessage message = new SingleMessage();
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
        Target target = new Target();
        target.setAppId(Global.getConfig("sc_app_id"));
        target.setAlias(alias);
        //target.setClientId(alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    /**
     * 自定义消息模板
      * @param map
      * @return
     */
    public static TransmissionTemplate transmissionTemplateDemo(Map<String, String> map) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));
        template.setTransmissionType(2);//1：立即启动APP、2：客户端收到消息后需要自行处理
        template.setTransmissionContent(JSON.toJSON(map).toString());
        return template;
    }

    /**
     * 跳转app首页的模板
      * @param map
      * @return
     */
    public static NotificationTemplate notificationTemplateDemo(Map<String, String> map) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));
        template.setTransmissionType(1);//1：立即启动APP、2：客户端收到消息后需要自行处理

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(map.get(TITLE));
        style.setText(map.get(TEXT));
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
      * @param map
      * @return
     */
    public static LinkTemplate linkTemplateDemo(Map<String, String> map) {
        LinkTemplate template = new LinkTemplate();
        template.setAppId(Global.getConfig("sc_app_id"));
        template.setAppkey(Global.getConfig("sc_app_key"));

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(map.get(TITLE));
        style.setText(map.get(TEXT));
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannelLevel(3);
        template.setStyle(style);
        // 设置打开的网址地址
        template.setUrl(map.get(GO_URL));
        return template;
    }

    /**
     *
      * 将app 所在的设备与别名(alias)绑定
      * @param cid 手机设备的唯一标识
      * @param alias  别名
     */
    public static void bindAlias(String cid, String alias) {
        IGtPush push = new IGtPush(URL, Global.getConfig("sc_app_key"), Global.getConfig("sc_master_secret"));
        IAliasResult bindSCid = push.bindAlias(Global.getConfig("sc_app_id"), alias, cid);
        System.out.println("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
    }

    public static void main(String[] args) throws IOException {
        //测试 cid:ddcd1caffb4d37ac21445bd81d4821f8
        //bindAlias("3b7f8728081af1fc1d76d3ac5cc238d9", "guoyang");
        Map<String, String> map = new HashMap<String, String>();
        map.put(TITLE, "kkk");
        map.put(TEXT, "kkk");
        map.put(GO_URL, "memberOrdersReceive");
        //sendAllAppMessage(1, map);
        sendOneAppMessage(1, "1", map);
    }
}
