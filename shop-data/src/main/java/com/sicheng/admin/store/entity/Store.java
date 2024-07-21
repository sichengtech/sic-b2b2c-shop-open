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
package com.sicheng.admin.store.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.purchase.dao.PurchaseSpaceDao;
import com.sicheng.admin.purchase.entity.PurchaseSpace;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.dao.UserSellerDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreAnalyzeDao;
import com.sicheng.admin.store.dao.StoreDecorateDao;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.dao.StoreIndustryDao;
import com.sicheng.admin.store.dao.StoreLevelDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 店铺 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-07
 */
public class Store extends StoreBase<Store> {

    private static final long serialVersionUID = 1L;

    public Store() {
        super();
    }

    public Store(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    //一对一映射
    private StoreEnter storeEnter;      //入驻申请

    public StoreEnter getStoreEnter() {
        if (storeEnter == null) {
            UserSellerDao userSellerDao = SpringContextHolder.getBean(UserSellerDao.class);
            UserSeller userSeller = new UserSeller();
            userSeller.setStoreId(this.getStoreId());
            List<UserSeller> userSellerList = userSellerDao.selectByWhere(null, new Wrapper(userSeller));
            if (!userSellerList.isEmpty()) {
                Long uId = userSellerList.get(0).getUId();
                StoreEnterDao dao = SpringContextHolder.getBean(StoreEnterDao.class);
                storeEnter = dao.selectById(uId);
            }
        }
        return storeEnter;
    }

    public void setStoreEnter(StoreEnter storeEnter) {
        this.storeEnter = storeEnter;
    }

    private UserMain userMain; //一条店铺--一个主账号

    public UserMain getUserMain() {
        if (userMain == null) {
            UserSellerDao userSellerDao = SpringContextHolder.getBean(UserSellerDao.class);
            UserMainDao userMainDao = SpringContextHolder.getBean(UserMainDao.class);
            List<UserSeller> userSellers = userSellerDao.selectByWhere(null, new Wrapper().and("a.store_id=", this.getStoreId()));
            UserSeller us = null;
            if (!userSellers.isEmpty()) {
                us = userSellers.get(0);
                userMain = userMainDao.selectById(us.getUId());
            } else {
                userMain = null;
            }
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    private StoreDecorate storeDecorate; //一个店铺 一个装修

    public StoreDecorate getStoreDecorate() {
        if (storeDecorate == null) {
            StoreDecorateDao dao = SpringContextHolder.getBean(StoreDecorateDao.class);
            storeDecorate = dao.selectById(this.getStoreId());
        }
        return storeDecorate;
    }

    public void setStoreDecorate(StoreDecorate storeDecorate) {
        this.storeDecorate = storeDecorate;
    }

    private StoreLevel storeLevel; //一个店铺 一个等级

    public StoreLevel getStoreLevel() {
        if (storeLevel == null) {
            StoreLevelDao dao = SpringContextHolder.getBean(StoreLevelDao.class);
            storeLevel = dao.selectById(this.getLevelId());
        }
        return storeLevel;
    }

    public void setStoreLevel(StoreLevel storeLevel) {
        this.storeLevel = storeLevel;
    }

    private StoreIndustry storeIndustry; //一个店铺 一个主营行业

    public StoreIndustry getStoreIndustry() {
        if (storeIndustry == null) {
            StoreIndustryDao dao = SpringContextHolder.getBean(StoreIndustryDao.class);
            storeIndustry = dao.selectById(this.getIndustryId());
        }
        return storeIndustry;
    }

    public void setStoreIndustry(StoreIndustry storeIndustry) {
        this.storeIndustry = storeIndustry;
    }

    private PurchaseSpace purchaseSpace; //一个店铺 一个采购空间

    public PurchaseSpace getPurchaseSpace() {
        if (purchaseSpace == null) {
            UserSellerDao userSellerDao = SpringContextHolder.getBean(UserSellerDao.class);
            UserSeller userSeller = new UserSeller();
            userSeller.setStoreId(this.getStoreId());
            List<UserSeller> userSellerList = userSellerDao.selectByWhere(null, new Wrapper(userSeller));
            PurchaseSpaceDao purchaseSpaceDao = SpringContextHolder.getBean(PurchaseSpaceDao.class);
            PurchaseSpace p = new PurchaseSpace();
            p.setUId(userSellerList.get(0).getUId());
            p.setIsOpen("1");
            List<PurchaseSpace> purchaseSpaceList = purchaseSpaceDao.selectByWhere(null, new Wrapper(p));
            if(!purchaseSpaceList.isEmpty()){
            	purchaseSpace = purchaseSpaceList.get(0);
            }
        }
        return purchaseSpace;
    }

    public void setPurchaseSpace(PurchaseSpace purchaseSpace) {
        this.purchaseSpace = purchaseSpace;
    }

    private String isBindingBrand; //是否绑定品牌

    public String getIsBindingBrand() {
        return isBindingBrand;
    }

    public void setIsBindingBrand(String isBindingBrand) {
        this.isBindingBrand = isBindingBrand;
    }

    //ListIdIn工具  在一个list中做 一对一,店铺等级
    //填充 xxx,把1+N改成1+1
    public static void fillStoreLevel(List<Store> list) {
        List<Object> ids = batchField(list, "levelId");//批量调用对象的getXxx()方法
        StoreLevelDao dao = SpringContextHolder.getBean(StoreLevelDao.class);
        List<StoreLevel> StoreLevellist = dao.selectByIdIn(ids);
        fill(StoreLevellist, "levelId", list, "levelId", "storeLevel");//循环填充
    }

    //ListIdIn工具  在一个list中做 一对一,店铺等级
    //填充 xxx,把1+N改成1+1
    public static void fillStoreIndustry(List<Store> list) {
        List<Object> ids = batchField(list, "industryId");//批量调用对象的getXxx()方法
        StoreIndustryDao dao = SpringContextHolder.getBean(StoreIndustryDao.class);
        List<StoreIndustry> StoreIndustrylist = dao.selectByIdIn(ids);
        fill(StoreIndustrylist, "industryId", list, "industryId", "storeIndustry");//循环填充
    }

    /**
     * 佣金比例（去掉无用小数点0）
     */
    @Override
    public BigDecimal getCommission() {
        if (super.getCommission() == null) {
            return super.getCommission();
        }
        String commission = super.getCommission().stripTrailingZeros().toPlainString();
        return new BigDecimal(commission);
    }

    /**
     * 用于获取店铺汇总信息
     */
    @JsonIgnore
    private StoreAnalyze storeAnalyze;

    public StoreAnalyze getStoreAnalyze() {
        if (this.getStoreId() != null) {
            StoreAnalyzeDao dao = SpringContextHolder.getBean(StoreAnalyzeDao.class);
            this.storeAnalyze = dao.selectById(this.getStoreId());
        }
        return storeAnalyze;
    }
}