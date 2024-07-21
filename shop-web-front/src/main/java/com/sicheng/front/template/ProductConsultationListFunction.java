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

import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.trade.service.TradeConsultationService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductConsultationListFunction函数
 * </p>
 * <p>
 * 描述: 根据pid获取一个咨询列表,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductConsultationListFunction implements Function {

    public static final String PID = "pid";

    public List<TradeConsultation> call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long pid = TagUtils.getLong(tagParamMap, PID);
        if (pid == null) {
            return new ArrayList<>();
        }
        TradeConsultation tradeConsultation = new TradeConsultation();
        tradeConsultation.setPId(pid);
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        // 类别，0咨询、1回复
        tradeConsultation.setType("0");
        TradeConsultationService service = SpringContextHolder.getBean(TradeConsultationService.class);
        //执行业务，查询出商品咨询列表
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<TradeConsultation> tradeConsultationPage = service.selectByWhere(new Page<TradeConsultation>(1, limit, limit), new Wrapper(tradeConsultation));
        return tradeConsultationPage.getList();
    }
}