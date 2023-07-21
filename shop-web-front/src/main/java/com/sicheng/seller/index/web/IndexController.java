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
package com.sicheng.seller.index.web;

import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.trade.service.TradeComplaintService;
import com.sicheng.seller.trade.service.TradeConsultationService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: seller商家中心首页</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月4日 下午3:13:05
 */
@Controller
@RequestMapping(value = "${sellerPath}/index")
public class IndexController extends BaseController {

    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private TradeConsultationService consulService;
    @Autowired
    private TradeComplaintService tradeComplaintService;

    /**
     * 进入seller商家后台主页面，这是seller的主入口
     *
     * @param request request
     * @param response response
     * @param model model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("userMain", SsoUtils.getUserMain());
        model.addAttribute("menu1id", null);//菜单高亮menu1id置为空，是为了配合lef.jsp的23~40行的逻辑
        return "seller/index";
    }

    /**
     *  ajax异步获取seller首页数据
     *  @param storeId
     *  @return
     */
    @ResponseBody
    @RequiresPermissions("user")
    @RequestMapping(value = "ajaxGetData")
    public Map<String, Object> ajaxGetData(Long storeId) {
        Map<String, Object> map = new HashMap<>();
        if (storeId == null) {
            return map;
        }
        //商品通知
        int spu1 = productSpuService.countByWhere(new Wrapper().and("a.store_id=", storeId).and("a.status=", "50"));//出售中的商品
        int spu2 = productSpuService.countByWhere(new Wrapper().and("a.store_id=", storeId).and("a.status=", "10"));//仓库中的商品
        int spu3 = productSpuService.countByWhere(new Wrapper().and("a.store_id=", storeId).and("a.status=", "20"));//禁售的商品
        //订单通知
        int tradeOrder1 = tradeOrderService.countByWhere(new Wrapper().and("a.store_id=", storeId));//订单总数
        int tradeOrder2 = tradeOrderService.countByWhere(new Wrapper().and("a.store_id=", storeId).and("a.order_status=", "10"));//待付款订单
        int tradeOrder3 = tradeOrderService.countByWhere(new Wrapper().and("a.store_id=", storeId).and("a.order_status=", "20"));//待发货订单
        //售后通知
        int returnMoney = tradeReturnOrderService.countByWhere(new Wrapper().and("a.store_id=", storeId));//退款通知
        int consul = consulService.countByWhere(new Wrapper().and("a.store_id=", storeId));//咨询通知
        int complaint = tradeComplaintService.countByWhere(new Wrapper().and("a.store_id=", storeId));//投诉通知
        //当月开始时间与当月结束时间
        Map<String, Object> mapTime = thisMonthTime();
        //当月订单数
        TradeOrder entity1 = new TradeOrder();
        entity1.setStoreId(storeId);
        entity1.setBeginCreateDate((Date) mapTime.get("thisMonthStart"));
        entity1.setEndCreateDate((Date) mapTime.get("thisMonthEnd"));
        int tradeOrderMonthNumber = tradeOrderService.countByWhere(new Wrapper(entity1));//当月订单数
        //预计本月收入
        TradeOrder entity2 = new TradeOrder();
        entity2.setStoreId(storeId);
        entity2.setBeginUpdateDate((Date) mapTime.get("thisMonthStart"));
        entity2.setEndUpdateDate((Date) mapTime.get("thisMonthEnd"));
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(entity2);
        wrapper.and("a.order_status<>", "10");
        wrapper.and("a.order_status<>", "60");
        Map<String, Object> mapMonry = tradeOrderService.sumByWhere(wrapper);//预计本月收入
        //数据回显
        map.put("spu1", spu1);
        map.put("spu2", spu2);
        map.put("spu3", spu3);
        map.put("tradeOrder1", tradeOrder1);
        map.put("tradeOrder2", tradeOrder2);
        map.put("tradeOrder3", tradeOrder3);
        map.put("returnMoney", returnMoney);
        map.put("consul", consul);
        map.put("complaint", complaint);
        map.put("tradeOrderMonthNumber", tradeOrderMonthNumber);
        if (mapMonry == null) {
            map.put("tradeOrderMonthMoney", 0);
        } else {
            map.put("tradeOrderMonthMoney", mapMonry.get("AMOUNTPAID"));
        }
        return map;
    }

    /**
     *  计算当月开始时间与当月结束时间 
     *  @return
     */
    private Map<String, Object> thisMonthTime() {
        Date thisMonthStart = null;//本月开始时间
        Date thisMonthEnd = null;//本月结束时间
        int year = Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"));
        int month = Integer.valueOf(DateUtils.formatDate(new Date(), "MM"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);//设置年
        cal.set(Calendar.MONTH, month - 1);//设置本月
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//本月的第一天
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), firstDay, 00, 00, 00);//按你的要求设置时间
        thisMonthStart = cal.getTime();
        int laseDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月的最后天
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), laseDay, 23, 59, 59);//按你的要求设置时间
        thisMonthEnd = cal.getTime();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("thisMonthStart", thisMonthStart);
        map.put("thisMonthEnd", thisMonthEnd);
        return map;
    }
}
