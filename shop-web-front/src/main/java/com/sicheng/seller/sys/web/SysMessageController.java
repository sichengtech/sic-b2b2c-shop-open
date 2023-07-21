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
package com.sicheng.seller.sys.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.SysMessageService;
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
 * <p>标题: StoreMessageController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhangjiali
 * @version 2017年2月24日 下午3:01:44
 */
@Controller
@RequestMapping(value = "${sellerPath}/sys/sysMessage")
public class SysMessageController extends BaseController {
    @Autowired
    private SysMessageService sysMessageService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020220";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 查询所属卖家的系统消息
     *
     * @param message  系统所发的消息
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("sys:sysMessage:view")
    @RequestMapping(value = "list")
    public String list(SysMessage message, Model model, HttpServletRequest request, HttpServletResponse response) {
        String type = R.get("type");//消息类型1.交易信息 2退换货信息 3商品信息 4 运营信息
        //查询所属卖家的系统消息，按未读在前，已读在后排序
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //message.setReceiveType("1");//接收人类型：1卖家、 2买家
        message.setUId(userSeller.getUId());
        Wrapper w = new Wrapper();
        w.orderBy("reading");
        w.setEntity(message);
        Page<SysMessage> page = sysMessageService.selectByWhere(new Page<SysMessage>(request, response), w);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        return "seller/sys/sysMessageList";
    }

    /**
     * 批量修改所选消息为已读
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "edit")
    public String edit(RedirectAttributes redirectAttributes) {
        List<Object> list = Lists.newArrayList();
        String[] ids = R.get("ids").split(",");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                list.add(ids[i]);
            }
            if (!list.isEmpty()) {
                SysMessage message = new SysMessage();
                message.setReading("1");//0未读、1已读 （只限站内信）
                Wrapper wrapper = new Wrapper();
                wrapper.and("a.information_id in", list);
                wrapper.and("a.u_id = ", SsoUtils.getUserMain().getUserSeller().getId());
                sysMessageService.updateByWhereSelective(message, wrapper);
            }
            addMessage(redirectAttributes, FYUtils.fyParams("成功标记所选消息为已读"));
        }
        return "redirect:" + sellerPath + "/sys/sysMessage/list.htm";
    }

    /**
     * 删除单条消息
     *
     * @param message            系统所发的消息
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "delete")
    public String delete(SysMessage message, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (message == null || message.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺消息不存在！"));
            return "error/400";

        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        message.setUId(userSeller.getUId());//属主检查
        sysMessageService.deleteByWhere(new Wrapper(message));
        addMessage(redirectAttributes, FYUtils.fyParams("成功删除此消息"));
        return "redirect:" + sellerPath + "/sys/sysMessage/list.htm";
    }

    /**
     * 批量删除所选消息
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(RedirectAttributes redirectAttributes) {
        List<Object> list = Lists.newArrayList();
        String[] ids = R.get("ids").split(",");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                list.add(Long.valueOf(ids[i]));
            }
            if (!list.isEmpty()) {
                Wrapper wrapper = new Wrapper();
                wrapper.and("a.information_id in", list);
                wrapper.and("a.u_id = ", SsoUtils.getUserMain().getUserSeller().getId());
                sysMessageService.deleteByWhere(wrapper);
            }
            addMessage(redirectAttributes, FYUtils.fyParams("成功删除所选消息"));
        }
        return "redirect:" + sellerPath + "/sys/sysMessage/list.htm";
    }
}
