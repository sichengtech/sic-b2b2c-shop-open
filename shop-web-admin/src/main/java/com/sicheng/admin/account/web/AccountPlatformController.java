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
package com.sicheng.admin.account.web;

import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.admin.account.service.AccountPlatformService;
import com.sicheng.admin.account.service.AccountWithdrawalsService;
import com.sicheng.admin.sys.service.MenuService;
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
 * 平台账户 Controller
 * 所属模块：account 
 * @author zhaolei
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountPlatform")
public class AccountPlatformController extends BaseController {

	@Autowired
	private AccountPlatformService accountPlatformService;
	
	@Autowired
	private AccountWithdrawalsService accountWithdrawalsService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060101";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param accountPlatform 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountPlatform:view")
	@RequestMapping(value = "list")
	public String list(AccountPlatform accountPlatform, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountPlatform> page = accountPlatformService.selectByWhere(new Page<AccountPlatform>(request, response), new Wrapper(accountPlatform).orderBy("a.ap_id ASC")); 
		model.addAttribute("page", page);
		return "admin/account/accountPlatformList";
	}

	/**
	 * 进入保存页面
	 * @param accountPlatform 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountPlatform:save")
	@RequestMapping(value = "save1")
	public String save1(AccountPlatform accountPlatform, Model model) {
		if (accountPlatform == null){
			accountPlatform = new AccountPlatform();
		}
		model.addAttribute("accountPlatform", accountPlatform);
		return "admin/account/accountPlatformForm";
	}

	/**
	 * 执行保存
	 * @param accountPlatform 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountPlatform:save")
	@RequestMapping(value = "save2")
	public String save2(AccountPlatform accountPlatform, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountPlatform, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountPlatform, model);//回显错误提示
		}
		
		//业务处理
		accountPlatformService.insertSelective(accountPlatform);
		addMessage(redirectAttributes, FYUtils.fyParams("保存平台账户成功"));
		return "redirect:"+adminPath+"/account/accountPlatform/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountPlatform 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatform:edit")
	@RequestMapping(value = "edit1")
	public String edit1(AccountPlatform accountPlatform, Model model) {
		AccountPlatform entity = null;
		if(accountPlatform!=null){
			if (accountPlatform.getId()!=null){
				entity = accountPlatformService.selectById(accountPlatform.getId());
			}
		}
		model.addAttribute("accountPlatform", entity);
		return "admin/account/accountPlatformForm";
	}

	/**
	 * 执行编辑
	 * @param accountPlatform 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountPlatform:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountPlatform accountPlatform, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountPlatform, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountPlatform, model);//回显错误提示
		}		
		
		//业务处理
		accountPlatformService.updateByIdSelective(accountPlatform);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑平台账户成功"));
		return "redirect:"+adminPath+"/account/accountPlatform/list.do?repage";
	}	

	/**
	 * 删除
	 * @param accountPlatform 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatform:drop")
	@RequestMapping(value = "delete")
	public String delete(AccountPlatform accountPlatform, RedirectAttributes redirectAttributes) {
		accountPlatformService.deleteById(accountPlatform.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除平台账户成功"));
		return "redirect:"+adminPath+"/account/accountPlatform/list.do?repage";
	}
	
	/**
	 * 进入提现表单
	 * @param accountPlatform 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatform:edit")
	@RequestMapping(value = "withdrawal1")
	public String withdrawal1(AccountPlatform accountPlatform, Model model) {
		return "admin/account/accountPlatformWithdrawal";
	}
	
	/**
	 * 保存提现
	 * @param accountWithdrawals 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatform:edit")
	@RequestMapping(value = "withdrawal2")
	public String withdrawal2(AccountWithdrawals accountWithdrawals, RedirectAttributes redirectAttributes) {
		String tips = null;
		try {
			tips = accountWithdrawalsService.withdrawal(accountWithdrawals);
			addMessage(redirectAttributes, tips);
			return "redirect:"+adminPath+"/account/accountPlatform/list.do?repage";
		} catch (Exception e) {
			logger.error("申请提现失败:",e.getMessage());
			addMessage(redirectAttributes, FYUtils.fyParams("申请提现失败"));
			return "redirect:"+adminPath+"/account/accountPlatform/withdrawal1.do";
		}
		
	}

	/**
	 * 表单验证
	 * @param accountPlatform 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountPlatform accountPlatform, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("accountType"))){
			errorList.add(FYUtils.fyParams("账户类型不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("accountType")) && R.get("accountType").length() > 10){
			errorList.add(FYUtils.fyParams("账户类型最大长度不能超过10字符"));
		}
		if(StringUtils.isBlank(R.get("ownAmount"))){
			errorList.add(FYUtils.fyParams("账户余额不能为空"));
		}
		if(StringUtils.isBlank(R.get("frozenMoney"))){
			errorList.add(FYUtils.fyParams("冻结金额不能为空"));
		}
		return errorList;
	}

}