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
package com.sicheng.seller.store.service;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreAlbumPictureDao;
import com.sicheng.admin.store.dao.StoreAlbumSpaceDao;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家相册图片 Service
 *
 * @author cl
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreAlbumPictureService extends CrudService<StoreAlbumPictureDao, StoreAlbumPicture> {

    //@Autowired
    //private StoreAlbumSpaceService storeAlbumSpaceService;
    @Autowired
    private StoreAlbumService storeAlbumService;
    @Autowired
    private StoreAlbumSpaceDao storeAlbumSpaceDao;

    /**
     * 相册空间删除图片
     *
     * @param pictureId 一个或多个图片id,多个图片id用逗号分割
     * @param albumId   相册id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deletebatchpic(String pictureId, Long albumId) {
        int count = 0;
        if (StringUtils.isBlank(pictureId) || albumId == null) {
            return count;
        }
        String[] pictureIds = pictureId.split(",");
        List<Object> pictureIdList = new ArrayList<>();
        for (int i = 0; i < pictureIds.length; i++) {
            pictureIdList.add(pictureIds[i]);
        }
        //修改相册空间信息表
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        Wrapper picWrapper = new Wrapper();
        picWrapper.and("a.picture_id in ", pictureIdList);
        picWrapper.and("store_id = ", userSeller.getStoreId());
        List<StoreAlbumPicture> storeAlbumPictures = dao.selectByWhere(null, picWrapper);
        Integer fileSize = 0;
        if (!storeAlbumPictures.isEmpty()) {
            for (int i = 0; i < storeAlbumPictures.size(); i++) {
                fileSize += storeAlbumPictures.get(i).getFileSize();
            }
        }
        StoreAlbumSpace storeAlbumSpace2 = new StoreAlbumSpace();
        storeAlbumSpace2.setPkMode(1);
        storeAlbumSpace2.setAlbumSpaceId(userSeller.getStoreId());
        storeAlbumSpace2.setPictureCount(pictureIdList.size());
        storeAlbumSpace2.setPictureSpace(fileSize);
        storeAlbumSpaceDao.reducePicMsgByAlbumSpecId(storeAlbumSpace2);
        //删除店铺相册图片表
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.album_id =", albumId);
        wrapper.and("a.store_id = ", userSeller.getStoreId());
        wrapper.and("a.picture_id in", pictureIdList);
        count += dao.deleteByWhere(wrapper);
        //更新商家相册图片数量
        count += storeAlbumService.updatePictureCount(albumId, "0", pictureIdList.size());
        return count;
    }

    /**
     * 移动图片
     *
     * @param pictureId     一个或多个图片id，多个图片id用逗号分割
     * @param albumId       相册id
     * @param targetAlbumId 目标相册id
     */
    @Transactional(rollbackFor = Exception.class)
    public int removePicture(String pictureId, Long albumId, Long targetAlbumId) {
        int count = 0;
        if (StringUtils.isBlank(pictureId) || albumId == null || targetAlbumId == null) {
            return count;
        }
        String[] pictureIds = pictureId.split(",");
        List<Object> pictureList = new ArrayList<>();
        for (int i = 0; i < pictureIds.length; i++) {
            pictureList.add(pictureIds[i]);
        }
        if (pictureList.size() == 0) {
            return count;
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //更新图片所属相册
        Wrapper wrapper = new Wrapper();
        wrapper.and("store_id = ", userSeller.getStoreId());
        wrapper.and("picture_id in", pictureList);
        StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
        storeAlbumPicture.setAlbumId(targetAlbumId);
        count += dao.updateByWhereSelective(storeAlbumPicture, wrapper);
        //减少原相册图片数量
        count += storeAlbumService.updatePictureCount(albumId, "0", pictureList.size());
        //增加新相册图片数量
        count += storeAlbumService.updatePictureCount(targetAlbumId, "1", pictureList.size());
        return count;
    }

}