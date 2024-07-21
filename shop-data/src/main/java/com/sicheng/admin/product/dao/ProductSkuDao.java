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
package com.sicheng.admin.product.dao;

import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 商品SKUDAO接口
 *
 * @author zhaolei
 * @version 2017-02-07
 */
@MyBatisDao
public interface ProductSkuDao extends CrudDao<ProductSku> {

    int addStockById(@Param(value = "skuId") Long skuId, @Param(value = "number") Integer number);

    int reduceStockById(@Param(value = "skuId") Long skuId, @Param(value = "number") Integer number);

    int addOccupyStockById(@Param(value = "skuId") Long skuId, @Param(value = "number") Integer number);

    int reduceOccupyStockById(@Param(value = "skuId") Long skuId, @Param(value = "number") Integer number);

}