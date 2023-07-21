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


import com.sicheng.admin.trade.entity.TradeErrorPool;
import com.sicheng.admin.trade.service.TradeErrorPoolService;
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
 * 对账差错池 Controller
 * 所属模块：trade
 *
 * @author fanxiuxiu
 * @version 2018-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeErrorPool")
public class TradeErrorPoolController extends BaseController {

    @Autowired
    private TradeErrorPoolService tradeErrorPoolService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030107";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeErrorPool 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:view")
    @RequestMapping(value = "list")
    public String list(TradeErrorPool tradeErrorPool, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeErrorPool> page = tradeErrorPoolService.selectByWhere(new Page<TradeErrorPool>(request, response), new Wrapper(tradeErrorPool));
        model.addAttribute("page", page);
        return "admin/trade/tradeErrorPoolList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeErrorPool 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:save")
    @RequestMapping(value = "save1")
    public String save1(TradeErrorPool tradeErrorPool, Model model) {
        if (tradeErrorPool == null) {
            tradeErrorPool = new TradeErrorPool();
        }
        model.addAttribute("tradeErrorPool", tradeErrorPool);
        return "admin/trade/tradeErrorPoolForm";
    }

    /**
     * 执行保存
     *
     * @param tradeErrorPool     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:save")
    @RequestMapping(value = "save2")
    public String save2(TradeErrorPool tradeErrorPool, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeErrorPool, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeErrorPool, model);//回显错误提示
        }

        //业务处理
        tradeErrorPoolService.insertSelective(tradeErrorPool);
        addMessage(redirectAttributes, "保存对账差错池成功");
        return "redirect:" + adminPath + "/trade/tradeErrorPool/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeErrorPool 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeErrorPool tradeErrorPool, Model model) {
        TradeErrorPool entity = null;
        if (tradeErrorPool != null) {
            if (tradeErrorPool.getId() != null) {
                entity = tradeErrorPoolService.selectById(tradeErrorPool.getId());
            }
        }
        model.addAttribute("tradeErrorPool", entity);
        return "admin/trade/tradeErrorPoolForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeErrorPool     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeErrorPool tradeErrorPool, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeErrorPool, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeErrorPool, model);//回显错误提示
        }

        //业务处理
        tradeErrorPoolService.updateByIdSelective(tradeErrorPool);
        addMessage(redirectAttributes, FYUtils.fy("处理成功"));
        return "redirect:" + adminPath + "/trade/tradeErrorPool/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeErrorPool     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeErrorPool:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeErrorPool tradeErrorPool, RedirectAttributes redirectAttributes) {
        tradeErrorPoolService.deleteById(tradeErrorPool.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除对账差错池成功"));
        return "redirect:" + adminPath + "/trade/tradeErrorPool/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeErrorPool 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeErrorPool tradeErrorPool, Model model) {
        List<String> errorList = new ArrayList<String>();
		/*if(StringUtils.isBlank(R.get("epId"))){
			errorList.add("主键不能为空");
		}*/
		/*if(StringUtils.isNotBlank(R.get("epId")) && R.get("epId").length() > 19){
			errorList.add("主键最大长度不能超过19字符");
		}
		if(StringUtils.isBlank(R.get("orderId"))){
			errorList.add("shop商城的订单编号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 19){
			errorList.add("shop商城的订单编号最大长度不能超过19字符");
		}
		if(StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19){
			errorList.add("店铺id最大长度不能超过19字符");
		}
		if(StringUtils.isNotBlank(R.get("storeName")) && R.get("storeName").length() > 255){
			errorList.add("店铺名称最大长度不能超过255字符");
		}
		if(StringUtils.isBlank(R.get("transactionId"))){
			errorList.add("第三方平台支付交易号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("transactionId")) && R.get("transactionId").length() > 64){
			errorList.add("第三方平台支付交易号最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("billType"))){
			errorList.add("类型不能为空");
		}
		if(StringUtils.isNotBlank(R.get("billType")) && R.get("billType").length() > 1){
			errorList.add("类型最大长度不能超过1字符");
		}
		if(StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19){
			errorList.add("支付方式id最大长度不能超过19字符");
		}
		if(StringUtils.isNotBlank(R.get("payWayName")) && R.get("payWayName").length() > 64){
			errorList.add("支付方式名称最大长度不能超过64字符");
		}
		if(StringUtils.isNotBlank(R.get("orderStatus")) && R.get("orderStatus").length() > 2){
			errorList.add("shop商城的订单状态最大长度不能超过2字符");
		}
		if(StringUtils.isNotBlank(R.get("transactionStatus")) && R.get("transactionStatus").length() > 64){
			errorList.add("第三方平台订单状态最大长度不能超过64字符");
		}*/
        if (StringUtils.isBlank(R.get("handlestatus"))) {
            errorList.add(FYUtils.fy("处理状态不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("handlestatus")) && R.get("handlestatus").length() > 1) {
            errorList.add(FYUtils.fy("处理状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("handleremark")) && R.get("handleremark").length() > 256) {
            errorList.add(FYUtils.fy("处理备注最大长度不能超过256字符"));
        }
        return errorList;
    }

}