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
package com.sicheng.admin.report.entity;

public class Report {

    private int time;//时间
    private Object yNum;//过去统计数量
    private Object tNum;//现在统计数量
    private Double count;//增长的数量


    /**
     *  @return the count
     *  
     */
    public Double getCount() {
        return count;
    }

    /**
     *  @param count the count to set
     *  
     */
    public void setCount(Double count) {
        this.count = count;
    }

    /**
     *  @return the time
     *  
     */
    public int getTime() {
        return time;
    }

    /**
     *  @param time the time to set
     *  
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     *  @return the yNum
     *  
     */
    public Object getyNum() {
        return yNum;
    }

    /**
     *  @param yNum the yNum to set
     *  
     */
    public void setyNum(Object yNum) {
        this.yNum = yNum;
    }

    /**
     *  @return the tNum
     *  
     */
    public Object gettNum() {
        return tNum;
    }

    /**
     *  @param tNum the tNum to set
     *  
     */
    public void settNum(Object tNum) {
        this.tNum = tNum;
    }


}