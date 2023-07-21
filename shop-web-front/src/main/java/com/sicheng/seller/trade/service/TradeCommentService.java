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
package com.sicheng.seller.trade.service;

import com.sicheng.admin.trade.dao.TradeCommentDao;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeCommentImage;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private TradeCommentImageService tradeCommentImageService;
    @Autowired
    private TradeOrderService tradeOrderService;

    /**
     * 发布评论
     *
     * @param tradeCommentList 多个评价
     * @param imgs             图片
     * @param tradeOrder       订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void addComment(List<TradeComment> tradeCommentList, List<String[]> imgs, TradeOrder tradeOrder) {
        //添加评论
        if (tradeCommentList.isEmpty()) {
            return;
        }
        super.insertSelectiveBatch(tradeCommentList);
        //添加评论图片
        if (imgs.size() == 5) {
            String[] img1 = imgs.get(0);
            String[] img2 = imgs.get(1);
            String[] img3 = imgs.get(2);
            String[] img4 = imgs.get(3);
            String[] img5 = imgs.get(4);
            List<TradeCommentImage> tradeCommentImages = new ArrayList<>();
            for (int i = 0; i < img1.length; i++) {
                if (!"".equals(img1[i])) {
                    TradeCommentImage tradeCommentImage = new TradeCommentImage();
                    tradeCommentImage.setPath(img1[i]);
                    tradeCommentImage.setCommentId(tradeCommentList.get(i).getCommentId());
                    tradeCommentImages.add(tradeCommentImage);
                }
                if (!"".equals(img2[i])) {
                    TradeCommentImage tradeCommentImage = new TradeCommentImage();
                    tradeCommentImage.setPath(img2[i]);
                    tradeCommentImage.setCommentId(tradeCommentList.get(i).getCommentId());
                    tradeCommentImages.add(tradeCommentImage);
                }
                if (!"".equals(img3[i])) {
                    TradeCommentImage tradeCommentImage = new TradeCommentImage();
                    tradeCommentImage.setPath(img3[i]);
                    tradeCommentImage.setCommentId(tradeCommentList.get(i).getCommentId());
                    tradeCommentImages.add(tradeCommentImage);
                }
                if (!"".equals(img4[i])) {
                    TradeCommentImage tradeCommentImage = new TradeCommentImage();
                    tradeCommentImage.setPath(img4[i]);
                    tradeCommentImage.setCommentId(tradeCommentList.get(i).getCommentId());
                    tradeCommentImages.add(tradeCommentImage);
                }
                if (!"".equals(img5[i])) {
                    TradeCommentImage tradeCommentImage = new TradeCommentImage();
                    tradeCommentImage.setPath(img5[i]);
                    tradeCommentImage.setCommentId(tradeCommentList.get(i).getCommentId());
                    tradeCommentImages.add(tradeCommentImage);
                }
            }
            tradeCommentImageService.insertSelectiveBatch(tradeCommentImages);
        }

        //更新订单状态：50已评价
        if (tradeOrder != null) {
            //10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
            tradeOrder.setOrderStatus("50");
            TradeOrder condition = new TradeOrder();
            condition.setId(tradeOrder.getId());
            condition.setUId(tradeOrder.getUId());
            tradeOrderService.updateByWhereSelective(tradeOrder, new Wrapper(condition));//属主检查
        }
    }
}