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
package com.sicheng.member.user.web;

import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.seller.member.service.MemberCollectionProductService;
import com.sicheng.seller.member.service.MemberCollectionStoreService;
import com.sicheng.seller.trade.service.TradeComplaintService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>标题: memberInfoController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2018年2月25日 下午1:57:07
 */
@Controller
@RequestMapping(value = "${memberPath}/user/member")
public class MemberInfoController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private TradeComplaintService tradeComplaintService;
    @Autowired
    private MemberCollectionProductService memberCollectionProductService;
    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;

    /**
     * 获取会员中心待办数据
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "info")
    public HashMap<Object, Object> getMemberWaitCountMap() {
        HashMap<Object, Object> map = new HashMap<>();
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //List<TradeOrder> tradeOrders = tradeOrderService.selectByWhere(new Wrapper());
        int status10Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 10).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        //int status20Count=tradeOrderService.countByWhere(new Wrapper().and("order_status =",20).and("is_return_status=",0).and("u_id=",userMember.getUId()));
        int status30Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 30).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status40Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 40).and("u_id=", userMember.getUId()));
        //int status50Count=tradeOrderService.countByWhere(new Wrapper().and("order_status =",50).and("is_return_status=",0).and("u_id=",userMember.getUId()));
        //int status60Count=tradeOrderService.countByWhere(new Wrapper().and("order_status =",60).and("is_return_status=",0).and("u_id=",userMember.getUId()));
        int isReturnStatusCount1 = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 1).and("u_id=", userMember.getUId()));
        int isReturnStatusCount2 = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 2).and("u_id=", userMember.getUId()));
        //投诉数量
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.u_id =", userMember.getUId());
        int tradeComplaintCount = tradeComplaintService.countByWhere(wrapper);
        //收藏商品数
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMember.getUId());
        int memberCollectionProductCount = memberCollectionProductService.countByWhere(new Wrapper(memberCollectionProduct));
        //收藏店铺数
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMember.getUId());
        int memberCollectionStoreCount = memberCollectionStoreService.countByWhere(new Wrapper(memberCollectionStore));

        map.put("userMain", SsoUtils.getUserMain());
        map.put("userMember", SsoUtils.getUserMain().getUserMember());
        map.put("status10Count", status10Count);
        map.put("status30Count", status30Count);
        map.put("status40Count", status40Count);
        map.put("isReturnStatusCount1", isReturnStatusCount1);
        map.put("isReturnStatusCount2", isReturnStatusCount2);
        map.put("tradeComplaintCount", tradeComplaintCount);
        map.put("memberCollectionProductCount", memberCollectionProductCount);
        map.put("memberCollectionStoreCount", memberCollectionStoreCount);
        return map;
    }

}
