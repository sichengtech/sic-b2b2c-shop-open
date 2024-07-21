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
package com.sicheng.admin.store.entity;

import java.util.List;

/**
 * <p>标题: StoreSellerRoleList</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月20日 下午8:09:45
 */
public class StoreSellerRoleList {

    private List<StoreSellerRole> list;

    /**
     * @Title:构造方法
     * @Description:TODO(这里用一句话描述这个方法的作用)
     */
    public StoreSellerRoleList() {

    }

    public List<StoreSellerRole> getList() {
        return list;
    }

    public void setList(List<StoreSellerRole> list) {
        this.list = list;
    }

}
