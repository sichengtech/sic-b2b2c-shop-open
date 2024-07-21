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
package com.sicheng.admin.pay.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.admin.pay.entity.PayCallbackLog;
import com.sicheng.admin.pay.service.PayCallbackLogService;

/**
 * 第三方支付回调日志 Controller
 * 所属模块：pay 
 * @author zhaolei
 * @version 2018-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/pay/payCallbackLog")
public class PayCallbackLogController extends BaseController {

	@Autowired
	private PayCallbackLogService payCallbackLogService;


	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="060106";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param payCallbackLog 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pay:payCallbackLog:view")
	@RequestMapping(value = "list")
	public String list(PayCallbackLog payCallbackLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayCallbackLog> page = payCallbackLogService.selectByWhere(new Page<PayCallbackLog>(request, response), new Wrapper(payCallbackLog)); 
		model.addAttribute("page", page);
		return "admin/pay/payCallbackLogList";
	}

	/**
	 * 进入保存页面
	 * @param payCallbackLog 实体对象
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pay:payCallbackLog:save")
	@RequestMapping(value = "save1")
	public String save1(PayCallbackLog payCallbackLog, Model model) {
		if (payCallbackLog == null){
			payCallbackLog = new PayCallbackLog();
		}
		model.addAttribute("payCallbackLog", payCallbackLog);
		return "admin/pay/payCallbackLogForm";
	}

	/**
	 * 执行保存
	 * @param payCallbackLog 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pay:payCallbackLog:save")
	@RequestMapping(value = "save2")
	public String save2(PayCallbackLog payCallbackLog, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(payCallbackLog, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return save1(payCallbackLog, model);//回显错误提示
		}
		
		//业务处理
		payCallbackLogService.insertSelective(payCallbackLog);
		addMessage(redirectAttributes, "保存第三方支付回调日志成功");
		return "redirect:"+adminPath+"/pay/payCallbackLog/list.do?repage";
	}

	/**
	 * 进入编辑页面
	 * @param payCallbackLog 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("pay:payCallbackLog:edit")
	@RequestMapping(value = "edit1")
	public String edit1(PayCallbackLog payCallbackLog, Model model) {
		PayCallbackLog entity = null;
		if(payCallbackLog!=null){
			if (payCallbackLog.getId()!=null){
				entity = payCallbackLogService.selectById(payCallbackLog.getId());
			}
		}
		model.addAttribute("payCallbackLog", entity);
		return "admin/pay/payCallbackLogForm";
	}

	/**
	 * 执行编辑
	 * @param payCallbackLog 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pay:payCallbackLog:edit")
	@RequestMapping(value = "edit2")
	public String edit2(PayCallbackLog payCallbackLog, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate(payCallbackLog, model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return edit1(payCallbackLog, model);//回显错误提示
		}		
		
		//业务处理
		payCallbackLogService.updateByIdSelective(payCallbackLog);
		addMessage(redirectAttributes, "编辑第三方支付回调日志成功");
		return "redirect:"+adminPath+"/pay/payCallbackLog/list.do?repage";
	}	

	/**
	 * 删除
	 * @param payCallbackLog 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("pay:payCallbackLog:drop")
	@RequestMapping(value = "delete")
	public String delete(PayCallbackLog payCallbackLog, RedirectAttributes redirectAttributes) {
		payCallbackLogService.deleteById(payCallbackLog.getId());
		addMessage(redirectAttributes, "删除第三方支付回调日志成功");
		return "redirect:"+adminPath+"/pay/payCallbackLog/list.do?repage";
	}

	/**
	 * 表单验证
	 * @param payCallbackLog 实体对象
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate(PayCallbackLog payCallbackLog, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("payWayId"))){
			errorList.add("支付方式id不能为空");
		}
		if(StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19){
			errorList.add("支付方式id最大长度不能超过19字符");
		}
		if(StringUtils.isBlank(R.get("payWayName"))){
			errorList.add("支付方式名称不能为空");
		}
		if(StringUtils.isNotBlank(R.get("payWayName")) && R.get("payWayName").length() > 64){
			errorList.add("支付方式名称最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(R.get("callbackInfo"))){
			errorList.add("支付回调信息(大字段)不能为空");
		}
		return errorList;
	}

}