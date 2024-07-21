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
package com.sicheng.admin.sys.web;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sys.entity.SysMessage;

import com.sicheng.admin.sys.service.SysMessageSafeService;
import com.sicheng.admin.sys.service.SysMessageService;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员通知 Controller
 * 所属模块：sys
 *
 * @author cl
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysMessage")
public class SysMessageController extends BaseController {

    @Autowired
    private SysMessageService sysMessageService;



    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SysMessageSafeService sysMessageSafeService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysMessage 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysMessage:view")
    @RequestMapping(value = "list")
    public String list(SysMessage sysMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        String loginName = R.get("loginName");
        model.addAttribute("loginName", loginName);
        //用买家名字去会员换取id
        Long uId = 0L;
        if (StringUtils.isNotBlank(loginName)) {
            //用户名转换小写
            loginName = loginName.toLowerCase();
            //用名字去换取id
            UserMain userMain = new UserMain();
            userMain.setLoginName(loginName);
            List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
            if (!userMains.isEmpty()) {
                uId = userMains.get(0).getUId();
                sysMessage.setUId(uId);
            } else {
                return "admin/sys/sysMessageList";
            }
        }
        Page<SysMessage> page = sysMessageService.selectByWhere(new Page<SysMessage>(request, response), new Wrapper(sysMessage));
        SysMessage.fillUserMain(page.getList());
        model.addAttribute("page", page);
        return "admin/sys/sysMessageList";
    }

