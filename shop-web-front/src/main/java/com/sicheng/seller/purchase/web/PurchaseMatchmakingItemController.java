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

import com.sicheng.admin.purchase.entity.PurchaseMatchmakingItem;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.purchase.service.PurchaseMatchmakingItemService;
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
 * <p>标题: 报价详情</p>
 * <p>描述: </p>
 * @author cailong
 * @date 2018年7月23日 下午3:44:16
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/matchmakingItem")
public class PurchaseMatchmakingItemController extends BaseController{
	
	@Autowired
	private PurchaseMatchmakingItemService purchaseMatchmakingItemService;
	
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
	 * 撮合采购详情列表
	 * 	从菜单的采购方报价管理，采购列表的查看报价，点击进去	sta=1
	 * 	从菜单的报价方报价管理点击进去	sta=2
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(PurchaseMatchmakingItem purchaseMatchmakingItem, Model model, HttpServletRequest request, HttpServletResponse response){
		String sta = R.get("sta");
		Page<PurchaseMatchmakingItem> page = purchaseMatchmakingItemService.selectByWhere(new Page<PurchaseMatchmakingItem>(request, response), new Wrapper(purchaseMatchmakingItem)); 
		PurchaseMatchmakingItem.fillPurchaseItem(page.getList());
		if("2".equals(sta)){
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			SellerMenuInterceptor.menuHighLighting("080601");
		}
		model.addAttribute("page", page);
		return "seller/purchase/purchaseMatchmakingItemList";
	}

}
