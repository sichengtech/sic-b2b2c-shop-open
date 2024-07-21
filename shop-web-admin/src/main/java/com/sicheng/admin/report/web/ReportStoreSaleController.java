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

package com.sicheng.admin.report.web;

import com.sicheng.admin.report.dao.ReportDao;
import com.sicheng.admin.report.service.ReportService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
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
  * @author zjl
  * @version 2017年7月4日 下午7:58:07
  *
  */
@Controller
@RequestMapping(value = "${adminPath}/report/storeSale")
public class ReportStoreSaleController extends BaseController {



    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDao reportDao;

    /**
     * 菜单高亮
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "010605";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
      * 统计一天的店铺销售  
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:storeSale:view")
    @RequestMapping(value = "countDayNum")
    public String countDayNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        String storeName = R.get("storeName");//店铺名称
        if (StringUtils.isNotBlank(storeName)) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
        }
        Date searchTime = R.getDate("searchTime", "yyyy-MM-dd", new Date());//搜索时间
        Map<String, Date> mapTime = reportService.getDayTime(searchTime);//获取昨天、今天的开始时间与结束时间
        //当天24小时商品销售数据
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(request, response);
        page.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeName", storeName);
        map.put("startDate", mapTime.get("todayStart"));
        map.put("endDate", mapTime.get("todayEnd"));
        map.put("page", page);
        List<Map<String, Object>> list = reportDao.report7(map);
        //回显列表数据
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("storeName", storeName);
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportStoreSaleDayNum";
    }

    /**
      * 统计一周的店铺销售 
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:storeSale:view")
    @RequestMapping(value = "countWeekNum")
    public String countWeekNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        String storeName = R.get("storeName");//店铺名称
        if (StringUtils.isNotBlank(storeName)) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
        }
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
        //本周会员购买数据
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(request, response);
        page.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeName", storeName);
        map.put("startDate", mapTime.get("thisWeekStart"));
        map.put("endDate", mapTime.get("thisWeekEnd"));
        map.put("page", page);
        List<Map<String, Object>> list = reportDao.report7(map);
        //回显列表数据
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("storeName", storeName);
        model.addAttribute("months", months);
        model.addAttribute("year", year);
        model.addAttribute("serchMonth", serchMonth);
        model.addAttribute("listWeek", listWeek);
        model.addAttribute("searchTime", searchTime);
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportStoreSaleWeekNum";
    }

    /**
      * 统计一月的店铺销售
      * @param request
      * @param response
      * @param model
      * @return
     */
    @RequiresPermissions("report:storeSale:view")
    @RequestMapping(value = "countMonthNum")
    public String countMonthNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索条件
        String storeName = R.get("storeName");//店铺名称
        if (StringUtils.isNotBlank(storeName)) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
        }
        Date searchTime = R.getDate("searchTime", "yyyy-MM", new Date());//搜索时间
        Map<String, Object> mapTime = reportService.getMonthTime(searchTime);//获取昨天、今天的开始时间与结束时间和本月的天数
        //本月商品销售数据
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(request, response);
        page.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeName", storeName);
        map.put("startDate", mapTime.get("thisMonthStart"));
        map.put("endDate", mapTime.get("thisMonthEnd"));
        map.put("page", page);
        List<Map<String, Object>> list = reportDao.report7(map);
        //回显列表数据
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        //回显搜索条件
        model.addAttribute("storeName", storeName);
        model.addAttribute("searchTime", searchTime);
        return "admin/report/reportStoreSaleMonthNum";
    }
}
