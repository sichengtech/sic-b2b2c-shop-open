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
package com.sicheng.pay.ali;

import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementPayWayAttr;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * <p>标题: AliPayConfig</p>
 * <p>描述: 支付宝初始化信息</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2018年3月30日 上午10:47:47
 */
public class AliPayConfig {

    SettlementPayWayDao settlementPayWayDao = SpringContextHolder.getBean(SettlementPayWayDao.class);
    //支付方式
    private SettlementPayWay settlementPayWay;
    //支付方式属性
    private List<SettlementPayWayAttr> settlementPayWayAttrList;
    //请求网关
    public String url;
    //应用id
    public String appId;
    //支付宝公钥
    public String appPublicKey;
    //支付宝私钥
    public String appPrivateKey;
    //参数返回类型
    public String format;
    //编码格式
    public String charset;
    //签名类型
    public String signType;
    //同步地址
    public String returnUrl;
    //异步地址
    public String notifyUrl;
    //支付方式编号
    private String payWayNum;

    public AliPayConfig(String payWayNum) {
        this.payWayNum = payWayNum;
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
     * 请求网关
     */
    public String getUrl() {
        String url = getPayWayAttrVal("url");
        return url;
    }

    /**
     * 应用id
     */
    public String getAppId() {
        String appId = getPayWayAttrVal("appId");
        return appId;
    }

    /**
     * 支付宝公钥
     */
    public String getAppPublicKey() {
        String appPublicKey = getPayWayAttrVal("app_public_key");
        return appPublicKey;
    }


    /**
     * 支付宝私钥
     */
    public String getAppPrivateKey() {
        String appPrivateKey = getPayWayAttrVal("app_private_key");
        return appPrivateKey;
    }

    /**
     * 参数返回类型
     */
    public String getFormat() {
        return "json";
    }

    /**
     * 编码格式
     */
    public String getCharset() {
        return "utf-8";
    }

    /**
     * 签名类型
     */
    public String getSignType() {
        return "RSA2";
    }

    /**
     * 同步地址
     */
    public String getReturnUrl() {
        String returnUrl = getPayWayAttrVal("return_url");
        return returnUrl;
    }

    /**
     * 异步地址
     */
    public String getNotifyUrl() {
        String notifyUrl = getPayWayAttrVal("notify_url");
        return notifyUrl;
    }


}
