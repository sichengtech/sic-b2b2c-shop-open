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

import com.sicheng.admin.sys.entity.SysToken;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: SysTokenController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2018年3月23日 下午16:21:03
 */
@Controller
public class SysTokenController extends BaseController {

    /**
     * 商家后台系统获取token(令牌)
     *
     * @return
     */
    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/upload/getToken")
    public Object getToken() {
        SysToken sysToken = TokenUtils.generateToken(null, "10");
        PrintWriter print;
        try {
            HttpServletRequest request = R.getRequest();
            HttpServletResponse response = R.getResponse();
            response.setContentType("application/json");//发送json数据需要设置contentType
            print = response.getWriter();
            String jsonp = request.getParameter("callback");//callback为回调函数名，要和js名保持一致
            String text = "";
            if (jsonp != null) {
                text = jsonp + "({\"token\":" + "'" + sysToken.getToken() + "'" + "})";
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("token", sysToken.getToken());
                text = JsonMapper.toJsonString(map);
            }
            print.write(text);
            print.close();
        } catch (IOException e) {
            logger.debug("获取token错误", e);
        }
        return null;
    }

}
