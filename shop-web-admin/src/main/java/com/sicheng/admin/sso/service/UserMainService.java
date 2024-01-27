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
import com.sicheng.admin.product.entity.*;
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
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private ProductExtService productExtService;
    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;

    /**
     * 按uId删除会员，包含买家和卖家
     * 1、会员买家删除（注意删除的是买家）
     * 2、卖家会员删除 （注意删除的是卖家）
     *
     * 商家将被删除以下信息：
     *
     * 删除会员总表(子账号)记录
     * 删除（角色和资源的中间表）记录
     * 删除卖家角色记录
     * 删除(卖家和角色的中间表)记录
     * 删除店铺表记录
     * 删除二级域名记录
     * 删除店铺商品分类记录
     * 删除店铺导航表内容记录
     * 删除店铺导航表记录
     * 删除店铺轮播图片记录
     * 删除店铺相册空间表记录
     * 删除相册夹表记录
     * 删除相册图片表记录
     * 删除店铺管理员操作日志记录
     * 删除商品SKU  删除商品区间价表  删除商品详情表 删除商品图片多对多中间表记录
     * 删除商品SPU记录
     * 删除店铺绑定品牌记录
     * 删除店铺装修表记录
     * 删除店铺物流公司中间表记录
     * 删除店铺客服表记录
     * 删除店铺文章表记录
     * 删除购物车表记录
     * 删除评论表记录
     * 删除咨询表记录
     * 删除投诉表记录
     * 删除运费模板、运费模板详情表记录
     * 删除结算账单表记录
     * 删除结算定时任务表记录
     * 删除采购空间表记录
     * 删除采购表记录
     * 删除交易凭证表记录
     * 删除采购咨询表记录
     * 删除账户表记录
     * 删除绑卡表记录
     * 删除提现表记录
     *
     * ------------------------------
     * 买家将被删除以下信息： （商家也是一个买家）
     *
     * 删除会员总表记录
     * 删除会员卖家表记录
     * 删除会员扩展表记录
     * 删除会员扩展表_采购商记录
     * 删除会员扩展表_汽车服务门店记录
     * 删除会员会员扩展表_商家注册信息表记录
     * 删除入驻申请审核表记录
     * 删除入驻申请查看表记录
     * 删除收货地址表记录
     * 删除收藏商品表记录
     * 删除收藏店铺表记录
     * 删除充值表记录
     * 删除提现表记录
     * 删除预存款明细表记录
     * 删除系统消息表记录
     * 删除购物车记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void cleanupMyWorld_DeleteUserEverything(Long uId) {
        //获取商家
        UserSeller userSeller = userSellerService.selectById(uId);
        if (userSeller != null && userSeller.getStoreId() != null) {
            Long storeId = userSeller.getStoreId();
            //删除会员总表(子账号)记录
            UserMain userMain = new UserMain();
            userMain.setParentUid(uId);
            this.deleteByWhere(new Wrapper(userMain));
            //删除（角色和资源的中间表）记录
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
            //删除卖家角色记录
            storeRoleService.deleteByWhere(new Wrapper(storeRole));
            //删除(卖家和角色的中间表)记录
            storeSellerRoleService.deleteByWhere(new Wrapper().and("a.u_id=", uId));
            //删除店铺表记录
            storeService.deleteById(storeId);
            //删除二级域名记录
            storeDomainService.deleteById(storeId);
            //删除店铺商品分类记录
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setStoreId(storeId);
            storeCategoryService.deleteByWhere(new Wrapper(storeCategory));
            //删除店铺导航表内容记录
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
            //删除店铺导航表记录
            storeNavigationService.deleteByWhere(new Wrapper(storeNavigation));
            //删除店铺管理员操作日志记录
            StoreAdminLog storeAdminLog = new StoreAdminLog();
            storeAdminLog.setStoreId(storeId);
            storeAdminLogService.deleteByWhere(new Wrapper(storeAdminLog));
            //删除商品SKU  删除商品区间价表  删除商品详情表 删除商品图片多对多中间表
            ProductSpu productSpu = new ProductSpu();
            productSpu.setStoreId(storeId);

            //使用游标查询出本店铺下全部商品，由于使用了游标不会内存溢出
            //List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
            Cursor<ProductSpu> cursor=productSpuService.selectCursor("selectByWhere", new Wrapper(productSpu));
            Iterator<ProductSpu> iter = cursor.iterator();
            while (iter.hasNext()) {
                ProductSpu entity = iter.next();
                Long pid = entity.getPId();
//            if (!productSpus.isEmpty()) {
//                List<Long> spuIds = new ArrayList<>();
//                for (int i = 0; i < productSpus.size(); i++) {
//                    spuIds.add(productSpus.get(i).getPId());
//                }
//                if (!spuIds.isEmpty()) {
//                    for (int i = 0; i < spuIds.size(); i++) {
                        //删除商品SKU
                        ProductSku productSku = new ProductSku();
                        productSku.setPId(pid);
                        productSkuService.deleteByWhere(new Wrapper(productSku));
                        //删除商品区间价表
                        ProductSectionPrice productSectionPrice = new ProductSectionPrice();
                        productSectionPrice.setPId(pid);
                        productSectionPriceService.deleteByWhere(new Wrapper(productSectionPrice));
                        //删除商品详情表
                        productDetailService.deleteById(pid);
                        //删除商品扩展表
                        productExtService.deleteById(pid);
                        //删除商品统计表
                        productSpuAnalyzeService.deleteById(pid);

                        //查询出商品的图片path并在存储中删除物理图片。
                        //问：我发现不能删除图片，因为相册中的图片是复用的，一个图片可以被多个商品引用，所以不能删除图片，请问对吗？
                        //答：不对，因为这里把整个店铺都删除了、把店铺的相册夹也都删除，把所有的商品都删除。所以图片也可都删除了。删吧
                        ProductPictureMapping ppm=new ProductPictureMapping();
                        ppm.setPId(pid);
                        List<ProductPictureMapping> listPpm=productPictureMappingService.selectByWhere(new Wrapper(ppm));
                        for(ProductPictureMapping ppm2: listPpm){
                            Long imgId=ppm2.getImgId();//图片ID
                            //按图片ID取出图片path,在存储中删除图片
                            StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
                            storeAlbumPicture.setPictureId(imgId);
                            StoreAlbumPicture storeAlbumPicture2=storeAlbumPictureService.selectOne(new Wrapper(storeAlbumPicture));
                            String path=storeAlbumPicture2.getPath();//图片path
                            //TODO 为了删除图片需要图片path压入到队列，由upload工程来消费来删除存储中的物理图片
                            //FileStorage.delete("")  //删除图片要使用FileStorage，但它只在upload子工程中有，admin子工程中没有没法删除物理图片。

                        }
                        //删除商品图片多对多中间表
                        productPictureMappingService.deleteById(pid);
                        //商品与参数中间表
                        ProductParamMapping productParamMapping = new ProductParamMapping();
                        productParamMapping.setPId(pid);
                        productParamMappingService.deleteByWhere(new Wrapper(productParamMapping));
//                    }
//                }
            }
            //删除店铺轮播图片记录
            StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
            storeCarouselPicture.setStoreId(storeId);
            storeCarouselPictureService.deleteByWhere(new Wrapper(storeCarouselPicture));
            //TODO 删除店铺轮播物理图片，为了删除图片需要图片path压入到队列，由upload工程来消费来删除存储中的物理图片
            //FileStorage.delete("")  //删除图片要使用FileStorage，但它只在upload子工程中有，admin子工程中没有没法删除物理图片。

            //删除店铺相册空间表记录
            storeAlbumSpaceService.deleteById(storeId);
            //删除相册夹表记录
            StoreAlbum storeAlbum = new StoreAlbum();
            storeAlbum.setStoreId(storeId);
            storeAlbumService.deleteByWhere(new Wrapper(storeAlbum));
            //删除相册图片表记录
            StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
            storeAlbumPicture.setStoreId(storeId);
            storeAlbumPictureService.deleteByWhere(new Wrapper(storeAlbumPicture));
            //删除商品SPU记录
            productSpuService.deleteByWhere(new Wrapper(productSpu));
            //删除店铺绑定品牌记录
            StoreBrand sb = new StoreBrand();
            sb.setStoreId(storeId);
            storeBrandService.deleteByWhere(new Wrapper(sb));
            //删除店铺装修表记录
            //TODO 删除装修物理图片
            storeDecorateService.deleteById(storeId);
            //删除店铺物流公司中间表记录
            storeLcService.deleteById(storeId);
            //删除店铺客服记录
            storeCustomerServiceService.deleteById(storeId);
            //删除店铺文章记录
            storeArticleService.deleteById(storeId);
            //删除购物车记录
            TradeCart tradeCart = new TradeCart();
            tradeCart.setStoreId(storeId);
            tradeCartService.deleteByWhere(new Wrapper(tradeCart));
            //删除评论记录
            //TODO 删除评论物理图片
            TradeComment tradeComment = new TradeComment();
            tradeComment.setStoreId(storeId);
            tradeCommentService.deleteByWhere(new Wrapper(tradeComment));
            //删除咨询记录
            TradeConsultation tradeConsultation = new TradeConsultation();
            tradeConsultation.setStoreId(storeId);
            tradeConsultationService.deleteByWhere(new Wrapper(tradeConsultation));
            //删除投诉记录
            //TODO 删除投物理图片
            TradeComplaint tradeComplaint = new TradeComplaint();
            tradeComplaint.setStoreId(storeId);
            tradeComplaintService.deleteByWhere(new Wrapper(tradeComplaint));
            //删除运费模板记录
            //删除运费模板详情记录(先查询运费模板在删除)
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
            //删除结算账单记录
            SettlementBill settlementBill = new SettlementBill();
            settlementBill.setStoreId(storeId);
            settlementBillService.deleteByWhere(new Wrapper(settlementBill));
            //删除结算定时任务记录
            SettlementTaskSub settlementTaskSub = new SettlementTaskSub();
            settlementTaskSub.setStoreId(storeId);
            settlementTaskSubService.deleteByWhere(new Wrapper(settlementTaskSub));
            //删除采购空间记录
            PurchaseSpace purchaseSpace = new PurchaseSpace();
            purchaseSpace.setUId(uId);
            purchaseSpaceService.deleteByWhere(new Wrapper(purchaseSpace));
            //删除采购记录
            Purchase purchase = new Purchase();
            purchase.setUId(uId);
            purchaseService.deleteByWhere(new Wrapper(purchase));
            //删除交易凭证记录
            PurchaseTradeVoucher purchaseTradeVoucher = new PurchaseTradeVoucher();
            purchaseTradeVoucher.setUId(uId);
            PurchaseTradeVoucherService.deleteByWhere(new Wrapper(purchaseTradeVoucher));
            //删除采购咨询记录
            PurchaseConsultation purchaseConsultation = new PurchaseConsultation();
            purchaseConsultation.setUId(uId);
            purchaseConsultationService.deleteByWhere(new Wrapper(purchaseConsultation));
            //删除账户记录
            AccountUser accountUser=new AccountUser();
            accountUser.setUId(uId);
            List<AccountUser> accountUserList = accountUserService.selectByWhere(new Wrapper(accountUser)); 
            accountService.delAccount(uId);
            //删除绑卡记录
            AccountTiedCard accountTiedCard = new AccountTiedCard();
            accountTiedCard.setUId(uId);
            accountTiedCardService.deleteByWhere(new Wrapper(accountTiedCard));
            //删除提现记录
            if(accountUserList.size()>0){
                AccountWithdrawals accountWithdrawals = new AccountWithdrawals();
                accountWithdrawals.setAccountId(accountUserList.get(0).getAuId());
                accountWithdrawalsService.deleteByWhere(new Wrapper(accountWithdrawals));
            }
        }
        //删除会员总表记录
        this.deleteById(uId);
        //删除会员卖家表记录
        userSellerService.deleteById(uId);
        //删除会员扩展表记录
        userMemberService.deleteById(uId);
        //删除会员扩展表_采购商记录
        userPurchaseService.deleteById(uId);
        //删除会员扩展表_汽车服务门店记录
        userRepairShopService.deleteById(uId);
        //删除会员会员扩展表_商家注册信息表记录
        userMerchantInfoService.deleteById(uId);
        //删除入驻申请审核表记录
        storeEnterService.deleteById(uId);
        //删除入驻申请查看表记录
        //StoreEnterAuthService storeEnterAuthService= SpringContextHolder.getBean(StoreEnterAuthService.class);
        storeEnterAuthService.deleteById(uId);
        //删除收货地址表记录
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setUId(uId);
        memberAddressService.deleteByWhere(new Wrapper(memberAddress));
        //删除收藏商品表记录
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(uId);
        memberCollectionProductService.deleteByWhere(new Wrapper(memberCollectionProduct));
        //删除收藏店铺表记录
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(uId);
        memberCollectionStoreService.deleteByWhere(new Wrapper(memberCollectionStore));
        //删除充值表记录
        SettlementRecharge settlementRecharge = new SettlementRecharge();
        settlementRecharge.setUId(uId);
        settlementRechargeService.deleteByWhere(new Wrapper(settlementRecharge));
        //删除提现表记录
        SettlementWithdrawals settlementWithdrawals = new SettlementWithdrawals();
        settlementWithdrawals.setUId(uId);
        settlementWithdrawalsService.deleteByWhere(new Wrapper(settlementWithdrawals));
        //删除预存款明细表记录
        SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
        settlementPreDeposit.setUId(uId);
        settlementPreDepositService.deleteByWhere(new Wrapper(settlementPreDeposit));
        //删除系统消息表记录
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUId(uId);
        sysMessageService.deleteByWhere(new Wrapper(sysMessage));
        //删除购物车记录
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