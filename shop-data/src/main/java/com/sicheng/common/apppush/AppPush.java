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

import java.util.Map;

/**
 * <p>标题: EmailSender</p>
 * <p>描述: 执行发送app通知</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author zhangjiali
 * @version 2019年10月9日 上午10:18:26
 */
public interface AppPush {

    /**
     * 给所有设备发送app通知
     * @param massageTemplateNum 通知模板类型
     * @param map 通知参数
     *
     * @param async true表示异步发送，false表示同步发送 公共参数
     */
    void sendAllAppMessage(Integer massageTemplateNum, Map<String, String> map, boolean async);

    /**
     * 给单一设备发送app通知
     * @param massageTemplateNum 通知模板类型
     * @param alias 每个用户设定的标识
     * @param map 通知参数
     *
     * @param async true表示异步发送，false表示同步发送 公共参数
     */
    void sendOneAppMessage(Integer massageTemplateNum, String alias, Map<String, String> map, boolean async);
}