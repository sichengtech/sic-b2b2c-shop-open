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
package com.sicheng.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果集 是一次查询结果的封装，包含多条文档T
 */
public class ResultSet<T> {
    private String status;//状态
    private String requestId;//请求ID
    private double searchtime;// 搜索耗时
    private int total;// 搜索到的总条数
    private int num;// 页大小
    private int viewtotal;// 可显示的总条数，5000封顶
    private List<T> items = new ArrayList<T>();//结果集
    private String[] errors;//异常
    private String tracer;

    public double getSearchtime() {
        return searchtime;
    }

    public void setSearchtime(double searchtime) {
        this.searchtime = searchtime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getViewtotal() {
        return viewtotal;
    }

    public void setViewtotal(int viewtotal) {
        this.viewtotal = viewtotal;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String request_id) {
        this.requestId = request_id;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public String getTracer() {
        return tracer;
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }
}