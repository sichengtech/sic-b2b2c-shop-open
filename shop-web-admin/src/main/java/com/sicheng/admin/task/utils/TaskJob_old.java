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
package com.sicheng.admin.task.utils;

import com.google.common.collect.Lists;
import com.sicheng.admin.sys.entity.SysTimedTask;
import com.sicheng.admin.sys.entity.SysTimedTaskLog;
import com.sicheng.admin.sys.service.SysTimedTaskLogService;
import com.sicheng.admin.sys.service.SysTimedTaskService;
import com.sicheng.admin.task.service.TaskListService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时任务类，每一个方法是一个定时任务
 *
 * @author zhangjiali
 */
public class TaskJob_old {
    @Autowired
    private SysTimedTaskService sysTimedTaskService;

    @Autowired
    private SysTimedTaskLogService sysTimedTaskLogService;

    @Autowired
    private TaskListService taskListService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //定时任务编号
    private final Integer TASK_1_NUM = 1;//对商家结算
    private final Integer TASK_2_NUM = 2;//清理过期token
    private final Integer TASK_3_NUM = 3;//计算每个店铺的商品总数
    private final Integer TASK_4_NUM = 4;//清理定时任务日志表
    private final Integer TASK_5_NUM = 5;//修改会员收藏商品的状态
    private final Integer TASK_6_NUM = 6;//一件商品30天所卖的数量
    private final Integer TASK_7_NUM = 7;//管理后台首页的数据
    private final Integer TASK_8_NUM = 8;//查询并修改超过最晚收货时间的订单
    private final Integer TASK_9_NUM = 9;//取消过期的订单
    private final Integer TASK_10_NUM = 10;//对账
    private final Integer TASK_11_NUM = 11;//查询并修改超过最晚收货时间的退款退货订单

