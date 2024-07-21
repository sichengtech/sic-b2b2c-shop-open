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
package com.sicheng.admin.trade.utils;

import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>标题: TradeOrderUtils</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月21日 上午10:53:11
 */
public class TradeOrderUtils {
    protected static Logger logger = LoggerFactory.getLogger(TradeOrderUtils.class);
    private static Random ra = new Random();//随机数据生成器
    private static TradeOrderDao dao = SpringContextHolder.getBean(TradeOrderDao.class);
    private static boolean jumpNumber = false;//是否跳号， true:增长步长是1-9随机,false:增长步长是1

    /**
     * 生成订单号（支持oracle和mysql）
     * 订单号生成规则：年2位+月2位+日2位+订单数7位+业务编码1位，共14位。
     * （业务编码必需放在最后面，才能保证订单号是正向增长的）
     * <p>
     * 订单数7位：可容纳1000万单
     * 订单数7位：增长步长是1-9随机，防止每日订单量外泄。
     * 订单数7位：每天0点定时清0
     * 功能：订单号包含了业务类型、时间信息方便运营、客服人员工作
     * 安全：跳号，流水号增长步长随机，防止每日订单量外泄
     * <p>
     * 示例：
     * 16121300000013
     * 16121300000021
     * 16121300000032
     * 16121300199932
     *
     * @param business 业务编码：Web=1，CallCenter=2,App=3
     * @return 14位订单号
     */
    public static Long creatOrderNumber(String business) {
        //业务编码：Web=1，CallCenter=2,App=3
        if (StringUtils.isBlank(business)) {
            business = "1";
        }

        int size = 1;
        if (jumpNumber) {//是否开启随机
            size = ra.nextInt(9);//产生大于等于0,小于9的整形随机数
            size = size + 1;//增长步长是1-9随机,stepList的长度就是步长
        }

        boolean ismysql = false;
        if ("mysql".equalsIgnoreCase(Global.getConfig("jdbc.type"))) {
            ismysql = true;
        }

        Long id = 0L;
        for (int i = 0; i < size; i++) {
            if (ismysql) {
                Map<String, Object> map = new HashMap<String, Object>();
                id = dao.generateOrderNumber4Mysql(map);//可直接拿到返回值
            } else {
                //方案二是纯oracle函数实现
                id = dao.generateOrderNumber4Oracle();
            }
        }

        //组装订单号
        StringBuilder sbl = new StringBuilder(14);
        sbl.append(id);
        sbl.append(business);//业务编码必需放在最后面，才能保证订单号是正向增长的
        return Long.valueOf(sbl.toString());
    }
}