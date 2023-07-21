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
package com.sicheng.seller.purchase.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.entity.PurchaseTradeVoucher;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.purchase.service.PurchaseTradeOrderService;
import com.sicheng.seller.purchase.service.PurchaseTradeVoucherService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 供采交易凭证管理</p>
 * <p>描述: </p>
 * @author cailong
 * @date 2018年6月11日 上午10:29:47
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/voucher")
public class PurchaserTradeVoucherController extends BaseController{
	
	@Autowired
	private PurchaseTradeOrderService purchaseTradeOrderService;

	@Lazy
	@Autowired
	private PurchaseTradeVoucherService purchaseTradeVoucherService;
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="080901";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}	
	
	/**
	 * 进入交易凭证列表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(PurchaseTradeVoucher purchaseTradeVoucher, Model model, HttpServletRequest request, HttpServletResponse response){
		UserMain userMain = SsoUtils.getUserMain();
		purchaseTradeVoucher.setUId(userMain.getUId());
		Page<PurchaseTradeVoucher> page = purchaseTradeVoucherService.selectByWhere(new Page<PurchaseTradeVoucher>(request, response), new Wrapper(purchaseTradeVoucher)); 
		PurchaseTradeVoucher.fillUserMain(page.getList());
		model.addAttribute("purchaseTradeVoucher", purchaseTradeVoucher);
		model.addAttribute("page", page);
		model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
		return "seller/purchase/purchaseTradeVoucherList";
	}
	
	/**
	 * 进入订单上传凭证页面
	 * 	orderSta=1	采购方订单管理
	 * 	orderSta=2	报价方订单管理 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save1")
	public String save1(PurchaseTradeVoucher purchaseTradeVoucher, Model model, HttpServletRequest request, HttpServletResponse response){
		String orderSta = R.get("orderSta");
		List<PurchaseTradeVoucher> purchaseTradeVoucherList = purchaseTradeVoucherService.selectByWhere(new Wrapper(purchaseTradeVoucher));
		if(!purchaseTradeVoucherList.isEmpty()){
			model.addAttribute("purchaseTradeVoucher", purchaseTradeVoucherList.get(0));
		}
		PurchaseTradeOrder purchaseTradeOrder = purchaseTradeOrderService.selectById(purchaseTradeVoucher.getPurchaseTradeId());
		model.addAttribute("orderSta", orderSta);
		model.addAttribute("purchaseTradeOrder", purchaseTradeOrder);
		model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
		return "seller/purchase/purchaseTradeVoucherForm";
	}
	
	/**
	 * 保存订单上传凭证页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save2")
	public String save2(PurchaseTradeVoucher purchaseTradeVoucher, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		String orderSta = R.get("orderSta");
		//表单验证
		List<String> errorList=validate(purchaseTradeVoucher, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return "redirect:"+sellerPath+"/purchase/order/list.htm?repage&orderSta="+orderSta;//回显错误提示
		}
		purchaseTradeVoucherService.uploadVoucher(purchaseTradeVoucher);
		addMessage(redirectAttributes, FYUtils.fyParams("交易凭证提交审核成功"));
		return "redirect:"+sellerPath+"/purchase/order/list.htm?repage&orderSta="+orderSta;
	}
	
	/**
	 * 表单验证
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseTradeVoucher purchaseTradeVoucher, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("type"))){
			errorList.add(FYUtils.fyParams("交易类型不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1){
			errorList.add(FYUtils.fyParams("交易类型最大长度不能超过1字符"));
		}
		if(StringUtils.isBlank(R.get("filePath"))){
			errorList.add(FYUtils.fyParams("凭证文件不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("filePath")) && R.get("filePath").length() > 64){
			errorList.add(FYUtils.fyParams("凭证文件最大长度不能超过64字符"));
		}
		return errorList;
	}
}
