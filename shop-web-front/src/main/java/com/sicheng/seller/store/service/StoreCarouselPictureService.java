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
package com.sicheng.seller.store.service;

import com.sicheng.admin.store.dao.StoreCarouselPictureDao;
import com.sicheng.admin.store.entity.StoreCarouselPicture;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺轮播图 Service
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreCarouselPictureService extends CrudService<StoreCarouselPictureDao, StoreCarouselPicture> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 店铺更新修改幻灯
     *
     * @param storeCarouselPicture
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Long storeId, List<String> urls, List<String> picturePaths, List<Integer> sorts) {
        //删除当前店铺的所有轮播图片
        StoreCarouselPicture storeCarouselPicture = new StoreCarouselPicture();
        storeCarouselPicture.setStoreId(storeId);
        dao.deleteByWhere(new Wrapper(storeCarouselPicture));
        //把重新上传的轮播图片保存到库中
        List<StoreCarouselPicture> storeCarouselPictures = new ArrayList<>();
        for (int i = 0; i < picturePaths.size(); i++) {
            StoreCarouselPicture sp = new StoreCarouselPicture();
            sp.setStoreId(storeId);
            sp.setUrl(urls.get(i));
            sp.setPicturePath(picturePaths.get(i));
            sp.setSort(sorts.get(i));
            storeCarouselPictures.add(sp);
        }
        super.insertSelectiveBatch(storeCarouselPictures);
    }

}