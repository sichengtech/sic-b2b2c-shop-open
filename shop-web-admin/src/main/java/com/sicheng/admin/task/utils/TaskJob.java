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
import com.sicheng.admin.site.service.SiteCreateSolrIndexService;
import com.sicheng.admin.sso.service.UserAppTokenService;
import com.sicheng.admin.store.service.StoreAnalyzeService;
import com.sicheng.admin.task.service.TaskListService;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.filter.PageCachingFilter;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.*;

/**
 * 定时任务入口类，每一个方法是一个定时任务
 * 配置文件spring-context-task.xml是spring框架下，配置定时任务的地方，是入口。
 *
 * @author zhangjiali
 */
public class TaskJob {
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private UserAppTokenService UserAppTokenService;
    @Autowired
    private StoreAnalyzeService storeAnalyzeService;
    @Autowired
    private SiteCreateSolrIndexService siteCreateSolrIndexService;

    /**
     * 缓存接口
     */
    @Autowired
    protected ShopCache cache;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //定时任务编号（本编号用于与数据表sys_timed_task(定时任务表)中的任务timed_task_num字段对应）
    private final Integer TASK_0_NUM = 0;//0、测试定时任务（不在表中）
    private final Integer TASK_1_NUM = 1;//1、对商家结算
    private final Integer TASK_2_NUM = 2;//2、清理过期的sysToken
    private final Integer TASK_3_NUM = 3;//3、计算每个店铺的商品总数
    private final Integer TASK_4_NUM = 4;//4、清理超过30天的日志（删除日志表中的记录）
    private final Integer TASK_5_NUM = 5;//5、维护会员收藏商品的状态（0下架，1上架）
    private final Integer TASK_6_NUM = 6;//6、更新商品30天销量
    private final Integer TASK_7_NUM = 7;//7、定时更新管理后台首页的统计数据
    private final Integer TASK_8_NUM = 8;//8、查询并修改超过最晚收货时间的订单
    private final Integer TASK_9_NUM = 9;//9、处理超时订单
    private final Integer TASK_10_NUM = 10;//10、定时插入对账任务，执行对账任务
    private final Integer TASK_11_NUM = 11;//11、定时处理超时的退款退货订单
    private final Integer TASK_12_NUM = 12;//12、处理超时的采购单
    private final Integer TASK_13_NUM = 13;//13、处理超时的服务单。【目前无用】  //TODO 还未从adq同步过来
    private final Integer TASK_14_NUM = 14;//14、定时清理页面缓存，并重新加载新的首页缓存 【目前无用】
    private final Integer TASK_15_NUM = 15;//服务结算定时任务(5大服务) 【目前无用】 //TODO 还未从adq同步过来
    private final Integer TASK_16_NUM = 16;//16、回收过期的优惠券 【目前无用】 //TODO 还未从adq同步过来
    private final Integer TASK_30_NUM = 30;//30、清理过期的AppToken
    private final Integer TASK_31_NUM = 31;//31、计算店铺评分和销售量
    private final Integer TASK_32_NUM = 32;//32、定时生成Solr索引（重建）

    /**
     * 0、测试定时任务（不在表中）
     * 测试定时任务,每20秒触发一次
     */
    public void testTask() {
        //定时任务的增强启动器
        //注意：参数1这里传17秒，由于本任务每20秒执行一次,参数1要小于20
        TaskRunnable tr = new TaskRunnable(TASK_0_NUM, 17, new Runnable() {
            public void run() {
                //这是具体的任务内容
                System.out.println("测试定时任务在执行,时间:" + new Date());

                //发消息，发布/订阅模式
                String topic = "topicName-xxx";
                String msg = "发消息，发布/订阅模式,123456," + Thread.currentThread().getId();
//			    	publisherService.sendMessage(topic,msg);

                //发消息，生产者/消费者模式
                String queue = "queue-1";
                String msg2 = "发消息，生产者/消费者模式,abcdef," + Thread.currentThread().getId();
//			    	producerService.sendMessage(queue, msg2);

            }
        });
        tr.start();//启动
    }

