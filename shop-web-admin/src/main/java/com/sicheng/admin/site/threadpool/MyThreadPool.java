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
package com.sicheng.admin.site.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;


/**
 * FixedThreadPool
 * <p>
 * 通过如下方式创建线程池：
 * AbstractExecutorService executor=new ThreadPoolExecutor(3,10,30L,TimeUnit.SECONDS,new SynchronousQueue(),new ExecutorThreadFactory("ThrowableThreadPoolExecutor"),new  AbortPolicy());
 * new ExecutorThreadFactory("ThrowableThreadPoolExecutor")简单的封装了ThreadFactory
 * 由于SynchronousQueue并不是一个真正的队列，而是一种管理直接在线程间移交信息的机制，为了把一个元素放入到SynchronousQueue中，必须有另一个线程正在等待接受移交的任务。如果没有这样一个线程，只要当前池的大小还小于最大值，ThreadPoolExcutor就会创建一个新的线程；否则根据饱和策略，任务会被拒绝。而设置的饱和策略恰恰是new  AbortPolicy()，当线程池满了后，execute抛出未检查的RejectedExecutionException，线程丢失。可以通过捕获该异常做相应的补救处理。
 * <p>
 * 另外的处理方式是设置线程池的队列的饱和策略，线程池创建如下：
 * AbstractExecutorService executor = new ThrowableThreadPoolExecutor(10,40, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
 * new ExecutorThreadFactory("ThrowableThreadPoolExecutor"), new ThreadPoolExecutor.CallerRunsPolicy());
 * <p>
 * 当任务向线程池请求要分配线程时，线程池处理如下：
 * <p>
 * 1、如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务
 * 2、如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列
 * <p>
 * 3、如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务
 * 4、如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务
 * 5、当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数
 * <p>
 * 1、    在默认的 ThreadPoolExecutor.AbortPolicy 中，处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
 * <p>
 * 2、    在 ThreadPoolExecutor.CallerRunsPolicy 中，线程调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制，能够减缓新任务的提交速度。
 * <p>
 * 3、    在 ThreadPoolExecutor.DiscardPolicy 中，不能执行的任务将被删除。
 * <p>
 * 4、    在 ThreadPoolExecutor.DiscardOldestPolicy 中，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
 */
public class MyThreadPool implements ThreadPool {
    private static Logger logger = LoggerFactory.getLogger(MyThreadPool.class);
    //线程的name的前缀
    String threadName = "worker";

    //线程池core_Pool_Size的值  
    int corePoolSize = 5;

    //线程池maximumPoolSize值
    int maximumPoolSize = 20;

    //线程池队列大小值  
    int queues = 50;

    //线程空闲时间超过keepAliveTime，线程将被终止
    int keepalive = 6000;

    public MyThreadPool() {
    }

    public MyThreadPool(int poolSize) {

        //线程池core_Pool_Size的值  
        this.corePoolSize = poolSize;

        //线程池maximumPoolSize值
        this.maximumPoolSize = poolSize;

        //线程池队列大小值  
        this.queues = 0;

    }

    public MyThreadPool(String threadName, int corePoolSize, int maximumPoolSize, int queues, int keepalive) {
        //线程的name的前缀
        this.threadName = threadName;

        //线程池core_Pool_Size的值  
        this.corePoolSize = corePoolSize;

        //线程池maximumPoolSize值
        this.maximumPoolSize = maximumPoolSize;

        //线程池队列大小值  
        this.queues = queues;

        //线程空闲时间超过keepAliveTime，线程将被终止
        this.keepalive = keepalive;
    }


    public Executor getExecutor(Object args) {
        logger.info("线程池类型=FixedThreadPool" + ",corePoolSize=" + corePoolSize + ",maximumPoolSize=" + maximumPoolSize + ",queueSize=" + queues + ",keepAliveTime(ms)=" + keepalive);
        //处理程序遭到拒绝将阻塞
        RejectedExecutionHandler rejected = new AbortPolicyWithReport();

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepalive, TimeUnit.MILLISECONDS,
                queues <= 0 ? new SynchronousQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(queues),
                new NamedThreadFactory(threadName, true), rejected);

    }
}