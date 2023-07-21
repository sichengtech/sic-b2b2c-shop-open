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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreCarouselPicture;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreCarouselPictureService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreCarouselPictureController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月17日 上午11:37:07
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeCarouselPicture")
public class StoreCarouselPictureController extends BaseController {

    @Autowired
    private StoreCarouselPictureService storeCarouselPictureService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040110";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入店铺幻灯片页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCarouselPicture:view")
    @RequestMapping(value = "save1")
    public String save1(Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
        storeCarouselPicture.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.orderBy("a.sort ASC");
        wrapper.setEntity(storeCarouselPicture);
        List<StoreCarouselPicture> storeCarouselPictures = storeCarouselPictureService.selectByWhere(wrapper);
        StoreCarouselPicture sp = new StoreCarouselPicture();
        List<StoreCarouselPicture> list = new ArrayList<>();
        //店铺轮播图片表中没有数据
        if (storeCarouselPictures.isEmpty()) {
            list.add(sp);
            list.add(sp);
            list.add(sp);
            list.add(sp);
        }
        //店铺轮播图片表中有1条数据
        if (storeCarouselPictures.size() == 1) {
            list.add(storeCarouselPictures.get(0));
            list.add(sp);
            list.add(sp);
            list.add(sp);
        }
        //店铺轮播图片表中有2条数据
        if (storeCarouselPictures.size() == 2) {
            list.add(storeCarouselPictures.get(0));
            list.add(storeCarouselPictures.get(1));
            list.add(sp);
            list.add(sp);
        }
        //店铺轮播图片表中有3条数据
        if (storeCarouselPictures.size() == 3) {
            list.add(storeCarouselPictures.get(0));
            list.add(storeCarouselPictures.get(1));
            list.add(storeCarouselPictures.get(2));
            list.add(sp);
        }
        //店铺轮播图片表中有4条数据
        if (storeCarouselPictures.size() == 4) {
            list.add(storeCarouselPictures.get(0));
            list.add(storeCarouselPictures.get(1));
            list.add(storeCarouselPictures.get(2));
            list.add(storeCarouselPictures.get(3));
        }
        model.addAttribute("list", list);
        return "seller/store/storeCarouselPicture";
    }

    /**
     * 保存幻灯页面
     *
     * @param storeCarouselPicture
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCarouselPicture:edit")
    @RequestMapping(value = "save2")
    public String save2(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        String[] Stringurls = R.getArray("url");
        String[] StringpicturePaths = R.getArray("picturePath");
        String[] StringSorts = R.getArray("sort");

        List<String> picturePaths = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<Integer> sorts = new ArrayList<>();
        if (StringpicturePaths.length > 0) {
            //把传回来的数组中为空的数据去除掉(URL，图片地址，排序)
            for (int i = 0; i < StringpicturePaths.length; i++) {
                if (StringUtils.isNotBlank(StringpicturePaths[i])) {
                    String url = "http://" + Stringurls[i];
                    if (url.indexOf("http://") != -1) {
                        url = Stringurls[i];
                    }
                    urls.add(url);
                    picturePaths.add(StringpicturePaths[i]);
                    if (StringUtils.isNotBlank(StringSorts[i])) {
                        sorts.add(Integer.parseInt(StringSorts[i]));
                    } else {
                        sorts.add(null);
                    }
                }
            }
            storeCarouselPictureService.update(userSeller.getStoreId(), urls, picturePaths, sorts);
        } else {
            //删除当前店铺的所有轮播图片
            StoreCarouselPicture sp = new StoreCarouselPicture();
            sp.setStoreId(userSeller.getStoreId());
            storeCarouselPictureService.deleteByWhere(new Wrapper(sp));
        }
        addMessage(redirectAttributes, FYUtils.fyParams("店铺幻灯设置成功"));
        return "redirect:" + sellerPath + "/store/storeCarouselPicture/save1.htm";
    }

}
