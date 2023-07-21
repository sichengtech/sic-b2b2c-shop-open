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
package com.sicheng.admin.logistics.web;

import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.logistics.service.LogisticsCompanyService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.Pinyin4j;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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
import java.util.List;

/**
 * 管理快递的快递公司Controller
 * 所属模块：logistics
 *
 * @author zjl
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics/logisticsCompany")
public class LogisticsCompanyController extends BaseController {

    @Autowired
    private LogisticsCompanyService logisticsCompanyService;



    //菜单高亮
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020202";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param logisticsCompany 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:view")
    @RequestMapping(value = "list")
    public String list(LogisticsCompany logisticsCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<LogisticsCompany> page = logisticsCompanyService.selectByWhere(new Page<LogisticsCompany>(request, response), new Wrapper(logisticsCompany));
        model.addAttribute("page", page);
        return "admin/logistics/logisticsCompanyList";
    }

    /**
     * 进入保存页面
     *
     * @param logisticsCompany 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:save")
    @RequestMapping(value = "save1")
    public String save1(LogisticsCompany logisticsCompany, Model model) {
        if (logisticsCompany == null) {
            logisticsCompany = new LogisticsCompany();
        }
        model.addAttribute("logisticsCompany", logisticsCompany);
        return "admin/logistics/logisticsCompanyForm";
    }

    /**
     * 执行保存
     *
     * @param logisticsCompany   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:save")
    @RequestMapping(value = "save2")
    public String save2(LogisticsCompany logisticsCompany, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsCompany, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(logisticsCompany, model);//回显错误提示
        }
        //获取公司名称首字母
        Pinyin4j pinyin4j = new Pinyin4j();
        try {
            String largeArea = pinyin4j.toPinYinUppercaseInitials(R.get("companyName"));
            logisticsCompany.setLargeArea(largeArea.toUpperCase());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("转换快递名称拼音首字母出错"), e);
        }
        //业务处理
        logisticsCompanyService.insertSelective(logisticsCompany);
        addMessage(redirectAttributes, FYUtils.fy("保存快递公司成功"));
        return "redirect:" + adminPath + "/logistics/logisticsCompany/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param logisticsCompany 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:edit")
    @RequestMapping(value = "edit1")
    public String edit1(LogisticsCompany logisticsCompany, Model model) {
        LogisticsCompany entity = null;
        if (logisticsCompany != null) {
            if (logisticsCompany.getId() != null) {
                entity = logisticsCompanyService.selectById(logisticsCompany.getId());
            }
        }
        model.addAttribute("logisticsCompany", entity);
        return "admin/logistics/logisticsCompanyForm";
    }

    /**
     * 执行编辑
     *
     * @param logisticsCompany   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:edit")
    @RequestMapping(value = "edit2")
    public String edit2(LogisticsCompany logisticsCompany, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsCompany, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(logisticsCompany, model);//回显错误提示
        }
        //获取公司名称首字母
        Pinyin4j pinyin4j = new Pinyin4j();
        try {
            String largeArea = pinyin4j.toPinYinUppercaseInitials(R.get("companyName"));
            logisticsCompany.setLargeArea(largeArea.toUpperCase());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("转换快递名称拼音首字母出错"), e);
        }
        //业务处理
        logisticsCompanyService.updateByIdSelective(logisticsCompany);
        addMessage(redirectAttributes, FYUtils.fy("编辑快递公司成功"));
        return "redirect:" + adminPath + "/logistics/logisticsCompany/list.do?repage";
    }

    /**
     * ajax更新状态
     *
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:edit")
    @ResponseBody
    @RequestMapping(value = "updateStatus")
    public String updateStatus() {
        String info = "0";
        String status = R.get("status");//状态
        Long id = R.getLong("id");//快递公司id
        if (id != null) {
            if ("1".equals(status)) {
                status = "0";
            } else {
                status = "1";
            }
            LogisticsCompany lc = new LogisticsCompany();
            lc.setLcId(id);
            lc.setStatus(status);
            logisticsCompanyService.updateByIdSelective(lc);//根据id更新数据
            info = "1";
        }
        return info;
    }

    /**
     * ajax更新常用快递
     *
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:edit")
    @ResponseBody
    @RequestMapping(value = "updateIsCommonUse")
    public String updateIsCommonUse() {
        String info = "0";
        String isCommonUse = R.get("isCommonUse");//状态
        Long id = R.getLong("id");//快递公司id
        if (id != null) {
            if ("1".equals(isCommonUse)) {
                isCommonUse = "0";
            } else {
                isCommonUse = "1";
            }
            LogisticsCompany lc = new LogisticsCompany();
            lc.setLcId(id);
            lc.setIsCommonUse(isCommonUse);
            logisticsCompanyService.updateByIdSelective(lc);//根据id更新数据
            info = "1";
        }
        return info;
    }

    /**
     * 删除
     *
     * @param logisticsCompany   实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsCompany:drop")
    @RequestMapping(value = "delete")
    public String delete(LogisticsCompany logisticsCompany, RedirectAttributes redirectAttributes) {
        logisticsCompanyService.deleteById(logisticsCompany.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除快递公司成功"));
        return "redirect:" + adminPath + "/logistics/logisticsCompany/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param logisticsCompany 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(LogisticsCompany logisticsCompany, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("largeArea")) && R.get("largeArea").length() > 2) {
            errorList.add(FYUtils.fy("首字母最大长度不能超过2字符"));
        }
        if (StringUtils.isBlank(R.get("companyName"))) {
            errorList.add(FYUtils.fy("公司名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("companyName")) && R.get("companyName").length() > 64) {
            errorList.add(FYUtils.fy("公司名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("companyNumber")) && R.get("companyNumber").length() > 64) {
            errorList.add(FYUtils.fy("公司编号最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("companyurl")) && R.get("companyurl").length() > 64) {
            errorList.add(FYUtils.fy("公司网址最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add(FYUtils.fy("请选择状态"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fy("状态最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("isCommonUse"))) {
            errorList.add(FYUtils.fy("请选择是否是常用快递"));
        }
        if (StringUtils.isNotBlank(R.get("isCommonUse")) && R.get("isCommonUse").length() > 1) {
            errorList.add(FYUtils.fy("是否是常用快递最大长度不能超过1字符"));
        }
        return errorList;
    }
}