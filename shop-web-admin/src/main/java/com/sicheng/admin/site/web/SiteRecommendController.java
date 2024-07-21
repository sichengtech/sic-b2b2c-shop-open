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

import com.sicheng.admin.site.entity.SiteRecommend;
import com.sicheng.admin.site.service.SiteRecommendService;

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
 * 推荐位 Controller
 * 所属模块：site
 *
 * @author fxx
 * @version 2017-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteRecommend")
public class SiteRecommendController extends BaseController {

    @Autowired
    private SiteRecommendService siteRecommendService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070111";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteRecommend 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommend:view")
    @RequestMapping(value = "list")
    public String list(SiteRecommend siteRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteRecommend> page = siteRecommendService.selectByWhere(new Page<SiteRecommend>(request, response), new Wrapper(siteRecommend).orderBy("sort"));
        model.addAttribute("page", page);
        return "admin/site/siteRecommendList";
    }

    /**
     * 进入保存页面
     *
     * @param siteRecommend 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommend:save")
    @RequestMapping(value = "save1")
    public String save1(SiteRecommend siteRecommend, Model model) {
        if (siteRecommend == null) {
            siteRecommend = new SiteRecommend();
        }
        model.addAttribute("siteRecommend", siteRecommend);
        return "admin/site/siteRecommendForm";
    }

    /**
     * 执行保存
     *
     * @param siteRecommend      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommend:save")
    @RequestMapping(value = "save2")
    public String save2(SiteRecommend siteRecommend, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteRecommend, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteRecommend, model);//回显错误提示
        }

        //业务处理
        siteRecommendService.insertSelective(siteRecommend);
        addMessage(redirectAttributes, FYUtils.fyParams("保存推荐位成功"));
        return "redirect:" + adminPath + "/site/siteRecommend/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteRecommend 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommend:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteRecommend siteRecommend, Model model) {
        SiteRecommend entity = null;
        if (siteRecommend != null) {
            if (siteRecommend.getId() != null) {
                entity = siteRecommendService.selectById(siteRecommend.getId());
            }
        }
        model.addAttribute("siteRecommend", entity);
        return "admin/site/siteRecommendForm";
    }

    /**
     * 执行编辑
     *
     * @param siteRecommend      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommend:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteRecommend siteRecommend, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteRecommend, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteRecommend, model);//回显错误提示
        }

        //业务处理
        siteRecommendService.updateByIdSelective(siteRecommend);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑推荐位成功"));
        return "redirect:" + adminPath + "/site/siteRecommend/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteRecommend      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommend:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteRecommend siteRecommend, RedirectAttributes redirectAttributes) {
        if (siteRecommend == null || siteRecommend.getRecommendId() == null) {
            addMessage(redirectAttributes, FYUtils.fyParams("缺少参数，删除推荐位失败"));
            return "redirect:" + adminPath + "/site/siteRecommend/list.do?repage";
        }
        siteRecommendService.deleteSiteRecommend(siteRecommend.getRecommendId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除推荐位成功"));
        return "redirect:" + adminPath + "/site/siteRecommend/list.do?repage";
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
    @RequestMapping(value = "validateNumber")
    public String validateAdNumber(String oldRecommendNumber, String recommendNumber) {
        if (StringUtils.isNotBlank(recommendNumber) && recommendNumber.equals(oldRecommendNumber)) {
            return "true";
        } else if (StringUtils.isNotBlank(recommendNumber)) {
            SiteRecommend siteRecommend = new SiteRecommend();
            siteRecommend.setRecommendNumber(recommendNumber);
            List<SiteRecommend> siteRecommendList = siteRecommendService.selectByWhere(new Wrapper(siteRecommend));
            if (siteRecommendList.isEmpty()) {
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
     * @param siteRecommend 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteRecommend siteRecommend, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("recommendNumber"))) {
            errorList.add(FYUtils.fyParams("编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("recommendNumber")) && R.get("recommendNumber").length() > 64) {
            errorList.add(FYUtils.fyParams("编号最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("推荐位名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("info")) && R.get("info").length() > 255) {
            errorList.add(FYUtils.fyParams("推荐位说明最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("是否开启不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开启最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("类型最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        return errorList;
    }

}