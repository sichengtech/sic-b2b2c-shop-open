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

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.service.SiteInfoService;

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
 * 管理网站的设置 Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteInfo")
public class SiteInfoController extends BaseController {

    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070106";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteInfo 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteInfo:view")
    @RequestMapping(value = "list")
    public String list(SiteInfo siteInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteInfo> page = siteInfoService.selectByWhere(new Page<SiteInfo>(request, response), new Wrapper(siteInfo));
        model.addAttribute("page", page);
        return "admin/site/siteInfoList";
    }

    /**
     * 进入保存页面
     *
     * @param siteInfo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteInfo:save")
    @RequestMapping(value = "save1")
    public String save1(SiteInfo siteInfo, Model model) {
        if (siteInfo == null) {
            siteInfo = new SiteInfo();
        }
        model.addAttribute("siteInfo", siteInfo);
        return "admin/site/siteInfoForm";
    }

    /**
     * 执行保存
     *
     * @param siteInfo           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteInfo:save")
    @RequestMapping(value = "save2")
    public String save2(SiteInfo siteInfo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteInfo, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteInfo, model);//回显错误提示
        }
        String detail = R.get("code");
        if (StringUtils.isNoneBlank(detail)) {
            String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
            siteInfo.setCode(html_unsafe);
        }
        //业务处理
        siteInfoService.insertSelective(siteInfo);
        addMessage(redirectAttributes, FYUtils.fyParams("保存站点设置成功"));
        return "redirect:" + adminPath + "/site/siteInfo/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteInfo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteInfo:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteInfo siteInfo, Model model) {
        SiteInfo entity = null;
        //按ID查
        if (siteInfo != null) {
            if (siteInfo.getId() != null) {
                entity = siteInfoService.selectById(siteInfo.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            entity = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        }
        //初始化一条数据
        if (entity == null) {
            //向表中插入一条记录
            entity = new SiteInfo();
            entity.setName("演示站");//网站名称
            entity.setSiteLogo("/shop_init/logo.png");//网站LOGO
            entity.setSellerLogo("/shop_init/logo.png");//商家中心LOGO(不需要了，改使用大文字标题了)
            siteInfoService.insertSelective(entity);
        }
        model.addAttribute("siteInfo", entity);
        return "admin/site/siteInfoForm";
    }

    /**
     * 执行编辑
     *
     * @param siteInfo           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteInfo:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteInfo siteInfo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteInfo, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteInfo, model);//回显错误提示
        }
        String detail = R.get("code");
        if (StringUtils.isNoneBlank(detail)) {
            String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
            siteInfo.setCode(html_unsafe);
        }
        //业务处理
        siteInfoService.updateByIdSelective(siteInfo);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑站点设置成功"));
        return "redirect:" + adminPath + "/site/siteInfo/edit1.do";
    }

    /**
     * 删除
     *
     * @param siteInfo           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteInfo:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteInfo siteInfo, RedirectAttributes redirectAttributes) {
        siteInfoService.deleteById(siteInfo.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除站点设置成功"));
        return "redirect:" + adminPath + "/site/siteInfo/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteInfo 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteInfo siteInfo, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("网站名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 128) {
            errorList.add(FYUtils.fyParams("网站名称最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("icp")) && R.get("icp").length() > 128) {
            errorList.add(FYUtils.fyParams("ICP备案号最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("code")) && R.get("code").length() > 1024) {
            errorList.add(FYUtils.fyParams("第三方流量统计代码最大长度不能超过1024字符"));
        }
        if (StringUtils.isBlank(R.get("siteLogo"))) {
            errorList.add(FYUtils.fyParams("网站LOGO不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("siteLogo")) && R.get("siteLogo").length() > 128) {
            errorList.add(FYUtils.fyParams("网站LOGO最大长度不能超过128字符"));
        }
//        if (StringUtils.isBlank(R.get("sellerLogo"))) {
//            errorList.add(FYUtils.fyParams("商家中心LOGO不能为空"));
//        }
//        if (StringUtils.isNotBlank(R.get("sellerLogo")) && R.get("sellerLogo").length() > 128) {
//            errorList.add(FYUtils.fyParams("商家中心LOGO最大长度不能超过128字符"));
//        }
        if (StringUtils.isNotBlank(R.get("email")) && R.get("email").length() > 128) {
            errorList.add(FYUtils.fyParams("站点联系邮箱最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("telephone")) && R.get("telephone").length() > 128) {
            errorList.add(FYUtils.fyParams("站点联系电话最大长度不能超过128字符"));
        }
        return errorList;
    }

}