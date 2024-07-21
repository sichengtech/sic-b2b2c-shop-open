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

import com.sicheng.admin.site.entity.SiteSpecialEdition;
import com.sicheng.admin.site.entity.SiteSpecialEditionDetail;
import com.sicheng.admin.site.service.SiteSpecialEditionDetailService;
import com.sicheng.admin.site.service.SiteSpecialEditionService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 特版明细福管理 Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteSpecialEditionDetail")
public class SiteSpecialEditionDetailController extends BaseController {

    @Autowired
    private SiteSpecialEditionDetailService siteSpecialEditionDetailService;

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
     * @param siteSpecialEditionDetail 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:view")
    @RequestMapping(value = "list")
    public String list(SiteSpecialEditionDetail siteSpecialEditionDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        //特版信息
        SiteSpecialEdition siteSpecialEdition = siteSpecialEditionService.selectById(siteSpecialEditionDetail.getSeId());
        //特版明细信息
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(siteSpecialEditionDetail);
        wrapper.orderBy("a.sort");
        Page<SiteSpecialEditionDetail> page = siteSpecialEditionDetailService.selectByWhere(new Page<SiteSpecialEditionDetail>(request, response), wrapper);
        model.addAttribute("page", page);
        model.addAttribute("siteSpecialEdition", siteSpecialEdition);
        return "admin/site/siteSpecialEditionDetailList";
    }

    /**
     * 进入保存页面
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:save")
    @RequestMapping(value = "save1")
    public String save1(SiteSpecialEditionDetail siteSpecialEditionDetail, Model model, RedirectAttributes redirectAttributes) {
        if (siteSpecialEditionDetail == null) {
            siteSpecialEditionDetail = new SiteSpecialEditionDetail();
        }
        if (siteSpecialEditionDetail.getSeId() == null) {
            addMessage(redirectAttributes, FYUtils.fyParams(""));
            return "redirect:" + adminPath + "/site/siteSpecialEditionDetail/list.do?repage";
        }
        model.addAttribute("siteSpecialEditionDetail", siteSpecialEditionDetail);
        return "admin/site/siteSpecialEditionDetailForm";
    }

    /**
     * 执行保存
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:save")
    @RequestMapping(value = "save2")
    public String save2(SiteSpecialEditionDetail siteSpecialEditionDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSpecialEditionDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败"));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteSpecialEditionDetail, model, redirectAttributes);//回显错误提示
        }

        //业务处理
        String detail = R.get("content");
        String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        siteSpecialEditionDetail.setContent(html_unsafe);
        siteSpecialEditionDetailService.insertSelective(siteSpecialEditionDetail);
        addMessage(redirectAttributes, FYUtils.fyParams("保存特版明细福管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEditionDetail/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteSpecialEditionDetail siteSpecialEditionDetail, Model model) {
        SiteSpecialEditionDetail entity = null;
        if (siteSpecialEditionDetail != null) {
            if (siteSpecialEditionDetail.getId() != null) {
                entity = siteSpecialEditionDetailService.selectById(siteSpecialEditionDetail.getId());
            }
        }
        model.addAttribute("siteSpecialEditionDetail", entity);
        return "admin/site/siteSpecialEditionDetailForm";
    }

    /**
     * 执行编辑
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteSpecialEditionDetail siteSpecialEditionDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteSpecialEditionDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败"));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteSpecialEditionDetail, model);//回显错误提示
        }

        //业务处理
        String detail = R.get("content");
        String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        siteSpecialEditionDetail.setContent(html_unsafe);
        siteSpecialEditionDetailService.updateByIdSelective(siteSpecialEditionDetail);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑特版明细福管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEditionDetail/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteSpecialEditionDetail:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteSpecialEditionDetail siteSpecialEditionDetail, RedirectAttributes redirectAttributes) {
        siteSpecialEditionDetailService.deleteById(siteSpecialEditionDetail.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除特版明细福管理成功"));
        return "redirect:" + adminPath + "/site/siteSpecialEditionDetail/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteSpecialEditionDetail 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteSpecialEditionDetail siteSpecialEditionDetail, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("特版详情内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 9) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过9字符"));
        }
        return errorList;
    }

}