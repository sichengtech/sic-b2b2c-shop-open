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
package com.sicheng.admin.store.entity;

import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.dao.StoreLevelDao;
import com.sicheng.common.utils.FileSizeHelper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 商家相册 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
public class StoreAlbumSpace extends StoreAlbumSpaceBase<StoreAlbumSpace> {

    private static final long serialVersionUID = 1L;

    public StoreAlbumSpace() {
        super();
    }

    public StoreAlbumSpace(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private String totalSpaceM;                // 图片总空间(单位M)
    private String pictureSpaceM;              // 图片占用空间(单位M)
    private String surplusSpaceM;              // 图片占用空间(单位M)

    public String getTotalSpaceM() {
        return FileSizeHelper.getHumanReadableFileSize(super.getTotalSpace());
    }

    public void setTotalSpaceM(String totalSpaceM) {
        this.totalSpaceM = totalSpaceM;
    }

    public String getPictureSpaceM() {
        return FileSizeHelper.getHumanReadableFileSize(super.getPictureSpace());
    }

    public void setPictureSpaceM(String pictureSpaceM) {
        this.pictureSpaceM = pictureSpaceM;
    }

    public String getSurplusSpaceM() {
        return surplusSpaceM;
    }

    public void setSurplusSpaceM(String surplusSpaceM) {
        this.surplusSpaceM = surplusSpaceM;
    }

    //一对一映射
    private Store store;        //店铺

    public Store getStore() {
        if (store == null) {
            StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
            store = dao.selectById(this.getAlbumSpaceId());
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private StoreLevel storeLevel;      //店铺等级

    public StoreLevel getStoreLevel() {
        if (storeLevel == null) {
            StoreDao storeDao = SpringContextHolder.getBean(StoreDao.class);
            Store store = storeDao.selectById(this.getAlbumSpaceId());
            if (store != null && store.getLevelId() != null) {
                StoreLevelDao storeLevelDao = SpringContextHolder.getBean(StoreLevelDao.class);
                storeLevel = storeLevelDao.selectById(store.getLevelId());
            }
        }
        return storeLevel;
    }

    public void setStoreLevel(StoreLevel storeLevel) {
        this.storeLevel = storeLevel;
    }

    //ListIdIn工具  在一个list中做 一对一，店铺
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<StoreAlbumSpace> list) {
        List<Object> ids = batchField(list, "albumSpaceId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> Storelist = dao.selectByIdIn(ids);
        fill(Storelist, "storeId", list, "albumSpaceId", "store");//循环填充
    }

}