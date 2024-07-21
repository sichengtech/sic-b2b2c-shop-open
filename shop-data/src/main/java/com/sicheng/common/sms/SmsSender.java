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
package com.sicheng.common.sms;

import java.util.Map;

/**
 * <p>标题: SmsSender 短信网关接口</p>
 * <p>描述: 用来发送一条短信</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年4月8日 下午6:46:08
 */
public interface SmsSender {

    /**
     * 发送一条短信消息
     *
     * @param phone           手机号  公共参数
     * @param completeContent 使用本地消息模板获得的完整内容  直接发送短信内容时单独使用
     * @param paramMap        第三方短信内容参数  使用第三方短信模板时单独使用
     * @param templateCode    本地消息模板的编号  使用第三方短信模板时单独使用
     * @param async           true表示异步发送，false表示同步发送 公共参数
     */
    boolean sendSmsMessage(String phone, String completeContent, Map<String, String> paramMap, String templateCode, boolean async);
}