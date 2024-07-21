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
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FileSizeHelper;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.IOUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import com.sicheng.upload.fileupload.service.SiteUploadService;
import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.ImageProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 *  @author zhangjiali
 *  @version 2018年5月30日 上午10:07:31
 */

@Controller
public class WebUploaderController extends BaseController {

    // 可上传的文件类型，请在这里添加(默认)
    private String type = "jpg,jpeg,png,bmp,pdf,xls,xlsx";

    // 最大文件大小,单位字节,请在这里修改（默认）
    private long uploadSize = 52428800;// 50M

    @Autowired
    private SiteUploadService siteUploadService;
    @Autowired
    private ImageProcess imageProcess;// 图片处理接口

    /**
     *  处理上传的入口方法 
     *  @param file 上传文件
     *  @param token 上传文件的token
     *  @param isSafe 文件是够存入安全区
     *  @return
     *  @throws IOException
     *  
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/upload/webUploadServer")
    @ResponseBody
    public Object upload(MultipartFile file, @Param("token") String token, @Param("isSafe") boolean isSafe)
            throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        // 数据检查，验证token的合法性
        if (StringUtils.isBlank(token)) {// token为空
            map.put("state", "ERROR");
            map.put("statusText", "文件上传失败,token为空");
            return map;
        }
        Boolean flag = TokenUtils.verificationToken(null, token);
        if (!flag) {// token不存在
            map.put("state", "ERROR");
            map.put("statusText", "文件上传失败,token无效");
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
            Long length = file.getSize();// 文件大小
            boolean typeFlag = true;
            if (length > uploadSize) {
                // 判断上传文件大小是否满足
                map.put("status", "error");
                map.put("statusText", "您的文件大小是" + FileSizeHelper.getHumanReadableFileSize(length) + ",超过"
                        + FileSizeHelper.getHumanReadableFileSize(uploadSize) + "的最大限制");
                typeFlag = false;
                return map;
            }
            boolean status = verificationExt(ext);
            if (!status) {
                // 判断上传文件扩展名是否满足
                if (StringUtils.isNotBlank(map.get("statusText"))) {
                    map.put("state", "ERROR");
                    map.put("statusText", map.get("statusText") + ",文件类型不合格");
                } else {
                    map.put("state", "ERROR");
                    map.put("statusText", "文件类型不合格");
                }
                typeFlag = false;
                return map;
            }
            if (typeFlag) {
                String pixel = null;
                try {
                    InputStream inputStream = file.getInputStream();
                    ImageInfo imageInfo = imageProcess.getImageInfo(inputStream);
                    IOUtils.closeQuietly(inputStream);// 关闭
                    pixel = imageInfo.getWidth() + "X" + imageInfo.getHeight();
                } catch (Exception e) {
                    logger.error("获取图片宽高异常：", e);
                }
                // 把文件写存储系统
                InputStream inputStream2 = file.getInputStream();
                FileStorage lf = FileStorageFcatory.get();
                String fileStorageName = "";
                if (isSafe) {
                    // 隐秘文件
                    fileStorageName = lf.writeSafe(inputStream2, ext);
                } else {
                    // 普通文件
                    fileStorageName = lf.write(inputStream2, ext);
                }
                IOUtils.closeQuietly(inputStream2);// 关闭
                // 给页面回提示
                String fileUrl = R.getRequest().getScheme() + "://" + R.getRequest().getServerName() + ":" + R.getRequest().getServerPort() +
                        R.getRequest().getContextPath() + Global.getConfig("filestorage.dir") + fileStorageName;
                map.put("state", "SUCCESS");//state文件上传状态：SUCCESS上传成功、ERROR上传失败
                map.put("statusText", "上传成功");//statusText上传返回的内容
                map.put("original", fileName);//original上传源文件名
                map.put("type", ext);//type文件类型
                map.put("size", String.valueOf(length));//size上传文件大小，单位：B
                map.put("url", fileUrl);//upload工程不是单独的系统，url只是文件访问地址，如果upload工程是单独的系统，url是文件访问地址也是文件存储路径，
                map.put("path", fileStorageName);//path文件存储路径
                map.put("pixel", pixel);//pixel文件宽高
            }
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            map.put("state", "ERROR");
            map.put("statusText", "上传失败");
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