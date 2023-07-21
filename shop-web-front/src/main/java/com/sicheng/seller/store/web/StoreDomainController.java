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
package com.sicheng.seller.store.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreDomain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreDomainService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreDomainController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月21日 上午11:23:55
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeDomain")
public class StoreDomainController extends BaseController {

    @Autowired
    private StoreDomainService storeDomainService;

    @Autowired
    private SysVariableService variableService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040135";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进去店铺二级域名页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequiresPermissions("store:storeDomain:view")
    @RequestMapping(value = "save1")
    public String save1(StoreDomain sd, Model model, HttpServletRequest request) {
        //获取后台设置的域名
        SysVariable sysVariable1 = variableService.getSysVariable("site_domain");
        SysVariable sysVariable2 = variableService.getSysVariable("store_update_domain_num");
        SysVariable sysVariable3 = variableService.getSysVariable("store_domain_forbidden_words");
        if (sysVariable3 == null || StringUtils.isBlank(sysVariable3.getValueClob())) {
            sysVariable3 = new SysVariable();
            sysVariable3.setValueClob("www,seller,member,sso,admin,upload,static,shop");
        }
        String siteDomain = null;
        if (sysVariable1 != null && StringUtils.isNotBlank(sysVariable1.getValue())) {
            siteDomain = sysVariable1.getValue();
        }
        //业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreDomain storeDomain = storeDomainService.selectById(userSeller.getStoreId());
        if (StringUtils.isNotBlank(sd.getDomain())) {
            storeDomain.setDomain(sd.getDomain());
        }
        model.addAttribute("storeDomain", storeDomain);
        model.addAttribute("siteDomain", siteDomain);
        model.addAttribute("sysVariable2", sysVariable2);
        model.addAttribute("sysVariable3", sysVariable3);
        return "seller/store/storeDomainForm";
    }

    /**
     * 保存店铺二级域名
     *
     * @param storeDomain
     * @param model
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDomain:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreDomain storeDomain, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeDomain, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeDomain, model, request);//回显错误提示
        }
        //二级域名转换小写
        storeDomain.setDomain(storeDomain.getDomain().toLowerCase());
        //获取后台店铺二级域名修改次数
        SysVariable variable = variableService.selectOne(new Wrapper().and("a.name=", "store_update_domain_num"));
        Long storeDomainEditCount = 0L;
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                storeDomainEditCount = Long.parseLong(variable.getValue());
            }
        }
        if (storeDomainEditCount == 0) {
            //店铺域名修改次数(没有给默认值15)
            storeDomainEditCount = 15L;
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreDomain sd = storeDomainService.selectById(userSeller.getStoreId());
        String today = DateUtils.getYear() + "-" + DateUtils.getMonth();
        //判断当前店铺的修改次数是否到达当前月份的最大上限
        if (sd != null) {
            int modifyCount = sd.getModifyCount();//店铺域名修改次数
            if (StringUtils.isBlank(sd.getModifyDate())) {
                //当前年月和修改年月不一致
                storeDomain.setModifyCount(1);
                storeDomain.setStoreId(userSeller.getStoreId());
                storeDomain.setModifyDate(today);
                storeDomainService.updateByIdSelective(storeDomain);
                addMessage(redirectAttributes, FYUtils.fyParams("修改店铺二级域名成功"));
            } else {
                //判断二级域名变更年月字段是否为空
                if (today.equals(sd.getModifyDate())) {
                    //当前年月和修改年月一致
                    if (modifyCount < storeDomainEditCount) {
                        //当前的修改次数少于店铺修改次数
                        modifyCount += 1;
                        storeDomain.setModifyCount(modifyCount);
                        storeDomain.setStoreId(userSeller.getStoreId());
                        storeDomain.setModifyDate(today);
                        storeDomainService.updateByIdSelective(storeDomain);
                        addMessage(redirectAttributes, FYUtils.fyParams("修改店铺二级域名成功"));
                    } else {
                        //当前的修改次数大于等于店铺修改次数
                        addMessage(redirectAttributes, FYUtils.fyParams("修改失败,店铺在当前月份修改域名上限"));
                    }
                } else {
                    //当前年月和修改年月不一致
                    storeDomain.setModifyCount(1);
                    storeDomain.setStoreId(userSeller.getStoreId());
                    storeDomain.setModifyDate(today);
                    storeDomainService.updateByIdSelective(storeDomain);
                    addMessage(redirectAttributes, FYUtils.fyParams("修改店铺二级域名成功"));
                }
            }
        }
        return "redirect:" + sellerPath + "/store/storeDomain/save1.htm?repage";
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
            domain = domain.toLowerCase();
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
        if (StringUtils.isNotBlank(R.get("domain")) && R.get("domain").length() < 4) {
            errorList.add(FYUtils.fyParams("二级域名最小长度不能少于4字符"));
        }
        if (StringUtils.isNotBlank(R.get("domain")) && StringUtils.isNotBlank(R.get("storeId"))) {
            StoreDomain sd = new StoreDomain();
            sd.setDomain(R.get("domain"));
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(sd);
            wrapper.and("a.store_id!=", R.get("storeId"));
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
            SysVariable sysVariable = variableService.getSysVariable("store_domain_forbidden_words");
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
