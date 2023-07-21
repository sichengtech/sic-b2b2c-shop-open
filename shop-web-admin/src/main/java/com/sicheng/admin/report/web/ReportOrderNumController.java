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
 *  
 */
package com.sicheng.admin.report.web;

import com.sicheng.admin.report.dao.ReportDao;
import com.sicheng.admin.report.service.ReportService;

import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: ReportController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2017年7月4日 下午7:58:07
  *
  */
@Controller
@RequestMapping(value = "${adminPath}/report/orderNum")
public class ReportOrderNumController extends BaseController {



    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private TradeOrderService tradeOrderService;

    /**
     * 菜单高亮
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "010608";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
      * 统计一天的下单数量
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:orderNum:view")
    @RequestMapping(value = "countDayNum")
    public String countDayNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        Date searchTime = R.getDate("searchTime", "yyyy-MM-dd", new Date());//搜索时间
        Map<String, Date> mapTime = reportService.getDayTime(searchTime);//获取昨天、今天的开始时间与结束时间
        //昨天24小时下单数量
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("startDate", mapTime.get("yesterdayStart"));
        lMap.put("endDate", mapTime.get("yesterdayEnd"));
        lMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> lList = reportDao.report10(lMap);
        HashMap<String, Object> yMapData = reportService.getDayHashMap(lList, reportService.getHashMap());
        //今天24小时下单数量
        Map<String, Object> tMap = new HashMap<String, Object>();
        tMap.put("startDate", mapTime.get("todayStart"));
        tMap.put("endDate", mapTime.get("todayEnd"));
        tMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> tList = reportDao.report10(tMap);
        HashMap<String, Object> tMapData = reportService.getDayHashMap(tList, reportService.getHashMap());
        //下单数量折线图数据
        Map<String, Object> mapNewDate = reportService.getNewDate(yMapData, tMapData);
        //下单数量明细
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBeginPayOrderTime(mapTime.get("todayStart"));
        tradeOrder.setEndPayOrderTime(mapTime.get("todayEnd"));
        tradeOrder.setIsReturnStatus("0");
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeOrder);
        wrapper.and("a.order_status<>", "10");
        wrapper.and("a.order_status<>", "60");
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), wrapper);
        TradeOrder.fillUserMain(page.getList());
        //回显折线图数据
        model.addAttribute("jsonTimeNum", mapNewDate.get("jsonTimeNum"));
        model.addAttribute("jsonlData", mapNewDate.get("jsonlData"));
        model.addAttribute("jsontData", mapNewDate.get("jsontData"));
        //回显列表详情数据
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportOrderNumDay";
    }

    /**
      * 统计一周的下单数量
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:orderNum:view")
    @RequestMapping(value = "countWeekNum")
    public String countWeekNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        String[] months = ReportService.MONTHS;//初始化一年12个月
        String year = R.get("year", DateUtils.formatDate(new Date(), "yyyy"));//年
        String serchMonth = R.get("serchMonth", DateUtils.formatDate(new Date(), "MM"));//月
        Date yearMonth = null;
        try {
            yearMonth = DateUtils.parseDate(year + "/" + serchMonth, "yyyy/MM");//年月
        } catch (ParseException e) {
            logger.error("日期转换发生异常", e);
        }
        List<String> listWeek = reportService.getWeek(yearMonth);
        String searchTime = R.get("searchTime", listWeek.get(0));//搜索时间
        Map<String, Date> mapTime = reportService.getWeekTime(searchTime);//获取昨天、今天的开始时间与结束时间
        //上周数据
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("startDate", mapTime.get("lastWeekStart"));
        lMap.put("endDate", mapTime.get("lastWeekEnd"));
        lMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> lList = reportDao.report11(lMap);
        HashMap<String, Object> lMapDate = reportService.getWeekHashMap(lList, reportService.getWeekHashMap());
        //本周数据
        Map<String, Object> tMap = new HashMap<String, Object>();
        tMap.put("startDate", mapTime.get("thisWeekStart"));
        tMap.put("endDate", mapTime.get("thisWeekEnd"));
        tMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> tList = reportDao.report11(tMap);
        HashMap<String, Object> tMapDate = reportService.getWeekHashMap(tList, reportService.getWeekHashMap());
        //下单金额折线图数据
        Map<String, Object> mapNewDate = reportService.getNewDate(lMapDate, tMapDate);
        //下单进金额明细
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBeginPayOrderTime(mapTime.get("thisWeekStart"));
        tradeOrder.setEndPayOrderTime(mapTime.get("thisWeekEnd"));
        tradeOrder.setIsReturnStatus("0");
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeOrder);
        wrapper.and("a.order_status<>", "10");
        wrapper.and("a.order_status<>", "60");
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), wrapper);
        TradeOrder.fillUserMain(page.getList());
        //回显折线图数据
        model.addAttribute("jsonlData", mapNewDate.get("jsonlData"));
        model.addAttribute("jsontData", mapNewDate.get("jsontData"));
        //回显列表详情数据
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("months", months);
        model.addAttribute("year", year);
        model.addAttribute("serchMonth", serchMonth);
        model.addAttribute("listWeek", listWeek);
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportOrderNumWeek";
    }

    /**
      * 统计一月的下单数量 
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:orderNum:view")
    @RequestMapping(value = "countMonthNum")
    public String countMonthNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        Date searchTime = R.getDate("searchTime", "yyyy-MM", new Date());//搜索时间
        Map<String, Object> mapTime = reportService.getMonthTime(searchTime);//获取昨天、今天的开始时间与结束时间和本月的天数
        //上月数据
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("startDate", mapTime.get("lastMonthStart"));
        lMap.put("endDate", mapTime.get("lastMonthEnd"));
        lMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> lList = reportDao.report11(lMap);
        HashMap<String, Object> lMapData = reportService.getMonthHashMap(lList, reportService.getHashMap((int) mapTime.get("monthDays")));
        //本月数据
        Map<String, Object> tMap = new HashMap<String, Object>();
        tMap.put("startDate", mapTime.get("thisMonthStart"));
        tMap.put("endDate", mapTime.get("thisMonthEnd"));
        tMap.put("dbType$", Global.getConfig("jdbc.type"));
        List<Map<String, Object>> tList = reportDao.report11(tMap);
        HashMap<String, Object> tMapData = reportService.getMonthHashMap(tList, reportService.getHashMap((int) mapTime.get("monthDays")));
        //下单金额折线图数据
        Map<String, Object> mapNewDate = reportService.getNewDate(lMapData, tMapData);
        //下单进金额明细
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBeginPayOrderTime((Date) mapTime.get("thisMonthStart"));
        tradeOrder.setEndPayOrderTime((Date) mapTime.get("thisMonthEnd"));
        tradeOrder.setIsReturnStatus("0");
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeOrder);
        wrapper.and("a.order_status<>", "10");
        wrapper.and("a.order_status<>", "60");
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), wrapper);
        TradeOrder.fillUserMain(page.getList());
        //回显折线图数据
        model.addAttribute("jsonTimeNum", mapNewDate.get("jsonTimeNum"));
        model.addAttribute("jsonlData", mapNewDate.get("jsonlData"));
        model.addAttribute("jsontData", mapNewDate.get("jsontData"));
        //回显列表详情数据
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportOrderNumMonth";
    }
}
