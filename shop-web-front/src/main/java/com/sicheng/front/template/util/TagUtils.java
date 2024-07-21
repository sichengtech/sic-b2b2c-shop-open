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
package com.sicheng.front.template.util;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 标题: TagUtils 自定义函数的的工具类</p>
 * <p> 描述:</p>
 * <p> 公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年5月21日 下午5:17:45
 */
public class TagUtils {
    public static final String LIMIT_KEY = "limit";
    public static final int LIMIT_DEFAULT = 10;
    public static final String PAGE_NO_KEY = "pageNo";
    public static final String PAGE_SIZE_KEY = "pageSize";
    public static final String PAGE_COUNT_KEY = "pageCount";

    /**
     * 原生参数 转 json参数
     * 获取自定义标签的参数 默认取第0个
     *
     * @param args beetl标签或函数的原生参数
     * @return map类型的参数
     */
    public static Map<String, Object> getTagParamMap(Object[] args) {
        return getTagParamMap(args, 0);
    }

    /**
     * 原生参数 转 json参数
     * 获取自定义标签的参数
     *
     * @param args  beetl标签或函数的原生参数
     * @param index 下标
     * @return map类型的参数
     */
    public static Map<String, Object> getTagParamMap(Object[] args, int index) {
        if (args == null || args.length == 0) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> tagParam = null;
        if (args.length >= index) {
            Object obj = args[index];
            if (obj instanceof Map) {
                tagParam = (Map<String, Object>) obj;
            }
        } else {
            throw new RuntimeException("Object[] args,没有第" + index + "个参数，数组下标越界");
        }
        if (tagParam == null) {
            tagParam = new HashMap<String, Object>();
        }
        return tagParam;
    }

    /**
     * 从入参中取得Page分页对象 先创建一个有默认值的Page对象， 如果tagParam入参有分页相关的参数，则覆盖Page中的原值。
     * 总是能返一个可用的、有值的Page对象。
     *
     * @param tagParam 入参
     * @return Page 分页对象
     */
    public static Page<Object> getPage(Map<String, Object> tagParam) {
        // 创建分页对象
        HttpServletRequest request = R.getRequest();// 分页需要request
        HttpServletResponse response = R.getResponse();// 分页有可能需要response，就是用于写cookie
        Page<Object> page = new Page<Object>(request, response);

        // 处理参数 PAGE_NO_KEY
        Integer pageNo = getInteger(tagParam, TagUtils.PAGE_NO_KEY);
        if (pageNo != null) {
            page.setPageNo(pageNo);
        }

        // 处理参数 PAGE_SIZE_KEY
        Integer pageSize = getInteger(tagParam, TagUtils.PAGE_SIZE_KEY);
        if (pageSize != null) {
            page.setPageSize(pageSize);
        }

        // 处理参数 PAGE_COUNT_KEY
        Integer pageCount = getInteger(tagParam, TagUtils.PAGE_COUNT_KEY);
        if (pageCount != null) {
            page.setCount(pageCount);
        }
        return page;
    }

    /**
     * 从入参中取得Integer类型的参数 如果key对应的值不存在，返回null
     *
     * @param tagParam 入参
     * @param key      键
     * @return
     */
    public static Integer getInteger(Map<String, Object> tagParam, String key) {
        return getInteger(tagParam, key, null);
    }

    /**
     * 从入参中取得Integer类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam     入参
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static Integer getInteger(Map<String, Object> tagParam, String key, Integer defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        Object v3 = tagParam.get(key);
        if (v3 != null) {
            String s = v3.toString();
            if (StringUtils.isNumeric(s)) {
                int value = Integer.valueOf(s);
                return value;
            }
        }
        return defaultValue;
    }

    /**
     * 从入参中取得Long类型的参数 如果key对应的值不存在，返回null
     *
     * @param tagParam 入参
     * @param key      键
     * @return
     */
    public static Long getLong(Map<String, Object> tagParam, String key) {
        return getLong(tagParam, key, null);
    }

    /**
     * 从入参中取得Long类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam     入参
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static Long getLong(Map<String, Object> tagParam, String key, Long defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        Object v3 = tagParam.get(key);
        if (v3 != null) {
            String s = v3.toString();
            if (StringUtils.isNumeric(s)) {
                Long value = Long.valueOf(s);
                return value;
            }
        }
        return defaultValue;

    }


    /**
     * 从入参中取得String类型的参数 如果key对应的值不存在，返回null
     *
     * @param tagParam 入参
     * @param key      键
     * @return
     */
    public static String getString(Map<String, Object> tagParam, String key) {
        return getString(tagParam, key, null);
    }

    /**
     * 从入参中取得String类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam     入参
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(Map<String, Object> tagParam, String key, String defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        Object v3 = tagParam.get(key);
        if (v3 != null) {
            String value = v3.toString();
            return value;
        } else {
            return defaultValue;
        }
    }

    /**
     * 从入参中取得Boolean类型的参数 如果key对应的值不存在，返回null
     *
     * @param tagParam 入参
     * @param key      键
     * @return
     */
    public static Boolean getBoolean(Map<String, Object> tagParam, String key) {
        return getBoolean(tagParam, key, null);
    }

    /**
     * 从入参中取得Boolean类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam     入参
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static Boolean getBoolean(Map<String, Object> tagParam, String key, Boolean defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        Object v3 = tagParam.get(key);
        if (v3 != null) {
            if (v3 instanceof Boolean) {
                return (Boolean) v3;
            } else {
                String value = v3.toString().trim();
                return "true".equalsIgnoreCase(value);
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * 从入参中取得Date类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam 入参
     * @param key      键
     * @param format   日期格式
     * @return
     */
    public static Date getDate(Map<String, Object> tagParam, String key, String format) {
        return getDate(tagParam, key, format);
    }

    /**
     * 从入参中取得Date类型的参数 如果key对应的值不存在，返回defaultValue默认值
     *
     * @param tagParam     入参
     * @param key          键
     * @param format       日期格式
     * @param defaultValue 默认值
     * @return
     */
    public static Date getDate(Map<String, Object> tagParam, String key, String format, Date defaultValue) {
        Object sDateTime = tagParam.get(key);
        if (sDateTime == null) {
            return defaultValue;
        }
        if (sDateTime instanceof Date) {
            return (Date) sDateTime;
        }
        Date date = null;
        if ((null != sDateTime)) {
            try {
                date = (Date) (new SimpleDateFormat(format)).parseObject(sDateTime.toString());
            } catch (ParseException e) {
                return defaultValue;// 不需要抛出异常
            }
        }
        return date;
    }
}
