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
package com.sicheng.admin.store.web;

import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.store.entity.StoreLevel;
import com.sicheng.admin.store.service.StoreEnterAuthService;
import com.sicheng.admin.store.service.StoreEnterService;
import com.sicheng.admin.store.service.StoreLevelService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileSizeHelper;
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
 * 店铺等级 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeLevel")
public class StoreLevelController extends BaseController {

    @Autowired
    private StoreLevelService storeLevelService;



    @Autowired
    private StoreEnterService storeEnterService;

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050104";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeLevel 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeLevel:view")
    @RequestMapping(value = "list")
    public String list(StoreLevel storeLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeLevel);
        wrapper.orderBy("a.sort ASC");
        Page<StoreLevel> page = storeLevelService.selectByWhere(new Page<StoreLevel>(request, response), wrapper);
        //把列表中的图片空间容量转换单位为M
        List<StoreLevel> storeLevels = page.getList();
        if (!storeLevels.isEmpty()) {
            for (int i = 0; i < storeLevels.size(); i++) {
                StoreLevel storeLevel2 = storeLevels.get(i);
                String pictureSpaceM = FileSizeHelper.getHumanReadableFileSize(storeLevels.get(i).getPictureSpace());
                storeLevel2.setPictureSpaceM(pictureSpaceM);
            }
        }
        model.addAttribute("page", page);
        return "admin/store/storeLevelList";
    }

    /**
     * 进入保存页面
     *
     * @param storeLevel 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeLevel:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreLevel storeLevel, Model model) {
        if (storeLevel == null) {
            storeLevel = new StoreLevel();
        }
        model.addAttribute("storeLevel", storeLevel);
        return "admin/store/storeLevelForm";
    }

    /**
     * 执行保存
     *
     * @param storeLevel         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeLevel:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreLevel storeLevel, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeLevel, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeLevel, model);//回显错误提示
        }
        storeLevel.setIsOpen(R.get("isOpen", "0"));
        //业务处理
        storeLevelService.insertSelective(storeLevel);
        addMessage(redirectAttributes, FYUtils.fyParams("保存店铺等级成功"));
        return "redirect:" + adminPath + "/store/storeLevel/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeLevel 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeLevel:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreLevel storeLevel, Model model) {
        StoreLevel entity = null;
        if (storeLevel != null) {
            if (storeLevel.getId() != null) {
                entity = storeLevelService.selectById(storeLevel.getId());
            }
        }
        model.addAttribute("storeLevel", entity);
        return "admin/store/storeLevelForm";
    }

    /**
     * 执行编辑
     *
     * @param storeLevel         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeLevel:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreLevel storeLevel, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeLevel, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeLevel, model);//回显错误提示
        }
        storeLevel.setIsOpen(R.get("isOpen", "0"));
        //业务处理
        storeLevelService.updateByIdSelective(storeLevel);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑店铺等级成功"));
        return "redirect:" + adminPath + "/store/storeLevel/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeLevel         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeLevel:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreLevel storeLevel, RedirectAttributes redirectAttributes) {
        //查询入驻申请表里有没有店铺绑定这等级
        StoreEnter storeEnter = new StoreEnter();
        storeEnter.setLevelId(storeLevel.getLevelId());
        List<StoreEnter> storeEnters = storeEnterService.selectByWhere(new Wrapper(storeEnter));
        //查询入驻申请审核表中有没有店铺绑定这等级
        StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
        storeEnterAuth.setLevelId(storeLevel.getLevelId());
        List<StoreEnterAuth> storeEnterAuths = storeEnterAuthService.selectByWhere(new Wrapper(storeEnterAuth));
        if (!storeEnters.isEmpty() || !storeEnterAuths.isEmpty()) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除等级失败，该等级下有店铺，请移除该等级下的店铺"));
            return "redirect:" + adminPath + "/store/storeLevel/list.do?repage";
        }
        storeLevelService.deleteById(storeLevel.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺等级成功"));
        return "redirect:" + adminPath + "/store/storeLevel/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeLevel 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreLevel storeLevel, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("等级名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("等级名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("recommendProductCount"))) {
            errorList.add(FYUtils.fyParams("可推荐商品数不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("recommendProductCount")) && R.get("recommendProductCount").length() > 10) {
            errorList.add(FYUtils.fyParams("可推荐商品数最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("releaseProcuctCount"))) {
            errorList.add(FYUtils.fyParams("可发布商品数不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("releaseProcuctCount")) && R.get("releaseProcuctCount").length() > 10) {
            errorList.add(FYUtils.fyParams("可发布商品数最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("pictureSpace"))) {
            errorList.add(FYUtils.fyParams("图片空间容量/M不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("pictureSpace")) && R.get("pictureSpace").length() > 10) {
            errorList.add(FYUtils.fyParams("图片空间容量/M最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("money"))) {
            errorList.add(FYUtils.fyParams("收费标准/年不能为空"));
        }
        if (StringUtils.isBlank(R.get("applicationNote"))) {
            errorList.add(FYUtils.fyParams("申请说明不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("applicationNote")) && R.get("applicationNote").length() > 255) {
            errorList.add(FYUtils.fyParams("申请说明最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("是否开启不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开启最大长度不能超过1字符"));
        }
        return errorList;
    }

}