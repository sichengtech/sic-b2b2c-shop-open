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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.entity.SiteMessageTemplate;
import com.sicheng.admin.site.service.SiteMessageTemplateService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.commons.lang3.StringEscapeUtils;
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
import java.util.List;

/**
 * 管理网站的消息模板 Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteMessageTemplate")
public class SiteMessageTemplateController extends BaseController {

    @Autowired
    private SiteMessageTemplateService siteMessageTemplateService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070110";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteMessageTemplate 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:view")
    @RequestMapping(value = "list")
    public String list(SiteMessageTemplate siteMessageTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteMessageTemplate> page = siteMessageTemplateService.selectByWhere(new Page<SiteMessageTemplate>(request, response), new Wrapper(siteMessageTemplate));
        model.addAttribute("page", page);
        return "admin/site/siteMessageTemplateList";
    }

    /**
     * 进入保存页面
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:save")
    @RequestMapping(value = "save1")
    public String save1(SiteMessageTemplate siteMessageTemplate, Model model) {
        if (siteMessageTemplate == null) {
            siteMessageTemplate = new SiteMessageTemplate();
        }
        model.addAttribute("siteMessageTemplate", siteMessageTemplate);
        return "admin/site/siteMessageTemplateForm";
    }

    /**
     * 执行保存
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:save")
    @RequestMapping(value = "save2")
    public String save2(SiteMessageTemplate siteMessageTemplate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldNum = R.get("oldNum");//已添加的编号
        List<String> errorList = validate(siteMessageTemplate, oldNum, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteMessageTemplate, model);//回显错误提示
        }
        String html_unsafe = StringEscapeUtils.unescapeHtml4(siteMessageTemplate.getEmailContent());//转回来（还原）
        //String html_safe=XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
        siteMessageTemplate.setEmailContent(html_unsafe);
        //业务处理
        siteMessageTemplateService.insertSelective(siteMessageTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("保存消息模板成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteMessageTemplate siteMessageTemplate, Model model) {
        SiteMessageTemplate entity = null;
        if (siteMessageTemplate != null) {
            if (siteMessageTemplate.getId() != null) {
                entity = siteMessageTemplateService.selectById(siteMessageTemplate.getId());
            }
        }
        String content = entity.getEmailContent();
        if (StringUtils.isNotBlank(content)) {
            entity.setEmailContent(StringEscapeUtils.escapeHtml4(content));
        }
        model.addAttribute("siteMessageTemplate", entity);
        return "admin/site/siteMessageTemplateForm";
    }

    /**
     * 执行编辑
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteMessageTemplate siteMessageTemplate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldNum = R.get("oldNum");//已添加的编号
        List<String> errorList = validate(siteMessageTemplate, oldNum, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteMessageTemplate, model);//回显错误提示
        }
        String html_unsafe = StringEscapeUtils.unescapeHtml4(siteMessageTemplate.getEmailContent());//转回来（还原）
        //String html_safe=XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
        siteMessageTemplate.setEmailContent(html_unsafe);
        //业务处理
        siteMessageTemplateService.updateByIdSelective(siteMessageTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑消息模板成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 执行站内信修改
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "siteMessage")
    public String siteMessage(Model model, RedirectAttributes redirectAttributes) {
        //业务处理
        Long id = R.getLong("messageId");
        String isOpen = R.get("isOpen", "1");
        String msgContent = R.get("msgContent");
        SiteMessageTemplate siteMessageTemplate = new SiteMessageTemplate();
        siteMessageTemplate.setId(id);//消息模板id
        siteMessageTemplate.setIsOpen(isOpen);//是否开启站内信（0否、1是）
        siteMessageTemplate.setMsgContent(msgContent);//站内信：站内信模板内容
        siteMessageTemplateService.updateByIdSelective(siteMessageTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑站内信成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 执行短信修改
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "sms")
    public String sms(Model model, RedirectAttributes redirectAttributes) {
        //业务处理
        Long id = R.getLong("smsId");
        String smsOpen = R.get("smsOpen", "1");
        String smsContent = R.get("smsContent");
        String thirdTemplateCode = R.get("thirdTemplateCode");
        SiteMessageTemplate siteMessageTemplate = new SiteMessageTemplate();
        siteMessageTemplate.setId(id);//消息模板id
        siteMessageTemplate.setSmsOpen(smsOpen);//是否开启短信（0否、1是）
        siteMessageTemplate.setSmsContent(smsContent);//短信模板内容
        siteMessageTemplate.setThirdtemplatecode(thirdTemplateCode);//第三方短信模板id
        siteMessageTemplateService.updateByIdSelective(siteMessageTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑短信成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 进入编辑邮件页面
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "email1")
    public String email1(SiteMessageTemplate siteMessageTemplate, Model model) {
        SiteMessageTemplate entity = null;
        if (siteMessageTemplate != null) {
            if (siteMessageTemplate.getId() != null) {
                entity = siteMessageTemplateService.selectById(siteMessageTemplate.getId());
            }
        }
        String content = entity.getEmailContent();
        if (StringUtils.isNotBlank(content)) {
            entity.setEmailContent(StringEscapeUtils.escapeHtml4(content));
        }
        model.addAttribute("siteMessageTemplate", entity);
        return "admin/site/siteEmailTemplateForm";
    }

    /**
     * 执行邮件编辑
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:edit")
    @RequestMapping(value = "email2")
    public String email2(SiteMessageTemplate siteMessageTemplate, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(R.get("emailTitle"))) {
            addMessage(model, FYUtils.fyParams("请填写模板标题"));
            return email1(siteMessageTemplate, model);//回显错误提示
        }
        if (StringUtils.isBlank(R.get("emailContent"))) {
            addMessage(model, FYUtils.fyParams("请填写模板内容"));
            return email1(siteMessageTemplate, model);//回显错误提示
        }
        //业务处理
        String emailOpen = R.get("emailOpen", "0");
        siteMessageTemplate.setEmailOpen(emailOpen);
        String html_unsafe = StringEscapeUtils.unescapeHtml4(siteMessageTemplate.getEmailContent());//转回来（还原）
        //String html_safe=XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
        siteMessageTemplate.setEmailContent(html_unsafe);
        siteMessageTemplateService.updateByIdSelective(siteMessageTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑邮件成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteMessageTemplate 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteMessageTemplate:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteMessageTemplate siteMessageTemplate, RedirectAttributes redirectAttributes) {
        siteMessageTemplateService.deleteById(siteMessageTemplate.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除消息模板成功"));
        return "redirect:" + adminPath + "/site/siteMessageTemplate/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteMessageTemplate 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteMessageTemplate siteMessageTemplate, String oldNum, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("num"))) {
            errorList.add(FYUtils.fyParams("模板编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("num"))) {
            if (R.get("num").length() > 64) {
                errorList.add(FYUtils.fyParams("模板编号最大长度不能超过64字符"));
            }
            SiteMessageTemplate template = new SiteMessageTemplate();
            template.setNum(R.get("num"));
            List<SiteMessageTemplate> list = siteMessageTemplateService.selectByWhere(new Wrapper(template));
            if (StringUtils.isBlank(oldNum)) {
                if (list.size() > 0) {
                    errorList.add(FYUtils.fyParams("模板编号已存在"));
                }
            } else if (!R.get("num").equals(oldNum)) {
                if (list.size() > 0) {
                    errorList.add(FYUtils.fyParams("模板编号已存在"));
                }
            }
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("模板名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("模板名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 64) {
            errorList.add(FYUtils.fyParams("类型最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("是否开启不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开启最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("msgContent")) && R.get("msgContent").length() > 512) {
            errorList.add(FYUtils.fyParams("站内信：站内信模板内容最大长度不能超过512字符"));
        }
        if (StringUtils.isBlank(R.get("smsOpen"))) {
            errorList.add(FYUtils.fyParams("短信：是否发送短信不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("smsOpen")) && R.get("smsOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("短信：是否发送短信最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("smsContent")) && R.get("smsContent").length() > 512) {
            errorList.add(FYUtils.fyParams("短信：短信模板内容最大长度不能超过512字符"));
        }
        if (StringUtils.isBlank(R.get("emailOpen"))) {
            errorList.add(FYUtils.fyParams("邮件：是否发送邮件不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("emailOpen")) && R.get("emailOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("邮件：是否发送邮件最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("emailTitle")) && R.get("emailTitle").length() > 255) {
            errorList.add(FYUtils.fyParams("邮件：模板标题最大长度不能超过255字符"));
        }
        return errorList;
    }

    /**
     * 验证商铺用户名是否存在
     *
     * @param oldLoginName 库中用户名
     * @param loginName    输入的用户名
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitNum")
    public String exitNum(String oldNum, String num) {
        if (StringUtils.isNotBlank(num)) {
            if (num.equals(oldNum)) {
                return "true";
            } else {
                SiteMessageTemplate siteMessageTemplate = new SiteMessageTemplate();
                siteMessageTemplate.setNum(num);
                List<SiteMessageTemplate> list = siteMessageTemplateService.selectByWhere(new Wrapper(siteMessageTemplate));
                if (list.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

}