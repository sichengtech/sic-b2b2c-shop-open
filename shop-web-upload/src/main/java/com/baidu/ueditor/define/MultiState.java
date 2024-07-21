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
package com.baidu.ueditor.define;

import com.baidu.ueditor.Encoder;

import java.util.*;

/**
 * 多状态集合状态
 * 其包含了多个状态的集合, 其本身自己也是一个状态
 *
 * @author zhaolei
 */
public class MultiState implements State {

    private boolean state = false;
    private String info = null;
    private Map<String, Long> intMap = new HashMap<String, Long>();
    private Map<String, String> infoMap = new HashMap<String, String>();
    private List<String> stateList = new ArrayList<String>();

    public MultiState(boolean state) {
        this.state = state;
    }

    public MultiState(boolean state, String info) {
        this.state = state;
        this.info = info;
    }

    public MultiState(boolean state, int infoKey) {
        this.state = state;
        this.info = AppInfo.getStateInfo(infoKey);
    }

    @Override
    public boolean isSuccess() {
        return this.state;
    }

    public void addState(State state) {
        stateList.add(state.toJSONString());
    }

    /**
     * 该方法调用无效果
     */
    @Override
    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }

    @Override
    public String toJSONString() {

        String stateVal = this.isSuccess() ? AppInfo.getStateInfo(AppInfo.SUCCESS) : this.info;

        StringBuilder builder = new StringBuilder();

        builder.append("{\"state\": \"" + stateVal + "\"");

        // 数字转换
        Iterator<String> iterator = this.intMap.keySet().iterator();

        while (iterator.hasNext()) {

            stateVal = iterator.next();

            builder.append(",\"" + stateVal + "\": " + this.intMap.get(stateVal));

        }

        iterator = this.infoMap.keySet().iterator();

        while (iterator.hasNext()) {

            stateVal = iterator.next();

            builder.append(",\"" + stateVal + "\": \"" + this.infoMap.get(stateVal) + "\"");

        }

        builder.append(", list: [");


        iterator = this.stateList.iterator();

        while (iterator.hasNext()) {

            builder.append(iterator.next() + ",");

        }

        if (this.stateList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(" ]}");

        return Encoder.toUnicode(builder.toString());

    }

    @Override
    public void putInfo(String name, long val) {
        this.intMap.put(name, val);
    }

}