    /**
     * 1、对商家结算定时任务
     */
    public void settlementProductTask() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_1_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.settlementProductTask();
            }
        });
        tr.start();//启动
    }

    /**
     * 2、清理过期的sysToken
     */
    public void sysTokenTask() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_2_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.clearToken();
            }
        });
        tr.start();//启动
    }

    /**
     * 3、计算每个店铺的商品总数
     */
    public void storeProductSum() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_3_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.storeProductSum();
            }
        });
        tr.start();//启动
    }

    /**
     * 4、清理超过30天的日志（删除日志表中的记录）
     */
    public void cleanLog() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_4_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.cleanLog();
            }
        });
        tr.start();//启动
    }

    /**
     * 5、维护会员收藏商品的状态（0下架，1上架）
     */
    public void updateCollectionProduct() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_5_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.updateCollectionProduct();
            }
        });
        tr.start();//启动
    }

    /**
     * 6、更新商品30天销量
     */
    public void monthSales() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_6_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.monthSales();
            }
        });
        tr.start();//启动
    }

    /**
     * 7、定时更新管理后台首页的统计数据
     */
    public void adminIndexInfo() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_7_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.adminIndexInfo();
            }
        });
        tr.start();//启动
    }

    /**
     * 8、查询并修改超过最晚收货时间的订单
     * 超过了最晚收货时间,将订单状态修改为已收货待评价
     */
    public void updateTradeOrder() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_8_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.updateTradeOrder();
            }
        });
        tr.start();//启动
    }

    /**
     * 9、处理超时订单。
     * 判断订单是否超过24小时未支付，若是则取消订单，释放库存。
     */
    public void cancleExpiredTradeOrder() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_9_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.cancleExpiredTradeOrder();
            }
        });
        tr.start();//启动
    }

    /**
     * 10、定时插入对账任务
     */
    public void insertBalanceTask() {
        //taskListService.balanceAccount();
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_10_NUM, new Runnable() {
            public void run() {
               //这是具体的任务内容
               taskListService.insertBalanceTask();
            }
        });
        tr.start();//启动
    }
    
    /**
     * 10、定时执行对账任务
     */
    public void balanceAccount() {
        //taskListService.balanceAccount();
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_10_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.balanceAccount();
            }
        });
        tr.start();//启动
    }

    /**
     * 11、定时处理超时的退款退货订单。
     * 查询超过15天的退款退货订单，但状态还是40待卖家收货的单子，将状态修改为待平台审核
     */
    public void updateTradeReturnOrder() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_11_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.updateTradeReturnOrder();
            }
        });
        tr.start();//启动
    }

    /**
     * 12、处理超时的采购单。
     * 查询超时的供采模块的采购单（20.审核未通过，30.待拆分，35.报价中，40.交易中），修改状态为50完成交易
     */
    public void updatePurchase() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_12_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                taskListService.updatePurchase();
            }
        });
        tr.start();//启动
    }

    /**
     * 13、处理超时的服务单。【目前无用】
     * 查询并修改超过接单截止时间的服务单
     */
    public void updateAppServiceInfoMeta() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_13_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                //TODO 还未从adq同步过来
                //taskListService.updateAppServiceInfoMeta();
            }
        });
        tr.start();//启动
    }

    /**
     * 14、定时清理页面缓存，并重新加载新的首页缓存【目前无用】
     */
    public void clearPageCache() {
        //定时任务的增强启动器

        // 参数0 taskId任务编号，要与数据库sys_timed_task(定时任务表)中的timed_task_num字段对应
        // 参数1 lockSeconds 锁的过期时间，单位：秒，传入null默认值是600秒
        // 注意：参数1这里传180秒，表示3分钟，当两次任务运行间隔小于10分钟时，都需要手动指定锁的过期时间，锁的过期时间默认是600秒
        TaskRunnable tr = new TaskRunnable(TASK_14_NUM, 180, new Runnable() {
            public void run() {
                //这是具体的任务内容
                //清理页面缓存
                Set<Object> set = cache.keys(PageCachingFilter.KEY_PREFIX + "*");
                Iterator<Object> it = set.iterator();
                while (it.hasNext()) {
                    cache.del(it.next());
                }
                //重新加载新的首页缓存，确定url地址目前是一个难点，所以配置到了fdp.p文件中
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("k", "clearPageCache");//无用
                String url = Global.getConfig("adq.index.url");//首页URL
                HttpClient.post(url, map1);//请求首页会重新加载缓存
            }
        });
        tr.start();//启动
    }

    /**
     * 15、服务结算定时任务(5大服务)【目前无用】
     */
    // TODO 补充
    /**
     * 16、回收过期的优惠券   【目前无用】
     */
    public void updateCouponTask() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_16_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                //TODO 还未从adq同步过来
                //taskListService.updateCouponTask();
            }
        });
        tr.start();//启动
    }

    /**
     * 30、清理过期的AppToken
     */
    public void clearAppToken() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_30_NUM, new Runnable() {
            public void run() {
                //这是具体的任务内容
                UserAppTokenService.clearAppToken();
            }
        });
        tr.start();//启动
    }

    /**
     * 31、计算店铺评分和销售量
     */
    public void computeScore() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_31_NUM, new Runnable() {
            @Override
            public void run() {
                storeAnalyzeService.score();
            }
        });
        tr.start();//启动
    }

    /**
     * 32、定时生成Solr索引（重建）
     */
    public void creatSolrIndex() {
        //定时任务的增强启动器
        TaskRunnable tr = new TaskRunnable(TASK_32_NUM, new Runnable() {
            @Override
            public void run() {
                siteCreateSolrIndexService.createProductIndex();
            }
        });
        tr.start();//启动
    }

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
