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

import com.sicheng.admin.purchase.entity.PurchaseMatchmaking;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.purchase.service.PurchaseMatchmakingService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: 采购方报价管理，报价方报价管理</p>
 * <p>描述: </p>
 * @author cl
 * @date 2018年6月11日 上午10:29:47
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/matchmaking")
public class PurchaseMatchmakingController extends BaseController{
	
	@Autowired
	private PurchaseMatchmakingService purchaseMatchmakingService;
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="080501";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}	
	
	/**
	 * 撮合采购列表
	 * 	从菜单的采购方报价管理，采购列表的查看报价，点击进去	sta=1
	 * 	从菜单的报价方报价管理点击进去	sta=2
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(PurchaseMatchmaking purchaseMatchmaking, Model model, HttpServletRequest request, HttpServletResponse response){
		UserMain userMain = SsoUtils.getUserMain();
		String sta = R.get("sta");
		if("1".equals(sta)){
			purchaseMatchmaking.setPurchaseUId(userMain.getUId());
		}
		if("2".equals(sta)){
			purchaseMatchmaking.setOfferUId(userMain.getUId());
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			SellerMenuInterceptor.menuHighLighting("080601");
		}
		Page<PurchaseMatchmaking> page = purchaseMatchmakingService.selectByWhere(new Page<PurchaseMatchmaking>(request, response), new Wrapper(purchaseMatchmaking)); 
		PurchaseMatchmaking.fillPurchaseStoreEnter(page.getList());
		PurchaseMatchmaking.fillOfferStoreEnter(page.getList());
		PurchaseMatchmaking.fillPurchase(page.getList());
		model.addAttribute("sta",sta);
		model.addAttribute("purchaseMatchmaking", purchaseMatchmaking);
		model.addAttribute("page", page);
		if("1".equals(sta)){
			return "seller/purchase/purchaseMatchmakingList";
		}
		if("2".equals(sta)){
			return "seller/purchase/purchaseSupplyMatchmakingList";
		}
		return null;
	}
	
	/**
	 * 采购
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "purchaseOk")
	public String purchaseOk(PurchaseMatchmaking purchaseMatchmaking, RedirectAttributes redirectAttributes){
		purchaseMatchmakingService.purchaseOk(purchaseMatchmaking);
		addMessage(redirectAttributes, FYUtils.fyParams("采购成功"));
		return "redirect:"+sellerPath+"/purchase/order/list.htm?repage&orderSta=1";
	}

}
