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

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Abort Policy.
 * 当队列满时改为调用BlockingQueue.put来实现生产者的阻塞
 */
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    protected static Logger logger = LoggerFactory.getLogger(AbortPolicyWithReport.class);

    public AbortPolicyWithReport() {
    }

    @Override
    public void rejectedExecution(Runnable run, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(run);
        } catch (InterruptedException e1) {
        }
    }
}