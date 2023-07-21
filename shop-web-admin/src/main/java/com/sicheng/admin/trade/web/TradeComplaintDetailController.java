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


import com.sicheng.admin.trade.entity.TradeComplaintDetail;
import com.sicheng.admin.trade.service.TradeComplaintDetailService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投诉对话详情 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeComplaintDetail")
public class TradeComplaintDetailController extends BaseController {

    @Autowired
    private TradeComplaintDetailService tradeComplaintDetailService;



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
     * @param tradeComplaintDetail 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:view")
    @RequestMapping(value = "list")
    public String list(TradeComplaintDetail tradeComplaintDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeComplaintDetail> page = tradeComplaintDetailService.selectByWhere(new Page<TradeComplaintDetail>(request, response), new Wrapper(tradeComplaintDetail));
        model.addAttribute("page", page);
        return "admin/trade/tradeComplaintDetailList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeComplaintDetail tradeComplaintDetail, Model model) {
        if (tradeComplaintDetail == null) {
            tradeComplaintDetail = new TradeComplaintDetail();
        }
        model.addAttribute("tradeComplaintDetail", tradeComplaintDetail);
        return "admin/trade/tradeComplaintDetailForm";
    }

    /**
     * 执行保存
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeComplaintDetail tradeComplaintDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeComplaintDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeComplaintDetail, model);//回显错误提示
        }

        //业务处理
        tradeComplaintDetailService.insertSelective(tradeComplaintDetail);
        addMessage(redirectAttributes, "保存投诉对话详情成功");
        return "redirect:" + adminPath + "/trade/tradeComplaintDetail/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeComplaintDetail tradeComplaintDetail, Model model) {
        TradeComplaintDetail entity = null;
        if (tradeComplaintDetail != null) {
            if (tradeComplaintDetail.getId() != null) {
                entity = tradeComplaintDetailService.selectById(tradeComplaintDetail.getId());
            }
        }
        model.addAttribute("tradeComplaintDetail", entity);
        return "admin/trade/tradeComplaintDetailForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeComplaintDetail tradeComplaintDetail, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeComplaintDetail, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeComplaintDetail, model);//回显错误提示
        }

        //业务处理
        tradeComplaintDetailService.updateByIdSelective(tradeComplaintDetail);
        addMessage(redirectAttributes, "编辑投诉对话详情成功");
        return "redirect:" + adminPath + "/trade/tradeComplaintDetail/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeComplaintDetail 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaintDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeComplaintDetail tradeComplaintDetail, RedirectAttributes redirectAttributes) {
        tradeComplaintDetailService.deleteById(tradeComplaintDetail.getId());
        addMessage(redirectAttributes, "删除投诉对话详情成功");
        return "redirect:" + adminPath + "/trade/tradeComplaintDetail/list.do?repage";
    }

    /**
     * 发送对话
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "sendMessage")
    public Map<String, Object> sendMessage(TradeComplaintDetail tradeComplaintDetail, Model model, RedirectAttributes redirectAttributes) {
        String stat = "1";
        Map<String, Object> map = new HashMap<>();
        //表单验证
        List<String> errorList = validate(tradeComplaintDetail, model);
        //用户类型，1买家、2商家、3平台
        tradeComplaintDetail.setUserType("3");
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            stat = "0";
            map.put("message", stat);
            return map;
        }
        //业务处理
        tradeComplaintDetailService.insertSelective(tradeComplaintDetail);
        map.put("message", stat);
        map.put("tradeComplaintDetail", tradeComplaintDetail);
        return map;
    }

    /**
     * 刷新对话
     *
     * @param tradeComplaint 实体对象
     * @return
     */
    @ResponseBody
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "refreshMessage")
    public List<TradeComplaintDetail> refreshMessage(TradeComplaintDetail tradeComplaintDetail) {
        //业务处理
        List<TradeComplaintDetail> complaintDetailList = tradeComplaintDetailService.selectByWhere(new Wrapper(tradeComplaintDetail));
        return complaintDetailList;
    }

    /**
     * 屏蔽对话
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "shieldMessage")
    public String shieldMessage(TradeComplaintDetail tradeComplaintDetail, Model model) {
        String stat = "1";
        if (tradeComplaintDetail == null) {
            stat = "0";
            return stat;
        }
        if (tradeComplaintDetail.getCdId() != null) {
            TradeComplaintDetail complaintDetail = tradeComplaintDetailService.selectById(tradeComplaintDetail.getCdId());
            // 是否屏蔽,0否，1是
            complaintDetail.setIsShield("1");
            tradeComplaintDetailService.updateById(complaintDetail);
        }
        return stat;
    }

    /**
     * 表单验证
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComplaintDetail tradeComplaintDetail, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("complaintId"))) {
            errorList.add("投诉id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("complaintId")) && R.get("complaintId").length() > 19) {
            errorList.add("投诉id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("count"))) {
            errorList.add("内容不能为空");
        }
        if (StringUtils.isNotBlank(R.get("count")) && R.get("count").length() > 1024) {
            errorList.add("内容最大长度不能超过1024字符");
        }
        return errorList;
    }

}