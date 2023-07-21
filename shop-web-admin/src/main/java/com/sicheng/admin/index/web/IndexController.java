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
package com.sicheng.admin.index.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.report.dao.ReportDao;
import com.sicheng.admin.report.service.ReportService;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.service.SiteInfoService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.service.StoreService;

import com.sicheng.admin.task.entity.TaskAdminIndex;
import com.sicheng.admin.task.service.TaskAdminIndexService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: admin管理后台首页</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月23日 下午5:16:05
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class IndexController extends BaseController {
    @Autowired
    private TaskAdminIndexService taskAdminIndexService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private StoreService storeService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "110101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入admin管理后台首页，这是admin的主入口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        //首页数据
        TaskAdminIndex adminIndex = taskAdminIndexService.selectOne(new Wrapper());
        model.addAttribute("adminIndex", adminIndex);
        //商家月销量排行
        Map<String, Object> mapTime = reportService.getMonthTime(new Date());//获取上月、本月的开始时间与结束时间和本月的天数
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(request, response);
        page.setPageSize(5);
        page.setPageNo(1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", mapTime.get("thisMonthStart"));
        map.put("endDate", mapTime.get("thisMonthEnd"));
        map.put("page", page);
        List<Map<String, Object>> list = reportDao.report7(map);
        List<Object> storeIds = Lists.newArrayList();
        DecimalFormat df = new DecimalFormat(".#####");
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Double payMoney = Double.valueOf(list.get(i).get("PAYMONEY").toString());
                Double onePayMoney = Double.valueOf(list.get(0).get("PAYMONEY").toString());
                list.get(i).put("count", df.format(payMoney * 100 / onePayMoney));
                storeIds.add(list.get(i).get("STOREID"));
            }
            if (storeIds.size() > 0) {
                List<Store> listStore = storeService.selectByIdIn(storeIds);
                if (!listStore.isEmpty()) {
                    for (Store store : listStore) {
                        for (int i = 0; i < list.size(); i++) {
                            if (store.getId().toString().equals(list.get(i).get("STOREID").toString())) {
                                list.get(i).put("store", store);
                                break;
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("list", list);

        return "admin/index";
    }

    /**
     * 获取主题方案 （无用）
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(request, response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }
}
