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


import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.admin.trade.service.TradeConsultationService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
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
 * 咨询 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeConsultation")
public class TradeConsultationController extends BaseController {

    @Autowired
    private TradeConsultationService tradeConsultationService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030105";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeConsultation 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:view")
    @RequestMapping(value = "list")
    public String list(TradeConsultation tradeConsultation, HttpServletRequest request, HttpServletResponse response, Model model) {
        //设置为咨询类型
        tradeConsultation.setType("0");
        Page<TradeConsultation> page = tradeConsultationService.selectByWhere(new Page<TradeConsultation>(request, response), new Wrapper(tradeConsultation));
        TradeConsultation.fillUserMain(page.getList());
        TradeConsultation.fillProductSpu(page.getList());
        TradeConsultation.fillStore(page.getList());
        TradeConsultation.fillReplyTradeConsultation(page.getList());
        model.addAttribute("page", page);
        return "admin/trade/tradeConsultationList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeConsultation 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:save")
    @RequestMapping(value = "save1")
    public String save1(TradeConsultation tradeConsultation, Model model) {
        if (tradeConsultation == null) {
            tradeConsultation = new TradeConsultation();
        }
        model.addAttribute("tradeConsultation", tradeConsultation);
        return "admin/trade/tradeConsultationForm";
    }

    /**
     * 执行保存
     *
     * @param tradeConsultation  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:save")
    @RequestMapping(value = "save2")
    public String save2(TradeConsultation tradeConsultation, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeConsultation, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeConsultation, model);//回显错误提示
        }

        //业务处理
        tradeConsultationService.insertSelective(tradeConsultation);
        addMessage(redirectAttributes, "保存咨询成功");
        return "redirect:" + adminPath + "/trade/tradeConsultation/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeConsultation 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeConsultation tradeConsultation, Model model) {
        TradeConsultation entity = null;
        if (tradeConsultation != null) {
            if (tradeConsultation.getId() != null) {
                entity = tradeConsultationService.selectById(tradeConsultation.getId());
            }
        }
        model.addAttribute("tradeConsultation", entity);
        return "admin/trade/tradeConsultationForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeConsultation  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeConsultation tradeConsultation, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeConsultation, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeConsultation, model);//回显错误提示
        }

        //业务处理
        tradeConsultationService.updateByIdSelective(tradeConsultation);
        addMessage(redirectAttributes, "编辑咨询成功");
        return "redirect:" + adminPath + "/trade/tradeConsultation/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeConsultation  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeConsultation tradeConsultation, RedirectAttributes redirectAttributes) {
        if (tradeConsultation != null) {
            tradeConsultationService.deleteConsultation(tradeConsultation);
        }
        addMessage(redirectAttributes, FYUtils.fy("删除咨询成功"));
        return "redirect:" + adminPath + "/trade/tradeConsultation/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeConsultation 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeConsultation tradeConsultation, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("consultationId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("consultationId")) && R.get("consultationId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("mId")) && R.get("mId").length() > 64) {
            errorList.add("会员id最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("商家id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("产品id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("产品id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add("咨询内容不能为空");
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 255) {
            errorList.add("咨询内容最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("category")) && R.get("category").length() > 64) {
            errorList.add("咨询类型最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("类别不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("类别最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("replyId")) && R.get("replyId").length() > 19) {
            errorList.add("回复的咨询id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("ip")) && R.get("ip").length() > 64) {
            errorList.add("ip最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("isShow")) && R.get("isShow").length() > 1) {
            errorList.add("是否显示最大长度不能超过1字符");
        }
        return errorList;
    }

}