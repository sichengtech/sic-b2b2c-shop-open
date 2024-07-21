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
package com.sicheng.seller.account.web;

import com.sicheng.admin.account.entity.AccountTiedCard;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.account.service.AccountTiedCardService;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题:绑卡管理</p>
 * <p>描述: </p>
 * @author cl
 * @date 2018年7月15日 下午2:38:04
 */
@Controller
@RequestMapping(value = "${sellerPath}/account/tiedCard")
public class AccountTiedCardController extends BaseController{
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	@Autowired
	private AccountTiedCardService accountTiedCardService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060102";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}
	
	/**
	 * 绑卡列表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(AccountTiedCard accountTiedCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
		Page<AccountTiedCard> page = accountTiedCardService.selectByWhere(new Page<AccountTiedCard>(request, response), new Wrapper(accountTiedCard));
		model.addAttribute("page", page);
		model.addAttribute("accountTiedCard", accountTiedCard);
		return "seller/account/accountTiedCardList";
	}
	
	/**
	 * 进入保存页面
	 * @param accountTiedCard 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save1")
	public String save1(AccountTiedCard accountTiedCard, Model model) {
		if (accountTiedCard == null){
			accountTiedCard = new AccountTiedCard();
		}
		model.addAttribute("accountTiedCard", accountTiedCard);
		return "seller/account/accountTiedCardForm";
	}

	/**
	 * 执行保存
	 * @param accountTiedCard 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save2")
	public String save2(AccountTiedCard accountTiedCard, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountTiedCard, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountTiedCard, model);//回显错误提示
		}
		//业务处理
		accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
		accountTiedCard.setAuditStatus("0");;//审核是否通过（0待审核，1审核同意，2审核失败）
		accountTiedCardService.insertSelective(accountTiedCard);
		addMessage(redirectAttributes, FYUtils.fyParams("保存账户绑卡表成功"));
		return "redirect:"+sellerPath+"/account/tiedCard/list.htm?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountTiedCard 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("user")
	@RequestMapping(value = "edit1")
	public String edit1(AccountTiedCard accountTiedCard, Model model) {
		AccountTiedCard entity = null;
		if(accountTiedCard!=null){
			if (accountTiedCard.getId()!=null){
				entity = accountTiedCardService.selectById(accountTiedCard.getId());
			}
		}
		model.addAttribute("accountTiedCard", entity);
		return "seller/account/accountTiedCardForm";
	}

	/**
	 * 执行编辑
	 * @param accountTiedCard 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "edit2")
	public String edit2(AccountTiedCard accountTiedCard, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountTiedCard, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountTiedCard, model);//回显错误提示
		}		
		//业务处理
		accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
		accountTiedCard.setAuditStatus("0");//审核是否通过（0待审核，1审核同意，2审核失败）
		AccountTiedCard atc = new AccountTiedCard();
		atc.setTiedCardId(accountTiedCard.getTiedCardId());
		atc.setUId(SsoUtils.getUserMain().getUId());
		accountTiedCardService.updateByWhereSelective(accountTiedCard,new Wrapper(atc));
		addMessage(redirectAttributes, FYUtils.fyParams("编辑账户绑卡表成功"));
		return "redirect:"+sellerPath+"/account/tiedCard/list.htm?repage";
	}
	
	/**
	 * 删除绑卡记录
	 * @param accountTiedCard
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	public String delete(AccountTiedCard accountTiedCard, Model model, RedirectAttributes redirectAttributes) {
		//业务处理
		accountTiedCard.setUId(SsoUtils.getUserMain().getUId());
		accountTiedCardService.deleteByWhere(new Wrapper(accountTiedCard));
		addMessage(redirectAttributes, FYUtils.fyParams("删除绑卡记录成功"));
		return "redirect:"+sellerPath+"/account/tiedCard/list.htm?repage";
	}
	
	/**
	 * 验证银行卡号是否重复
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "validateBankCardNumber")
	public String validateEmail(String oldBankCardNumber, String bankCardNumber) {
		if (StringUtils.isNotBlank(bankCardNumber)  && bankCardNumber.equals(oldBankCardNumber)) {
			return "true";
		}else if(StringUtils.isNotBlank(bankCardNumber)){
			AccountTiedCard accountTiedCard = new AccountTiedCard();
			accountTiedCard.setBankCardNumber(bankCardNumber);
			List<AccountTiedCard> accountTiedCardList = accountTiedCardService.selectByWhere(new Wrapper(accountTiedCard));
			if(accountTiedCardList.isEmpty()){
				return "true";
			}else{
				return "false";
			}
		}
		return "false";
	}

	/**
	 * 表单验证
	 * @param accountTiedCard 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountTiedCard accountTiedCard, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("bankCardNumber"))){
			errorList.add(FYUtils.fyParams("银行卡号不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("bankCardNumber")) && R.get("bankCardNumber").length() > 64){
			errorList.add(FYUtils.fyParams("银行卡号最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("payee"))){
			errorList.add(FYUtils.fyParams("收款人不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("payee")) && R.get("payee").length() > 64){
			errorList.add(FYUtils.fyParams("收款人最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("idNumber"))){
			errorList.add(FYUtils.fyParams("身份证号不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("idNumber")) && R.get("idNumber").length() > 63){
			errorList.add(FYUtils.fyParams("身份证号最大长度不能超过63字符"));
		}
		if(StringUtils.isBlank(R.get("accountOpeningBank"))){
			errorList.add(FYUtils.fyParams("开户银行不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("accountOpeningBank")) && R.get("accountOpeningBank").length() > 64){
			errorList.add(FYUtils.fyParams("开户银行最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("mobilePhoneNumber"))){
			errorList.add(FYUtils.fyParams("开户手机号不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("mobilePhoneNumber")) && R.get("mobilePhoneNumber").length() > 64){
			errorList.add(FYUtils.fyParams("开户手机号最大长度不能超过64字符"));
		}
		return errorList;
	}
	
}
