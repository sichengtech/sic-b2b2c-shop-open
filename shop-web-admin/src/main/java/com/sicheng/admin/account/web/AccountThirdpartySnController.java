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

import com.sicheng.admin.account.entity.AccountThirdpartySn;
import com.sicheng.admin.account.service.AccountThirdpartySnService;

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
 * 第三方账户资金流水 Controller
 * 所属模块：account 
 * @author zhaolei
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountThirdpartySn")
public class AccountThirdpartySnController extends BaseController {

	@Autowired
	private AccountThirdpartySnService accountThirdpartySnService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060104";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param accountThirdpartySn 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountThirdpartySn:view")
	@RequestMapping(value = "list")
	public String list(AccountThirdpartySn accountThirdpartySn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountThirdpartySn> page = accountThirdpartySnService.selectByWhere(new Page<AccountThirdpartySn>(request, response), new Wrapper(accountThirdpartySn)); 
		model.addAttribute("page", page);
		return "admin/account/accountThirdpartySnList";
	}

	/**
	 * 进入保存页面
	 * @param accountThirdpartySn 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountThirdpartySn:save")
	@RequestMapping(value = "save1")
	public String save1(AccountThirdpartySn accountThirdpartySn, Model model) {
		if (accountThirdpartySn == null){
			accountThirdpartySn = new AccountThirdpartySn();
		}
		model.addAttribute("accountThirdpartySn", accountThirdpartySn);
		return "admin/account/accountThirdpartySnForm";
	}

	/**
	 * 执行保存
	 * @param accountThirdpartySn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountThirdpartySn:save")
	@RequestMapping(value = "save2")
	public String save2(AccountThirdpartySn accountThirdpartySn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountThirdpartySn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(accountThirdpartySn, model);//回显错误提示
		}
		
		//业务处理
		accountThirdpartySnService.insertSelective(accountThirdpartySn);
		addMessage(redirectAttributes, "保存第三方账户资金流水成功");
		return "redirect:"+adminPath+"/account/accountThirdpartySn/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param accountThirdpartySn 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("account:accountThirdpartySn:edit")
	@RequestMapping(value = "edit1")
	public String edit1(AccountThirdpartySn accountThirdpartySn, Model model) {
		AccountThirdpartySn entity = null;
		if(accountThirdpartySn!=null){
			if (accountThirdpartySn.getId()!=null){
				entity = accountThirdpartySnService.selectById(accountThirdpartySn.getId());
			}
		}
		model.addAttribute("accountThirdpartySn", entity);
		return "admin/account/accountThirdpartySnForm";
	}

	/**
	 * 执行编辑
	 * @param accountThirdpartySn 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:accountThirdpartySn:edit")
	@RequestMapping(value = "edit2")
	public String edit2(AccountThirdpartySn accountThirdpartySn, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(accountThirdpartySn, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(accountThirdpartySn, model);//回显错误提示
		}		
		
		//业务处理
		accountThirdpartySnService.updateByIdSelective(accountThirdpartySn);
		addMessage(redirectAttributes, "编辑第三方账户资金流水成功");
		return "redirect:"+adminPath+"/account/accountThirdpartySn/list.do?repage";
	}	

	/**
	 * 删除
	 * @param accountThirdpartySn 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("account:accountThirdpartySn:drop")
	@RequestMapping(value = "delete")
	public String delete(AccountThirdpartySn accountThirdpartySn, RedirectAttributes redirectAttributes) {
		accountThirdpartySnService.deleteById(accountThirdpartySn.getId());
		addMessage(redirectAttributes, "删除第三方账户资金流水成功");
		return "redirect:"+adminPath+"/account/accountThirdpartySn/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param accountThirdpartySn 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(AccountThirdpartySn accountThirdpartySn, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("serialNumber"))){
			errorList.add("流水号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("serialNumber")) && R.get("serialNumber").length() > 64){
			errorList.add("流水号最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("moneyFlowType"))){
			errorList.add("资金流类型（1.付款、2.提现、3.充值、4.退款）不能为空");
		}
		if(StringUtils.isNotBlank(R.get("moneyFlowType")) && R.get("moneyFlowType").length() > 1){
			errorList.add("资金流类型（1.付款、2.提现、3.充值、4.退款）最大长度不能超过1字符");
		}
		if(StringUtils.isBlank(R.get("money"))){
			errorList.add("交易金额不能为空");
		}
		if(StringUtils.isBlank(R.get("payWayId"))){
			errorList.add("交易渠道不能为空");
		}
		if(StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19){
			errorList.add("交易渠道最大长度不能超过19字符");
		}
		if(StringUtils.isNotBlank(R.get("payWayName")) && R.get("payWayName").length() > 64){
			errorList.add("支付方式名称最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("outerTradeNo"))){
			errorList.add("外部交易记录号不能为空");
		}
		if(StringUtils.isNotBlank(R.get("outerTradeNo")) && R.get("outerTradeNo").length() > 64){
			errorList.add("外部交易记录号最大长度不能超过64字符");
		}
		if(StringUtils.isNotBlank(R.get("tradeRemarks")) && R.get("tradeRemarks").length() > 255){
			errorList.add("交易备注最大长度不能超过255字符");
		}
		return errorList;
	}

}