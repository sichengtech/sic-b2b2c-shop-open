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
package com.sicheng.upload.weixin.web;

import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: DloadImgController</p>
 * <p>描述: 从微信服务器下载图片</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2018年1月8日 下午3:52:25
 */
@Controller
public class DloadImgController extends BaseController {

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileexpandedName(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType)) {
            fileEndWitsh = ".jpg";
        } else if ("audio/mpeg".equals(contentType)) {
            fileEndWitsh = ".mp3";
        } else if ("audio/amr".equals(contentType)) {
            fileEndWitsh = ".amr";
        } else if ("video/mp4".equals(contentType)) {
            fileEndWitsh = ".mp4";
        } else if ("video/mpeg4".equals(contentType)) {
            fileEndWitsh = ".mp4";
        }
        return fileEndWitsh;
    }

    /**
     * 获取媒体文件
     *
     * @param accessToken 接口访问凭证
     * @param mediaIds    多个图片id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/common/downloadImage")
    public Map<String, Object> downloadImage(String accessToken, String mediaIds, String token) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(accessToken)) {
            map.put("status", false);
            map.put("message", "文件下载失败,accessToken不能为空");
            return map;
        }
        if (StringUtils.isBlank(mediaIds)) {
            map.put("status", false);
            map.put("message", "imgId不能为空");
            return map;
        }
        if (StringUtils.isBlank(token)) {// token为空
            map.put("state", false);
            map.put("statusText", "文件下载失败,token为空");
            return map;
        }
        Boolean flag = TokenUtils.verificationToken(null, token);
        if (!flag) {// token不存在
            map.put("state", false);
            map.put("statusText", "文件下载失败,token无效");
            return map;
        }
        if (",".equals(mediaIds.substring(0, 1))) {
            mediaIds = mediaIds.substring(1);
        }
		/*String accessToken = "";
		Map<String,String> tokenMap=AccessTokenUtils.getAccessToken();
		if(tokenMap!=null){
			accessToken =tokenMap.get("access_token");
		}*/
        String localFilePath = "";
        logger.info("mediaIds：" + mediaIds);
        String[] media = mediaIds.split(",");
        logger.info("media：" + media);
        if (media.length > 0) {
            for (int i = 0; i < media.length; i++) {
                // 拼接请求地址
                String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + media[i];
                //requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", media[i]);
                URL url;
                BufferedInputStream bis = null;
                //ByteArrayOutputStream fos = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(requestUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setRequestMethod("GET");
                    // 根据内容类型获取扩展名
                    String fileExt = getFileexpandedName(conn.getHeaderField("Content-Type"));
                    FileStorage lf = FileStorageFcatory.get();
                    // 将mediaId作为文件名
                    bis = new BufferedInputStream(conn.getInputStream());
                    //byte[] buf = new byte[8096];
                    //int size = 0;
                    //fos=new ByteArrayOutputStream ();
                    //while ((size = bis.read(buf)) != -1){
                    //fos.write(buf, 0, size);
                    //}
                    localFilePath += lf.write(bis, fileExt) + ",";
                } catch (Exception e) {
                    logger.error("下载图片失败", e);
                    map.put("status", false);
                    map.put("message", "下载图片出现错误");
                    return map;
                } finally {
                    try {
                        //fos.close();
                        bis.close();
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(localFilePath)) {
            if (",".equals(localFilePath.substring(localFilePath.length() - 1))) {
                localFilePath = localFilePath.substring(0, localFilePath.length() - 1);
            }
        }
        map.put("status", true);
        map.put("message", "下载成功");
        map.put("path", localFilePath);
        return map;
    }
}