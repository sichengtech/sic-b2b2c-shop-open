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
package com.sicheng.admin.report.service;

import com.google.common.collect.Lists;
import com.sicheng.admin.report.dao.ReportDao;
import com.sicheng.admin.report.entity.Report;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 *  <p>标题: ReportService</p>
 *  <p>描述: 统计报表的service</p>
 *  <p>公司: 思程科技 www.sicheng.net</p>
 *  @author zhangjiali
 *  @version 2017年7月20日 下午3:46:25
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ReportService {

    protected Logger logger = LoggerFactory.getLogger(getClass());//日志对象

    @Autowired
    protected ReportDao reportDao;

    public static final String[] MONTHS = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};//初始化一年12个月

    /**
     *  获取昨天、今天的开始时间与结束时间
     *  @param searchTime 搜索时间（今天的时间）
     *  @return 返回一个map,包含昨天、今天的开始时间与结束时间
     */
    public Map<String, Date> getDayTime(Date searchTime) {
        Date yesterdayStart = null;//昨天开始时间
        Date yesterdayEnd = null;//昨天结束时间
        Date todayStart = null;//今天开始时间
        Date todayEnd = null;//今天结束时间
        try {
            yesterdayStart = DateUtils.parseDate(DateUtils.formatDate(new Date(searchTime.getTime() - (long) (1000 * 60 * 60 * 24)), "yyyy/MM/dd ") + "00:00:00", "yyyy/MM/dd HH:mm:ss");
            yesterdayEnd = DateUtils.parseDate(DateUtils.formatDate(new Date(searchTime.getTime() - (long) (1000 * 60 * 60 * 24)), "yyyy/MM/dd ") + "23:59:59", "yyyy/MM/dd HH:mm:ss");
            todayStart = DateUtils.parseDate(DateUtils.formatDate(searchTime, "yyyy/MM/dd ") + "00:00:00", "yyyy/MM/dd HH:mm:ss");
            todayEnd = DateUtils.parseDate(DateUtils.formatDate(searchTime, "yyyy/MM/dd ") + "23:59:59", "yyyy/MM/dd HH:mm:ss");
        } catch (ParseException e) {
            logger.error("日期转换发生异常", e);
        }
        Map<String, Date> map = new HashMap<String, Date>();
        map.put("yesterdayStart", yesterdayStart);
        map.put("yesterdayEnd", yesterdayEnd);
        map.put("todayStart", todayStart);
        map.put("todayEnd", todayEnd);
        return map;
    }

    /**
     *  获取上周、本周的开始时间与结束时间
     *  @param searchTime 搜索时间（今天的时间）
     *  @return 返回一个map,包含上周、本周的开始时间与结束时间
     */
    public Map<String, Date> getWeekTime(String searchTime) {
        Date lastWeekStart = null;//上周开始时间
        Date lastWeekEnd = null;//上周结束时间
        Date thisWeekStart = null;//本周开始时间
        Date thisWeekEnd = null;//本周结束时间
        String[] searchTimes = searchTime.split("~");
        try {
            thisWeekStart = DateUtils.parseDate(searchTimes[0] + " 00:00:00", "yyyy/MM/dd HH:mm:ss");
            thisWeekEnd = DateUtils.parseDate(searchTimes[1] + " 23:59:59", "yyyy/MM/dd HH:mm:ss");
            lastWeekStart = DateUtils.parseDate(DateUtils.formatDate(new Date(thisWeekStart.getTime() - (long) (1000 * 60 * 60 * 24 * 7)), "yyyy/MM/dd ") + "00:00:00", "yyyy/MM/dd HH:mm:ss");
            lastWeekEnd = DateUtils.parseDate(DateUtils.formatDate(new Date(thisWeekEnd.getTime() - (long) (1000 * 60 * 60 * 24 * 7)), "yyyy/MM/dd ") + "23:59:59", "yyyy/MM/dd HH:mm:ss");
        } catch (ParseException e) {
            logger.error("日期转换发生异常", e);
        }
        Map<String, Date> map = new HashMap<String, Date>();
        map.put("lastWeekStart", lastWeekStart);
        map.put("lastWeekEnd", lastWeekEnd);
        map.put("thisWeekStart", thisWeekStart);
        map.put("thisWeekEnd", thisWeekEnd);
        return map;
    }

    /**
     *  获取上月、本月的开始时间与结束时间 
     *  @param searchTime 搜索的月份
     *  @return 返回一个map,包含上月、本月的开始时间与结束时间和本月的天数
     */
    public Map<String, Object> getMonthTime(Date searchTime) {
        Date lastMonthStart = null;//上月开始时间
        Date lastMonthEnd = null;//上月结束时间
        Date thisMonthStart = null;//本月开始时间
        Date thisMonthEnd = null;//本月结束时间
        int year = Integer.valueOf(DateUtils.formatDate(searchTime, "yyyy"));
        int month = Integer.valueOf(DateUtils.formatDate(searchTime, "MM"));

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);//设置年
        cal1.set(Calendar.MONTH, month - 2);//设置上月
        int firstDay1 = cal1.getActualMinimum(Calendar.DAY_OF_MONTH);//上月的第一天
        cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), firstDay1, 00, 00, 00);//按你的要求设置时间
        lastMonthStart = cal1.getTime();
        int laseDay1 = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);//上月的最后天
        cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), laseDay1, 23, 59, 59);//按你的要求设置时间
        lastMonthEnd = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, year);//设置年
        cal2.set(Calendar.MONTH, month - 1);//设置本月
        int firstDay2 = cal2.getActualMinimum(Calendar.DAY_OF_MONTH);//本月的第一天
        cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), firstDay2, 00, 00, 00);//按你的要求设置时间
        thisMonthStart = cal2.getTime();
        int laseDay2 = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);//本月的最后天
        cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), laseDay2, 23, 59, 59);//按你的要求设置时间
        thisMonthEnd = cal2.getTime();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lastMonthStart", lastMonthStart);
        map.put("lastMonthEnd", lastMonthEnd);
        map.put("thisMonthStart", thisMonthStart);
        map.put("thisMonthEnd", thisMonthEnd);
        map.put("monthDays", laseDay2);
        return map;
    }

    /**
     *  将查询出的一天的数据存入一个有序map中 
     *  @param list 一天的数据
     *  @param map 有序的map
     *  @return 返回一个新的有序的map
     */
    public HashMap<String, Object> getDayHashMap(List<Map<String, Object>> list, HashMap<String, Object> map) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String hour = list.get(i).get("DDATE").toString().split(" ")[1];
                for (String key : map.keySet()) {
                    if (hour.equals(key)) {
                        map.put(key, list.get(i).get("COUNTNUMBER"));
                    }
                }
            }
        }
        return map;
    }

    /**
     *  将查询出的一周的数据存入一个有序map中 
     *  @param list 一天的数据
     *  @param map 有序的map
     *  @return 返回一个新的有序的map
     */
    public HashMap<String, Object> getWeekHashMap(List<Map<String, Object>> list, HashMap<String, Object> map) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    Date date = DateUtils.parseDate(list.get(i).get("DDATE").toString(), "yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    String k = new Integer(cal.get(Calendar.DAY_OF_WEEK)).toString();
                    for (String key : map.keySet()) {
                        if (k.equals(key)) {
                            map.put(key, list.get(i).get("COUNTNUMBER"));
                        }
                    }
                } catch (ParseException e) {
                    logger.error("日期转换发生异常", e);
                }
            }
        }
        return map;
    }

    /**
     *  将查询出的一个月的数据存入一个有序map中  
     *  @param list 一个月的数据
     *  @param map 有序的map
     *  @return  返回一个新的有序的map
     */
    public HashMap<String, Object> getMonthHashMap(List<Map<String, Object>> list, HashMap<String, Object> map) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String day = list.get(i).get("DDATE").toString().split("-")[2];
                for (String key : map.keySet()) {
                    if (day.equals(key)) {
                        map.put(key, list.get(i).get("COUNTNUMBER"));
                    }
                }
            }
        }
        return map;
    }

    /**
     *  初始化一个有序的map（一天24小时）
     * 
     *  
     */
    public HashMap<String, Object> getHashMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("00", 0);
        map.put("01", 0);
        map.put("02", 0);
        map.put("03", 0);
        map.put("04", 0);
        map.put("05", 0);
        map.put("06", 0);
        map.put("07", 0);
        map.put("08", 0);
        map.put("09", 0);
        map.put("10", 0);
        map.put("11", 0);
        map.put("12", 0);
        map.put("13", 0);
        map.put("14", 0);
        map.put("15", 0);
        map.put("16", 0);
        map.put("17", 0);
        map.put("18", 0);
        map.put("19", 0);
        map.put("20", 0);
        map.put("21", 0);
        map.put("22", 0);
        map.put("23", 0);
        return map;
    }

    /**
     *  初始化一个星期有序的map
     *  @param map key的个数
     *  @return
     */
    public HashMap<String, Object> getWeekHashMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("2", 0);
        map.put("3", 0);
        map.put("4", 0);
        map.put("5", 0);
        map.put("6", 0);
        map.put("7", 0);
        map.put("1", 0);
        return map;
    }

    /**
     *  初始化一个有序的map
     *  @param map key的个数
     *  @return
     */
    public HashMap<String, Object> getHashMap(int size) {
        HashMap<String, Object> map = new LinkedHashMap<>();
        for (int i = 1; i < size + 1; i++) {
            if (i < 10) {
                map.put("0" + i, 0);
            } else {
                map.put("" + i, 0);
            }
        }
        return map;
    }

    /**
     *  构造新的数据
     *  @param map key的个数
     *  @return
     */
    public HashMap<String, Object> getNewDate(HashMap<String, Object> tMapData) {
        List<String> timeNum = Lists.newArrayList();//一天的24显示、本周的星期、本月月份的天数
        List<Object> tData = Lists.newArrayList();//今天、本周、本月数据
        for (String key : tMapData.keySet()) {
            timeNum.add(key);
            tData.add(tMapData.get(key));
        }
        String jsonTimeNum = JsonMapper.getInstance().toJson(timeNum);
        String jsontData = JsonMapper.getInstance().toJson(tData);
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("timeNum", timeNum);
        map.put("tData", tData);
        map.put("jsonTimeNum", jsonTimeNum);
        map.put("jsontData", jsontData);
        return map;
    }

    /**
     *  构造新的数据
     *  @param map key的个数
     *  @return
     */
    public HashMap<String, Object> getNewDate(HashMap<String, Object> lMapData, HashMap<String, Object> tMapData) {
        //折线图数据
        List<String> timeNum = Lists.newArrayList();//一天的24显示、本周的星期、本月月份的天数
        List<Object> lDatas = Lists.newArrayList();//昨天、上周、上月数据
        List<Object> tDatas = Lists.newArrayList();//今天、本周、本月数据
        for (String key : lMapData.keySet()) {
            timeNum.add(key);
            lDatas.add(lMapData.get(key));
        }
        for (String key : tMapData.keySet()) {
            tDatas.add(tMapData.get(key));
        }
        String jsonTimeNum = JsonMapper.getInstance().toJson(timeNum);
        String jsonlData = JsonMapper.getInstance().toJson(lDatas);
        String jsontData = JsonMapper.getInstance().toJson(tDatas);
        //列表明细数据
        DecimalFormat df = new DecimalFormat(".##");
        List<Report> list = Lists.newArrayList();//新增会员明细
        for (int i = 0; i < timeNum.size(); i++) {
            Integer lData = 0;
            if (String.valueOf(lDatas.get(i)).indexOf(".") == -1) {
                lData = Integer.valueOf(String.valueOf(lDatas.get(i)));
            }
            Integer tData = 0;
            if (String.valueOf(tDatas.get(i)).indexOf(".") == -1) {
                tData = Integer.valueOf(String.valueOf(tDatas.get(i)));
            }
            Report report = new Report();
            report.setyNum(lData);
            report.settNum(tData);
            if (lData != 0 && tData != 0 && lData != tData) {
                if (lData > tData) {
                    report.setCount(Double.valueOf(df.format((double) ((lData - tData) / lData))) * 100);
                } else {
                    report.setCount(Double.valueOf(df.format((double) ((tData - lData) / lData))) * 100);
                }
            }
            list.add(report);
        }
        Integer lcount = 0;//昨天、上周、上月新增总数
        Integer tcount = 0;//今天、本周、本月新增总数
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                lcount = lcount + Integer.valueOf(list.get(i).getyNum().toString());
                tcount = tcount + Integer.valueOf(list.get(i).gettNum().toString());
            }
        }
        Double count = null;//今天、本周、本月与昨天、上周、上月的增长量
        if (lcount != 0 && tcount != 0 && lcount != tcount) {
            if (lcount > tcount) {
                count = (double) (lcount - tcount) / lcount;
            } else {
                count = (double) (tcount - lcount) / lcount;
            }
        }
        if (count != null) {
            count = Double.valueOf(df.format(count)) * 100;
        }
        //折线图数据与列表明细数据放入一个map中
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("jsonTimeNum", jsonTimeNum);//一天的24显示、本周的星期、本月月份的天数
        map.put("jsonlData", jsonlData);//昨天、上周、上月数据
        map.put("jsontData", jsontData);//今天、本周、本月数据
        map.put("list", list);//列表明细数据
        map.put("lcount", lcount);//昨天、上周、上月新增总数
        map.put("tcount", tcount);//今天、本周、本月新增总数
        map.put("count", count);//今天、本周、本月与昨天、上周、上月的增长量
        return map;
    }

    /**
     *  获取指定年月的所周并算出每周的开始日期与结束日期  
     *  @param yearMonth
     *  @throws ParseException
     */
    public List<String> getWeek(Date yearMonth) {
        int year = Integer.valueOf(DateUtils.formatDate(yearMonth, "yyyy"));
        int month = Integer.valueOf(DateUtils.formatDate(yearMonth, "MM"));

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);//设置年
        cal1.set(Calendar.MONTH, month - 2);//设置上月
        int lastMonthDays = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);//上月总天数
        String lastMonth = DateUtils.formatDate(cal1.getTime(), "yyyy/MM");//上月日期

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, year);//设置年
        cal2.set(Calendar.MONTH, month - 1);//设置本月
        int thisMonthDays = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);//本月总天数
        String thisMonth = DateUtils.formatDate(cal2.getTime(), "yyyy/MM");//本月日期

        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.YEAR, year);//设置年
        cal3.set(Calendar.MONTH, month);//设置下月
        String nextMonth = DateUtils.formatDate(cal3.getTime(), "yyyy/MM");//下月日期

        int count = 0;
        List<String> list = Lists.newArrayList();
        for (int i = 1; i <= thisMonthDays; i++) {
            Date date = null;
            try {
                date = DateUtils.parseDate(thisMonth + "/" + i, "yyyy/MM/dd");
            } catch (ParseException e) {
                logger.error("日期转换发生异常", e);
            }//本月的某一天
            cal2.clear();
            cal2.setTime(date);
            int k = new Integer(cal2.get(Calendar.DAY_OF_WEEK));
            String startWeek = null;//本周开始日期
            String endWeek = null;//本周结束日期
            if (k == 1) {//若当天是周日  
                count++;
                if (i - 6 < 1) {
                    startWeek = lastMonth + "/" + (lastMonthDays - (6 - i));
                } else {
                    startWeek = thisMonth + "/" + (i - 6);
                }
                endWeek = thisMonth + "/" + i;
            }
            if (k != 1 && i == thisMonthDays) {// 若是本月最好一天，且不是周日
                count++;
                startWeek = thisMonth + "/" + (i - k + 2);
                endWeek = nextMonth + "/" + (7 - (k - 1));
            }
            if (startWeek != null && endWeek != null) {
                list.add(startWeek + "~" + endWeek);
            }
            if (count == 5) {
                break;
            }
        }
        return list;
    }
}