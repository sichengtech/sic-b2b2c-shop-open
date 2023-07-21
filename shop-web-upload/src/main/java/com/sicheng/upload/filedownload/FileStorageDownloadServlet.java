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
package com.sicheng.upload.filedownload;

import com.sicheng.common.config.Global;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.utils.IOUtils;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>标题: 从文件存储系统下载文件，图片可实时生成缩略图</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月12日 下午2:27:12
 */
public class FileStorageDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String cacheControl = "public, max-age=604800";//缓存7天

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fileOutputStream(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fileOutputStream(request, response);
    }

    public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求url路径，如：/upload/filestorage/10/63/35/9d68e4c0fcc14f15abf233ee1e746a1e.jpg@!300x200
        String url = req.getRequestURI().trim().toLowerCase();

        //查找url是否包含@！，这是一种缩图的格式，shop商城已使用了这种格式
        int haveFlag1 = url.indexOf("@!");
        int haveFlag2 = url.indexOf('@');

        String basePath = Global.getConfig("filestorage.dir");
        int index = url.indexOf(basePath);

        //存储路径，示例：01/85/52/ec2c5fdc861540ac9696234d57df4455.jpg@220x220
        String storageUrl = url;
        if (index != -1) {
            storageUrl = url.substring(index + basePath.length());
        }

        //文件路径，如：/10/63/35/9d68e4c0fcc14f15abf233ee1e746a1e.jpg
        String filePath = "";

        //截取文件路径，如:/10/63/35/9d68e4c0fcc14f15abf233ee1e746a1e.jpg
        if (haveFlag1 != -1) {
            filePath = url.substring(index + basePath.length(), haveFlag1);
        } else if (haveFlag2 != -1) {
            filePath = url.substring(index + basePath.length(), haveFlag2);
        } else {
            if (index != -1) {
                filePath = url.substring(index + basePath.length());
            } else {
                filePath = url;
            }
        }

        //权限检查,访问safe区的文件，需要accessKey
        if (filePath.startsWith("/safe")) {
            String accessKey = req.getParameter("accessKey");
            boolean bl = AccessKey.verification(accessKey);
            if (!bl) {
                //无访问权限,直接响应404
                write403(resp, filePath);
                return;
            }
        }

        // 读取文件供下载，如果是图片并支持实时缩图
        FileStorage fileStorage = FileStorageFcatory.get();
        InputStream inp = fileStorage.download(storageUrl);
        if (inp == null) {
            write404(resp, storageUrl);
            return;
        } else {
            BufferedInputStream in = new BufferedInputStream(inp);
            BufferedOutputStream os = new BufferedOutputStream(resp.getOutputStream());
            IOUtils.copy(in, os);
            IOUtils.closeQuietly(os);//关闭
            IOUtils.closeQuietly(in);//关闭
            return;
        }
    }

    /**
     * 当文件不存在时返回404
     *
     * @param resp
     * @param fileAllPath
     */
    private void write404(HttpServletResponse resp, String fileAllPath) {
        resp.setContentType("text/html;charset=UTF-8");//响应头
        if (logger.isDebugEnabled()) {
            logger.debug("原图不存在,直接响应404," + fileAllPath);
        }
        resp.setStatus(404);
        try {
            resp.getWriter().write("<html>File not find,404</html>");
        } catch (IOException e) {
            logger.error("write404", e);
        }
    }

    /**
     * 当无访问权限时返回403
     *
     * @param resp
     * @param fileAllPath
     */
    private void write403(HttpServletResponse resp, String fileAllPath) {
        resp.setContentType("text/html;charset=UTF-8");//响应头
        if (logger.isDebugEnabled()) {
            logger.debug("无访问权限," + fileAllPath);
        }
        resp.setStatus(403);
        try {
            resp.getWriter().write("<html>无访问权限,403</html>");
        } catch (IOException e) {
            logger.error("write403", e);
        }
    }
}