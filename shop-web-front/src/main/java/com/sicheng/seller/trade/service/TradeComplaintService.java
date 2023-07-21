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

import com.sicheng.admin.trade.dao.TradeComplaintDao;
import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.entity.TradeComplaintImage;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 投诉 Service
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeComplaintService extends CrudService<TradeComplaintDao, TradeComplaint> {

    @Autowired
    private TradeComplaintImageService tradeComplaintImageService;

    //申请投诉
    @Transactional(rollbackFor = Exception.class)
    public void applyTradeComplaint(TradeComplaint tradeComplaint, List<String> imgs) {
        dao.insertSelective(tradeComplaint);
        //添加投诉凭证
        if (!imgs.isEmpty()) {
            List<TradeComplaintImage> complaintImageList = new ArrayList<>();
            for (int i = 0; i < imgs.size(); i++) {
                TradeComplaintImage tradeComplaintImage = new TradeComplaintImage();
                tradeComplaintImage.setPath(imgs.get(i));
                tradeComplaintImage.setComplaintId(tradeComplaint.getComplaintId());
                complaintImageList.add(tradeComplaintImage);
            }
            tradeComplaintImageService.insertBatch(complaintImageList);
        }
    }

}