    /**
     * 结算定时任务
     * 定时任务具体的逻辑方法写在try中
     */
    public void settlementTask() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_1_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.settlementTask();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("结算定时任务失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 清理sysToken 过期token
     * 定时任务具体的逻辑方法写在try中
     */
    public void sysTokenTask() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_2_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    taskListService.clearToken();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("清理token定时任务失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 计算每个店铺的商品总数
     * 定时任务具体的逻辑方法写在try中
     */
    public void storeProductSum() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_3_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.storeProductSum();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("计算每个店铺的商品总数失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 清理30天前工程所有的日志
     * 定时任务具体的逻辑方法写在try中
     */
    public void cleanLog() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_4_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.cleanLog();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("清理30天前定时任务产生的日志失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 修改会员收藏商品的状态
     * 定时任务具体的逻辑方法写在try中
     */
    public void updateCollectionProduct() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_5_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.updateCollectionProduct();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("修改会员收藏商品的状态失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 商品 30天销量
     * 定时任务具体的逻辑方法写在try中
     */
    public void monthSales() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_6_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.monthSales();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("计算商品30天销量失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 管理后台首页的数据
     * 定时任务具体的逻辑方法写在try中
     */
    public void adminIndexInfo() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_7_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.adminIndexInfo();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("计算管理后台首页的数据失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 查询并修改超过最晚收货时间的订单
     * 定时任务具体的逻辑方法写在try中
     */
    public void updateTradeOrder() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_8_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.updateTradeOrder();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("修改超过最晚收货时间的订单数据失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 取消过期的订单(超过24小时未支付的订单)
     * 定时任务具体的逻辑方法写在try中
     */
    public void cancleExpiredTradeOrder() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_9_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.cancleExpiredTradeOrder();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("取消订单失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 对账
     * 定时任务具体的逻辑方法写在try中
     */
    public void reconciliation() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_10_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                   // taskListService.reconciliation();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("对账失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 查询并修改超过最晚收货时间的退款退货订单
     * 定时任务具体的逻辑方法写在try中
     */
    public void updateTradeReturnOrder() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(TASK_11_NUM);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
        if (!list.isEmpty()) {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                //插入定时任务日志
                String result = "2";//定时任务执行结果，0失败、1成功、2执行中
                Long sttId = list.get(0).getSttId();//定时任id
                Long sttlId = insertTaskLog(sttId, result);//获取数据库自增长
                try {
                    //taskListService.updateTradeReturnOrder();
                    result = "1";
                } catch (Exception e) {
                    result = "0";
                    logger.error("修改超过最晚收货时间的退款退货订单数据失败", e);
                }
                //更新定时任务日志
                updateTaskLog(sttlId, result);
            }
        }
    }

    /**
     * 插入定时任务日志
     *
     * @param sttId  定时任务id
     * @param result 定时任务执行结果
     * @return 定时任务的日志id
     */
    private Long insertTaskLog(Long sttId, String result) {
        SysTimedTaskLog sysTimedTaskLog = new SysTimedTaskLog();
        sysTimedTaskLog.setSttId(sttId);
        sysTimedTaskLog.setStartTime(new Date());//定时任务开始时间
        sysTimedTaskLog.setResult(result);//定时任务执行结果，0失败、1成功、执行中
        sysTimedTaskLogService.insertSelective(sysTimedTaskLog);
        return sysTimedTaskLog.getId();//获取数据库自增长
    }

    /**
     * 更新定时任务日志
     *
     * @param sttlId 定时任务的日志id
     * @param result 定时任务执行结果
     */
    private void updateTaskLog(Long sttlId, String result) {
        SysTimedTaskLog updateTimedTaskLog = new SysTimedTaskLog();
        updateTimedTaskLog.setSttlId(sttlId);
        updateTimedTaskLog.setEndTime(new Date());//定时任务结束时间
        updateTimedTaskLog.setResult(result);//定时任务执行结果，0失败、1成功、执行中
        sysTimedTaskLogService.updateByIdSelective(updateTimedTaskLog);
    }

//    public static void main(String[] args) {
//    	//获取当前时间
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//		Calendar cal2 = Calendar.getInstance();
//		//调到上个月
//		cal2.add(Calendar.MONTH, -1);
//		//前一个月的第一天
//		int minDay=cal2.getActualMinimum(Calendar.DAY_OF_MONTH);
//		//按你的要求设置时间
//		cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), minDay, 00, 00, 00);
//		//按格式输出
//		System.out.println(cal2.getTime());
//		System.out.println(sdf.format(cal2.getTime()));	
//    	
//    	//获取当前时间
//		Calendar cal = Calendar.getInstance();
//		//调到上个月
//		cal.add(Calendar.MONTH, -1);
//		//得到一个月最最后一天日期(31/30/29/28)
//		int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		//按你的要求设置时间
//		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay, 23, 59, 59);
//		//按格式输出
//		System.out.println(cal.getTime());
//		System.out.println(sdf.format(cal.getTime()));	
//	}

    public static void main(String[] args) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);//设置年
        cal.set(Calendar.MONTH, 4 - 2);//设置上月
        int lastMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//上月总天数
        String lastMonth = DateUtils.formatDate(cal.getTime(), "yyyy-MM");//上月日期

        cal.set(Calendar.MONTH, 4 - 1);//设置本月
        int thisMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月总天数
        String thisMonth = DateUtils.formatDate(cal.getTime(), "yyyy-MM");//本月日期

        cal.set(Calendar.MONTH, 4);//设置本月
        String nextMonth = DateUtils.formatDate(cal.getTime(), "yyyy-MM");//本月日期

        System.out.println("lastMonthDays:" + lastMonthDays);
        System.out.println("lastMonth:" + lastMonth);
        System.out.println("thisMonthDays:" + thisMonthDays);
        System.out.println("thisMonth:" + thisMonth);
        System.out.println("nextMonth:" + nextMonth);

        int count = 0;
        List<String> list = Lists.newArrayList();
        for (int i = 1; i <= thisMonthDays; i++) {
            Date date = DateUtils.parseDate(thisMonth + "-" + i, "yyyy-MM-dd");//本月的某一天
            cal.clear();
            cal.setTime(date);
            int k = new Integer(cal.get(Calendar.DAY_OF_WEEK));
            String startWeek = null;//本周开始日期
            String endWeek = null;//本周结束日期
            if (k == 1) {//若当天是周日  
                count++;
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                if (i - 6 < 1) {
                    startWeek = lastMonth + "-" + (lastMonthDays - (6 - i));
                    System.out.println("本周开始日期:" + lastMonth + "-" + (lastMonthDays - (6 - i)));
                } else {
                    startWeek = thisMonth + "-" + (i - 6);
                    System.out.println("本周开始日期:" + thisMonth + "-" + (i - 6));
                }
                endWeek = thisMonth + "-" + i;
                System.out.println("本周结束日期:" + thisMonth + "-" + i);
                System.out.println("-----------------------------------");
            }
            if (k != 1 && i == thisMonthDays) {// 若是本月最好一天，且不是周日
                count++;
                startWeek = thisMonth + "-" + (i - k + 2);
                endWeek = nextMonth + "-" + (7 - (k - 1));
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                System.out.println("本周开始日期:" + thisMonth + "-" + (i - k + 2));
                System.out.println("本周结束日期:" + nextMonth + "-" + (7 - (k - 1)));
                System.out.println("-----------------------------------");
            }
            if (startWeek != null && endWeek != null) {
                list.add(startWeek + "~" + endWeek);
            }
            if (count == 5) {
                break;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + i + "周：" + list.get(i));
        }
    }
}
