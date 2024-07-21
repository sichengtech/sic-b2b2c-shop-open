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


import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.service.TradeOrderItemService;
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
 * 订单详情 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeOrderItem")
public class TradeOrderItemController extends BaseController {

    @Autowired
    private TradeOrderItemService tradeOrderItemService;



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
     * @param tradeOrderItem 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:view")
    @RequestMapping(value = "list")
    public String list(TradeOrderItem tradeOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeOrderItem> page = tradeOrderItemService.selectByWhere(new Page<TradeOrderItem>(request, response), new Wrapper(tradeOrderItem));
        model.addAttribute("page", page);
        return "admin/trade/tradeOrderItemList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeOrderItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeOrderItem tradeOrderItem, Model model) {
        if (tradeOrderItem == null) {
            tradeOrderItem = new TradeOrderItem();
        }
        model.addAttribute("tradeOrderItem", tradeOrderItem);
        return "admin/trade/tradeOrderItemForm";
    }

    /**
     * 执行保存
     *
     * @param tradeOrderItem     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeOrderItem tradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeOrderItem, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeOrderItem, model);//回显错误提示
        }

        //业务处理
        tradeOrderItemService.insertSelective(tradeOrderItem);
        addMessage(redirectAttributes, "保存订单详情成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeOrderItem/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeOrderItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeOrderItem tradeOrderItem, Model model) {
        TradeOrderItem entity = null;
        if (tradeOrderItem != null) {
            if (tradeOrderItem.getId() != null) {
                entity = tradeOrderItemService.selectById(tradeOrderItem.getId());
            }
        }
        model.addAttribute("tradeOrderItem", entity);
        return "admin/trade/tradeOrderItemForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeOrderItem     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeOrderItem tradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeOrderItem, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeOrderItem, model);//回显错误提示
        }

        //业务处理
        tradeOrderItemService.updateByIdSelective(tradeOrderItem);
        addMessage(redirectAttributes, "编辑订单详情成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeOrderItem/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeOrderItem     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrderItem:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeOrderItem tradeOrderItem, RedirectAttributes redirectAttributes) {
        tradeOrderItemService.deleteById(tradeOrderItem.getId());
        addMessage(redirectAttributes, "删除订单详情成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeOrderItem/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeOrderItem 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeOrderItem tradeOrderItem, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("orderId"))) {
            errorList.add("订单id(订单表id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 19) {
            errorList.add("订单id(订单表id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("prodcutId"))) {
            errorList.add("商品id(商品表id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品id(商品表id)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("thumbnailPath")) && R.get("thumbnailPath").length() > 64) {
            errorList.add("商品缩略图(图片表id)最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("skuId")) && R.get("skuId").length() > 19) {
            errorList.add("商品sku的id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("skuValue")) && R.get("skuValue").length() > 128) {
            errorList.add("sku规格值(快照)最大长度不能超过128字符");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("商品名称(快照)最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("quantity"))) {
            errorList.add("数量不能为空");
        }
        if (StringUtils.isNotBlank(R.get("quantity")) && R.get("quantity").length() > 10) {
            errorList.add("数量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("category")) && R.get("category").length() > 64) {
            errorList.add("商品分类(平台)(快照)最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("storeCategory")) && R.get("storeCategory").length() > 64) {
            errorList.add("商品分类(本店)(快照)最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("brand")) && R.get("brand").length() > 64) {
            errorList.add("品牌(快照)最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("销售类型，1零售型、2批发型(快照)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("productType"))) {
            errorList.add("商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("productType")) && R.get("productType").length() > 1) {
            errorList.add("商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("score")) && R.get("score").length() > 10) {
            errorList.add("所用积分最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("weight")) && R.get("weight").length() > 64) {
            errorList.add("商品重量(快照)最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isAdditionalComment"))) {
            errorList.add("是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isAdditionalComment")) && R.get("isAdditionalComment").length() > 1) {
            errorList.add("是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)最大长度不能超过1字符");
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