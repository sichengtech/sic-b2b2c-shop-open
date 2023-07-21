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

import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.entity.AccountUserSn;
import com.sicheng.admin.account.service.*;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>标题: 我的钱包</p>s
 * <p>描述: </p>
 * @author zhaolei
 */
@Controller
@RequestMapping(value = "${sellerPath}/account/myWallet")
public class MyWalletController extends BaseController{
	
	@Autowired
	private StoreMenuService storeMenuService;
	@Autowired
	AccountUserService accountUserService;//会员账户
	@Autowired
	AccountUserSnService accountUserSnService;//会员账户流水
	@Autowired
	AccountPlatformService accountPlatformService;//平台账户
	@Autowired
	AccountPlatformSnService accountPlatformSnService;//平台账户流水
	@Autowired
	AccountThirdpartySnService accountThirdpartySnService;//第三方账户资金流水
	
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
	 * 进入我的账户列表页
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(AccountUserSn accountUserSn,HttpServletRequest request, HttpServletResponse response, Model model) {
		UserMain user=SsoUtils.getUserMain();//当前用户
		Wrapper wrapper=new Wrapper();
		wrapper.and("u_id=", user.getId());
		List<AccountUser> list = accountUserService.selectByWhere(wrapper);
		if(list.isEmpty()){
			return "seller/account/myWalletDetail";
		}
		if(list.size()==1) {
			AccountUser accountUser=list.get(0);
			//进入账户详情页
			return "redirect:"+sellerPath+"/account/myWallet/detail.htm?auId="+accountUser.getAuId();
		}
		model.addAttribute("list",list);
		//进入账户列表页
		return "seller/account/myWalletList";
	}
	
	/**
	 * 进入我的某一个账户（我的钱包）
	 * /xxx/detail.thm?auId=1
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "detail")
	public String detail(AccountUserSn accountUserSn,HttpServletRequest request, HttpServletResponse response, Model model) {
		Long auId=R.getLong("auId");
		model.addAttribute("auId",auId);
		UserMain user=SsoUtils.getUserMain();//当前用户
		Wrapper wrapper=new Wrapper();
		wrapper.and("u_id=", user.getId());//属主检查
		wrapper.and("au_id=", auId);
		List<AccountUser> list = accountUserService.selectByWhere(wrapper);
		if(list.size()==0) {
			//未找到账户
			return "seller/account/myWalletDetail";
		}
		
		//查询账户余额
		AccountUser accountUser=list.get(0);
		model.addAttribute("accountUser",accountUser);
		
		
		//查询账户流水
		Page<AccountUserSn> page=new Page<>(request, response);
		
		accountUserSn.setAuId(accountUser.getAuId());
		
		page=accountUserSnService.selectByWhere(page, new Wrapper(accountUserSn));
		List<AccountUserSn> list2 = page.getList();
		BigDecimal m1Count = new BigDecimal("0");//本页的总收入
		BigDecimal m2Count = new BigDecimal("0");//本页的总支出
		for(AccountUserSn sn2:list2) {
			BigDecimal m1 = sn2.getIncomeMoney();
			BigDecimal m2 = sn2.getExpensesMoney();
			if(m1!=null) {
				m1Count=m1Count.add(m1);
			}
			if(m2!=null) {
				m2Count=m2Count.add(m2);
			}
		}
		model.addAttribute("page",page);
		model.addAttribute("m1Count",m1Count);
		model.addAttribute("m2Count",m2Count);
		
		//直接进入账户详情页
		return "seller/account/myWalletDetail";
	}
}