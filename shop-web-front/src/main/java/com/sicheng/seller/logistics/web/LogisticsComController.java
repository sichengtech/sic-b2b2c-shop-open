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
package com.sicheng.seller.logistics.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreLc;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.logistics.service.LogisticsCompanyService;
import com.sicheng.seller.store.service.StoreLcService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>标题: LogisticsComController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月22日 上午10:10:51
 */
@Controller
@RequestMapping(value = "${sellerPath}/logistics/logisticsCompany")
public class LogisticsComController extends BaseController {
    @Autowired
    private LogisticsCompanyService logistComService;
    @Autowired
    private StoreLcService storeLcService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020125";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    @RequiresPermissions("logistics:logisticsCompany:view")
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        //店铺添加的物流
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreLc storeLc = new StoreLc();
        storeLc.setStoreId(userSeller.getStoreId());
        List<StoreLc> storeLcs = storeLcService.selectByWhere(new Wrapper(storeLc));
        //物流公司
        LogisticsCompany logisticsCompany = new LogisticsCompany();
        logisticsCompany.setStatus("1");//状态，0禁用、1启用
        Wrapper w = new Wrapper();
        w.orderBy("is_common_use desc");//是否是常用快递，0否、1是
        w.setEntity(logisticsCompany);
        List<LogisticsCompany> list = logistComService.selectByWhere(w);
        model.addAttribute("list", list);
        model.addAttribute("storeLcs", storeLcs);
        return "seller/logistics/logisticsCompanyList";
    }

    @RequiresPermissions("logistics:logisticsCompany:edit")
    @RequestMapping(value = "save")
    public String save(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //物流公司id
        String[] lcIds = R.getArray("lcIds");
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //店铺添加的物流
        StoreLc entity = new StoreLc();
        entity.setStoreId(userSeller.getStoreId());
        List<StoreLc> storeLcs = storeLcService.selectByWhere(new Wrapper(entity));
        List<StoreLc> list = Lists.newArrayList();
        if (storeLcs.isEmpty() && storeLcs.size() == 0) {
            //执行添加
            if (lcIds.length > 0) {
                for (int i = 0; i < lcIds.length; i++) {
                    StoreLc storeLc = new StoreLc();
                    storeLc.setStoreId(userSeller.getStoreId());
                    storeLc.setLcId(Long.parseLong(lcIds[i]));
                    list.add(storeLc);
                }
                //保存成功提示
                storeLcService.insertSelectiveBatch(list);
                addMessage(redirectAttributes, FYUtils.fyParams("保存物流公司成功"));
            } else {
                addMessage(redirectAttributes, FYUtils.fyParams("没有选择物流公司，将会选用默认的物流公司"));
            }
        } else {
            //执行修改
            storeLcService.deleteByWhere(new Wrapper(entity));
            if (lcIds.length > 0) {
                for (int i = 0; i < lcIds.length; i++) {
                    StoreLc storeLc = new StoreLc();
                    storeLc.setStoreId(userSeller.getStoreId());
                    storeLc.setLcId(Long.parseLong(lcIds[i]));
                    list.add(storeLc);
                }
                //保存成功提示
                storeLcService.insertSelectiveBatch(list);
                addMessage(redirectAttributes, FYUtils.fyParams("保存物流公司成功"));
            } else {
                addMessage(redirectAttributes, FYUtils.fyParams("没有选择物流公司，将会选用默认的物流公司"));
            }
        }
        return "redirect:" + sellerPath + "/logistics/logisticsCompany/list.htm";
    }

}
