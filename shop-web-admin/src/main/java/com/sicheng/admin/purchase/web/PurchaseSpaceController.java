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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.purchase.service.PurchaseSpaceService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;

/**
 * 采购空间 Controller
 * 所属模块：purchase 
 * @author cl
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseSpace")
public class PurchaseSpaceController extends BaseController {

	@Autowired
	private PurchaseSpaceService purchaseSpaceService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030305";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseSpace 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseSpace:view")
	@RequestMapping(value = "list")
	public String list(PurchaseSpace purchaseSpace, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseSpace> page = purchaseSpaceService.selectByWhere(new Page<PurchaseSpace>(request, response), new Wrapper(purchaseSpace)); 
		PurchaseSpace.fillUserMain(page.getList());
		model.addAttribute("page", page);
		return "admin/purchase/purchaseSpaceList";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseSpace 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseSpace:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseSpace purchaseSpace, Model model) {
		PurchaseSpace entity = null;
		if(purchaseSpace!=null){
			if (purchaseSpace.getSpaceId()!=null){
				entity = purchaseSpaceService.selectById(purchaseSpace.getSpaceId());
			}
		}
		model.addAttribute("purchaseSpace", entity);
		return "admin/purchase/purchaseSpaceForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseSpace 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseSpace:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseSpace purchaseSpace, Model model, RedirectAttributes redirectAttributes) {
		//业务处理
		purchaseSpaceService.updateByIdSelective(purchaseSpace);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑采购空间成功"));
		return "redirect:"+adminPath+"/purchase/purchaseSpace/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseSpace 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseSpace:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseSpace purchaseSpace, RedirectAttributes redirectAttributes) {
		purchaseSpaceService.deleteById(purchaseSpace.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除采购空间成功"));
		return "redirect:"+adminPath+"/purchase/purchaseSpace/list.do?repage";
	}
}