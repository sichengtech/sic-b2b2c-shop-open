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

import com.sicheng.admin.account.entity.AccountPlatformSn;
import com.sicheng.admin.account.service.AccountPlatformSnService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
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
import com.sicheng.admin.sys.service.MenuService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台账户流水 Controller
 * 所属模块：account 
 * @author zhaolei
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountPlatformSn")
public class AccountPlatformSnController extends BaseController {

	@Autowired
	private AccountPlatformSnService accountPlatformSnService;


	
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
	 * @param accountPlatformSn 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountPlatformSn:view")
	@RequestMapping(value = "list")
	public String list(AccountPlatformSn accountPlatformSn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountPlatformSn> page = accountPlatformSnService.selectByWhere(new Page<AccountPlatformSn>(request, response), new Wrapper(accountPlatformSn)); 
		model.addAttribute("page", page);
		if(accountPlatformSn.getApId()==13L){
			String menu3="060110";//请修改为正确的三级菜单编号
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting(menu3);
			return "admin/account/accountPoundageList";
		}
		return "admin/account/accountPlatformSnList";
	}

	/**
	 * 进入保存页面
	 * @param accountPlatformSn 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountPlatformSn:save")
	@RequestMapping(value = "save1")
	public String save1(AccountPlatformSn accountPlatformSn, Model model) {
		if (accountPlatformSn == null){
			accountPlatformSn = new AccountPlatformSn();
		}
		model.addAttribute("accountPlatformSn", accountPlatformSn);
		return "admin/account/accountPlatformSnForm";
	}

	/**
	 * 执行保存
	 * @param accountPlatformSn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountPlatformSn:save")
	@RequestMapping(value = "save2")
	public String save2(AccountPlatformSn accountPlatformSn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountPlatformSn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountPlatformSn, model);//回显错误提示
		}
		
		//业务处理
		accountPlatformSnService.insertSelective(accountPlatformSn);
		addMessage(redirectAttributes, "保存平台账户流水成功");
		return "redirect:"+adminPath+"/account/accountPlatformSn/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountPlatformSn 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatformSn:edit")
	@RequestMapping(value = "edit1")
	public String edit1(AccountPlatformSn accountPlatformSn, Model model) {
		AccountPlatformSn entity = null;
		if(accountPlatformSn!=null){
			if (accountPlatformSn.getId()!=null){
				entity = accountPlatformSnService.selectById(accountPlatformSn.getId());
			}
		}
		model.addAttribute("accountPlatformSn", entity);
		return "admin/account/accountPlatformSnForm";
	}

	/**
	 * 执行编辑
	 * @param accountPlatformSn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountPlatformSn:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountPlatformSn accountPlatformSn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountPlatformSn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountPlatformSn, model);//回显错误提示
		}		
		
		//业务处理
		accountPlatformSnService.updateByIdSelective(accountPlatformSn);
		addMessage(redirectAttributes, "编辑平台账户流水成功");
		return "redirect:"+adminPath+"/account/accountPlatformSn/list.do?repage";
	}	

	/**
	 * 删除
	 * @param accountPlatformSn 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountPlatformSn:drop")
	@RequestMapping(value = "delete")
	public String delete(AccountPlatformSn accountPlatformSn, RedirectAttributes redirectAttributes) {
		accountPlatformSnService.deleteById(accountPlatformSn.getId());
		addMessage(redirectAttributes, "删除平台账户流水成功");
		return "redirect:"+adminPath+"/account/accountPlatformSn/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param accountPlatformSn 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountPlatformSn accountPlatformSn, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("apId"))){
			errorList.add("平台账户ID不能为空");
		}
		if(StringUtils.isNotBlank(R.get("apId")) && R.get("apId").length() > 19){
			errorList.add("平台账户ID最大长度不能超过19字符");
		}
		if(StringUtils.isBlank(R.get("serialNumber"))){
			errorList.add("流水号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("serialNumber")) && R.get("serialNumber").length() > 64){
			errorList.add("流水号最大长度不能超过64字符");
		}
		if(StringUtils.isNotBlank(R.get("payRemarks")) && R.get("payRemarks").length() > 255){
			errorList.add("备注最大长度不能超过255字符");
		}
		if(StringUtils.isBlank(R.get("incomeMoney"))){
			errorList.add("收入金额不能为空");
		}
		if(StringUtils.isBlank(R.get("expensesMoney"))){
			errorList.add("支出金额不能为空");
		}
		return errorList;
	}

}