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
package com.sicheng.admin.sso.service;

import com.sicheng.admin.account.entity.AccountTiedCard;
import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.admin.account.entity.SettlementBill;
import com.sicheng.admin.account.entity.SettlementTaskSub;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.account.service.AccountTiedCardService;
import com.sicheng.admin.account.service.AccountUserService;
import com.sicheng.admin.account.service.AccountWithdrawalsService;
import com.sicheng.admin.account.service.SettlementBillService;
import com.sicheng.admin.account.service.SettlementTaskSubService;
import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.logistics.service.LogisticsTemplateItemService;
import com.sicheng.admin.logistics.service.LogisticsTemplateService;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.member.service.MemberAddressService;
import com.sicheng.admin.member.service.MemberCollectionProductService;
import com.sicheng.admin.member.service.MemberCollectionStoreService;
import com.sicheng.admin.product.entity.ProductParamMapping;
import com.sicheng.admin.product.entity.ProductSectionPrice;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.*;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseConsultation;
import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.purchase.entity.PurchaseTradeVoucher;
import com.sicheng.admin.purchase.service.PurchaseConsultationService;
import com.sicheng.admin.purchase.service.PurchaseService;
import com.sicheng.admin.purchase.service.PurchaseSpaceService;
import com.sicheng.admin.purchase.service.PurchaseTradeVoucherService;
import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.entity.SettlementRecharge;
import com.sicheng.admin.settlement.entity.SettlementWithdrawals;
import com.sicheng.admin.settlement.service.SettlementPreDepositService;
import com.sicheng.admin.settlement.service.SettlementRechargeService;
import com.sicheng.admin.settlement.service.SettlementWithdrawalsService;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.*;
import com.sicheng.admin.store.service.*;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.admin.sys.service.SysMessageService;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.admin.trade.service.TradeCartService;
import com.sicheng.admin.trade.service.TradeCommentService;
import com.sicheng.admin.trade.service.TradeComplaintService;
import com.sicheng.admin.trade.service.TradeConsultationService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.web.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员总表 Service
 *
 * @author 蔡龙
 * @version 2017-04-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserMainService extends CrudService<UserMainDao, UserMain> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private MemberCollectionProductService memberCollectionProductService;
    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;
    @Autowired
    private SettlementRechargeService settlementRechargeService;
    @Autowired
    private SettlementWithdrawalsService settlementWithdrawalsService;
    @Autowired
    private SettlementPreDepositService settlementPreDepositService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private UserSellerService userSellerService;
    @Autowired
    private StoreEnterService storeEnterService;
    @Lazy
    @Autowired
    private StoreEnterAuthService storeEnterAuthService;
    @Autowired
    private StoreRoleService storeRoleService;
    @Autowired
    private StoreRoleMenuService storeRoleMenuService;
    @Autowired
    private StoreSellerRoleService storeSellerRoleService;
    @Lazy
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreDomainService storeDomainService;
    @Autowired
    private StoreCategoryService storeCategoryService;
    @Autowired
    private StoreNavigationService storeNavigationService;
    @Autowired
    private StoreNavigationDetailService storeNavigationDetailService;
    @Autowired
    private StoreCarouselPictureService storeCarouselPictureService;
    @Autowired
    private StoreAlbumSpaceService storeAlbumSpaceService;
    @Autowired
    private StoreAlbumService storeAlbumService;
    @Autowired
    private StoreAlbumPictureService storeAlbumPictureService;
    @Autowired
    private StoreAdminLogService storeAdminLogService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSectionPriceService productSectionPriceService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductPictureMappingService productPictureMappingService;
    @Autowired
    private ProductParamMappingService productParamMappingService;
    @Autowired
    private StoreBrandService storeBrandService;
    @Autowired
    private UserPurchaseService userPurchaseService;
    @Autowired
    private UserRepairShopService userRepairShopService;
    @Autowired
    private StoreDecorateService storeDecorateService;
    @Autowired
    private StoreLcService storeLcService;
    @Autowired
    private StoreCustomerServiceService storeCustomerServiceService;
    @Autowired
    private StoreArticleService storeArticleService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private TradeConsultationService tradeConsultationService;
    @Autowired
    private TradeComplaintService tradeComplaintService;
    @Autowired
    private LogisticsTemplateService LogisticsTemplateService;
    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;
    @Autowired
    private SettlementBillService settlementBillService;
    @Autowired
    private SettlementTaskSubService settlementTaskSubService;
    @Autowired
    private UserMerchantInfoService userMerchantInfoService;
    @Autowired
    private PurchaseSpaceService purchaseSpaceService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseConsultationService purchaseConsultationService;
    @Autowired
    private PurchaseTradeVoucherService PurchaseTradeVoucherService;
    @Autowired
    private AccountTiedCardService accountTiedCardService;
    @Autowired
    private AccountWithdrawalsService accountWithdrawalsService;
    @Autowired
    private AccountUserService accountUserService;
    @Lazy
    @Autowired
    private StoreAnalyzeService storeAnalyzeService;

    /**
     * 删除会员
     */
    @Transactional(rollbackFor = Exception.class)
    public void userDelete(Long uId) {
        //获取商家
        UserSeller userSeller = userSellerService.selectById(uId);
        if (userSeller != null && userSeller.getStoreId() != null) {
            Long storeId = userSeller.getStoreId();
            //删除会员总表(子账号)
            UserMain userMain = new UserMain();
            userMain.setParentUid(uId);
            this.deleteByWhere(new Wrapper(userMain));
            //删除（角色和资源的中间表）
            StoreRole storeRole = new StoreRole();
            storeRole.setStoreId(storeId);
            List<StoreRole> storeRoles = storeRoleService.selectByWhere(new Wrapper(storeRole));
            if (!storeRoles.isEmpty()) {
                List<Long> roleIds = new ArrayList<>();
                for (int i = 0; i < storeRoles.size(); i++) {
                    roleIds.add(storeRoles.get(i).getStoreId());
                }
                storeRoleMenuService.deleteByWhere(new Wrapper().and("a.role_id in", roleIds));
            }
            //删除卖家角色
            storeRoleService.deleteByWhere(new Wrapper(storeRole));
            //删除(卖家和角色的中间表)
            storeSellerRoleService.deleteByWhere(new Wrapper().and("a.u_id=", uId));
            //删除店铺表
//            StoreService storeService=SpringContextHolder.getBean(StoreService.class);
            storeService.deleteById(storeId);
            //删除二级域名
            storeDomainService.deleteById(storeId);
            //删除店铺商品分类
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setStoreId(storeId);
            storeCategoryService.deleteByWhere(new Wrapper(storeCategory));
            //删除店铺导航表内容
            StoreNavigation storeNavigation = new StoreNavigation();
            storeNavigation.setStoreId(storeId);
            List<StoreNavigation> storeNavigations = storeNavigationService.selectByWhere(new Wrapper(storeNavigation));
            if (!storeNavigations.isEmpty()) {
                List<Long> navIds = new ArrayList<>();
                for (int i = 0; i < storeNavigations.size(); i++) {
                    navIds.add(storeNavigations.get(i).getStoreNavigationId());
                }
                if (!navIds.isEmpty()) {
                    for (int i = 0; i < navIds.size(); i++) {
                        StoreNavigationDetail storeNavigationDetail = new StoreNavigationDetail();
                        storeNavigationDetail.setStoreNavigationId(navIds.get(i));
                        storeNavigationDetailService.deleteByWhere(new Wrapper(storeNavigationDetail));
                    }
                }
            }
            //删除店铺导航表
            storeNavigationService.deleteByWhere(new Wrapper(storeNavigation));
            //删除店铺轮播图片
            StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
            storeCarouselPicture.setStoreId(storeId);
            storeCarouselPictureService.deleteByWhere(new Wrapper(storeCarouselPicture));
            //删除店铺相册空间表
            storeAlbumSpaceService.deleteById(storeId);
            //删除相册夹表
            StoreAlbum storeAlbum = new StoreAlbum();
            storeAlbum.setStoreId(storeId);
            storeAlbumService.deleteByWhere(new Wrapper(storeAlbum));
            //删除相册空间表
            StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
            storeAlbumPicture.setStoreId(storeId);
            storeAlbumPictureService.deleteByWhere(new Wrapper(storeAlbumPicture));
            //删除店铺管理员操作日志
            StoreAdminLog storeAdminLog = new StoreAdminLog();
            storeAdminLog.setStoreId(storeId);
            storeAdminLogService.deleteByWhere(new Wrapper(storeAdminLog));
            //删除商品SKU  删除商品区间价表  删除商品详情表 删除商品图片多对多中间表
            ProductSpu productSpu = new ProductSpu();
            productSpu.setStoreId(storeId);
            List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
            if (!productSpus.isEmpty()) {
                List<Long> pIds = new ArrayList<>();
                for (int i = 0; i < productSpus.size(); i++) {
                    pIds.add(productSpus.get(i).getPId());
                }
                if (!pIds.isEmpty()) {
                    for (int i = 0; i < pIds.size(); i++) {
                        ProductSku productSku = new ProductSku();
                        productSku.setPId(pIds.get(i));
                        productSkuService.deleteByWhere(new Wrapper(productSku));
                        ProductSectionPrice productSectionPrice = new ProductSectionPrice();
                        productSectionPrice.setPId(pIds.get(i));
                        productSectionPriceService.deleteByWhere(new Wrapper(productSectionPrice));
                        productDetailService.deleteById(pIds.get(i));
                        productPictureMappingService.deleteById(pIds.get(i));
                        ProductParamMapping productParamMapping = new ProductParamMapping();
                        productParamMapping.setPId(pIds.get(i));
                        productParamMappingService.deleteByWhere(new Wrapper(productParamMapping));
                    }
                }
            }
            //删除商品SPU
            productSpuService.deleteByWhere(new Wrapper(productSpu));
            //删除店铺绑定品牌
            StoreBrand sb = new StoreBrand();
            sb.setStoreId(storeId);
            storeBrandService.deleteByWhere(new Wrapper(sb));
            //删除店铺装修
            storeDecorateService.deleteById(storeId);
            //店铺物流公司中间表
            storeLcService.deleteById(storeId);
            //店铺客服表
            storeCustomerServiceService.deleteById(storeId);
            //店铺文章
            storeArticleService.deleteById(storeId);
            //购物车
            TradeCart tradeCart = new TradeCart();
            tradeCart.setStoreId(storeId);
            tradeCartService.deleteByWhere(new Wrapper(tradeCart));
            //评论
            TradeComment tradeComment = new TradeComment();
            tradeComment.setStoreId(storeId);
            tradeCommentService.deleteByWhere(new Wrapper(tradeComment));
            //咨询
            TradeConsultation tradeConsultation = new TradeConsultation();
            tradeConsultation.setStoreId(storeId);
            tradeConsultationService.deleteByWhere(new Wrapper(tradeConsultation));
            //投诉
            TradeComplaint tradeComplaint = new TradeComplaint();
            tradeComplaint.setStoreId(storeId);
            tradeComplaintService.deleteByWhere(new Wrapper(tradeComplaint));
            //运费模板
            //运费模板详情(先查询运费模板在删除)
            LogisticsTemplate logisticsTemplate = new LogisticsTemplate();
            logisticsTemplate.setStoreId(storeId);
            List<LogisticsTemplate> logisticsTemplateList = LogisticsTemplateService.selectByWhere(new Wrapper(logisticsTemplate));
            if (!logisticsTemplateList.isEmpty()) {
                for (int j = 0; j < logisticsTemplateList.size(); j++) {
                    LogisticsTemplateItem logisticsTemplateItem = new LogisticsTemplateItem();
                    logisticsTemplateItem.setLtId(logisticsTemplateList.get(j).getLtId());
                    logisticsTemplateItemService.deleteByWhere(new Wrapper(logisticsTemplateItem));
                }
            }
            LogisticsTemplateService.deleteByWhere(new Wrapper(logisticsTemplate));
            //结算账单
            SettlementBill settlementBill = new SettlementBill();
            settlementBill.setStoreId(storeId);
            settlementBillService.deleteByWhere(new Wrapper(settlementBill));
            //结算定时任务
            SettlementTaskSub settlementTaskSub = new SettlementTaskSub();
            settlementTaskSub.setStoreId(storeId);
            settlementTaskSubService.deleteByWhere(new Wrapper(settlementTaskSub));
            //采购空间
            PurchaseSpace purchaseSpace = new PurchaseSpace();
            purchaseSpace.setUId(uId);
            purchaseSpaceService.deleteByWhere(new Wrapper(purchaseSpace));
            //采购
            Purchase purchase = new Purchase();
            purchase.setUId(uId);
            purchaseService.deleteByWhere(new Wrapper(purchase));
            //交易凭证
            PurchaseTradeVoucher purchaseTradeVoucher = new PurchaseTradeVoucher();
            purchaseTradeVoucher.setUId(uId);
            PurchaseTradeVoucherService.deleteByWhere(new Wrapper(purchaseTradeVoucher));
            //采购咨询
            PurchaseConsultation purchaseConsultation = new PurchaseConsultation();
            purchaseConsultation.setUId(uId);
            purchaseConsultationService.deleteByWhere(new Wrapper(purchaseConsultation));
            //账户
            AccountUser accountUser=new AccountUser();
            accountUser.setUId(uId);
            List<AccountUser> accountUserList = accountUserService.selectByWhere(new Wrapper(accountUser)); 
            accountService.delAccount(uId);
            //绑卡
            AccountTiedCard accountTiedCard = new AccountTiedCard();
            accountTiedCard.setUId(uId);
            accountTiedCardService.deleteByWhere(new Wrapper(accountTiedCard));
            //提现
            AccountWithdrawals accountWithdrawals = new AccountWithdrawals();
            accountWithdrawals.setAccountId(accountUserList.get(0).getAuId());
            accountWithdrawalsService.deleteByWhere(new Wrapper(accountWithdrawals));
        }
        //删除会员总表
        this.deleteById(uId);
        //删除会员卖家表
        userSellerService.deleteById(uId);
        //删除会员扩展表
        userMemberService.deleteById(uId);
        //删除会员扩展表_采购商
        userPurchaseService.deleteById(uId);
        //删除会员扩展表_汽车服务门店
        userRepairShopService.deleteById(uId);
        //删除会员会员扩展表_商家注册信息表
        userMerchantInfoService.deleteById(uId);
        //删除入驻申请审核表
        storeEnterService.deleteById(uId);
        //删除入驻申请查看表
//        StoreEnterAuthService storeEnterAuthService= SpringContextHolder.getBean(StoreEnterAuthService.class);
        storeEnterAuthService.deleteById(uId);
        //删除收货地址表
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setUId(uId);
        memberAddressService.deleteByWhere(new Wrapper(memberAddress));
        //删除收藏商品表
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(uId);
        memberCollectionProductService.deleteByWhere(new Wrapper(memberCollectionProduct));
        //删除收藏店铺表
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(uId);
        memberCollectionStoreService.deleteByWhere(new Wrapper(memberCollectionStore));
        //删除充值表
        SettlementRecharge settlementRecharge = new SettlementRecharge();
        settlementRecharge.setUId(uId);
        settlementRechargeService.deleteByWhere(new Wrapper(settlementRecharge));
        //删除提现表
        SettlementWithdrawals settlementWithdrawals = new SettlementWithdrawals();
        settlementWithdrawals.setUId(uId);
        settlementWithdrawalsService.deleteByWhere(new Wrapper(settlementWithdrawals));
        //删除预存款明细表
        SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
        settlementPreDeposit.setUId(uId);
        settlementPreDepositService.deleteByWhere(new Wrapper(settlementPreDeposit));
        //删除系统消息表
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUId(uId);
        sysMessageService.deleteByWhere(new Wrapper(sysMessage));
        //购物车
        TradeCart tradeCart = new TradeCart();
        tradeCart.setUId(uId);
        tradeCartService.deleteByWhere(new Wrapper(tradeCart));
    }

    /**
     * 锁定会员
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void locked(Long uId) {
        //锁定会员表
        UserMain userMain = new UserMain();
        userMain.setUId(uId);
        userMain.setIsLocked("1");//是否锁定(0否，1是)锁定后不能登录
        this.updateByIdSelective(userMain);
        UserSeller u = userSellerService.selectById(uId);
        if (u != null) {
            Long storeId = u.getStoreId();
            if (storeId != null) {
                //关闭店铺
                Store storebase = new Store();
                storebase.setStoreId(storeId);
                storebase.setIsOpen("0"); //店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的)
                StoreService storeService=SpringContextHolder.getBean(StoreService.class);
                storeService.updateByIdSelective(storebase);
            }
        }
        //锁定子账号
        UserMain userChild = new UserMain();
        userChild.setParentUid(userMain.getUId());
        UserMain userChild1 = new UserMain();
        userChild1.setIsLocked("1");//是否锁定(0否，1是)锁定后不能登录
        this.updateByWhereSelective(userChild1, new Wrapper(userChild));
    }


    /**
     * 解锁会员
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void unLocked(Long uId) {
        //锁定会员表
        UserMain userMain = new UserMain();
        userMain.setUId(uId);
        userMain.setIsLocked("0");//是否锁定(0否，1是)锁定后不能登录
        this.updateByIdSelective(userMain);
        UserSeller u = userSellerService.selectById(uId);
        if (u != null) {
            Long storeId = u.getStoreId();
            if (storeId != null) {
                //关闭店铺
                Store storebase = new Store();
                storebase.setStoreId(storeId);
                storebase.setIsOpen("1"); //店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的)
                StoreService storeService=SpringContextHolder.getBean(StoreService.class);
                storeService.updateByIdSelective(storebase);
            }
        }
        //解锁子账号
        UserMain userChild = new UserMain();
        userChild.setParentUid(userMain.getUId());
        UserMain userChild1 = new UserMain();
        userChild1.setIsLocked("0");//是否锁定(0否，1是)锁定后不能登录
        this.updateByWhereSelective(userChild1, new Wrapper(userChild));
    }
}