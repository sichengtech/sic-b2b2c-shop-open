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
package com.sicheng.seller.store.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreDecorate;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.xss.XssClean;
import com.sicheng.seller.store.service.StoreDecorateService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 店铺装修</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年6月20日 下午12:10:03
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeDecoration")
public class StoreDecorationController extends BaseController {



    @Autowired
    private StoreDecorateService storeDecorateService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040150";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 店铺装修页面
     *
     * @return
     */
    @RequiresPermissions("store:storeDecoration:view")
    @RequestMapping(value = "save1")
    public String save1(Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreDecorate storeDecorate = storeDecorateService.selectById(userSeller.getStoreId());
        model.addAttribute("storeDecorate", storeDecorate);
        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        return "seller/store/storeDecorationSave1";
    }

    /**
     * 保存装修模板
     *
     * @return
     */
    @RequiresPermissions("store:storeDecoration:edit")
    @RequestMapping(value = "save2")
    public String save2(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreDecorate storeDecorate = new StoreDecorate();
        storeDecorate.setStoreId(userSeller.getStoreId());
        storeDecorate.setSolution(R.get("solution"));
        String detail = R.get("content");
        if (StringUtils.isNotBlank(detail)) {
            String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
            String detailSafe = XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
            storeDecorate.setContent(detailSafe);
        }
        storeDecorateService.updateById(storeDecorate);
        addMessage(redirectAttributes, FYUtils.fyParams("保存模板成功"));
        return "redirect:" + sellerPath + "/store/storeDecoration/save1.htm";
    }

}
