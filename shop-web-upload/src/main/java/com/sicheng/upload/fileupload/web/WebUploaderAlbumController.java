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
package com.sicheng.upload.fileupload.web;

import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.store.dao.StoreAlbumDao;
import com.sicheng.admin.store.dao.StoreAlbumPictureDao;
import com.sicheng.admin.store.dao.StoreAlbumSpaceDao;
import com.sicheng.admin.store.entity.StoreAlbum;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.store.entity.StoreAlbumSpace;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.*;
import com.sicheng.common.web.BaseController;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import com.sicheng.upload.fileupload.service.SiteUploadService;
import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.ImageProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题:WebUploaderController文件上传组件2的服务端
 * </p>
 * <p>
 * 描述:文件上传组件2的客户端使用的是WebUpload WebUploader是由Baidu
 * WebFE(FEX)团队开发的一个简单的以HTML5为主，FLASH为辅的现代文件上传组件。
 * 在现代的浏览器里面能充分发挥HTML5的优势，同时又不摒弃主流IE浏览器，沿用原来的FLASH运行时， 兼容IE6+，iOS 6+, android
 * 4+。两套运行时，同样的调用方式，可供用户任意选用。
 * </p>
 * <p>
 *
 * </p>
 *
 * @author cl
 * @version 2017年3月31日 上午10:47:53
 */
@Controller
public class WebUploaderAlbumController extends BaseController {

    // 可上传的文件类型，请在这里添加(默认)
    private String type = "jpg,jpeg,png,bmp,pdf,xls,xlsx";

    // 最大文件大小,单位字节,请在这里修改（默认）
    private long uploadSize = 52428800;// 50M

    @Autowired
    private SiteUploadService siteUploadService;
    @Autowired
    private StoreAlbumDao storeAlbumDao;
    @Autowired
    private StoreAlbumPictureDao storeAlbumPictureDao;
    @Autowired
    private StoreAlbumSpaceDao storeAlbumSpaceDao;
    @Autowired
    private ShopCache cache;
    @Autowired
    private ImageProcess imageProcess;// 图片处理接口

    /**
     * 处理上传的入口方法
     */
    @RequestMapping(value = "/upload/webUploadAlbumServer")
    @ResponseBody
    public Object upload(MultipartFile file, @Param("albumId") Long albumId, @Param("token") String token)
            throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        // 数据检查，验证token的合法性
        // String token = R.get("token");
        if (StringUtils.isBlank(token)) {// token为空
            map.put("state", "ERROR");
            map.put("statusText", FYUtils.fyParams("文件上传失败,token为空"));
            return map;
        }
        Boolean flag = TokenUtils.verificationToken(null, token);
        if (!flag) {// token不存在
            map.put("state", "ERROR");
            map.put("statusText", FYUtils.fyParams("文件上传失败,token无效"));
            return map;
        }

        // 数据检查，验证相册夹albumId必须非空
        if (albumId == null || albumId <= 0) {
            map.put("state", "ERROR");
            map.put("statusText", FYUtils.fyParams("请指定上传到哪一个相册夹，相册夹不能为空"));
            return map;
        }

