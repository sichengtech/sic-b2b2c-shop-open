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

import com.sicheng.admin.purchase.entity.PurchaseMatchmaking;
import com.sicheng.admin.purchase.service.PurchaseMatchmakingService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 撮合采购 Controller
 * 所属模块：purchase 
 * @author cl
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseMatchmaking")
public class PurchaseMatchmakingController extends BaseController {

	@Autowired
	private PurchaseMatchmakingService purchaseMatchmakingService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030304";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseMatchmaking 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseMatchmaking:view")
	@RequestMapping(value = "list")
	public String list(PurchaseMatchmaking purchaseMatchmaking, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseMatchmaking> page = purchaseMatchmakingService.selectByWhere(new Page<PurchaseMatchmaking>(request, response), new Wrapper(purchaseMatchmaking)); 
		PurchaseMatchmaking.fillPurchaseStoreEnter(page.getList());//采购商信息
		PurchaseMatchmaking.fillOfferStoreEnter(page.getList());//供应商信息
		model.addAttribute("page", page);
		return "admin/purchase/purchaseMatchmakingList";
	}

	/**
	 * 进入保存页面
	 * @param purchaseMatchmaking 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseMatchmaking:save")
	@RequestMapping(value = "save1")
	public String save1(PurchaseMatchmaking purchaseMatchmaking, Model model) {
		if (purchaseMatchmaking == null){
			purchaseMatchmaking = new PurchaseMatchmaking();
		}
		model.addAttribute("purchaseMatchmaking", purchaseMatchmaking);
		return "admin/purchase/purchaseMatchmakingForm";
	}

	/**
	 * 执行保存
	 * @param purchaseMatchmaking 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseMatchmaking:save")
	@RequestMapping(value = "save2")
	public String save2(PurchaseMatchmaking purchaseMatchmaking, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseMatchmaking, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(purchaseMatchmaking, model);//回显错误提示
		}
		
		//业务处理
		purchaseMatchmakingService.insertSelective(purchaseMatchmaking);
		addMessage(redirectAttributes, FYUtils.fyParams("保存撮合采购成功"));
		return "redirect:"+adminPath+"/purchase/purchaseMatchmaking/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseMatchmaking 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseMatchmaking:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseMatchmaking purchaseMatchmaking, Model model) {
		PurchaseMatchmaking entity = null;
		if(purchaseMatchmaking!=null){
			if (purchaseMatchmaking.getId()!=null){
				entity = purchaseMatchmakingService.selectById(purchaseMatchmaking.getId());
			}
		}
		model.addAttribute("purchaseMatchmaking", entity);
		return "admin/purchase/purchaseMatchmakingForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseMatchmaking 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseMatchmaking:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseMatchmaking purchaseMatchmaking, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseMatchmaking, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(purchaseMatchmaking, model);//回显错误提示
		}		
		
		//业务处理
		purchaseMatchmakingService.updateByIdSelective(purchaseMatchmaking);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑撮合采购成功"));
		return "redirect:"+adminPath+"/purchase/purchaseMatchmaking/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseMatchmaking 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseMatchmaking:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseMatchmaking purchaseMatchmaking, RedirectAttributes redirectAttributes) {
		purchaseMatchmakingService.deleteById(purchaseMatchmaking.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除撮合采购成功"));
		return "redirect:"+adminPath+"/purchase/purchaseMatchmaking/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseMatchmaking 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseMatchmaking purchaseMatchmaking, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("purchaseMatchmakingId"))){
			errorList.add(FYUtils.fyParams("撮合采购id不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseMatchmakingId")) && R.get("purchaseMatchmakingId").length() > 19){
			errorList.add(FYUtils.fyParams("撮合采购id最大长度不能超过19字符"));
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
		if(StringUtils.isNotBlank(R.get("purchaseTradeId")) && R.get("purchaseTradeId").length() > 19){
			errorList.add(FYUtils.fyParams("订单id(关联trade_order订单表)最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("content"))){
			errorList.add(FYUtils.fyParams("内容不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 2000){
			errorList.add(FYUtils.fyParams("内容最大长度不能超过2000字符"));
		}
		if(StringUtils.isBlank(R.get("uId"))){
			errorList.add(FYUtils.fyParams("会员id(供应方id)不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19){
			errorList.add(FYUtils.fyParams("会员id(供应方id)最大长度不能超过19字符"));
		}
		if(StringUtils.isBlank(R.get("status"))){
			errorList.add(FYUtils.fyParams("10待采购20已采购30放弃采购不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2){
			errorList.add(FYUtils.fyParams("10待采购20已采购30放弃采购最大长度不能超过2字符"));
		}
		return errorList;
	}

}