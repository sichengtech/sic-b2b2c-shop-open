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

import com.sicheng.admin.site.entity.SiteFileManage;
import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.site.service.SiteFileManageService;
import com.sicheng.admin.site.service.SiteUploadService;

import com.sicheng.admin.sys.service.SysVariableService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理器 Controller
 * 所属模块：site
 *
 * @author fxx
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteFileManage")
public class SiteFileManageController extends BaseController {

    @Autowired
    private SiteFileManageService siteFileManageService;



    @Autowired
    private SiteUploadService siteUploadService;

    @Autowired
    private SysVariableService sysVariableService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070114";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteFileManage 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteFileManage:view")
    @RequestMapping(value = "list")
    public String list(SiteFileManage siteFileManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteFileManage> page = siteFileManageService.selectByWhere(new Page<SiteFileManage>(request, response), new Wrapper(siteFileManage));
        for (int i = 0; i < page.getList().size(); i++) {
            SiteFileManage file = page.getList().get(i);
            if (file == null || StringUtils.isBlank(file.getPath())) {
                continue;
            }
            String path = file.getPath();
            int index = path.lastIndexOf(".");
            String suffix = path.substring(index + 1, path.length());
            file.setSuffix(suffix);
        }
        model.addAttribute("page", page);
        if (sysVariableService.selectOne(new Wrapper().and("name =", "site_domain")) != null) {
            model.addAttribute("siteDomain", "http://" + sysVariableService.selectOne(new Wrapper().and("name =", "site_domain")).getValue());
        }
        System.out.println(R.getRequest().getContextPath());
        return "admin/site/siteFileManageList";
    }

    /**
     * 进入保存页面
     *
     * @param siteFileManage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteFileManage:save")
    @RequestMapping(value = "save1")
    public String save1(SiteFileManage siteFileManage, Model model) {
        if (siteFileManage == null) {
            siteFileManage = new SiteFileManage();
        }
        SiteUpload siteUpload = siteUploadService.selectOne(new Wrapper());
        model.addAttribute("siteUpload", siteUpload);
        model.addAttribute("siteFileManage", siteFileManage);
        return "admin/site/siteFileManageForm";
    }

    /**
     * 执行保存
     *
     * @param siteFileManage     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteFileManage:save")
    @RequestMapping(value = "save2")
    public String save2(SiteFileManage siteFileManage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteFileManage, model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteFileManage, model);//回显错误提示
        }
        String[] path = R.getArray("path");
        String[] name = R.getArray("name");
        if (path.length == 0 || name.length == 0) {
            addMessage(model, FYUtils.fyParams("请上传文件"));
            return save1(siteFileManage, model);//回显错误提示
        }
        if (path.length != name.length) {
            addMessage(model, FYUtils.fyParams("缺少参数"));
            return save1(siteFileManage, model);//回显错误提示
        }
        List<SiteFileManage> siteFileManageList = new ArrayList<>();
        for (int i = 0; i < path.length; i++) {
            if (StringUtils.isBlank(path[i]) || StringUtils.isBlank(name[i])) {
                continue;
            }
            SiteFileManage file = new SiteFileManage();
            file.setCategory(siteFileManage.getCategory());
            file.setPath(path[i]);
            file.setName(name[i]);
            file.setRemarks(siteFileManage.getRemarks());
            siteFileManageList.add(file);
        }
        //业务处理
        siteFileManageService.insertBatch(siteFileManageList);
        addMessage(redirectAttributes, FYUtils.fyParams("保存文件成功"));
        return "redirect:" + adminPath + "/site/siteFileManage/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteFileManage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteFileManage:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteFileManage siteFileManage, Model model) {
        SiteFileManage entity = null;
        if (siteFileManage != null) {
            if (siteFileManage.getId() != null) {
                entity = siteFileManageService.selectById(siteFileManage.getId());
            }
        }
        SiteUpload siteUpload = siteUploadService.selectOne(new Wrapper());
        model.addAttribute("siteUpload", siteUpload);
        model.addAttribute("siteFileManage", entity);
        return "admin/site/siteFileManageForm";
    }

    /**
     * 执行编辑
     *
     * @param siteFileManage     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteFileManage:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteFileManage siteFileManage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteFileManage, model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteFileManage, model);//回显错误提示
        }
        String[] path = R.getArray("path");
        String[] name = R.getArray("name");
        if (path.length == 0 || name.length == 0) {
            addMessage(model, FYUtils.fyParams("请上传文件"));
            return save1(siteFileManage, model);//回显错误提示
        }
        if (path.length != name.length) {
            addMessage(model, FYUtils.fyParams("缺少参数"));
            return save1(siteFileManage, model);//回显错误提示
        }
        //业务处理
        siteFileManageService.editFile(siteFileManage, name, path);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑文件成功"));
        return "redirect:" + adminPath + "/site/siteFileManage/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteFileManage     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteFileManage:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteFileManage siteFileManage, RedirectAttributes redirectAttributes) {
        siteFileManageService.deleteById(siteFileManage.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除文件成功"));
        return "redirect:" + adminPath + "/site/siteFileManage/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteFileManage 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteFileManage siteFileManage, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("category"))) {
            errorList.add(FYUtils.fyParams("文件分类不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("category")) && R.get("category").length() > 64) {
            errorList.add(FYUtils.fyParams("文件分类最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("文件名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("文件名最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("path"))) {
            errorList.add(FYUtils.fyParams("文件地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("path")) && R.get("path").length() > 128) {
            errorList.add(FYUtils.fyParams("文件地址最大长度不能超过128字符"));
        }
        return errorList;
    }

}