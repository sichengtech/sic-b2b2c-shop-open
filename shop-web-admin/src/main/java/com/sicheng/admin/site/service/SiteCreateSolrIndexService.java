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
package com.sicheng.admin.site.service;

import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.admin.product.service.SolrProductService;
import com.sicheng.admin.site.threadpool.MyThreadPool;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.sys.service.SysVariableService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.search.ProductSearchInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 创建文章索引
 */
@Service
public class SiteCreateSolrIndexService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductSearchInterface productSearch;

    //主线程
    private Thread masterThread;
    //线程池
    private static ThreadPoolExecutor e = null;
    private String threadName = "静态化";
    //核心线程数
    private Integer corePoolSize = 0;
    //最大线程数
    private Integer maximumPoolSize = 5;
    //队列最大长度
    private Integer queues = 1000;

    //线程池维护线程所允许的空闲时间
    private Integer keepalive = 60;

    //是否在运行
    private boolean isrun = false;
    //完成任务标记
    private boolean finish = false;
    //任务启动时间
    private Date startTime;
    //已完成任务数量
    private AtomicInteger count;
    //当前处理任务的类型 ，为谁生成Solr索引，可选值：store、product
    private String subType;
    //查询一页的大小
    private Integer pageSize = 2000;

    public static final String STORE = "store"; //为店铺生成索引
    public static final String PRODUCT = "product";//为商品生成索引

    @Autowired
    private SysVariableService sysVariableService;
    @Autowired
    private SolrProductService solrProductService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Date OpenTime;
    private Date endTime;


    /**
     * 停止任务
     */
    public void stop() {
        /**
         * 主线程要自己跑完，这里先设置为停止运行，主线程的while就会执行完毕。
         * 线程池会在主线程里关闭，主线程等待线程池里的子线程跑完现有任务后自动结束。
         */
        isrun = false;
        finish = false;
        startTime = null;
    }

    /**
     * 启动任务(主线程)
     */
    public void start() {
        if (!isIsrun()) {
            //开启主线程
            masterThread = new Thread(new Runnable() {
                @Override
                @Transactional(rollbackFor = Exception.class)
                public void run() {
                    startTime = new Date();
                    isrun = true;
                    finish = false;
                    count = new AtomicInteger(0);
                    if (e != null) {
                        e.shutdownNow();
                    }
                    //创建一个线程池
                    e = (ThreadPoolExecutor) createExecutor();
                    Integer pageNo = 1;

                    Long countPage = null;
                    Wrapper wrapper = new Wrapper();
                    Page<SolrProduct> buffer = new Page<>(pageNo, pageSize);
                    if (subType == null) {
                        stop();
                    } else if (STORE.equals(subType.toLowerCase())) {
                        for (int i = 0; i <= 999000; i++) {
                            e.execute(new StoreTask(null));
                        }
                    } else if (PRODUCT.equals(subType.toLowerCase())) {
                        //先删除再添加
                        productSearch.deleteDocAll();
                        Page<SolrProduct> solrProductPage = solrProductService.selectByWhere(buffer, wrapper);
                        long count = solrProductPage.getCount();
                        countPage = getCountPage(solrProductPage.getCount(), pageSize);
                        while (pageNo <= countPage) {
                            e.execute(new ProductTask(solrProductPage.getList()));
                            pageNo++;
                            buffer = new Page<>(pageNo, pageSize,count);
                            solrProductPage = solrProductService.selectByWhere(buffer, wrapper);
                        }
                    }

                    //等任务完毕后关闭线程池
                    e.shutdown();
                    try {
                        //等待直到所有任务完成
                        e.awaitTermination(10, TimeUnit.MINUTES);
                    } catch (InterruptedException e) {
                        logger.error("异常", e);
                    }

                    isrun = false;
                    finish = true;
                    subType = "null";
                }
            });
            masterThread.start();
        }
    }
    /**
     * 定时生成Solr索引（重建商品索引），每天6点执行一次
     * 定时任务会来调用本方法，本方法是一个简化的同步方法，定时任务需要这样的同步方法。
     * 本方法不同于上的start()方法，start()方法是在多线程中执行生成Solr索引，每个线程负责2000条记录。start()方法是一个较复杂的实现。
     */
    public void createProductIndex(){
        Integer pageNo = 1;
        Long countPage = null;
        Wrapper wrapper = new Wrapper();
        Page<SolrProduct> buffer = new Page<>(pageNo, pageSize);
        //先删除再添加
        productSearch.deleteDocAll();
        Page<SolrProduct> solrProductPage = solrProductService.selectByWhere(buffer, wrapper);
        long count = solrProductPage.getCount();
        countPage = getCountPage(solrProductPage.getCount(), pageSize);
        while (pageNo <= countPage) {
            productSearch.addDocList(solrProductPage.getList());  //同步
            pageNo++;
            buffer = new Page<>(pageNo, pageSize,count);
            solrProductPage = solrProductService.selectByWhere(buffer, wrapper);
        }
    }

    /**
     * 一个店铺任务
     *
     * @author WIN10
     */
    private class StoreTask implements Runnable {
        List<Store> store;//文章实体类

        public StoreTask(List<Store> store) {
            this.store = store;
        }

        @Override
        public void run() {
            //当前是第几个任务
            int num = count.incrementAndGet();
            try {
                //生成索引
//                SolrSearchHelper.addList(cmsArticles);
                Thread.sleep(20);
            } catch (Exception e) {
                logger.info("对第" + num + "个任务生成索引失败，异常信息：", e);
            }
        }
    }

    /**
     * 一个商品任务
     *
     * @author WIN10
     */
    private class ProductTask implements Runnable {
        List<SolrProduct> solrProducts;//文章实体类

        public ProductTask(List<SolrProduct> solrProducts) {
            this.solrProducts = solrProducts;
        }

        @Override
        public void run() {
            //当前是第几个任务
            int num = count.incrementAndGet();
            try {
                productSearch.addDocList(solrProducts);
                System.out.println(num);
            } catch (Exception e) {
                logger.info("对第" + num + "个任务生成索引失败，异常信息：", e);
            }
        }
    }

    /**
     * 获取日志
     *
     * @return
     */
    public String getLog() {
        StringBuffer log = new StringBuffer();
        log.append("{");
        log.append("\"msg\":\"");
        log.append(simpleDateFormat.format(new Date()));
        log.append("," + FYUtils.fyParams("运行状态") + "=" + (isrun ? FYUtils.fyParams("运行中") : FYUtils.fyParams("未运行")));
        if (isrun) {
            log.append("," + FYUtils.fyParams("处理任务") + "=" + subType);
        }
        if (isrun) {
            //当前已完成的任务数
            Integer num = count.get() * pageSize;
            //计算从启动到现在过了多少毫秒数
            long tt = (System.currentTimeMillis() - startTime.getTime());
            //处理速度 个/秒
            float avg = (num * 1000F / tt);

            log.append("," + FYUtils.fyParams("启动日期") + "=" + simpleDateFormat.format(startTime));
            log.append("," + FYUtils.fyParams("是否完成任务") + "=" + (finish ? FYUtils.fyParams("是") : FYUtils.fyParams("否")));
            log.append("," + FYUtils.fyParams("完成任务量") + "=" + num);
            log.append("," + FYUtils.fyParams("平均速度(个/秒)") + "=" + avg);
        }
        if (finish) {
            log.append(FYUtils.fyParams("任务完成"));
        }
        log.append("\"");
        log.append("}");
        return log.toString();
    }

    /**
     * 创建一个线程池
     *
     * @return
     */
    private Executor createExecutor() {
        MyThreadPool pool = new MyThreadPool(threadName, corePoolSize, maximumPoolSize, queues, keepalive);
        Executor e = pool.getExecutor(null);
        return e;
    }

    /**
     * 将秒数转为天:时:分:秒
     *
     * @param time
     * @return
     */
    private String secToTime(int time) {
        String timeStr = null;
        int day;
        int hour;
        int minute;
        int second;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00天00小时" + unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                if (hour > 23) {
                    day = hour / 24;
                    hour = hour % 24;
                    minute = minute % 60;
                    second = time - (day * 24 * 3600) - (hour * 3600) - (minute * 60);
                    timeStr = unitFormat(day) + "天" + unitFormat(hour) + "小时" + unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
                } else {
                    minute = minute % 60;
                    second = time - (hour * 3600) - (minute * 60);
                    timeStr = "00天" + unitFormat(hour) + "小时" + unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
                }

            }
        }
        return timeStr;
    }

    /**
     * 将数字格式化为字符串
     *
     * @param i
     * @return
     */
    private String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + i;
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * 计算起始查询数
     *
     * @param pageSize
     * @param pageNo
     * @return
     */
    private Integer from(Integer pageSize, Integer pageNo) {
        if (pageSize == null || pageSize <= 0 || pageNo == null || pageNo <= 0) {
            return 0;
        }
        if (pageNo == 1) {
            return 0;
        }
        return pageSize * (pageNo - 1);
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Thread getMasterThread() {
        return masterThread;
    }

    public void setMasterThread(Thread masterThread) {
        this.masterThread = masterThread;
    }

    public static ThreadPoolExecutor getE() {
        return e;
    }

    public static void setE(ThreadPoolExecutor e) {
        SiteCreateSolrIndexService.e = e;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Integer getQueues() {
        return queues;
    }

    public void setQueues(Integer queues) {
        this.queues = queues;
    }

    public Integer getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(Integer keepalive) {
        this.keepalive = keepalive;
    }

    public boolean isIsrun() {
        return isrun;
    }

    public void setIsrun(boolean isrun) {
        this.isrun = isrun;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public SysVariableService getSysVariableService() {
        return sysVariableService;
    }

    public void setSysVariableService(SysVariableService sysVariableService) {
        this.sysVariableService = sysVariableService;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public Date getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(Date openTime) {
        OpenTime = openTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
