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
package com.sicheng.front.template;

import com.sicheng.admin.cms.entity.Comment;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.CommentService;
import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>标题: 获取评论(列表)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月25日 下午4:49:51
 */
public class CmsCommentListFunction implements Function {

    private static final String ARTICLE_ID = "articleId";//文章id

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long articleId = TagUtils.getLong(tagParamMap, ARTICLE_ID);
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        if (articleId == null) {
            return null;
        }
        CommentService commentService = SpringContextHolder.getBean(CommentService.class);
        Comment comment = new Comment();
        comment.setContentId(articleId);
        Page<Comment> commentPage = commentService.selectByWhere(new Page<Comment>(1, limit, limit), new Wrapper(comment));
        return commentPage.getList();
    }

}
