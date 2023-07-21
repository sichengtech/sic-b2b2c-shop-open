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
package com.sicheng.admin.product.entity;

import com.sicheng.admin.store.dao.StoreAlbumPictureDao;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 商品图片多对多中间表 Entity 子类，请把你的业务代码写在这里
 *
 * @author 赵磊
 * @version 2017-02-07
 */
public class ProductPictureMapping extends ProductPictureMappingBase<ProductPictureMapping> {

    private static final long serialVersionUID = 1L;

    public ProductPictureMapping() {
        super();
    }

    public ProductPictureMapping(Long id) {
        super(id);
    }

    //一对一映射
    private StoreAlbumPicture storeAlbumPicture; //一个中间数据--一张图片

    public StoreAlbumPicture getStoreAlbumPicture() {
        if (storeAlbumPicture == null) {
            StoreAlbumPictureDao dao = SpringContextHolder.getBean(StoreAlbumPictureDao.class);
            storeAlbumPicture = dao.selectById(this.getImgId());
        }
        return storeAlbumPicture;
    }

    public void setStoreAlbumPicture(StoreAlbumPicture storeAlbumPicture) {
        this.storeAlbumPicture = storeAlbumPicture;
    }

}