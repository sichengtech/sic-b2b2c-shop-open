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
package com.sicheng.front.template;

import com.sicheng.admin.product.dao.ProductExtDao;
import com.sicheng.admin.product.entity.ProductCar;
import com.sicheng.admin.product.entity.ProductCarMapping;
import com.sicheng.admin.product.entity.ProductExt;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.ProductCarService;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductCarMappingService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.*;

/**
 * <p>标题: 适用车型列表</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月15日 下午6:39:11
 */
public class ProductApplyCarListFunction implements Function {

    private static final String PIDS = "pId";//商品id
    private static final String LEVEL = "level";//显示等级

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long pId = TagUtils.getLong(tagParamMap, PIDS);
        Integer level = TagUtils.getInteger(tagParamMap, LEVEL);
        if (level == null) {
            level = 3;
        }
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Map<String, Object> map = new HashMap<String, Object>();
        if (pId == null) {
            return map;
        }
        //判断是否是全车系
        ProductExtDao productExtDao = SpringContextHolder.getBean(ProductExtDao.class);
        ProductExt productExt = productExtDao.selectById(pId);
        if (productExt == null) {
            return map;
        }
        String allCar = productExt.getAllCar();
        if ("1".equals(allCar)) {
            //全车系
            map.put("allCar", allCar);
            return map;
        }
        //不是全车系
        //根据商品获取车型list
        ProductCarMappingService productCarMappingService = SpringContextHolder.getBean(ProductCarMappingService.class);
        ProductCarMapping productCarMapping = new ProductCarMapping();
        productCarMapping.setPId(pId);
        Page<ProductCarMapping> productCarMappingPage = productCarMappingService.selectByWhere(new Page<ProductCarMapping>(1, limit, limit), new Wrapper(productCarMapping));
        List<ProductCarMapping> productCarMappingList = productCarMappingPage.getList();
        if (productCarMappingList.isEmpty()) {
            return map;
        }
        //根据等级去除重复carIds
        List<String> carIds = duplicateRemoval(productCarMappingList, level);
        //根据carIdList来获取车型名字list
        ProductCarService productCarService = SpringContextHolder.getBean(ProductCarService.class);
        List<String> carIdList = getCarIds(carIds);
        List<ProductCar> productCarNameList = productCarService.selectByIdIn(carIdList);
        //组装名字
        List<ProductCarMapping> list = install(carIds, productCarNameList);
        //往map中装值
        map.put("allCar", allCar);
        map.put("applyCarList", list);
        return map;

    }

    /**
     * 把一批carIds装入List，为selectByIn做准备(会去除重复carId)
     *
     * @param carIds 车系carIds 例如:[1,2 , 3,4 , 5,6 ...]
     * @param level  等级
     * @return 例如:[1,2,3,4,.....]
     */
    private List<String> getCarIds(List<String> carIds) {
        List<String> listAll = new ArrayList<>();
        for (int i = 0; i < carIds.size(); i++) {
            String[] carIdss = carIds.get(i).split(",");
            List<String> list = java.util.Arrays.asList(carIdss);
            listAll.addAll(list);
        }
        return listAll;
    }

    /**
     * 按carId查找车型
     *
     * @param productCarNameList 车系名字list
     * @param carId              车型carId
     * @return
     */
    private ProductCar find(List<ProductCar> productCarNameList, Long carId) {
        for (int i = 0; i < productCarNameList.size(); i++) {
            if (productCarNameList.get(i).getCarId().equals(carId)) {
                return productCarNameList.get(i);
            }
        }
        return null;
    }

    /**
     * 车型名字的组装
     *
     * @param carIds             车系carIds的list
     * @param productCarNameList 车型名称list（有名字）
     * @return 组装完成的车型list
     */
    private List<ProductCarMapping> install(List<String> carIds, List<ProductCar> productCarNameList) {
        List<ProductCarMapping> list = new ArrayList<ProductCarMapping>();
        for (int i = 0; i < carIds.size(); i++) {
            String[] carIdss = carIds.get(i).split(",");
            StringBuffer productCarIds = new StringBuffer();
            StringBuffer productCarNames = new StringBuffer();
            for (int j = 0; j < carIdss.length; j++) {
                Long id = Long.parseLong((carIdss[j]));
                ProductCar productCar = find(productCarNameList, id);
                if (productCar != null) {
                    productCarIds.append(",");
                    productCarIds.append(productCar.getCarId());
                    productCarNames.append(" ");
                    productCarNames.append(productCar.getName());
                }
            }
            if (StringUtils.isNotBlank(productCarIds) && StringUtils.isNotBlank(productCarNames)) {
                ProductCarMapping productCarMapping2 = new ProductCarMapping();
                productCarMapping2.setProductCarId(productCarIds.substring(1));
                productCarMapping2.setProductCarName(productCarNames.substring(1));
                list.add(productCarMapping2);
            }
        }
        return list;
    }


    /**
     * 根据等级去除重复carIds
     *
     * @param productCarMappingList 车型list
     * @param level                 等级
     * @return 例如:[1,2 , 3,4 , 5,6 ...]
     */
    private List<String> duplicateRemoval(List<ProductCarMapping> productCarMappingList, Integer level) {
        Set<String> cSet = new TreeSet<String>();
        for (int i = 0; i < productCarMappingList.size(); i++) {
            String[] carIds = productCarMappingList.get(i).getCarIds().split(",");
            carIds = Arrays.copyOf(carIds, level);
            StringBuffer changeCarIds = new StringBuffer();
            for (int j = 0; j < carIds.length; j++) {
                changeCarIds.append(",");
                changeCarIds.append(carIds[j]);
            }
            cSet.add(changeCarIds.substring(1));
        }
        List<String> carIdlist = new ArrayList<>(cSet);
        return carIdlist;
    }

}
