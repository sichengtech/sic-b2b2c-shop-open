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
package com.sicheng.seller.account.web;

import com.sicheng.admin.account.entity.AccountTiedCard;
import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.account.service.AccountTiedCardService;
import com.sicheng.seller.account.service.AccountWithdrawalsService;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
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
 * <p>标题: 提现管理</p>
 * <p>描述: </p>
 * @author cailong
 * @date 2018年7月13日 下午1:48:59
 */
@Controller
@RequestMapping(value = "${sellerPath}/account/withdrawals")
public class AccountWithdrawalsController extends BaseController{
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	@Autowired
	private AccountWithdrawalsService accountWithdrawalsService;
	
	@Autowired
	private AccountTiedCardService accountTiedCardService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060101";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}
	
	/**
	 * 提现列表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(AccountWithdrawals accountWithdrawals,HttpServletRequest request, HttpServletResponse response, Model model) {
		String bankCardNumber = R.get("bankCardNumber");//银行卡号
		if(StringUtils.isNotBlank(bankCardNumber)){
			AccountTiedCard accountTiedCard = new AccountTiedCard();
			accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
			accountTiedCard.setBankCardNumber(bankCardNumber);
			List<AccountTiedCard> accountTiedCardList = accountTiedCardService.selectByWhere(new Wrapper(accountTiedCard));
			if(!accountTiedCardList.isEmpty()){
				accountWithdrawals.setTiedCardId(accountTiedCardList.get(0).getTiedCardId());
			}else{
				accountWithdrawals.setTiedCardId(-1L);
			}
		}
		Page<AccountWithdrawals> page = accountWithdrawalsService.selectByWhere(new Page<AccountWithdrawals>(request, response), new Wrapper(accountWithdrawals));
		AccountWithdrawals.fillAccountTiedCard(page.getList());
		model.addAttribute("page", page);
		model.addAttribute("accountWithdrawals", accountWithdrawals);
		model.addAttribute("bankCardNumber", bankCardNumber);
		return "seller/account/accountWithdrawalsList";
	}
	
	/**
	 * 进入提现表单
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save1")
	public String save1(Model model,RedirectAttributes redirectAttributes) {
		Long accountId = R.getLong("accountId");//账户id
		//查询是否绑卡过
		AccountTiedCard accountTiedCard = new AccountTiedCard();
		accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
		accountTiedCard.setAuditStatus("1");//审核是否通过（0待审核，1审核同意，2审核失败）
		List<AccountTiedCard> accountTiedCardList = accountTiedCardService.selectByWhere(new Wrapper(accountTiedCard));
		if(accountTiedCardList.isEmpty()){
			addMessage(redirectAttributes, FYUtils.fyParams("申请提现失败，当前用户未绑卡"));
			return "redirect:"+sellerPath+"/account/withdrawals/list.htm?accountId="+accountId;
		}
		model.addAttribute("accountId", accountId);
		model.addAttribute("accountTiedCardList", accountTiedCardList);
		return "seller/account/accountWithdrawalsForm";
	}
	
	/**
	 * 保存申请提现
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save2")
	public String save2(Model model,RedirectAttributes redirectAttributes) {
		Long accountId = R.getLong("accountId");//账户id
		Long tiedCardId = R.getLong("tiedCardId");//提现卡号id
		String money = R.get("money");//提现金额
		//表单验证
		List<String> errorList=validate(model);
		if(errorList.size()>0){
			addMessage(redirectAttributes, errorList.toArray(new String[]{}));
			return "redirect:"+sellerPath+"/account/withdrawals/save1.htm?accountId="+accountId;
		}
		String tips = null;
		try {
			tips = accountWithdrawalsService.applyWithdrawCash(accountId,tiedCardId,money);
			addMessage(redirectAttributes, tips);
			return "redirect:"+sellerPath+"/account/withdrawals/list.htm?repage&accountId="+accountId;
		} catch (Exception e) {
			logger.error("申请提现报错",e.getMessage());
			addMessage(redirectAttributes, FYUtils.fyParams("申请提现失败"));
			return "redirect:"+sellerPath+"/account/withdrawals/save1.htm?accountId="+accountId;
		}
	}
	
	/**
	 * 表单验证
	 * @param model
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("tiedCardId"))){
			errorList.add(FYUtils.fyParams("收款账号不能为空"));
		}
		if(StringUtils.isBlank(R.get("money"))){
			errorList.add(FYUtils.fyParams("提现金额不能为空"));
		}
		return errorList;
	}

}
