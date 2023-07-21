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
package com.sicheng.upload.component;

import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.utils.FileSizeHelper;
import com.sicheng.common.web.R;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring mvc上传文件超过设定大小异常处理客户端无响应
 * <p>
 * 在Spring mvc中，我使用了CommonsMultipartResolver来实现文件上传功能,上传本身没有问题。
 * 限制上传文件大小为20M（maxUploadSize=20M），当上传的文件大小超过20M后，浏览器报错：“连接被重置”，无法给用户一个友好提示页面。
 * <p>
 * 虽然已经使用以下了两种方案：@ExceptionHandler(Exception.class) 和 SimpleMappingExceptionResolver
 * 来捕捉并处理MaxUploadSizeExceededException异常，并向浏览器输出了一个友好提示页面.
 * <p>
 * 但出现了两种不满意的结果：
 * 1、浏览器不接收服务端发来响应,浏览器会报告ERR_CONNECTION_RESET错误)  “连接被重置”。试过各种浏览器(FF、Chrome、IE11)，问题同样存在。
 * 2、捕捉了多次同类型的异常，服务端向浏览器输出了多次html。
 * <p>
 * 解决方案：
 * 首先，把maxUploadSize 设置大一点，让它不会抛异常出来，也就是放过不处理。
 * 然后创建一个拦截器控制上传文件大小，然后抛出同样的异常出来，或者也可以直接在拦截器中处理异常
 */
public class FileUploadInterceptor implements HandlerInterceptor {
    private long maxSize;//可上传文件的最大大小，单位字节

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request != null && ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext ctx = new ServletRequestContext(request);
            long requestSize = ctx.contentLength();
            if (requestSize > maxSize) {
                //上传的文件大小超过限制
                String __fileUpload = request.getParameter("__fileUpload");

                // 如果是上传组件进行的上传，返回json
                if (__fileUpload != null) {
                    //TODO 输出json
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("imgname", "您上传的文件");
                    map.put("type", "2");
                    map.put("status", "2");
                    map.put("errMsg", "单次上传文件总大小超过" + FileSizeHelper.getHumanReadableFileSize(maxSize) + ",禁止上传！");
                    list.add(map);
                    String json = JsonMapper.getInstance().toJson(list);
                    R.writeJson(response, json, "UTF-8");
                    return false;
                } else {
                    //进入错误提示页
                    R.forward("/views/error/uploadError.jsp");
                    return false;
                }
                //throw new MaxUploadSizeExceededException(maxSize);
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}