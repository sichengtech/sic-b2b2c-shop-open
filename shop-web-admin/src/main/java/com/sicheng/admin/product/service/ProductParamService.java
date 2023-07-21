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
package com.sicheng.admin.product.service;

import com.sicheng.admin.product.dao.ProductParamDao;
import com.sicheng.admin.product.entity.ProductParam;
import com.sicheng.admin.product.entity.ProductParamVo;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数和参数值 Service
 *
 * @author 赵磊
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductParamService extends CrudService<ProductParamDao, ProductParam> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private ProductParamDao productParamDao;

    public List<ProductParam> selectByCategoryIdIn(List<Object> list) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("category_id in ", list);
        wrapper.orderBy("a.param_sort ASC");
        return productParamDao.selectByWhere(null, wrapper);
    }

    /**
     * 新增商品参数
     *
     * @param productParamVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveProductParam(ProductParamVo productParamVo) {
        Long categoryId = productParamVo.getCategoryId();
        String[] paramId = productParamVo.getParamId();
        String[] name = productParamVo.getName();
        String[] paramValues = productParamVo.getParamValues();
        String[] paramSort = productParamVo.getParamSort();
        String[] isRequired = productParamVo.getIsRequired();
        String[] isDisplay = productParamVo.getIsDisplay();
        List<Long> p = new ArrayList<>();
        //把新增的id为空的去除掉
        for (int i = 0; i < name.length; i++) {
            if (StringUtils.isNotBlank(paramId[i])) {
                p.add(Long.parseLong(paramId[i]));
            }
        }
        if (p.isEmpty()) {
            //全部为空删除原来的所有分类
            ProductParam pp = new ProductParam();
            pp.setCategoryId(categoryId);
            productParamDao.deleteByWhere(new Wrapper(pp));
        } else {
            //删除用户的商品参数
            Wrapper wrapper = new Wrapper();
            wrapper.and("category_id=", categoryId).and("param_id not in", p);
            productParamDao.deleteByWhere(wrapper);
        }

        for (int i = 0; i < name.length; i++) {
            ProductParam productParam = new ProductParam();
            productParam.setName(name[i]);
            productParam.setCategoryId(categoryId);
            productParam.setParamValues(paramValues[i]);
            productParam.setParamSort(Integer.parseInt(paramSort[i]));
            productParam.setIsRequired(isRequired[i]);
            productParam.setIsDisplay(isDisplay[i]);
            if (StringUtils.isNotBlank(paramId[i])) {
                //修改一条记录
                productParam.setParamId(Long.parseLong(paramId[i]));
                productParamDao.updateByIdSelective(productParam);
            } else {
                //新增记录
                productParamDao.insertSelective(productParam);
            }
        }
    }

}