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
package com.sicheng.common.utils;

import com.sicheng.common.web.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 翻译工具类（获取国际化配置文件的值）
 * @author fxx
 * @version 2019-11-20
 */
public class FYUtils {
	private static Logger logger = LoggerFactory.getLogger(FYUtils.class);

	/**
	 * 根据键和参数获取国际化值
	 * @param key 键
	 * @param params 参数,可变参数
	 * @return
	 */
	public static String fyParams(String key, String... params){
		String message=key;
		try {
			RequestContext requestContext = new RequestContext(R.getRequest());
	        message = requestContext.getMessage(key,params);
		} catch (Exception e) {
			message=key;
			logger.warn("获取国际化值发生错误,key="+key,e);
		}
		return message;
	}

	/**
	 * 根据键获取国际化值
	 * @param key 键
	 * @return
	 */
	public static String fy(String key){
		return fyParams(key);
	}

	/**
	 * 根据键获取国际化值
	 * @param key 键
	 * @param param 替换参数，替换”来自{0}的通知“中{0}部分
	 * @return
	 */
	public static String fyParam(String key,String param){
		return fyParams(key,param);
	}

	/**
	 * 获取resources文件夹下的porperties文件中的内容
	 * @param name properties文件名称
	 * @return
	 */
	public static Map<String, String> getFYProperties(String name) {
		Map<String, String> map = new HashMap<>();
		InputStream in = null;
		Properties p = new Properties();
		try {
			in = FYUtils.class.getClassLoader().getResourceAsStream(name);
			p.load(new InputStreamReader(in, StandardCharsets.UTF_8));//properties文件，不用unicode编码的实现方法
		} catch (Exception e) {
			logger.error("获取resources文件夹下的porperties文件中的内容发生错误，name="+name,e);
		}
		Set<Map.Entry<Object, Object>> entrySet = p.entrySet();
		for (Map.Entry<Object, Object> entry : entrySet) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}

}
