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
package com.sicheng.front.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.purchase.entity.*;
import com.sicheng.admin.site.entity.SiteRecommend;
import com.sicheng.admin.site.entity.SiteRecommendItem;
import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.InterceptStrUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.purchase.service.*;
import com.sicheng.seller.site.service.SiteRecommendService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.site.service.SiteUploadService;
import com.sicheng.seller.store.service.StoreEnterAuthService;
import com.sicheng.seller.store.service.StoreService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: PurchaseController</p>
 * <p>描述: 采购controller</p>
 * @author fanxiuxiu
 * @date 2018年6月11日 上午11:10:11
 */
@Controller
@RequestMapping(value = "${frontPath}/purchase")
public class PurchaseController extends BaseController{

	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private StoreEnterAuthService storeEnterAuthService;
	@Autowired
	private PurchaseSpaceService purchaseSpaceService;
	@Autowired
	private SiteRecommendService siteRecommendService;
	@Autowired
	private PurchaseMatchmakingService purchaseMatchmakingService;
	@Autowired
	private SiteUploadService siteUploadService;
	@Autowired
	private ViewPurchaseService viewPurchaseService;
	@Autowired
	private SiteSendMessagsService siteSendMessagsService;
	@Autowired
	private PurchaseConsultationService purchaseConsultationService;
	@Autowired
	private SysVariableService sysVariableService;
	@Autowired
	private StoreService storeService;
	
	@ModelAttribute
	public void init(Model model){
		//获取项目服务列表信息(报价)
//		List<String> statusList = new ArrayList<>();
//		statusList.add("3");//招募中
//		statusList.add("4");//接洽中
//		List<AppServiceInfo> appServiceInfoType1List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",1).and("state =",1));
//		List<AppServiceInfo> appServiceInfoType2List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",2).and("state =",1));
//		List<AppServiceInfo> appServiceInfoType3List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",3).and("state =",1));
//		List<AppServiceInfo> appServiceInfoType4List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",4).and("state =",1));
//		List<AppServiceInfo> appServiceInfoType5List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",5).and("state =",1));
//		List<AppServiceInfo> appServiceInfoType6List = appServiceInfoDao.joinSelectByWhere(new Page<AppServiceInfo>(1, 12, 12), new Wrapper().and("status in",statusList).and("type=",6).and("state =",1));
//		model.addAttribute("appServiceInfoType1List",appServiceInfoType1List);//报价列表
//		model.addAttribute("appServiceInfoType2List",appServiceInfoType2List);//生产列表
//		model.addAttribute("appServiceInfoType3List",appServiceInfoType3List);//设计列表
//		model.addAttribute("appServiceInfoType4List",appServiceInfoType4List);//售后列表
//		model.addAttribute("appServiceInfoType5List",appServiceInfoType5List);//物流列表
//		model.addAttribute("appServiceInfoType6List",appServiceInfoType6List);//帮手列表
		//地址
		model.addAttribute("ctx", R.getCtx());
		model.addAttribute("ctxsso", R.getCtx()+Global.getSsoPath());
		model.addAttribute("ctxs", R.getCtx()+Global.getSellerPath());
		model.addAttribute("ctxf", R.getCtx()+Global.getFrontPath());
		model.addAttribute("ctxm",R.getCtx()+Global.getMemberPath());
		model.addAttribute("ctxStatic","/static/static");
		model.addAttribute("ctxfs", "/upload"+Global.getConfig("filestorage.dir"));
		model.addAttribute("ctxu", "/upload");
	}
	
