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

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreAlbumDao;
import com.sicheng.admin.store.entity.StoreAlbum;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 相册夹 Service
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreAlbumService extends CrudService<StoreAlbumDao, StoreAlbum> {

    @Autowired
    @Lazy
    private StoreAlbumPictureService storeAlbumPictureService;

    @Autowired
    private StoreAlbumSpaceService storeAlbumSpaceService;

    /**
     * 商家中心-管理相册-删除相册夹
     *
     * @param storeAlbum
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(StoreAlbum storeAlbum) {
        //更改店铺相册空间中的相册夹的数量，图片数量，图片占用空间
        StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceService.selectById(storeAlbum.getStoreId());
        if (storeAlbumSpace.getAlbumCount() >= 0) {
            //更改相册数量
            storeAlbumSpace.setAlbumCount(storeAlbumSpace.getAlbumCount() - 1);
        }
        StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
        storeAlbumPicture.setStoreId(storeAlbum.getStoreId());
        storeAlbumPicture.setAlbumId(storeAlbum.getAlbumId());
        int sap = storeAlbumPictureService.countByWhere(new Wrapper(storeAlbumPicture));
        if (storeAlbumSpace.getPictureCount() >= 0) {
            //更改图片数量
            storeAlbumSpace.setPictureCount(storeAlbumSpace.getPictureCount() - sap);
        }
        List<StoreAlbumPicture> storeAlbumPictures = storeAlbumPictureService.selectByWhere(new Wrapper(storeAlbumPicture));
        int pictureSpace = 0;
        if (!storeAlbumPictures.isEmpty()) {
            for (int i = 0; i < storeAlbumPictures.size(); i++) {
                pictureSpace += storeAlbumPictures.get(i).getFileSize();
            }
        }
        if (storeAlbumSpace.getPictureSpace() >= 0) {
            //更改店铺相片占用空间
            storeAlbumSpace.setPictureSpace(storeAlbumSpace.getPictureSpace() + pictureSpace);
        }
        storeAlbumSpace.setAlbumSpaceId(storeAlbum.getStoreId());
        storeAlbumSpaceService.updateById(storeAlbumSpace);
        //删除相册夹
        super.deleteById(storeAlbum.getAlbumId());
        //删除相册表的对应的图片
        StoreAlbumPicture sp = new StoreAlbumPicture();
        sp.setAlbumId(storeAlbum.getAlbumId());
        storeAlbumPictureService.deleteByWhere(new Wrapper(sp));
    }

    /**
     * 商家中心-管理相册-保存相册夹
     *
     * @param storeAlbum
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String[] albumIds, String[] sorts, String[] albumNames, StoreAlbum storeAlbum) {
        Integer albumCount1 = 0;
        for (int i = 0; i < albumNames.length; i++) {
            if (StringUtils.isNotBlank(albumIds[i])) {
                //有id代表为更新
                StoreAlbum sa = new StoreAlbum();
                StoreAlbum sa1 = new StoreAlbum();
                sa1.setAlbumId(Long.parseLong(albumIds[i]));
                sa1.setStoreId(storeAlbum.getStoreId());
                sa.setSort(Integer.parseInt(sorts[i]));
                sa.setAlbumName(albumNames[i]);
                super.updateByWhereSelective(sa, new Wrapper(sa1));
            } else {
                //无id代表为新增
                StoreAlbum sa = new StoreAlbum();
                sa.setSort(Integer.parseInt(sorts[i]));
                sa.setAlbumName(albumNames[i]);
                sa.setStoreId(storeAlbum.getStoreId());
                sa.setPictureCount(0);
                super.insertSelective(sa);
                albumCount1 += 1;
            }
        }
        //更新相册空间中的相册数量
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreAlbumSpace storeAlbumSpace1 = storeAlbumSpaceService.selectById(userSeller.getStoreId());
        if (storeAlbumSpace1 != null) {
            Integer albumCount2 = storeAlbumSpace1.getAlbumCount() == null ? 0 : storeAlbumSpace1.getAlbumCount();
            StoreAlbumSpace storeAlbumSpace2 = new StoreAlbumSpace();
            storeAlbumSpace2.setAlbumSpaceId(userSeller.getStoreId());
            storeAlbumSpace2.setAlbumCount(albumCount1 + albumCount2);
            storeAlbumSpaceService.updateByIdSelective(storeAlbumSpace2);
        }
    }

    /**
     * 更新相册图片数量
     *
     * @param albumId  相册id
     * @param type     类型(0减，1加)
     * @param picCount 增加或减少数量
     * @return
     */
    public int updatePictureCount(Long albumId, String type, Integer picCount) {
        int count = 0;
        if (albumId == null || StringUtils.isBlank(type)) {
            return count;
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        Wrapper wrapper = new Wrapper();
        wrapper.and("store_id = ", userSeller.getStoreId()).and("album_id=", albumId);
        //StoreAlbum storeAlbum=super.selectOne(wrapper);
        //Long pictureCount= storeAlbum.getPictureCount();
        StoreAlbum storeAlbum2 = new StoreAlbum();
        storeAlbum2.setPictureCount(picCount);
        storeAlbum2.setAlbumId(albumId);
        storeAlbum2.setStoreId(userSeller.getStoreId());
        if ("0".equals(type)) {
            count += dao.reducePicCountByAlbum(albumId, picCount);
        }
        if ("1".equals(type)) {
            count += dao.addPicCountByAlbum(albumId, picCount);
        }
        return count;
    }

}