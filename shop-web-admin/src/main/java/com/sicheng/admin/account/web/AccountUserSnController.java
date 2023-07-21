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

import com.sicheng.admin.account.entity.AccountUserSn;
import com.sicheng.admin.account.service.AccountUserSnService;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员账户流水 Controller
 * 所属模块：account 
 * @author 赵磊
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountUserSn")
public class AccountUserSnController extends BaseController {

	@Autowired
	private AccountUserSnService accountUserSnService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060102";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param accountUserSn 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountUserSn:view")
	@RequestMapping(value = "list")
	public String list(AccountUserSn accountUserSn, HttpServletRequest request, HttpServletResponse response, Model model) {
		String accountType=R.get("accountType");//会员账户类型0.商家账户、1.服务账户
		if("0".equals(accountType)){
			//商家账户
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("060102");
		}
		if("1".equals(accountType)){
			//服务账户
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("060103");
		}
		Page<AccountUserSn> page = accountUserSnService.selectByWhere(new Page<AccountUserSn>(request, response), new Wrapper(accountUserSn)); 
		model.addAttribute("page", page);
		return "admin/account/accountUserSnList";
	}

	/**
	 * 进入保存页面
	 * @param accountUserSn 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountUserSn:save")
	@RequestMapping(value = "save1")
	public String save1(AccountUserSn accountUserSn, Model model) {
		if (accountUserSn == null){
			accountUserSn = new AccountUserSn();
		}
		model.addAttribute("accountUserSn", accountUserSn);
		return "admin/account/accountUserSnForm";
	}

	/**
	 * 执行保存
	 * @param accountUserSn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountUserSn:save")
	@RequestMapping(value = "save2")
	public String save2(AccountUserSn accountUserSn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountUserSn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountUserSn, model);//回显错误提示
		}
		
		//业务处理
		accountUserSnService.insertSelective(accountUserSn);
		addMessage(redirectAttributes, "保存会员账户流水成功");
		return "redirect:"+adminPath+"/account/accountUserSn/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountUserSn 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountUserSn:edit")
	@RequestMapping(value = "edit1")
	public String edit1(AccountUserSn accountUserSn, Model model) {
		AccountUserSn entity = null;
		if(accountUserSn!=null){
			if (accountUserSn.getId()!=null){
				entity = accountUserSnService.selectById(accountUserSn.getId());
			}
		}
		model.addAttribute("accountUserSn", entity);
		return "admin/account/accountUserSnForm";
	}

	/**
	 * 执行编辑
	 * @param accountUserSn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountUserSn:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountUserSn accountUserSn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountUserSn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountUserSn, model);//回显错误提示
		}		
		
		//业务处理
		accountUserSnService.updateByIdSelective(accountUserSn);
		addMessage(redirectAttributes, "编辑会员账户流水成功");
		return "redirect:"+adminPath+"/account/accountUserSn/list.do?repage";
	}	

	/**
	 * 删除
	 * @param accountUserSn 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountUserSn:drop")
	@RequestMapping(value = "delete")
	public String delete(AccountUserSn accountUserSn, RedirectAttributes redirectAttributes) {
		accountUserSnService.deleteById(accountUserSn.getId());
		addMessage(redirectAttributes, "删除会员账户流水成功");
		return "redirect:"+adminPath+"/account/accountUserSn/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param accountUserSn 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountUserSn accountUserSn, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("auId"))){
			errorList.add("会员账户ID不能为空");
		}
		if(StringUtils.isNotBlank(R.get("auId")) && R.get("auId").length() > 19){
			errorList.add("会员账户ID最大长度不能超过19字符");
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