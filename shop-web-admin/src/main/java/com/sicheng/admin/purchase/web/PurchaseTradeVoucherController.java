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

import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.entity.PurchaseTradeVoucher;
import com.sicheng.admin.purchase.service.PurchaseTradeVoucherService;

import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 采购交易凭证 Controller
 * 所属模块：purchase 
 * @author cl
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseTradeVoucher")
public class PurchaseTradeVoucherController extends BaseController {

	@Autowired
	private PurchaseTradeVoucherService purchaseTradeVoucherService;
	

	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030402";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseTradeVoucher 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeVoucher:view")
	@RequestMapping(value = "list")
	public String list(PurchaseTradeVoucher purchaseTradeVoucher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseTradeVoucher> page = purchaseTradeVoucherService.selectByWhere(new Page<PurchaseTradeVoucher>(request, response), new Wrapper(purchaseTradeVoucher)); 
		PurchaseTradeVoucher.fillUserMain(page.getList());
		model.addAttribute("page", page);
		model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
		return "admin/purchase/purchaseTradeVoucherList";
	}

	/**
	 * 进入审核页面
	 * @param purchaseTradeVoucher 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeVoucher:auth")
	@RequestMapping(value = "auth1")
	public String auth1(PurchaseTradeVoucher purchaseTradeVoucher, Model model) {
		PurchaseTradeVoucher entity = null;
		if(purchaseTradeVoucher!=null){
			if (purchaseTradeVoucher.getTradeVoucherId()!=null){
				entity = purchaseTradeVoucherService.selectById(purchaseTradeVoucher.getTradeVoucherId());
			}
		}
		model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
		model.addAttribute("purchaseTradeVoucher", entity);
		return "admin/purchase/purchaseTradeVoucherForm";
	}

	/**
	 * 执行审核
	 * @param purchaseTradeVoucher 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseTradeVoucher:auth")
	@RequestMapping(value = "auth2")
	public String auth2(PurchaseTradeVoucher purchaseTradeVoucher, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseTradeVoucher, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return auth1(purchaseTradeVoucher, model);//回显错误提示
		}		
		
		//业务处理
		String authResult=R.get("authResult");//审核结果:0.不通过、1.通过
		PurchaseTradeOrder purchaseTradeOrder=new PurchaseTradeOrder();//交易订单对象
		purchaseTradeOrder.setPurchaseTradeId(purchaseTradeVoucher.getPurchaseTradeId());//交易订单id
		Purchase purchase=purchaseTradeVoucher.getPurchaseTradeOrder().getPurchase();//采购单信息
		if("1".equals(authResult)){
			purchaseTradeVoucher.setStatus("30");//交易凭证审核状态：10.审核中20.审核未30.审核通过
			purchaseTradeOrder.setStatus("40");//交易订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成
			if(purchase!=null){
				purchase.setStatus("50");//采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
			}
		}else{
			purchaseTradeVoucher.setStatus("20");//交易凭证审核状态：10.审核中20.审核未30.审核通过
			purchaseTradeOrder.setStatus("30");//交易订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成
		}
		//更新交易凭证状态、交易订单状态、采购单状态
		purchaseTradeVoucherService.updatePurchaseTrade(purchaseTradeVoucher,purchaseTradeOrder,purchase);
		
		addMessage(redirectAttributes, FYUtils.fyParams("审核采购交易凭证成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeVoucher/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseTradeVoucher 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseTradeVoucher:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseTradeVoucher purchaseTradeVoucher, RedirectAttributes redirectAttributes) {
		purchaseTradeVoucherService.deleteById(purchaseTradeVoucher.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除采购交易凭证成功"));
		return "redirect:"+adminPath+"/purchase/purchaseTradeVoucher/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseTradeVoucher 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseTradeVoucher purchaseTradeVoucher, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("tradeVoucherId"))){
			errorList.add(FYUtils.fyParams("凭证交易号不能为空"));
		}
		if(StringUtils.isBlank(R.get("purchaseTradeId"))){
			errorList.add(FYUtils.fyParams("订单号不能为空"));
		}
		if(StringUtils.isBlank(R.get("auditGrounds"))){
			errorList.add(FYUtils.fyParams("审核理由不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("auditGrounds")) && R.get("auditGrounds").length() > 255){
			errorList.add(FYUtils.fyParams("审核理由最大长度不能超过255字符"));
		}
		return errorList;
	}

}