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

package com.sicheng.wap.web.api;

import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 用于测试发生异常时的返回值 
 * @author zhaolei
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class ErrorTestController extends BaseController {

    /**
      * 产生一个异常。客户端一般是app通过http协议来调用服务端，当服务端发生异常时，客户端应收到特定格式的数据
      * 客户端是PC浏览器，当服务端发生异常时，客户端应收到一个HTML页面，里面有异常信息
      * 客户端是wap站，当服务端发生异常时，客户端应收到一个“包含错误提示语的字符串”。
      * 客户端是App（是uni-app开发的app、小程序、h5），当服务端发生异常时，客户端应收到一个json,格式是《统一数据包装体和状态码》
      * @return
     */
    @RequestMapping(value = "/{version}/errorTest")
    @ResponseBody
    public void error() {
        int a = 0;
        int b = 1;
        int c = b / a;//产生一个被0除的异常
        System.out.println(c);
    }

    @RequestMapping(value = "/{version}/restTest")
    @ResponseBody
    public Object restTest(String a, Long b, String[] c, ArrayList<String> d) { //不报错，ArrayList中无值
        System.out.println("进入了restTest方法");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        map.put("a", R.get("a"));
        map.put("b", R.get("b"));
        map.put("c", c);
        map.put("d", d);
        System.out.println("进入了restTest方法,所有参数:" + R.getMapAll());
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, "测试成功", map, null);
    }

}
