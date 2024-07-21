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
package com.sicheng.admin.store.web;

import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreDomain;
import com.sicheng.admin.store.service.StoreDomainService;
import com.sicheng.admin.store.service.StoreService;
import com.sicheng.admin.sys.entity.SysVariable;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺二级域名 Controller
 * 所属模块：store
 *
 * @author cl
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeDomain")
public class StoreDomainController extends BaseController {

    @Autowired
    private StoreDomainService storeDomainService;

    @Autowired
    private StoreService storeService;
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
        String menu3 = "050107";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeDomain 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeDomain:view")
    @RequestMapping(value = "list")
    public String list(StoreDomain storeDomain, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(storeDomain.getDomain())) {
            //二级域名转换小写
            storeDomain.setDomain(storeDomain.getDomain().toLowerCase());
        }
        Page<StoreDomain> page = storeDomainService.selectByWhere(new Page<StoreDomain>(request, response), new Wrapper(storeDomain));
        //判断店铺的修改域名次数，如果是超过一个月修改次数变为0
        List<StoreDomain> storeDomains = page.getList();
        StoreDomain.fillStore(storeDomains);
        model.addAttribute("page", page);
        return "admin/store/storeDomainList";
    }

    /**
     * 进入编辑页面
     *
     * @param storeDomain 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeDomain:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreDomain storeDomain, Model model) {
        StoreDomain entity = null;
        if (storeDomain != null) {
            if (storeDomain.getId() != null) {
                entity = storeDomainService.selectById(storeDomain.getId());
            }
        }
        Store store = storeService.selectById(storeDomain.getStoreId());
        SysVariable variable = sysVariableService.getSysVariable("site_domain");
        SysVariable sysVariable3 = sysVariableService.getSysVariable("store_domain_forbidden_words");
        if (sysVariable3 == null || StringUtils.isBlank(sysVariable3.getValueClob())) {
            sysVariable3 = new SysVariable();
            sysVariable3.setValueClob("www,seller,member,sso,admin,upload,static,shop");
        }
        model.addAttribute("storeName", store.getName());
        model.addAttribute("storeDomain", entity);
        model.addAttribute("domain", variable.getValue());
        model.addAttribute("sysVariable3", sysVariable3);
        return "admin/store/storeDomainForm";
    }

    /**
     * 执行编辑
     *
     * @param storeDomain        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDomain:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreDomain storeDomain, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeDomain, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeDomain, model);//回显错误提示
        }
        //二级域名转换小写
        storeDomain.setDomain(storeDomain.getDomain().toLowerCase());
        //业务处理
        storeDomainService.updateByIdSelective(storeDomain);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑店铺二级域名成功"));
        return "redirect:" + adminPath + "/store/storeDomain/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeDomain        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDomain:drop")
    @RequestMapping(value = "delete")
    public String delete(StoreDomain storeDomain, RedirectAttributes redirectAttributes) {
        storeDomainService.deleteById(storeDomain.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺二级域名成功"));
        return "redirect:" + adminPath + "/store/storeDomain/list.do?repage";
    }

    /**
     * 验证二级域名是否重复
     *
     * @param oldDomain
     * @param domain
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateDomain")
    public String validatedomain(String oldDomain, String domain) {
        if (StringUtils.isNotBlank(domain) && domain.equals(oldDomain)) {
            return "true";
        } else if (StringUtils.isNotBlank(domain)) {
            StoreDomain storeDomain = new StoreDomain();
            storeDomain.setDomain(domain);
            List<StoreDomain> storeDomains = storeDomainService.selectByWhere(new Wrapper(storeDomain));
            if (storeDomains.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 表单验证(编辑)
     *
     * @param storeDomain 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreDomain storeDomain, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("domain"))) {
            errorList.add(FYUtils.fyParams("二级域名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("domain")) && R.get("domain").length() > 64) {
            errorList.add(FYUtils.fyParams("二级域名最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("domain")) && R.get("domain").length() <= 4) {
            errorList.add(FYUtils.fyParams("二级域名最小长度不能少于4字符"));
        }
        if (StringUtils.isNotBlank(R.get("domain")) && StringUtils.isNotBlank(R.get("id"))) {
            StoreDomain sd = new StoreDomain();
            sd.setDomain(R.get("domain"));
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(sd);
            wrapper.and("a.store_id!=", R.get("id"));
            List<StoreDomain> storeDomainList = storeDomainService.selectByWhere(wrapper);
            if (!storeDomainList.isEmpty()) {
                errorList.add(FYUtils.fyParams("二级域名已存在"));
            }
        }
        if (StringUtils.isNotBlank(R.get("domain"))) {
            if (R.get("domain").length() >= 4) {
                if ("shop".equals(R.get("domain").substring(0, 4))) {
                    errorList.add(FYUtils.fyParams("二级域名不能以shop开头"));
                }
            }
            SysVariable sysVariable = sysVariableService.getSysVariable("store_domain_forbidden_words");
            if (sysVariable == null || StringUtils.isBlank(sysVariable.getValueClob())) {
                sysVariable = new SysVariable();
                sysVariable.setValueClob("www,seller,member,sso,admin,upload,static,shop");
            }
            String[] domainForbiddenWords = sysVariable.getValueClob().split(",");
            for (int i = 0; i < domainForbiddenWords.length; i++) {
                if (R.get("domain").equals(domainForbiddenWords[i])) {
                    errorList.add(FYUtils.fyParams("二级域名不能是") + domainForbiddenWords[i]);
                }
            }
        }
        return errorList;
    }

}