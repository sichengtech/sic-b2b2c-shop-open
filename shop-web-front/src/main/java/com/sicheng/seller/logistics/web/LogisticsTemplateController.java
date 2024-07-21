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
package com.sicheng.seller.logistics.web;

import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.logistics.service.LogisticsTemplateItemService;
import com.sicheng.seller.logistics.service.LogisticsTemplateService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.utils.SsoUtils;
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
 * 运费模板 Controller
 * 所属模块：logistics
 *
 * @author fxx
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${sellerPath}/logistics/logisticsTemplate")
public class LogisticsTemplateController extends BaseController {

    @Autowired
    private LogisticsTemplateService logisticsTemplateService;

    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;

    @Autowired
    private AreaService areaService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020210";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param logisticsTemplate 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:view")
    @RequestMapping(value = "list")
    public String list(LogisticsTemplate logisticsTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());
        Page<LogisticsTemplate> page = logisticsTemplateService.selectByWhere(new Page<LogisticsTemplate>(request, response), new Wrapper(logisticsTemplate));
        model.addAttribute("page", page);
        return "seller/logistics/logisticsTemplateList";
    }

    /**
     * 进入保存页面
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "save1")
    public String save1(LogisticsTemplate logisticsTemplate, Model model) {
        if (logisticsTemplate == null) {
            logisticsTemplate = new LogisticsTemplate();
        }
        model.addAttribute("logisticsTemplate", logisticsTemplate);
        model.addAttribute("itemSize", logisticsTemplate.getLogisticsTemplateItemList().size());
        model.addAttribute("areaList", areaService.selectByWhere(new Wrapper().and("del_flag=", BaseEntity.DEL_FLAG_NORMAL)));
        return "seller/logistics/logisticsTemplateForm";
    }

    /**
     * 执行保存
     *
     * @param logisticsTemplate  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "save2")
    public String save2(LogisticsTemplate logisticsTemplate, Model model, RedirectAttributes redirectAttributes) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());
        if (StringUtils.isBlank(logisticsTemplate.getValuationMethod())) {
            logisticsTemplate.setValuationMethod("0");
        }
        //表单验证
        List<String> errorList = validate(logisticsTemplate, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(logisticsTemplate, model);//回显错误提示
        }
        logisticsTemplateService.saveOrUpdateTemplate(logisticsTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("保存运费模板成功"));
        return "redirect:" + sellerPath + "/logistics/logisticsTemplate/list.htm?repage";
    }

    /**
     * 进入保存页面
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "edit1")
    public String edit1(LogisticsTemplate logisticsTemplate, Model model) {
        //入参检查
        if (logisticsTemplate == null || logisticsTemplate.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());//属主检查
        LogisticsTemplate entity = logisticsTemplateService.selectOne(new Wrapper(logisticsTemplate));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("logisticsTemplate", entity);
        model.addAttribute("itemSize", logisticsTemplate.getLogisticsTemplateItemList().size());
        model.addAttribute("logisticsTemplateList", JsonMapper.getInstance().toJson(logisticsTemplate.getLogisticsTemplateItemList()));
        model.addAttribute("areaList", areaService.selectByWhere(new Wrapper().and("del_flag=", BaseEntity.DEL_FLAG_NORMAL)));
        return "seller/logistics/logisticsTemplateForm";
    }

    /**
     * 执行保存
     *
     * @param logisticsTemplate  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "edit2")
    public String edit2(LogisticsTemplate logisticsTemplate, Model model, RedirectAttributes redirectAttributes) {
        String oldValuationMethod = R.get("oldValuationMethod");
        if (StringUtils.isBlank(logisticsTemplate.getValuationMethod())) {
            logisticsTemplate.setValuationMethod(oldValuationMethod);
        }

        //表单验证
        List<String> errorList = validate(logisticsTemplate, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(logisticsTemplate, model);//回显错误提示
        }
        //入参检查
        if (logisticsTemplate == null || logisticsTemplate.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        logisticsTemplateService.saveOrUpdateTemplate(logisticsTemplate);
        //业务处理
        addMessage(redirectAttributes, FYUtils.fyParams("保存运费模板成功"));
        return "redirect:" + sellerPath + "/logistics/logisticsTemplate/list.htm?repage";
    }

    /**
     * 复制
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "copy")
    public String copy(LogisticsTemplate logisticsTemplate, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (logisticsTemplate == null || logisticsTemplate.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());//属主检查
        LogisticsTemplate entity = logisticsTemplateService.selectOne(new Wrapper(logisticsTemplate));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        entity.setName(entity.getName() + FYUtils.fyParams("的副本"));
        logisticsTemplateService.insertSelective(entity);
        List<LogisticsTemplateItem> itemList = logisticsTemplate.getLogisticsTemplateItemList();
        if (itemList.size() > 0) {
            for (LogisticsTemplateItem item : itemList) {
                item.setLtId(entity.getLtId());
            }
        }
        logisticsTemplateItemService.insertBatch(itemList);
        addMessage(redirectAttributes, FYUtils.fyParams("复制运费模板成功"));
        return "redirect:" + sellerPath + "/logistics/logisticsTemplate/list.htm?repage";
    }

    /**
     * 删除
     *
     * @param logisticsTemplate  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "delete")
    public String delete(LogisticsTemplate logisticsTemplate, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (logisticsTemplate == null || logisticsTemplate.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("物流配送规则不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());//属主检查
        logisticsTemplateService.delete(logisticsTemplate);
        addMessage(redirectAttributes, FYUtils.fyParams("删除运费模板成功"));
        return "redirect:" + sellerPath + "/logistics/logisticsTemplate/list.htm?repage";
    }

    /**
     * 获取店铺的物流规则
     *
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:view")
    @ResponseBody
    @RequestMapping(value = "getStoreTemplate")
    public List<LogisticsTemplate> getStoreTemplate() {
        LogisticsTemplate logisticsTemplate = new LogisticsTemplate();
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        logisticsTemplate.setStoreId(userSeller.getStoreId());
        List<LogisticsTemplate> templateList = logisticsTemplateService.selectByWhere(new Wrapper(logisticsTemplate));
        return templateList;
    }

    /**
     * 表单验证
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(LogisticsTemplate logisticsTemplate, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("模板名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("模板名称最大长度不能超过64字符"));
        }
        return errorList;
    }

}