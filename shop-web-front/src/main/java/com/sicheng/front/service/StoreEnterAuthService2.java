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
package com.sicheng.front.service;

import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreEnterAuthDao;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.*;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.seller.member.service.MemberCollectionStoreService;
import com.sicheng.seller.purchase.service.PurchaseSpaceService;
import com.sicheng.seller.store.service.*;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.service.UserMemberService;
import com.sicheng.sso.service.UserSellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 抓取数据用
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreEnterAuthService2 extends CrudService<StoreEnterAuthDao, StoreEnterAuth> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "scheduledExecutor")
    private ScheduledThreadPoolExecutor scheduledExecutor;

    @Autowired
    private StoreEnterAuthDao storeEnterAuthDao;
    @Autowired
    private StoreEnterDao storeEnterDao;
    @Autowired
    private StoreRoleService storeRoleService;
    @Lazy
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreAlbumService storeAlbumService;
    @Autowired
    private StoreDomainService storeDomainService;
    @Autowired
    private StoreAlbumSpaceService storeAlbumSpaceService;
    @Autowired
    private StoreArticleService storeArticleService;
    @Autowired
    private StoreCategoryService storeCategoryService;
    @Autowired
    private StoreNavigationService storeNavigationService;
    @Autowired
    private StoreSellerRoleService storeSellerRoleService;
    @Autowired
    private StoreCarouselPictureService storeCarouselPictureService;
    @Autowired
    private StoreLevelService storeLevelService;
    @Autowired
    private UserSellerService userSellerService;
    @Autowired
    private StoreDecorateService storeDecorateService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SysVariableService sysVariableService;
    @Autowired
    private PurchaseSpaceService purchaseSpaceService;
    @Autowired
    private StoreAnalyzeService storeAnalyzeService;
    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private AccountService accountService;

    /**
     * 获取用户名
     *
     * @param storeName
     * @return
     */
    private String getUserName(String storeName) {
        storeName = storeName.replaceAll("\\., Ltd\\.", "");
        String[] s = storeName.split(" ");
        StringBuffer userName = new StringBuffer();
        for (String str : s) {
            userName.append(str.toCharArray()[0]);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.and("login_name = ", userName.toString().toLowerCase());
        List<UserMain> userMains = userMainService.selectByWhere(wrapper);
        if (userMains.size() > 0) {
            userName.append(userMains.size() + 1);
        }
        return userName.toString().toLowerCase();
    }

    /**
     * 初始化店铺
     */
    public Long initStore(Store store, String companyIntroduction, String contactUs) {
        store.setName(store.getName().split("-")[1].trim());
        UserMain u = new UserMain();
        String salt = IdGen.randomBase62(32);
        u.setLoginName(getUserName(store.getName()));
        u.setPassword(PasswordUtils.entryptPassword(u.getLoginName() + "168", salt));
        u.setSalt(salt);
        u.setTypeUser("0000001111");
        u.setParentUid(0L);
        u.setTypeAccount("1");
        u.setRegisterIp("127.0.0.1");
        u.setLoginIp("127.0.0.1");
        u.setEmailValidate("1");
        u.setMobileValidate("1");
        u.addTypeUserSeller(); //修改成卖家
        u.addTypeUserPurchaser();//修改成采购商
        userMainService.insertSelective(u);

        //开通用户会员
        UserMember userMember = new UserMember();
        userMember.setUId(u.getUId());
        userMember.setHeadPicPath("/shop_init/user_face_01.png");
        userMember.setPkMode(1);
        userMemberService.insertSelective(userMember);

        //在店铺表增加一条数据
        store.setStoreType("2");//店铺类型（1.普通店铺，2.旗舰店铺）
        store.setIsOpen("1");//店铺营业状态(0关闭、1开启)
        store.setSettlementPeriod("40");//结算周期(10日结、20周结、30双周结、40月结)
        store.setLevelId(2L);
        store.setCommission(new BigDecimal(0));

//            StoreService storeService= SpringContextHolder.getBean(StoreService.class);
        storeService.insertSelective(store);
        //修改卖家表是否开店字段为1(是否已完成开店:0否、1是),并填写店铺id字段
        UserSeller userSeller = new UserSeller();
        userSeller.setUId(u.getUId());
        userSeller.setIsOpen("1");//是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）
        userSeller.setStoreId(store.getStoreId());
        userSeller.setPkMode(1);
        userSellerService.insertSelective(userSeller);

        //绑定用户和店铺
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(u.getUId());
        memberCollectionStore.setStoreId(store.getStoreId());
        memberCollectionStoreService.insertSelective(memberCollectionStore);

        //商家入驻审核表
        StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
        storeEnterAuth.setPkMode(1);
        storeEnterAuth.setIsPerfect("0");
        storeEnterAuth.setEnterId(u.getUId());
        storeEnterAuth.setStatus("80");
        storeEnterAuth.setCompanyName(store.getName());
        storeEnterAuth.setCountryName(store.getCountryName());
        storeEnterAuth.setProvinceName(store.getProvinceName());
        storeEnterAuth.setCityName(store.getCityName());
        storeEnterAuth.setDistrictName(store.getDistrictName());
        storeEnterAuth.setDetailedAddress(store.getDetailedAddress());
        storeEnterAuth.setStoreName(store.getName());
        storeEnterAuth.setLevelId(store.getLevelId());
        storeEnterAuth.setContactNumber(store.getStoreTel());
        this.insertSelective(storeEnterAuth);

        //新增卖家角色表数据
        List<StoreRole> storeRoleList = new ArrayList<StoreRole>();
        String[] roleNames = {"商品管理", "订单管理","供采管理", "店铺管理","资金管理", "售后管理", "运营管理", "客服管理"};
        String[] roleeNames = {"Commodity management", "Order management", "purchasing management", "Store management", "fund management", "After sale management", "operation management", "Customer service management"};
        for (int i = 0; i < roleNames.length; i++) {
            StoreRole storeRole = new StoreRole();
            storeRole.setStoreId(store.getStoreId());
            storeRole.setRoleName(roleNames[i]);
            storeRole.setEnname(roleeNames[i]);
            storeRole.setUseable("1");//是否可用，0否、1是
            storeRole.setIsSys("0");//是否系统数据(0否、1是)
            storeRoleService.insertSelective(storeRole);
            storeRoleList.add(storeRole);
        }
        //新增卖家和角色表中间表
        for (int i = 0; i < storeRoleList.size(); i++) {
            StoreSellerRole storeSellerRole = new StoreSellerRole();
            storeSellerRole.setUId(u.getUId());
            storeSellerRole.setRoleId(storeRoleList.get(i).getRoleId());
            storeSellerRoleService.insertSelective(storeSellerRole);
        }
        //插入店铺二级域名
        StoreDomain storeDomain = new StoreDomain();
        storeDomain.setPkMode(1);
        storeDomain.setStoreId(store.getStoreId());
        storeDomain.setDomain(u.getLoginName());
        storeDomainService.insertSelective(storeDomain);
        //获取等级
        StoreLevel storeLevel = storeLevelService.selectById(store.getLevelId());
        //插入店铺相册空间信息表
        StoreAlbumSpace storeAlbumSpace = new StoreAlbumSpace();
        storeAlbumSpace.setPkMode(1);
        storeAlbumSpace.setAlbumSpaceId(store.getStoreId());
        storeAlbumSpace.setPictureCount(0);
        storeAlbumSpace.setAlbumCount(5);
        storeAlbumSpace.setTotalSpace(storeLevel.getPictureSpace());
        storeAlbumSpace.setPictureSpace(0);
        storeAlbumSpaceService.insertSelective(storeAlbumSpace);
        //插入相册夹(默认5个)
        List<StoreAlbum> storeAlbumList = new ArrayList<>();
        String[] albumNames = {"Photo album 1", "Photo album 2", "Photo album 3", "Photo album 4", "Photo album 5"};//相册夹名
        for (int i = 0; i < albumNames.length; i++) {
            StoreAlbum storeAlbum = new StoreAlbum();
            storeAlbum.setAlbumName(albumNames[i]);
            storeAlbum.setPictureCount(0);//图片数量为0
            storeAlbum.setSort(10);//排序
            storeAlbum.setStoreId(store.getStoreId());
            storeAlbum.setCreateDate(new java.util.Date());
            storeAlbum.setUpdateDate(new java.util.Date());
            storeAlbumList.add(storeAlbum);
        }
        storeAlbumService.insertSelectiveBatch(storeAlbumList);
        //店铺分类
        String[] categoryNames = {"Classification 1", "Classification 2", "Classification 3", "Classification 4", "Classification 5"};
        List<StoreCategory> storeCategoryList = new ArrayList<StoreCategory>();
        for (int i = 0; i < categoryNames.length; i++) {
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setStoreId(store.getStoreId());
            storeCategory.setName(categoryNames[i]);
            storeCategory.setIsOpen("1");//是否开启(0否、1是)
            storeCategory.setSort(30);
            storeCategory.setParentIds("0,");
            storeCategory.setCreateDate(new java.util.Date());
            storeCategory.setUpdateDate(new java.util.Date());
            StoreCategory st = new StoreCategory();
            st.setStoreCategoryId(0L);
            storeCategory.setParent(st);
            storeCategoryList.add(storeCategory);
        }
        storeCategoryService.insertSelectiveBatch(storeCategoryList);
        //插入店铺装修表
        StoreDecorate storeDecorate = new StoreDecorate();
        storeDecorate.setPkMode(1);
        storeDecorate.setStoreId(store.getStoreId());
        storeDecorateService.insertSelective(storeDecorate);
        //插入店铺文章
        List<StoreArticle> storeArticleList = new ArrayList<StoreArticle>();
        String[] titles = {"Company introduction", "Contact us"};//标题
        String[] contents = {companyIntroduction, contactUs};//内容
        for (int i = 0; i < titles.length; i++) {
            StoreArticle storeArticle = new StoreArticle();
            storeArticle.setStoreId(store.getStoreId());
            storeArticle.setTitle(titles[i]);
            storeArticle.setSort(10);
            storeArticle.setContent(contents[i]);
            storeArticleList.add(storeArticle);
        }
        storeArticleService.insertSelectiveBatch(storeArticleList);
        //插入店铺导航
        List<StoreNavigation> storeNavigationList = new ArrayList<StoreNavigation>();
        for (int i = 0; i < storeArticleList.size(); i++) {
            StoreNavigation storeNavigation = new StoreNavigation();
            storeNavigation.setStoreId(store.getStoreId());
            storeNavigation.setName(storeArticleList.get(i).getTitle());
            storeNavigation.setIsOpen("1");//是否开启:0否、1是
            storeNavigation.setSort(10);//排序
            storeNavigation.setUrl("/store/" + store.getStoreId() + "/article.htm?id=" + storeArticleList.get(i).getSaId());
            storeNavigation.setTarget("2");//窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）
            storeNavigation.setCreateDate(new java.util.Date());
            storeNavigation.setUpdateDate(new java.util.Date());
            storeNavigationList.add(storeNavigation);
        }
        storeNavigationService.insertSelectiveBatch(storeNavigationList);
        //插入店铺轮播图片
        String[] picturePaths = {"/shop_init/store_carousel_picture_01.png", "/shop_init/store_carousel_picture_02.png", "/shop_init/store_carousel_picture_03.png", "/shop_init/store_carousel_picture_04.png"};//轮播图片地址
        List<StoreCarouselPicture> storeCarouselPictureList = new ArrayList<StoreCarouselPicture>();
        for (int i = 0; i < picturePaths.length; i++) {
            StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
            storeCarouselPicture.setStoreId(store.getStoreId());
            storeCarouselPicture.setPicturePath(picturePaths[i]);
            storeCarouselPicture.setUrl("#");
            storeCarouselPicture.setCreateDate(new java.util.Date());
            storeCarouselPicture.setUpdateDate(new java.util.Date());
            storeCarouselPictureList.add(storeCarouselPicture);
        }
        storeCarouselPictureService.insertSelectiveBatch(storeCarouselPictureList);

        //开通采购空间
        PurchaseSpace purchaseSpace = new PurchaseSpace();
        purchaseSpace.setUId(u.getUId());
        purchaseSpace.setBanner("/shop_init/purchase_space_banner.jpg");
        purchaseSpace.setLogo("/shop_init/purchase_space_logo.png");
        purchaseSpace.setName(store.getName());
        purchaseSpace.setSynopsis("The company has branches all over the country, mainly engaged in domestic and foreign trade, import and export business.");
        purchaseSpace.setIsOpen("1");
        purchaseSpace.setCreateDate(new java.util.Date());
        purchaseSpace.setUpdateDate(new java.util.Date());
        purchaseSpaceService.insertSelective(purchaseSpace);

        //开通商户资金账号
        accountService.openAccount(u.getUId(), 0);

        //店铺总计
        StoreAnalyze storeAnalyze = new StoreAnalyze();
        storeAnalyze.setStoreId(store.getStoreId());
        storeAnalyze.setAllSales(0);
        storeAnalyze.setAllSalesDate(new Date());
        storeAnalyze.setWeekSales(0);
        storeAnalyze.setWeekSalesDate(new Date());
        storeAnalyze.setMonthSales(0);
        storeAnalyze.setMonthSalesDate(new Date());
        storeAnalyze.setMonth3Sales(0);
        storeAnalyze.setMonth3SalesDate(new Date());
        storeAnalyze.setProductScore("5");
        storeAnalyze.setLogisticsScore("5");
        storeAnalyze.setServiceAttitudeScore("5");
        storeAnalyze.setProductScoreUpOrDown("1");
        storeAnalyze.setServiceAttitudeScoreUpOrDown("1");
        storeAnalyze.setLogisticsScoreUpOrDown("1");
        storeAnalyze.setPkMode(1);
        storeAnalyzeService.insertSelective(storeAnalyze);
        return store.getStoreId();
    }
}