    /**
     * 进入保存页面
     *
     * @param sysMessage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysMessage:save")
    @RequestMapping(value = "save1")
    public String save1(SysMessage sysMessage, Model model) {
        if (sysMessage == null) {
            sysMessage = new SysMessage();
        }
        model.addAttribute("sysMessage", sysMessage);
        return "admin/sys/sysMessageForm";
    }

    /**
     * 执行保存
     *
     * @param sysMessage         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:save")
    @RequestMapping(value = "save2")
    public String save2(SysMessage sysMessage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysMessage, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysMessage, model);//回显错误提示
        }

        //业务处理
        sysMessageService.insertSelective(sysMessage);
        addMessage(redirectAttributes, FYUtils.fyParams("保存会员通知成功"));
        return "redirect:" + Global.getAdminPath() + "/sys/sysMessage/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysMessage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysMessage sysMessage, Model model) {
        SysMessage entity = null;
        if (sysMessage != null) {
            if (sysMessage.getId() != null) {
                entity = sysMessageService.selectById(sysMessage.getId());
            }
        }
        model.addAttribute("sysMessage", entity);
        return "admin/sys/sysMessageForm";
    }

    /**
     * 执行编辑
     *
     * @param sysMessage         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysMessage sysMessage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysMessage, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysMessage, model);//回显错误提示
        }

        //业务处理
        sysMessageService.updateByIdSelective(sysMessage);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑会员通知成功"));
        return "redirect:" + Global.getAdminPath() + "/sys/sysMessage/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysMessage         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:drop")
    @RequestMapping(value = "delete")
    public String delete(SysMessage sysMessage, RedirectAttributes redirectAttributes) {
        sysMessageService.deleteById(sysMessage.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除会员通知成功"));
        return "redirect:" + Global.getAdminPath() + "/sys/sysMessage/list.do?repage";
    }

    /**
     * 进入消息群发页面
     *
     * @param sysMessage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @RequestMapping(value = "mass1")
    public String mass1(Model model) {
        String menu3 = "040104";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        return "admin/sys/sysMessageMassForm";
    }

    /**
     * 保存消息群发页面
     *
     * @param sysMessage         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @ResponseBody
    @RequestMapping(value = "mass2")
    public Map<String, Object> mass2(RedirectAttributes redirectAttributes) {
        String uIds = R.get("uIds");
        String message = R.get("message");
        String sysMode = R.get("sysMode");//1站内信2短信3邮件
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(uIds) || StringUtils.isBlank(message) || StringUtils.isBlank(sysMode)) {
            map.put("status", "2");
            map.put("content", FYUtils.fyParams("通知群发失败"));
            return map;
        }
        sysMessageSafeService.messageMass(uIds, message, sysMode);
        map.put("status", "1");
        map.put("content", FYUtils.fyParams("通知群发成功"));
        return map;
    }

    /**
     * ajax 获取会员列表
     *
     * @param userMain
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysMessage:edit")
    @ResponseBody
    @RequestMapping(value = "userList")
    public Map<String, Object> userList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String typeUser = R.get("typeUser");
        String loginName = R.get("loginName");
        String pageNo = R.get("pageNo");
        Map<String, Object> map = new HashMap<>();
        UserMain userMain = new UserMain();
        Wrapper wrapper = new Wrapper();
        //分页
        if (!StringUtils.isNumeric(pageNo)) {
            return map;
        }
        //搜索条件-会员类型
        if (StringUtils.isNotBlank(typeUser)) {
            //个人
            if ("1".equals(typeUser)) {
                wrapper.and("a.type_user like", "_________1");
            }
            //供应商
            if ("2".equals(typeUser)) {
                wrapper.and("a.type_user like", "______1___");
            }
            //门店服务商
            if ("3".equals(typeUser)) {
                wrapper.and("a.type_user like", "_______1__");
            }
            //采购商
            if ("4".equals(typeUser)) {
                wrapper.and("a.type_user like", "________1_");
            }
        }
        //搜索条件-用户名
        if (StringUtils.isNotBlank(loginName)) {
            //用户名转小写
            userMain.setLoginName(loginName.toLowerCase());
        }
        userMain.setParentUid(0L);//0表示主账号
        wrapper.setEntity(userMain);
        Page<UserMain> page = new Page<UserMain>(request, response);
        page.setPageSize(15);
        page.setPageNo(Integer.parseInt(pageNo));
        page = userMainService.selectByWhere(page, wrapper);
        map.put("page", page);
        map.put("isLast", page.isLastPage());
        map.put("isFirst", page.isFirstPage());
        map.put("typeUser", typeUser);
        map.put("loginName", loginName);
        return map;
    }

    /**
     * 表单验证
     *
     * @param sysMessage 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysMessage sysMessage, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("sender"))) {
            errorList.add(FYUtils.fyParams(FYUtils.fyParams("发送人类型不能为空")));
        }
        if (StringUtils.isNotBlank(R.get("sender")) && R.get("sender").length() > 1) {
            errorList.add(FYUtils.fyParams("发送人类型最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("receiveType"))) {
            errorList.add(FYUtils.fyParams("接收人类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("receiveType")) && R.get("receiveType").length() > 1) {
            errorList.add(FYUtils.fyParams("接收人类型最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("mId")) && R.get("mId").length() > 19) {
            errorList.add(FYUtils.fyParams("买家最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add(FYUtils.fyParams("卖家最大长度不能超过19字符"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("消息类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("消息类型最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("statusMsg"))) {
            errorList.add(FYUtils.fyParams("站内信是否发送不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("statusMsg")) && R.get("statusMsg").length() > 1) {
            errorList.add(FYUtils.fyParams("站内信是否发送最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("statusSms"))) {
            errorList.add(FYUtils.fyParams("短信是否发送不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("statusSms")) && R.get("statusSms").length() > 1) {
            errorList.add(FYUtils.fyParams("短信是否发送最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("statusEmail"))) {
            errorList.add(FYUtils.fyParams("邮件是否发送不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("statusEmail")) && R.get("statusEmail").length() > 1) {
            errorList.add(FYUtils.fyParams("邮件是否发送最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("statusWeixin"))) {
            errorList.add(FYUtils.fyParams("微信是否发送不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("statusWeixin")) && R.get("statusWeixin").length() > 1) {
            errorList.add(FYUtils.fyParams("微信是否发送最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("statusB"))) {
            errorList.add(FYUtils.fyParams("预留是否发送不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("statusB")) && R.get("statusB").length() > 1) {
            errorList.add(FYUtils.fyParams("预留是否发送最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 64) {
            errorList.add(FYUtils.fyParams("消息标题，预留最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("消息内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 512) {
            errorList.add(FYUtils.fyParams("消息内容最大长度不能超过512字符"));
        }
        if (StringUtils.isBlank(R.get("reading"))) {
            errorList.add(FYUtils.fyParams("站内信是否已读不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("reading")) && R.get("reading").length() > 1) {
            errorList.add(FYUtils.fyParams("站内信是否已读最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段1最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段2最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段3最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段4最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段5最大长度不能超过64字符"));
        }
        return errorList;
    }

}