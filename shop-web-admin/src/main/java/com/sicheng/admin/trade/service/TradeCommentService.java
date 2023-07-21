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
package com.sicheng.admin.trade.service;

import com.sicheng.admin.trade.dao.TradeCommentDao;
import com.sicheng.admin.trade.dao.TradeCommentImageDao;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeCommentImage;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论 Service
 *
 * @author 范秀秀
 * @version 2017-01-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeCommentService extends CrudService<TradeCommentDao, TradeComment> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private TradeCommentDao tradeCommentDao;
    @Autowired
    private TradeCommentImageDao tradeCommentImageDao;

    /**
     * 删除评论
     *
     * @param tradeComment
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(TradeComment tradeComment) {
        //删除该评论的回复内容
        TradeComment comment = new TradeComment();
        comment.setReplyId(tradeComment.getCommentId());
        List<TradeComment> commentList = tradeCommentDao.selectByWhere(null, new Wrapper(comment));
        if (commentList.size() > 0) {
            tradeCommentDao.deleteById(commentList.get(0).getCommentId());
        }
        //删除评论的图片
        TradeCommentImage tradeCommentImage = new TradeCommentImage();
        tradeCommentImage.setCommentId(tradeComment.getCommentId());
        List<TradeCommentImage> imgList = tradeCommentImageDao.selectByWhere(null, new Wrapper(tradeCommentImage));
        if (imgList.size() > 0) {
            for (TradeCommentImage img : imgList) {
                tradeCommentImageDao.deleteById(img.getCiId());
            }
        }
        //删除评论
        tradeCommentDao.deleteById(tradeComment.getCommentId());
    }

}