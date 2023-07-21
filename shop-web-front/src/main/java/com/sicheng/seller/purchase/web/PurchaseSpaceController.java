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

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.purchase.service.PurchaseSpaceService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 供采采购空间管理</p>
 * <p>描述: </p>
 * @author cailong
 * @date 2018年6月11日 上午10:29:47
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/space")
public class PurchaseSpaceController extends BaseController{
	
	@Autowired
	private PurchaseSpaceService purchaseSpaceService;
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="081001";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}	
	
	/**
	 * 进入采购空间页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save1")
	public String save1(PurchaseSpace purchaseSpace,Model model){
		if(purchaseSpace.getSpaceId()==null){
			UserMain userMain = SsoUtils.getUserMain();
			PurchaseSpace ps = new PurchaseSpace();
			ps.setUId(userMain.getUId());
			List<PurchaseSpace> purchaseSpaceList = purchaseSpaceService.selectByWhere(new Wrapper(ps));
			if(!purchaseSpaceList.isEmpty()){
				model.addAttribute("purchaseSpace",purchaseSpaceList.get(0));
			}
		}else{
			model.addAttribute("purchaseSpace",purchaseSpace);
		}
		return "seller/purchase/purchaseSpaceForm";
	}
	
	/**
	 * 进入采购空间页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save2")
	public String save2(PurchaseSpace purchaseSpace, Model model, RedirectAttributes redirectAttributes){
		//表单验证
		List<String> errorList=validate(purchaseSpace, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(purchaseSpace, model);
		}
		UserMain userMain = SsoUtils.getUserMain();
		purchaseSpace.setUId(userMain.getUId());
		purchaseSpaceService.updateByIdSelective(purchaseSpace);
		addMessage(redirectAttributes, FYUtils.fyParams("采购空间提交成功"));
		return "redirect:"+sellerPath+"/purchase/space/save1.htm?repage";
	}
	
	/**
	 * 表单验证
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseSpace purchaseSpace, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("logo"))){
			errorList.add(FYUtils.fyParams("采购空间logo不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("logo")) && R.get("logo").length() > 64){
			errorList.add(FYUtils.fyParams("采购空间logo最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("banner"))){
			errorList.add(FYUtils.fyParams("采购空间banner不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("banner")) && R.get("banner").length() > 64){
			errorList.add(FYUtils.fyParams("采购空间banner最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("name"))){
			errorList.add(FYUtils.fyParams("采购空间名称不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64){
			errorList.add(FYUtils.fyParams("采购空间名称最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("synopsis"))){
			errorList.add(FYUtils.fyParams("采购空间简介不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("synopsis")) && R.get("synopsis").length() > 255){
			errorList.add(FYUtils.fyParams("采购空间简介最大长度不能超过64字符"));
		}
		return errorList;
	}
}
