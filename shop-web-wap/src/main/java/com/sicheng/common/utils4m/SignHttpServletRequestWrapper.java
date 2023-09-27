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
package com.sicheng.common.utils4m;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * <p>标题: 移动端接口签名后的参数处理类</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2019年11月03日
 */
public class SignHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 业务数据的容器
     */
    private Map<String, Object> data;

    public SignHttpServletRequestWrapper(HttpServletRequest request, Map<String, Object> data) {
        super(request);
        this.data = data; //业务数据
        if (this.data == null) {
            this.data = new HashMap<>();
        }
    }

    @Override
    public String getParameter(String name) {
        if (name == null) {
            return null;
        }
        Object v = data.get(name);
        if (v != null) {
            return v.toString();
        } else {
            return null;
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        if (name == null) {
            return null;
        }
        Object v = data.get(name);
        if (v == null) {
            return null;
        }
        if (v instanceof String[]) {
            return (String[]) v;
        }
        if (v instanceof List) {
            List list = (List) v;
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj != null) {
                    arr[i] = obj.toString();
                } else {
                    arr[i] = null;
                }
            }
            return arr;
        }
        if (v instanceof String) {
            String s = (String) v;
            return new String[]{s};
        }
        //保底的判断
        if (v instanceof Object) {
            String s = v.toString();
            return new String[]{s};
        }
        return null;
    }

    @Override
    public Map getParameterMap() {
        //直接返回map是不行的，不符合servlet规范
        //return data;

        //对map进行处理，符合servlet规范
        return Collections.unmodifiableMap(toStringArrayMap());
    }

    @Override
    public Enumeration getParameterNames() {
        Set<String> paramNames = new LinkedHashSet<String>();
        for (String key : data.keySet()) {
            paramNames.add(key);
        }
        return Collections.enumeration(paramNames);
    }

    /**
     * 内部工具类
     *
     * @return
     */
    private Map<String, String[]> toStringArrayMap() {
        HashMap<String, String[]> map = new HashMap<String, String[]>(data.size() * 3 / 2) {
            public String toString() {
                StringBuilder b = new StringBuilder();
                b.append('{');
                for (String k : keySet()) {
                    if (b.length() > 1)
                        b.append(',');
                    b.append(k);
                    b.append('=');
                    b.append(Arrays.asList(get(k)));
                }

                b.append('}');
                return b.toString();
            }
        };

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String[] a = toStringArray(entry.getValue());
            map.put(entry.getKey(), a);
        }
        return map;
    }

    /**
     * 内部工具类 List 转 String[]
     *
     * @param list
     * @return
     */
    private static String[] toStringArray(Object list) {
        if (list == null)
            return new String[0];

        if (list instanceof List) {
            List<?> l = (List<?>) list;
            String[] a = new String[l.size()];
            for (int i = l.size(); i-- > 0; ) {
                Object o = l.get(i);
                if (o != null)
                    a[i] = o.toString();
            }
            return a;
        }
        return new String[]{list.toString()};
    }
}