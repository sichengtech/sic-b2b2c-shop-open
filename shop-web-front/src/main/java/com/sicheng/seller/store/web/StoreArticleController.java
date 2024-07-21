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
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreArticle;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.xss.XssClean;
import com.sicheng.seller.store.service.StoreArticleService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;


/**
 * 发布店铺文章 Controller
 * 所属模块：store
 *
 * @author zjl
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeArticle")
public class StoreArticleController extends BaseController {

    @Autowired
    private StoreArticleService storeArticleService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040140";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }


    /**
     * 进入列表页
     *
     * @param storeArticle 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeArticle:view")
    @RequestMapping(value = "list")
    public String list(StoreArticle storeArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeArticle.setStoreId(userSeller.getStoreId());
        //查询店铺文章列表
        Page<StoreArticle> page = storeArticleService.selectByWhere(new Page<StoreArticle>(request, response), new Wrapper(storeArticle));
        model.addAttribute("page", page);
        model.addAttribute("title", storeArticle.getTitle());
        //店铺信息
        model.addAttribute("userMain", SsoUtils.getUserMain());
        return "seller/store/storeArticleList";
    }

    /**
     * 进入保存页面
     *
     * @param storeArticle 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeArticle:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreArticle storeArticle, Model model) {
        if (storeArticle == null) {
            storeArticle = new StoreArticle();
        }
        model.addAttribute("storeArticle", storeArticle);
        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        return "seller/store/storeArticleForm";
    }

    /**
     * 执行保存
     *
     * @param storeArticle       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeArticle:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreArticle storeArticle, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeArticle, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeArticle, model);//回显错误提示
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //获取经过白名单过滤之后的文章内容
        String content = getContent(storeArticle.getContent());
        //业务处理
        storeArticle.setStoreId(userSeller.getStoreId());
        storeArticle.setContent(content);//获取经过白名单过滤之后的文章内容
        storeArticleService.insertSelective(storeArticle);
        addMessage(redirectAttributes, FYUtils.fyParams("保存店铺文章成功"));
        return "redirect:" + sellerPath + "/store/storeArticle/list.htm?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeArticle 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeArticle:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreArticle storeArticle, Model model) {
        //入参检查
        if (storeArticle == null || storeArticle.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺文章不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeArticle.setStoreId(userSeller.getStoreId());//属主检查
        StoreArticle entity = storeArticleService.selectOne(new Wrapper(storeArticle));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺文章不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("storeArticle", entity);
        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        return "seller/store/storeArticleForm";
    }

    /**
     * 执行编辑
     *
     * @param storeArticle       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeArticle:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreArticle storeArticle, Model model, RedirectAttributes redirectAttributes) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //入参检查
        if (storeArticle == null || storeArticle.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺文章不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(storeArticle, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeArticle, model);//回显错误提示
        }

        //获取经过白名单过滤之后的文章内容
        String content = getContent(storeArticle.getContent());
        //业务处理
        storeArticle.setContent(content);//获取经过白名单过滤之后的文章内容
        StoreArticle condition = new StoreArticle();
        condition.setId(storeArticle.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        storeArticleService.updateByWhereSelective(storeArticle, new Wrapper(condition));
        addMessage(redirectAttributes, FYUtils.fyParams("编辑店铺文章成功"));
        return "redirect:" + sellerPath + "/store/storeArticle/list.htm?repage";
    }

    /**
     * 删除
     *
     * @param storeArticle       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeArticle:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreArticle storeArticle, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (storeArticle == null || storeArticle.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺文章不存在！"));
            return "error/400";
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeArticle.setStoreId(userSeller.getStoreId());//属主检查
        storeArticleService.deleteByWhere(new Wrapper(storeArticle));
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺文章成功"));
        return "redirect:" + sellerPath + "/store/storeArticle/list.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param storeArticle 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreArticle storeArticle, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("title"))) {
            errorList.add(FYUtils.fyParams("标题不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 255) {
            errorList.add(FYUtils.fyParams("标题最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("内容不能为空"));
        }
        return errorList;
    }

    /**
     * 获取经过白名单过滤之后的文章内容
     *
     * @param content 未经过白名单过滤之后的文章内容
     * @return 经过白名单过滤之后的文章内容
     */
    private String getContent(String content) {
        String html_unsafe = StringEscapeUtils.unescapeHtml4(content);//转回来（还原）
        content = XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
        return content;
    }
}