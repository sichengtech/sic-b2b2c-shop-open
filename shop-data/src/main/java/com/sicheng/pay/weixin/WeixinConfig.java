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
package com.sicheng.pay.weixin;

import com.github.wxpay.sdk.WXPayConfig;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementPayWayAttr;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

import java.io.InputStream;
import java.util.List;

/**
 * <p>标题: WeixinConfig</p>
 * <p>描述: 微信初始化信息</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2018年3月27日 上午9:59:07
 */
public class WeixinConfig implements WXPayConfig {

    SettlementPayWayDao settlementPayWayDao = SpringContextHolder.getBean(SettlementPayWayDao.class);
    //支付方式
    SettlementPayWay settlementPayWay;

    private String payWayNum;//支付方式编号

    public WeixinConfig(String payWayNum) {
        this.payWayNum = payWayNum;
    }

    public SettlementPayWay getSettlementPayWay() {
        if (settlementPayWay == null) {
            SettlementPayWay settlementPayWay2 = new SettlementPayWay();
            settlementPayWay2.setPayWayNum(payWayNum);
            List<SettlementPayWay> settlementPayWayList = settlementPayWayDao.selectByWhere(null, new Wrapper(settlementPayWay2));
            if (!settlementPayWayList.isEmpty()) {
                settlementPayWay = settlementPayWayList.get(0);
            }
        }
        return settlementPayWay;
    }

    public void setSettlementPayWay(SettlementPayWay settlementPayWay) {
        this.settlementPayWay = settlementPayWay;
    }

    //支付方式属性
    List<SettlementPayWayAttr> settlementPayWayAttrList;

    public List<SettlementPayWayAttr> getSettlementPayWayAttrList() {
        if (settlementPayWayAttrList == null) {
            settlementPayWayAttrList = getSettlementPayWay().getPayWayAttrList();
        }
        return settlementPayWayAttrList;
    }

    public void setSettlementPayWayAttrList(List<SettlementPayWayAttr> settlementPayWayAttrList) {
        this.settlementPayWayAttrList = settlementPayWayAttrList;
    }

    /**
     * <p>描述: 获取微信appId </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getAppID()
     */
    @Override
    public String getAppID() {
        String appId = getPayWayAttrVal("appId");
        return appId;
    }

    /**
     * <p>描述: 获取微信获取商户证书内容 </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getCertStream()
     */
    @Override
    public InputStream getCertStream() {
        return null;
    }

    /**
     * <p>描述: 连接超时时间，单位毫秒 </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getHttpConnectTimeoutMs()
     */
    @Override
    public int getHttpConnectTimeoutMs() {
        return 10000;
    }

    /**
     * <p>描述: 读数据超时时间，单位毫秒 </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getHttpReadTimeoutMs()
     */
    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * <p>描述: 获取 API 密钥 </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getKey()
     */
    @Override
    public String getKey() {
        String paternerKey = getPayWayAttrVal("paternerKey");
        return paternerKey;
    }

    /**
     * <p>描述: 获取商户号 </p>
     * @return
     * @see com.github.wxpay.sdk.WXPayConfig#getMchID()
     */
    @Override
    public String getMchID() {
        String partner = getPayWayAttrVal("partner");
        return partner;
    }

    /**
     * <p>描述: 获取回调地址 </p>
     * @return
     */
    public String getNotifyUrl() {
        String notifyUrl = getPayWayAttrVal("notifyUrl");
        return notifyUrl;
    }

    /**
     * 根据支付方式属性的键获取属性值
     *
     * @param key
     * @return
     */
    private String getPayWayAttrVal(String key) {
        String value = "";
        if (StringUtils.isBlank(key)) {
            return value;
        }
        List<SettlementPayWayAttr> settlementPayWayAttrList = getSettlementPayWayAttrList();
        if (settlementPayWayAttrList == null || settlementPayWayAttrList.isEmpty()) {
            return value;
        }
        for (SettlementPayWayAttr attr : settlementPayWayAttrList) {
            if (key.equals(attr.getPayWayKey())) {
                value = attr.getPayWayValue();
                break;
            }
        }
        return value;
    }
}
