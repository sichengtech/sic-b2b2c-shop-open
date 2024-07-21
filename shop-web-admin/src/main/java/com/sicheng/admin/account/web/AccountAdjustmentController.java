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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sicheng.admin.sys.service.MenuService;
import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.service.AccountAdjustmentService;
import com.sicheng.admin.account.service.AccountPlatformService;
import com.sicheng.admin.account.service.AccountUserService;

import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 平台管理员调账 Controller
 * 所属模块：account 
 * @author zjl
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountAdjustmentForm")
public class AccountAdjustmentController extends BaseController {

	@Autowired
	private AccountPlatformService accountPlatformService;
	
	@Autowired
	private AccountUserService accountUserService;

	@Autowired
	private AccountAdjustmentService accountAdjustmentService;
	

	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060105";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	

	/**
	 * 进入调账页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountAdjustmentForm:edit")
	@RequestMapping(value = "edit1")
	public String edit1(Model model) {
		//平台账户
		List<AccountPlatform> list1=accountPlatformService.selectByWhere(new Wrapper());
		//商家账户
		AccountUser enyity1=new AccountUser();
		enyity1.setAccountType(0);//账户类型（0.商家账户、1.服务账户）
		List<AccountUser> list2=accountUserService.selectByWhere(new Wrapper(enyity1));
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		return "admin/account/accountAdjustmentForm";
	}

	/**
	 * 执行调账
	 * @param accountPlatform 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountAdjustmentForm:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountPlatform accountPlatform, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(model);//回显错误提示
		}
		//操作账户1信息
		String account1=R.get("account1");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation1=R.get("operation1");//操作类型0.减少、1.增加
		String money1=R.get("money1");//操作金额
		//操作账户2信息
		String account2=R.get("account2");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation2=R.get("operation2");//操作类型0.减少、1.增加
		String money2=R.get("money2");//操作金额
		//操作账户3信息
		String account3=R.get("account3");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation3=R.get("operation3");//操作类型0.减少、1.增加
		String money3=R.get("money3");//操作金额
		//调账原因
		String cause=R.get("cause");
		//业务处理
		String tips = FYUtils.fyParams("调账成功");
		try {
			accountAdjustmentService.adjustmentAccount(account1, operation1, money1, account2, operation2, money2, account3, operation3, money3, cause);
		} catch (Exception e) {
			logger.error(FYUtils.fyParams("验收报错:"), e.getMessage());
		    tips = e.getMessage();
		}
		addMessage(redirectAttributes, tips);
		return "redirect:"+adminPath+"/account/accountAdjustmentForm/edit1.do?repage";
	}


	/**
	 * 表单验证
	 * @param accountPlatform 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(Model model){
		//操作账户1信息
		String account1=R.get("account1");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation1=R.get("operation1");//操作类型0.减少、1.增加
		String money1=R.get("money1");//操作金额
		//操作账户2信息
		String account2=R.get("account2");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation2=R.get("operation2");//操作类型0.减少、1.增加
		String money2=R.get("money2");//操作金额
		//操作账户3信息
		String account3=R.get("account3");//操作账户(账户类型:1.平台账户、2.会员账户+账户id)
		String operation3=R.get("operation3");//操作类型0.减少、1.增加
		String money3=R.get("money3");//操作金额
		//调账原因
		String cause=R.get("cause");
		//验证错误
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isNotBlank(account1)){
			String[] accounts = account1.split(",");
			String accountId = accounts[1];//账户id
			if("14".equals(accountId)){
				AccountPlatform accountPlatform = accountPlatformService.selectById(14L);
				if("1".equals(operation1)){
					errorList.add(FYUtils.fyParams("调节操作账户1中补贴账户为支出账户，不能增加"));
				}
				BigDecimal operationMoney = new BigDecimal(money1);
				if(accountPlatform.getOwnMoney().compareTo(operationMoney)==-1 && "0".equals(operation1)){
					errorList.add(FYUtils.fyParams("调节操作账户1中补贴账户为支出账户，不能减少到0元以下"));
				}
			}
		}
		if(StringUtils.isNotBlank(account2)){
			String[] accounts = account2.split(",");
			String accountId = accounts[1];//账户id
			if("14".equals(accountId)){
				AccountPlatform accountPlatform = accountPlatformService.selectById(14L);
				if("1".equals(operation2)){
					errorList.add(FYUtils.fyParams("调节操作账户2中补贴账户为支出账户，不能增加"));
				}
				BigDecimal operationMoney = new BigDecimal(money2);
				if(accountPlatform.getOwnMoney().compareTo(operationMoney)==-1 && "0".equals(operation1)){
					errorList.add(FYUtils.fyParams("调节操作账户2中补贴账户为支出账户，不能减少到0元以下"));
				}
			}
		}
		if(StringUtils.isNotBlank(account3)){
			String[] accounts = account3.split(",");
			String accountId = accounts[1];//账户id
			if("14".equals(accountId)){
				AccountPlatform accountPlatform = accountPlatformService.selectById(14L);
				if("1".equals(operation3)){
					errorList.add(FYUtils.fyParams("调节操作账户3中补贴账户为支出账户，不能增加"));
				}
				BigDecimal operationMoney = new BigDecimal(money3);
				if(accountPlatform.getOwnMoney().compareTo(operationMoney)==-1 && "0".equals(operation1)){
					errorList.add(FYUtils.fyParams("调节操作账户3中补贴账户为支出账户，不能减少到0元以下"));
				}
			}
		}
		if(StringUtils.isBlank(cause)){
			errorList.add(FYUtils.fyParams("调账原因不能为空"));
		}
		return errorList;
	}

}