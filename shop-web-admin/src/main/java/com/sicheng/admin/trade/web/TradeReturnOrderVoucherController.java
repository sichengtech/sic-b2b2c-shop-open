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


import com.sicheng.admin.trade.entity.TradeReturnOrderVoucher;
import com.sicheng.admin.trade.service.TradeReturnOrderVoucherService;
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
 * 退货凭证 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeReturnOrderVoucher")
public class TradeReturnOrderVoucherController extends BaseController {

    @Autowired
    private TradeReturnOrderVoucherService tradeReturnOrderVoucherService;



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
     * @param tradeReturnOrderVoucher 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:view")
    @RequestMapping(value = "list")
    public String list(TradeReturnOrderVoucher tradeReturnOrderVoucher, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeReturnOrderVoucher> page = tradeReturnOrderVoucherService.selectByWhere(new Page<TradeReturnOrderVoucher>(request, response), new Wrapper(tradeReturnOrderVoucher));
        model.addAttribute("page", page);
        return "admin/trade/tradeReturnOrderVoucherList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeReturnOrderVoucher tradeReturnOrderVoucher, Model model) {
        if (tradeReturnOrderVoucher == null) {
            tradeReturnOrderVoucher = new TradeReturnOrderVoucher();
        }
        model.addAttribute("tradeReturnOrderVoucher", tradeReturnOrderVoucher);
        return "admin/trade/tradeReturnOrderVoucherForm";
    }

    /**
     * 执行保存
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeReturnOrderVoucher tradeReturnOrderVoucher, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrderVoucher, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeReturnOrderVoucher, model);//回显错误提示
        }

        //业务处理
        tradeReturnOrderVoucherService.insertSelective(tradeReturnOrderVoucher);
        addMessage(redirectAttributes, "保存退货凭证成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrderVoucher/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeReturnOrderVoucher tradeReturnOrderVoucher, Model model) {
        TradeReturnOrderVoucher entity = null;
        if (tradeReturnOrderVoucher != null) {
            if (tradeReturnOrderVoucher.getId() != null) {
                entity = tradeReturnOrderVoucherService.selectById(tradeReturnOrderVoucher.getId());
            }
        }
        model.addAttribute("tradeReturnOrderVoucher", entity);
        return "admin/trade/tradeReturnOrderVoucherForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeReturnOrderVoucher tradeReturnOrderVoucher, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrderVoucher, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeReturnOrderVoucher, model);//回显错误提示
        }

        //业务处理
        tradeReturnOrderVoucherService.updateByIdSelective(tradeReturnOrderVoucher);
        addMessage(redirectAttributes, "编辑退货凭证成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrderVoucher/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrderVoucher:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeReturnOrderVoucher tradeReturnOrderVoucher, RedirectAttributes redirectAttributes) {
        tradeReturnOrderVoucherService.deleteById(tradeReturnOrderVoucher.getId());
        addMessage(redirectAttributes, "删除退货凭证成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrderVoucher/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeReturnOrderVoucher 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeReturnOrderVoucher tradeReturnOrderVoucher, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("ciId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("ciId")) && R.get("ciId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("returnOrderId"))) {
            errorList.add("退货订单id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("returnOrderId")) && R.get("returnOrderId").length() > 19) {
            errorList.add("退货订单id最大长度不能超过19字符");
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
        if (StringUtils.isNotBlank(R.get("voucherSize")) && R.get("voucherSize").length() > 10) {
            errorList.add("图片大小最大长度不能超过10字符");
        }
        return errorList;
    }

}