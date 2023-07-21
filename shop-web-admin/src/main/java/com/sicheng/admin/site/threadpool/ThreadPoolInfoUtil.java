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

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现取出线程池的信息
 * <p>
 * 如：池大小，活动线程数量，
 *
 * @author zhaorai
 */
public class ThreadPoolInfoUtil {
    /**
     * @param e    线程池对象
     * @param side 线程池在哪一端的描述信息，（服务端、客户端）
     * @return 文字信息
     */
    public static String getInfo(ThreadPoolExecutor e) {
        String msg_pool = String.format("Thread pool status--" +
                        "Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Queue size: %d,Task: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
                e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getQueue().size(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        return msg_pool;
    }
}
