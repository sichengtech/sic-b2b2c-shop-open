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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.entity.SiteSpecialEdition;
import com.sicheng.admin.site.service.SiteSpecialEditionService;
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
import java.util.List;

/**
 * 网站特版管理 Controller
 * 所属模块：site
 *
 * @author 张加利
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteSpecialEdition")
public class SiteSpecialEditionController extends BaseController {

    @Autowired
    private SiteSpecialEditionService siteSpecialEditionService;

    @Autowired
    private com.sicheng.admin.sys.service.MenuService MenuService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteSpecialEdition 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:view")
    @RequestMapping(value = "list")
    public String list(SiteSpecialEdition siteSpecialEdition, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteSpecialEdition> page = siteSpecialEditionService.selectByWhere(new Page<SiteSpecialEdition>(request, response), new Wrapper(siteSpecialEdition));
        model.addAttribute("page", page);
        return "admin/site/siteSpecialEditionList";
    }

    /**
     * 进入保存页面
     *
     * @param siteSpecialEdition 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:save")
    @RequestMapping(value = "save1")
    public String save1(SiteSpecialEdition siteSpecialEdition, Model model) {
        if (siteSpecialEdition == null) {
            siteSpecialEdition = new SiteSpecialEdition();
        }
        model.addAttribute("siteSpecialEdition", siteSpecialEdition);
        return "admin/site/siteSpecialEditionForm";
    }

    /**
     * 执行保存
     *
     * @param siteSpecialEdition 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:save")
    @RequestMapping(value = "save2")
    public String save2(SiteSpecialEdition siteSpecialEdition, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSpecialEdition, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败"));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteSpecialEdition, model);//回显错误提示
        }

        //业务处理
        siteSpecialEditionService.insertSelective(siteSpecialEdition);
        addMessage(redirectAttributes, FYUtils.fyParams("保存网站特版管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEdition/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteSpecialEdition 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteSpecialEdition siteSpecialEdition, Model model) {
        SiteSpecialEdition entity = null;
        if (siteSpecialEdition != null) {
            if (siteSpecialEdition.getId() != null) {
                entity = siteSpecialEditionService.selectById(siteSpecialEdition.getId());
            }
        }
        model.addAttribute("siteSpecialEdition", entity);
        return "admin/site/siteSpecialEditionForm";
    }

    /**
     * 执行编辑
     *
     * @param siteSpecialEdition 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteSpecialEdition siteSpecialEdition, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSpecialEdition, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败"));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteSpecialEdition, model);//回显错误提示
        }

        //业务处理
        siteSpecialEditionService.updateByIdSelective(siteSpecialEdition);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑网站特版管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEdition/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteSpecialEdition 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEdition:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteSpecialEdition siteSpecialEdition, RedirectAttributes redirectAttributes) {
        siteSpecialEditionService.delete(siteSpecialEdition.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除网站特版管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEdition/list.do?repage";
    }

    /**
     * 判断编号是否重复
     *
     * @param oldAdNumber
     * @param adNumber
     * @return
     */

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkSpecialNumber")
    public String checkSpecialNumber(String oldNumber, String number) {
        if (StringUtils.isNotBlank(number) && number.equals(oldNumber)) {
            return "true";
        } else if (StringUtils.isNotBlank(number)) {
            SiteSpecialEdition siteSpecialEdition = new SiteSpecialEdition();
            siteSpecialEdition.setNumber(number);
            siteSpecialEdition = siteSpecialEditionService.selectOne(new Wrapper(siteSpecialEdition));
            if (siteSpecialEdition == null) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param siteSpecialEdition 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteSpecialEdition siteSpecialEdition, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("number"))) {
            errorList.add(FYUtils.fyParams("编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("number")) && R.get("number").length() > 64) {
            errorList.add(FYUtils.fyParams("编号最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("seName")) && R.get("seName").length() > 64) {
            errorList.add(FYUtils.fyParams("特版名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("info")) && R.get("info").length() > 255) {
            errorList.add(FYUtils.fyParams("说明信息最大长度不能超过255字符"));
        }
        return errorList;
    }

}