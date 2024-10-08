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

import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.trade.service.TradeCommentService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductCommentListFunction函数
 * </p>
 * <p>
 * 描述: 根据pid获取一个商品评论列表,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductCommentListFunction implements Function {

    public static final String PID = "pid";
    public static final String GRADE = "grade";

    public List<TradeComment> call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long pid = TagUtils.getLong(tagParamMap, PID);
        String grade = TagUtils.getString(tagParamMap, GRADE);
        if (pid == null) {
            return new ArrayList<>();
        }
        //执行业务，查询出产品分类列表
        TradeComment comment = new TradeComment();
        comment.setPId(pid);
        // 是否显示，0否、1是
        comment.setIsShow("1");
        // 类型，0评论、1追评、2回复
        comment.setType("0");
        if (StringUtils.isNotBlank(grade)) {
            comment.setGrade(grade);
        }
        TradeCommentService service = SpringContextHolder.getBean(TradeCommentService.class);
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<TradeComment> tradeCommentPage = service.selectByWhere(new Page<TradeComment>(1, limit, limit), new Wrapper(comment));
        return tradeCommentPage.getList();
    }
}