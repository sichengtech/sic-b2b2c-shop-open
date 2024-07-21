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

import com.sicheng.admin.account.entity.SettlementTaskMain;
import com.sicheng.admin.account.service.SettlementTaskMainService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
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
import java.util.Calendar;
import java.util.List;

/**
 * 结算主任务 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementTaskMain")
public class SettlementTaskMainController extends BaseController {

    @Autowired
    private SettlementTaskMainService settlementTaskMainService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030206";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param settlementTaskMain 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskMain:view")
    @RequestMapping(value = "list")
    public String list(SettlementTaskMain settlementTaskMain, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementTaskMain> page = settlementTaskMainService.selectByWhere(new Page<SettlementTaskMain>(request, response), new Wrapper(settlementTaskMain));
        Calendar now = Calendar.getInstance();
        model.addAttribute("page", page);
        model.addAttribute("year", now.get(Calendar.YEAR));
        return "admin/settlement/settlementTaskMainList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementTaskMain 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskMain:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementTaskMain settlementTaskMain, Model model) {
        if (settlementTaskMain == null) {
            settlementTaskMain = new SettlementTaskMain();
        }
        Calendar now = Calendar.getInstance();
        model.addAttribute("year", now.get(Calendar.YEAR));
        model.addAttribute("settlementTaskMain", settlementTaskMain);
        return "admin/settlement/settlementTaskMainForm";
    }

    /**
     * 执行保存
     *
     * @param settlementTaskMain 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskMain:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementTaskMain settlementTaskMain, Model model, RedirectAttributes redirectAttributes) {
        String name=DateUtils.formatDate(settlementTaskMain.getBeginTime(),"yyyy-MM-dd");
        boolean check=checkTaskName(name);
        if(check){
            addMessage(model,"任务已存在,请选择其他年月");
            return save1(settlementTaskMain, model);//回显错误提示
        }
        if(settlementTaskMain.getBeginTime()==null){
            addMessage(model,"请选择任务日期");
            return save1(settlementTaskMain, model);//回显错误提示
        }
        settlementTaskMain.setName(name);
        // 状态(1未运行,2运行中,3已完成)
        settlementTaskMain.setStatus("1");
        //业务处理
        settlementTaskMainService.insertSelective(settlementTaskMain);
        addMessage(redirectAttributes, "保存结算主任务成功");
        return "redirect:"+adminPath+"/settlement/settlementTaskMain/list.do?repage";
    }

    //运行
    @RequiresPermissions("settlement:settlementTaskMain:edit")
    @RequestMapping(value = "runTaskSub")
    public String runTaskSub(SettlementTaskMain settlementTaskMain, RedirectAttributes redirectAttributes) {
        settlementTaskMainService.runTaskSub(settlementTaskMain);
        addMessage(redirectAttributes, FYUtils.fy("运行成功"));
        return "redirect:" + adminPath + "/settlement/settlementTaskMain/list.do?repage";
    }

    //重算
    @RequiresPermissions("settlement:settlementTaskMain:edit")
    @RequestMapping(value = "retryTaskSub")
    public String retryTaskSub(SettlementTaskMain settlementTaskMain, RedirectAttributes redirectAttributes) {
        settlementTaskMainService.retryTaskSub(settlementTaskMain);
        addMessage(redirectAttributes, FYUtils.fy("重算成功"));
        return "redirect:" + adminPath + "/settlement/settlementTaskMain/list.do?repage";
    }

    //补算
    @RequiresPermissions("settlement:settlementTaskMain:edit")
    @RequestMapping(value = "supplementTaskSub")
    public String supplementTaskSub(SettlementTaskMain settlementTaskMain, RedirectAttributes redirectAttributes) {
        settlementTaskMainService.supplementTaskSub(settlementTaskMain);
        addMessage(redirectAttributes, FYUtils.fy("补算成功"));
        return "redirect:" + adminPath + "/settlement/settlementTaskMain/list.do?repage";
    }

    /**
     * 验证任务名称是否有效
     * @param name
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkTaskName")
    public boolean checkTaskName(String name) {
        if (name!=null) {
            List<SettlementTaskMain> tasks=settlementTaskMainService.selectByWhere(new Wrapper().and("name =",name));
            if(tasks.size()==0){
                return false;
            }
        }
        return true;
    }

}