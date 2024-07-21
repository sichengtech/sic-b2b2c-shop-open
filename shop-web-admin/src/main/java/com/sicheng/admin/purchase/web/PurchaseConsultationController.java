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

import com.sicheng.admin.purchase.entity.PurchaseConsultation;
import com.sicheng.admin.purchase.service.PurchaseConsultationService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购咨询 Controller
 * 所属模块：purchase 
 * @author fxx
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchaseConsultation")
public class PurchaseConsultationController extends BaseController {

	@Autowired
	private PurchaseConsultationService purchaseConsultationService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030306";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param purchaseConsultation 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseConsultation:view")
	@RequestMapping(value = "list")
	public String list(PurchaseConsultation purchaseConsultation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseConsultation> page = purchaseConsultationService.selectByWhere(new Page<PurchaseConsultation>(request, response), new Wrapper(purchaseConsultation)); 
		model.addAttribute("page", page);
		return "admin/purchase/purchaseConsultationList";
	}

	/**
	 * 进入保存页面
	 * @param purchaseConsultation 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseConsultation:save")
	@RequestMapping(value = "save1")
	public String save1(PurchaseConsultation purchaseConsultation, Model model) {
		if (purchaseConsultation == null){
			purchaseConsultation = new PurchaseConsultation();
		}
		model.addAttribute("purchaseConsultation", purchaseConsultation);
		return "admin/purchase/purchaseConsultationForm";
	}

	/**
	 * 执行保存
	 * @param purchaseConsultation 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseConsultation:save")
	@RequestMapping(value = "save2")
	public String save2(PurchaseConsultation purchaseConsultation, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseConsultation, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(purchaseConsultation, model);//回显错误提示
		}
		
		//业务处理
		purchaseConsultationService.insertSelective(purchaseConsultation);
		addMessage(redirectAttributes, "保存采购咨询成功");
		return "redirect:"+adminPath+"/purchase/purchaseConsultation/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param purchaseConsultation 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseConsultation:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PurchaseConsultation purchaseConsultation, Model model) {
		PurchaseConsultation entity = null;
		if(purchaseConsultation!=null){
			if (purchaseConsultation.getId()!=null){
				entity = purchaseConsultationService.selectById(purchaseConsultation.getId());
			}
		}
		model.addAttribute("purchaseConsultation", entity);
		return "admin/purchase/purchaseConsultationForm";
	}

	/**
	 * 执行编辑
	 * @param purchaseConsultation 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchaseConsultation:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PurchaseConsultation purchaseConsultation, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(purchaseConsultation, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(purchaseConsultation, model);//回显错误提示
		}		
		
		//业务处理
		purchaseConsultationService.updateByIdSelective(purchaseConsultation);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑采购咨询成功"));
		return "redirect:"+adminPath+"/purchase/purchaseConsultation/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchaseConsultation 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchaseConsultation:drop")
	@RequestMapping(value = "delete")
	public String delete(PurchaseConsultation purchaseConsultation, RedirectAttributes redirectAttributes) {
		purchaseConsultationService.deleteById(purchaseConsultation.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除采购咨询成功"));
		return "redirect:"+adminPath+"/purchase/purchaseConsultation/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param purchaseConsultation 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PurchaseConsultation purchaseConsultation, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("isContact"))){
			errorList.add(FYUtils.fyParams("是否联系不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("isContact")) && R.get("isContact").length() > 1){
			errorList.add(FYUtils.fyParams("是否联系最大长度不能超过1字符"));
		}
		return errorList;
	}

}