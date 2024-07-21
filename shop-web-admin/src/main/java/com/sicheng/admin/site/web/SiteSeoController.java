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

import com.sicheng.admin.site.entity.SiteSeo;
import com.sicheng.admin.site.service.SiteSeoService;

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
 * 管理网站的seo Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteSeo")
public class SiteSeoController extends BaseController {

    @Autowired
    private SiteSeoService siteSeoService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070107";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteSeo  实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSeo:view")
    @RequestMapping(value = "list")
    public String list(SiteSeo siteSeo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteSeo> page = siteSeoService.selectByWhere(new Page<SiteSeo>(request, response), new Wrapper(siteSeo));
        model.addAttribute("page", page);
        return "admin/site/siteSeoList";
    }

    /**
     * 进入保存页面
     *
     * @param siteSeo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSeo:edit")
    @RequestMapping(value = "save1")
    public String save1(SiteSeo siteSeo, Model model) {
        if (siteSeo == null) {
            siteSeo = new SiteSeo();
        }
        model.addAttribute("siteSeo", siteSeo);
        return "admin/site/siteSeoForm";
    }

    /**
     * 执行保存
     *
     * @param siteSeo            实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSeo:edit")
    @RequestMapping(value = "save2")
    public String save2(SiteSeo siteSeo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldType = R.get("oldType");//已添加的类型
        List<String> errorList = validate(siteSeo, oldType, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteSeo, model);//回显错误提示
        }

        //业务处理
        siteSeoService.insertSelective(siteSeo);
        addMessage(redirectAttributes, FYUtils.fyParams("保存seo设置成功"));
        return "redirect:" + adminPath + "/site/siteSeo/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteSeo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSeo:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteSeo siteSeo, Model model) {
        SiteSeo entity = null;
        if (siteSeo != null) {
            if (siteSeo.getId() != null) {
                entity = siteSeoService.selectById(siteSeo.getId());
            }
        }
        if (entity != null) {
            model.addAttribute("type", entity.getType());
            model.addAttribute("siteSeo", entity);
        }
        return "admin/site/siteSeoForm";
    }

    /**
     * 执行编辑
     *
     * @param siteSeo            实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSeo:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteSeo siteSeo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldType = R.get("oldType");//已添加的类型
        List<String> errorList = validate(siteSeo, oldType, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteSeo, model);//回显错误提示
        }

        //业务处理
        siteSeoService.updateByIdSelective(siteSeo);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑seo设置成功"));
        return "redirect:" + adminPath + "/site/siteSeo/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteSeo            实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSeo:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteSeo siteSeo, RedirectAttributes redirectAttributes) {
        siteSeoService.deleteById(siteSeo.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除seo设置成功"));
        return "redirect:" + adminPath + "/site/siteSeo/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteSeo 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteSeo siteSeo, String oldType, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("业务类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type"))) {
            if (R.get("type").length() > 64) {
                errorList.add(FYUtils.fyParams("业务类型最大长度不能超过64字符"));
            }
            if (!R.get("type").equals(oldType)) {
                List<SiteSeo> list = siteSeoService.selectByWhere(new Wrapper().and("a.type=", R.get("type")));
                if (list.size() > 0) {
                    errorList.add(FYUtils.fyParams("此业务类型已存在"));
                }
            }

        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 255) {
            errorList.add(FYUtils.fyParams("Title最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("keywords")) && R.get("keywords").length() > 255) {
            errorList.add(FYUtils.fyParams("Keywords最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("description")) && R.get("description").length() > 255) {
            errorList.add(FYUtils.fyParams("Description最大长度不能超过255字符"));
        }
        return errorList;
    }

    /**
     * 验证seo的业务类型是否存在
     *
     * @param oldType 库中业务类型
     * @param type    选择的业务类型
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitType")
    public String exitType(String oldType, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (type.equals(oldType)) {
                return "true";
            } else {
                SiteSeo siteSeo = new SiteSeo();
                siteSeo.setType(type);
                List<SiteSeo> list = siteSeoService.selectByWhere(new Wrapper(siteSeo));
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