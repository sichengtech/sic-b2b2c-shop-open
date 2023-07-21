package com.sicheng.admin.task.utils;

import com.google.common.collect.Lists;
import com.sicheng.admin.sso.service.UserAppTokenService;
import com.sicheng.admin.store.service.StoreAnalyzeService;
import com.sicheng.admin.task.service.TaskListService;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.filter.PageCachingFilter;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.*;

//import com.sicheng.common.mqredis.ProducerService;
//import com.sicheng.common.mqredis.PublisherService;

/**
 * 定时任务类，每一个方法是一个定时任务
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

    /**
     * 缓存接口
     */
    @Autowired
    protected ShopCache cache;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //定时任务编号
    private final Integer TASK_0_NUM = 0;//测试定时任务（不在表中）
    private final Integer TASK_1_NUM = 1;//对商家结算
    private final Integer TASK_2_NUM = 2;//清理过期sysToken
    private final Integer TASK_3_NUM = 3;//计算每个店铺的商品总数
    private final Integer TASK_4_NUM = 4;//清理定时任务日志表
    private final Integer TASK_5_NUM = 5;//修改会员收藏商品的状态
    private final Integer TASK_6_NUM = 6;//一件商品30天所卖的数量
    private final Integer TASK_7_NUM = 7;//管理后台首页的数据
    private final Integer TASK_8_NUM = 8;//查询并修改超过最晚收货时间的订单
    private final Integer TASK_9_NUM = 9;//取消过期的订单
    private final Integer TASK_10_NUM = 10;//对账
    private final Integer TASK_11_NUM = 11;//查询并修改超过最晚收货时间的退款退货订单
    private final Integer TASK_12_NUM = 12;//查询并修改超过最晚到期时间的供采模块的采购单与采购单详情
    private final Integer TASK_13_NUM = 13;//查询并修改超过接单截止时间的服务单
    private final Integer TASK_14_NUM = 14;//定时清理页面缓存，并重新加载新的首页缓存
    private final Integer TASK_15_NUM = 15;//服务结算定时任务(5大服务)
    private final Integer TASK_16_NUM = 16;//服务结算定时任务(5大服务)
    private final Integer TASK_30_NUM = 30;//清理过期的AppToken
    private final Integer TASK_31_NUM = 31;//计算店铺评分和销售量

    /**
     * 计算店铺评分和销售量
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
     * 商品结算定时任务
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
     * 清理sysToken 过期token
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
     * 计算每个店铺的商品总数
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
     * 清理30天前工程所有的日志
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
     * 修改会员收藏商品的状态
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
     * 商品 30天销量
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
     * 管理后台首页的数据
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
     * 查询并修改超过最晚收货时间的订单
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
     * 取消过期的订单(超过24小时未支付的订单)
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
     * 插入对账任务
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
     * 执行对账对账任务
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
     * 查询并修改超过最晚收货时间的退款退货订单
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
     * 修改超过最晚到期时间的供采模块的采购单与采购单详情
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
     * 定时清理页面缓存，并重新加载新的首页缓存
     */
    public void clearPageCache() {
        //定时任务的增强启动器

        // 参数0 taskId 任务ID，要与数据库中的ID对应
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
                HttpPost.post(url, map1);//请求首页会重新加载缓存
            }
        });
        tr.start();//启动
    }

    /**
     * 回收过期的优惠券
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
     * 清理过期的AppToken
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
