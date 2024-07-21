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
package com.sicheng.seller.purchase.web;

import com.sicheng.admin.purchase.entity.PurchaseTradeOrderItem;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.purchase.service.PurchaseTradeOrderItemService;
import com.sicheng.seller.store.service.StoreMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: 供采订单详情</p>
 * <p>描述: </p>
 * @author cl
 * @date 2018年7月23日 上午10:29:50
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/orderItem")
public class PurchaserTradeOrderItemController extends BaseController{
	
	@Autowired
	private PurchaseTradeOrderItemService purchaseTradeOrderItemService;
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="080701";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}	
	
	/**
	 * 进入订单详情列表
	 * 	orderSta=1	入口：采购方订单管理
	 * 	orderSta=2	入口：报价方订单管理
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String save1(PurchaseTradeOrderItem purchaseTradeOrderItem, Model model, HttpServletRequest request, HttpServletResponse response){
		String orderSta = R.get("orderSta");
		model.addAttribute("orderSta", orderSta);
		if("1".equals(orderSta)){
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			SellerMenuInterceptor.menuHighLighting("080701");
		}
		if("2".equals(orderSta)){
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			SellerMenuInterceptor.menuHighLighting("080801");
		}
		Page<PurchaseTradeOrderItem> page = purchaseTradeOrderItemService.selectByWhere(new Page<PurchaseTradeOrderItem>(request, response), new Wrapper(purchaseTradeOrderItem)); 
		model.addAttribute("page", page);
		return "seller/purchase/purchaseTradeOrderItemList";
	}

}
