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
package com.sicheng.upload.fileupload.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileSizeHelper;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import com.sicheng.upload.fileupload.service.SiteUploadService;

/**
 * <p>
 * 标题:文件上传组件1的服务端
 * </p>
 * <p>
 * 描述:文件上传组件1的客户端使用的是ajaxFileUpload.js
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2016年8月9日 下午6:51:16
 */
@Controller
public class AjaxFileUploadController extends BaseController {

    // 可上传的文件类型，请在这里添加(默认)
    private String type = "jpg,jpeg,png,bmp,pdf,xls,xlsx";

    // 最大文件大小,单位字节,请在这里修改（默认）
    private long uploadSize = 52428800;// 50M

    @Autowired
    private SiteUploadService siteUploadService;

    /**
     * 处理上传的入口方法 token 使用一次失效 isSafe 是否存储到安全区
     */
    @RequestMapping(value = "/common/ajaxfileupload")
    @ResponseBody
    public Object upload(@RequestParam("fileUpload") MultipartFile[] fileList, String token, String isSafe) {
        List<FileMsg> jsonList = new ArrayList<FileMsg>();
        if (fileList == null || fileList.length <= 0) {
            FileMsg fMsg = new FileMsg();
            jsonList.add(fMsg);
            fMsg.errorToken = FYUtils.fy("文件上传失败,无文件");
            return jsonList;
        }
        if (StringUtils.isBlank(token)) {
            // token为空
            FileMsg fMsg = new FileMsg();
            jsonList.add(fMsg);
            fMsg.errorToken = FYUtils.fy("文件上传失败,token为空");
            return jsonList;
        }
        // token不为空
        Boolean flag = TokenUtils.verificationToken(null, token);
        if (!flag) {
            // token不存在
            FileMsg fMsg = new FileMsg();
            jsonList.add(fMsg);
            fMsg.errorToken = FYUtils.fy("文件上传失败,token无效");
            return jsonList;
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
        // 循环处理上传
        if (fileList != null && fileList.length > 0) {
            for (int i = 0; i < fileList.length; i++) {
                FileMsg msg = new FileMsg();
                jsonList.add(msg);
                try {
                    MultipartFile file = fileList[i];
                    String fileName = file.getOriginalFilename();// 文件原名
                    String ext = FileUtils.fileSuff(fileName);// 文件扩展名
                    if (StringUtils.isBlank(ext)) {
                        ext = "jpg";
                    }
                    msg.fileName = fileName;
                    msg.filesize = (int) file.getSize();// 文件大小
                    boolean typeFlag = true;
                    // 验证上传文件的大小
                    if (msg.filesize > uploadSize) {
                        msg.errorMsg = FYUtils.fyParams("上传文件超出限制", FileSizeHelper.getHumanReadableFileSize(msg.filesize),FileSizeHelper.getHumanReadableFileSize(uploadSize));
                        typeFlag = false;
                    }
                    if (msg.filesize == 0) {
                        msg.errorMsg = FYUtils.fy("您的文件大小有误");
                        typeFlag = false;
                    }
                    // 验证上传文件类型
                    boolean status = verificationExt(ext);
                    if (!status) {
                        if (StringUtils.isNotBlank(msg.errorMsg)) {
                            msg.errorMsg = FYUtils.fyParam("文件类型不合格", msg.errorMsg);
                        } else {
                            msg.errorMsg = FYUtils.fy("您的文件类型不合格");
                        }
                        typeFlag = false;
                    }
                    if (typeFlag) {
                        // 把文件写存储系统
                        String fileStorageName = "";
                        FileStorage lf = FileStorageFcatory.get();
                        InputStream inputStream2 = file.getInputStream();
                        if (StringUtils.isNotBlank(isSafe)) {
                            // 受保护的文件（不可匿名访问）
                            fileStorageName = lf.writeSafe(inputStream2, ext);
                        } else {
                            // 普通文件（可匿名访问）
                            fileStorageName = lf.write(inputStream2, ext);
                        }
                        IOUtils.closeQuietly(inputStream2);//关闭
                        msg.fileStorageName = fileStorageName;
                        msg.errorMsg = FYUtils.fy("上传成功");
                    }
                } catch (IOException e1) {
                    logger.error("文件上传失败", e1);
                    msg.errorMsg = FYUtils.fyParam("文件上传失败", e1.toString());
                }
            }
        }
        return jsonList;
    }

    /**
     * 返回的json串的数据格式
     */
    @SuppressWarnings("unused")
    private class FileMsg {
        public String fileName; // 文件原名
        public String fileStorageName; // 文件存储名
        public int filesize = 0; // 文件大小，字节
        public String errorMsg; // 错误信息
        public String errorToken; // token错误信息
    }

    /**
     * 验证扩展名是否有效
     *
     * @param ext
     * @return
     */
    public boolean verificationExt(String ext) {
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