        // 获取平台后台设置的上传格式，如果没设置用默认的格式
        List<SiteUpload> siteUploads = siteUploadService.selectByWhere(new Wrapper(new SiteUpload()));
        if (!siteUploads.isEmpty()) {
            SiteUpload siteUpload = siteUploads.get(0);
            if (StringUtils.isNotBlank(siteUpload.getType())) {
                type = siteUpload.getType();
            }
            if (siteUpload.getUploadSize() != 0) {
                uploadSize = siteUpload.getUploadSize();
            }
        }
        try {
            String fileName = file.getOriginalFilename();// 文件原名
            String ext = FileUtils.fileSuff(fileName);// 文件扩展名
            // byte[] data = file.getBytes();//文件内容
            // int length = data.length;//文件大小
            Long length = file.getSize();// 文件大小
            boolean typeFlag = true;
            if (length > uploadSize) {
                // 判断上传文件大小是否满足
                map.put("status", "error");
                map.put("statusText", FYUtils.fyParams("文件大小超出范围",FileSizeHelper.getHumanReadableFileSize(length),FileSizeHelper.getHumanReadableFileSize(uploadSize)));

                typeFlag = false;
                return map;
            }
            boolean status = verificationExt(ext);
            if (!status) {
                // 判断上传文件扩展名是否满足
                if (StringUtils.isNotBlank(map.get("statusText"))) {
                    map.put("state", "ERROR");
                    map.put("statusText", map.get("statusText") + ","+FYUtils.fyParams("文件类型不合格"));
                } else {
                    map.put("state", "ERROR");
                    map.put("statusText", FYUtils.fyParams("文件类型不合格"));
                }
                typeFlag = false;
                return map;
            }
            // 获取当前相册的店铺id
            StoreAlbum storeAlbum = storeAlbumDao.selectById(albumId);
            if (storeAlbum == null) {
                map.put("state", "ERROR");
                map.put("statusText", FYUtils.fyParams("相册不存在"));
                return map;
            }
            Long storeId = storeAlbum.getStoreId();
            // 验证相册图片空间是否剩余
            StoreAlbumSpace storeAlbumSpace = storeAlbumSpaceDao.selectById(storeId);
            if (storeAlbumSpace.getPictureSpace() + length > storeAlbumSpace.getTotalSpace()) {
                if (StringUtils.isNotBlank(map.get("statusText"))) {
                    map.put("state", "ERROR");
                    map.put("statusText", map.get("statusText") + ","+FYUtils.fyParams("相册空间已满，请升级店铺等级"));
                } else {
                    map.put("state", "ERROR");
                    map.put("statusText",FYUtils.fyParams("相册空间已满，请升级店铺等级"));
                }
                typeFlag = false;
            }
            if (typeFlag) {
                InputStream inputStream = file.getInputStream();
                ImageInfo imageInfo = imageProcess.getImageInfo(inputStream);
                IOUtils.closeQuietly(inputStream);// 关闭
                String pixel = imageInfo.getWidth() + "X" + imageInfo.getHeight();

                // 把文件写存储系统
                InputStream inputStream2 = file.getInputStream();
                FileStorage lf = FileStorageFcatory.get();
                String fileStorageName = lf.write(inputStream2, ext);
                IOUtils.closeQuietly(inputStream2);// 关闭
                // 保存图片地址到相册图片表
                StoreAlbumPicture storeAlbumPicture = new StoreAlbumPicture();
                storeAlbumPicture.setStoreId(storeId);
                storeAlbumPicture.setAlbumId(albumId);
                storeAlbumPicture.setPath(fileStorageName);
                storeAlbumPicture.setFileSize(length.intValue());
                storeAlbumPicture.setPixel(pixel);
                storeAlbumPictureDao.insertSelective(storeAlbumPicture);
                // 修改店铺相册空间信息表
                StoreAlbumSpace storeAlbumSpace2 = new StoreAlbumSpace();
                storeAlbumSpace2.setPkMode(1);
                storeAlbumSpace2.setAlbumSpaceId(storeId);
                storeAlbumSpace2.setPictureCount(1);
                storeAlbumSpace2.setPictureSpace(length.intValue());
                storeAlbumSpaceDao.addPicMsgByAlbumSpecId(storeAlbumSpace2);
                // 修改相册表中的信息
                storeAlbumDao.addPicCountByAlbum(albumId, 1);
                // 给页面回提示
                map.put("state", "SUCCESS");
                map.put("statusText", FYUtils.fyParams("上传完成"));
                map.put("original", fileName);
                map.put("type", ext);
                map.put("size", String.valueOf(length));
                map.put("url", fileStorageName);
                map.put("pixel", pixel);
                map.put("pictureId", String.valueOf(storeAlbumPicture.getPictureId()));
            }
        } catch (Exception e) {
            logger.error(FYUtils.fyParams("文件上传失败"), e);
            map.put("state", "ERROR");
            map.put("statusText", FYUtils.fyParams("文件上传失败"));
        }
        return JsonMapper.toJsonString(map);
    }

    /**
     * 验证扩展名是否有效
     *
     * @param ext
     * @return
     */
    private boolean verificationExt(String ext) {
        boolean flag = false;
        String[] typeExt = type.split(",");
        for (int j = 0; j < typeExt.length; j++) {
            if (typeExt[j].equalsIgnoreCase(ext)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}