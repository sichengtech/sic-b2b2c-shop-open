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

import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.service.PurchaseTradeOrderService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 撮合交易订单 Controller
 * 所属模块：purchase 
 * @author 蔡龙
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseTradeOrder")
public class PurchaseTradeOrderController extends BaseController {

	@Autowired
	private PurchaseTradeOrderService purchaseTradeOrderService;
	
	@Autowired
	private UserMainService userMainService;


	
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
	 * @param purchaseTradeOrder 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrder:view")
	@RequestMapping(value = "list")
	public String list(PurchaseTradeOrder purchaseTradeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		String purchaseName = R.get("purchaseName");//采购方用户名
		String offerName = R.get("offerName");//报价方用户名
		model.addAttribute("purchaseName", purchaseName);
		model.addAttribute("offerName", offerName);
		//查询采购方订单
		if(StringUtils.isNoneBlank(purchaseName)){
			UserMain entity1 = new UserMain();
			entity1.setLoginName(purchaseName.toLowerCase());//用户名转小写
			entity1 = userMainService.selectOne(new Wrapper(entity1));
			if(entity1==null){
				return "admin/purchase/purchaseTradeOrderList";
			}
			purchaseTradeOrder.setPurchaseId(entity1.getUId());
		}
		//查询报价方订单
		if(StringUtils.isNoneBlank(offerName)){
			UserMain entity2 = new UserMain();
			entity2.setLoginName(offerName.toLowerCase());//用户名转小写
			entity2 = userMainService.selectOne(new Wrapper(entity2));
			if(entity2==null){
				return "admin/purchase/purchaseTradeOrderList";
			}
			purchaseTradeOrder.setOfferUId(entity2.getUId());
		}
		Page<PurchaseTradeOrder> page = purchaseTradeOrderService.selectByWhere(new Page<PurchaseTradeOrder>(request, response), new Wrapper(purchaseTradeOrder)); 
		PurchaseTradeOrder.fillPurchaseUser(page.getList());//添加采购方信息
		PurchaseTradeOrder.fillOfferUser(page.getList());//添加报价方信息
		model.addAttribute("page", page);
		return "admin/purchase/purchaseTradeOrderList";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseTradeOrder 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeOrder:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseTradeOrder purchaseTradeOrder, Model model) {
		PurchaseTradeOrder entity = null;
		if(purchaseTradeOrder!=null){
			if (purchaseTradeOrder.getId()!=null){
				entity = purchaseTradeOrderService.selectById(purchaseTradeOrder.getId());
			}
		}
		model.addAttribute("purchaseTradeOrder", entity);
		return "admin/purchase/purchaseTradeOrderForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseTradeOrder 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeOrder:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseTradeOrder purchaseTradeOrder, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseTradeOrder, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(purchaseTradeOrder, model);//回显错误提示
		}		
		
		//业务处理
		purchaseTradeOrderService.updateByIdSelective(purchaseTradeOrder);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑撮合交易订单成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeOrder/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseTradeOrder 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeOrder:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseTradeOrder purchaseTradeOrder, RedirectAttributes redirectAttributes) {
		purchaseTradeOrderService.deleteById(purchaseTradeOrder.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除撮合交易订单成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeOrder/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseTradeOrder 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseTradeOrder purchaseTradeOrder, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("purchaseTradeId"))){
			errorList.add(FYUtils.fyParams("订单id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseTradeId")) && R.get("purchaseTradeId").length() > 19){
			errorList.add(FYUtils.fyParams("订单id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("purchaseUId"))){
			errorList.add(FYUtils.fyParams("采购方不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseUId")) && R.get("purchaseUId").length() > 19){
			errorList.add(FYUtils.fyParams("采购方最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("offerUId"))){
			errorList.add(FYUtils.fyParams("报价方不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("offerUId")) && R.get("offerUId").length() > 19){
			errorList.add(FYUtils.fyParams("报价方最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("purchaseId"))){
			errorList.add(FYUtils.fyParams("采购单id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseId")) && R.get("purchaseId").length() > 19){
			errorList.add(FYUtils.fyParams("采购单id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("purchaseItemId"))){
			errorList.add(FYUtils.fyParams("采购单详情id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseItemId")) && R.get("purchaseItemId").length() > 19){
			errorList.add(FYUtils.fyParams("采购单详情id最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("purchaseContent"))){
			errorList.add(FYUtils.fyParams("采购内容不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseContent")) && R.get("purchaseContent").length() > 2000){
			errorList.add(FYUtils.fyParams("采购内容最大长度不能超过2000字符"));
		}
		if(StringUtils.isBlank(R.get("offerContent"))){
			errorList.add(FYUtils.fyParams("报价内容不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("offerContent")) && R.get("offerContent").length() > 2000){
			errorList.add(FYUtils.fyParams("报价内容最大长度不能超过2000字符"));
		}
		if(StringUtils.isBlank(R.get("price"))){
			errorList.add(FYUtils.fyParams("总价格不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("price")) && R.get("price").length() > 12){
			errorList.add(FYUtils.fyParams("总价格最大长度不能超过12字符"));
		}
		if(StringUtils.isBlank(R.get("status"))){
			errorList.add(FYUtils.fyParams("订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2){
			errorList.add(FYUtils.fyParams("订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成最大长度不能超过2字符"));
		}
		return errorList;
	}

}