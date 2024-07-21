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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 店铺菜单 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-13
 */
public class StoreMenu extends StoreMenuBase<StoreMenu> {

    private static final long serialVersionUID = 1L;

    public StoreMenu() {
        super();
    }

    public StoreMenu(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    private Long sellerId;  //商家id

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @JsonIgnore
    public static void sortList(List<StoreMenu> list, List<StoreMenu> sourcelist, Long parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            StoreMenu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        StoreMenu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    public static Long getRootId() {
        return 1L;
    }
}