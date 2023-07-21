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
package com.sicheng.member.collect.web;

import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.member.service.MemberCollectionStoreService;
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

/**
 * <p>标题: 我收藏的店铺</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年4月21日 上午10:59:51
 */
@Controller
@RequestMapping(value = "${memberPath}/collect/memberCollectionStore")
public class MemberCollectioStoreController extends BaseController {

    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting( "collectionStore");//三级菜单高亮
    }

    /**
     * 我收藏的店铺
     *
     * @param memberCollectionStore
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(MemberCollectionStore memberCollectionStore, HttpServletRequest request, HttpServletResponse response, Model model) {
        memberCollectionStore.setUId(SsoUtils.getUserMain().getUId());
        Page<MemberCollectionStore> page = memberCollectionStoreService.selectByWhere(new Page<MemberCollectionStore>(request, response), new Wrapper(memberCollectionStore));
        model.addAttribute("page", page);
        //菜单高亮
        MemberMenuInterceptor.menuHighLighting("collectionStore");//三级菜单高亮
        return "member/collect/memberCollectionStore";
    }

    /**
     * 删除
     *
     * @param memberCollectionStore 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(MemberCollectionStore memberCollectionStore, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (memberCollectionStore == null || memberCollectionStore.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("收藏的店铺不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        memberCollectionStore.setUId(SsoUtils.getUserMain().getUId());
        memberCollectionStoreService.deleteByWhere(new Wrapper(memberCollectionStore));
        addMessage(redirectAttributes, FYUtils.fyParams("删除收藏店铺成功！"));
        return "redirect:" + memberPath + "/collect/memberCollectionStore/list.htm?repage";
    }

}
