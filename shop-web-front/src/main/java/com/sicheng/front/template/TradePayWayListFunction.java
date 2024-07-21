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
package com.sicheng.front.template;

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.settlement.service.SettlementPayWayService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，TradePayWayListFunction函数
 * </p>
 * <p>
 * 描述: 获取支付方式列表,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class TradePayWayListFunction implements Function {

    public List<SettlementPayWay> call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        SettlementPayWayService payWayService = SpringContextHolder.getBean(SettlementPayWayService.class);
        //执行业务，查询支付方式列表
        SettlementPayWay payWay = new SettlementPayWay();
        payWay.setStatus("1");//支付方式状态，0关闭、1开启
        payWay.setUseTerminal("0");//使用终端,0pc、1移动端
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<SettlementPayWay> payWayPage = payWayService.selectByWhere(new Page<SettlementPayWay>(1, limit, limit), new Wrapper(payWay));
        return payWayPage.getList();
    }
}