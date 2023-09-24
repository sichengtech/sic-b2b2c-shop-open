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
package com.sicheng.admin.store.service;

import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeOrderItemDao;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.service.TradeCommentService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sicheng.common.service.CrudService;
import com.sicheng.admin.store.entity.StoreAnalyze;
import com.sicheng.admin.store.dao.StoreAnalyzeDao;

import java.util.*;

/**
 * 店铺统计表 Service
 *
 * @author 贺东泽
 * @version 2019-11-18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreAnalyzeService extends CrudService<StoreAnalyzeDao, StoreAnalyze> {
    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Lazy
    @Autowired
    private StoreService storeService;
    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private TradeOrderItemDao tradeOrderItemDao;
    private Calendar calendar = Calendar.getInstance();

    /**
     * 31、计算店铺评分和销售量  （定时任务会来调用本方法）
     */
    public void score() {
        //计算日期
        Date date = new Date();
        Date date7 = this.dateMoveBack(date, -7);
        Date date30 = this.dateMoveBack(date, -30);
        Date date90 = this.dateMoveBack(date, -90);
        Date date180 = this.dateMoveBack(date, -180);

        //查询出需要计算评分的店铺列表
        Integer pageSize = 200;
        Integer pageNo = 1;
        Page<Store> storePage = new Page<>(pageNo, pageSize);
        Wrapper wrapper = new Wrapper();
        wrapper.and("is_open = ", "1");
        storePage = storeService.selectByWhere(storePage, wrapper);
        Long countPage = getCountPage(storePage.getCount(), pageSize);

        //循环游标获取店铺，挨个更新
        while (pageNo <= countPage) {
            for (Store store : storePage.getList()) {
                StoreAnalyze storeAnalyze = new StoreAnalyze();
                storeAnalyze.setStoreId(store.getStoreId());
                storeAnalyze.setId(store.getStoreId());
                //查询并计算最近6个月的评分
                Map<String, Float> stringFloatMap = this.computeScore(store.getStoreId(), date180);
                storeAnalyze.setProductScore(stringFloatMap.get("productScore").toString());
                storeAnalyze.setLogisticsScore(stringFloatMap.get("logisticsScore").toString());
                storeAnalyze.setServiceAttitudeScore(stringFloatMap.get("serviceAttitudeScore").toString());

                //查询并计算最近一周、一个月、三个月以及总销售额
                Integer allSales = tradeOrderItemDao.countSalesByStoreId(store.getStoreId(), null);
                storeAnalyze.setAllSales(allSales == null ? 0 : allSales);
                storeAnalyze.setAllSalesDate(date);

                Integer weekSales = tradeOrderItemDao.countSalesByStoreId(store.getStoreId(), date7);
                storeAnalyze.setWeekSales(weekSales == null ? 0 : weekSales);
                storeAnalyze.setWeekSalesDate(date);

                Integer monthSales = tradeOrderItemDao.countSalesByStoreId(store.getStoreId(), date30);
                storeAnalyze.setMonthSales(monthSales == null ? 0 : monthSales);
                storeAnalyze.setMonthSalesDate(date);

                Integer month3Sales = tradeOrderItemDao.countSalesByStoreId(store.getStoreId(), date90);
                storeAnalyze.setMonth3Sales(month3Sales == null ? 0 : month3Sales);
                storeAnalyze.setMonth3SalesDate(date);

                //保存入库
                StoreAnalyze buffer = this.selectById(store.getStoreId());
                if (buffer == null) {
                    //新增店铺统计记录
                    storeAnalyze.setPkMode(1);
                    this.insertSelective(storeAnalyze);
                } else {
                    //更新店铺统计记录，判断分数是上涨还是下降
                    if (new Float(buffer.getProductScore()) >= stringFloatMap.get("productScore")) {
                        storeAnalyze.setProductScoreUpOrDown("1");
                    } else {
                        storeAnalyze.setProductScoreUpOrDown("0");
                    }
                    if (new Float(buffer.getLogisticsScore()) >= stringFloatMap.get("logisticsScore")) {
                        storeAnalyze.setLogisticsScoreUpOrDown("1");
                    } else {
                        storeAnalyze.setLogisticsScoreUpOrDown("0");
                    }
                    if (new Float(buffer.getServiceAttitudeScore()) >= stringFloatMap.get("serviceAttitudeScore")) {
                        storeAnalyze.setServiceAttitudeScoreUpOrDown("1");
                    } else {
                        storeAnalyze.setServiceAttitudeScoreUpOrDown("0");
                    }
                    this.updateByIdSelective(storeAnalyze);
                }
            }
            //查询下一页
            pageNo++;
            storePage = new Page<>(pageNo, pageSize);
            storePage = storeService.selectByWhere(storePage, wrapper);
        }
    }

    /**
     * 将指定日期后推N天
     *
     * @param days
     * @return
     */
    private Date dateMoveBack(Date date, Integer days) {
        //需要将date数据转移到Calender对象中操作
        calendar.setTime(date);
        //把日期往后增加n天.正数往后推,负数往前移动
        calendar.add(calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 计算商品评分、服务态度评分、物流评分
     *
     * @param storeId
     * @param date
     * @return
     */
    private Map<String, Float> computeScore(Long storeId, Date date) {
        Map<String, Float> data = new HashMap<>();
        try {
            Wrapper wrapper = new Wrapper();
            wrapper.and("store_id = ", storeId);
            wrapper.and("create_date >= ", date);
            List<TradeComment> tradeComments = tradeCommentService.selectByWhere(wrapper);

            Float productScoreCount = 0F;
            Float serviceAttitudeScoreCount = 0F;
            Float deliverySpeedScore = 0F;

            for (TradeComment tradeComment : tradeComments) {
                if (StringUtils.isNotBlank(tradeComment.getProductScore())) {
                    productScoreCount += new Float(tradeComment.getProductScore());
                }
                if (StringUtils.isNotBlank(tradeComment.getDeliverySpeedScore())) {
                    deliverySpeedScore += new Float(tradeComment.getDeliverySpeedScore());
                }
                if (StringUtils.isNotBlank(tradeComment.getServiceAttitudeScore())) {
                    serviceAttitudeScoreCount += new Float(tradeComment.getServiceAttitudeScore());
                }
            }
            //如果近期六个月没有订单评价则按5分算
            if (tradeComments.size() == 0) {
                data.put("productScore", 5.00F);
                data.put("logisticsScore", 5.00F);
                data.put("serviceAttitudeScore", 5.00F);
            } else {
                data.put("productScore", (float) (Math.round((productScoreCount / tradeComments.size()) * 100) / 100));
                data.put("logisticsScore", (float) (Math.round((deliverySpeedScore / tradeComments.size()) * 100) / 100));
                data.put("serviceAttitudeScore", (float) (Math.round((serviceAttitudeScoreCount / tradeComments.size()) * 100) / 100));
            }
        } catch (Exception e) {
            logger.error("错误异常：", e);
        }
        return data;
    }

    /**
     * 计算总页数
     *
     * @param count
     * @param pageSize
     * @return
     */
    private Long getCountPage(Long count, Integer pageSize) {
        if (count == 0) {
            return 0L;
        }
        Long countPage = count / pageSize;
        if (count % pageSize != 0) {
            countPage += 1;
        }
        return countPage;
    }

}













