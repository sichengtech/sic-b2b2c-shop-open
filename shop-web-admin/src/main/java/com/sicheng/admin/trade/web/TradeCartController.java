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


import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.service.TradeCartService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeCart")
public class TradeCartController extends BaseController {

//    @Autowired
    @Resource
    private TradeCartService tradeCartService;


    @ModelAttribute
    public TradeCart get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "130";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return tradeCartService.selectById(id);
        } else {
            return new TradeCart();
        }
    }

    /**
     * 进入列表页
     *
     * @param tradeCart 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCart:view")
    @RequestMapping(value = "list")
    public String list(TradeCart tradeCart, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeCart> page = tradeCartService.selectByWhere(new Page<TradeCart>(request, response), new Wrapper(tradeCart));
        model.addAttribute("page", page);
        return "newviews:admin/trade/tradeCartList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeCart 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCart:edit")
    @RequestMapping(value = "save1")
    public String save1(TradeCart tradeCart, Model model) {
        if (tradeCart == null) {
            tradeCart = new TradeCart();
        }
        model.addAttribute("tradeCart", tradeCart);
        return "newviews:admin/trade/tradeCartForm";
    }

    /**
     * 执行保存
     *
     * @param tradeCart          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCart:edit")
    @RequestMapping(value = "save2")
    public String save2(TradeCart tradeCart, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeCart, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeCart, model);//回显错误提示
        }

        //业务处理
        tradeCartService.insertSelective(tradeCart);
        addMessage(redirectAttributes, "保存购物车成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCart/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeCart 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeCart:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeCart tradeCart, Model model) {
        TradeCart entity = null;
        if (tradeCart != null) {
            if (tradeCart.getId() != null) {
                entity = tradeCartService.selectById(tradeCart.getId());
            }
        }
        model.addAttribute("tradeCart", entity);
        return "newviews:admin/trade/tradeCartForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeCart          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCart:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeCart tradeCart, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeCart, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeCart, model);//回显错误提示
        }

        //业务处理
        tradeCartService.updateByIdSelective(tradeCart);
        addMessage(redirectAttributes, "编辑购物车成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCart/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeCart          实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeCart:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeCart tradeCart, RedirectAttributes redirectAttributes) {
        tradeCartService.deleteById(tradeCart.getId());
        addMessage(redirectAttributes, "删除购物车成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeCart/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeCart 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeCart tradeCart, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("产品id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("产品id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("skuId"))) {
            errorList.add("sku的id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("skuId")) && R.get("skuId").length() > 19) {
            errorList.add("sku的id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("skuValue")) && R.get("skuValue").length() > 128) {
            errorList.add("sku值最大长度不能超过128字符");
        }
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add("买家id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("买家id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("count"))) {
            errorList.add("数量不能为空");
        }
        if (StringUtils.isNotBlank(R.get("count")) && R.get("count").length() > 10) {
            errorList.add("数量最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("priceSum"))) {
            errorList.add("总价格不能为空");
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