	/**
	 * 采购首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Model model) {
		//采购订单目录列表
		Wrapper wrapper1=new Wrapper();
		wrapper1.and("pu.status=", "35");//10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		wrapper1.or("pu.status=", "40");
		wrapper1.or("pu.status=", "50");
		wrapper1.or("pu.status=","60");
		String purchaseName=R.get("purchaseName");//产品名称
		String purchaseModel=R.get("model");//型号
		String brand=R.get("brand");//品牌
		if(StringUtils.isNotBlank(purchaseName)){
			wrapper1.andNew("pi.name =",purchaseName);
		}
		if(StringUtils.isNotBlank(purchaseModel)){
			wrapper1.andNew("pi.model =",purchaseModel);
		}
		if(StringUtils.isNoneBlank(brand)){
			wrapper1.andNew("pi.brand =",brand);
		}
		Page<ViewPurchase> viewPurchasePage=viewPurchaseService.selectByWhere1(new Page<ViewPurchase>(R.getRequest(),R.getResponse()),wrapper1);
		//采购订单播报列表
		Wrapper wrapper2=new Wrapper();
		wrapper2.and("status=", "35");//10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		wrapper2.or("status=", "40");
		wrapper2.or("status=", "50");
		Page<Purchase> purchasePage2 =purchaseService.selectByWhere(new Page<Purchase>(1, 10, 10), wrapper2);
		Purchase.fillUserMain(purchasePage2.getList());
		//公司名中间用*代替
		for(int i=0;i<purchasePage2.getList().size();i++){
			Purchase purchase=purchasePage2.getList().get(i);
			UserMain userMain=purchase.getUserMain();
			if(userMain!=null && userMain.getStoreEnter()!=null && StringUtils.isNotBlank(userMain.getStoreEnter().getCompanyName())){
				String companyName=userMain.getStoreEnter().getCompanyName();
				purchase.setCompanyName(InterceptStrUtils.InterceptCompany(companyName, 2, 2));
			}
		}
		if(SsoUtils.getUserMain()!=null){
			model.addAttribute("uId", SsoUtils.getUserMain().getUId());//登录会员id
		}
		model.addAttribute("viewPurchasePage", viewPurchasePage);//采购订单目录
		model.addAttribute("purchasePage2",purchasePage2.getList());//采购订单播报列表
		model.addAttribute("purchaseName",purchaseName);//产品名称
		model.addAttribute("model",purchaseModel);//型号
		model.addAttribute("brand",brand);//品牌
		return "purchaseIndex";
	}
	
	/**
	 * 发布新购信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/release/form")
	public String releaseForm(Model model) {
		SiteUpload siteUpload = siteUploadService.selectOne(new Wrapper());
		String releaseType=R.get("releaseType");
		model.addAttribute("siteUpload", siteUpload);
		model.addAttribute("releaseType",StringUtils.isNotBlank(releaseType)?releaseType:"0");
		return "purchaseForm";
	}
	
	/**
	 * 批量发布采购 
	 * @param purchase 采购实体
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/release/batch")
	public String releaseBatch(Purchase purchase,RedirectAttributes redirectAttributes) {
		UserMain userMain=SsoUtils.getUserMain();
		if(!userMain.isTypeUserSeller()){
			addMessage(redirectAttributes,FYUtils.fyParams("商家用户才能发布采购，请注册为商家用户"));
			return "redirect:"+frontPath+"/purchase/release/form.htm?releaseType=0";
		}
		StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userMain.getUId());
		if(!(storeEnterAuth!=null && "20".equals(storeEnterAuth.getStatus()))){
			//未进行企业认证，审核失败，审核中
			return "redirect:"+sellerPath+"/store/storeEnterInfo/save1.htm";
		}
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
			return "redirect:"+frontPath+"/purchase/release/form.htm?releaseType=0";
		}
		purchaseService.batchPurchase(purchase,names,models,brands,amounts,priceRequirements,units,purchaseRemarks);
		addMessage(redirectAttributes, FYUtils.fyParams("批量发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+frontPath+"/purchase/release/success.htm";
	}
	
	/**
	 * 一句话发布采购 
	 * @param purchase 采购实体
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/release/one")
	public String releaseOne(Purchase purchase,RedirectAttributes redirectAttributes) {
		UserMain userMain=SsoUtils.getUserMain();
		if(!userMain.isTypeUserSeller()){
			addMessage(redirectAttributes,FYUtils.fyParams("商家用户才能发布采购，请注册为商家用户"));
			return "redirect:"+frontPath+"/purchase/index.htm?repage";
		}
		StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userMain.getUId());
		if(!(storeEnterAuth!=null && "20".equals(storeEnterAuth.getStatus()))){
			//未进行企业认证，审核失败，审核中
			return "redirect:"+sellerPath+"/store/storeEnterInfo/save1.htm";
		}
		//表单验证
		List<String> errorList=validate1();
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(redirectAttributes, errorList.toArray(new String[]{}));
			return "redirect:"+frontPath+"/purchase/release/form.htm?releaseType=1";
		}
		purchase.setContent(purchase.getContent().trim());	
		purchase.setUId(SsoUtils.getUserMain().getUId());
		purchase.setType("10");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		purchase.setStatus("10"); //采购状态：10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchaseService.insertSelective(purchase);
		addMessage(redirectAttributes, FYUtils.fyParams("一句话发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+frontPath+"/purchase/release/success.htm";
	}
	
	/**
	 * bom表发布采购
	 * @param purchase 采购实体
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/release/bom")
	public String releaseBom(Purchase purchase,RedirectAttributes redirectAttributes) {
		UserMain userMain=SsoUtils.getUserMain();
		if(!userMain.isTypeUserSeller()){
			addMessage(redirectAttributes,FYUtils.fyParams("商家用户才能发布采购，请注册为商家用户"));
			return "redirect:"+frontPath+"/purchase/release/form.htm?releaseType=2";
		}
		StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userMain.getUId());
		if(!(storeEnterAuth!=null && "20".equals(storeEnterAuth.getStatus()))){
			//未进行企业认证，审核失败，审核中
			return "redirect:"+sellerPath+"/store/storeEnterInfo/save1.htm?releaseType=2";
		}
		//表单验证
		List<String> errorList=validate2();
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(redirectAttributes, errorList.toArray(new String[]{}));
			return "redirect:"+frontPath+"/purchase/release/form.htm?releaseType=2";
		}
		purchase.setUId(SsoUtils.getUserMain().getUId());
		purchase.setType("20");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		purchase.setStatus("10"); //采购状态：10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchaseService.insertSelective(purchase);
		addMessage(redirectAttributes, FYUtils.fyParams("bom发布采购成功"));
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_RELEASE);
		return "redirect:"+frontPath+"/purchase/release/success.htm";
	}
	
	/**
	 * 进入发布成功页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/release/success")
	public String success(){
		return "purchaseSuccess";
	}
	
	/**
	 * 采购空间
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/space")
	public String space(Model model) {
		String spaceId=R.get("spaceId");
		Wrapper wrapper=new Wrapper();
		wrapper.and("purchase_status=", "35");//10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		wrapper.or("purchase_status=", "40");
		wrapper.or("purchase_status=", "50");
		wrapper.or("purchase_status=","60");
		if(StringUtils.isBlank(spaceId) || !StringUtils.isNumeric(spaceId)){
			model.addAttribute("message", FYUtils.fyParams("参数错误！"));
			return "400";
		}
		//采购空间信息
		PurchaseSpace purchaseSpace=purchaseSpaceService.selectById(Long.valueOf(spaceId));
		if(purchaseSpace==null || "0".equals(purchaseSpace.getIsOpen())){
			model.addAttribute("message", FYUtils.fyParams("参数错误！"));
			return "400";
		}
		wrapper.andNew("purchase_u_id =",purchaseSpace.getUId());
		//采购订单目录列表
		Page<ViewPurchase> viewPurchasePage=viewPurchaseService.selectByWhere(new Page<ViewPurchase>(R.getRequest(),R.getResponse()),wrapper);
		
		if(SsoUtils.getUserMain()!=null){
			model.addAttribute("uId", SsoUtils.getUserMain().getUId());//会员id
		}
		model.addAttribute("purchaseSpace", purchaseSpace);//采购空间信息
		model.addAttribute("viewPurchasePage", viewPurchasePage);//采购订单目录
		return "purchaseSpace";
	}
	
	/**
	 * 采购汇总页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/collect")
	public String collect(Model model) {
		List<SiteRecommendItem> siteRecommendItemList=new ArrayList<>();
		SiteRecommend siteRecommend=siteRecommendService.selectOne(new Wrapper().and("recommend_number=","purchase_collect_logo").and("is_open=",1));
		if(siteRecommend!=null){
			siteRecommendItemList=siteRecommend.getSiteRecommendItemList();
			siteRecommendItemList.add(8,new SiteRecommendItem());
			siteRecommendItemList.add(9,new SiteRecommendItem());
			siteRecommendItemList.add(14,new SiteRecommendItem());
			siteRecommendItemList.add(15,new SiteRecommendItem());
		}
		model.addAttribute("siteRecommendItemList", siteRecommendItemList);//采购订单目录
		return "purchaseCollect";
	}
	
	/**
	 * 进入立即报价页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/offer")
	public String offer(Model model) {
		String purchaseId=R.get("purchaseId");
		if(StringUtils.isBlank(purchaseId) || !StringUtils.isNumeric(purchaseId)){
			model.addAttribute("message", FYUtils.fyParams("参数错误！"));
			return "error/400";
		}
		Purchase purchase=purchaseService.selectById(Long.parseLong(purchaseId));
		if(purchase==null){
			model.addAttribute("message", FYUtils.fyParams("采购单不存在！"));
			return "error/400";
		}
		if(purchase.getUserMain()!=null){
			model.addAttribute("storeEnter",purchase.getUserMain().getStoreEnter());//采购商信息
		}
		PurchaseItem purchaseItem=new PurchaseItem();
		purchaseItem.setPurchaseId(purchase.getPurchaseId());
		List<PurchaseItem> purchaseItemList=purchaseItemService.selectByWhere(new Wrapper(purchaseItem));
		model.addAttribute("purchase",purchase);//采购单信息
		model.addAttribute("purchaseItemList",purchaseItemList);//采购单详情信息
		return "purchaseOffer";
	}
	
	/**
	 * 报价方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/release/offer2")
	public String offer2(Model model,RedirectAttributes redirectAttributes) {
		//采购单id
		String purchaseId=R.get("purchaseId");
		UserMain userMain=SsoUtils.getUserMain();//报价会员
		if(!userMain.isTypeUserSeller()){
			addMessage(redirectAttributes,FYUtils.fyParams("企业身份的用户才能报价采购，请注册为企业用户"));
			return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
		}
		StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userMain.getUId());
		if(storeEnterAuth==null || !("80".equals(storeEnterAuth.getStatus()) || "50".equals(storeEnterAuth.getStatus()))){
			//未进行企业认证，审核失败，审核中
			return "redirect:"+sellerPath+"/store/storeEnterInfo/save1.htm?releaseType=2";
		}
		//判断采购报价店铺等级
		/**
		 * 采购报价店铺等级:
			1：V1等级店铺可以报价
			2：V2等级店铺可以报价
			3：V1,V2等级店铺都可以报价
		 */
		SysVariable sysVariable = sysVariableService.getSysVariable("quotation_shop_level");
		if(sysVariable!=null){
			UserSeller userSeller = userMain.getUserSeller();
			Store store = storeService.selectById(userSeller.getStoreId());
			Long levelId = store.getLevelId();
			if("1".equals(sysVariable.getValue()) && levelId!=1L){
				addMessage(redirectAttributes,FYUtils.fyParams("报价失败，只有V1店铺可以报价"));
				return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
			}
			if("2".equals(sysVariable.getValue()) && levelId!=2L){
				addMessage(redirectAttributes,FYUtils.fyParams("报价失败，只有V2店铺可以报价"));
				return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
			}
		}
		
