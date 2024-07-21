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
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.admin.store.service.StoreAlbumPictureService;
import com.sicheng.admin.store.service.StoreAlbumSpaceService;
import com.sicheng.admin.store.service.StoreService;

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
 * 商家相册 Controller
 * 所属模块：store
 *
 * @author cl
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeAlbumSpace")
public class StoreAlbumSpaceController extends BaseController {

    @Autowired
    private StoreAlbumSpaceService storeAlbumSpaceService;

    @Autowired
    private StoreAlbumPictureService storeAlbumPictureService;



    @Autowired
    private StoreService storeService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050106";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeAlbumSpace 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:view")
    @RequestMapping(value = "list")
    public String list(StoreAlbumSpace storeAlbumSpace, HttpServletRequest request, HttpServletResponse response, Model model) {
        String storeName = R.get("storeName");
        model.addAttribute("storeName", storeName);
        if (StringUtils.isNoneBlank(storeName)) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
            Store storeB = new Store();
            storeB.setName(storeName);
            List<Store> list = storeService.selectByWhere(new Wrapper(storeB));
            if (!list.isEmpty()) {
                Store stobase = list.get(0);
                storeAlbumSpace.setId(stobase.getId());
            } else {
                return "admin/store/storeAlbumSpaceList";
            }
        }
        Page<StoreAlbumSpace> page = storeAlbumSpaceService.selectByWhere(new Page<StoreAlbumSpace>(request, response), new Wrapper(storeAlbumSpace));
        List<StoreAlbumSpace> storeAlbumSpaces = page.getList();
        if (!storeAlbumSpaces.isEmpty()) {
            for (int i = 0; i < storeAlbumSpaces.size(); i++) {
                StoreAlbumSpace storeAlbumSpace2 = storeAlbumSpaces.get(i);
                String totalSpaceM = FileSizeHelper.getHumanReadableFileSize(storeAlbumSpaces.get(i).getTotalSpace());
                storeAlbumSpace2.setTotalSpaceM(totalSpaceM);
                String pictureSpaceM = FileSizeHelper.getHumanReadableFileSize(storeAlbumSpaces.get(i).getPictureSpace());
                storeAlbumSpace2.setPictureSpaceM(pictureSpaceM);
                Long s = (long) (storeAlbumSpaces.get(i).getTotalSpace() - storeAlbumSpaces.get(i).getPictureSpace());
                String surplusSpaceM = FileSizeHelper.getHumanReadableFileSize(s);
                storeAlbumSpace2.setSurplusSpaceM(surplusSpaceM);
            }
        }
        StoreAlbumSpace.fillStore(storeAlbumSpaces);
        model.addAttribute("page", page);
        return "admin/store/storeAlbumSpaceList";
    }

    /**
     * 进入相册空间
     *
     * @param albumSpaceId
     * @param storeAlbumPicture
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:view")
    @RequestMapping(value = {"storePictureList"})
    public String storePictureList(Long albumSpaceId, StoreAlbumPicture storeAlbumPicture, HttpServletRequest request, HttpServletResponse response, Model model) {
        storeAlbumPicture.setStoreId(albumSpaceId);
        Page<StoreAlbumPicture> page = storeAlbumPictureService.selectByWhere(new Page<StoreAlbumPicture>(request, response), new Wrapper(storeAlbumPicture));
        model.addAttribute("page", page);
        return "admin/store/storeAlbumSpaceView";
    }

    /**
     * 进入保存页面
     *
     * @param storeAlbumSpace 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreAlbumSpace storeAlbumSpace, Model model) {
        if (storeAlbumSpace == null) {
            storeAlbumSpace = new StoreAlbumSpace();
        }
        model.addAttribute("storeAlbumSpace", storeAlbumSpace);
        return "admin/store/storeAlbumSpaceForm";
    }

    /**
     * 执行保存
     *
     * @param storeAlbumSpace    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreAlbumSpace storeAlbumSpace, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbumSpace, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeAlbumSpace, model);//回显错误提示
        }

        //业务处理
        storeAlbumSpaceService.insertSelective(storeAlbumSpace);
        addMessage(redirectAttributes, FYUtils.fyParams("保存商家相册成功"));
        return "redirect:" + adminPath + "/store/storeAlbumSpace/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeAlbumSpace 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreAlbumSpace storeAlbumSpace, Model model) {
        StoreAlbumSpace entity = null;
        if (storeAlbumSpace != null) {
            if (storeAlbumSpace.getId() != null) {
                entity = storeAlbumSpaceService.selectById(storeAlbumSpace.getId());
            }
        }
        model.addAttribute("storeAlbumSpace", entity);
        return "admin/store/storeAlbumSpaceForm";
    }

    /**
     * 执行编辑
     *
     * @param storeAlbumSpace    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreAlbumSpace storeAlbumSpace, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAlbumSpace, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeAlbumSpace, model);//回显错误提示
        }

        //业务处理
        storeAlbumSpaceService.updateByIdSelective(storeAlbumSpace);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑商家相册成功"));
        return "redirect:" + adminPath + "/store/storeAlbumSpace/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeAlbumSpace    实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumSpace:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreAlbumSpace storeAlbumSpace, RedirectAttributes redirectAttributes) {
        storeAlbumSpaceService.deleteById(storeAlbumSpace.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除商家相册成功"));
        return "redirect:" + adminPath + "/store/storeAlbumSpace/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeAlbumSpace 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreAlbumSpace storeAlbumSpace, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("albumSpaceId"))) {
            errorList.add(FYUtils.fyParams("相册ID不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("albumSpaceId")) && R.get("albumSpaceId").length() > 19) {
            errorList.add(FYUtils.fyParams("相册ID最大长度不能超过19字符"));
        }
        if (StringUtils.isBlank(R.get("pictureCount"))) {
            errorList.add(FYUtils.fyParams("图片数量不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("pictureCount")) && R.get("pictureCount").length() > 10) {
            errorList.add(FYUtils.fyParams("图片数量最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("albumCount"))) {
            errorList.add(FYUtils.fyParams("相册的数量不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("albumCount")) && R.get("albumCount").length() > 10) {
            errorList.add(FYUtils.fyParams("相册的数量最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("totalSpace"))) {
            errorList.add(FYUtils.fyParams("图片总空间不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("totalSpace")) && R.get("totalSpace").length() > 10) {
            errorList.add(FYUtils.fyParams("图片总空间最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("pictureSpace"))) {
            errorList.add(FYUtils.fyParams("图片占用空间不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("pictureSpace")) && R.get("pictureSpace").length() > 10) {
            errorList.add(FYUtils.fyParams("图片占用空间最大长度不能超过10字符"));
        }
        return errorList;
    }

}