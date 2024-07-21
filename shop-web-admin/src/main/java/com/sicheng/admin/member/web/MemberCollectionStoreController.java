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
package com.sicheng.admin.member.web;

import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.member.service.MemberCollectionStoreService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
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
 * 收藏店铺 Controller
 * 所属模块：member
 *
 * @author cl
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberCollectionStore")
public class MemberCollectionStoreController extends BaseController {

    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param memberCollectionStore 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:view")
    @RequestMapping(value = "list")
    public String list(MemberCollectionStore memberCollectionStore, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MemberCollectionStore> page = memberCollectionStoreService.selectByWhere(new Page<MemberCollectionStore>(request, response), new Wrapper(memberCollectionStore));
        model.addAttribute("page", page);
        return "admin/member/memberCollectionStoreList";
    }

    /**
     * 进入保存页面
     *
     * @param memberCollectionStore 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:edit")
    @RequestMapping(value = "save1")
    public String save1(MemberCollectionStore memberCollectionStore, Model model) {
        if (memberCollectionStore == null) {
            memberCollectionStore = new MemberCollectionStore();
        }
        model.addAttribute("memberCollectionStore", memberCollectionStore);
        return "admin/member/memberCollectionStoreForm";
    }

    /**
     * 执行保存
     *
     * @param memberCollectionStore 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:edit")
    @RequestMapping(value = "save2")
    public String save2(MemberCollectionStore memberCollectionStore, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(memberCollectionStore, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(memberCollectionStore, model);//回显错误提示
        }

        //业务处理
        memberCollectionStoreService.insertSelective(memberCollectionStore);
        addMessage(redirectAttributes, "保存收藏店铺成功");
        return "redirect:" + adminPath + "/member/memberCollectionStore/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param memberCollectionStore 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:edit")
    @RequestMapping(value = "edit1")
    public String edit1(MemberCollectionStore memberCollectionStore, Model model) {
        MemberCollectionStore entity = null;
        if (memberCollectionStore != null) {
            if (memberCollectionStore.getId() != null) {
                entity = memberCollectionStoreService.selectById(memberCollectionStore.getId());
            }
        }
        model.addAttribute("memberCollectionStore", entity);
        return "admin/member/memberCollectionStoreForm";
    }

    /**
     * 执行编辑
     *
     * @param memberCollectionStore 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:edit")
    @RequestMapping(value = "edit2")
    public String edit2(MemberCollectionStore memberCollectionStore, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(memberCollectionStore, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(memberCollectionStore, model);//回显错误提示
        }

        //业务处理
        memberCollectionStoreService.updateByIdSelective(memberCollectionStore);
        addMessage(redirectAttributes, "编辑收藏店铺成功");
        return "redirect:" + adminPath + "/member/memberCollectionStore/list.do?repage";
    }

    /**
     * 删除
     *
     * @param memberCollectionStore 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberCollectionStore:edit")
    @RequestMapping(value = "delete")
    public String delete(MemberCollectionStore memberCollectionStore, RedirectAttributes redirectAttributes) {
        memberCollectionStoreService.deleteById(memberCollectionStore.getId());
        addMessage(redirectAttributes, "删除收藏店铺成功");
        return "redirect:" + adminPath + "/member/memberCollectionStore/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param memberCollectionStore 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(MemberCollectionStore memberCollectionStore, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("collectionStoreId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("collectionStoreId")) && R.get("collectionStoreId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("mId"))) {
            errorList.add("关联(会员表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("mId")) && R.get("mId").length() > 19) {
            errorList.add("关联(会员表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add("关联(店铺表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("关联(店铺表)最大长度不能超过19字符");
        }
        return errorList;
    }

}