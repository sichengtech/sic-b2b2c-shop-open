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

import com.sicheng.admin.account.entity.AccountTiedCard;
import com.sicheng.admin.account.service.AccountTiedCardService;

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
 * 账户绑卡表 Controller
 * 所属模块：account 
 * @author cl
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountTiedCard")
public class AccountTiedCardController extends BaseController {

	@Autowired
	private AccountTiedCardService accountTiedCardService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060109";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param accountTiedCard 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountTiedCard:view")
	@RequestMapping(value = "list")
	public String list(AccountTiedCard accountTiedCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountTiedCard> page = accountTiedCardService.selectByWhere(new Page<AccountTiedCard>(request, response), new Wrapper(accountTiedCard)); 
		model.addAttribute("page", page);
		return "admin/account/accountTiedCardList";
	}

	/**
	 * 审核绑卡单
	 * @param accountTiedCard
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountTiedCard:auth")
	@RequestMapping(value = "auth1")
	public String auth1(AccountTiedCard accountTiedCard, Model model) {
		AccountTiedCard entity = null;
		if(accountTiedCard!=null){
			if (accountTiedCard.getId()!=null){
				entity = accountTiedCardService.selectById(accountTiedCard.getId());
			}
		}
		model.addAttribute("accountTiedCard", entity);
		return "admin/account/accountTiedCardAuth";
	}
	
	/**
	 * 执行保存审核
	 * @param accountTiedCard 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountTiedCard:auth")
	@RequestMapping(value = "auth2")
	public String auth2(AccountTiedCard accountTiedCard, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountTiedCard, model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return auth1(accountTiedCard, model);//回显错误提示
		}		
		//业务处理
		accountTiedCardService.updateByIdSelective(accountTiedCard);
		addMessage(redirectAttributes, FYUtils.fyParams("审核绑卡成功"));
		return "redirect:"+adminPath+"/account/accountTiedCard/list.do?repage";
	}	
	
	/**
	 * 表单验证
	 * @param accountTiedCard 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountTiedCard accountTiedCard, Model model){
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