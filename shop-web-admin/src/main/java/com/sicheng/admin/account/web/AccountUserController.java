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

import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.service.AccountUserService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;

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
 * 会员账户 Controller
 * 所属模块：account 
 * @author zhaolei
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountUser")
public class AccountUserController extends BaseController {

	@Autowired
	private AccountUserService accountUserService;

	@Autowired
	private UserMainService userMainService;
	

	
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
	 * @param accountUser 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountUser:view")
	@RequestMapping(value = "list")
	public String list(AccountUser accountUser, HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String loginName = R.get("loginName");//会员名称
		model.addAttribute("loginName", loginName);
		//查询会员账户
		if(StringUtils.isNoneBlank(loginName)){
			UserMain entity = new UserMain();
			entity.setLoginName(loginName.toLowerCase());//用户名转小写
			entity = userMainService.selectOne(new Wrapper(entity));
			if(entity==null){
				return "admin/account/accountUserList";
			}
			accountUser.setUId(entity.getUId());
		}
		Page<AccountUser> page = accountUserService.selectByWhere(new Page<AccountUser>(request, response), new Wrapper(accountUser)); 
		model.addAttribute("page", page);
		return "admin/account/accountUserList";
	}

	/**
	 * 进入保存页面
	 * @param accountUser 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountUser:save")
	@RequestMapping(value = "save1")
	public String save1(AccountUser accountUser, Model model) {
		if (accountUser == null){
			accountUser = new AccountUser();
		}
		model.addAttribute("accountUser", accountUser);
		return "admin/account/accountUserForm";
	}

	/**
	 * 执行保存
	 * @param accountUser 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountUser:save")
	@RequestMapping(value = "save2")
	public String save2(AccountUser accountUser, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountUser, model);
		if(errorList.size()>0){
			errorList.add(0,FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountUser, model);//回显错误提示
		}
		
		//业务处理
		accountUserService.insertSelective(accountUser);
		addMessage(redirectAttributes, FYUtils.fyParams("保存会员账户成功"));
		return "redirect:"+adminPath+"/account/accountUser/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountUser 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountUser:edit")
	@RequestMapping(value = "edit1")
	public String edit1(AccountUser accountUser, Model model) {
		AccountUser entity = null;
		if(accountUser!=null){
			if (accountUser.getId()!=null){
				entity = accountUserService.selectById(accountUser.getId());
			}
		}
		model.addAttribute("accountUser", entity);
		return "admin/account/accountUserForm";
	}

	/**
	 * 执行编辑
	 * @param accountUser 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountUser:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountUser accountUser, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountUser, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountUser, model);//回显错误提示
		}		
		
		//业务处理
		accountUserService.updateByIdSelective(accountUser);
		addMessage(redirectAttributes, FYUtils.fyParams("编辑会员账户成功"));
		return "redirect:"+adminPath+"/account/accountUser/list.do?repage";
	}	

	/**
	 * 删除
	 * @param accountUser 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountUser:drop")
	@RequestMapping(value = "delete")
	public String delete(AccountUser accountUser, RedirectAttributes redirectAttributes) {
		accountUserService.deleteById(accountUser.getId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除会员账户成功"));
		return "redirect:"+adminPath+"/account/accountUser/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param accountUser 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountUser accountUser, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("auId"))){
			errorList.add("会员账户ID不能为空");
		}
		if(StringUtils.isNotBlank(R.get("auId")) && R.get("auId").length() > 19){
			errorList.add("会员账户ID最大长度不能超过19字符");
		}
		if(StringUtils.isBlank(R.get("uId"))){
			errorList.add("会员ID不能为空");
		}
		if(StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19){
			errorList.add("会员ID最大长度不能超过19字符");
		}
		if(StringUtils.isBlank(R.get("accountType"))){
			errorList.add("账户类型不能为空");
		}
		if(StringUtils.isNotBlank(R.get("accountType")) && R.get("accountType").length() > 10){
			errorList.add("账户类型最大长度不能超过10字符");
		}
		if(StringUtils.isBlank(R.get("ownMoney"))){
			errorList.add("会员账户余额不能为空");
		}
		if(StringUtils.isBlank(R.get("frozenMoney"))){
			errorList.add("会员冻结金额不能为空");
		}
		return errorList;
	}

}