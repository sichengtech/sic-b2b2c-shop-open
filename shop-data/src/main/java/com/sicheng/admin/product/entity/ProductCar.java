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
package com.sicheng.admin.product.entity;

import com.sicheng.admin.product.dao.ProductCarDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 车系车型 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-06-12
 */
public class ProductCar extends ProductCarBase<ProductCar> {

    private static final long serialVersionUID = 1L;

    public ProductCar() {
        super();
    }

    public ProductCar(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    private String carParentNames;  //父级名字

    public String getCarParentNames() {
        if (!"0,".equals(this.parentIds)) {
            ProductCarDao dao = SpringContextHolder.getBean(ProductCarDao.class);
            String[] carIds = this.getParentIds().split(",");
            StringBuffer carNames = new StringBuffer();
            for (int i = 0; i < carIds.length; i++) {
                if (!"0".equals(carIds[i])) {
                    ProductCar productCar = dao.selectById(Long.parseLong(carIds[i]));
                    carNames.append("-");
                    carNames.append(productCar.getName());
                }
            }
            carParentNames = carNames.substring(1);
        }
        return carParentNames;
    }

    public void setCarParentNames(String carParentNames) {
        this.carParentNames = carParentNames;
    }

    private Long level; //等级

    public Long getLevel() {
        String[] carIds = this.getParentIds().split(",");
        if (carIds.length == 1) {
            level = 1L;
        }
        if (carIds.length == 2) {
            level = 2L;
        }
        if (carIds.length == 3) {
            level = 3L;
        }
        if (carIds.length == 4) {
            level = 4L;
        }
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

}