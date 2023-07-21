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
package com.sicheng.admin.trade.web;


import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.admin.trade.service.TradeDeliverService;
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
 * 发票 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeDeliver")
public class TradeDeliverController extends BaseController {

    @Autowired
    private TradeDeliverService tradeDeliverService;



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
     * @param tradeDeliver 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:view")
    @RequestMapping(value = "list")
    public String list(TradeDeliver tradeDeliver, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeDeliver> page = tradeDeliverService.selectByWhere(new Page<TradeDeliver>(request, response), new Wrapper(tradeDeliver));
        model.addAttribute("page", page);
        return "admin/trade/tradeDeliverList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeDeliver 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeDeliver tradeDeliver, Model model) {
        if (tradeDeliver == null) {
            tradeDeliver = new TradeDeliver();
        }
        model.addAttribute("tradeDeliver", tradeDeliver);
        return "admin/trade/tradeDeliverForm";
    }

    /**
     * 执行保存
     *
     * @param tradeDeliver       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeDeliver tradeDeliver, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeDeliver, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeDeliver, model);//回显错误提示
        }

        //业务处理
        tradeDeliverService.insertSelective(tradeDeliver);
        addMessage(redirectAttributes, "保存发票成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeDeliver/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeDeliver 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeDeliver tradeDeliver, Model model) {
        TradeDeliver entity = null;
        if (tradeDeliver != null) {
            if (tradeDeliver.getId() != null) {
                entity = tradeDeliverService.selectById(tradeDeliver.getId());
            }
        }
        model.addAttribute("tradeDeliver", entity);
        return "admin/trade/tradeDeliverForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeDeliver       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeDeliver tradeDeliver, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeDeliver, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeDeliver, model);//回显错误提示
        }

        //业务处理
        tradeDeliverService.updateByIdSelective(tradeDeliver);
        addMessage(redirectAttributes, "编辑发票成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeDeliver/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeDeliver       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeDeliver:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeDeliver tradeDeliver, RedirectAttributes redirectAttributes) {
        tradeDeliverService.deleteById(tradeDeliver.getId());
        addMessage(redirectAttributes, "删除发票成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeDeliver/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeDeliver 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeDeliver tradeDeliver, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("mId"))) {
            errorList.add("会员id(会员表Id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("mId")) && R.get("mId").length() > 19) {
            errorList.add("会员id(会员表Id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("deliverType"))) {
            errorList.add("发票类型：1普通发票，2增值税普通发票，3增值税专用发票不能为空");
        }
        if (StringUtils.isNotBlank(R.get("deliverType")) && R.get("deliverType").length() > 1) {
            errorList.add("发票类型：1普通发票，2增值税普通发票，3增值税专用发票最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("deliverTitle"))) {
            errorList.add("发票抬头不能为空");
        }
        if (StringUtils.isNotBlank(R.get("deliverTitle")) && R.get("deliverTitle").length() > 64) {
            errorList.add("发票抬头最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("deliverContent"))) {
            errorList.add("发票内容不能为空");
        }
        if (StringUtils.isNotBlank(R.get("deliverContent")) && R.get("deliverContent").length() > 255) {
            errorList.add("发票内容最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("hbjbuyer"))) {
            errorList.add("默认，0否、1是不能为空");
        }
        if (StringUtils.isNotBlank(R.get("hbjbuyer")) && R.get("hbjbuyer").length() > 1) {
            errorList.add("默认，0否、1是最大长度不能超过1字符");
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
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add("备用字段6最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add("备用字段7最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add("备用字段8最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add("备用字段9最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add("备用字段10最大长度不能超过64字符");
        }
        return errorList;
    }

}