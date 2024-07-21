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

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.site.service.SiteRegisterService;

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
 * 注册设置 Controller
 * 所属模块：site
 *
 * @author cl
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteRegister")
public class SiteRegisterController extends BaseController {

    @Autowired
    private SiteRegisterService siteRegisterService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070108";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteRegister 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRegister:view")
    @RequestMapping(value = "list")
    public String list(SiteRegister siteRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteRegister> page = siteRegisterService.selectByWhere(new Page<SiteRegister>(request, response), new Wrapper(siteRegister));
        model.addAttribute("page", page);
        return "admin/site/siteRegisterList";
    }

    /**
     * 进入保存页面
     *
     * @param siteRegister 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRegister:save")
    @RequestMapping(value = "save1")
    public String save1(SiteRegister siteRegister, Model model) {
        if (siteRegister == null) {
            siteRegister = new SiteRegister();
        }
        model.addAttribute("siteRegister", siteRegister);
        return "admin/site/siteRegisterForm";
    }

    /**
     * 执行保存
     *
     * @param siteRegister       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRegister:save")
    @RequestMapping(value = "save2")
    public String save2(SiteRegister siteRegister, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteRegister, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteRegister, model);//回显错误提示
        }

        //如果注册协议的html不为空则转译
        if (StringUtils.isNotBlank(siteRegister.getAgreement())) {
            siteRegister.setAgreement(StringEscapeUtils.unescapeHtml4(siteRegister.getAgreement()));
        }

        //业务处理
        siteRegisterService.insertSelective(siteRegister);
        addMessage(redirectAttributes, FYUtils.fyParams("保存注册设置成功"));
        return "redirect:" + adminPath + "/site/siteRegister/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteRegister 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRegister:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteRegister siteRegister, Model model) {
        SiteRegister entity = null;
        if (siteRegister != null) {
            if (siteRegister.getId() != null) {
                entity = siteRegisterService.selectById(siteRegister.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SiteRegister> page = new Page<SiteRegister>();
            page.setOrderBy("id asc");//按ID排序
            SiteRegister register = new SiteRegister();
            page = siteRegisterService.selectByWhere(page, new Wrapper(register));
            if (page.getList().size() > 0) {
                entity = page.getList().get(0);//取ID最小的一个
            }
        }
        //初始化一条数据
        if (entity == null) {
            //向表中插入一条记录
            entity = new SiteRegister();
            entity.setIsRegister("1");////是否开放注册:1是、0否
            entity.setUsernameMax(20);//用户名最大长度
            entity.setUsernameMin(6);//用户名最小长度
            entity.setPwdMax(20);//密码最大长度
            entity.setPwdMin(6);//密码最小长度
            siteRegisterService.insertSelective(entity);
        }
        model.addAttribute("siteRegister", entity);
        return "admin/site/siteRegisterForm";
    }

    /**
     * 执行编辑
     *
     * @param siteRegister       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRegister:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteRegister siteRegister, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteRegister, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteRegister, model);//回显错误提示
        }

        //如果注册协议的html不为空则转译
        if (StringUtils.isNotBlank(siteRegister.getAgreement())) {
            siteRegister.setAgreement(StringEscapeUtils.unescapeHtml4(siteRegister.getAgreement()));
        }

        //业务处理
        siteRegisterService.updateByIdSelective(siteRegister);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑注册设置成功"));
        return "redirect:" + adminPath + "/site/siteRegister/edit1.do";
    }

    /**
     * 删除
     *
     * @param siteRegister       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRegister:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteRegister siteRegister, RedirectAttributes redirectAttributes) {
        siteRegisterService.deleteById(siteRegister.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除注册设置成功"));
        return "redirect:" + adminPath + "/site/siteRegister/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteRegister 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteRegister siteRegister, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("isRegister"))) {
            errorList.add(FYUtils.fyParams("是否开放注册 不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isRegister")) && R.get("isRegister").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开放注册最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("usernameMax"))) {
            errorList.add(FYUtils.fyParams("用户名最大长度最大长度不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("usernameMax")) && R.get("usernameMax").length() > 10) {
            errorList.add(FYUtils.fyParams("用户名最大长度最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("usernameMin"))) {
            errorList.add(FYUtils.fyParams("用户名最小长度最大长度不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("usernameMin")) && R.get("usernameMin").length() > 10) {
            errorList.add(FYUtils.fyParams("用户名最小长度最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("pwdMax"))) {
            errorList.add(FYUtils.fyParams("密码最大长度最大长度不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("pwdMax")) && R.get("pwdMax").length() > 10) {
            errorList.add(FYUtils.fyParams("密码最大长度最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("pwdMin"))) {
            errorList.add(FYUtils.fyParams("密码最小长度最大长度不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("pwdMin")) && R.get("pwdMin").length() > 10) {
            errorList.add(FYUtils.fyParams("密码最小长度最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("disableUsername")) && R.get("disableUsername").length() > 1024) {
            errorList.add(FYUtils.fyParams("禁用用户名最大长度不能超过1024字符"));
        }
		/*if(StringUtils.isNotBlank(R.get("agreement")) && R.get("agreement").length() > 1024){
			errorList.add(FYUtils.fyParams("注册协议最大长度不能超过1024字符"));
		}*/
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
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段6最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段7最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段8最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段9最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段10最大长度不能超过64字符"));
        }
        return errorList;
    }

}