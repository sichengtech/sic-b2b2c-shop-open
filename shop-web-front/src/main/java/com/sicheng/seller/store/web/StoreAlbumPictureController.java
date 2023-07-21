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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sicheng.admin.store.entity.StoreAlbum;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreAlbumPictureService;
import com.sicheng.seller.store.service.StoreAlbumService;
import com.sicheng.seller.store.service.StoreAlbumSpaceService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreAlbumPictureController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月24日 下午4:43:49
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeAlbumPicture")
public class StoreAlbumPictureController extends BaseController {

    @Autowired
    private StoreAlbumPictureService storeAlbumPictureService;

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
     * 查询对应相册的图片
     *
     * @param storeAlbumPicture
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequiresPermissions("store:storeAlbumPicture:view")
    @RequestMapping(value = "switch")
    public String switchStoreAlbumPicture(StoreAlbumPicture storeAlbumPicture, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAlbumPicture.setStoreId(userSeller.getStoreId());
        Page<StoreAlbumPicture> page = new Page<StoreAlbumPicture>(request, response);
        String pageNo = R.get("pageNo");
        if (StringUtils.isNoneBlank(pageNo)) {
            String[] pageNos = pageNo.split("-");
            if (pageNos.length > 1 && storeAlbumPicture.getAlbumId() != null && storeAlbumPicture.getAlbumId().toString().equals(pageNos[0])) {
                page.setPageNo(Integer.parseInt(pageNos[1]));
            }
        }
        page = storeAlbumPictureService.selectByWhere(page, new Wrapper(storeAlbumPicture));
        StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceService.selectById(userSeller.getStoreId());
		/*if(storeAlbumSpace!=null && storeAlbumSpace.getTotalSpace()!=null && storeAlbumSpace.getPictureSpace()!=null){
			storeAlbumSpace.setTotalSpaceM(FileSizeHelper.getHumanReadableFileSize(storeAlbumSpace.getTotalSpace()));
			storeAlbumSpace.setPictureSpaceM(FileSizeHelper.getHumanReadableFileSize(storeAlbumSpace.getPictureSpace()));
		}*/
        model.addAttribute("storeAlbumSpace", storeAlbumSpace);
        model.addAttribute("albumId", storeAlbumPicture.getAlbumId());
        model.addAttribute("page", page);
        return "seller/store/storeAlbumPictureSwitch";
    }

    /**
     * 移动弹出相册弹出框
     *
     * @param storeAlbum
     * @param request
     * @param model
     * @return
     * @throws IOException
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @RequestMapping(value = "albumList")
    public String albumList(StoreAlbum storeAlbum, HttpServletRequest request, Model model) throws IOException {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeAlbum.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeAlbum);
        wrapper.orderBy("a.sort");
        List<StoreAlbum> storeAlbums = storeAlbumService.selectByWhere(wrapper);
        model.addAttribute("storeAlbums", storeAlbums);
        return "seller/store/storeAlbumPop";
    }

    /**
     * 移动照片到相册
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @ResponseBody
    @RequestMapping(value = "moveAlbumPicBatch")
    public Map<String, Object> moveAlbumPicBatch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Map<String, Object> map = new HashMap<>();
        String albumId = R.get("albumId");
        String targetAlbumId = R.get("targetAlbumId");
        String pictureId = R.get("pictureId");
        if (StringUtils.isBlank(albumId) || !StringUtils.isNumeric(albumId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("相册不能为空，必须是数字"));
            return map;
        }
        if (StringUtils.isBlank(targetAlbumId) || !StringUtils.isNumeric(targetAlbumId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("目标相册不能为空，必须是数字"));
            return map;
        }
        if (StringUtils.isBlank(pictureId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("请选择要移动的图片"));
            return map;
        }
        int count = storeAlbumPictureService.removePicture(pictureId, Long.parseLong(albumId), Long.parseLong(targetAlbumId));
        if (count > 0) {
            UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
            StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceService.selectById(userSeller.getStoreId());
            map.put("success", true);
            map.put("message", FYUtils.fyParams("移动成功"));
            map.put("totalSpaceM", storeAlbumSpace.getTotalSpaceM());
            map.put("pictureSpaceM", storeAlbumSpace.getPictureSpaceM());
            map.put("pictureSpace", storeAlbumSpace.getPictureSpace());
        } else {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("请选择要移动的图片"));
        }
        return map;
    }

    /**
     * 相册空间删除图片
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAlbumPicture:edit")
    @ResponseBody
    @RequestMapping(value = "deleteBatch")
    public Map<String, Object> deleteBatch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String albumId = R.get("albumId");
        String pictureId = R.get("pictureId");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(albumId) || !StringUtils.isNumeric(albumId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("相册不能为空，切必须是数字"));
            return map;
        }
        if (StringUtils.isBlank(pictureId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("请选择要删除的图片"));
            return map;
        }
        int count = storeAlbumPictureService.deletebatchpic(pictureId, Long.parseLong(albumId));
        if (count > 0) {
            UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
            StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceService.selectById(userSeller.getStoreId());
            map.put("success", true);
            map.put("message", FYUtils.fyParams("删除成功"));
            map.put("totalSpaceM", storeAlbumSpace.getTotalSpaceM());
            map.put("pictureSpaceM", storeAlbumSpace.getPictureSpaceM());
            map.put("pictureSpace", storeAlbumSpace.getPictureSpace());
        } else {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("删除失败"));
        }
        return map;
    }

    /**
     * 查询相册中的图片
     *
     * @param albumId
     * @return
     * @page
     */
    @RequiresPermissions("store:storeAlbumPicture:view")
    @ResponseBody
    @RequestMapping(value = "albumPictureList")
    public Map<String, Object> albumPictureList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String albumId = R.get("albumId");
        String pageNo = R.get("pageNo");
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        if ((StringUtils.isNotBlank(albumId) && !StringUtils.isNumeric(albumId)) || !StringUtils.isNumeric(pageNo)) {
            return map;
        }
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(albumId)) {
            wrapper.and("album_id=", albumId);
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller == null || userSeller.getStoreId() == null) {
            return map;
        }
        wrapper.and("store_id=", SsoUtils.getUserMain().getUserSeller().getStoreId());
        wrapper.orderBy("create_date desc, picture_id asc");
        Page<StoreAlbumPicture> page = new Page<StoreAlbumPicture>(request, response);
        page.setPageSize(21);
        page.setPageNo(Integer.parseInt(pageNo));
        page = storeAlbumPictureService.selectByWhere(page, wrapper);
        map.put("page", page);
        map.put("isLast", page.isLastPage());
        map.put("isFirst", page.isFirstPage());
        return map;
    }
}
