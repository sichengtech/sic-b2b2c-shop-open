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
package com.sicheng.admin.report.dao;

import com.sicheng.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 *  <p>标题: ReportDao</p>
 *  <p>描述: 统计报表的dao</p>
 *  <p>公司: 思程科技 www.sicheng.net</p>
 *  @author zhangjiali
 *  @version 2017年7月20日 下午12:48:45
 * 
 */
@MyBatisDao
public interface ReportDao {

    /**
     * 统计：新增会员（按小时分组）
     */
    List<Map<String, Object>> report1(Map<String, Object> map);

    /**
     * 统计：新增会员（按天分组）
     */
    List<Map<String, Object>> report2(Map<String, Object> map);

    /**
     * 统计：新开店铺（按小时分组）
     */
    List<Map<String, Object>> report3(Map<String, Object> map);

    /**
     * 统计：新开店铺（按天分组）
     */
    List<Map<String, Object>> report4(Map<String, Object> map);

    /**
     * 统计：商品销售
     */
    List<Map<String, Object>> report5(Map<String, Object> map);

    /**
     * 统计：会员购买
     */
    List<Map<String, Object>> report6(Map<String, Object> map);

    /**
     * 统计：店铺销售
     */
    List<Map<String, Object>> report7(Map<String, Object> map);

    /**
     * 统计：订单金额 （按小时分组）
     */
    List<Map<String, Object>> report8(Map<String, Object> map);

    /**
     * 统计：订单金额 （按天分组）
     */
    List<Map<String, Object>> report9(Map<String, Object> map);

    /**
     * 统计：下单数量 （按小时分组）
     */
    List<Map<String, Object>> report10(Map<String, Object> map);

    /**
     * 统计：下单数量 （按天分组）
     */
    List<Map<String, Object>> report11(Map<String, Object> map);

    /**
     * 统计：退款金额（按小时分组）
     */
    List<Map<String, Object>> report12(Map<String, Object> map);

    /**
     * 统计：退款金额（按天分组）
     */
    List<Map<String, Object>> report13(Map<String, Object> map);

    /**
     * 统计：下单金额与下单数量
     */
    List<Map<String, Object>> report14(Map<String, Object> map);

    /**
     * 统计：下单商品的分类
     */
    List<Map<String, Object>> report15(Map<String, Object> map);

    /**
     * 统计：按店铺等级统计店铺
     */
    List<Map<String, Object>> report16(Map<String, Object> map);

}