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

import com.sicheng.admin.purchase.entity.PurchaseItem;
import com.sicheng.admin.purchase.service.PurchaseItemService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 采购单明细 Controller
 * 所属模块：purchase 
 * @author 蔡龙
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseItem")
public class PurchaseItemController extends BaseController {

	@Autowired
	private PurchaseItemService purchaseItemService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030301";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseItem 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseItem:view")
	@RequestMapping(value = "list")
	public String list(PurchaseItem purchaseItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseItem> page = purchaseItemService.selectByWhere(new Page<PurchaseItem>(request, response), new Wrapper(purchaseItem)); 
		model.addAttribute("page", page);
		return "admin/purchase/purchaseItemList";
	}

	/**
	 * 进入保存页面
	 * @param purchaseItem 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseItem:save")
	@RequestMapping(value = "save1")
	public String save1(PurchaseItem purchaseItem, Model model) {
		if (purchaseItem == null){
			purchaseItem = new PurchaseItem();
		}
		model.addAttribute("purchaseItem", purchaseItem);
		return "admin/purchase/purchaseItemForm";
	}

	/**
	 * 执行保存
	 * @param purchaseItem 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseItem:save")
	@RequestMapping(value = "save2")
	public String save2(PurchaseItem purchaseItem, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseItem, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(purchaseItem, model);//回显错误提示
		}
		
		//业务处理
		purchaseItemService.insertSelective(purchaseItem);
		addMessage(redirectAttributes, FYUtils.fyParams("保存采购单明细成功"));
		return "redirect:"+adminPath+"/purchase/purchaseItem/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseItem 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseItem:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseItem purchaseItem, Model model) {
		PurchaseItem entity = null;
		if(purchaseItem!=null){
			if (purchaseItem.getId()!=null){
				entity = purchaseItemService.selectById(purchaseItem.getId());
			}
		}
		model.addAttribute("purchaseItem", entity);
		return "admin/purchase/purchaseItemForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseItem 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseItem:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseItem purchaseItem, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseItem, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(purchaseItem, model);//回显错误提示
		}		
		
		//业务处理
		purchaseItemService.updateByIdSelective(purchaseItem);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑采购单明细成功"));
		return "redirect:"+adminPath+"/purchase/purchaseItem/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseItem 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseItem:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseItem purchaseItem, RedirectAttributes redirectAttributes) {
		purchaseItemService.deleteById(purchaseItem.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除采购单明细成功"));
		return "redirect:"+adminPath+"/purchase/purchaseItem/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseItem 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseItem purchaseItem, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("purchaseItemId"))){
			errorList.add(FYUtils.fyParams("采购详情id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseItemId")) && R.get("purchaseItemId").length() > 19){
			errorList.add(FYUtils.fyParams("采购详情id最大长度不能超过19字符"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseId")) && R.get("purchaseId").length() > 19){
			errorList.add(FYUtils.fyParams("采购单id(关联purchaes_order(采购单表))最大长度不能超过19字符"));
		}
		if(StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19){
			errorList.add(FYUtils.fyParams("会员ID最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("name"))){
			errorList.add(FYUtils.fyParams("产品名称不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64){
			errorList.add(FYUtils.fyParams("产品名称最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("model"))){
			errorList.add(FYUtils.fyParams("产品型号不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("model")) && R.get("model").length() > 64){
			errorList.add(FYUtils.fyParams("产品型号最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("brand"))){
			errorList.add(FYUtils.fyParams("产品品牌不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("brand")) && R.get("brand").length() > 64){
			errorList.add(FYUtils.fyParams("产品品牌最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("encapsulation")) && R.get("encapsulation").length() > 64){
			errorList.add(FYUtils.fyParams("产品封装最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("amount"))){
			errorList.add(FYUtils.fyParams("产品数量不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("amount")) && R.get("amount").length() > 10){
			errorList.add(FYUtils.fyParams("产品数量最大长度不能超过10字符"));
		}
		if(StringUtils.isNotBlank(R.get("cycle")) && R.get("cycle").length() > 64){
			errorList.add(FYUtils.fyParams("交货期限最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("priceRequirement"))){
			errorList.add(FYUtils.fyParams("价格要求不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("priceRequirement")) && R.get("priceRequirement").length() > 12){
			errorList.add(FYUtils.fyParams("价格要求最大长度不能超过12字符"));
		}
		if(StringUtils.isNotBlank(R.get("area")) && R.get("area").length() > 64){
			errorList.add(FYUtils.fyParams("地区最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("batchNumber")) && R.get("batchNumber").length() > 64){
			errorList.add(FYUtils.fyParams("批号最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("unit")) && R.get("unit").length() > 64){
			errorList.add(FYUtils.fyParams("单位最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("status"))){
			errorList.add(FYUtils.fyParams("采购状态10.交易中，20.完成交易，30.取消不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2){
			errorList.add(FYUtils.fyParams("采购状态10.交易中，20.完成交易，30.取消最大长度不能超过2字符"));
		}
		return errorList;
	}

}