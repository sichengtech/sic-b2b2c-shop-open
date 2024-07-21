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
package com.sicheng.admin.store.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.Resource;

import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.member.service.MemberCollectionStoreService;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.service.UserMemberService;
import com.sicheng.admin.store.entity.*;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.web.R;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hc360.rsf.config.ConfigLoader;
import com.hc360.rsf.config.RsfSpringLoader;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.purchase.service.PurchaseSpaceService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.admin.store.dao.StoreEnterAuthDao;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.sys.service.SysVariableService;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.shiro.SsoClearAuthorizationCache;
import com.sicheng.common.utils.FYUtils;

/**
 * 入驻申请（业务审核） Service
 *
 * @author cl
 * @version 2017-01-11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreEnterAuthService extends CrudService<StoreEnterAuthDao, StoreEnterAuth> {

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
    private AccountService accountService;
    @Autowired
    private UserMemberService userMemberService;
    @Lazy
    @Autowired
    private StoreAnalyzeService storeAnalyzeService;

    /**
     * 入驻审核
     */
    @Transactional(rollbackFor = Exception.class)
    public String storeEnterAuthview(StoreEnterAuth storeEnterAuth, String auth) {
        // 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、
        //40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、
        //80入驻信息更改后审核通过、90入驻信息更改后审核不通过)
        //一审审核基本信息，二审审核是付钱
        String info = null;
        //一审审核不通过
//        if ("1".equals(auth)) {
//            storeEnterAuth.setStatus("30");
//            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
//            info = FYUtils.fyParams("一审审核不通过");
//        }
        //一审审核成功
//        if ("2".equals(auth)) {
//            storeEnterAuth.setStatus("20");
//            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
//            info = FYUtils.fyParams("一审审核成功");
//        }
        //二审审核不通过
        if ("3".equals(auth)) {
            storeEnterAuth.setStatus("60");
            storeEnterAuth.setOneAuditOpinion(FYUtils.fyParams("入驻信息审核不通过"));
            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
            info = FYUtils.fyParams("入驻信息审核不通过");
        }
        //入驻信息二审核成功
        if ("4".equals(auth)) {
            storeEnterAuth.setStatus("50");
            storeEnterAuth.setOneAuditOpinion(FYUtils.fyParams("入驻信息审核成功,请管理员绑定"));
            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
            //利用BeanUtils进行赋值对象，赋值到StoreEnter中进行存储
            ConvertUtils.register(new DateLocaleConverter(), Date.class);//注册转化器，将按照本地格式转化为日期对象
            StoreEnter storeEnter = new StoreEnter();
            StoreEnterAuth sea = storeEnterAuthDao.selectById(storeEnterAuth.getEnterId());
            try {
                BeanUtils.copyProperties(storeEnter, sea);
            } catch (Exception e) {
                logger.error("BeanUtils复制对象出错", e);
            }
            storeEnter.setPkMode(1);
            storeEnterDao.insertSelective(storeEnter);
            //初始化公司--是否开过公司
            UserSeller userSeller = userSellerService.selectById(storeEnter.getEnterId());
            if (userSeller.getStoreId() == null) {
                initStore(storeEnter);
            }
            //新开线程创建公司慧付宝
            //createOrChangeCompanyThread(storeEnter);
            //清理sso的权限缓存
            try {
                ConfigLoader configLoader = RsfSpringLoader.getConfigLoader();
                SsoClearAuthorizationCache ssoClearAuthorizationCacheImpl = (SsoClearAuthorizationCache) configLoader.getServiceProxyBean("clearAuthorizationCache");//配置文件中的id
                ssoClearAuthorizationCacheImpl.clearAuthorizationCache(storeEnter.getEnterId());
            } catch (Exception e) {
                logger.error("RSF调用sso清理权限缓存出错", e);
            }
            info = FYUtils.fyParams("入驻信息审核成功,请管理员绑定");
        }
        //入驻信息审核不通过
        if ("5".equals(auth)) {
            storeEnterAuth.setStatus("90");
            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
            info = FYUtils.fyParams("入驻信息审核不通过");
        }
        //入驻信息审核成功
        if ("6".equals(auth)) {
            storeEnterDao.deleteById(storeEnterAuth.getId());
            //storeEnterAuth.setAuditOpinion("入驻信息审核成功");
            storeEnterAuth.setStatus("80");
            storeEnterAuthDao.updateByIdSelective(storeEnterAuth);
            //利用BeanUtils进行赋值对象，赋值到StoreEnter中进行存储
            ConvertUtils.register(new DateLocaleConverter(), Date.class);//注册转化器，将按照本地格式转化为日期对象
            StoreEnter storeEnter = new StoreEnter();
            StoreEnterAuth sea = storeEnterAuthDao.selectById(storeEnterAuth.getEnterId());
            try {
                BeanUtils.copyProperties(storeEnter, sea);
            } catch (Exception e) {
                logger.error("BeanUtils复制对象出错", e);
            }
            storeEnter.setPkMode(1);
            storeEnterDao.insertSelective(storeEnter);
            //修改店铺表(店铺类型，店铺等级，主营行业)
            UserSeller userSeller = userSellerService.selectById(storeEnter.getEnterId());
            Long storeId = userSeller.getStoreId();
            Store store = new Store();
            store.setName(storeEnter.getStoreName());//店铺名称
            store.setStoreType(storeEnter.getStoreType());//店铺类型字典（1普通店铺，2旗舰店铺）
            store.setLevelId(storeEnter.getLevelId());//店铺等级（关联店铺等级id）
            store.setIndustryId(storeEnter.getIndustryId());//主营行业（关联主营行业id）
            store.setStoreId(storeId);
//            StoreService storeService= SpringContextHolder.getBean(StoreService.class);
            storeService.updateByIdSelective(store);
            info = FYUtils.fyParams("入驻信息审核成功");
        }
        return info;
    }

    /**
     * 初始化店铺
     */
    private void initStore(StoreEnter storeEnter) {
        //判断用户是否开店，未开店才初始化店铺
        UserSeller us = userSellerService.selectById(storeEnter.getEnterId());
        if (us.getStoreId() == null) {
            UserMain userMain = userMainService.selectById(storeEnter.getEnterId());
            //会员总表修改成卖家
            UserMain u = new UserMain();
            u.setUId(storeEnter.getEnterId());
            u.setTypeUser(userMain.getTypeUser());
            u.addTypeUserSeller(); //修改成卖家
            u.addTypeUserPurchaser();//修改成采购商
            userMainService.updateByIdSelective(u);
            //在店铺表增加一条数据
            Store store = new Store();
            store.setHeadPic("/shop_init/store_face_01.png");//店铺头像图片路径（默认图片）
            store.setLogo("/shop_init/store_logo.png");//店铺logo图片路径（默认图片）
            store.setBanner("/shop_init/store_banner.png");//店铺横幅图片路径（默认图片）
            store.setName(storeEnter.getStoreName());
            store.setStoreType(storeEnter.getStoreType());//店铺类型（1.普通店铺，2.旗舰店铺）
            store.setIsOpen("1");//店铺营业状态(0关闭、1开启)
            store.setSettlementPeriod("40");//结算周期(10日结、20周结、30双周结、40月结)
            store.setLevelId(storeEnter.getLevelId());
            store.setIndustryId(storeEnter.getIndustryId());
            store.setCommission(new BigDecimal(0));

//            StoreService storeService= SpringContextHolder.getBean(StoreService.class);
            storeService.insertSelective(store);
            //修改卖家表是否开店字段为1(是否已完成开店:0否、1是),并填写店铺id字段
            UserSeller userSeller = new UserSeller();
            userSeller.setUId(storeEnter.getEnterId());
            userSeller.setIsOpen("1");//是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）
            userSeller.setStoreId(store.getStoreId());
            userSellerService.updateByIdSelective(userSeller);
            //新增卖家角色表数据
            List<StoreRole> storeRoleList = new ArrayList<StoreRole>();
            String[] roleNames = {FYUtils.fyParams("商品管理"), FYUtils.fyParams("订单管理"),FYUtils.fyParams("供采管理"), FYUtils.fyParams("店铺管理"),FYUtils.fyParams("运营管理"), FYUtils.fyParams("售后管理"), FYUtils.fyParams("运营管理"), FYUtils.fyParams("客服管理")};
            for (int i = 0; i < roleNames.length; i++) {
                StoreRole storeRole = new StoreRole();
                storeRole.setStoreId(store.getStoreId());
                storeRole.setRoleName(roleNames[i]);
                storeRole.setUseable("1");//是否可用，0否、1是
                storeRole.setIsSys("0");//是否系统数据(0否、1是)
                storeRoleList.add(storeRole);
            }
            storeRoleService.insertSelectiveBatch(storeRoleList);
            //新增卖家和角色表中间表
            List<StoreSellerRole> storeSellerRoleList = new ArrayList<StoreSellerRole>();
            for (int i = 0; i < storeRoleList.size(); i++) {
                StoreSellerRole storeSellerRole = new StoreSellerRole();
                storeSellerRole.setUId(storeEnter.getEnterId());
                storeSellerRole.setRoleId(storeRoleList.get(i).getRoleId());
                storeSellerRoleList.add(storeSellerRole);
            }
            storeSellerRoleService.insertSelectiveBatch(storeSellerRoleList);
            //插入店铺二级域名
            StoreDomain storeDomain = new StoreDomain();
            storeDomain.setPkMode(1);
            storeDomain.setStoreId(store.getStoreId());
            storeDomain.setDomain(userMain.getLoginName());
            storeDomainService.insertSelective(storeDomain);
            //获取等级
            StoreLevel storeLevel = storeLevelService.selectById(storeEnter.getLevelId());
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
            String[] albumNames = {FYUtils.fyParams("相册1"), FYUtils.fyParams("相册2"), FYUtils.fyParams("相册3"), FYUtils.fyParams("相册4"), FYUtils.fyParams("相册5")};//相册夹名
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
            String[] categoryNames = {FYUtils.fyParams("分类1"), FYUtils.fyParams("分类2"), FYUtils.fyParams("分类3"), FYUtils.fyParams("分类4"), FYUtils.fyParams("分类5")};
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
            String[] titles = {FYUtils.fyParams("公司介绍"), FYUtils.fyParams("联系我们")};//标题
            String[] contents = {FYUtils.fyParams("公司介绍"), FYUtils.fyParams("联系我们")};//内容
            SysVariable storeArticleInitCompanyIntroduction = sysVariableService.getSysVariable("store_articleInit_company_introduction");
            if (storeArticleInitCompanyIntroduction != null) {
                contents[0] = storeArticleInitCompanyIntroduction.getValueClob();
            }
            SysVariable storeArticleInitAboutUs = sysVariableService.getSysVariable("store_articleInit_about_us");
            if (storeArticleInitAboutUs != null) {
                contents[1] = storeArticleInitAboutUs.getValueClob();
            }
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
            purchaseSpace.setUId(storeEnter.getEnterId());
            purchaseSpace.setBanner("/shop_init/purchase_space_banner.jpg");
            purchaseSpace.setLogo("/shop_init/purchase_space_logo.png");
            purchaseSpace.setName(storeEnter.getStoreName());
            purchaseSpace.setSynopsis(FYUtils.fyParams("本公司在各地都设有分公司，主要进行国内外贸易，经营进出口业务。"));
            purchaseSpace.setIsOpen("1");
            purchaseSpace.setCreateDate(new java.util.Date());
            purchaseSpace.setUpdateDate(new java.util.Date());
            purchaseSpaceService.insertSelective(purchaseSpace);
            //开通商户资金账号
            accountService.openAccount(storeEnter.getEnterId(), 0);

            //店铺总计
            StoreAnalyze storeAnalyze = new StoreAnalyze();
            storeAnalyze.setStoreId(store.getStoreId());
            storeAnalyze.setAllSales(0);
            storeAnalyze.setAllSalesDate(new java.util.Date());
            storeAnalyze.setWeekSales(0);
            storeAnalyze.setWeekSalesDate(new java.util.Date());
            storeAnalyze.setMonthSales(0);
            storeAnalyze.setMonthSalesDate(new java.util.Date());
            storeAnalyze.setMonth3Sales(0);
            storeAnalyze.setMonth3SalesDate(new java.util.Date());
            storeAnalyze.setProductScore("5");
            storeAnalyze.setLogisticsScore("5");
            storeAnalyze.setServiceAttitudeScore("5");
            storeAnalyze.setProductScoreUpOrDown("1");
            storeAnalyze.setServiceAttitudeScoreUpOrDown("1");
            storeAnalyze.setLogisticsScoreUpOrDown("1");
            storeAnalyze.setPkMode(1);
            storeAnalyzeService.insertSelective(storeAnalyze);
        }
    }

    /**
     * 保存用户并入驻商家插入记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserMain userMain) {
        //添加买家
        userMain.setEmailValidate("1");
        userMain.setMobileValidate("1");
        userMain.setTypeUser("0000000001");
        userMain.setTypeAccount("1");
        userMain.addTypeUserMember();
        String salt = IdGen.randomBase62(32);
        userMain.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), salt));
        userMain.setSalt(salt);
        userMain.setParentUid(0L);
        userMain.setRegisterIp(R.getRealIp());
        userMain.setLoginIp(R.getRealIp());
        userMainService.insertSelective(userMain);

        //开通用户会员
        UserMember userMember = new UserMember();
        userMember.setUId(userMain.getUId());
        userMember.setHeadPicPath("/shop_init/user_face_01.png");
        userMember.setPkMode(1);
        userMemberService.insertSelective(userMember);

        UserSeller us = userMain.getUserSeller();
        if (us == null) {
            //卖家表插入一条记录
            UserSeller userSeller = new UserSeller();
            userSeller.setPkMode(1);
            userSeller.setUId(userMain.getUId());
            userSellerService.insertSelective(userSeller);
            //入驻申请表插入一条记录
            StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
            storeEnterAuth.setPkMode(1);
            storeEnterAuth.setEnterId(userMain.getUId());
            storeEnterAuth.setIsPerfect("0");
            this.insertSelective(storeEnterAuth);
        }
    }
}