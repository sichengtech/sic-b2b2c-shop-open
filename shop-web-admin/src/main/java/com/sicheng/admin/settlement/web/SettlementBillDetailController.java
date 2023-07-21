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
package com.sicheng.admin.settlement.web;

import com.sicheng.admin.account.entity.SettlementBillDetail;
import com.sicheng.admin.account.service.SettlementBillDetailService;

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
 * 账单详情 Controller
 * 所属模块：settlement
 *
 * @author 范秀秀
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementBillDetail")
public class SettlementBillDetailController extends BaseController {

    @Autowired
    private SettlementBillDetailService settlementBillDetailService;



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
     * @param settlementBillDetail 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:view")
    @RequestMapping(value = "list")
    public String list(SettlementBillDetail settlementBillDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementBillDetail> page = settlementBillDetailService.selectByWhere(new Page<SettlementBillDetail>(request, response), new Wrapper(settlementBillDetail));
        model.addAttribute("page", page);
        return "admin/settlement/settlementBillDetailList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementBillDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:edit")
    @RequestMapping(value = "save1")
    public String save1(SettlementBillDetail settlementBillDetail, Model model) {
        if (settlementBillDetail == null) {
            settlementBillDetail = new SettlementBillDetail();
        }
        model.addAttribute("settlementBillDetail", settlementBillDetail);
        return "admin/settlement/settlementBillDetailForm";
    }

    /**
     * 执行保存
     *
     * @param settlementBillDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:edit")
    @RequestMapping(value = "save2")
    public String save2(SettlementBillDetail settlementBillDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementBillDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementBillDetail, model);//回显错误提示
        }

        //业务处理
        settlementBillDetailService.insertSelective(settlementBillDetail);
        addMessage(redirectAttributes, "保存账单详情成功");
        return "redirect:" + adminPath + "/settlement/settlementBillDetail/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementBillDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementBillDetail settlementBillDetail, Model model) {
        SettlementBillDetail entity = null;
        if (settlementBillDetail != null) {
            if (settlementBillDetail.getId() != null) {
                entity = settlementBillDetailService.selectById(settlementBillDetail.getId());
            }
        }
        model.addAttribute("settlementBillDetail", entity);
        return "admin/settlement/settlementBillDetailForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementBillDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementBillDetail settlementBillDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementBillDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementBillDetail, model);//回显错误提示
        }

        //业务处理
        settlementBillDetailService.updateByIdSelective(settlementBillDetail);
        addMessage(redirectAttributes, "编辑账单详情成功");
        return "redirect:" + adminPath + "/settlement/settlementBillDetail/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementBillDetail 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBillDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(SettlementBillDetail settlementBillDetail, RedirectAttributes redirectAttributes) {
        settlementBillDetailService.deleteById(settlementBillDetail.getId());
        addMessage(redirectAttributes, "删除账单详情成功");
        return "redirect:" + adminPath + "/settlement/settlementBillDetail/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementBillDetail 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementBillDetail settlementBillDetail, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("billDetailId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("billDetailId")) && R.get("billDetailId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("billId"))) {
            errorList.add("账单id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("billId")) && R.get("billId").length() > 19) {
            errorList.add("账单id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("类型不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 19) {
            errorList.add("订单id最大长度不能超过19字符");
        }
        return errorList;
    }

}