		//采购单详情id
		String[] purchaseItemIds=R.getArray("purchaseItemIds");
		//数量
		String[] amounts=R.getArray("amounts");
		//单价
		String[] offerPrices=R.getArray("offerPrices");
		//采购备注
		String[] offerRemarks=R.getArray("offerRemarks");
		//验证参数
		List<String> errorList=validate(purchaseId, purchaseItemIds,amounts, offerPrices, offerRemarks);
		if(!errorList.isEmpty()){
			addMessage(redirectAttributes, errorList.toArray(new String[]{}));
			return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
		}
		//采购单
		Purchase purchase=purchaseService.selectById(Long.parseLong(purchaseId));
		if(purchase==null){
			addMessage(redirectAttributes,FYUtils.fyParams("报价失败，采购单不存在"));
			return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
		}
		if(purchase.getUId().equals(userMain.getUId())){
			addMessage(redirectAttributes,FYUtils.fyParams("报价失败，不能给自己的采购单报价"));
			return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
		}
		//报价单
		PurchaseMatchmaking entity=new PurchaseMatchmaking();
		entity.setOfferUId(userMain.getUId());
		entity.setPurchaseId(purchase.getPurchaseId());
		entity=purchaseMatchmakingService.selectOne(new Wrapper(entity));
		if(entity!=null){
			addMessage(redirectAttributes,FYUtils.fyParams("报价失败，您已对采购单：")+purchase.getPurchaseId()+FYUtils.fyParams("进行报价"));
			return "redirect:"+frontPath+"/purchase/offer.htm?purchaseId="+purchaseId;
		}
		//验证通过后，处理业务
		BigDecimal prices=getPrices(amounts, offerPrices);//总价
		//撮合采购信息
		PurchaseMatchmaking purchaseMatchmaking=new PurchaseMatchmaking();
		purchaseMatchmaking.setPurchaseId(Long.parseLong(purchaseId));//采购单id
		purchaseMatchmaking.setPurchaseUId(purchase.getUId());//采购方id
		purchaseMatchmaking.setOfferUId(userMain.getUId());//报价方id
		purchaseMatchmaking.setStatus("10");//状态：10待采购   20已采购 30放弃采购
		purchaseMatchmaking.setPrice(prices);//
		//撮合采购详情信息
		List<PurchaseMatchmakingItem> list=Lists.newArrayList();
		for (int i = 0; i < purchaseItemIds.length; i++) {
			PurchaseMatchmakingItem purchaseMatchmakingItem=new PurchaseMatchmakingItem();
			purchaseMatchmakingItem.setPurchaseItemId(Long.parseLong(purchaseItemIds[i]));//采购单详情id
			purchaseMatchmakingItem.setAmount(Integer.parseInt(amounts[i]));//数量
			purchaseMatchmakingItem.setOfferPrice(new BigDecimal(offerPrices[i]));//报价单价
			purchaseMatchmakingItem.setOfferRemarks(offerRemarks[i]);//报价备注
			list.add(purchaseMatchmakingItem);
		}
		purchaseMatchmakingService.insertAll(purchaseMatchmaking, list);
		addMessage(redirectAttributes,FYUtils.fyParams("报价成功"));
		return "redirect:"+frontPath+"/purchase/release/offerSuccess.htm";
	}
	
	/**
	 * 进入报价成功页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/release/offerSuccess")
	public String offerSuccess(){
		return "purchaseOfferSuccess";
	}
	
	/**
	 * 表单验证(一句话)
	 * @param model 
	 * @return List<String> 错误提示信息
	 */
	private List<String> validate1(){
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
	private List<String> validate2(){
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
	
	/** 验证参数
	 * @param purchaseId
	 * @param purchaseItemIds
	 * @param amounts
	 * @param offerPrices
	 * @param offerRemarks
	 * @return
	 */
	private List<String> validate(String purchaseId,String[] purchaseItemIds,String[] amounts,String[] offerPrices,String[] offerRemarks) {
		List<String> errorList=new ArrayList<>();
		//验证采购单id
		if(StringUtils.isBlank(purchaseId)){
			errorList.add(FYUtils.fyParams("采购单id不能为空"));
		}
		if(StringUtils.isNotBlank(purchaseId) && !StringUtils.isNumeric(purchaseId)){
			errorList.add(FYUtils.fyParams("采购单id只能是数字"));
		}
		//验证采购单详情id
		if(purchaseItemIds!=null && purchaseItemIds.length!=0){
			for(int i=0;i<purchaseItemIds.length;i++){
				if(StringUtils.isBlank(purchaseItemIds[i])){
					errorList.add(FYUtils.fyParams("采购单详情id不能为空"));
					break;
				}
				if(StringUtils.isNotBlank(purchaseItemIds[i]) && !StringUtils.isNumeric(purchaseItemIds[i])){
					errorList.add(FYUtils.fyParams("采购单详情id只能是数字"));
					break;
				}
			}
		}
		//验证数量
		if(amounts!=null && amounts.length!=0){
			for(int i=0;i<amounts.length;i++){
				if(StringUtils.isBlank(amounts[i])){
					errorList.add(FYUtils.fyParams("数量不能为空"));
					break;
				}
				if(!amounts[i].matches("^([1-9][0-9]*)$")){
					errorList.add(FYUtils.fyParams("数量请输入非0开头的数字"));
					break;
				}
			}
		}
		//验证单价
		if(offerPrices!=null && offerPrices.length!=0){
			for(int i=0;i<offerPrices.length;i++){
				if(StringUtils.isBlank(offerPrices[i])){
					errorList.add(FYUtils.fyParams("单价不能为空"));
					break;
				}
				if(StringUtils.isNotBlank(offerPrices[i]) && offerPrices[i].length()>12){
					errorList.add(FYUtils.fyParams("单价最长12位"));
					break;
				}
				if(!offerPrices[i].matches("^([1-9]\\d{0,8}|0)(\\.\\d{1,2})?$")){
					errorList.add(FYUtils.fyParams("单价输入正整数或两位以内的正小数"));
					break;
				}
			}
		}
		//验证报价备注
		if(offerRemarks!=null && offerRemarks.length!=0){
			for(int i=0;i<offerRemarks.length;i++){
				if(StringUtils.isNotBlank(offerRemarks[i]) && offerRemarks[i].length()>255){
					errorList.add(FYUtils.fyParams("报价备注最长255位"));
					break;
				}
			}
		}
		int purchaseItemIdsLen=purchaseItemIds.length;
		int amountsLen=amounts.length;
		int offerPricesLen=offerPrices.length;
		int offerRemarksLen=offerRemarks.length;
		if(purchaseItemIdsLen==0 || amountsLen==0 || offerPricesLen==0 || offerRemarksLen==0){
			errorList.add(FYUtils.fyParams("参数错误"));
		}
		if(purchaseItemIdsLen!=amountsLen || purchaseItemIdsLen!=offerPricesLen || purchaseItemIdsLen!=offerRemarksLen){
			errorList.add(FYUtils.fyParams("参数错误"));
		}
		return errorList;
	}
	
	/** 计算总价
	 * @param purchaseItem
	 * @return
	 */
	private BigDecimal getPrices(String[] amounts,String[] offerPrices){
		BigDecimal prices=new BigDecimal("0");
		for (int i = 0; i < amounts.length; i++) {
			BigDecimal price=new BigDecimal(offerPrices[i]).multiply(new BigDecimal(amounts[i]));
			prices=prices.add(price);
		}
		return prices;
	}

	/**
	 * 提交采购咨询
	 * @param purchaseConsultation 采购咨询实体
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/consultation")
	public Map<String,Object> releaseConsultation(PurchaseConsultation purchaseConsultation, RedirectAttributes redirectAttributes) {
		UserMain userMain=SsoUtils.getUserMain();
		Map<String,Object> map=new HashMap<>();
		if(userMain==null){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("请登录"));
			return map;
		}
		if(purchaseConsultation==null){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("缺少参数"));
			return map;
		}
		if(StringUtils.isBlank(purchaseConsultation.getPurchaseDescribe())){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("不能为空",FYUtils.fyParams("问题描述")));
			return map;
		}
		if(purchaseConsultation.getPurchaseDescribe().length()>250){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("最大字符",FYUtils.fyParams("问题描述"),"250"));
			return map;
		}
		if(StringUtils.isBlank(purchaseConsultation.getContactInfo())){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("不能为空",FYUtils.fyParams("手机号或邮箱")));
			return map;
		}
		if(!RegexUtils.checkEmail(purchaseConsultation.getContactInfo()) && !RegexUtils.checkMobile(purchaseConsultation.getContactInfo())){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("请输入正确的邮箱或手机号"));
			return map;
		}
		if(purchaseConsultation.getContactInfo().length()>64){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("最大字符",FYUtils.fyParams("手机号或邮箱"),"64"));
			return map;
		}
		if(purchaseConsultation.getQuantity()==null){
			map.put("status","400");
			map.put("message",FYUtils.fyParams("不能为空",FYUtils.fyParams("数量")));
			return map;
		}
		//是否联系，0否、1是
		purchaseConsultation.setIsContact("0");
		purchaseConsultation.setUId(userMain.getUId());
		purchaseConsultationService.insertSelective(purchaseConsultation);
		map.put("status","200");
		map.put("message",FYUtils.fyParams("提交成功"));
		return map;
	}
}
