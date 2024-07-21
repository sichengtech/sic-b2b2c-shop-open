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

import com.sicheng.admin.site.entity.SiteSwitch;
import com.sicheng.admin.site.service.SiteSwitchService;

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
 * 管理网站的开关 Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteSwitch")
public class SiteSwitchController extends BaseController {

    @Autowired
    private SiteSwitchService siteSwitchService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070109";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteSwitch 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSwitch:view")
    @RequestMapping(value = "list")
    public String list(SiteSwitch siteSwitch, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteSwitch> page = siteSwitchService.selectByWhere(new Page<SiteSwitch>(request, response), new Wrapper(siteSwitch));
        model.addAttribute("page", page);
        return "admin/site/siteSwitchList";
    }

    /**
     * 进入保存页面
     *
     * @param siteSwitch 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSwitch:save")
    @RequestMapping(value = "save1")
    public String save1(SiteSwitch siteSwitch, Model model) {
        if (siteSwitch == null) {
            siteSwitch = new SiteSwitch();
        }
        model.addAttribute("siteSwitch", siteSwitch);
        return "admin/site/siteSwitchForm";
    }

    /**
     * 执行保存
     *
     * @param siteSwitch         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSwitch:save")
    @RequestMapping(value = "save2")
    public String save2(SiteSwitch siteSwitch, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSwitch, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteSwitch, model);//回显错误提示
        }

        //业务处理
        siteSwitchService.insertSelective(siteSwitch);
        addMessage(redirectAttributes, FYUtils.fyParams("保存站点开关成功"));
        return "redirect:" + adminPath + "/site/siteSwitch/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteSwitch 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSwitch:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteSwitch siteSwitch, Model model) {
        SiteSwitch entity = null;
        if (siteSwitch != null) {
            if (siteSwitch.getId() != null) {
                entity = siteSwitchService.selectById(siteSwitch.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SiteSwitch> page = new Page<SiteSwitch>();
            page.setOrderBy("id asc");//按ID排序
            SiteSwitch sSwitch = new SiteSwitch();
            page = siteSwitchService.selectByWhere(page, new Wrapper(sSwitch));
            if (page.getList().size() > 0) {
                entity = page.getList().get(0);//取ID最小的一个
            }
        }
        //初始化一条数据
        if (entity == null) {
            //向表中插入一条记录
            entity = new SiteSwitch();
            entity.setIsOpen("1");//是否开放网站
            entity.setMsg("<dl><dt>" + FYUtils.fyParams("网站维护中，暂时无法访问！") + "</dt><dd>" + FYUtils.fyParams("本网站正在进行系统维护和技术升级，网站暂时无法访问，敬请谅解！") + "</dd></dl>");//网站关闭消息
            siteSwitchService.insertSelective(entity);
        }
        model.addAttribute("siteSwitch", entity);
        return "admin/site/siteSwitchForm";
    }

    /**
     * 执行编辑
     *
     * @param siteSwitch         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSwitch:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteSwitch siteSwitch, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSwitch, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteSwitch, model);//回显错误提示
        }

        //业务处理
        siteSwitchService.updateByIdSelective(siteSwitch);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑站点开关成功"));
        return "redirect:" + adminPath + "/site/siteSwitch/edit1.do";
    }

    /**
     * 删除
     *
     * @param siteSwitch         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSwitch:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteSwitch siteSwitch, RedirectAttributes redirectAttributes) {
        siteSwitchService.deleteById(siteSwitch.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除站点开关成功"));
        return "redirect:" + adminPath + "/site/siteSwitch/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteSwitch 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteSwitch siteSwitch, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("是否开放网站不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开放网站最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("msg"))) {
            errorList.add(FYUtils.fyParams("网站关闭消息不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("msg")) && R.get("msg").length() > 1024) {
            errorList.add(FYUtils.fyParams("网站关闭消息最大长度不能超过1024字符"));
        }
        return errorList;
    }

}