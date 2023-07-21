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
package com.sicheng.wap.web.fy.web;

import com.sicheng.common.config.Global;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 翻译Controller
 * 所属模块：
 *
 * @author fxx
 * @version 2019-11-22
 */
@Controller
@RequestMapping(value = "${wapPath}/fy")
public class FYController extends BaseController {
    /**
     * 获取国际化properties文件内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "poperties")
    public Map<String, Object> poperties() {
        Map<String, String> cnMap = FYUtils.getFYProperties("msg_zh_CN.properties");
        Map<String, String> enMap = FYUtils.getFYProperties("msg_en_US.properties");
        
        //字典配置文件
        Map<String, String> dictCNMap = FYUtils.getFYProperties("msg/dict_zh_CN.properties");
        Map<String, String> dictENMap = FYUtils.getFYProperties("msg/dict_en_US.properties");
        //MyUpload配置文件
        Map<String, String> uploadCNMap = FYUtils.getFYProperties("msg/upload_zh_CN.properties");
        Map<String, String> uploadENMap = FYUtils.getFYProperties("msg/upload_en_US.properties");
        dictCNMap.putAll(cnMap);
        dictENMap.putAll(enMap);
        dictCNMap.putAll(uploadCNMap);
        dictENMap.putAll(uploadENMap);
        
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("cnMap",dictCNMap);
        dataMap.put("enMap",dictENMap);
        dataMap.put("defaultLocale", Global.getConfig("sys.defaultLocale"));
        return dataMap;
    }

}