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
package com.sicheng.seller.purchase.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.ViewPurchase;
import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.purchase.service.PurchaseService;
import com.sicheng.seller.purchase.service.ViewPurchaseService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.site.service.SiteUploadService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 供采管理</p>
 * <p>描述: </p>
 * @author cailong
 * @date 2018年6月11日 上午10:29:47
 */
@Controller
@RequestMapping(value = "${sellerPath}/purchase/purchase")
public class PurchaserController extends BaseController{
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ViewPurchaseService viewPurchaseService;
	
	@Autowired
	private SiteUploadService siteUploadService;
	
	@Autowired
	private StoreMenuService storeMenuService;
	
	@Autowired
	private SiteSendMessagsService siteSendMessagsService;
	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="080401";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting(menu3);
	}	
	
	/**
	 * 进入批量发布采购页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "batchRelease1")
	public String batchRelease1(Model model){
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting("080101");
		return "seller/purchase/purchaseBatch";
	}
	
	/**
	 * 保存批量发布采购页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "batchRelease2")
	public String batchRelease2(Purchase purchase,RedirectAttributes redirectAttributes){
		//产品名称
		String[] names = R.getArray("name");
		//产品型号
		String[] models = R.getArray("model");
		//产品品牌
		String[] brands = R.getArray("brand");
		//产品数量
		String[] amounts = R.getArray("amount");
		//单价
		String[] priceRequirements = R.getArray("priceRequirement");
		//单位
		String[] units = R.getArray("unit");
		//采购备注
		String[] purchaseRemarks = R.getArray("purchaseRemark");
		if(StringUtils.isBlank(purchase.getTitle()) || StringUtils.isBlank(purchase.getCycle()) || purchase.getExpiryTime()==null || names.length==0 || models.length==0 || brands.length==0 || amounts.length==0 || priceRequirements.length==0 || units.length==0 || purchaseRemarks.length==0){
			addMessage(redirectAttributes, FYUtils.fyParams("内容为空"));
			return "redirect:"+sellerPath+"/purchase/purchase/batchRelease1.htm";
		}
		purchaseService.batchPurchase(purchase,names,models,brands,amounts,priceRequirements,units,purchaseRemarks);
		addMessage(redirectAttributes, FYUtils.fyParams("批量发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+sellerPath+"/purchase/purchase/list.htm?repage";
	}
	
	/**
	 * 进入一句话发布采购页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "oneRelease1")
	public String oneRelease1(Purchase purchase,Model model){
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting("080201");
		model.addAttribute("purchase", purchase);
		return "seller/purchase/purchaseOne";
	}
	
	/**
	 * 保存一句话发布采购
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "oneRelease2")
	public String oneRelease2(Purchase purchase, Model model, RedirectAttributes redirectAttributes){
		//表单验证
		List<String> errorList=validate1(purchase,model);
		if(errorList.size()>0){
			errorList.add(0, "数据验证失败：");
			addMessage(model, errorList.toArray(new String[]{}));
			return oneRelease1(purchase, model);//回显错误提示
		}
		UserMain userMain = SsoUtils.getUserMain();
		purchase.setContent(purchase.getContent().trim());	
		purchase.setUId(userMain.getUId());
		purchase.setType("10");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		purchase.setStatus("10"); //采购状态：10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchaseService.insertSelective(purchase);
		addMessage(redirectAttributes, FYUtils.fyParams("一句话发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+sellerPath+"/purchase/purchase/list.htm?repage";
	}
	
	/**
	 * 进入bom发布采购页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "bomRelease1")
	public String bomRelease1(Purchase purchase, Model model){
		SiteUpload siteUpload = siteUploadService.selectOne(new Wrapper());
		model.addAttribute("siteUpload", siteUpload);
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		SellerMenuInterceptor.menuHighLighting("080301");
		model.addAttribute("purchase", purchase);
		return "seller/purchase/purchaseBOM";
	}
	
	/**
	 * 保存bom发布采购
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "bomRelease2")
	public String bomRelease2(Purchase purchase, Model model, RedirectAttributes redirectAttributes){
		//表单验证
		List<String> errorList=validate2(purchase,model);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return bomRelease1(purchase, model);//回显错误提示
		}
		UserMain userMain = SsoUtils.getUserMain();
		purchase.setUId(userMain.getUId());
		purchase.setType("20");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		purchase.setStatus("10"); //采购状态：10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchaseService.insertSelective(purchase);
		addMessage(redirectAttributes, FYUtils.fyParams("bom发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+sellerPath+"/purchase/purchase/list.htm?repage";
	}
	
	/**
	 * 进入采购列表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public String list(ViewPurchase viewPurchase, Model model, HttpServletRequest request, HttpServletResponse response){
		UserMain userMain = SsoUtils.getUserMain();
		viewPurchase.setPurchaseUId(userMain.getUId());
		Page<ViewPurchase> page = viewPurchaseService.selectByWhere(new Page<ViewPurchase>(request, response), new Wrapper(viewPurchase).orderBy("purchase_id desc")); 
		model.addAttribute("viewPurchase", viewPurchase);
		model.addAttribute("page", page);
		return "seller/purchase/purchaseList";
	}
	
	/**
	 * 取消采购
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "purchaseCancel")
	public String purchaseCancel(Purchase purchase, RedirectAttributes redirectAttributes){
		UserMain userMain = SsoUtils.getUserMain();
		Purchase purchase1 = new Purchase();
		purchase1.setStatus("60");//采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchase.setUId(userMain.getUId());
		purchaseService.updateByWhereSelective(purchase1, new Wrapper(purchase));
		addMessage(redirectAttributes, FYUtils.fyParams("取消采购成功"));
		return "redirect:"+sellerPath+"/purchase/purchase/list.htm?repage";
	}
	
	/**
	 * 表单验证(一句话)
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate1(Purchase purchase, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("title"))){
			errorList.add(FYUtils.fyParams("采购标题不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 64){
			errorList.add(FYUtils.fyParams("采购标题最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("cycle"))){
			errorList.add(FYUtils.fyParams("交货周期不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("cycle")) && R.get("cycle").length() > 64){
			errorList.add(FYUtils.fyParams("交货周期最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseExplain")) && R.get("purchaseExplain").length() > 255){
			errorList.add(FYUtils.fyParams("采购说明最大长度不能超过255字符"));
		}
		if(StringUtils.isBlank(R.get("expiryTime"))){
			errorList.add(FYUtils.fyParams("采购到期时间不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("expiryTime")) && R.get("expiryTime").length() > 64){
			errorList.add(FYUtils.fyParams("采购到期时间最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("content"))){
			errorList.add(FYUtils.fyParams("采购内容不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 2000){
			errorList.add(FYUtils.fyParams("采购内容最大长度不能超过2000字符"));
		}
		return errorList;
	}
	
	/**
	 * 表单验证(bom发布)
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate2(Purchase purchase, Model model){
		List<String> errorList = new ArrayList<String>();
		if(StringUtils.isBlank(R.get("title"))){
			errorList.add(FYUtils.fyParams("采购标题不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 64){
			errorList.add(FYUtils.fyParams("采购标题最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("cycle"))){
			errorList.add(FYUtils.fyParams("交货周期不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("cycle")) && R.get("cycle").length() > 64){
			errorList.add(FYUtils.fyParams("交货周期最大长度不能超过64字符"));
		}
		if(StringUtils.isNotBlank(R.get("purchaseExplain")) && R.get("purchaseExplain").length() > 255){
			errorList.add(FYUtils.fyParams("采购说明最大长度不能超过255字符"));
		}
		if(StringUtils.isBlank(R.get("expiryTime"))){
			errorList.add(FYUtils.fyParams("采购到期时间不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("expiryTime")) && R.get("expiryTime").length() > 64){
			errorList.add(FYUtils.fyParams("采购到期时间最大长度不能超过64字符"));
		}
		if(StringUtils.isBlank(R.get("bomPath"))){
			errorList.add(FYUtils.fyParams("bom不能为空"));
		}
		return errorList;
	}

}
