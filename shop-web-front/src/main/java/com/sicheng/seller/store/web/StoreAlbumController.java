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
package com.sicheng.seller.store.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreAlbum;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileSizeHelper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreAlbumService;
import com.sicheng.seller.store.service.StoreAlbumSpaceService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreAlbumController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年2月24日 上午11:25:17
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeAlbum")
public class StoreAlbumController extends BaseController {

    @Autowired
    private StoreAlbumService storeAlbumService;

    @Autowired
    private StoreAlbumSpaceService storeAlbumSpaceService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040125";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入相册空间
     *
     * @param storeAlbum
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:view")
    @RequestMapping(value = "info")
    public String info(StoreAlbum storeAlbum, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreAlbum s = new StoreAlbum();
        s.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(s);
        wrapper.orderBy("a.sort,a.album_id");
        //查询相册
        List<StoreAlbum> storeAlbumList = storeAlbumService.selectByWhere(wrapper);
        //如果相册id不为空，查询相册对应的图片,否则查询第一个相册对应的图片
        if (!storeAlbumList.isEmpty()) {
            Long albumId = null;
            if (storeAlbum.getAlbumId() != null) {
                albumId = storeAlbum.getAlbumId();
            } else {
                albumId = storeAlbumList.get(0).getAlbumId();
            }
            model.addAttribute("albumId", albumId);
        }
        //查询相册的容量
        StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceService.selectById(userSeller.getStoreId());
        if (storeAlbumSpace != null && storeAlbumSpace.getTotalSpace() != null && storeAlbumSpace.getPictureSpace() != null) {
            storeAlbumSpace.setTotalSpaceM(FileSizeHelper.getHumanReadableFileSize(storeAlbumSpace.getTotalSpace()));
            storeAlbumSpace.setPictureSpaceM(FileSizeHelper.getHumanReadableFileSize(storeAlbumSpace.getPictureSpace()));
        }
        model.addAttribute("storeAlbums", storeAlbumList);
        model.addAttribute("storeAlbumSpace", storeAlbumSpace);
        model.addAttribute("pageNo", R.get("pageNo"));
        return "seller/store/storeAlbumPicture";
    }

    /**
     * 管理相册
     *
     * @param storeAlbum
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:view")
    @RequestMapping(value = "list")
    public String list(StoreAlbum storeAlbum, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAlbum.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeAlbum);
        wrapper.orderBy("a.sort,a.album_id");
        Page<StoreAlbum> page = storeAlbumService.selectByWhere(new Page<StoreAlbum>(request, response), wrapper);
        model.addAttribute("page", page);
        return "seller/store/storeAlbumList";
    }

    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "save")
    public String save(StoreAlbum storeAlbum, RedirectAttributes redirectAttributes, HttpServletResponse response, Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAlbum.setStoreId(userSeller.getStoreId());
        String[] albumIds = R.getArray("albumId");
        String[] sorts = R.getArray("sort");
        String[] albumNames = R.getArray("albumName");
        if (albumNames.length == 0) {
            addMessage(redirectAttributes, FYUtils.fyParams("请添加相册在保存"));
            return "redirect:" + sellerPath + "/store/storeAlbum/list.htm?repage";
        }
        if (albumIds.length != albumNames.length) {
            addMessage(redirectAttributes, FYUtils.fyParams("相册信息缺失"));
            return "redirect:" + sellerPath + "/store/storeAlbum/list.htm?repage";
        }
        List<String> errorList = new ArrayList<String>();
        //判断相册夹名
        for (int i = 0; i < albumNames.length; i++) {
            boolean a = false;
            boolean b = false;
            if (StringUtils.isBlank(albumNames[i])) {
                errorList.add(FYUtils.fyParams("相册夹不能为空"));
                a = true;
            }
            if (StringUtils.isNotBlank(albumNames[i]) && albumNames[i].length() > 64) {
                errorList.add(FYUtils.fyParams("相册夹不能大于64字符"));
                b = true;
            }
            if (a == true && b == true) {
                break;
            }
        }
        //判断排序
        for (int i = 0; i < sorts.length; i++) {
            boolean a = false;
            boolean b = false;
            if (StringUtils.isBlank(sorts[i])) {
                errorList.add(FYUtils.fyParams("排序不能为空"));
                a = true;
            }
            if (StringUtils.isNotBlank(sorts[i]) && sorts[i].length() > 10) {
                errorList.add(FYUtils.fyParams("排序不能大于10字符"));
                b = true;
            }
            if (a == true && b == true) {
                break;
            }
        }
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return list(storeAlbum, request, response, model);//回显错误提示
        } else {
            storeAlbumService.save(albumIds, sorts, albumNames, storeAlbum);
            addMessage(redirectAttributes, FYUtils.fyParams("保存相册夹成功"));
        }
        return "redirect:" + sellerPath + "/store/storeAlbum/list.htm?repage";
    }

    /**
     * 删除相册
     *
     * @param storeAlbum
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAlbum:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreAlbum storeAlbum, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        //入参检查
        if (storeAlbum == null || storeAlbum.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("相册不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAlbum.setStoreId(userSeller.getStoreId());//属主检查
        List<StoreAlbum> list = storeAlbumService.selectByWhere(new Wrapper(storeAlbum));
        if (list.isEmpty()) {
            model.addAttribute("message", FYUtils.fyParams("相册不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        storeAlbumService.delete(storeAlbum);
        addMessage(redirectAttributes, FYUtils.fyParams("删除相册夹成功"));
        return "redirect:" + sellerPath + "/store/storeAlbum/list.htm?repage";
    }

}
