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

import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.service.StoreAlbumPictureService;

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
 * 商家相册图片 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeAlbumPicture")
public class StoreAlbumPictureController extends BaseController {

    @Autowired
    private StoreAlbumPictureService storeAlbumPictureService;



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
     * @param storeAlbumPicture 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:view")
    @RequestMapping(value = "list")
    public String list(StoreAlbumPicture storeAlbumPicture, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreAlbumPicture> page = storeAlbumPictureService.selectByWhere(new Page<StoreAlbumPicture>(request, response), new Wrapper(storeAlbumPicture));
        model.addAttribute("page", page);
        return "admin/store/storeAlbumPictureList";
    }

    /**
     * 进入保存页面
     *
     * @param storeAlbumPicture 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreAlbumPicture storeAlbumPicture, Model model) {
        if (storeAlbumPicture == null) {
            storeAlbumPicture = new StoreAlbumPicture();
        }
        model.addAttribute("storeAlbumPicture", storeAlbumPicture);
        return "admin/store/storeAlbumPictureForm";
    }

    /**
     * 执行保存
     *
     * @param storeAlbumPicture  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreAlbumPicture storeAlbumPicture, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbumPicture, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeAlbumPicture, model);//回显错误提示
        }

        //业务处理
        storeAlbumPictureService.insertSelective(storeAlbumPicture);
        addMessage(redirectAttributes, "保存商家相册图片成功");
        return "redirect:" + adminPath + "/store/storeAlbumPicture/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeAlbumPicture 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreAlbumPicture storeAlbumPicture, Model model) {
        StoreAlbumPicture entity = null;
        if (storeAlbumPicture != null) {
            if (storeAlbumPicture.getId() != null) {
                entity = storeAlbumPictureService.selectById(storeAlbumPicture.getId());
            }
        }
        model.addAttribute("storeAlbumPicture", entity);
        return "admin/store/storeAlbumPictureForm";
    }

    /**
     * 执行编辑
     *
     * @param storeAlbumPicture  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreAlbumPicture storeAlbumPicture, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbumPicture, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeAlbumPicture, model);//回显错误提示
        }

        //业务处理
        storeAlbumPictureService.updateByIdSelective(storeAlbumPicture);
        addMessage(redirectAttributes, "编辑商家相册图片成功");
        return "redirect:" + adminPath + "/store/storeAlbumPicture/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeAlbumPicture  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreAlbumPicture storeAlbumPicture, RedirectAttributes redirectAttributes) {
        storeAlbumPictureService.deleteById(storeAlbumPicture.getId());
        addMessage(redirectAttributes, "删除商家相册图片成功");
        return "redirect:" + adminPath + "/store/storeAlbumPicture/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeAlbumPicture 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreAlbumPicture storeAlbumPicture, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pictureId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pictureId")) && R.get("pictureId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联(卖家表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联(卖家表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("albumId"))) {
            errorList.add("store_album(相册夹表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("albumId")) && R.get("albumId").length() > 19) {
            errorList.add("store_album(相册夹表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("path")) && R.get("path").length() > 255) {
            errorList.add("图片的存储路径最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("pixel")) && R.get("pixel").length() > 64) {
            errorList.add("原图像素，例如：200x300，单位px最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("fileSize")) && R.get("fileSize").length() > 10) {
            errorList.add("文件大小，单位byte最大长度不能超过10字符");
        }
        return errorList;
    }

}