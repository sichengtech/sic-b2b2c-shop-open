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
package com.sicheng.admin.purchase.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.purchase.entity.PurchaseTradeOrderItem;
import com.sicheng.admin.purchase.service.PurchaseTradeOrderItemService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 撮合交易订单详情 Controller
 * 所属模块：purchase 
 * @author cl
 * @version 2018-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseTradeOrderItem")
public class PurchaseTradeOrderItemController extends BaseController {

	@Autowired
	private PurchaseTradeOrderItemService purchaseTradeOrderItemService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030401";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseTradeOrderItem 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrderItem:view")
	@RequestMapping(value = "list")
	public String list(PurchaseTradeOrderItem purchaseTradeOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseTradeOrderItem> page = purchaseTradeOrderItemService.selectByWhere(new Page<PurchaseTradeOrderItem>(request, response), new Wrapper(purchaseTradeOrderItem)); 
		model.addAttribute("page", page);
		return "admin/purchase/purchaseTradeOrderItemList";
	}

	/**
	 * 进入保存页面
	 * @param purchaseTradeOrderItem 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrderItem:save")
	@RequestMapping(value = "save1")
	public String save1(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model) {
		if (purchaseTradeOrderItem == null){
			purchaseTradeOrderItem = new PurchaseTradeOrderItem();
		}
		model.addAttribute("purchaseTradeOrderItem", purchaseTradeOrderItem);
		return "admin/purchase/purchaseTradeOrderItemForm";
	}

	/**
	 * 执行保存
	 * @param purchaseTradeOrderItem 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrderItem:save")
	@RequestMapping(value = "save2")
	public String save2(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseTradeOrderItem, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(purchaseTradeOrderItem, model);//回显错误提示
		}
		
		//业务处理
		purchaseTradeOrderItemService.insertSelective(purchaseTradeOrderItem);
		addMessage(redirectAttributes, FYUtils.fyParams("保存撮合交易订单详情成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeOrderItem/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseTradeOrderItem 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeOrderItem:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model) {
		PurchaseTradeOrderItem entity = null;
		if(purchaseTradeOrderItem!=null){
			if (purchaseTradeOrderItem.getId()!=null){
				entity = purchaseTradeOrderItemService.selectById(purchaseTradeOrderItem.getId());
			}
		}
		model.addAttribute("purchaseTradeOrderItem", entity);
		return "admin/purchase/purchaseTradeOrderItemForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseTradeOrderItem 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrderItem:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseTradeOrderItem, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(purchaseTradeOrderItem, model);//回显错误提示
		}		
		
		//业务处理
		purchaseTradeOrderItemService.updateByIdSelective(purchaseTradeOrderItem);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑撮合交易订单详情成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeOrderItem/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseTradeOrderItem 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeOrderItem:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseTradeOrderItem purchaseTradeOrderItem, RedirectAttributes redirectAttributes) {
		purchaseTradeOrderItemService.deleteById(purchaseTradeOrderItem.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除撮合交易订单详情成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeOrderItem/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseTradeOrderItem 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("ptoiId"))){
			errorList.add(FYUtils.fyParams("采购订单详情id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("ptoiId")) && R.get("ptoiId").length() > 19){
			errorList.add(FYUtils.fyParams("采购订单详情id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("ptoId"))){
			errorList.add(FYUtils.fyParams("采购订单id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("ptoId")) && R.get("ptoId").length() > 19){
			errorList.add(FYUtils.fyParams("采购订单id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("purchaseItemId"))){
			errorList.add(FYUtils.fyParams("采购单详情id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseItemId")) && R.get("purchaseItemId").length() > 19){
			errorList.add(FYUtils.fyParams("采购单详情id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("pmiId"))){
			errorList.add(FYUtils.fyParams("撮合采购详情id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("pmiId")) && R.get("pmiId").length() > 19){
			errorList.add(FYUtils.fyParams("撮合采购详情id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("name"))){
			errorList.add(FYUtils.fyParams("产品名称不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64){
			errorList.add(FYUtils.fyParams("产品名称最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("model"))){
			errorList.add(FYUtils.fyParams("规格型号不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("model")) && R.get("model").length() > 64){
			errorList.add(FYUtils.fyParams("规格型号最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("brand")) && R.get("brand").length() > 64){
			errorList.add(FYUtils.fyParams("品牌最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("amount"))){
			errorList.add(FYUtils.fyParams("数量不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("amount")) && R.get("amount").length() > 10){
			errorList.add(FYUtils.fyParams("数量最大长度不能超过10字符"));
		}
		if(StringUtils.isNotBlank(R.get("priceRequirement")) && R.get("priceRequirement").length() > 12){
			errorList.add(FYUtils.fyParams("采购单价最大长度不能超过12字符"));
		}
		if(StringUtils.isNotBlank(R.get("unit")) && R.get("unit").length() > 64){
			errorList.add(FYUtils.fyParams("单位最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseRemarks")) && R.get("purchaseRemarks").length() > 255){
			errorList.add(FYUtils.fyParams("采购备注最大长度不能超过255字符"));
		}
		if(StringUtils.isBlank(R.get("offerPrice"))){
			errorList.add(FYUtils.fyParams("报价单价不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("offerPrice")) && R.get("offerPrice").length() > 12){
			errorList.add(FYUtils.fyParams("报价单价最大长度不能超过12字符"));
		}
		if(StringUtils.isNotBlank(R.get("offerRemarks")) && R.get("offerRemarks").length() > 255){
			errorList.add(FYUtils.fyParams("报价备注最大长度不能超过255字符"));
		}
		return errorList;
	}

}