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
package com.sicheng.seller.user.web;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreAdminLog;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreAdminLogService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: SellerLogController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhangjiali
 * @version 2017年2月27日 下午5:55:38
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeAdminLog")
public class SellerLogController extends BaseController {

    @Autowired
    private StoreAdminLogService logService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "060303";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    @RequiresPermissions("store:storeAdminLog:view")
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        //搜索条件
        String title = R.get("title");//日志标题
        Date beginCreateDate = R.getDate("beginCreateDate", "yyyy-MM-dd HH:mm:ss", null);//日志创建的开始时间
        Date endCreateDate = R.getDate("endCreateDate", "yyyy-MM-dd HH:mm:ss", null);//日志创建的结束时间
        //日志查询
        StoreAdminLog storeAdminLog = new StoreAdminLog();
        storeAdminLog.setTitle(title);
        storeAdminLog.setBeginCreateDate(beginCreateDate);
        storeAdminLog.setEndCreateDate(endCreateDate);
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAdminLog.setStoreId(userSeller.getStoreId());
        Page<StoreAdminLog> page = logService.selectByWhere(new Page<StoreAdminLog>(request, response), new Wrapper(storeAdminLog));
        StoreAdminLog.fillUserMain(page.getList());
        model.addAttribute("page", page);
        model.addAttribute("log", storeAdminLog);
        return "/seller/user/userAdminLogList";
    }
}
