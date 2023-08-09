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

import com.sicheng.admin.sys.entity.SysTimedTask;
import com.sicheng.admin.sys.entity.SysTimedTaskLog;
import com.sicheng.admin.sys.service.SysTimedTaskLogService;
import com.sicheng.admin.sys.service.SysTimedTaskService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.redis.Lock;
import com.sicheng.common.redis.LockConfig;
import com.sicheng.common.redis.LockManagerFactory;
import com.sicheng.common.web.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 定时任务的增强启动器， 做一个Runnable线程执行，表示一个任务。
 * 采用了模板模式，简化定时任务的开发，并增强了功能
 * <p>
 * 增强了以下功能：
 * 1、根据：管理后台-系统-定时任务 列表页中的开关设置，决定是否执行定时任务。
 * 2、记录任务的执行日志到日志表
 * 3、根据配置决定是否使用分布式锁，适用集群环境下的单点运行定时任务
 *
 * @author zhaolei
 */
public class TaskRunnable {
    private Integer taskId;
    private Runnable runnable;
    private SysTimedTaskService sysTimedTaskService;
    private SysTimedTaskLogService sysTimedTaskLogService;
    private LockConfig config;
    private Integer lockSeconds;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 构造方法
     *
     * @param taskId   任务ID，要与数据库中的ID对应
     * @param runnable 具体任务的实现
     */
    public TaskRunnable(Integer taskId, Runnable runnable) {
        this.taskId = taskId;
        this.runnable = runnable;
        this.sysTimedTaskService = SpringContextHolder.getBean(SysTimedTaskService.class);
        this.sysTimedTaskLogService = SpringContextHolder.getBean(SysTimedTaskLogService.class);
        this.config = SpringContextHolder.getBean(LockConfig.class);
    }

    /**
     * 构造方法
     *
     * @param taskId      任务ID，要与数据库中的ID对应
     * @param lockSeconds 锁的过期时间，单位：秒
     * @param runnable    具体任务的实现
     */
    public TaskRunnable(Integer taskId, Integer lockSeconds, Runnable runnable) {
        this.taskId = taskId;
        this.lockSeconds = lockSeconds;
        this.runnable = runnable;
        this.sysTimedTaskService = SpringContextHolder.getBean(SysTimedTaskService.class);
        this.sysTimedTaskLogService = SpringContextHolder.getBean(SysTimedTaskLogService.class);
        this.config = SpringContextHolder.getBean(LockConfig.class);
    }

    /**
     * 启动
     */
    public void start() {
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setTimedTaskNum(taskId);//按编号查询定时任务
        List<SysTimedTask> list = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));

        boolean run = false;
        if (list.isEmpty()) {
            run = true;  //表中未查到些任务的ID，默认按“运行”处理
        } else {
            String task_status = list.get(0).getStatus();//定时任务状态，0禁用、1启用
            if ("1".equals(task_status)) {
                run = true;
            }
        }
        if (!run) {
            return;
        }

        //是否使用分布式锁
        Lock lock = null;
        if (config != null && config.isDistributed()) {
            // 参数0 taskId 任务ID，要与数据库中的ID对应
            // 参数2 lockSeconds 锁的过期时间，单位：秒，传入null默认值是600秒
            lock = LockManagerFactory.getLockManager().getLock(taskId.toString(), 0, lockSeconds);//需要一个唯一标识
            if (lock == null) {
                return; //未能获得锁，退出。集群环境下有可能其它节点获得了锁。
            }
        }

        //插入定时任务日志
        String result = "2";//定时任务执行结果，0失败、1成功、2执行中
        Long sttlId = null;
        if (!list.isEmpty()) {
            Long sttId = list.get(0).getSttId();//定时任id
            sttlId = insertTaskLog(sttId, result);//获取数据库自增长
        }
        try {

            //执行具体的 任务内容
            runnable.run();

            result = "1";
        } catch (Exception e) {
            result = "0";
            logger.error("执行具体的"+taskId+"号定时任务失败", e);
        } finally {
            //不释放锁，600秒(lockSeconds参数)后会自行过期，考虑到多个节点的操作系统时间可能有误差，这里最多可解决600秒的误差。
            //定时任务可能3秒就执行完成了，但也要锁住600秒。一般的定时任务是每天一次、每小时一次，可满足。
            if (lock != null) {
                try {
                    //lock.close();
                } catch (Exception e) {
                }
            }
            if (sttlId != null) {
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
}
