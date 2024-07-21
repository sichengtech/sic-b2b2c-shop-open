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
package com.sicheng.admin.trade.web;


import com.sicheng.admin.trade.entity.TradeCommentImage;
import com.sicheng.admin.trade.service.TradeCommentImageService;
import com.sicheng.common.config.Global;
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
 * 评论图片 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeCommentImage")
public class TradeCommentImageController extends BaseController {

    @Autowired
    private TradeCommentImageService tradeCommentImageService;



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
     * @param tradeCommentImage 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:view")
    @RequestMapping(value = "list")
    public String list(TradeCommentImage tradeCommentImage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeCommentImage> page = tradeCommentImageService.selectByWhere(new Page<TradeCommentImage>(request, response), new Wrapper(tradeCommentImage));
        model.addAttribute("page", page);
        return "admin/trade/tradeCommentImageList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeCommentImage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeCommentImage tradeCommentImage, Model model) {
        if (tradeCommentImage == null) {
            tradeCommentImage = new TradeCommentImage();
        }
        model.addAttribute("tradeCommentImage", tradeCommentImage);
        return "admin/trade/tradeCommentImageForm";
    }

    /**
     * 执行保存
     *
     * @param tradeCommentImage  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeCommentImage tradeCommentImage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeCommentImage, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeCommentImage, model);//回显错误提示
        }

        //业务处理
        tradeCommentImageService.insertSelective(tradeCommentImage);
        addMessage(redirectAttributes, "保存评论图片成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCommentImage/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeCommentImage 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeCommentImage tradeCommentImage, Model model) {
        TradeCommentImage entity = null;
        if (tradeCommentImage != null) {
            if (tradeCommentImage.getId() != null) {
                entity = tradeCommentImageService.selectById(tradeCommentImage.getId());
            }
        }
        model.addAttribute("tradeCommentImage", entity);
        return "admin/trade/tradeCommentImageForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeCommentImage  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeCommentImage tradeCommentImage, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeCommentImage, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeCommentImage, model);//回显错误提示
        }

        //业务处理
        tradeCommentImageService.updateByIdSelective(tradeCommentImage);
        addMessage(redirectAttributes, "编辑评论图片成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCommentImage/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeCommentImage  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCommentImage:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeCommentImage tradeCommentImage, RedirectAttributes redirectAttributes) {
        tradeCommentImageService.deleteById(tradeCommentImage.getId());
        addMessage(redirectAttributes, "删除评论图片成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCommentImage/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeCommentImage 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeCommentImage tradeCommentImage, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("commentId"))) {
            errorList.add("评论id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("commentId")) && R.get("commentId").length() > 19) {
            errorList.add("评论id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("图片名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("path"))) {
            errorList.add("图片地址不能为空");
        }
        if (StringUtils.isNotBlank(R.get("path")) && R.get("path").length() > 64) {
            errorList.add("图片地址最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("imgSize")) && R.get("imgSize").length() > 10) {
            errorList.add("图片大小byte最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add("备用字段1最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add("备用字段2最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add("备用字段3最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add("备用字段4最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add("备用字段5最大长度不能超过64字符");
        }
        return errorList;
    }

}