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

import com.sicheng.admin.store.entity.StoreAlbum;
import com.sicheng.admin.store.service.StoreAlbumService;

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
 * 相册夹 Controller
 * 所属模块：store
 *
 * @author cl
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeAlbum")
public class StoreAlbumController extends BaseController {

    @Autowired
    private StoreAlbumService storeAlbumService;



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
     * @param storeAlbum 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:view")
    @RequestMapping(value = "list")
    public String list(StoreAlbum storeAlbum, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreAlbum> page = storeAlbumService.selectByWhere(new Page<StoreAlbum>(request, response), new Wrapper(storeAlbum));
        model.addAttribute("page", page);
        return "admin/store/storeAlbumList";
    }

    /**
     * 进入保存页面
     *
     * @param storeAlbum 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreAlbum storeAlbum, Model model) {
        if (storeAlbum == null) {
            storeAlbum = new StoreAlbum();
        }
        model.addAttribute("storeAlbum", storeAlbum);
        return "admin/store/storeAlbumForm";
    }

    /**
     * 执行保存
     *
     * @param storeAlbum         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreAlbum storeAlbum, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbum, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeAlbum, model);//回显错误提示
        }

        //业务处理
        storeAlbumService.insertSelective(storeAlbum);
        addMessage(redirectAttributes, "保存相册夹成功");
        return "redirect:" + adminPath + "/store/storeAlbum/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeAlbum 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreAlbum storeAlbum, Model model) {
        StoreAlbum entity = null;
        if (storeAlbum != null) {
            if (storeAlbum.getId() != null) {
                entity = storeAlbumService.selectById(storeAlbum.getId());
            }
        }
        model.addAttribute("storeAlbum", entity);
        return "admin/store/storeAlbumForm";
    }

    /**
     * 执行编辑
     *
     * @param storeAlbum         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreAlbum storeAlbum, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbum, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeAlbum, model);//回显错误提示
        }

        //业务处理
        storeAlbumService.updateByIdSelective(storeAlbum);
        addMessage(redirectAttributes, "编辑相册夹成功");
        return "redirect:" + adminPath + "/store/storeAlbum/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeAlbum         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreAlbum storeAlbum, RedirectAttributes redirectAttributes) {
        storeAlbumService.deleteById(storeAlbum.getId());
        addMessage(redirectAttributes, "删除相册夹成功");
        return "redirect:" + adminPath + "/store/storeAlbum/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeAlbum 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreAlbum storeAlbum, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("albumId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("albumId")) && R.get("albumId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联(卖家表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联(卖家表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("albumName"))) {
            errorList.add("相册名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("albumName")) && R.get("albumName").length() > 64) {
            errorList.add("相册名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("pictureCount"))) {
            errorList.add("图片数量不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pictureCount")) && R.get("pictureCount").length() > 10) {
            errorList.add("图片数量最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        return errorList;
    }

}