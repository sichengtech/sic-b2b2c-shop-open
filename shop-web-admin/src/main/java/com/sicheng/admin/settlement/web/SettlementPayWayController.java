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
package com.sicheng.admin.settlement.web;

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementPayWayAttr;
import com.sicheng.admin.settlement.service.SettlementPayWayService;

import com.sicheng.common.mapper.JsonMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 支付方式 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementPayWay")
public class SettlementPayWayController extends BaseController {

    @Autowired
    private SettlementPayWayService settlementPayWayService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030203";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param settlementPayWay 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:view")
    @RequestMapping(value = "list")
    public String list(SettlementPayWay settlementPayWay, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementPayWay> page = settlementPayWayService.selectByWhere(new Page<SettlementPayWay>(request, response), new Wrapper(settlementPayWay));
        model.addAttribute("page", page);
        return "admin/settlement/settlementPayWayList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementPayWay 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementPayWay settlementPayWay, Model model) {
        if (settlementPayWay == null) {
            settlementPayWay = new SettlementPayWay();
        }
        model.addAttribute("settlementPayWay", settlementPayWay);
        return "admin/settlement/settlementPayWayForm";
    }

    /**
     * 执行保存
     *
     * @param settlementPayWay   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementPayWay settlementPayWay, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPayWay, model);
        //支付方式属性验证
        errorList = validate2(errorList);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementPayWay, model);//回显错误提示
        }
        //业务处理
        String[] payWayKeys = R.getArray("payWayKey");//支付方式属性键
        String[] payWayValues = R.getArray("payWayValue");//支付方式属性值
        String[] payWayDescribes = R.getArray("payWayDescribe");//支付方式属性描述
        List<SettlementPayWayAttr> payWayAttrList = new ArrayList<>();
        for (int i = 0; i < payWayKeys.length; i++) {
            SettlementPayWayAttr payWayAttr = new SettlementPayWayAttr();
            payWayAttr.setPayWayKey(payWayKeys[i]);
            payWayAttr.setPayWayValue(payWayValues[i]);
            payWayAttr.setPayWayDescribe(payWayDescribes[i]);
            payWayAttrList.add(payWayAttr);
        }
        //执行保存
        settlementPayWayService.savePayWay(settlementPayWay, payWayAttrList);
        addMessage(redirectAttributes, FYUtils.fy("保存支付方式成功"));
        return "redirect:" + adminPath + "/settlement/settlementPayWay/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementPayWay 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementPayWay settlementPayWay, Model model) {
        SettlementPayWay entity = null;
        if (settlementPayWay != null) {
            if (settlementPayWay.getId() != null) {
                entity = settlementPayWayService.selectById(settlementPayWay.getId());
            }
        }
        model.addAttribute("settlementPayWay", entity);
        model.addAttribute("payWayAttrList", JsonMapper.getInstance().toJson(entity.getPayWayAttrList()));
        return "admin/settlement/settlementPayWayForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementPayWay   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementPayWay settlementPayWay, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPayWay, model);
        if (StringUtils.isBlank(R.get("payWayId"))) {
            errorList.add(FYUtils.fy("支付方式id不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19) {
            errorList.add(FYUtils.fy("支付方式id最大长度不能超过19字符"));
        }
        //支付方式属性验证
        errorList = validate2(errorList);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementPayWay, model);//回显错误提示
        }
        //业务处理
        String[] payWayKeys = R.getArray("payWayKey");//支付方式属性键
        String[] payWayValues = R.getArray("payWayValue");//支付方式属性值
        String[] payWayDescribes = R.getArray("payWayDescribe");//支付方式属性描述
        List<SettlementPayWayAttr> payWayAttrList = new ArrayList<>();
        for (int i = 0; i < payWayKeys.length; i++) {
            SettlementPayWayAttr payWayAttr = new SettlementPayWayAttr();
            payWayAttr.setPayWayKey(payWayKeys[i]);
            payWayAttr.setPayWayValue(payWayValues[i]);
            payWayAttr.setPayWayDescribe(payWayDescribes[i]);
            payWayAttrList.add(payWayAttr);
        }
        //执行编辑
        settlementPayWayService.savePayWay(settlementPayWay, payWayAttrList);
        addMessage(redirectAttributes, FYUtils.fy("编辑支付方式成功"));
        return "redirect:" + adminPath + "/settlement/settlementPayWay/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementPayWay   实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWay:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementPayWay settlementPayWay, RedirectAttributes redirectAttributes) {
        if (settlementPayWay == null || settlementPayWay.getPayWayId() == null) {
            addMessage(redirectAttributes, FYUtils.fy("支付方式id不能为空"));
            return "redirect:" + adminPath + "/settlement/settlementPayWay/list.do?repage";
        }
        settlementPayWayService.deletePayWay(settlementPayWay.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除支付方式成功"));
        return "redirect:" + adminPath + "/settlement/settlementPayWay/list.do?repage";
    }

    /**
     * 验证定时任务编号的唯一性
     *
     * @param oldNum       数据库的编号
     * @param timedTaskNum 输入的编号
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitNum")
    public String exitRoleName(String oldNum, String payWayNum) {
        if (StringUtils.isNotBlank(payWayNum)) {
            if (oldNum.equals(payWayNum)) {
                return "true";
            } else {
                SettlementPayWay payWay = new SettlementPayWay();
                payWay.setPayWayNum(payWayNum);
                List<SettlementPayWay> payWays = settlementPayWayService.selectByWhere(new Wrapper(payWay));
                if (payWays.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param settlementPayWay 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementPayWay settlementPayWay, Model model) {
    	List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("name"))){
			errorList.add("支付方式不能为空");
		}
		if(StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64){
			errorList.add("支付方式最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("payWayNum"))){
			errorList.add("支付方式编号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("payWayNum")) && R.get("payWayNum").length() > 64){
			errorList.add("支付方式编号最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("poundage"))){
			errorList.add("支付服务费用不能为空");
		}
		if(StringUtils.isNotBlank(R.get("poundage")) && R.get("poundage").length() > 11){
			errorList.add("支付服务费用最大长度不能超过11字符");
		}
		if(StringUtils.isBlank(R.get("useTerminal"))){
			errorList.add("使用终端不能为空");
		}
		if(StringUtils.isNotBlank(R.get("useTerminal")) && R.get("useTerminal").length() > 1){
			errorList.add("使用终端最大长度不能超过1字符");
		}
		if(StringUtils.isBlank(R.get("payWayLogo"))){
			errorList.add("支付方式logo不能为空");
		}
		if(StringUtils.isNotBlank(R.get("payWayLogo")) && R.get("payWayLogo").length() > 128){
			errorList.add("支付方式logo不能超过128字符");
		}
		if(StringUtils.isBlank(R.get("status"))){
			errorList.add("状态不能为空");
		}
		if(StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1){
			errorList.add("状态最大长度不能超过1字符");
		}
        return errorList;
    }

    /**
     * 验证支付方式属性
     *
     * @param errorList
     * @return
     */
    private List<String> validate2(List<String> errorList) {
        String[] payWayKeys = R.getArray("payWayKey");//支付方式属性键
        String[] payWayValues = R.getArray("payWayValue");//支付方式属性值
        String[] payWayDescribes = R.getArray("payWayDescribe");//支付方式属性描述
        //验证支付方式属性键
        if (payWayKeys != null && payWayKeys.length != 0) {
            for (int i = 0; i < payWayKeys.length; i++) {
                if (StringUtils.isBlank(payWayKeys[i])) {
                    errorList.add(FYUtils.fy("支付方式属性键不能为空"));
                    break;
                }
                if (payWayKeys[i].length() > 64) {
                    errorList.add(FYUtils.fy("支付方式属性键最大长度不能超过64字符"));
                    break;
                }
            }
            HashSet<String> hashSet = new HashSet<String>();
            for (int i = 0; i < payWayKeys.length; i++) {
                hashSet.add(payWayKeys[i]);
            }
            if (hashSet.size() != payWayKeys.length) {
                errorList.add(FYUtils.fy("支付方式属性键重复"));
            }
        }
        //验证支付方式属性值
        if (payWayValues != null && payWayValues.length != 0) {
            for (int i = 0; i < payWayValues.length; i++) {
                if (StringUtils.isBlank(payWayValues[i])) {
                    errorList.add(FYUtils.fy("支付方式属性值不能为空"));
                    break;
                }
                if (payWayValues[i].length() > 4000) {
                    errorList.add(FYUtils.fy("支付方式属性值不能超过4000字符"));
                    break;
                }
            }
        }
        //验证支付方式属性描述
        if (payWayDescribes != null && payWayDescribes.length != 0) {
            for (int i = 0; i < payWayDescribes.length; i++) {
                if (payWayDescribes[i].length() > 255) {
                    errorList.add(FYUtils.fy("支付方式属性描述不能超过255字符"));
                    break;
                }
            }
        }
        int payWayKeysLen = payWayKeys.length;
        int payWayValuesLen = payWayValues.length;
        int payWayDescribesLen = payWayDescribes.length;
        if (payWayKeysLen != payWayValuesLen || payWayKeysLen != payWayDescribesLen || payWayValuesLen != payWayDescribesLen) {
            errorList.add(FYUtils.fy("参数错误"));
        }
        return errorList;
    }

    public static void main(String[] args) {
        String[] array = {"wq", "ewe", "ewf", "wq"};
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < array.length; i++) {
            hashSet.add(array[i]);
        }
        if (hashSet.size() == array.length) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}