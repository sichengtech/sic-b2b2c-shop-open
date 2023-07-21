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
package com.sicheng.admin.account.web;

import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.admin.account.service.AccountWithdrawalsService;

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
 * 账户提现 Controller
 * 所属模块：account 
 * @author 蔡龙
 * @version 2018-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountWithdrawals")
public class AccountWithdrawalsController extends BaseController {

	@Autowired
	private AccountWithdrawalsService accountWithdrawalsService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060107";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param accountWithdrawals 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountWithdrawals:view")
	@RequestMapping(value = "list")
	public String list(AccountWithdrawals accountWithdrawals, HttpServletRequest request, HttpServletResponse response, Model model) {
		String status = R.get("status");//1会员2平台
		if("2".equals(status)){
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("060108");

		}
		accountWithdrawals.setStatus(status);
		Page<AccountWithdrawals> page = accountWithdrawalsService.selectByWhere(new Page<AccountWithdrawals>(request, response), new Wrapper(accountWithdrawals)); 
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		return "admin/account/accountWithdrawalsList";
	}
	
	/**
	 * 审核提现单
	 * @param accountWithdrawals
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountWithdrawals:auth")
	@RequestMapping(value = "auth1")
	public String auth1(AccountWithdrawals accountWithdrawals, Model model) {
		String status = R.get("status");//1会员2平台
		if("2".equals(status)){
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("060108");
		}
		AccountWithdrawals entity = null;
		if(accountWithdrawals!=null){
			if (accountWithdrawals.getId()!=null){
				entity = accountWithdrawalsService.selectById(accountWithdrawals.getId());
			}
		}
		model.addAttribute("status", status);
		model.addAttribute("accountWithdrawals", entity);
		return "admin/account/accountWithdrawalsAuth";
	}
	
	/**
	 * 执行保存审核
	 * @param accountWithdrawals 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountWithdrawals:auth")
	@RequestMapping(value = "auth2")
	public String auth2(AccountWithdrawals accountWithdrawals, Model model, RedirectAttributes redirectAttributes) {
		String status = R.get("status");//1会员2平台
		//表单验证
		List<String> errorList=validate(accountWithdrawals, model);
		if(errorList.size()>0){
			addMessage(redirectAttributes, errorList.toArray(new String[]{}));
			return "redirect:"+adminPath+"/account/accountWithdrawals/auth1.do?repage&status="+status;
		}		
		//业务处理
		String tips = null;
		try {
			tips = accountWithdrawalsService.auth(accountWithdrawals);
			addMessage(redirectAttributes, tips);
			return "redirect:"+adminPath+"/account/accountWithdrawals/list.do?repage&status="+status;
		} catch (Exception e) {
			logger.error("审核提现失败：",e.getMessage());
			addMessage(redirectAttributes, FYUtils.fyParams("审核失败"));
			return "redirect:"+adminPath+"/account/accountWithdrawals/list.do?repage&status="+status;
		}
		
	}	
	
	/**
	 * 支付提现单
	 * @param accountWithdrawals
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountWithdrawals:edit")
	@RequestMapping(value = "pay")
	public String pay(AccountWithdrawals accountWithdrawals, Model model, RedirectAttributes redirectAttributes) {
		String status = R.get("status");//1会员2平台
		String tips = null;
		//业务处理
		try {
			tips = accountWithdrawalsService.pay(status,accountWithdrawals);
			addMessage(redirectAttributes, tips);
			return "redirect:"+adminPath+"/account/accountWithdrawals/list.do?repage&status="+status;
		} catch (Exception e) {
			logger.error(FYUtils.fyParams("审核提现失败："),e.getMessage());
			addMessage(redirectAttributes, FYUtils.fyParams("支付失败"));
			return "redirect:"+adminPath+"/account/accountWithdrawals/list.do?repage&status="+status;
		}
	}
	
	/**
	 * 表单验证
	 * @param accountWithdrawals 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountWithdrawals accountWithdrawals, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("auditOpinion"))){
			errorList.add(FYUtils.fyParams("审核理由不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("auditOpinion")) && R.get("auditOpinion").length() > 255){
			errorList.add(FYUtils.fyParams("审核理由最大长度不能超过255字符"));
		}
		return errorList;
	}

}