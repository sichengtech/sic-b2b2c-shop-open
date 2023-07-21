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
package com.sicheng.member.user.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.sys.service.SysMessageService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 我的消息</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年4月18日 下午5:15:19
 */
@Controller
@RequestMapping(value = "${memberPath}/user/memberMessage")
public class MemberMessageController extends BaseController {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("userMessage");//三级菜单高亮
    }

    /**
     * 我的消息列表
     *
     * @param sysMessage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(SysMessage sysMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("type", sysMessage.getType());
        sysMessage.setSender("0");//0 表示“系统“
        //sysMessage.setReceiveType("2");//接收人类型：1卖家、 2买家
        sysMessage.setUId(SsoUtils.getUserMain().getUId());
        sysMessage.setStatusMsg("1");//站内信：0未发送、1已发送
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(sysMessage);
        wrapper.orderBy("a.reading asc");
        Page<SysMessage> page = sysMessageService.selectByWhere(new Page<SysMessage>(request, response), wrapper);
        sysMessage.setReading("0");//0未读、1已读 （只限站内信）
        //获取交易消息类型的未读数量
        sysMessage.setType("1");//类型1.交易信息 2退换货信息 3商品信息 4 运营信息
        int countType1 = sysMessageService.countByWhere(new Wrapper(sysMessage));
        sysMessage.setType("2");//类型1.交易信息 2退换货信息 3商品信息 4 运营信息
        int countType2 = sysMessageService.countByWhere(new Wrapper(sysMessage));
        sysMessage.setType("3");//类型1.交易信息 2退换货信息 3商品信息 4 运营信息
        int countType3 = sysMessageService.countByWhere(new Wrapper(sysMessage));
        sysMessage.setType("4");//类型1.交易信息 2退换货信息 3商品信息 4 运营信息
        int countType4 = sysMessageService.countByWhere(new Wrapper(sysMessage));
        model.addAttribute("page", page);
        model.addAttribute("countType1", countType1);
        model.addAttribute("countType2", countType2);
        model.addAttribute("countType3", countType3);
        model.addAttribute("countType4", countType4);
        return "member/user/memberMessage";
    }


    /**
     * 删除单一消息
     *
     * @param sysMessage         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(SysMessage sysMessage, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (sysMessage == null || sysMessage.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺文章不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        sysMessage.setUId(SsoUtils.getUserMain().getUId());//属主检查
        sysMessageService.deleteByWhere(new Wrapper(sysMessage));
        addMessage(redirectAttributes, FYUtils.fyParams("删除消息成功！"));
        return "redirect:" + memberPath + "/user/memberMessage/list.htm?repage";
    }

    /**
     * 批量删除消息
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "deleteBatch")
    public String deleteBatch(RedirectAttributes redirectAttributes, Model model) {
        String ifi = R.get("informationIds");
        //入参检查
        if (StringUtils.isBlank(ifi)) {
            model.addAttribute("message", FYUtils.fyParams("没有选中消息！"));
            return "member/user/memberMessage";
        }
        //检查合格后，业务处理
        String[] informationIds = ifi.split(",");
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < informationIds.length; i++) {
            list.add(informationIds[i]);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.information_id in", list);
        wrapper.and("a.u_id = ", SsoUtils.getUserMain().getUId());
        sysMessageService.deleteByWhere(wrapper);
        addMessage(redirectAttributes, FYUtils.fyParams("批量删除消息成功！"));
        return "redirect:" + memberPath + "/user/memberMessage/list.htm?repage";
    }

    /**
     * 批量标记为已读
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "readBatch")
    public String readBatch(RedirectAttributes redirectAttributes, Model model) {
        String ifi = R.get("informationIds");
        //入参检查
        if (StringUtils.isBlank(ifi)) {
            model.addAttribute("message", FYUtils.fyParams("没有选中消息！"));
            return "member/user/memberMessage";
        }
        //检查合格后，业务处理
        String[] informationIds = ifi.split(",");
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < informationIds.length; i++) {
            list.add(informationIds[i]);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.information_id in", list);
        wrapper.and("a.u_id = ", SsoUtils.getUserMain().getUId());
        SysMessage sysMessage = new SysMessage();
        sysMessage.setReading("1");//0未读、1已读 （只限站内信）
        sysMessageService.updateByWhereSelective(sysMessage, wrapper);
        addMessage(redirectAttributes, FYUtils.fyParams("批量已读成功！"));
        return "redirect:" + memberPath + "/user/memberMessage/list.htm?repage";
    }
}
