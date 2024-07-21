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

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>标题: UEditorController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月14日 下午6:53:27
 */
@Controller
public class UEditorController extends BaseController {

    /**
     * 处理上传的入口方法
     */
    @RequestMapping(value = "/upload/ueditorServer")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //action=config表示请求配置文件，不验证accessKey
        String action = request.getParameter("action");
        if (!"config".equals(action)) {
            //验证accessKey,不合格就返回错误标识
            String accessKey = request.getParameter("accessKey");
            if (StringUtils.isBlank(accessKey) || (!AccessKey.verification(accessKey))) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Type", "text/html");
                PrintWriter out = response.getWriter();
                out.write(new BaseState(false, AppInfo.ACCESSKEY_ERROR).toJSONString());
                return;
            }
        }

        //以下代码是ueditor自带的，原样保留在这里，未做改动
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        String rootPath = request.getContextPath();
        PrintWriter out = response.getWriter();
        out.write(new ActionEnter(R.getRequest(), rootPath).exec());
    }
}
